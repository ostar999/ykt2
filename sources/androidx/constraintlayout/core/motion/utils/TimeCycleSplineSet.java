package androidx.constraintlayout.core.motion.utils;

import androidx.constraintlayout.core.motion.CustomAttribute;
import androidx.constraintlayout.core.motion.CustomVariable;
import androidx.constraintlayout.core.motion.MotionWidget;
import androidx.constraintlayout.core.motion.utils.KeyFrameArray;
import cn.hutool.core.text.StrPool;
import java.lang.reflect.Array;
import java.text.DecimalFormat;

/* loaded from: classes.dex */
public abstract class TimeCycleSplineSet {
    protected static final int CURVE_OFFSET = 2;
    protected static final int CURVE_PERIOD = 1;
    protected static final int CURVE_VALUE = 0;
    private static final String TAG = "SplineSet";
    protected static float VAL_2PI = 6.2831855f;
    protected int count;
    protected long last_time;
    protected CurveFit mCurveFit;
    protected String mType;
    protected int mWaveShape = 0;
    protected int[] mTimePoints = new int[10];
    protected float[][] mValues = (float[][]) Array.newInstance((Class<?>) Float.TYPE, 10, 3);
    protected float[] mCache = new float[3];
    protected boolean mContinue = false;
    protected float last_cycle = Float.NaN;

    public static class CustomSet extends TimeCycleSplineSet {
        String mAttributeName;
        float[] mCache;
        KeyFrameArray.CustomArray mConstraintAttributeList;
        float[] mTempValues;
        KeyFrameArray.FloatArray mWaveProperties = new KeyFrameArray.FloatArray();

        public CustomSet(String str, KeyFrameArray.CustomArray customArray) {
            this.mAttributeName = str.split(",")[1];
            this.mConstraintAttributeList = customArray;
        }

        @Override // androidx.constraintlayout.core.motion.utils.TimeCycleSplineSet
        public void setPoint(int i2, float f2, float f3, int i3, float f4) {
            throw new RuntimeException("don't call for custom attribute call setPoint(pos, ConstraintAttribute,...)");
        }

        public boolean setProperty(MotionWidget motionWidget, float f2, long j2, KeyCache keyCache) {
            this.mCurveFit.getPos(f2, this.mTempValues);
            float[] fArr = this.mTempValues;
            float f3 = fArr[fArr.length - 2];
            float f4 = fArr[fArr.length - 1];
            long j3 = j2 - this.last_time;
            if (Float.isNaN(this.last_cycle)) {
                float floatValue = keyCache.getFloatValue(motionWidget, this.mAttributeName, 0);
                this.last_cycle = floatValue;
                if (Float.isNaN(floatValue)) {
                    this.last_cycle = 0.0f;
                }
            }
            float f5 = (float) ((this.last_cycle + ((j3 * 1.0E-9d) * f3)) % 1.0d);
            this.last_cycle = f5;
            this.last_time = j2;
            float fCalcWave = calcWave(f5);
            this.mContinue = false;
            int i2 = 0;
            while (true) {
                float[] fArr2 = this.mCache;
                if (i2 >= fArr2.length) {
                    break;
                }
                boolean z2 = this.mContinue;
                float f6 = this.mTempValues[i2];
                this.mContinue = z2 | (((double) f6) != 0.0d);
                fArr2[i2] = (f6 * fCalcWave) + f4;
                i2++;
            }
            motionWidget.setInterpolatedValue(this.mConstraintAttributeList.valueAt(0), this.mCache);
            if (f3 != 0.0f) {
                this.mContinue = true;
            }
            return this.mContinue;
        }

