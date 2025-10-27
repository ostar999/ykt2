package com.easefun.polyv.livecommon.module.modules.marquee.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;
import androidx.annotation.Nullable;
import java.util.HashMap;

/* loaded from: classes3.dex */
public class PLVMarqueeRollAdvanceAnimation extends PLVMarqueeRollAnimation {
    private static final String TAG = "PLVMarqueeRollAdvanceAn";

    @Nullable
    private View secondView;
    protected ObjectAnimator secondAnimator = new ObjectAnimator();
    private boolean isSetSecondParams = false;

    /* JADX INFO: Access modifiers changed from: private */
    public void setSecondAnimation() {
        View view;
        if (this.isSetSecondParams || (view = this.secondView) == null) {
            return;
        }
        this.isSetSecondParams = true;
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.secondView, "translationX", this.isAlwaysShowWhenRun ? this.screenWidth - view.getWidth() : this.screenWidth, this.isAlwaysShowWhenRun ? 0.0f : -this.secondView.getWidth());
        this.secondAnimator = objectAnimatorOfFloat;
        objectAnimatorOfFloat.setDuration(this.duration);
        this.secondAnimator.setInterpolator(new LinearInterpolator());
        this.secondAnimator.addListener(new Animator.AnimatorListener() { // from class: com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeRollAdvanceAnimation.2
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                ObjectAnimator objectAnimator;
                PLVMarqueeRollAdvanceAnimation.this.secondView.setAlpha(0.0f);
                PLVMarqueeRollAdvanceAnimation pLVMarqueeRollAdvanceAnimation = PLVMarqueeRollAdvanceAnimation.this;
                if (pLVMarqueeRollAdvanceAnimation.animationStatus == 1 || ((objectAnimator = pLVMarqueeRollAdvanceAnimation.mainAnimator) != null && objectAnimator.isStarted())) {
                    PLVMarqueeRollAdvanceAnimation pLVMarqueeRollAdvanceAnimation2 = PLVMarqueeRollAdvanceAnimation.this;
                    pLVMarqueeRollAdvanceAnimation2.secondAnimator.setStartDelay(pLVMarqueeRollAdvanceAnimation2.isAlwaysShowWhenRun ? 0L : pLVMarqueeRollAdvanceAnimation2.interval);
                    animation.start();
                }
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
                PLVMarqueeRollAdvanceAnimation.this.secondView.setAlpha(1.0f);
                PLVMarqueeRollAdvanceAnimation.this.setSecondActiveRect();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopSecondAnimator() {
        ObjectAnimator objectAnimator = this.secondAnimator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
            this.secondAnimator.end();
        }
        this.isSetSecondParams = false;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeRollAnimation, com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeAnimation
    public void destroy() {
        super.destroy();
        this.secondAnimator = null;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeRollAnimation, com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeAnimation
    public void onParentSizeChanged(final View parentView) {
        super.onParentSizeChanged(parentView);
        View view = this.secondView;
        if (view == null) {
            return;
        }
        view.clearAnimation();
        this.secondView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeRollAdvanceAnimation.1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                PLVMarqueeRollAdvanceAnimation.this.screenWidth = parentView.getWidth();
                PLVMarqueeRollAdvanceAnimation.this.screenHeight = parentView.getHeight();
                PLVMarqueeRollAdvanceAnimation.this.secondView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                PLVMarqueeRollAdvanceAnimation pLVMarqueeRollAdvanceAnimation = PLVMarqueeRollAdvanceAnimation.this;
                int i2 = pLVMarqueeRollAdvanceAnimation.animationStatus;
                if (i2 == 1) {
                    pLVMarqueeRollAdvanceAnimation.stopSecondAnimator();
                    PLVMarqueeRollAdvanceAnimation.this.setSecondAnimation();
                    PLVMarqueeRollAdvanceAnimation.this.secondAnimator.start();
                } else if (i2 == 2) {
                    pLVMarqueeRollAdvanceAnimation.stopSecondAnimator();
                    PLVMarqueeRollAdvanceAnimation.this.setSecondAnimation();
                }
            }
        });
    }

    @Override // com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeRollAnimation, com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeAnimation
    public void pause() {
        super.pause();
        if (this.secondView == null) {
            return;
        }
        this.secondAnimator.pause();
        this.secondView.setAlpha(0.0f);
    }

    public void setSecondActiveRect() {
        View view = this.secondView;
        if (view == null) {
            return;
        }
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        double dRandom = Math.random();
        int i2 = this.screenHeight;
        marginLayoutParams.topMargin = (int) (dRandom * (i2 - Math.min(i2, this.secondView.getHeight())));
        this.secondView.setLayoutParams(marginLayoutParams);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeRollAnimation, com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeAnimation
    public void setViews(HashMap<Integer, View> viewMap) {
        super.setViews(viewMap);
        View view = viewMap.get(21);
        this.secondView = view;
        if (view == null) {
            return;
        }
        view.setAlpha(0.0f);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeRollAnimation, com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeAnimation
    public void start() {
        if (this.secondView == null) {
            return;
        }
        if (this.animationStatus == 2) {
            this.secondAnimator.resume();
            if (this.secondAnimator.isRunning()) {
                this.secondView.setAlpha(1.0f);
            }
        } else {
            setSecondAnimation();
            this.secondAnimator.start();
        }
        super.start();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeRollAnimation, com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeAnimation
    public void stop() {
        super.stop();
        stopSecondAnimator();
    }
}
