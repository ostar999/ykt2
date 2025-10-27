package androidx.constraintlayout.core.motion;

import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.core.motion.utils.Rect;
import androidx.constraintlayout.core.motion.utils.SplineSet;
import androidx.constraintlayout.core.motion.utils.Utils;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;

/* loaded from: classes.dex */
class MotionConstrainedPoint implements Comparable<MotionConstrainedPoint> {
    static final int CARTESIAN = 2;
    public static final boolean DEBUG = false;
    static final int PERPENDICULAR = 1;
    public static final String TAG = "MotionPaths";
    static String[] names = {"position", "x", "y", "width", "height", "pathRotate"};
    private float height;
    private Easing mKeyFrameEasing;
    private float position;
    int visibility;
    private float width;

    /* renamed from: x, reason: collision with root package name */
    private float f1767x;

    /* renamed from: y, reason: collision with root package name */
    private float f1768y;
    private float alpha = 1.0f;
    int mVisibilityMode = 0;
    private boolean applyElevation = false;
    private float elevation = 0.0f;
    private float rotation = 0.0f;
    private float rotationX = 0.0f;
    public float rotationY = 0.0f;
    private float scaleX = 1.0f;
    private float scaleY = 1.0f;
    private float mPivotX = Float.NaN;
    private float mPivotY = Float.NaN;
    private float translationX = 0.0f;
    private float translationY = 0.0f;
    private float translationZ = 0.0f;
    private int mDrawPath = 0;
    private float mPathRotate = Float.NaN;
    private float mProgress = Float.NaN;
    private int mAnimateRelativeTo = -1;
    LinkedHashMap<String, CustomVariable> mCustomVariable = new LinkedHashMap<>();
    int mMode = 0;
    double[] mTempValue = new double[18];
    double[] mTempDelta = new double[18];

