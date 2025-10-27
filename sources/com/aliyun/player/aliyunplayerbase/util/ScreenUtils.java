package com.aliyun.player.aliyunplayerbase.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

/* loaded from: classes2.dex */
public class ScreenUtils {
    public static int getHeight(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getApplicationContext().getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public static int getNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        return resources.getDimensionPixelSize(resources.getIdentifier("navigation_bar_height", "dimen", "android"));
    }

    public static int getWidth(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getApplicationContext().getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static boolean isInLeft(Context context, int i2) {
        return i2 < getWidth(context) / 2;
    }

    public static boolean isInRight(Context context, int i2) {
        return i2 > getWidth(context) / 2;
    }

    public static boolean isInLeft(View view, int i2) {
        return i2 < view.getMeasuredWidth() / 2;
    }

    public static boolean isInRight(View view, int i2) {
        return i2 > view.getMeasuredWidth() / 2;
    }
}
