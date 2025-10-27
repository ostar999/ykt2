package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import androidx.constraintlayout.core.motion.utils.CurveFit;
import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.core.motion.utils.KeyCache;
import androidx.constraintlayout.core.motion.utils.VelocityMatrix;
import androidx.constraintlayout.motion.utils.CustomSupport;
import androidx.constraintlayout.motion.utils.ViewOscillator;
import androidx.constraintlayout.motion.utils.ViewSpline;
import androidx.constraintlayout.motion.utils.ViewState;
import androidx.constraintlayout.motion.utils.ViewTimeCycle;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/* loaded from: classes.dex */
public class MotionController {
    static final int BOUNCE = 4;
    private static final boolean DEBUG = false;
    public static final int DRAW_PATH_AS_CONFIGURED = 4;
    public static final int DRAW_PATH_BASIC = 1;
    public static final int DRAW_PATH_CARTESIAN = 3;
    public static final int DRAW_PATH_NONE = 0;
    public static final int DRAW_PATH_RECTANGLE = 5;
    public static final int DRAW_PATH_RELATIVE = 2;
    public static final int DRAW_PATH_SCREEN = 6;
    static final int EASE_IN = 1;
    static final int EASE_IN_OUT = 0;
    static final int EASE_OUT = 2;
    private static final boolean FAVOR_FIXED_SIZE_VIEWS = false;
    public static final int HORIZONTAL_PATH_X = 2;
    public static final int HORIZONTAL_PATH_Y = 3;
    private static final int INTERPOLATOR_REFERENCE_ID = -2;
    private static final int INTERPOLATOR_UNDEFINED = -3;
    static final int LINEAR = 3;
    static final int OVERSHOOT = 5;
    public static final int PATH_PERCENT = 0;
    public static final int PATH_PERPENDICULAR = 1;
    public static final int ROTATION_LEFT = 2;
    public static final int ROTATION_RIGHT = 1;
    private static final int SPLINE_STRING = -1;
    private static final String TAG = "MotionController";
    public static final int VERTICAL_PATH_X = 4;
    public static final int VERTICAL_PATH_Y = 5;
    String[] attributeTable;
    private CurveFit mArcSpline;
    private int[] mAttributeInterpolatorCount;
    private String[] mAttributeNames;
    private HashMap<String, ViewSpline> mAttributesMap;
    String mConstraintTag;
    float mCurrentCenterX;
    float mCurrentCenterY;
    private HashMap<String, ViewOscillator> mCycleMap;
    int mId;
    private double[] mInterpolateData;
    private int[] mInterpolateVariables;
    private double[] mInterpolateVelocity;
    private KeyTrigger[] mKeyTriggers;
    private boolean mNoMovement;
    private int mPathMotionArc;
    private Interpolator mQuantizeMotionInterpolator;
    private float mQuantizeMotionPhase;
    private int mQuantizeMotionSteps;
    private CurveFit[] mSpline;
    private HashMap<String, ViewTimeCycle> mTimeCycleAttributesMap;
    private int mTransformPivotTarget;
    private View mTransformPivotView;
    View mView;
    Rect mTempRect = new Rect();
    boolean mForceMeasure = false;
    private int mCurveFitType = -1;
    private MotionPaths mStartMotionPath = new MotionPaths();
    private MotionPaths mEndMotionPath = new MotionPaths();
    private MotionConstrainedPoint mStartPoint = new MotionConstrainedPoint();
    private MotionConstrainedPoint mEndPoint = new MotionConstrainedPoint();
    float mMotionStagger = Float.NaN;
    float mStaggerOffset = 0.0f;
    float mStaggerScale = 1.0f;
    private int MAX_DIMENSION = 4;
    private float[] mValuesBuff = new float[4];
    private ArrayList<MotionPaths> mMotionPaths = new ArrayList<>();
    private float[] mVelocity = new float[1];
    private ArrayList<Key> mKeyList = new ArrayList<>();

    public MotionController(View view) {
        int i2 = Key.UNSET;
        this.mPathMotionArc = i2;
        this.mTransformPivotTarget = i2;
        this.mTransformPivotView = null;
        this.mQuantizeMotionSteps = i2;
        this.mQuantizeMotionPhase = Float.NaN;
        this.mQuantizeMotionInterpolator = null;
        this.mNoMovement = false;
        setView(view);
    }

    private float getAdjustedPosition(float position, float[] velocity) {
        float f2 = 0.0f;
        if (velocity != null) {
            velocity[0] = 1.0f;
        } else {
            float f3 = this.mStaggerScale;
            if (f3 != 1.0d) {
                float f4 = this.mStaggerOffset;
                if (position < f4) {
                    position = 0.0f;
                }
                if (position > f4 && position < 1.0d) {
                    position = Math.min((position - f4) * f3, 1.0f);
                }
            }
        }
        Easing easing = this.mStartMotionPath.mKeyFrameEasing;
        Iterator<MotionPaths> it = this.mMotionPaths.iterator();
        float f5 = Float.NaN;
        while (it.hasNext()) {
            MotionPaths next = it.next();
            Easing easing2 = next.mKeyFrameEasing;
            if (easing2 != null) {
                float f6 = next.time;
                if (f6 < position) {
                    easing = easing2;
                    f2 = f6;
                } else if (Float.isNaN(f5)) {
                    f5 = next.time;
                }
            }
        }
        if (easing != null) {
            float f7 = (Float.isNaN(f5) ? 1.0f : f5) - f2;
            double d3 = (position - f2) / f7;
            position = (((float) easing.get(d3)) * f7) + f2;
            if (velocity != null) {
                velocity[0] = (float) easing.getDiff(d3);
            }
        }
        return position;
    }

    private static Interpolator getInterpolator(Context context, int type, String interpolatorString, int id) {
        if (type == -2) {
            return AnimationUtils.loadInterpolator(context, id);
        }
        if (type == -1) {
            final Easing interpolator = Easing.getInterpolator(interpolatorString);
            return new Interpolator() { // from class: androidx.constraintlayout.motion.widget.MotionController.1
                @Override // android.animation.TimeInterpolator
                public float getInterpolation(float v2) {
                    return (float) interpolator.get(v2);
                }
            };
        }
        if (type == 0) {
            return new AccelerateDecelerateInterpolator();
        }
        if (type == 1) {
            return new AccelerateInterpolator();
        }
        if (type == 2) {
            return new DecelerateInterpolator();
        }
        if (type == 4) {
            return new BounceInterpolator();
        }
        if (type != 5) {
            return null;
        }
        return new OvershootInterpolator();
    }

    private float getPreCycleDistance() {
        char c3;
        float fHypot;
        float[] fArr = new float[2];
        float f2 = 1.0f / 99;
        double d3 = 0.0d;
        double d4 = 0.0d;
        float f3 = 0.0f;
        int i2 = 0;
        while (i2 < 100) {
            float f4 = i2 * f2;
            double d5 = f4;
            Easing easing = this.mStartMotionPath.mKeyFrameEasing;
            Iterator<MotionPaths> it = this.mMotionPaths.iterator();
            float f5 = Float.NaN;
            float f6 = 0.0f;
            while (it.hasNext()) {
                MotionPaths next = it.next();
                Easing easing2 = next.mKeyFrameEasing;
                if (easing2 != null) {
                    float f7 = next.time;
                    if (f7 < f4) {
                        easing = easing2;
                        f6 = f7;
                    } else if (Float.isNaN(f5)) {
                        f5 = next.time;
                    }
                }
            }
            if (easing != null) {
                if (Float.isNaN(f5)) {
                    f5 = 1.0f;
                }
                d5 = (((float) easing.get((f4 - f6) / r17)) * (f5 - f6)) + f6;
            }
            this.mSpline[0].getPos(d5, this.mInterpolateData);
            float f8 = f3;
            int i3 = i2;
            this.mStartMotionPath.getCenter(d5, this.mInterpolateVariables, this.mInterpolateData, fArr, 0);
            if (i3 > 0) {
                c3 = 0;
                fHypot = (float) (f8 + Math.hypot(d4 - fArr[1], d3 - fArr[0]));
            } else {
                c3 = 0;
                fHypot = f8;
            }
            d3 = fArr[c3];
            i2 = i3 + 1;
            f3 = fHypot;
            d4 = fArr[1];
        }
        return f3;
    }

