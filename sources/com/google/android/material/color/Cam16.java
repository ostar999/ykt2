package com.google.android.material.color;

/* loaded from: classes3.dex */
final class Cam16 {
    private final float astar;
    private final float bstar;
    private final float chroma;
    private final float hue;

    /* renamed from: j, reason: collision with root package name */
    private final float f7020j;
    private final float jstar;

    /* renamed from: m, reason: collision with root package name */
    private final float f7021m;

    /* renamed from: q, reason: collision with root package name */
    private final float f7022q;

    /* renamed from: s, reason: collision with root package name */
    private final float f7023s;
    static final float[][] XYZ_TO_CAM16RGB = {new float[]{0.401288f, 0.650173f, -0.051461f}, new float[]{-0.250268f, 1.204414f, 0.045854f}, new float[]{-0.002079f, 0.048952f, 0.953127f}};
    static final float[][] CAM16RGB_TO_XYZ = {new float[]{1.8620678f, -1.0112547f, 0.14918678f}, new float[]{0.38752654f, 0.62144744f, -0.00897398f}, new float[]{-0.0158415f, -0.03412294f, 1.0499644f}};

    private Cam16(float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10) {
        this.hue = f2;
        this.chroma = f3;
        this.f7020j = f4;
        this.f7022q = f5;
        this.f7021m = f6;
        this.f7023s = f7;
        this.jstar = f8;
        this.astar = f9;
        this.bstar = f10;
    }

    public static Cam16 fromInt(int i2) {
        return fromIntInViewingConditions(i2, ViewingConditions.DEFAULT);
    }

    public static Cam16 fromIntInViewingConditions(int i2, ViewingConditions viewingConditions) {
        float fLinearized = ColorUtils.linearized(((16711680 & i2) >> 16) / 255.0f) * 100.0f;
        float fLinearized2 = ColorUtils.linearized(((65280 & i2) >> 8) / 255.0f) * 100.0f;
        float fLinearized3 = ColorUtils.linearized((i2 & 255) / 255.0f) * 100.0f;
        float f2 = (0.41233894f * fLinearized) + (0.35762063f * fLinearized2) + (0.18051042f * fLinearized3);
        float f3 = (0.2126f * fLinearized) + (0.7152f * fLinearized2) + (0.0722f * fLinearized3);
        float f4 = (fLinearized * 0.01932141f) + (fLinearized2 * 0.11916382f) + (fLinearized3 * 0.9503448f);
        float[][] fArr = XYZ_TO_CAM16RGB;
        float[] fArr2 = fArr[0];
        float f5 = (fArr2[0] * f2) + (fArr2[1] * f3) + (fArr2[2] * f4);
        float[] fArr3 = fArr[1];
        float f6 = (fArr3[0] * f2) + (fArr3[1] * f3) + (fArr3[2] * f4);
        float[] fArr4 = fArr[2];
        float f7 = (f2 * fArr4[0]) + (f3 * fArr4[1]) + (f4 * fArr4[2]);
        float f8 = viewingConditions.getRgbD()[0] * f5;
        float f9 = viewingConditions.getRgbD()[1] * f6;
        float f10 = viewingConditions.getRgbD()[2] * f7;
        float fPow = (float) Math.pow((viewingConditions.getFl() * Math.abs(f8)) / 100.0d, 0.42d);
        float fPow2 = (float) Math.pow((viewingConditions.getFl() * Math.abs(f9)) / 100.0d, 0.42d);
        float fPow3 = (float) Math.pow((viewingConditions.getFl() * Math.abs(f10)) / 100.0d, 0.42d);
        float fSignum = ((Math.signum(f8) * 400.0f) * fPow) / (fPow + 27.13f);
        float fSignum2 = ((Math.signum(f9) * 400.0f) * fPow2) / (fPow2 + 27.13f);
        float fSignum3 = ((Math.signum(f10) * 400.0f) * fPow3) / (fPow3 + 27.13f);
        double d3 = fSignum3;
        float f11 = ((float) (((fSignum * 11.0d) + (fSignum2 * (-12.0d))) + d3)) / 11.0f;
        float f12 = ((float) ((fSignum + fSignum2) - (d3 * 2.0d))) / 9.0f;
        float f13 = fSignum2 * 20.0f;
        float f14 = (((fSignum * 20.0f) + f13) + (21.0f * fSignum3)) / 20.0f;
        float f15 = (((fSignum * 40.0f) + f13) + fSignum3) / 20.0f;
        float fAtan2 = (((float) Math.atan2(f12, f11)) * 180.0f) / 3.1415927f;
        if (fAtan2 < 0.0f) {
            fAtan2 += 360.0f;
        } else if (fAtan2 >= 360.0f) {
            fAtan2 -= 360.0f;
        }
        float f16 = (3.1415927f * fAtan2) / 180.0f;
        float fPow4 = ((float) Math.pow((f15 * viewingConditions.getNbb()) / viewingConditions.getAw(), viewingConditions.getC() * viewingConditions.getZ())) * 100.0f;
        float c3 = (4.0f / viewingConditions.getC()) * ((float) Math.sqrt(fPow4 / 100.0f)) * (viewingConditions.getAw() + 4.0f) * viewingConditions.getFlRoot();
        float fPow5 = ((float) Math.pow(1.64d - Math.pow(0.29d, viewingConditions.getN()), 0.73d)) * ((float) Math.pow((((((((float) (Math.cos(Math.toRadians(((double) fAtan2) < 20.14d ? 360.0f + fAtan2 : fAtan2) + 2.0d) + 3.8d)) * 0.25f) * 3846.1538f) * viewingConditions.getNc()) * viewingConditions.getNcb()) * ((float) Math.hypot(r5, r1))) / (f14 + 0.305f), 0.9d)) * ((float) Math.sqrt(fPow4 / 100.0d));
        float flRoot = fPow5 * viewingConditions.getFlRoot();
        float fSqrt = ((float) Math.sqrt((r1 * viewingConditions.getC()) / (viewingConditions.getAw() + 4.0f))) * 50.0f;
        float f17 = (1.7f * fPow4) / ((0.007f * fPow4) + 1.0f);
        float fLog1p = ((float) Math.log1p(0.0228f * flRoot)) * 43.85965f;
        double d4 = f16;
        return new Cam16(fAtan2, fPow5, fPow4, c3, flRoot, fSqrt, f17, fLog1p * ((float) Math.cos(d4)), fLog1p * ((float) Math.sin(d4)));
    }

