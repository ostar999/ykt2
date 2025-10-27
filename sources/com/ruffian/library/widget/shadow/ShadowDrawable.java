package com.ruffian.library.widget.shadow;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;

/* loaded from: classes6.dex */
public class ShadowDrawable extends Drawable {
    private float[] mRoundRadii;
    private int mShadowColor;
    private int mShadowDx;
    private int mShadowDy;
    private float mShadowRadius;
    private Path mPath = new Path();
    private RectF mBoundsF = new RectF();
    private Paint mPaint = new Paint(5);

    private void updateBounds(Rect rect) {
        if (rect == null) {
            rect = getBounds();
        }
        this.mBoundsF.set(rect.left + getShadowOffset() + Math.abs(this.mShadowDx), rect.top + getShadowOffset() + Math.abs(this.mShadowDy), (rect.right - getShadowOffset()) - Math.abs(this.mShadowDx), (rect.bottom - getShadowOffset()) - Math.abs(this.mShadowDy));
        this.mPath.reset();
        this.mPath.addRoundRect(this.mBoundsF, this.mRoundRadii, Path.Direction.CW);
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        canvas.drawPath(this.mPath, this.mPaint);
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    public float getShadowOffset() {
        return this.mShadowRadius;
    }

    @Override // android.graphics.drawable.Drawable
    public void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        if (rect.right - rect.left <= 0 || rect.bottom - rect.top <= 0) {
            return;
        }
        updateBounds(rect);
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i2) {
        this.mPaint.setAlpha(i2);
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
    }

    public void updateParameter(int i2, float f2, int i3, int i4, float[] fArr) {
        this.mShadowColor = i2;
        this.mRoundRadii = fArr;
        this.mShadowRadius = f2;
        this.mShadowDx = i3;
        this.mShadowDy = i4;
        this.mPaint.setColor(i2);
        this.mPaint.setShadowLayer(this.mShadowRadius, this.mShadowDx, this.mShadowDy, this.mShadowColor);
    }
}
