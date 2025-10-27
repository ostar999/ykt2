package com.tencent.liteav.renderer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;

/* loaded from: classes6.dex */
public class TXCFocusIndicatorView extends View {
    private static final int FOCUS_AREA_STROKE = 1;
    private int mFocusAreaStroke;
    private Runnable mHideRunnable;
    private Paint mPaint;
    private ScaleAnimation mScaleAnimation;
    private int mSize;

    public TXCFocusIndicatorView(Context context) {
        super(context);
        this.mSize = 0;
        this.mFocusAreaStroke = 2;
        this.mHideRunnable = new Runnable() { // from class: com.tencent.liteav.renderer.TXCFocusIndicatorView.1
            @Override // java.lang.Runnable
            public void run() {
                TXCFocusIndicatorView.this.setVisibility(8);
            }
        };
        init(null, 0);
    }

    private void init(AttributeSet attributeSet, int i2) {
        this.mPaint = new Paint();
        this.mFocusAreaStroke = (int) ((getContext().getResources().getDisplayMetrics().density * 1.0f) + 0.5f);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.3f, 1.0f, 1.3f, 1.0f, 1, 0.5f, 1, 0.5f);
        this.mScaleAnimation = scaleAnimation;
        scaleAnimation.setDuration(200L);
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        canvas.save();
        int i2 = this.mFocusAreaStroke / 2;
        int i3 = this.mSize;
        Rect rect = new Rect(i2, i2, i3 - i2, i3 - i2);
        this.mPaint.setColor(-1);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setStrokeWidth(i2 * 2);
        canvas.drawRect(rect, this.mPaint);
        canvas.restore();
        super.onDraw(canvas);
    }

    public void show(int i2, int i3, int i4) {
        removeCallbacks(this.mHideRunnable);
        this.mScaleAnimation.cancel();
        this.mSize = i4;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) getLayoutParams();
        layoutParams.setMargins(i2, i3, 0, 0);
        int i5 = this.mSize;
        layoutParams.width = i5;
        layoutParams.height = i5;
        setVisibility(0);
        requestLayout();
        this.mScaleAnimation.reset();
        startAnimation(this.mScaleAnimation);
        postDelayed(this.mHideRunnable, 1000L);
    }

    public TXCFocusIndicatorView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mSize = 0;
        this.mFocusAreaStroke = 2;
        this.mHideRunnable = new Runnable() { // from class: com.tencent.liteav.renderer.TXCFocusIndicatorView.1
            @Override // java.lang.Runnable
            public void run() {
                TXCFocusIndicatorView.this.setVisibility(8);
            }
        };
        init(attributeSet, 0);
    }

    public TXCFocusIndicatorView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mSize = 0;
        this.mFocusAreaStroke = 2;
        this.mHideRunnable = new Runnable() { // from class: com.tencent.liteav.renderer.TXCFocusIndicatorView.1
            @Override // java.lang.Runnable
            public void run() {
                TXCFocusIndicatorView.this.setVisibility(8);
            }
        };
        init(attributeSet, i2);
    }
}
