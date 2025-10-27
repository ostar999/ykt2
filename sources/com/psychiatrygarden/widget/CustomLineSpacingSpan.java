package com.psychiatrygarden.widget;

import android.graphics.Paint;
import android.text.style.LineHeightSpan;

/* loaded from: classes6.dex */
public class CustomLineSpacingSpan implements LineHeightSpan {
    private int extraSpace;

    public CustomLineSpacingSpan(int extraSpace) {
        this.extraSpace = extraSpace;
    }

    @Override // android.text.style.LineHeightSpan
    public void chooseHeight(CharSequence text, int start, int end, int spanstartv, int lineHeight, Paint.FontMetricsInt fm) {
        int i2 = this.extraSpace;
        if (i2 > 0) {
            fm.descent += i2;
            fm.bottom += i2;
        }
    }
}
