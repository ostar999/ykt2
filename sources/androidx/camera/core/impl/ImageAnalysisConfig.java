package androidx.camera.core.impl;

import android.util.Range;
import android.util.Size;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageReaderProxyProvider;
import androidx.camera.core.UseCase;
import androidx.camera.core.impl.CaptureConfig;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.core.internal.ThreadConfig;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;

@RequiresApi(21)
/* loaded from: classes.dex */
public final class ImageAnalysisConfig implements UseCaseConfig<ImageAnalysis>, ImageOutputConfig, ThreadConfig {
    private final OptionsBundle mConfig;
    public static final Config.Option<Integer> OPTION_BACKPRESSURE_STRATEGY = Config.Option.create("camerax.core.imageAnalysis.backpressureStrategy", ImageAnalysis.BackpressureStrategy.class);
    public static final Config.Option<Integer> OPTION_IMAGE_QUEUE_DEPTH = Config.Option.create("camerax.core.imageAnalysis.imageQueueDepth", Integer.TYPE);
    public static final Config.Option<ImageReaderProxyProvider> OPTION_IMAGE_READER_PROXY_PROVIDER = Config.Option.create("camerax.core.imageAnalysis.imageReaderProxyProvider", ImageReaderProxyProvider.class);
    public static final Config.Option<Integer> OPTION_OUTPUT_IMAGE_FORMAT = Config.Option.create("camerax.core.imageAnalysis.outputImageFormat", ImageAnalysis.OutputImageFormat.class);
    public static final Config.Option<Boolean> OPTION_ONE_PIXEL_SHIFT_ENABLED = Config.Option.create("camerax.core.imageAnalysis.onePixelShiftEnabled", Boolean.class);
    public static final Config.Option<Boolean> OPTION_OUTPUT_IMAGE_ROTATION_ENABLED = Config.Option.create("camerax.core.imageAnalysis.outputImageRotationEnabled", Boolean.class);

    public ImageAnalysisConfig(@NonNull OptionsBundle optionsBundle) {
        this.mConfig = optionsBundle;
    }

    @Override // androidx.camera.core.impl.ReadableConfig, androidx.camera.core.impl.Config
    public /* synthetic */ boolean containsOption(Config.Option option) {
        return d0.a(this, option);
    }

    @Override // androidx.camera.core.impl.ReadableConfig, androidx.camera.core.impl.Config
    public /* synthetic */ void findOptions(String str, Config.OptionMatcher optionMatcher) {
        d0.b(this, str, optionMatcher);
    }

    @Override // androidx.camera.core.impl.ImageOutputConfig
    public /* synthetic */ int getAppTargetRotation(int i2) {
        return w.a(this, i2);
    }

    @Override // androidx.camera.core.internal.ThreadConfig
    public /* synthetic */ Executor getBackgroundExecutor() {
        return androidx.camera.core.internal.e.a(this);
    }

    @Override // androidx.camera.core.internal.ThreadConfig
    public /* synthetic */ Executor getBackgroundExecutor(Executor executor) {
        return androidx.camera.core.internal.e.b(this, executor);
    }

    public int getBackpressureStrategy(int i2) {
        return ((Integer) retrieveOption(OPTION_BACKPRESSURE_STRATEGY, Integer.valueOf(i2))).intValue();
    }

    @Override // androidx.camera.core.impl.UseCaseConfig
    public /* synthetic */ CameraSelector getCameraSelector() {
        return h0.a(this);
    }

    @Override // androidx.camera.core.impl.UseCaseConfig
    public /* synthetic */ CameraSelector getCameraSelector(CameraSelector cameraSelector) {
        return h0.b(this, cameraSelector);
    }

    @Override // androidx.camera.core.impl.UseCaseConfig
    public /* synthetic */ CaptureConfig.OptionUnpacker getCaptureOptionUnpacker() {
        return h0.c(this);
    }

    @Override // androidx.camera.core.impl.UseCaseConfig
    public /* synthetic */ CaptureConfig.OptionUnpacker getCaptureOptionUnpacker(CaptureConfig.OptionUnpacker optionUnpacker) {
        return h0.d(this, optionUnpacker);
    }