    private void insertKey(MotionPaths point) {
        if (Collections.binarySearch(this.mMotionPaths, point) == 0) {
            Log.e(TAG, " KeyPath position \"" + point.position + "\" outside of range");
        }
        this.mMotionPaths.add((-r0) - 1, point);
    }

    private void readView(MotionPaths motionPaths) {
        motionPaths.setBounds((int) this.mView.getX(), (int) this.mView.getY(), this.mView.getWidth(), this.mView.getHeight());
    }

    public void addKey(Key key) {
        this.mKeyList.add(key);
    }

    public void addKeys(ArrayList<Key> list) {
        this.mKeyList.addAll(list);
    }

    public void buildBounds(float[] bounds, int pointCount) {
        float f2 = 1.0f / (pointCount - 1);
        HashMap<String, ViewSpline> map = this.mAttributesMap;
        if (map != null) {
            map.get("translationX");
        }
        HashMap<String, ViewSpline> map2 = this.mAttributesMap;
        if (map2 != null) {
            map2.get("translationY");
        }
        HashMap<String, ViewOscillator> map3 = this.mCycleMap;
        if (map3 != null) {
            map3.get("translationX");
        }
        HashMap<String, ViewOscillator> map4 = this.mCycleMap;
        if (map4 != null) {
            map4.get("translationY");
        }
        for (int i2 = 0; i2 < pointCount; i2++) {
            float fMin = i2 * f2;
            float f3 = this.mStaggerScale;
            float f4 = 0.0f;
            if (f3 != 1.0f) {
                float f5 = this.mStaggerOffset;
                if (fMin < f5) {
                    fMin = 0.0f;
                }
                if (fMin > f5 && fMin < 1.0d) {
                    fMin = Math.min((fMin - f5) * f3, 1.0f);
                }
            }
            double d3 = fMin;
            Easing easing = this.mStartMotionPath.mKeyFrameEasing;
            Iterator<MotionPaths> it = this.mMotionPaths.iterator();
            float f6 = Float.NaN;
            while (it.hasNext()) {
                MotionPaths next = it.next();
                Easing easing2 = next.mKeyFrameEasing;
                if (easing2 != null) {
                    float f7 = next.time;
                    if (f7 < fMin) {
                        easing = easing2;
                        f4 = f7;
                    } else if (Float.isNaN(f6)) {
                        f6 = next.time;
                    }
                }
            }
            if (easing != null) {
                if (Float.isNaN(f6)) {
                    f6 = 1.0f;
                }
                d3 = (((float) easing.get((fMin - f4) / r12)) * (f6 - f4)) + f4;
            }
            this.mSpline[0].getPos(d3, this.mInterpolateData);
            CurveFit curveFit = this.mArcSpline;
            if (curveFit != null) {
                double[] dArr = this.mInterpolateData;
                if (dArr.length > 0) {
                    curveFit.getPos(d3, dArr);
                }
            }
            this.mStartMotionPath.getBounds(this.mInterpolateVariables, this.mInterpolateData, bounds, i2 * 2);
        }
    }

    public int buildKeyBounds(float[] keyBounds, int[] mode) {
        if (keyBounds == null) {
            return 0;
        }
        double[] timePoints = this.mSpline[0].getTimePoints();
        if (mode != null) {
            Iterator<MotionPaths> it = this.mMotionPaths.iterator();
            int i2 = 0;
            while (it.hasNext()) {
                mode[i2] = it.next().mMode;
                i2++;
            }
        }
        int i3 = 0;
        for (double d3 : timePoints) {
            this.mSpline[0].getPos(d3, this.mInterpolateData);
            this.mStartMotionPath.getBounds(this.mInterpolateVariables, this.mInterpolateData, keyBounds, i3);
            i3 += 2;
        }
        return i3 / 2;
    }

    public int buildKeyFrames(float[] keyFrames, int[] mode) {
        if (keyFrames == null) {
            return 0;
        }
        double[] timePoints = this.mSpline[0].getTimePoints();
        if (mode != null) {
            Iterator<MotionPaths> it = this.mMotionPaths.iterator();
            int i2 = 0;
            while (it.hasNext()) {
                mode[i2] = it.next().mMode;
                i2++;
            }
        }
        int i3 = 0;
        for (int i4 = 0; i4 < timePoints.length; i4++) {
            this.mSpline[0].getPos(timePoints[i4], this.mInterpolateData);
            this.mStartMotionPath.getCenter(timePoints[i4], this.mInterpolateVariables, this.mInterpolateData, keyFrames, i3);
            i3 += 2;
        }
        return i3 / 2;
    }

    public void buildPath(float[] points, int pointCount) {
        double d3;
        float f2 = 1.0f;
        float f3 = 1.0f / (pointCount - 1);
        HashMap<String, ViewSpline> map = this.mAttributesMap;
        ViewSpline viewSpline = map == null ? null : map.get("translationX");
        HashMap<String, ViewSpline> map2 = this.mAttributesMap;
        ViewSpline viewSpline2 = map2 == null ? null : map2.get("translationY");
        HashMap<String, ViewOscillator> map3 = this.mCycleMap;
        ViewOscillator viewOscillator = map3 == null ? null : map3.get("translationX");
        HashMap<String, ViewOscillator> map4 = this.mCycleMap;
        ViewOscillator viewOscillator2 = map4 != null ? map4.get("translationY") : null;
        int i2 = 0;
        while (i2 < pointCount) {
            float fMin = i2 * f3;
            float f4 = this.mStaggerScale;
            float f5 = 0.0f;
            if (f4 != f2) {
                float f6 = this.mStaggerOffset;
                if (fMin < f6) {
                    fMin = 0.0f;
                }
                if (fMin > f6 && fMin < 1.0d) {
                    fMin = Math.min((fMin - f6) * f4, f2);
                }
            }
            float f7 = fMin;
            double d4 = f7;
            Easing easing = this.mStartMotionPath.mKeyFrameEasing;
            Iterator<MotionPaths> it = this.mMotionPaths.iterator();
            float f8 = Float.NaN;
            while (it.hasNext()) {
                MotionPaths next = it.next();
                Easing easing2 = next.mKeyFrameEasing;
                double d5 = d4;
                if (easing2 != null) {
                    float f9 = next.time;
                    if (f9 < f7) {
                        f5 = f9;
                        easing = easing2;
                    } else if (Float.isNaN(f8)) {
                        f8 = next.time;
                    }
                }
                d4 = d5;
            }
            double d6 = d4;
            if (easing != null) {
                if (Float.isNaN(f8)) {
                    f8 = 1.0f;
                }
                d3 = (((float) easing.get((f7 - f5) / r16)) * (f8 - f5)) + f5;
            } else {
                d3 = d6;
            }
            this.mSpline[0].getPos(d3, this.mInterpolateData);
            CurveFit curveFit = this.mArcSpline;
            if (curveFit != null) {
                double[] dArr = this.mInterpolateData;
                if (dArr.length > 0) {
                    curveFit.getPos(d3, dArr);
                }
            }
            int i3 = i2 * 2;
            int i4 = i2;
            this.mStartMotionPath.getCenter(d3, this.mInterpolateVariables, this.mInterpolateData, points, i3);
            if (viewOscillator != null) {
                points[i3] = points[i3] + viewOscillator.get(f7);
            } else if (viewSpline != null) {
                points[i3] = points[i3] + viewSpline.get(f7);
            }
            if (viewOscillator2 != null) {
                int i5 = i3 + 1;
                points[i5] = points[i5] + viewOscillator2.get(f7);
            } else if (viewSpline2 != null) {
                int i6 = i3 + 1;
                points[i6] = points[i6] + viewSpline2.get(f7);
            }
            i2 = i4 + 1;
            f2 = 1.0f;
        }
    }

