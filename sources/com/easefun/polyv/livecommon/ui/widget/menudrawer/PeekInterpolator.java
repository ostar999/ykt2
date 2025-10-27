package com.easefun.polyv.livecommon.ui.widget.menudrawer;

import android.view.animation.Interpolator;

/* loaded from: classes3.dex */
class PeekInterpolator implements Interpolator {
    private static final SinusoidalInterpolator SINUSOIDAL_INTERPOLATOR = new SinusoidalInterpolator();
    private static final String TAG = "PeekInterpolator";

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float input) {
        if (input < 0.33333334f) {
            return SINUSOIDAL_INTERPOLATOR.getInterpolation(input * 3.0f);
        }
        if (input <= 0.6666667f) {
            return 1.0f;
        }
        return 1.0f - SINUSOIDAL_INTERPOLATOR.getInterpolation(((input + 0.33333334f) - 1.0f) * 3.0f);
    }
}
