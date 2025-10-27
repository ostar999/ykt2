package com.catchpig.utils;

import android.content.Context;
import android.util.Log;
import cn.hutool.core.text.StrPool;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.catchpig.utils.enums.LEVEL;
import com.nirvana.tools.logger.cache.db.DBHelpTool;
import com.umeng.analytics.pro.am;
import com.umeng.analytics.pro.d;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt___RangesKt;
import kotlin.text.StringsKt__StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u0000 #2\u00020\u0001:\u0002#$B\u0005¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u0004J\u001d\u0010\u000b\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u0004H\u0000¢\u0006\u0002\b\fJ\u0016\u0010\r\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u0004J\u001d\u0010\u000e\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u0004H\u0000¢\u0006\u0002\b\u000fJ\u0012\u0010\u0010\u001a\u00020\u00042\b\b\u0002\u0010\u0011\u001a\u00020\u0012H\u0002J\u0016\u0010\u0013\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u0004J\u001d\u0010\u0014\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u0004H\u0000¢\u0006\u0002\b\u0015J\u000e\u0010\u0016\u001a\u00020\b2\u0006\u0010\u0017\u001a\u00020\u0018J\u0018\u0010\u0019\u001a\u00020\b2\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u0004H\u0002J \u0010\u0019\u001a\u00020\b2\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\t\u001a\u00020\u00042\u0006\u0010\u001c\u001a\u00020\u0004H\u0002J\u000e\u0010\u0005\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u0006J\u0016\u0010\u001d\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u0004J\u001d\u0010\u001e\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u0004H\u0000¢\u0006\u0002\b\u001fJ\u0016\u0010 \u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u0004J\u001d\u0010!\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u0004H\u0000¢\u0006\u0002\b\"R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006%"}, d2 = {"Lcom/catchpig/utils/LogUtils;", "", "()V", "prefixTag", "", "showLineNumber", "", "d", "", "tag", "msg", "dExt", "dExt$utils_release", AliyunLogKey.KEY_EVENT, "eExt", "eExt$utils_release", "getLineNumber", "index", "", am.aC, "iExt", "iExt$utils_release", "init", d.R, "Landroid/content/Context;", com.mobile.auth.BuildConfig.FLAVOR_type, DBHelpTool.RecordEntry.COLUMN_NAME_LEVEL, "Lcom/catchpig/utils/enums/LEVEL;", "message", "v", "vExt", "vExt$utils_release", "w", "wExt", "wExt$utils_release", "Companion", "LogUtilsHolder", "utils_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class LogUtils {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    private static final int INDEX_EXT = 6;

    @NotNull
    private static final String SEPARATOR = "*";

    @NotNull
    private String prefixTag = "kotlin-mvvm";
    private boolean showLineNumber;

    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0007\u001a\u00020\bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lcom/catchpig/utils/LogUtils$Companion;", "", "()V", "INDEX_EXT", "", "SEPARATOR", "", "getInstance", "Lcom/catchpig/utils/LogUtils;", "utils_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final LogUtils getInstance() {
            return LogUtilsHolder.INSTANCE.getHolder();
        }
    }

    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÂ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lcom/catchpig/utils/LogUtils$LogUtilsHolder;", "", "()V", "holder", "Lcom/catchpig/utils/LogUtils;", "getHolder", "()Lcom/catchpig/utils/LogUtils;", "utils_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class LogUtilsHolder {

        @NotNull
        public static final LogUtilsHolder INSTANCE = new LogUtilsHolder();

        @NotNull
        private static final LogUtils holder = new LogUtils();

        private LogUtilsHolder() {
        }

        @NotNull
        public final LogUtils getHolder() {
            return holder;
        }
    }

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[LEVEL.values().length];
            try {
                iArr[LEVEL.V.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[LEVEL.D.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[LEVEL.I.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[LEVEL.W.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[LEVEL.E.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private final String getLineNumber(int index) {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[index];
        String className = stackTraceElement.getClassName();
        Intrinsics.checkNotNullExpressionValue(className, "className");
        Intrinsics.checkNotNullExpressionValue(className, "className");
        String strSubstring = className.substring(StringsKt__StringsKt.lastIndexOf$default((CharSequence) className, StrPool.DOT, 0, false, 6, (Object) null) + 1);
        Intrinsics.checkNotNullExpressionValue(strSubstring, "this as java.lang.String).substring(startIndex)");
        return strSubstring + ':' + stackTraceElement.getMethodName() + "(line:" + stackTraceElement.getLineNumber() + ')';
    }

    public static /* synthetic */ String getLineNumber$default(LogUtils logUtils, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i2 = 5;
        }
        return logUtils.getLineNumber(i2);
    }

    private final void log(LEVEL level, String tag, String message) {
        if (!this.showLineNumber) {
            log(level, tag + ':' + message);
            return;
        }
        String str = tag + ':' + message;
        int length = str.length();
        String lineNumber = getLineNumber(6);
        int length2 = lineNumber.length();
        int iCoerceAtLeast = RangesKt___RangesKt.coerceAtLeast(length, length2) + 3;
        String str2 = "";
        if (iCoerceAtLeast >= 0) {
            int i2 = 0;
            while (true) {
                str2 = str2 + '*';
                if (i2 == iCoerceAtLeast) {
                    break;
                } else {
                    i2++;
                }
            }
        }
        for (int i3 = 0; i3 < iCoerceAtLeast - length2; i3++) {
            lineNumber = lineNumber + ' ';
        }
        String str3 = lineNumber + '*';
        for (int i4 = 0; i4 < iCoerceAtLeast - length; i4++) {
            str = str + ' ';
        }
        log(level, str2);
        log(level, str3);
        log(level, str + '*');
        log(level, str2);
    }

    public final void d(@NotNull String tag, @NotNull String msg) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(msg, "msg");
        log(LEVEL.D, tag, msg);
    }

    public final void dExt$utils_release(@NotNull String tag, @NotNull String msg) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(msg, "msg");
        log(LEVEL.D, tag, msg);
    }

    public final void e(@NotNull String tag, @NotNull String msg) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(msg, "msg");
        log(LEVEL.D, tag, msg);
    }

    public final void eExt$utils_release(@NotNull String tag, @NotNull String msg) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(msg, "msg");
        log(LEVEL.D, tag, msg);
    }

    public final void i(@NotNull String tag, @NotNull String msg) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(msg, "msg");
        log(LEVEL.I, tag, msg);
    }

    public final void iExt$utils_release(@NotNull String tag, @NotNull String msg) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(msg, "msg");
        log(LEVEL.I, tag, msg);
    }

    public final void init(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        String packageName = context.getPackageName();
        Intrinsics.checkNotNullExpressionValue(packageName, "context.packageName");
        this.prefixTag = packageName;
        this.showLineNumber = this.showLineNumber;
    }

    public final void showLineNumber(boolean showLineNumber) {
        this.showLineNumber = showLineNumber;
    }

    public final void v(@NotNull String tag, @NotNull String msg) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(msg, "msg");
        log(LEVEL.V, tag, msg);
    }

    public final void vExt$utils_release(@NotNull String tag, @NotNull String msg) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(msg, "msg");
        log(LEVEL.V, tag, msg);
    }

    public final void w(@NotNull String tag, @NotNull String msg) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(msg, "msg");
        log(LEVEL.W, tag, msg);
    }

    public final void wExt$utils_release(@NotNull String tag, @NotNull String msg) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(msg, "msg");
        log(LEVEL.W, tag, msg);
    }

    private final void log(LEVEL level, String message) {
        int i2 = WhenMappings.$EnumSwitchMapping$0[level.ordinal()];
        if (i2 == 1) {
            Log.v(this.prefixTag, message);
            return;
        }
        if (i2 == 2) {
            Log.d(this.prefixTag, message);
            return;
        }
        if (i2 == 3) {
            Log.i(this.prefixTag, message);
        } else if (i2 == 4) {
            Log.w(this.prefixTag, message);
        } else {
            if (i2 != 5) {
                return;
            }
            Log.e(this.prefixTag, message);
        }
    }
}
