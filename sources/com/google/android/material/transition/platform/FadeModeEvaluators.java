package com.google.android.material.transition.platform;

import androidx.annotation.RequiresApi;

@RequiresApi(21)
/* loaded from: classes3.dex */
class FadeModeEvaluators {
    private static final FadeModeEvaluator IN = new FadeModeEvaluator() { // from class: com.google.android.material.transition.platform.FadeModeEvaluators.1
        @Override // com.google.android.material.transition.platform.FadeModeEvaluator
        public FadeModeResult evaluate(float f2, float f3, float f4, float f5) {
            return FadeModeResult.endOnTop(255, TransitionUtils.lerp(0, 255, f3, f4, f2));
        }
    };
    private static final FadeModeEvaluator OUT = new FadeModeEvaluator() { // from class: com.google.android.material.transition.platform.FadeModeEvaluators.2
        @Override // com.google.android.material.transition.platform.FadeModeEvaluator
        public FadeModeResult evaluate(float f2, float f3, float f4, float f5) {
            return FadeModeResult.startOnTop(TransitionUtils.lerp(255, 0, f3, f4, f2), 255);
        }
    };
    private static final FadeModeEvaluator CROSS = new FadeModeEvaluator() { // from class: com.google.android.material.transition.platform.FadeModeEvaluators.3
        @Override // com.google.android.material.transition.platform.FadeModeEvaluator
        public FadeModeResult evaluate(float f2, float f3, float f4, float f5) {
            return FadeModeResult.startOnTop(TransitionUtils.lerp(255, 0, f3, f4, f2), TransitionUtils.lerp(0, 255, f3, f4, f2));
        }
    };
    private static final FadeModeEvaluator THROUGH = new FadeModeEvaluator() { // from class: com.google.android.material.transition.platform.FadeModeEvaluators.4
        @Override // com.google.android.material.transition.platform.FadeModeEvaluator
        public FadeModeResult evaluate(float f2, float f3, float f4, float f5) {
            float f6 = ((f4 - f3) * f5) + f3;
            return FadeModeResult.startOnTop(TransitionUtils.lerp(255, 0, f3, f6, f2), TransitionUtils.lerp(0, 255, f6, f4, f2));
        }
    };

    private FadeModeEvaluators() {
    }

    public static FadeModeEvaluator get(int i2, boolean z2) {
        if (i2 == 0) {
            return z2 ? IN : OUT;
        }
        if (i2 == 1) {
            return z2 ? OUT : IN;
        }
        if (i2 == 2) {
            return CROSS;
        }
        if (i2 == 3) {
            return THROUGH;
        }
        throw new IllegalArgumentException("Invalid fade mode: " + i2);
    }
}
