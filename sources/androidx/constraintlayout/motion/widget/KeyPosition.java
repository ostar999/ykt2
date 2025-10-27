package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.motion.utils.ViewSpline;
import androidx.constraintlayout.widget.R;
import java.util.HashMap;

/* loaded from: classes.dex */
public class KeyPosition extends KeyPositionBase {
    public static final String DRAWPATH = "drawPath";
    static final int KEY_TYPE = 2;
    static final String NAME = "KeyPosition";
    public static final String PERCENT_HEIGHT = "percentHeight";
    public static final String PERCENT_WIDTH = "percentWidth";
    public static final String PERCENT_X = "percentX";
    public static final String PERCENT_Y = "percentY";
    public static final String SIZE_PERCENT = "sizePercent";
    private static final String TAG = "KeyPosition";
    public static final String TRANSITION_EASING = "transitionEasing";
    public static final int TYPE_CARTESIAN = 0;
    public static final int TYPE_PATH = 1;
    public static final int TYPE_SCREEN = 2;
    String mTransitionEasing = null;
    int mPathMotionArc = Key.UNSET;
    int mDrawPath = 0;
    float mPercentWidth = Float.NaN;
    float mPercentHeight = Float.NaN;
    float mPercentX = Float.NaN;
    float mPercentY = Float.NaN;
    float mAltPercentX = Float.NaN;
    float mAltPercentY = Float.NaN;
    int mPositionType = 0;
    private float mCalculatedPositionX = Float.NaN;
    private float mCalculatedPositionY = Float.NaN;

    public static class Loader {
        private static final int CURVE_FIT = 4;
        private static final int DRAW_PATH = 5;
        private static final int FRAME_POSITION = 2;
        private static final int PATH_MOTION_ARC = 10;
        private static final int PERCENT_HEIGHT = 12;
        private static final int PERCENT_WIDTH = 11;
        private static final int PERCENT_X = 6;
        private static final int PERCENT_Y = 7;
        private static final int SIZE_PERCENT = 8;
        private static final int TARGET_ID = 1;
        private static final int TRANSITION_EASING = 3;
        private static final int TYPE = 9;
        private static SparseIntArray mAttrMap;

        static {
            SparseIntArray sparseIntArray = new SparseIntArray();
            mAttrMap = sparseIntArray;
            sparseIntArray.append(R.styleable.KeyPosition_motionTarget, 1);
            mAttrMap.append(R.styleable.KeyPosition_framePosition, 2);
            mAttrMap.append(R.styleable.KeyPosition_transitionEasing, 3);
            mAttrMap.append(R.styleable.KeyPosition_curveFit, 4);
            mAttrMap.append(R.styleable.KeyPosition_drawPath, 5);
            mAttrMap.append(R.styleable.KeyPosition_percentX, 6);
            mAttrMap.append(R.styleable.KeyPosition_percentY, 7);
            mAttrMap.append(R.styleable.KeyPosition_keyPositionType, 9);
            mAttrMap.append(R.styleable.KeyPosition_sizePercent, 8);
            mAttrMap.append(R.styleable.KeyPosition_percentWidth, 11);
            mAttrMap.append(R.styleable.KeyPosition_percentHeight, 12);
            mAttrMap.append(R.styleable.KeyPosition_pathMotionArc, 10);
        }

        private Loader() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void read(KeyPosition c3, TypedArray a3) {
            int indexCount = a3.getIndexCount();
            for (int i2 = 0; i2 < indexCount; i2++) {
                int index = a3.getIndex(i2);
                switch (mAttrMap.get(index)) {
                    case 1:
                        if (MotionLayout.IS_IN_EDIT_MODE) {
                            int resourceId = a3.getResourceId(index, c3.mTargetId);
                            c3.mTargetId = resourceId;
                            if (resourceId == -1) {
                                c3.mTargetString = a3.getString(index);
                                break;
                            } else {
                                break;
                            }
                        } else if (a3.peekValue(index).type == 3) {
                            c3.mTargetString = a3.getString(index);
                            break;
                        } else {
                            c3.mTargetId = a3.getResourceId(index, c3.mTargetId);
                            break;
                        }
                    case 2:
                        c3.mFramePosition = a3.getInt(index, c3.mFramePosition);
                        break;
                    case 3:
                        if (a3.peekValue(index).type == 3) {
                            c3.mTransitionEasing = a3.getString(index);
                            break;
                        } else {
                            c3.mTransitionEasing = Easing.NAMED_EASING[a3.getInteger(index, 0)];
                            break;
                        }
                    case 4:
                        c3.mCurveFit = a3.getInteger(index, c3.mCurveFit);
                        break;
                    case 5:
                        c3.mDrawPath = a3.getInt(index, c3.mDrawPath);
                        break;
                    case 6:
                        c3.mPercentX = a3.getFloat(index, c3.mPercentX);
                        break;
                    case 7:
                        c3.mPercentY = a3.getFloat(index, c3.mPercentY);
                        break;
                    case 8:
                        float f2 = a3.getFloat(index, c3.mPercentHeight);
                        c3.mPercentWidth = f2;
                        c3.mPercentHeight = f2;
                        break;
                    case 9:
                        c3.mPositionType = a3.getInt(index, c3.mPositionType);
                        break;
                    case 10:
                        c3.mPathMotionArc = a3.getInt(index, c3.mPathMotionArc);
                        break;
                    case 11:
                        c3.mPercentWidth = a3.getFloat(index, c3.mPercentWidth);
                        break;
                    case 12:
                        c3.mPercentHeight = a3.getFloat(index, c3.mPercentHeight);
                        break;
                    default:
                        Log.e("KeyPosition", "unused attribute 0x" + Integer.toHexString(index) + "   " + mAttrMap.get(index));
                        break;
                }
            }
            if (c3.mFramePosition == -1) {
                Log.e("KeyPosition", "no frame position");
            }
        }
    }

