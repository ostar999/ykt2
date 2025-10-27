package androidx.core.content.res;

import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.ColorUtils;
import kotlin.jvm.internal.DoubleCompanionObject;

/* loaded from: classes.dex */
class CamColor {
    private static final float CHROMA_SEARCH_ENDPOINT = 0.4f;
    private static final float DE_MAX = 1.0f;
    private static final float DL_MAX = 0.2f;
    private static final float LIGHTNESS_SEARCH_ENDPOINT = 0.01f;
    private final float mAstar;
    private final float mBstar;
    private final float mChroma;
    private final float mHue;
    private final float mJ;
    private final float mJstar;
    private final float mM;
    private final float mQ;
    private final float mS;

    public CamColor(float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10) {
        this.mHue = f2;
        this.mChroma = f3;
        this.mJ = f4;
        this.mQ = f5;
        this.mM = f6;
        this.mS = f7;
        this.mJstar = f8;
        this.mAstar = f9;
        this.mBstar = f10;
    }

    @Nullable
    private static CamColor findCamByJ(@FloatRange(from = 0.0d, to = 360.0d) float f2, @FloatRange(from = 0.0d, to = DoubleCompanionObject.POSITIVE_INFINITY, toInclusive = false) float f3, @FloatRange(from = 0.0d, to = 100.0d) float f4) {
        float f5 = 100.0f;
        float f6 = 1000.0f;
        float f7 = 0.0f;
        CamColor camColor = null;
        float f8 = 1000.0f;
        while (Math.abs(f7 - f5) > LIGHTNESS_SEARCH_ENDPOINT) {
            float f9 = ((f5 - f7) / 2.0f) + f7;
            int iViewedInSrgb = fromJch(f9, f3, f2).viewedInSrgb();
            float fLStarFromInt = CamUtils.lStarFromInt(iViewedInSrgb);
            float fAbs = Math.abs(f4 - fLStarFromInt);
            if (fAbs < 0.2f) {
                CamColor camColorFromColor = fromColor(iViewedInSrgb);
                float fDistance = camColorFromColor.distance(fromJch(camColorFromColor.getJ(), camColorFromColor.getChroma(), f2));
                if (fDistance <= 1.0f) {
                    camColor = camColorFromColor;
                    f6 = fAbs;
                    f8 = fDistance;
                }
            }
            if (f6 == 0.0f && f8 == 0.0f) {
                break;
            }
            if (fLStarFromInt < f4) {
                f7 = f9;
            } else {
                f5 = f9;
            }
        }
        return camColor;
    }

    @NonNull
    public static CamColor fromColor(@ColorInt int i2) {
        return fromColorInViewingConditions(i2, ViewingConditions.DEFAULT);
    }

