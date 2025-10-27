package com.easefun.polyv.livecommon.module.modules.marquee.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;
import androidx.annotation.Nullable;
import java.util.HashMap;

/* loaded from: classes3.dex */
public class PLVMarqueeRollAnimation extends PLVMarqueeAnimation {
    private static final String TAG = "PLVMarqueeRollAnimation";
    protected int duration;
    protected int interval;

    @Nullable
    protected RelativeLayout.LayoutParams layoutParams;
    protected ObjectAnimator mainAnimator = new ObjectAnimator();

    @Nullable
    protected View mainView;

    @Override // com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeAnimation
    public void destroy() {
        ObjectAnimator objectAnimator = this.mainAnimator;
        if (objectAnimator != null) {
            objectAnimator.removeAllListeners();
        }
        this.mainAnimator = null;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeAnimation
    public void onParentSizeChanged(final View parentView) {
        View view = this.mainView;
        if (view == null) {
            return;
        }
        view.clearAnimation();
        this.mainView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeRollAnimation.1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                PLVMarqueeRollAnimation.this.screenWidth = parentView.getWidth();
                PLVMarqueeRollAnimation.this.screenHeight = parentView.getHeight();
                PLVMarqueeRollAnimation.this.mainView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                PLVMarqueeRollAnimation pLVMarqueeRollAnimation = PLVMarqueeRollAnimation.this;
                int i2 = pLVMarqueeRollAnimation.animationStatus;
                if (i2 == 1) {
                    pLVMarqueeRollAnimation.stop();
                    PLVMarqueeRollAnimation.this.start();
                } else if (i2 == 2) {
                    pLVMarqueeRollAnimation.stop();
                    PLVMarqueeRollAnimation.this.setAnimation();
                }
            }
        });
    }

    @Override // com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeAnimation
    public void pause() {
        if (this.mainView == null) {
            return;
        }
        this.animationStatus = 2;
        this.mainAnimator.pause();
        if (this.isHiddenWhenPause) {
            this.mainView.setVisibility(8);
        }
    }

    public void setActiveRect() {
        RelativeLayout.LayoutParams layoutParams = this.layoutParams;
        if (layoutParams == null) {
            return;
        }
        double dRandom = Math.random();
        int i2 = this.screenHeight;
        layoutParams.topMargin = (int) (dRandom * (i2 - Math.min(i2, this.viewHeight)));
    }

    public void setAnimation() {
        View view;
        if (this.animationStatus == 1 || (view = this.mainView) == null) {
            return;
        }
        this.animationStatus = 1;
        boolean z2 = this.isAlwaysShowWhenRun;
        int i2 = this.screenWidth;
        if (z2) {
            i2 -= this.viewWidth;
        }
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, "translationX", i2, z2 ? 0.0f : -this.viewWidth);
        this.mainAnimator = objectAnimatorOfFloat;
        objectAnimatorOfFloat.setDuration(this.duration);
        this.mainAnimator.setInterpolator(new LinearInterpolator());
        this.mainAnimator.addListener(new Animator.AnimatorListener() { // from class: com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeRollAnimation.2
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                PLVMarqueeRollAnimation.this.mainView.setVisibility(8);
                PLVMarqueeRollAnimation pLVMarqueeRollAnimation = PLVMarqueeRollAnimation.this;
                if (pLVMarqueeRollAnimation.animationStatus == 1) {
                    pLVMarqueeRollAnimation.mainAnimator.setStartDelay(pLVMarqueeRollAnimation.isAlwaysShowWhenRun ? 0L : pLVMarqueeRollAnimation.interval);
                    animation.start();
                }
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
                PLVMarqueeRollAnimation.this.mainView.setVisibility(0);
                PLVMarqueeRollAnimation.this.setActiveRect();
                PLVMarqueeRollAnimation pLVMarqueeRollAnimation = PLVMarqueeRollAnimation.this;
                pLVMarqueeRollAnimation.mainView.setLayoutParams(pLVMarqueeRollAnimation.layoutParams);
            }
        });
    }

    @Override // com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeAnimation
    public void setParams(HashMap<Integer, Integer> paramMap) {
        this.viewWidth = paramMap.containsKey(0) ? paramMap.get(0).intValue() : 0;
        this.viewHeight = paramMap.containsKey(1) ? paramMap.get(1).intValue() : 0;
        this.screenWidth = paramMap.containsKey(2) ? paramMap.get(2).intValue() : 0;
        this.screenHeight = paramMap.containsKey(3) ? paramMap.get(3).intValue() : 0;
        this.duration = paramMap.containsKey(4) ? paramMap.get(4).intValue() : 0;
        this.interval = paramMap.containsKey(7) ? paramMap.get(7).intValue() : 0;
        if (paramMap.containsKey(10)) {
            this.isAlwaysShowWhenRun = paramMap.get(10).intValue() == 1;
        }
        if (paramMap.containsKey(11)) {
            this.isHiddenWhenPause = paramMap.get(11).intValue() == 1;
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeAnimation
    public void setViews(HashMap<Integer, View> viewMap) {
        View view = viewMap.get(20);
        this.mainView = view;
        if (view == null) {
            return;
        }
        this.layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        this.mainView.setVisibility(8);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeAnimation
    public void start() {
        if (this.mainView == null) {
            return;
        }
        if (this.animationStatus != 2) {
            setAnimation();
            this.mainAnimator.start();
            return;
        }
        this.mainAnimator.resume();
        this.animationStatus = 1;
        if (this.mainAnimator.isRunning()) {
            this.mainView.setVisibility(0);
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeAnimation
    public void stop() {
        if (this.mainView == null) {
            return;
        }
        this.animationStatus = 3;
        ObjectAnimator objectAnimator = this.mainAnimator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
            this.mainAnimator.end();
        }
    }
}
