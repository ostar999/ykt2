package cn.webdemo.com.supporfragment.tablayout.buildins;

import android.content.Context;

/* loaded from: classes.dex */
public final class UIUtil {
    public static int dip2px(Context context, double d3) {
        return (int) ((d3 * context.getResources().getDisplayMetrics().density) + 0.5d);
    }

    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }
}
