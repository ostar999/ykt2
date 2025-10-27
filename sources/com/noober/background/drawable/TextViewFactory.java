package com.noober.background.drawable;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/* loaded from: classes4.dex */
public class TextViewFactory {
    public static void setTextGradientColor(Context context, AttributeSet attributeSet, TextView textView) {
        new TextViewGradientColor().invoke(context, attributeSet, textView);
    }
}
