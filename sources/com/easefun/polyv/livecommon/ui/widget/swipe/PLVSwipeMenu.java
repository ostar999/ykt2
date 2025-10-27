package com.easefun.polyv.livecommon.ui.widget.swipe;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/* loaded from: classes3.dex */
public class PLVSwipeMenu extends ViewGroup {
    public static PLVSwipeMenu swipeMenu;
    private int downX;
    private boolean enabledSwipe;
    private boolean haveShowRight;
    private boolean isRequestEv;
    private float lastX;
    private float lastY;
    private int moveX;
    private int moved;
    private OnShowRightChangedListener onShowRightChangedListener;
    private Scroller scroller;

    public interface OnShowRightChangedListener {
        void onChanged(boolean haveShowRight);
    }

    public PLVSwipeMenu(Context context) {
        this(context, null);
    }

    public static void closeMenu() {
        PLVSwipeMenu pLVSwipeMenu = swipeMenu;
        if (pLVSwipeMenu != null) {
            pLVSwipeMenu.closeMenus();
            swipeMenu = null;
        }
    }

    private void smoothScrollTo(int destX, int destY) {
        int scrollX = getScrollX();
        this.scroller.startScroll(scrollX, 0, destX - scrollX, 0, 100);
        invalidate();
    }

    public void closeMenus() {
        smoothScrollTo(0, 0);
        this.haveShowRight = false;
        OnShowRightChangedListener onShowRightChangedListener = this.onShowRightChangedListener;
        if (onShowRightChangedListener != null) {
            onShowRightChangedListener.onChanged(false);
        }
    }

    @Override // android.view.View
    public void computeScroll() {
        if (this.scroller.computeScrollOffset()) {
            scrollTo(this.scroller.getCurrX(), this.scroller.getCurrY());
            postInvalidate();
        }
    }

    public void enabledSwipe(boolean enabledSwipe) {
        this.enabledSwipe = enabledSwipe;
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new ViewGroup.MarginLayoutParams(getContext(), attrs);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        PLVSwipeMenu pLVSwipeMenu = swipeMenu;
        if (pLVSwipeMenu == null || pLVSwipeMenu != this) {
            return;
        }
        pLVSwipeMenu.closeMenus();
        swipeMenu = null;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean changed, int l2, int t2, int r2, int b3) {
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if (i2 == 0) {
                childAt.layout(l2, t2, r2, b3);
            } else if (i2 == 1) {
                childAt.layout(r2, t2, childAt.getMeasuredWidth() + r2, b3);
            }
        }
    }

    @Override // android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int size = View.MeasureSpec.getSize(widthMeasureSpec);
        View childAt = getChildAt(0);
        setMeasuredDimension(size, getChildAt(0).getMeasuredHeight() + ((ViewGroup.MarginLayoutParams) childAt.getLayoutParams()).topMargin + ((ViewGroup.MarginLayoutParams) childAt.getLayoutParams()).bottomMargin);
    }

    @Override // android.view.View
    public void onSizeChanged(int w2, int h2, int oldw, int oldh) {
        super.onSizeChanged(w2, h2, oldw, oldh);
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x00a2  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r8) {
        /*
            Method dump skipped, instructions count: 247
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.easefun.polyv.livecommon.ui.widget.swipe.PLVSwipeMenu.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public void openMenus() {
        this.haveShowRight = true;
        swipeMenu = this;
        smoothScrollTo(getChildAt(1).getMeasuredWidth(), 0);
        OnShowRightChangedListener onShowRightChangedListener = this.onShowRightChangedListener;
        if (onShowRightChangedListener != null) {
            onShowRightChangedListener.onChanged(this.haveShowRight);
        }
    }

    public void setOnShowRightChangedListener(OnShowRightChangedListener l2) {
        this.onShowRightChangedListener = l2;
    }

    public PLVSwipeMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PLVSwipeMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.scroller = new Scroller(context);
    }
}
