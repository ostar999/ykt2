package androidx.camera.camera2.internal;

import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.util.Size;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.OptIn;
import androidx.annotation.RequiresApi;
import androidx.arch.core.util.Function;
import androidx.camera.camera2.impl.Camera2ImplConfig;
import androidx.camera.camera2.interop.CaptureRequestOptions;
import androidx.camera.camera2.interop.ExperimentalCamera2Interop;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.Logger;
import androidx.camera.core.Preview;
import androidx.camera.core.impl.CameraCaptureCallback;
import androidx.camera.core.impl.CameraCaptureFailure;
import androidx.camera.core.impl.CameraCaptureResult;
import androidx.camera.core.impl.CaptureConfig;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.camera.core.impl.DeferrableSurfaces;
import androidx.camera.core.impl.OutputSurface;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.core.impl.SessionProcessor;
import androidx.camera.core.impl.SessionProcessorSurface;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.impl.utils.futures.AsyncFunction;
import androidx.camera.core.impl.utils.futures.FutureCallback;
import androidx.camera.core.impl.utils.futures.FutureChain;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.core.util.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;

@OptIn(markerClass = {ExperimentalCamera2Interop.class})
@RequiresApi(21)
/* loaded from: classes.dex */
final class ProcessingCaptureSession implements CaptureSessionInterface {
    private static final String TAG = "ProcessingCaptureSession";
    private static final long TIMEOUT_GET_SURFACE_IN_MS = 5000;
    private static List<DeferrableSurface> sHeldProcessorSurfaces = new ArrayList();
    private static int sNextInstanceId = 0;
    private final Camera2CameraInfoImpl mCamera2CameraInfoImpl;
    final Executor mExecutor;
    private int mInstanceId;

    @Nullable
    private SessionConfig mProcessorSessionConfig;

    @Nullable
    private Camera2RequestProcessor mRequestProcessor;
    private final ScheduledExecutorService mScheduledExecutorService;

    @Nullable
    private SessionConfig mSessionConfig;
    private final SessionProcessor mSessionProcessor;
    private List<DeferrableSurface> mOutputSurfaces = new ArrayList();

    @Nullable
    private volatile CaptureConfig mPendingCaptureConfig = null;
    volatile boolean mIsExecutingStillCaptureRequest = false;
    private CaptureRequestOptions mSessionOptions = new CaptureRequestOptions.Builder().build();
    private CaptureRequestOptions mStillCaptureOptions = new CaptureRequestOptions.Builder().build();
    private final CaptureSession mCaptureSession = new CaptureSession();
    private ProcessorState mProcessorState = ProcessorState.UNINITIALIZED;
    private final SessionProcessorCaptureCallback mSessionProcessorCaptureCallback = new SessionProcessorCaptureCallback();

    /* renamed from: androidx.camera.camera2.internal.ProcessingCaptureSession$2, reason: invalid class name */
    public class AnonymousClass2 implements SessionProcessor.CaptureCallback {
        final /* synthetic */ CaptureConfig val$captureConfig;

