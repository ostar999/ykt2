package com.just.agentweb;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import androidx.annotation.Nullable;

/* loaded from: classes4.dex */
public class WebIndicator extends BaseIndicatorView {
    public static final int DO_END_ANIMATION_DURATION = 600;
    public static final int FINISH = 2;
    public static final int MAX_DECELERATE_SPEED_DURATION = 450;
    public static final int MAX_UNIFORM_SPEED_DURATION = 8000;
    public static final int STARTED = 1;
    public static final int UN_START = 0;
    private int indicatorStatus;
    private Animator mAnimator;
    private AnimatorListenerAdapter mAnimatorListenerAdapter;
    private ValueAnimator.AnimatorUpdateListener mAnimatorUpdateListener;
    private int mColor;
    private int mCurrentDoEndAnimationDuration;
    private int mCurrentMaxDecelerateSpeedDuration;
    private int mCurrentMaxUniformSpeedDuration;
    private float mCurrentProgress;
    private Paint mPaint;
    private int mTargetWidth;
    public int mWebIndicatorDefaultHeight;

    public WebIndicator(Context context) {
        this(context, null);
    }

    public WebIndicator(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public WebIndicator(Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mTargetWidth = 0;
        this.mCurrentMaxUniformSpeedDuration = 8000;
        this.mCurrentMaxDecelerateSpeedDuration = 450;
        this.mCurrentDoEndAnimationDuration = 600;
        this.indicatorStatus = 0;
        this.mCurrentProgress = 0.0f;
        this.mWebIndicatorDefaultHeight = 3;
        this.mAnimatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.just.agentweb.WebIndicator.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                WebIndicator.this.mCurrentProgress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                WebIndicator.this.invalidate();
            }
        };
        this.mAnimatorListenerAdapter = new AnimatorListenerAdapter() { // from class: com.just.agentweb.WebIndicator.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                WebIndicator.this.doEnd();
            }
        };
        init(context, attributeSet, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doEnd() {
        if (this.indicatorStatus == 2 && this.mCurrentProgress == 100.0f) {
            setVisibility(8);
            this.mCurrentProgress = 0.0f;
            setAlpha(1.0f);
        }
        this.indicatorStatus = 0;
    }

    private void init(Context context, AttributeSet attributeSet, int i2) {
        this.mPaint = new Paint();
        this.mColor = Color.parseColor("#1aad19");
        this.mPaint.setAntiAlias(true);
        this.mPaint.setColor(this.mColor);
        this.mPaint.setDither(true);
        this.mPaint.setStrokeCap(Paint.Cap.SQUARE);
        this.mTargetWidth = context.getResources().getDisplayMetrics().widthPixels;
        this.mWebIndicatorDefaultHeight = AgentWebUtils.dp2px(context, 3.0f);
    }

    private void startAnim(boolean z2) {
        ValueAnimator valueAnimatorOfFloat;
        float f2 = z2 ? 100.0f : 95.0f;
        Animator animator = this.mAnimator;
        if (animator != null && animator.isStarted()) {
            this.mAnimator.cancel();
        }
        float f3 = this.mCurrentProgress;
        if (f3 == 0.0f) {
            f3 = 1.0E-8f;
        }
        this.mCurrentProgress = f3;
        if (z2) {
            if (f3 < 95.0f) {
                valueAnimatorOfFloat = ValueAnimator.ofFloat(f3, 95.0f);
                valueAnimatorOfFloat.setDuration((long) (((1.0f - (this.mCurrentProgress / 100.0f)) - 0.05f) * this.mCurrentMaxDecelerateSpeedDuration));
                valueAnimatorOfFloat.setInterpolator(new DecelerateInterpolator());
                valueAnimatorOfFloat.addUpdateListener(this.mAnimatorUpdateListener);
            } else {
                valueAnimatorOfFloat = null;
            }
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this, "alpha", 1.0f, 0.0f);
            objectAnimatorOfFloat.setDuration(this.mCurrentDoEndAnimationDuration);
            ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(95.0f, 100.0f);
            valueAnimatorOfFloat2.setDuration(this.mCurrentDoEndAnimationDuration);
            valueAnimatorOfFloat2.addUpdateListener(this.mAnimatorUpdateListener);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(objectAnimatorOfFloat, valueAnimatorOfFloat2);
            if (valueAnimatorOfFloat != null) {
                AnimatorSet animatorSet2 = new AnimatorSet();
                animatorSet2.play(animatorSet).after(valueAnimatorOfFloat);
                animatorSet = animatorSet2;
            }
            animatorSet.addListener(this.mAnimatorListenerAdapter);
            animatorSet.start();
            this.mAnimator = animatorSet;
        } else {
            AnimatorSet animatorSet3 = new AnimatorSet();
            float f4 = f2 * 0.6f;
            ValueAnimator valueAnimatorOfFloat3 = ValueAnimator.ofFloat(this.mCurrentProgress, f4);
            ValueAnimator valueAnimatorOfFloat4 = ValueAnimator.ofFloat(f4, f2);
            float f5 = (long) (((1.0f - (this.mCurrentProgress / 100.0f)) - 0.05f) * this.mCurrentMaxUniformSpeedDuration);
            valueAnimatorOfFloat3.setInterpolator(new LinearInterpolator());
            valueAnimatorOfFloat3.setDuration((long) (f5 * 0.4f));
            valueAnimatorOfFloat3.addUpdateListener(this.mAnimatorUpdateListener);
            valueAnimatorOfFloat4.setInterpolator(new LinearInterpolator());
            valueAnimatorOfFloat4.setDuration((long) (0.6f * f5));
            valueAnimatorOfFloat4.addUpdateListener(this.mAnimatorUpdateListener);
            animatorSet3.play(valueAnimatorOfFloat4).after(valueAnimatorOfFloat3);
            animatorSet3.start();
            this.mAnimator = animatorSet3;
        }
        this.indicatorStatus = 1;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchDraw(Canvas canvas) {
        canvas.drawRect(0.0f, 0.0f, (this.mCurrentProgress / 100.0f) * Float.valueOf(getWidth()).floatValue(), getHeight(), this.mPaint);
    }

    @Override // com.just.agentweb.BaseIndicatorView, com.just.agentweb.BaseIndicatorSpec
    public void hide() {
        this.indicatorStatus = 2;
    }

    @Override // com.just.agentweb.LayoutParamsOffer
    public FrameLayout.LayoutParams offerLayoutParams() {
        return new FrameLayout.LayoutParams(-1, this.mWebIndicatorDefaultHeight);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Animator animator = this.mAnimator;
        if (animator == null || !animator.isStarted()) {
            return;
        }
        this.mAnimator.cancel();
        this.mAnimator = null;
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
    }

    @Override // android.widget.FrameLayout, android.view.View
    public void onMeasure(int i2, int i3) {
        int mode = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i2);
        int mode2 = View.MeasureSpec.getMode(i3);
        int size2 = View.MeasureSpec.getSize(i3);
        if (mode == Integer.MIN_VALUE && size > getContext().getResources().getDisplayMetrics().widthPixels) {
            size = getContext().getResources().getDisplayMetrics().widthPixels;
        }
        if (mode2 == Integer.MIN_VALUE) {
            size2 = this.mWebIndicatorDefaultHeight;
        }
        setMeasuredDimension(size, size2);
    }

    @Override // android.view.View
    public void onSizeChanged(int i2, int i3, int i4, int i5) {
        int i6;
        super.onSizeChanged(i2, i3, i4, i5);
        this.mTargetWidth = getMeasuredWidth();
        int i7 = getContext().getResources().getDisplayMetrics().widthPixels;
        int i8 = this.mTargetWidth;
        if (i8 >= i7) {
            i6 = 450;
            this.mCurrentMaxDecelerateSpeedDuration = 450;
            this.mCurrentMaxUniformSpeedDuration = 8000;
        } else {
            float fFloatValue = i8 / Float.valueOf(i7).floatValue();
            this.mCurrentMaxUniformSpeedDuration = (int) (8000.0f * fFloatValue);
            this.mCurrentMaxDecelerateSpeedDuration = (int) (450.0f * fFloatValue);
            i6 = (int) (fFloatValue * 600.0f);
        }
        this.mCurrentDoEndAnimationDuration = i6;
        LogUtils.i("WebProgress", "CURRENT_MAX_UNIFORM_SPEED_DURATION" + this.mCurrentMaxUniformSpeedDuration);
    }

    @Override // com.just.agentweb.BaseIndicatorView, com.just.agentweb.BaseIndicatorSpec
    public void reset() {
        this.mCurrentProgress = 0.0f;
        Animator animator = this.mAnimator;
        if (animator == null || !animator.isStarted()) {
            return;
        }
        this.mAnimator.cancel();
    }

    public void setColor(int i2) {
        this.mColor = i2;
        this.mPaint.setColor(i2);
    }

    public void setColor(String str) {
        setColor(Color.parseColor(str));
    }

    public void setProgress(float f2) {
        if (getVisibility() == 8) {
            setVisibility(0);
        }
        if (f2 >= 95.0f && this.indicatorStatus != 2) {
            startAnim(true);
        }
    }

    @Override // com.just.agentweb.BaseIndicatorView, com.just.agentweb.BaseIndicatorSpec
    public void setProgress(int i2) {
        setProgress(Float.valueOf(i2).floatValue());
    }

    @Override // com.just.agentweb.BaseIndicatorView, com.just.agentweb.BaseIndicatorSpec
    public void show() {
        if (getVisibility() == 8) {
            setVisibility(0);
            this.mCurrentProgress = 0.0f;
            startAnim(false);
        }
    }
}
