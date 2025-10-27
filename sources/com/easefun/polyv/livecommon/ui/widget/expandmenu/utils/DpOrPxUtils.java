package com.easefun.polyv.livecommon.ui.widget.expandmenu.utils;

import android.content.Context;

/* loaded from: classes3.dex */
public class DpOrPxUtils {
    public static int dip2px(Context context, float dpValue) {
        return (int) ((dpValue * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        return (int) ((pxValue / context.getResources().getDisplayMetrics().density) + 0.5f);
    }
}
