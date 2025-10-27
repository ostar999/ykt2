package com.ykb.ebook.scrollUtil;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.TypedValue;
import android.view.MotionEvent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes7.dex */
public class FastScrollerEx extends RecyclerView.ItemDecoration implements RecyclerView.OnItemTouchListener {
    private static final int ANIMATION_STATE_FADING_IN = 1;
    private static final int ANIMATION_STATE_FADING_OUT = 3;
    private static final int ANIMATION_STATE_IN = 2;
    private static final int ANIMATION_STATE_OUT = 0;
    private static final int DRAG_NONE = 0;
    private static final int DRAG_X = 1;
    private static final int DRAG_Y = 2;
    private static final int HIDE_DELAY_AFTER_DRAGGING_MS = 1200;
    private static final int HIDE_DELAY_AFTER_VISIBLE_MS = 1500;
    private static final int HIDE_DURATION_MS = 500;
    private static final int SCROLLBAR_FULL_OPAQUE = 255;
    private static final int SHOW_DURATION_MS = 500;
    private static final int STATE_DRAGGING = 2;
    private static final int STATE_HIDDEN = 0;
    private static final int STATE_VISIBLE = 1;
    int mAnimationState;
    private final boolean mConsiderPadding;
    private final Runnable mHideRunnable;

    @VisibleForTesting
    private float mHorizontalDragX;

    @VisibleForTesting
    private int mHorizontalThumbCenterX;
    private final StateListDrawable mHorizontalThumbDrawable;
    private final int mHorizontalThumbHeight;

    @VisibleForTesting
    private int mHorizontalThumbWidth;
    private final Drawable mHorizontalTrackDrawable;
    private final int mHorizontalTrackHeight;
    private final int mMargin;
    private final RecyclerView.OnScrollListener mOnScrollListener;
    private final int mScrollbarMinimumRange;
    final ValueAnimator mShowHideAnimator;

    @VisibleForTesting
    private float mVerticalDragY;

    @VisibleForTesting
    private int mVerticalThumbCenterY;
    final StateListDrawable mVerticalThumbDrawable;

    @VisibleForTesting
    private int mVerticalThumbHeight;
    private final int mVerticalThumbWidth;
    final Drawable mVerticalTrackDrawable;
    private final int mVerticalTrackWidth;
    private final int minThumbSize;
    private RecyclerView recyclerView;
    private static final int[] PRESSED_STATE_SET = {R.attr.state_pressed};
    private static final int[] EMPTY_STATE_SET = new int[0];
    private int recyclerViewWidth = 0;
    private int recyclerViewHeight = 0;
    private boolean mNeedVerticalScrollbar = false;
    private boolean mNeedHorizontalScrollbar = false;
    private int state = 0;
    private int dragState = 0;
    private final int[] mVerticalRange = new int[2];
    private final int[] mHorizontalRange = new int[2];

    @Retention(RetentionPolicy.SOURCE)
    public @interface AnimationState {
    }

    public class AnimatorListener extends AnimatorListenerAdapter {
        private boolean mCanceled = false;

