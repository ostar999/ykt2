package com.catchpig.loading.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.catchpig.loading.R;

/* loaded from: classes2.dex */
public class LoadingView extends View {
    private static final int DEGREE_PER_LINE = 45;
    private static final int LINE_COUNT = 8;
    private int mAnimateValue;
    private int mLoadColor;
    private int mLoadDuration;
    private int mLoadSize;
    private Paint mPaint;
    private ValueAnimator mValueAnimator;
    private ValueAnimator.AnimatorUpdateListener updateListener;

    public LoadingView(Context context) {
        this(context, null);
    }

    private int dpToPxInt(float f2) {
        return (int) ((f2 * getContext().getResources().getDisplayMetrics().density) + 0.5f);
    }

    private void drawLoading(Canvas canvas, int i2) {
        int i3 = this.mLoadSize;
        int i4 = i3 / 4;
        int i5 = i3 / 4;
        int width = getWidth() / 2;
        this.mPaint.setStrokeWidth(i4 / 2);
        float f2 = width;
        canvas.rotate(i2, f2, f2);
        canvas.translate(f2, f2);
        for (int i6 = 0; i6 < 8; i6++) {
            canvas.rotate(45.0f);
            canvas.translate(0.0f, ((-this.mLoadSize) / 2) + r1);
            canvas.drawCircle(0.0f, 0.0f, (float) (((i6 + 7) * i5) / 28.0d), this.mPaint);
            canvas.translate(0.0f, (this.mLoadSize / 2) - r1);
        }
    }

    private void initPaint() {
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setColor(this.mLoadColor);
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(ValueAnimator valueAnimator) {
        this.mAnimateValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
        invalidate();
    }

    private void startAnim() {
        if (this.mValueAnimator == null) {
            ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(0, 7);
            this.mValueAnimator = valueAnimatorOfInt;
            valueAnimatorOfInt.setDuration(this.mLoadDuration);
            this.mValueAnimator.setRepeatMode(1);
            this.mValueAnimator.setRepeatCount(-1);
            this.mValueAnimator.setInterpolator(new LinearInterpolator());
            this.mValueAnimator.addUpdateListener(this.updateListener);
        }
        if (this.mValueAnimator.isStarted()) {
            return;
        }
        this.mValueAnimator.start();
    }

    private void stopAnim() {
        ValueAnimator valueAnimator = this.mValueAnimator;
        if (valueAnimator == null || !valueAnimator.isStarted()) {
            return;
        }
        this.mValueAnimator.removeUpdateListener(this.updateListener);
        this.mValueAnimator.removeAllUpdateListeners();
        this.mValueAnimator.cancel();
        this.mValueAnimator = null;
    }

    @Override // android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAnim();
    }

    @Override // android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAnim();
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int iSaveLayer = canvas.saveLayer(0.0f, 0.0f, getWidth(), getHeight(), null, 31);
        drawLoading(canvas, this.mAnimateValue * 45);
        canvas.restoreToCount(iSaveLayer);
    }

    @Override // android.view.View
    public void onVisibilityChanged(@NonNull View view, int i2) {
        super.onVisibilityChanged(view, i2);
        if (i2 == 0) {
            startAnim();
        } else {
            stopAnim();
        }
    }

    public void setLoadColor(@ColorRes int i2) {
        int color = ContextCompat.getColor(getContext(), i2);
        this.mLoadColor = color;
        this.mPaint.setColor(color);
        invalidate();
    }

    public LoadingView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.loading_view_style);
    }

    public LoadingView(Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mLoadDuration = 800;
        this.mAnimateValue = 0;
        this.updateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: q0.a
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f28157c.lambda$new$0(valueAnimator);
            }
        };
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.LoadingView, i2, 0);
        this.mLoadColor = typedArrayObtainStyledAttributes.getColor(R.styleable.LoadingView_loading_view_color, -1);
        this.mLoadSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.LoadingView_loading_view_size, dpToPxInt(32.0f));
        this.mLoadDuration = typedArrayObtainStyledAttributes.getInteger(R.styleable.LoadingView_loading_view_duration, this.mLoadDuration);
        typedArrayObtainStyledAttributes.recycle();
        initPaint();
    }
}
