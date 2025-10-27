package androidx.camera.camera2.internal;

import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.OptIn;
import androidx.annotation.RequiresApi;
import androidx.annotation.VisibleForTesting;
import androidx.arch.core.util.Function;
import androidx.camera.camera2.impl.Camera2ImplConfig;
import androidx.camera.camera2.internal.Camera2CameraControlImpl;
import androidx.camera.camera2.internal.Camera2CapturePipeline;
import androidx.camera.camera2.internal.compat.CameraCharacteristicsCompat;
import androidx.camera.camera2.internal.compat.workaround.OverrideAeModeForStillCapture;
import androidx.camera.camera2.internal.compat.workaround.UseTorchAsFlash;
import androidx.camera.camera2.interop.ExperimentalCamera2Interop;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Logger;
import androidx.camera.core.impl.CameraCaptureCallback;
import androidx.camera.core.impl.CameraCaptureFailure;
import androidx.camera.core.impl.CameraCaptureMetaData;
import androidx.camera.core.impl.CameraCaptureResult;
import androidx.camera.core.impl.CaptureConfig;
import androidx.camera.core.impl.Quirks;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.impl.utils.futures.AsyncFunction;
import androidx.camera.core.impl.utils.futures.FutureChain;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

@RequiresApi(21)
/* loaded from: classes.dex */
class Camera2CapturePipeline {
    private static final Set<CameraCaptureMetaData.AeState> AE_CONVERGED_STATE_SET;
    private static final Set<CameraCaptureMetaData.AeState> AE_TORCH_AS_FLASH_CONVERGED_STATE_SET;
    private static final Set<CameraCaptureMetaData.AfState> AF_CONVERGED_STATE_SET = Collections.unmodifiableSet(EnumSet.of(CameraCaptureMetaData.AfState.PASSIVE_FOCUSED, CameraCaptureMetaData.AfState.PASSIVE_NOT_FOCUSED, CameraCaptureMetaData.AfState.LOCKED_FOCUSED, CameraCaptureMetaData.AfState.LOCKED_NOT_FOCUSED));
    private static final Set<CameraCaptureMetaData.AwbState> AWB_CONVERGED_STATE_SET = Collections.unmodifiableSet(EnumSet.of(CameraCaptureMetaData.AwbState.CONVERGED, CameraCaptureMetaData.AwbState.UNKNOWN));
    private static final String TAG = "Camera2CapturePipeline";

    @NonNull
    private final Camera2CameraControlImpl mCameraControl;

    @NonNull
    private final Quirks mCameraQuirk;

    @NonNull
    private final Executor mExecutor;
    private final boolean mIsLegacyDevice;
    private int mTemplate = 1;

    @NonNull
    private final UseTorchAsFlash mUseTorchAsFlash;

    public static class AePreCaptureTask implements PipelineTask {
        private final Camera2CameraControlImpl mCameraControl;
        private final int mFlashMode;
        private boolean mIsExecuted = false;
        private final OverrideAeModeForStillCapture mOverrideAeModeForStillCapture;

