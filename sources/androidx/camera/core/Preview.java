package androidx.camera.core;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Pair;
import android.util.Size;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.annotation.UiThread;
import androidx.annotation.VisibleForTesting;
import androidx.camera.core.SurfaceOutput;
import androidx.camera.core.SurfaceRequest;
import androidx.camera.core.UseCase;
import androidx.camera.core.impl.CameraCaptureCallback;
import androidx.camera.core.impl.CameraCaptureResult;
import androidx.camera.core.impl.CameraInfoInternal;
import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.impl.CaptureConfig;
import androidx.camera.core.impl.CaptureProcessor;
import androidx.camera.core.impl.CaptureStage;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.ConfigProvider;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.camera.core.impl.ImageInfoProcessor;
import androidx.camera.core.impl.ImageInputConfig;
import androidx.camera.core.impl.ImageOutputConfig;
import androidx.camera.core.impl.MutableConfig;
import androidx.camera.core.impl.MutableOptionsBundle;
import androidx.camera.core.impl.OptionsBundle;
import androidx.camera.core.impl.PreviewConfig;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.core.impl.UseCaseConfig;
import androidx.camera.core.impl.UseCaseConfigFactory;
import androidx.camera.core.impl.utils.Threads;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.internal.CameraCaptureResultImageInfo;
import androidx.camera.core.internal.TargetConfig;
import androidx.camera.core.internal.ThreadConfig;
import androidx.camera.core.internal.UseCaseEventConfig;
import androidx.camera.core.processing.SettableSurface;
import androidx.camera.core.processing.SurfaceEdge;
import androidx.camera.core.processing.SurfaceProcessorInternal;
import androidx.camera.core.processing.SurfaceProcessorNode;
import androidx.core.util.Preconditions;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Executor;

@RequiresApi(21)
/* loaded from: classes.dex */
public final class Preview extends UseCase {

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static final Defaults DEFAULT_CONFIG = new Defaults();
    private static final Executor DEFAULT_SURFACE_PROVIDER_EXECUTOR = CameraXExecutors.mainThreadExecutor();
    private static final String TAG = "Preview";

    @Nullable
    @VisibleForTesting
    SurfaceRequest mCurrentSurfaceRequest;

    @Nullable
    private SurfaceProcessorNode mNode;
    private DeferrableSurface mSessionDeferrableSurface;

    @Nullable
    private SurfaceProcessorInternal mSurfaceProcessor;

    @Nullable
    private SurfaceProvider mSurfaceProvider;

    @NonNull
    private Executor mSurfaceProviderExecutor;

    @Nullable
    private Size mSurfaceSize;

    public static final class Builder implements UseCaseConfig.Builder<Preview, PreviewConfig, Builder>, ImageOutputConfig.Builder<Builder>, ThreadConfig.Builder<Builder> {
        private final MutableOptionsBundle mMutableConfig;

