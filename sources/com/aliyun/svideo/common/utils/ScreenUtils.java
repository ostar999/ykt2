package com.aliyun.svideo.common.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/* loaded from: classes2.dex */
public class ScreenUtils {
    public static int getHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int getNavigationHeight(Context context) {
        Resources resources;
        int identifier;
        if (context != null && (identifier = (resources = context.getResources()).getIdentifier("navigation_bar_height", "dimen", "android")) > 0) {
            return resources.getDimensionPixelSize(identifier);
        }
        return 0;
    }

    public static int getRealHeight(Context context) {
        Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getRealMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public static int getRealWidth(Context context) {
        Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getRealMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static Point getScreenPoint(Context context) {
        Point point = new Point();
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        point.x = displayMetrics.widthPixels;
        point.y = displayMetrics.heightPixels;
        return point;
    }

    public static int getWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static boolean isInLeft(Context context, int i2) {
        return i2 < getWidth(context) / 2;
    }

    public static boolean isInRight(Context context, int i2) {
        return i2 > getWidth(context) / 2;
    }
}
