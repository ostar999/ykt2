package com.easefun.polyv.livecommon.ui.widget.menudrawer;

import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

/* loaded from: classes3.dex */
class FloatScroller {
    private float mCurr;
    private float mDeltaX;
    private int mDuration;
    private float mDurationReciprocal;
    private float mFinal;
    private boolean mFinished = true;
    private Interpolator mInterpolator;
    private float mStart;
    private long mStartTime;

    public FloatScroller(Interpolator interpolator) {
        this.mInterpolator = interpolator;
    }

    public void abortAnimation() {
        this.mCurr = this.mFinal;
        this.mFinished = true;
    }

    public boolean computeScrollOffset() {
        if (this.mFinished) {
            return false;
        }
        int iCurrentAnimationTimeMillis = (int) (AnimationUtils.currentAnimationTimeMillis() - this.mStartTime);
        if (iCurrentAnimationTimeMillis < this.mDuration) {
            this.mCurr = this.mStart + (this.mInterpolator.getInterpolation(iCurrentAnimationTimeMillis * this.mDurationReciprocal) * this.mDeltaX);
        } else {
            this.mCurr = this.mFinal;
            this.mFinished = true;
        }
        return true;
    }

    public void extendDuration(int extend) {
        int iTimePassed = timePassed() + extend;
        this.mDuration = iTimePassed;
        this.mDurationReciprocal = 1.0f / iTimePassed;
        this.mFinished = false;
    }

    public final void forceFinished(boolean finished) {
        this.mFinished = finished;
    }

    public final float getCurr() {
        return this.mCurr;
    }

    public final int getDuration() {
        return this.mDuration;
    }

    public final float getFinal() {
        return this.mFinal;
    }

    public final float getStart() {
        return this.mStart;
    }

    public final boolean isFinished() {
        return this.mFinished;
    }

    public void setFinal(float newVal) {
        this.mFinal = newVal;
        this.mDeltaX = newVal - this.mStart;
        this.mFinished = false;
    }

    public void startScroll(float start, float delta, int duration) {
        this.mFinished = false;
        this.mDuration = duration;
        this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
        this.mStart = start;
        this.mFinal = start + delta;
        this.mDeltaX = delta;
        this.mDurationReciprocal = 1.0f / this.mDuration;
    }

    public int timePassed() {
        return (int) (AnimationUtils.currentAnimationTimeMillis() - this.mStartTime);
    }
}
