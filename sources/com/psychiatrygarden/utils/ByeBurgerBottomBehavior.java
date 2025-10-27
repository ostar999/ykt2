package com.psychiatrygarden.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

/* loaded from: classes6.dex */
public class ByeBurgerBottomBehavior extends ByeBurgerBehavior {
    public ByeBurgerBottomBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        return true;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        if (this.canInit) {
            this.canInit = false;
            TranslateAnimateHelper translateAnimateHelper = TranslateAnimateHelper.get(child);
            this.mAnimateHelper = translateAnimateHelper;
            translateAnimateHelper.setStartY(child.getY());
            this.mAnimateHelper.setMode(TranslateAnimateHelper.MODE_BOTTOM);
        }
        return super.onDependentViewChanged(parent, child, dependency);
    }

    @Override // com.psychiatrygarden.utils.ByeBurgerBehavior
    public void onNestPreScrollInit(View child) {
    }
}
