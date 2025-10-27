package androidx.constraintlayout.core.state;

import androidx.constraintlayout.core.motion.Motion;
import androidx.constraintlayout.core.motion.MotionWidget;
import androidx.constraintlayout.core.motion.key.MotionKeyAttributes;
import androidx.constraintlayout.core.motion.key.MotionKeyCycle;
import androidx.constraintlayout.core.motion.key.MotionKeyPosition;
import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.core.motion.utils.KeyCache;
import androidx.constraintlayout.core.motion.utils.TypedBundle;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import com.mobile.auth.BuildConfig;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/* loaded from: classes.dex */
public class Transition implements TypedValues {
    static final int ANTICIPATE = 6;
    static final int BOUNCE = 4;
    static final int EASE_IN = 1;
    static final int EASE_IN_OUT = 0;
    static final int EASE_OUT = 2;
    public static final int END = 1;
    public static final int INTERPOLATED = 2;
    private static final int INTERPOLATOR_REFERENCE_ID = -2;
    static final int LINEAR = 3;
    static final int OVERSHOOT = 5;
    private static final int SPLINE_STRING = -1;
    public static final int START = 0;
    HashMap<Integer, HashMap<String, KeyPosition>> keyPositions = new HashMap<>();
    private HashMap<String, WidgetState> state = new HashMap<>();
    TypedBundle mBundle = new TypedBundle();
    private int mDefaultInterpolator = 0;
    private String mDefaultInterpolatorString = null;
    private Easing mEasing = null;
    private int mAutoTransition = 0;
    private int mDuration = 400;
    private float mStagger = 0.0f;

    public static class KeyPosition {
        int frame;
        String target;
        int type;

        /* renamed from: x, reason: collision with root package name */
        float f1776x;

        /* renamed from: y, reason: collision with root package name */
        float f1777y;

        public KeyPosition(String str, int i2, int i3, float f2, float f3) {
            this.target = str;
            this.frame = i2;
            this.type = i3;
            this.f1776x = f2;
            this.f1777y = f3;
        }
    }

    public static class WidgetState {
        Motion motionControl;
        KeyCache myKeyCache = new KeyCache();
        int myParentHeight = -1;
        int myParentWidth = -1;
        WidgetFrame start = new WidgetFrame();
        WidgetFrame end = new WidgetFrame();
        WidgetFrame interpolated = new WidgetFrame();
        MotionWidget motionWidgetStart = new MotionWidget(this.start);
        MotionWidget motionWidgetEnd = new MotionWidget(this.end);
        MotionWidget motionWidgetInterpolated = new MotionWidget(this.interpolated);

        public WidgetState() {
            Motion motion = new Motion(this.motionWidgetStart);
            this.motionControl = motion;
            motion.setStart(this.motionWidgetStart);
            this.motionControl.setEnd(this.motionWidgetEnd);
        }

        public WidgetFrame getFrame(int i2) {
            return i2 == 0 ? this.start : i2 == 1 ? this.end : this.interpolated;
        }

        public void interpolate(int i2, int i3, float f2, Transition transition) {
            this.myParentHeight = i3;
            this.myParentWidth = i2;
            this.motionControl.setup(i2, i3, 1.0f, System.nanoTime());
            WidgetFrame.interpolate(i2, i3, this.interpolated, this.start, this.end, transition, f2);
            this.interpolated.interpolatedPos = f2;
            this.motionControl.interpolate(this.motionWidgetInterpolated, f2, System.nanoTime(), this.myKeyCache);
        }

        public void setKeyAttribute(TypedBundle typedBundle) {
            MotionKeyAttributes motionKeyAttributes = new MotionKeyAttributes();
            typedBundle.applyDelta(motionKeyAttributes);
            this.motionControl.addKey(motionKeyAttributes);
        }

        public void setKeyCycle(TypedBundle typedBundle) {
            MotionKeyCycle motionKeyCycle = new MotionKeyCycle();
            typedBundle.applyDelta(motionKeyCycle);
            this.motionControl.addKey(motionKeyCycle);
        }

