package com.psychiatrygarden.utils;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import com.psychiatrygarden.SimpleAnimatorListener;

/* loaded from: classes6.dex */
public class AnimUtil {
    public static void collapseView(final View view, int redDotWidth, final View secondView) {
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(redDotWidth, 0);
        valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.utils.b
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                AnimUtil.lambda$collapseView$0(secondView, view, valueAnimator);
            }
        });
        valueAnimatorOfInt.setDuration(300L);
        valueAnimatorOfInt.start();
    }

    public static void expandView(final View view, int redDotWidth, final View secondView) {
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(0, redDotWidth);
        valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.utils.a
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                AnimUtil.lambda$expandView$1(secondView, view, valueAnimator);
            }
        });
        valueAnimatorOfInt.setDuration(500L);
        valueAnimatorOfInt.start();
    }

    public static void fromBottomToTopAnim(View view) {
        AnimationSet animationSet = new AnimationSet(true);
        TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 1.0f, 1, 0.0f);
        translateAnimation.setDuration(300L);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(300L);
        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(alphaAnimation);
        view.setAnimation(animationSet);
    }

    public static void fromTopToBottomAnim(View view) {
        AnimationSet animationSet = new AnimationSet(true);
        TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, -1.0f, 1, 0.0f);
        translateAnimation.setDuration(300L);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(300L);
        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(alphaAnimation);
        view.setAnimation(animationSet);
    }

    public static void fromTopToBottomAnims(View view) {
        AnimationSet animationSet = new AnimationSet(true);
        TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.0f, 1, 1.0f);
        translateAnimation.setDuration(300L);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(300L);
        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(alphaAnimation);
        view.setAnimation(animationSet);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$collapseView$0(View view, View view2, ValueAnimator valueAnimator) {
        int iIntValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
        if (iIntValue == 0) {
            view.setVisibility(0);
        }
        ViewGroup.LayoutParams layoutParams = view2.getLayoutParams();
        layoutParams.width = iIntValue;
        view2.setLayoutParams(layoutParams);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$expandView$1(View view, View view2, ValueAnimator valueAnimator) {
        int iIntValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
        if (iIntValue == 0) {
            view.setVisibility(8);
        }
        ViewGroup.LayoutParams layoutParams = view2.getLayoutParams();
        layoutParams.width = iIntValue;
        view2.setLayoutParams(layoutParams);
    }

    public static void slideIn(View view, final View view2, int extraOffset) {
        view.animate().translationX(-extraOffset).setDuration(300L).setInterpolator(new AccelerateInterpolator()).setListener(new SimpleAnimatorListener() { // from class: com.psychiatrygarden.utils.AnimUtil.1
            @Override // com.psychiatrygarden.SimpleAnimatorListener, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                view2.setVisibility(8);
            }
        }).start();
    }

    public static void slideOut(View view, final View view2, int extraOffset) {
        view.animate().translationX(view.getWidth() + extraOffset).setListener(new SimpleAnimatorListener() { // from class: com.psychiatrygarden.utils.AnimUtil.2
            @Override // com.psychiatrygarden.SimpleAnimatorListener, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                view2.setVisibility(0);
            }
        }).setDuration(300L).setInterpolator(new AccelerateInterpolator()).start();
    }
}
