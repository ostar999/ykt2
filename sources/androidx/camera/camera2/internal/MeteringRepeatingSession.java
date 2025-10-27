package androidx.camera.camera2.internal;

import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.util.Range;
import android.util.Size;
import android.view.Surface;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.camera.camera2.internal.compat.CameraCharacteristicsCompat;
import androidx.camera.camera2.internal.compat.workaround.SupportedRepeatingSurfaceSize;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.Logger;
import androidx.camera.core.UseCase;
import androidx.camera.core.impl.CaptureConfig;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.camera.core.impl.ImmediateSurface;
import androidx.camera.core.impl.MutableOptionsBundle;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.core.impl.UseCaseConfig;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.impl.utils.futures.FutureCallback;
import androidx.camera.core.impl.utils.futures.Futures;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@RequiresApi(21)
/* loaded from: classes.dex */
class MeteringRepeatingSession {
    private static final String TAG = "MeteringRepeating";

    @NonNull
    private final MeteringRepeatingConfig mConfigWithDefaults;
    private DeferrableSurface mDeferrableSurface;

    @NonNull
    private final SessionConfig mSessionConfig;

    @NonNull
    private final SupportedRepeatingSurfaceSize mSupportedRepeatingSurfaceSize = new SupportedRepeatingSurfaceSize();

    public static class MeteringRepeatingConfig implements UseCaseConfig<UseCase> {

        @NonNull
        private final Config mConfig;

        public MeteringRepeatingConfig() {
            MutableOptionsBundle mutableOptionsBundleCreate = MutableOptionsBundle.create();
            mutableOptionsBundleCreate.insertOption(UseCaseConfig.OPTION_SESSION_CONFIG_UNPACKER, new Camera2SessionOptionUnpacker());
            this.mConfig = mutableOptionsBundleCreate;
        }

        @Override // androidx.camera.core.impl.ReadableConfig, androidx.camera.core.impl.Config
        public /* synthetic */ boolean containsOption(Config.Option option) {
            return androidx.camera.core.impl.d0.a(this, option);
        }

        @Override // androidx.camera.core.impl.ReadableConfig, androidx.camera.core.impl.Config
        public /* synthetic */ void findOptions(String str, Config.OptionMatcher optionMatcher) {
            androidx.camera.core.impl.d0.b(this, str, optionMatcher);
        }

        @Override // androidx.camera.core.impl.UseCaseConfig
        public /* synthetic */ CameraSelector getCameraSelector() {
            return androidx.camera.core.impl.h0.a(this);
        }

        @Override // androidx.camera.core.impl.UseCaseConfig
        public /* synthetic */ CameraSelector getCameraSelector(CameraSelector cameraSelector) {
            return androidx.camera.core.impl.h0.b(this, cameraSelector);
        }

        @Override // androidx.camera.core.impl.UseCaseConfig
        public /* synthetic */ CaptureConfig.OptionUnpacker getCaptureOptionUnpacker() {
            return androidx.camera.core.impl.h0.c(this);
        }

        @Override // androidx.camera.core.impl.UseCaseConfig
        public /* synthetic */ CaptureConfig.OptionUnpacker getCaptureOptionUnpacker(CaptureConfig.OptionUnpacker optionUnpacker) {
            return androidx.camera.core.impl.h0.d(this, optionUnpacker);
        }

        @Override // androidx.camera.core.impl.ReadableConfig
        @NonNull
        public Config getConfig() {
            return this.mConfig;
        }

        @Override // androidx.camera.core.impl.UseCaseConfig
        public /* synthetic */ CaptureConfig getDefaultCaptureConfig() {
            return androidx.camera.core.impl.h0.e(this);
        }

        @Override // androidx.camera.core.impl.UseCaseConfig
        public /* synthetic */ CaptureConfig getDefaultCaptureConfig(CaptureConfig captureConfig) {
            return androidx.camera.core.impl.h0.f(this, captureConfig);
        }

        @Override // androidx.camera.core.impl.UseCaseConfig
        public /* synthetic */ SessionConfig getDefaultSessionConfig() {
            return androidx.camera.core.impl.h0.g(this);
        }

