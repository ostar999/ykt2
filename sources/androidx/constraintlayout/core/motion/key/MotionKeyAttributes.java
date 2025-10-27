package androidx.constraintlayout.core.motion.key;

import androidx.constraintlayout.core.motion.utils.a;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/* loaded from: classes.dex */
public class MotionKeyAttributes extends MotionKey {
    private static final boolean DEBUG = false;
    public static final int KEY_TYPE = 1;
    static final String NAME = "KeyAttribute";
    private static final String TAG = "KeyAttributes";
    private String mTransitionEasing;
    private int mCurveFit = -1;
    private int mVisibility = 0;
    private float mAlpha = Float.NaN;
    private float mElevation = Float.NaN;
    private float mRotation = Float.NaN;
    private float mRotationX = Float.NaN;
    private float mRotationY = Float.NaN;
    private float mPivotX = Float.NaN;
    private float mPivotY = Float.NaN;
    private float mTransitionPathRotate = Float.NaN;
    private float mScaleX = Float.NaN;
    private float mScaleY = Float.NaN;
    private float mTranslationX = Float.NaN;
    private float mTranslationY = Float.NaN;
    private float mTranslationZ = Float.NaN;
    private float mProgress = Float.NaN;

    public MotionKeyAttributes() {
        this.mType = 1;
        this.mCustom = new HashMap<>();
    }

