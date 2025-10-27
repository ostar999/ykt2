package com.psychiatrygarden.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

/* loaded from: classes6.dex */
public abstract class ByeBurgerBehavior extends CoordinatorLayout.Behavior<View> {
    protected boolean canInit;
    protected boolean isFirstMove;
    protected AnimateHelper mAnimateHelper;
    protected final int mTouchSlop;

    public ByeBurgerBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.isFirstMove = true;
        this.canInit = true;
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public static ByeBurgerBehavior from(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (!(layoutParams instanceof CoordinatorLayout.LayoutParams)) {
            throw new IllegalArgumentException("The view is not a child of CoordinatorLayout");
        }
        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) layoutParams).getBehavior();
        if (behavior instanceof ByeBurgerBehavior) {
            return (ByeBurgerBehavior) behavior;
        }
        throw new IllegalArgumentException("The view is not associated with ByeBurgerBehavior");
    }

    public void hide() {
        this.mAnimateHelper.hide();
    }

    public abstract void onNestPreScrollInit(View child);

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        onNestPreScrollInit(child);
        if (Math.abs(dy) > 2) {
            if (dy < 0) {
                if (this.mAnimateHelper.getState() == 0) {
                    this.mAnimateHelper.show();
                }
            } else {
                if (dy <= 0 || this.mAnimateHelper.getState() != 1) {
                    return;
                }
                this.mAnimateHelper.hide();
            }
        }
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & 2) != 0;
    }

    public void show() {
        this.mAnimateHelper.show();
    }
}