        public AnonymousClass2(CaptureConfig captureConfig) {
            this.val$captureConfig = captureConfig;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureFailed$0(CaptureConfig captureConfig) {
            Iterator<CameraCaptureCallback> it = captureConfig.getCameraCaptureCallbacks().iterator();
            while (it.hasNext()) {
                it.next().onCaptureFailed(new CameraCaptureFailure(CameraCaptureFailure.Reason.ERROR));
            }
            ProcessingCaptureSession.this.mIsExecutingStillCaptureRequest = false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureSequenceCompleted$1(CaptureConfig captureConfig) {
            Iterator<CameraCaptureCallback> it = captureConfig.getCameraCaptureCallbacks().iterator();
            while (it.hasNext()) {
                it.next().onCaptureCompleted(new CameraCaptureResult.EmptyCameraCaptureResult());
            }
            ProcessingCaptureSession.this.mIsExecutingStillCaptureRequest = false;
        }

        @Override // androidx.camera.core.impl.SessionProcessor.CaptureCallback
        public void onCaptureCompleted(long j2, int i2, @NonNull Map<CaptureResult.Key, Object> map) {
        }

        @Override // androidx.camera.core.impl.SessionProcessor.CaptureCallback
        public void onCaptureFailed(int i2) {
            Executor executor = ProcessingCaptureSession.this.mExecutor;
            final CaptureConfig captureConfig = this.val$captureConfig;
            executor.execute(new Runnable() { // from class: androidx.camera.camera2.internal.w1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f1430c.lambda$onCaptureFailed$0(captureConfig);
                }
            });
        }

        @Override // androidx.camera.core.impl.SessionProcessor.CaptureCallback
        public void onCaptureProcessStarted(int i2) {
        }

        @Override // androidx.camera.core.impl.SessionProcessor.CaptureCallback
        public void onCaptureSequenceAborted(int i2) {
        }

        @Override // androidx.camera.core.impl.SessionProcessor.CaptureCallback
        public void onCaptureSequenceCompleted(int i2) {
            Executor executor = ProcessingCaptureSession.this.mExecutor;
            final CaptureConfig captureConfig = this.val$captureConfig;
            executor.execute(new Runnable() { // from class: androidx.camera.camera2.internal.v1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f1425c.lambda$onCaptureSequenceCompleted$1(captureConfig);
                }
            });
        }

        @Override // androidx.camera.core.impl.SessionProcessor.CaptureCallback
        public void onCaptureStarted(int i2, long j2) {
        }
    }

