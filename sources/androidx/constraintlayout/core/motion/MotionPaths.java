package androidx.constraintlayout.core.motion;

import androidx.constraintlayout.core.motion.MotionWidget;
import androidx.constraintlayout.core.motion.key.MotionKeyPosition;
import androidx.constraintlayout.core.motion.utils.Easing;
import java.util.Arrays;
import java.util.HashMap;

/* loaded from: classes.dex */
public class MotionPaths implements Comparable<MotionPaths> {
    public static final int CARTESIAN = 0;
    public static final boolean DEBUG = false;
    static final int OFF_HEIGHT = 4;
    static final int OFF_PATH_ROTATE = 5;
    static final int OFF_POSITION = 0;
    static final int OFF_WIDTH = 3;
    static final int OFF_X = 1;
    static final int OFF_Y = 2;
    public static final boolean OLD_WAY = false;
    public static final int PERPENDICULAR = 1;
    public static final int SCREEN = 2;
    public static final String TAG = "MotionPaths";
    static String[] names = {"position", "x", "y", "width", "height", "pathRotate"};
    HashMap<String, CustomVariable> customAttributes;
    float height;
    int mAnimateCircleAngleTo;
    int mAnimateRelativeTo;
    int mDrawPath;
    Easing mKeyFrameEasing;
    int mMode;
    int mPathMotionArc;
    float mPathRotate;
    float mProgress;
    float mRelativeAngle;
    Motion mRelativeToController;
    double[] mTempDelta;
    double[] mTempValue;
    float position;
    float time;
    float width;

    /* renamed from: x, reason: collision with root package name */
    float f1769x;

    /* renamed from: y, reason: collision with root package name */
    float f1770y;

    public MotionPaths() {
        this.mDrawPath = 0;
        this.mPathRotate = Float.NaN;
        this.mProgress = Float.NaN;
        this.mPathMotionArc = -1;
        this.mAnimateRelativeTo = -1;
        this.mRelativeAngle = Float.NaN;
        this.mRelativeToController = null;
        this.customAttributes = new HashMap<>();
        this.mMode = 0;
        this.mTempValue = new double[18];
        this.mTempDelta = new double[18];
    }

    private boolean diff(float f2, float f3) {
        return (Float.isNaN(f2) || Float.isNaN(f3)) ? Float.isNaN(f2) != Float.isNaN(f3) : Math.abs(f2 - f3) > 1.0E-6f;
    }

    private static final float xRotate(float f2, float f3, float f4, float f5, float f6, float f7) {
        return (((f6 - f4) * f3) - ((f7 - f5) * f2)) + f4;
    }

    private static final float yRotate(float f2, float f3, float f4, float f5, float f6, float f7) {
        return ((f6 - f4) * f2) + ((f7 - f5) * f3) + f5;
    }

    public void applyParameters(MotionWidget motionWidget) {
        this.mKeyFrameEasing = Easing.getInterpolator(motionWidget.motion.mTransitionEasing);
        MotionWidget.Motion motion = motionWidget.motion;
        this.mPathMotionArc = motion.mPathMotionArc;
        this.mAnimateRelativeTo = motion.mAnimateRelativeTo;
        this.mPathRotate = motion.mPathRotate;
        this.mDrawPath = motion.mDrawPath;
        this.mAnimateCircleAngleTo = motion.mAnimateCircleAngleTo;
        this.mProgress = motionWidget.propertySet.mProgress;
        this.mRelativeAngle = 0.0f;
        for (String str : motionWidget.getCustomAttributeNames()) {
            CustomVariable customAttribute = motionWidget.getCustomAttribute(str);
            if (customAttribute != null && customAttribute.isContinuous()) {
                this.customAttributes.put(str, customAttribute);
            }
        }
    }

    public void configureRelativeTo(Motion motion) {
        motion.getPos(this.mProgress);
    }

