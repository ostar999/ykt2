package com.hyphenate.easeui.player;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import androidx.annotation.AttrRes;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
class Util {
    public static int adjustAlpha(int i2, float f2) {
        return Color.argb(Math.round(Color.alpha(i2) * f2), Color.red(i2), Color.green(i2), Color.blue(i2));
    }

    public static String getDurationString(long j2, boolean z2) {
        Locale locale = Locale.getDefault();
        Object[] objArr = new Object[3];
        objArr[0] = z2 ? "-" : "";
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        objArr[1] = Long.valueOf(timeUnit.toMinutes(j2));
        objArr[2] = Long.valueOf(timeUnit.toSeconds(j2) - TimeUnit.MINUTES.toSeconds(timeUnit.toMinutes(j2)));
        return String.format(locale, "%s%02d:%02d", objArr);
    }

    public static boolean isColorDark(int i2) {
        return 1.0d - ((((((double) Color.red(i2)) * 0.299d) + (((double) Color.green(i2)) * 0.587d)) + (((double) Color.blue(i2)) * 0.114d)) / 255.0d) >= 0.5d;
    }

    public static int resolveColor(Context context, @AttrRes int i2) {
        return resolveColor(context, i2, 0);
    }

    private static int resolveColor(Context context, @AttrRes int i2, int i3) {
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(new int[]{i2});
        try {
            return typedArrayObtainStyledAttributes.getColor(0, i3);
        } finally {
            typedArrayObtainStyledAttributes.recycle();
        }
    }
}
