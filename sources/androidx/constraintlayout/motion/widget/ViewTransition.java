package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.Xml;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.core.motion.utils.KeyCache;
import androidx.constraintlayout.motion.widget.MotionScene;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.R;
import java.util.ArrayList;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;

/* loaded from: classes.dex */
public class ViewTransition {
    static final int ANTICIPATE = 6;
    static final int BOUNCE = 4;
    public static final String CONSTRAINT_OVERRIDE = "ConstraintOverride";
    public static final String CUSTOM_ATTRIBUTE = "CustomAttribute";
    public static final String CUSTOM_METHOD = "CustomMethod";
    static final int EASE_IN = 1;
    static final int EASE_IN_OUT = 0;
    static final int EASE_OUT = 2;
    private static final int INTERPOLATOR_REFERENCE_ID = -2;
    public static final String KEY_FRAME_SET_TAG = "KeyFrameSet";
    static final int LINEAR = 3;
    public static final int ONSTATE_ACTION_DOWN = 1;
    public static final int ONSTATE_ACTION_DOWN_UP = 3;
    public static final int ONSTATE_ACTION_UP = 2;
    public static final int ONSTATE_SHARED_VALUE_SET = 4;
    public static final int ONSTATE_SHARED_VALUE_UNSET = 5;
    static final int OVERSHOOT = 5;
    private static final int SPLINE_STRING = -1;
    private static String TAG = "ViewTransition";
    private static final int UNSET = -1;
    static final int VIEWTRANSITIONMODE_ALLSTATES = 1;
    static final int VIEWTRANSITIONMODE_CURRENTSTATE = 0;
    static final int VIEWTRANSITIONMODE_NOSTATE = 2;
    public static final String VIEW_TRANSITION_TAG = "ViewTransition";
    ConstraintSet.Constraint mConstraintDelta;
    Context mContext;
    private int mId;
    KeyFrames mKeyFrames;
    private int mTargetId;
    private String mTargetString;
    int mViewTransitionMode;
    ConstraintSet set;
    private int mOnStateTransition = -1;
    private boolean mDisabled = false;
    private int mPathMotionArc = 0;
    private int mDuration = -1;
    private int mUpDuration = -1;
    private int mDefaultInterpolator = 0;
    private String mDefaultInterpolatorString = null;
    private int mDefaultInterpolatorID = -1;
    private int mSetsTag = -1;
    private int mClearsTag = -1;
    private int mIfTagSet = -1;
    private int mIfTagNotSet = -1;
    private int mSharedValueTarget = -1;
    private int mSharedValueID = -1;
    private int mSharedValueCurrent = -1;

    public static class Animate {
        boolean hold_at_100;
        private final int mClearsTag;
        float mDpositionDt;
        int mDuration;
        Interpolator mInterpolator;
        long mLastRender;
        MotionController mMC;
        float mPosition;
        private final int mSetsTag;
        long mStart;
        int mUpDuration;
        ViewTransitionController mVtController;
        KeyCache mCache = new KeyCache();
        boolean reverse = false;
        Rect mTempRec = new Rect();

        public Animate(ViewTransitionController controller, MotionController motionController, int duration, int upDuration, int mode, Interpolator interpolator, int setTag, int clearTag) {
            this.hold_at_100 = false;
            this.mVtController = controller;
            this.mMC = motionController;
            this.mDuration = duration;
            this.mUpDuration = upDuration;
            long jNanoTime = System.nanoTime();
            this.mStart = jNanoTime;
            this.mLastRender = jNanoTime;
            this.mVtController.addAnimation(this);
            this.mInterpolator = interpolator;
            this.mSetsTag = setTag;
            this.mClearsTag = clearTag;
            if (mode == 3) {
                this.hold_at_100 = true;
            }
            this.mDpositionDt = duration == 0 ? Float.MAX_VALUE : 1.0f / duration;
            mutate();
        }

        public void mutate() {
            if (this.reverse) {
                mutateReverse();
            } else {
                mutateForward();
            }
        }

        public void mutateForward() {
            long jNanoTime = System.nanoTime();
            long j2 = jNanoTime - this.mLastRender;
            this.mLastRender = jNanoTime;
            float f2 = this.mPosition + (((float) (j2 * 1.0E-6d)) * this.mDpositionDt);
            this.mPosition = f2;
            if (f2 >= 1.0f) {
                this.mPosition = 1.0f;
            }
            Interpolator interpolator = this.mInterpolator;
            float interpolation = interpolator == null ? this.mPosition : interpolator.getInterpolation(this.mPosition);
            MotionController motionController = this.mMC;
            boolean zInterpolate = motionController.interpolate(motionController.mView, interpolation, jNanoTime, this.mCache);
            if (this.mPosition >= 1.0f) {
                if (this.mSetsTag != -1) {
                    this.mMC.getView().setTag(this.mSetsTag, Long.valueOf(System.nanoTime()));
                }
                if (this.mClearsTag != -1) {
                    this.mMC.getView().setTag(this.mClearsTag, null);
                }
                if (!this.hold_at_100) {
                    this.mVtController.removeAnimation(this);
                }
            }
            if (this.mPosition < 1.0f || zInterpolate) {
                this.mVtController.invalidate();
            }
        }