        @Override // androidx.camera.core.impl.UseCaseConfig
        public /* synthetic */ SessionConfig getDefaultSessionConfig(SessionConfig sessionConfig) {
            return androidx.camera.core.impl.h0.h(this, sessionConfig);
        }

        @Override // androidx.camera.core.impl.ImageInputConfig
        public /* synthetic */ int getInputFormat() {
            return androidx.camera.core.impl.v.a(this);
        }

        @Override // androidx.camera.core.impl.ReadableConfig, androidx.camera.core.impl.Config
        public /* synthetic */ Config.OptionPriority getOptionPriority(Config.Option option) {
            return androidx.camera.core.impl.d0.c(this, option);
        }

        @Override // androidx.camera.core.impl.ReadableConfig, androidx.camera.core.impl.Config
        public /* synthetic */ Set getPriorities(Config.Option option) {
            return androidx.camera.core.impl.d0.d(this, option);
        }

        @Override // androidx.camera.core.impl.UseCaseConfig
        public /* synthetic */ SessionConfig.OptionUnpacker getSessionOptionUnpacker() {
            return androidx.camera.core.impl.h0.i(this);
        }

        @Override // androidx.camera.core.impl.UseCaseConfig
        public /* synthetic */ SessionConfig.OptionUnpacker getSessionOptionUnpacker(SessionConfig.OptionUnpacker optionUnpacker) {
            return androidx.camera.core.impl.h0.j(this, optionUnpacker);
        }

        @Override // androidx.camera.core.impl.UseCaseConfig
        public /* synthetic */ int getSurfaceOccupancyPriority() {
            return androidx.camera.core.impl.h0.k(this);
        }

        @Override // androidx.camera.core.impl.UseCaseConfig
        public /* synthetic */ int getSurfaceOccupancyPriority(int i2) {
            return androidx.camera.core.impl.h0.l(this, i2);
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
            return androidx.camera.core.impl.h0.m(this);
        }

        @Override // androidx.camera.core.impl.UseCaseConfig
        public /* synthetic */ Range getTargetFramerate(Range range) {
            return androidx.camera.core.impl.h0.n(this, range);
        }

        @Override // androidx.camera.core.internal.TargetConfig
        public /* synthetic */ String getTargetName() {
            return androidx.camera.core.internal.d.c(this);
        }

        @Override // androidx.camera.core.internal.TargetConfig
        public /* synthetic */ String getTargetName(String str) {
            return androidx.camera.core.internal.d.d(this, str);
        }

        @Override // androidx.camera.core.internal.UseCaseEventConfig
        public /* synthetic */ UseCase.EventCallback getUseCaseEventCallback() {
            return androidx.camera.core.internal.f.a(this);
        }

        @Override // androidx.camera.core.internal.UseCaseEventConfig
        public /* synthetic */ UseCase.EventCallback getUseCaseEventCallback(UseCase.EventCallback eventCallback) {
            return androidx.camera.core.internal.f.b(this, eventCallback);
        }

        @Override // androidx.camera.core.impl.UseCaseConfig
        public /* synthetic */ boolean isZslDisabled(boolean z2) {
            return androidx.camera.core.impl.h0.o(this, z2);
        }

        @Override // androidx.camera.core.impl.ReadableConfig, androidx.camera.core.impl.Config
        public /* synthetic */ Set listOptions() {
            return androidx.camera.core.impl.d0.e(this);
        }

        @Override // androidx.camera.core.impl.ReadableConfig, androidx.camera.core.impl.Config
        public /* synthetic */ Object retrieveOption(Config.Option option) {
            return androidx.camera.core.impl.d0.f(this, option);
        }

        @Override // androidx.camera.core.impl.ReadableConfig, androidx.camera.core.impl.Config
        public /* synthetic */ Object retrieveOption(Config.Option option, Object obj) {
            return androidx.camera.core.impl.d0.g(this, option, obj);
        }

        @Override // androidx.camera.core.impl.ReadableConfig, androidx.camera.core.impl.Config
        public /* synthetic */ Object retrieveOptionWithPriority(Config.Option option, Config.OptionPriority optionPriority) {
            return androidx.camera.core.impl.d0.h(this, option, optionPriority);
        }
    }