    public static Cam16 fromJch(float f2, float f3, float f4) {
        return fromJchInViewingConditions(f2, f3, f4, ViewingConditions.DEFAULT);
    }

    private static Cam16 fromJchInViewingConditions(float f2, float f3, float f4, ViewingConditions viewingConditions) {
        float c3 = (4.0f / viewingConditions.getC()) * ((float) Math.sqrt(f2 / 100.0d)) * (viewingConditions.getAw() + 4.0f) * viewingConditions.getFlRoot();
        float flRoot = f3 * viewingConditions.getFlRoot();
        float fSqrt = ((float) Math.sqrt(((f3 / ((float) Math.sqrt(r4))) * viewingConditions.getC()) / (viewingConditions.getAw() + 4.0f))) * 50.0f;
        float f5 = (1.7f * f2) / ((0.007f * f2) + 1.0f);
        float fLog1p = ((float) Math.log1p(flRoot * 0.0228d)) * 43.85965f;
        double d3 = (3.1415927f * f4) / 180.0f;
        return new Cam16(f4, f3, f2, c3, flRoot, fSqrt, f5, fLog1p * ((float) Math.cos(d3)), fLog1p * ((float) Math.sin(d3)));
    }

    public static Cam16 fromUcs(float f2, float f3, float f4) {
        return fromUcsInViewingConditions(f2, f3, f4, ViewingConditions.DEFAULT);
    }

    public static Cam16 fromUcsInViewingConditions(float f2, float f3, float f4, ViewingConditions viewingConditions) {
        double d3 = f3;
        double d4 = f4;
        double dExpm1 = (Math.expm1(Math.hypot(d3, d4) * 0.02280000038444996d) / 0.02280000038444996d) / viewingConditions.getFlRoot();
        double dAtan2 = Math.atan2(d4, d3) * 57.29577951308232d;
        if (dAtan2 < 0.0d) {
            dAtan2 += 360.0d;
        }
        return fromJchInViewingConditions(f2 / (1.0f - ((f2 - 100.0f) * 0.007f)), (float) dExpm1, (float) dAtan2, viewingConditions);
    }

