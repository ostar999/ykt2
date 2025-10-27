package com.airbnb.lottie.value;

import androidx.annotation.NonNull;
import com.airbnb.lottie.utils.MiscUtils;

/* loaded from: classes2.dex */
public class LottieRelativeFloatValueCallback extends LottieValueCallback<Float> {
    public LottieRelativeFloatValueCallback() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Float getOffset(LottieFrameInfo<Float> lottieFrameInfo) {
        T t2 = this.value;
        if (t2 != 0) {
            return (Float) t2;
        }
        throw new IllegalArgumentException("You must provide a static value in the constructor , call setValue, or override getValue.");
    }

    public LottieRelativeFloatValueCallback(@NonNull Float f2) {
        super(f2);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.airbnb.lottie.value.LottieValueCallback
    public Float getValue(LottieFrameInfo<Float> lottieFrameInfo) {
        return Float.valueOf(MiscUtils.lerp(lottieFrameInfo.getStartValue().floatValue(), lottieFrameInfo.getEndValue().floatValue(), lottieFrameInfo.getInterpolatedKeyframeProgress()) + getOffset(lottieFrameInfo).floatValue());
    }
}