        @Override // androidx.constraintlayout.core.motion.utils.TimeCycleSplineSet
        public void setup(int i2) {
            int size = this.mConstraintAttributeList.size();
            int iNumberOfInterpolatedValues = this.mConstraintAttributeList.valueAt(0).numberOfInterpolatedValues();
            double[] dArr = new double[size];
            int i3 = iNumberOfInterpolatedValues + 2;
            this.mTempValues = new float[i3];
            this.mCache = new float[iNumberOfInterpolatedValues];
            double[][] dArr2 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, size, i3);
            for (int i4 = 0; i4 < size; i4++) {
                int iKeyAt = this.mConstraintAttributeList.keyAt(i4);
                CustomAttribute customAttributeValueAt = this.mConstraintAttributeList.valueAt(i4);
                float[] fArrValueAt = this.mWaveProperties.valueAt(i4);
                dArr[i4] = iKeyAt * 0.01d;
                customAttributeValueAt.getValuesToInterpolate(this.mTempValues);
                int i5 = 0;
                while (true) {
                    if (i5 < this.mTempValues.length) {
                        dArr2[i4][i5] = r8[i5];
                        i5++;
                    }
                }
                double[] dArr3 = dArr2[i4];
                dArr3[iNumberOfInterpolatedValues] = fArrValueAt[0];
                dArr3[iNumberOfInterpolatedValues + 1] = fArrValueAt[1];
            }
            this.mCurveFit = CurveFit.get(i2, dArr, dArr2);
        }

