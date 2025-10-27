package com.google.android.material.bottomsheet;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.annotation.RestrictTo;
import androidx.annotation.StringRes;
import androidx.annotation.VisibleForTesting;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.math.MathUtils;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import androidx.customview.view.AbsSavedState;
import androidx.customview.widget.ViewDragHelper;
import com.google.android.material.R;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public class BottomSheetBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {
    private static final int CORNER_ANIMATION_DURATION = 500;
    private static final int DEF_STYLE_RES = R.style.Widget_Design_BottomSheet_Modal;
    private static final float HIDE_FRICTION = 0.1f;
    private static final float HIDE_THRESHOLD = 0.5f;
    private static final int NO_MAX_SIZE = -1;
    public static final int PEEK_HEIGHT_AUTO = -1;
    public static final int SAVE_ALL = -1;
    public static final int SAVE_FIT_TO_CONTENTS = 2;
    public static final int SAVE_HIDEABLE = 4;
    public static final int SAVE_NONE = 0;
    public static final int SAVE_PEEK_HEIGHT = 1;
    public static final int SAVE_SKIP_COLLAPSED = 8;
    private static final int SIGNIFICANT_VEL_THRESHOLD = 500;
    public static final int STATE_COLLAPSED = 4;
    public static final int STATE_DRAGGING = 1;
    public static final int STATE_EXPANDED = 3;
    public static final int STATE_HALF_EXPANDED = 6;
    public static final int STATE_HIDDEN = 5;
    public static final int STATE_SETTLING = 2;
    private static final String TAG = "BottomSheetBehavior";
    int activePointerId;

    @NonNull
    private final ArrayList<BottomSheetCallback> callbacks;
    private int childHeight;
    int collapsedOffset;
    private final ViewDragHelper.Callback dragCallback;
    private boolean draggable;
    float elevation;
    private int expandHalfwayActionId;
    int expandedOffset;
    private boolean fitToContents;
    int fitToContentsOffset;
    private int gestureInsetBottom;
    private boolean gestureInsetBottomIgnored;
    int halfExpandedOffset;
    float halfExpandedRatio;
    boolean hideable;
    private boolean ignoreEvents;

    @Nullable
    private Map<View, Integer> importantForAccessibilityMap;
    private int initialY;
    private int insetBottom;
    private int insetTop;

    @Nullable
    private ValueAnimator interpolatorAnimator;
    private boolean isShapeExpanded;
    private int lastNestedScrollDy;
    int lastStableState;
    private MaterialShapeDrawable materialShapeDrawable;
    private int maxHeight;
    private int maxWidth;
    private float maximumVelocity;
    private boolean nestedScrolled;

    @Nullable
    WeakReference<View> nestedScrollingChildRef;
    private boolean paddingBottomSystemWindowInsets;
    private boolean paddingLeftSystemWindowInsets;
    private boolean paddingRightSystemWindowInsets;
    private boolean paddingTopSystemWindowInsets;
    int parentHeight;
    int parentWidth;
    private int peekHeight;
    private boolean peekHeightAuto;
    private int peekHeightGestureInsetBuffer;
    private int peekHeightMin;
    private int saveFlags;
    private BottomSheetBehavior<V>.SettleRunnable settleRunnable;
    private ShapeAppearanceModel shapeAppearanceModelDefault;
    private boolean shapeThemingEnabled;
    private boolean skipCollapsed;
    int state;
    boolean touchingScrollingChild;
    private boolean updateImportantForAccessibilityOnSiblings;

    @Nullable
    private VelocityTracker velocityTracker;

    @Nullable
    ViewDragHelper viewDragHelper;

    @Nullable
    WeakReference<V> viewRef;

    public static abstract class BottomSheetCallback {
        public abstract void onSlide(@NonNull View view, float f2);

        public abstract void onStateChanged(@NonNull View view, int i2);
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface SaveFlags {
    }

    public static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() { // from class: com.google.android.material.bottomsheet.BottomSheetBehavior.SavedState.1
            @Override // android.os.Parcelable.Creator
            @NonNull
            public SavedState[] newArray(int i2) {
                return new SavedState[i2];
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.ClassLoaderCreator
            @NonNull
            public SavedState createFromParcel(@NonNull Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            @Override // android.os.Parcelable.Creator
            @Nullable
            public SavedState createFromParcel(@NonNull Parcel parcel) {
                return new SavedState(parcel, (ClassLoader) null);
            }
        };
        boolean fitToContents;
        boolean hideable;
        int peekHeight;
        boolean skipCollapsed;
        final int state;

        public SavedState(@NonNull Parcel parcel) {
            this(parcel, (ClassLoader) null);
        }

        @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(@NonNull Parcel parcel, int i2) {
            super.writeToParcel(parcel, i2);
            parcel.writeInt(this.state);
            parcel.writeInt(this.peekHeight);
            parcel.writeInt(this.fitToContents ? 1 : 0);
            parcel.writeInt(this.hideable ? 1 : 0);
            parcel.writeInt(this.skipCollapsed ? 1 : 0);
        }

        public SavedState(@NonNull Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.state = parcel.readInt();
            this.peekHeight = parcel.readInt();
            this.fitToContents = parcel.readInt() == 1;
            this.hideable = parcel.readInt() == 1;
            this.skipCollapsed = parcel.readInt() == 1;
        }

        public SavedState(Parcelable parcelable, @NonNull BottomSheetBehavior<?> bottomSheetBehavior) {
            super(parcelable);
            this.state = bottomSheetBehavior.state;
            this.peekHeight = ((BottomSheetBehavior) bottomSheetBehavior).peekHeight;
            this.fitToContents = ((BottomSheetBehavior) bottomSheetBehavior).fitToContents;
            this.hideable = bottomSheetBehavior.hideable;
            this.skipCollapsed = ((BottomSheetBehavior) bottomSheetBehavior).skipCollapsed;
        }

        @Deprecated
        public SavedState(Parcelable parcelable, int i2) {
            super(parcelable);
            this.state = i2;
        }
    }

    public class SettleRunnable implements Runnable {
        private boolean isPosted;
        int targetState;
        private final View view;

        public SettleRunnable(View view, int i2) {
            this.view = view;
            this.targetState = i2;
        }

        @Override // java.lang.Runnable
        public void run() {
            ViewDragHelper viewDragHelper = BottomSheetBehavior.this.viewDragHelper;
            if (viewDragHelper == null || !viewDragHelper.continueSettling(true)) {
                BottomSheetBehavior.this.setStateInternal(this.targetState);
            } else {
                ViewCompat.postOnAnimation(this.view, this);
            }
            this.isPosted = false;
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface State {
    }

    public BottomSheetBehavior() {
        this.saveFlags = 0;
        this.fitToContents = true;
        this.updateImportantForAccessibilityOnSiblings = false;
        this.maxWidth = -1;
        this.maxHeight = -1;
        this.settleRunnable = null;
        this.halfExpandedRatio = 0.5f;
        this.elevation = -1.0f;
        this.draggable = true;
        this.state = 4;
        this.lastStableState = 4;
        this.callbacks = new ArrayList<>();
        this.expandHalfwayActionId = -1;
        this.dragCallback = new ViewDragHelper.Callback() { // from class: com.google.android.material.bottomsheet.BottomSheetBehavior.4
            private long viewCapturedMillis;

            private boolean releasedLow(@NonNull View view) {
                int top2 = view.getTop();
                BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.this;
                return top2 > (bottomSheetBehavior.parentHeight + bottomSheetBehavior.getExpandedOffset()) / 2;
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int clampViewPositionHorizontal(@NonNull View view, int i2, int i3) {
                return view.getLeft();
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int clampViewPositionVertical(@NonNull View view, int i2, int i3) {
                int expandedOffset = BottomSheetBehavior.this.getExpandedOffset();
                BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.this;
                return MathUtils.clamp(i2, expandedOffset, bottomSheetBehavior.hideable ? bottomSheetBehavior.parentHeight : bottomSheetBehavior.collapsedOffset);
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int getViewVerticalDragRange(@NonNull View view) {
                BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.this;
                return bottomSheetBehavior.hideable ? bottomSheetBehavior.parentHeight : bottomSheetBehavior.collapsedOffset;
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public void onViewDragStateChanged(int i2) {
                if (i2 == 1 && BottomSheetBehavior.this.draggable) {
                    BottomSheetBehavior.this.setStateInternal(1);
                }
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public void onViewPositionChanged(@NonNull View view, int i2, int i3, int i4, int i5) {
                BottomSheetBehavior.this.dispatchOnSlide(i3);
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public void onViewReleased(@NonNull View view, float f2, float f3) {
                int expandedOffset;
                int i2 = 6;
                if (f3 < 0.0f) {
                    if (BottomSheetBehavior.this.fitToContents) {
                        expandedOffset = BottomSheetBehavior.this.fitToContentsOffset;
                    } else {
                        int top2 = view.getTop();
                        long jCurrentTimeMillis = System.currentTimeMillis() - this.viewCapturedMillis;
                        if (BottomSheetBehavior.this.shouldSkipHalfExpandedStateWhenDragging()) {
                            if (BottomSheetBehavior.this.shouldExpandOnUpwardDrag(jCurrentTimeMillis, (top2 * 100.0f) / r10.parentHeight)) {
                                expandedOffset = BottomSheetBehavior.this.expandedOffset;
                            } else {
                                expandedOffset = BottomSheetBehavior.this.collapsedOffset;
                                i2 = 4;
                            }
                        } else {
                            BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.this;
                            int i3 = bottomSheetBehavior.halfExpandedOffset;
                            if (top2 > i3) {
                                expandedOffset = i3;
                            } else {
                                expandedOffset = bottomSheetBehavior.getExpandedOffset();
                            }
                        }
                    }
                    i2 = 3;
                } else {
                    BottomSheetBehavior bottomSheetBehavior2 = BottomSheetBehavior.this;
                    if (bottomSheetBehavior2.hideable && bottomSheetBehavior2.shouldHide(view, f3)) {
                        if ((Math.abs(f2) >= Math.abs(f3) || f3 <= 500.0f) && !releasedLow(view)) {
                            if (BottomSheetBehavior.this.fitToContents) {
                                expandedOffset = BottomSheetBehavior.this.fitToContentsOffset;
                            } else if (Math.abs(view.getTop() - BottomSheetBehavior.this.getExpandedOffset()) < Math.abs(view.getTop() - BottomSheetBehavior.this.halfExpandedOffset)) {
                                expandedOffset = BottomSheetBehavior.this.getExpandedOffset();
                            } else {
                                expandedOffset = BottomSheetBehavior.this.halfExpandedOffset;
                            }
                            i2 = 3;
                        } else {
                            expandedOffset = BottomSheetBehavior.this.parentHeight;
                            i2 = 5;
                        }
                    } else if (f3 == 0.0f || Math.abs(f2) > Math.abs(f3)) {
                        int top3 = view.getTop();
                        if (!BottomSheetBehavior.this.fitToContents) {
                            BottomSheetBehavior bottomSheetBehavior3 = BottomSheetBehavior.this;
                            int i4 = bottomSheetBehavior3.halfExpandedOffset;
                            if (top3 < i4) {
                                if (top3 < Math.abs(top3 - bottomSheetBehavior3.collapsedOffset)) {
                                    expandedOffset = BottomSheetBehavior.this.getExpandedOffset();
                                    i2 = 3;
                                } else if (BottomSheetBehavior.this.shouldSkipHalfExpandedStateWhenDragging()) {
                                    expandedOffset = BottomSheetBehavior.this.collapsedOffset;
                                    i2 = 4;
                                } else {
                                    expandedOffset = BottomSheetBehavior.this.halfExpandedOffset;
                                }
                            } else if (Math.abs(top3 - i4) >= Math.abs(top3 - BottomSheetBehavior.this.collapsedOffset) || BottomSheetBehavior.this.shouldSkipHalfExpandedStateWhenDragging()) {
                                expandedOffset = BottomSheetBehavior.this.collapsedOffset;
                                i2 = 4;
                            } else {
                                expandedOffset = BottomSheetBehavior.this.halfExpandedOffset;
                            }
                        } else if (Math.abs(top3 - BottomSheetBehavior.this.fitToContentsOffset) < Math.abs(top3 - BottomSheetBehavior.this.collapsedOffset)) {
                            expandedOffset = BottomSheetBehavior.this.fitToContentsOffset;
                            i2 = 3;
                        } else {
                            expandedOffset = BottomSheetBehavior.this.collapsedOffset;
                            i2 = 4;
                        }
                    } else {
                        if (BottomSheetBehavior.this.fitToContents) {
                            expandedOffset = BottomSheetBehavior.this.collapsedOffset;
                        } else {
                            int top4 = view.getTop();
                            if (Math.abs(top4 - BottomSheetBehavior.this.halfExpandedOffset) >= Math.abs(top4 - BottomSheetBehavior.this.collapsedOffset) || BottomSheetBehavior.this.shouldSkipHalfExpandedStateWhenDragging()) {
                                expandedOffset = BottomSheetBehavior.this.collapsedOffset;
                            } else {
                                expandedOffset = BottomSheetBehavior.this.halfExpandedOffset;
                            }
                        }
                        i2 = 4;
                    }
                }
                BottomSheetBehavior bottomSheetBehavior4 = BottomSheetBehavior.this;
                bottomSheetBehavior4.startSettlingAnimation(view, i2, expandedOffset, bottomSheetBehavior4.shouldSkipSmoothAnimation());
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public boolean tryCaptureView(@NonNull View view, int i2) {
                BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.this;
                int i3 = bottomSheetBehavior.state;
                if (i3 == 1 || bottomSheetBehavior.touchingScrollingChild) {
                    return false;
                }
                if (i3 == 3 && bottomSheetBehavior.activePointerId == i2) {
                    WeakReference<View> weakReference = bottomSheetBehavior.nestedScrollingChildRef;
                    View view2 = weakReference != null ? weakReference.get() : null;
                    if (view2 != null && view2.canScrollVertically(-1)) {
                        return false;
                    }
                }
                this.viewCapturedMillis = System.currentTimeMillis();
                WeakReference<V> weakReference2 = BottomSheetBehavior.this.viewRef;
                return weakReference2 != null && weakReference2.get() == view;
            }
        };
    }

    private int addAccessibilityActionForState(V v2, @StringRes int i2, int i3) {
        return ViewCompat.addAccessibilityAction(v2, v2.getResources().getString(i2), createAccessibilityViewCommandForState(i3));
    }

    private void calculateCollapsedOffset() {
        int iCalculatePeekHeight = calculatePeekHeight();
        if (this.fitToContents) {
            this.collapsedOffset = Math.max(this.parentHeight - iCalculatePeekHeight, this.fitToContentsOffset);
        } else {
            this.collapsedOffset = this.parentHeight - iCalculatePeekHeight;
        }
    }

    private void calculateHalfExpandedOffset() {
        this.halfExpandedOffset = (int) (this.parentHeight * (1.0f - this.halfExpandedRatio));
    }

    private int calculatePeekHeight() {
        int i2;
        return this.peekHeightAuto ? Math.min(Math.max(this.peekHeightMin, this.parentHeight - ((this.parentWidth * 9) / 16)), this.childHeight) + this.insetBottom : (this.gestureInsetBottomIgnored || this.paddingBottomSystemWindowInsets || (i2 = this.gestureInsetBottom) <= 0) ? this.peekHeight + this.insetBottom : Math.max(this.peekHeight, i2 + this.peekHeightGestureInsetBuffer);
    }

    private AccessibilityViewCommand createAccessibilityViewCommandForState(final int i2) {
        return new AccessibilityViewCommand() { // from class: com.google.android.material.bottomsheet.BottomSheetBehavior.5
            @Override // androidx.core.view.accessibility.AccessibilityViewCommand
            public boolean perform(@NonNull View view, @Nullable AccessibilityViewCommand.CommandArguments commandArguments) {
                BottomSheetBehavior.this.setState(i2);
                return true;
            }
        };
    }

    private void createMaterialShapeDrawable(@NonNull Context context, AttributeSet attributeSet, boolean z2) {
        createMaterialShapeDrawable(context, attributeSet, z2, null);
    }

    private void createShapeValueAnimator() {
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.interpolatorAnimator = valueAnimatorOfFloat;
        valueAnimatorOfFloat.setDuration(500L);
        this.interpolatorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.bottomsheet.BottomSheetBehavior.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator) {
                float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                if (BottomSheetBehavior.this.materialShapeDrawable != null) {
                    BottomSheetBehavior.this.materialShapeDrawable.setInterpolation(fFloatValue);
                }
            }
        });
    }

    @NonNull
    public static <V extends View> BottomSheetBehavior<V> from(@NonNull V v2) {
        ViewGroup.LayoutParams layoutParams = v2.getLayoutParams();
        if (!(layoutParams instanceof CoordinatorLayout.LayoutParams)) {
            throw new IllegalArgumentException("The view is not a child of CoordinatorLayout");
        }
        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) layoutParams).getBehavior();
        if (behavior instanceof BottomSheetBehavior) {
            return (BottomSheetBehavior) behavior;
        }
        throw new IllegalArgumentException("The view is not associated with BottomSheetBehavior");
    }

    private int getChildMeasureSpec(int i2, int i3, int i4, int i5) {
        int childMeasureSpec = ViewGroup.getChildMeasureSpec(i2, i3, i5);
        if (i4 == -1) {
            return childMeasureSpec;
        }
        int mode = View.MeasureSpec.getMode(childMeasureSpec);
        int size = View.MeasureSpec.getSize(childMeasureSpec);
        if (mode == 1073741824) {
            return View.MeasureSpec.makeMeasureSpec(Math.min(size, i4), 1073741824);
        }
        if (size != 0) {
            i4 = Math.min(size, i4);
        }
        return View.MeasureSpec.makeMeasureSpec(i4, Integer.MIN_VALUE);
    }

    private float getYVelocity() {
        VelocityTracker velocityTracker = this.velocityTracker;
        if (velocityTracker == null) {
            return 0.0f;
        }
        velocityTracker.computeCurrentVelocity(1000, this.maximumVelocity);
        return this.velocityTracker.getYVelocity(this.activePointerId);
    }

    private void replaceAccessibilityActionForState(V v2, AccessibilityNodeInfoCompat.AccessibilityActionCompat accessibilityActionCompat, int i2) {
        ViewCompat.replaceAccessibilityAction(v2, accessibilityActionCompat, null, createAccessibilityViewCommandForState(i2));
    }

    private void reset() {
        this.activePointerId = -1;
        VelocityTracker velocityTracker = this.velocityTracker;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.velocityTracker = null;
        }
    }

    private void restoreOptionalState(@NonNull SavedState savedState) {
        int i2 = this.saveFlags;
        if (i2 == 0) {
            return;
        }
        if (i2 == -1 || (i2 & 1) == 1) {
            this.peekHeight = savedState.peekHeight;
        }
        if (i2 == -1 || (i2 & 2) == 2) {
            this.fitToContents = savedState.fitToContents;
        }
        if (i2 == -1 || (i2 & 4) == 4) {
            this.hideable = savedState.hideable;
        }
        if (i2 == -1 || (i2 & 8) == 8) {
            this.skipCollapsed = savedState.skipCollapsed;
        }
    }

    private void setWindowInsetsListener(@NonNull View view) {
        final boolean z2 = (Build.VERSION.SDK_INT < 29 || isGestureInsetBottomIgnored() || this.peekHeightAuto) ? false : true;
        if (this.paddingBottomSystemWindowInsets || this.paddingLeftSystemWindowInsets || this.paddingRightSystemWindowInsets || z2) {
            ViewUtils.doOnApplyWindowInsets(view, new ViewUtils.OnApplyWindowInsetsListener() { // from class: com.google.android.material.bottomsheet.BottomSheetBehavior.3
                @Override // com.google.android.material.internal.ViewUtils.OnApplyWindowInsetsListener
                public WindowInsetsCompat onApplyWindowInsets(View view2, WindowInsetsCompat windowInsetsCompat, ViewUtils.RelativePadding relativePadding) {
                    BottomSheetBehavior.this.insetTop = windowInsetsCompat.getSystemWindowInsetTop();
                    boolean zIsLayoutRtl = ViewUtils.isLayoutRtl(view2);
                    int paddingBottom = view2.getPaddingBottom();
                    int paddingLeft = view2.getPaddingLeft();
                    int paddingRight = view2.getPaddingRight();
                    if (BottomSheetBehavior.this.paddingBottomSystemWindowInsets) {
                        BottomSheetBehavior.this.insetBottom = windowInsetsCompat.getSystemWindowInsetBottom();
                        paddingBottom = relativePadding.bottom + BottomSheetBehavior.this.insetBottom;
                    }
                    if (BottomSheetBehavior.this.paddingLeftSystemWindowInsets) {
                        paddingLeft = (zIsLayoutRtl ? relativePadding.end : relativePadding.start) + windowInsetsCompat.getSystemWindowInsetLeft();
                    }
                    if (BottomSheetBehavior.this.paddingRightSystemWindowInsets) {
                        paddingRight = (zIsLayoutRtl ? relativePadding.start : relativePadding.end) + windowInsetsCompat.getSystemWindowInsetRight();
                    }
                    view2.setPadding(paddingLeft, view2.getPaddingTop(), paddingRight, paddingBottom);
                    if (z2) {
                        BottomSheetBehavior.this.gestureInsetBottom = windowInsetsCompat.getMandatorySystemGestureInsets().bottom;
                    }
                    if (BottomSheetBehavior.this.paddingBottomSystemWindowInsets || z2) {
                        BottomSheetBehavior.this.updatePeekHeight(false);
                    }
                    return windowInsetsCompat;
                }
            });
        }
    }

    private void settleToStatePendingLayout(final int i2) {
        final V v2 = this.viewRef.get();
        if (v2 == null) {
            return;
        }
        ViewParent parent = v2.getParent();
        if (parent != null && parent.isLayoutRequested() && ViewCompat.isAttachedToWindow(v2)) {
            v2.post(new Runnable() { // from class: com.google.android.material.bottomsheet.BottomSheetBehavior.1
                @Override // java.lang.Runnable
                public void run() {
                    BottomSheetBehavior.this.settleToState(v2, i2);
                }
            });
        } else {
            settleToState(v2, i2);
        }
    }

    private boolean shouldHandleDraggingWithHelper() {
        return this.viewDragHelper != null && (this.draggable || this.state == 1);
    }

    private void updateAccessibilityActions() {
        V v2;
        WeakReference<V> weakReference = this.viewRef;
        if (weakReference == null || (v2 = weakReference.get()) == null) {
            return;
        }
        ViewCompat.removeAccessibilityAction(v2, 524288);
        ViewCompat.removeAccessibilityAction(v2, 262144);
        ViewCompat.removeAccessibilityAction(v2, 1048576);
        int i2 = this.expandHalfwayActionId;
        if (i2 != -1) {
            ViewCompat.removeAccessibilityAction(v2, i2);
        }
        if (!this.fitToContents && this.state != 6) {
            this.expandHalfwayActionId = addAccessibilityActionForState(v2, R.string.bottomsheet_action_expand_halfway, 6);
        }
        if (this.hideable && this.state != 5) {
            replaceAccessibilityActionForState(v2, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_DISMISS, 5);
        }
        int i3 = this.state;
        if (i3 == 3) {
            replaceAccessibilityActionForState(v2, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_COLLAPSE, this.fitToContents ? 4 : 6);
            return;
        }
        if (i3 == 4) {
            replaceAccessibilityActionForState(v2, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_EXPAND, this.fitToContents ? 3 : 6);
        } else {
            if (i3 != 6) {
                return;
            }
            replaceAccessibilityActionForState(v2, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_COLLAPSE, 4);
            replaceAccessibilityActionForState(v2, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_EXPAND, 3);
        }
    }

    private void updateDrawableForTargetState(int i2) {
        ValueAnimator valueAnimator;
        if (i2 == 2) {
            return;
        }
        boolean z2 = i2 == 3;
        if (this.isShapeExpanded != z2) {
            this.isShapeExpanded = z2;
            if (this.materialShapeDrawable == null || (valueAnimator = this.interpolatorAnimator) == null) {
                return;
            }
            if (valueAnimator.isRunning()) {
                this.interpolatorAnimator.reverse();
                return;
            }
            float f2 = z2 ? 0.0f : 1.0f;
            this.interpolatorAnimator.setFloatValues(1.0f - f2, f2);
            this.interpolatorAnimator.start();
        }
    }

    private void updateImportantForAccessibility(boolean z2) {
        Map<View, Integer> map;
        WeakReference<V> weakReference = this.viewRef;
        if (weakReference == null) {
            return;
        }
        ViewParent parent = weakReference.get().getParent();
        if (parent instanceof CoordinatorLayout) {
            CoordinatorLayout coordinatorLayout = (CoordinatorLayout) parent;
            int childCount = coordinatorLayout.getChildCount();
            if (z2) {
                if (this.importantForAccessibilityMap != null) {
                    return;
                } else {
                    this.importantForAccessibilityMap = new HashMap(childCount);
                }
            }
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = coordinatorLayout.getChildAt(i2);
                if (childAt != this.viewRef.get()) {
                    if (z2) {
                        this.importantForAccessibilityMap.put(childAt, Integer.valueOf(childAt.getImportantForAccessibility()));
                        if (this.updateImportantForAccessibilityOnSiblings) {
                            ViewCompat.setImportantForAccessibility(childAt, 4);
                        }
                    } else if (this.updateImportantForAccessibilityOnSiblings && (map = this.importantForAccessibilityMap) != null && map.containsKey(childAt)) {
                        ViewCompat.setImportantForAccessibility(childAt, this.importantForAccessibilityMap.get(childAt).intValue());
                    }
                }
            }
            if (!z2) {
                this.importantForAccessibilityMap = null;
            } else if (this.updateImportantForAccessibilityOnSiblings) {
                this.viewRef.get().sendAccessibilityEvent(8);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updatePeekHeight(boolean z2) {
        V v2;
        if (this.viewRef != null) {
            calculateCollapsedOffset();
            if (this.state != 4 || (v2 = this.viewRef.get()) == null) {
                return;
            }
            if (z2) {
                settleToStatePendingLayout(this.state);
            } else {
                v2.requestLayout();
            }
        }
    }

    public void addBottomSheetCallback(@NonNull BottomSheetCallback bottomSheetCallback) {
        if (this.callbacks.contains(bottomSheetCallback)) {
            return;
        }
        this.callbacks.add(bottomSheetCallback);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    @VisibleForTesting
    public void disableShapeAnimations() {
        this.interpolatorAnimator = null;
    }

    public void dispatchOnSlide(int i2) {
        float f2;
        float expandedOffset;
        V v2 = this.viewRef.get();
        if (v2 == null || this.callbacks.isEmpty()) {
            return;
        }
        int i3 = this.collapsedOffset;
        if (i2 > i3 || i3 == getExpandedOffset()) {
            int i4 = this.collapsedOffset;
            f2 = i4 - i2;
            expandedOffset = this.parentHeight - i4;
        } else {
            int i5 = this.collapsedOffset;
            f2 = i5 - i2;
            expandedOffset = i5 - getExpandedOffset();
        }
        float f3 = f2 / expandedOffset;
        for (int i6 = 0; i6 < this.callbacks.size(); i6++) {
            this.callbacks.get(i6).onSlide(v2, f3);
        }
    }

    @Nullable
    @VisibleForTesting
    public View findScrollingChild(View view) {
        if (ViewCompat.isNestedScrollingEnabled(view)) {
            return view;
        }
        if (!(view instanceof ViewGroup)) {
            return null;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        int childCount = viewGroup.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View viewFindScrollingChild = findScrollingChild(viewGroup.getChildAt(i2));
            if (viewFindScrollingChild != null) {
                return viewFindScrollingChild;
            }
        }
        return null;
    }

    public int getExpandedOffset() {
        if (this.fitToContents) {
            return this.fitToContentsOffset;
        }
        return Math.max(this.expandedOffset, this.paddingTopSystemWindowInsets ? 0 : this.insetTop);
    }

    @FloatRange(from = 0.0d, to = 1.0d)
    public float getHalfExpandedRatio() {
        return this.halfExpandedRatio;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public int getLastStableState() {
        return this.lastStableState;
    }

    public MaterialShapeDrawable getMaterialShapeDrawable() {
        return this.materialShapeDrawable;
    }

    @Px
    public int getMaxHeight() {
        return this.maxHeight;
    }

    @Px
    public int getMaxWidth() {
        return this.maxWidth;
    }

    public int getPeekHeight() {
        if (this.peekHeightAuto) {
            return -1;
        }
        return this.peekHeight;
    }

    @VisibleForTesting
    public int getPeekHeightMin() {
        return this.peekHeightMin;
    }

    public int getSaveFlags() {
        return this.saveFlags;
    }

    public boolean getSkipCollapsed() {
        return this.skipCollapsed;
    }

    public int getState() {
        return this.state;
    }

    public boolean isDraggable() {
        return this.draggable;
    }

    public boolean isFitToContents() {
        return this.fitToContents;
    }

    public boolean isGestureInsetBottomIgnored() {
        return this.gestureInsetBottomIgnored;
    }

    public boolean isHideable() {
        return this.hideable;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public boolean isNestedScrollingCheckEnabled() {
        return true;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public void onAttachedToLayoutParams(@NonNull CoordinatorLayout.LayoutParams layoutParams) {
        super.onAttachedToLayoutParams(layoutParams);
        this.viewRef = null;
        this.viewDragHelper = null;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public void onDetachedFromLayoutParams() {
        super.onDetachedFromLayoutParams();
        this.viewRef = null;
        this.viewDragHelper = null;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onInterceptTouchEvent(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v2, @NonNull MotionEvent motionEvent) {
        ViewDragHelper viewDragHelper;
        if (!v2.isShown() || !this.draggable) {
            this.ignoreEvents = true;
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            reset();
        }
        if (this.velocityTracker == null) {
            this.velocityTracker = VelocityTracker.obtain();
        }
        this.velocityTracker.addMovement(motionEvent);
        if (actionMasked == 0) {
            int x2 = (int) motionEvent.getX();
            this.initialY = (int) motionEvent.getY();
            if (this.state != 2) {
                WeakReference<View> weakReference = this.nestedScrollingChildRef;
                View view = weakReference != null ? weakReference.get() : null;
                if (view != null && coordinatorLayout.isPointInChildBounds(view, x2, this.initialY)) {
                    this.activePointerId = motionEvent.getPointerId(motionEvent.getActionIndex());
                    this.touchingScrollingChild = true;
                }
            }
            this.ignoreEvents = this.activePointerId == -1 && !coordinatorLayout.isPointInChildBounds(v2, x2, this.initialY);
        } else if (actionMasked == 1 || actionMasked == 3) {
            this.touchingScrollingChild = false;
            this.activePointerId = -1;
            if (this.ignoreEvents) {
                this.ignoreEvents = false;
                return false;
            }
        }
        if (!this.ignoreEvents && (viewDragHelper = this.viewDragHelper) != null && viewDragHelper.shouldInterceptTouchEvent(motionEvent)) {
            return true;
        }
        WeakReference<View> weakReference2 = this.nestedScrollingChildRef;
        View view2 = weakReference2 != null ? weakReference2.get() : null;
        return (actionMasked != 2 || view2 == null || this.ignoreEvents || this.state == 1 || coordinatorLayout.isPointInChildBounds(view2, (int) motionEvent.getX(), (int) motionEvent.getY()) || this.viewDragHelper == null || Math.abs(((float) this.initialY) - motionEvent.getY()) <= ((float) this.viewDragHelper.getTouchSlop())) ? false : true;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onLayoutChild(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v2, int i2) {
        MaterialShapeDrawable materialShapeDrawable;
        if (ViewCompat.getFitsSystemWindows(coordinatorLayout) && !ViewCompat.getFitsSystemWindows(v2)) {
            v2.setFitsSystemWindows(true);
        }
        if (this.viewRef == null) {
            this.peekHeightMin = coordinatorLayout.getResources().getDimensionPixelSize(R.dimen.design_bottom_sheet_peek_height_min);
            setWindowInsetsListener(v2);
            this.viewRef = new WeakReference<>(v2);
            if (this.shapeThemingEnabled && (materialShapeDrawable = this.materialShapeDrawable) != null) {
                ViewCompat.setBackground(v2, materialShapeDrawable);
            }
            MaterialShapeDrawable materialShapeDrawable2 = this.materialShapeDrawable;
            if (materialShapeDrawable2 != null) {
                float elevation = this.elevation;
                if (elevation == -1.0f) {
                    elevation = ViewCompat.getElevation(v2);
                }
                materialShapeDrawable2.setElevation(elevation);
                boolean z2 = this.state == 3;
                this.isShapeExpanded = z2;
                this.materialShapeDrawable.setInterpolation(z2 ? 0.0f : 1.0f);
            }
            updateAccessibilityActions();
            if (ViewCompat.getImportantForAccessibility(v2) == 0) {
                ViewCompat.setImportantForAccessibility(v2, 1);
            }
        }
        if (this.viewDragHelper == null) {
            this.viewDragHelper = ViewDragHelper.create(coordinatorLayout, this.dragCallback);
        }
        int top2 = v2.getTop();
        coordinatorLayout.onLayoutChild(v2, i2);
        this.parentWidth = coordinatorLayout.getWidth();
        this.parentHeight = coordinatorLayout.getHeight();
        int height = v2.getHeight();
        this.childHeight = height;
        int i3 = this.parentHeight;
        int i4 = i3 - height;
        int i5 = this.insetTop;
        if (i4 < i5) {
            if (this.paddingTopSystemWindowInsets) {
                this.childHeight = i3;
            } else {
                this.childHeight = i3 - i5;
            }
        }
        this.fitToContentsOffset = Math.max(0, i3 - this.childHeight);
        calculateHalfExpandedOffset();
        calculateCollapsedOffset();
        int i6 = this.state;
        if (i6 == 3) {
            ViewCompat.offsetTopAndBottom(v2, getExpandedOffset());
        } else if (i6 == 6) {
            ViewCompat.offsetTopAndBottom(v2, this.halfExpandedOffset);
        } else if (this.hideable && i6 == 5) {
            ViewCompat.offsetTopAndBottom(v2, this.parentHeight);
        } else if (i6 == 4) {
            ViewCompat.offsetTopAndBottom(v2, this.collapsedOffset);
        } else if (i6 == 1 || i6 == 2) {
            ViewCompat.offsetTopAndBottom(v2, top2 - v2.getTop());
        }
        this.nestedScrollingChildRef = new WeakReference<>(findScrollingChild(v2));
        return true;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onMeasureChild(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v2, int i2, int i3, int i4, int i5) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) v2.getLayoutParams();
        v2.measure(getChildMeasureSpec(i2, coordinatorLayout.getPaddingLeft() + coordinatorLayout.getPaddingRight() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + i3, this.maxWidth, marginLayoutParams.width), getChildMeasureSpec(i4, coordinatorLayout.getPaddingTop() + coordinatorLayout.getPaddingBottom() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin + i5, this.maxHeight, marginLayoutParams.height));
        return true;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onNestedPreFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v2, @NonNull View view, float f2, float f3) {
        WeakReference<View> weakReference;
        if (isNestedScrollingCheckEnabled() && (weakReference = this.nestedScrollingChildRef) != null && view == weakReference.get()) {
            return this.state != 3 || super.onNestedPreFling(coordinatorLayout, v2, view, f2, f3);
        }
        return false;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v2, @NonNull View view, int i2, int i3, @NonNull int[] iArr, int i4) {
        if (i4 == 1) {
            return;
        }
        WeakReference<View> weakReference = this.nestedScrollingChildRef;
        View view2 = weakReference != null ? weakReference.get() : null;
        if (!isNestedScrollingCheckEnabled() || view == view2) {
            int top2 = v2.getTop();
            int i5 = top2 - i3;
            if (i3 > 0) {
                if (i5 < getExpandedOffset()) {
                    int expandedOffset = top2 - getExpandedOffset();
                    iArr[1] = expandedOffset;
                    ViewCompat.offsetTopAndBottom(v2, -expandedOffset);
                    setStateInternal(3);
                } else {
                    if (!this.draggable) {
                        return;
                    }
                    iArr[1] = i3;
                    ViewCompat.offsetTopAndBottom(v2, -i3);
                    setStateInternal(1);
                }
            } else if (i3 < 0 && !view.canScrollVertically(-1)) {
                int i6 = this.collapsedOffset;
                if (i5 > i6 && !this.hideable) {
                    int i7 = top2 - i6;
                    iArr[1] = i7;
                    ViewCompat.offsetTopAndBottom(v2, -i7);
                    setStateInternal(4);
                } else {
                    if (!this.draggable) {
                        return;
                    }
                    iArr[1] = i3;
                    ViewCompat.offsetTopAndBottom(v2, -i3);
                    setStateInternal(1);
                }
            }
            dispatchOnSlide(v2.getTop());
            this.lastNestedScrollDy = i3;
            this.nestedScrolled = true;
        }
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v2, @NonNull View view, int i2, int i3, int i4, int i5, int i6, @NonNull int[] iArr) {
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public void onRestoreInstanceState(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v2, @NonNull Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(coordinatorLayout, v2, savedState.getSuperState());
        restoreOptionalState(savedState);
        int i2 = savedState.state;
        if (i2 == 1 || i2 == 2) {
            this.state = 4;
            this.lastStableState = 4;
        } else {
            this.state = i2;
            this.lastStableState = i2;
        }
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    @NonNull
    public Parcelable onSaveInstanceState(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v2) {
        return new SavedState(super.onSaveInstanceState(coordinatorLayout, v2), (BottomSheetBehavior<?>) this);
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v2, @NonNull View view, @NonNull View view2, int i2, int i3) {
        this.lastNestedScrollDy = 0;
        this.nestedScrolled = false;
        return (i2 & 2) != 0;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public void onStopNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v2, @NonNull View view, int i2) {
        int expandedOffset;
        WeakReference<View> weakReference;
        int i3 = 3;
        if (v2.getTop() == getExpandedOffset()) {
            setStateInternal(3);
            return;
        }
        if (!isNestedScrollingCheckEnabled() || ((weakReference = this.nestedScrollingChildRef) != null && view == weakReference.get() && this.nestedScrolled)) {
            if (this.lastNestedScrollDy > 0) {
                if (this.fitToContents) {
                    expandedOffset = this.fitToContentsOffset;
                } else {
                    int top2 = v2.getTop();
                    int i4 = this.halfExpandedOffset;
                    if (top2 > i4) {
                        i3 = 6;
                        expandedOffset = i4;
                    } else {
                        expandedOffset = getExpandedOffset();
                    }
                }
            } else if (this.hideable && shouldHide(v2, getYVelocity())) {
                expandedOffset = this.parentHeight;
                i3 = 5;
            } else if (this.lastNestedScrollDy == 0) {
                int top3 = v2.getTop();
                if (!this.fitToContents) {
                    int i5 = this.halfExpandedOffset;
                    if (top3 < i5) {
                        if (top3 < Math.abs(top3 - this.collapsedOffset)) {
                            expandedOffset = getExpandedOffset();
                        } else if (shouldSkipHalfExpandedStateWhenDragging()) {
                            expandedOffset = this.collapsedOffset;
                            i3 = 4;
                        } else {
                            expandedOffset = this.halfExpandedOffset;
                            i3 = 6;
                        }
                    } else if (Math.abs(top3 - i5) < Math.abs(top3 - this.collapsedOffset)) {
                        expandedOffset = this.halfExpandedOffset;
                        i3 = 6;
                    } else {
                        expandedOffset = this.collapsedOffset;
                        i3 = 4;
                    }
                } else if (Math.abs(top3 - this.fitToContentsOffset) < Math.abs(top3 - this.collapsedOffset)) {
                    expandedOffset = this.fitToContentsOffset;
                } else {
                    expandedOffset = this.collapsedOffset;
                    i3 = 4;
                }
            } else {
                if (this.fitToContents) {
                    expandedOffset = this.collapsedOffset;
                } else {
                    int top4 = v2.getTop();
                    if (Math.abs(top4 - this.halfExpandedOffset) < Math.abs(top4 - this.collapsedOffset)) {
                        expandedOffset = this.halfExpandedOffset;
                        i3 = 6;
                    } else {
                        expandedOffset = this.collapsedOffset;
                    }
                }
                i3 = 4;
            }
            startSettlingAnimation(v2, i3, expandedOffset, false);
            this.nestedScrolled = false;
        }
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onTouchEvent(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v2, @NonNull MotionEvent motionEvent) {
        if (!v2.isShown()) {
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (this.state == 1 && actionMasked == 0) {
            return true;
        }
        if (shouldHandleDraggingWithHelper()) {
            this.viewDragHelper.processTouchEvent(motionEvent);
        }
        if (actionMasked == 0) {
            reset();
        }
        if (this.velocityTracker == null) {
            this.velocityTracker = VelocityTracker.obtain();
        }
        this.velocityTracker.addMovement(motionEvent);
        if (shouldHandleDraggingWithHelper() && actionMasked == 2 && !this.ignoreEvents && Math.abs(this.initialY - motionEvent.getY()) > this.viewDragHelper.getTouchSlop()) {
            this.viewDragHelper.captureChildView(v2, motionEvent.getPointerId(motionEvent.getActionIndex()));
        }
        return !this.ignoreEvents;
    }

    public void removeBottomSheetCallback(@NonNull BottomSheetCallback bottomSheetCallback) {
        this.callbacks.remove(bottomSheetCallback);
    }

    @Deprecated
    public void setBottomSheetCallback(BottomSheetCallback bottomSheetCallback) {
        Log.w(TAG, "BottomSheetBehavior now supports multiple callbacks. `setBottomSheetCallback()` removes all existing callbacks, including ones set internally by library authors, which may result in unintended behavior. This may change in the future. Please use `addBottomSheetCallback()` and `removeBottomSheetCallback()` instead to set your own callbacks.");
        this.callbacks.clear();
        if (bottomSheetCallback != null) {
            this.callbacks.add(bottomSheetCallback);
        }
    }

    public void setDraggable(boolean z2) {
        this.draggable = z2;
    }

    public void setExpandedOffset(int i2) {
        if (i2 < 0) {
            throw new IllegalArgumentException("offset must be greater than or equal to 0");
        }
        this.expandedOffset = i2;
    }

    public void setFitToContents(boolean z2) {
        if (this.fitToContents == z2) {
            return;
        }
        this.fitToContents = z2;
        if (this.viewRef != null) {
            calculateCollapsedOffset();
        }
        setStateInternal((this.fitToContents && this.state == 6) ? 3 : this.state);
        updateAccessibilityActions();
    }

    public void setGestureInsetBottomIgnored(boolean z2) {
        this.gestureInsetBottomIgnored = z2;
    }

    public void setHalfExpandedRatio(@FloatRange(from = 0.0d, fromInclusive = false, to = 1.0d, toInclusive = false) float f2) {
        if (f2 <= 0.0f || f2 >= 1.0f) {
            throw new IllegalArgumentException("ratio must be a float value between 0 and 1");
        }
        this.halfExpandedRatio = f2;
        if (this.viewRef != null) {
            calculateHalfExpandedOffset();
        }
    }

    public void setHideable(boolean z2) {
        if (this.hideable != z2) {
            this.hideable = z2;
            if (!z2 && this.state == 5) {
                setState(4);
            }
            updateAccessibilityActions();
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setHideableInternal(boolean z2) {
        this.hideable = z2;
    }

    public void setMaxHeight(@Px int i2) {
        this.maxHeight = i2;
    }

    public void setMaxWidth(@Px int i2) {
        this.maxWidth = i2;
    }

    public void setPeekHeight(int i2) {
        setPeekHeight(i2, false);
    }

    public void setSaveFlags(int i2) {
        this.saveFlags = i2;
    }

    public void setSkipCollapsed(boolean z2) {
        this.skipCollapsed = z2;
    }

    public void setState(int i2) {
        if (i2 == this.state) {
            return;
        }
        if (this.viewRef != null) {
            settleToStatePendingLayout(i2);
            return;
        }
        if (i2 == 4 || i2 == 3 || i2 == 6 || (this.hideable && i2 == 5)) {
            this.state = i2;
            this.lastStableState = i2;
        }
    }

    public void setStateInternal(int i2) {
        V v2;
        if (this.state == i2) {
            return;
        }
        this.state = i2;
        if (i2 == 4 || i2 == 3 || i2 == 6 || (this.hideable && i2 == 5)) {
            this.lastStableState = i2;
        }
        WeakReference<V> weakReference = this.viewRef;
        if (weakReference == null || (v2 = weakReference.get()) == null) {
            return;
        }
        if (i2 == 3) {
            updateImportantForAccessibility(true);
        } else if (i2 == 6 || i2 == 5 || i2 == 4) {
            updateImportantForAccessibility(false);
        }
        updateDrawableForTargetState(i2);
        for (int i3 = 0; i3 < this.callbacks.size(); i3++) {
            this.callbacks.get(i3).onStateChanged(v2, i2);
        }
        updateAccessibilityActions();
    }

    public void setUpdateImportantForAccessibilityOnSiblings(boolean z2) {
        this.updateImportantForAccessibilityOnSiblings = z2;
    }

    public void settleToState(@NonNull View view, int i2) {
        int expandedOffset;
        int i3;
        if (i2 == 4) {
            expandedOffset = this.collapsedOffset;
        } else if (i2 == 6) {
            expandedOffset = this.halfExpandedOffset;
            if (this.fitToContents && expandedOffset <= (i3 = this.fitToContentsOffset)) {
                i2 = 3;
                expandedOffset = i3;
            }
        } else if (i2 == 3) {
            expandedOffset = getExpandedOffset();
        } else {
            if (!this.hideable || i2 != 5) {
                Log.w(TAG, "The bottom sheet may be in an invalid state. Ensure `hideable` is true when using `STATE_HIDDEN`.");
                return;
            }
            expandedOffset = this.parentHeight;
        }
        startSettlingAnimation(view, i2, expandedOffset, false);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public boolean shouldExpandOnUpwardDrag(long j2, @FloatRange(from = 0.0d, to = 100.0d) float f2) {
        return false;
    }

    public boolean shouldHide(@NonNull View view, float f2) {
        if (this.skipCollapsed) {
            return true;
        }
        if (view.getTop() < this.collapsedOffset) {
            return false;
        }
        return Math.abs((((float) view.getTop()) + (f2 * 0.1f)) - ((float) this.collapsedOffset)) / ((float) calculatePeekHeight()) > 0.5f;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public boolean shouldSkipHalfExpandedStateWhenDragging() {
        return false;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public boolean shouldSkipSmoothAnimation() {
        return true;
    }

    public void startSettlingAnimation(View view, int i2, int i3, boolean z2) {
        ViewDragHelper viewDragHelper = this.viewDragHelper;
        if (!(viewDragHelper != null && (!z2 ? !viewDragHelper.smoothSlideViewTo(view, view.getLeft(), i3) : !viewDragHelper.settleCapturedViewAt(view.getLeft(), i3)))) {
            setStateInternal(i2);
            return;
        }
        setStateInternal(2);
        updateDrawableForTargetState(i2);
        if (this.settleRunnable == null) {
            this.settleRunnable = new SettleRunnable(view, i2);
        }
        if (((SettleRunnable) this.settleRunnable).isPosted) {
            this.settleRunnable.targetState = i2;
            return;
        }
        BottomSheetBehavior<V>.SettleRunnable settleRunnable = this.settleRunnable;
        settleRunnable.targetState = i2;
        ViewCompat.postOnAnimation(view, settleRunnable);
        ((SettleRunnable) this.settleRunnable).isPosted = true;
    }

    private void createMaterialShapeDrawable(@NonNull Context context, AttributeSet attributeSet, boolean z2, @Nullable ColorStateList colorStateList) {
        if (this.shapeThemingEnabled) {
            this.shapeAppearanceModelDefault = ShapeAppearanceModel.builder(context, attributeSet, R.attr.bottomSheetStyle, DEF_STYLE_RES).build();
            MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable(this.shapeAppearanceModelDefault);
            this.materialShapeDrawable = materialShapeDrawable;
            materialShapeDrawable.initializeElevationOverlay(context);
            if (z2 && colorStateList != null) {
                this.materialShapeDrawable.setFillColor(colorStateList);
                return;
            }
            TypedValue typedValue = new TypedValue();
            context.getTheme().resolveAttribute(android.R.attr.colorBackground, typedValue, true);
            this.materialShapeDrawable.setTint(typedValue.data);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0015  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setPeekHeight(int r4, boolean r5) {
        /*
            r3 = this;
            r0 = -1
            r1 = 1
            r2 = 0
            if (r4 != r0) goto Lc
            boolean r4 = r3.peekHeightAuto
            if (r4 != 0) goto L15
            r3.peekHeightAuto = r1
            goto L1f
        Lc:
            boolean r0 = r3.peekHeightAuto
            if (r0 != 0) goto L17
            int r0 = r3.peekHeight
            if (r0 == r4) goto L15
            goto L17
        L15:
            r1 = r2
            goto L1f
        L17:
            r3.peekHeightAuto = r2
            int r4 = java.lang.Math.max(r2, r4)
            r3.peekHeight = r4
        L1f:
            if (r1 == 0) goto L24
            r3.updatePeekHeight(r5)
        L24:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.bottomsheet.BottomSheetBehavior.setPeekHeight(int, boolean):void");
    }

    public BottomSheetBehavior(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        int i2;
        super(context, attributeSet);
        this.saveFlags = 0;
        this.fitToContents = true;
        this.updateImportantForAccessibilityOnSiblings = false;
        this.maxWidth = -1;
        this.maxHeight = -1;
        this.settleRunnable = null;
        this.halfExpandedRatio = 0.5f;
        this.elevation = -1.0f;
        this.draggable = true;
        this.state = 4;
        this.lastStableState = 4;
        this.callbacks = new ArrayList<>();
        this.expandHalfwayActionId = -1;
        this.dragCallback = new ViewDragHelper.Callback() { // from class: com.google.android.material.bottomsheet.BottomSheetBehavior.4
            private long viewCapturedMillis;

            private boolean releasedLow(@NonNull View view) {
                int top2 = view.getTop();
                BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.this;
                return top2 > (bottomSheetBehavior.parentHeight + bottomSheetBehavior.getExpandedOffset()) / 2;
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int clampViewPositionHorizontal(@NonNull View view, int i22, int i3) {
                return view.getLeft();
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int clampViewPositionVertical(@NonNull View view, int i22, int i3) {
                int expandedOffset = BottomSheetBehavior.this.getExpandedOffset();
                BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.this;
                return MathUtils.clamp(i22, expandedOffset, bottomSheetBehavior.hideable ? bottomSheetBehavior.parentHeight : bottomSheetBehavior.collapsedOffset);
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int getViewVerticalDragRange(@NonNull View view) {
                BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.this;
                return bottomSheetBehavior.hideable ? bottomSheetBehavior.parentHeight : bottomSheetBehavior.collapsedOffset;
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public void onViewDragStateChanged(int i22) {
                if (i22 == 1 && BottomSheetBehavior.this.draggable) {
                    BottomSheetBehavior.this.setStateInternal(1);
                }
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public void onViewPositionChanged(@NonNull View view, int i22, int i3, int i4, int i5) {
                BottomSheetBehavior.this.dispatchOnSlide(i3);
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public void onViewReleased(@NonNull View view, float f2, float f3) {
                int expandedOffset;
                int i22 = 6;
                if (f3 < 0.0f) {
                    if (BottomSheetBehavior.this.fitToContents) {
                        expandedOffset = BottomSheetBehavior.this.fitToContentsOffset;
                    } else {
                        int top2 = view.getTop();
                        long jCurrentTimeMillis = System.currentTimeMillis() - this.viewCapturedMillis;
                        if (BottomSheetBehavior.this.shouldSkipHalfExpandedStateWhenDragging()) {
                            if (BottomSheetBehavior.this.shouldExpandOnUpwardDrag(jCurrentTimeMillis, (top2 * 100.0f) / r10.parentHeight)) {
                                expandedOffset = BottomSheetBehavior.this.expandedOffset;
                            } else {
                                expandedOffset = BottomSheetBehavior.this.collapsedOffset;
                                i22 = 4;
                            }
                        } else {
                            BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.this;
                            int i3 = bottomSheetBehavior.halfExpandedOffset;
                            if (top2 > i3) {
                                expandedOffset = i3;
                            } else {
                                expandedOffset = bottomSheetBehavior.getExpandedOffset();
                            }
                        }
                    }
                    i22 = 3;
                } else {
                    BottomSheetBehavior bottomSheetBehavior2 = BottomSheetBehavior.this;
                    if (bottomSheetBehavior2.hideable && bottomSheetBehavior2.shouldHide(view, f3)) {
                        if ((Math.abs(f2) >= Math.abs(f3) || f3 <= 500.0f) && !releasedLow(view)) {
                            if (BottomSheetBehavior.this.fitToContents) {
                                expandedOffset = BottomSheetBehavior.this.fitToContentsOffset;
                            } else if (Math.abs(view.getTop() - BottomSheetBehavior.this.getExpandedOffset()) < Math.abs(view.getTop() - BottomSheetBehavior.this.halfExpandedOffset)) {
                                expandedOffset = BottomSheetBehavior.this.getExpandedOffset();
                            } else {
                                expandedOffset = BottomSheetBehavior.this.halfExpandedOffset;
                            }
                            i22 = 3;
                        } else {
                            expandedOffset = BottomSheetBehavior.this.parentHeight;
                            i22 = 5;
                        }
                    } else if (f3 == 0.0f || Math.abs(f2) > Math.abs(f3)) {
                        int top3 = view.getTop();
                        if (!BottomSheetBehavior.this.fitToContents) {
                            BottomSheetBehavior bottomSheetBehavior3 = BottomSheetBehavior.this;
                            int i4 = bottomSheetBehavior3.halfExpandedOffset;
                            if (top3 < i4) {
                                if (top3 < Math.abs(top3 - bottomSheetBehavior3.collapsedOffset)) {
                                    expandedOffset = BottomSheetBehavior.this.getExpandedOffset();
                                    i22 = 3;
                                } else if (BottomSheetBehavior.this.shouldSkipHalfExpandedStateWhenDragging()) {
                                    expandedOffset = BottomSheetBehavior.this.collapsedOffset;
                                    i22 = 4;
                                } else {
                                    expandedOffset = BottomSheetBehavior.this.halfExpandedOffset;
                                }
                            } else if (Math.abs(top3 - i4) >= Math.abs(top3 - BottomSheetBehavior.this.collapsedOffset) || BottomSheetBehavior.this.shouldSkipHalfExpandedStateWhenDragging()) {
                                expandedOffset = BottomSheetBehavior.this.collapsedOffset;
                                i22 = 4;
                            } else {
                                expandedOffset = BottomSheetBehavior.this.halfExpandedOffset;
                            }
                        } else if (Math.abs(top3 - BottomSheetBehavior.this.fitToContentsOffset) < Math.abs(top3 - BottomSheetBehavior.this.collapsedOffset)) {
                            expandedOffset = BottomSheetBehavior.this.fitToContentsOffset;
                            i22 = 3;
                        } else {
                            expandedOffset = BottomSheetBehavior.this.collapsedOffset;
                            i22 = 4;
                        }
                    } else {
                        if (BottomSheetBehavior.this.fitToContents) {
                            expandedOffset = BottomSheetBehavior.this.collapsedOffset;
                        } else {
                            int top4 = view.getTop();
                            if (Math.abs(top4 - BottomSheetBehavior.this.halfExpandedOffset) >= Math.abs(top4 - BottomSheetBehavior.this.collapsedOffset) || BottomSheetBehavior.this.shouldSkipHalfExpandedStateWhenDragging()) {
                                expandedOffset = BottomSheetBehavior.this.collapsedOffset;
                            } else {
                                expandedOffset = BottomSheetBehavior.this.halfExpandedOffset;
                            }
                        }
                        i22 = 4;
                    }
                }
                BottomSheetBehavior bottomSheetBehavior4 = BottomSheetBehavior.this;
                bottomSheetBehavior4.startSettlingAnimation(view, i22, expandedOffset, bottomSheetBehavior4.shouldSkipSmoothAnimation());
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public boolean tryCaptureView(@NonNull View view, int i22) {
                BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.this;
                int i3 = bottomSheetBehavior.state;
                if (i3 == 1 || bottomSheetBehavior.touchingScrollingChild) {
                    return false;
                }
                if (i3 == 3 && bottomSheetBehavior.activePointerId == i22) {
                    WeakReference<View> weakReference = bottomSheetBehavior.nestedScrollingChildRef;
                    View view2 = weakReference != null ? weakReference.get() : null;
                    if (view2 != null && view2.canScrollVertically(-1)) {
                        return false;
                    }
                }
                this.viewCapturedMillis = System.currentTimeMillis();
                WeakReference<V> weakReference2 = BottomSheetBehavior.this.viewRef;
                return weakReference2 != null && weakReference2.get() == view;
            }
        };
        this.peekHeightGestureInsetBuffer = context.getResources().getDimensionPixelSize(R.dimen.mtrl_min_touch_target_size);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.BottomSheetBehavior_Layout);
        this.shapeThemingEnabled = typedArrayObtainStyledAttributes.hasValue(R.styleable.BottomSheetBehavior_Layout_shapeAppearance);
        int i3 = R.styleable.BottomSheetBehavior_Layout_backgroundTint;
        boolean zHasValue = typedArrayObtainStyledAttributes.hasValue(i3);
        if (zHasValue) {
            createMaterialShapeDrawable(context, attributeSet, zHasValue, MaterialResources.getColorStateList(context, typedArrayObtainStyledAttributes, i3));
        } else {
            createMaterialShapeDrawable(context, attributeSet, zHasValue);
        }
        createShapeValueAnimator();
        this.elevation = typedArrayObtainStyledAttributes.getDimension(R.styleable.BottomSheetBehavior_Layout_android_elevation, -1.0f);
        int i4 = R.styleable.BottomSheetBehavior_Layout_android_maxWidth;
        if (typedArrayObtainStyledAttributes.hasValue(i4)) {
            setMaxWidth(typedArrayObtainStyledAttributes.getDimensionPixelSize(i4, -1));
        }
        int i5 = R.styleable.BottomSheetBehavior_Layout_android_maxHeight;
        if (typedArrayObtainStyledAttributes.hasValue(i5)) {
            setMaxHeight(typedArrayObtainStyledAttributes.getDimensionPixelSize(i5, -1));
        }
        int i6 = R.styleable.BottomSheetBehavior_Layout_behavior_peekHeight;
        TypedValue typedValuePeekValue = typedArrayObtainStyledAttributes.peekValue(i6);
        if (typedValuePeekValue != null && (i2 = typedValuePeekValue.data) == -1) {
            setPeekHeight(i2);
        } else {
            setPeekHeight(typedArrayObtainStyledAttributes.getDimensionPixelSize(i6, -1));
        }
        setHideable(typedArrayObtainStyledAttributes.getBoolean(R.styleable.BottomSheetBehavior_Layout_behavior_hideable, false));
        setGestureInsetBottomIgnored(typedArrayObtainStyledAttributes.getBoolean(R.styleable.BottomSheetBehavior_Layout_gestureInsetBottomIgnored, false));
        setFitToContents(typedArrayObtainStyledAttributes.getBoolean(R.styleable.BottomSheetBehavior_Layout_behavior_fitToContents, true));
        setSkipCollapsed(typedArrayObtainStyledAttributes.getBoolean(R.styleable.BottomSheetBehavior_Layout_behavior_skipCollapsed, false));
        setDraggable(typedArrayObtainStyledAttributes.getBoolean(R.styleable.BottomSheetBehavior_Layout_behavior_draggable, true));
        setSaveFlags(typedArrayObtainStyledAttributes.getInt(R.styleable.BottomSheetBehavior_Layout_behavior_saveFlags, 0));
        setHalfExpandedRatio(typedArrayObtainStyledAttributes.getFloat(R.styleable.BottomSheetBehavior_Layout_behavior_halfExpandedRatio, 0.5f));
        int i7 = R.styleable.BottomSheetBehavior_Layout_behavior_expandedOffset;
        TypedValue typedValuePeekValue2 = typedArrayObtainStyledAttributes.peekValue(i7);
        if (typedValuePeekValue2 != null && typedValuePeekValue2.type == 16) {
            setExpandedOffset(typedValuePeekValue2.data);
        } else {
            setExpandedOffset(typedArrayObtainStyledAttributes.getDimensionPixelOffset(i7, 0));
        }
        this.paddingBottomSystemWindowInsets = typedArrayObtainStyledAttributes.getBoolean(R.styleable.BottomSheetBehavior_Layout_paddingBottomSystemWindowInsets, false);
        this.paddingLeftSystemWindowInsets = typedArrayObtainStyledAttributes.getBoolean(R.styleable.BottomSheetBehavior_Layout_paddingLeftSystemWindowInsets, false);
        this.paddingRightSystemWindowInsets = typedArrayObtainStyledAttributes.getBoolean(R.styleable.BottomSheetBehavior_Layout_paddingRightSystemWindowInsets, false);
        this.paddingTopSystemWindowInsets = typedArrayObtainStyledAttributes.getBoolean(R.styleable.BottomSheetBehavior_Layout_paddingTopSystemWindowInsets, true);
        typedArrayObtainStyledAttributes.recycle();
        this.maximumVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
    }
}