    public MeteringRepeatingSession(@NonNull CameraCharacteristicsCompat cameraCharacteristicsCompat, @NonNull DisplayInfoManager displayInfoManager) {
        MeteringRepeatingConfig meteringRepeatingConfig = new MeteringRepeatingConfig();
        this.mConfigWithDefaults = meteringRepeatingConfig;
        final SurfaceTexture surfaceTexture = new SurfaceTexture(0);
        Size properPreviewSize = getProperPreviewSize(cameraCharacteristicsCompat, displayInfoManager);
        Logger.d(TAG, "MeteringSession SurfaceTexture size: " + properPreviewSize);
        surfaceTexture.setDefaultBufferSize(properPreviewSize.getWidth(), properPreviewSize.getHeight());
        final Surface surface = new Surface(surfaceTexture);
        SessionConfig.Builder builderCreateFrom = SessionConfig.Builder.createFrom(meteringRepeatingConfig);
        builderCreateFrom.setTemplateType(1);
        ImmediateSurface immediateSurface = new ImmediateSurface(surface);
        this.mDeferrableSurface = immediateSurface;
        Futures.addCallback(immediateSurface.getTerminationFuture(), new FutureCallback<Void>() { // from class: androidx.camera.camera2.internal.MeteringRepeatingSession.1
            @Override // androidx.camera.core.impl.utils.futures.FutureCallback
            public void onFailure(@NonNull Throwable th) {
                throw new IllegalStateException("Future should never fail. Did it get completed by GC?", th);
            }

            @Override // androidx.camera.core.impl.utils.futures.FutureCallback
            public void onSuccess(@Nullable Void r12) {
                surface.release();
                surfaceTexture.release();
            }
        }, CameraXExecutors.directExecutor());
        builderCreateFrom.addSurface(this.mDeferrableSurface);
        this.mSessionConfig = builderCreateFrom.build();
    }

    @NonNull
    private Size getProperPreviewSize(@NonNull CameraCharacteristicsCompat cameraCharacteristicsCompat, @NonNull DisplayInfoManager displayInfoManager) {
        StreamConfigurationMap streamConfigurationMap = (StreamConfigurationMap) cameraCharacteristicsCompat.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
        if (streamConfigurationMap == null) {
            Logger.e(TAG, "Can not retrieve SCALER_STREAM_CONFIGURATION_MAP.");
            return new Size(0, 0);
        }
        Size[] outputSizes = streamConfigurationMap.getOutputSizes(34);
        if (outputSizes == null) {
            Logger.e(TAG, "Can not get output size list.");
            return new Size(0, 0);
        }
        Size[] supportedSizes = this.mSupportedRepeatingSurfaceSize.getSupportedSizes(outputSizes);
        List listAsList = Arrays.asList(supportedSizes);
        Collections.sort(listAsList, new Comparator() { // from class: androidx.camera.camera2.internal.q1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return MeteringRepeatingSession.lambda$getProperPreviewSize$0((Size) obj, (Size) obj2);
            }
        });
        Size previewSize = displayInfoManager.getPreviewSize();
        long jMin = Math.min(previewSize.getWidth() * previewSize.getHeight(), 307200L);
        int length = supportedSizes.length;
        Size size = null;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                break;
            }
            Size size2 = supportedSizes[i2];
            long width = size2.getWidth() * size2.getHeight();
            if (width == jMin) {
                return size2;
            }
            if (width <= jMin) {
                i2++;
                size = size2;
            } else if (size != null) {
                return size;
            }
        }
        return (Size) listAsList.get(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$getProperPreviewSize$0(Size size, Size size2) {
        return Long.signum((size.getWidth() * size.getHeight()) - (size2.getWidth() * size2.getHeight()));
    }

    public void clear() {
        Logger.d(TAG, "MeteringRepeating clear!");
        DeferrableSurface deferrableSurface = this.mDeferrableSurface;
        if (deferrableSurface != null) {
            deferrableSurface.close();
        }
        this.mDeferrableSurface = null;
    }

    @NonNull
    public String getName() {
        return TAG;
    }

    @NonNull
    public SessionConfig getSessionConfig() {
        return this.mSessionConfig;
    }

    @NonNull
    public UseCaseConfig<?> getUseCaseConfig() {
        return this.mConfigWithDefaults;
    }
}