        public void mutateReverse() {
            long jNanoTime = System.nanoTime();
            long j2 = jNanoTime - this.mLastRender;
            this.mLastRender = jNanoTime;
            float f2 = this.mPosition - (((float) (j2 * 1.0E-6d)) * this.mDpositionDt);
            this.mPosition = f2;
            if (f2 < 0.0f) {
                this.mPosition = 0.0f;
            }
            Interpolator interpolator = this.mInterpolator;
            float interpolation = interpolator == null ? this.mPosition : interpolator.getInterpolation(this.mPosition);
            MotionController motionController = this.mMC;
            boolean zInterpolate = motionController.interpolate(motionController.mView, interpolation, jNanoTime, this.mCache);
            if (this.mPosition <= 0.0f) {
                if (this.mSetsTag != -1) {
                    this.mMC.getView().setTag(this.mSetsTag, Long.valueOf(System.nanoTime()));
                }
                if (this.mClearsTag != -1) {
                    this.mMC.getView().setTag(this.mClearsTag, null);
                }
                this.mVtController.removeAnimation(this);
            }
            if (this.mPosition > 0.0f || zInterpolate) {
                this.mVtController.invalidate();
            }
        }

        public void reactTo(int action, float x2, float y2) {
            if (action == 1) {
                if (this.reverse) {
                    return;
                }
                reverse(true);
            } else {
                if (action != 2) {
                    return;
                }
                this.mMC.getView().getHitRect(this.mTempRec);
                if (this.mTempRec.contains((int) x2, (int) y2) || this.reverse) {
                    return;
                }
                reverse(true);
            }
        }

