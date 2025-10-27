package androidx.camera.core.internal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* loaded from: classes.dex */
public final /* synthetic */ class d<T> {
    @NonNull
    public static Class a(TargetConfig targetConfig) {
        return (Class) targetConfig.retrieveOption(TargetConfig.OPTION_TARGET_CLASS);
    }

    @Nullable
    public static Class b(TargetConfig targetConfig, @Nullable Class cls) {
        return (Class) targetConfig.retrieveOption(TargetConfig.OPTION_TARGET_CLASS, cls);
    }

    @NonNull
    public static String c(TargetConfig targetConfig) {
        return (String) targetConfig.retrieveOption(TargetConfig.OPTION_TARGET_NAME);
    }

    @Nullable
    public static String d(TargetConfig targetConfig, @Nullable String str) {
        return (String) targetConfig.retrieveOption(TargetConfig.OPTION_TARGET_NAME, str);
    }
}
