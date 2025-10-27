package com.easefun.polyv.livecommon.ui.widget.menudrawer;

import android.view.animation.Interpolator;

/* loaded from: classes3.dex */
class SinusoidalInterpolator implements Interpolator {
    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float input) {
        return (float) ((Math.sin((input * 3.141592653589793d) - 1.5707963267948966d) * 0.5d) + 0.5d);
    }
}
