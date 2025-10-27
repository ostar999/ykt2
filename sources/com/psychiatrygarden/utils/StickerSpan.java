package com.psychiatrygarden.utils;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;

/* loaded from: classes6.dex */
public class StickerSpan extends ImageSpan {
    public StickerSpan(Drawable b3, int verticalAlignment) {
        super(b3, verticalAlignment);
    }

    @Override // android.text.style.DynamicDrawableSpan, android.text.style.ReplacementSpan
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x2, int top2, int y2, int bottom, Paint paint) {
        Drawable drawable = getDrawable();
        Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
        int i2 = ((((fontMetricsInt.descent + y2) + y2) + fontMetricsInt.ascent) / 2) - (drawable.getBounds().bottom / 2);
        canvas.save();
        canvas.translate(x2, i2);
        drawable.draw(canvas);
        canvas.restore();
    }
}
