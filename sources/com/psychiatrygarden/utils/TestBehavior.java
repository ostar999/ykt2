package com.psychiatrygarden.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.OverScroller;
import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.appbar.AppBarLayout;
import java.lang.reflect.Field;

/* loaded from: classes6.dex */
public class TestBehavior extends AppBarLayout.Behavior {
    private static final String TAG = "TestBehavior";
    private static final int TYPE_FLING = 1;
    private boolean isFlinging;
    private boolean shouldBlockNestedScroll;

    public TestBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private Field getFlingRunnableField() throws NoSuchFieldException {
        try {
            return getClass().getSuperclass().getSuperclass().getDeclaredField("mFlingRunnable");
        } catch (NoSuchFieldException e2) {
            e2.printStackTrace();
            return getClass().getSuperclass().getSuperclass().getSuperclass().getDeclaredField("flingRunnable");
        }
    }

    private Field getScrollerField() throws NoSuchFieldException {
        try {
            return getClass().getSuperclass().getSuperclass().getDeclaredField("mScroller");
        } catch (NoSuchFieldException e2) {
            e2.printStackTrace();
            return getClass().getSuperclass().getSuperclass().getSuperclass().getDeclaredField("scroller");
        }
    }

    private void stopAppbarLayoutFling(AppBarLayout appBarLayout) throws IllegalAccessException, SecurityException, IllegalArgumentException {
        try {
            Field flingRunnableField = getFlingRunnableField();
            Field scrollerField = getScrollerField();
            flingRunnableField.setAccessible(true);
            scrollerField.setAccessible(true);
            Runnable runnable = (Runnable) flingRunnableField.get(this);
            OverScroller overScroller = (OverScroller) scrollerField.get(this);
            if (runnable != null) {
                appBarLayout.removeCallbacks(runnable);
                flingRunnableField.set(this, null);
            }
            if (overScroller == null || overScroller.isFinished()) {
                return;
            }
            overScroller.abortAnimation();
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
        } catch (NoSuchFieldException e3) {
            e3.printStackTrace();
        }
    }

    @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior, com.google.android.material.appbar.HeaderBehavior, androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public /* bridge */ /* synthetic */ boolean onTouchEvent(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull MotionEvent ev) {
        return super.onTouchEvent(parent, child, ev);
    }

    @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior, com.google.android.material.appbar.HeaderBehavior, androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, AppBarLayout child, MotionEvent ev) throws IllegalAccessException, SecurityException, IllegalArgumentException {
        this.shouldBlockNestedScroll = false;
        if (this.isFlinging) {
            this.shouldBlockNestedScroll = true;
        }
        if (ev.getActionMasked() == 0) {
            stopAppbarLayoutFling(child);
        }
        return super.onInterceptTouchEvent(parent, (View) child, ev);
    }

    @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior, androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, int dx, int dy, int[] consumed, int type) {
        if (type == 1) {
            this.isFlinging = true;
        }
        if (this.shouldBlockNestedScroll) {
            return;
        }
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        if (this.shouldBlockNestedScroll) {
            return;
        }
        super.onNestedScroll(coordinatorLayout, (CoordinatorLayout) child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
    }

    @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior, androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onStartNestedScroll(CoordinatorLayout parent, AppBarLayout child, View directTargetChild, View target, int nestedScrollAxes, int type) throws IllegalAccessException, SecurityException, IllegalArgumentException {
        stopAppbarLayoutFling(child);
        return super.onStartNestedScroll(parent, child, directTargetChild, target, nestedScrollAxes, type);
    }

    @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior, androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout abl, View target, int type) {
        super.onStopNestedScroll(coordinatorLayout, abl, target, type);
        this.isFlinging = false;
        this.shouldBlockNestedScroll = false;
    }
}
