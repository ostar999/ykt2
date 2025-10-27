package com.ykb.ebook.weight;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

/* loaded from: classes8.dex */
public class RoundCornerDrawable extends Drawable {
    private Paint paint;
    public int radius;
    private boolean topOnly;

    public RoundCornerDrawable(int i2, int i3) {
        this(i2, i3, false);
    }

    private void drawFullRoundRect(Canvas canvas) {
        RectF rectF = new RectF(getBounds());
        int i2 = this.radius;
        canvas.drawRoundRect(rectF, i2, i2, this.paint);
    }

    private void drawTopRoundRect(Canvas canvas) {
        RectF rectF = new RectF(getBounds());
        Path path = new Path();
        path.moveTo(rectF.left, rectF.top + this.radius);
        float f2 = rectF.left;
        float f3 = rectF.top;
        path.quadTo(f2, f3, this.radius + f2, f3);
        path.lineTo(rectF.right - this.radius, rectF.top);
        float f4 = rectF.right;
        float f5 = rectF.top;
        path.quadTo(f4, f5, f4, this.radius + f5);
        path.lineTo(rectF.right, rectF.bottom);
        path.lineTo(rectF.left, rectF.bottom);
        path.close();
        canvas.drawPath(path, this.paint);
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        if (this.topOnly) {
            drawTopRoundRect(canvas);
        } else {
            drawFullRoundRect(canvas);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i2) {
        this.paint.setAlpha(i2);
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.paint.setColorFilter(colorFilter);
    }

    public RoundCornerDrawable(int i2, int i3, boolean z2) {
        this.radius = 20;
        this.topOnly = false;
        Paint paint = new Paint();
        this.paint = paint;
        paint.setColor(i2);
        this.paint.setAntiAlias(true);
        this.radius = i3;
        this.topOnly = z2;
    }
}
