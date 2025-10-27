package com.chaychan.library;

import android.content.Context;
import android.os.Parcelable;
import android.util.AttributeSet;
import com.airbnb.lottie.LottieAnimationView;

/* loaded from: classes3.dex */
class MyLottieAnimationView extends LottieAnimationView {
    public MyLottieAnimationView(Context context) {
        super(context);
    }

    @Override // com.airbnb.lottie.LottieAnimationView, android.view.View
    public Parcelable onSaveInstanceState() {
        super.onSaveInstanceState();
        return null;
    }

    public MyLottieAnimationView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public MyLottieAnimationView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
    }
}
