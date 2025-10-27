package com.easefun.polyv.livecommon.ui.widget.menudrawer;

import android.content.Context;
import android.view.ViewConfiguration;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

/* loaded from: classes3.dex */
class Scroller {
    private static final float ALPHA = 800.0f;
    private static final int DEFAULT_DURATION = 250;
    private static final float END_TENSION = 0.6f;
    private static final int FLING_MODE = 1;
    private static final int NB_SAMPLES = 100;
    private static final int SCROLL_MODE = 0;
    private static final float START_TENSION = 0.4f;
    private static float sViscousFluidNormalize;
    private static float sViscousFluidScale;
    private int mCurrX;
    private int mCurrY;
    private float mDeceleration;
    private float mDeltaX;
    private float mDeltaY;
    private int mDuration;
    private float mDurationReciprocal;
    private int mFinalX;
    private int mFinalY;
    private boolean mFinished;
    private boolean mFlywheel;
    private Interpolator mInterpolator;
    private int mMaxX;
    private int mMaxY;
    private int mMinX;
    private int mMinY;
    private int mMode;
    private final float mPpi;
    private long mStartTime;
    private int mStartX;
    private int mStartY;
    private float mVelocity;
    private static final float DECELERATION_RATE = (float) (Math.log(0.75d) / Math.log(0.9d));
    private static final float[] SPLINE = new float[101];

    static {
        float f2;
        float f3;
        float f4 = 0.0f;
        for (int i2 = 0; i2 <= 100; i2++) {
            float f5 = i2 / 100.0f;
            float f6 = 1.0f;
            while (true) {
                float f7 = ((f6 - f4) / 2.0f) + f4;
                float f8 = 1.0f - f7;
                f2 = 3.0f * f7 * f8;
                f3 = f7 * f7 * f7;
                float f9 = (((f8 * START_TENSION) + (END_TENSION * f7)) * f2) + f3;
                if (Math.abs(f9 - f5) < 1.0E-5d) {
                    break;
                } else if (f9 > f5) {
                    f6 = f7;
                } else {
                    f4 = f7;
                }
            }
            SPLINE[i2] = f2 + f3;
        }
        SPLINE[100] = 1.0f;
        sViscousFluidScale = 8.0f;
        sViscousFluidNormalize = 1.0f;
        sViscousFluidNormalize = 1.0f / viscousFluid(1.0f);
    }

    public Scroller(Context context) {
        this(context, null);
    }

    private float computeDeceleration(float friction) {
        return this.mPpi * 386.0878f * friction;
    }

    public static float viscousFluid(float x2) {
        float f2 = x2 * sViscousFluidScale;
        return (f2 < 1.0f ? f2 - (1.0f - ((float) Math.exp(-f2))) : 0.36787945f + ((1.0f - ((float) Math.exp(1.0f - f2))) * 0.63212055f)) * sViscousFluidNormalize;
    }

    public void abortAnimation() {
        this.mCurrX = this.mFinalX;
        this.mCurrY = this.mFinalY;
        this.mFinished = true;
    }