    public float distance(Cam16 cam16) {
        float jStar = getJStar() - cam16.getJStar();
        float aStar = getAStar() - cam16.getAStar();
        float bStar = getBStar() - cam16.getBStar();
        return (float) (Math.pow(Math.sqrt((jStar * jStar) + (aStar * aStar) + (bStar * bStar)), 0.63d) * 1.41d);
    }

    public float getAStar() {
        return this.astar;
    }

    public float getBStar() {
        return this.bstar;
    }

    public float getChroma() {
        return this.chroma;
    }

    public float getHue() {
        return this.hue;
    }

    public int getInt() {
        return viewed(ViewingConditions.DEFAULT);
    }

    public float getJ() {
        return this.f7020j;
    }

    public float getJStar() {
        return this.jstar;
    }

    public float getM() {
        return this.f7021m;
    }

    public float getQ() {
        return this.f7022q;
    }

    public float getS() {
        return this.f7023s;
    }

    public int viewed(ViewingConditions viewingConditions) {
        float fPow = (float) Math.pow(((((double) getChroma()) == 0.0d || ((double) getJ()) == 0.0d) ? 0.0f : getChroma() / ((float) Math.sqrt(getJ() / 100.0d))) / Math.pow(1.64d - Math.pow(0.29d, viewingConditions.getN()), 0.73d), 1.1111111111111112d);
        double hue = (getHue() * 3.1415927f) / 180.0f;
        float fCos = ((float) (Math.cos(2.0d + hue) + 3.8d)) * 0.25f;
        float aw = viewingConditions.getAw() * ((float) Math.pow(getJ() / 100.0d, (1.0d / viewingConditions.getC()) / viewingConditions.getZ()));
        float nc = fCos * 3846.1538f * viewingConditions.getNc() * viewingConditions.getNcb();
        float nbb = aw / viewingConditions.getNbb();
        float fSin = (float) Math.sin(hue);
        float fCos2 = (float) Math.cos(hue);
        float f2 = (((0.305f + nbb) * 23.0f) * fPow) / (((nc * 23.0f) + ((11.0f * fPow) * fCos2)) + ((fPow * 108.0f) * fSin));
        float f3 = fCos2 * f2;
        float f4 = f2 * fSin;
        float f5 = nbb * 460.0f;
        float f6 = (((451.0f * f3) + f5) + (288.0f * f4)) / 1403.0f;
        float f7 = ((f5 - (891.0f * f3)) - (261.0f * f4)) / 1403.0f;
        float fSignum = Math.signum(f6) * (100.0f / viewingConditions.getFl()) * ((float) Math.pow((float) Math.max(0.0d, (Math.abs(f6) * 27.13d) / (400.0d - Math.abs(f6))), 2.380952380952381d));
        float fSignum2 = Math.signum(f7) * (100.0f / viewingConditions.getFl()) * ((float) Math.pow((float) Math.max(0.0d, (Math.abs(f7) * 27.13d) / (400.0d - Math.abs(f7))), 2.380952380952381d));
        float fSignum3 = Math.signum(((f5 - (f3 * 220.0f)) - (f4 * 6300.0f)) / 1403.0f) * (100.0f / viewingConditions.getFl()) * ((float) Math.pow((float) Math.max(0.0d, (Math.abs(r8) * 27.13d) / (400.0d - Math.abs(r8))), 2.380952380952381d));
        float f8 = fSignum / viewingConditions.getRgbD()[0];
        float f9 = fSignum2 / viewingConditions.getRgbD()[1];
        float f10 = fSignum3 / viewingConditions.getRgbD()[2];
        float[][] fArr = CAM16RGB_TO_XYZ;
        float[] fArr2 = fArr[0];
        float f11 = (fArr2[0] * f8) + (fArr2[1] * f9) + (fArr2[2] * f10);
        float[] fArr3 = fArr[1];
        float f12 = (fArr3[0] * f8) + (fArr3[1] * f9) + (fArr3[2] * f10);
        float[] fArr4 = fArr[2];
        return ColorUtils.intFromXyzComponents(f11, f12, (f8 * fArr4[0]) + (f9 * fArr4[1]) + (f10 * fArr4[2]));
    }
}
