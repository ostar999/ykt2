package com.beizi.fusion.g;

import android.content.Context;

/* loaded from: classes2.dex */
public class a {
    public static float a(Context context) {
        float f2 = context.getResources().getDisplayMetrics().density;
        float f3 = context.getResources().getDisplayMetrics().widthPixels;
        if (f2 <= 0.0f) {
            f2 = 1.0f;
        }
        return (f3 / f2) + 0.5f;
    }
}
