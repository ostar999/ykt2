package com.hyphenate.easeui.modules;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;

/* loaded from: classes4.dex */
public class EaseBaseLayout extends LinearLayout {
    public EaseBaseLayout(Context context) {
        super(context);
    }

    public static float dip2px(Context context, float f2) {
        return TypedValue.applyDimension(1, f2, context.getResources().getDisplayMetrics());
    }

    public static float sp2px(Context context, float f2) {
        return TypedValue.applyDimension(2, f2, context.getResources().getDisplayMetrics());
    }

    public EaseBaseLayout(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public EaseBaseLayout(Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
    }
}
