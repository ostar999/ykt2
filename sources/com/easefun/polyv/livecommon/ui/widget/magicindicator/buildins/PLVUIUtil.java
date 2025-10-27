package com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins;

import android.content.Context;

/* loaded from: classes3.dex */
public final class PLVUIUtil {
    public static int dip2px(Context context, double dpValue) {
        return (int) ((dpValue * context.getResources().getDisplayMetrics().density) + 0.5d);
    }

    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }
}
