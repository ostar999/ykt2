package androidx.camera.camera2.internal;

import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.view.Surface;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.camera.camera2.internal.CaptureSession;
import androidx.camera.core.Logger;
import androidx.camera.core.impl.CameraCaptureCallback;
import androidx.camera.core.impl.CameraCaptureFailure;
import androidx.camera.core.impl.CaptureConfig;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.camera.core.impl.RequestProcessor;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.core.impl.SessionProcessorSurface;
import androidx.camera.core.impl.TagBundle;
import androidx.core.util.Preconditions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@RequiresApi(21)
/* loaded from: classes.dex */
public class Camera2RequestProcessor implements RequestProcessor {
    private static final String TAG = "Camera2RequestProcessor";

    @NonNull
    private final CaptureSession mCaptureSession;
    private volatile boolean mIsClosed = false;

    @NonNull
    private final List<SessionProcessorSurface> mProcessorSurfaces;

    @Nullable
    private volatile SessionConfig mSessionConfig;

    public class Camera2CallbackWrapper extends CameraCaptureSession.CaptureCallback {
        private final RequestProcessor.Callback mCallback;
        private final boolean mInvokeSequenceCallback;
        private final RequestProcessor.Request mRequest;

        public Camera2CallbackWrapper(@NonNull RequestProcessor.Request request, @NonNull RequestProcessor.Callback callback, boolean z2) {
            this.mCallback = callback;
            this.mRequest = request;
            this.mInvokeSequenceCallback = z2;
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureBufferLost(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull Surface surface, long j2) {
            this.mCallback.onCaptureBufferLost(this.mRequest, j2, Camera2RequestProcessor.this.findOutputConfigId(surface));
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureCompleted(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull TotalCaptureResult totalCaptureResult) {
            this.mCallback.onCaptureCompleted(this.mRequest, new Camera2CameraCaptureResult(totalCaptureResult));
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureFailed(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull CaptureFailure captureFailure) {
            this.mCallback.onCaptureFailed(this.mRequest, new Camera2CameraCaptureFailure(CameraCaptureFailure.Reason.ERROR, captureFailure));
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureProgressed(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull CaptureResult captureResult) {
            this.mCallback.onCaptureProgressed(this.mRequest, new Camera2CameraCaptureResult(captureResult));
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureSequenceAborted(@NonNull CameraCaptureSession cameraCaptureSession, int i2) {
            if (this.mInvokeSequenceCallback) {
                this.mCallback.onCaptureSequenceAborted(i2);
            }
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureSequenceCompleted(@NonNull CameraCaptureSession cameraCaptureSession, int i2, long j2) {
            if (this.mInvokeSequenceCallback) {
                this.mCallback.onCaptureSequenceCompleted(i2, j2);
            }
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureStarted(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, long j2, long j3) {
            this.mCallback.onCaptureStarted(this.mRequest, j3, j2);
        }
    }

    public Camera2RequestProcessor(@NonNull CaptureSession captureSession, @NonNull List<SessionProcessorSurface> list) {
        Preconditions.checkArgument(captureSession.mState == CaptureSession.State.OPENED, "CaptureSession state must be OPENED. Current state:" + captureSession.mState);
        this.mCaptureSession = captureSession;
        this.mProcessorSurfaces = Collections.unmodifiableList(new ArrayList(list));
    }

    private boolean areRequestsValid(@NonNull List<RequestProcessor.Request> list) {
        Iterator<RequestProcessor.Request> it = list.iterator();
        while (it.hasNext()) {
            if (!isRequestValid(it.next())) {
                return false;
            }
        }
        return true;
    }

    @Nullable
    private DeferrableSurface findSurface(int i2) {
        for (SessionProcessorSurface sessionProcessorSurface : this.mProcessorSurfaces) {
            if (sessionProcessorSurface.getOutputConfigId() == i2) {
                return sessionProcessorSurface;
            }
        }
        return null;
    }

    private boolean isRequestValid(@NonNull RequestProcessor.Request request) {
        if (request.getTargetOutputConfigIds().isEmpty()) {
            Logger.e(TAG, "Unable to submit the RequestProcessor.Request: empty targetOutputConfigIds");
            return false;
        }
        for (Integer num : request.getTargetOutputConfigIds()) {
            if (findSurface(num.intValue()) == null) {
                Logger.e(TAG, "Unable to submit the RequestProcessor.Request: targetOutputConfigId(" + num + ") is not a valid id");
                return false;
            }
        }
        return true;
    }

    @Override // androidx.camera.core.impl.RequestProcessor
    public void abortCaptures() {
        if (this.mIsClosed) {
            return;
        }
        this.mCaptureSession.abortCaptures();
    }

    public void close() {
        this.mIsClosed = true;
    }

    public int findOutputConfigId(@NonNull Surface surface) {
        for (SessionProcessorSurface sessionProcessorSurface : this.mProcessorSurfaces) {
            if (sessionProcessorSurface.getSurface().get() == surface) {
                return sessionProcessorSurface.getOutputConfigId();
            }
            continue;
        }
        return -1;
    }

    @Override // androidx.camera.core.impl.RequestProcessor
    public int setRepeating(@NonNull RequestProcessor.Request request, @NonNull RequestProcessor.Callback callback) {
        if (this.mIsClosed || !isRequestValid(request)) {
            return -1;
        }
        SessionConfig.Builder builder = new SessionConfig.Builder();
        builder.setTemplateType(request.getTemplateId());
        builder.setImplementationOptions(request.getParameters());
        builder.addCameraCaptureCallback(CaptureCallbackContainer.create(new Camera2CallbackWrapper(request, callback, true)));
        if (this.mSessionConfig != null) {
            Iterator<CameraCaptureCallback> it = this.mSessionConfig.getRepeatingCameraCaptureCallbacks().iterator();
            while (it.hasNext()) {
                builder.addCameraCaptureCallback(it.next());
            }
            TagBundle tagBundle = this.mSessionConfig.getRepeatingCaptureConfig().getTagBundle();
            for (String str : tagBundle.listKeys()) {
                builder.addTag(str, tagBundle.getTag(str));
            }
        }
        Iterator<Integer> it2 = request.getTargetOutputConfigIds().iterator();
        while (it2.hasNext()) {
            builder.addSurface(findSurface(it2.next().intValue()));
        }
        return this.mCaptureSession.issueRepeatingCaptureRequests(builder.build());
    }

    @Override // androidx.camera.core.impl.RequestProcessor
    public void stopRepeating() {
        if (this.mIsClosed) {
            return;
        }
        this.mCaptureSession.stopRepeating();
    }

    @Override // androidx.camera.core.impl.RequestProcessor
    public int submit(@NonNull RequestProcessor.Request request, @NonNull RequestProcessor.Callback callback) {
        return submit(Arrays.asList(request), callback);
    }

    public void updateSessionConfig(@Nullable SessionConfig sessionConfig) {
        this.mSessionConfig = sessionConfig;
    }

    @Override // androidx.camera.core.impl.RequestProcessor
    public int submit(@NonNull List<RequestProcessor.Request> list, @NonNull RequestProcessor.Callback callback) {
        if (this.mIsClosed || !areRequestsValid(list)) {
            return -1;
        }
        ArrayList arrayList = new ArrayList();
        boolean z2 = true;
        for (RequestProcessor.Request request : list) {
            CaptureConfig.Builder builder = new CaptureConfig.Builder();
            builder.setTemplateType(request.getTemplateId());
            builder.setImplementationOptions(request.getParameters());
            builder.addCameraCaptureCallback(CaptureCallbackContainer.create(new Camera2CallbackWrapper(request, callback, z2)));
            Iterator<Integer> it = request.getTargetOutputConfigIds().iterator();
            while (it.hasNext()) {
                builder.addSurface(findSurface(it.next().intValue()));
            }
            arrayList.add(builder.build());
            z2 = false;
        }
        return this.mCaptureSession.issueBurstCaptureRequest(arrayList);
    }
}