    /* renamed from: androidx.camera.camera2.internal.ProcessingCaptureSession$3, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$androidx$camera$camera2$internal$ProcessingCaptureSession$ProcessorState;

        static {
            int[] iArr = new int[ProcessorState.values().length];
            $SwitchMap$androidx$camera$camera2$internal$ProcessingCaptureSession$ProcessorState = iArr;
            try {
                iArr[ProcessorState.UNINITIALIZED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$androidx$camera$camera2$internal$ProcessingCaptureSession$ProcessorState[ProcessorState.SESSION_INITIALIZED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$androidx$camera$camera2$internal$ProcessingCaptureSession$ProcessorState[ProcessorState.ON_CAPTURE_SESSION_STARTED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$androidx$camera$camera2$internal$ProcessingCaptureSession$ProcessorState[ProcessorState.ON_CAPTURE_SESSION_ENDED.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$androidx$camera$camera2$internal$ProcessingCaptureSession$ProcessorState[ProcessorState.CLOSED.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public enum ProcessorState {
        UNINITIALIZED,
        SESSION_INITIALIZED,
        ON_CAPTURE_SESSION_STARTED,
        ON_CAPTURE_SESSION_ENDED,
        CLOSED
    }

    public static class SessionProcessorCaptureCallback implements SessionProcessor.CaptureCallback {
        @Override // androidx.camera.core.impl.SessionProcessor.CaptureCallback
        public void onCaptureCompleted(long j2, int i2, @NonNull Map<CaptureResult.Key, Object> map) {
        }

        @Override // androidx.camera.core.impl.SessionProcessor.CaptureCallback
        public void onCaptureFailed(int i2) {
        }

        @Override // androidx.camera.core.impl.SessionProcessor.CaptureCallback
        public void onCaptureProcessStarted(int i2) {
        }

        @Override // androidx.camera.core.impl.SessionProcessor.CaptureCallback
        public void onCaptureSequenceAborted(int i2) {
        }

        @Override // androidx.camera.core.impl.SessionProcessor.CaptureCallback
        public void onCaptureSequenceCompleted(int i2) {
        }

        @Override // androidx.camera.core.impl.SessionProcessor.CaptureCallback
        public void onCaptureStarted(int i2, long j2) {
        }
    }

    public ProcessingCaptureSession(@NonNull SessionProcessor sessionProcessor, @NonNull Camera2CameraInfoImpl camera2CameraInfoImpl, @NonNull Executor executor, @NonNull ScheduledExecutorService scheduledExecutorService) {
        this.mInstanceId = 0;
        this.mSessionProcessor = sessionProcessor;
        this.mCamera2CameraInfoImpl = camera2CameraInfoImpl;
        this.mExecutor = executor;
        this.mScheduledExecutorService = scheduledExecutorService;
        int i2 = sNextInstanceId;
        sNextInstanceId = i2 + 1;
        this.mInstanceId = i2;
        Logger.d(TAG, "New ProcessingCaptureSession (id=" + this.mInstanceId + ")");
    }

    private static void cancelRequests(@NonNull List<CaptureConfig> list) {
        Iterator<CaptureConfig> it = list.iterator();
        while (it.hasNext()) {
            Iterator<CameraCaptureCallback> it2 = it.next().getCameraCaptureCallbacks().iterator();
            while (it2.hasNext()) {
                it2.next().onCaptureCancelled();
            }
        }
    }

    private static List<SessionProcessorSurface> getSessionProcessorSurfaceList(List<DeferrableSurface> list) {
        ArrayList arrayList = new ArrayList();
        for (DeferrableSurface deferrableSurface : list) {
            Preconditions.checkArgument(deferrableSurface instanceof SessionProcessorSurface, "Surface must be SessionProcessorSurface");
            arrayList.add((SessionProcessorSurface) deferrableSurface);
        }
        return arrayList;
    }

    private boolean isStillCapture(@NonNull List<CaptureConfig> list) {
        if (list.isEmpty()) {
            return false;
        }
        Iterator<CaptureConfig> it = list.iterator();
        while (it.hasNext()) {
            if (it.next().getTemplateType() != 2) {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$open$0() {
        DeferrableSurfaces.decrementAll(this.mOutputSurfaces);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$open$1(DeferrableSurface deferrableSurface) {
        sHeldProcessorSurfaces.remove(deferrableSurface);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ ListenableFuture lambda$open$2(SessionConfig sessionConfig, CameraDevice cameraDevice, SynchronizedCaptureSessionOpener synchronizedCaptureSessionOpener, List list) throws Exception {
        Logger.d(TAG, "-- getSurfaces done, start init (id=" + this.mInstanceId + ")");
        if (this.mProcessorState == ProcessorState.CLOSED) {
            return Futures.immediateFailedFuture(new IllegalStateException("SessionProcessorCaptureSession is closed."));
        }
        OutputSurface outputSurfaceCreate = null;
        if (list.contains(null)) {
            return Futures.immediateFailedFuture(new DeferrableSurface.SurfaceClosedException("Surface closed", sessionConfig.getSurfaces().get(list.indexOf(null))));
        }
        try {
            DeferrableSurfaces.incrementAll(this.mOutputSurfaces);
            OutputSurface outputSurfaceCreate2 = null;
            OutputSurface outputSurfaceCreate3 = null;
            for (int i2 = 0; i2 < sessionConfig.getSurfaces().size(); i2++) {
                DeferrableSurface deferrableSurface = sessionConfig.getSurfaces().get(i2);
                if (Objects.equals(deferrableSurface.getContainerClass(), Preview.class)) {
                    outputSurfaceCreate = OutputSurface.create(deferrableSurface.getSurface().get(), new Size(deferrableSurface.getPrescribedSize().getWidth(), deferrableSurface.getPrescribedSize().getHeight()), deferrableSurface.getPrescribedStreamFormat());
                } else if (Objects.equals(deferrableSurface.getContainerClass(), ImageCapture.class)) {
                    outputSurfaceCreate2 = OutputSurface.create(deferrableSurface.getSurface().get(), new Size(deferrableSurface.getPrescribedSize().getWidth(), deferrableSurface.getPrescribedSize().getHeight()), deferrableSurface.getPrescribedStreamFormat());
                } else if (Objects.equals(deferrableSurface.getContainerClass(), ImageAnalysis.class)) {
                    outputSurfaceCreate3 = OutputSurface.create(deferrableSurface.getSurface().get(), new Size(deferrableSurface.getPrescribedSize().getWidth(), deferrableSurface.getPrescribedSize().getHeight()), deferrableSurface.getPrescribedStreamFormat());
                }
            }
            this.mProcessorState = ProcessorState.SESSION_INITIALIZED;
            Logger.w(TAG, "== initSession (id=" + this.mInstanceId + ")");
            SessionConfig sessionConfigInitSession = this.mSessionProcessor.initSession(this.mCamera2CameraInfoImpl, outputSurfaceCreate, outputSurfaceCreate2, outputSurfaceCreate3);
            this.mProcessorSessionConfig = sessionConfigInitSession;
            sessionConfigInitSession.getSurfaces().get(0).getTerminationFuture().addListener(new Runnable() { // from class: androidx.camera.camera2.internal.r1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f1410c.lambda$open$0();
                }
            }, CameraXExecutors.directExecutor());
            for (final DeferrableSurface deferrableSurface2 : this.mProcessorSessionConfig.getSurfaces()) {
                sHeldProcessorSurfaces.add(deferrableSurface2);
                deferrableSurface2.getTerminationFuture().addListener(new Runnable() { // from class: androidx.camera.camera2.internal.s1
                    @Override // java.lang.Runnable
                    public final void run() {
                        ProcessingCaptureSession.lambda$open$1(deferrableSurface2);
                    }
                }, this.mExecutor);
            }
            SessionConfig.ValidatingBuilder validatingBuilder = new SessionConfig.ValidatingBuilder();
            validatingBuilder.add(sessionConfig);
            validatingBuilder.clearSurfaces();
            validatingBuilder.add(this.mProcessorSessionConfig);
            Preconditions.checkArgument(validatingBuilder.isValid(), "Cannot transform the SessionConfig");
            ListenableFuture<Void> listenableFutureOpen = this.mCaptureSession.open(validatingBuilder.build(), (CameraDevice) Preconditions.checkNotNull(cameraDevice), synchronizedCaptureSessionOpener);
            Futures.addCallback(listenableFutureOpen, new FutureCallback<Void>() { // from class: androidx.camera.camera2.internal.ProcessingCaptureSession.1
                @Override // androidx.camera.core.impl.utils.futures.FutureCallback
                public void onFailure(@NonNull Throwable th) {
                    Logger.e(ProcessingCaptureSession.TAG, "open session failed ", th);
                    ProcessingCaptureSession.this.close();
                }

                @Override // androidx.camera.core.impl.utils.futures.FutureCallback
                public void onSuccess(@Nullable Void r12) {
                }
            }, this.mExecutor);
            return listenableFutureOpen;
        } catch (DeferrableSurface.SurfaceClosedException e2) {
            return Futures.immediateFailedFuture(e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Void lambda$open$3(Void r12) {
        onConfigured(this.mCaptureSession);
        return null;
    }

    private void updateParameters(@NonNull CaptureRequestOptions captureRequestOptions, @NonNull CaptureRequestOptions captureRequestOptions2) {
        Camera2ImplConfig.Builder builder = new Camera2ImplConfig.Builder();
        builder.insertAllOptions(captureRequestOptions);
        builder.insertAllOptions(captureRequestOptions2);
        this.mSessionProcessor.setParameters(builder.build());
    }

    @Override // androidx.camera.camera2.internal.CaptureSessionInterface
    public void cancelIssuedCaptureRequests() {
        Logger.d(TAG, "cancelIssuedCaptureRequests (id=" + this.mInstanceId + ")");
        if (this.mPendingCaptureConfig != null) {
            Iterator<CameraCaptureCallback> it = this.mPendingCaptureConfig.getCameraCaptureCallbacks().iterator();
            while (it.hasNext()) {
                it.next().onCaptureCancelled();
            }
            this.mPendingCaptureConfig = null;
        }
    }

    @Override // androidx.camera.camera2.internal.CaptureSessionInterface
    public void close() {
        Logger.d(TAG, "close (id=" + this.mInstanceId + ") state=" + this.mProcessorState);
        int i2 = AnonymousClass3.$SwitchMap$androidx$camera$camera2$internal$ProcessingCaptureSession$ProcessorState[this.mProcessorState.ordinal()];
        if (i2 == 2) {
            this.mSessionProcessor.deInitSession();
        } else {
            if (i2 == 3) {
                this.mSessionProcessor.onCaptureSessionEnd();
                Camera2RequestProcessor camera2RequestProcessor = this.mRequestProcessor;
                if (camera2RequestProcessor != null) {
                    camera2RequestProcessor.close();
                }
                this.mProcessorState = ProcessorState.ON_CAPTURE_SESSION_ENDED;
            } else if (i2 != 4) {
                if (i2 == 5) {
                    return;
                }
            }
            this.mSessionProcessor.deInitSession();
        }
        this.mProcessorState = ProcessorState.CLOSED;
        this.mCaptureSession.close();
    }

    @Override // androidx.camera.camera2.internal.CaptureSessionInterface
    @NonNull
    public List<CaptureConfig> getCaptureConfigs() {
        return this.mPendingCaptureConfig != null ? Arrays.asList(this.mPendingCaptureConfig) : Collections.emptyList();
    }

    @Override // androidx.camera.camera2.internal.CaptureSessionInterface
    @Nullable
    public SessionConfig getSessionConfig() {
        return this.mSessionConfig;
    }

    @Override // androidx.camera.camera2.internal.CaptureSessionInterface
    public void issueCaptureRequests(@NonNull List<CaptureConfig> list) {
        if (list.isEmpty()) {
            return;
        }
        if (list.size() > 1 || !isStillCapture(list)) {
            cancelRequests(list);
            return;
        }
        if (this.mPendingCaptureConfig != null || this.mIsExecutingStillCaptureRequest) {
            cancelRequests(list);
            return;
        }
        CaptureConfig captureConfig = list.get(0);
        Logger.d(TAG, "issueCaptureRequests (id=" + this.mInstanceId + ") + state =" + this.mProcessorState);
        int i2 = AnonymousClass3.$SwitchMap$androidx$camera$camera2$internal$ProcessingCaptureSession$ProcessorState[this.mProcessorState.ordinal()];
        if (i2 == 1 || i2 == 2) {
            this.mPendingCaptureConfig = captureConfig;
            return;
        }
        if (i2 != 3) {
            if (i2 == 4 || i2 == 5) {
                Logger.d(TAG, "Run issueCaptureRequests in wrong state, state = " + this.mProcessorState);
                cancelRequests(list);
                return;
            }
            return;
        }
        this.mIsExecutingStillCaptureRequest = true;
        CaptureRequestOptions.Builder builderFrom = CaptureRequestOptions.Builder.from(captureConfig.getImplementationOptions());
        Config implementationOptions = captureConfig.getImplementationOptions();
        Config.Option<Integer> option = CaptureConfig.OPTION_ROTATION;
        if (implementationOptions.containsOption(option)) {
            builderFrom.setCaptureRequestOption(CaptureRequest.JPEG_ORIENTATION, (Integer) captureConfig.getImplementationOptions().retrieveOption(option));
        }
        Config implementationOptions2 = captureConfig.getImplementationOptions();
        Config.Option<Integer> option2 = CaptureConfig.OPTION_JPEG_QUALITY;
        if (implementationOptions2.containsOption(option2)) {
            builderFrom.setCaptureRequestOption(CaptureRequest.JPEG_QUALITY, Byte.valueOf(((Integer) captureConfig.getImplementationOptions().retrieveOption(option2)).byteValue()));
        }
        CaptureRequestOptions captureRequestOptionsBuild = builderFrom.build();
        this.mStillCaptureOptions = captureRequestOptionsBuild;
        updateParameters(this.mSessionOptions, captureRequestOptionsBuild);
        this.mSessionProcessor.startCapture(new AnonymousClass2(captureConfig));
    }

    public void onConfigured(@NonNull CaptureSession captureSession) {
        Preconditions.checkArgument(this.mProcessorState == ProcessorState.SESSION_INITIALIZED, "Invalid state state:" + this.mProcessorState);
        Camera2RequestProcessor camera2RequestProcessor = new Camera2RequestProcessor(captureSession, getSessionProcessorSurfaceList(this.mProcessorSessionConfig.getSurfaces()));
        this.mRequestProcessor = camera2RequestProcessor;
        this.mSessionProcessor.onCaptureSessionStart(camera2RequestProcessor);
        this.mProcessorState = ProcessorState.ON_CAPTURE_SESSION_STARTED;
        SessionConfig sessionConfig = this.mSessionConfig;
        if (sessionConfig != null) {
            setSessionConfig(sessionConfig);
        }
        if (this.mPendingCaptureConfig != null) {
            List<CaptureConfig> listAsList = Arrays.asList(this.mPendingCaptureConfig);
            this.mPendingCaptureConfig = null;
            issueCaptureRequests(listAsList);
        }
    }

    @Override // androidx.camera.camera2.internal.CaptureSessionInterface
    @NonNull
    public ListenableFuture<Void> open(@NonNull final SessionConfig sessionConfig, @NonNull final CameraDevice cameraDevice, @NonNull final SynchronizedCaptureSessionOpener synchronizedCaptureSessionOpener) {
        Preconditions.checkArgument(this.mProcessorState == ProcessorState.UNINITIALIZED, "Invalid state state:" + this.mProcessorState);
        Preconditions.checkArgument(sessionConfig.getSurfaces().isEmpty() ^ true, "SessionConfig contains no surfaces");
        Logger.d(TAG, "open (id=" + this.mInstanceId + ")");
        List<DeferrableSurface> surfaces = sessionConfig.getSurfaces();
        this.mOutputSurfaces = surfaces;
        return FutureChain.from(DeferrableSurfaces.surfaceListWithTimeout(surfaces, false, 5000L, this.mExecutor, this.mScheduledExecutorService)).transformAsync(new AsyncFunction() { // from class: androidx.camera.camera2.internal.t1
            @Override // androidx.camera.core.impl.utils.futures.AsyncFunction
            public final ListenableFuture apply(Object obj) {
                return this.f1416a.lambda$open$2(sessionConfig, cameraDevice, synchronizedCaptureSessionOpener, (List) obj);
            }
        }, this.mExecutor).transform(new Function() { // from class: androidx.camera.camera2.internal.u1
            @Override // androidx.arch.core.util.Function
            public final Object apply(Object obj) {
                return this.f1423a.lambda$open$3((Void) obj);
            }
        }, this.mExecutor);
    }

    @Override // androidx.camera.camera2.internal.CaptureSessionInterface
    @NonNull
    public ListenableFuture<Void> release(boolean z2) {
        Preconditions.checkState(this.mProcessorState == ProcessorState.CLOSED, "release() can only be called in CLOSED state");
        Logger.d(TAG, "release (id=" + this.mInstanceId + ")");
        return this.mCaptureSession.release(z2);
    }

    @Override // androidx.camera.camera2.internal.CaptureSessionInterface
    public void setSessionConfig(@Nullable SessionConfig sessionConfig) {
        Logger.d(TAG, "setSessionConfig (id=" + this.mInstanceId + ")");
        this.mSessionConfig = sessionConfig;
        if (sessionConfig == null) {
            return;
        }
        Camera2RequestProcessor camera2RequestProcessor = this.mRequestProcessor;
        if (camera2RequestProcessor != null) {
            camera2RequestProcessor.updateSessionConfig(sessionConfig);
        }
        if (this.mProcessorState == ProcessorState.ON_CAPTURE_SESSION_STARTED) {
            CaptureRequestOptions captureRequestOptionsBuild = CaptureRequestOptions.Builder.from(sessionConfig.getImplementationOptions()).build();
            this.mSessionOptions = captureRequestOptionsBuild;
            updateParameters(captureRequestOptionsBuild, this.mStillCaptureOptions);
            this.mSessionProcessor.startRepeating(this.mSessionProcessorCaptureCallback);
        }
    }
}
