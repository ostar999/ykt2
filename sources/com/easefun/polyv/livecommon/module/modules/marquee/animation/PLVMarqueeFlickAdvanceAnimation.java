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
public class PLVMarqueeFlickAdvanceAnimation extends PLVMarqueeFlickAnimation {
    private static final String TAG = "PLVMarqueeFlickAdvanceA";

    @Nullable
    private View secondView;
    protected ObjectAnimator secondFlickObjectAnimator1 = new ObjectAnimator();
    protected ObjectAnimator secondFlickObjectAnimator2 = new ObjectAnimator();
    private boolean isSetSecondParams = false;

    /* JADX INFO: Access modifiers changed from: private */
    public void setSecondAnimation() {
        View view;
        if (this.isSetSecondParams || (view = this.secondView) == null) {
            return;
        }
        this.isSetSecondParams = true;
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, "alpha", 0.0f, 1.0f);
        this.secondFlickObjectAnimator1 = objectAnimatorOfFloat;
        objectAnimatorOfFloat.setDuration(this.tweenTime);
        this.secondFlickObjectAnimator1.setInterpolator(new LinearInterpolator());
        this.secondFlickObjectAnimator1.addListener(new Animator.AnimatorListener() { // from class: com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeFlickAdvanceAnimation.2
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                ObjectAnimator objectAnimator;
                ObjectAnimator objectAnimator2;
                PLVMarqueeFlickAdvanceAnimation pLVMarqueeFlickAdvanceAnimation = PLVMarqueeFlickAdvanceAnimation.this;
                if (pLVMarqueeFlickAdvanceAnimation.animationStatus == 1 || (((objectAnimator = pLVMarqueeFlickAdvanceAnimation.flickObjectAnimator1) != null && objectAnimator.isStarted()) || ((objectAnimator2 = PLVMarqueeFlickAdvanceAnimation.this.flickObjectAnimator2) != null && objectAnimator2.isStarted()))) {
                    PLVMarqueeFlickAdvanceAnimation pLVMarqueeFlickAdvanceAnimation2 = PLVMarqueeFlickAdvanceAnimation.this;
                    pLVMarqueeFlickAdvanceAnimation2.secondFlickObjectAnimator1.setStartDelay(pLVMarqueeFlickAdvanceAnimation2.isAlwaysShowWhenRun ? 0L : pLVMarqueeFlickAdvanceAnimation2.interval);
                    PLVMarqueeFlickAdvanceAnimation.this.secondFlickObjectAnimator2.start();
                }
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
                PLVMarqueeFlickAdvanceAnimation.this.setSecondActiveRect();
            }
        });
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(this.secondView, "alpha", 1.0f, 0.0f);
        this.secondFlickObjectAnimator2 = objectAnimatorOfFloat2;
        objectAnimatorOfFloat2.setDuration(this.tweenTime);
        this.secondFlickObjectAnimator2.setInterpolator(new LinearInterpolator());
        this.secondFlickObjectAnimator2.addListener(new Animator.AnimatorListener() { // from class: com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeFlickAdvanceAnimation.3
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                ObjectAnimator objectAnimator;
                ObjectAnimator objectAnimator2;
                PLVMarqueeFlickAdvanceAnimation pLVMarqueeFlickAdvanceAnimation = PLVMarqueeFlickAdvanceAnimation.this;
                if (pLVMarqueeFlickAdvanceAnimation.animationStatus == 1 || (((objectAnimator = pLVMarqueeFlickAdvanceAnimation.flickObjectAnimator1) != null && objectAnimator.isStarted()) || ((objectAnimator2 = PLVMarqueeFlickAdvanceAnimation.this.flickObjectAnimator2) != null && objectAnimator2.isStarted()))) {
                    PLVMarqueeFlickAdvanceAnimation.this.secondFlickObjectAnimator2.setStartDelay(r4.lifeTime);
                    PLVMarqueeFlickAdvanceAnimation.this.secondFlickObjectAnimator1.start();
                }
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopSecondAnimator() {
        ObjectAnimator objectAnimator = this.secondFlickObjectAnimator1;
        if (objectAnimator != null) {
            objectAnimator.cancel();
            this.secondFlickObjectAnimator1.end();
        }
        ObjectAnimator objectAnimator2 = this.secondFlickObjectAnimator2;
        if (objectAnimator2 != null) {
            objectAnimator2.cancel();
            this.secondFlickObjectAnimator2.end();
        }
        this.isSetSecondParams = false;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeFlickAnimation, com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeAnimation
    public void destroy() {
        super.destroy();
        stopSecondAnimator();
        this.secondFlickObjectAnimator1 = null;
        this.secondFlickObjectAnimator2 = null;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeFlickAnimation, com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeAnimation
    public void onParentSizeChanged(final View parentView) {
        super.onParentSizeChanged(parentView);
        View view = this.secondView;
        if (view == null) {
            return;
        }
        view.clearAnimation();
        this.secondView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeFlickAdvanceAnimation.1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                PLVMarqueeFlickAdvanceAnimation.this.screenWidth = parentView.getWidth();
                PLVMarqueeFlickAdvanceAnimation.this.screenHeight = parentView.getHeight();
                PLVMarqueeFlickAdvanceAnimation.this.secondView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                PLVMarqueeFlickAdvanceAnimation pLVMarqueeFlickAdvanceAnimation = PLVMarqueeFlickAdvanceAnimation.this;
                int i2 = pLVMarqueeFlickAdvanceAnimation.animationStatus;
                if (i2 == 1) {
                    pLVMarqueeFlickAdvanceAnimation.stopSecondAnimator();
                    PLVMarqueeFlickAdvanceAnimation.this.setSecondAnimation();
                    PLVMarqueeFlickAdvanceAnimation.this.secondFlickObjectAnimator1.start();
                } else if (i2 == 2) {
                    pLVMarqueeFlickAdvanceAnimation.stopSecondAnimator();
                    PLVMarqueeFlickAdvanceAnimation.this.setSecondAnimation();
                }
            }
        });
    }

    @Override // com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeFlickAnimation, com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeAnimation
    public void pause() {
        super.pause();
        if (this.secondView == null) {
            return;
        }
        if (this.secondFlickObjectAnimator1.isStarted()) {
            this.secondFlickObjectAnimator1.pause();
        }
        if (this.secondFlickObjectAnimator2.isStarted()) {
            this.secondFlickObjectAnimator2.pause();
        }
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
        double dRandom2 = Math.random();
        int i3 = this.screenWidth;
        marginLayoutParams.leftMargin = (int) (dRandom2 * (i3 - Math.min(i3, this.secondView.getWidth())));
        this.secondView.setLayoutParams(marginLayoutParams);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeFlickAnimation, com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeAnimation
    public void setViews(HashMap<Integer, View> viewMap) {
        super.setViews(viewMap);
        View view = viewMap.get(21);
        this.secondView = view;
        if (view == null) {
            return;
        }
        view.setAlpha(0.0f);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeFlickAnimation, com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeAnimation
    public void start() {
        if (this.secondView == null) {
            return;
        }
        if (this.animationStatus != 2) {
            setSecondAnimation();
            this.secondFlickObjectAnimator1.start();
        } else if (this.secondFlickObjectAnimator1.isPaused()) {
            this.secondFlickObjectAnimator1.resume();
        } else if (this.secondFlickObjectAnimator2.isPaused()) {
            this.secondFlickObjectAnimator2.resume();
        }
        super.start();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeFlickAnimation, com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeAnimation
    public void stop() {
        super.stop();
        if (this.secondView == null) {
            return;
        }
        stopSecondAnimator();
    }
}
