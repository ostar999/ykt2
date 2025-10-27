package com.psychiatrygarden.utils;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;

/* loaded from: classes6.dex */
public class VerticalImageSpan extends ImageSpan {
    private Drawable drawable;

    public VerticalImageSpan(Drawable drawable) {
        super(drawable);
        this.drawable = drawable;
    }

    @Override // android.text.style.DynamicDrawableSpan, android.text.style.ReplacementSpan
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x2, int top2, int y2, int bottom, Paint paint) {
        Drawable drawable = getDrawable();
        canvas.save();
        canvas.translate(x2, (((bottom - top2) - drawable.getBounds().bottom) / 2) + top2);
        drawable.draw(canvas);
        canvas.restore();
    }

    @Override // android.text.style.DynamicDrawableSpan, android.text.style.ReplacementSpan
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fontMetricsInt) {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            drawable = this.drawable;
        }
        Rect bounds = drawable.getBounds();
        if (fontMetricsInt != null) {
            Paint.FontMetricsInt fontMetricsInt2 = paint.getFontMetricsInt();
            int i2 = fontMetricsInt2.bottom - fontMetricsInt2.top;
            int i3 = (bounds.bottom - bounds.top) / 2;
            int i4 = i2 / 4;
            int i5 = i3 - i4;
            int i6 = -(i3 + i4);
            fontMetricsInt.ascent = i6;
            fontMetricsInt.top = i6;
            fontMetricsInt.bottom = i5;
            fontMetricsInt.descent = i5;
        }
        return bounds.right;
    }
}
