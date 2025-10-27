package me.dkzwm.widget.srl.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

/* loaded from: classes9.dex */
public class PixelUtl {
    public static int dp2px(Context context, float f2) {
        return Math.round(TypedValue.applyDimension(1, f2, (context == null ? Resources.getSystem() : context.getResources()).getDisplayMetrics()));
    }
}
