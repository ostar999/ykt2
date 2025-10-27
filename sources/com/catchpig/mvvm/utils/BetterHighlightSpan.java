package com.catchpig.mvvm.utils;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.style.ReplacementSpan;

/* loaded from: classes2.dex */
public class BetterHighlightSpan extends ReplacementSpan {
    private int backgroundColor;
    private int lineSpacingExtra;
    private int topSpacingExtra;

    public BetterHighlightSpan(int i2) {
        this(i2, 0);
    }

    @Override // android.text.style.ReplacementSpan
    public void draw(Canvas canvas, CharSequence charSequence, int i2, int i3, float f2, int i4, int i5, int i6, Paint paint) {
        int color = paint.getColor();
        RectF rectF = new RectF(f2, i4 + this.topSpacingExtra, paint.measureText(charSequence, i2, i3) + f2, i6 - this.lineSpacingExtra);
        paint.setColor(this.backgroundColor);
        canvas.drawRect(rectF, paint);
        paint.setColor(color);
        canvas.drawText(charSequence, i2, i3, f2, i5, paint);
    }

    @Override // android.text.style.ReplacementSpan
    public int getSize(Paint paint, CharSequence charSequence, int i2, int i3, Paint.FontMetricsInt fontMetricsInt) {
        return Math.round(paint.measureText(charSequence, i2, i3));
    }

    public BetterHighlightSpan(int i2, int i3) {
        this(i2, i3, 10);
    }

    public BetterHighlightSpan(int i2, int i3, int i4) {
        this.backgroundColor = i2;
        this.lineSpacingExtra = i3;
        this.topSpacingExtra = i4;
    }
}
