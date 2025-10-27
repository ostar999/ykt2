package com.psychiatrygarden.utils.weblong;

import android.content.Context;
import android.util.TypedValue;

/* loaded from: classes6.dex */
public class SizeUtil {
    public static int dp2px(Context ctx, int dp) {
        return (int) TypedValue.applyDimension(1, dp, ctx.getResources().getDisplayMetrics());
    }

    public static int px2dp(Context ctx, int px) {
        return (int) TypedValue.applyDimension(0, px, ctx.getResources().getDisplayMetrics());
    }

    public static int px2sp(Context ctx, int px) {
        return (int) TypedValue.applyDimension(0, px, ctx.getResources().getDisplayMetrics());
    }

    public static int sp2px(Context ctx, int sp) {
        return (int) TypedValue.applyDimension(2, sp, ctx.getResources().getDisplayMetrics());
    }
}