    @Override // androidx.camera.core.impl.ReadableConfig
    @NonNull
    public Config getConfig() {
        return this.mConfig;
    }

    @Override // androidx.camera.core.impl.UseCaseConfig
    public /* synthetic */ CaptureConfig getDefaultCaptureConfig() {
        return h0.e(this);
    }

    @Override // androidx.camera.core.impl.UseCaseConfig
    public /* synthetic */ CaptureConfig getDefaultCaptureConfig(CaptureConfig captureConfig) {
        return h0.f(this, captureConfig);
    }

    @Override // androidx.camera.core.impl.ImageOutputConfig
    public /* synthetic */ Size getDefaultResolution() {
        return w.b(this);
    }

    @Override // androidx.camera.core.impl.ImageOutputConfig
    public /* synthetic */ Size getDefaultResolution(Size size) {
        return w.c(this, size);
    }

    @Override // androidx.camera.core.impl.UseCaseConfig
    public /* synthetic */ SessionConfig getDefaultSessionConfig() {
        return h0.g(this);
    }

    @Override // androidx.camera.core.impl.UseCaseConfig
    public /* synthetic */ SessionConfig getDefaultSessionConfig(SessionConfig sessionConfig) {
        return h0.h(this, sessionConfig);
    }

    public int getImageQueueDepth(int i2) {
        return ((Integer) retrieveOption(OPTION_IMAGE_QUEUE_DEPTH, Integer.valueOf(i2))).intValue();
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public ImageReaderProxyProvider getImageReaderProxyProvider() {
        return (ImageReaderProxyProvider) retrieveOption(OPTION_IMAGE_READER_PROXY_PROVIDER, null);
    }

    @Override // androidx.camera.core.impl.ImageInputConfig
    public int getInputFormat() {
        return 35;
    }

    @Override // androidx.camera.core.impl.ImageOutputConfig
    public /* synthetic */ Size getMaxResolution() {
        return w.d(this);
    }

    @Override // androidx.camera.core.impl.ImageOutputConfig
    public /* synthetic */ Size getMaxResolution(Size size) {
        return w.e(this, size);
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public Boolean getOnePixelShiftEnabled(@Nullable Boolean bool) {
        return (Boolean) retrieveOption(OPTION_ONE_PIXEL_SHIFT_ENABLED, bool);
    }

    @Override // androidx.camera.core.impl.ReadableConfig, androidx.camera.core.impl.Config
    public /* synthetic */ Config.OptionPriority getOptionPriority(Config.Option option) {
        return d0.c(this, option);
    }

    public int getOutputImageFormat(int i2) {
        return ((Integer) retrieveOption(OPTION_OUTPUT_IMAGE_FORMAT, Integer.valueOf(i2))).intValue();
    }

    @Override // androidx.camera.core.impl.ReadableConfig, androidx.camera.core.impl.Config
    public /* synthetic */ Set getPriorities(Config.Option option) {
        return d0.d(this, option);
    }

    @Override // androidx.camera.core.impl.UseCaseConfig
    public /* synthetic */ SessionConfig.OptionUnpacker getSessionOptionUnpacker() {
        return h0.i(this);
    }

    @Override // androidx.camera.core.impl.UseCaseConfig
    public /* synthetic */ SessionConfig.OptionUnpacker getSessionOptionUnpacker(SessionConfig.OptionUnpacker optionUnpacker) {
        return h0.j(this, optionUnpacker);
    }

    @Override // androidx.camera.core.impl.ImageOutputConfig
    public /* synthetic */ List getSupportedResolutions() {
        return w.f(this);
    }

    @Override // androidx.camera.core.impl.ImageOutputConfig
    public /* synthetic */ List getSupportedResolutions(List list) {
        return w.g(this, list);
    }

    @Override // androidx.camera.core.impl.UseCaseConfig
    public /* synthetic */ int getSurfaceOccupancyPriority() {
        return h0.k(this);
    }

    @Override // androidx.camera.core.impl.UseCaseConfig
    public /* synthetic */ int getSurfaceOccupancyPriority(int i2) {
        return h0.l(this, i2);
    }

    @Override // androidx.camera.core.impl.ImageOutputConfig
    public /* synthetic */ int getTargetAspectRatio() {
        return w.h(this);
    }

    @Override // androidx.camera.core.internal.TargetConfig
    public /* synthetic */ Class getTargetClass() {
        return androidx.camera.core.internal.d.a(this);
    }

    @Override // androidx.camera.core.internal.TargetConfig
    public /* synthetic */ Class getTargetClass(Class cls) {
        return androidx.camera.core.internal.d.b(this, cls);
    }

    @Override // androidx.camera.core.impl.UseCaseConfig
    public /* synthetic */ Range getTargetFramerate() {
        return h0.m(this);
    }

    @Override // androidx.camera.core.impl.UseCaseConfig
    public /* synthetic */ Range getTargetFramerate(Range range) {
        return h0.n(this, range);
    }

    @Override // androidx.camera.core.internal.TargetConfig
    public /* synthetic */ String getTargetName() {
        return androidx.camera.core.internal.d.c(this);
    }

    @Override // androidx.camera.core.internal.TargetConfig
    public /* synthetic */ String getTargetName(String str) {
        return androidx.camera.core.internal.d.d(this, str);
    }

    @Override // androidx.camera.core.impl.ImageOutputConfig
    public /* synthetic */ Size getTargetResolution() {
        return w.i(this);
    }

    @Override // androidx.camera.core.impl.ImageOutputConfig
    public /* synthetic */ Size getTargetResolution(Size size) {
        return w.j(this, size);
    }

    @Override // androidx.camera.core.impl.ImageOutputConfig
    public /* synthetic */ int getTargetRotation() {
        return w.k(this);
    }

    @Override // androidx.camera.core.impl.ImageOutputConfig
    public /* synthetic */ int getTargetRotation(int i2) {
        return w.l(this, i2);
    }

    @Override // androidx.camera.core.internal.UseCaseEventConfig
    public /* synthetic */ UseCase.EventCallback getUseCaseEventCallback() {
        return androidx.camera.core.internal.f.a(this);
    }

    @Override // androidx.camera.core.internal.UseCaseEventConfig
    public /* synthetic */ UseCase.EventCallback getUseCaseEventCallback(UseCase.EventCallback eventCallback) {
        return androidx.camera.core.internal.f.b(this, eventCallback);
    }

    @Override // androidx.camera.core.impl.ImageOutputConfig
    public /* synthetic */ boolean hasTargetAspectRatio() {
        return w.m(this);
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public Boolean isOutputImageRotationEnabled(@Nullable Boolean bool) {
        return (Boolean) retrieveOption(OPTION_OUTPUT_IMAGE_ROTATION_ENABLED, bool);
    }

    @Override // androidx.camera.core.impl.UseCaseConfig
    public /* synthetic */ boolean isZslDisabled(boolean z2) {
        return h0.o(this, z2);
    }

    @Override // androidx.camera.core.impl.ReadableConfig, androidx.camera.core.impl.Config
    public /* synthetic */ Set listOptions() {
        return d0.e(this);
    }

    @Override // androidx.camera.core.impl.ReadableConfig, androidx.camera.core.impl.Config
    public /* synthetic */ Object retrieveOption(Config.Option option) {
        return d0.f(this, option);
    }

    @Override // androidx.camera.core.impl.ReadableConfig, androidx.camera.core.impl.Config
    public /* synthetic */ Object retrieveOption(Config.Option option, Object obj) {
        return d0.g(this, option, obj);
    }

    @Override // androidx.camera.core.impl.ReadableConfig, androidx.camera.core.impl.Config
    public /* synthetic */ Object retrieveOptionWithPriority(Config.Option option, Config.OptionPriority optionPriority) {
        return d0.h(this, option, optionPriority);
    }

    public int getBackpressureStrategy() {
        return ((Integer) retrieveOption(OPTION_BACKPRESSURE_STRATEGY)).intValue();
    }

    public int getImageQueueDepth() {
        return ((Integer) retrieveOption(OPTION_IMAGE_QUEUE_DEPTH)).intValue();
    }
}
