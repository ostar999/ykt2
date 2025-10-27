package com.lxj.xpopup.animator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewPropertyAnimator;
import com.lxj.xpopup.enums.PopupAnimation;

/* loaded from: classes4.dex */
public abstract class PopupAnimator {
    protected boolean animating;
    public int animationDuration;
    public PopupAnimation popupAnimation;
    public View targetView;

    public PopupAnimator() {
        this.animating = false;
        this.animationDuration = 0;
    }

    public abstract void animateDismiss();

    public abstract void animateShow();

    public int getDuration() {
        return this.animationDuration;
    }

    public abstract void initAnimator();

    public ValueAnimator observerAnimator(ValueAnimator valueAnimator) {
        valueAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.lxj.xpopup.animator.PopupAnimator.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                PopupAnimator.this.animating = false;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                super.onAnimationStart(animator);
                PopupAnimator.this.animating = true;
            }
        });
        return valueAnimator;
    }

    public ViewPropertyAnimator observerAnimator(ViewPropertyAnimator viewPropertyAnimator) {
        viewPropertyAnimator.setListener(new AnimatorListenerAdapter() { // from class: com.lxj.xpopup.animator.PopupAnimator.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                PopupAnimator.this.animating = false;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                super.onAnimationStart(animator);
                PopupAnimator.this.animating = true;
            }
        });
        return viewPropertyAnimator;
    }

    public PopupAnimator(View view, int i2) {
        this(view, i2, null);
    }

    public PopupAnimator(View view, int i2, PopupAnimation popupAnimation) {
        this.animating = false;
        this.targetView = view;
        this.animationDuration = i2;
        this.popupAnimation = popupAnimation;
    }
}