    public void different(MotionPaths motionPaths, boolean[] zArr, String[] strArr, boolean z2) {
        boolean zDiff = diff(this.f1769x, motionPaths.f1769x);
        boolean zDiff2 = diff(this.f1770y, motionPaths.f1770y);
        zArr[0] = zArr[0] | diff(this.position, motionPaths.position);
        boolean z3 = zDiff | zDiff2 | z2;
        zArr[1] = zArr[1] | z3;
        zArr[2] = z3 | zArr[2];
        zArr[3] = zArr[3] | diff(this.width, motionPaths.width);
        zArr[4] = diff(this.height, motionPaths.height) | zArr[4];
    }

    public void fillStandard(double[] dArr, int[] iArr) {
        float[] fArr = {this.position, this.f1769x, this.f1770y, this.width, this.height, this.mPathRotate};
        int i2 = 0;
        for (int i3 : iArr) {
            if (i3 < 6) {
                dArr[i2] = fArr[r4];
                i2++;
            }
        }
    }

    public void getBounds(int[] iArr, double[] dArr, float[] fArr, int i2) {
        float f2 = this.width;
        float f3 = this.height;
        for (int i3 = 0; i3 < iArr.length; i3++) {
            float f4 = (float) dArr[i3];
            int i4 = iArr[i3];
            if (i4 == 3) {
                f2 = f4;
            } else if (i4 == 4) {
                f3 = f4;
            }
        }
        fArr[i2] = f2;
        fArr[i2 + 1] = f3;
    }

    public void getCenter(double d3, int[] iArr, double[] dArr, float[] fArr, int i2) {
        float fSin = this.f1769x;
        float fCos = this.f1770y;
        float f2 = this.width;
        float f3 = this.height;
        for (int i3 = 0; i3 < iArr.length; i3++) {
            float f4 = (float) dArr[i3];
            int i4 = iArr[i3];
            if (i4 == 1) {
                fSin = f4;
            } else if (i4 == 2) {
                fCos = f4;
            } else if (i4 == 3) {
                f2 = f4;
            } else if (i4 == 4) {
                f3 = f4;
            }
        }
        Motion motion = this.mRelativeToController;
        if (motion != null) {
            float[] fArr2 = new float[2];
            motion.getCenter(d3, fArr2, new float[2]);
            float f5 = fArr2[0];
            float f6 = fArr2[1];
            double d4 = f5;
            double d5 = fSin;
            double d6 = fCos;
            fSin = (float) ((d4 + (Math.sin(d6) * d5)) - (f2 / 2.0f));
            fCos = (float) ((f6 - (d5 * Math.cos(d6))) - (f3 / 2.0f));
        }
        fArr[i2] = fSin + (f2 / 2.0f) + 0.0f;
        fArr[i2 + 1] = fCos + (f3 / 2.0f) + 0.0f;
    }

    public void getCenterVelocity(double d3, int[] iArr, double[] dArr, float[] fArr, int i2) {
        float fSin = this.f1769x;
        float fCos = this.f1770y;
        float f2 = this.width;
        float f3 = this.height;
        for (int i3 = 0; i3 < iArr.length; i3++) {
            float f4 = (float) dArr[i3];
            int i4 = iArr[i3];
            if (i4 == 1) {
                fSin = f4;
            } else if (i4 == 2) {
                fCos = f4;
            } else if (i4 == 3) {
                f2 = f4;
            } else if (i4 == 4) {
                f3 = f4;
            }
        }
        Motion motion = this.mRelativeToController;
        if (motion != null) {
            float[] fArr2 = new float[2];
            motion.getCenter(d3, fArr2, new float[2]);
            float f5 = fArr2[0];
            float f6 = fArr2[1];
            double d4 = f5;
            double d5 = fSin;
            double d6 = fCos;
            fSin = (float) ((d4 + (Math.sin(d6) * d5)) - (f2 / 2.0f));
            fCos = (float) ((f6 - (d5 * Math.cos(d6))) - (f3 / 2.0f));
        }
        fArr[i2] = fSin + (f2 / 2.0f) + 0.0f;
        fArr[i2 + 1] = fCos + (f3 / 2.0f) + 0.0f;
    }

