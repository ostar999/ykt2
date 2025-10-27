package com.beizi.ad.internal.animation;

import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Interpolator;

/* loaded from: classes2.dex */
public class Fade implements Transition {
    private Animation inAnimation;
    private Animation outAnimation;

    public Fade(long j2) {
        AccelerateInterpolator accelerateInterpolator = new AccelerateInterpolator();
        setInAnimation(accelerateInterpolator, j2);
        setOutAnimation(accelerateInterpolator, j2);
    }

    private void setInAnimation(Interpolator interpolator, long j2) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        this.inAnimation = alphaAnimation;
        alphaAnimation.setDuration(j2);
        this.inAnimation.setInterpolator(interpolator);
    }

    private void setOutAnimation(Interpolator interpolator, long j2) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        this.outAnimation = alphaAnimation;
        alphaAnimation.setDuration(j2);
        this.outAnimation.setInterpolator(interpolator);
    }

    @Override // com.beizi.ad.internal.animation.Transition
    public Animation getInAnimation() {
        return this.inAnimation;
    }

    @Override // com.beizi.ad.internal.animation.Transition
    public Animation getOutAnimation() {
        return this.outAnimation;
    }
}
