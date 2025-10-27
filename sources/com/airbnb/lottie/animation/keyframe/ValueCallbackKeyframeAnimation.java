package com.airbnb.lottie.animation.keyframe;

import androidx.annotation.Nullable;
import com.airbnb.lottie.value.Keyframe;
import com.airbnb.lottie.value.LottieFrameInfo;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.Collections;

/* loaded from: classes.dex */
public class ValueCallbackKeyframeAnimation<K, A> extends BaseKeyframeAnimation<K, A> {
    private final LottieFrameInfo<A> frameInfo;
    private final A valueCallbackValue;

    public ValueCallbackKeyframeAnimation(LottieValueCallback<A> lottieValueCallback) {
        this(lottieValueCallback, null);
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
    public float getEndProgress() {
        return 1.0f;
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
    public A getValue() {
        LottieValueCallback<A> lottieValueCallback = this.valueCallback;
        A a3 = this.valueCallbackValue;
        return lottieValueCallback.getValueInternal(0.0f, 0.0f, a3, a3, getProgress(), getProgress(), getProgress());
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
    public void notifyListeners() {
        if (this.valueCallback != null) {
            super.notifyListeners();
        }
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
    public void setProgress(float f2) {
        this.progress = f2;
    }

    public ValueCallbackKeyframeAnimation(LottieValueCallback<A> lottieValueCallback, @Nullable A a3) {
        super(Collections.emptyList());
        this.frameInfo = new LottieFrameInfo<>();
        setValueCallback(lottieValueCallback);
        this.valueCallbackValue = a3;
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
    public A getValue(Keyframe<K> keyframe, float f2) {
        return getValue();
    }
}
