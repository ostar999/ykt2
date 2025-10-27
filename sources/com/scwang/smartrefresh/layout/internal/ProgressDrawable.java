package com.scwang.smartrefresh.layout.internal;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import androidx.annotation.NonNull;
import com.heytap.mcssdk.constant.a;

/* loaded from: classes6.dex */
public class ProgressDrawable extends PaintDrawable implements Animatable, ValueAnimator.AnimatorUpdateListener {
    protected ValueAnimator mValueAnimator;
    protected int mWidth = 0;
    protected int mHeight = 0;
    protected int mProgressDegree = 0;
    protected Path mPath = new Path();

    public ProgressDrawable() {
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(30, 3600);
        this.mValueAnimator = valueAnimatorOfInt;
        valueAnimatorOfInt.setDuration(a.f7153q);
        this.mValueAnimator.setInterpolator(null);
        this.mValueAnimator.setRepeatCount(-1);
        this.mValueAnimator.setRepeatMode(1);
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(@NonNull Canvas canvas) {
        Rect bounds = getBounds();
        int iWidth = bounds.width();
        int iHeight = bounds.height();
        float f2 = iWidth;
        float fMax = Math.max(1.0f, f2 / 22.0f);
        if (this.mWidth != iWidth || this.mHeight != iHeight) {
            this.mPath.reset();
            float f3 = f2 - fMax;
            float f4 = iHeight / 2.0f;
            this.mPath.addCircle(f3, f4, fMax, Path.Direction.CW);
            float f5 = f2 - (5.0f * fMax);
            this.mPath.addRect(f5, f4 - fMax, f3, f4 + fMax, Path.Direction.CW);
            this.mPath.addCircle(f5, f4, fMax, Path.Direction.CW);
            this.mWidth = iWidth;
            this.mHeight = iHeight;
        }
        canvas.save();
        float f6 = f2 / 2.0f;
        float f7 = iHeight / 2.0f;
        canvas.rotate(this.mProgressDegree, f6, f7);
        for (int i2 = 0; i2 < 12; i2++) {
            this.mPaint.setAlpha((i2 + 5) * 17);
            canvas.rotate(30.0f, f6, f7);
            canvas.drawPath(this.mPath, this.mPaint);
        }
        canvas.restore();
    }

    @Override // android.graphics.drawable.Animatable
    public boolean isRunning() {
        return this.mValueAnimator.isRunning();
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.mProgressDegree = (((Integer) valueAnimator.getAnimatedValue()).intValue() / 30) * 30;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Animatable
    public void start() {
        if (this.mValueAnimator.isRunning()) {
            return;
        }
        this.mValueAnimator.addUpdateListener(this);
        this.mValueAnimator.start();
    }

    @Override // android.graphics.drawable.Animatable
    public void stop() {
        if (this.mValueAnimator.isRunning()) {
            this.mValueAnimator.removeAllListeners();
            this.mValueAnimator.removeAllUpdateListeners();
            this.mValueAnimator.cancel();
        }
    }
}
