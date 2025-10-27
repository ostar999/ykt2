package androidx.camera.core.impl;

import android.util.Range;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.UseCase;
import androidx.camera.core.impl.CaptureConfig;
import androidx.camera.core.impl.SessionConfig;

/* loaded from: classes.dex */
public final /* synthetic */ class h0<T extends UseCase> {
    @NonNull
    public static CameraSelector a(UseCaseConfig useCaseConfig) {
        return (CameraSelector) useCaseConfig.retrieveOption(UseCaseConfig.OPTION_CAMERA_SELECTOR);
    }

    @Nullable
    public static CameraSelector b(UseCaseConfig useCaseConfig, @Nullable CameraSelector cameraSelector) {
        return (CameraSelector) useCaseConfig.retrieveOption(UseCaseConfig.OPTION_CAMERA_SELECTOR, cameraSelector);
    }

    @NonNull
    public static CaptureConfig.OptionUnpacker c(UseCaseConfig useCaseConfig) {
        return (CaptureConfig.OptionUnpacker) useCaseConfig.retrieveOption(UseCaseConfig.OPTION_CAPTURE_CONFIG_UNPACKER);
    }

    @Nullable
    public static CaptureConfig.OptionUnpacker d(UseCaseConfig useCaseConfig, @Nullable CaptureConfig.OptionUnpacker optionUnpacker) {
        return (CaptureConfig.OptionUnpacker) useCaseConfig.retrieveOption(UseCaseConfig.OPTION_CAPTURE_CONFIG_UNPACKER, optionUnpacker);
    }

    @NonNull
    public static CaptureConfig e(UseCaseConfig useCaseConfig) {
        return (CaptureConfig) useCaseConfig.retrieveOption(UseCaseConfig.OPTION_DEFAULT_CAPTURE_CONFIG);
    }

    @Nullable
    public static CaptureConfig f(UseCaseConfig useCaseConfig, @Nullable CaptureConfig captureConfig) {
        return (CaptureConfig) useCaseConfig.retrieveOption(UseCaseConfig.OPTION_DEFAULT_CAPTURE_CONFIG, captureConfig);
    }

    @NonNull
    public static SessionConfig g(UseCaseConfig useCaseConfig) {
        return (SessionConfig) useCaseConfig.retrieveOption(UseCaseConfig.OPTION_DEFAULT_SESSION_CONFIG);
    }

    @Nullable
    public static SessionConfig h(UseCaseConfig useCaseConfig, @Nullable SessionConfig sessionConfig) {
        return (SessionConfig) useCaseConfig.retrieveOption(UseCaseConfig.OPTION_DEFAULT_SESSION_CONFIG, sessionConfig);
    }

    @NonNull
    public static SessionConfig.OptionUnpacker i(UseCaseConfig useCaseConfig) {
        return (SessionConfig.OptionUnpacker) useCaseConfig.retrieveOption(UseCaseConfig.OPTION_SESSION_CONFIG_UNPACKER);
    }

    @Nullable
    public static SessionConfig.OptionUnpacker j(UseCaseConfig useCaseConfig, @Nullable SessionConfig.OptionUnpacker optionUnpacker) {
        return (SessionConfig.OptionUnpacker) useCaseConfig.retrieveOption(UseCaseConfig.OPTION_SESSION_CONFIG_UNPACKER, optionUnpacker);
    }

    public static int k(UseCaseConfig useCaseConfig) {
        return ((Integer) useCaseConfig.retrieveOption(UseCaseConfig.OPTION_SURFACE_OCCUPANCY_PRIORITY)).intValue();
    }

    public static int l(UseCaseConfig useCaseConfig, int i2) {
        return ((Integer) useCaseConfig.retrieveOption(UseCaseConfig.OPTION_SURFACE_OCCUPANCY_PRIORITY, Integer.valueOf(i2))).intValue();
    }

    @NonNull
    public static Range m(UseCaseConfig useCaseConfig) {
        return (Range) useCaseConfig.retrieveOption(UseCaseConfig.OPTION_TARGET_FRAME_RATE);
    }

    @Nullable
    public static Range n(UseCaseConfig useCaseConfig, @Nullable Range range) {
        return (Range) useCaseConfig.retrieveOption(UseCaseConfig.OPTION_TARGET_FRAME_RATE, range);
    }

    public static boolean o(UseCaseConfig useCaseConfig, boolean z2) {
        return ((Boolean) useCaseConfig.retrieveOption(UseCaseConfig.OPTION_ZSL_DISABLED, Boolean.valueOf(z2))).booleanValue();
    }
}
