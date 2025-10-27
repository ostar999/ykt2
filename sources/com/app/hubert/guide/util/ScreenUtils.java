package com.app.hubert.guide.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.view.Display;

/* loaded from: classes2.dex */
public class ScreenUtils {
    private ScreenUtils() {
        throw new AssertionError();
    }

    public static int dp2px(Context context, int i2) {
        return (int) (i2 * context.getResources().getDisplayMetrics().density);
    }

    public static int getNavigationBarHeight(Activity activity) {
        if (!isNavigationBarShow(activity)) {
            return 0;
        }
        Resources resources = activity.getResources();
        int identifier = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        int dimensionPixelSize = identifier > 0 ? resources.getDimensionPixelSize(identifier) : 0;
        LogUtil.i("NavigationBar的高度:" + dimensionPixelSize);
        return dimensionPixelSize;
    }

    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getStatusBarHeight(Context context) throws Resources.NotFoundException {
        int iDp2px = dp2px(context, 20);
        LogUtil.i("common statusBar height:" + iDp2px);
        int identifier = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (identifier > 0) {
            iDp2px = context.getResources().getDimensionPixelSize(identifier);
            LogUtil.i("real statusBar height:" + iDp2px);
        }
        LogUtil.i("finally statusBar height:" + iDp2px);
        return iDp2px;
    }

    public static boolean isNavigationBarShow(Activity activity) {
        Display defaultDisplay = activity.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        Point point2 = new Point();
        defaultDisplay.getSize(point);
        defaultDisplay.getRealSize(point2);
        return point2.y != point.y;
    }
}
