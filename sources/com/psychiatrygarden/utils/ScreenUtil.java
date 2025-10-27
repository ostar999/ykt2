package com.psychiatrygarden.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;

/* loaded from: classes6.dex */
public class ScreenUtil {
    public static int getDpByPx(Context context, int px) {
        return (int) ((px / context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int getPxByDp(Context context, int dp) {
        return (int) TypedValue.applyDimension(1, dp, context.getResources().getDisplayMetrics());
    }

    public static int getPxByDpF(Context context, float dp) {
        return (int) TypedValue.applyDimension(1, dp, context.getResources().getDisplayMetrics());
    }

    public static int getPxBySp(Context context, int sp) {
        return (int) TypedValue.applyDimension(2, sp, context.getResources().getDisplayMetrics());
    }

    public static float getScreenDensity(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.density;
    }

    public static int getScreenHeight(Activity activity) {
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
        double dSqrt = Math.sqrt(Math.pow(displayMetrics.widthPixels / displayMetrics.xdpi, 2.0d) + Math.pow(displayMetrics.heightPixels / displayMetrics.ydpi, 2.0d));
        Log.e("screen_table", "平板：" + dSqrt);
        return dSqrt >= 7.0d;
    }

    public static int px2sp(Context context, float pxValue) {
        return (int) ((pxValue / context.getResources().getDisplayMetrics().scaledDensity) + 0.5f);
    }

    public static float pxToSp(Context context, Float px) {
        return px.floatValue() / context.getResources().getDisplayMetrics().scaledDensity;
    }

    public static int sp2px(Context context, float spValue) {
        return (int) ((spValue * context.getResources().getDisplayMetrics().scaledDensity) + 0.5f);
    }

    public static int getPxByDp(Context context, float dp) {
        return (int) TypedValue.applyDimension(1, dp, context.getResources().getDisplayMetrics());
    }
}
