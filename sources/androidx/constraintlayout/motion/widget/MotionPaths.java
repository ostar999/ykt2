package androidx.constraintlayout.motion.widget;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.ConstraintSet;
import java.util.Arrays;
import java.util.LinkedHashMap;

/* loaded from: classes.dex */
class MotionPaths implements Comparable<MotionPaths> {
    static final int CARTESIAN = 0;
    public static final boolean DEBUG = false;
    static final int OFF_HEIGHT = 4;
    static final int OFF_PATH_ROTATE = 5;
    static final int OFF_POSITION = 0;
    static final int OFF_WIDTH = 3;
    static final int OFF_X = 1;
    static final int OFF_Y = 2;
    public static final boolean OLD_WAY = false;
    static final int PERPENDICULAR = 1;
    static final int SCREEN = 2;
    public static final String TAG = "MotionPaths";
    static String[] names = {"position", "x", "y", "width", "height", "pathRotate"};
    LinkedHashMap<String, ConstraintAttribute> attributes;
    float height;
    int mAnimateCircleAngleTo;
    int mAnimateRelativeTo;
    Easing mKeyFrameEasing;
    int mMode;
    int mPathMotionArc;
    float mRelativeAngle;
    MotionController mRelativeToController;
    double[] mTempDelta;
    double[] mTempValue;
    float position;
    float time;
    float width;

    /* renamed from: x, reason: collision with root package name */
    float f1788x;

    /* renamed from: y, reason: collision with root package name */
    float f1789y;
    int mDrawPath = 0;
    float mPathRotate = Float.NaN;
    float mProgress = Float.NaN;

    public MotionPaths() {
        int i2 = Key.UNSET;
        this.mPathMotionArc = i2;
        this.mAnimateRelativeTo = i2;
        this.mRelativeAngle = Float.NaN;
        this.mRelativeToController = null;
        this.attributes = new LinkedHashMap<>();
        this.mMode = 0;
        this.mTempValue = new double[18];
        this.mTempDelta = new double[18];
    }

    private boolean diff(float a3, float b3) {
        return (Float.isNaN(a3) || Float.isNaN(b3)) ? Float.isNaN(a3) != Float.isNaN(b3) : Math.abs(a3 - b3) > 1.0E-6f;
    }

    private static final float xRotate(float sin, float cos, float cx, float cy, float x2, float y2) {
        return (((x2 - cx) * cos) - ((y2 - cy) * sin)) + cx;
    }

    private static final float yRotate(float sin, float cos, float cx, float cy, float x2, float y2) {
        return ((x2 - cx) * sin) + ((y2 - cy) * cos) + cy;
    }

    public void applyParameters(ConstraintSet.Constraint c3) {
        this.mKeyFrameEasing = Easing.getInterpolator(c3.motion.mTransitionEasing);
        ConstraintSet.Motion motion = c3.motion;
        this.mPathMotionArc = motion.mPathMotionArc;
        this.mAnimateRelativeTo = motion.mAnimateRelativeTo;
        this.mPathRotate = motion.mPathRotate;
        this.mDrawPath = motion.mDrawPath;
        this.mAnimateCircleAngleTo = motion.mAnimateCircleAngleTo;
        this.mProgress = c3.propertySet.mProgress;
        this.mRelativeAngle = c3.layout.circleAngle;
        for (String str : c3.mCustomConstraints.keySet()) {
            ConstraintAttribute constraintAttribute = c3.mCustomConstraints.get(str);
            if (constraintAttribute != null && constraintAttribute.isContinuous()) {
                this.attributes.put(str, constraintAttribute);
            }
        }
    }

    public void configureRelativeTo(MotionController toOrbit) {
        toOrbit.getPos(this.mProgress);
    }

    public void different(MotionPaths points, boolean[] mask, String[] custom, boolean arcMode) {
        boolean zDiff = diff(this.f1788x, points.f1788x);
        boolean zDiff2 = diff(this.f1789y, points.f1789y);
        mask[0] = mask[0] | diff(this.position, points.position);
        boolean z2 = zDiff | zDiff2 | arcMode;
        mask[1] = mask[1] | z2;
        mask[2] = z2 | mask[2];
        mask[3] = mask[3] | diff(this.width, points.width);
        mask[4] = diff(this.height, points.height) | mask[4];
    }

