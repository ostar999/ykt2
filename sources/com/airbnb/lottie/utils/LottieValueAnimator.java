package com.airbnb.lottie.utils;

import android.view.Choreographer;
import androidx.annotation.FloatRange;
import androidx.annotation.MainThread;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.airbnb.lottie.L;
import com.airbnb.lottie.LottieComposition;

/* loaded from: classes2.dex */
public class LottieValueAnimator extends BaseLottieAnimator implements Choreographer.FrameCallback {

    @Nullable
    private LottieComposition composition;
    private float speed = 1.0f;
    private boolean speedReversedForRepeatMode = false;
    private long lastFrameTimeNs = 0;
    private float frame = 0.0f;
    private int repeatCount = 0;
    private float minFrame = -2.1474836E9f;
    private float maxFrame = 2.1474836E9f;

    @VisibleForTesting
    protected boolean running = false;

    private float getFrameDurationNs() {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            return Float.MAX_VALUE;
        }
        return (1.0E9f / lottieComposition.getFrameRate()) / Math.abs(this.speed);
    }

    private boolean isReversed() {
        return getSpeed() < 0.0f;
    }

    private void verifyFrame() {
        if (this.composition == null) {
            return;
        }
        float f2 = this.frame;
        if (f2 < this.minFrame || f2 > this.maxFrame) {
            throw new IllegalStateException(String.format("Frame must be [%f,%f]. It is %f", Float.valueOf(this.minFrame), Float.valueOf(this.maxFrame), Float.valueOf(this.frame)));
        }
    }

    @Override // android.animation.ValueAnimator, android.animation.Animator
    @MainThread
    public void cancel() {
        notifyCancel();
        removeFrameCallback();
    }

    public void clearComposition() {
        this.composition = null;
        this.minFrame = -2.1474836E9f;
        this.maxFrame = 2.1474836E9f;
    }

    @Override // android.view.Choreographer.FrameCallback
    public void doFrame(long j2) {
        postFrameCallback();
        if (this.composition == null || !isRunning()) {
            return;
        }
        L.beginSection("LottieValueAnimator#doFrame");
        float frameDurationNs = (this.lastFrameTimeNs != 0 ? j2 - r1 : 0L) / getFrameDurationNs();
        float f2 = this.frame;
        if (isReversed()) {
            frameDurationNs = -frameDurationNs;
        }
        float f3 = f2 + frameDurationNs;
        this.frame = f3;
        boolean z2 = !MiscUtils.contains(f3, getMinFrame(), getMaxFrame());
        this.frame = MiscUtils.clamp(this.frame, getMinFrame(), getMaxFrame());
        this.lastFrameTimeNs = j2;
        notifyUpdate();
        if (z2) {
            if (getRepeatCount() == -1 || this.repeatCount < getRepeatCount()) {
                notifyRepeat();
                this.repeatCount++;
                if (getRepeatMode() == 2) {
                    this.speedReversedForRepeatMode = !this.speedReversedForRepeatMode;
                    reverseAnimationSpeed();
                } else {
                    this.frame = isReversed() ? getMaxFrame() : getMinFrame();
                }
                this.lastFrameTimeNs = j2;
            } else {
                this.frame = this.speed < 0.0f ? getMinFrame() : getMaxFrame();
                removeFrameCallback();
                notifyEnd(isReversed());
            }
        }
        verifyFrame();
        L.endSection("LottieValueAnimator#doFrame");
    }

    @MainThread
    public void endAnimation() {
        removeFrameCallback();
        notifyEnd(isReversed());
    }

    @Override // android.animation.ValueAnimator
    @FloatRange(from = 0.0d, to = 1.0d)
    public float getAnimatedFraction() {
        float minFrame;
        float maxFrame;
        float minFrame2;
        if (this.composition == null) {
            return 0.0f;
        }
        if (isReversed()) {
            minFrame = getMaxFrame() - this.frame;
            maxFrame = getMaxFrame();
            minFrame2 = getMinFrame();
        } else {
            minFrame = this.frame - getMinFrame();
            maxFrame = getMaxFrame();
            minFrame2 = getMinFrame();
        }
        return minFrame / (maxFrame - minFrame2);
    }

    @Override // android.animation.ValueAnimator
    public Object getAnimatedValue() {
        return Float.valueOf(getAnimatedValueAbsolute());
    }

    @FloatRange(from = 0.0d, to = 1.0d)
    public float getAnimatedValueAbsolute() {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            return 0.0f;
        }
        return (this.frame - lottieComposition.getStartFrame()) / (this.composition.getEndFrame() - this.composition.getStartFrame());
    }

    @Override // android.animation.ValueAnimator, android.animation.Animator
    public long getDuration() {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            return 0L;
        }
        return (long) lottieComposition.getDuration();
    }

    public float getFrame() {
        return this.frame;
    }

    public float getMaxFrame() {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            return 0.0f;
        }
        float f2 = this.maxFrame;
        return f2 == 2.1474836E9f ? lottieComposition.getEndFrame() : f2;
    }

    public float getMinFrame() {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            return 0.0f;
        }
        float f2 = this.minFrame;
        return f2 == -2.1474836E9f ? lottieComposition.getStartFrame() : f2;
    }

    public float getSpeed() {
        return this.speed;
    }

    @Override // android.animation.ValueAnimator, android.animation.Animator
    public boolean isRunning() {
        return this.running;
    }

    @MainThread
    public void pauseAnimation() {
        removeFrameCallback();
    }

    @MainThread
    public void playAnimation() {
        this.running = true;
        notifyStart(isReversed());
        setFrame((int) (isReversed() ? getMaxFrame() : getMinFrame()));
        this.lastFrameTimeNs = 0L;
        this.repeatCount = 0;
        postFrameCallback();
    }

    public void postFrameCallback() {
        if (isRunning()) {
            removeFrameCallback(false);
            Choreographer.getInstance().postFrameCallback(this);
        }
    }

    @MainThread
    public void removeFrameCallback() {
        removeFrameCallback(true);
    }

    @MainThread
    public void resumeAnimation() {
        this.running = true;
        postFrameCallback();
        this.lastFrameTimeNs = 0L;
        if (isReversed() && getFrame() == getMinFrame()) {
            this.frame = getMaxFrame();
        } else {
            if (isReversed() || getFrame() != getMaxFrame()) {
                return;
            }
            this.frame = getMinFrame();
        }
    }

    public void reverseAnimationSpeed() {
        setSpeed(-getSpeed());
    }

    public void setComposition(LottieComposition lottieComposition) {
        boolean z2 = this.composition == null;
        this.composition = lottieComposition;
        if (z2) {
            setMinAndMaxFrames((int) Math.max(this.minFrame, lottieComposition.getStartFrame()), (int) Math.min(this.maxFrame, lottieComposition.getEndFrame()));
        } else {
            setMinAndMaxFrames((int) lottieComposition.getStartFrame(), (int) lottieComposition.getEndFrame());
        }
        float f2 = this.frame;
        this.frame = 0.0f;
        setFrame((int) f2);
        notifyUpdate();
    }

    public void setFrame(float f2) {
        if (this.frame == f2) {
            return;
        }
        this.frame = MiscUtils.clamp(f2, getMinFrame(), getMaxFrame());
        this.lastFrameTimeNs = 0L;
        notifyUpdate();
    }

    public void setMaxFrame(float f2) {
        setMinAndMaxFrames(this.minFrame, f2);
    }

    public void setMinAndMaxFrames(float f2, float f3) {
        if (f2 > f3) {
            throw new IllegalArgumentException(String.format("minFrame (%s) must be <= maxFrame (%s)", Float.valueOf(f2), Float.valueOf(f3)));
        }
        LottieComposition lottieComposition = this.composition;
        float startFrame = lottieComposition == null ? -3.4028235E38f : lottieComposition.getStartFrame();
        LottieComposition lottieComposition2 = this.composition;
        float endFrame = lottieComposition2 == null ? Float.MAX_VALUE : lottieComposition2.getEndFrame();
        this.minFrame = MiscUtils.clamp(f2, startFrame, endFrame);
        this.maxFrame = MiscUtils.clamp(f3, startFrame, endFrame);
        setFrame((int) MiscUtils.clamp(this.frame, f2, f3));
    }

    public void setMinFrame(int i2) {
        setMinAndMaxFrames(i2, (int) this.maxFrame);
    }

    @Override // android.animation.ValueAnimator
    public void setRepeatMode(int i2) {
        super.setRepeatMode(i2);
        if (i2 == 2 || !this.speedReversedForRepeatMode) {
            return;
        }
        this.speedReversedForRepeatMode = false;
        reverseAnimationSpeed();
    }

    public void setSpeed(float f2) {
        this.speed = f2;
    }

    @MainThread
    public void removeFrameCallback(boolean z2) {
        Choreographer.getInstance().removeFrameCallback(this);
        if (z2) {
            this.running = false;
        }
    }
}
