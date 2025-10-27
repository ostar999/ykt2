package com.ykb.common_share_lib.util;

import android.content.Context;
import android.view.WindowManager;

/* loaded from: classes4.dex */
public class CommonUtil {
    public static int dip2px(Context context, float f2) {
        return (int) ((f2 * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int getScreenHeight(Context context) {
        return ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getHeight();
    }

    public static int getScreenWidth(Context context) {
        return ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getWidth();
    }

    public static int px2dip(Context context, float f2) {
        return (int) ((f2 / context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int px2sp(Context context, float f2) {
        return (int) ((f2 / context.getResources().getDisplayMetrics().scaledDensity) + 0.5f);
    }

    public static int sp2px(Context context, float f2) {
        return (int) ((f2 * context.getResources().getDisplayMetrics().scaledDensity) + 0.5f);
    }
}