    public void fillStandard(double[] data, int[] toUse) {
        float[] fArr = {this.position, this.f1788x, this.f1789y, this.width, this.height, this.mPathRotate};
        int i2 = 0;
        for (int i3 : toUse) {
            if (i3 < 6) {
                data[i2] = fArr[r4];
                i2++;
            }
        }
    }

    public void getBounds(int[] toUse, double[] data, float[] point, int offset) {
        float f2 = this.width;
        float f3 = this.height;
        for (int i2 = 0; i2 < toUse.length; i2++) {
            float f4 = (float) data[i2];
            int i3 = toUse[i2];
            if (i3 == 3) {
                f2 = f4;
            } else if (i3 == 4) {
                f3 = f4;
            }
        }
        point[offset] = f2;
        point[offset + 1] = f3;
    }

    public void getCenter(double p2, int[] toUse, double[] data, float[] point, int offset) {
        float fSin = this.f1788x;
        float fCos = this.f1789y;
        float f2 = this.width;
        float f3 = this.height;
        for (int i2 = 0; i2 < toUse.length; i2++) {
            float f4 = (float) data[i2];
            int i3 = toUse[i2];
            if (i3 == 1) {
                fSin = f4;
            } else if (i3 == 2) {
                fCos = f4;
            } else if (i3 == 3) {
                f2 = f4;
            } else if (i3 == 4) {
                f3 = f4;
            }
        }
        MotionController motionController = this.mRelativeToController;
        if (motionController != null) {
            float[] fArr = new float[2];
            motionController.getCenter(p2, fArr, new float[2]);
            float f5 = fArr[0];
            float f6 = fArr[1];
            double d3 = f5;
            double d4 = fSin;
            double d5 = fCos;
            fSin = (float) ((d3 + (Math.sin(d5) * d4)) - (f2 / 2.0f));
            fCos = (float) ((f6 - (d4 * Math.cos(d5))) - (f3 / 2.0f));
        }
        point[offset] = fSin + (f2 / 2.0f) + 0.0f;
        point[offset + 1] = fCos + (f3 / 2.0f) + 0.0f;
    }

    public void getCenterVelocity(double p2, int[] toUse, double[] data, float[] point, int offset) {
        float fSin = this.f1788x;
        float fCos = this.f1789y;
        float f2 = this.width;
        float f3 = this.height;
        for (int i2 = 0; i2 < toUse.length; i2++) {
            float f4 = (float) data[i2];
            int i3 = toUse[i2];
            if (i3 == 1) {
                fSin = f4;
            } else if (i3 == 2) {
                fCos = f4;
            } else if (i3 == 3) {
                f2 = f4;
            } else if (i3 == 4) {
                f3 = f4;
            }
        }
        MotionController motionController = this.mRelativeToController;
        if (motionController != null) {
            float[] fArr = new float[2];
            motionController.getCenter(p2, fArr, new float[2]);
            float f5 = fArr[0];
            float f6 = fArr[1];
            double d3 = f5;
            double d4 = fSin;
            double d5 = fCos;
            fSin = (float) ((d3 + (Math.sin(d5) * d4)) - (f2 / 2.0f));
            fCos = (float) ((f6 - (d4 * Math.cos(d5))) - (f3 / 2.0f));
        }
        point[offset] = fSin + (f2 / 2.0f) + 0.0f;
        point[offset + 1] = fCos + (f3 / 2.0f) + 0.0f;
    }

    public int getCustomData(String name, double[] value, int offset) {
        ConstraintAttribute constraintAttribute = this.attributes.get(name);
        int i2 = 0;
        if (constraintAttribute == null) {
            return 0;
        }
        if (constraintAttribute.numberOfInterpolatedValues() == 1) {
            value[offset] = constraintAttribute.getValueToInterpolate();
            return 1;
        }
        int iNumberOfInterpolatedValues = constraintAttribute.numberOfInterpolatedValues();
        constraintAttribute.getValuesToInterpolate(new float[iNumberOfInterpolatedValues]);
        while (i2 < iNumberOfInterpolatedValues) {
            value[offset] = r2[i2];
            i2++;
            offset++;
        }
        return iNumberOfInterpolatedValues;
    }

