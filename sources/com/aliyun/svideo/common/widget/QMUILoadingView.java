package com.aliyun.svideo.common.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import com.aliyun.svideo.common.R;
import com.aliyun.svideo.common.utils.DensityUtils;

/* loaded from: classes2.dex */
public class QMUILoadingView extends View {
    private static final int DEGREE_PER_LINE = 30;
    private static final int LINE_COUNT = 12;
    private int mAnimateValue;
    private ValueAnimator mAnimator;
    private Paint mPaint;
    private int mPaintColor;
    private int mSize;
    private ValueAnimator.AnimatorUpdateListener mUpdateListener;

    public QMUILoadingView(Context context) {
        this(context, null);
    }

    private void drawLoading(Canvas canvas, int i2) {
        int i3 = this.mSize;
        int i4 = i3 / 12;
        int i5 = i3 / 6;
        this.mPaint.setStrokeWidth(i4);
        int i6 = this.mSize;
        canvas.rotate(i2, i6 / 2, i6 / 2);
        int i7 = this.mSize;
        canvas.translate(i7 / 2, i7 / 2);
        int i8 = 0;
        while (i8 < 12) {
            canvas.rotate(30.0f);
            i8++;
            this.mPaint.setAlpha((int) ((i8 * 255.0f) / 12.0f));
            int i9 = i4 / 2;
            canvas.translate(0.0f, ((-this.mSize) / 2) + i9);
            canvas.drawLine(0.0f, 0.0f, 0.0f, i5, this.mPaint);
            canvas.translate(0.0f, (this.mSize / 2) - i9);
        }
    }

    private void initPaint() {
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setColor(this.mPaintColor);
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override // android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        start();
    }

    @Override // android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int iSaveLayer = canvas.saveLayer(0.0f, 0.0f, getWidth(), getHeight(), null, 31);
        drawLoading(canvas, this.mAnimateValue * 30);
        canvas.restoreToCount(iSaveLayer);
    }

    @Override // android.view.View
    public void onMeasure(int i2, int i3) {
        int i4 = this.mSize;
        setMeasuredDimension(i4, i4);
    }

    @Override // android.view.View
    public void onVisibilityChanged(View view, int i2) {
        super.onVisibilityChanged(view, i2);
        if (i2 == 0) {
            start();
        } else {
            stop();
        }
    }

    public void setColor(int i2) {
        this.mPaintColor = i2;
        this.mPaint.setColor(i2);
        invalidate();
    }

    public void setSize(int i2) {
        this.mSize = i2;
        requestLayout();
    }

    public void start() {
        ValueAnimator valueAnimator = this.mAnimator;
        if (valueAnimator != null) {
            if (valueAnimator.isStarted()) {
                return;
            }
            this.mAnimator.start();
            return;
        }
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(0, 11);
        this.mAnimator = valueAnimatorOfInt;
        valueAnimatorOfInt.addUpdateListener(this.mUpdateListener);
        this.mAnimator.setDuration(600L);
        this.mAnimator.setRepeatMode(1);
        this.mAnimator.setRepeatCount(-1);
        this.mAnimator.setInterpolator(new LinearInterpolator());
        this.mAnimator.start();
    }

    public void stop() {
        ValueAnimator valueAnimator = this.mAnimator;
        if (valueAnimator != null) {
            valueAnimator.removeUpdateListener(this.mUpdateListener);
            this.mAnimator.removeAllUpdateListeners();
            this.mAnimator.cancel();
            this.mAnimator = null;
        }
    }

    public QMUILoadingView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.LoadingStyle);
    }

    public QMUILoadingView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mAnimateValue = 0;
        this.mUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.aliyun.svideo.common.widget.QMUILoadingView.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                QMUILoadingView.this.mAnimateValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                QMUILoadingView.this.invalidate();
            }
        };
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.LoadingView, i2, 0);
        this.mSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.LoadingView_loading_view_size, DensityUtils.dip2px(context, 32.0f));
        this.mPaintColor = typedArrayObtainStyledAttributes.getInt(R.styleable.LoadingView_android_color, -1);
        typedArrayObtainStyledAttributes.recycle();
        initPaint();
    }

    public QMUILoadingView(Context context, int i2, int i3) {
        super(context);
        this.mAnimateValue = 0;
        this.mUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.aliyun.svideo.common.widget.QMUILoadingView.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                QMUILoadingView.this.mAnimateValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                QMUILoadingView.this.invalidate();
            }
        };
        this.mSize = i2;
        this.mPaintColor = i3;
        initPaint();
    }
}