    public void buildRect(float p2, float[] path, int offset) {
        this.mSpline[0].getPos(getAdjustedPosition(p2, null), this.mInterpolateData);
        this.mStartMotionPath.getRect(this.mInterpolateVariables, this.mInterpolateData, path, offset);
    }

    public void buildRectangles(float[] path, int pointCount) {
        float f2 = 1.0f / (pointCount - 1);
        for (int i2 = 0; i2 < pointCount; i2++) {
            this.mSpline[0].getPos(getAdjustedPosition(i2 * f2, null), this.mInterpolateData);
            this.mStartMotionPath.getRect(this.mInterpolateVariables, this.mInterpolateData, path, i2 * 8);
        }
    }

    public void endTrigger(boolean start) {
        if (!"button".equals(Debug.getName(this.mView)) || this.mKeyTriggers == null) {
            return;
        }
        int i2 = 0;
        while (true) {
            KeyTrigger[] keyTriggerArr = this.mKeyTriggers;
            if (i2 >= keyTriggerArr.length) {
                return;
            }
            keyTriggerArr[i2].conditionallyFire(start ? -100.0f : 100.0f, this.mView);
            i2++;
        }
    }

    public int getAnimateRelativeTo() {
        return this.mStartMotionPath.mAnimateRelativeTo;
    }

    public int getAttributeValues(String attributeType, float[] points, int pointCount) {
        ViewSpline viewSpline = this.mAttributesMap.get(attributeType);
        if (viewSpline == null) {
            return -1;
        }
        for (int i2 = 0; i2 < points.length; i2++) {
            points[i2] = viewSpline.get(i2 / (points.length - 1));
        }
        return points.length;
    }

    public void getCenter(double p2, float[] pos, float[] vel) {
        double[] dArr = new double[4];
        double[] dArr2 = new double[4];
        this.mSpline[0].getPos(p2, dArr);
        this.mSpline[0].getSlope(p2, dArr2);
        Arrays.fill(vel, 0.0f);
        this.mStartMotionPath.getCenter(p2, this.mInterpolateVariables, dArr, pos, dArr2, vel);
    }

    public float getCenterX() {
        return this.mCurrentCenterX;
    }

    public float getCenterY() {
        return this.mCurrentCenterY;
    }

    public void getDpDt(float position, float locationX, float locationY, float[] mAnchorDpDt) {
        double[] dArr;
        float adjustedPosition = getAdjustedPosition(position, this.mVelocity);
        CurveFit[] curveFitArr = this.mSpline;
        int i2 = 0;
        if (curveFitArr == null) {
            MotionPaths motionPaths = this.mEndMotionPath;
            float f2 = motionPaths.f1788x;
            MotionPaths motionPaths2 = this.mStartMotionPath;
            float f3 = f2 - motionPaths2.f1788x;
            float f4 = motionPaths.f1789y - motionPaths2.f1789y;
            float f5 = (motionPaths.width - motionPaths2.width) + f3;
            float f6 = (motionPaths.height - motionPaths2.height) + f4;
            mAnchorDpDt[0] = (f3 * (1.0f - locationX)) + (f5 * locationX);
            mAnchorDpDt[1] = (f4 * (1.0f - locationY)) + (f6 * locationY);
            return;
        }
        double d3 = adjustedPosition;
        curveFitArr[0].getSlope(d3, this.mInterpolateVelocity);
        this.mSpline[0].getPos(d3, this.mInterpolateData);
        float f7 = this.mVelocity[0];
        while (true) {
            dArr = this.mInterpolateVelocity;
            if (i2 >= dArr.length) {
                break;
            }
            dArr[i2] = dArr[i2] * f7;
            i2++;
        }
        CurveFit curveFit = this.mArcSpline;
        if (curveFit == null) {
            this.mStartMotionPath.setDpDt(locationX, locationY, mAnchorDpDt, this.mInterpolateVariables, dArr, this.mInterpolateData);
            return;
        }
        double[] dArr2 = this.mInterpolateData;
        if (dArr2.length > 0) {
            curveFit.getPos(d3, dArr2);
            this.mArcSpline.getSlope(d3, this.mInterpolateVelocity);
            this.mStartMotionPath.setDpDt(locationX, locationY, mAnchorDpDt, this.mInterpolateVariables, this.mInterpolateVelocity, this.mInterpolateData);
        }
    }

    public int getDrawPath() {
        int iMax = this.mStartMotionPath.mDrawPath;
        Iterator<MotionPaths> it = this.mMotionPaths.iterator();
        while (it.hasNext()) {
            iMax = Math.max(iMax, it.next().mDrawPath);
        }
        return Math.max(iMax, this.mEndMotionPath.mDrawPath);
    }

    public float getFinalHeight() {
        return this.mEndMotionPath.height;
    }

    public float getFinalWidth() {
        return this.mEndMotionPath.width;
    }

    public float getFinalX() {
        return this.mEndMotionPath.f1788x;
    }

    public float getFinalY() {
        return this.mEndMotionPath.f1789y;
    }

    public MotionPaths getKeyFrame(int i2) {
        return this.mMotionPaths.get(i2);
    }

    public int getKeyFrameInfo(int type, int[] info) {
        float[] fArr = new float[2];
        Iterator<Key> it = this.mKeyList.iterator();
        int i2 = 0;
        int i3 = 0;
        while (it.hasNext()) {
            Key next = it.next();
            int i4 = next.mType;
            if (i4 == type || type != -1) {
                info[i3] = 0;
                int i5 = i3 + 1;
                info[i5] = i4;
                int i6 = i5 + 1;
                int i7 = next.mFramePosition;
                info[i6] = i7;
                double d3 = i7 / 100.0f;
                this.mSpline[0].getPos(d3, this.mInterpolateData);
                this.mStartMotionPath.getCenter(d3, this.mInterpolateVariables, this.mInterpolateData, fArr, 0);
                int i8 = i6 + 1;
                info[i8] = Float.floatToIntBits(fArr[0]);
                int i9 = i8 + 1;
                info[i9] = Float.floatToIntBits(fArr[1]);
                if (next instanceof KeyPosition) {
                    KeyPosition keyPosition = (KeyPosition) next;
                    int i10 = i9 + 1;
                    info[i10] = keyPosition.mPositionType;
                    int i11 = i10 + 1;
                    info[i11] = Float.floatToIntBits(keyPosition.mPercentX);
                    i9 = i11 + 1;
                    info[i9] = Float.floatToIntBits(keyPosition.mPercentY);
                }
                int i12 = i9 + 1;
                info[i3] = i12 - i3;
                i2++;
                i3 = i12;
            }
        }
        return i2;
    }