    public int getCustomData(String str, double[] dArr, int i2) {
        CustomVariable customVariable = this.customAttributes.get(str);
        int i3 = 0;
        if (customVariable == null) {
            return 0;
        }
        if (customVariable.numberOfInterpolatedValues() == 1) {
            dArr[i2] = customVariable.getValueToInterpolate();
            return 1;
        }
        int iNumberOfInterpolatedValues = customVariable.numberOfInterpolatedValues();
        customVariable.getValuesToInterpolate(new float[iNumberOfInterpolatedValues]);
        while (i3 < iNumberOfInterpolatedValues) {
            dArr[i2] = r2[i3];
            i3++;
            i2++;
        }
        return iNumberOfInterpolatedValues;
    }

    public int getCustomDataCount(String str) {
        CustomVariable customVariable = this.customAttributes.get(str);
        if (customVariable == null) {
            return 0;
        }
        return customVariable.numberOfInterpolatedValues();
    }

    public void getRect(int[] iArr, double[] dArr, float[] fArr, int i2) {
        float f2 = this.f1769x;
        float fCos = this.f1770y;
        float f3 = this.width;
        float f4 = this.height;
        for (int i3 = 0; i3 < iArr.length; i3++) {
            float f5 = (float) dArr[i3];
            int i4 = iArr[i3];
            if (i4 == 1) {
                f2 = f5;
            } else if (i4 == 2) {
                fCos = f5;
            } else if (i4 == 3) {
                f3 = f5;
            } else if (i4 == 4) {
                f4 = f5;
            }
        }
        Motion motion = this.mRelativeToController;
        if (motion != null) {
            float centerX = motion.getCenterX();
            float centerY = this.mRelativeToController.getCenterY();
            double d3 = f2;
            double d4 = fCos;
            float fSin = (float) ((centerX + (Math.sin(d4) * d3)) - (f3 / 2.0f));
            fCos = (float) ((centerY - (d3 * Math.cos(d4))) - (f4 / 2.0f));
            f2 = fSin;
        }
        float f6 = f3 + f2;
        float f7 = f4 + fCos;
        Float.isNaN(Float.NaN);
        Float.isNaN(Float.NaN);
        int i5 = i2 + 1;
        fArr[i2] = f2 + 0.0f;
        int i6 = i5 + 1;
        fArr[i5] = fCos + 0.0f;
        int i7 = i6 + 1;
        fArr[i6] = f6 + 0.0f;
        int i8 = i7 + 1;
        fArr[i7] = fCos + 0.0f;
        int i9 = i8 + 1;
        fArr[i8] = f6 + 0.0f;
        int i10 = i9 + 1;
        fArr[i9] = f7 + 0.0f;
        fArr[i10] = f2 + 0.0f;
        fArr[i10 + 1] = f7 + 0.0f;
    }

    public boolean hasCustomData(String str) {
        return this.customAttributes.containsKey(str);
    }

