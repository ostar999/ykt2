package com.scwang.smartrefresh.layout.internal;

import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

/* loaded from: classes6.dex */
public abstract class PaintDrawable extends Drawable {
    protected Paint mPaint;

    public PaintDrawable() {
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setStyle(Paint.Style.FILL);
        this.mPaint.setAntiAlias(true);
        this.mPaint.setColor(-5592406);
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i2) {
        this.mPaint.setAlpha(i2);
    }

    public void setColor(int i2) {
        this.mPaint.setColor(i2);
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
    }
}
