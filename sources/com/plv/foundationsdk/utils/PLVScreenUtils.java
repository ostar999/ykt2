package com.plv.foundationsdk.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import com.plv.thirdpart.blankj.utilcode.util.ActivityUtils;
import com.yikaobang.yixue.R2;

/* loaded from: classes4.dex */
public class PLVScreenUtils {
    private static int itemHeight;
    private static int itemWidth;

    public static int dip2px(float f2) {
        return dip2px(ActivityUtils.getTopActivity(), f2);
    }

    public static void enterLandscape(Activity activity) {
        activity.getWindow().setFlags(1024, 1024);
    }

    public static void enterPortrait(Activity activity) {
        exitFullScreen(activity);
    }

    public static void exitFullScreen(Activity activity) {
        activity.getWindow().clearFlags(1024);
    }

    public static int getItemHeight() {
        return itemHeight;
    }

    public static int getItemWidth() {
        return itemWidth;
    }

    public static int[] getNormalWH(Activity activity) {
        Point point = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(point);
        return new int[]{point.x, point.y};
    }

    public static void hideStatusBar(Activity activity) {
        activity.getWindow().getDecorView().setSystemUiVisibility(R2.color.material_dynamic_neutral_variant20);
    }

    public static boolean isLandscape(Context context) {
        return context.getResources().getConfiguration().orientation == 2;
    }

    public static boolean isPortrait(Context context) {
        return context.getResources().getConfiguration().orientation == 1;
    }

    public static int px2dip(Context context, float f2) {
        return (int) ((f2 / context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static void reSetStatusBar(Activity activity) {
        if (isLandscape(activity)) {
            hideStatusBar(activity);
        } else {
            setDecorVisible(activity);
        }
    }

    public static void setDecorVisible(Activity activity) {
        activity.getWindow().getDecorView().setSystemUiVisibility(0);
    }

    public static void setItemHeight(int i2) {
        itemHeight = i2;
    }

    public static void setItemWidth(int i2) {
        itemWidth = i2;
    }

    public static void showStatusBar(Activity activity) {
        setDecorVisible(activity);
    }

    public static int dip2px(Context context, float f2) {
        return (int) ((f2 * context.getResources().getDisplayMetrics().density) + 0.5f);
    }
}