    public float getKeyFrameParameter(int type, float x2, float y2) {
        MotionPaths motionPaths = this.mEndMotionPath;
        float f2 = motionPaths.f1788x;
        MotionPaths motionPaths2 = this.mStartMotionPath;
        float f3 = motionPaths2.f1788x;
        float f4 = f2 - f3;
        float f5 = motionPaths.f1789y;
        float f6 = motionPaths2.f1789y;
        float f7 = f5 - f6;
        float f8 = f3 + (motionPaths2.width / 2.0f);
        float f9 = f6 + (motionPaths2.height / 2.0f);
        float fHypot = (float) Math.hypot(f4, f7);
        if (fHypot < 1.0E-7d) {
            return Float.NaN;
        }
        float f10 = x2 - f8;
        float f11 = y2 - f9;
        if (((float) Math.hypot(f10, f11)) == 0.0f) {
            return 0.0f;
        }
        float f12 = (f10 * f4) + (f11 * f7);
        if (type == 0) {
            return f12 / fHypot;
        }
        if (type == 1) {
            return (float) Math.sqrt((fHypot * fHypot) - (f12 * f12));
        }
        if (type == 2) {
            return f10 / f4;
        }
        if (type == 3) {
            return f11 / f4;
        }
        if (type == 4) {
            return f10 / f7;
        }
        if (type != 5) {
            return 0.0f;
        }
        return f11 / f7;
    }

    public int getKeyFramePositions(int[] type, float[] pos) {
        Iterator<Key> it = this.mKeyList.iterator();
        int i2 = 0;
        int i3 = 0;
        while (it.hasNext()) {
            Key next = it.next();
            int i4 = next.mFramePosition;
            type[i2] = (next.mType * 1000) + i4;
            double d3 = i4 / 100.0f;
            this.mSpline[0].getPos(d3, this.mInterpolateData);
            this.mStartMotionPath.getCenter(d3, this.mInterpolateVariables, this.mInterpolateData, pos, i3);
            i3 += 2;
            i2++;
        }
        return i2;
    }

    public double[] getPos(double position) {
        this.mSpline[0].getPos(position, this.mInterpolateData);
        CurveFit curveFit = this.mArcSpline;
        if (curveFit != null) {
            double[] dArr = this.mInterpolateData;
            if (dArr.length > 0) {
                curveFit.getPos(position, dArr);
            }
        }
        return this.mInterpolateData;
    }

    public KeyPositionBase getPositionKeyframe(int layoutWidth, int layoutHeight, float x2, float y2) {
        RectF rectF = new RectF();
        MotionPaths motionPaths = this.mStartMotionPath;
        float f2 = motionPaths.f1788x;
        rectF.left = f2;
        float f3 = motionPaths.f1789y;
        rectF.top = f3;
        rectF.right = f2 + motionPaths.width;
        rectF.bottom = f3 + motionPaths.height;
        RectF rectF2 = new RectF();
        MotionPaths motionPaths2 = this.mEndMotionPath;
        float f4 = motionPaths2.f1788x;
        rectF2.left = f4;
        float f5 = motionPaths2.f1789y;
        rectF2.top = f5;
        rectF2.right = f4 + motionPaths2.width;
        rectF2.bottom = f5 + motionPaths2.height;
        Iterator<Key> it = this.mKeyList.iterator();
        while (it.hasNext()) {
            Key next = it.next();
            if (next instanceof KeyPositionBase) {
                KeyPositionBase keyPositionBase = (KeyPositionBase) next;
                if (keyPositionBase.intersects(layoutWidth, layoutHeight, rectF, rectF2, x2, y2)) {
                    return keyPositionBase;
                }
            }
        }
        return null;
    }

    public void getPostLayoutDvDp(float position, int width, int height, float locationX, float locationY, float[] mAnchorDpDt) {
        float adjustedPosition = getAdjustedPosition(position, this.mVelocity);
        HashMap<String, ViewSpline> map = this.mAttributesMap;
        ViewSpline viewSpline = map == null ? null : map.get("translationX");
        HashMap<String, ViewSpline> map2 = this.mAttributesMap;
        ViewSpline viewSpline2 = map2 == null ? null : map2.get("translationY");
        HashMap<String, ViewSpline> map3 = this.mAttributesMap;
        ViewSpline viewSpline3 = map3 == null ? null : map3.get(Key.ROTATION);
        HashMap<String, ViewSpline> map4 = this.mAttributesMap;
        ViewSpline viewSpline4 = map4 == null ? null : map4.get("scaleX");
        HashMap<String, ViewSpline> map5 = this.mAttributesMap;
        ViewSpline viewSpline5 = map5 == null ? null : map5.get("scaleY");
        HashMap<String, ViewOscillator> map6 = this.mCycleMap;
        ViewOscillator viewOscillator = map6 == null ? null : map6.get("translationX");
        HashMap<String, ViewOscillator> map7 = this.mCycleMap;
        ViewOscillator viewOscillator2 = map7 == null ? null : map7.get("translationY");
        HashMap<String, ViewOscillator> map8 = this.mCycleMap;
        ViewOscillator viewOscillator3 = map8 == null ? null : map8.get(Key.ROTATION);
        HashMap<String, ViewOscillator> map9 = this.mCycleMap;
        ViewOscillator viewOscillator4 = map9 == null ? null : map9.get("scaleX");
        HashMap<String, ViewOscillator> map10 = this.mCycleMap;
        ViewOscillator viewOscillator5 = map10 != null ? map10.get("scaleY") : null;
        VelocityMatrix velocityMatrix = new VelocityMatrix();
        velocityMatrix.clear();
        velocityMatrix.setRotationVelocity(viewSpline3, adjustedPosition);
        velocityMatrix.setTranslationVelocity(viewSpline, viewSpline2, adjustedPosition);
        velocityMatrix.setScaleVelocity(viewSpline4, viewSpline5, adjustedPosition);
        velocityMatrix.setRotationVelocity(viewOscillator3, adjustedPosition);
        velocityMatrix.setTranslationVelocity(viewOscillator, viewOscillator2, adjustedPosition);
        velocityMatrix.setScaleVelocity(viewOscillator4, viewOscillator5, adjustedPosition);
        CurveFit curveFit = this.mArcSpline;
        if (curveFit != null) {
            double[] dArr = this.mInterpolateData;
            if (dArr.length > 0) {
                double d3 = adjustedPosition;
                curveFit.getPos(d3, dArr);
                this.mArcSpline.getSlope(d3, this.mInterpolateVelocity);
                this.mStartMotionPath.setDpDt(locationX, locationY, mAnchorDpDt, this.mInterpolateVariables, this.mInterpolateVelocity, this.mInterpolateData);
            }
            velocityMatrix.applyTransform(locationX, locationY, width, height, mAnchorDpDt);
            return;
        }
        int i2 = 0;
        if (this.mSpline == null) {
            MotionPaths motionPaths = this.mEndMotionPath;
            float f2 = motionPaths.f1788x;
            MotionPaths motionPaths2 = this.mStartMotionPath;
            float f3 = f2 - motionPaths2.f1788x;
            ViewOscillator viewOscillator6 = viewOscillator5;
            float f4 = motionPaths.f1789y - motionPaths2.f1789y;
            ViewOscillator viewOscillator7 = viewOscillator4;
            float f5 = (motionPaths.width - motionPaths2.width) + f3;
            float f6 = (motionPaths.height - motionPaths2.height) + f4;
            mAnchorDpDt[0] = (f3 * (1.0f - locationX)) + (f5 * locationX);
            mAnchorDpDt[1] = (f4 * (1.0f - locationY)) + (f6 * locationY);
            velocityMatrix.clear();
            velocityMatrix.setRotationVelocity(viewSpline3, adjustedPosition);
            velocityMatrix.setTranslationVelocity(viewSpline, viewSpline2, adjustedPosition);
            velocityMatrix.setScaleVelocity(viewSpline4, viewSpline5, adjustedPosition);
            velocityMatrix.setRotationVelocity(viewOscillator3, adjustedPosition);
            velocityMatrix.setTranslationVelocity(viewOscillator, viewOscillator2, adjustedPosition);
            velocityMatrix.setScaleVelocity(viewOscillator7, viewOscillator6, adjustedPosition);
            velocityMatrix.applyTransform(locationX, locationY, width, height, mAnchorDpDt);
            return;
        }
        double adjustedPosition2 = getAdjustedPosition(adjustedPosition, this.mVelocity);
        this.mSpline[0].getSlope(adjustedPosition2, this.mInterpolateVelocity);
        this.mSpline[0].getPos(adjustedPosition2, this.mInterpolateData);
        float f7 = this.mVelocity[0];
        while (true) {
            double[] dArr2 = this.mInterpolateVelocity;
            if (i2 >= dArr2.length) {
                this.mStartMotionPath.setDpDt(locationX, locationY, mAnchorDpDt, this.mInterpolateVariables, dArr2, this.mInterpolateData);
                velocityMatrix.applyTransform(locationX, locationY, width, height, mAnchorDpDt);
                return;
            } else {
                dArr2[i2] = dArr2[i2] * f7;
                i2++;
            }
        }
    }

