package com.ykb.ebook.common;

import android.annotation.SuppressLint;
import com.catchpig.mvvm.utils.DateUtil;
import com.yikaobang.yixue.BuildConfig;
import com.ykb.common_share_lib.CommonConfig;
import java.text.SimpleDateFormat;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\"\u001a\u0010\u0000\u001a\u00020\u0001X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0002\u0010\u0003\"\u0004\b\u0004\u0010\u0005\"\u001a\u0010\u0006\u001a\u00020\u0001X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\u0003\"\u0004\b\b\u0010\u0005\"\u001a\u0010\t\u001a\u00020\u0001X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0003\"\u0004\b\u000b\u0010\u0005\"\u000e\u0010\f\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u0014\u0010\r\u001a\u00020\u000eX\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0016\u0010\u0011\u001a\u00020\u00128\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014¨\u0006\u0015"}, d2 = {"BASE_URL", "", "getBASE_URL", "()Ljava/lang/String;", "setBASE_URL", "(Ljava/lang/String;)V", "BOOK_MANAGE_URL", "getBOOK_MANAGE_URL", "setBOOK_MANAGE_URL", "BOOK_SHARE_URL", "getBOOK_SHARE_URL", "setBOOK_SHARE_URL", "FILE_PROVIDER_AUTHORITY", "IS_DEBUG", "", "getIS_DEBUG", "()Z", "timeFormat", "Ljava/text/SimpleDateFormat;", "getTimeFormat", "()Ljava/text/SimpleDateFormat;", "ebook_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class ConstantKt {

    @NotNull
    private static String BASE_URL = null;

    @NotNull
    private static String BOOK_MANAGE_URL = null;

    @NotNull
    private static String BOOK_SHARE_URL = null;

    @NotNull
    public static final String FILE_PROVIDER_AUTHORITY = "com.yikaobang.yixue.fileProvider";
    private static final boolean IS_DEBUG = false;

    @SuppressLint({"SimpleDateFormat"})
    @NotNull
    private static final SimpleDateFormat timeFormat;

    static {
        CommonConfig commonConfig = CommonConfig.INSTANCE;
        BASE_URL = commonConfig.getYI_KAO_BANG_NETWORK() == 0 ? BuildConfig.TEST_API_URL : BuildConfig.RELEASE_API_URL;
        BOOK_MANAGE_URL = commonConfig.getYI_KAO_BANG_NETWORK() == 0 ? BuildConfig.TEST_API_URL_NEW : BuildConfig.RELEASE_API_URL_NEW;
        BOOK_SHARE_URL = commonConfig.getYI_KAO_BANG_NETWORK() == 0 ? "http://test.share.yikaobang.com.cn/" : "https://share.yikaobang.com.cn/";
        timeFormat = new SimpleDateFormat(DateUtil.TIME_FORMAT_WITH_HM);
    }

    @NotNull
    public static final String getBASE_URL() {
        return BASE_URL;
    }

    @NotNull
    public static final String getBOOK_MANAGE_URL() {
        return BOOK_MANAGE_URL;
    }

    @NotNull
    public static final String getBOOK_SHARE_URL() {
        return BOOK_SHARE_URL;
    }

    public static final boolean getIS_DEBUG() {
        return IS_DEBUG;
    }

    @NotNull
    public static final SimpleDateFormat getTimeFormat() {
        return timeFormat;
    }

    public static final void setBASE_URL(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        BASE_URL = str;
    }

    public static final void setBOOK_MANAGE_URL(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        BOOK_MANAGE_URL = str;
    }

    public static final void setBOOK_SHARE_URL(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        BOOK_SHARE_URL = str;
    }
}
