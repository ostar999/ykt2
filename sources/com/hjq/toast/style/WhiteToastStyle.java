package com.hjq.toast.style;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.TypedValue;

/* loaded from: classes4.dex */
public class WhiteToastStyle extends BlackToastStyle {
    @Override // com.hjq.toast.style.BlackToastStyle
    public Drawable getBackgroundDrawable(Context context) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(-1381654);
        gradientDrawable.setCornerRadius(TypedValue.applyDimension(1, 8.0f, context.getResources().getDisplayMetrics()));
        return gradientDrawable;
    }

    @Override // com.hjq.toast.style.BlackToastStyle
    public int getTextColor(Context context) {
        return -1157627904;
    }
}
