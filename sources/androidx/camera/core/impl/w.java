package androidx.camera.core.impl;

import android.util.Size;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;

/* loaded from: classes.dex */
public final /* synthetic */ class w {
    public static int a(ImageOutputConfig imageOutputConfig, int i2) {
        return ((Integer) imageOutputConfig.retrieveOption(ImageOutputConfig.OPTION_APP_TARGET_ROTATION, Integer.valueOf(i2))).intValue();
    }

    @NonNull
    public static Size b(ImageOutputConfig imageOutputConfig) {
        return (Size) imageOutputConfig.retrieveOption(ImageOutputConfig.OPTION_DEFAULT_RESOLUTION);
    }

    @Nullable
    public static Size c(ImageOutputConfig imageOutputConfig, @Nullable Size size) {
        return (Size) imageOutputConfig.retrieveOption(ImageOutputConfig.OPTION_DEFAULT_RESOLUTION, size);
    }

    @NonNull
    public static Size d(ImageOutputConfig imageOutputConfig) {
        return (Size) imageOutputConfig.retrieveOption(ImageOutputConfig.OPTION_MAX_RESOLUTION);
    }

    @Nullable
    public static Size e(ImageOutputConfig imageOutputConfig, @Nullable Size size) {
        return (Size) imageOutputConfig.retrieveOption(ImageOutputConfig.OPTION_MAX_RESOLUTION, size);
    }

    @NonNull
    public static List f(ImageOutputConfig imageOutputConfig) {
        return (List) imageOutputConfig.retrieveOption(ImageOutputConfig.OPTION_SUPPORTED_RESOLUTIONS);
    }

    @Nullable
    public static List g(ImageOutputConfig imageOutputConfig, @Nullable List list) {
        return (List) imageOutputConfig.retrieveOption(ImageOutputConfig.OPTION_SUPPORTED_RESOLUTIONS, list);
    }

    public static int h(ImageOutputConfig imageOutputConfig) {
        return ((Integer) imageOutputConfig.retrieveOption(ImageOutputConfig.OPTION_TARGET_ASPECT_RATIO)).intValue();
    }

    @NonNull
    public static Size i(ImageOutputConfig imageOutputConfig) {
        return (Size) imageOutputConfig.retrieveOption(ImageOutputConfig.OPTION_TARGET_RESOLUTION);
    }

    @Nullable
    public static Size j(ImageOutputConfig imageOutputConfig, @Nullable Size size) {
        return (Size) imageOutputConfig.retrieveOption(ImageOutputConfig.OPTION_TARGET_RESOLUTION, size);
    }

    public static int k(ImageOutputConfig imageOutputConfig) {
        return ((Integer) imageOutputConfig.retrieveOption(ImageOutputConfig.OPTION_TARGET_ROTATION)).intValue();
    }

    public static int l(ImageOutputConfig imageOutputConfig, int i2) {
        return ((Integer) imageOutputConfig.retrieveOption(ImageOutputConfig.OPTION_TARGET_ROTATION, Integer.valueOf(i2))).intValue();
    }

    public static boolean m(ImageOutputConfig imageOutputConfig) {
        return imageOutputConfig.containsOption(ImageOutputConfig.OPTION_TARGET_ASPECT_RATIO);
    }
}
