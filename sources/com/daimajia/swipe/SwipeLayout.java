package com.daimajia.swipe;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public class SwipeLayout extends FrameLayout {
    private GestureDetector gestureDetector;
    private DoubleClickListener mDoubleClickListener;
    private int mDragDistance;
    private DragEdge mDragEdge;
    private ViewDragHelper mDragHelper;
    private ViewDragHelper.Callback mDragHelperCallback;
    private int mEventCounter;
    private float mHorizontalSwipeOffset;
    private List<OnLayout> mOnLayoutListeners;
    private Map<View, ArrayList<OnRevealListener>> mRevealListeners;
    private Map<View, Boolean> mShowEntirely;
    private ShowMode mShowMode;
    private List<SwipeDenier> mSwipeDeniers;
    private boolean mSwipeEnabled;
    private List<SwipeListener> mSwipeListeners;
    private boolean mTouchConsumedByChild;
    private float mVerticalSwipeOffset;
    private float sX;
    private float sY;

    /* renamed from: com.daimajia.swipe.SwipeLayout$2, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$daimajia$swipe$SwipeLayout$DragEdge;

        static {
            int[] iArr = new int[DragEdge.values().length];
            $SwitchMap$com$daimajia$swipe$SwipeLayout$DragEdge = iArr;
            try {
                iArr[DragEdge.Top.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$daimajia$swipe$SwipeLayout$DragEdge[DragEdge.Bottom.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$daimajia$swipe$SwipeLayout$DragEdge[DragEdge.Left.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$daimajia$swipe$SwipeLayout$DragEdge[DragEdge.Right.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public interface DoubleClickListener {
        void onDoubleClick(SwipeLayout swipeLayout, boolean z2);
    }

    public enum DragEdge {
        Left,
        Right,
        Top,
        Bottom
    }

    public interface OnLayout {
        void onLayout(SwipeLayout swipeLayout);
    }

    public interface OnRevealListener {
        void onReveal(View view, DragEdge dragEdge, float f2, int i2);
    }

    public enum ShowMode {
        LayDown,
        PullOut
    }

    public enum Status {
        Middle,
        Open,
        Close
    }

    public interface SwipeDenier {
        boolean shouldDenySwipe(MotionEvent motionEvent);
    }

    public class SwipeDetector extends GestureDetector.SimpleOnGestureListener {
        public SwipeDetector() {
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
        public boolean onDoubleTap(MotionEvent motionEvent) {
            if (SwipeLayout.this.mDoubleClickListener != null) {
                ViewGroup bottomView = SwipeLayout.this.getBottomView();
                ViewGroup surfaceView = SwipeLayout.this.getSurfaceView();
                if (motionEvent.getX() <= bottomView.getLeft() || motionEvent.getX() >= bottomView.getRight() || motionEvent.getY() <= bottomView.getTop() || motionEvent.getY() >= bottomView.getBottom()) {
                    bottomView = surfaceView;
                }
                SwipeLayout.this.mDoubleClickListener.onDoubleClick(SwipeLayout.this, bottomView == surfaceView);
            }
            return true;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onDown(MotionEvent motionEvent) {
            return true;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public void onLongPress(MotionEvent motionEvent) {
            SwipeLayout.this.performLongClick();
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
        public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
            if (SwipeLayout.this.mDoubleClickListener == null) {
                return true;
            }
            SwipeLayout.this.performAdapterViewItemClick(motionEvent);
            return true;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            if (SwipeLayout.this.mDoubleClickListener != null) {
                return true;
            }
            SwipeLayout.this.performAdapterViewItemClick(motionEvent);
            return true;
        }
    }

    public interface SwipeListener {
        void onClose(SwipeLayout swipeLayout);

        void onHandRelease(SwipeLayout swipeLayout, float f2, float f3);

        void onOpen(SwipeLayout swipeLayout);

        void onStartClose(SwipeLayout swipeLayout);

        void onStartOpen(SwipeLayout swipeLayout);

        void onUpdate(SwipeLayout swipeLayout, int i2, int i3);
    }

    public SwipeLayout(Context context) {
        this(context, null);
    }

    private View childNeedHandleTouchEvent(ViewGroup viewGroup, MotionEvent motionEvent) {
        if (viewGroup == null) {
            return null;
        }
        if (viewGroup.onTouchEvent(motionEvent)) {
            return viewGroup;
        }
        for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = viewGroup.getChildAt(childCount);
            if (childAt instanceof ViewGroup) {
                View viewChildNeedHandleTouchEvent = childNeedHandleTouchEvent((ViewGroup) childAt, motionEvent);
                if (viewChildNeedHandleTouchEvent != null) {
                    return viewChildNeedHandleTouchEvent;
                }
            } else if (childNeedHandleTouchEvent(viewGroup.getChildAt(childCount), motionEvent)) {
                return viewGroup.getChildAt(childCount);
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Rect computeBottomLayDown(DragEdge dragEdge) {
        int measuredWidth;
        int measuredHeight;
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        DragEdge dragEdge2 = DragEdge.Right;
        if (dragEdge == dragEdge2) {
            paddingLeft = getMeasuredWidth() - this.mDragDistance;
        } else if (dragEdge == DragEdge.Bottom) {
            paddingTop = getMeasuredHeight() - this.mDragDistance;
        }
        if (dragEdge == DragEdge.Left || dragEdge == dragEdge2) {
            measuredWidth = this.mDragDistance + paddingLeft;
            measuredHeight = getMeasuredHeight();
        } else {
            measuredWidth = getMeasuredWidth() + paddingLeft;
            measuredHeight = this.mDragDistance;
        }
        return new Rect(paddingLeft, paddingTop, measuredWidth, measuredHeight + paddingTop);
    }

    private Rect computeBottomLayoutAreaViaSurface(ShowMode showMode, Rect rect) {
        int measuredWidth;
        int i2 = rect.left;
        int i3 = rect.top;
        int i4 = rect.right;
        int measuredHeight = rect.bottom;
        if (showMode == ShowMode.PullOut) {
            DragEdge dragEdge = this.mDragEdge;
            DragEdge dragEdge2 = DragEdge.Left;
            if (dragEdge == dragEdge2) {
                i2 -= this.mDragDistance;
            } else if (dragEdge == DragEdge.Right) {
                i2 = i4;
            } else {
                i3 = dragEdge == DragEdge.Top ? i3 - this.mDragDistance : measuredHeight;
            }
            if (dragEdge == dragEdge2 || dragEdge == DragEdge.Right) {
                measuredWidth = getBottomView().getMeasuredWidth();
                i4 = i2 + measuredWidth;
            } else {
                measuredHeight = i3 + getBottomView().getMeasuredHeight();
                i4 = rect.right;
            }
        } else if (showMode == ShowMode.LayDown) {
            DragEdge dragEdge3 = this.mDragEdge;
            if (dragEdge3 == DragEdge.Left) {
                measuredWidth = this.mDragDistance;
                i4 = i2 + measuredWidth;
            } else if (dragEdge3 == DragEdge.Right) {
                i2 = i4 - this.mDragDistance;
            } else if (dragEdge3 == DragEdge.Top) {
                measuredHeight = i3 + this.mDragDistance;
            } else {
                i3 = measuredHeight - this.mDragDistance;
            }
        }
        return new Rect(i2, i3, i4, measuredHeight);
    }

    private Rect computeSurfaceLayoutArea(boolean z2) {
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        if (z2) {
            DragEdge dragEdge = this.mDragEdge;
            if (dragEdge == DragEdge.Left) {
                paddingLeft = this.mDragDistance + getPaddingLeft();
            } else if (dragEdge == DragEdge.Right) {
                paddingLeft = getPaddingLeft() - this.mDragDistance;
            } else if (dragEdge == DragEdge.Top) {
                paddingTop = this.mDragDistance + getPaddingTop();
            } else {
                paddingTop = getPaddingTop() - this.mDragDistance;
            }
        }
        return new Rect(paddingLeft, paddingTop, getMeasuredWidth() + paddingLeft, getMeasuredHeight() + paddingTop);
    }

    private int dp2px(float f2) {
        return (int) ((f2 * getContext().getResources().getDisplayMetrics().density) + 0.5f);
    }

    private AdapterView getAdapterView() {
        for (ViewParent parent = getParent(); parent != null; parent = parent.getParent()) {
            if (parent instanceof AdapterView) {
                return (AdapterView) parent;
            }
        }
        return null;
    }

    private boolean insideAdapterView() {
        return getAdapterView() != null;
    }

    private boolean isEnabledInAdapterView() {
        Adapter adapter;
        AdapterView adapterView = getAdapterView();
        if (adapterView != null && (adapter = adapterView.getAdapter()) != null) {
            int positionForView = adapterView.getPositionForView(this);
            if (adapter instanceof BaseAdapter) {
                return ((BaseAdapter) adapter).isEnabled(positionForView);
            }
            if (adapter instanceof ListAdapter) {
                return ((ListAdapter) adapter).isEnabled(positionForView);
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void performAdapterViewItemClick(MotionEvent motionEvent) {
        for (ViewParent parent = getParent(); parent != null; parent = parent.getParent()) {
            if (parent instanceof AdapterView) {
                AdapterView adapterView = (AdapterView) parent;
                int positionForView = adapterView.getPositionForView(this);
                if (positionForView != -1 && adapterView.performItemClick(adapterView.getChildAt(positionForView - adapterView.getFirstVisiblePosition()), positionForView, adapterView.getAdapter().getItemId(positionForView))) {
                    return;
                }
            } else if ((parent instanceof View) && ((View) parent).performClick()) {
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processBottomLayDownMode(float f2, float f3) {
        if (f2 == 0.0f && getOpenStatus() == Status.Middle) {
            close();
        }
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        if (f2 < 0.0f && this.mDragEdge == DragEdge.Right) {
            paddingLeft -= this.mDragDistance;
        }
        if (f2 > 0.0f && this.mDragEdge == DragEdge.Left) {
            paddingLeft += this.mDragDistance;
        }
        if (f3 > 0.0f && this.mDragEdge == DragEdge.Top) {
            paddingTop += this.mDragDistance;
        }
        if (f3 < 0.0f && this.mDragEdge == DragEdge.Bottom) {
            paddingTop -= this.mDragDistance;
        }
        this.mDragHelper.smoothSlideViewTo(getSurfaceView(), paddingLeft, paddingTop);
        invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processBottomPullOutRelease(float f2, float f3) {
        if (f2 == 0.0f && getOpenStatus() == Status.Middle) {
            close();
        }
        DragEdge dragEdge = this.mDragEdge;
        DragEdge dragEdge2 = DragEdge.Left;
        if (dragEdge == dragEdge2 || dragEdge == DragEdge.Right) {
            if (f2 > 0.0f) {
                if (dragEdge == dragEdge2) {
                    open();
                } else {
                    close();
                }
            }
            if (f2 < 0.0f) {
                if (this.mDragEdge == dragEdge2) {
                    close();
                    return;
                } else {
                    open();
                    return;
                }
            }
            return;
        }
        if (f3 > 0.0f) {
            if (dragEdge == DragEdge.Top) {
                open();
            } else {
                close();
            }
        }
        if (f3 < 0.0f) {
            if (this.mDragEdge == DragEdge.Top) {
                close();
            } else {
                open();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processSurfaceRelease(float f2, float f3) {
        if (f2 == 0.0f && getOpenStatus() == Status.Middle) {
            close();
        }
        DragEdge dragEdge = this.mDragEdge;
        DragEdge dragEdge2 = DragEdge.Left;
        if (dragEdge == dragEdge2 || dragEdge == DragEdge.Right) {
            if (f2 > 0.0f) {
                if (dragEdge == dragEdge2) {
                    open();
                } else {
                    close();
                }
            }
            if (f2 < 0.0f) {
                if (this.mDragEdge == dragEdge2) {
                    close();
                    return;
                } else {
                    open();
                    return;
                }
            }
            return;
        }
        if (f3 > 0.0f) {
            if (dragEdge == DragEdge.Top) {
                open();
            } else {
                close();
            }
        }
        if (f3 < 0.0f) {
            if (this.mDragEdge == DragEdge.Top) {
                close();
            } else {
                open();
            }
        }
    }

    private void safeBottomView() {
        Status openStatus = getOpenStatus();
        ViewGroup bottomView = getBottomView();
        if (openStatus == Status.Close) {
            if (bottomView.getVisibility() != 4) {
                bottomView.setVisibility(4);
            }
        } else if (bottomView.getVisibility() != 0) {
            bottomView.setVisibility(0);
        }
    }

    public void addOnLayoutListener(OnLayout onLayout) {
        if (this.mOnLayoutListeners == null) {
            this.mOnLayoutListeners = new ArrayList();
        }
        this.mOnLayoutListeners.add(onLayout);
    }

    public void addRevealListener(int i2, OnRevealListener onRevealListener) {
        View viewFindViewById = findViewById(i2);
        if (viewFindViewById == null) {
            throw new IllegalArgumentException("Child does not belong to SwipeListener.");
        }
        if (!this.mShowEntirely.containsKey(viewFindViewById)) {
            this.mShowEntirely.put(viewFindViewById, Boolean.FALSE);
        }
        if (this.mRevealListeners.get(viewFindViewById) == null) {
            this.mRevealListeners.put(viewFindViewById, new ArrayList<>());
        }
        this.mRevealListeners.get(viewFindViewById).add(onRevealListener);
    }

    public void addSwipeDenier(SwipeDenier swipeDenier) {
        this.mSwipeDeniers.add(swipeDenier);
    }

    public void addSwipeListener(SwipeListener swipeListener) {
        this.mSwipeListeners.add(swipeListener);
    }

    public void close() {
        close(true, true);
    }

    @Override // android.view.View
    public void computeScroll() {
        super.computeScroll();
        if (this.mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x00f7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void dispatchRevealEvent(int r15, int r16, int r17, int r18) {
        /*
            Method dump skipped, instructions count: 353
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.daimajia.swipe.SwipeLayout.dispatchRevealEvent(int, int, int, int):void");
    }

    public void dispatchSwipeEvent(int i2, int i3, int i4, int i5) {
        DragEdge dragEdge = getDragEdge();
        boolean z2 = false;
        if (dragEdge != DragEdge.Left ? dragEdge != DragEdge.Right ? dragEdge != DragEdge.Top ? dragEdge != DragEdge.Bottom || i5 <= 0 : i5 >= 0 : i4 <= 0 : i4 >= 0) {
            z2 = true;
        }
        dispatchSwipeEvent(i2, i3, z2);
    }

    public ViewGroup getBottomView() {
        return (ViewGroup) getChildAt(0);
    }

    public int getDragDistance() {
        return this.mDragDistance;
    }

    public DragEdge getDragEdge() {
        return this.mDragEdge;
    }

    public Status getOpenStatus() {
        int left = getSurfaceView().getLeft();
        int top2 = getSurfaceView().getTop();
        return (left == getPaddingLeft() && top2 == getPaddingTop()) ? Status.Close : (left == getPaddingLeft() - this.mDragDistance || left == getPaddingLeft() + this.mDragDistance || top2 == getPaddingTop() - this.mDragDistance || top2 == getPaddingTop() + this.mDragDistance) ? Status.Open : Status.Middle;
    }

    public Rect getRelativePosition(View view) {
        Rect rect = new Rect(view.getLeft(), view.getTop(), 0, 0);
        View view2 = view;
        while (view2.getParent() != null && view2 != getRootView() && (view2 = (View) view2.getParent()) != this) {
            rect.left += view2.getLeft();
            rect.top += view2.getTop();
        }
        rect.right = rect.left + view.getMeasuredWidth();
        rect.bottom = rect.top + view.getMeasuredHeight();
        return rect;
    }

    public ShowMode getShowMode() {
        return this.mShowMode;
    }

    public ViewGroup getSurfaceView() {
        return (ViewGroup) getChildAt(1);
    }

    public boolean isSwipeEnabled() {
        return this.mSwipeEnabled;
    }

    public boolean isViewShowing(View view, Rect rect, DragEdge dragEdge, int i2, int i3, int i4, int i5) {
        int i6 = rect.left;
        int i7 = rect.right;
        int i8 = rect.top;
        int i9 = rect.bottom;
        if (getShowMode() == ShowMode.LayDown) {
            int i10 = AnonymousClass2.$SwitchMap$com$daimajia$swipe$SwipeLayout$DragEdge[dragEdge.ordinal()];
            return i10 != 1 ? i10 != 2 ? i10 != 3 ? i10 == 4 && i4 > i6 && i4 <= i7 : i2 < i7 && i2 >= i6 : i5 > i8 && i5 <= i9 : i3 >= i8 && i3 < i9;
        }
        if (getShowMode() != ShowMode.PullOut) {
            return false;
        }
        int i11 = AnonymousClass2.$SwitchMap$com$daimajia$swipe$SwipeLayout$DragEdge[dragEdge.ordinal()];
        return i11 != 1 ? i11 != 2 ? i11 != 3 ? i11 == 4 && i6 <= getWidth() && i7 > getWidth() : i7 >= getPaddingLeft() && i6 < getPaddingLeft() : i8 < getHeight() && i8 >= getPaddingTop() : i8 < getPaddingTop() && i9 >= getPaddingTop();
    }

    public boolean isViewTotallyFirstShowed(View view, Rect rect, DragEdge dragEdge, int i2, int i3, int i4, int i5) {
        if (this.mShowEntirely.get(view).booleanValue()) {
            return false;
        }
        int i6 = rect.left;
        int i7 = rect.right;
        int i8 = rect.top;
        int i9 = rect.bottom;
        if (getShowMode() == ShowMode.LayDown) {
            if ((dragEdge != DragEdge.Right || i4 > i6) && ((dragEdge != DragEdge.Left || i2 < i7) && ((dragEdge != DragEdge.Top || i3 < i9) && (dragEdge != DragEdge.Bottom || i5 > i8)))) {
                return false;
            }
        } else {
            if (getShowMode() != ShowMode.PullOut) {
                return false;
            }
            if ((dragEdge != DragEdge.Right || i7 > getWidth()) && ((dragEdge != DragEdge.Left || i6 < getPaddingLeft()) && ((dragEdge != DragEdge.Top || i8 < getPaddingTop()) && (dragEdge != DragEdge.Bottom || i9 > getHeight())))) {
                return false;
            }
        }
        return true;
    }

    public void layoutLayDown() {
        Rect rectComputeSurfaceLayoutArea = computeSurfaceLayoutArea(false);
        getSurfaceView().layout(rectComputeSurfaceLayoutArea.left, rectComputeSurfaceLayoutArea.top, rectComputeSurfaceLayoutArea.right, rectComputeSurfaceLayoutArea.bottom);
        Rect rectComputeBottomLayoutAreaViaSurface = computeBottomLayoutAreaViaSurface(ShowMode.LayDown, rectComputeSurfaceLayoutArea);
        getBottomView().layout(rectComputeBottomLayoutAreaViaSurface.left, rectComputeBottomLayoutAreaViaSurface.top, rectComputeBottomLayoutAreaViaSurface.right, rectComputeBottomLayoutAreaViaSurface.bottom);
        bringChildToFront(getSurfaceView());
    }

    public void layoutPullOut() {
        Rect rectComputeSurfaceLayoutArea = computeSurfaceLayoutArea(false);
        getSurfaceView().layout(rectComputeSurfaceLayoutArea.left, rectComputeSurfaceLayoutArea.top, rectComputeSurfaceLayoutArea.right, rectComputeSurfaceLayoutArea.bottom);
        Rect rectComputeBottomLayoutAreaViaSurface = computeBottomLayoutAreaViaSurface(ShowMode.PullOut, rectComputeSurfaceLayoutArea);
        getBottomView().layout(rectComputeBottomLayoutAreaViaSurface.left, rectComputeBottomLayoutAreaViaSurface.top, rectComputeBottomLayoutAreaViaSurface.right, rectComputeBottomLayoutAreaViaSurface.bottom);
        bringChildToFront(getSurfaceView());
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (!isEnabled() || !isEnabledInAdapterView()) {
            return true;
        }
        if (!isSwipeEnabled()) {
            return false;
        }
        for (SwipeDenier swipeDenier : this.mSwipeDeniers) {
            if (swipeDenier != null && swipeDenier.shouldDenySwipe(motionEvent)) {
                return false;
            }
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            Status openStatus = getOpenStatus();
            if (openStatus == Status.Close) {
                this.mTouchConsumedByChild = childNeedHandleTouchEvent(getSurfaceView(), motionEvent) != null;
            } else if (openStatus == Status.Open) {
                this.mTouchConsumedByChild = childNeedHandleTouchEvent(getBottomView(), motionEvent) != null;
            }
        } else if (actionMasked == 1 || actionMasked == 3) {
            this.mTouchConsumedByChild = false;
        }
        if (this.mTouchConsumedByChild) {
            return false;
        }
        return this.mDragHelper.shouldInterceptTouchEvent(motionEvent);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        if (getChildCount() != 2) {
            throw new IllegalStateException("You need 2  views in SwipeLayout");
        }
        if (!(getChildAt(0) instanceof ViewGroup) || !(getChildAt(1) instanceof ViewGroup)) {
            throw new IllegalArgumentException("The 2 children in SwipeLayout must be an instance of ViewGroup");
        }
        ShowMode showMode = this.mShowMode;
        if (showMode == ShowMode.PullOut) {
            layoutPullOut();
        } else if (showMode == ShowMode.LayDown) {
            layoutLayDown();
        }
        safeBottomView();
        if (this.mOnLayoutListeners != null) {
            for (int i6 = 0; i6 < this.mOnLayoutListeners.size(); i6++) {
                this.mOnLayoutListeners.get(i6).onLayout(this);
            }
        }
    }

    @Override // android.widget.FrameLayout, android.view.View
    public void onMeasure(int i2, int i3) {
        super.onMeasure(i2, i3);
        DragEdge dragEdge = this.mDragEdge;
        if (dragEdge == DragEdge.Left || dragEdge == DragEdge.Right) {
            this.mDragDistance = getBottomView().getMeasuredWidth() - dp2px(this.mHorizontalSwipeOffset);
        } else {
            this.mDragDistance = getBottomView().getMeasuredHeight() - dp2px(this.mVerticalSwipeOffset);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:112:0x010c  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x0110  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x011e  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0095  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x00c2  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x00e8  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r15) {
        /*
            Method dump skipped, instructions count: 332
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.daimajia.swipe.SwipeLayout.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public void open() {
        open(true, true);
    }

    public void removeAllRevealListeners(int i2) {
        View viewFindViewById = findViewById(i2);
        if (viewFindViewById != null) {
            this.mRevealListeners.remove(viewFindViewById);
            this.mShowEntirely.remove(viewFindViewById);
        }
    }

    public void removeAllSwipeDeniers() {
        this.mSwipeDeniers.clear();
    }

    public void removeOnLayoutListener(OnLayout onLayout) {
        List<OnLayout> list = this.mOnLayoutListeners;
        if (list != null) {
            list.remove(onLayout);
        }
    }

    public void removeRevealListener(int i2, OnRevealListener onRevealListener) {
        View viewFindViewById = findViewById(i2);
        if (viewFindViewById == null) {
            return;
        }
        this.mShowEntirely.remove(viewFindViewById);
        if (this.mRevealListeners.containsKey(viewFindViewById)) {
            this.mRevealListeners.get(viewFindViewById).remove(onRevealListener);
        }
    }

    public void removeSwipeDenier(SwipeDenier swipeDenier) {
        this.mSwipeDeniers.remove(swipeDenier);
    }

    public void removeSwipeListener(SwipeListener swipeListener) {
        this.mSwipeListeners.remove(swipeListener);
    }

    public void setDragDistance(int i2) {
        if (i2 < 0) {
            throw new IllegalArgumentException("Drag distance can not be < 0");
        }
        this.mDragDistance = dp2px(i2);
        requestLayout();
    }

    public void setDragEdge(DragEdge dragEdge) {
        this.mDragEdge = dragEdge;
        requestLayout();
    }

    public void setOnDoubleClickListener(DoubleClickListener doubleClickListener) {
        this.mDoubleClickListener = doubleClickListener;
    }

    public void setShowMode(ShowMode showMode) {
        this.mShowMode = showMode;
        requestLayout();
    }

    public void setSwipeEnabled(boolean z2) {
        this.mSwipeEnabled = z2;
    }

    public void toggle() {
        toggle(true);
    }

    public SwipeLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public void close(boolean z2) {
        close(z2, true);
    }

    public void open(boolean z2) {
        open(z2, true);
    }

    public void toggle(boolean z2) {
        if (getOpenStatus() == Status.Open) {
            close(z2);
        } else if (getOpenStatus() == Status.Close) {
            open(z2);
        }
    }

    public SwipeLayout(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mDragDistance = 0;
        this.mSwipeListeners = new ArrayList();
        this.mSwipeDeniers = new ArrayList();
        this.mRevealListeners = new HashMap();
        this.mShowEntirely = new HashMap();
        this.mSwipeEnabled = true;
        this.mDragHelperCallback = new ViewDragHelper.Callback() { // from class: com.daimajia.swipe.SwipeLayout.1
            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int clampViewPositionHorizontal(View view, int i3, int i4) {
                if (view == SwipeLayout.this.getSurfaceView()) {
                    int i5 = AnonymousClass2.$SwitchMap$com$daimajia$swipe$SwipeLayout$DragEdge[SwipeLayout.this.mDragEdge.ordinal()];
                    if (i5 == 1 || i5 == 2) {
                        return SwipeLayout.this.getPaddingLeft();
                    }
                    if (i5 != 3) {
                        if (i5 == 4) {
                            if (i3 > SwipeLayout.this.getPaddingLeft()) {
                                return SwipeLayout.this.getPaddingLeft();
                            }
                            if (i3 < SwipeLayout.this.getPaddingLeft() - SwipeLayout.this.mDragDistance) {
                                return SwipeLayout.this.getPaddingLeft() - SwipeLayout.this.mDragDistance;
                            }
                        }
                    } else {
                        if (i3 < SwipeLayout.this.getPaddingLeft()) {
                            return SwipeLayout.this.getPaddingLeft();
                        }
                        if (i3 > SwipeLayout.this.getPaddingLeft() + SwipeLayout.this.mDragDistance) {
                            return SwipeLayout.this.getPaddingLeft() + SwipeLayout.this.mDragDistance;
                        }
                    }
                } else if (view == SwipeLayout.this.getBottomView()) {
                    int i6 = AnonymousClass2.$SwitchMap$com$daimajia$swipe$SwipeLayout$DragEdge[SwipeLayout.this.mDragEdge.ordinal()];
                    if (i6 == 1 || i6 == 2) {
                        return SwipeLayout.this.getPaddingLeft();
                    }
                    if (i6 != 3) {
                        if (i6 == 4 && SwipeLayout.this.mShowMode == ShowMode.PullOut && i3 < SwipeLayout.this.getMeasuredWidth() - SwipeLayout.this.mDragDistance) {
                            return SwipeLayout.this.getMeasuredWidth() - SwipeLayout.this.mDragDistance;
                        }
                    } else if (SwipeLayout.this.mShowMode == ShowMode.PullOut && i3 > SwipeLayout.this.getPaddingLeft()) {
                        return SwipeLayout.this.getPaddingLeft();
                    }
                }
                return i3;
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int clampViewPositionVertical(View view, int i3, int i4) {
                if (view == SwipeLayout.this.getSurfaceView()) {
                    int i5 = AnonymousClass2.$SwitchMap$com$daimajia$swipe$SwipeLayout$DragEdge[SwipeLayout.this.mDragEdge.ordinal()];
                    if (i5 != 1) {
                        if (i5 != 2) {
                            if (i5 == 3 || i5 == 4) {
                                return SwipeLayout.this.getPaddingTop();
                            }
                        } else {
                            if (i3 < SwipeLayout.this.getPaddingTop() - SwipeLayout.this.mDragDistance) {
                                return SwipeLayout.this.getPaddingTop() - SwipeLayout.this.mDragDistance;
                            }
                            if (i3 > SwipeLayout.this.getPaddingTop()) {
                                return SwipeLayout.this.getPaddingTop();
                            }
                        }
                    } else {
                        if (i3 < SwipeLayout.this.getPaddingTop()) {
                            return SwipeLayout.this.getPaddingTop();
                        }
                        if (i3 > SwipeLayout.this.getPaddingTop() + SwipeLayout.this.mDragDistance) {
                            return SwipeLayout.this.getPaddingTop() + SwipeLayout.this.mDragDistance;
                        }
                    }
                } else {
                    int i6 = AnonymousClass2.$SwitchMap$com$daimajia$swipe$SwipeLayout$DragEdge[SwipeLayout.this.mDragEdge.ordinal()];
                    if (i6 != 1) {
                        if (i6 != 2) {
                            if (i6 == 3 || i6 == 4) {
                                return SwipeLayout.this.getPaddingTop();
                            }
                        } else if (SwipeLayout.this.mShowMode == ShowMode.PullOut) {
                            if (i3 < SwipeLayout.this.getMeasuredHeight() - SwipeLayout.this.mDragDistance) {
                                return SwipeLayout.this.getMeasuredHeight() - SwipeLayout.this.mDragDistance;
                            }
                        } else {
                            if (SwipeLayout.this.getSurfaceView().getTop() + i4 >= SwipeLayout.this.getPaddingTop()) {
                                return SwipeLayout.this.getPaddingTop();
                            }
                            if (SwipeLayout.this.getSurfaceView().getTop() + i4 <= SwipeLayout.this.getPaddingTop() - SwipeLayout.this.mDragDistance) {
                                return SwipeLayout.this.getPaddingTop() - SwipeLayout.this.mDragDistance;
                            }
                        }
                    } else if (SwipeLayout.this.mShowMode == ShowMode.PullOut) {
                        if (i3 > SwipeLayout.this.getPaddingTop()) {
                            return SwipeLayout.this.getPaddingTop();
                        }
                    } else {
                        if (SwipeLayout.this.getSurfaceView().getTop() + i4 < SwipeLayout.this.getPaddingTop()) {
                            return SwipeLayout.this.getPaddingTop();
                        }
                        if (SwipeLayout.this.getSurfaceView().getTop() + i4 > SwipeLayout.this.getPaddingTop() + SwipeLayout.this.mDragDistance) {
                            return SwipeLayout.this.getPaddingTop() + SwipeLayout.this.mDragDistance;
                        }
                    }
                }
                return i3;
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int getViewHorizontalDragRange(View view) {
                return SwipeLayout.this.mDragDistance;
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int getViewVerticalDragRange(View view) {
                return SwipeLayout.this.mDragDistance;
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public void onViewPositionChanged(View view, int i3, int i4, int i5, int i6) {
                int left = SwipeLayout.this.getSurfaceView().getLeft();
                int right = SwipeLayout.this.getSurfaceView().getRight();
                int top2 = SwipeLayout.this.getSurfaceView().getTop();
                int bottom = SwipeLayout.this.getSurfaceView().getBottom();
                if (view == SwipeLayout.this.getSurfaceView()) {
                    if (SwipeLayout.this.mShowMode == ShowMode.PullOut) {
                        if (SwipeLayout.this.mDragEdge == DragEdge.Left || SwipeLayout.this.mDragEdge == DragEdge.Right) {
                            SwipeLayout.this.getBottomView().offsetLeftAndRight(i5);
                        } else {
                            SwipeLayout.this.getBottomView().offsetTopAndBottom(i6);
                        }
                    }
                } else if (view == SwipeLayout.this.getBottomView()) {
                    if (SwipeLayout.this.mShowMode == ShowMode.PullOut) {
                        SwipeLayout.this.getSurfaceView().offsetLeftAndRight(i5);
                        SwipeLayout.this.getSurfaceView().offsetTopAndBottom(i6);
                    } else {
                        SwipeLayout swipeLayout = SwipeLayout.this;
                        Rect rectComputeBottomLayDown = swipeLayout.computeBottomLayDown(swipeLayout.mDragEdge);
                        SwipeLayout.this.getBottomView().layout(rectComputeBottomLayDown.left, rectComputeBottomLayDown.top, rectComputeBottomLayDown.right, rectComputeBottomLayDown.bottom);
                        int left2 = SwipeLayout.this.getSurfaceView().getLeft() + i5;
                        int top3 = SwipeLayout.this.getSurfaceView().getTop() + i6;
                        if (SwipeLayout.this.mDragEdge == DragEdge.Left && left2 < SwipeLayout.this.getPaddingLeft()) {
                            left2 = SwipeLayout.this.getPaddingLeft();
                        } else if (SwipeLayout.this.mDragEdge == DragEdge.Right && left2 > SwipeLayout.this.getPaddingLeft()) {
                            left2 = SwipeLayout.this.getPaddingLeft();
                        } else if (SwipeLayout.this.mDragEdge == DragEdge.Top && top3 < SwipeLayout.this.getPaddingTop()) {
                            top3 = SwipeLayout.this.getPaddingTop();
                        } else if (SwipeLayout.this.mDragEdge == DragEdge.Bottom && top3 > SwipeLayout.this.getPaddingTop()) {
                            top3 = SwipeLayout.this.getPaddingTop();
                        }
                        SwipeLayout.this.getSurfaceView().layout(left2, top3, SwipeLayout.this.getMeasuredWidth() + left2, SwipeLayout.this.getMeasuredHeight() + top3);
                    }
                }
                SwipeLayout.this.dispatchRevealEvent(left, top2, right, bottom);
                SwipeLayout.this.dispatchSwipeEvent(left, top2, i5, i6);
                SwipeLayout.this.invalidate();
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public void onViewReleased(View view, float f2, float f3) {
                super.onViewReleased(view, f2, f3);
                Iterator it = SwipeLayout.this.mSwipeListeners.iterator();
                while (it.hasNext()) {
                    ((SwipeListener) it.next()).onHandRelease(SwipeLayout.this, f2, f3);
                }
                if (view == SwipeLayout.this.getSurfaceView()) {
                    SwipeLayout.this.processSurfaceRelease(f2, f3);
                } else if (view == SwipeLayout.this.getBottomView()) {
                    if (SwipeLayout.this.getShowMode() == ShowMode.PullOut) {
                        SwipeLayout.this.processBottomPullOutRelease(f2, f3);
                    } else if (SwipeLayout.this.getShowMode() == ShowMode.LayDown) {
                        SwipeLayout.this.processBottomLayDownMode(f2, f3);
                    }
                }
                SwipeLayout.this.invalidate();
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public boolean tryCaptureView(View view, int i3) {
                return view == SwipeLayout.this.getSurfaceView() || view == SwipeLayout.this.getBottomView();
            }
        };
        this.mEventCounter = 0;
        this.mTouchConsumedByChild = false;
        this.sX = -1.0f;
        this.sY = -1.0f;
        this.gestureDetector = new GestureDetector(getContext(), new SwipeDetector());
        this.mDragHelper = ViewDragHelper.create(this, this.mDragHelperCallback);
        int iOrdinal = DragEdge.Right.ordinal();
        this.mHorizontalSwipeOffset = 0.0f;
        this.mVerticalSwipeOffset = 0.0f;
        this.mDragEdge = DragEdge.values()[iOrdinal];
        this.mShowMode = ShowMode.values()[ShowMode.PullOut.ordinal()];
    }

    public void close(boolean z2, boolean z3) {
        ViewGroup surfaceView = getSurfaceView();
        if (z2) {
            this.mDragHelper.smoothSlideViewTo(getSurfaceView(), getPaddingLeft(), getPaddingTop());
        } else {
            Rect rectComputeSurfaceLayoutArea = computeSurfaceLayoutArea(false);
            int left = rectComputeSurfaceLayoutArea.left - surfaceView.getLeft();
            int top2 = rectComputeSurfaceLayoutArea.top - surfaceView.getTop();
            surfaceView.layout(rectComputeSurfaceLayoutArea.left, rectComputeSurfaceLayoutArea.top, rectComputeSurfaceLayoutArea.right, rectComputeSurfaceLayoutArea.bottom);
            if (z3) {
                dispatchRevealEvent(rectComputeSurfaceLayoutArea.left, rectComputeSurfaceLayoutArea.top, rectComputeSurfaceLayoutArea.right, rectComputeSurfaceLayoutArea.bottom);
                dispatchSwipeEvent(rectComputeSurfaceLayoutArea.left, rectComputeSurfaceLayoutArea.top, left, top2);
            } else {
                safeBottomView();
            }
        }
        invalidate();
    }

    public void open(boolean z2, boolean z3) {
        ViewGroup surfaceView = getSurfaceView();
        ViewGroup bottomView = getBottomView();
        Rect rectComputeSurfaceLayoutArea = computeSurfaceLayoutArea(true);
        if (z2) {
            this.mDragHelper.smoothSlideViewTo(getSurfaceView(), rectComputeSurfaceLayoutArea.left, rectComputeSurfaceLayoutArea.top);
        } else {
            int left = rectComputeSurfaceLayoutArea.left - surfaceView.getLeft();
            int top2 = rectComputeSurfaceLayoutArea.top - surfaceView.getTop();
            surfaceView.layout(rectComputeSurfaceLayoutArea.left, rectComputeSurfaceLayoutArea.top, rectComputeSurfaceLayoutArea.right, rectComputeSurfaceLayoutArea.bottom);
            ShowMode showMode = getShowMode();
            ShowMode showMode2 = ShowMode.PullOut;
            if (showMode == showMode2) {
                Rect rectComputeBottomLayoutAreaViaSurface = computeBottomLayoutAreaViaSurface(showMode2, rectComputeSurfaceLayoutArea);
                bottomView.layout(rectComputeBottomLayoutAreaViaSurface.left, rectComputeBottomLayoutAreaViaSurface.top, rectComputeBottomLayoutAreaViaSurface.right, rectComputeBottomLayoutAreaViaSurface.bottom);
            }
            if (z3) {
                dispatchRevealEvent(rectComputeSurfaceLayoutArea.left, rectComputeSurfaceLayoutArea.top, rectComputeSurfaceLayoutArea.right, rectComputeSurfaceLayoutArea.bottom);
                dispatchSwipeEvent(rectComputeSurfaceLayoutArea.left, rectComputeSurfaceLayoutArea.top, left, top2);
            } else {
                safeBottomView();
            }
        }
        invalidate();
    }

    public void dispatchSwipeEvent(int i2, int i3, boolean z2) {
        safeBottomView();
        Status openStatus = getOpenStatus();
        if (this.mSwipeListeners.isEmpty()) {
            return;
        }
        this.mEventCounter++;
        for (SwipeListener swipeListener : this.mSwipeListeners) {
            if (this.mEventCounter == 1) {
                if (z2) {
                    swipeListener.onStartOpen(this);
                } else {
                    swipeListener.onStartClose(this);
                }
            }
            swipeListener.onUpdate(this, i2 - getPaddingLeft(), i3 - getPaddingTop());
        }
        if (openStatus == Status.Close) {
            Iterator<SwipeListener> it = this.mSwipeListeners.iterator();
            while (it.hasNext()) {
                it.next().onClose(this);
            }
            this.mEventCounter = 0;
        }
        if (openStatus == Status.Open) {
            getBottomView().setEnabled(true);
            Iterator<SwipeListener> it2 = this.mSwipeListeners.iterator();
            while (it2.hasNext()) {
                it2.next().onOpen(this);
            }
            this.mEventCounter = 0;
        }
    }

    private boolean childNeedHandleTouchEvent(View view, MotionEvent motionEvent) {
        if (view == null) {
            return false;
        }
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        int i2 = iArr[0];
        int i3 = iArr[1];
        if (motionEvent.getRawX() <= i2 || motionEvent.getRawX() >= i2 + view.getWidth() || motionEvent.getRawY() <= i3 || motionEvent.getRawY() >= i3 + view.getHeight()) {
            return false;
        }
        return view.onTouchEvent(motionEvent);
    }

    public void addRevealListener(int[] iArr, OnRevealListener onRevealListener) {
        for (int i2 : iArr) {
            addRevealListener(i2, onRevealListener);
        }
    }
}
