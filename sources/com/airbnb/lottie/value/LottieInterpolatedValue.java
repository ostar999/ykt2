package com.airbnb.lottie.value;

import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

/* loaded from: classes2.dex */
abstract class LottieInterpolatedValue<T> extends LottieValueCallback<T> {
    private final T endValue;
    private final Interpolator interpolator;
    private final T startValue;

    public LottieInterpolatedValue(T t2, T t3) {
        this(t2, t3, new LinearInterpolator());
    }

    @Override // com.airbnb.lottie.value.LottieValueCallback
    public T getValue(LottieFrameInfo<T> lottieFrameInfo) {
        return interpolateValue(this.startValue, this.endValue, this.interpolator.getInterpolation(lottieFrameInfo.getOverallProgress()));
    }

    public abstract T interpolateValue(T t2, T t3, float f2);

    public LottieInterpolatedValue(T t2, T t3, Interpolator interpolator) {
        this.startValue = t2;
        this.endValue = t3;
        this.interpolator = interpolator;
    }
}