    public float getStartHeight() {
        return this.mStartMotionPath.height;
    }

    public float getStartWidth() {
        return this.mStartMotionPath.width;
    }

    public float getStartX() {
        return this.mStartMotionPath.f1788x;
    }

    public float getStartY() {
        return this.mStartMotionPath.f1789y;
    }

    public int getTransformPivotTarget() {
        return this.mTransformPivotTarget;
    }

    public View getView() {
        return this.mView;
    }

    public boolean interpolate(View child, float global_position, long time, KeyCache keyCache) {
        ViewTimeCycle.PathRotate pathRotate;
        boolean pathRotate2;
        int i2;
        double d3;
        float adjustedPosition = getAdjustedPosition(global_position, null);
        int i3 = this.mQuantizeMotionSteps;
        if (i3 != Key.UNSET) {
            float f2 = 1.0f / i3;
            float fFloor = ((float) Math.floor(adjustedPosition / f2)) * f2;
            float f3 = (adjustedPosition % f2) / f2;
            if (!Float.isNaN(this.mQuantizeMotionPhase)) {
                f3 = (f3 + this.mQuantizeMotionPhase) % 1.0f;
            }
            Interpolator interpolator = this.mQuantizeMotionInterpolator;
            adjustedPosition = ((interpolator != null ? interpolator.getInterpolation(f3) : ((double) f3) > 0.5d ? 1.0f : 0.0f) * f2) + fFloor;
        }
        float f4 = adjustedPosition;
        HashMap<String, ViewSpline> map = this.mAttributesMap;
        if (map != null) {
            Iterator<ViewSpline> it = map.values().iterator();
            while (it.hasNext()) {
                it.next().setProperty(child, f4);
            }
        }
        HashMap<String, ViewTimeCycle> map2 = this.mTimeCycleAttributesMap;
        if (map2 != null) {
            ViewTimeCycle.PathRotate pathRotate3 = null;
            boolean property = false;
            for (ViewTimeCycle viewTimeCycle : map2.values()) {
                if (viewTimeCycle instanceof ViewTimeCycle.PathRotate) {
                    pathRotate3 = (ViewTimeCycle.PathRotate) viewTimeCycle;
                } else {
                    property |= viewTimeCycle.setProperty(child, f4, time, keyCache);
                }
            }
            pathRotate2 = property;
            pathRotate = pathRotate3;
        } else {
            pathRotate = null;
            pathRotate2 = false;
        }
        CurveFit[] curveFitArr = this.mSpline;
        if (curveFitArr != null) {
            double d4 = f4;
            curveFitArr[0].getPos(d4, this.mInterpolateData);
            this.mSpline[0].getSlope(d4, this.mInterpolateVelocity);
            CurveFit curveFit = this.mArcSpline;
            if (curveFit != null) {
                double[] dArr = this.mInterpolateData;
                if (dArr.length > 0) {
                    curveFit.getPos(d4, dArr);
                    this.mArcSpline.getSlope(d4, this.mInterpolateVelocity);
                }
            }
            if (this.mNoMovement) {
                d3 = d4;
            } else {
                d3 = d4;
                this.mStartMotionPath.setView(f4, child, this.mInterpolateVariables, this.mInterpolateData, this.mInterpolateVelocity, null, this.mForceMeasure);
                this.mForceMeasure = false;
            }
            if (this.mTransformPivotTarget != Key.UNSET) {
                if (this.mTransformPivotView == null) {
                    this.mTransformPivotView = ((View) child.getParent()).findViewById(this.mTransformPivotTarget);
                }
                if (this.mTransformPivotView != null) {
                    float top2 = (r1.getTop() + this.mTransformPivotView.getBottom()) / 2.0f;
                    float left = (this.mTransformPivotView.getLeft() + this.mTransformPivotView.getRight()) / 2.0f;
                    if (child.getRight() - child.getLeft() > 0 && child.getBottom() - child.getTop() > 0) {
                        child.setPivotX(left - child.getLeft());
                        child.setPivotY(top2 - child.getTop());
                    }
                }
            }
            HashMap<String, ViewSpline> map3 = this.mAttributesMap;
            if (map3 != null) {
                for (ViewSpline viewSpline : map3.values()) {
                    if (viewSpline instanceof ViewSpline.PathRotate) {
                        double[] dArr2 = this.mInterpolateVelocity;
                        if (dArr2.length > 1) {
                            ((ViewSpline.PathRotate) viewSpline).setPathRotate(child, f4, dArr2[0], dArr2[1]);
                        }
                    }
                }
            }
            if (pathRotate != null) {
                double[] dArr3 = this.mInterpolateVelocity;
                i2 = 1;
                pathRotate2 |= pathRotate.setPathRotate(child, keyCache, f4, time, dArr3[0], dArr3[1]);
            } else {
                i2 = 1;
            }
            int i4 = i2;
            while (true) {
                CurveFit[] curveFitArr2 = this.mSpline;
                if (i4 >= curveFitArr2.length) {
                    break;
                }
                curveFitArr2[i4].getPos(d3, this.mValuesBuff);
                CustomSupport.setInterpolatedValue(this.mStartMotionPath.attributes.get(this.mAttributeNames[i4 - 1]), child, this.mValuesBuff);
                i4++;
            }
            MotionConstrainedPoint motionConstrainedPoint = this.mStartPoint;
            if (motionConstrainedPoint.mVisibilityMode == 0) {
                if (f4 <= 0.0f) {
                    child.setVisibility(motionConstrainedPoint.visibility);
                } else if (f4 >= 1.0f) {
                    child.setVisibility(this.mEndPoint.visibility);
                } else if (this.mEndPoint.visibility != motionConstrainedPoint.visibility) {
                    child.setVisibility(0);
                }
            }
            if (this.mKeyTriggers != null) {
                int i5 = 0;
                while (true) {
                    KeyTrigger[] keyTriggerArr = this.mKeyTriggers;
                    if (i5 >= keyTriggerArr.length) {
                        break;
                    }
                    keyTriggerArr[i5].conditionallyFire(f4, child);
                    i5++;
                }
            }
        } else {
            i2 = 1;
            MotionPaths motionPaths = this.mStartMotionPath;
            float f5 = motionPaths.f1788x;
            MotionPaths motionPaths2 = this.mEndMotionPath;
            float f6 = f5 + ((motionPaths2.f1788x - f5) * f4);
            float f7 = motionPaths.f1789y;
            float f8 = f7 + ((motionPaths2.f1789y - f7) * f4);
            float f9 = motionPaths.width;
            float f10 = motionPaths2.width;
            float f11 = motionPaths.height;
            float f12 = motionPaths2.height;
            float f13 = f6 + 0.5f;
            int i6 = (int) f13;
            float f14 = f8 + 0.5f;
            int i7 = (int) f14;
            int i8 = (int) (f13 + ((f10 - f9) * f4) + f9);
            int i9 = (int) (f14 + ((f12 - f11) * f4) + f11);
            int i10 = i8 - i6;
            int i11 = i9 - i7;
            if (f10 != f9 || f12 != f11 || this.mForceMeasure) {
                child.measure(View.MeasureSpec.makeMeasureSpec(i10, 1073741824), View.MeasureSpec.makeMeasureSpec(i11, 1073741824));
                this.mForceMeasure = false;
            }
            child.layout(i6, i7, i8, i9);
        }
        HashMap<String, ViewOscillator> map4 = this.mCycleMap;
        if (map4 != null) {
            for (ViewOscillator viewOscillator : map4.values()) {
                if (viewOscillator instanceof ViewOscillator.PathRotateSet) {
                    double[] dArr4 = this.mInterpolateVelocity;
                    ((ViewOscillator.PathRotateSet) viewOscillator).setPathRotate(child, f4, dArr4[0], dArr4[i2]);
                } else {
                    viewOscillator.setProperty(child, f4);
                }
            }
        }
        return pathRotate2;
    }

