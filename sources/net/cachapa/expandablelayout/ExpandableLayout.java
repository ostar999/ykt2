package net.cachapa.expandablelayout;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import net.cachapa.expandablelayout.util.FastOutSlowInInterpolator;

/* loaded from: classes9.dex */
public class ExpandableLayout extends FrameLayout {
    private static final int DEFAULT_DURATION = 300;
    public static final int HORIZONTAL = 0;
    public static final String KEY_EXPANSION = "expansion";
    public static final String KEY_SUPER_STATE = "super_state";
    public static final int VERTICAL = 1;
    private ValueAnimator animator;
    private int duration;
    private float expansion;
    private Interpolator interpolator;
    private OnExpansionUpdateListener listener;
    private int orientation;
    private float parallax;
    private int state;

    public class ExpansionListener implements Animator.AnimatorListener {
        private boolean canceled;
        private int targetExpansion;

        public ExpansionListener(int i2) {
            this.targetExpansion = i2;
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animator) {
            this.canceled = true;
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            if (this.canceled) {
                return;
            }
            ExpandableLayout.this.state = this.targetExpansion == 0 ? 0 : 3;
            ExpandableLayout.this.setExpansion(this.targetExpansion);
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationRepeat(Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animator) {
            ExpandableLayout.this.state = this.targetExpansion == 0 ? 1 : 2;
        }
    }

    public interface OnExpansionUpdateListener {
        void onExpansionUpdate(float f2, int i2);
    }

    public interface State {
        public static final int COLLAPSED = 0;
        public static final int COLLAPSING = 1;
        public static final int EXPANDED = 3;
        public static final int EXPANDING = 2;
    }

    public ExpandableLayout(Context context) {
        this(context, null);
    }

    private void animateSize(int i2) {
        ValueAnimator valueAnimator = this.animator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
            this.animator = null;
        }
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.expansion, i2);
        this.animator = valueAnimatorOfFloat;
        valueAnimatorOfFloat.setInterpolator(this.interpolator);
        this.animator.setDuration(this.duration);
        this.animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: net.cachapa.expandablelayout.ExpandableLayout.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator2) {
                ExpandableLayout.this.setExpansion(((Float) valueAnimator2.getAnimatedValue()).floatValue());
            }
        });
        this.animator.addListener(new ExpansionListener(i2));
        this.animator.start();
    }

    public void collapse() {
        collapse(true);
    }

    public void expand() {
        expand(true);
    }

    public int getDuration() {
        return this.duration;
    }

    public float getExpansion() {
        return this.expansion;
    }

    public int getOrientation() {
        return this.orientation;
    }

    public float getParallax() {
        return this.parallax;
    }

    public int getState() {
        return this.state;
    }

    public boolean isExpanded() {
        int i2 = this.state;
        return i2 == 2 || i2 == 3;
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        ValueAnimator valueAnimator = this.animator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        super.onConfigurationChanged(configuration);
    }

    @Override // android.widget.FrameLayout, android.view.View
    public void onMeasure(int i2, int i3) {
        super.onMeasure(i2, i3);
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        int i4 = this.orientation == 0 ? measuredWidth : measuredHeight;
        setVisibility((this.expansion == 0.0f && i4 == 0) ? 8 : 0);
        int iRound = i4 - Math.round(i4 * this.expansion);
        float f2 = this.parallax;
        if (f2 > 0.0f) {
            float f3 = iRound * f2;
            for (int i5 = 0; i5 < getChildCount(); i5++) {
                View childAt = getChildAt(i5);
                if (this.orientation == 0) {
                    childAt.setTranslationX((getLayoutDirection() != 1 ? -1 : 1) * f3);
                } else {
                    childAt.setTranslationY(-f3);
                }
            }
        }
        if (this.orientation == 0) {
            setMeasuredDimension(measuredWidth - iRound, measuredHeight);
        } else {
            setMeasuredDimension(measuredWidth, measuredHeight - iRound);
        }
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        Bundle bundle = (Bundle) parcelable;
        float f2 = bundle.getFloat(KEY_EXPANSION);
        this.expansion = f2;
        this.state = f2 == 1.0f ? 3 : 0;
        super.onRestoreInstanceState(bundle.getParcelable(KEY_SUPER_STATE));
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        Parcelable parcelableOnSaveInstanceState = super.onSaveInstanceState();
        Bundle bundle = new Bundle();
        float f2 = isExpanded() ? 1.0f : 0.0f;
        this.expansion = f2;
        bundle.putFloat(KEY_EXPANSION, f2);
        bundle.putParcelable(KEY_SUPER_STATE, parcelableOnSaveInstanceState);
        return bundle;
    }

    public void setDuration(int i2) {
        this.duration = i2;
    }

    public void setExpanded(boolean z2) {
        setExpanded(z2, true);
    }

    public void setExpansion(float f2) {
        float f3 = this.expansion;
        if (f3 == f2) {
            return;
        }
        float f4 = f2 - f3;
        if (f2 == 0.0f) {
            this.state = 0;
        } else if (f2 == 1.0f) {
            this.state = 3;
        } else if (f4 < 0.0f) {
            this.state = 1;
        } else if (f4 > 0.0f) {
            this.state = 2;
        }
        setVisibility(this.state == 0 ? 8 : 0);
        this.expansion = f2;
        requestLayout();
        OnExpansionUpdateListener onExpansionUpdateListener = this.listener;
        if (onExpansionUpdateListener != null) {
            onExpansionUpdateListener.onExpansionUpdate(f2, this.state);
        }
    }

    public void setInterpolator(Interpolator interpolator) {
        this.interpolator = interpolator;
    }

    public void setOnExpansionUpdateListener(OnExpansionUpdateListener onExpansionUpdateListener) {
        this.listener = onExpansionUpdateListener;
    }

    public void setOrientation(int i2) {
        if (i2 < 0 || i2 > 1) {
            throw new IllegalArgumentException("Orientation must be either 0 (horizontal) or 1 (vertical)");
        }
        this.orientation = i2;
    }

    public void setParallax(float f2) {
        this.parallax = Math.min(1.0f, Math.max(0.0f, f2));
    }

    public void toggle() {
        toggle(true);
    }

    public ExpandableLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.duration = 300;
        this.interpolator = new FastOutSlowInInterpolator();
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.ExpandableLayout);
            this.duration = typedArrayObtainStyledAttributes.getInt(R.styleable.ExpandableLayout_el_duration, 300);
            this.expansion = typedArrayObtainStyledAttributes.getBoolean(R.styleable.ExpandableLayout_el_expanded, false) ? 1.0f : 0.0f;
            this.orientation = typedArrayObtainStyledAttributes.getInt(R.styleable.ExpandableLayout_android_orientation, 1);
            this.parallax = typedArrayObtainStyledAttributes.getFloat(R.styleable.ExpandableLayout_el_parallax, 1.0f);
            typedArrayObtainStyledAttributes.recycle();
            this.state = this.expansion != 0.0f ? 3 : 0;
            setParallax(this.parallax);
        }
    }

    public void collapse(boolean z2) {
        setExpanded(false, z2);
    }

    public void expand(boolean z2) {
        setExpanded(true, z2);
    }

    public void setExpanded(boolean z2, boolean z3) {
        if (z2 == isExpanded()) {
            return;
        }
        if (z3) {
            animateSize(z2 ? 1 : 0);
        } else {
            setExpansion(z2 ? 1.0f : 0.0f);
        }
    }

    public void toggle(boolean z2) {
        if (isExpanded()) {
            collapse(z2);
        } else {
            expand(z2);
        }
    }
}
