package com.psychiatrygarden.utils;

import android.content.Context;

/* loaded from: classes6.dex */
public class DensityUtil {
    public static int dip2px(Context context, float dpValue) {
        return (int) ((dpValue * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        return (int) ((pxValue / context.getResources().getDisplayMetrics().density) + 0.5f);
    }
}
