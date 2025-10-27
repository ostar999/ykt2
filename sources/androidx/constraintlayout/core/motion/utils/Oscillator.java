package androidx.constraintlayout.core.motion.utils;

import java.util.Arrays;

/* loaded from: classes.dex */
public class Oscillator {
    public static final int BOUNCE = 6;
    public static final int COS_WAVE = 5;
    public static final int CUSTOM = 7;
    public static final int REVERSE_SAW_WAVE = 4;
    public static final int SAW_WAVE = 3;
    public static final int SIN_WAVE = 0;
    public static final int SQUARE_WAVE = 1;
    public static String TAG = "Oscillator";
    public static final int TRIANGLE_WAVE = 2;
    double[] mArea;
    MonotonicCurveFit mCustomCurve;
    String mCustomType;
    int mType;
    float[] mPeriod = new float[0];
    double[] mPosition = new double[0];
    double PI2 = 6.283185307179586d;
    private boolean mNormalized = false;

    public void addPoint(double d3, float f2) {
        int length = this.mPeriod.length + 1;
        int iBinarySearch = Arrays.binarySearch(this.mPosition, d3);
        if (iBinarySearch < 0) {
            iBinarySearch = (-iBinarySearch) - 1;
        }
        this.mPosition = Arrays.copyOf(this.mPosition, length);
        this.mPeriod = Arrays.copyOf(this.mPeriod, length);
        this.mArea = new double[length];
        double[] dArr = this.mPosition;
        System.arraycopy(dArr, iBinarySearch, dArr, iBinarySearch + 1, (length - iBinarySearch) - 1);
        this.mPosition[iBinarySearch] = d3;
        this.mPeriod[iBinarySearch] = f2;
        this.mNormalized = false;
    }

    public double getDP(double d3) {
        if (d3 <= 0.0d) {
            d3 = 1.0E-5d;
        } else if (d3 >= 1.0d) {
            d3 = 0.999999d;
        }
        int iBinarySearch = Arrays.binarySearch(this.mPosition, d3);
        if (iBinarySearch > 0 || iBinarySearch == 0) {
            return 0.0d;
        }
        int i2 = (-iBinarySearch) - 1;
        float[] fArr = this.mPeriod;
        float f2 = fArr[i2];
        int i3 = i2 - 1;
        float f3 = fArr[i3];
        double d4 = f2 - f3;
        double[] dArr = this.mPosition;
        double d5 = dArr[i2];
        double d6 = dArr[i3];
        double d7 = d4 / (d5 - d6);
        return (f3 - (d7 * d6)) + (d3 * d7);
    }

    public double getP(double d3) {
        if (d3 < 0.0d) {
            d3 = 0.0d;
        } else if (d3 > 1.0d) {
            d3 = 1.0d;
        }
        int iBinarySearch = Arrays.binarySearch(this.mPosition, d3);
        if (iBinarySearch > 0) {
            return 1.0d;
        }
        if (iBinarySearch == 0) {
            return 0.0d;
        }
        int i2 = (-iBinarySearch) - 1;
        float[] fArr = this.mPeriod;
        float f2 = fArr[i2];
        int i3 = i2 - 1;
        float f3 = fArr[i3];
        double d4 = f2 - f3;
        double[] dArr = this.mPosition;
        double d5 = dArr[i2];
        double d6 = dArr[i3];
        double d7 = d4 / (d5 - d6);
        return this.mArea[i3] + ((f3 - (d7 * d6)) * (d3 - d6)) + ((d7 * ((d3 * d3) - (d6 * d6))) / 2.0d);
    }

    public double getSlope(double d3, double d4, double d5) {
        double p2 = d4 + getP(d3);
        double dp = getDP(d3) + d5;
        switch (this.mType) {
            case 1:
                return 0.0d;
            case 2:
                return dp * 4.0d * Math.signum((((p2 * 4.0d) + 3.0d) % 4.0d) - 2.0d);
            case 3:
                return dp * 2.0d;
            case 4:
                return (-dp) * 2.0d;
            case 5:
                double d6 = this.PI2;
                return (-d6) * dp * Math.sin(d6 * p2);
            case 6:
                return dp * 4.0d * ((((p2 * 4.0d) + 2.0d) % 4.0d) - 2.0d);
            case 7:
                return this.mCustomCurve.getSlope(p2 % 1.0d, 0);
            default:
                double d7 = this.PI2;
                return dp * d7 * Math.cos(d7 * p2);
        }
    }

    public double getValue(double d3, double d4) {
        double dAbs;
        double p2 = getP(d3) + d4;
        switch (this.mType) {
            case 1:
                return Math.signum(0.5d - (p2 % 1.0d));
            case 2:
                dAbs = Math.abs((((p2 * 4.0d) + 1.0d) % 4.0d) - 2.0d);
                break;
            case 3:
                return (((p2 * 2.0d) + 1.0d) % 2.0d) - 1.0d;
            case 4:
                dAbs = ((p2 * 2.0d) + 1.0d) % 2.0d;
                break;
            case 5:
                return Math.cos(this.PI2 * (d4 + p2));
            case 6:
                double dAbs2 = 1.0d - Math.abs(((p2 * 4.0d) % 4.0d) - 2.0d);
                dAbs = dAbs2 * dAbs2;
                break;
            case 7:
                return this.mCustomCurve.getPos(p2 % 1.0d, 0);
            default:
                return Math.sin(this.PI2 * p2);
        }
        return 1.0d - dAbs;
    }

    public void normalize() {
        double d3 = 0.0d;
        int i2 = 0;
        while (true) {
            if (i2 >= this.mPeriod.length) {
                break;
            }
            d3 += r7[i2];
            i2++;
        }
        double d4 = 0.0d;
        int i3 = 1;
        while (true) {
            float[] fArr = this.mPeriod;
            if (i3 >= fArr.length) {
                break;
            }
            int i4 = i3 - 1;
            float f2 = (fArr[i4] + fArr[i3]) / 2.0f;
            double[] dArr = this.mPosition;
            d4 += (dArr[i3] - dArr[i4]) * f2;
            i3++;
        }
        int i5 = 0;
        while (true) {
            float[] fArr2 = this.mPeriod;
            if (i5 >= fArr2.length) {
                break;
            }
            fArr2[i5] = (float) (fArr2[i5] * (d3 / d4));
            i5++;
        }
        this.mArea[0] = 0.0d;
        int i6 = 1;
        while (true) {
            float[] fArr3 = this.mPeriod;
            if (i6 >= fArr3.length) {
                this.mNormalized = true;
                return;
            }
            int i7 = i6 - 1;
            float f3 = (fArr3[i7] + fArr3[i6]) / 2.0f;
            double[] dArr2 = this.mPosition;
            double d5 = dArr2[i6] - dArr2[i7];
            double[] dArr3 = this.mArea;
            dArr3[i6] = dArr3[i7] + (d5 * f3);
            i6++;
        }
    }

    public void setType(int i2, String str) {
        this.mType = i2;
        this.mCustomType = str;
        if (str != null) {
            this.mCustomCurve = MonotonicCurveFit.buildWave(str);
        }
    }

    public String toString() {
        return "pos =" + Arrays.toString(this.mPosition) + " period=" + Arrays.toString(this.mPeriod);
    }
}
