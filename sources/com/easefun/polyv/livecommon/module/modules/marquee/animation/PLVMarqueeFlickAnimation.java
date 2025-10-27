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
public class PLVMarqueeFlickAnimation extends PLVMarqueeAnimation {
    private static final String TAG = "PLVMarqueeFlickAnimatio";

    @Nullable
    protected RelativeLayout.LayoutParams layoutParams;

    @Nullable
    protected View mainView;
    protected int lifeTime = 0;
    protected int interval = 0;
    protected int tweenTime = 0;
    private int repeatCount = -1;
    private int repeatMode = 1;
    protected ObjectAnimator flickObjectAnimator1 = new ObjectAnimator();
    protected ObjectAnimator flickObjectAnimator2 = new ObjectAnimator();

    private void stopAnimation() {
        ObjectAnimator objectAnimator = this.flickObjectAnimator1;
        if (objectAnimator != null) {
            objectAnimator.cancel();
            this.flickObjectAnimator1.end();
        }
        ObjectAnimator objectAnimator2 = this.flickObjectAnimator2;
        if (objectAnimator2 != null) {
            objectAnimator2.cancel();
            this.flickObjectAnimator2.end();
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeAnimation
    public void destroy() {
        stopAnimation();
        this.flickObjectAnimator1 = null;
        this.flickObjectAnimator2 = null;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeAnimation
    public void onParentSizeChanged(final View parentView) {
        View view = this.mainView;
        if (view == null) {
            return;
        }
        view.clearAnimation();
        this.mainView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeFlickAnimation.1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                PLVMarqueeFlickAnimation.this.screenWidth = parentView.getWidth();
                PLVMarqueeFlickAnimation.this.screenHeight = parentView.getHeight();
                PLVMarqueeFlickAnimation.this.mainView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                PLVMarqueeFlickAnimation pLVMarqueeFlickAnimation = PLVMarqueeFlickAnimation.this;
                int i2 = pLVMarqueeFlickAnimation.animationStatus;
                if (i2 == 1) {
                    pLVMarqueeFlickAnimation.stop();
                    PLVMarqueeFlickAnimation.this.start();
                } else if (i2 == 2) {
                    pLVMarqueeFlickAnimation.stop();
                    PLVMarqueeFlickAnimation.this.setAnimation();
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
        if (this.flickObjectAnimator1.isStarted()) {
            this.flickObjectAnimator1.pause();
        }
        if (this.flickObjectAnimator2.isStarted()) {
            this.flickObjectAnimator2.pause();
        }
        if (this.isHiddenWhenPause) {
            this.mainView.setAlpha(0.0f);
        }
    }

    public void setAnimation() {
        View view;
        if (this.animationStatus == 1 || (view = this.mainView) == null) {
            return;
        }
        this.animationStatus = 1;
        float f2 = this.isAlwaysShowWhenRun ? 0.1f : 0.0f;
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, "alpha", f2, 1.0f);
        this.flickObjectAnimator1 = objectAnimatorOfFloat;
        objectAnimatorOfFloat.setDuration(this.tweenTime);
        this.flickObjectAnimator1.setInterpolator(new LinearInterpolator());
        this.flickObjectAnimator1.addListener(new Animator.AnimatorListener() { // from class: com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeFlickAnimation.2
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                PLVMarqueeFlickAnimation.this.mainView.setAlpha(1.0f);
                PLVMarqueeFlickAnimation pLVMarqueeFlickAnimation = PLVMarqueeFlickAnimation.this;
                if (pLVMarqueeFlickAnimation.animationStatus == 1) {
                    pLVMarqueeFlickAnimation.flickObjectAnimator1.setStartDelay(pLVMarqueeFlickAnimation.isAlwaysShowWhenRun ? 0L : pLVMarqueeFlickAnimation.interval);
                    PLVMarqueeFlickAnimation.this.flickObjectAnimator2.start();
                }
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
                PLVMarqueeFlickAnimation.this.mainView.setAlpha(0.0f);
                PLVMarqueeFlickAnimation.this.setMainActiveRect();
                PLVMarqueeFlickAnimation pLVMarqueeFlickAnimation = PLVMarqueeFlickAnimation.this;
                pLVMarqueeFlickAnimation.mainView.setLayoutParams(pLVMarqueeFlickAnimation.layoutParams);
            }
        });
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(this.mainView, "alpha", 1.0f, f2);
        this.flickObjectAnimator2 = objectAnimatorOfFloat2;
        objectAnimatorOfFloat2.setDuration(this.tweenTime);
        this.flickObjectAnimator2.setInterpolator(new LinearInterpolator());
        this.flickObjectAnimator2.addListener(new Animator.AnimatorListener() { // from class: com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeFlickAnimation.3
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                PLVMarqueeFlickAnimation.this.mainView.setAlpha(0.0f);
                PLVMarqueeFlickAnimation pLVMarqueeFlickAnimation = PLVMarqueeFlickAnimation.this;
                if (pLVMarqueeFlickAnimation.animationStatus == 1) {
                    pLVMarqueeFlickAnimation.flickObjectAnimator2.setStartDelay(pLVMarqueeFlickAnimation.lifeTime);
                    PLVMarqueeFlickAnimation.this.flickObjectAnimator1.start();
                }
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
                PLVMarqueeFlickAnimation.this.mainView.setAlpha(1.0f);
            }
        });
    }

    public void setMainActiveRect() {
        RelativeLayout.LayoutParams layoutParams = this.layoutParams;
        if (layoutParams == null) {
            return;
        }
        double dRandom = Math.random();
        int i2 = this.screenHeight;
        layoutParams.topMargin = (int) (dRandom * (i2 - Math.min(i2, this.viewHeight)));
        RelativeLayout.LayoutParams layoutParams2 = this.layoutParams;
        double dRandom2 = Math.random();
        int i3 = this.screenWidth;
        layoutParams2.leftMargin = (int) (dRandom2 * (i3 - Math.min(i3, this.viewWidth)));
    }

    @Override // com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeAnimation
    public void setParams(HashMap<Integer, Integer> paramMap) {
        this.viewWidth = paramMap.containsKey(0) ? paramMap.get(0).intValue() : 0;
        this.viewHeight = paramMap.containsKey(1) ? paramMap.get(1).intValue() : 0;
        this.screenWidth = paramMap.containsKey(2) ? paramMap.get(2).intValue() : 0;
        this.screenHeight = paramMap.containsKey(3) ? paramMap.get(3).intValue() : 0;
        this.lifeTime = paramMap.containsKey(5) ? paramMap.get(5).intValue() : 0;
        this.interval = paramMap.containsKey(7) ? paramMap.get(7).intValue() : 0;
        this.tweenTime = paramMap.containsKey(6) ? paramMap.get(6).intValue() : 0;
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
        this.mainView.setAlpha(0.0f);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeAnimation
    public void start() {
        if (this.mainView == null) {
            return;
        }
        if (this.animationStatus != 2) {
            setAnimation();
            this.flickObjectAnimator1.start();
            return;
        }
        if (this.flickObjectAnimator1.isPaused()) {
            this.flickObjectAnimator1.resume();
        } else if (this.flickObjectAnimator2.isPaused()) {
            this.flickObjectAnimator2.resume();
        }
        this.animationStatus = 1;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeAnimation
    public void stop() {
        if (this.mainView == null) {
            return;
        }
        this.animationStatus = 3;
        stopAnimation();
    }
}
