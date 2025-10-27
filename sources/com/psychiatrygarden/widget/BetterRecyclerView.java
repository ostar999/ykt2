package com.psychiatrygarden.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import androidx.annotation.Nullable;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewConfigurationCompat;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes6.dex */
public class BetterRecyclerView extends RecyclerView {
    private static final int INVALID_POINTER = -1;
    private int mInitialTouchX;
    private int mInitialTouchY;
    private int mScrollPointerId;
    private int mTouchSlop;

    public BetterRecyclerView(Context context) {
        this(context, null);
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent e2) {
        int actionMasked = MotionEventCompat.getActionMasked(e2);
        int actionIndex = MotionEventCompat.getActionIndex(e2);
        if (actionMasked == 0) {
            this.mScrollPointerId = MotionEventCompat.getPointerId(e2, 0);
            this.mInitialTouchX = (int) (e2.getX() + 0.5f);
            this.mInitialTouchY = (int) (e2.getY() + 0.5f);
            return super.onInterceptTouchEvent(e2);
        }
        if (actionMasked != 2) {
            if (actionMasked != 5) {
                return super.onInterceptTouchEvent(e2);
            }
            this.mScrollPointerId = MotionEventCompat.getPointerId(e2, actionIndex);
            this.mInitialTouchX = (int) (MotionEventCompat.getX(e2, actionIndex) + 0.5f);
            this.mInitialTouchY = (int) (MotionEventCompat.getY(e2, actionIndex) + 0.5f);
            return super.onInterceptTouchEvent(e2);
        }
        int iFindPointerIndex = MotionEventCompat.findPointerIndex(e2, this.mScrollPointerId);
        if (iFindPointerIndex < 0) {
            return false;
        }
        int x2 = (int) (MotionEventCompat.getX(e2, iFindPointerIndex) + 0.5f);
        int y2 = (int) (MotionEventCompat.getY(e2, iFindPointerIndex) + 0.5f);
        if (getScrollState() == 1) {
            return super.onInterceptTouchEvent(e2);
        }
        int i2 = x2 - this.mInitialTouchX;
        int i3 = y2 - this.mInitialTouchY;
        boolean zCanScrollHorizontally = getLayoutManager().canScrollHorizontally();
        boolean zCanScrollVertically = getLayoutManager().canScrollVertically();
        boolean z2 = zCanScrollHorizontally && Math.abs(i2) > this.mTouchSlop && (Math.abs(i2) >= Math.abs(i3) || zCanScrollVertically);
        if (zCanScrollVertically && Math.abs(i3) > this.mTouchSlop && (Math.abs(i3) >= Math.abs(i2) || zCanScrollHorizontally)) {
            z2 = true;
        }
        return z2 && super.onInterceptTouchEvent(e2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView
    public void setScrollingTouchSlop(int slopConstant) {
        super.setScrollingTouchSlop(slopConstant);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
        if (slopConstant == 0) {
            this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        } else {
            if (slopConstant != 1) {
                return;
            }
            this.mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(viewConfiguration);
        }
    }

    public BetterRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BetterRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mScrollPointerId = -1;
        this.mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }
}