        public AePreCaptureTask(@NonNull Camera2CameraControlImpl camera2CameraControlImpl, int i2, @NonNull OverrideAeModeForStillCapture overrideAeModeForStillCapture) {
            this.mCameraControl = camera2CameraControlImpl;
            this.mFlashMode = i2;
            this.mOverrideAeModeForStillCapture = overrideAeModeForStillCapture;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ Object lambda$preCapture$0(CallbackToFutureAdapter.Completer completer) throws Exception {
            this.mCameraControl.getFocusMeteringControl().triggerAePrecapture(completer);
            this.mOverrideAeModeForStillCapture.onAePrecaptureStarted();
            return "AePreCapture";
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ Boolean lambda$preCapture$1(Void r02) {
            return Boolean.TRUE;
        }

        @Override // androidx.camera.camera2.internal.Camera2CapturePipeline.PipelineTask
        public boolean isCaptureResultNeeded() {
            return this.mFlashMode == 0;
        }

        @Override // androidx.camera.camera2.internal.Camera2CapturePipeline.PipelineTask
        public void postCapture() {
            if (this.mIsExecuted) {
                Logger.d(Camera2CapturePipeline.TAG, "cancel TriggerAePreCapture");
                this.mCameraControl.getFocusMeteringControl().cancelAfAeTrigger(false, true);
                this.mOverrideAeModeForStillCapture.onAePrecaptureFinished();
            }
        }

        @Override // androidx.camera.camera2.internal.Camera2CapturePipeline.PipelineTask
        @NonNull
        public ListenableFuture<Boolean> preCapture(@Nullable TotalCaptureResult totalCaptureResult) {
            if (!Camera2CapturePipeline.isFlashRequired(this.mFlashMode, totalCaptureResult)) {
                return Futures.immediateFuture(Boolean.FALSE);
            }
            Logger.d(Camera2CapturePipeline.TAG, "Trigger AE");
            this.mIsExecuted = true;
            return FutureChain.from(CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.camera2.internal.l0
                @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
                public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                    return this.f1372a.lambda$preCapture$0(completer);
                }
            })).transform(new Function() { // from class: androidx.camera.camera2.internal.m0
                @Override // androidx.arch.core.util.Function
                public final Object apply(Object obj) {
                    return Camera2CapturePipeline.AePreCaptureTask.lambda$preCapture$1((Void) obj);
                }
            }, CameraXExecutors.directExecutor());
        }
    }

    public static class AfTask implements PipelineTask {
        private final Camera2CameraControlImpl mCameraControl;
        private boolean mIsExecuted = false;

        public AfTask(@NonNull Camera2CameraControlImpl camera2CameraControlImpl) {
            this.mCameraControl = camera2CameraControlImpl;
        }

        @Override // androidx.camera.camera2.internal.Camera2CapturePipeline.PipelineTask
        public boolean isCaptureResultNeeded() {
            return true;
        }

        @Override // androidx.camera.camera2.internal.Camera2CapturePipeline.PipelineTask
        public void postCapture() {
            if (this.mIsExecuted) {
                Logger.d(Camera2CapturePipeline.TAG, "cancel TriggerAF");
                this.mCameraControl.getFocusMeteringControl().cancelAfAeTrigger(true, false);
            }
        }

        @Override // androidx.camera.camera2.internal.Camera2CapturePipeline.PipelineTask
        @NonNull
        public ListenableFuture<Boolean> preCapture(@Nullable TotalCaptureResult totalCaptureResult) {
            Integer num;
            ListenableFuture<Boolean> listenableFutureImmediateFuture = Futures.immediateFuture(Boolean.TRUE);
            if (totalCaptureResult == null || (num = (Integer) totalCaptureResult.get(CaptureResult.CONTROL_AF_MODE)) == null) {
                return listenableFutureImmediateFuture;
            }
            int iIntValue = num.intValue();
            if (iIntValue == 1 || iIntValue == 2) {
                Logger.d(Camera2CapturePipeline.TAG, "TriggerAf? AF mode auto");
                Integer num2 = (Integer) totalCaptureResult.get(CaptureResult.CONTROL_AF_STATE);
                if (num2 != null && num2.intValue() == 0) {
                    Logger.d(Camera2CapturePipeline.TAG, "Trigger AF");
                    this.mIsExecuted = true;
                    this.mCameraControl.getFocusMeteringControl().triggerAf(null, false);
                }
            }
            return listenableFutureImmediateFuture;
        }
    }

    @VisibleForTesting
    public static class Pipeline {
        private static final long CHECK_3A_TIMEOUT_IN_NS;
        private static final long CHECK_3A_WITH_FLASH_TIMEOUT_IN_NS;
        private final Camera2CameraControlImpl mCameraControl;
        private final Executor mExecutor;
        private final boolean mIsLegacyDevice;
        private final OverrideAeModeForStillCapture mOverrideAeModeForStillCapture;
        private final int mTemplate;
        private long mTimeout3A = CHECK_3A_TIMEOUT_IN_NS;
        final List<PipelineTask> mTasks = new ArrayList();
        private final PipelineTask mPipelineSubTask = new AnonymousClass1();

        /* renamed from: androidx.camera.camera2.internal.Camera2CapturePipeline$Pipeline$1, reason: invalid class name */
        public class AnonymousClass1 implements PipelineTask {
            public AnonymousClass1() {
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static /* synthetic */ Boolean lambda$preCapture$0(List list) {
                return Boolean.valueOf(list.contains(Boolean.TRUE));
            }

            @Override // androidx.camera.camera2.internal.Camera2CapturePipeline.PipelineTask
            public boolean isCaptureResultNeeded() {
                Iterator<PipelineTask> it = Pipeline.this.mTasks.iterator();
                while (it.hasNext()) {
                    if (it.next().isCaptureResultNeeded()) {
                        return true;
                    }
                }
                return false;
            }

            @Override // androidx.camera.camera2.internal.Camera2CapturePipeline.PipelineTask
            public void postCapture() {
                Iterator<PipelineTask> it = Pipeline.this.mTasks.iterator();
                while (it.hasNext()) {
                    it.next().postCapture();
                }
            }

            @Override // androidx.camera.camera2.internal.Camera2CapturePipeline.PipelineTask
            @NonNull
            public ListenableFuture<Boolean> preCapture(@Nullable TotalCaptureResult totalCaptureResult) {
                ArrayList arrayList = new ArrayList();
                Iterator<PipelineTask> it = Pipeline.this.mTasks.iterator();
                while (it.hasNext()) {
                    arrayList.add(it.next().preCapture(totalCaptureResult));
                }
                return Futures.transform(Futures.allAsList(arrayList), new Function() { // from class: androidx.camera.camera2.internal.t0
                    @Override // androidx.arch.core.util.Function
                    public final Object apply(Object obj) {
                        return Camera2CapturePipeline.Pipeline.AnonymousClass1.lambda$preCapture$0((List) obj);
                    }
                }, CameraXExecutors.directExecutor());
            }
        }

        static {
            TimeUnit timeUnit = TimeUnit.SECONDS;
            CHECK_3A_TIMEOUT_IN_NS = timeUnit.toNanos(1L);
            CHECK_3A_WITH_FLASH_TIMEOUT_IN_NS = timeUnit.toNanos(5L);
        }

        public Pipeline(int i2, @NonNull Executor executor, @NonNull Camera2CameraControlImpl camera2CameraControlImpl, boolean z2, @NonNull OverrideAeModeForStillCapture overrideAeModeForStillCapture) {
            this.mTemplate = i2;
            this.mExecutor = executor;
            this.mCameraControl = camera2CameraControlImpl;
            this.mIsLegacyDevice = z2;
            this.mOverrideAeModeForStillCapture = overrideAeModeForStillCapture;
        }

        @OptIn(markerClass = {ExperimentalCamera2Interop.class})
        private void applyAeModeQuirk(@NonNull CaptureConfig.Builder builder) {
            Camera2ImplConfig.Builder builder2 = new Camera2ImplConfig.Builder();
            builder2.setCaptureRequestOption(CaptureRequest.CONTROL_AE_MODE, 3);
            builder.addImplementationOptions(builder2.build());
        }

        private void applyStillCaptureTemplate(@NonNull CaptureConfig.Builder builder, @NonNull CaptureConfig captureConfig) {
            int i2 = (this.mTemplate != 3 || this.mIsLegacyDevice) ? (captureConfig.getTemplateType() == -1 || captureConfig.getTemplateType() == 5) ? 2 : -1 : 4;
            if (i2 != -1) {
                builder.setTemplateType(i2);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ ListenableFuture lambda$executeCapture$0(int i2, TotalCaptureResult totalCaptureResult) throws Exception {
            if (Camera2CapturePipeline.isFlashRequired(i2, totalCaptureResult)) {
                setTimeout3A(CHECK_3A_WITH_FLASH_TIMEOUT_IN_NS);
            }
            return this.mPipelineSubTask.preCapture(totalCaptureResult);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$executeCapture$1(TotalCaptureResult totalCaptureResult) {
            return Camera2CapturePipeline.is3AConverged(totalCaptureResult, false);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ ListenableFuture lambda$executeCapture$2(Boolean bool) throws Exception {
            return Boolean.TRUE.equals(bool) ? Camera2CapturePipeline.waitForResult(this.mTimeout3A, this.mCameraControl, new ResultListener.Checker() { // from class: androidx.camera.camera2.internal.o0
                @Override // androidx.camera.camera2.internal.Camera2CapturePipeline.ResultListener.Checker
                public final boolean check(TotalCaptureResult totalCaptureResult) {
                    return Camera2CapturePipeline.Pipeline.lambda$executeCapture$1(totalCaptureResult);
                }
            }) : Futures.immediateFuture(null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ ListenableFuture lambda$executeCapture$3(List list, int i2, TotalCaptureResult totalCaptureResult) throws Exception {
            return submitConfigsInternal(list, i2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ Object lambda$submitConfigsInternal$4(CaptureConfig.Builder builder, final CallbackToFutureAdapter.Completer completer) throws Exception {
            builder.addCameraCaptureCallback(new CameraCaptureCallback() { // from class: androidx.camera.camera2.internal.Camera2CapturePipeline.Pipeline.2
                @Override // androidx.camera.core.impl.CameraCaptureCallback
                public void onCaptureCancelled() {
                    completer.setException(new ImageCaptureException(3, "Capture request is cancelled because camera is closed", null));
                }

                @Override // androidx.camera.core.impl.CameraCaptureCallback
                public void onCaptureCompleted(@NonNull CameraCaptureResult cameraCaptureResult) {
                    completer.set(null);
                }

                @Override // androidx.camera.core.impl.CameraCaptureCallback
                public void onCaptureFailed(@NonNull CameraCaptureFailure cameraCaptureFailure) {
                    completer.setException(new ImageCaptureException(2, "Capture request failed with reason " + cameraCaptureFailure.getReason(), null));
                }
            });
            return "submitStillCapture";
        }

        private void setTimeout3A(long j2) {
            this.mTimeout3A = j2;
        }

        public void addTask(@NonNull PipelineTask pipelineTask) {
            this.mTasks.add(pipelineTask);
        }

        @NonNull
        public ListenableFuture<List<Void>> executeCapture(@NonNull final List<CaptureConfig> list, final int i2) {
            ListenableFuture listenableFutureImmediateFuture = Futures.immediateFuture(null);
            if (!this.mTasks.isEmpty()) {
                listenableFutureImmediateFuture = FutureChain.from(this.mPipelineSubTask.isCaptureResultNeeded() ? Camera2CapturePipeline.waitForResult(0L, this.mCameraControl, null) : Futures.immediateFuture(null)).transformAsync(new AsyncFunction() { // from class: androidx.camera.camera2.internal.p0
                    @Override // androidx.camera.core.impl.utils.futures.AsyncFunction
                    public final ListenableFuture apply(Object obj) {
                        return this.f1397a.lambda$executeCapture$0(i2, (TotalCaptureResult) obj);
                    }
                }, this.mExecutor).transformAsync(new AsyncFunction() { // from class: androidx.camera.camera2.internal.q0
                    @Override // androidx.camera.core.impl.utils.futures.AsyncFunction
                    public final ListenableFuture apply(Object obj) {
                        return this.f1404a.lambda$executeCapture$2((Boolean) obj);
                    }
                }, this.mExecutor);
            }
            FutureChain futureChainTransformAsync = FutureChain.from(listenableFutureImmediateFuture).transformAsync(new AsyncFunction() { // from class: androidx.camera.camera2.internal.r0
                @Override // androidx.camera.core.impl.utils.futures.AsyncFunction
                public final ListenableFuture apply(Object obj) {
                    return this.f1407a.lambda$executeCapture$3(list, i2, (TotalCaptureResult) obj);
                }
            }, this.mExecutor);
            final PipelineTask pipelineTask = this.mPipelineSubTask;
            Objects.requireNonNull(pipelineTask);
            futureChainTransformAsync.addListener(new Runnable() { // from class: androidx.camera.camera2.internal.s0
                @Override // java.lang.Runnable
                public final void run() {
                    pipelineTask.postCapture();
                }
            }, this.mExecutor);
            return futureChainTransformAsync;
        }

        /* JADX WARN: Removed duplicated region for block: B:19:0x0063  */
        @androidx.annotation.NonNull
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public com.google.common.util.concurrent.ListenableFuture<java.util.List<java.lang.Void>> submitConfigsInternal(@androidx.annotation.NonNull java.util.List<androidx.camera.core.impl.CaptureConfig> r7, int r8) {
            /*
                r6 = this;
                java.util.ArrayList r0 = new java.util.ArrayList
                r0.<init>()
                java.util.ArrayList r1 = new java.util.ArrayList
                r1.<init>()
                java.util.Iterator r7 = r7.iterator()
            Le:
                boolean r2 = r7.hasNext()
                if (r2 == 0) goto L8c
                java.lang.Object r2 = r7.next()
                androidx.camera.core.impl.CaptureConfig r2 = (androidx.camera.core.impl.CaptureConfig) r2
                androidx.camera.core.impl.CaptureConfig$Builder r3 = androidx.camera.core.impl.CaptureConfig.Builder.from(r2)
                int r4 = r2.getTemplateType()
                r5 = 5
                if (r4 != r5) goto L63
                androidx.camera.camera2.internal.Camera2CameraControlImpl r4 = r6.mCameraControl
                androidx.camera.camera2.internal.ZslControl r4 = r4.getZslControl()
                boolean r4 = r4.isZslDisabledByFlashMode()
                if (r4 != 0) goto L63
                androidx.camera.camera2.internal.Camera2CameraControlImpl r4 = r6.mCameraControl
                androidx.camera.camera2.internal.ZslControl r4 = r4.getZslControl()
                boolean r4 = r4.isZslDisabledByUserCaseConfig()
                if (r4 != 0) goto L63
                androidx.camera.camera2.internal.Camera2CameraControlImpl r4 = r6.mCameraControl
                androidx.camera.camera2.internal.ZslControl r4 = r4.getZslControl()
                androidx.camera.core.ImageProxy r4 = r4.dequeueImageFromBuffer()
                if (r4 == 0) goto L57
                androidx.camera.camera2.internal.Camera2CameraControlImpl r5 = r6.mCameraControl
                androidx.camera.camera2.internal.ZslControl r5 = r5.getZslControl()
                boolean r5 = r5.enqueueImageToImageWriter(r4)
                if (r5 == 0) goto L57
                r5 = 1
                goto L58
            L57:
                r5 = 0
            L58:
                if (r5 == 0) goto L63
                androidx.camera.core.ImageInfo r4 = r4.getImageInfo()
                androidx.camera.core.impl.CameraCaptureResult r4 = androidx.camera.core.impl.CameraCaptureResults.retrieveCameraCaptureResult(r4)
                goto L64
            L63:
                r4 = 0
            L64:
                if (r4 == 0) goto L6a
                r3.setCameraCaptureResult(r4)
                goto L6d
            L6a:
                r6.applyStillCaptureTemplate(r3, r2)
            L6d:
                androidx.camera.camera2.internal.compat.workaround.OverrideAeModeForStillCapture r2 = r6.mOverrideAeModeForStillCapture
                boolean r2 = r2.shouldSetAeModeAlwaysFlash(r8)
                if (r2 == 0) goto L78
                r6.applyAeModeQuirk(r3)
            L78:
                androidx.camera.camera2.internal.n0 r2 = new androidx.camera.camera2.internal.n0
                r2.<init>()
                com.google.common.util.concurrent.ListenableFuture r2 = androidx.concurrent.futures.CallbackToFutureAdapter.getFuture(r2)
                r0.add(r2)
                androidx.camera.core.impl.CaptureConfig r2 = r3.build()
                r1.add(r2)
                goto Le
            L8c:
                androidx.camera.camera2.internal.Camera2CameraControlImpl r7 = r6.mCameraControl
                r7.submitCaptureRequestsInternal(r1)
                com.google.common.util.concurrent.ListenableFuture r7 = androidx.camera.core.impl.utils.futures.Futures.allAsList(r0)
                return r7
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.internal.Camera2CapturePipeline.Pipeline.submitConfigsInternal(java.util.List, int):com.google.common.util.concurrent.ListenableFuture");
        }
    }

    public interface PipelineTask {
        boolean isCaptureResultNeeded();

        void postCapture();

        @NonNull
        ListenableFuture<Boolean> preCapture(@Nullable TotalCaptureResult totalCaptureResult);
    }

    public static class ResultListener implements Camera2CameraControlImpl.CaptureResultListener {
        static final long NO_TIMEOUT = 0;
        private final Checker mChecker;
        private CallbackToFutureAdapter.Completer<TotalCaptureResult> mCompleter;
        private final long mTimeLimitNs;
        private final ListenableFuture<TotalCaptureResult> mFuture = CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.camera2.internal.u0
            @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
            public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                return this.f1422a.lambda$new$0(completer);
            }
        });
        private volatile Long mTimestampOfFirstUpdateNs = null;

        public interface Checker {
            boolean check(@NonNull TotalCaptureResult totalCaptureResult);
        }

        public ResultListener(long j2, @Nullable Checker checker) {
            this.mTimeLimitNs = j2;
            this.mChecker = checker;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ Object lambda$new$0(CallbackToFutureAdapter.Completer completer) throws Exception {
            this.mCompleter = completer;
            return "waitFor3AResult";
        }

        @NonNull
        public ListenableFuture<TotalCaptureResult> getFuture() {
            return this.mFuture;
        }

        @Override // androidx.camera.camera2.internal.Camera2CameraControlImpl.CaptureResultListener
        public boolean onCaptureResult(@NonNull TotalCaptureResult totalCaptureResult) {
            Long l2 = (Long) totalCaptureResult.get(CaptureResult.SENSOR_TIMESTAMP);
            if (l2 != null && this.mTimestampOfFirstUpdateNs == null) {
                this.mTimestampOfFirstUpdateNs = l2;
            }
            Long l3 = this.mTimestampOfFirstUpdateNs;
            if (0 == this.mTimeLimitNs || l3 == null || l2 == null || l2.longValue() - l3.longValue() <= this.mTimeLimitNs) {
                Checker checker = this.mChecker;
                if (checker != null && !checker.check(totalCaptureResult)) {
                    return false;
                }
                this.mCompleter.set(totalCaptureResult);
                return true;
            }
            this.mCompleter.set(null);
            Logger.d(Camera2CapturePipeline.TAG, "Wait for capture result timeout, current:" + l2 + " first: " + l3);
            return true;
        }
    }

    public static class TorchTask implements PipelineTask {
        private static final long CHECK_3A_WITH_TORCH_TIMEOUT_IN_NS = TimeUnit.SECONDS.toNanos(2);
        private final Camera2CameraControlImpl mCameraControl;
        private final Executor mExecutor;
        private final int mFlashMode;
        private boolean mIsExecuted = false;

        public TorchTask(@NonNull Camera2CameraControlImpl camera2CameraControlImpl, int i2, @NonNull Executor executor) {
            this.mCameraControl = camera2CameraControlImpl;
            this.mFlashMode = i2;
            this.mExecutor = executor;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ Object lambda$preCapture$0(CallbackToFutureAdapter.Completer completer) throws Exception {
            this.mCameraControl.getTorchControl().lambda$enableTorch$1(completer, true);
            return "TorchOn";
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$preCapture$1(TotalCaptureResult totalCaptureResult) {
            return Camera2CapturePipeline.is3AConverged(totalCaptureResult, true);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ ListenableFuture lambda$preCapture$2(Void r4) throws Exception {
            return Camera2CapturePipeline.waitForResult(CHECK_3A_WITH_TORCH_TIMEOUT_IN_NS, this.mCameraControl, new ResultListener.Checker() { // from class: androidx.camera.camera2.internal.v0
                @Override // androidx.camera.camera2.internal.Camera2CapturePipeline.ResultListener.Checker
                public final boolean check(TotalCaptureResult totalCaptureResult) {
                    return Camera2CapturePipeline.TorchTask.lambda$preCapture$1(totalCaptureResult);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ Boolean lambda$preCapture$3(TotalCaptureResult totalCaptureResult) {
            return Boolean.FALSE;
        }

        @Override // androidx.camera.camera2.internal.Camera2CapturePipeline.PipelineTask
        public boolean isCaptureResultNeeded() {
            return this.mFlashMode == 0;
        }

        @Override // androidx.camera.camera2.internal.Camera2CapturePipeline.PipelineTask
        public void postCapture() {
            if (this.mIsExecuted) {
                this.mCameraControl.getTorchControl().lambda$enableTorch$1(null, false);
                Logger.d(Camera2CapturePipeline.TAG, "Turn off torch");
            }
        }

        @Override // androidx.camera.camera2.internal.Camera2CapturePipeline.PipelineTask
        @NonNull
        public ListenableFuture<Boolean> preCapture(@Nullable TotalCaptureResult totalCaptureResult) {
            if (Camera2CapturePipeline.isFlashRequired(this.mFlashMode, totalCaptureResult)) {
                if (!this.mCameraControl.isTorchOn()) {
                    Logger.d(Camera2CapturePipeline.TAG, "Turn on torch");
                    this.mIsExecuted = true;
                    return FutureChain.from(CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.camera2.internal.w0
                        @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
                        public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                            return this.f1429a.lambda$preCapture$0(completer);
                        }
                    })).transformAsync(new AsyncFunction() { // from class: androidx.camera.camera2.internal.x0
                        @Override // androidx.camera.core.impl.utils.futures.AsyncFunction
                        public final ListenableFuture apply(Object obj) {
                            return this.f1434a.lambda$preCapture$2((Void) obj);
                        }
                    }, this.mExecutor).transform(new Function() { // from class: androidx.camera.camera2.internal.y0
                        @Override // androidx.arch.core.util.Function
                        public final Object apply(Object obj) {
                            return Camera2CapturePipeline.TorchTask.lambda$preCapture$3((TotalCaptureResult) obj);
                        }
                    }, CameraXExecutors.directExecutor());
                }
                Logger.d(Camera2CapturePipeline.TAG, "Torch already on, not turn on");
            }
            return Futures.immediateFuture(Boolean.FALSE);
        }
    }

    static {
        CameraCaptureMetaData.AeState aeState = CameraCaptureMetaData.AeState.CONVERGED;
        CameraCaptureMetaData.AeState aeState2 = CameraCaptureMetaData.AeState.FLASH_REQUIRED;
        CameraCaptureMetaData.AeState aeState3 = CameraCaptureMetaData.AeState.UNKNOWN;
        Set<CameraCaptureMetaData.AeState> setUnmodifiableSet = Collections.unmodifiableSet(EnumSet.of(aeState, aeState2, aeState3));
        AE_CONVERGED_STATE_SET = setUnmodifiableSet;
        EnumSet enumSetCopyOf = EnumSet.copyOf((Collection) setUnmodifiableSet);
        enumSetCopyOf.remove(aeState2);
        enumSetCopyOf.remove(aeState3);
        AE_TORCH_AS_FLASH_CONVERGED_STATE_SET = Collections.unmodifiableSet(enumSetCopyOf);
    }

    public Camera2CapturePipeline(@NonNull Camera2CameraControlImpl camera2CameraControlImpl, @NonNull CameraCharacteristicsCompat cameraCharacteristicsCompat, @NonNull Quirks quirks, @NonNull Executor executor) {
        this.mCameraControl = camera2CameraControlImpl;
        Integer num = (Integer) cameraCharacteristicsCompat.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL);
        this.mIsLegacyDevice = num != null && num.intValue() == 2;
        this.mExecutor = executor;
        this.mCameraQuirk = quirks;
        this.mUseTorchAsFlash = new UseTorchAsFlash(quirks);
    }

    public static boolean is3AConverged(@Nullable TotalCaptureResult totalCaptureResult, boolean z2) {
        if (totalCaptureResult == null) {
            return false;
        }
        Camera2CameraCaptureResult camera2CameraCaptureResult = new Camera2CameraCaptureResult(totalCaptureResult);
        boolean z3 = camera2CameraCaptureResult.getAfMode() == CameraCaptureMetaData.AfMode.OFF || camera2CameraCaptureResult.getAfMode() == CameraCaptureMetaData.AfMode.UNKNOWN || AF_CONVERGED_STATE_SET.contains(camera2CameraCaptureResult.getAfState());
        boolean z4 = ((Integer) totalCaptureResult.get(CaptureResult.CONTROL_AE_MODE)).intValue() == 0;
        boolean z5 = !z2 ? !(z4 || AE_CONVERGED_STATE_SET.contains(camera2CameraCaptureResult.getAeState())) : !(z4 || AE_TORCH_AS_FLASH_CONVERGED_STATE_SET.contains(camera2CameraCaptureResult.getAeState()));
        boolean z6 = (((Integer) totalCaptureResult.get(CaptureResult.CONTROL_AWB_MODE)).intValue() == 0) || AWB_CONVERGED_STATE_SET.contains(camera2CameraCaptureResult.getAwbState());
        Logger.d(TAG, "checkCaptureResult, AE=" + camera2CameraCaptureResult.getAeState() + " AF =" + camera2CameraCaptureResult.getAfState() + " AWB=" + camera2CameraCaptureResult.getAwbState());
        return z3 && z5 && z6;
    }

    public static boolean isFlashRequired(int i2, @Nullable TotalCaptureResult totalCaptureResult) {
        if (i2 == 0) {
            Integer num = totalCaptureResult != null ? (Integer) totalCaptureResult.get(CaptureResult.CONTROL_AE_STATE) : null;
            return num != null && num.intValue() == 4;
        }
        if (i2 == 1) {
            return true;
        }
        if (i2 == 2) {
            return false;
        }
        throw new AssertionError(i2);
    }

    private boolean isTorchAsFlash(int i2) {
        return this.mUseTorchAsFlash.shouldUseTorchAsFlash() || this.mTemplate == 3 || i2 == 1;
    }

    @NonNull
    public static ListenableFuture<TotalCaptureResult> waitForResult(long j2, @NonNull Camera2CameraControlImpl camera2CameraControlImpl, @Nullable ResultListener.Checker checker) {
        ResultListener resultListener = new ResultListener(j2, checker);
        camera2CameraControlImpl.addCaptureResultListener(resultListener);
        return resultListener.getFuture();
    }

    public void setTemplate(int i2) {
        this.mTemplate = i2;
    }

    @NonNull
    public ListenableFuture<List<Void>> submitStillCaptures(@NonNull List<CaptureConfig> list, int i2, int i3, int i4) {
        OverrideAeModeForStillCapture overrideAeModeForStillCapture = new OverrideAeModeForStillCapture(this.mCameraQuirk);
        Pipeline pipeline = new Pipeline(this.mTemplate, this.mExecutor, this.mCameraControl, this.mIsLegacyDevice, overrideAeModeForStillCapture);
        if (i2 == 0) {
            pipeline.addTask(new AfTask(this.mCameraControl));
        }
        if (isTorchAsFlash(i4)) {
            pipeline.addTask(new TorchTask(this.mCameraControl, i3, this.mExecutor));
        } else {
            pipeline.addTask(new AePreCaptureTask(this.mCameraControl, i3, overrideAeModeForStillCapture));
        }
        return Futures.nonCancellationPropagating(pipeline.executeCapture(list, i3));
    }
}
