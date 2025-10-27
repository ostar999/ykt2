package com.azhon.appupdate.config;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\b\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u001c\u0010\n\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\u0006\"\u0004\b\f\u0010\bR\u000e\u0010\r\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0011X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lcom/azhon/appupdate/config/Constant;", "", "()V", "APK_PATH", "", "getAPK_PATH", "()Ljava/lang/String;", "setAPK_PATH", "(Ljava/lang/String;)V", "APK_SUFFIX", "AUTHORITIES", "getAUTHORITIES", "setAUTHORITIES", "COROUTINE_NAME", "DEFAULT_CHANNEL_ID", "DEFAULT_CHANNEL_NAME", "DEFAULT_NOTIFY_ID", "", "HTTP_TIME_OUT", "TAG", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class Constant {

    @NotNull
    public static final String APK_SUFFIX = ".apk";

    @Nullable
    private static String AUTHORITIES = null;

    @NotNull
    public static final String COROUTINE_NAME = "app-update-coroutine";

    @NotNull
    public static final String DEFAULT_CHANNEL_ID = "appUpdate";

    @NotNull
    public static final String DEFAULT_CHANNEL_NAME = "AppUpdate";
    public static final int DEFAULT_NOTIFY_ID = 1011;
    public static final int HTTP_TIME_OUT = 30000;

    @NotNull
    public static final String TAG = "AppUpdate.";

    @NotNull
    public static final Constant INSTANCE = new Constant();

    @NotNull
    private static String APK_PATH = "/storage/emulated/0/Android/data/%s/cache";

    private Constant() {
    }

    @NotNull
    public final String getAPK_PATH() {
        return APK_PATH;
    }

    @Nullable
    public final String getAUTHORITIES() {
        return AUTHORITIES;
    }

    public final void setAPK_PATH(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        APK_PATH = str;
    }

    public final void setAUTHORITIES(@Nullable String str) {
        AUTHORITIES = str;
    }
}
