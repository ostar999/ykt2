package androidx.constraintlayout.core.motion.utils;

import java.lang.reflect.Array;
import java.util.Arrays;

/* loaded from: classes.dex */
public class StepCurve extends Easing {
    private static final boolean DEBUG = false;
    MonotonicCurveFit mCurveFit;

    public StepCurve(String str) {
        this.str = str;
        double[] dArr = new double[str.length() / 2];
        int iIndexOf = str.indexOf(40) + 1;
        int iIndexOf2 = str.indexOf(44, iIndexOf);
        int i2 = 0;
        while (iIndexOf2 != -1) {
            dArr[i2] = Double.parseDouble(str.substring(iIndexOf, iIndexOf2).trim());
            iIndexOf = iIndexOf2 + 1;
            iIndexOf2 = str.indexOf(44, iIndexOf);
            i2++;
        }
        dArr[i2] = Double.parseDouble(str.substring(iIndexOf, str.indexOf(41, iIndexOf)).trim());
        this.mCurveFit = genSpline(Arrays.copyOf(dArr, i2 + 1));
    }

    private static MonotonicCurveFit genSpline(String str) {
        String[] strArrSplit = str.split("\\s+");
        int length = strArrSplit.length;
        double[] dArr = new double[length];
        for (int i2 = 0; i2 < length; i2++) {
            dArr[i2] = Double.parseDouble(strArrSplit[i2]);
        }
        return genSpline(dArr);
    }

    @Override // androidx.constraintlayout.core.motion.utils.Easing
    public double get(double d3) {
        return this.mCurveFit.getPos(d3, 0);
    }

    @Override // androidx.constraintlayout.core.motion.utils.Easing
    public double getDiff(double d3) {
        return this.mCurveFit.getSlope(d3, 0);
    }

    private static MonotonicCurveFit genSpline(double[] dArr) {
        int length = (dArr.length * 3) - 2;
        int length2 = dArr.length - 1;
        double d3 = 1.0d / length2;
        double[][] dArr2 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, length, 1);
        double[] dArr3 = new double[length];
        for (int i2 = 0; i2 < dArr.length; i2++) {
            double d4 = dArr[i2];
            int i3 = i2 + length2;
            dArr2[i3][0] = d4;
            double d5 = i2 * d3;
            dArr3[i3] = d5;
            if (i2 > 0) {
                int i4 = (length2 * 2) + i2;
                dArr2[i4][0] = d4 + 1.0d;
                dArr3[i4] = d5 + 1.0d;
                int i5 = i2 - 1;
                dArr2[i5][0] = (d4 - 1.0d) - d3;
                dArr3[i5] = (d5 - 1.0d) - d3;
            }
        }
        MonotonicCurveFit monotonicCurveFit = new MonotonicCurveFit(dArr3, dArr2);
        System.out.println(" 0 " + monotonicCurveFit.getPos(0.0d, 0));
        System.out.println(" 1 " + monotonicCurveFit.getPos(1.0d, 0));
        return monotonicCurveFit;
    }
}
