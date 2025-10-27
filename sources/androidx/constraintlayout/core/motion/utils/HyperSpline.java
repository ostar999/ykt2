package androidx.constraintlayout.core.motion.utils;

import java.lang.reflect.Array;

/* loaded from: classes.dex */
public class HyperSpline {
    double[][] mCtl;
    Cubic[][] mCurve;
    double[] mCurveLength;
    int mDimensionality;
    int mPoints;
    double mTotalLength;

    public static class Cubic {
        double mA;
        double mB;
        double mC;
        double mD;

        public Cubic(double d3, double d4, double d5, double d6) {
            this.mA = d3;
            this.mB = d4;
            this.mC = d5;
            this.mD = d6;
        }

        public double eval(double d3) {
            return (((((this.mD * d3) + this.mC) * d3) + this.mB) * d3) + this.mA;
        }

        public double vel(double d3) {
            return (((this.mD * 3.0d * d3) + (this.mC * 2.0d)) * d3) + this.mB;
        }
    }

    public HyperSpline(double[][] dArr) {
        setup(dArr);
    }

    public static Cubic[] calcNaturalCubic(int i2, double[] dArr) {
        double[] dArr2 = new double[i2];
        double[] dArr3 = new double[i2];
        double[] dArr4 = new double[i2];
        int i3 = i2 - 1;
        int i4 = 0;
        dArr2[0] = 0.5d;
        int i5 = 1;
        for (int i6 = 1; i6 < i3; i6++) {
            dArr2[i6] = 1.0d / (4.0d - dArr2[i6 - 1]);
        }
        int i7 = i3 - 1;
        dArr2[i3] = 1.0d / (2.0d - dArr2[i7]);
        dArr3[0] = (dArr[1] - dArr[0]) * 3.0d * dArr2[0];
        while (i5 < i3) {
            int i8 = i5 + 1;
            int i9 = i5 - 1;
            dArr3[i5] = (((dArr[i8] - dArr[i9]) * 3.0d) - dArr3[i9]) * dArr2[i5];
            i5 = i8;
        }
        double d3 = (((dArr[i3] - dArr[i7]) * 3.0d) - dArr3[i7]) * dArr2[i3];
        dArr3[i3] = d3;
        dArr4[i3] = d3;
        while (i7 >= 0) {
            dArr4[i7] = dArr3[i7] - (dArr2[i7] * dArr4[i7 + 1]);
            i7--;
        }
        Cubic[] cubicArr = new Cubic[i3];
        while (i4 < i3) {
            double d4 = dArr[i4];
            double d5 = dArr4[i4];
            int i10 = i4 + 1;
            double d6 = dArr[i10];
            double d7 = dArr4[i10];
            cubicArr[i4] = new Cubic((float) d4, d5, (((d6 - d4) * 3.0d) - (d5 * 2.0d)) - d7, ((d4 - d6) * 2.0d) + d5 + d7);
            i4 = i10;
        }
        return cubicArr;
    }

    public double approxLength(Cubic[] cubicArr) {
        int i2;
        int length = cubicArr.length;
        double[] dArr = new double[cubicArr.length];
        double d3 = 0.0d;
        double d4 = 0.0d;
        double dSqrt = 0.0d;
        while (true) {
            i2 = 0;
            if (d4 >= 1.0d) {
                break;
            }
            double d5 = 0.0d;
            while (i2 < cubicArr.length) {
                double d6 = dArr[i2];
                double dEval = cubicArr[i2].eval(d4);
                dArr[i2] = dEval;
                double d7 = d6 - dEval;
                d5 += d7 * d7;
                i2++;
            }
            if (d4 > 0.0d) {
                dSqrt += Math.sqrt(d5);
            }
            d4 += 0.1d;
        }
        while (i2 < cubicArr.length) {
            double d8 = dArr[i2];
            double dEval2 = cubicArr[i2].eval(1.0d);
            dArr[i2] = dEval2;
            double d9 = d8 - dEval2;
            d3 += d9 * d9;
            i2++;
        }
        return dSqrt + Math.sqrt(d3);
    }