    public void initCartesian(MotionKeyPosition motionKeyPosition, MotionPaths motionPaths, MotionPaths motionPaths2) {
        float f2 = motionKeyPosition.mFramePosition / 100.0f;
        this.time = f2;
        this.mDrawPath = motionKeyPosition.mDrawPath;
        float f3 = Float.isNaN(motionKeyPosition.mPercentWidth) ? f2 : motionKeyPosition.mPercentWidth;
        float f4 = Float.isNaN(motionKeyPosition.mPercentHeight) ? f2 : motionKeyPosition.mPercentHeight;
        float f5 = motionPaths2.width;
        float f6 = motionPaths.width;
        float f7 = motionPaths2.height;
        float f8 = motionPaths.height;
        this.position = this.time;
        float f9 = motionPaths.f1769x;
        float f10 = motionPaths.f1770y;
        float f11 = (motionPaths2.f1769x + (f5 / 2.0f)) - ((f6 / 2.0f) + f9);
        float f12 = (motionPaths2.f1770y + (f7 / 2.0f)) - (f10 + (f8 / 2.0f));
        float f13 = ((f5 - f6) * f3) / 2.0f;
        this.f1769x = (int) ((f9 + (f11 * f2)) - f13);
        float f14 = ((f7 - f8) * f4) / 2.0f;
        this.f1770y = (int) ((f10 + (f12 * f2)) - f14);
        this.width = (int) (f6 + r9);
        this.height = (int) (f8 + r12);
        float f15 = Float.isNaN(motionKeyPosition.mPercentX) ? f2 : motionKeyPosition.mPercentX;
        float f16 = Float.isNaN(motionKeyPosition.mAltPercentY) ? 0.0f : motionKeyPosition.mAltPercentY;
        if (!Float.isNaN(motionKeyPosition.mPercentY)) {
            f2 = motionKeyPosition.mPercentY;
        }
        float f17 = Float.isNaN(motionKeyPosition.mAltPercentX) ? 0.0f : motionKeyPosition.mAltPercentX;
        this.mMode = 0;
        this.f1769x = (int) (((motionPaths.f1769x + (f15 * f11)) + (f17 * f12)) - f13);
        this.f1770y = (int) (((motionPaths.f1770y + (f11 * f16)) + (f12 * f2)) - f14);
        this.mKeyFrameEasing = Easing.getInterpolator(motionKeyPosition.mTransitionEasing);
        this.mPathMotionArc = motionKeyPosition.mPathMotionArc;
    }

    public void initPath(MotionKeyPosition motionKeyPosition, MotionPaths motionPaths, MotionPaths motionPaths2) {
        float f2 = motionKeyPosition.mFramePosition / 100.0f;
        this.time = f2;
        this.mDrawPath = motionKeyPosition.mDrawPath;
        float f3 = Float.isNaN(motionKeyPosition.mPercentWidth) ? f2 : motionKeyPosition.mPercentWidth;
        float f4 = Float.isNaN(motionKeyPosition.mPercentHeight) ? f2 : motionKeyPosition.mPercentHeight;
        float f5 = motionPaths2.width - motionPaths.width;
        float f6 = motionPaths2.height - motionPaths.height;
        this.position = this.time;
        if (!Float.isNaN(motionKeyPosition.mPercentX)) {
            f2 = motionKeyPosition.mPercentX;
        }
        float f7 = motionPaths.f1769x;
        float f8 = motionPaths.width;
        float f9 = motionPaths.f1770y;
        float f10 = motionPaths.height;
        float f11 = (motionPaths2.f1769x + (motionPaths2.width / 2.0f)) - ((f8 / 2.0f) + f7);
        float f12 = (motionPaths2.f1770y + (motionPaths2.height / 2.0f)) - ((f10 / 2.0f) + f9);
        float f13 = f11 * f2;
        float f14 = (f5 * f3) / 2.0f;
        this.f1769x = (int) ((f7 + f13) - f14);
        float f15 = f2 * f12;
        float f16 = (f6 * f4) / 2.0f;
        this.f1770y = (int) ((f9 + f15) - f16);
        this.width = (int) (f8 + r7);
        this.height = (int) (f10 + r8);
        float f17 = Float.isNaN(motionKeyPosition.mPercentY) ? 0.0f : motionKeyPosition.mPercentY;
        this.mMode = 1;
        float f18 = (int) ((motionPaths.f1769x + f13) - f14);
        float f19 = (int) ((motionPaths.f1770y + f15) - f16);
        this.f1769x = f18 + ((-f12) * f17);
        this.f1770y = f19 + (f11 * f17);
        this.mAnimateRelativeTo = this.mAnimateRelativeTo;
        this.mKeyFrameEasing = Easing.getInterpolator(motionKeyPosition.mTransitionEasing);
        this.mPathMotionArc = motionKeyPosition.mPathMotionArc;
    }