    private boolean diff(float f2, float f3) {
        return (Float.isNaN(f2) || Float.isNaN(f3)) ? Float.isNaN(f2) != Float.isNaN(f3) : Math.abs(f2 - f3) > 1.0E-6f;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public void addValues(HashMap<String, SplineSet> map, int i2) {
        for (String str : map.keySet()) {
            SplineSet splineSet = map.get(str);
            str.hashCode();
            char c3 = 65535;
            switch (str.hashCode()) {
                case -1249320806:
                    if (str.equals("rotationX")) {
                        c3 = 0;
                        break;
                    }
                    break;
                case -1249320805:
                    if (str.equals("rotationY")) {
                        c3 = 1;
                        break;
                    }
                    break;
                case -1249320804:
                    if (str.equals("rotationZ")) {
                        c3 = 2;
                        break;
                    }
                    break;
                case -1225497657:
                    if (str.equals("translationX")) {
                        c3 = 3;
                        break;
                    }
                    break;
                case -1225497656:
                    if (str.equals("translationY")) {
                        c3 = 4;
                        break;
                    }
                    break;
                case -1225497655:
                    if (str.equals("translationZ")) {
                        c3 = 5;
                        break;
                    }
                    break;
                case -1001078227:
                    if (str.equals("progress")) {
                        c3 = 6;
                        break;
                    }
                    break;
                case -987906986:
                    if (str.equals("pivotX")) {
                        c3 = 7;
                        break;
                    }
                    break;
                case -987906985:
                    if (str.equals("pivotY")) {
                        c3 = '\b';
                        break;
                    }
                    break;
                case -908189618:
                    if (str.equals("scaleX")) {
                        c3 = '\t';
                        break;
                    }
                    break;
                case -908189617:
                    if (str.equals("scaleY")) {
                        c3 = '\n';
                        break;
                    }
                    break;
                case 92909918:
                    if (str.equals("alpha")) {
                        c3 = 11;
                        break;
                    }
                    break;
                case 803192288:
                    if (str.equals("pathRotate")) {
                        c3 = '\f';
                        break;
                    }
                    break;
            }
            switch (c3) {
                case 0:
                    splineSet.setPoint(i2, Float.isNaN(this.rotationX) ? 0.0f : this.rotationX);
                    break;
                case 1:
                    splineSet.setPoint(i2, Float.isNaN(this.rotationY) ? 0.0f : this.rotationY);
                    break;
                case 2:
                    splineSet.setPoint(i2, Float.isNaN(this.rotation) ? 0.0f : this.rotation);
                    break;
                case 3:
                    splineSet.setPoint(i2, Float.isNaN(this.translationX) ? 0.0f : this.translationX);
                    break;
                case 4:
                    splineSet.setPoint(i2, Float.isNaN(this.translationY) ? 0.0f : this.translationY);
                    break;
                case 5:
                    splineSet.setPoint(i2, Float.isNaN(this.translationZ) ? 0.0f : this.translationZ);
                    break;
                case 6:
                    splineSet.setPoint(i2, Float.isNaN(this.mProgress) ? 0.0f : this.mProgress);
                    break;
                case 7:
                    splineSet.setPoint(i2, Float.isNaN(this.mPivotX) ? 0.0f : this.mPivotX);
                    break;
                case '\b':
                    splineSet.setPoint(i2, Float.isNaN(this.mPivotY) ? 0.0f : this.mPivotY);
                    break;
                case '\t':
                    splineSet.setPoint(i2, Float.isNaN(this.scaleX) ? 1.0f : this.scaleX);
                    break;
                case '\n':
                    splineSet.setPoint(i2, Float.isNaN(this.scaleY) ? 1.0f : this.scaleY);
                    break;
                case 11:
                    splineSet.setPoint(i2, Float.isNaN(this.alpha) ? 1.0f : this.alpha);
                    break;
                case '\f':
                    splineSet.setPoint(i2, Float.isNaN(this.mPathRotate) ? 0.0f : this.mPathRotate);
                    break;
                default:
                    if (str.startsWith("CUSTOM")) {
                        String str2 = str.split(",")[1];
                        if (this.mCustomVariable.containsKey(str2)) {
                            CustomVariable customVariable = this.mCustomVariable.get(str2);
                            if (splineSet instanceof SplineSet.CustomSpline) {
                                ((SplineSet.CustomSpline) splineSet).setPoint(i2, customVariable);
                                break;
                            } else {
                                Utils.loge("MotionPaths", str + " ViewSpline not a CustomSet frame = " + i2 + ", value" + customVariable.getValueToInterpolate() + splineSet);
                                break;
                            }
                        } else {
                            break;
                        }
                    } else {
                        Utils.loge("MotionPaths", "UNKNOWN spline " + str);
                        break;
                    }
            }
        }
    }

    public void applyParameters(MotionWidget motionWidget) {
        this.visibility = motionWidget.getVisibility();
        this.alpha = motionWidget.getVisibility() != 4 ? 0.0f : motionWidget.getAlpha();
        this.applyElevation = false;
        this.rotation = motionWidget.getRotationZ();
        this.rotationX = motionWidget.getRotationX();
        this.rotationY = motionWidget.getRotationY();
        this.scaleX = motionWidget.getScaleX();
        this.scaleY = motionWidget.getScaleY();
        this.mPivotX = motionWidget.getPivotX();
        this.mPivotY = motionWidget.getPivotY();
        this.translationX = motionWidget.getTranslationX();
        this.translationY = motionWidget.getTranslationY();
        this.translationZ = motionWidget.getTranslationZ();
        for (String str : motionWidget.getCustomAttributeNames()) {
            CustomVariable customAttribute = motionWidget.getCustomAttribute(str);
            if (customAttribute != null && customAttribute.isContinuous()) {
                this.mCustomVariable.put(str, customAttribute);
            }
        }
    }

    public void different(MotionConstrainedPoint motionConstrainedPoint, HashSet<String> hashSet) {
        if (diff(this.alpha, motionConstrainedPoint.alpha)) {
            hashSet.add("alpha");
        }
        if (diff(this.elevation, motionConstrainedPoint.elevation)) {
            hashSet.add("translationZ");
        }
        int i2 = this.visibility;
        int i3 = motionConstrainedPoint.visibility;
        if (i2 != i3 && this.mVisibilityMode == 0 && (i2 == 4 || i3 == 4)) {
            hashSet.add("alpha");
        }
        if (diff(this.rotation, motionConstrainedPoint.rotation)) {
            hashSet.add("rotationZ");
        }
        if (!Float.isNaN(this.mPathRotate) || !Float.isNaN(motionConstrainedPoint.mPathRotate)) {
            hashSet.add("pathRotate");
        }
        if (!Float.isNaN(this.mProgress) || !Float.isNaN(motionConstrainedPoint.mProgress)) {
            hashSet.add("progress");
        }
        if (diff(this.rotationX, motionConstrainedPoint.rotationX)) {
            hashSet.add("rotationX");
        }
        if (diff(this.rotationY, motionConstrainedPoint.rotationY)) {
            hashSet.add("rotationY");
        }
        if (diff(this.mPivotX, motionConstrainedPoint.mPivotX)) {
            hashSet.add("pivotX");
        }
        if (diff(this.mPivotY, motionConstrainedPoint.mPivotY)) {
            hashSet.add("pivotY");
        }
        if (diff(this.scaleX, motionConstrainedPoint.scaleX)) {
            hashSet.add("scaleX");
        }
        if (diff(this.scaleY, motionConstrainedPoint.scaleY)) {
            hashSet.add("scaleY");
        }
        if (diff(this.translationX, motionConstrainedPoint.translationX)) {
            hashSet.add("translationX");
        }
        if (diff(this.translationY, motionConstrainedPoint.translationY)) {
            hashSet.add("translationY");
        }
        if (diff(this.translationZ, motionConstrainedPoint.translationZ)) {
            hashSet.add("translationZ");
        }
        if (diff(this.elevation, motionConstrainedPoint.elevation)) {
            hashSet.add("elevation");
        }
    }

    public void fillStandard(double[] dArr, int[] iArr) {
        float[] fArr = {this.position, this.f1767x, this.f1768y, this.width, this.height, this.alpha, this.elevation, this.rotation, this.rotationX, this.rotationY, this.scaleX, this.scaleY, this.mPivotX, this.mPivotY, this.translationX, this.translationY, this.translationZ, this.mPathRotate};
        int i2 = 0;
        for (int i3 : iArr) {
            if (i3 < 18) {
                dArr[i2] = fArr[r4];
                i2++;
            }
        }
    }

    public int getCustomData(String str, double[] dArr, int i2) {
        CustomVariable customVariable = this.mCustomVariable.get(str);
        if (customVariable.numberOfInterpolatedValues() == 1) {
            dArr[i2] = customVariable.getValueToInterpolate();
            return 1;
        }
        int iNumberOfInterpolatedValues = customVariable.numberOfInterpolatedValues();
        customVariable.getValuesToInterpolate(new float[iNumberOfInterpolatedValues]);
        int i3 = 0;
        while (i3 < iNumberOfInterpolatedValues) {
            dArr[i2] = r1[i3];
            i3++;
            i2++;
        }
        return iNumberOfInterpolatedValues;
    }

    public int getCustomDataCount(String str) {
        return this.mCustomVariable.get(str).numberOfInterpolatedValues();
    }

    public boolean hasCustomData(String str) {
        return this.mCustomVariable.containsKey(str);
    }

    public void setBounds(float f2, float f3, float f4, float f5) {
        this.f1767x = f2;
        this.f1768y = f3;
        this.width = f4;
        this.height = f5;
    }

    public void setState(MotionWidget motionWidget) {
        setBounds(motionWidget.getX(), motionWidget.getY(), motionWidget.getWidth(), motionWidget.getHeight());
        applyParameters(motionWidget);
    }

    @Override // java.lang.Comparable
    public int compareTo(MotionConstrainedPoint motionConstrainedPoint) {
        return Float.compare(this.position, motionConstrainedPoint.position);
    }

    public void setState(Rect rect, MotionWidget motionWidget, int i2, float f2) {
        setBounds(rect.left, rect.f1774top, rect.width(), rect.height());
        applyParameters(motionWidget);
        this.mPivotX = Float.NaN;
        this.mPivotY = Float.NaN;
        if (i2 == 1) {
            this.rotation = f2 - 90.0f;
        } else {
            if (i2 != 2) {
                return;
            }
            this.rotation = f2 + 90.0f;
        }
    }

    public void different(MotionConstrainedPoint motionConstrainedPoint, boolean[] zArr, String[] strArr) {
        zArr[0] = zArr[0] | diff(this.position, motionConstrainedPoint.position);
        zArr[1] = zArr[1] | diff(this.f1767x, motionConstrainedPoint.f1767x);
        zArr[2] = zArr[2] | diff(this.f1768y, motionConstrainedPoint.f1768y);
        zArr[3] = zArr[3] | diff(this.width, motionConstrainedPoint.width);
        zArr[4] = diff(this.height, motionConstrainedPoint.height) | zArr[4];
    }
}