        public void reverse(boolean dir) {
            int i2;
            this.reverse = dir;
            if (dir && (i2 = this.mUpDuration) != -1) {
                this.mDpositionDt = i2 == 0 ? Float.MAX_VALUE : 1.0f / i2;
            }
            this.mVtController.invalidate();
            this.mLastRender = System.nanoTime();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x007f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ViewTransition(android.content.Context r10, org.xmlpull.v1.XmlPullParser r11) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 258
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.motion.widget.ViewTransition.<init>(android.content.Context, org.xmlpull.v1.XmlPullParser):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$applyTransition$0(View[] viewArr) {
        if (this.mSetsTag != -1) {
            for (View view : viewArr) {
                view.setTag(this.mSetsTag, Long.valueOf(System.nanoTime()));
            }
        }
        if (this.mClearsTag != -1) {
            for (View view2 : viewArr) {
                view2.setTag(this.mClearsTag, null);
            }
        }
    }

    private void parseViewTransitionTags(Context context, XmlPullParser parser) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(Xml.asAttributeSet(parser), R.styleable.ViewTransition);
        int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
        for (int i2 = 0; i2 < indexCount; i2++) {
            int index = typedArrayObtainStyledAttributes.getIndex(i2);
            if (index == R.styleable.ViewTransition_android_id) {
                this.mId = typedArrayObtainStyledAttributes.getResourceId(index, this.mId);
            } else if (index == R.styleable.ViewTransition_motionTarget) {
                if (MotionLayout.IS_IN_EDIT_MODE) {
                    int resourceId = typedArrayObtainStyledAttributes.getResourceId(index, this.mTargetId);
                    this.mTargetId = resourceId;
                    if (resourceId == -1) {
                        this.mTargetString = typedArrayObtainStyledAttributes.getString(index);
                    }
                } else if (typedArrayObtainStyledAttributes.peekValue(index).type == 3) {
                    this.mTargetString = typedArrayObtainStyledAttributes.getString(index);
                } else {
                    this.mTargetId = typedArrayObtainStyledAttributes.getResourceId(index, this.mTargetId);
                }
            } else if (index == R.styleable.ViewTransition_onStateTransition) {
                this.mOnStateTransition = typedArrayObtainStyledAttributes.getInt(index, this.mOnStateTransition);
            } else if (index == R.styleable.ViewTransition_transitionDisable) {
                this.mDisabled = typedArrayObtainStyledAttributes.getBoolean(index, this.mDisabled);
            } else if (index == R.styleable.ViewTransition_pathMotionArc) {
                this.mPathMotionArc = typedArrayObtainStyledAttributes.getInt(index, this.mPathMotionArc);
            } else if (index == R.styleable.ViewTransition_duration) {
                this.mDuration = typedArrayObtainStyledAttributes.getInt(index, this.mDuration);
            } else if (index == R.styleable.ViewTransition_upDuration) {
                this.mUpDuration = typedArrayObtainStyledAttributes.getInt(index, this.mUpDuration);
            } else if (index == R.styleable.ViewTransition_viewTransitionMode) {
                this.mViewTransitionMode = typedArrayObtainStyledAttributes.getInt(index, this.mViewTransitionMode);
            } else if (index == R.styleable.ViewTransition_motionInterpolator) {
                int i3 = typedArrayObtainStyledAttributes.peekValue(index).type;
                if (i3 == 1) {
                    int resourceId2 = typedArrayObtainStyledAttributes.getResourceId(index, -1);
                    this.mDefaultInterpolatorID = resourceId2;
                    if (resourceId2 != -1) {
                        this.mDefaultInterpolator = -2;
                    }
                } else if (i3 == 3) {
                    String string = typedArrayObtainStyledAttributes.getString(index);
                    this.mDefaultInterpolatorString = string;
                    if (string == null || string.indexOf("/") <= 0) {
                        this.mDefaultInterpolator = -1;
                    } else {
                        this.mDefaultInterpolatorID = typedArrayObtainStyledAttributes.getResourceId(index, -1);
                        this.mDefaultInterpolator = -2;
                    }
                } else {
                    this.mDefaultInterpolator = typedArrayObtainStyledAttributes.getInteger(index, this.mDefaultInterpolator);
                }
            } else if (index == R.styleable.ViewTransition_setsTag) {
                this.mSetsTag = typedArrayObtainStyledAttributes.getResourceId(index, this.mSetsTag);
            } else if (index == R.styleable.ViewTransition_clearsTag) {
                this.mClearsTag = typedArrayObtainStyledAttributes.getResourceId(index, this.mClearsTag);
            } else if (index == R.styleable.ViewTransition_ifTagSet) {
                this.mIfTagSet = typedArrayObtainStyledAttributes.getResourceId(index, this.mIfTagSet);
            } else if (index == R.styleable.ViewTransition_ifTagNotSet) {
                this.mIfTagNotSet = typedArrayObtainStyledAttributes.getResourceId(index, this.mIfTagNotSet);
            } else if (index == R.styleable.ViewTransition_SharedValueId) {
                this.mSharedValueID = typedArrayObtainStyledAttributes.getResourceId(index, this.mSharedValueID);
            } else if (index == R.styleable.ViewTransition_SharedValue) {
                this.mSharedValueTarget = typedArrayObtainStyledAttributes.getInteger(index, this.mSharedValueTarget);
            }
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    private void updateTransition(MotionScene.Transition transition, View view) {
        int i2 = this.mDuration;
        if (i2 != -1) {
            transition.setDuration(i2);
        }
        transition.setPathMotionArc(this.mPathMotionArc);
        transition.setInterpolatorInfo(this.mDefaultInterpolator, this.mDefaultInterpolatorString, this.mDefaultInterpolatorID);
        int id = view.getId();
        KeyFrames keyFrames = this.mKeyFrames;
        if (keyFrames != null) {
            ArrayList<Key> keyFramesForView = keyFrames.getKeyFramesForView(-1);
            KeyFrames keyFrames2 = new KeyFrames();
            Iterator<Key> it = keyFramesForView.iterator();
            while (it.hasNext()) {
                keyFrames2.addKey(it.next().mo5clone().setViewId(id));
            }
            transition.addKeyFrame(keyFrames2);
        }
    }

    public void applyIndependentTransition(ViewTransitionController controller, MotionLayout motionLayout, View view) {
        MotionController motionController = new MotionController(view);
        motionController.setBothStates(view);
        this.mKeyFrames.addAllFrames(motionController);
        motionController.setup(motionLayout.getWidth(), motionLayout.getHeight(), this.mDuration, System.nanoTime());
        new Animate(controller, motionController, this.mDuration, this.mUpDuration, this.mOnStateTransition, getInterpolator(motionLayout.getContext()), this.mSetsTag, this.mClearsTag);
    }

    public void applyTransition(ViewTransitionController controller, MotionLayout layout, int fromId, ConstraintSet current, final View... views) {
        if (this.mDisabled) {
            return;
        }
        int i2 = this.mViewTransitionMode;
        if (i2 == 2) {
            applyIndependentTransition(controller, layout, views[0]);
            return;
        }
        if (i2 == 1) {
            for (int i3 : layout.getConstraintSetIds()) {
                if (i3 != fromId) {
                    ConstraintSet constraintSet = layout.getConstraintSet(i3);
                    for (View view : views) {
                        ConstraintSet.Constraint constraint = constraintSet.getConstraint(view.getId());
                        ConstraintSet.Constraint constraint2 = this.mConstraintDelta;
                        if (constraint2 != null) {
                            constraint2.applyDelta(constraint);
                            constraint.mCustomConstraints.putAll(this.mConstraintDelta.mCustomConstraints);
                        }
                    }
                }
            }
        }
        ConstraintSet constraintSet2 = new ConstraintSet();
        constraintSet2.clone(current);
        for (View view2 : views) {
            ConstraintSet.Constraint constraint3 = constraintSet2.getConstraint(view2.getId());
            ConstraintSet.Constraint constraint4 = this.mConstraintDelta;
            if (constraint4 != null) {
                constraint4.applyDelta(constraint3);
                constraint3.mCustomConstraints.putAll(this.mConstraintDelta.mCustomConstraints);
            }
        }
        layout.updateState(fromId, constraintSet2);
        int i4 = R.id.view_transition;
        layout.updateState(i4, current);
        layout.setState(i4, -1, -1);
        MotionScene.Transition transition = new MotionScene.Transition(-1, layout.mScene, i4, fromId);
        for (View view3 : views) {
            updateTransition(transition, view3);
        }
        layout.setTransition(transition);
        layout.transitionToEnd(new Runnable() { // from class: androidx.constraintlayout.motion.widget.a
            @Override // java.lang.Runnable
            public final void run() {
                this.f1790c.lambda$applyTransition$0(views);
            }
        });
    }

    public boolean checkTags(View view) {
        int i2 = this.mIfTagSet;
        boolean z2 = i2 == -1 || view.getTag(i2) != null;
        int i3 = this.mIfTagNotSet;
        return z2 && (i3 == -1 || view.getTag(i3) == null);
    }

    public int getId() {
        return this.mId;
    }

    public Interpolator getInterpolator(Context context) {
        int i2 = this.mDefaultInterpolator;
        if (i2 == -2) {
            return AnimationUtils.loadInterpolator(context, this.mDefaultInterpolatorID);
        }
        if (i2 == -1) {
            final Easing interpolator = Easing.getInterpolator(this.mDefaultInterpolatorString);
            return new Interpolator(this) { // from class: androidx.constraintlayout.motion.widget.ViewTransition.1
                @Override // android.animation.TimeInterpolator
                public float getInterpolation(float v2) {
                    return (float) interpolator.get(v2);
                }
            };
        }
        if (i2 == 0) {
            return new AccelerateDecelerateInterpolator();
        }
        if (i2 == 1) {
            return new AccelerateInterpolator();
        }
        if (i2 == 2) {
            return new DecelerateInterpolator();
        }
        if (i2 == 4) {
            return new BounceInterpolator();
        }
        if (i2 == 5) {
            return new OvershootInterpolator();
        }
        if (i2 != 6) {
            return null;
        }
        return new AnticipateInterpolator();
    }

    public int getSharedValue() {
        return this.mSharedValueTarget;
    }

    public int getSharedValueCurrent() {
        return this.mSharedValueCurrent;
    }

    public int getSharedValueID() {
        return this.mSharedValueID;
    }

    public int getStateTransition() {
        return this.mOnStateTransition;
    }

    public boolean isEnabled() {
        return !this.mDisabled;
    }

    public boolean matchesView(View view) {
        String str;
        if (view == null) {
            return false;
        }
        if ((this.mTargetId == -1 && this.mTargetString == null) || !checkTags(view)) {
            return false;
        }
        if (view.getId() == this.mTargetId) {
            return true;
        }
        return this.mTargetString != null && (view.getLayoutParams() instanceof ConstraintLayout.LayoutParams) && (str = ((ConstraintLayout.LayoutParams) view.getLayoutParams()).constraintTag) != null && str.matches(this.mTargetString);
    }

    public void setEnabled(boolean enable) {
        this.mDisabled = !enable;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public void setSharedValue(int sharedValue) {
        this.mSharedValueTarget = sharedValue;
    }

    public void setSharedValueCurrent(int sharedValueCurrent) {
        this.mSharedValueCurrent = sharedValueCurrent;
    }

    public void setSharedValueID(int sharedValueID) {
        this.mSharedValueID = sharedValueID;
    }

    public void setStateTransition(int stateTransition) {
        this.mOnStateTransition = stateTransition;
    }

    public boolean supports(int action) {
        int i2 = this.mOnStateTransition;
        return i2 == 1 ? action == 0 : i2 == 2 ? action == 1 : i2 == 3 && action == 0;
    }

    public String toString() {
        return "ViewTransition(" + Debug.getName(this.mContext, this.mId) + ")";
    }
}