        public void setPoint(int i2, CustomAttribute customAttribute, float f2, int i3, float f3) {
            this.mConstraintAttributeList.append(i2, customAttribute);
            this.mWaveProperties.append(i2, new float[]{f2, f3});
            this.mWaveShape = Math.max(this.mWaveShape, i3);
        }
    }

    public static class CustomVarSet extends TimeCycleSplineSet {
        String mAttributeName;
        float[] mCache;
        KeyFrameArray.CustomVar mConstraintAttributeList;
        float[] mTempValues;
        KeyFrameArray.FloatArray mWaveProperties = new KeyFrameArray.FloatArray();

        public CustomVarSet(String str, KeyFrameArray.CustomVar customVar) {
            this.mAttributeName = str.split(",")[1];
            this.mConstraintAttributeList = customVar;
        }

        @Override // androidx.constraintlayout.core.motion.utils.TimeCycleSplineSet
        public void setPoint(int i2, float f2, float f3, int i3, float f4) {
            throw new RuntimeException("don't call for custom attribute call setPoint(pos, ConstraintAttribute,...)");
        }

        public boolean setProperty(MotionWidget motionWidget, float f2, long j2, KeyCache keyCache) {
            this.mCurveFit.getPos(f2, this.mTempValues);
            float[] fArr = this.mTempValues;
            float f3 = fArr[fArr.length - 2];
            float f4 = fArr[fArr.length - 1];
            long j3 = j2 - this.last_time;
            if (Float.isNaN(this.last_cycle)) {
                float floatValue = keyCache.getFloatValue(motionWidget, this.mAttributeName, 0);
                this.last_cycle = floatValue;
                if (Float.isNaN(floatValue)) {
                    this.last_cycle = 0.0f;
                }
            }
            float f5 = (float) ((this.last_cycle + ((j3 * 1.0E-9d) * f3)) % 1.0d);
            this.last_cycle = f5;
            this.last_time = j2;
            float fCalcWave = calcWave(f5);
            this.mContinue = false;
            int i2 = 0;
            while (true) {
                float[] fArr2 = this.mCache;
                if (i2 >= fArr2.length) {
                    break;
                }
                boolean z2 = this.mContinue;
                float f6 = this.mTempValues[i2];
                this.mContinue = z2 | (((double) f6) != 0.0d);
                fArr2[i2] = (f6 * fCalcWave) + f4;
                i2++;
            }
            this.mConstraintAttributeList.valueAt(0).setInterpolatedValue(motionWidget, this.mCache);
            if (f3 != 0.0f) {
                this.mContinue = true;
            }
            return this.mContinue;
        }

        @Override // androidx.constraintlayout.core.motion.utils.TimeCycleSplineSet
        public void setup(int i2) {
            int size = this.mConstraintAttributeList.size();
            int iNumberOfInterpolatedValues = this.mConstraintAttributeList.valueAt(0).numberOfInterpolatedValues();
            double[] dArr = new double[size];
            int i3 = iNumberOfInterpolatedValues + 2;
            this.mTempValues = new float[i3];
            this.mCache = new float[iNumberOfInterpolatedValues];
            double[][] dArr2 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, size, i3);
            for (int i4 = 0; i4 < size; i4++) {
                int iKeyAt = this.mConstraintAttributeList.keyAt(i4);
                CustomVariable customVariableValueAt = this.mConstraintAttributeList.valueAt(i4);
                float[] fArrValueAt = this.mWaveProperties.valueAt(i4);
                dArr[i4] = iKeyAt * 0.01d;
                customVariableValueAt.getValuesToInterpolate(this.mTempValues);
                int i5 = 0;
                while (true) {
                    if (i5 < this.mTempValues.length) {
                        dArr2[i4][i5] = r8[i5];
                        i5++;
                    }
                }
                double[] dArr3 = dArr2[i4];
                dArr3[iNumberOfInterpolatedValues] = fArrValueAt[0];
                dArr3[iNumberOfInterpolatedValues + 1] = fArrValueAt[1];
            }
            this.mCurveFit = CurveFit.get(i2, dArr, dArr2);
        }

        public void setPoint(int i2, CustomVariable customVariable, float f2, int i3, float f3) {
            this.mConstraintAttributeList.append(i2, customVariable);
            this.mWaveProperties.append(i2, new float[]{f2, f3});
            this.mWaveShape = Math.max(this.mWaveShape, i3);
        }
    }

    public static class Sort {
        public static void doubleQuickSort(int[] iArr, float[][] fArr, int i2, int i3) {
            int[] iArr2 = new int[iArr.length + 10];
            iArr2[0] = i3;
            iArr2[1] = i2;
            int i4 = 2;
            while (i4 > 0) {
                int i5 = i4 - 1;
                int i6 = iArr2[i5];
                i4 = i5 - 1;
                int i7 = iArr2[i4];
                if (i6 < i7) {
                    int iPartition = partition(iArr, fArr, i6, i7);
                    int i8 = i4 + 1;
                    iArr2[i4] = iPartition - 1;
                    int i9 = i8 + 1;
                    iArr2[i8] = i6;
                    int i10 = i9 + 1;
                    iArr2[i9] = i7;
                    i4 = i10 + 1;
                    iArr2[i10] = iPartition + 1;
                }
            }
        }

        private static int partition(int[] iArr, float[][] fArr, int i2, int i3) {
            int i4 = iArr[i3];
            int i5 = i2;
            while (i2 < i3) {
                if (iArr[i2] <= i4) {
                    swap(iArr, fArr, i5, i2);
                    i5++;
                }
                i2++;
            }
            swap(iArr, fArr, i5, i3);
            return i5;
        }

        private static void swap(int[] iArr, float[][] fArr, int i2, int i3) {
            int i4 = iArr[i2];
            iArr[i2] = iArr[i3];
            iArr[i3] = i4;
            float[] fArr2 = fArr[i2];
            fArr[i2] = fArr[i3];
            fArr[i3] = fArr2;
        }
    }

    public float calcWave(float f2) {
        float fAbs;
        switch (this.mWaveShape) {
            case 1:
                return Math.signum(f2 * VAL_2PI);
            case 2:
                fAbs = Math.abs(f2);
                break;
            case 3:
                return (((f2 * 2.0f) + 1.0f) % 2.0f) - 1.0f;
            case 4:
                fAbs = ((f2 * 2.0f) + 1.0f) % 2.0f;
                break;
            case 5:
                return (float) Math.cos(f2 * VAL_2PI);
            case 6:
                float fAbs2 = 1.0f - Math.abs(((f2 * 4.0f) % 4.0f) - 2.0f);
                fAbs = fAbs2 * fAbs2;
                break;
            default:
                return (float) Math.sin(f2 * VAL_2PI);
        }
        return 1.0f - fAbs;
    }

    public CurveFit getCurveFit() {
        return this.mCurveFit;
    }

    public void setPoint(int i2, float f2, float f3, int i3, float f4) {
        int[] iArr = this.mTimePoints;
        int i4 = this.count;
        iArr[i4] = i2;
        float[] fArr = this.mValues[i4];
        fArr[0] = f2;
        fArr[1] = f3;
        fArr[2] = f4;
        this.mWaveShape = Math.max(this.mWaveShape, i3);
        this.count++;
    }

    public void setStartTime(long j2) {
        this.last_time = j2;
    }

    public void setType(String str) {
        this.mType = str;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0063  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void setup(int r12) {
        /*
            r11 = this;
            int r0 = r11.count
            if (r0 != 0) goto L1d
            java.io.PrintStream r12 = java.lang.System.err
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "Error no points added to "
            r0.append(r1)
            java.lang.String r1 = r11.mType
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r12.println(r0)
            return
        L1d:
            int[] r1 = r11.mTimePoints
            float[][] r2 = r11.mValues
            r3 = 1
            int r0 = r0 - r3
            r4 = 0
            androidx.constraintlayout.core.motion.utils.TimeCycleSplineSet.Sort.doubleQuickSort(r1, r2, r4, r0)
            r0 = r3
            r1 = r4
        L29:
            int[] r2 = r11.mTimePoints
            int r5 = r2.length
            if (r0 >= r5) goto L3b
            r5 = r2[r0]
            int r6 = r0 + (-1)
            r2 = r2[r6]
            if (r5 == r2) goto L38
            int r1 = r1 + 1
        L38:
            int r0 = r0 + 1
            goto L29
        L3b:
            if (r1 != 0) goto L3e
            r1 = r3
        L3e:
            double[] r0 = new double[r1]
            r2 = 2
            int[] r5 = new int[r2]
            r6 = 3
            r5[r3] = r6
            r5[r4] = r1
            java.lang.Class r1 = java.lang.Double.TYPE
            java.lang.Object r1 = java.lang.reflect.Array.newInstance(r1, r5)
            double[][] r1 = (double[][]) r1
            r5 = r4
            r6 = r5
        L52:
            int r7 = r11.count
            if (r5 >= r7) goto L8a
            if (r5 <= 0) goto L63
            int[] r7 = r11.mTimePoints
            r8 = r7[r5]
            int r9 = r5 + (-1)
            r7 = r7[r9]
            if (r8 != r7) goto L63
            goto L87
        L63:
            int[] r7 = r11.mTimePoints
            r7 = r7[r5]
            double r7 = (double) r7
            r9 = 4576918229304087675(0x3f847ae147ae147b, double:0.01)
            double r7 = r7 * r9
            r0[r6] = r7
            r7 = r1[r6]
            float[][] r8 = r11.mValues
            r8 = r8[r5]
            r9 = r8[r4]
            double r9 = (double) r9
            r7[r4] = r9
            r9 = r8[r3]
            double r9 = (double) r9
            r7[r3] = r9
            r8 = r8[r2]
            double r8 = (double) r8
            r7[r2] = r8
            int r6 = r6 + 1
        L87:
            int r5 = r5 + 1
            goto L52
        L8a:
            androidx.constraintlayout.core.motion.utils.CurveFit r12 = androidx.constraintlayout.core.motion.utils.CurveFit.get(r12, r0, r1)
            r11.mCurveFit = r12
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.motion.utils.TimeCycleSplineSet.setup(int):void");
    }

    public String toString() {
        String str = this.mType;
        DecimalFormat decimalFormat = new DecimalFormat("##.##");
        for (int i2 = 0; i2 < this.count; i2++) {
            str = str + StrPool.BRACKET_START + this.mTimePoints[i2] + " , " + decimalFormat.format(this.mValues[i2]) + "] ";
        }
        return str;
    }
}
