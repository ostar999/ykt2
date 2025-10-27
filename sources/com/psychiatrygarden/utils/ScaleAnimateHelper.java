package com.psychiatrygarden.utils;

import android.animation.ValueAnimator;
import android.view.View;

/* loaded from: classes6.dex */
public class ScaleAnimateHelper implements AnimateHelper {
    public int mCurrentState = 1;
    public View mTarget;

    private ScaleAnimateHelper(View view) {
        this.mTarget = view;
    }

    public static ScaleAnimateHelper get(View view) {
        return new ScaleAnimateHelper(view);
    }

    @Override // com.psychiatrygarden.utils.AnimateHelper
    public int getState() {
        return this.mCurrentState;
    }

    @Override // com.psychiatrygarden.utils.AnimateHelper
    public void hide() {
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.mTarget.getScaleX(), 0.0f);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.utils.ScaleAnimateHelper.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                ScaleAnimateHelper.this.mTarget.setScaleX(fFloatValue);
                ScaleAnimateHelper.this.mTarget.setScaleY(fFloatValue);
            }
        });
        valueAnimatorOfFloat.setDuration(300L);
        valueAnimatorOfFloat.start();
        this.mCurrentState = 0;
    }

    @Override // com.psychiatrygarden.utils.AnimateHelper
    public void setMode(int modeBottom) {
    }

    @Override // com.psychiatrygarden.utils.AnimateHelper
    public void setStartY(float y2) {
    }

    @Override // com.psychiatrygarden.utils.AnimateHelper
    public void show() {
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.mTarget.getScaleX(), 1.0f);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.utils.ScaleAnimateHelper.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                ScaleAnimateHelper.this.mTarget.setScaleX(fFloatValue);
                ScaleAnimateHelper.this.mTarget.setScaleY(fFloatValue);
            }
        });
        valueAnimatorOfFloat.setDuration(300L);
        valueAnimatorOfFloat.start();
        this.mCurrentState = 1;
    }
}