    public int getCustomDataCount(String name) {
        ConstraintAttribute constraintAttribute = this.attributes.get(name);
        if (constraintAttribute == null) {
            return 0;
        }
        return constraintAttribute.numberOfInterpolatedValues();
    }

    public void getRect(int[] toUse, double[] data, float[] path, int offset) {
        float f2 = this.f1788x;
        float fCos = this.f1789y;
        float f3 = this.width;
        float f4 = this.height;
        for (int i2 = 0; i2 < toUse.length; i2++) {
            float f5 = (float) data[i2];
            int i3 = toUse[i2];
            if (i3 == 1) {
                f2 = f5;
            } else if (i3 == 2) {
                fCos = f5;
            } else if (i3 == 3) {
                f3 = f5;
            } else if (i3 == 4) {
                f4 = f5;
            }
        }
        MotionController motionController = this.mRelativeToController;
        if (motionController != null) {
            float centerX = motionController.getCenterX();
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
        int i4 = offset + 1;
        path[offset] = f2 + 0.0f;
        int i5 = i4 + 1;
        path[i4] = fCos + 0.0f;
        int i6 = i5 + 1;
        path[i5] = f6 + 0.0f;
        int i7 = i6 + 1;
        path[i6] = fCos + 0.0f;
        int i8 = i7 + 1;
        path[i7] = f6 + 0.0f;
        int i9 = i8 + 1;
        path[i8] = f7 + 0.0f;
        path[i9] = f2 + 0.0f;
        path[i9 + 1] = f7 + 0.0f;
    }

    public boolean hasCustomData(String name) {
        return this.attributes.containsKey(name);
    }

    public void initCartesian(KeyPosition c3, MotionPaths startTimePoint, MotionPaths endTimePoint) {
        float f2 = c3.mFramePosition / 100.0f;
        this.time = f2;
        this.mDrawPath = c3.mDrawPath;
        float f3 = Float.isNaN(c3.mPercentWidth) ? f2 : c3.mPercentWidth;
        float f4 = Float.isNaN(c3.mPercentHeight) ? f2 : c3.mPercentHeight;
        float f5 = endTimePoint.width;
        float f6 = startTimePoint.width;
        float f7 = endTimePoint.height;
        float f8 = startTimePoint.height;
        this.position = this.time;
        float f9 = startTimePoint.f1788x;
        float f10 = startTimePoint.f1789y;
        float f11 = (endTimePoint.f1788x + (f5 / 2.0f)) - ((f6 / 2.0f) + f9);
        float f12 = (endTimePoint.f1789y + (f7 / 2.0f)) - (f10 + (f8 / 2.0f));
        float f13 = ((f5 - f6) * f3) / 2.0f;
        this.f1788x = (int) ((f9 + (f11 * f2)) - f13);
        float f14 = ((f7 - f8) * f4) / 2.0f;
        this.f1789y = (int) ((f10 + (f12 * f2)) - f14);
        this.width = (int) (f6 + r9);
        this.height = (int) (f8 + r12);
        float f15 = Float.isNaN(c3.mPercentX) ? f2 : c3.mPercentX;
        float f16 = Float.isNaN(c3.mAltPercentY) ? 0.0f : c3.mAltPercentY;
        if (!Float.isNaN(c3.mPercentY)) {
            f2 = c3.mPercentY;
        }
        float f17 = Float.isNaN(c3.mAltPercentX) ? 0.0f : c3.mAltPercentX;
        this.mMode = 0;
        this.f1788x = (int) (((startTimePoint.f1788x + (f15 * f11)) + (f17 * f12)) - f13);
        this.f1789y = (int) (((startTimePoint.f1789y + (f11 * f16)) + (f12 * f2)) - f14);
        this.mKeyFrameEasing = Easing.getInterpolator(c3.mTransitionEasing);
        this.mPathMotionArc = c3.mPathMotionArc;
    }

    public void initPath(KeyPosition c3, MotionPaths startTimePoint, MotionPaths endTimePoint) {
        float f2 = c3.mFramePosition / 100.0f;
        this.time = f2;
        this.mDrawPath = c3.mDrawPath;
        float f3 = Float.isNaN(c3.mPercentWidth) ? f2 : c3.mPercentWidth;
        float f4 = Float.isNaN(c3.mPercentHeight) ? f2 : c3.mPercentHeight;
        float f5 = endTimePoint.width - startTimePoint.width;
        float f6 = endTimePoint.height - startTimePoint.height;
        this.position = this.time;
        if (!Float.isNaN(c3.mPercentX)) {
            f2 = c3.mPercentX;
        }
        float f7 = startTimePoint.f1788x;
        float f8 = startTimePoint.width;
        float f9 = startTimePoint.f1789y;
        float f10 = startTimePoint.height;
        float f11 = (endTimePoint.f1788x + (endTimePoint.width / 2.0f)) - ((f8 / 2.0f) + f7);
        float f12 = (endTimePoint.f1789y + (endTimePoint.height / 2.0f)) - ((f10 / 2.0f) + f9);
        float f13 = f11 * f2;
        float f14 = (f5 * f3) / 2.0f;
        this.f1788x = (int) ((f7 + f13) - f14);
        float f15 = f2 * f12;
        float f16 = (f6 * f4) / 2.0f;
        this.f1789y = (int) ((f9 + f15) - f16);
        this.width = (int) (f8 + r7);
        this.height = (int) (f10 + r8);
        float f17 = Float.isNaN(c3.mPercentY) ? 0.0f : c3.mPercentY;
        this.mMode = 1;
        float f18 = (int) ((startTimePoint.f1788x + f13) - f14);
        float f19 = (int) ((startTimePoint.f1789y + f15) - f16);
        this.f1788x = f18 + ((-f12) * f17);
        this.f1789y = f19 + (f11 * f17);
        this.mAnimateRelativeTo = this.mAnimateRelativeTo;
        this.mKeyFrameEasing = Easing.getInterpolator(c3.mTransitionEasing);
        this.mPathMotionArc = c3.mPathMotionArc;
    }

    public void initPolar(int parentWidth, int parentHeight, KeyPosition c3, MotionPaths s2, MotionPaths e2) {
        float fMin;
        float f2;
        float f3 = c3.mFramePosition / 100.0f;
        this.time = f3;
        this.mDrawPath = c3.mDrawPath;
        this.mMode = c3.mPositionType;
        float f4 = Float.isNaN(c3.mPercentWidth) ? f3 : c3.mPercentWidth;
        float f5 = Float.isNaN(c3.mPercentHeight) ? f3 : c3.mPercentHeight;
        float f6 = e2.width;
        float f7 = s2.width;
        float f8 = e2.height;
        float f9 = s2.height;
        this.position = this.time;
        this.width = (int) (f7 + ((f6 - f7) * f4));
        this.height = (int) (f9 + ((f8 - f9) * f5));
        int i2 = c3.mPositionType;
        if (i2 == 1) {
            float f10 = Float.isNaN(c3.mPercentX) ? f3 : c3.mPercentX;
            float f11 = e2.f1788x;
            float f12 = s2.f1788x;
            this.f1788x = (f10 * (f11 - f12)) + f12;
            if (!Float.isNaN(c3.mPercentY)) {
                f3 = c3.mPercentY;
            }
            float f13 = e2.f1789y;
            float f14 = s2.f1789y;
            this.f1789y = (f3 * (f13 - f14)) + f14;
        } else if (i2 != 2) {
            float f15 = Float.isNaN(c3.mPercentX) ? f3 : c3.mPercentX;
            float f16 = e2.f1788x;
            float f17 = s2.f1788x;
            this.f1788x = (f15 * (f16 - f17)) + f17;
            if (!Float.isNaN(c3.mPercentY)) {
                f3 = c3.mPercentY;
            }
            float f18 = e2.f1789y;
            float f19 = s2.f1789y;
            this.f1789y = (f3 * (f18 - f19)) + f19;
        } else {
            if (Float.isNaN(c3.mPercentX)) {
                float f20 = e2.f1788x;
                float f21 = s2.f1788x;
                fMin = ((f20 - f21) * f3) + f21;
            } else {
                fMin = Math.min(f5, f4) * c3.mPercentX;
            }
            this.f1788x = fMin;
            if (Float.isNaN(c3.mPercentY)) {
                float f22 = e2.f1789y;
                float f23 = s2.f1789y;
                f2 = (f3 * (f22 - f23)) + f23;
            } else {
                f2 = c3.mPercentY;
            }
            this.f1789y = f2;
        }
        this.mAnimateRelativeTo = s2.mAnimateRelativeTo;
        this.mKeyFrameEasing = Easing.getInterpolator(c3.mTransitionEasing);
        this.mPathMotionArc = c3.mPathMotionArc;
    }

    public void initScreen(int parentWidth, int parentHeight, KeyPosition c3, MotionPaths startTimePoint, MotionPaths endTimePoint) {
        float f2 = c3.mFramePosition / 100.0f;
        this.time = f2;
        this.mDrawPath = c3.mDrawPath;
        float f3 = Float.isNaN(c3.mPercentWidth) ? f2 : c3.mPercentWidth;
        float f4 = Float.isNaN(c3.mPercentHeight) ? f2 : c3.mPercentHeight;
        float f5 = endTimePoint.width;
        float f6 = startTimePoint.width;
        float f7 = endTimePoint.height;
        float f8 = startTimePoint.height;
        this.position = this.time;
        float f9 = startTimePoint.f1788x;
        float f10 = startTimePoint.f1789y;
        float f11 = endTimePoint.f1788x + (f5 / 2.0f);
        float f12 = endTimePoint.f1789y + (f7 / 2.0f);
        float f13 = (f5 - f6) * f3;
        this.f1788x = (int) ((f9 + ((f11 - ((f6 / 2.0f) + f9)) * f2)) - (f13 / 2.0f));
        float f14 = (f7 - f8) * f4;
        this.f1789y = (int) ((f10 + ((f12 - (f10 + (f8 / 2.0f))) * f2)) - (f14 / 2.0f));
        this.width = (int) (f6 + f13);
        this.height = (int) (f8 + f14);
        this.mMode = 2;
        if (!Float.isNaN(c3.mPercentX)) {
            this.f1788x = (int) (c3.mPercentX * ((int) (parentWidth - this.width)));
        }
        if (!Float.isNaN(c3.mPercentY)) {
            this.f1789y = (int) (c3.mPercentY * ((int) (parentHeight - this.height)));
        }
        this.mAnimateRelativeTo = this.mAnimateRelativeTo;
        this.mKeyFrameEasing = Easing.getInterpolator(c3.mTransitionEasing);
        this.mPathMotionArc = c3.mPathMotionArc;
    }

    public void setBounds(float x2, float y2, float w2, float h2) {
        this.f1788x = x2;
        this.f1789y = y2;
        this.width = w2;
        this.height = h2;
    }

    public void setDpDt(float locationX, float locationY, float[] mAnchorDpDt, int[] toUse, double[] deltaData, double[] data) {
        float f2 = 0.0f;
        float f3 = 0.0f;
        float f4 = 0.0f;
        float f5 = 0.0f;
        for (int i2 = 0; i2 < toUse.length; i2++) {
            float f6 = (float) deltaData[i2];
            double d3 = data[i2];
            int i3 = toUse[i2];
            if (i3 == 1) {
                f2 = f6;
            } else if (i3 == 2) {
                f4 = f6;
            } else if (i3 == 3) {
                f3 = f6;
            } else if (i3 == 4) {
                f5 = f6;
            }
        }
        float f7 = f2 - ((0.0f * f3) / 2.0f);
        float f8 = f4 - ((0.0f * f5) / 2.0f);
        mAnchorDpDt[0] = (f7 * (1.0f - locationX)) + (((f3 * 1.0f) + f7) * locationX) + 0.0f;
        mAnchorDpDt[1] = (f8 * (1.0f - locationY)) + (((f5 * 1.0f) + f8) * locationY) + 0.0f;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setView(float position, View view, int[] toUse, double[] data, double[] slope, double[] cycle, boolean mForceMeasure) {
        float f2;
        boolean z2;
        boolean z3;
        float f3;
        float f4 = this.f1788x;
        float f5 = this.f1789y;
        float f6 = this.width;
        float f7 = this.height;
        if (toUse.length != 0 && this.mTempValue.length <= toUse[toUse.length - 1]) {
            int i2 = toUse[toUse.length - 1] + 1;
            this.mTempValue = new double[i2];
            this.mTempDelta = new double[i2];
        }
        Arrays.fill(this.mTempValue, Double.NaN);
        for (int i3 = 0; i3 < toUse.length; i3++) {
            double[] dArr = this.mTempValue;
            int i4 = toUse[i3];
            dArr[i4] = data[i3];
            this.mTempDelta[i4] = slope[i3];
        }
        float f8 = Float.NaN;
        int i5 = 0;
        float f9 = 0.0f;
        float f10 = 0.0f;
        float f11 = 0.0f;
        float f12 = 0.0f;
        while (true) {
            double[] dArr2 = this.mTempValue;
            if (i5 >= dArr2.length) {
                break;
            }
            if (Double.isNaN(dArr2[i5]) && (cycle == null || cycle[i5] == 0.0d)) {
                f3 = f8;
            } else {
                double d3 = cycle != null ? cycle[i5] : 0.0d;
                if (!Double.isNaN(this.mTempValue[i5])) {
                    d3 = this.mTempValue[i5] + d3;
                }
                f3 = f8;
                float f13 = (float) d3;
                float f14 = (float) this.mTempDelta[i5];
                if (i5 == 1) {
                    f8 = f3;
                    f9 = f14;
                    f4 = f13;
                } else if (i5 == 2) {
                    f8 = f3;
                    f10 = f14;
                    f5 = f13;
                } else if (i5 == 3) {
                    f8 = f3;
                    f11 = f14;
                    f6 = f13;
                } else if (i5 == 4) {
                    f8 = f3;
                    f12 = f14;
                    f7 = f13;
                } else if (i5 == 5) {
                    f8 = f13;
                }
                i5++;
            }
            f8 = f3;
            i5++;
        }
        float f15 = f8;
        MotionController motionController = this.mRelativeToController;
        if (motionController != null) {
            float[] fArr = new float[2];
            float[] fArr2 = new float[2];
            motionController.getCenter(position, fArr, fArr2);
            float f16 = fArr[0];
            float f17 = fArr[1];
            float f18 = fArr2[0];
            float f19 = fArr2[1];
            double d4 = f4;
            double d5 = f5;
            float fSin = (float) ((f16 + (Math.sin(d5) * d4)) - (f6 / 2.0f));
            f2 = f7;
            float fCos = (float) ((f17 - (Math.cos(d5) * d4)) - (f7 / 2.0f));
            double d6 = f9;
            double d7 = f10;
            float fSin2 = (float) (f18 + (Math.sin(d5) * d6) + (Math.cos(d5) * d4 * d7));
            float fCos2 = (float) ((f19 - (d6 * Math.cos(d5))) + (d4 * Math.sin(d5) * d7));
            if (slope.length >= 2) {
                z2 = false;
                slope[0] = fSin2;
                z3 = true;
                slope[1] = fCos2;
            } else {
                z2 = false;
                z3 = true;
            }
            if (!Float.isNaN(f15)) {
                view.setRotation((float) (f15 + Math.toDegrees(Math.atan2(fCos2, fSin2))));
            }
            f4 = fSin;
            f5 = fCos;
        } else {
            f2 = f7;
            z2 = false;
            z3 = true;
            if (!Float.isNaN(f15)) {
                view.setRotation((float) (0.0f + f15 + Math.toDegrees(Math.atan2(f10 + (f12 / 2.0f), f9 + (f11 / 2.0f)))));
            }
        }
        if (view instanceof FloatLayout) {
            ((FloatLayout) view).layout(f4, f5, f6 + f4, f5 + f2);
            return;
        }
        float f20 = f4 + 0.5f;
        int i6 = (int) f20;
        float f21 = f5 + 0.5f;
        int i7 = (int) f21;
        int i8 = (int) (f20 + f6);
        int i9 = (int) (f21 + f2);
        int i10 = i8 - i6;
        int i11 = i9 - i7;
        if (i10 != view.getMeasuredWidth() || i11 != view.getMeasuredHeight()) {
            z2 = z3;
        }
        if (z2 || mForceMeasure) {
            view.measure(View.MeasureSpec.makeMeasureSpec(i10, 1073741824), View.MeasureSpec.makeMeasureSpec(i11, 1073741824));
        }
        view.layout(i6, i7, i8, i9);
    }

    public void setupRelative(MotionController mc, MotionPaths relative) {
        double d3 = ((this.f1788x + (this.width / 2.0f)) - relative.f1788x) - (relative.width / 2.0f);
        double d4 = ((this.f1789y + (this.height / 2.0f)) - relative.f1789y) - (relative.height / 2.0f);
        this.mRelativeToController = mc;
        this.f1788x = (float) Math.hypot(d4, d3);
        if (Float.isNaN(this.mRelativeAngle)) {
            this.f1789y = (float) (Math.atan2(d4, d3) + 1.5707963267948966d);
        } else {
            this.f1789y = (float) Math.toRadians(this.mRelativeAngle);
        }
    }

    @Override // java.lang.Comparable
    public int compareTo(@NonNull MotionPaths o2) {
        return Float.compare(this.position, o2.position);
    }

    public MotionPaths(int parentWidth, int parentHeight, KeyPosition c3, MotionPaths startTimePoint, MotionPaths endTimePoint) {
        int i2 = Key.UNSET;
        this.mPathMotionArc = i2;
        this.mAnimateRelativeTo = i2;
        this.mRelativeAngle = Float.NaN;
        this.mRelativeToController = null;
        this.attributes = new LinkedHashMap<>();
        this.mMode = 0;
        this.mTempValue = new double[18];
        this.mTempDelta = new double[18];
        if (startTimePoint.mAnimateRelativeTo != Key.UNSET) {
            initPolar(parentWidth, parentHeight, c3, startTimePoint, endTimePoint);
            return;
        }
        int i3 = c3.mPositionType;
        if (i3 == 1) {
            initPath(c3, startTimePoint, endTimePoint);
        } else if (i3 != 2) {
            initCartesian(c3, startTimePoint, endTimePoint);
        } else {
            initScreen(parentWidth, parentHeight, c3, startTimePoint, endTimePoint);
        }
    }

    public void getCenter(double p2, int[] toUse, double[] data, float[] point, double[] vdata, float[] velocity) {
        float f2;
        float f3 = this.f1788x;
        float f4 = this.f1789y;
        float f5 = this.width;
        float f6 = this.height;
        float f7 = 0.0f;
        float f8 = 0.0f;
        float f9 = 0.0f;
        float f10 = 0.0f;
        for (int i2 = 0; i2 < toUse.length; i2++) {
            float f11 = (float) data[i2];
            float f12 = (float) vdata[i2];
            int i3 = toUse[i2];
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
        MotionController motionController = this.mRelativeToController;
        if (motionController != null) {
            float[] fArr = new float[2];
            float[] fArr2 = new float[2];
            motionController.getCenter(p2, fArr, fArr2);
            float f15 = fArr[0];
            float f16 = fArr[1];
            float f17 = fArr2[0];
            float f18 = fArr2[1];
            double d3 = f3;
            double d4 = f4;
            f2 = f5;
            float fSin = (float) ((f15 + (Math.sin(d4) * d3)) - (f5 / 2.0f));
            float fCos2 = (float) ((f16 - (d3 * Math.cos(d4))) - (f6 / 2.0f));
            double d5 = f7;
            double d6 = f9;
            float fSin2 = (float) (f17 + (Math.sin(d4) * d5) + (Math.cos(d4) * d6));
            fCos = (float) ((f18 - (d5 * Math.cos(d4))) + (Math.sin(d4) * d6));
            f14 = fSin2;
            f3 = fSin;
            f4 = fCos2;
            f13 = 2.0f;
        } else {
            f2 = f5;
        }
        point[0] = f3 + (f2 / f13) + 0.0f;
        point[1] = f4 + (f6 / f13) + 0.0f;
        velocity[0] = f14;
        velocity[1] = fCos;
    }
}