    public String name() {
        return this.mView.getContext().getResources().getResourceEntryName(this.mView.getId());
    }

    public void positionKeyframe(View view, KeyPositionBase key, float x2, float y2, String[] attribute, float[] value) {
        RectF rectF = new RectF();
        MotionPaths motionPaths = this.mStartMotionPath;
        float f2 = motionPaths.f1788x;
        rectF.left = f2;
        float f3 = motionPaths.f1789y;
        rectF.top = f3;
        rectF.right = f2 + motionPaths.width;
        rectF.bottom = f3 + motionPaths.height;
        RectF rectF2 = new RectF();
        MotionPaths motionPaths2 = this.mEndMotionPath;
        float f4 = motionPaths2.f1788x;
        rectF2.left = f4;
        float f5 = motionPaths2.f1789y;
        rectF2.top = f5;
        rectF2.right = f4 + motionPaths2.width;
        rectF2.bottom = f5 + motionPaths2.height;
        key.positionAttributes(view, rectF, rectF2, x2, y2, attribute, value);
    }

    public void remeasure() {
        this.mForceMeasure = true;
    }

    public void rotate(Rect rect, Rect out, int rotation, int preHeight, int preWidth) {
        if (rotation == 1) {
            int i2 = rect.left + rect.right;
            out.left = ((rect.top + rect.bottom) - rect.width()) / 2;
            out.top = preWidth - ((i2 + rect.height()) / 2);
            out.right = out.left + rect.width();
            out.bottom = out.top + rect.height();
            return;
        }
        if (rotation == 2) {
            int i3 = rect.left + rect.right;
            out.left = preHeight - (((rect.top + rect.bottom) + rect.width()) / 2);
            out.top = (i3 - rect.height()) / 2;
            out.right = out.left + rect.width();
            out.bottom = out.top + rect.height();
            return;
        }
        if (rotation == 3) {
            int i4 = rect.left + rect.right;
            out.left = ((rect.height() / 2) + rect.top) - (i4 / 2);
            out.top = preWidth - ((i4 + rect.height()) / 2);
            out.right = out.left + rect.width();
            out.bottom = out.top + rect.height();
            return;
        }
        if (rotation != 4) {
            return;
        }
        int i5 = rect.left + rect.right;
        out.left = preHeight - (((rect.bottom + rect.top) + rect.width()) / 2);
        out.top = (i5 - rect.height()) / 2;
        out.right = out.left + rect.width();
        out.bottom = out.top + rect.height();
    }

    public void setBothStates(View v2) {
        MotionPaths motionPaths = this.mStartMotionPath;
        motionPaths.time = 0.0f;
        motionPaths.position = 0.0f;
        this.mNoMovement = true;
        motionPaths.setBounds(v2.getX(), v2.getY(), v2.getWidth(), v2.getHeight());
        this.mEndMotionPath.setBounds(v2.getX(), v2.getY(), v2.getWidth(), v2.getHeight());
        this.mStartPoint.setState(v2);
        this.mEndPoint.setState(v2);
    }

    public void setDrawPath(int debugMode) {
        this.mStartMotionPath.mDrawPath = debugMode;
    }

    public void setEndState(Rect cw, ConstraintSet constraintSet, int parentWidth, int parentHeight) {
        int i2 = constraintSet.mRotate;
        if (i2 != 0) {
            rotate(cw, this.mTempRect, i2, parentWidth, parentHeight);
            cw = this.mTempRect;
        }
        MotionPaths motionPaths = this.mEndMotionPath;
        motionPaths.time = 1.0f;
        motionPaths.position = 1.0f;
        readView(motionPaths);
        this.mEndMotionPath.setBounds(cw.left, cw.top, cw.width(), cw.height());
        this.mEndMotionPath.applyParameters(constraintSet.getParameters(this.mId));
        this.mEndPoint.setState(cw, constraintSet, i2, this.mId);
    }

    public void setPathMotionArc(int arc) {
        this.mPathMotionArc = arc;
    }

    public void setStartCurrentState(View v2) {
        MotionPaths motionPaths = this.mStartMotionPath;
        motionPaths.time = 0.0f;
        motionPaths.position = 0.0f;
        motionPaths.setBounds(v2.getX(), v2.getY(), v2.getWidth(), v2.getHeight());
        this.mStartPoint.setState(v2);
    }

    public void setStartState(ViewState rect, View v2, int rotation, int preWidth, int preHeight) {
        MotionPaths motionPaths = this.mStartMotionPath;
        motionPaths.time = 0.0f;
        motionPaths.position = 0.0f;
        Rect rect2 = new Rect();
        if (rotation == 1) {
            int i2 = rect.left + rect.right;
            rect2.left = ((rect.f1784top + rect.bottom) - rect.width()) / 2;
            rect2.top = preWidth - ((i2 + rect.height()) / 2);
            rect2.right = rect2.left + rect.width();
            rect2.bottom = rect2.top + rect.height();
        } else if (rotation == 2) {
            int i3 = rect.left + rect.right;
            rect2.left = preHeight - (((rect.f1784top + rect.bottom) + rect.width()) / 2);
            rect2.top = (i3 - rect.height()) / 2;
            rect2.right = rect2.left + rect.width();
            rect2.bottom = rect2.top + rect.height();
        }
        this.mStartMotionPath.setBounds(rect2.left, rect2.top, rect2.width(), rect2.height());
        this.mStartPoint.setState(rect2, v2, rotation, rect.rotation);
    }

    public void setTransformPivotTarget(int transformPivotTarget) {
        this.mTransformPivotTarget = transformPivotTarget;
        this.mTransformPivotView = null;
    }

