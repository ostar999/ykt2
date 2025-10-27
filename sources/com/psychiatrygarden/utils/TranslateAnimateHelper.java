package com.psychiatrygarden.utils;

import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

/* loaded from: classes6.dex */
public class TranslateAnimateHelper implements AnimateHelper {
    public static int MODE_BOTTOM = 2333;
    public static int MODE_TITLE = 233;
    private float mFirstY;
    private float mMargin;
    public float mStartY;
    public View mTarget;
    public int mCurrentState = 1;
    public int mMode = MODE_TITLE;

    private TranslateAnimateHelper(View view) {
        this.mFirstY = 0.0f;
        this.mTarget = view;
        this.mFirstY = view.getY();
        this.mMargin = ((ViewGroup.MarginLayoutParams) ((CoordinatorLayout.LayoutParams) this.mTarget.getLayoutParams())).topMargin + ((ViewGroup.MarginLayoutParams) ((CoordinatorLayout.LayoutParams) this.mTarget.getLayoutParams())).bottomMargin;
    }

    public static TranslateAnimateHelper get(View target) {
        return new TranslateAnimateHelper(target);
    }

    private void hideBottom() {
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.mTarget.getY(), this.mFirstY + this.mTarget.getHeight() + this.mMargin);
        valueAnimatorOfFloat.setDuration(300L);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.utils.TranslateAnimateHelper.4
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                TranslateAnimateHelper.this.mTarget.setY(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        valueAnimatorOfFloat.start();
        this.mCurrentState = 0;
    }

    private void hideTitle() {
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.mTarget.getY(), -this.mTarget.getHeight());
        valueAnimatorOfFloat.setDuration(300L);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.utils.TranslateAnimateHelper.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                TranslateAnimateHelper.this.mTarget.setY(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        valueAnimatorOfFloat.start();
        this.mCurrentState = 0;
    }

    private void setState(int state) {
        this.mCurrentState = state;
    }

    private void showBottom() {
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.mTarget.getY(), this.mFirstY);
        valueAnimatorOfFloat.setDuration(300L);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.utils.TranslateAnimateHelper.3
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                TranslateAnimateHelper.this.mTarget.setY(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        valueAnimatorOfFloat.start();
        this.mCurrentState = 1;
    }

    private void showTitle() {
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.mTarget.getY(), 0.0f);
        valueAnimatorOfFloat.setDuration(300L);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.utils.TranslateAnimateHelper.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                TranslateAnimateHelper.this.mTarget.setY(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        valueAnimatorOfFloat.start();
        this.mCurrentState = 1;
    }

    @Override // com.psychiatrygarden.utils.AnimateHelper
    public int getState() {
        return this.mCurrentState;
    }

    @Override // com.psychiatrygarden.utils.AnimateHelper
    public void hide() {
        int i2 = this.mMode;
        if (i2 == MODE_TITLE) {
            hideTitle();
        } else if (i2 == MODE_BOTTOM) {
            hideBottom();
        }
    }

    @Override // com.psychiatrygarden.utils.AnimateHelper
    public void setMode(int mode) {
        this.mMode = mode;
    }

    @Override // com.psychiatrygarden.utils.AnimateHelper
    public void setStartY(float y2) {
        this.mStartY = y2;
    }

    @Override // com.psychiatrygarden.utils.AnimateHelper
    public void show() {
        int i2 = this.mMode;
        if (i2 == MODE_TITLE) {
            showTitle();
        } else if (i2 == MODE_BOTTOM) {
            showBottom();
        }
    }
}