    public boolean computeScrollOffset() {
        if (this.mFinished) {
            return false;
        }
        int iCurrentAnimationTimeMillis = (int) (AnimationUtils.currentAnimationTimeMillis() - this.mStartTime);
        int i2 = this.mDuration;
        if (iCurrentAnimationTimeMillis < i2) {
            int i3 = this.mMode;
            if (i3 == 0) {
                float f2 = iCurrentAnimationTimeMillis * this.mDurationReciprocal;
                Interpolator interpolator = this.mInterpolator;
                float fViscousFluid = interpolator == null ? viscousFluid(f2) : interpolator.getInterpolation(f2);
                this.mCurrX = this.mStartX + Math.round(this.mDeltaX * fViscousFluid);
                this.mCurrY = this.mStartY + Math.round(fViscousFluid * this.mDeltaY);
            } else if (i3 == 1) {
                float f3 = iCurrentAnimationTimeMillis / i2;
                int i4 = (int) (f3 * 100.0f);
                float f4 = i4 / 100.0f;
                int i5 = i4 + 1;
                float[] fArr = SPLINE;
                float f5 = fArr[i4];
                float f6 = f5 + (((f3 - f4) / ((i5 / 100.0f) - f4)) * (fArr[i5] - f5));
                int iRound = this.mStartX + Math.round((this.mFinalX - r0) * f6);
                this.mCurrX = iRound;
                int iMin = Math.min(iRound, this.mMaxX);
                this.mCurrX = iMin;
                this.mCurrX = Math.max(iMin, this.mMinX);
                int iRound2 = this.mStartY + Math.round(f6 * (this.mFinalY - r0));
                this.mCurrY = iRound2;
                int iMin2 = Math.min(iRound2, this.mMaxY);
                this.mCurrY = iMin2;
                int iMax = Math.max(iMin2, this.mMinY);
                this.mCurrY = iMax;
                if (this.mCurrX == this.mFinalX && iMax == this.mFinalY) {
                    this.mFinished = true;
                }
            }
        } else {
            this.mCurrX = this.mFinalX;
            this.mCurrY = this.mFinalY;
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

    /* JADX WARN: Removed duplicated region for block: B:15:0x0099  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x00a0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void fling(int r16, int r17, int r18, int r19, int r20, int r21, int r22, int r23) {
        /*
            Method dump skipped, instructions count: 242
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.easefun.polyv.livecommon.ui.widget.menudrawer.Scroller.fling(int, int, int, int, int, int, int, int):void");
    }

    public final void forceFinished(boolean finished) {
        this.mFinished = finished;
    }

    public float getCurrVelocity() {
        return this.mVelocity - ((this.mDeceleration * timePassed()) / 2000.0f);
    }

    public final int getCurrX() {
        return this.mCurrX;
    }

    public final int getCurrY() {
        return this.mCurrY;
    }

    public final int getDuration() {
        return this.mDuration;
    }

    public final int getFinalX() {
        return this.mFinalX;
    }

    public final int getFinalY() {
        return this.mFinalY;
    }

    public final int getStartX() {
        return this.mStartX;
    }

    public final int getStartY() {
        return this.mStartY;
    }

    public final boolean isFinished() {
        return this.mFinished;
    }

    public boolean isScrollingInDirection(float xvel, float yvel) {
        return !this.mFinished && Math.signum(xvel) == Math.signum((float) (this.mFinalX - this.mStartX)) && Math.signum(yvel) == Math.signum((float) (this.mFinalY - this.mStartY));
    }

    public void setFinalX(int newX) {
        this.mFinalX = newX;
        this.mDeltaX = newX - this.mStartX;
        this.mFinished = false;
    }

    public void setFinalY(int newY) {
        this.mFinalY = newY;
        this.mDeltaY = newY - this.mStartY;
        this.mFinished = false;
    }

    public final void setFriction(float friction) {
        this.mDeceleration = computeDeceleration(friction);
    }

    public void startScroll(int startX, int startY, int dx, int dy) {
        startScroll(startX, startY, dx, dy, 250);
    }

    public int timePassed() {
        return (int) (AnimationUtils.currentAnimationTimeMillis() - this.mStartTime);
    }

    public Scroller(Context context, Interpolator interpolator) {
        this(context, interpolator, context.getApplicationInfo().targetSdkVersion >= 11);
    }

    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        this.mMode = 0;
        this.mFinished = false;
        this.mDuration = duration;
        this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
        this.mStartX = startX;
        this.mStartY = startY;
        this.mFinalX = startX + dx;
        this.mFinalY = startY + dy;
        this.mDeltaX = dx;
        this.mDeltaY = dy;
        this.mDurationReciprocal = 1.0f / this.mDuration;
    }

    public Scroller(Context context, Interpolator interpolator, boolean flywheel) {
        this.mFinished = true;
        this.mInterpolator = interpolator;
        this.mPpi = context.getResources().getDisplayMetrics().density * 160.0f;
        this.mDeceleration = computeDeceleration(ViewConfiguration.getScrollFriction());
        this.mFlywheel = flywheel;
    }
}