    public void initPolar(int i2, int i3, MotionKeyPosition motionKeyPosition, MotionPaths motionPaths, MotionPaths motionPaths2) {
        float fMin;
        float f2;
        float f3 = motionKeyPosition.mFramePosition / 100.0f;
        this.time = f3;
        this.mDrawPath = motionKeyPosition.mDrawPath;
        this.mMode = motionKeyPosition.mPositionType;
        float f4 = Float.isNaN(motionKeyPosition.mPercentWidth) ? f3 : motionKeyPosition.mPercentWidth;
        float f5 = Float.isNaN(motionKeyPosition.mPercentHeight) ? f3 : motionKeyPosition.mPercentHeight;
        float f6 = motionPaths2.width;
        float f7 = motionPaths.width;
        float f8 = motionPaths2.height;
        float f9 = motionPaths.height;
        this.position = this.time;
        this.width = (int) (f7 + ((f6 - f7) * f4));
        this.height = (int) (f9 + ((f8 - f9) * f5));
        int i4 = motionKeyPosition.mPositionType;
        if (i4 == 1) {
            float f10 = Float.isNaN(motionKeyPosition.mPercentX) ? f3 : motionKeyPosition.mPercentX;
            float f11 = motionPaths2.f1769x;
            float f12 = motionPaths.f1769x;
            this.f1769x = (f10 * (f11 - f12)) + f12;
            if (!Float.isNaN(motionKeyPosition.mPercentY)) {
                f3 = motionKeyPosition.mPercentY;
            }
            float f13 = motionPaths2.f1770y;
            float f14 = motionPaths.f1770y;
            this.f1770y = (f3 * (f13 - f14)) + f14;
        } else if (i4 != 2) {
            float f15 = Float.isNaN(motionKeyPosition.mPercentX) ? f3 : motionKeyPosition.mPercentX;
            float f16 = motionPaths2.f1769x;
            float f17 = motionPaths.f1769x;
            this.f1769x = (f15 * (f16 - f17)) + f17;
            if (!Float.isNaN(motionKeyPosition.mPercentY)) {
                f3 = motionKeyPosition.mPercentY;
            }
            float f18 = motionPaths2.f1770y;
            float f19 = motionPaths.f1770y;
            this.f1770y = (f3 * (f18 - f19)) + f19;
        } else {
            if (Float.isNaN(motionKeyPosition.mPercentX)) {
                float f20 = motionPaths2.f1769x;
                float f21 = motionPaths.f1769x;
                fMin = ((f20 - f21) * f3) + f21;
            } else {
                fMin = Math.min(f5, f4) * motionKeyPosition.mPercentX;
            }
            this.f1769x = fMin;
            if (Float.isNaN(motionKeyPosition.mPercentY)) {
                float f22 = motionPaths2.f1770y;
                float f23 = motionPaths.f1770y;
                f2 = (f3 * (f22 - f23)) + f23;
            } else {
                f2 = motionKeyPosition.mPercentY;
            }
            this.f1770y = f2;
        }
        this.mAnimateRelativeTo = motionPaths.mAnimateRelativeTo;
        this.mKeyFrameEasing = Easing.getInterpolator(motionKeyPosition.mTransitionEasing);
        this.mPathMotionArc = motionKeyPosition.mPathMotionArc;
    }