    @NonNull
    public static CamColor fromColorInViewingConditions(@ColorInt int i2, @NonNull ViewingConditions viewingConditions) {
        float[] fArrXyzFromInt = CamUtils.xyzFromInt(i2);
        float[][] fArr = CamUtils.XYZ_TO_CAM16RGB;
        float f2 = fArrXyzFromInt[0];
        float[] fArr2 = fArr[0];
        float f3 = fArr2[0] * f2;
        float f4 = fArrXyzFromInt[1];
        float f5 = f3 + (fArr2[1] * f4);
        float f6 = fArrXyzFromInt[2];
        float f7 = f5 + (fArr2[2] * f6);
        float[] fArr3 = fArr[1];
        float f8 = (fArr3[0] * f2) + (fArr3[1] * f4) + (fArr3[2] * f6);
        float[] fArr4 = fArr[2];
        float f9 = (f2 * fArr4[0]) + (f4 * fArr4[1]) + (f6 * fArr4[2]);
        float f10 = viewingConditions.getRgbD()[0] * f7;
        float f11 = viewingConditions.getRgbD()[1] * f8;
        float f12 = viewingConditions.getRgbD()[2] * f9;
        float fPow = (float) Math.pow((viewingConditions.getFl() * Math.abs(f10)) / 100.0d, 0.42d);
        float fPow2 = (float) Math.pow((viewingConditions.getFl() * Math.abs(f11)) / 100.0d, 0.42d);
        float fPow3 = (float) Math.pow((viewingConditions.getFl() * Math.abs(f12)) / 100.0d, 0.42d);
        float fSignum = ((Math.signum(f10) * 400.0f) * fPow) / (fPow + 27.13f);
        float fSignum2 = ((Math.signum(f11) * 400.0f) * fPow2) / (fPow2 + 27.13f);
        float fSignum3 = ((Math.signum(f12) * 400.0f) * fPow3) / (fPow3 + 27.13f);
        double d3 = fSignum3;
        float f13 = ((float) (((fSignum * 11.0d) + (fSignum2 * (-12.0d))) + d3)) / 11.0f;
        float f14 = ((float) ((fSignum + fSignum2) - (d3 * 2.0d))) / 9.0f;
        float f15 = fSignum2 * 20.0f;
        float f16 = (((fSignum * 20.0f) + f15) + (21.0f * fSignum3)) / 20.0f;
        float f17 = (((fSignum * 40.0f) + f15) + fSignum3) / 20.0f;
        float fAtan2 = (((float) Math.atan2(f14, f13)) * 180.0f) / 3.1415927f;
        if (fAtan2 < 0.0f) {
            fAtan2 += 360.0f;
        } else if (fAtan2 >= 360.0f) {
            fAtan2 -= 360.0f;
        }
        float f18 = fAtan2;
        float f19 = (3.1415927f * f18) / 180.0f;
        float fPow4 = ((float) Math.pow((f17 * viewingConditions.getNbb()) / viewingConditions.getAw(), viewingConditions.getC() * viewingConditions.getZ())) * 100.0f;
        float flRoot = viewingConditions.getFlRoot() * (4.0f / viewingConditions.getC()) * ((float) Math.sqrt(fPow4 / 100.0f)) * (viewingConditions.getAw() + 4.0f);
        float fPow5 = ((float) Math.pow(1.64d - Math.pow(0.29d, viewingConditions.getN()), 0.73d)) * ((float) Math.pow((((((((float) (Math.cos((((((double) f18) < 20.14d ? 360.0f + f18 : f18) * 3.141592653589793d) / 180.0d) + 2.0d) + 3.8d)) * 0.25f) * 3846.1538f) * viewingConditions.getNc()) * viewingConditions.getNcb()) * ((float) Math.sqrt((f13 * f13) + (f14 * f14)))) / (f16 + 0.305f), 0.9d)) * ((float) Math.sqrt(fPow4 / 100.0d));
        float flRoot2 = fPow5 * viewingConditions.getFlRoot();
        float fSqrt = ((float) Math.sqrt((r3 * viewingConditions.getC()) / (viewingConditions.getAw() + 4.0f))) * 50.0f;
        float f20 = (1.7f * fPow4) / ((0.007f * fPow4) + 1.0f);
        float fLog = ((float) Math.log((0.0228f * flRoot2) + 1.0f)) * 43.85965f;
        double d4 = f19;
        return new CamColor(f18, fPow5, fPow4, flRoot, flRoot2, fSqrt, f20, fLog * ((float) Math.cos(d4)), fLog * ((float) Math.sin(d4)));
    }

    @NonNull
    private static CamColor fromJch(@FloatRange(from = 0.0d, to = 100.0d) float f2, @FloatRange(from = 0.0d, to = DoubleCompanionObject.POSITIVE_INFINITY, toInclusive = false) float f3, @FloatRange(from = 0.0d, to = 360.0d) float f4) {
        return fromJchInFrame(f2, f3, f4, ViewingConditions.DEFAULT);
    }

    @NonNull
    private static CamColor fromJchInFrame(@FloatRange(from = 0.0d, to = 100.0d) float f2, @FloatRange(from = 0.0d, to = DoubleCompanionObject.POSITIVE_INFINITY, toInclusive = false) float f3, @FloatRange(from = 0.0d, to = 360.0d) float f4, ViewingConditions viewingConditions) {
        float c3 = (4.0f / viewingConditions.getC()) * ((float) Math.sqrt(f2 / 100.0d)) * (viewingConditions.getAw() + 4.0f) * viewingConditions.getFlRoot();
        float flRoot = f3 * viewingConditions.getFlRoot();
        float fSqrt = ((float) Math.sqrt(((f3 / ((float) Math.sqrt(r4))) * viewingConditions.getC()) / (viewingConditions.getAw() + 4.0f))) * 50.0f;
        float f5 = (1.7f * f2) / ((0.007f * f2) + 1.0f);
        float fLog = ((float) Math.log((flRoot * 0.0228d) + 1.0d)) * 43.85965f;
        double d3 = (3.1415927f * f4) / 180.0f;
        return new CamColor(f4, f3, f2, c3, flRoot, fSqrt, f5, fLog * ((float) Math.cos(d3)), fLog * ((float) Math.sin(d3)));
    }

