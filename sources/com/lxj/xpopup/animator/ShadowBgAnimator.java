package com.lxj.xpopup.animator;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.view.View;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

/* loaded from: classes4.dex */
public class ShadowBgAnimator extends PopupAnimator {
    public ArgbEvaluator argbEvaluator;
    public boolean isZeroDuration;
    public int shadowColor;
    public int startColor;

    public ShadowBgAnimator(View view, int i2, int i3) {
        super(view, i2);
        this.argbEvaluator = new ArgbEvaluator();
        this.startColor = 0;
        this.isZeroDuration = false;
        this.shadowColor = i3;
    }

    @Override // com.lxj.xpopup.animator.PopupAnimator
    public void animateDismiss() {
        if (this.animating) {
            return;
        }
        ValueAnimator valueAnimatorOfObject = ValueAnimator.ofObject(this.argbEvaluator, Integer.valueOf(this.shadowColor), Integer.valueOf(this.startColor));
        valueAnimatorOfObject.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.lxj.xpopup.animator.ShadowBgAnimator.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                ShadowBgAnimator.this.targetView.setBackgroundColor(((Integer) valueAnimator.getAnimatedValue()).intValue());
            }
        });
        observerAnimator(valueAnimatorOfObject);
        valueAnimatorOfObject.setInterpolator(new FastOutSlowInInterpolator());
        valueAnimatorOfObject.setDuration(this.isZeroDuration ? 0L : this.animationDuration).start();
    }

    @Override // com.lxj.xpopup.animator.PopupAnimator
    public void animateShow() {
        ValueAnimator valueAnimatorOfObject = ValueAnimator.ofObject(this.argbEvaluator, Integer.valueOf(this.startColor), Integer.valueOf(this.shadowColor));
        valueAnimatorOfObject.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.lxj.xpopup.animator.ShadowBgAnimator.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                ShadowBgAnimator.this.targetView.setBackgroundColor(((Integer) valueAnimator.getAnimatedValue()).intValue());
            }
        });
        valueAnimatorOfObject.setInterpolator(new FastOutSlowInInterpolator());
        valueAnimatorOfObject.setDuration(this.isZeroDuration ? 0L : this.animationDuration).start();
    }

    public void applyColorValue(float f2) {
        this.targetView.setBackgroundColor(Integer.valueOf(calculateBgColor(f2)).intValue());
    }

    public int calculateBgColor(float f2) {
        return ((Integer) this.argbEvaluator.evaluate(f2, Integer.valueOf(this.startColor), Integer.valueOf(this.shadowColor))).intValue();
    }

    @Override // com.lxj.xpopup.animator.PopupAnimator
    public void initAnimator() {
        this.targetView.setBackgroundColor(this.startColor);
    }

    public ShadowBgAnimator() {
        this.argbEvaluator = new ArgbEvaluator();
        this.startColor = 0;
        this.isZeroDuration = false;
    }
}