    public KeyPosition() {
        this.mType = 2;
    }

    private void calcCartesianPosition(float start_x, float start_y, float end_x, float end_y) {
        float f2 = end_x - start_x;
        float f3 = end_y - start_y;
        float f4 = Float.isNaN(this.mPercentX) ? 0.0f : this.mPercentX;
        float f5 = Float.isNaN(this.mAltPercentY) ? 0.0f : this.mAltPercentY;
        float f6 = Float.isNaN(this.mPercentY) ? 0.0f : this.mPercentY;
        this.mCalculatedPositionX = (int) (start_x + (f4 * f2) + ((Float.isNaN(this.mAltPercentX) ? 0.0f : this.mAltPercentX) * f3));
        this.mCalculatedPositionY = (int) (start_y + (f2 * f5) + (f3 * f6));
    }

    private void calcPathPosition(float start_x, float start_y, float end_x, float end_y) {
        float f2 = end_x - start_x;
        float f3 = end_y - start_y;
        float f4 = this.mPercentX;
        float f5 = this.mPercentY;
        this.mCalculatedPositionX = start_x + (f2 * f4) + ((-f3) * f5);
        this.mCalculatedPositionY = start_y + (f3 * f4) + (f2 * f5);
    }

    private void calcScreenPosition(int layoutWidth, int layoutHeight) {
        float f2 = this.mPercentX;
        float f3 = 0;
        this.mCalculatedPositionX = ((layoutWidth - 0) * f2) + f3;
        this.mCalculatedPositionY = ((layoutHeight - 0) * f2) + f3;
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public void addValues(HashMap<String, ViewSpline> splines) {
    }

    @Override // androidx.constraintlayout.motion.widget.KeyPositionBase
    public void calcPosition(int layoutWidth, int layoutHeight, float start_x, float start_y, float end_x, float end_y) {
        int i2 = this.mPositionType;
        if (i2 == 1) {
            calcPathPosition(start_x, start_y, end_x, end_y);
        } else if (i2 != 2) {
            calcCartesianPosition(start_x, start_y, end_x, end_y);
        } else {
            calcScreenPosition(layoutWidth, layoutHeight);
        }
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public Key copy(Key src) {
        super.copy(src);
        KeyPosition keyPosition = (KeyPosition) src;
        this.mTransitionEasing = keyPosition.mTransitionEasing;
        this.mPathMotionArc = keyPosition.mPathMotionArc;
        this.mDrawPath = keyPosition.mDrawPath;
        this.mPercentWidth = keyPosition.mPercentWidth;
        this.mPercentHeight = Float.NaN;
        this.mPercentX = keyPosition.mPercentX;
        this.mPercentY = keyPosition.mPercentY;
        this.mAltPercentX = keyPosition.mAltPercentX;
        this.mAltPercentY = keyPosition.mAltPercentY;
        this.mCalculatedPositionX = keyPosition.mCalculatedPositionX;
        this.mCalculatedPositionY = keyPosition.mCalculatedPositionY;
        return this;
    }

    @Override // androidx.constraintlayout.motion.widget.KeyPositionBase
    public float getPositionX() {
        return this.mCalculatedPositionX;
    }

    @Override // androidx.constraintlayout.motion.widget.KeyPositionBase
    public float getPositionY() {
        return this.mCalculatedPositionY;
    }

    @Override // androidx.constraintlayout.motion.widget.KeyPositionBase
    public boolean intersects(int layoutWidth, int layoutHeight, RectF start, RectF end, float x2, float y2) {
        calcPosition(layoutWidth, layoutHeight, start.centerX(), start.centerY(), end.centerX(), end.centerY());
        return Math.abs(x2 - this.mCalculatedPositionX) < 20.0f && Math.abs(y2 - this.mCalculatedPositionY) < 20.0f;
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public void load(Context context, AttributeSet attrs) {
        Loader.read(this, context.obtainStyledAttributes(attrs, R.styleable.KeyPosition));
    }

    @Override // androidx.constraintlayout.motion.widget.KeyPositionBase
    public void positionAttributes(View view, RectF start, RectF end, float x2, float y2, String[] attribute, float[] value) {
        int i2 = this.mPositionType;
        if (i2 == 1) {
            positionPathAttributes(start, end, x2, y2, attribute, value);
        } else if (i2 != 2) {
            positionCartAttributes(start, end, x2, y2, attribute, value);
        } else {
            positionScreenAttributes(view, start, end, x2, y2, attribute, value);
        }
    }

    public void positionCartAttributes(RectF start, RectF end, float x2, float y2, String[] attribute, float[] value) {
        float fCenterX = start.centerX();
        float fCenterY = start.centerY();
        float fCenterX2 = end.centerX() - fCenterX;
        float fCenterY2 = end.centerY() - fCenterY;
        String str = attribute[0];
        if (str == null) {
            attribute[0] = "percentX";
            value[0] = (x2 - fCenterX) / fCenterX2;
            attribute[1] = "percentY";
            value[1] = (y2 - fCenterY) / fCenterY2;
            return;
        }
        if ("percentX".equals(str)) {
            value[0] = (x2 - fCenterX) / fCenterX2;
            value[1] = (y2 - fCenterY) / fCenterY2;
        } else {
            value[1] = (x2 - fCenterX) / fCenterX2;
            value[0] = (y2 - fCenterY) / fCenterY2;
        }
    }

    public void positionPathAttributes(RectF start, RectF end, float x2, float y2, String[] attribute, float[] value) {
        float fCenterX = start.centerX();
        float fCenterY = start.centerY();
        float fCenterX2 = end.centerX() - fCenterX;
        float fCenterY2 = end.centerY() - fCenterY;
        float fHypot = (float) Math.hypot(fCenterX2, fCenterY2);
        if (fHypot < 1.0E-4d) {
            System.out.println("distance ~ 0");
            value[0] = 0.0f;
            value[1] = 0.0f;
            return;
        }
        float f2 = fCenterX2 / fHypot;
        float f3 = fCenterY2 / fHypot;
        float f4 = y2 - fCenterY;
        float f5 = x2 - fCenterX;
        float f6 = ((f2 * f4) - (f5 * f3)) / fHypot;
        float f7 = ((f2 * f5) + (f3 * f4)) / fHypot;
        String str = attribute[0];
        if (str != null) {
            if ("percentX".equals(str)) {
                value[0] = f7;
                value[1] = f6;
                return;
            }
            return;
        }
        attribute[0] = "percentX";
        attribute[1] = "percentY";
        value[0] = f7;
        value[1] = f6;
    }

    public void positionScreenAttributes(View view, RectF start, RectF end, float x2, float y2, String[] attribute, float[] value) {
        start.centerX();
        start.centerY();
        end.centerX();
        end.centerY();
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        int width = viewGroup.getWidth();
        int height = viewGroup.getHeight();
        String str = attribute[0];
        if (str == null) {
            attribute[0] = "percentX";
            value[0] = x2 / width;
            attribute[1] = "percentY";
            value[1] = y2 / height;
            return;
        }
        if ("percentX".equals(str)) {
            value[0] = x2 / width;
            value[1] = y2 / height;
        } else {
            value[1] = x2 / width;
            value[0] = y2 / height;
        }
    }

    public void setType(int type) {
        this.mPositionType = type;
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public void setValue(String tag, Object value) {
        tag.hashCode();
        switch (tag) {
            case "transitionEasing":
                this.mTransitionEasing = value.toString();
                break;
            case "percentWidth":
                this.mPercentWidth = toFloat(value);
                break;
            case "percentHeight":
                this.mPercentHeight = toFloat(value);
                break;
            case "drawPath":
                this.mDrawPath = toInt(value);
                break;
            case "sizePercent":
                float f2 = toFloat(value);
                this.mPercentWidth = f2;
                this.mPercentHeight = f2;
                break;
            case "percentX":
                this.mPercentX = toFloat(value);
                break;
            case "percentY":
                this.mPercentY = toFloat(value);
                break;
        }
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    /* renamed from: clone */
    public Key mo5clone() {
        return new KeyPosition().copy(this);
    }
}