    public void initScreen(int i2, int i3, MotionKeyPosition motionKeyPosition, MotionPaths motionPaths, MotionPaths motionPaths2) {
        float f2 = motionKeyPosition.mFramePosition / 100.0f;
        this.time = f2;
        this.mDrawPath = motionKeyPosition.mDrawPath;
        float f3 = Float.isNaN(motionKeyPosition.mPercentWidth) ? f2 : motionKeyPosition.mPercentWidth;
        float f4 = Float.isNaN(motionKeyPosition.mPercentHeight) ? f2 : motionKeyPosition.mPercentHeight;
        float f5 = motionPaths2.width;
        float f6 = motionPaths.width;
        float f7 = motionPaths2.height;
        float f8 = motionPaths.height;
        this.position = this.time;
        float f9 = motionPaths.f1769x;
        float f10 = motionPaths.f1770y;
        float f11 = motionPaths2.f1769x + (f5 / 2.0f);
        float f12 = motionPaths2.f1770y + (f7 / 2.0f);
        float f13 = (f5 - f6) * f3;
        this.f1769x = (int) ((f9 + ((f11 - ((f6 / 2.0f) + f9)) * f2)) - (f13 / 2.0f));
        float f14 = (f7 - f8) * f4;
        this.f1770y = (int) ((f10 + ((f12 - (f10 + (f8 / 2.0f))) * f2)) - (f14 / 2.0f));
        this.width = (int) (f6 + f13);
        this.height = (int) (f8 + f14);
        this.mMode = 2;
        if (!Float.isNaN(motionKeyPosition.mPercentX)) {
            this.f1769x = (int) (motionKeyPosition.mPercentX * ((int) (i2 - this.width)));
        }
        if (!Float.isNaN(motionKeyPosition.mPercentY)) {
            this.f1770y = (int) (motionKeyPosition.mPercentY * ((int) (i3 - this.height)));
        }
        this.mAnimateRelativeTo = this.mAnimateRelativeTo;
        this.mKeyFrameEasing = Easing.getInterpolator(motionKeyPosition.mTransitionEasing);
        this.mPathMotionArc = motionKeyPosition.mPathMotionArc;
    }

    public void setBounds(float f2, float f3, float f4, float f5) {
        this.f1769x = f2;
        this.f1770y = f3;
        this.width = f4;
        this.height = f5;
    }

    public void setDpDt(float f2, float f3, float[] fArr, int[] iArr, double[] dArr, double[] dArr2) {
        float f4 = 0.0f;
        float f5 = 0.0f;
        float f6 = 0.0f;
        float f7 = 0.0f;
        for (int i2 = 0; i2 < iArr.length; i2++) {
            float f8 = (float) dArr[i2];
            double d3 = dArr2[i2];
            int i3 = iArr[i2];
            if (i3 == 1) {
                f4 = f8;
            } else if (i3 == 2) {
                f6 = f8;
            } else if (i3 == 3) {
                f5 = f8;
            } else if (i3 == 4) {
                f7 = f8;
            }
        }
        float f9 = f4 - ((0.0f * f5) / 2.0f);
        float f10 = f6 - ((0.0f * f7) / 2.0f);
        fArr[0] = (f9 * (1.0f - f2)) + (((f5 * 1.0f) + f9) * f2) + 0.0f;
        fArr[1] = (f10 * (1.0f - f3)) + (((f7 * 1.0f) + f10) * f3) + 0.0f;
    }

