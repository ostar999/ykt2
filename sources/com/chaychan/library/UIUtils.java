package com.chaychan.library;

import android.content.Context;
import android.graphics.drawable.Drawable;

/* loaded from: classes3.dex */
public class UIUtils {
    public static int dip2Px(Context context, int i2) {
        return (int) ((i2 * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int getColor(Context context, int i2) {
        return context.getResources().getColor(i2);
    }

    public static Drawable getDrawable(Context context, int i2) {
        return context.getResources().getDrawable(i2);
    }

    public static int sp2px(Context context, float f2) {
        return (int) ((f2 * context.getResources().getDisplayMetrics().scaledDensity) + 0.5f);
    }
}
