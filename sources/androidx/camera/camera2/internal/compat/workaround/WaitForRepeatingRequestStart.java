package androidx.camera.camera2.internal.compat.workaround;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureRequest;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.camera.camera2.internal.Camera2CaptureCallbacks;
import androidx.camera.camera2.internal.SynchronizedCaptureSession;
import androidx.camera.camera2.internal.compat.params.SessionConfigurationCompat;
import androidx.camera.camera2.internal.compat.quirk.CaptureSessionStuckQuirk;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.camera.core.impl.Quirks;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.impl.utils.futures.AsyncFunction;
import androidx.camera.core.impl.utils.futures.FutureChain;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import cn.hutool.core.text.StrPool;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RequiresApi(21)
/* loaded from: classes.dex */
public class WaitForRepeatingRequestStart {
    private final boolean mHasCaptureSessionStuckQuirk;
    private boolean mHasSubmittedRepeating;
    CallbackToFutureAdapter.Completer<Void> mStartStreamingCompleter;

    @NonNull
    private final ListenableFuture<Void> mStartStreamingFuture;
    private final Object mLock = new Object();
    private final CameraCaptureSession.CaptureCallback mCaptureCallback = new CameraCaptureSession.CaptureCallback() { // from class: androidx.camera.camera2.internal.compat.workaround.WaitForRepeatingRequestStart.1
        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureSequenceAborted(@NonNull CameraCaptureSession cameraCaptureSession, int i2) {
            CallbackToFutureAdapter.Completer<Void> completer = WaitForRepeatingRequestStart.this.mStartStreamingCompleter;
            if (completer != null) {
                completer.setCancelled();
                WaitForRepeatingRequestStart.this.mStartStreamingCompleter = null;
            }
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureStarted(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, long j2, long j3) {
            CallbackToFutureAdapter.Completer<Void> completer = WaitForRepeatingRequestStart.this.mStartStreamingCompleter;
            if (completer != null) {
                completer.set(null);
                WaitForRepeatingRequestStart.this.mStartStreamingCompleter = null;
            }
        }
    };

    @FunctionalInterface
    public interface OpenCaptureSession {
        @NonNull
        ListenableFuture<Void> run(@NonNull CameraDevice cameraDevice, @NonNull SessionConfigurationCompat sessionConfigurationCompat, @NonNull List<DeferrableSurface> list);
    }

    @FunctionalInterface
    public interface SingleRepeatingRequest {
        int run(@NonNull CaptureRequest captureRequest, @NonNull CameraCaptureSession.CaptureCallback captureCallback) throws CameraAccessException;
    }

    public WaitForRepeatingRequestStart(@NonNull Quirks quirks) {
        this.mHasCaptureSessionStuckQuirk = quirks.contains(CaptureSessionStuckQuirk.class);
        if (shouldWaitRepeatingSubmit()) {
            this.mStartStreamingFuture = CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.camera2.internal.compat.workaround.b
                @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
                public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                    return this.f1294a.lambda$new$0(completer);
                }
            });
        } else {
            this.mStartStreamingFuture = Futures.immediateFuture(null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Object lambda$new$0(CallbackToFutureAdapter.Completer completer) throws Exception {
        this.mStartStreamingCompleter = completer;
        return "WaitForRepeatingRequestStart[" + this + StrPool.BRACKET_END;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ ListenableFuture lambda$openCaptureSession$1(OpenCaptureSession openCaptureSession, CameraDevice cameraDevice, SessionConfigurationCompat sessionConfigurationCompat, List list, List list2) throws Exception {
        return openCaptureSession.run(cameraDevice, sessionConfigurationCompat, list);
    }

    @NonNull
    public ListenableFuture<Void> getStartStreamFuture() {
        return Futures.nonCancellationPropagating(this.mStartStreamingFuture);
    }

    public void onSessionEnd() {
        synchronized (this.mLock) {
            if (shouldWaitRepeatingSubmit() && !this.mHasSubmittedRepeating) {
                this.mStartStreamingFuture.cancel(true);
            }
        }
    }

    @NonNull
    public ListenableFuture<Void> openCaptureSession(@NonNull final CameraDevice cameraDevice, @NonNull final SessionConfigurationCompat sessionConfigurationCompat, @NonNull final List<DeferrableSurface> list, @NonNull List<SynchronizedCaptureSession> list2, @NonNull final OpenCaptureSession openCaptureSession) {
        ArrayList arrayList = new ArrayList();
        Iterator<SynchronizedCaptureSession> it = list2.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getOpeningBlocker());
        }
        return FutureChain.from(Futures.successfulAsList(arrayList)).transformAsync(new AsyncFunction() { // from class: androidx.camera.camera2.internal.compat.workaround.c
            @Override // androidx.camera.core.impl.utils.futures.AsyncFunction
            public final ListenableFuture apply(Object obj) {
                return WaitForRepeatingRequestStart.lambda$openCaptureSession$1(openCaptureSession, cameraDevice, sessionConfigurationCompat, list, (List) obj);
            }
        }, CameraXExecutors.directExecutor());
    }

    public int setSingleRepeatingRequest(@NonNull CaptureRequest captureRequest, @NonNull CameraCaptureSession.CaptureCallback captureCallback, @NonNull SingleRepeatingRequest singleRepeatingRequest) throws CameraAccessException {
        int iRun;
        synchronized (this.mLock) {
            if (shouldWaitRepeatingSubmit()) {
                captureCallback = Camera2CaptureCallbacks.createComboCallback(this.mCaptureCallback, captureCallback);
                this.mHasSubmittedRepeating = true;
            }
            iRun = singleRepeatingRequest.run(captureRequest, captureCallback);
        }
        return iRun;
    }

    public boolean shouldWaitRepeatingSubmit() {
        return this.mHasCaptureSessionStuckQuirk;
    }
}
