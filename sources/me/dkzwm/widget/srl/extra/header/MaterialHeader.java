package me.dkzwm.widget.srl.extra.header;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.NonNull;
import me.dkzwm.widget.srl.SmoothRefreshLayout;
import me.dkzwm.widget.srl.drawable.MaterialProgressDrawable;
import me.dkzwm.widget.srl.extra.IRefreshView;
import me.dkzwm.widget.srl.indicator.IIndicator;

/* loaded from: classes9.dex */
public class MaterialHeader<T extends IIndicator> extends View implements IRefreshView<T> {
    private ValueAnimator mAnimator;
    private int mCachedDuration;
    protected MaterialProgressDrawable mDrawable;
    private boolean mHasHook;
    private SmoothRefreshLayout.OnHookUIRefreshCompleteCallBack mHookUIRefreshCompleteCallBack;
    private SmoothRefreshLayout mRefreshLayout;
    protected float mScale;

    public MaterialHeader(Context context) {
        this(context, null);
    }

    private void cancelAnimator() {
        if (this.mAnimator.isRunning()) {
            this.mAnimator.cancel();
        }
    }

    private void resetDrawable() {
        this.mDrawable.setAlpha(255);
        this.mDrawable.stop();
        this.mScale = 1.0f;
    }

    private void resetLayoutHeaderCloseDuration(SmoothRefreshLayout smoothRefreshLayout) {
        int i2;
        if (this.mHasHook && (i2 = this.mCachedDuration) > 0) {
            smoothRefreshLayout.setDurationToCloseHeader(i2);
        }
        this.mCachedDuration = -1;
    }

    public void doHookUIRefreshComplete(SmoothRefreshLayout smoothRefreshLayout) {
        this.mRefreshLayout = smoothRefreshLayout;
        this.mHasHook = true;
        smoothRefreshLayout.setOnHookHeaderRefreshCompleteCallback(this.mHookUIRefreshCompleteCallBack);
    }

    @Override // me.dkzwm.widget.srl.extra.IRefreshView
    public int getCustomHeight() {
        return 0;
    }

    @Override // me.dkzwm.widget.srl.extra.IRefreshView
    public int getStyle() {
        return 0;
    }

    @Override // me.dkzwm.widget.srl.extra.IRefreshView
    public int getType() {
        return 0;
    }

    @Override // me.dkzwm.widget.srl.extra.IRefreshView
    @NonNull
    public View getView() {
        return this;
    }

    @Override // android.view.View, android.graphics.drawable.Drawable.Callback
    public void invalidateDrawable(Drawable drawable) {
        if (drawable == this.mDrawable) {
            invalidate();
        } else {
            super.invalidateDrawable(drawable);
        }
    }

