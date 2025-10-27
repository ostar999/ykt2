package com.opensource.svgaplayer.utils.log;

import com.aliyun.vod.log.core.AliyunLogCommon;
import com.opensource.svgaplayer.BuildConfig;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0003\n\u0002\b\u0004\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0004J\u0018\u0010\t\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0004J \u0010\t\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\nJ\u0016\u0010\t\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\nJ\u0018\u0010\u000b\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0004J\u0018\u0010\f\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0004J\u0018\u0010\r\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lcom/opensource/svgaplayer/utils/log/LogUtils;", "", "()V", "TAG", "", "debug", "", "tag", "msg", "error", "", AliyunLogCommon.LogLevel.INFO, "verbose", AliyunLogCommon.LogLevel.WARN, BuildConfig.LIBRARY_PACKAGE_NAME}, k = 1, mv = {1, 1, 15})
/* loaded from: classes4.dex */
public final class LogUtils {
    public static final LogUtils INSTANCE = new LogUtils();
    private static final String TAG = "SVGALog";

    private LogUtils() {
    }

    public static /* synthetic */ void debug$default(LogUtils logUtils, String str, String str2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = TAG;
        }
        logUtils.debug(str, str2);
    }

    public static /* synthetic */ void error$default(LogUtils logUtils, String str, String str2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = TAG;
        }
        logUtils.error(str, str2);
    }

    public static /* synthetic */ void info$default(LogUtils logUtils, String str, String str2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = TAG;
        }
        logUtils.info(str, str2);
    }

    public static /* synthetic */ void verbose$default(LogUtils logUtils, String str, String str2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = TAG;
        }
        logUtils.verbose(str, str2);
    }

    public static /* synthetic */ void warn$default(LogUtils logUtils, String str, String str2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = TAG;
        }
        logUtils.warn(str, str2);
    }

    public final void debug(@NotNull String tag, @NotNull String msg) {
        ILogger sVGALogger;
        Intrinsics.checkParameterIsNotNull(tag, "tag");
        Intrinsics.checkParameterIsNotNull(msg, "msg");
        SVGALogger sVGALogger2 = SVGALogger.INSTANCE;
        if (sVGALogger2.isLogEnabled() && (sVGALogger = sVGALogger2.getSVGALogger()) != null) {
            sVGALogger.debug(tag, msg);
        }
    }

    public final void error(@NotNull String tag, @NotNull String msg) {
        ILogger sVGALogger;
        Intrinsics.checkParameterIsNotNull(tag, "tag");
        Intrinsics.checkParameterIsNotNull(msg, "msg");
        SVGALogger sVGALogger2 = SVGALogger.INSTANCE;
        if (sVGALogger2.isLogEnabled() && (sVGALogger = sVGALogger2.getSVGALogger()) != null) {
            sVGALogger.error(tag, msg, null);
        }
    }

    public final void info(@NotNull String tag, @NotNull String msg) {
        ILogger sVGALogger;
        Intrinsics.checkParameterIsNotNull(tag, "tag");
        Intrinsics.checkParameterIsNotNull(msg, "msg");
        SVGALogger sVGALogger2 = SVGALogger.INSTANCE;
        if (sVGALogger2.isLogEnabled() && (sVGALogger = sVGALogger2.getSVGALogger()) != null) {
            sVGALogger.info(tag, msg);
        }
    }

    public final void verbose(@NotNull String tag, @NotNull String msg) {
        ILogger sVGALogger;
        Intrinsics.checkParameterIsNotNull(tag, "tag");
        Intrinsics.checkParameterIsNotNull(msg, "msg");
        SVGALogger sVGALogger2 = SVGALogger.INSTANCE;
        if (sVGALogger2.isLogEnabled() && (sVGALogger = sVGALogger2.getSVGALogger()) != null) {
            sVGALogger.verbose(tag, msg);
        }
    }

    public final void warn(@NotNull String tag, @NotNull String msg) {
        ILogger sVGALogger;
        Intrinsics.checkParameterIsNotNull(tag, "tag");
        Intrinsics.checkParameterIsNotNull(msg, "msg");
        SVGALogger sVGALogger2 = SVGALogger.INSTANCE;
        if (sVGALogger2.isLogEnabled() && (sVGALogger = sVGALogger2.getSVGALogger()) != null) {
            sVGALogger.warn(tag, msg);
        }
    }

    public static /* synthetic */ void error$default(LogUtils logUtils, String str, String str2, Throwable th, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = TAG;
        }
        logUtils.error(str, str2, th);
    }

    public final void error(@NotNull String tag, @NotNull Throwable error) {
        ILogger sVGALogger;
        Intrinsics.checkParameterIsNotNull(tag, "tag");
        Intrinsics.checkParameterIsNotNull(error, "error");
        SVGALogger sVGALogger2 = SVGALogger.INSTANCE;
        if (sVGALogger2.isLogEnabled() && (sVGALogger = sVGALogger2.getSVGALogger()) != null) {
            sVGALogger.error(tag, error.getMessage(), error);
        }
    }

    public final void error(@NotNull String tag, @NotNull String msg, @NotNull Throwable error) {
        ILogger sVGALogger;
        Intrinsics.checkParameterIsNotNull(tag, "tag");
        Intrinsics.checkParameterIsNotNull(msg, "msg");
        Intrinsics.checkParameterIsNotNull(error, "error");
        SVGALogger sVGALogger2 = SVGALogger.INSTANCE;
        if (sVGALogger2.isLogEnabled() && (sVGALogger = sVGALogger2.getSVGALogger()) != null) {
            sVGALogger.error(tag, msg, error);
        }
    }
}
