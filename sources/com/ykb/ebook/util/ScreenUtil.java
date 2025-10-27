package com.ykb.ebook.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Insets;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowInsets;
import android.view.WindowMetrics;

/* loaded from: classes7.dex */
public class ScreenUtil {
    public static int getDpByPx(Context context, int i2) {
        return (int) ((i2 / context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int getNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        int identifier = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (identifier > 0) {
            return resources.getDimensionPixelSize(identifier);
        }
        return 0;
    }

    public static int getPxByDp(Context context, int i2) {
        return (int) TypedValue.applyDimension(1, i2, context.getResources().getDisplayMetrics());
    }

    public static int getPxByDpF(Context context, float f2) {
        return (int) TypedValue.applyDimension(1, f2, context.getResources().getDisplayMetrics());
    }

    public static int getPxBySp(Context context, int i2) {
        return (int) TypedValue.applyDimension(2, i2, context.getResources().getDisplayMetrics());
    }

    public static float getScreenDensity(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.density;
    }

    public static int getScreenHeight(Activity activity) {
        if (Build.VERSION.SDK_INT >= 30) {
            WindowMetrics currentWindowMetrics = activity.getWindowManager().getCurrentWindowMetrics();
            Insets insetsIgnoringVisibility = currentWindowMetrics.getWindowInsets().getInsetsIgnoringVisibility(WindowInsets.Type.systemBars());
            return (currentWindowMetrics.getBounds().height() - insetsIgnoringVisibility.top) - insetsIgnoringVisibility.bottom;
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public static float getScreenPPI(Activity activity) {
        activity.getWindowManager().getDefaultDisplay().getMetrics(new DisplayMetrics());
        return r0.densityDpi;
    }

    public static int getScreenWidth(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static void initScreenPara(Activity activity) {
        getScreenWidth(activity);
        getScreenHeight(activity);
        getScreenDensity(activity);
        getScreenPPI(activity);
    }

    public static boolean isTablet(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.sqrt(Math.pow((double) (((float) displayMetrics.widthPixels) / displayMetrics.xdpi), 2.0d) + Math.pow((double) (((float) displayMetrics.heightPixels) / displayMetrics.ydpi), 2.0d)) >= 7.0d;
    }

    public static int px2sp(Context context, float f2) {
        return (int) ((f2 / context.getResources().getDisplayMetrics().scaledDensity) + 0.5f);
    }

    public static float pxToSp(Context context, Float f2) {
        return f2.floatValue() / context.getResources().getDisplayMetrics().scaledDensity;
    }

    public static int sp2px(Context context, float f2) {
        return (int) ((f2 * context.getResources().getDisplayMetrics().scaledDensity) + 0.5f);
    }

    public static int getPxByDp(Context context, float f2) {
        return (int) TypedValue.applyDimension(1, f2, context.getResources().getDisplayMetrics());
    }
}