    @Override // android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mHasHook) {
            SmoothRefreshLayout smoothRefreshLayout = this.mRefreshLayout;
            if (smoothRefreshLayout != null) {
                smoothRefreshLayout.setOnHookHeaderRefreshCompleteCallback(this.mHookUIRefreshCompleteCallBack);
            } else if (getParent() instanceof SmoothRefreshLayout) {
                SmoothRefreshLayout smoothRefreshLayout2 = (SmoothRefreshLayout) getParent();
                this.mRefreshLayout = smoothRefreshLayout2;
                smoothRefreshLayout2.setOnHookHeaderRefreshCompleteCallback(this.mHookUIRefreshCompleteCallBack);
            }
        }
    }

    @Override // android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        resetDrawable();
        cancelAnimator();
        SmoothRefreshLayout smoothRefreshLayout = this.mRefreshLayout;
        if (smoothRefreshLayout == null || !this.mHasHook) {
            return;
        }
        smoothRefreshLayout.setOnHookHeaderRefreshCompleteCallback(null);
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        if (this.mRefreshLayout == null) {
            return;
        }
        int iSave = canvas.save();
        if (this.mRefreshLayout.getSupportScrollAxis() == 2) {
            canvas.translate(getPaddingLeft() + ((getMeasuredWidth() - this.mDrawable.getIntrinsicWidth()) / 2), getPaddingTop());
        } else {
            canvas.translate(getPaddingLeft(), getPaddingTop() + ((getMeasuredHeight() - this.mDrawable.getIntrinsicWidth()) / 2));
        }
        Rect bounds = this.mDrawable.getBounds();
        float f2 = this.mScale;
        canvas.scale(f2, f2, bounds.exactCenterX(), bounds.exactCenterY());
        this.mDrawable.draw(canvas);
        canvas.restoreToCount(iSave);
    }

    @Override // me.dkzwm.widget.srl.extra.IRefreshView
    public void onFingerUp(SmoothRefreshLayout smoothRefreshLayout, T t2) {
    }

    @Override // android.view.View
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        int intrinsicHeight = this.mDrawable.getIntrinsicHeight();
        this.mDrawable.setBounds(0, 0, intrinsicHeight, intrinsicHeight);
    }

    @Override // android.view.View
    public void onMeasure(int i2, int i3) {
        if (this.mRefreshLayout == null) {
            if (getParent() instanceof SmoothRefreshLayout) {
                this.mRefreshLayout = (SmoothRefreshLayout) getParent();
            }
            if (this.mRefreshLayout == null) {
                super.onMeasure(i2, i3);
                return;
            }
        }
        if (this.mRefreshLayout.getSupportScrollAxis() == 2) {
            i3 = View.MeasureSpec.makeMeasureSpec(this.mDrawable.getIntrinsicHeight() + getPaddingTop() + getPaddingBottom(), 1073741824);
        } else {
            i2 = View.MeasureSpec.makeMeasureSpec(this.mDrawable.getIntrinsicWidth() + getPaddingLeft() + getPaddingRight(), 1073741824);
        }
        super.onMeasure(i2, i3);
    }

    @Override // me.dkzwm.widget.srl.extra.IRefreshView
    public void onPureScrollPositionChanged(SmoothRefreshLayout smoothRefreshLayout, byte b3, T t2) {
        if (t2.hasJustLeftStartPosition()) {
            this.mDrawable.setAlpha(255);
            this.mDrawable.setStartEndTrim(0.0f, 0.8f);
            this.mDrawable.showArrow(true);
            this.mDrawable.setArrowScale(1.0f);
            invalidate();
        }
    }

    @Override // me.dkzwm.widget.srl.extra.IRefreshView
    public void onRefreshBegin(SmoothRefreshLayout smoothRefreshLayout, T t2) {
        int durationToCloseHeader = smoothRefreshLayout.getDurationToCloseHeader();
        if (durationToCloseHeader > 0) {
            this.mCachedDuration = durationToCloseHeader;
        }
        this.mDrawable.setAlpha(255);
        this.mDrawable.start();
        invalidate();
    }

    @Override // me.dkzwm.widget.srl.extra.IRefreshView
    public void onRefreshComplete(SmoothRefreshLayout smoothRefreshLayout, boolean z2) {
        if (!this.mHasHook) {
            this.mAnimator.setDuration(smoothRefreshLayout.getDurationToCloseHeader());
            this.mAnimator.start();
        } else {
            int durationToCloseHeader = smoothRefreshLayout.getDurationToCloseHeader();
            if (durationToCloseHeader > 0 && this.mCachedDuration <= 0) {
                this.mCachedDuration = durationToCloseHeader;
            }
            smoothRefreshLayout.setDurationToCloseHeader(0);
        }
    }

    @Override // me.dkzwm.widget.srl.extra.IRefreshView
    public void onRefreshPositionChanged(SmoothRefreshLayout smoothRefreshLayout, byte b3, T t2) {
        float fMin = Math.min(1.0f, t2.getCurrentPercentOfRefreshOffset());
        float fMin2 = Math.min(1.0f, fMin * fMin * fMin);
        if (b3 == 2) {
            this.mDrawable.setAlpha((int) (fMin2 * 255.0f));
            this.mDrawable.showArrow(true);
            this.mDrawable.setStartEndTrim(0.0f, Math.min(0.8f, fMin * 0.8f));
            this.mDrawable.setArrowScale(fMin);
            this.mDrawable.setProgressRotation((((0.4f * fMin) - 0.25f) + (fMin * 2.0f)) * 0.5f);
            invalidate();
        }
    }

    @Override // me.dkzwm.widget.srl.extra.IRefreshView
    public void onRefreshPrepare(SmoothRefreshLayout smoothRefreshLayout) {
        resetLayoutHeaderCloseDuration(smoothRefreshLayout);
        resetDrawable();
        cancelAnimator();
    }

    @Override // me.dkzwm.widget.srl.extra.IRefreshView
    public void onReset(SmoothRefreshLayout smoothRefreshLayout) {
        resetLayoutHeaderCloseDuration(smoothRefreshLayout);
        resetDrawable();
        cancelAnimator();
    }

    public void setColorSchemeColors(int[] iArr) {
        this.mDrawable.setColorSchemeColors(iArr);
        invalidate();
    }

    public MaterialHeader(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MaterialHeader(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mScale = 1.0f;
        this.mCachedDuration = -1;
        this.mHasHook = false;
        this.mHookUIRefreshCompleteCallBack = new SmoothRefreshLayout.OnHookUIRefreshCompleteCallBack() { // from class: me.dkzwm.widget.srl.extra.header.MaterialHeader.1
            @Override // me.dkzwm.widget.srl.SmoothRefreshLayout.OnHookUIRefreshCompleteCallBack
            public void onHook(final SmoothRefreshLayout.RefreshCompleteHook refreshCompleteHook) {
                if (MaterialHeader.this.mRefreshLayout == null || !MaterialHeader.this.mRefreshLayout.isRefreshing()) {
                    refreshCompleteHook.onHookComplete();
                    return;
                }
                MaterialHeader.this.mAnimator.setDuration(200L);
                MaterialHeader.this.mAnimator.addListener(new AnimatorListenerAdapter() { // from class: me.dkzwm.widget.srl.extra.header.MaterialHeader.1.1
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        MaterialHeader.this.mAnimator.removeListener(this);
                        refreshCompleteHook.onHookComplete();
                    }
                });
                MaterialHeader.this.mAnimator.start();
            }
        };
        MaterialProgressDrawable materialProgressDrawable = new MaterialProgressDrawable(getContext(), this);
        this.mDrawable = materialProgressDrawable;
        materialProgressDrawable.setBackgroundColor(-1);
        this.mDrawable.setCallback(this);
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
        this.mAnimator = valueAnimatorOfFloat;
        valueAnimatorOfFloat.setRepeatCount(0);
        this.mAnimator.setRepeatMode(1);
        this.mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: me.dkzwm.widget.srl.extra.header.MaterialHeader.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                MaterialHeader.this.mScale = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                MaterialHeader materialHeader = MaterialHeader.this;
                materialHeader.mDrawable.setAlpha((int) (materialHeader.mScale * 255.0f));
                MaterialHeader.this.invalidate();
            }
        });
    }
}
