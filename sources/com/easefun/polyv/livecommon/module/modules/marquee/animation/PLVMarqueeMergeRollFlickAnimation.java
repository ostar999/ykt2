package com.easefun.polyv.livecommon.module.modules.marquee.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.util.Log;
import android.view.animation.LinearInterpolator;
import java.util.HashMap;

/* loaded from: classes3.dex */
public class PLVMarqueeMergeRollFlickAnimation extends PLVMarqueeRollAnimation {
    private static final String TAG = "PLVMarqueeMergeRollFlic";
    private int tweenTime = 0;
    private ObjectAnimator flickObjectAnimation1 = new ObjectAnimator();
    private ObjectAnimator flickObjectAnimation2 = new ObjectAnimator();

    private void stopAnimation() {
        ObjectAnimator objectAnimator = this.flickObjectAnimation1;
        if (objectAnimator != null) {
            objectAnimator.cancel();
            this.flickObjectAnimation1.end();
        }
        ObjectAnimator objectAnimator2 = this.flickObjectAnimation2;
        if (objectAnimator2 != null) {
            objectAnimator2.cancel();
            this.flickObjectAnimation2.end();
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeRollAnimation, com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeAnimation
    public void destroy() {
        super.destroy();
        stopAnimation();
        this.flickObjectAnimation1 = null;
        this.flickObjectAnimation2 = null;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeRollAnimation, com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeAnimation
    public void pause() {
        super.pause();
        if (this.mainView == null) {
            return;
        }
        if (this.flickObjectAnimation1.isStarted()) {
            this.flickObjectAnimation1.pause();
        }
        if (this.flickObjectAnimation2.isStarted()) {
            this.flickObjectAnimation2.pause();
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeRollAnimation
    public void setAnimation() {
        super.setAnimation();
        float f2 = this.isAlwaysShowWhenRun ? 0.1f : 0.0f;
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.mainView, "alpha", f2, 1.0f);
        this.flickObjectAnimation1 = objectAnimatorOfFloat;
        objectAnimatorOfFloat.setDuration(this.tweenTime);
        this.flickObjectAnimation1.setInterpolator(new LinearInterpolator());
        this.flickObjectAnimation1.addListener(new Animator.AnimatorListener() { // from class: com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeMergeRollFlickAnimation.1
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                Log.i(PLVMarqueeMergeRollFlickAnimation.TAG, "onAnimationEnd: ");
                PLVMarqueeMergeRollFlickAnimation pLVMarqueeMergeRollFlickAnimation = PLVMarqueeMergeRollFlickAnimation.this;
                if (pLVMarqueeMergeRollFlickAnimation.animationStatus == 1) {
                    pLVMarqueeMergeRollFlickAnimation.flickObjectAnimation2.start();
                }
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
            }
        });
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(this.mainView, "alpha", 1.0f, f2);
        this.flickObjectAnimation2 = objectAnimatorOfFloat2;
        objectAnimatorOfFloat2.setDuration(this.tweenTime);
        this.flickObjectAnimation2.setInterpolator(new LinearInterpolator());
        this.flickObjectAnimation2.addListener(new Animator.AnimatorListener() { // from class: com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeMergeRollFlickAnimation.2
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                PLVMarqueeMergeRollFlickAnimation pLVMarqueeMergeRollFlickAnimation = PLVMarqueeMergeRollFlickAnimation.this;
                if (pLVMarqueeMergeRollFlickAnimation.animationStatus == 1) {
                    pLVMarqueeMergeRollFlickAnimation.flickObjectAnimation2.setStartDelay(PLVMarqueeMergeRollFlickAnimation.this.isAlwaysShowWhenRun ? 0L : r0.interval);
                    PLVMarqueeMergeRollFlickAnimation.this.flickObjectAnimation1.start();
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

    @Override // com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeRollAnimation, com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeAnimation
    public void setParams(HashMap<Integer, Integer> paramMap) {
        super.setParams(paramMap);
        this.tweenTime = paramMap.containsKey(6) ? paramMap.get(6).intValue() : 0;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeRollAnimation, com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeAnimation
    public void start() {
        super.start();
        if (this.mainView == null) {
            return;
        }
        if (this.animationStatus != 2) {
            setAnimation();
            this.flickObjectAnimation1.start();
            return;
        }
        if (this.flickObjectAnimation1.isPaused()) {
            this.flickObjectAnimation1.resume();
        } else if (this.flickObjectAnimation2.isPaused()) {
            this.flickObjectAnimation2.resume();
        }
        this.animationStatus = 1;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeRollAnimation, com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeAnimation
    public void stop() {
        super.stop();
        if (this.mainView == null) {
            return;
        }
        stopAnimation();
    }
}
