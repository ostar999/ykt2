package com.google.android.material.color;

/* loaded from: classes3.dex */
final class ViewingConditions {
    public static final ViewingConditions DEFAULT = make(ColorUtils.whitePointD65(), (float) ((ColorUtils.yFromLstar(50.0f) * 63.66197723675813d) / 100.0d), 50.0f, 2.0f, false);
    private final float aw;

    /* renamed from: c, reason: collision with root package name */
    private final float f7024c;
    private final float fl;
    private final float flRoot;

    /* renamed from: n, reason: collision with root package name */
    private final float f7025n;
    private final float nbb;
    private final float nc;
    private final float ncb;
    private final float[] rgbD;

    /* renamed from: z, reason: collision with root package name */
    private final float f7026z;

    private ViewingConditions(float f2, float f3, float f4, float f5, float f6, float f7, float[] fArr, float f8, float f9, float f10) {
        this.f7025n = f2;
        this.aw = f3;
        this.nbb = f4;
        this.ncb = f5;
        this.f7024c = f6;
        this.nc = f7;
        this.rgbD = fArr;
        this.fl = f8;
        this.flRoot = f9;
        this.f7026z = f10;
    }

    public static ViewingConditions make(float[] fArr, float f2, float f3, float f4, boolean z2) {
        float[][] fArr2 = Cam16.XYZ_TO_CAM16RGB;
        float f5 = fArr[0];
        float[] fArr3 = fArr2[0];
        float f6 = fArr3[0] * f5;
        float f7 = fArr[1];
        float f8 = f6 + (fArr3[1] * f7);
        float f9 = fArr[2];
        float f10 = f8 + (fArr3[2] * f9);
        float[] fArr4 = fArr2[1];
        float f11 = (fArr4[0] * f5) + (fArr4[1] * f7) + (fArr4[2] * f9);
        float[] fArr5 = fArr2[2];
        float f12 = (f5 * fArr5[0]) + (f7 * fArr5[1]) + (f9 * fArr5[2]);
        float f13 = (f4 / 10.0f) + 0.8f;
        float fLerp = ((double) f13) >= 0.9d ? MathUtils.lerp(0.59f, 0.69f, (f13 - 0.9f) * 10.0f) : MathUtils.lerp(0.525f, 0.59f, (f13 - 0.8f) * 10.0f);
        float fExp = z2 ? 1.0f : (1.0f - (((float) Math.exp(((-f2) - 42.0f) / 92.0f)) * 0.2777778f)) * f13;
        double d3 = fExp;
        if (d3 > 1.0d) {
            fExp = 1.0f;
        } else if (d3 < 0.0d) {
            fExp = 0.0f;
        }
        float[] fArr6 = {(((100.0f / f10) * fExp) + 1.0f) - fExp, (((100.0f / f11) * fExp) + 1.0f) - fExp, (((100.0f / f12) * fExp) + 1.0f) - fExp};
        float f14 = 1.0f / ((5.0f * f2) + 1.0f);
        float f15 = f14 * f14 * f14 * f14;
        float f16 = 1.0f - f15;
        float fCbrt = (f15 * f2) + (0.1f * f16 * f16 * ((float) Math.cbrt(f2 * 5.0d)));
        float fYFromLstar = ColorUtils.yFromLstar(f3) / fArr[1];
        double d4 = fYFromLstar;
        float fSqrt = ((float) Math.sqrt(d4)) + 1.48f;
        float fPow = 0.725f / ((float) Math.pow(d4, 0.2d));
        float fPow2 = (float) Math.pow(((fArr6[2] * fCbrt) * f12) / 100.0d, 0.42d);
        float[] fArr7 = {(float) Math.pow(((fArr6[0] * fCbrt) * f10) / 100.0d, 0.42d), (float) Math.pow(((fArr6[1] * fCbrt) * f11) / 100.0d, 0.42d), fPow2};
        float f17 = fArr7[0];
        float f18 = fArr7[1];
        return new ViewingConditions(fYFromLstar, ((((f17 * 400.0f) / (f17 + 27.13f)) * 2.0f) + ((f18 * 400.0f) / (f18 + 27.13f)) + (((400.0f * fPow2) / (fPow2 + 27.13f)) * 0.05f)) * fPow, fPow, fPow, fLerp, f13, fArr6, fCbrt, (float) Math.pow(fCbrt, 0.25d), fSqrt);
    }

    public float getAw() {
        return this.aw;
    }

    public float getC() {
        return this.f7024c;
    }

    public float getFl() {
        return this.fl;
    }

    public float getFlRoot() {
        return this.flRoot;
    }

    public float getN() {
        return this.f7025n;
    }

    public float getNbb() {
        return this.nbb;
    }

    public float getNc() {
        return this.nc;
    }

    public float getNcb() {
        return this.ncb;
    }

    public float[] getRgbD() {
        return this.rgbD;
    }

    public float getZ() {
        return this.f7026z;
    }
}