    public void getPos(double d3, double[] dArr) {
        double d4 = d3 * this.mTotalLength;
        int i2 = 0;
        while (true) {
            double[] dArr2 = this.mCurveLength;
            if (i2 >= dArr2.length - 1) {
                break;
            }
            double d5 = dArr2[i2];
            if (d5 >= d4) {
                break;
            }
            d4 -= d5;
            i2++;
        }
        for (int i3 = 0; i3 < dArr.length; i3++) {
            dArr[i3] = this.mCurve[i3][i2].eval(d4 / this.mCurveLength[i2]);
        }
    }

    public void getVelocity(double d3, double[] dArr) {
        double d4 = d3 * this.mTotalLength;
        int i2 = 0;
        while (true) {
            double[] dArr2 = this.mCurveLength;
            if (i2 >= dArr2.length - 1) {
                break;
            }
            double d5 = dArr2[i2];
            if (d5 >= d4) {
                break;
            }
            d4 -= d5;
            i2++;
        }
        for (int i3 = 0; i3 < dArr.length; i3++) {
            dArr[i3] = this.mCurve[i3][i2].vel(d4 / this.mCurveLength[i2]);
        }
    }

    public void setup(double[][] dArr) {
        int i2;
        int length = dArr[0].length;
        this.mDimensionality = length;
        int length2 = dArr.length;
        this.mPoints = length2;
        this.mCtl = (double[][]) Array.newInstance((Class<?>) Double.TYPE, length, length2);
        this.mCurve = new Cubic[this.mDimensionality][];
        for (int i3 = 0; i3 < this.mDimensionality; i3++) {
            for (int i4 = 0; i4 < this.mPoints; i4++) {
                this.mCtl[i3][i4] = dArr[i4][i3];
            }
        }
        int i5 = 0;
        while (true) {
            i2 = this.mDimensionality;
            if (i5 >= i2) {
                break;
            }
            Cubic[][] cubicArr = this.mCurve;
            double[] dArr2 = this.mCtl[i5];
            cubicArr[i5] = calcNaturalCubic(dArr2.length, dArr2);
            i5++;
        }
        this.mCurveLength = new double[this.mPoints - 1];
        this.mTotalLength = 0.0d;
        Cubic[] cubicArr2 = new Cubic[i2];
        for (int i6 = 0; i6 < this.mCurveLength.length; i6++) {
            for (int i7 = 0; i7 < this.mDimensionality; i7++) {
                cubicArr2[i7] = this.mCurve[i7][i6];
            }
            double d3 = this.mTotalLength;
            double[] dArr3 = this.mCurveLength;
            double dApproxLength = approxLength(cubicArr2);
            dArr3[i6] = dApproxLength;
            this.mTotalLength = d3 + dApproxLength;
        }
    }

    public HyperSpline() {
    }

    public void getPos(double d3, float[] fArr) {
        double d4 = d3 * this.mTotalLength;
        int i2 = 0;
        while (true) {
            double[] dArr = this.mCurveLength;
            if (i2 >= dArr.length - 1) {
                break;
            }
            double d5 = dArr[i2];
            if (d5 >= d4) {
                break;
            }
            d4 -= d5;
            i2++;
        }
        for (int i3 = 0; i3 < fArr.length; i3++) {
            fArr[i3] = (float) this.mCurve[i3][i2].eval(d4 / this.mCurveLength[i2]);
        }
    }

    public double getPos(double d3, int i2) {
        double[] dArr;
        double d4 = d3 * this.mTotalLength;
        int i3 = 0;
        while (true) {
            dArr = this.mCurveLength;
            if (i3 >= dArr.length - 1) {
                break;
            }
            double d5 = dArr[i3];
            if (d5 >= d4) {
                break;
            }
            d4 -= d5;
            i3++;
        }
        return this.mCurve[i2][i3].eval(d4 / dArr[i3]);
    }
}
