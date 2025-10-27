package com.easefun.polyv.livecommon.ui.widget.swipe;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;
import com.easefun.polyv.livecommon.R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public class PLVSwipeLayout extends FrameLayout {
    private static final int DRAG_BOTTOM = 8;
    private static final int DRAG_LEFT = 1;
    private static final int DRAG_RIGHT = 2;
    private static final int DRAG_TOP = 4;
    private static final DragEdge DefaultDragEdge = DragEdge.Right;

    @Deprecated
    public static final int EMPTY_LAYOUT = -1;
    View.OnClickListener clickListener;
    private GestureDetector gestureDetector;
    private Rect hitSurfaceRect;
    View.OnLongClickListener longClickListener;
    private boolean mClickToClose;
    private DragEdge mCurrentDragEdge;
    private DoubleClickListener mDoubleClickListener;
    private int mDragDistance;
    private LinkedHashMap<DragEdge, View> mDragEdges;
    private ViewDragHelper mDragHelper;
    private ViewDragHelper.Callback mDragHelperCallback;
    private float[] mEdgeSwipesOffset;
    private int mEventCounter;
    private boolean mIsBeingDragged;
    private List<OnLayout> mOnLayoutListeners;
    private Map<View, ArrayList<OnRevealListener>> mRevealListeners;
    private Map<View, Boolean> mShowEntirely;
    private ShowMode mShowMode;
    private List<SwipeDenier> mSwipeDeniers;
    private boolean mSwipeEnabled;
    private List<SwipeListener> mSwipeListeners;
    private boolean[] mSwipesEnabled;
    private int mTouchSlop;
    private Map<View, Rect> mViewBoundCache;
    private float mWillOpenPercentAfterClose;
    private float mWillOpenPercentAfterOpen;
    private float sX;
    private float sY;

    /* renamed from: com.easefun.polyv.livecommon.ui.widget.swipe.PLVSwipeLayout$4, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] $SwitchMap$com$easefun$polyv$livecommon$ui$widget$swipe$PLVSwipeLayout$DragEdge;

        static {
            int[] iArr = new int[DragEdge.values().length];
            $SwitchMap$com$easefun$polyv$livecommon$ui$widget$swipe$PLVSwipeLayout$DragEdge = iArr;
            try {
                iArr[DragEdge.Top.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$easefun$polyv$livecommon$ui$widget$swipe$PLVSwipeLayout$DragEdge[DragEdge.Bottom.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$easefun$polyv$livecommon$ui$widget$swipe$PLVSwipeLayout$DragEdge[DragEdge.Left.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$easefun$polyv$livecommon$ui$widget$swipe$PLVSwipeLayout$DragEdge[DragEdge.Right.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public interface DoubleClickListener {
        void onDoubleClick(PLVSwipeLayout layout, boolean surface);
    }

    public enum DragEdge {
        Left,
        Top,
        Right,
        Bottom
    }

    public interface OnLayout {
        void onLayout(PLVSwipeLayout v2);
    }

    public interface OnRevealListener {
        void onReveal(View child, DragEdge edge, float fraction, int distance);
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
        boolean shouldDenySwipe(MotionEvent ev);
    }

    public class SwipeDetector extends GestureDetector.SimpleOnGestureListener {
        public SwipeDetector() {
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
        public boolean onDoubleTap(MotionEvent e2) {
            if (PLVSwipeLayout.this.mDoubleClickListener != null) {
                View currentBottomView = PLVSwipeLayout.this.getCurrentBottomView();
                View surfaceView = PLVSwipeLayout.this.getSurfaceView();
                if (currentBottomView == null || e2.getX() <= currentBottomView.getLeft() || e2.getX() >= currentBottomView.getRight() || e2.getY() <= currentBottomView.getTop() || e2.getY() >= currentBottomView.getBottom()) {
                    currentBottomView = surfaceView;
                }
                PLVSwipeLayout.this.mDoubleClickListener.onDoubleClick(PLVSwipeLayout.this, currentBottomView == surfaceView);
            }
            return true;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onSingleTapUp(MotionEvent e2) {
            if (PLVSwipeLayout.this.mClickToClose && PLVSwipeLayout.this.isTouchOnSurface(e2)) {
                PLVSwipeLayout.this.close();
            }
            return super.onSingleTapUp(e2);
        }
    }

    public interface SwipeListener {
        void onClose(PLVSwipeLayout layout);

        void onHandRelease(PLVSwipeLayout layout, float xvel, float yvel);

        void onOpen(PLVSwipeLayout layout);

        void onStartClose(PLVSwipeLayout layout);

        void onStartOpen(PLVSwipeLayout layout);

        void onUpdate(PLVSwipeLayout layout, int leftOffset, int topOffset);
    }

    public PLVSwipeLayout(Context context) {
        this(context, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void captureChildrenBound() {
        View currentBottomView = getCurrentBottomView();
        if (getOpenStatus() == Status.Close) {
            this.mViewBoundCache.remove(currentBottomView);
            return;
        }
        View[] viewArr = {getSurfaceView(), currentBottomView};
        for (int i2 = 0; i2 < 2; i2++) {
            View view = viewArr[i2];
            Rect rect = this.mViewBoundCache.get(view);
            if (rect == null) {
                rect = new Rect();
                this.mViewBoundCache.put(view, rect);
            }
            rect.left = view.getLeft();
            rect.top = view.getTop();
            rect.right = view.getRight();
            rect.bottom = view.getBottom();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:56:0x00ac  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void checkCanDrag(android.view.MotionEvent r12) {
        /*
            Method dump skipped, instructions count: 306
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.easefun.polyv.livecommon.ui.widget.swipe.PLVSwipeLayout.checkCanDrag(android.view.MotionEvent):void");
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

    private Rect computeBottomLayoutAreaViaSurface(ShowMode mode, Rect surfaceArea) {
        View currentBottomView = getCurrentBottomView();
        int i2 = surfaceArea.left;
        int i3 = surfaceArea.top;
        int measuredWidth = surfaceArea.right;
        int measuredHeight = surfaceArea.bottom;
        if (mode == ShowMode.PullOut) {
            DragEdge dragEdge = this.mCurrentDragEdge;
            DragEdge dragEdge2 = DragEdge.Left;
            if (dragEdge == dragEdge2) {
                i2 -= this.mDragDistance;
            } else if (dragEdge == DragEdge.Right) {
                i2 = measuredWidth;
            } else {
                i3 = dragEdge == DragEdge.Top ? i3 - this.mDragDistance : measuredHeight;
            }
            if (dragEdge == dragEdge2 || dragEdge == DragEdge.Right) {
                measuredWidth = (currentBottomView != null ? currentBottomView.getMeasuredWidth() : 0) + i2;
            } else {
                measuredHeight = i3 + (currentBottomView != null ? currentBottomView.getMeasuredHeight() : 0);
                measuredWidth = surfaceArea.right;
            }
        } else if (mode == ShowMode.LayDown) {
            DragEdge dragEdge3 = this.mCurrentDragEdge;
            if (dragEdge3 == DragEdge.Left) {
                measuredWidth = i2 + this.mDragDistance;
            } else if (dragEdge3 == DragEdge.Right) {
                i2 = measuredWidth - this.mDragDistance;
            } else if (dragEdge3 == DragEdge.Top) {
                measuredHeight = i3 + this.mDragDistance;
            } else {
                i3 = measuredHeight - this.mDragDistance;
            }
        }
        return new Rect(i2, i3, measuredWidth, measuredHeight);
    }

    private Rect computeSurfaceLayoutArea(boolean open) {
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        if (open) {
            DragEdge dragEdge = this.mCurrentDragEdge;
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

    private int dp2px(float dp) {
        return (int) ((dp * getContext().getResources().getDisplayMetrics().density) + 0.5f);
    }

    private AdapterView getAdapterView() {
        ViewParent parent = getParent();
        if (parent instanceof AdapterView) {
            return (AdapterView) parent;
        }
        return null;
    }

    private float getCurrentOffset() {
        DragEdge dragEdge = this.mCurrentDragEdge;
        if (dragEdge == null) {
            return 0.0f;
        }
        return this.mEdgeSwipesOffset[dragEdge.ordinal()];
    }

    private boolean insideAdapterView() {
        return getAdapterView() != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isTouchOnSurface(MotionEvent ev) {
        View surfaceView = getSurfaceView();
        if (surfaceView == null) {
            return false;
        }
        if (this.hitSurfaceRect == null) {
            this.hitSurfaceRect = new Rect();
        }
        surfaceView.getHitRect(this.hitSurfaceRect);
        return this.hitSurfaceRect.contains((int) ev.getX(), (int) ev.getY());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void performAdapterViewItemClick() {
        AdapterView adapterView;
        int positionForView;
        if (getOpenStatus() != Status.Close) {
            return;
        }
        ViewParent parent = getParent();
        if (!(parent instanceof AdapterView) || (positionForView = (adapterView = (AdapterView) parent).getPositionForView(this)) == -1) {
            return;
        }
        adapterView.performItemClick(adapterView.getChildAt(positionForView - adapterView.getFirstVisiblePosition()), positionForView, adapterView.getAdapter().getItemId(positionForView));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x0012, code lost:
    
        r0 = (android.widget.AdapterView) r0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean performAdapterViewItemLongClick() throws java.lang.NoSuchMethodException, java.lang.SecurityException {
        /*
            r12 = this;
            com.easefun.polyv.livecommon.ui.widget.swipe.PLVSwipeLayout$Status r0 = r12.getOpenStatus()
            com.easefun.polyv.livecommon.ui.widget.swipe.PLVSwipeLayout$Status r1 = com.easefun.polyv.livecommon.ui.widget.swipe.PLVSwipeLayout.Status.Close
            r2 = 0
            if (r0 == r1) goto La
            return r2
        La:
            android.view.ViewParent r0 = r12.getParent()
            boolean r1 = r0 instanceof android.widget.AdapterView
            if (r1 == 0) goto L74
            android.widget.AdapterView r0 = (android.widget.AdapterView) r0
            int r6 = r0.getPositionForView(r12)
            r1 = -1
            if (r6 != r1) goto L1c
            return r2
        L1c:
            long r7 = r0.getItemIdAtPosition(r6)
            java.lang.Class<android.widget.AbsListView> r1 = android.widget.AbsListView.class
            java.lang.String r3 = "performLongPress"
            r4 = 3
            java.lang.Class[] r5 = new java.lang.Class[r4]     // Catch: java.lang.Exception -> L57
            java.lang.Class<android.view.View> r9 = android.view.View.class
            r5[r2] = r9     // Catch: java.lang.Exception -> L57
            java.lang.Class r9 = java.lang.Integer.TYPE     // Catch: java.lang.Exception -> L57
            r10 = 1
            r5[r10] = r9     // Catch: java.lang.Exception -> L57
            java.lang.Class r9 = java.lang.Long.TYPE     // Catch: java.lang.Exception -> L57
            r11 = 2
            r5[r11] = r9     // Catch: java.lang.Exception -> L57
            java.lang.reflect.Method r1 = r1.getDeclaredMethod(r3, r5)     // Catch: java.lang.Exception -> L57
            r1.setAccessible(r10)     // Catch: java.lang.Exception -> L57
            java.lang.Object[] r3 = new java.lang.Object[r4]     // Catch: java.lang.Exception -> L57
            r3[r2] = r12     // Catch: java.lang.Exception -> L57
            java.lang.Integer r4 = java.lang.Integer.valueOf(r6)     // Catch: java.lang.Exception -> L57
            r3[r10] = r4     // Catch: java.lang.Exception -> L57
            java.lang.Long r4 = java.lang.Long.valueOf(r7)     // Catch: java.lang.Exception -> L57
            r3[r11] = r4     // Catch: java.lang.Exception -> L57
            java.lang.Object r1 = r1.invoke(r0, r3)     // Catch: java.lang.Exception -> L57
            java.lang.Boolean r1 = (java.lang.Boolean) r1     // Catch: java.lang.Exception -> L57
            boolean r0 = r1.booleanValue()     // Catch: java.lang.Exception -> L57
            goto L73
        L57:
            r1 = move-exception
            r1.printStackTrace()
            android.widget.AdapterView$OnItemLongClickListener r1 = r0.getOnItemLongClickListener()
            if (r1 == 0) goto L6c
            android.widget.AdapterView$OnItemLongClickListener r3 = r0.getOnItemLongClickListener()
            r4 = r0
            r5 = r12
            boolean r1 = r3.onItemLongClick(r4, r5, r6, r7)
            goto L6d
        L6c:
            r1 = r2
        L6d:
            if (r1 == 0) goto L72
            r0.performHapticFeedback(r2)
        L72:
            r0 = r1
        L73:
            return r0
        L74:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.easefun.polyv.livecommon.ui.widget.swipe.PLVSwipeLayout.performAdapterViewItemLongClick():boolean");
    }

    private void safeBottomView() {
        Status openStatus = getOpenStatus();
        List<View> bottomViews = getBottomViews();
        if (openStatus != Status.Close) {
            View currentBottomView = getCurrentBottomView();
            if (currentBottomView == null || currentBottomView.getVisibility() == 0) {
                return;
            }
            currentBottomView.setVisibility(0);
            return;
        }
        for (View view : bottomViews) {
            if (view != null && view.getVisibility() != 4) {
                view.setVisibility(4);
            }
        }
    }

    private void setCurrentDragEdge(DragEdge dragEdge) {
        this.mCurrentDragEdge = dragEdge;
        updateBottomViews();
    }

    private void updateBottomViews() {
        View currentBottomView = getCurrentBottomView();
        if (currentBottomView != null) {
            DragEdge dragEdge = this.mCurrentDragEdge;
            if (dragEdge == DragEdge.Left || dragEdge == DragEdge.Right) {
                this.mDragDistance = currentBottomView.getMeasuredWidth() - dp2px(getCurrentOffset());
            } else {
                this.mDragDistance = currentBottomView.getMeasuredHeight() - dp2px(getCurrentOffset());
            }
        }
        ShowMode showMode = this.mShowMode;
        if (showMode == ShowMode.PullOut) {
            layoutPullOut();
        } else if (showMode == ShowMode.LayDown) {
            layoutLayDown();
        }
        safeBottomView();
    }

    public void addDrag(DragEdge dragEdge, int childId) {
        addDrag(dragEdge, findViewById(childId), null);
    }

    public void addOnLayoutListener(OnLayout l2) {
        if (this.mOnLayoutListeners == null) {
            this.mOnLayoutListeners = new ArrayList();
        }
        this.mOnLayoutListeners.add(l2);
    }

    public void addRevealListener(int childId, OnRevealListener l2) {
        View viewFindViewById = findViewById(childId);
        if (viewFindViewById == null) {
            throw new IllegalArgumentException("Child does not belong to SwipeListener.");
        }
        if (!this.mShowEntirely.containsKey(viewFindViewById)) {
            this.mShowEntirely.put(viewFindViewById, Boolean.FALSE);
        }
        if (this.mRevealListeners.get(viewFindViewById) == null) {
            this.mRevealListeners.put(viewFindViewById, new ArrayList<>());
        }
        this.mRevealListeners.get(viewFindViewById).add(l2);
    }

    public void addSwipeDenier(SwipeDenier denier) {
        this.mSwipeDeniers.add(denier);
    }

    public void addSwipeListener(SwipeListener l2) {
        this.mSwipeListeners.add(l2);
    }

    @Override // android.view.ViewGroup
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        int iIntValue;
        if (child == null) {
            return;
        }
        try {
            iIntValue = ((Integer) params.getClass().getField("gravity").get(params)).intValue();
        } catch (Exception e2) {
            e2.printStackTrace();
            iIntValue = 0;
        }
        if (iIntValue <= 0) {
            Iterator<Map.Entry<DragEdge, View>> it = this.mDragEdges.entrySet().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Map.Entry<DragEdge, View> next = it.next();
                if (next.getValue() == null) {
                    this.mDragEdges.put(next.getKey(), child);
                    break;
                }
            }
        } else {
            int absoluteGravity = GravityCompat.getAbsoluteGravity(iIntValue, ViewCompat.getLayoutDirection(this));
            if ((absoluteGravity & 3) == 3) {
                this.mDragEdges.put(DragEdge.Left, child);
            }
            if ((absoluteGravity & 5) == 5) {
                this.mDragEdges.put(DragEdge.Right, child);
            }
            if ((absoluteGravity & 48) == 48) {
                this.mDragEdges.put(DragEdge.Top, child);
            }
            if ((absoluteGravity & 80) == 80) {
                this.mDragEdges.put(DragEdge.Bottom, child);
            }
        }
        if (child.getParent() == this) {
            return;
        }
        super.addView(child, index, params);
    }

    public void clearDragEdge() {
        this.mDragEdges.clear();
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
    public void dispatchRevealEvent(final int r15, final int r16, final int r17, final int r18) {
        /*
            Method dump skipped, instructions count: 353
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.easefun.polyv.livecommon.ui.widget.swipe.PLVSwipeLayout.dispatchRevealEvent(int, int, int, int):void");
    }

    public void dispatchSwipeEvent(int surfaceLeft, int surfaceTop, int dx, int dy) {
        DragEdge dragEdge = getDragEdge();
        boolean z2 = false;
        if (dragEdge != DragEdge.Left ? dragEdge != DragEdge.Right ? dragEdge != DragEdge.Top ? dragEdge != DragEdge.Bottom || dy <= 0 : dy >= 0 : dx <= 0 : dx >= 0) {
            z2 = true;
        }
        dispatchSwipeEvent(surfaceLeft, surfaceTop, z2);
    }

    public List<View> getBottomViews() {
        ArrayList arrayList = new ArrayList();
        for (DragEdge dragEdge : DragEdge.values()) {
            arrayList.add(this.mDragEdges.get(dragEdge));
        }
        return arrayList;
    }

    public View getCurrentBottomView() {
        List<View> bottomViews = getBottomViews();
        if (this.mCurrentDragEdge.ordinal() < bottomViews.size()) {
            return bottomViews.get(this.mCurrentDragEdge.ordinal());
        }
        return null;
    }

    public int getDragDistance() {
        return this.mDragDistance;
    }

    public DragEdge getDragEdge() {
        return this.mCurrentDragEdge;
    }

    public Map<DragEdge, View> getDragEdgeMap() {
        return this.mDragEdges;
    }

    @Deprecated
    public List<DragEdge> getDragEdges() {
        return new ArrayList(this.mDragEdges.keySet());
    }

    public Status getOpenStatus() {
        View surfaceView = getSurfaceView();
        if (surfaceView == null) {
            return Status.Close;
        }
        int left = surfaceView.getLeft();
        int top2 = surfaceView.getTop();
        return (left == getPaddingLeft() && top2 == getPaddingTop()) ? Status.Close : (left == getPaddingLeft() - this.mDragDistance || left == getPaddingLeft() + this.mDragDistance || top2 == getPaddingTop() - this.mDragDistance || top2 == getPaddingTop() + this.mDragDistance) ? Status.Open : Status.Middle;
    }

    public Rect getRelativePosition(View child) {
        Rect rect = new Rect(child.getLeft(), child.getTop(), 0, 0);
        View view = child;
        while (view.getParent() != null && view != getRootView() && (view = (View) view.getParent()) != this) {
            rect.left += view.getLeft();
            rect.top += view.getTop();
        }
        rect.right = rect.left + child.getMeasuredWidth();
        rect.bottom = rect.top + child.getMeasuredHeight();
        return rect;
    }

    public ShowMode getShowMode() {
        return this.mShowMode;
    }

    public View getSurfaceView() {
        if (getChildCount() == 0) {
            return null;
        }
        return getChildAt(getChildCount() - 1);
    }

    public float getWillOpenPercentAfterClose() {
        return this.mWillOpenPercentAfterClose;
    }

    public float getWillOpenPercentAfterOpen() {
        return this.mWillOpenPercentAfterOpen;
    }

    public boolean isBottomSwipeEnabled() {
        LinkedHashMap<DragEdge, View> linkedHashMap = this.mDragEdges;
        DragEdge dragEdge = DragEdge.Bottom;
        View view = linkedHashMap.get(dragEdge);
        return view != null && view.getParent() == this && view != getSurfaceView() && this.mSwipesEnabled[dragEdge.ordinal()];
    }

    public boolean isClickToClose() {
        return this.mClickToClose;
    }

    public boolean isLeftSwipeEnabled() {
        LinkedHashMap<DragEdge, View> linkedHashMap = this.mDragEdges;
        DragEdge dragEdge = DragEdge.Left;
        View view = linkedHashMap.get(dragEdge);
        return view != null && view.getParent() == this && view != getSurfaceView() && this.mSwipesEnabled[dragEdge.ordinal()];
    }

    public boolean isRightSwipeEnabled() {
        LinkedHashMap<DragEdge, View> linkedHashMap = this.mDragEdges;
        DragEdge dragEdge = DragEdge.Right;
        View view = linkedHashMap.get(dragEdge);
        return view != null && view.getParent() == this && view != getSurfaceView() && this.mSwipesEnabled[dragEdge.ordinal()];
    }

    public boolean isSwipeEnabled() {
        return this.mSwipeEnabled;
    }

    public boolean isTopSwipeEnabled() {
        LinkedHashMap<DragEdge, View> linkedHashMap = this.mDragEdges;
        DragEdge dragEdge = DragEdge.Top;
        View view = linkedHashMap.get(dragEdge);
        return view != null && view.getParent() == this && view != getSurfaceView() && this.mSwipesEnabled[dragEdge.ordinal()];
    }

    public boolean isViewShowing(View child, Rect relativePosition, DragEdge availableEdge, int surfaceLeft, int surfaceTop, int surfaceRight, int surfaceBottom) {
        int i2 = relativePosition.left;
        int i3 = relativePosition.right;
        int i4 = relativePosition.top;
        int i5 = relativePosition.bottom;
        if (getShowMode() == ShowMode.LayDown) {
            int i6 = AnonymousClass4.$SwitchMap$com$easefun$polyv$livecommon$ui$widget$swipe$PLVSwipeLayout$DragEdge[availableEdge.ordinal()];
            return i6 != 1 ? i6 != 2 ? i6 != 3 ? i6 == 4 && surfaceRight > i2 && surfaceRight <= i3 : surfaceLeft < i3 && surfaceLeft >= i2 : surfaceBottom > i4 && surfaceBottom <= i5 : surfaceTop >= i4 && surfaceTop < i5;
        }
        if (getShowMode() != ShowMode.PullOut) {
            return false;
        }
        int i7 = AnonymousClass4.$SwitchMap$com$easefun$polyv$livecommon$ui$widget$swipe$PLVSwipeLayout$DragEdge[availableEdge.ordinal()];
        return i7 != 1 ? i7 != 2 ? i7 != 3 ? i7 == 4 && i2 <= getWidth() && i3 > getWidth() : i3 >= getPaddingLeft() && i2 < getPaddingLeft() : i4 < getHeight() && i4 >= getPaddingTop() : i4 < getPaddingTop() && i5 >= getPaddingTop();
    }

    public boolean isViewTotallyFirstShowed(View child, Rect relativePosition, DragEdge edge, int surfaceLeft, int surfaceTop, int surfaceRight, int surfaceBottom) {
        if (this.mShowEntirely.get(child).booleanValue()) {
            return false;
        }
        int i2 = relativePosition.left;
        int i3 = relativePosition.right;
        int i4 = relativePosition.top;
        int i5 = relativePosition.bottom;
        if (getShowMode() == ShowMode.LayDown) {
            if ((edge != DragEdge.Right || surfaceRight > i2) && ((edge != DragEdge.Left || surfaceLeft < i3) && ((edge != DragEdge.Top || surfaceTop < i5) && (edge != DragEdge.Bottom || surfaceBottom > i4)))) {
                return false;
            }
        } else {
            if (getShowMode() != ShowMode.PullOut) {
                return false;
            }
            if ((edge != DragEdge.Right || i3 > getWidth()) && ((edge != DragEdge.Left || i2 < getPaddingLeft()) && ((edge != DragEdge.Top || i4 < getPaddingTop()) && (edge != DragEdge.Bottom || i5 > getHeight())))) {
                return false;
            }
        }
        return true;
    }

    public void layoutLayDown() {
        View surfaceView = getSurfaceView();
        Rect rectComputeSurfaceLayoutArea = this.mViewBoundCache.get(surfaceView);
        if (rectComputeSurfaceLayoutArea == null) {
            rectComputeSurfaceLayoutArea = computeSurfaceLayoutArea(false);
        }
        if (surfaceView != null) {
            surfaceView.layout(rectComputeSurfaceLayoutArea.left, rectComputeSurfaceLayoutArea.top, rectComputeSurfaceLayoutArea.right, rectComputeSurfaceLayoutArea.bottom);
            bringChildToFront(surfaceView);
        }
        View currentBottomView = getCurrentBottomView();
        Rect rectComputeBottomLayoutAreaViaSurface = this.mViewBoundCache.get(currentBottomView);
        if (rectComputeBottomLayoutAreaViaSurface == null) {
            rectComputeBottomLayoutAreaViaSurface = computeBottomLayoutAreaViaSurface(ShowMode.LayDown, rectComputeSurfaceLayoutArea);
        }
        if (currentBottomView != null) {
            currentBottomView.layout(rectComputeBottomLayoutAreaViaSurface.left, rectComputeBottomLayoutAreaViaSurface.top, rectComputeBottomLayoutAreaViaSurface.right, rectComputeBottomLayoutAreaViaSurface.bottom);
        }
    }

    public void layoutPullOut() {
        View surfaceView = getSurfaceView();
        Rect rectComputeSurfaceLayoutArea = this.mViewBoundCache.get(surfaceView);
        if (rectComputeSurfaceLayoutArea == null) {
            rectComputeSurfaceLayoutArea = computeSurfaceLayoutArea(false);
        }
        if (surfaceView != null) {
            surfaceView.layout(rectComputeSurfaceLayoutArea.left, rectComputeSurfaceLayoutArea.top, rectComputeSurfaceLayoutArea.right, rectComputeSurfaceLayoutArea.bottom);
            bringChildToFront(surfaceView);
        }
        View currentBottomView = getCurrentBottomView();
        Rect rectComputeBottomLayoutAreaViaSurface = this.mViewBoundCache.get(currentBottomView);
        if (rectComputeBottomLayoutAreaViaSurface == null) {
            rectComputeBottomLayoutAreaViaSurface = computeBottomLayoutAreaViaSurface(ShowMode.PullOut, rectComputeSurfaceLayoutArea);
        }
        if (currentBottomView != null) {
            currentBottomView.layout(rectComputeBottomLayoutAreaViaSurface.left, rectComputeBottomLayoutAreaViaSurface.top, rectComputeBottomLayoutAreaViaSurface.right, rectComputeBottomLayoutAreaViaSurface.bottom);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (insideAdapterView()) {
            if (this.clickListener == null) {
                setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecommon.ui.widget.swipe.PLVSwipeLayout.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v2) {
                        PLVSwipeLayout.this.performAdapterViewItemClick();
                    }
                });
            }
            if (this.longClickListener == null) {
                setOnLongClickListener(new View.OnLongClickListener() { // from class: com.easefun.polyv.livecommon.ui.widget.swipe.PLVSwipeLayout.3
                    @Override // android.view.View.OnLongClickListener
                    public boolean onLongClick(View v2) throws NoSuchMethodException, SecurityException {
                        PLVSwipeLayout.this.performAdapterViewItemLongClick();
                        return true;
                    }
                });
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x0064  */
    @Override // android.view.ViewGroup
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onInterceptTouchEvent(android.view.MotionEvent r5) {
        /*
            r4 = this;
            boolean r0 = r4.isSwipeEnabled()
            r1 = 0
            if (r0 != 0) goto L8
            return r1
        L8:
            boolean r0 = r4.mClickToClose
            r2 = 1
            if (r0 == 0) goto L1c
            com.easefun.polyv.livecommon.ui.widget.swipe.PLVSwipeLayout$Status r0 = r4.getOpenStatus()
            com.easefun.polyv.livecommon.ui.widget.swipe.PLVSwipeLayout$Status r3 = com.easefun.polyv.livecommon.ui.widget.swipe.PLVSwipeLayout.Status.Open
            if (r0 != r3) goto L1c
            boolean r0 = r4.isTouchOnSurface(r5)
            if (r0 == 0) goto L1c
            return r2
        L1c:
            java.util.List<com.easefun.polyv.livecommon.ui.widget.swipe.PLVSwipeLayout$SwipeDenier> r0 = r4.mSwipeDeniers
            java.util.Iterator r0 = r0.iterator()
        L22:
            boolean r3 = r0.hasNext()
            if (r3 == 0) goto L37
            java.lang.Object r3 = r0.next()
            com.easefun.polyv.livecommon.ui.widget.swipe.PLVSwipeLayout$SwipeDenier r3 = (com.easefun.polyv.livecommon.ui.widget.swipe.PLVSwipeLayout.SwipeDenier) r3
            if (r3 == 0) goto L22
            boolean r3 = r3.shouldDenySwipe(r5)
            if (r3 == 0) goto L22
            return r1
        L37:
            int r0 = r5.getAction()
            if (r0 == 0) goto L6c
            if (r0 == r2) goto L64
            r3 = 2
            if (r0 == r3) goto L4b
            r2 = 3
            if (r0 == r2) goto L64
            androidx.customview.widget.ViewDragHelper r0 = r4.mDragHelper
            r0.processTouchEvent(r5)
            goto L89
        L4b:
            boolean r0 = r4.mIsBeingDragged
            r4.checkCanDrag(r5)
            boolean r5 = r4.mIsBeingDragged
            if (r5 == 0) goto L5d
            android.view.ViewParent r5 = r4.getParent()
            if (r5 == 0) goto L5d
            r5.requestDisallowInterceptTouchEvent(r2)
        L5d:
            if (r0 != 0) goto L89
            boolean r5 = r4.mIsBeingDragged
            if (r5 == 0) goto L89
            return r1
        L64:
            r4.mIsBeingDragged = r1
            androidx.customview.widget.ViewDragHelper r0 = r4.mDragHelper
            r0.processTouchEvent(r5)
            goto L89
        L6c:
            androidx.customview.widget.ViewDragHelper r0 = r4.mDragHelper
            r0.processTouchEvent(r5)
            r4.mIsBeingDragged = r1
            float r0 = r5.getRawX()
            r4.sX = r0
            float r5 = r5.getRawY()
            r4.sY = r5
            com.easefun.polyv.livecommon.ui.widget.swipe.PLVSwipeLayout$Status r5 = r4.getOpenStatus()
            com.easefun.polyv.livecommon.ui.widget.swipe.PLVSwipeLayout$Status r0 = com.easefun.polyv.livecommon.ui.widget.swipe.PLVSwipeLayout.Status.Middle
            if (r5 != r0) goto L89
            r4.mIsBeingDragged = r2
        L89:
            boolean r5 = r4.mIsBeingDragged
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.easefun.polyv.livecommon.ui.widget.swipe.PLVSwipeLayout.onInterceptTouchEvent(android.view.MotionEvent):boolean");
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean changed, int l2, int t2, int r2, int b3) {
        updateBottomViews();
        if (this.mOnLayoutListeners != null) {
            for (int i2 = 0; i2 < this.mOnLayoutListeners.size(); i2++) {
                this.mOnLayoutListeners.get(i2).onLayout(this);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0026  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r5) {
        /*
            r4 = this;
            boolean r0 = r4.isSwipeEnabled()
            if (r0 != 0) goto Lb
            boolean r5 = super.onTouchEvent(r5)
            return r5
        Lb:
            int r0 = r5.getActionMasked()
            android.view.GestureDetector r1 = r4.gestureDetector
            r1.onTouchEvent(r5)
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L2e
            if (r0 == r2) goto L26
            r3 = 2
            if (r0 == r3) goto L3f
            r3 = 3
            if (r0 == r3) goto L26
            androidx.customview.widget.ViewDragHelper r3 = r4.mDragHelper
            r3.processTouchEvent(r5)
            goto L52
        L26:
            r4.mIsBeingDragged = r1
            androidx.customview.widget.ViewDragHelper r3 = r4.mDragHelper
            r3.processTouchEvent(r5)
            goto L52
        L2e:
            androidx.customview.widget.ViewDragHelper r3 = r4.mDragHelper
            r3.processTouchEvent(r5)
            float r3 = r5.getRawX()
            r4.sX = r3
            float r3 = r5.getRawY()
            r4.sY = r3
        L3f:
            r4.checkCanDrag(r5)
            boolean r3 = r4.mIsBeingDragged
            if (r3 == 0) goto L52
            android.view.ViewParent r3 = r4.getParent()
            r3.requestDisallowInterceptTouchEvent(r2)
            androidx.customview.widget.ViewDragHelper r3 = r4.mDragHelper
            r3.processTouchEvent(r5)
        L52:
            boolean r5 = super.onTouchEvent(r5)
            if (r5 != 0) goto L5e
            boolean r5 = r4.mIsBeingDragged
            if (r5 != 0) goto L5e
            if (r0 != 0) goto L5f
        L5e:
            r1 = r2
        L5f:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.easefun.polyv.livecommon.ui.widget.swipe.PLVSwipeLayout.onTouchEvent(android.view.MotionEvent):boolean");
    }

    @Override // android.view.ViewGroup
    public void onViewRemoved(View child) {
        for (Map.Entry entry : new HashMap(this.mDragEdges).entrySet()) {
            if (entry.getValue() == child) {
                this.mDragEdges.remove(entry.getKey());
            }
        }
    }

    public void open() {
        open(true, true);
    }

    public void processHandRelease(float xvel, float yvel, boolean isCloseBeforeDragged) {
        float minVelocity = this.mDragHelper.getMinVelocity();
        View surfaceView = getSurfaceView();
        DragEdge dragEdge = this.mCurrentDragEdge;
        if (dragEdge == null || surfaceView == null) {
            return;
        }
        float f2 = isCloseBeforeDragged ? this.mWillOpenPercentAfterClose : this.mWillOpenPercentAfterOpen;
        if (dragEdge == DragEdge.Left) {
            if (xvel > minVelocity) {
                open();
                return;
            }
            if (xvel < (-minVelocity)) {
                close();
                return;
            } else if ((getSurfaceView().getLeft() * 1.0f) / this.mDragDistance > f2) {
                open();
                return;
            } else {
                close();
                return;
            }
        }
        if (dragEdge == DragEdge.Right) {
            if (xvel > minVelocity) {
                close();
                return;
            }
            if (xvel < (-minVelocity)) {
                open();
                return;
            } else if (((-getSurfaceView().getLeft()) * 1.0f) / this.mDragDistance > f2) {
                open();
                return;
            } else {
                close();
                return;
            }
        }
        if (dragEdge == DragEdge.Top) {
            if (yvel > minVelocity) {
                open();
                return;
            }
            if (yvel < (-minVelocity)) {
                close();
                return;
            } else if ((getSurfaceView().getTop() * 1.0f) / this.mDragDistance > f2) {
                open();
                return;
            } else {
                close();
                return;
            }
        }
        if (dragEdge == DragEdge.Bottom) {
            if (yvel > minVelocity) {
                close();
                return;
            }
            if (yvel < (-minVelocity)) {
                open();
            } else if (((-getSurfaceView().getTop()) * 1.0f) / this.mDragDistance > f2) {
                open();
            } else {
                close();
            }
        }
    }

    public void removeAllRevealListeners(int childId) {
        View viewFindViewById = findViewById(childId);
        if (viewFindViewById != null) {
            this.mRevealListeners.remove(viewFindViewById);
            this.mShowEntirely.remove(viewFindViewById);
        }
    }

    public void removeAllSwipeDeniers() {
        this.mSwipeDeniers.clear();
    }

    public void removeAllSwipeListener() {
        this.mSwipeListeners.clear();
    }

    public void removeOnLayoutListener(OnLayout l2) {
        List<OnLayout> list = this.mOnLayoutListeners;
        if (list != null) {
            list.remove(l2);
        }
    }

    public void removeRevealListener(int childId, OnRevealListener l2) {
        View viewFindViewById = findViewById(childId);
        if (viewFindViewById == null) {
            return;
        }
        this.mShowEntirely.remove(viewFindViewById);
        if (this.mRevealListeners.containsKey(viewFindViewById)) {
            this.mRevealListeners.get(viewFindViewById).remove(l2);
        }
    }

    public void removeSwipeDenier(SwipeDenier denier) {
        this.mSwipeDeniers.remove(denier);
    }

    public void removeSwipeListener(SwipeListener l2) {
        this.mSwipeListeners.remove(l2);
    }

    public void setBottomSwipeEnabled(boolean bottomSwipeEnabled) {
        this.mSwipesEnabled[DragEdge.Bottom.ordinal()] = bottomSwipeEnabled;
    }

    @Deprecated
    public void setBottomViewIds(int leftId, int rightId, int topId, int bottomId) {
        addDrag(DragEdge.Left, findViewById(leftId));
        addDrag(DragEdge.Right, findViewById(rightId));
        addDrag(DragEdge.Top, findViewById(topId));
        addDrag(DragEdge.Bottom, findViewById(bottomId));
    }

    public void setClickToClose(boolean mClickToClose) {
        this.mClickToClose = mClickToClose;
    }

    public void setDrag(DragEdge dragEdge, int childId) {
        clearDragEdge();
        addDrag(dragEdge, childId);
    }

    public void setDragDistance(int max) {
        if (max < 0) {
            max = 0;
        }
        this.mDragDistance = dp2px(max);
        requestLayout();
    }

    @Deprecated
    public void setDragEdge(DragEdge dragEdge) {
        clearDragEdge();
        if (getChildCount() >= 2) {
            this.mDragEdges.put(dragEdge, getChildAt(getChildCount() - 2));
        }
        setCurrentDragEdge(dragEdge);
    }

    @Deprecated
    public void setDragEdges(List<DragEdge> dragEdges) {
        clearDragEdge();
        int iMin = Math.min(dragEdges.size(), getChildCount() - 1);
        for (int i2 = 0; i2 < iMin; i2++) {
            this.mDragEdges.put(dragEdges.get(i2), getChildAt(i2));
        }
        if (dragEdges.size() == 0 || dragEdges.contains(DefaultDragEdge)) {
            setCurrentDragEdge(DefaultDragEdge);
        } else {
            setCurrentDragEdge(dragEdges.get(0));
        }
    }

    public void setLeftSwipeEnabled(boolean leftSwipeEnabled) {
        this.mSwipesEnabled[DragEdge.Left.ordinal()] = leftSwipeEnabled;
    }

    @Override // android.view.View
    public void setOnClickListener(View.OnClickListener l2) {
        super.setOnClickListener(l2);
        this.clickListener = l2;
    }

    public void setOnDoubleClickListener(DoubleClickListener doubleClickListener) {
        this.mDoubleClickListener = doubleClickListener;
    }

    @Override // android.view.View
    public void setOnLongClickListener(View.OnLongClickListener l2) {
        super.setOnLongClickListener(l2);
        this.longClickListener = l2;
    }

    public void setRightSwipeEnabled(boolean rightSwipeEnabled) {
        this.mSwipesEnabled[DragEdge.Right.ordinal()] = rightSwipeEnabled;
    }

    public void setShowMode(ShowMode mode) {
        this.mShowMode = mode;
        requestLayout();
    }

    public void setSwipeEnabled(boolean enabled) {
        this.mSwipeEnabled = enabled;
    }

    public void setTopSwipeEnabled(boolean topSwipeEnabled) {
        this.mSwipesEnabled[DragEdge.Top.ordinal()] = topSwipeEnabled;
    }

    public void setWillOpenPercentAfterClose(float willOpenPercentAfterClose) {
        this.mWillOpenPercentAfterClose = willOpenPercentAfterClose;
    }

    public void setWillOpenPercentAfterOpen(float willOpenPercentAfterOpen) {
        this.mWillOpenPercentAfterOpen = willOpenPercentAfterOpen;
    }

    public void toggle() {
        toggle(true);
    }

    public PLVSwipeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public void addDrag(DragEdge dragEdge, View child) {
        addDrag(dragEdge, child, null);
    }

    public void close(boolean smooth) {
        close(smooth, true);
    }

    public void open(boolean smooth) {
        open(smooth, true);
    }

    public void toggle(boolean smooth) {
        if (getOpenStatus() == Status.Open) {
            close(smooth);
        } else if (getOpenStatus() == Status.Close) {
            open(smooth);
        }
    }

    public PLVSwipeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mCurrentDragEdge = DefaultDragEdge;
        this.mDragDistance = 0;
        this.mDragEdges = new LinkedHashMap<>();
        this.mEdgeSwipesOffset = new float[4];
        this.mSwipeListeners = new ArrayList();
        this.mSwipeDeniers = new ArrayList();
        this.mRevealListeners = new HashMap();
        this.mShowEntirely = new HashMap();
        this.mViewBoundCache = new HashMap();
        this.mSwipeEnabled = true;
        this.mSwipesEnabled = new boolean[]{true, true, true, true};
        this.mClickToClose = false;
        this.mWillOpenPercentAfterOpen = 0.75f;
        this.mWillOpenPercentAfterClose = 0.25f;
        this.mDragHelperCallback = new ViewDragHelper.Callback() { // from class: com.easefun.polyv.livecommon.ui.widget.swipe.PLVSwipeLayout.1
            boolean isCloseBeforeDrag = true;

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                if (child == PLVSwipeLayout.this.getSurfaceView()) {
                    int i2 = AnonymousClass4.$SwitchMap$com$easefun$polyv$livecommon$ui$widget$swipe$PLVSwipeLayout$DragEdge[PLVSwipeLayout.this.mCurrentDragEdge.ordinal()];
                    if (i2 == 1 || i2 == 2) {
                        return PLVSwipeLayout.this.getPaddingLeft();
                    }
                    if (i2 != 3) {
                        if (i2 == 4) {
                            if (left > PLVSwipeLayout.this.getPaddingLeft()) {
                                return PLVSwipeLayout.this.getPaddingLeft();
                            }
                            if (left < PLVSwipeLayout.this.getPaddingLeft() - PLVSwipeLayout.this.mDragDistance) {
                                return PLVSwipeLayout.this.getPaddingLeft() - PLVSwipeLayout.this.mDragDistance;
                            }
                        }
                    } else {
                        if (left < PLVSwipeLayout.this.getPaddingLeft()) {
                            return PLVSwipeLayout.this.getPaddingLeft();
                        }
                        if (left > PLVSwipeLayout.this.getPaddingLeft() + PLVSwipeLayout.this.mDragDistance) {
                            return PLVSwipeLayout.this.getPaddingLeft() + PLVSwipeLayout.this.mDragDistance;
                        }
                    }
                } else if (PLVSwipeLayout.this.getCurrentBottomView() == child) {
                    int i3 = AnonymousClass4.$SwitchMap$com$easefun$polyv$livecommon$ui$widget$swipe$PLVSwipeLayout$DragEdge[PLVSwipeLayout.this.mCurrentDragEdge.ordinal()];
                    if (i3 == 1 || i3 == 2) {
                        return PLVSwipeLayout.this.getPaddingLeft();
                    }
                    if (i3 != 3) {
                        if (i3 == 4 && PLVSwipeLayout.this.mShowMode == ShowMode.PullOut && left < PLVSwipeLayout.this.getMeasuredWidth() - PLVSwipeLayout.this.mDragDistance) {
                            return PLVSwipeLayout.this.getMeasuredWidth() - PLVSwipeLayout.this.mDragDistance;
                        }
                    } else if (PLVSwipeLayout.this.mShowMode == ShowMode.PullOut && left > PLVSwipeLayout.this.getPaddingLeft()) {
                        return PLVSwipeLayout.this.getPaddingLeft();
                    }
                }
                return left;
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int clampViewPositionVertical(View child, int top2, int dy) {
                if (child == PLVSwipeLayout.this.getSurfaceView()) {
                    int i2 = AnonymousClass4.$SwitchMap$com$easefun$polyv$livecommon$ui$widget$swipe$PLVSwipeLayout$DragEdge[PLVSwipeLayout.this.mCurrentDragEdge.ordinal()];
                    if (i2 != 1) {
                        if (i2 != 2) {
                            if (i2 == 3 || i2 == 4) {
                                return PLVSwipeLayout.this.getPaddingTop();
                            }
                        } else {
                            if (top2 < PLVSwipeLayout.this.getPaddingTop() - PLVSwipeLayout.this.mDragDistance) {
                                return PLVSwipeLayout.this.getPaddingTop() - PLVSwipeLayout.this.mDragDistance;
                            }
                            if (top2 > PLVSwipeLayout.this.getPaddingTop()) {
                                return PLVSwipeLayout.this.getPaddingTop();
                            }
                        }
                    } else {
                        if (top2 < PLVSwipeLayout.this.getPaddingTop()) {
                            return PLVSwipeLayout.this.getPaddingTop();
                        }
                        if (top2 > PLVSwipeLayout.this.getPaddingTop() + PLVSwipeLayout.this.mDragDistance) {
                            return PLVSwipeLayout.this.getPaddingTop() + PLVSwipeLayout.this.mDragDistance;
                        }
                    }
                } else {
                    View surfaceView = PLVSwipeLayout.this.getSurfaceView();
                    int top3 = surfaceView == null ? 0 : surfaceView.getTop();
                    int i3 = AnonymousClass4.$SwitchMap$com$easefun$polyv$livecommon$ui$widget$swipe$PLVSwipeLayout$DragEdge[PLVSwipeLayout.this.mCurrentDragEdge.ordinal()];
                    if (i3 != 1) {
                        if (i3 != 2) {
                            if (i3 == 3 || i3 == 4) {
                                return PLVSwipeLayout.this.getPaddingTop();
                            }
                        } else if (PLVSwipeLayout.this.mShowMode != ShowMode.PullOut) {
                            int i4 = top3 + dy;
                            if (i4 >= PLVSwipeLayout.this.getPaddingTop()) {
                                return PLVSwipeLayout.this.getPaddingTop();
                            }
                            if (i4 <= PLVSwipeLayout.this.getPaddingTop() - PLVSwipeLayout.this.mDragDistance) {
                                return PLVSwipeLayout.this.getPaddingTop() - PLVSwipeLayout.this.mDragDistance;
                            }
                        } else if (top2 < PLVSwipeLayout.this.getMeasuredHeight() - PLVSwipeLayout.this.mDragDistance) {
                            return PLVSwipeLayout.this.getMeasuredHeight() - PLVSwipeLayout.this.mDragDistance;
                        }
                    } else if (PLVSwipeLayout.this.mShowMode != ShowMode.PullOut) {
                        int i5 = top3 + dy;
                        if (i5 < PLVSwipeLayout.this.getPaddingTop()) {
                            return PLVSwipeLayout.this.getPaddingTop();
                        }
                        if (i5 > PLVSwipeLayout.this.getPaddingTop() + PLVSwipeLayout.this.mDragDistance) {
                            return PLVSwipeLayout.this.getPaddingTop() + PLVSwipeLayout.this.mDragDistance;
                        }
                    } else if (top2 > PLVSwipeLayout.this.getPaddingTop()) {
                        return PLVSwipeLayout.this.getPaddingTop();
                    }
                }
                return top2;
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int getViewHorizontalDragRange(View child) {
                return PLVSwipeLayout.this.mDragDistance;
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int getViewVerticalDragRange(View child) {
                return PLVSwipeLayout.this.mDragDistance;
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public void onViewPositionChanged(View changedView, int left, int top2, int dx, int dy) {
                View surfaceView = PLVSwipeLayout.this.getSurfaceView();
                if (surfaceView == null) {
                    return;
                }
                View currentBottomView = PLVSwipeLayout.this.getCurrentBottomView();
                int left2 = surfaceView.getLeft();
                int right = surfaceView.getRight();
                int top3 = surfaceView.getTop();
                int bottom = surfaceView.getBottom();
                if (changedView == surfaceView) {
                    if (PLVSwipeLayout.this.mShowMode == ShowMode.PullOut && currentBottomView != null) {
                        if (PLVSwipeLayout.this.mCurrentDragEdge == DragEdge.Left || PLVSwipeLayout.this.mCurrentDragEdge == DragEdge.Right) {
                            currentBottomView.offsetLeftAndRight(dx);
                        } else {
                            currentBottomView.offsetTopAndBottom(dy);
                        }
                    }
                } else if (PLVSwipeLayout.this.getBottomViews().contains(changedView)) {
                    if (PLVSwipeLayout.this.mShowMode == ShowMode.PullOut) {
                        surfaceView.offsetLeftAndRight(dx);
                        surfaceView.offsetTopAndBottom(dy);
                    } else {
                        PLVSwipeLayout pLVSwipeLayout = PLVSwipeLayout.this;
                        Rect rectComputeBottomLayDown = pLVSwipeLayout.computeBottomLayDown(pLVSwipeLayout.mCurrentDragEdge);
                        if (currentBottomView != null) {
                            currentBottomView.layout(rectComputeBottomLayDown.left, rectComputeBottomLayDown.top, rectComputeBottomLayDown.right, rectComputeBottomLayDown.bottom);
                        }
                        int left3 = surfaceView.getLeft() + dx;
                        int top4 = surfaceView.getTop() + dy;
                        if (PLVSwipeLayout.this.mCurrentDragEdge == DragEdge.Left && left3 < PLVSwipeLayout.this.getPaddingLeft()) {
                            left3 = PLVSwipeLayout.this.getPaddingLeft();
                        } else if (PLVSwipeLayout.this.mCurrentDragEdge == DragEdge.Right && left3 > PLVSwipeLayout.this.getPaddingLeft()) {
                            left3 = PLVSwipeLayout.this.getPaddingLeft();
                        } else if (PLVSwipeLayout.this.mCurrentDragEdge == DragEdge.Top && top4 < PLVSwipeLayout.this.getPaddingTop()) {
                            top4 = PLVSwipeLayout.this.getPaddingTop();
                        } else if (PLVSwipeLayout.this.mCurrentDragEdge == DragEdge.Bottom && top4 > PLVSwipeLayout.this.getPaddingTop()) {
                            top4 = PLVSwipeLayout.this.getPaddingTop();
                        }
                        surfaceView.layout(left3, top4, PLVSwipeLayout.this.getMeasuredWidth() + left3, PLVSwipeLayout.this.getMeasuredHeight() + top4);
                    }
                }
                PLVSwipeLayout.this.dispatchRevealEvent(left2, top3, right, bottom);
                PLVSwipeLayout.this.dispatchSwipeEvent(left2, top3, dx, dy);
                PLVSwipeLayout.this.invalidate();
                PLVSwipeLayout.this.captureChildrenBound();
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
                PLVSwipeLayout.this.processHandRelease(xvel, yvel, this.isCloseBeforeDrag);
                Iterator it = PLVSwipeLayout.this.mSwipeListeners.iterator();
                while (it.hasNext()) {
                    ((SwipeListener) it.next()).onHandRelease(PLVSwipeLayout.this, xvel, yvel);
                }
                PLVSwipeLayout.this.invalidate();
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public boolean tryCaptureView(View child, int pointerId) {
                boolean z2 = child == PLVSwipeLayout.this.getSurfaceView() || PLVSwipeLayout.this.getBottomViews().contains(child);
                if (z2) {
                    this.isCloseBeforeDrag = PLVSwipeLayout.this.getOpenStatus() == Status.Close;
                }
                return z2;
            }
        };
        this.mEventCounter = 0;
        this.sX = -1.0f;
        this.sY = -1.0f;
        this.gestureDetector = new GestureDetector(getContext(), new SwipeDetector());
        this.mDragHelper = ViewDragHelper.create(this, this.mDragHelperCallback);
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.PLVSwipeLayout);
        int i2 = typedArrayObtainStyledAttributes.getInt(R.styleable.PLVSwipeLayout_drag_edge, 2);
        float[] fArr = this.mEdgeSwipesOffset;
        DragEdge dragEdge = DragEdge.Left;
        fArr[dragEdge.ordinal()] = typedArrayObtainStyledAttributes.getDimension(R.styleable.PLVSwipeLayout_leftEdgeSwipeOffset, 0.0f);
        float[] fArr2 = this.mEdgeSwipesOffset;
        DragEdge dragEdge2 = DragEdge.Right;
        fArr2[dragEdge2.ordinal()] = typedArrayObtainStyledAttributes.getDimension(R.styleable.PLVSwipeLayout_rightEdgeSwipeOffset, 0.0f);
        float[] fArr3 = this.mEdgeSwipesOffset;
        DragEdge dragEdge3 = DragEdge.Top;
        fArr3[dragEdge3.ordinal()] = typedArrayObtainStyledAttributes.getDimension(R.styleable.PLVSwipeLayout_topEdgeSwipeOffset, 0.0f);
        float[] fArr4 = this.mEdgeSwipesOffset;
        DragEdge dragEdge4 = DragEdge.Bottom;
        fArr4[dragEdge4.ordinal()] = typedArrayObtainStyledAttributes.getDimension(R.styleable.PLVSwipeLayout_bottomEdgeSwipeOffset, 0.0f);
        setClickToClose(typedArrayObtainStyledAttributes.getBoolean(R.styleable.PLVSwipeLayout_clickToClose, this.mClickToClose));
        if ((i2 & 1) == 1) {
            this.mDragEdges.put(dragEdge, null);
        }
        if ((i2 & 4) == 4) {
            this.mDragEdges.put(dragEdge3, null);
        }
        if ((i2 & 2) == 2) {
            this.mDragEdges.put(dragEdge2, null);
        }
        if ((i2 & 8) == 8) {
            this.mDragEdges.put(dragEdge4, null);
        }
        this.mShowMode = ShowMode.values()[typedArrayObtainStyledAttributes.getInt(R.styleable.PLVSwipeLayout_show_mode, ShowMode.PullOut.ordinal())];
        typedArrayObtainStyledAttributes.recycle();
    }

    public void addDrag(DragEdge dragEdge, View child, ViewGroup.LayoutParams params) {
        int i2;
        if (child == null) {
            return;
        }
        if (params == null) {
            params = generateDefaultLayoutParams();
        }
        if (!checkLayoutParams(params)) {
            params = generateLayoutParams(params);
        }
        int i3 = AnonymousClass4.$SwitchMap$com$easefun$polyv$livecommon$ui$widget$swipe$PLVSwipeLayout$DragEdge[dragEdge.ordinal()];
        if (i3 == 1) {
            i2 = 48;
        } else if (i3 != 2) {
            i2 = 3;
            if (i3 != 3) {
                i2 = i3 != 4 ? -1 : 5;
            }
        } else {
            i2 = 80;
        }
        if (params instanceof FrameLayout.LayoutParams) {
            ((FrameLayout.LayoutParams) params).gravity = i2;
        }
        addView(child, 0, params);
    }

    public void close(boolean smooth, boolean notify) {
        View surfaceView = getSurfaceView();
        if (surfaceView == null) {
            return;
        }
        if (smooth) {
            this.mDragHelper.smoothSlideViewTo(getSurfaceView(), getPaddingLeft(), getPaddingTop());
        } else {
            Rect rectComputeSurfaceLayoutArea = computeSurfaceLayoutArea(false);
            int left = rectComputeSurfaceLayoutArea.left - surfaceView.getLeft();
            int top2 = rectComputeSurfaceLayoutArea.top - surfaceView.getTop();
            surfaceView.layout(rectComputeSurfaceLayoutArea.left, rectComputeSurfaceLayoutArea.top, rectComputeSurfaceLayoutArea.right, rectComputeSurfaceLayoutArea.bottom);
            if (notify) {
                dispatchRevealEvent(rectComputeSurfaceLayoutArea.left, rectComputeSurfaceLayoutArea.top, rectComputeSurfaceLayoutArea.right, rectComputeSurfaceLayoutArea.bottom);
                dispatchSwipeEvent(rectComputeSurfaceLayoutArea.left, rectComputeSurfaceLayoutArea.top, left, top2);
            } else {
                safeBottomView();
            }
        }
        invalidate();
    }

    public void open(boolean smooth, boolean notify) {
        View surfaceView = getSurfaceView();
        View currentBottomView = getCurrentBottomView();
        if (surfaceView == null) {
            return;
        }
        Rect rectComputeSurfaceLayoutArea = computeSurfaceLayoutArea(true);
        if (smooth) {
            this.mDragHelper.smoothSlideViewTo(surfaceView, rectComputeSurfaceLayoutArea.left, rectComputeSurfaceLayoutArea.top);
        } else {
            int left = rectComputeSurfaceLayoutArea.left - surfaceView.getLeft();
            int top2 = rectComputeSurfaceLayoutArea.top - surfaceView.getTop();
            surfaceView.layout(rectComputeSurfaceLayoutArea.left, rectComputeSurfaceLayoutArea.top, rectComputeSurfaceLayoutArea.right, rectComputeSurfaceLayoutArea.bottom);
            ShowMode showMode = getShowMode();
            ShowMode showMode2 = ShowMode.PullOut;
            if (showMode == showMode2) {
                Rect rectComputeBottomLayoutAreaViaSurface = computeBottomLayoutAreaViaSurface(showMode2, rectComputeSurfaceLayoutArea);
                if (currentBottomView != null) {
                    currentBottomView.layout(rectComputeBottomLayoutAreaViaSurface.left, rectComputeBottomLayoutAreaViaSurface.top, rectComputeBottomLayoutAreaViaSurface.right, rectComputeBottomLayoutAreaViaSurface.bottom);
                }
            }
            if (notify) {
                dispatchRevealEvent(rectComputeSurfaceLayoutArea.left, rectComputeSurfaceLayoutArea.top, rectComputeSurfaceLayoutArea.right, rectComputeSurfaceLayoutArea.bottom);
                dispatchSwipeEvent(rectComputeSurfaceLayoutArea.left, rectComputeSurfaceLayoutArea.top, left, top2);
            } else {
                safeBottomView();
            }
        }
        invalidate();
    }

    public void setDrag(DragEdge dragEdge, View child) {
        clearDragEdge();
        addDrag(dragEdge, child);
    }

    public void dispatchSwipeEvent(int surfaceLeft, int surfaceTop, boolean open) {
        safeBottomView();
        Status openStatus = getOpenStatus();
        if (this.mSwipeListeners.isEmpty()) {
            return;
        }
        this.mEventCounter++;
        for (SwipeListener swipeListener : this.mSwipeListeners) {
            if (this.mEventCounter == 1) {
                if (open) {
                    swipeListener.onStartOpen(this);
                } else {
                    swipeListener.onStartClose(this);
                }
            }
            swipeListener.onUpdate(this, surfaceLeft - getPaddingLeft(), surfaceTop - getPaddingTop());
        }
        if (openStatus == Status.Close) {
            Iterator<SwipeListener> it = this.mSwipeListeners.iterator();
            while (it.hasNext()) {
                it.next().onClose(this);
            }
            this.mEventCounter = 0;
        }
        if (openStatus == Status.Open) {
            View currentBottomView = getCurrentBottomView();
            if (currentBottomView != null) {
                currentBottomView.setEnabled(true);
            }
            Iterator<SwipeListener> it2 = this.mSwipeListeners.iterator();
            while (it2.hasNext()) {
                it2.next().onOpen(this);
            }
            this.mEventCounter = 0;
        }
    }

    public void addRevealListener(int[] childIds, OnRevealListener l2) {
        for (int i2 : childIds) {
            addRevealListener(i2, l2);
        }
    }

    @Deprecated
    public void setDragEdges(DragEdge... mDragEdges) {
        clearDragEdge();
        setDragEdges(Arrays.asList(mDragEdges));
    }

    public void open(DragEdge edge) {
        setCurrentDragEdge(edge);
        open(true, true);
    }

    public void open(boolean smooth, DragEdge edge) {
        setCurrentDragEdge(edge);
        open(smooth, true);
    }

    public void open(boolean smooth, boolean notify, DragEdge edge) {
        setCurrentDragEdge(edge);
        open(smooth, notify);
    }
}