        public AnimatorListener() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animator) {
            this.mCanceled = true;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            if (this.mCanceled) {
                this.mCanceled = false;
                return;
            }
            if (((Float) FastScrollerEx.this.mShowHideAnimator.getAnimatedValue()).floatValue() == 0.0f) {
                FastScrollerEx fastScrollerEx = FastScrollerEx.this;
                fastScrollerEx.mAnimationState = 0;
                fastScrollerEx.setState(0);
            } else {
                FastScrollerEx fastScrollerEx2 = FastScrollerEx.this;
                fastScrollerEx2.mAnimationState = 2;
                fastScrollerEx2.requestRedraw();
            }
        }
    }

    public class AnimatorUpdater implements ValueAnimator.AnimatorUpdateListener {
        public AnimatorUpdater() {
        }

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            int iFloatValue = (int) (((Float) valueAnimator.getAnimatedValue()).floatValue() * 255.0f);
            FastScrollerEx.this.mVerticalThumbDrawable.setAlpha(iFloatValue);
            FastScrollerEx.this.mVerticalTrackDrawable.setAlpha(iFloatValue);
            FastScrollerEx.this.requestRedraw();
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface DragState {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }

    public FastScrollerEx(RecyclerView recyclerView, StateListDrawable stateListDrawable, Drawable drawable, StateListDrawable stateListDrawable2, Drawable drawable2, int i2, int i3, int i4, boolean z2, int i5) {
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.mShowHideAnimator = valueAnimatorOfFloat;
        this.mAnimationState = 0;
        this.mHideRunnable = new Runnable() { // from class: com.ykb.ebook.scrollUtil.a
            @Override // java.lang.Runnable
            public final void run() {
                this.f26450c.lambda$new$0();
            }
        };
        this.mOnScrollListener = new RecyclerView.OnScrollListener() { // from class: com.ykb.ebook.scrollUtil.FastScrollerEx.1
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView2, int i6, int i7) {
                FastScrollerEx.this.updateScrollPosition(recyclerView2.computeHorizontalScrollOffset(), recyclerView2.computeVerticalScrollOffset());
            }
        };
        this.mVerticalThumbDrawable = stateListDrawable;
        this.mVerticalTrackDrawable = drawable;
        this.mHorizontalThumbDrawable = stateListDrawable2;
        this.mHorizontalTrackDrawable = drawable2;
        this.minThumbSize = i5;
        this.mVerticalThumbWidth = (int) TypedValue.applyDimension(1, 16.0f, recyclerView.getContext().getResources().getDisplayMetrics());
        this.mVerticalTrackWidth = (int) TypedValue.applyDimension(1, 16.0f, recyclerView.getContext().getResources().getDisplayMetrics());
        this.mHorizontalThumbHeight = Math.max(i2, stateListDrawable2.getIntrinsicWidth());
        this.mHorizontalTrackHeight = Math.max(i2, drawable2.getIntrinsicWidth());
        this.mScrollbarMinimumRange = i3;
        this.mMargin = i4;
        stateListDrawable.setAlpha(255);
        drawable.setAlpha(255);
        this.mConsiderPadding = z2;
        valueAnimatorOfFloat.addListener(new AnimatorListener());
        valueAnimatorOfFloat.addUpdateListener(new AnimatorUpdater());
        attachToRecyclerView(recyclerView);
    }

    private void attachToRecyclerView(@Nullable RecyclerView recyclerView) {
        RecyclerView recyclerView2 = this.recyclerView;
        if (recyclerView2 == recyclerView) {
            return;
        }
        if (recyclerView2 != null) {
            destroyCallbacks();
        }
        this.recyclerView = recyclerView;
        if (recyclerView != null) {
            setupCallbacks();
        }
    }

    private void cancelHide() {
        this.recyclerView.removeCallbacks(this.mHideRunnable);
    }

    private void destroyCallbacks() {
        this.recyclerView.removeItemDecoration(this);
        this.recyclerView.removeOnItemTouchListener(this);
        this.recyclerView.removeOnScrollListener(this.mOnScrollListener);
        cancelHide();
    }

    private void drawHorizontalScrollbar(Canvas canvas) {
        int i2 = this.recyclerViewHeight;
        int i3 = this.mHorizontalThumbHeight;
        int i4 = this.mHorizontalThumbCenterX;
        int i5 = this.mHorizontalThumbWidth;
        this.mHorizontalThumbDrawable.setBounds(0, 0, i5, i3);
        this.mHorizontalTrackDrawable.setBounds(0, 0, this.recyclerViewWidth, this.mHorizontalTrackHeight);
        canvas.translate(0.0f, i2 - i3);
        this.mHorizontalTrackDrawable.draw(canvas);
        canvas.translate(i4 - (i5 / 2), 0.0f);
        this.mHorizontalThumbDrawable.draw(canvas);
        canvas.translate(-r2, -r0);
    }

    private void drawVerticalScrollbar(Canvas canvas) {
        int paddingRight = this.recyclerViewWidth - this.mVerticalThumbWidth;
        if (this.mConsiderPadding) {
            paddingRight -= this.recyclerView.getPaddingRight();
        }
        int i2 = this.mVerticalThumbCenterY;
        int i3 = this.mVerticalThumbHeight;
        int i4 = i2 - (i3 / 2);
        this.mVerticalThumbDrawable.setBounds(0, 0, this.mVerticalThumbWidth, i3);
        int paddingBottom = this.recyclerViewHeight;
        if (this.mConsiderPadding) {
            paddingBottom -= this.recyclerView.getPaddingBottom();
        }
        this.mVerticalTrackDrawable.setBounds(0, 0, this.mVerticalTrackWidth, paddingBottom);
        if (!isLayoutRTL()) {
            canvas.translate(paddingRight, 0.0f);
            this.mVerticalTrackDrawable.draw(canvas);
            canvas.translate(0.0f, i4);
            this.mVerticalThumbDrawable.draw(canvas);
            canvas.translate(-paddingRight, -i4);
            return;
        }
        this.mVerticalTrackDrawable.draw(canvas);
        canvas.translate(this.mVerticalThumbWidth, i4);
        canvas.scale(-1.0f, 1.0f);
        this.mVerticalThumbDrawable.draw(canvas);
        canvas.scale(1.0f, 1.0f);
        canvas.translate(-this.mVerticalThumbWidth, -i4);
    }

    private int[] getHorizontalRange() {
        int[] iArr = this.mHorizontalRange;
        int i2 = this.mMargin;
        iArr[0] = i2;
        iArr[1] = this.recyclerViewWidth - i2;
        return iArr;
    }

    private int[] getVerticalRange() {
        int[] iArr = this.mVerticalRange;
        int i2 = this.mMargin;
        iArr[0] = i2;
        int i3 = this.recyclerViewHeight - i2;
        iArr[1] = i3;
        if (this.mConsiderPadding) {
            iArr[1] = i3 - this.recyclerView.getPaddingBottom();
        }
        return this.mVerticalRange;
    }

    @SuppressLint({"SwitchIntDef"})
    @VisibleForTesting
    private void hide(int i2) {
        int i3 = this.mAnimationState;
        if (i3 == 1) {
            this.mShowHideAnimator.cancel();
        } else if (i3 != 2) {
            return;
        }
        this.mAnimationState = 3;
        ValueAnimator valueAnimator = this.mShowHideAnimator;
        valueAnimator.setFloatValues(((Float) valueAnimator.getAnimatedValue()).floatValue(), 0.0f);
        this.mShowHideAnimator.setDuration(i2);
        this.mShowHideAnimator.start();
    }

    private void horizontalScrollTo(float f2) {
        int[] horizontalRange = getHorizontalRange();
        float fMax = Math.max(horizontalRange[0], Math.min(horizontalRange[1], f2));
        if (Math.abs(this.mHorizontalThumbCenterX - fMax) < 2.0f) {
            return;
        }
        int iScrollTo = scrollTo(this.mHorizontalDragX, fMax, horizontalRange, this.recyclerView.computeHorizontalScrollRange(), this.recyclerView.computeHorizontalScrollOffset(), this.recyclerViewWidth);
        if (iScrollTo != 0) {
            this.recyclerView.scrollBy(iScrollTo, 0);
        }
        this.mHorizontalDragX = fMax;
    }

    private boolean isLayoutRTL() {
        return ViewCompat.getLayoutDirection(this.recyclerView) == 1;
    }

    @VisibleForTesting
    private boolean isPointInsideHorizontalThumb(float f2, float f3) {
        if (f3 >= this.recyclerViewHeight - this.mHorizontalThumbHeight) {
            int i2 = this.mHorizontalThumbCenterX;
            int i3 = this.mHorizontalThumbWidth;
            if (f2 >= i2 - (i3 / 2.0f) && f2 <= i2 + (i3 / 2.0f)) {
                return true;
            }
        }
        return false;
    }

    @VisibleForTesting
    private boolean isPointInsideVerticalThumb(float f2, float f3) {
        if (!isLayoutRTL() ? f2 >= this.recyclerViewWidth - this.mVerticalThumbWidth : f2 <= this.mVerticalThumbWidth / 2.0f) {
            int i2 = this.mVerticalThumbCenterY;
            int i3 = this.mVerticalThumbHeight;
            if (f3 >= i2 - (i3 / 2.0f) && f3 <= i2 + (i3 / 2.0f)) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        hide(500);
    }

    private void resetHideDelay(int i2) {
        cancelHide();
        this.recyclerView.postDelayed(this.mHideRunnable, i2);
    }

    private int scrollTo(float f2, float f3, int[] iArr, int i2, int i3, int i4) {
        int i5 = iArr[1] - iArr[0];
        if (i5 == 0) {
            return 0;
        }
        int i6 = i2 - i4;
        int i7 = (int) (((f3 - f2) / i5) * i6);
        int i8 = i3 + i7;
        if (i8 >= i6 || i8 < 0) {
            return 0;
        }
        return i7;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setState(int i2) {
        if (i2 == 2 && this.state != 2) {
            this.mVerticalThumbDrawable.setState(PRESSED_STATE_SET);
            cancelHide();
        }
        if (i2 == 0) {
            requestRedraw();
        } else {
            show();
        }
        if (this.state == 2 && i2 != 2) {
            this.mVerticalThumbDrawable.setState(EMPTY_STATE_SET);
            resetHideDelay(1200);
        } else if (i2 == 1) {
            resetHideDelay(1500);
        }
        this.state = i2;
    }

    private void setupCallbacks() {
        this.recyclerView.addItemDecoration(this);
        this.recyclerView.addOnItemTouchListener(this);
        this.recyclerView.addOnScrollListener(this.mOnScrollListener);
    }

    @SuppressLint({"SwitchIntDef"})
    private void show() {
        int i2 = this.mAnimationState;
        if (i2 != 0) {
            if (i2 != 3) {
                return;
            } else {
                this.mShowHideAnimator.cancel();
            }
        }
        this.mAnimationState = 1;
        ValueAnimator valueAnimator = this.mShowHideAnimator;
        valueAnimator.setFloatValues(((Float) valueAnimator.getAnimatedValue()).floatValue(), 1.0f);
        this.mShowHideAnimator.setDuration(500L);
        this.mShowHideAnimator.setStartDelay(0L);
        this.mShowHideAnimator.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateScrollPosition(int i2, int i3) {
        int iComputeVerticalScrollRange = this.recyclerView.computeVerticalScrollRange();
        int paddingBottom = this.recyclerViewHeight;
        if (this.mConsiderPadding) {
            paddingBottom -= this.recyclerView.getPaddingBottom();
        }
        this.mNeedVerticalScrollbar = iComputeVerticalScrollRange - paddingBottom > 0 && this.recyclerViewHeight >= this.mScrollbarMinimumRange;
        int iComputeHorizontalScrollRange = this.recyclerView.computeHorizontalScrollRange();
        int i4 = this.recyclerViewWidth;
        boolean z2 = iComputeHorizontalScrollRange - i4 > 0 && i4 >= this.mScrollbarMinimumRange;
        this.mNeedHorizontalScrollbar = z2;
        boolean z3 = this.mNeedVerticalScrollbar;
        if (!z3 && !z2) {
            if (this.state != 0) {
                setState(0);
                return;
            }
            return;
        }
        if (z3) {
            int iMin = Math.min(paddingBottom, (paddingBottom * paddingBottom) / iComputeVerticalScrollRange);
            int i5 = this.minThumbSize;
            if (i5 <= 0) {
                float f2 = paddingBottom;
                this.mVerticalThumbCenterY = (int) ((f2 * (i3 + (f2 / 2.0f))) / iComputeVerticalScrollRange);
                this.mVerticalThumbHeight = iMin;
            } else {
                this.mVerticalThumbCenterY = (int) ((((paddingBottom - r10) / (iComputeVerticalScrollRange - paddingBottom)) * i3) + (this.mVerticalThumbHeight / 2.0d));
                this.mVerticalThumbHeight = Math.max(i5, iMin);
            }
        }
        if (this.mNeedHorizontalScrollbar) {
            int iMin2 = Math.min(i4, (i4 * i4) / iComputeHorizontalScrollRange);
            int i6 = this.minThumbSize;
            if (i6 <= 0) {
                float f3 = i4;
                this.mHorizontalThumbCenterX = (int) ((f3 * (i2 + (f3 / 2.0f))) / iComputeHorizontalScrollRange);
                this.mHorizontalThumbWidth = iMin2;
            } else {
                this.mHorizontalThumbCenterX = (int) ((((i4 - this.mHorizontalThumbCenterX) / (iComputeHorizontalScrollRange - i4)) * i2) + (this.mHorizontalThumbHeight / 2.0d));
                this.mHorizontalThumbWidth = Math.max(i6, iMin2);
            }
        }
        int i7 = this.state;
        if (i7 == 0 || i7 == 1) {
            setState(1);
        }
    }

    private void verticalScrollTo(float f2) {
        int[] verticalRange = getVerticalRange();
        if (Math.abs(this.mVerticalThumbCenterY - f2) < 2.0f) {
            return;
        }
        int paddingBottom = this.recyclerViewHeight;
        if (this.mConsiderPadding) {
            paddingBottom -= this.recyclerView.getPaddingBottom();
        }
        int iScrollTo = scrollTo(this.mVerticalDragY, f2, verticalRange, this.recyclerView.computeVerticalScrollRange(), this.recyclerView.computeVerticalScrollOffset(), paddingBottom);
        if (iScrollTo != 0) {
            this.recyclerView.scrollBy(0, iScrollTo);
        }
        this.mVerticalDragY = f2;
    }

    @VisibleForTesting
    public Drawable getHorizontalThumbDrawable() {
        return this.mHorizontalThumbDrawable;
    }

    @VisibleForTesting
    public Drawable getHorizontalTrackDrawable() {
        return this.mHorizontalTrackDrawable;
    }

    @VisibleForTesting
    public Drawable getVerticalThumbDrawable() {
        return this.mVerticalThumbDrawable;
    }

    @VisibleForTesting
    public Drawable getVerticalTrackDrawable() {
        return this.mVerticalTrackDrawable;
    }

    public boolean isDragging() {
        return this.state == 2;
    }

    @VisibleForTesting
    public boolean isVisible() {
        return this.state == 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void onDrawOver(@NotNull Canvas canvas, @NotNull RecyclerView recyclerView, @NotNull RecyclerView.State state) {
        if (this.recyclerViewWidth != this.recyclerView.getWidth() || this.recyclerViewHeight != this.recyclerView.getHeight()) {
            this.recyclerViewWidth = this.recyclerView.getWidth();
            this.recyclerViewHeight = this.recyclerView.getHeight();
            setState(0);
        } else if (this.mAnimationState != 0) {
            if (this.mNeedVerticalScrollbar) {
                drawVerticalScrollbar(canvas);
            }
            if (this.mNeedHorizontalScrollbar) {
                drawHorizontalScrollbar(canvas);
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
    public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
        int i2 = this.state;
        if (i2 == 1) {
            boolean zIsPointInsideVerticalThumb = isPointInsideVerticalThumb(motionEvent.getX(), motionEvent.getY());
            boolean zIsPointInsideHorizontalThumb = isPointInsideHorizontalThumb(motionEvent.getX(), motionEvent.getY());
            if (motionEvent.getAction() != 0) {
                return false;
            }
            if (!zIsPointInsideVerticalThumb && !zIsPointInsideHorizontalThumb) {
                return false;
            }
            if (zIsPointInsideHorizontalThumb) {
                this.dragState = 1;
                this.mHorizontalDragX = (int) motionEvent.getX();
            } else {
                this.dragState = 2;
                this.mVerticalDragY = (int) motionEvent.getY();
            }
            setState(2);
        } else if (i2 != 2) {
            return false;
        }
        return true;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
    public void onRequestDisallowInterceptTouchEvent(boolean z2) {
    }

    @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
    public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
        if (this.state == 0) {
            return;
        }
        if (motionEvent.getAction() == 0) {
            boolean zIsPointInsideVerticalThumb = isPointInsideVerticalThumb(motionEvent.getX(), motionEvent.getY());
            boolean zIsPointInsideHorizontalThumb = isPointInsideHorizontalThumb(motionEvent.getX(), motionEvent.getY());
            if (zIsPointInsideVerticalThumb || zIsPointInsideHorizontalThumb) {
                if (zIsPointInsideHorizontalThumb) {
                    this.dragState = 1;
                    this.mHorizontalDragX = (int) motionEvent.getX();
                } else {
                    this.dragState = 2;
                    this.mVerticalDragY = (int) motionEvent.getY();
                }
                setState(2);
                return;
            }
            return;
        }
        if (motionEvent.getAction() == 1 && this.state == 2) {
            this.mVerticalDragY = 0.0f;
            this.mHorizontalDragX = 0.0f;
            setState(1);
            this.dragState = 0;
            return;
        }
        if (motionEvent.getAction() == 2 && this.state == 2) {
            show();
            if (this.dragState == 1) {
                horizontalScrollTo(motionEvent.getX());
            }
            if (this.dragState == 2) {
                verticalScrollTo(motionEvent.getY());
            }
        }
    }

    public void requestRedraw() {
        this.recyclerView.invalidate();
    }
}
