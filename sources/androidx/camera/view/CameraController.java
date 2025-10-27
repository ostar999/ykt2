package androidx.camera.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.Size;
import android.view.Display;
import androidx.annotation.DoNotInline;
import androidx.annotation.FloatRange;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.OptIn;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.arch.core.util.Function;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraControl;
import androidx.camera.core.CameraEffect;
import androidx.camera.core.CameraInfo;
import androidx.camera.core.CameraInfoUnavailableException;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.FocusMeteringAction;
import androidx.camera.core.FocusMeteringResult;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.Logger;
import androidx.camera.core.MeteringPointFactory;
import androidx.camera.core.Preview;
import androidx.camera.core.UseCaseGroup;
import androidx.camera.core.VideoCapture;
import androidx.camera.core.ViewPort;
import androidx.camera.core.ZoomState;
import androidx.camera.core.impl.ImageOutputConfig;
import androidx.camera.core.impl.utils.Threads;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.impl.utils.futures.FutureCallback;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.RotationProvider;
import androidx.camera.view.transform.OutputTransform;
import androidx.camera.view.video.ExperimentalVideo;
import androidx.camera.view.video.OnVideoSavedCallback;
import androidx.camera.view.video.OutputFileOptions;
import androidx.camera.view.video.OutputFileResults;
import androidx.core.util.Preconditions;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.common.util.concurrent.ListenableFuture;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

@RequiresApi(21)
/* loaded from: classes.dex */
public abstract class CameraController {
    private static final float AE_SIZE = 0.25f;
    private static final float AF_SIZE = 0.16666667f;
    private static final String CAMERA_NOT_ATTACHED = "Use cases not attached to camera.";
    private static final String CAMERA_NOT_INITIALIZED = "Camera not initialized.";
    public static final int COORDINATE_SYSTEM_VIEW_REFERENCED = 1;
    public static final int IMAGE_ANALYSIS = 2;
    public static final int IMAGE_CAPTURE = 1;
    private static final String IMAGE_CAPTURE_DISABLED = "ImageCapture disabled.";
    private static final String PREVIEW_VIEW_NOT_ATTACHED = "PreviewView not attached to CameraController.";
    private static final String TAG = "CameraController";
    public static final int TAP_TO_FOCUS_FAILED = 4;
    public static final int TAP_TO_FOCUS_FOCUSED = 2;
    public static final int TAP_TO_FOCUS_NOT_FOCUSED = 3;
    public static final int TAP_TO_FOCUS_NOT_STARTED = 0;
    public static final int TAP_TO_FOCUS_STARTED = 1;

    @ExperimentalVideo
    public static final int VIDEO_CAPTURE = 4;
    private static final String VIDEO_CAPTURE_DISABLED = "VideoCapture disabled.";

    @Nullable
    private ImageAnalysis.Analyzer mAnalysisAnalyzer;

    @Nullable
    private Executor mAnalysisBackgroundExecutor;

    @Nullable
    private Executor mAnalysisExecutor;
    private final Context mAppContext;

    @Nullable
    Camera mCamera;

    @Nullable
    ProcessCameraProvider mCameraProvider;

    @NonNull
    @VisibleForTesting
    final RotationProvider.Listener mDeviceRotationListener;

    @NonNull
    ImageAnalysis mImageAnalysis;

    @Nullable
    OutputSize mImageAnalysisTargetSize;

    @NonNull
    ImageCapture mImageCapture;

    @Nullable
    Executor mImageCaptureIoExecutor;

    @Nullable
    OutputSize mImageCaptureTargetSize;

    @NonNull
    private final ListenableFuture<Void> mInitializationFuture;

    @NonNull
    Preview mPreview;

    @Nullable
    Display mPreviewDisplay;

    @Nullable
    OutputSize mPreviewTargetSize;
    private final RotationProvider mRotationProvider;

    @Nullable
    Preview.SurfaceProvider mSurfaceProvider;

    @NonNull
    VideoCapture mVideoCapture;

    @Nullable
    OutputSize mVideoCaptureOutputSize;

    @Nullable
    ViewPort mViewPort;
    CameraSelector mCameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;
    private int mEnabledUseCases = 3;

    @NonNull
    final AtomicBoolean mVideoIsRecording = new AtomicBoolean(false);
    private boolean mPinchToZoomEnabled = true;
    private boolean mTapToFocusEnabled = true;
    private final ForwardingLiveData<ZoomState> mZoomState = new ForwardingLiveData<>();
    private final ForwardingLiveData<Integer> mTorchState = new ForwardingLiveData<>();
    final MutableLiveData<Integer> mTapToFocusState = new MutableLiveData<>(0);

