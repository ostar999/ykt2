package com.easefun.polyv.livecommon.ui.widget.gif;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;

/* loaded from: classes3.dex */
public class RelativeImageSpan extends ImageSpan {
    public static final int ALIGN_BASELINE = 1;
    public static final int ALIGN_BOTTOM = 0;
    public static final int ALIGN_CENTER = 3;
    public static final int ALIGN_TEXTBOTTOM = 2;
    private float scale;
    private int status;

    public RelativeImageSpan(Drawable d3) {
        super(d3);
        this.status = 0;
        this.scale = 1.0f;
    }

    @Override // android.text.style.DynamicDrawableSpan, android.text.style.ReplacementSpan
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x2, int top2, int y2, int bottom, Paint paint) {
        int i2;
        int i3;
        Drawable drawable = getDrawable();
        canvas.save();
        int i4 = this.status;
        if (i4 == 0) {
            i2 = drawable.getBounds().bottom;
        } else {
            if (i4 != 1) {
                i3 = i4 != 2 ? i4 != 3 ? 0 : (((bottom - top2) - drawable.getBounds().bottom) / 2) + top2 : (bottom - drawable.getBounds().bottom) - (paint.getFontMetricsInt().descent - (bottom / 4));
                canvas.translate(x2 + (this.scale * 3.0f), i3);
                drawable.draw(canvas);
                canvas.restore();
            }
            bottom -= drawable.getBounds().bottom;
            i2 = paint.getFontMetricsInt().descent;
        }
        i3 = bottom - i2;
        canvas.translate(x2 + (this.scale * 3.0f), i3);
        drawable.draw(canvas);
        canvas.restore();
    }

    @Override // android.text.style.DynamicDrawableSpan, android.text.style.ReplacementSpan
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        Rect bounds = getDrawable().getBounds();
        if (this.status != 3) {
            if (fm != null) {
                int i2 = -bounds.bottom;
                fm.ascent = i2;
                fm.descent = 0;
                fm.top = i2;
                fm.bottom = 0;
            }
        } else if (fm != null) {
            Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
            int i3 = fontMetricsInt.bottom - fontMetricsInt.top;
            int i4 = (bounds.bottom - bounds.top) / 2;
            int i5 = i3 / 4;
            int i6 = i4 - i5;
            int i7 = -(i4 + i5);
            fm.ascent = i7;
            fm.top = i7;
            fm.bottom = i6;
            fm.descent = i6;
        }
        return bounds.right + ((int) (this.scale * 3.0f * 2.0f));
    }

    public RelativeImageSpan(Drawable d3, int verticalAlignment) {
        super(d3, verticalAlignment);
        this.scale = 1.0f;
        this.status = verticalAlignment;
    }

    public RelativeImageSpan(Drawable d3, int verticalAlignment, float spacingScale) {
        super(d3, verticalAlignment);
        this.status = verticalAlignment;
        this.scale = spacingScale;
    }
}