        public void setKeyPosition(TypedBundle typedBundle) {
            MotionKeyPosition motionKeyPosition = new MotionKeyPosition();
            typedBundle.applyDelta(motionKeyPosition);
            this.motionControl.addKey(motionKeyPosition);
        }

        public void update(ConstraintWidget constraintWidget, int i2) {
            if (i2 == 0) {
                this.start.update(constraintWidget);
                this.motionControl.setStart(this.motionWidgetStart);
            } else if (i2 == 1) {
                this.end.update(constraintWidget);
                this.motionControl.setEnd(this.motionWidgetEnd);
            }
            this.myParentWidth = -1;
        }
    }

    public static Interpolator getInterpolator(int i2, final String str) {
        switch (i2) {
            case -1:
                return new Interpolator() { // from class: androidx.constraintlayout.core.state.a
                    @Override // androidx.constraintlayout.core.state.Interpolator
                    public final float getInterpolation(float f2) {
                        return Transition.lambda$getInterpolator$0(str, f2);
                    }
                };
            case 0:
                return new Interpolator() { // from class: androidx.constraintlayout.core.state.b
                    @Override // androidx.constraintlayout.core.state.Interpolator
                    public final float getInterpolation(float f2) {
                        return Transition.lambda$getInterpolator$1(f2);
                    }
                };
            case 1:
                return new Interpolator() { // from class: androidx.constraintlayout.core.state.c
                    @Override // androidx.constraintlayout.core.state.Interpolator
                    public final float getInterpolation(float f2) {
                        return Transition.lambda$getInterpolator$2(f2);
                    }
                };
            case 2:
                return new Interpolator() { // from class: androidx.constraintlayout.core.state.d
                    @Override // androidx.constraintlayout.core.state.Interpolator
                    public final float getInterpolation(float f2) {
                        return Transition.lambda$getInterpolator$3(f2);
                    }
                };
            case 3:
                return new Interpolator() { // from class: androidx.constraintlayout.core.state.e
                    @Override // androidx.constraintlayout.core.state.Interpolator
                    public final float getInterpolation(float f2) {
                        return Transition.lambda$getInterpolator$4(f2);
                    }
                };
            case 4:
                return new Interpolator() { // from class: androidx.constraintlayout.core.state.h
                    @Override // androidx.constraintlayout.core.state.Interpolator
                    public final float getInterpolation(float f2) {
                        return Transition.lambda$getInterpolator$7(f2);
                    }
                };
            case 5:
                return new Interpolator() { // from class: androidx.constraintlayout.core.state.g
                    @Override // androidx.constraintlayout.core.state.Interpolator
                    public final float getInterpolation(float f2) {
                        return Transition.lambda$getInterpolator$6(f2);
                    }
                };
            case 6:
                return new Interpolator() { // from class: androidx.constraintlayout.core.state.f
                    @Override // androidx.constraintlayout.core.state.Interpolator
                    public final float getInterpolation(float f2) {
                        return Transition.lambda$getInterpolator$5(f2);
                    }
                };
            default:
                return null;
        }
    }