    public void setView(float f2, MotionWidget motionWidget, int[] iArr, double[] dArr, double[] dArr2, double[] dArr3) {
        float f3;
        float f4;
        float f5 = this.f1769x;
        float f6 = this.f1770y;
        float f7 = this.width;
        float f8 = this.height;
        if (iArr.length != 0 && this.mTempValue.length <= iArr[iArr.length - 1]) {
            int i2 = iArr[iArr.length - 1] + 1;
            this.mTempValue = new double[i2];
            this.mTempDelta = new double[i2];
        }
        Arrays.fill(this.mTempValue, Double.NaN);
        for (int i3 = 0; i3 < iArr.length; i3++) {
            double[] dArr4 = this.mTempValue;
            int i4 = iArr[i3];
            dArr4[i4] = dArr[i3];
            this.mTempDelta[i4] = dArr2[i3];
        }
        float f9 = Float.NaN;
        int i5 = 0;
        float f10 = 0.0f;
        float f11 = 0.0f;
        float f12 = 0.0f;
        float f13 = 0.0f;
        while (true) {
            double[] dArr5 = this.mTempValue;
            if (i5 >= dArr5.length) {
                break;
            }
            if (Double.isNaN(dArr5[i5]) && (dArr3 == null || dArr3[i5] == 0.0d)) {
                f4 = f9;
            } else {
                double d3 = dArr3 != null ? dArr3[i5] : 0.0d;
                if (!Double.isNaN(this.mTempValue[i5])) {
                    d3 = this.mTempValue[i5] + d3;
                }
                f4 = f9;
                float f14 = (float) d3;
                float f15 = (float) this.mTempDelta[i5];
                if (i5 == 1) {
                    f9 = f4;
                    f10 = f15;
                    f5 = f14;
                } else if (i5 == 2) {
                    f9 = f4;
                    f11 = f15;
                    f6 = f14;
                } else if (i5 == 3) {
                    f9 = f4;
                    f12 = f15;
                    f7 = f14;
                } else if (i5 == 4) {
                    f9 = f4;
                    f13 = f15;
                    f8 = f14;
                } else if (i5 == 5) {
                    f9 = f14;
                }
                i5++;
            }
            f9 = f4;
            i5++;
        }
        float f16 = f9;
        Motion motion = this.mRelativeToController;
        if (motion != null) {
            float[] fArr = new float[2];
            float[] fArr2 = new float[2];
            motion.getCenter(f2, fArr, fArr2);
            float f17 = fArr[0];
            float f18 = fArr[1];
            float f19 = fArr2[0];
            float f20 = fArr2[1];
            double d4 = f5;
            double d5 = f6;
            float fSin = (float) ((f17 + (Math.sin(d5) * d4)) - (f7 / 2.0f));
            f3 = f8;
            float fCos = (float) ((f18 - (Math.cos(d5) * d4)) - (f8 / 2.0f));
            double d6 = f10;
            double d7 = f11;
            float fSin2 = (float) (f19 + (Math.sin(d5) * d6) + (Math.cos(d5) * d4 * d7));
            float fCos2 = (float) ((f20 - (d6 * Math.cos(d5))) + (d4 * Math.sin(d5) * d7));
            if (dArr2.length >= 2) {
                dArr2[0] = fSin2;
                dArr2[1] = fCos2;
            }
            if (!Float.isNaN(f16)) {
                motionWidget.setRotationZ((float) (f16 + Math.toDegrees(Math.atan2(fCos2, fSin2))));
            }
            f5 = fSin;
            f6 = fCos;
        } else {
            f3 = f8;
            if (!Float.isNaN(f16)) {
                motionWidget.setRotationZ((float) (0.0f + f16 + Math.toDegrees(Math.atan2(f11 + (f13 / 2.0f), f10 + (f12 / 2.0f)))));
            }
        }
        float f21 = f5 + 0.5f;
        float f22 = f6 + 0.5f;
        motionWidget.layout((int) f21, (int) f22, (int) (f21 + f7), (int) (f22 + f3));
    }

    public void setupRelative(Motion motion, MotionPaths motionPaths) {
        double d3 = ((this.f1769x + (this.width / 2.0f)) - motionPaths.f1769x) - (motionPaths.width / 2.0f);
        double d4 = ((this.f1770y + (this.height / 2.0f)) - motionPaths.f1770y) - (motionPaths.height / 2.0f);
        this.mRelativeToController = motion;
        this.f1769x = (float) Math.hypot(d4, d3);
        if (Float.isNaN(this.mRelativeAngle)) {
            this.f1770y = (float) (Math.atan2(d4, d3) + 1.5707963267948966d);
        } else {
            this.f1770y = (float) Math.toRadians(this.mRelativeAngle);
        }
    }

