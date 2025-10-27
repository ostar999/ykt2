package com.psychiatrygarden.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Property;
import android.view.MotionEvent;
import android.view.View;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class DribSearchView extends View implements ValueAnimator.AnimatorUpdateListener {
    private static final float DEFAULT_ALPHA = 0.8f;
    private static final int DEFAULT_COLOR = -1;
    private static final float DEFAULT_JOIN_ANGLE = 45.0f;
    private static final float DEGREE_360 = 360.0f;
    private static final double sin45 = Math.sin(0.7853981633974483d);
    float defaultJoinx;
    float defaultJoiny;
    int drawBottom;
    int drawLeft;
    int drawRight;
    int drawTop;
    private float joinAngle;
    private Property<DribSearchView, Float> joinAngleProperty;
    private Property<DribSearchView, Float> joinXProperty;
    private Property<DribSearchView, Float> joinYProperty;
    private float joinx;
    private float joiny;
    private float lineDelx;
    private Property<DribSearchView, Float> lineDelxProperty;
    private int mBreadth;
    private OnClickSearchListener mClickSearchListener;
    private OnChangeListener mListener;
    RectF mRoundRect;
    private int mSearchColor;
    private Paint mSearchPaint;
    private Path mSearchPath;
    private SearchTouchListener mSearchTouchListener;
    State mState;
    private View.OnTouchListener mTmpOnTouchListener;
    RectF mTouchRect;
    float rx;
    float ry;

    /* renamed from: com.psychiatrygarden.widget.DribSearchView$7, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass7 {
        static final /* synthetic */ int[] $SwitchMap$com$psychiatrygarden$widget$DribSearchView$State;

        static {
            int[] iArr = new int[State.values().length];
            $SwitchMap$com$psychiatrygarden$widget$DribSearchView$State = iArr;
            try {
                iArr[State.RUNNING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$psychiatrygarden$widget$DribSearchView$State[State.SEARCH.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$psychiatrygarden$widget$DribSearchView$State[State.LINE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public interface OnChangeListener {
        void onChange(State state);
    }

    public interface OnClickSearchListener {
        void onClickSearch();
    }

    public static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: com.psychiatrygarden.widget.DribSearchView.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel source) {
                return new SavedState(source);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        protected float joinAngle;
        protected float joinx;
        protected float joiny;
        protected float lineDelx;

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeFloat(this.joinAngle);
            dest.writeFloat(this.joinx);
            dest.writeFloat(this.joiny);
            dest.writeFloat(this.lineDelx);
        }

        public SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
        }
    }

    public class SearchTouchListener implements View.OnTouchListener {
        private View.OnTouchListener other;
        private RectF rect;

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View v2, MotionEvent event) {
            if (event.getAction() == 0) {
                if (this.rect.contains(event.getX(), event.getY()) && DribSearchView.this.mClickSearchListener != null) {
                    DribSearchView dribSearchView = DribSearchView.this;
                    if (dribSearchView.mState == State.SEARCH) {
                        dribSearchView.mClickSearchListener.onClickSearch();
                    }
                }
            }
            View.OnTouchListener onTouchListener = this.other;
            if (onTouchListener != null) {
                return onTouchListener.onTouch(v2, event);
            }
            return false;
        }

        public void setOther(View.OnTouchListener other, RectF rect) {
            this.other = other;
            this.rect = rect;
        }

        private SearchTouchListener() {
        }
    }

    public class SimpleAnimatorListener implements Animator.AnimatorListener {
        private SimpleAnimatorListener() {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationRepeat(Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animator) {
        }
    }

    public enum State {
        RUNNING,
        SEARCH,
        LINE
    }

    public DribSearchView(Context context) {
        this(context, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float getJoinAngle() {
        return this.joinAngle;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float getJoinx() {
        return this.joinx;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float getJoiny() {
        return this.joiny;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float getLineDelx() {
        return this.lineDelx;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setJoinAngle(float joinAngle) {
        this.joinAngle = joinAngle;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setJoinx(float joinx) {
        this.joinx = joinx;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setJoiny(float joiny) {
        this.joiny = joiny;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setLineDelx(float lineDelx) {
        this.lineDelx = lineDelx;
    }

    public void changeLine() {
        if (this.mState == State.LINE) {
            return;
        }
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this, this.joinAngleProperty, DEFAULT_JOIN_ANGLE, 405.0f);
        objectAnimatorOfFloat.setDuration(450L);
        objectAnimatorOfFloat.addUpdateListener(this);
        ObjectAnimator objectAnimatorOfPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(this, PropertyValuesHolder.ofFloat(this.joinXProperty, 0.0f, this.drawRight - this.defaultJoinx), PropertyValuesHolder.ofFloat(this.joinYProperty, 0.0f, this.drawBottom - this.defaultJoiny));
        objectAnimatorOfPropertyValuesHolder.setDuration(300L);
        objectAnimatorOfPropertyValuesHolder.addUpdateListener(this);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(this, this.lineDelxProperty, this.drawRight - this.drawLeft, 0.0f);
        objectAnimatorOfFloat2.setDuration(700L);
        objectAnimatorOfFloat2.addUpdateListener(this);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(objectAnimatorOfFloat).before(objectAnimatorOfPropertyValuesHolder).with(objectAnimatorOfFloat2);
        animatorSet.addListener(new SimpleAnimatorListener() { // from class: com.psychiatrygarden.widget.DribSearchView.1
            @Override // com.psychiatrygarden.widget.DribSearchView.SimpleAnimatorListener, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                DribSearchView dribSearchView = DribSearchView.this;
                dribSearchView.mState = State.LINE;
                dribSearchView.mListener.onChange(DribSearchView.this.mState);
            }

            @Override // com.psychiatrygarden.widget.DribSearchView.SimpleAnimatorListener, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                DribSearchView.this.mState = State.RUNNING;
            }
        });
        animatorSet.start();
    }

    public void changeSearch() {
        if (this.mState == State.SEARCH) {
            return;
        }
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this, this.joinAngleProperty, 405.0f, DEFAULT_JOIN_ANGLE);
        objectAnimatorOfFloat.setDuration(450L);
        objectAnimatorOfFloat.addUpdateListener(this);
        ObjectAnimator objectAnimatorOfPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(this, PropertyValuesHolder.ofFloat(this.joinXProperty, this.drawRight - this.defaultJoinx, 0.0f), PropertyValuesHolder.ofFloat(this.joinYProperty, this.drawBottom - this.defaultJoiny, 0.0f));
        objectAnimatorOfPropertyValuesHolder.setDuration(300L);
        objectAnimatorOfPropertyValuesHolder.addUpdateListener(this);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(this, this.lineDelxProperty, 0.0f, this.drawRight - this.drawLeft);
        objectAnimatorOfFloat2.setDuration(700L);
        objectAnimatorOfFloat2.addUpdateListener(this);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(objectAnimatorOfPropertyValuesHolder).with(objectAnimatorOfFloat2);
        animatorSet.play(objectAnimatorOfPropertyValuesHolder).before(objectAnimatorOfFloat);
        animatorSet.addListener(new SimpleAnimatorListener() { // from class: com.psychiatrygarden.widget.DribSearchView.2
            @Override // com.psychiatrygarden.widget.DribSearchView.SimpleAnimatorListener, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                DribSearchView dribSearchView = DribSearchView.this;
                dribSearchView.mState = State.SEARCH;
                dribSearchView.mListener.onChange(DribSearchView.this.mState);
            }

            @Override // com.psychiatrygarden.widget.DribSearchView.SimpleAnimatorListener, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                DribSearchView.this.mState = State.RUNNING;
            }
        });
        animatorSet.start();
    }

    public void initView() {
        setLayerType(1, null);
        this.mSearchPaint.setStyle(Paint.Style.STROKE);
        this.mSearchPaint.setStrokeWidth(this.mBreadth);
        this.mSearchPaint.setColor(this.mSearchColor);
        this.mSearchPaint.setAntiAlias(true);
        this.mSearchPaint.setAlpha(204);
        this.mSearchPaint.setStrokeCap(Paint.Cap.ROUND);
        this.mSearchPaint.setStrokeJoin(Paint.Join.ROUND);
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        invalidate();
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mSearchPath.reset();
        this.mSearchPaint.setStrokeWidth(this.mBreadth);
        Path path = this.mSearchPath;
        RectF rectF = this.mRoundRect;
        float f2 = this.joinAngle;
        path.addArc(rectF, f2, 405.0f - f2);
        this.mSearchPath.moveTo(this.defaultJoinx + this.joinx, this.defaultJoiny + this.joiny);
        this.mSearchPath.lineTo(this.drawRight, this.drawBottom);
        canvas.drawPath(this.mSearchPath, this.mSearchPaint);
        this.mSearchPaint.setStrokeWidth(2.0f);
        this.mSearchPath.moveTo(this.drawRight, this.drawBottom);
        this.mSearchPath.lineTo(this.drawLeft + this.lineDelx, this.drawBottom);
        canvas.drawPath(this.mSearchPath, this.mSearchPaint);
    }

    @Override // android.view.View
    public void onLayout(boolean changed, int left, int top2, int right, int bottom) {
        super.onLayout(changed, left, top2, right, bottom);
        resetLayout(left, top2, right, bottom);
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.joinAngle = savedState.joinAngle;
        this.joinx = savedState.joinx;
        this.joiny = savedState.joiny;
        this.lineDelx = savedState.lineDelx;
        invalidate();
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.joinAngle = this.joinAngle;
        savedState.joinx = this.joinx;
        savedState.joiny = this.joiny;
        savedState.lineDelx = this.lineDelx;
        return savedState;
    }

    public void resetLayout(int left, int top2, int right, int bottom) {
        this.drawLeft = left + getPaddingLeft() + 1;
        this.drawTop = top2 + getPaddingTop() + 1;
        this.drawRight = (right - getPaddingRight()) - 1;
        int paddingBottom = (bottom - getPaddingBottom()) - 1;
        this.drawBottom = paddingBottom;
        int i2 = this.drawTop;
        this.mTouchRect.set(r0 - r6, i2, this.drawRight, paddingBottom);
        double d3 = sin45;
        float f2 = (float) ((paddingBottom - i2) / ((2.0d * d3) + 1.0d));
        float f3 = (float) (this.drawRight - ((2.0f * f2) * d3));
        this.rx = f3;
        float f4 = this.drawTop + f2;
        this.ry = f4;
        this.mRoundRect.set(f3 - f2, f4 - f2, f3 + f2, f4 + f2);
        double d4 = f2;
        float f5 = (float) (this.rx + (d3 * d4));
        this.defaultJoinx = f5;
        float f6 = (float) (this.ry + (d3 * d4));
        this.defaultJoiny = f6;
        State state = this.mState;
        State state2 = State.SEARCH;
        if (state == state2) {
            this.joinAngle = DEFAULT_JOIN_ANGLE;
            this.joinx = 0.0f;
            this.joiny = 0.0f;
            this.lineDelx = this.drawRight - this.drawLeft;
        } else if (state == state2) {
            this.joinAngle = 405.0f;
            this.joinx = this.drawRight - f5;
            this.joiny = this.drawBottom - f6;
            this.lineDelx = 0.0f;
        }
        this.mSearchTouchListener.setOther(this.mTmpOnTouchListener, this.mTouchRect);
        super.setOnTouchListener(this.mSearchTouchListener);
    }

    public void setOnChangeListener(OnChangeListener listener) {
        this.mListener = listener;
    }

    public void setOnClickSearchListener(OnClickSearchListener l2) {
        this.mClickSearchListener = l2;
    }

    @Override // android.view.View
    public void setOnTouchListener(View.OnTouchListener l2) {
        this.mTmpOnTouchListener = l2;
        this.mSearchTouchListener.setOther(l2, this.mTouchRect);
        super.setOnTouchListener(this.mSearchTouchListener);
    }

    public void toggle() {
        int i2 = AnonymousClass7.$SwitchMap$com$psychiatrygarden$widget$DribSearchView$State[this.mState.ordinal()];
        if (i2 == 2) {
            changeLine();
        } else {
            if (i2 != 3) {
                return;
            }
            changeSearch();
        }
    }

    public DribSearchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DribSearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mSearchPaint = new Paint();
        this.mSearchPath = new Path();
        this.mBreadth = 2;
        this.mSearchColor = -1;
        this.drawLeft = 0;
        this.drawTop = 0;
        this.drawRight = 0;
        this.drawBottom = 9;
        this.rx = 0.0f;
        this.ry = 0.0f;
        this.mRoundRect = new RectF();
        this.defaultJoinx = 0.0f;
        this.defaultJoiny = 0.0f;
        this.mTouchRect = new RectF();
        this.joinAngle = 0.0f;
        this.joinx = 0.0f;
        this.joiny = 0.0f;
        this.lineDelx = 0.0f;
        this.mState = State.SEARCH;
        this.mSearchTouchListener = new SearchTouchListener();
        this.mTmpOnTouchListener = null;
        Class<Float> cls = Float.class;
        this.joinAngleProperty = new Property<DribSearchView, Float>(cls, "joinAngle") { // from class: com.psychiatrygarden.widget.DribSearchView.3
            @Override // android.util.Property
            public Float get(DribSearchView object) {
                return Float.valueOf(object.getJoinAngle());
            }

            @Override // android.util.Property
            public void set(DribSearchView object, Float value) {
                object.setJoinAngle(value.floatValue());
            }
        };
        this.joinXProperty = new Property<DribSearchView, Float>(cls, "joinx") { // from class: com.psychiatrygarden.widget.DribSearchView.4
            @Override // android.util.Property
            public Float get(DribSearchView object) {
                return Float.valueOf(object.getJoinx());
            }

            @Override // android.util.Property
            public void set(DribSearchView object, Float value) {
                object.setJoinx(value.floatValue());
            }
        };
        this.joinYProperty = new Property<DribSearchView, Float>(cls, "joiny") { // from class: com.psychiatrygarden.widget.DribSearchView.5
            @Override // android.util.Property
            public Float get(DribSearchView object) {
                return Float.valueOf(object.getJoiny());
            }

            @Override // android.util.Property
            public void set(DribSearchView object, Float value) {
                object.setJoiny(value.floatValue());
            }
        };
        this.lineDelxProperty = new Property<DribSearchView, Float>(cls, "lineDelx") { // from class: com.psychiatrygarden.widget.DribSearchView.6
            @Override // android.util.Property
            public Float get(DribSearchView object) {
                return Float.valueOf(object.getLineDelx());
            }

            @Override // android.util.Property
            public void set(DribSearchView object, Float value) {
                object.setLineDelx(value.floatValue());
            }
        };
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.DribSearchView);
        this.mBreadth = typedArrayObtainStyledAttributes.getInteger(0, 2);
        this.mSearchColor = typedArrayObtainStyledAttributes.getColor(1, -1);
        typedArrayObtainStyledAttributes.recycle();
        initView();
    }

    @TargetApi(21)
    public DribSearchView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mSearchPaint = new Paint();
        this.mSearchPath = new Path();
        this.mBreadth = 2;
        this.mSearchColor = -1;
        this.drawLeft = 0;
        this.drawTop = 0;
        this.drawRight = 0;
        this.drawBottom = 9;
        this.rx = 0.0f;
        this.ry = 0.0f;
        this.mRoundRect = new RectF();
        this.defaultJoinx = 0.0f;
        this.defaultJoiny = 0.0f;
        this.mTouchRect = new RectF();
        this.joinAngle = 0.0f;
        this.joinx = 0.0f;
        this.joiny = 0.0f;
        this.lineDelx = 0.0f;
        this.mState = State.SEARCH;
        this.mSearchTouchListener = new SearchTouchListener();
        this.mTmpOnTouchListener = null;
        Class<Float> cls = Float.class;
        this.joinAngleProperty = new Property<DribSearchView, Float>(cls, "joinAngle") { // from class: com.psychiatrygarden.widget.DribSearchView.3
            @Override // android.util.Property
            public Float get(DribSearchView object) {
                return Float.valueOf(object.getJoinAngle());
            }

            @Override // android.util.Property
            public void set(DribSearchView object, Float value) {
                object.setJoinAngle(value.floatValue());
            }
        };
        this.joinXProperty = new Property<DribSearchView, Float>(cls, "joinx") { // from class: com.psychiatrygarden.widget.DribSearchView.4
            @Override // android.util.Property
            public Float get(DribSearchView object) {
                return Float.valueOf(object.getJoinx());
            }

            @Override // android.util.Property
            public void set(DribSearchView object, Float value) {
                object.setJoinx(value.floatValue());
            }
        };
        this.joinYProperty = new Property<DribSearchView, Float>(cls, "joiny") { // from class: com.psychiatrygarden.widget.DribSearchView.5
            @Override // android.util.Property
            public Float get(DribSearchView object) {
                return Float.valueOf(object.getJoiny());
            }

            @Override // android.util.Property
            public void set(DribSearchView object, Float value) {
                object.setJoiny(value.floatValue());
            }
        };
        this.lineDelxProperty = new Property<DribSearchView, Float>(cls, "lineDelx") { // from class: com.psychiatrygarden.widget.DribSearchView.6
            @Override // android.util.Property
            public Float get(DribSearchView object) {
                return Float.valueOf(object.getLineDelx());
            }

            @Override // android.util.Property
            public void set(DribSearchView object, Float value) {
                object.setLineDelx(value.floatValue());
            }
        };
        initView();
    }
}