        public Builder() {
            this(MutableOptionsBundle.create());
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public static Builder fromConfig(@NonNull Config config) {
            return new Builder(MutableOptionsBundle.from(config));
        }

        @Override // androidx.camera.core.ExtendableBuilder
        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public MutableConfig getMutableConfig() {
            return this.mMutableConfig;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setCaptureProcessor(@NonNull CaptureProcessor captureProcessor) {
            getMutableConfig().insertOption(PreviewConfig.OPTION_PREVIEW_CAPTURE_PROCESSOR, captureProcessor);
            return this;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setImageInfoProcessor(@NonNull ImageInfoProcessor imageInfoProcessor) {
            getMutableConfig().insertOption(PreviewConfig.IMAGE_INFO_PROCESSOR, imageInfoProcessor);
            return this;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setIsRgba8888SurfaceRequired(boolean z2) {
            getMutableConfig().insertOption(PreviewConfig.OPTION_RGBA8888_SURFACE_REQUIRED, Boolean.valueOf(z2));
            return this;
        }

        @Override // androidx.camera.core.impl.ImageOutputConfig.Builder
        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public /* bridge */ /* synthetic */ Builder setSupportedResolutions(@NonNull List list) {
            return setSupportedResolutions((List<Pair<Integer, Size[]>>) list);
        }

        @Override // androidx.camera.core.internal.TargetConfig.Builder
        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public /* bridge */ /* synthetic */ Object setTargetClass(@NonNull Class cls) {
            return setTargetClass((Class<Preview>) cls);
        }

        private Builder(MutableOptionsBundle mutableOptionsBundle) {
            this.mMutableConfig = mutableOptionsBundle;
            Class cls = (Class) mutableOptionsBundle.retrieveOption(TargetConfig.OPTION_TARGET_CLASS, null);
            if (cls == null || cls.equals(Preview.class)) {
                setTargetClass(Preview.class);
                return;
            }
            throw new IllegalArgumentException("Invalid target class configuration for " + this + ": " + cls);
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public static Builder fromConfig(@NonNull PreviewConfig previewConfig) {
            return new Builder(MutableOptionsBundle.from((Config) previewConfig));
        }

        @Override // androidx.camera.core.ExtendableBuilder
        @NonNull
        public Preview build() {
            if (getMutableConfig().retrieveOption(ImageOutputConfig.OPTION_TARGET_ASPECT_RATIO, null) == null || getMutableConfig().retrieveOption(ImageOutputConfig.OPTION_TARGET_RESOLUTION, null) == null) {
                return new Preview(getUseCaseConfig());
            }
            throw new IllegalArgumentException("Cannot use both setTargetResolution and setTargetAspectRatio on the same config.");
        }

        @Override // androidx.camera.core.impl.UseCaseConfig.Builder
        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public PreviewConfig getUseCaseConfig() {
            return new PreviewConfig(OptionsBundle.from(this.mMutableConfig));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.internal.ThreadConfig.Builder
        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setBackgroundExecutor(@NonNull Executor executor) {
            getMutableConfig().insertOption(ThreadConfig.OPTION_BACKGROUND_EXECUTOR, executor);
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.UseCaseConfig.Builder
        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setCameraSelector(@NonNull CameraSelector cameraSelector) {
            getMutableConfig().insertOption(UseCaseConfig.OPTION_CAMERA_SELECTOR, cameraSelector);
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.UseCaseConfig.Builder
        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setCaptureOptionUnpacker(@NonNull CaptureConfig.OptionUnpacker optionUnpacker) {
            getMutableConfig().insertOption(UseCaseConfig.OPTION_CAPTURE_CONFIG_UNPACKER, optionUnpacker);
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.UseCaseConfig.Builder
        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setDefaultCaptureConfig(@NonNull CaptureConfig captureConfig) {
            getMutableConfig().insertOption(UseCaseConfig.OPTION_DEFAULT_CAPTURE_CONFIG, captureConfig);
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.ImageOutputConfig.Builder
        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setDefaultResolution(@NonNull Size size) {
            getMutableConfig().insertOption(ImageOutputConfig.OPTION_DEFAULT_RESOLUTION, size);
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.UseCaseConfig.Builder
        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setDefaultSessionConfig(@NonNull SessionConfig sessionConfig) {
            getMutableConfig().insertOption(UseCaseConfig.OPTION_DEFAULT_SESSION_CONFIG, sessionConfig);
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.ImageOutputConfig.Builder
        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setMaxResolution(@NonNull Size size) {
            getMutableConfig().insertOption(ImageOutputConfig.OPTION_MAX_RESOLUTION, size);
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.UseCaseConfig.Builder
        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setSessionOptionUnpacker(@NonNull SessionConfig.OptionUnpacker optionUnpacker) {
            getMutableConfig().insertOption(UseCaseConfig.OPTION_SESSION_CONFIG_UNPACKER, optionUnpacker);
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.ImageOutputConfig.Builder
        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setSupportedResolutions(@NonNull List<Pair<Integer, Size[]>> list) {
            getMutableConfig().insertOption(ImageOutputConfig.OPTION_SUPPORTED_RESOLUTIONS, list);
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.UseCaseConfig.Builder
        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setSurfaceOccupancyPriority(int i2) {
            getMutableConfig().insertOption(UseCaseConfig.OPTION_SURFACE_OCCUPANCY_PRIORITY, Integer.valueOf(i2));
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.ImageOutputConfig.Builder
        @NonNull
        public Builder setTargetAspectRatio(int i2) {
            getMutableConfig().insertOption(ImageOutputConfig.OPTION_TARGET_ASPECT_RATIO, Integer.valueOf(i2));
            return this;
        }

        @Override // androidx.camera.core.internal.TargetConfig.Builder
        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setTargetClass(@NonNull Class<Preview> cls) {
            getMutableConfig().insertOption(TargetConfig.OPTION_TARGET_CLASS, cls);
            if (getMutableConfig().retrieveOption(TargetConfig.OPTION_TARGET_NAME, null) == null) {
                setTargetName(cls.getCanonicalName() + "-" + UUID.randomUUID());
            }
            return this;
        }

        @Override // androidx.camera.core.internal.TargetConfig.Builder
        @NonNull
        public Builder setTargetName(@NonNull String str) {
            getMutableConfig().insertOption(TargetConfig.OPTION_TARGET_NAME, str);
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.ImageOutputConfig.Builder
        @NonNull
        public Builder setTargetResolution(@NonNull Size size) {
            getMutableConfig().insertOption(ImageOutputConfig.OPTION_TARGET_RESOLUTION, size);
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.ImageOutputConfig.Builder
        @NonNull
        public Builder setTargetRotation(int i2) {
            getMutableConfig().insertOption(ImageOutputConfig.OPTION_TARGET_ROTATION, Integer.valueOf(i2));
            getMutableConfig().insertOption(ImageOutputConfig.OPTION_APP_TARGET_ROTATION, Integer.valueOf(i2));
            return this;
        }

        @Override // androidx.camera.core.internal.UseCaseEventConfig.Builder
        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setUseCaseEventCallback(@NonNull UseCase.EventCallback eventCallback) {
            getMutableConfig().insertOption(UseCaseEventConfig.OPTION_USE_CASE_EVENT_CALLBACK, eventCallback);
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.UseCaseConfig.Builder
        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setZslDisabled(boolean z2) {
            getMutableConfig().insertOption(UseCaseConfig.OPTION_ZSL_DISABLED, Boolean.valueOf(z2));
            return this;
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static final class Defaults implements ConfigProvider<PreviewConfig> {
        private static final int DEFAULT_ASPECT_RATIO = 0;
        private static final PreviewConfig DEFAULT_CONFIG = new Builder().setSurfaceOccupancyPriority(2).setTargetAspectRatio(0).getUseCaseConfig();
        private static final int DEFAULT_SURFACE_OCCUPANCY_PRIORITY = 2;

        @Override // androidx.camera.core.impl.ConfigProvider
        @NonNull
        public PreviewConfig getConfig() {
            return DEFAULT_CONFIG;
        }
    }

    public interface SurfaceProvider {
        void onSurfaceRequested(@NonNull SurfaceRequest surfaceRequest);
    }

    @MainThread
    public Preview(@NonNull PreviewConfig previewConfig) {
        super(previewConfig);
        this.mSurfaceProviderExecutor = DEFAULT_SURFACE_PROVIDER_EXECUTOR;
    }

    private void addCameraSurfaceAndErrorListener(@NonNull SessionConfig.Builder builder, @NonNull final String str, @NonNull final PreviewConfig previewConfig, @NonNull final Size size) {
        if (this.mSurfaceProvider != null) {
            builder.addSurface(this.mSessionDeferrableSurface);
        }
        builder.addErrorListener(new SessionConfig.ErrorListener() { // from class: androidx.camera.core.a1
            @Override // androidx.camera.core.impl.SessionConfig.ErrorListener
            public final void onError(SessionConfig sessionConfig, SessionConfig.SessionError sessionError) {
                this.f1469a.lambda$addCameraSurfaceAndErrorListener$0(str, previewConfig, size, sessionConfig, sessionError);
            }
        });
    }

    private void clearPipeline() {
        DeferrableSurface deferrableSurface = this.mSessionDeferrableSurface;
        if (deferrableSurface != null) {
            deferrableSurface.close();
            this.mSessionDeferrableSurface = null;
        }
        SurfaceProcessorNode surfaceProcessorNode = this.mNode;
        if (surfaceProcessorNode != null) {
            surfaceProcessorNode.release();
            this.mNode = null;
        }
        this.mCurrentSurfaceRequest = null;
    }

    @NonNull
    @MainThread
    private SessionConfig.Builder createPipelineWithNode(@NonNull String str, @NonNull PreviewConfig previewConfig, @NonNull Size size) {
        Threads.checkMainThread();
        Preconditions.checkNotNull(this.mSurfaceProcessor);
        CameraInternal camera = getCamera();
        Preconditions.checkNotNull(camera);
        clearPipeline();
        this.mNode = new SurfaceProcessorNode(camera, SurfaceOutput.GlTransformOptions.USE_SURFACE_TEXTURE_TRANSFORM, this.mSurfaceProcessor);
        Matrix matrix = new Matrix();
        Rect cropRect = getCropRect(size);
        Objects.requireNonNull(cropRect);
        SettableSurface settableSurface = new SettableSurface(1, size, 34, matrix, true, cropRect, getRelativeRotation(camera), false);
        SettableSurface settableSurface2 = this.mNode.transform(SurfaceEdge.create(Collections.singletonList(settableSurface))).getSurfaces().get(0);
        this.mSessionDeferrableSurface = settableSurface;
        this.mCurrentSurfaceRequest = settableSurface2.createSurfaceRequest(camera);
        if (this.mSurfaceProvider != null) {
            sendSurfaceRequest();
        }
        SessionConfig.Builder builderCreateFrom = SessionConfig.Builder.createFrom(previewConfig);
        addCameraSurfaceAndErrorListener(builderCreateFrom, str, previewConfig, size);
        return builderCreateFrom;
    }

    @Nullable
    private Rect getCropRect(@Nullable Size size) {
        if (getViewPortCropRect() != null) {
            return getViewPortCropRect();
        }
        if (size != null) {
            return new Rect(0, 0, size.getWidth(), size.getHeight());
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addCameraSurfaceAndErrorListener$0(String str, PreviewConfig previewConfig, Size size, SessionConfig sessionConfig, SessionConfig.SessionError sessionError) {
        if (isCurrentCamera(str)) {
            updateSessionConfig(createPipeline(str, previewConfig, size).build());
            notifyReset();
        }
    }

    private void sendSurfaceRequest() {
        final SurfaceProvider surfaceProvider = (SurfaceProvider) Preconditions.checkNotNull(this.mSurfaceProvider);
        final SurfaceRequest surfaceRequest = (SurfaceRequest) Preconditions.checkNotNull(this.mCurrentSurfaceRequest);
        this.mSurfaceProviderExecutor.execute(new Runnable() { // from class: androidx.camera.core.z0
            @Override // java.lang.Runnable
            public final void run() {
                surfaceProvider.onSurfaceRequested(surfaceRequest);
            }
        });
        sendTransformationInfoIfReady();
    }

    private void sendTransformationInfoIfReady() {
        CameraInternal camera = getCamera();
        SurfaceProvider surfaceProvider = this.mSurfaceProvider;
        Rect cropRect = getCropRect(this.mSurfaceSize);
        SurfaceRequest surfaceRequest = this.mCurrentSurfaceRequest;
        if (camera == null || surfaceProvider == null || cropRect == null || surfaceRequest == null) {
            return;
        }
        surfaceRequest.updateTransformationInfo(SurfaceRequest.TransformationInfo.of(cropRect, getRelativeRotation(camera), getAppTargetRotation()));
    }

    private void updateConfigAndOutput(@NonNull String str, @NonNull PreviewConfig previewConfig, @NonNull Size size) {
        updateSessionConfig(createPipeline(str, previewConfig, size).build());
    }

    @MainThread
    public SessionConfig.Builder createPipeline(@NonNull String str, @NonNull PreviewConfig previewConfig, @NonNull Size size) {
        if (this.mSurfaceProcessor != null) {
            return createPipelineWithNode(str, previewConfig, size);
        }
        Threads.checkMainThread();
        SessionConfig.Builder builderCreateFrom = SessionConfig.Builder.createFrom(previewConfig);
        CaptureProcessor captureProcessor = previewConfig.getCaptureProcessor(null);
        clearPipeline();
        SurfaceRequest surfaceRequest = new SurfaceRequest(size, getCamera(), previewConfig.isRgba8888SurfaceRequired(false));
        this.mCurrentSurfaceRequest = surfaceRequest;
        if (this.mSurfaceProvider != null) {
            sendSurfaceRequest();
        }
        if (captureProcessor != null) {
            CaptureStage.DefaultCaptureStage defaultCaptureStage = new CaptureStage.DefaultCaptureStage();
            final HandlerThread handlerThread = new HandlerThread("CameraX-preview_processing");
            handlerThread.start();
            String string = Integer.toString(defaultCaptureStage.hashCode());
            ProcessingSurface processingSurface = new ProcessingSurface(size.getWidth(), size.getHeight(), previewConfig.getInputFormat(), new Handler(handlerThread.getLooper()), defaultCaptureStage, captureProcessor, surfaceRequest.getDeferrableSurface(), string);
            builderCreateFrom.addCameraCaptureCallback(processingSurface.getCameraCaptureCallback());
            processingSurface.getTerminationFuture().addListener(new Runnable() { // from class: androidx.camera.core.b1
                @Override // java.lang.Runnable
                public final void run() {
                    handlerThread.quitSafely();
                }
            }, CameraXExecutors.directExecutor());
            this.mSessionDeferrableSurface = processingSurface;
            builderCreateFrom.addTag(string, Integer.valueOf(defaultCaptureStage.getId()));
        } else {
            final ImageInfoProcessor imageInfoProcessor = previewConfig.getImageInfoProcessor(null);
            if (imageInfoProcessor != null) {
                builderCreateFrom.addCameraCaptureCallback(new CameraCaptureCallback() { // from class: androidx.camera.core.Preview.1
                    @Override // androidx.camera.core.impl.CameraCaptureCallback
                    public void onCaptureCompleted(@NonNull CameraCaptureResult cameraCaptureResult) {
                        super.onCaptureCompleted(cameraCaptureResult);
                        if (imageInfoProcessor.process(new CameraCaptureResultImageInfo(cameraCaptureResult))) {
                            Preview.this.notifyUpdated();
                        }
                    }
                });
            }
            this.mSessionDeferrableSurface = surfaceRequest.getDeferrableSurface();
        }
        addCameraSurfaceAndErrorListener(builderCreateFrom, str, previewConfig, size);
        return builderCreateFrom;
    }

    @Override // androidx.camera.core.UseCase
    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public UseCaseConfig<?> getDefaultConfig(boolean z2, @NonNull UseCaseConfigFactory useCaseConfigFactory) {
        Config config = useCaseConfigFactory.getConfig(UseCaseConfigFactory.CaptureType.PREVIEW, 1);
        if (z2) {
            config = androidx.camera.core.impl.n.b(config, DEFAULT_CONFIG.getConfig());
        }
        if (config == null) {
            return null;
        }
        return getUseCaseConfigBuilder(config).getUseCaseConfig();
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    @VisibleForTesting
    public SurfaceProcessorInternal getProcessor() {
        return this.mSurfaceProcessor;
    }

    @Override // androidx.camera.core.UseCase
    @Nullable
    public ResolutionInfo getResolutionInfo() {
        return super.getResolutionInfo();
    }

    public int getTargetRotation() {
        return getTargetRotationInternal();
    }

    @Override // androidx.camera.core.UseCase
    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public UseCaseConfig.Builder<?, ?, ?> getUseCaseConfigBuilder(@NonNull Config config) {
        return Builder.fromConfig(config);
    }

    @Override // androidx.camera.core.UseCase
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void onDetached() {
        clearPipeline();
    }

    @Override // androidx.camera.core.UseCase
    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public UseCaseConfig<?> onMergeConfig(@NonNull CameraInfoInternal cameraInfoInternal, @NonNull UseCaseConfig.Builder<?, ?, ?> builder) {
        if (builder.getMutableConfig().retrieveOption(PreviewConfig.OPTION_PREVIEW_CAPTURE_PROCESSOR, null) != null) {
            builder.getMutableConfig().insertOption(ImageInputConfig.OPTION_INPUT_FORMAT, 35);
        } else {
            builder.getMutableConfig().insertOption(ImageInputConfig.OPTION_INPUT_FORMAT, 34);
        }
        return builder.getUseCaseConfig();
    }

    @Override // androidx.camera.core.UseCase
    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public Size onSuggestedResolutionUpdated(@NonNull Size size) {
        this.mSurfaceSize = size;
        updateConfigAndOutput(getCameraId(), (PreviewConfig) getCurrentConfig(), this.mSurfaceSize);
        return size;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setProcessor(@Nullable SurfaceProcessorInternal surfaceProcessorInternal) {
        this.mSurfaceProcessor = surfaceProcessorInternal;
    }

    @UiThread
    public void setSurfaceProvider(@NonNull Executor executor, @Nullable SurfaceProvider surfaceProvider) {
        Threads.checkMainThread();
        if (surfaceProvider == null) {
            this.mSurfaceProvider = null;
            notifyInactive();
            return;
        }
        this.mSurfaceProvider = surfaceProvider;
        this.mSurfaceProviderExecutor = executor;
        notifyActive();
        if (getAttachedSurfaceResolution() != null) {
            updateConfigAndOutput(getCameraId(), (PreviewConfig) getCurrentConfig(), getAttachedSurfaceResolution());
            notifyReset();
        }
    }

    public void setTargetRotation(int i2) {
        if (setTargetRotationInternal(i2)) {
            sendTransformationInfoIfReady();
        }
    }

    @Override // androidx.camera.core.UseCase
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public void setViewPortCropRect(@NonNull Rect rect) {
        super.setViewPortCropRect(rect);
        sendTransformationInfoIfReady();
    }

    @NonNull
    public String toString() {
        return "Preview:" + getName();
    }

    @UiThread
    public void setSurfaceProvider(@Nullable SurfaceProvider surfaceProvider) {
        setSurfaceProvider(DEFAULT_SURFACE_PROVIDER_EXECUTOR, surfaceProvider);
    }
}