    private float getFloatValue(int i2) {
        if (i2 == 100) {
            return this.mFramePosition;
        }
        switch (i2) {
            case 303:
                return this.mAlpha;
            case 304:
                return this.mTranslationX;
            case 305:
                return this.mTranslationY;
            case 306:
                return this.mTranslationZ;
            case 307:
                return this.mElevation;
            case 308:
                return this.mRotationX;
            case 309:
                return this.mRotationY;
            case 310:
                return this.mRotation;
            case 311:
                return this.mScaleX;
            case 312:
                return this.mScaleY;
            case 313:
                return this.mPivotX;
            case 314:
                return this.mPivotY;
            case 315:
                return this.mProgress;
            case 316:
                return this.mTransitionPathRotate;
            default:
                return Float.NaN;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0044  */
    @Override // androidx.constraintlayout.core.motion.key.MotionKey
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void addValues(java.util.HashMap<java.lang.String, androidx.constraintlayout.core.motion.utils.SplineSet> r7) {
        /*
            Method dump skipped, instructions count: 594
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.motion.key.MotionKeyAttributes.addValues(java.util.HashMap):void");
    }

    @Override // androidx.constraintlayout.core.motion.key.MotionKey
    /* renamed from: clone */
    public MotionKey mo4clone() {
        return null;
    }

    @Override // androidx.constraintlayout.core.motion.key.MotionKey
    public void getAttributeNames(HashSet<String> hashSet) {
        if (!Float.isNaN(this.mAlpha)) {
            hashSet.add("alpha");
        }
        if (!Float.isNaN(this.mElevation)) {
            hashSet.add("elevation");
        }
        if (!Float.isNaN(this.mRotation)) {
            hashSet.add("rotationZ");
        }
        if (!Float.isNaN(this.mRotationX)) {
            hashSet.add("rotationX");
        }
        if (!Float.isNaN(this.mRotationY)) {
            hashSet.add("rotationY");
        }
        if (!Float.isNaN(this.mPivotX)) {
            hashSet.add("pivotX");
        }
        if (!Float.isNaN(this.mPivotY)) {
            hashSet.add("pivotY");
        }
        if (!Float.isNaN(this.mTranslationX)) {
            hashSet.add("translationX");
        }
        if (!Float.isNaN(this.mTranslationY)) {
            hashSet.add("translationY");
        }
        if (!Float.isNaN(this.mTranslationZ)) {
            hashSet.add("translationZ");
        }
        if (!Float.isNaN(this.mTransitionPathRotate)) {
            hashSet.add("pathRotate");
        }
        if (!Float.isNaN(this.mScaleX)) {
            hashSet.add("scaleX");
        }
        if (!Float.isNaN(this.mScaleY)) {
            hashSet.add("scaleY");
        }
        if (!Float.isNaN(this.mProgress)) {
            hashSet.add("progress");
        }
        if (this.mCustom.size() > 0) {
            Iterator<String> it = this.mCustom.keySet().iterator();
            while (it.hasNext()) {
                hashSet.add("CUSTOM," + it.next());
            }
        }
    }

    public int getCurveFit() {
        return this.mCurveFit;
    }

    @Override // androidx.constraintlayout.core.motion.utils.TypedValues
    public int getId(String str) {
        return a.a(str);
    }

    public void printAttributes() {
        HashSet<String> hashSet = new HashSet<>();
        getAttributeNames(hashSet);
        System.out.println(" ------------- " + this.mFramePosition + " -------------");
        String[] strArr = (String[]) hashSet.toArray(new String[0]);
        for (int i2 = 0; i2 < strArr.length; i2++) {
            int iA = a.a(strArr[i2]);
            System.out.println(strArr[i2] + ":" + getFloatValue(iA));
        }
    }

    @Override // androidx.constraintlayout.core.motion.key.MotionKey
    public void setInterpolation(HashMap<String, Integer> map) {
        if (!Float.isNaN(this.mAlpha)) {
            map.put("alpha", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mElevation)) {
            map.put("elevation", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mRotation)) {
            map.put("rotationZ", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mRotationX)) {
            map.put("rotationX", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mRotationY)) {
            map.put("rotationY", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mPivotX)) {
            map.put("pivotX", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mPivotY)) {
            map.put("pivotY", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mTranslationX)) {
            map.put("translationX", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mTranslationY)) {
            map.put("translationY", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mTranslationZ)) {
            map.put("translationZ", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mTransitionPathRotate)) {
            map.put("pathRotate", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mScaleX)) {
            map.put("scaleX", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mScaleY)) {
            map.put("scaleY", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mProgress)) {
            map.put("progress", Integer.valueOf(this.mCurveFit));
        }
        if (this.mCustom.size() > 0) {
            Iterator<String> it = this.mCustom.keySet().iterator();
            while (it.hasNext()) {
                map.put("CUSTOM," + it.next(), Integer.valueOf(this.mCurveFit));
            }
        }
    }

    @Override // androidx.constraintlayout.core.motion.key.MotionKey, androidx.constraintlayout.core.motion.utils.TypedValues
    public boolean setValue(int i2, int i3) {
        if (i2 == 100) {
            this.mFramePosition = i3;
            return true;
        }
        if (i2 == 301) {
            this.mCurveFit = i3;
            return true;
        }
        if (i2 == 302) {
            this.mVisibility = i3;
            return true;
        }
        if (setValue(i2, i3)) {
            return true;
        }
        return super.setValue(i2, i3);
    }

    @Override // androidx.constraintlayout.core.motion.key.MotionKey, androidx.constraintlayout.core.motion.utils.TypedValues
    public boolean setValue(int i2, float f2) {
        if (i2 != 100) {
            switch (i2) {
                case 303:
                    this.mAlpha = f2;
                    return true;
                case 304:
                    this.mTranslationX = f2;
                    return true;
                case 305:
                    this.mTranslationY = f2;
                    return true;
                case 306:
                    this.mTranslationZ = f2;
                    return true;
                case 307:
                    this.mElevation = f2;
                    return true;
                case 308:
                    this.mRotationX = f2;
                    return true;
                case 309:
                    this.mRotationY = f2;
                    return true;
                case 310:
                    this.mRotation = f2;
                    return true;
                case 311:
                    this.mScaleX = f2;
                    return true;
                case 312:
                    this.mScaleY = f2;
                    return true;
                case 313:
                    this.mPivotX = f2;
                    return true;
                case 314:
                    this.mPivotY = f2;
                    return true;
                case 315:
                    this.mProgress = f2;
                    return true;
                case 316:
                    this.mTransitionPathRotate = f2;
                    return true;
                default:
                    return super.setValue(i2, f2);
            }
        }
        this.mTransitionPathRotate = f2;
        return true;
    }

    @Override // androidx.constraintlayout.core.motion.key.MotionKey, androidx.constraintlayout.core.motion.utils.TypedValues
    public boolean setValue(int i2, String str) {
        if (i2 == 101) {
            this.mTargetString = str;
            return true;
        }
        if (i2 != 317) {
            return super.setValue(i2, str);
        }
        this.mTransitionEasing = str;
        return true;
    }
}
