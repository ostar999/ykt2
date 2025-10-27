package com.luck.lib.camerax.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/* loaded from: classes4.dex */
public class DensityUtil {
    public static int dip2px(Context context, float f2) {
        return (int) ((f2 * context.getApplicationContext().getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int getScreenHeight(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }
}