    private WidgetState getWidgetState(String str) {
        return this.state.get(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ float lambda$getInterpolator$0(String str, float f2) {
        return (float) Easing.getInterpolator(str).get(f2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ float lambda$getInterpolator$1(float f2) {
        return (float) Easing.getInterpolator(BuildConfig.FLAVOR_feat).get(f2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ float lambda$getInterpolator$2(float f2) {
        return (float) Easing.getInterpolator("accelerate").get(f2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ float lambda$getInterpolator$3(float f2) {
        return (float) Easing.getInterpolator("decelerate").get(f2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ float lambda$getInterpolator$4(float f2) {
        return (float) Easing.getInterpolator("linear").get(f2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ float lambda$getInterpolator$5(float f2) {
        return (float) Easing.getInterpolator("anticipate").get(f2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ float lambda$getInterpolator$6(float f2) {
        return (float) Easing.getInterpolator("overshoot").get(f2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ float lambda$getInterpolator$7(float f2) {
        return (float) Easing.getInterpolator("spline(0.0, 0.2, 0.4, 0.6, 0.8 ,1.0, 0.8, 1.0, 0.9, 1.0)").get(f2);
    }

    public void addCustomColor(int i2, String str, String str2, int i3) {
        getWidgetState(str, null, i2).getFrame(i2).addCustomColor(str2, i3);
    }

    public void addCustomFloat(int i2, String str, String str2, float f2) {
        getWidgetState(str, null, i2).getFrame(i2).addCustomFloat(str2, f2);
    }

    public void addKeyAttribute(String str, TypedBundle typedBundle) {
        getWidgetState(str, null, 0).setKeyAttribute(typedBundle);
    }

    public void addKeyCycle(String str, TypedBundle typedBundle) {
        getWidgetState(str, null, 0).setKeyCycle(typedBundle);
    }

    public void addKeyPosition(String str, TypedBundle typedBundle) {
        getWidgetState(str, null, 0).setKeyPosition(typedBundle);
    }

    public void clear() {
        this.state.clear();
    }

    public boolean contains(String str) {
        return this.state.containsKey(str);
    }

    public void fillKeyPositions(WidgetFrame widgetFrame, float[] fArr, float[] fArr2, float[] fArr3) {
        KeyPosition keyPosition;
        int i2 = 0;
        for (int i3 = 0; i3 <= 100; i3++) {
            HashMap<String, KeyPosition> map = this.keyPositions.get(Integer.valueOf(i3));
            if (map != null && (keyPosition = map.get(widgetFrame.widget.stringId)) != null) {
                fArr[i2] = keyPosition.f1776x;
                fArr2[i2] = keyPosition.f1777y;
                fArr3[i2] = keyPosition.frame;
                i2++;
            }
        }
    }

    public KeyPosition findNextPosition(String str, int i2) {
        KeyPosition keyPosition;
        while (i2 <= 100) {
            HashMap<String, KeyPosition> map = this.keyPositions.get(Integer.valueOf(i2));
            if (map != null && (keyPosition = map.get(str)) != null) {
                return keyPosition;
            }
            i2++;
        }
        return null;
    }

    public KeyPosition findPreviousPosition(String str, int i2) {
        KeyPosition keyPosition;
        while (i2 >= 0) {
            HashMap<String, KeyPosition> map = this.keyPositions.get(Integer.valueOf(i2));
            if (map != null && (keyPosition = map.get(str)) != null) {
                return keyPosition;
            }
            i2--;
        }
        return null;
    }

    public int getAutoTransition() {
        return this.mAutoTransition;
    }

    public WidgetFrame getEnd(String str) {
        WidgetState widgetState = this.state.get(str);
        if (widgetState == null) {
            return null;
        }
        return widgetState.end;
    }

    @Override // androidx.constraintlayout.core.motion.utils.TypedValues
    public int getId(String str) {
        return 0;
    }

    public WidgetFrame getInterpolated(String str) {
        WidgetState widgetState = this.state.get(str);
        if (widgetState == null) {
            return null;
        }
        return widgetState.interpolated;
    }

    public int getKeyFrames(String str, float[] fArr, int[] iArr, int[] iArr2) {
        return this.state.get(str).motionControl.buildKeyFrames(fArr, iArr, iArr2);
    }

    public Motion getMotion(String str) {
        return getWidgetState(str, null, 0).motionControl;
    }

    public int getNumberKeyPositions(WidgetFrame widgetFrame) {
        int i2 = 0;
        for (int i3 = 0; i3 <= 100; i3++) {
            HashMap<String, KeyPosition> map = this.keyPositions.get(Integer.valueOf(i3));
            if (map != null && map.get(widgetFrame.widget.stringId) != null) {
                i2++;
            }
        }
        return i2;
    }

    public float[] getPath(String str) {
        float[] fArr = new float[124];
        this.state.get(str).motionControl.buildPath(fArr, 62);
        return fArr;
    }

    public WidgetFrame getStart(String str) {
        WidgetState widgetState = this.state.get(str);
        if (widgetState == null) {
            return null;
        }
        return widgetState.start;
    }

    public boolean hasPositionKeyframes() {
        return this.keyPositions.size() > 0;
    }

    public void interpolate(int i2, int i3, float f2) {
        Easing easing = this.mEasing;
        if (easing != null) {
            f2 = (float) easing.get(f2);
        }
        Iterator<String> it = this.state.keySet().iterator();
        while (it.hasNext()) {
            this.state.get(it.next()).interpolate(i2, i3, f2, this);
        }
    }

    public boolean isEmpty() {
        return this.state.isEmpty();
    }

    public void setTransitionProperties(TypedBundle typedBundle) {
        typedBundle.applyDelta(this.mBundle);
        typedBundle.applyDelta(this);
    }

    @Override // androidx.constraintlayout.core.motion.utils.TypedValues
    public boolean setValue(int i2, float f2) {
        if (i2 != 706) {
            return false;
        }
        this.mStagger = f2;
        return false;
    }

    @Override // androidx.constraintlayout.core.motion.utils.TypedValues
    public boolean setValue(int i2, int i3) {
        return false;
    }

    @Override // androidx.constraintlayout.core.motion.utils.TypedValues
    public boolean setValue(int i2, boolean z2) {
        return false;
    }

    public void updateFrom(ConstraintWidgetContainer constraintWidgetContainer, int i2) {
        ArrayList<ConstraintWidget> children = constraintWidgetContainer.getChildren();
        int size = children.size();
        for (int i3 = 0; i3 < size; i3++) {
            ConstraintWidget constraintWidget = children.get(i3);
            getWidgetState(constraintWidget.stringId, null, i2).update(constraintWidget, i2);
        }
    }

    private WidgetState getWidgetState(String str, ConstraintWidget constraintWidget, int i2) {
        WidgetState widgetState = this.state.get(str);
        if (widgetState == null) {
            widgetState = new WidgetState();
            this.mBundle.applyDelta(widgetState.motionControl);
            this.state.put(str, widgetState);
            if (constraintWidget != null) {
                widgetState.update(constraintWidget, i2);
            }
        }
        return widgetState;
    }

    public void addKeyPosition(String str, int i2, int i3, float f2, float f3) {
        TypedBundle typedBundle = new TypedBundle();
        typedBundle.add(510, 2);
        typedBundle.add(100, i2);
        typedBundle.add(506, f2);
        typedBundle.add(507, f3);
        getWidgetState(str, null, 0).setKeyPosition(typedBundle);
        KeyPosition keyPosition = new KeyPosition(str, i2, i3, f2, f3);
        HashMap<String, KeyPosition> map = this.keyPositions.get(Integer.valueOf(i2));
        if (map == null) {
            map = new HashMap<>();
            this.keyPositions.put(Integer.valueOf(i2), map);
        }
        map.put(str, keyPosition);
    }

    @Override // androidx.constraintlayout.core.motion.utils.TypedValues
    public boolean setValue(int i2, String str) {
        if (i2 != 705) {
            return false;
        }
        this.mDefaultInterpolatorString = str;
        this.mEasing = Easing.getInterpolator(str);
        return false;
    }

    public WidgetFrame getEnd(ConstraintWidget constraintWidget) {
        return getWidgetState(constraintWidget.stringId, null, 1).end;
    }

    public WidgetFrame getInterpolated(ConstraintWidget constraintWidget) {
        return getWidgetState(constraintWidget.stringId, null, 2).interpolated;
    }

    public WidgetFrame getStart(ConstraintWidget constraintWidget) {
        return getWidgetState(constraintWidget.stringId, null, 0).start;
    }

    public Interpolator getInterpolator() {
        return getInterpolator(this.mDefaultInterpolator, this.mDefaultInterpolatorString);
    }
}
