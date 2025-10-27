package com.psychiatrygarden.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

/* loaded from: classes6.dex */
public class ByeBurgerFloatButtonBehavior extends ByeBurgerBehavior {
    public ByeBurgerFloatButtonBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        if (this.canInit) {
            this.mAnimateHelper = ScaleAnimateHelper.get(child);
            this.canInit = false;
        }
        return super.layoutDependsOn(parent, child, dependency);
    }

    @Override // com.psychiatrygarden.utils.ByeBurgerBehavior
    public void onNestPreScrollInit(View child) {
    }
}
