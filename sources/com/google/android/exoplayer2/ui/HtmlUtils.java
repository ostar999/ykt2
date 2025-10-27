package com.google.android.exoplayer2.ui;

import android.graphics.Color;
import androidx.annotation.ColorInt;
import cn.hutool.core.text.StrPool;
import com.google.android.exoplayer2.util.Util;

/* loaded from: classes3.dex */
final class HtmlUtils {
    private HtmlUtils() {
    }

    public static String cssAllClassDescendantsSelector(String str) {
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 5 + String.valueOf(str).length());
        sb.append(StrPool.DOT);
        sb.append(str);
        sb.append(",.");
        sb.append(str);
        sb.append(" *");
        return sb.toString();
    }

    public static String toCssRgba(@ColorInt int i2) {
        return Util.formatInvariant("rgba(%d,%d,%d,%.3f)", Integer.valueOf(Color.red(i2)), Integer.valueOf(Color.green(i2)), Integer.valueOf(Color.blue(i2)), Double.valueOf(Color.alpha(i2) / 255.0d));
    }
}