    @Override // java.lang.Comparable
    public int compareTo(MotionPaths motionPaths) {
        return Float.compare(this.position, motionPaths.position);
    }

    public MotionPaths(int i2, int i3, MotionKeyPosition motionKeyPosition, MotionPaths motionPaths, MotionPaths motionPaths2) {
        this.mDrawPath = 0;
        this.mPathRotate = Float.NaN;
        this.mProgress = Float.NaN;
        this.mPathMotionArc = -1;
        this.mAnimateRelativeTo = -1;
        this.mRelativeAngle = Float.NaN;
        this.mRelativeToController = null;
        this.customAttributes = new HashMap<>();
        this.mMode = 0;
        this.mTempValue = new double[18];
        this.mTempDelta = new double[18];
        if (motionPaths.mAnimateRelativeTo != -1) {
            initPolar(i2, i3, motionKeyPosition, motionPaths, motionPaths2);
            return;
        }
        int i4 = motionKeyPosition.mPositionType;
        if (i4 == 1) {
            initPath(motionKeyPosition, motionPaths, motionPaths2);
        } else if (i4 != 2) {
            initCartesian(motionKeyPosition, motionPaths, motionPaths2);
        } else {
            initScreen(i2, i3, motionKeyPosition, motionPaths, motionPaths2);
        }
    }

    public void getCenter(double d3, int[] iArr, double[] dArr, float[] fArr, double[] dArr2, float[] fArr2) {
        float f2;
        float f3 = this.f1769x;
        float f4 = this.f1770y;
        float f5 = this.width;
        float f6 = this.height;
        float f7 = 0.0f;
        float f8 = 0.0f;
        float f9 = 0.0f;
        float f10 = 0.0f;
        for (int i2 = 0; i2 < iArr.length; i2++) {
            float f11 = (float) dArr[i2];
            float f12 = (float) dArr2[i2];
            int i3 = iArr[i2];
            if (i3 == 1) {
                f3 = f11;
                f7 = f12;
            } else if (i3 == 2) {
                f4 = f11;
                f9 = f12;
            } else if (i3 == 3) {
                f5 = f11;
                f8 = f12;
            } else if (i3 == 4) {
                f6 = f11;
                f10 = f12;
            }
        }
        float f13 = 2.0f;
        float f14 = (f8 / 2.0f) + f7;
        float fCos = (f10 / 2.0f) + f9;
        Motion motion = this.mRelativeToController;
        if (motion != null) {
            float[] fArr3 = new float[2];
            float[] fArr4 = new float[2];
            motion.getCenter(d3, fArr3, fArr4);
            float f15 = fArr3[0];
            float f16 = fArr3[1];
            float f17 = fArr4[0];
            float f18 = fArr4[1];
            double d4 = f3;
            double d5 = f4;
            f2 = f5;
            float fSin = (float) ((f15 + (Math.sin(d5) * d4)) - (f5 / 2.0f));
            float fCos2 = (float) ((f16 - (d4 * Math.cos(d5))) - (f6 / 2.0f));
            double d6 = f7;
            double d7 = f9;
            float fSin2 = (float) (f17 + (Math.sin(d5) * d6) + (Math.cos(d5) * d7));
            fCos = (float) ((f18 - (d6 * Math.cos(d5))) + (Math.sin(d5) * d7));
            f14 = fSin2;
            f3 = fSin;
            f4 = fCos2;
            f13 = 2.0f;
        } else {
            f2 = f5;
        }
        fArr[0] = f3 + (f2 / f13) + 0.0f;
        fArr[1] = f4 + (f6 / f13) + 0.0f;
        fArr2[0] = f14;
        fArr2[1] = fCos;
    }
}
