package com.ykb.ebook.util;

import com.ykb.ebook.common.ConstantKt;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006J\u0016\u0010\b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006J\u0016\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006J\u0016\u0010\n\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006J\u0016\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006¨\u0006\f"}, d2 = {"Lcom/ykb/ebook/util/Log;", "", "()V", "logD", "", "tag", "", "msg", "logE", "logI", "logV", "logW", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class Log {

    @NotNull
    public static final Log INSTANCE = new Log();

    private Log() {
    }

    public final void logD(@NotNull String tag, @NotNull String msg) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(msg, "msg");
        if (ConstantKt.getIS_DEBUG()) {
            int length = msg.length();
            int i2 = 200;
            int i3 = 0;
            int i4 = 0;
            while (i3 < 100) {
                if (length <= i2) {
                    String strSubstring = msg.substring(i4, length);
                    Intrinsics.checkNotNullExpressionValue(strSubstring, "this as java.lang.String…ing(startIndex, endIndex)");
                    android.util.Log.d(tag, strSubstring);
                    return;
                } else {
                    String strSubstring2 = msg.substring(i4, i2);
                    Intrinsics.checkNotNullExpressionValue(strSubstring2, "this as java.lang.String…ing(startIndex, endIndex)");
                    android.util.Log.d(tag, strSubstring2);
                    i3++;
                    i4 = i2;
                    i2 += 200;
                }
            }
        }
    }

    public final void logE(@NotNull String tag, @NotNull String msg) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(msg, "msg");
        if (ConstantKt.getIS_DEBUG()) {
            int length = msg.length();
            int i2 = 200;
            int i3 = 0;
            int i4 = 0;
            while (i3 < 100) {
                if (length <= i2) {
                    String strSubstring = msg.substring(i4, length);
                    Intrinsics.checkNotNullExpressionValue(strSubstring, "this as java.lang.String…ing(startIndex, endIndex)");
                    android.util.Log.e(tag, strSubstring);
                    return;
                } else {
                    String strSubstring2 = msg.substring(i4, i2);
                    Intrinsics.checkNotNullExpressionValue(strSubstring2, "this as java.lang.String…ing(startIndex, endIndex)");
                    android.util.Log.e(tag, strSubstring2);
                    i3++;
                    i4 = i2;
                    i2 += 200;
                }
            }
        }
    }

    public final void logI(@NotNull String tag, @NotNull String msg) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(msg, "msg");
        if (ConstantKt.getIS_DEBUG()) {
            int length = msg.length();
            int i2 = 200;
            int i3 = 0;
            int i4 = 0;
            while (i3 < 100) {
                if (length <= i2) {
                    String strSubstring = msg.substring(i4, length);
                    Intrinsics.checkNotNullExpressionValue(strSubstring, "this as java.lang.String…ing(startIndex, endIndex)");
                    android.util.Log.i(tag, strSubstring);
                    return;
                } else {
                    String strSubstring2 = msg.substring(i4, i2);
                    Intrinsics.checkNotNullExpressionValue(strSubstring2, "this as java.lang.String…ing(startIndex, endIndex)");
                    android.util.Log.i(tag, strSubstring2);
                    i3++;
                    i4 = i2;
                    i2 += 200;
                }
            }
        }
    }

    public final void logV(@NotNull String tag, @NotNull String msg) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(msg, "msg");
        if (ConstantKt.getIS_DEBUG()) {
            int length = msg.length();
            int i2 = 200;
            int i3 = 0;
            int i4 = 0;
            while (i3 < 100) {
                if (length <= i2) {
                    String strSubstring = msg.substring(i4, length);
                    Intrinsics.checkNotNullExpressionValue(strSubstring, "this as java.lang.String…ing(startIndex, endIndex)");
                    android.util.Log.v(tag, strSubstring);
                    return;
                } else {
                    String strSubstring2 = msg.substring(i4, i2);
                    Intrinsics.checkNotNullExpressionValue(strSubstring2, "this as java.lang.String…ing(startIndex, endIndex)");
                    android.util.Log.v(tag, strSubstring2);
                    i3++;
                    i4 = i2;
                    i2 += 200;
                }
            }
        }
    }

    public final void logW(@NotNull String tag, @NotNull String msg) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(msg, "msg");
        if (ConstantKt.getIS_DEBUG()) {
            int length = msg.length();
            int i2 = 200;
            int i3 = 0;
            int i4 = 0;
            while (i3 < 100) {
                if (length <= i2) {
                    String strSubstring = msg.substring(i4, length);
                    Intrinsics.checkNotNullExpressionValue(strSubstring, "this as java.lang.String…ing(startIndex, endIndex)");
                    android.util.Log.w(tag, strSubstring);
                    return;
                } else {
                    String strSubstring2 = msg.substring(i4, i2);
                    Intrinsics.checkNotNullExpressionValue(strSubstring2, "this as java.lang.String…ing(startIndex, endIndex)");
                    android.util.Log.w(tag, strSubstring2);
                    i3++;
                    i4 = i2;
                    i2 += 200;
                }
            }
        }
    }
}
