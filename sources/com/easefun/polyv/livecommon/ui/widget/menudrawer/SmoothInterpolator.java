package com.easefun.polyv.livecommon.ui.widget.menudrawer;

import android.view.animation.Interpolator;

/* loaded from: classes3.dex */
class SmoothInterpolator implements Interpolator {
    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float t2) {
        float f2 = t2 - 1.0f;
        return (f2 * f2 * f2 * f2 * f2) + 1.0f;
    }
}
