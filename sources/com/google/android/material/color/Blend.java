package com.google.android.material.color;

/* loaded from: classes3.dex */
final class Blend {
    private static final float HARMONIZE_MAX_DEGREES = 15.0f;
    private static final float HARMONIZE_PERCENTAGE = 0.5f;

    private Blend() {
    }

    public static int blendCam16Ucs(int i2, int i3, float f2) {
        Cam16 cam16FromInt = Cam16.fromInt(i2);
        Cam16 cam16FromInt2 = Cam16.fromInt(i3);
        float jStar = cam16FromInt.getJStar();
        float aStar = cam16FromInt.getAStar();
        float bStar = cam16FromInt.getBStar();
        return Cam16.fromUcs(jStar + ((cam16FromInt2.getJStar() - jStar) * f2), aStar + ((cam16FromInt2.getAStar() - aStar) * f2), bStar + ((cam16FromInt2.getBStar() - bStar) * f2)).getInt();
    }

    public static int blendHctHue(int i2, int i3, float f2) {
        return Hct.from(Cam16.fromInt(blendCam16Ucs(i2, i3, f2)).getHue(), Cam16.fromInt(i2).getChroma(), ColorUtils.lstarFromInt(i2)).toInt();
    }

    public static int harmonize(int i2, int i3) {
        Hct hctFromInt = Hct.fromInt(i2);
        Hct hctFromInt2 = Hct.fromInt(i3);
        return Hct.from(MathUtils.sanitizeDegrees(hctFromInt.getHue() + (Math.min(MathUtils.differenceDegrees(hctFromInt.getHue(), hctFromInt2.getHue()) * 0.5f, HARMONIZE_MAX_DEGREES) * rotationDirection(hctFromInt.getHue(), hctFromInt2.getHue()))), hctFromInt.getChroma(), hctFromInt.getTone()).toInt();
    }

    private static float rotationDirection(float f2, float f3) {
        float f4 = f3 - f2;
        float f5 = f4 + 360.0f;
        float f6 = f4 - 360.0f;
        float fAbs = Math.abs(f4);
        float fAbs2 = Math.abs(f5);
        float fAbs3 = Math.abs(f6);
        return (fAbs > fAbs2 || fAbs > fAbs3) ? (fAbs2 > fAbs || fAbs2 > fAbs3) ? ((double) f6) >= 0.0d ? 1.0f : -1.0f : ((double) f5) >= 0.0d ? 1.0f : -1.0f : ((double) f4) >= 0.0d ? 1.0f : -1.0f;
    }
}
