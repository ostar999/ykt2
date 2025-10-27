package com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins;

/* loaded from: classes3.dex */
public class PLVArgbEvaluatorHolder {
    public static int eval(float fraction, int startValue, int endValue) {
        return ((((startValue >> 24) & 255) + ((int) ((((endValue >> 24) & 255) - r0) * fraction))) << 24) | ((((startValue >> 16) & 255) + ((int) ((((endValue >> 16) & 255) - r1) * fraction))) << 16) | ((((startValue >> 8) & 255) + ((int) ((((endValue >> 8) & 255) - r2) * fraction))) << 8) | ((startValue & 255) + ((int) (fraction * ((endValue & 255) - r7))));
    }
}