    @NonNull
    private List<CameraEffect> mEffects = Collections.emptyList();

    @RequiresApi(30)
    public static class Api30Impl {
        private Api30Impl() {
        }

        @NonNull
        @DoNotInline
        public static Context createAttributionContext(@NonNull Context context, @Nullable String str) {
            return context.createAttributionContext(str);
        }

        @Nullable
        @DoNotInline
        public static String getAttributionTag(@NonNull Context context) {
            return context.getAttributionTag();
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public @interface TapToFocusStates {
    }

    @OptIn(markerClass = {ExperimentalVideo.class})
    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public @interface UseCases {
    }

    public CameraController(@NonNull Context context) {
        Context applicationContext = getApplicationContext(context);
        this.mAppContext = applicationContext;
        this.mPreview = new Preview.Builder().build();
        this.mImageCapture = new ImageCapture.Builder().build();
        this.mImageAnalysis = new ImageAnalysis.Builder().build();
        this.mVideoCapture = new VideoCapture.Builder().build();
        this.mInitializationFuture = Futures.transform(ProcessCameraProvider.getInstance(applicationContext), new Function() { // from class: androidx.camera.view.b
            @Override // androidx.arch.core.util.Function
            public final Object apply(Object obj) {
                return this.f1730a.lambda$new$0((ProcessCameraProvider) obj);
            }
        }, CameraXExecutors.mainThreadExecutor());
        this.mRotationProvider = new RotationProvider(applicationContext);
        this.mDeviceRotationListener = new RotationProvider.Listener() { // from class: androidx.camera.view.c
            @Override // androidx.camera.view.RotationProvider.Listener
            public final void onRotationChanged(int i2) {
                this.f1731a.lambda$new$1(i2);
            }
        };
    }

    private static Context getApplicationContext(@NonNull Context context) {
        String attributionTag;
        Context applicationContext = context.getApplicationContext();
        return (Build.VERSION.SDK_INT < 30 || (attributionTag = Api30Impl.getAttributionTag(context)) == null) ? applicationContext : Api30Impl.createAttributionContext(applicationContext, attributionTag);
    }

    private boolean isCameraAttached() {
        return this.mCamera != null;
    }

    private boolean isCameraInitialized() {
        return this.mCameraProvider != null;
    }

    private boolean isOutputSizeEqual(@Nullable OutputSize outputSize, @Nullable OutputSize outputSize2) {
        if (outputSize == outputSize2) {
            return true;
        }
        return outputSize != null && outputSize.equals(outputSize2);
    }

    private boolean isPreviewViewAttached() {
        return (this.mSurfaceProvider == null || this.mViewPort == null || this.mPreviewDisplay == null) ? false : true;
    }

    private boolean isUseCaseEnabled(int i2) {
        return (i2 & this.mEnabledUseCases) != 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Void lambda$new$0(ProcessCameraProvider processCameraProvider) {
        this.mCameraProvider = processCameraProvider;
        startCameraAndTrackStates();
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(int i2) {
        this.mImageAnalysis.setTargetRotation(i2);
        this.mImageCapture.setTargetRotation(i2);
        this.mVideoCapture.setTargetRotation(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setCameraSelector$3(CameraSelector cameraSelector) {
        this.mCameraSelector = cameraSelector;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setEnabledUseCases$2(int i2) {
        this.mEnabledUseCases = i2;
    }

    private void restartCameraIfAnalyzerResolutionChanged(@Nullable ImageAnalysis.Analyzer analyzer, @Nullable ImageAnalysis.Analyzer analyzer2) {
        if (Objects.equals(analyzer == null ? null : analyzer.getDefaultTargetResolution(), analyzer2 != null ? analyzer2.getDefaultTargetResolution() : null)) {
            return;
        }
        unbindImageAnalysisAndRecreate(this.mImageAnalysis.getBackpressureStrategy(), this.mImageAnalysis.getImageQueueDepth());
        startCameraAndTrackStates();
    }

    private void setTargetOutputSize(@NonNull ImageOutputConfig.Builder<?> builder, @Nullable OutputSize outputSize) {
        if (outputSize == null) {
            return;
        }
        if (outputSize.getResolution() != null) {
            builder.setTargetResolution(outputSize.getResolution());
            return;
        }
        if (outputSize.getAspectRatio() != -1) {
            builder.setTargetAspectRatio(outputSize.getAspectRatio());
            return;
        }
        Logger.e(TAG, "Invalid target surface size. " + outputSize);
    }

    private float speedUpZoomBy2X(float f2) {
        return f2 > 1.0f ? ((f2 - 1.0f) * 2.0f) + 1.0f : 1.0f - ((1.0f - f2) * 2.0f);
    }

    private void startListeningToRotationEvents() {
        this.mRotationProvider.addListener(CameraXExecutors.mainThreadExecutor(), this.mDeviceRotationListener);
    }

    private void stopListeningToRotationEvents() {
        this.mRotationProvider.removeListener(this.mDeviceRotationListener);
    }

    @MainThread
    private void unbindImageAnalysisAndRecreate(int i2, int i3) {
        ImageAnalysis.Analyzer analyzer;
        Threads.checkMainThread();
        if (isCameraInitialized()) {
            this.mCameraProvider.unbind(this.mImageAnalysis);
        }
        ImageAnalysis.Builder imageQueueDepth = new ImageAnalysis.Builder().setBackpressureStrategy(i2).setImageQueueDepth(i3);
        setTargetOutputSize(imageQueueDepth, this.mImageAnalysisTargetSize);
        Executor executor = this.mAnalysisBackgroundExecutor;
        if (executor != null) {
            imageQueueDepth.setBackgroundExecutor(executor);
        }
        ImageAnalysis imageAnalysisBuild = imageQueueDepth.build();
        this.mImageAnalysis = imageAnalysisBuild;
        Executor executor2 = this.mAnalysisExecutor;
        if (executor2 == null || (analyzer = this.mAnalysisAnalyzer) == null) {
            return;
        }
        imageAnalysisBuild.setAnalyzer(executor2, analyzer);
    }

    private void unbindImageCaptureAndRecreate(int i2) {
        if (isCameraInitialized()) {
            this.mCameraProvider.unbind(this.mImageCapture);
        }
        ImageCapture.Builder captureMode = new ImageCapture.Builder().setCaptureMode(i2);
        setTargetOutputSize(captureMode, this.mImageCaptureTargetSize);
        Executor executor = this.mImageCaptureIoExecutor;
        if (executor != null) {
            captureMode.setIoExecutor(executor);
        }
        this.mImageCapture = captureMode.build();
    }

    private void unbindPreviewAndRecreate() {
        if (isCameraInitialized()) {
            this.mCameraProvider.unbind(this.mPreview);
        }
        Preview.Builder builder = new Preview.Builder();
        setTargetOutputSize(builder, this.mPreviewTargetSize);
        this.mPreview = builder.build();
    }

    private void unbindVideoAndRecreate() {
        if (isCameraInitialized()) {
            this.mCameraProvider.unbind(this.mVideoCapture);
        }
        VideoCapture.Builder builder = new VideoCapture.Builder();
        setTargetOutputSize(builder, this.mVideoCaptureOutputSize);
        this.mVideoCapture = builder.build();
    }

    @SuppressLint({"MissingPermission", "WrongConstant"})
    @MainThread
    public void attachPreviewSurface(@NonNull Preview.SurfaceProvider surfaceProvider, @NonNull ViewPort viewPort, @NonNull Display display) {
        Threads.checkMainThread();
        if (this.mSurfaceProvider != surfaceProvider) {
            this.mSurfaceProvider = surfaceProvider;
            this.mPreview.setSurfaceProvider(surfaceProvider);
        }
        this.mViewPort = viewPort;
        this.mPreviewDisplay = display;
        startListeningToRotationEvents();
        startCameraAndTrackStates();
    }

    @MainThread
    public void clearImageAnalysisAnalyzer() {
        Threads.checkMainThread();
        ImageAnalysis.Analyzer analyzer = this.mAnalysisAnalyzer;
        this.mAnalysisExecutor = null;
        this.mAnalysisAnalyzer = null;
        this.mImageAnalysis.clearAnalyzer();
        restartCameraIfAnalyzerResolutionChanged(analyzer, null);
    }

    @MainThread
    public void clearPreviewSurface() {
        Threads.checkMainThread();
        ProcessCameraProvider processCameraProvider = this.mCameraProvider;
        if (processCameraProvider != null) {
            processCameraProvider.unbind(this.mPreview, this.mImageCapture, this.mImageAnalysis, this.mVideoCapture);
        }
        this.mPreview.setSurfaceProvider(null);
        this.mCamera = null;
        this.mSurfaceProvider = null;
        this.mViewPort = null;
        this.mPreviewDisplay = null;
        stopListeningToRotationEvents();
    }

    @Nullable
    @OptIn(markerClass = {ExperimentalVideo.class})
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public UseCaseGroup createUseCaseGroup() {
        if (!isCameraInitialized()) {
            Logger.d(TAG, CAMERA_NOT_INITIALIZED);
            return null;
        }
        if (!isPreviewViewAttached()) {
            Logger.d(TAG, PREVIEW_VIEW_NOT_ATTACHED);
            return null;
        }
        UseCaseGroup.Builder builderAddUseCase = new UseCaseGroup.Builder().addUseCase(this.mPreview);
        if (isImageCaptureEnabled()) {
            builderAddUseCase.addUseCase(this.mImageCapture);
        } else {
            this.mCameraProvider.unbind(this.mImageCapture);
        }
        if (isImageAnalysisEnabled()) {
            builderAddUseCase.addUseCase(this.mImageAnalysis);
        } else {
            this.mCameraProvider.unbind(this.mImageAnalysis);
        }
        if (isVideoCaptureEnabled()) {
            builderAddUseCase.addUseCase(this.mVideoCapture);
        } else {
            this.mCameraProvider.unbind(this.mVideoCapture);
        }
        builderAddUseCase.setViewPort(this.mViewPort);
        Iterator<CameraEffect> it = this.mEffects.iterator();
        while (it.hasNext()) {
            builderAddUseCase.addEffect(it.next());
        }
        return builderAddUseCase.build();
    }

    @NonNull
    @MainThread
    public ListenableFuture<Void> enableTorch(boolean z2) {
        Threads.checkMainThread();
        if (isCameraAttached()) {
            return this.mCamera.getCameraControl().enableTorch(z2);
        }
        Logger.w(TAG, CAMERA_NOT_ATTACHED);
        return Futures.immediateFuture(null);
    }

    @Nullable
    @MainThread
    public CameraControl getCameraControl() {
        Threads.checkMainThread();
        Camera camera = this.mCamera;
        if (camera == null) {
            return null;
        }
        return camera.getCameraControl();
    }

    @Nullable
    @MainThread
    public CameraInfo getCameraInfo() {
        Threads.checkMainThread();
        Camera camera = this.mCamera;
        if (camera == null) {
            return null;
        }
        return camera.getCameraInfo();
    }

    @NonNull
    @MainThread
    public CameraSelector getCameraSelector() {
        Threads.checkMainThread();
        return this.mCameraSelector;
    }

    @Nullable
    @MainThread
    public Executor getImageAnalysisBackgroundExecutor() {
        Threads.checkMainThread();
        return this.mAnalysisBackgroundExecutor;
    }

    @MainThread
    public int getImageAnalysisBackpressureStrategy() {
        Threads.checkMainThread();
        return this.mImageAnalysis.getBackpressureStrategy();
    }

    @MainThread
    public int getImageAnalysisImageQueueDepth() {
        Threads.checkMainThread();
        return this.mImageAnalysis.getImageQueueDepth();
    }

    @Nullable
    @MainThread
    public OutputSize getImageAnalysisTargetSize() {
        Threads.checkMainThread();
        return this.mImageAnalysisTargetSize;
    }

    @MainThread
    public int getImageCaptureFlashMode() {
        Threads.checkMainThread();
        return this.mImageCapture.getFlashMode();
    }

    @Nullable
    @MainThread
    public Executor getImageCaptureIoExecutor() {
        Threads.checkMainThread();
        return this.mImageCaptureIoExecutor;
    }

    @MainThread
    public int getImageCaptureMode() {
        Threads.checkMainThread();
        return this.mImageCapture.getCaptureMode();
    }

    @Nullable
    @MainThread
    public OutputSize getImageCaptureTargetSize() {
        Threads.checkMainThread();
        return this.mImageCaptureTargetSize;
    }

    @NonNull
    public ListenableFuture<Void> getInitializationFuture() {
        return this.mInitializationFuture;
    }

    @Nullable
    @MainThread
    public OutputSize getPreviewTargetSize() {
        Threads.checkMainThread();
        return this.mPreviewTargetSize;
    }

    @NonNull
    @MainThread
    public LiveData<Integer> getTapToFocusState() {
        Threads.checkMainThread();
        return this.mTapToFocusState;
    }

    @NonNull
    @MainThread
    public LiveData<Integer> getTorchState() {
        Threads.checkMainThread();
        return this.mTorchState;
    }

    @Nullable
    @ExperimentalVideo
    @MainThread
    public OutputSize getVideoCaptureTargetSize() {
        Threads.checkMainThread();
        return this.mVideoCaptureOutputSize;
    }

    @NonNull
    @MainThread
    public LiveData<ZoomState> getZoomState() {
        Threads.checkMainThread();
        return this.mZoomState;
    }

    @MainThread
    public boolean hasCamera(@NonNull CameraSelector cameraSelector) {
        Threads.checkMainThread();
        Preconditions.checkNotNull(cameraSelector);
        ProcessCameraProvider processCameraProvider = this.mCameraProvider;
        if (processCameraProvider == null) {
            throw new IllegalStateException("Camera not initialized. Please wait for the initialization future to finish. See #getInitializationFuture().");
        }
        try {
            return processCameraProvider.hasCamera(cameraSelector);
        } catch (CameraInfoUnavailableException e2) {
            Logger.w(TAG, "Failed to check camera availability", e2);
            return false;
        }
    }

    @MainThread
    public boolean isImageAnalysisEnabled() {
        Threads.checkMainThread();
        return isUseCaseEnabled(2);
    }

    @MainThread
    public boolean isImageCaptureEnabled() {
        Threads.checkMainThread();
        return isUseCaseEnabled(1);
    }

    @MainThread
    public boolean isPinchToZoomEnabled() {
        Threads.checkMainThread();
        return this.mPinchToZoomEnabled;
    }

    @ExperimentalVideo
    @MainThread
    public boolean isRecording() {
        Threads.checkMainThread();
        return this.mVideoIsRecording.get();
    }

    @MainThread
    public boolean isTapToFocusEnabled() {
        Threads.checkMainThread();
        return this.mTapToFocusEnabled;
    }

    @ExperimentalVideo
    @MainThread
    public boolean isVideoCaptureEnabled() {
        Threads.checkMainThread();
        return isUseCaseEnabled(4);
    }

    public void onPinchToZoom(float f2) {
        if (!isCameraAttached()) {
            Logger.w(TAG, CAMERA_NOT_ATTACHED);
            return;
        }
        if (!this.mPinchToZoomEnabled) {
            Logger.d(TAG, "Pinch to zoom disabled.");
            return;
        }
        Logger.d(TAG, "Pinch to zoom with scale: " + f2);
        ZoomState value = getZoomState().getValue();
        if (value == null) {
            return;
        }
        setZoomRatio(Math.min(Math.max(value.getZoomRatio() * speedUpZoomBy2X(f2), value.getMinZoomRatio()), value.getMaxZoomRatio()));
    }

    public void onTapToFocus(MeteringPointFactory meteringPointFactory, float f2, float f3) {
        if (!isCameraAttached()) {
            Logger.w(TAG, CAMERA_NOT_ATTACHED);
            return;
        }
        if (!this.mTapToFocusEnabled) {
            Logger.d(TAG, "Tap to focus disabled. ");
            return;
        }
        Logger.d(TAG, "Tap to focus started: " + f2 + ", " + f3);
        this.mTapToFocusState.postValue(1);
        Futures.addCallback(this.mCamera.getCameraControl().startFocusAndMetering(new FocusMeteringAction.Builder(meteringPointFactory.createPoint(f2, f3, AF_SIZE), 1).addPoint(meteringPointFactory.createPoint(f2, f3, AE_SIZE), 2).build()), new FutureCallback<FocusMeteringResult>() { // from class: androidx.camera.view.CameraController.2
            @Override // androidx.camera.core.impl.utils.futures.FutureCallback
            public void onFailure(@NonNull Throwable th) {
                if (th instanceof CameraControl.OperationCanceledException) {
                    Logger.d(CameraController.TAG, "Tap-to-focus is canceled by new action.");
                } else {
                    Logger.d(CameraController.TAG, "Tap to focus failed.", th);
                    CameraController.this.mTapToFocusState.postValue(4);
                }
            }

            @Override // androidx.camera.core.impl.utils.futures.FutureCallback
            public void onSuccess(@Nullable FocusMeteringResult focusMeteringResult) {
                if (focusMeteringResult == null) {
                    return;
                }
                Logger.d(CameraController.TAG, "Tap to focus onSuccess: " + focusMeteringResult.isFocusSuccessful());
                CameraController.this.mTapToFocusState.postValue(Integer.valueOf(focusMeteringResult.isFocusSuccessful() ? 2 : 3));
            }
        }, CameraXExecutors.directExecutor());
    }

    @MainThread
    public void setCameraSelector(@NonNull CameraSelector cameraSelector) {
        Threads.checkMainThread();
        final CameraSelector cameraSelector2 = this.mCameraSelector;
        if (cameraSelector2 == cameraSelector) {
            return;
        }
        this.mCameraSelector = cameraSelector;
        ProcessCameraProvider processCameraProvider = this.mCameraProvider;
        if (processCameraProvider == null) {
            return;
        }
        processCameraProvider.unbind(this.mPreview, this.mImageCapture, this.mImageAnalysis, this.mVideoCapture);
        startCameraAndTrackStates(new Runnable() { // from class: androidx.camera.view.a
            @Override // java.lang.Runnable
            public final void run() {
                this.f1728c.lambda$setCameraSelector$3(cameraSelector2);
            }
        });
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setEffects(@NonNull List<CameraEffect> list) {
        if (Objects.equals(this.mEffects, list)) {
            return;
        }
        ProcessCameraProvider processCameraProvider = this.mCameraProvider;
        if (processCameraProvider != null) {
            processCameraProvider.unbindAll();
        }
        this.mEffects = list;
        startCameraAndTrackStates();
    }

    @OptIn(markerClass = {ExperimentalVideo.class})
    @MainThread
    public void setEnabledUseCases(int i2) {
        Threads.checkMainThread();
        final int i3 = this.mEnabledUseCases;
        if (i2 == i3) {
            return;
        }
        this.mEnabledUseCases = i2;
        if (!isVideoCaptureEnabled()) {
            stopRecording();
        }
        startCameraAndTrackStates(new Runnable() { // from class: androidx.camera.view.d
            @Override // java.lang.Runnable
            public final void run() {
                this.f1732c.lambda$setEnabledUseCases$2(i3);
            }
        });
    }

    @MainThread
    public void setImageAnalysisAnalyzer(@NonNull Executor executor, @NonNull ImageAnalysis.Analyzer analyzer) {
        Threads.checkMainThread();
        ImageAnalysis.Analyzer analyzer2 = this.mAnalysisAnalyzer;
        if (analyzer2 == analyzer && this.mAnalysisExecutor == executor) {
            return;
        }
        this.mAnalysisExecutor = executor;
        this.mAnalysisAnalyzer = analyzer;
        this.mImageAnalysis.setAnalyzer(executor, analyzer);
        restartCameraIfAnalyzerResolutionChanged(analyzer2, analyzer);
    }

    @MainThread
    public void setImageAnalysisBackgroundExecutor(@Nullable Executor executor) {
        Threads.checkMainThread();
        if (this.mAnalysisBackgroundExecutor == executor) {
            return;
        }
        this.mAnalysisBackgroundExecutor = executor;
        unbindImageAnalysisAndRecreate(this.mImageAnalysis.getBackpressureStrategy(), this.mImageAnalysis.getImageQueueDepth());
        startCameraAndTrackStates();
    }

    @MainThread
    public void setImageAnalysisBackpressureStrategy(int i2) {
        Threads.checkMainThread();
        if (this.mImageAnalysis.getBackpressureStrategy() == i2) {
            return;
        }
        unbindImageAnalysisAndRecreate(i2, this.mImageAnalysis.getImageQueueDepth());
        startCameraAndTrackStates();
    }

    @MainThread
    public void setImageAnalysisImageQueueDepth(int i2) {
        Threads.checkMainThread();
        if (this.mImageAnalysis.getImageQueueDepth() == i2) {
            return;
        }
        unbindImageAnalysisAndRecreate(this.mImageAnalysis.getBackpressureStrategy(), i2);
        startCameraAndTrackStates();
    }

    @MainThread
    public void setImageAnalysisTargetSize(@Nullable OutputSize outputSize) {
        Threads.checkMainThread();
        if (isOutputSizeEqual(this.mImageAnalysisTargetSize, outputSize)) {
            return;
        }
        this.mImageAnalysisTargetSize = outputSize;
        unbindImageAnalysisAndRecreate(this.mImageAnalysis.getBackpressureStrategy(), this.mImageAnalysis.getImageQueueDepth());
        startCameraAndTrackStates();
    }

    @MainThread
    public void setImageCaptureFlashMode(int i2) {
        Threads.checkMainThread();
        this.mImageCapture.setFlashMode(i2);
    }

    @MainThread
    public void setImageCaptureIoExecutor(@Nullable Executor executor) {
        Threads.checkMainThread();
        if (this.mImageCaptureIoExecutor == executor) {
            return;
        }
        this.mImageCaptureIoExecutor = executor;
        unbindImageCaptureAndRecreate(this.mImageCapture.getCaptureMode());
        startCameraAndTrackStates();
    }

    @MainThread
    public void setImageCaptureMode(int i2) {
        Threads.checkMainThread();
        if (this.mImageCapture.getCaptureMode() == i2) {
            return;
        }
        unbindImageCaptureAndRecreate(i2);
        startCameraAndTrackStates();
    }

    @MainThread
    public void setImageCaptureTargetSize(@Nullable OutputSize outputSize) {
        Threads.checkMainThread();
        if (isOutputSizeEqual(this.mImageCaptureTargetSize, outputSize)) {
            return;
        }
        this.mImageCaptureTargetSize = outputSize;
        unbindImageCaptureAndRecreate(getImageCaptureMode());
        startCameraAndTrackStates();
    }

    @NonNull
    @MainThread
    public ListenableFuture<Void> setLinearZoom(@FloatRange(from = 0.0d, to = 1.0d) float f2) {
        Threads.checkMainThread();
        if (isCameraAttached()) {
            return this.mCamera.getCameraControl().setLinearZoom(f2);
        }
        Logger.w(TAG, CAMERA_NOT_ATTACHED);
        return Futures.immediateFuture(null);
    }

    @MainThread
    public void setPinchToZoomEnabled(boolean z2) {
        Threads.checkMainThread();
        this.mPinchToZoomEnabled = z2;
    }

    @MainThread
    public void setPreviewTargetSize(@Nullable OutputSize outputSize) {
        Threads.checkMainThread();
        if (isOutputSizeEqual(this.mPreviewTargetSize, outputSize)) {
            return;
        }
        this.mPreviewTargetSize = outputSize;
        unbindPreviewAndRecreate();
        startCameraAndTrackStates();
    }

    @MainThread
    public void setTapToFocusEnabled(boolean z2) {
        Threads.checkMainThread();
        this.mTapToFocusEnabled = z2;
    }

    @ExperimentalVideo
    @MainThread
    public void setVideoCaptureTargetSize(@Nullable OutputSize outputSize) {
        Threads.checkMainThread();
        if (isOutputSizeEqual(this.mVideoCaptureOutputSize, outputSize)) {
            return;
        }
        this.mVideoCaptureOutputSize = outputSize;
        unbindVideoAndRecreate();
        startCameraAndTrackStates();
    }

    @NonNull
    @MainThread
    public ListenableFuture<Void> setZoomRatio(float f2) {
        Threads.checkMainThread();
        if (isCameraAttached()) {
            return this.mCamera.getCameraControl().setZoomRatio(f2);
        }
        Logger.w(TAG, CAMERA_NOT_ATTACHED);
        return Futures.immediateFuture(null);
    }

    @Nullable
    public abstract Camera startCamera();

    public void startCameraAndTrackStates() {
        startCameraAndTrackStates(null);
    }

    @ExperimentalVideo
    @SuppressLint({"MissingPermission"})
    @MainThread
    public void startRecording(@NonNull OutputFileOptions outputFileOptions, @NonNull Executor executor, @NonNull final OnVideoSavedCallback onVideoSavedCallback) throws IllegalStateException {
        Threads.checkMainThread();
        Preconditions.checkState(isCameraInitialized(), CAMERA_NOT_INITIALIZED);
        Preconditions.checkState(isVideoCaptureEnabled(), VIDEO_CAPTURE_DISABLED);
        this.mVideoCapture.lambda$startRecording$0(outputFileOptions.toVideoCaptureOutputFileOptions(), executor, new VideoCapture.OnVideoSavedCallback() { // from class: androidx.camera.view.CameraController.1
            @Override // androidx.camera.core.VideoCapture.OnVideoSavedCallback
            public void onError(int i2, @NonNull String str, @Nullable Throwable th) {
                CameraController.this.mVideoIsRecording.set(false);
                onVideoSavedCallback.onError(i2, str, th);
            }

            @Override // androidx.camera.core.VideoCapture.OnVideoSavedCallback
            public void onVideoSaved(@NonNull VideoCapture.OutputFileResults outputFileResults) {
                CameraController.this.mVideoIsRecording.set(false);
                onVideoSavedCallback.onVideoSaved(OutputFileResults.create(outputFileResults.getSavedUri()));
            }
        });
        this.mVideoIsRecording.set(true);
    }

    @ExperimentalVideo
    @MainThread
    public void stopRecording() {
        Threads.checkMainThread();
        if (this.mVideoIsRecording.get()) {
            this.mVideoCapture.lambda$stopRecording$5();
        }
    }

    @MainThread
    public void takePicture(@NonNull ImageCapture.OutputFileOptions outputFileOptions, @NonNull Executor executor, @NonNull ImageCapture.OnImageSavedCallback onImageSavedCallback) {
        Threads.checkMainThread();
        Preconditions.checkState(isCameraInitialized(), CAMERA_NOT_INITIALIZED);
        Preconditions.checkState(isImageCaptureEnabled(), IMAGE_CAPTURE_DISABLED);
        updateMirroringFlagInOutputFileOptions(outputFileOptions);
        this.mImageCapture.lambda$takePicture$4(outputFileOptions, executor, onImageSavedCallback);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    @VisibleForTesting
    public void updateMirroringFlagInOutputFileOptions(@NonNull ImageCapture.OutputFileOptions outputFileOptions) {
        if (this.mCameraSelector.getLensFacing() == null || outputFileOptions.getMetadata().isReversedHorizontalSet()) {
            return;
        }
        outputFileOptions.getMetadata().setReversedHorizontal(this.mCameraSelector.getLensFacing().intValue() == 0);
    }

    @OptIn(markerClass = {TransformExperimental.class})
    @MainThread
    public void updatePreviewViewTransform(@Nullable OutputTransform outputTransform) {
        Threads.checkMainThread();
        ImageAnalysis.Analyzer analyzer = this.mAnalysisAnalyzer;
        if (analyzer == null) {
            return;
        }
        if (outputTransform == null) {
            analyzer.updateTransform(null);
        } else if (analyzer.getTargetCoordinateSystem() == 1) {
            this.mAnalysisAnalyzer.updateTransform(outputTransform.getMatrix());
        }
    }

    public void startCameraAndTrackStates(@Nullable Runnable runnable) {
        try {
            this.mCamera = startCamera();
            if (!isCameraAttached()) {
                Logger.d(TAG, CAMERA_NOT_ATTACHED);
            } else {
                this.mZoomState.setSource(this.mCamera.getCameraInfo().getZoomState());
                this.mTorchState.setSource(this.mCamera.getCameraInfo().getTorchState());
            }
        } catch (IllegalArgumentException e2) {
            if (runnable != null) {
                runnable.run();
            }
            throw new IllegalStateException("The selected camera does not support the enabled use cases. Please disable use case and/or select a different camera. e.g. #setVideoCaptureEnabled(false)", e2);
        }
    }

    @RequiresApi(21)
    public static final class OutputSize {
        public static final int UNASSIGNED_ASPECT_RATIO = -1;
        private final int mAspectRatio;

        @Nullable
        private final Size mResolution;

        @Retention(RetentionPolicy.SOURCE)
        @RestrictTo({RestrictTo.Scope.LIBRARY})
        public @interface OutputAspectRatio {
        }

        public OutputSize(int i2) {
            Preconditions.checkArgument(i2 != -1);
            this.mAspectRatio = i2;
            this.mResolution = null;
        }

        public int getAspectRatio() {
            return this.mAspectRatio;
        }

        @Nullable
        public Size getResolution() {
            return this.mResolution;
        }

        @NonNull
        public String toString() {
            return "aspect ratio: " + this.mAspectRatio + " resolution: " + this.mResolution;
        }

        public OutputSize(@NonNull Size size) {
            Preconditions.checkNotNull(size);
            this.mAspectRatio = -1;
            this.mResolution = size;
        }
    }

    @MainThread
    public void takePicture(@NonNull Executor executor, @NonNull ImageCapture.OnImageCapturedCallback onImageCapturedCallback) {
        Threads.checkMainThread();
        Preconditions.checkState(isCameraInitialized(), CAMERA_NOT_INITIALIZED);
        Preconditions.checkState(isImageCaptureEnabled(), IMAGE_CAPTURE_DISABLED);
        this.mImageCapture.lambda$takePicture$3(executor, onImageCapturedCallback);
    }
}
