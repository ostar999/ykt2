package com.aliyun.svideo.common.utils;

import android.content.Context;

/* loaded from: classes2.dex */
public class DensityUtils {
    public static int dip2px(Context context, float f2) {
        return (int) ((f2 * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int px2dip(Context context, float f2) {
        return (int) ((f2 / context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int sp2px(Context context, int i2) {
        return (int) ((i2 * context.getResources().getDisplayMetrics().scaledDensity) + 0.5f);
    }
}
