package com.github.mikephil.charting.animation;

import android.animation.TimeInterpolator;
import androidx.annotation.RequiresApi;

@RequiresApi(11)
/* loaded from: classes3.dex */
public class Easing {
    private static final float DOUBLE_PI = 6.2831855f;
    public static final EasingFunction Linear = new EasingFunction() { // from class: com.github.mikephil.charting.animation.Easing.1
        @Override // com.github.mikephil.charting.animation.Easing.EasingFunction, android.animation.TimeInterpolator
        public float getInterpolation(float f2) {
            return f2;
        }
    };
    public static final EasingFunction EaseInQuad = new EasingFunction() { // from class: com.github.mikephil.charting.animation.Easing.2
        @Override // com.github.mikephil.charting.animation.Easing.EasingFunction, android.animation.TimeInterpolator
        public float getInterpolation(float f2) {
            return f2 * f2;
        }
    };
    public static final EasingFunction EaseOutQuad = new EasingFunction() { // from class: com.github.mikephil.charting.animation.Easing.3
        @Override // com.github.mikephil.charting.animation.Easing.EasingFunction, android.animation.TimeInterpolator
        public float getInterpolation(float f2) {
            return (-f2) * (f2 - 2.0f);
        }
    };
    public static final EasingFunction EaseInOutQuad = new EasingFunction() { // from class: com.github.mikephil.charting.animation.Easing.4
        @Override // com.github.mikephil.charting.animation.Easing.EasingFunction, android.animation.TimeInterpolator
        public float getInterpolation(float f2) {
            float f3 = f2 * 2.0f;
            if (f3 < 1.0f) {
                return 0.5f * f3 * f3;
            }
            float f4 = f3 - 1.0f;
            return ((f4 * (f4 - 2.0f)) - 1.0f) * (-0.5f);
        }
    };
    public static final EasingFunction EaseInCubic = new EasingFunction() { // from class: com.github.mikephil.charting.animation.Easing.5
        @Override // com.github.mikephil.charting.animation.Easing.EasingFunction, android.animation.TimeInterpolator
        public float getInterpolation(float f2) {
            return (float) Math.pow(f2, 3.0d);
        }
    };
    public static final EasingFunction EaseOutCubic = new EasingFunction() { // from class: com.github.mikephil.charting.animation.Easing.6
        @Override // com.github.mikephil.charting.animation.Easing.EasingFunction, android.animation.TimeInterpolator
        public float getInterpolation(float f2) {
            return ((float) Math.pow(f2 - 1.0f, 3.0d)) + 1.0f;
        }
    };
    public static final EasingFunction EaseInOutCubic = new EasingFunction() { // from class: com.github.mikephil.charting.animation.Easing.7
        @Override // com.github.mikephil.charting.animation.Easing.EasingFunction, android.animation.TimeInterpolator
        public float getInterpolation(float f2) {
            float f3 = f2 * 2.0f;
            return (f3 < 1.0f ? (float) Math.pow(f3, 3.0d) : ((float) Math.pow(f3 - 2.0f, 3.0d)) + 2.0f) * 0.5f;
        }
    };
    public static final EasingFunction EaseInQuart = new EasingFunction() { // from class: com.github.mikephil.charting.animation.Easing.8
        @Override // com.github.mikephil.charting.animation.Easing.EasingFunction, android.animation.TimeInterpolator
        public float getInterpolation(float f2) {
            return (float) Math.pow(f2, 4.0d);
        }
    };
    public static final EasingFunction EaseOutQuart = new EasingFunction() { // from class: com.github.mikephil.charting.animation.Easing.9
        @Override // com.github.mikephil.charting.animation.Easing.EasingFunction, android.animation.TimeInterpolator
        public float getInterpolation(float f2) {
            return -(((float) Math.pow(f2 - 1.0f, 4.0d)) - 1.0f);
        }
    };
    public static final EasingFunction EaseInOutQuart = new EasingFunction() { // from class: com.github.mikephil.charting.animation.Easing.10
        @Override // com.github.mikephil.charting.animation.Easing.EasingFunction, android.animation.TimeInterpolator
        public float getInterpolation(float f2) {
            float fPow;
            float f3;
            float f4 = f2 * 2.0f;
            if (f4 < 1.0f) {
                fPow = (float) Math.pow(f4, 4.0d);
                f3 = 0.5f;
            } else {
                fPow = ((float) Math.pow(f4 - 2.0f, 4.0d)) - 2.0f;
                f3 = -0.5f;
            }
            return fPow * f3;
        }
    };
    public static final EasingFunction EaseInSine = new EasingFunction() { // from class: com.github.mikephil.charting.animation.Easing.11
        @Override // com.github.mikephil.charting.animation.Easing.EasingFunction, android.animation.TimeInterpolator
        public float getInterpolation(float f2) {
            return (-((float) Math.cos(f2 * 1.5707963267948966d))) + 1.0f;
        }
    };
    public static final EasingFunction EaseOutSine = new EasingFunction() { // from class: com.github.mikephil.charting.animation.Easing.12
        @Override // com.github.mikephil.charting.animation.Easing.EasingFunction, android.animation.TimeInterpolator
        public float getInterpolation(float f2) {
            return (float) Math.sin(f2 * 1.5707963267948966d);
        }
    };
    public static final EasingFunction EaseInOutSine = new EasingFunction() { // from class: com.github.mikephil.charting.animation.Easing.13
        @Override // com.github.mikephil.charting.animation.Easing.EasingFunction, android.animation.TimeInterpolator
        public float getInterpolation(float f2) {
            return (((float) Math.cos(f2 * 3.141592653589793d)) - 1.0f) * (-0.5f);
        }
    };
    public static final EasingFunction EaseInExpo = new EasingFunction() { // from class: com.github.mikephil.charting.animation.Easing.14
        @Override // com.github.mikephil.charting.animation.Easing.EasingFunction, android.animation.TimeInterpolator
        public float getInterpolation(float f2) {
            if (f2 == 0.0f) {
                return 0.0f;
            }
            return (float) Math.pow(2.0d, (f2 - 1.0f) * 10.0f);
        }
    };
    public static final EasingFunction EaseOutExpo = new EasingFunction() { // from class: com.github.mikephil.charting.animation.Easing.15
        @Override // com.github.mikephil.charting.animation.Easing.EasingFunction, android.animation.TimeInterpolator
        public float getInterpolation(float f2) {
            if (f2 == 1.0f) {
                return 1.0f;
            }
            return -((float) Math.pow(2.0d, (f2 + 1.0f) * (-10.0f)));
        }
    };
    public static final EasingFunction EaseInOutExpo = new EasingFunction() { // from class: com.github.mikephil.charting.animation.Easing.16
        @Override // com.github.mikephil.charting.animation.Easing.EasingFunction, android.animation.TimeInterpolator
        public float getInterpolation(float f2) {
            if (f2 == 0.0f) {
                return 0.0f;
            }
            if (f2 == 1.0f) {
                return 1.0f;
            }
            return (f2 * 2.0f < 1.0f ? (float) Math.pow(2.0d, (r9 - 1.0f) * 10.0f) : (-((float) Math.pow(2.0d, (r9 - 1.0f) * (-10.0f)))) + 2.0f) * 0.5f;
        }
    };
    public static final EasingFunction EaseInCirc = new EasingFunction() { // from class: com.github.mikephil.charting.animation.Easing.17
        @Override // com.github.mikephil.charting.animation.Easing.EasingFunction, android.animation.TimeInterpolator
        public float getInterpolation(float f2) {
            return -(((float) Math.sqrt(1.0f - (f2 * f2))) - 1.0f);
        }
    };
    public static final EasingFunction EaseOutCirc = new EasingFunction() { // from class: com.github.mikephil.charting.animation.Easing.18
        @Override // com.github.mikephil.charting.animation.Easing.EasingFunction, android.animation.TimeInterpolator
        public float getInterpolation(float f2) {
            float f3 = f2 - 1.0f;
            return (float) Math.sqrt(1.0f - (f3 * f3));
        }
    };
    public static final EasingFunction EaseInOutCirc = new EasingFunction() { // from class: com.github.mikephil.charting.animation.Easing.19
        @Override // com.github.mikephil.charting.animation.Easing.EasingFunction, android.animation.TimeInterpolator
        public float getInterpolation(float f2) {
            float fSqrt;
            float f3;
            float f4 = f2 * 2.0f;
            if (f4 < 1.0f) {
                fSqrt = ((float) Math.sqrt(1.0f - (f4 * f4))) - 1.0f;
                f3 = -0.5f;
            } else {
                float f5 = f4 - 2.0f;
                fSqrt = ((float) Math.sqrt(1.0f - (f5 * f5))) + 1.0f;
                f3 = 0.5f;
            }
            return fSqrt * f3;
        }
    };
    public static final EasingFunction EaseInElastic = new EasingFunction() { // from class: com.github.mikephil.charting.animation.Easing.20
        @Override // com.github.mikephil.charting.animation.Easing.EasingFunction, android.animation.TimeInterpolator
        public float getInterpolation(float f2) {
            if (f2 == 0.0f) {
                return 0.0f;
            }
            if (f2 == 1.0f) {
                return 1.0f;
            }
            float f3 = f2 - 1.0f;
            return -(((float) Math.pow(2.0d, 10.0f * f3)) * ((float) Math.sin(((f3 - (0.047746483f * ((float) Math.asin(1.0d)))) * Easing.DOUBLE_PI) / 0.3f)));
        }
    };
    public static final EasingFunction EaseOutElastic = new EasingFunction() { // from class: com.github.mikephil.charting.animation.Easing.21
        @Override // com.github.mikephil.charting.animation.Easing.EasingFunction, android.animation.TimeInterpolator
        public float getInterpolation(float f2) {
            if (f2 == 0.0f) {
                return 0.0f;
            }
            if (f2 == 1.0f) {
                return 1.0f;
            }
            return (((float) Math.pow(2.0d, (-10.0f) * f2)) * ((float) Math.sin(((f2 - (0.047746483f * ((float) Math.asin(1.0d)))) * Easing.DOUBLE_PI) / 0.3f))) + 1.0f;
        }
    };
    public static final EasingFunction EaseInOutElastic = new EasingFunction() { // from class: com.github.mikephil.charting.animation.Easing.22
        @Override // com.github.mikephil.charting.animation.Easing.EasingFunction, android.animation.TimeInterpolator
        public float getInterpolation(float f2) {
            if (f2 == 0.0f) {
                return 0.0f;
            }
            float f3 = f2 * 2.0f;
            if (f3 == 2.0f) {
                return 1.0f;
            }
            float fAsin = ((float) Math.asin(1.0d)) * 0.07161972f;
            if (f3 < 1.0f) {
                float f4 = f3 - 1.0f;
                return ((float) Math.pow(2.0d, 10.0f * f4)) * ((float) Math.sin(((f4 * 1.0f) - fAsin) * Easing.DOUBLE_PI * 2.2222223f)) * (-0.5f);
            }
            float f5 = f3 - 1.0f;
            return (((float) Math.pow(2.0d, (-10.0f) * f5)) * 0.5f * ((float) Math.sin(((f5 * 1.0f) - fAsin) * Easing.DOUBLE_PI * 2.2222223f))) + 1.0f;
        }
    };
    public static final EasingFunction EaseInBack = new EasingFunction() { // from class: com.github.mikephil.charting.animation.Easing.23
        @Override // com.github.mikephil.charting.animation.Easing.EasingFunction, android.animation.TimeInterpolator
        public float getInterpolation(float f2) {
            return f2 * f2 * ((f2 * 2.70158f) - 1.70158f);
        }
    };
    public static final EasingFunction EaseOutBack = new EasingFunction() { // from class: com.github.mikephil.charting.animation.Easing.24
        @Override // com.github.mikephil.charting.animation.Easing.EasingFunction, android.animation.TimeInterpolator
        public float getInterpolation(float f2) {
            float f3 = f2 - 1.0f;
            return (f3 * f3 * ((f3 * 2.70158f) + 1.70158f)) + 1.0f;
        }
    };
    public static final EasingFunction EaseInOutBack = new EasingFunction() { // from class: com.github.mikephil.charting.animation.Easing.25
        @Override // com.github.mikephil.charting.animation.Easing.EasingFunction, android.animation.TimeInterpolator
        public float getInterpolation(float f2) {
            float f3 = f2 * 2.0f;
            if (f3 < 1.0f) {
                return f3 * f3 * ((3.5949094f * f3) - 2.5949094f) * 0.5f;
            }
            float f4 = f3 - 2.0f;
            return ((f4 * f4 * ((3.5949094f * f4) + 2.5949094f)) + 2.0f) * 0.5f;
        }
    };
    public static final EasingFunction EaseInBounce = new EasingFunction() { // from class: com.github.mikephil.charting.animation.Easing.26
        @Override // com.github.mikephil.charting.animation.Easing.EasingFunction, android.animation.TimeInterpolator
        public float getInterpolation(float f2) {
            return 1.0f - Easing.EaseOutBounce.getInterpolation(1.0f - f2);
        }
    };
    public static final EasingFunction EaseOutBounce = new EasingFunction() { // from class: com.github.mikephil.charting.animation.Easing.27
        @Override // com.github.mikephil.charting.animation.Easing.EasingFunction, android.animation.TimeInterpolator
        public float getInterpolation(float f2) {
            if (f2 < 0.36363637f) {
                return 7.5625f * f2 * f2;
            }
            if (f2 < 0.72727275f) {
                float f3 = f2 - 0.54545456f;
                return (7.5625f * f3 * f3) + 0.75f;
            }
            if (f2 < 0.90909094f) {
                float f4 = f2 - 0.8181818f;
                return (7.5625f * f4 * f4) + 0.9375f;
            }
            float f5 = f2 - 0.95454544f;
            return (7.5625f * f5 * f5) + 0.984375f;
        }
    };
    public static final EasingFunction EaseInOutBounce = new EasingFunction() { // from class: com.github.mikephil.charting.animation.Easing.28
        @Override // com.github.mikephil.charting.animation.Easing.EasingFunction, android.animation.TimeInterpolator
        public float getInterpolation(float f2) {
            return f2 < 0.5f ? Easing.EaseInBounce.getInterpolation(f2 * 2.0f) * 0.5f : (Easing.EaseOutBounce.getInterpolation((f2 * 2.0f) - 1.0f) * 0.5f) + 0.5f;
        }
    };

    public interface EasingFunction extends TimeInterpolator {
        @Override // android.animation.TimeInterpolator
        float getInterpolation(float f2);
    }
}