    public void setView(View view) {
        this.mView = view;
        this.mId = view.getId();
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof ConstraintLayout.LayoutParams) {
            this.mConstraintTag = ((ConstraintLayout.LayoutParams) layoutParams).getConstraintTag();
        }
    }

    public void setup(int parentWidth, int parentHeight, float transitionDuration, long currentTime) {
        ArrayList arrayList;
        String[] strArr;
        ConstraintAttribute constraintAttribute;
        ViewTimeCycle viewTimeCycleMakeSpline;
        ConstraintAttribute constraintAttribute2;
        Integer num;
        ViewSpline viewSplineMakeSpline;
        ConstraintAttribute constraintAttribute3;
        new HashSet();
        HashSet<String> hashSet = new HashSet<>();
        HashSet<String> hashSet2 = new HashSet<>();
        HashSet<String> hashSet3 = new HashSet<>();
        HashMap<String, Integer> map = new HashMap<>();
        int i2 = this.mPathMotionArc;
        if (i2 != Key.UNSET) {
            this.mStartMotionPath.mPathMotionArc = i2;
        }
        this.mStartPoint.different(this.mEndPoint, hashSet2);
        ArrayList<Key> arrayList2 = this.mKeyList;
        if (arrayList2 != null) {
            Iterator<Key> it = arrayList2.iterator();
            arrayList = null;
            while (it.hasNext()) {
                Key next = it.next();
                if (next instanceof KeyPosition) {
                    KeyPosition keyPosition = (KeyPosition) next;
                    insertKey(new MotionPaths(parentWidth, parentHeight, keyPosition, this.mStartMotionPath, this.mEndMotionPath));
                    int i3 = keyPosition.mCurveFit;
                    if (i3 != Key.UNSET) {
                        this.mCurveFitType = i3;
                    }
                } else if (next instanceof KeyCycle) {
                    next.getAttributeNames(hashSet3);
                } else if (next instanceof KeyTimeCycle) {
                    next.getAttributeNames(hashSet);
                } else if (next instanceof KeyTrigger) {
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add((KeyTrigger) next);
                } else {
                    next.setInterpolation(map);
                    next.getAttributeNames(hashSet2);
                }
            }
        } else {
            arrayList = null;
        }
        int i4 = 0;
        if (arrayList != null) {
            this.mKeyTriggers = (KeyTrigger[]) arrayList.toArray(new KeyTrigger[0]);
        }
        char c3 = 1;
        if (!hashSet2.isEmpty()) {
            this.mAttributesMap = new HashMap<>();
            Iterator<String> it2 = hashSet2.iterator();
            while (it2.hasNext()) {
                String next2 = it2.next();
                if (next2.startsWith("CUSTOM,")) {
                    SparseArray sparseArray = new SparseArray();
                    String str = next2.split(",")[1];
                    Iterator<Key> it3 = this.mKeyList.iterator();
                    while (it3.hasNext()) {
                        Key next3 = it3.next();
                        HashMap<String, ConstraintAttribute> map2 = next3.mCustomConstraints;
                        if (map2 != null && (constraintAttribute3 = map2.get(str)) != null) {
                            sparseArray.append(next3.mFramePosition, constraintAttribute3);
                        }
                    }
                    viewSplineMakeSpline = ViewSpline.makeCustomSpline(next2, (SparseArray<ConstraintAttribute>) sparseArray);
                } else {
                    viewSplineMakeSpline = ViewSpline.makeSpline(next2);
                }
                if (viewSplineMakeSpline != null) {
                    viewSplineMakeSpline.setType(next2);
                    this.mAttributesMap.put(next2, viewSplineMakeSpline);
                }
            }
            ArrayList<Key> arrayList3 = this.mKeyList;
            if (arrayList3 != null) {
                Iterator<Key> it4 = arrayList3.iterator();
                while (it4.hasNext()) {
                    Key next4 = it4.next();
                    if (next4 instanceof KeyAttributes) {
                        next4.addValues(this.mAttributesMap);
                    }
                }
            }
            this.mStartPoint.addValues(this.mAttributesMap, 0);
            this.mEndPoint.addValues(this.mAttributesMap, 100);
            for (String str2 : this.mAttributesMap.keySet()) {
                int iIntValue = (!map.containsKey(str2) || (num = map.get(str2)) == null) ? 0 : num.intValue();
                ViewSpline viewSpline = this.mAttributesMap.get(str2);
                if (viewSpline != null) {
                    viewSpline.setup(iIntValue);
                }
            }
        }
        if (!hashSet.isEmpty()) {
            if (this.mTimeCycleAttributesMap == null) {
                this.mTimeCycleAttributesMap = new HashMap<>();
            }
            Iterator<String> it5 = hashSet.iterator();
            while (it5.hasNext()) {
                String next5 = it5.next();
                if (!this.mTimeCycleAttributesMap.containsKey(next5)) {
                    if (next5.startsWith("CUSTOM,")) {
                        SparseArray sparseArray2 = new SparseArray();
                        String str3 = next5.split(",")[1];
                        Iterator<Key> it6 = this.mKeyList.iterator();
                        while (it6.hasNext()) {
                            Key next6 = it6.next();
                            HashMap<String, ConstraintAttribute> map3 = next6.mCustomConstraints;
                            if (map3 != null && (constraintAttribute2 = map3.get(str3)) != null) {
                                sparseArray2.append(next6.mFramePosition, constraintAttribute2);
                            }
                        }
                        viewTimeCycleMakeSpline = ViewTimeCycle.makeCustomSpline(next5, sparseArray2);
                    } else {
                        viewTimeCycleMakeSpline = ViewTimeCycle.makeSpline(next5, currentTime);
                    }
                    if (viewTimeCycleMakeSpline != null) {
                        viewTimeCycleMakeSpline.setType(next5);
                        this.mTimeCycleAttributesMap.put(next5, viewTimeCycleMakeSpline);
                    }
                }
            }
            ArrayList<Key> arrayList4 = this.mKeyList;
            if (arrayList4 != null) {
                Iterator<Key> it7 = arrayList4.iterator();
                while (it7.hasNext()) {
                    Key next7 = it7.next();
                    if (next7 instanceof KeyTimeCycle) {
                        ((KeyTimeCycle) next7).addTimeValues(this.mTimeCycleAttributesMap);
                    }
                }
            }
            for (String str4 : this.mTimeCycleAttributesMap.keySet()) {
                this.mTimeCycleAttributesMap.get(str4).setup(map.containsKey(str4) ? map.get(str4).intValue() : 0);
            }
        }
        int i5 = 2;
        int size = this.mMotionPaths.size() + 2;
        MotionPaths[] motionPathsArr = new MotionPaths[size];
        motionPathsArr[0] = this.mStartMotionPath;
        motionPathsArr[size - 1] = this.mEndMotionPath;
        if (this.mMotionPaths.size() > 0 && this.mCurveFitType == -1) {
            this.mCurveFitType = 0;
        }
        Iterator<MotionPaths> it8 = this.mMotionPaths.iterator();
        int i6 = 1;
        while (it8.hasNext()) {
            motionPathsArr[i6] = it8.next();
            i6++;
        }
        HashSet hashSet4 = new HashSet();
        for (String str5 : this.mEndMotionPath.attributes.keySet()) {
            if (this.mStartMotionPath.attributes.containsKey(str5)) {
                if (!hashSet2.contains("CUSTOM," + str5)) {
                    hashSet4.add(str5);
                }
            }
        }
        String[] strArr2 = (String[]) hashSet4.toArray(new String[0]);
        this.mAttributeNames = strArr2;
        this.mAttributeInterpolatorCount = new int[strArr2.length];
        int i7 = 0;
        while (true) {
            strArr = this.mAttributeNames;
            if (i7 >= strArr.length) {
                break;
            }
            String str6 = strArr[i7];
            this.mAttributeInterpolatorCount[i7] = 0;
            int i8 = 0;
            while (true) {
                if (i8 >= size) {
                    break;
                }
                if (motionPathsArr[i8].attributes.containsKey(str6) && (constraintAttribute = motionPathsArr[i8].attributes.get(str6)) != null) {
                    int[] iArr = this.mAttributeInterpolatorCount;
                    iArr[i7] = iArr[i7] + constraintAttribute.numberOfInterpolatedValues();
                    break;
                }
                i8++;
            }
            i7++;
        }
        boolean z2 = motionPathsArr[0].mPathMotionArc != Key.UNSET;
        int length = 18 + strArr.length;
        boolean[] zArr = new boolean[length];
        for (int i9 = 1; i9 < size; i9++) {
            motionPathsArr[i9].different(motionPathsArr[i9 - 1], zArr, this.mAttributeNames, z2);
        }
        int i10 = 0;
        for (int i11 = 1; i11 < length; i11++) {
            if (zArr[i11]) {
                i10++;
            }
        }
        this.mInterpolateVariables = new int[i10];
        int iMax = Math.max(2, i10);
        this.mInterpolateData = new double[iMax];
        this.mInterpolateVelocity = new double[iMax];
        int i12 = 0;
        for (int i13 = 1; i13 < length; i13++) {
            if (zArr[i13]) {
                this.mInterpolateVariables[i12] = i13;
                i12++;
            }
        }
        double[][] dArr = (double[][]) Array.newInstance((Class<?>) Double.TYPE, size, this.mInterpolateVariables.length);
        double[] dArr2 = new double[size];
        for (int i14 = 0; i14 < size; i14++) {
            motionPathsArr[i14].fillStandard(dArr[i14], this.mInterpolateVariables);
            dArr2[i14] = motionPathsArr[i14].time;
        }
        int i15 = 0;
        while (true) {
            int[] iArr2 = this.mInterpolateVariables;
            if (i15 >= iArr2.length) {
                break;
            }
            if (iArr2[i15] < MotionPaths.names.length) {
                String str7 = MotionPaths.names[this.mInterpolateVariables[i15]] + " [";
                for (int i16 = 0; i16 < size; i16++) {
                    str7 = str7 + dArr[i16][i15];
                }
            }
            i15++;
        }
        this.mSpline = new CurveFit[this.mAttributeNames.length + 1];
        int i17 = 0;
        while (true) {
            String[] strArr3 = this.mAttributeNames;
            if (i17 >= strArr3.length) {
                break;
            }
            String str8 = strArr3[i17];
            int i18 = i4;
            int i19 = i18;
            double[] dArr3 = null;
            double[][] dArr4 = null;
            while (i18 < size) {
                if (motionPathsArr[i18].hasCustomData(str8)) {
                    if (dArr4 == null) {
                        dArr3 = new double[size];
                        int[] iArr3 = new int[i5];
                        iArr3[c3] = motionPathsArr[i18].getCustomDataCount(str8);
                        iArr3[i4] = size;
                        dArr4 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, iArr3);
                    }
                    MotionPaths motionPaths = motionPathsArr[i18];
                    dArr3[i19] = motionPaths.time;
                    motionPaths.getCustomData(str8, dArr4[i19], 0);
                    i19++;
                }
                i18++;
                i5 = 2;
                i4 = 0;
                c3 = 1;
            }
            i17++;
            this.mSpline[i17] = CurveFit.get(this.mCurveFitType, Arrays.copyOf(dArr3, i19), (double[][]) Arrays.copyOf(dArr4, i19));
            i5 = 2;
            i4 = 0;
            c3 = 1;
        }
        this.mSpline[0] = CurveFit.get(this.mCurveFitType, dArr2, dArr);
        if (motionPathsArr[0].mPathMotionArc != Key.UNSET) {
            int[] iArr4 = new int[size];
            double[] dArr5 = new double[size];
            double[][] dArr6 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, size, 2);
            for (int i20 = 0; i20 < size; i20++) {
                iArr4[i20] = motionPathsArr[i20].mPathMotionArc;
                dArr5[i20] = r8.time;
                double[] dArr7 = dArr6[i20];
                dArr7[0] = r8.f1788x;
                dArr7[1] = r8.f1789y;
            }
            this.mArcSpline = CurveFit.getArc(iArr4, dArr5, dArr6);
        }
        this.mCycleMap = new HashMap<>();
        if (this.mKeyList != null) {
            Iterator<String> it9 = hashSet3.iterator();
            float preCycleDistance = Float.NaN;
            while (it9.hasNext()) {
                String next8 = it9.next();
                ViewOscillator viewOscillatorMakeSpline = ViewOscillator.makeSpline(next8);
                if (viewOscillatorMakeSpline != null) {
                    if (viewOscillatorMakeSpline.variesByPath() && Float.isNaN(preCycleDistance)) {
                        preCycleDistance = getPreCycleDistance();
                    }
                    viewOscillatorMakeSpline.setType(next8);
                    this.mCycleMap.put(next8, viewOscillatorMakeSpline);
                }
            }
            Iterator<Key> it10 = this.mKeyList.iterator();
            while (it10.hasNext()) {
                Key next9 = it10.next();
                if (next9 instanceof KeyCycle) {
                    ((KeyCycle) next9).addCycleValues(this.mCycleMap);
                }
            }
            Iterator<ViewOscillator> it11 = this.mCycleMap.values().iterator();
            while (it11.hasNext()) {
                it11.next().setup(preCycleDistance);
            }
        }
    }

    public void setupRelative(MotionController motionController) {
        this.mStartMotionPath.setupRelative(motionController, motionController.mStartMotionPath);
        this.mEndMotionPath.setupRelative(motionController, motionController.mEndMotionPath);
    }

    public String toString() {
        return " start: x: " + this.mStartMotionPath.f1788x + " y: " + this.mStartMotionPath.f1789y + " end: x: " + this.mEndMotionPath.f1788x + " y: " + this.mEndMotionPath.f1789y;
    }

    public void setStartState(Rect cw, ConstraintSet constraintSet, int parentWidth, int parentHeight) {
        int i2 = constraintSet.mRotate;
        if (i2 != 0) {
            rotate(cw, this.mTempRect, i2, parentWidth, parentHeight);
        }
        MotionPaths motionPaths = this.mStartMotionPath;
        motionPaths.time = 0.0f;
        motionPaths.position = 0.0f;
        readView(motionPaths);
        this.mStartMotionPath.setBounds(cw.left, cw.top, cw.width(), cw.height());
        ConstraintSet.Constraint parameters = constraintSet.getParameters(this.mId);
        this.mStartMotionPath.applyParameters(parameters);
        this.mMotionStagger = parameters.motion.mMotionStagger;
        this.mStartPoint.setState(cw, constraintSet, i2, this.mId);
        this.mTransformPivotTarget = parameters.transform.transformPivotTarget;
        ConstraintSet.Motion motion = parameters.motion;
        this.mQuantizeMotionSteps = motion.mQuantizeMotionSteps;
        this.mQuantizeMotionPhase = motion.mQuantizeMotionPhase;
        Context context = this.mView.getContext();
        ConstraintSet.Motion motion2 = parameters.motion;
        this.mQuantizeMotionInterpolator = getInterpolator(context, motion2.mQuantizeInterpolatorType, motion2.mQuantizeInterpolatorString, motion2.mQuantizeInterpolatorID);
    }
}
