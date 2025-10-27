package com.github.mikephil.charting.animation;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import androidx.annotation.RequiresApi;
import com.github.mikephil.charting.animation.Easing;

/* loaded from: classes3.dex */
public class ChartAnimator {
    private ValueAnimator.AnimatorUpdateListener mListener;
    protected float mPhaseY = 1.0f;
    protected float mPhaseX = 1.0f;

    public ChartAnimator() {
    }

    @RequiresApi(11)
    private ObjectAnimator xAnimator(int i2, Easing.EasingFunction easingFunction) {
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this, "phaseX", 0.0f, 1.0f);
        objectAnimatorOfFloat.setInterpolator(easingFunction);
        objectAnimatorOfFloat.setDuration(i2);
        return objectAnimatorOfFloat;
    }

    @RequiresApi(11)
    private ObjectAnimator yAnimator(int i2, Easing.EasingFunction easingFunction) {
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this, "phaseY", 0.0f, 1.0f);
        objectAnimatorOfFloat.setInterpolator(easingFunction);
        objectAnimatorOfFloat.setDuration(i2);
        return objectAnimatorOfFloat;
    }

    @RequiresApi(11)
    public void animateX(int i2) {
        animateX(i2, Easing.Linear);
    }

    @RequiresApi(11)
    public void animateXY(int i2, int i3) {
        Easing.EasingFunction easingFunction = Easing.Linear;
        animateXY(i2, i3, easingFunction, easingFunction);
    }

    @RequiresApi(11)
    public void animateY(int i2) {
        animateY(i2, Easing.Linear);
    }

    public float getPhaseX() {
        return this.mPhaseX;
    }

    public float getPhaseY() {
        return this.mPhaseY;
    }

    /* JADX WARN: Removed duplicated region for block: B:4:0x0006 A[PHI: r0
      0x0006: PHI (r0v2 float) = (r0v0 float), (r0v1 float) binds: [B:3:0x0004, B:6:0x000b] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void setPhaseX(float r3) {
        /*
            r2 = this;
            r0 = 1065353216(0x3f800000, float:1.0)
            int r1 = (r3 > r0 ? 1 : (r3 == r0 ? 0 : -1))
            if (r1 <= 0) goto L8
        L6:
            r3 = r0
            goto Le
        L8:
            r0 = 0
            int r1 = (r3 > r0 ? 1 : (r3 == r0 ? 0 : -1))
            if (r1 >= 0) goto Le
            goto L6
        Le:
            r2.mPhaseX = r3
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.github.mikephil.charting.animation.ChartAnimator.setPhaseX(float):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:4:0x0006 A[PHI: r0
      0x0006: PHI (r0v2 float) = (r0v0 float), (r0v1 float) binds: [B:3:0x0004, B:6:0x000b] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void setPhaseY(float r3) {
        /*
            r2 = this;
            r0 = 1065353216(0x3f800000, float:1.0)
            int r1 = (r3 > r0 ? 1 : (r3 == r0 ? 0 : -1))
            if (r1 <= 0) goto L8
        L6:
            r3 = r0
            goto Le
        L8:
            r0 = 0
            int r1 = (r3 > r0 ? 1 : (r3 == r0 ? 0 : -1))
            if (r1 >= 0) goto Le
            goto L6
        Le:
            r2.mPhaseY = r3
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.github.mikephil.charting.animation.ChartAnimator.setPhaseY(float):void");
    }

    @RequiresApi(11)
    public void animateX(int i2, Easing.EasingFunction easingFunction) {
        ObjectAnimator objectAnimatorXAnimator = xAnimator(i2, easingFunction);
        objectAnimatorXAnimator.addUpdateListener(this.mListener);
        objectAnimatorXAnimator.start();
    }

    @RequiresApi(11)
    public void animateXY(int i2, int i3, Easing.EasingFunction easingFunction) {
        ObjectAnimator objectAnimatorXAnimator = xAnimator(i2, easingFunction);
        ObjectAnimator objectAnimatorYAnimator = yAnimator(i3, easingFunction);
        if (i2 > i3) {
            objectAnimatorXAnimator.addUpdateListener(this.mListener);
        } else {
            objectAnimatorYAnimator.addUpdateListener(this.mListener);
        }
        objectAnimatorXAnimator.start();
        objectAnimatorYAnimator.start();
    }

    @RequiresApi(11)
    public void animateY(int i2, Easing.EasingFunction easingFunction) {
        ObjectAnimator objectAnimatorYAnimator = yAnimator(i2, easingFunction);
        objectAnimatorYAnimator.addUpdateListener(this.mListener);
        objectAnimatorYAnimator.start();
    }

    @RequiresApi(11)
    public ChartAnimator(ValueAnimator.AnimatorUpdateListener animatorUpdateListener) {
        this.mListener = animatorUpdateListener;
    }

    @RequiresApi(11)
    public void animateXY(int i2, int i3, Easing.EasingFunction easingFunction, Easing.EasingFunction easingFunction2) {
        ObjectAnimator objectAnimatorXAnimator = xAnimator(i2, easingFunction);
        ObjectAnimator objectAnimatorYAnimator = yAnimator(i3, easingFunction2);
        if (i2 > i3) {
            objectAnimatorXAnimator.addUpdateListener(this.mListener);
        } else {
            objectAnimatorYAnimator.addUpdateListener(this.mListener);
        }
        objectAnimatorXAnimator.start();
        objectAnimatorYAnimator.start();
    }
}