    public static int toColor(@FloatRange(from = 0.0d, to = 360.0d) float f2, @FloatRange(from = 0.0d, to = DoubleCompanionObject.POSITIVE_INFINITY, toInclusive = false) float f3, @FloatRange(from = 0.0d, to = 100.0d) float f4) {
        return toColor(f2, f3, f4, ViewingConditions.DEFAULT);
    }

    public float distance(@NonNull CamColor camColor) {
        float jStar = getJStar() - camColor.getJStar();
        float aStar = getAStar() - camColor.getAStar();
        float bStar = getBStar() - camColor.getBStar();
        return (float) (Math.pow(Math.sqrt((jStar * jStar) + (aStar * aStar) + (bStar * bStar)), 0.63d) * 1.41d);
    }

    @FloatRange(from = DoubleCompanionObject.NEGATIVE_INFINITY, fromInclusive = false, to = DoubleCompanionObject.POSITIVE_INFINITY, toInclusive = false)
    public float getAStar() {
        return this.mAstar;
    }

    @FloatRange(from = DoubleCompanionObject.NEGATIVE_INFINITY, fromInclusive = false, to = DoubleCompanionObject.POSITIVE_INFINITY, toInclusive = false)
    public float getBStar() {
        return this.mBstar;
    }

    @FloatRange(from = 0.0d, to = DoubleCompanionObject.POSITIVE_INFINITY, toInclusive = false)
    public float getChroma() {
        return this.mChroma;
    }

    @FloatRange(from = 0.0d, to = 360.0d, toInclusive = false)
    public float getHue() {
        return this.mHue;
    }

    @FloatRange(from = 0.0d, to = 100.0d)
    public float getJ() {
        return this.mJ;
    }

    @FloatRange(from = 0.0d, to = 100.0d)
    public float getJStar() {
        return this.mJstar;
    }

    @FloatRange(from = 0.0d, to = DoubleCompanionObject.POSITIVE_INFINITY, toInclusive = false)
    public float getM() {
        return this.mM;
    }

    @FloatRange(from = 0.0d, to = DoubleCompanionObject.POSITIVE_INFINITY, toInclusive = false)
    public float getQ() {
        return this.mQ;
    }

    @FloatRange(from = 0.0d, to = DoubleCompanionObject.POSITIVE_INFINITY, toInclusive = false)
    public float getS() {
        return this.mS;
    }

    @ColorInt
    public int viewed(@NonNull ViewingConditions viewingConditions) {
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
        float[][] fArr = CamUtils.CAM16RGB_TO_XYZ;
        float[] fArr2 = fArr[0];
        float f11 = (fArr2[0] * f8) + (fArr2[1] * f9) + (fArr2[2] * f10);
        float[] fArr3 = fArr[1];
        float f12 = (fArr3[0] * f8) + (fArr3[1] * f9) + (fArr3[2] * f10);
        float[] fArr4 = fArr[2];
        return ColorUtils.XYZToColor(f11, f12, (f8 * fArr4[0]) + (f9 * fArr4[1]) + (f10 * fArr4[2]));
    }

    @ColorInt
    public int viewedInSrgb() {
        return viewed(ViewingConditions.DEFAULT);
    }

    @ColorInt
    public static int toColor(@FloatRange(from = 0.0d, to = 360.0d) float f2, @FloatRange(from = 0.0d, to = DoubleCompanionObject.POSITIVE_INFINITY, toInclusive = false) float f3, @FloatRange(from = 0.0d, to = 100.0d) float f4, @NonNull ViewingConditions viewingConditions) {
        if (f3 < 1.0d || Math.round(f4) <= 0.0d || Math.round(f4) >= 100.0d) {
            return CamUtils.intFromLStar(f4);
        }
        float fMin = f2 < 0.0f ? 0.0f : Math.min(360.0f, f2);
        CamColor camColor = null;
        boolean z2 = true;
        float f5 = 0.0f;
        float f6 = f3;
        while (Math.abs(f5 - f3) >= CHROMA_SEARCH_ENDPOINT) {
            CamColor camColorFindCamByJ = findCamByJ(fMin, f6, f4);
            if (!z2) {
                if (camColorFindCamByJ == null) {
                    f3 = f6;
                } else {
                    f5 = f6;
                    camColor = camColorFindCamByJ;
                }
                f6 = ((f3 - f5) / 2.0f) + f5;
            } else {
                if (camColorFindCamByJ != null) {
                    return camColorFindCamByJ.viewed(viewingConditions);
                }
                f6 = ((f3 - f5) / 2.0f) + f5;
                z2 = false;
            }
        }
        return camColor == null ? CamUtils.intFromLStar(f4) : camColor.viewed(viewingConditions);
    }
}
