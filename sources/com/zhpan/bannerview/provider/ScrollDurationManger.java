package com.zhpan.bannerview.provider;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.zhpan.bannerview.utils.BannerUtils;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: classes8.dex */
public class ScrollDurationManger extends LinearLayoutManager {
    private final LinearLayoutManager mParent;
    private final int scrollDuration;

    public ScrollDurationManger(ViewPager2 viewPager2, int i2, LinearLayoutManager linearLayoutManager) {
        super(viewPager2.getContext(), linearLayoutManager.getOrientation(), false);
        this.scrollDuration = i2;
        this.mParent = linearLayoutManager;
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager
    public void calculateExtraLayoutSpace(@NonNull RecyclerView.State state, @NonNull int[] iArr) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        try {
            Method declaredMethod = this.mParent.getClass().getDeclaredMethod("calculateExtraLayoutSpace", state.getClass(), iArr.getClass());
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(this.mParent, state, iArr);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e2) {
            e2.printStackTrace();
            BannerUtils.log(e2.getMessage());
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onInitializeAccessibilityNodeInfo(@NonNull RecyclerView.Recycler recycler, @NonNull RecyclerView.State state, @NonNull AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        this.mParent.onInitializeAccessibilityNodeInfo(recycler, state, accessibilityNodeInfoCompat);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean performAccessibilityAction(@NonNull RecyclerView.Recycler recycler, @NonNull RecyclerView.State state, int i2, @Nullable Bundle bundle) {
        return this.mParent.performAccessibilityAction(recycler, state, i2, bundle);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean requestChildRectangleOnScreen(@NonNull RecyclerView recyclerView, @NonNull View view, @NonNull Rect rect, boolean z2, boolean z3) {
        return false;
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int i2) {
        LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext()) { // from class: com.zhpan.bannerview.provider.ScrollDurationManger.1
            @Override // androidx.recyclerview.widget.LinearSmoothScroller
            public int calculateTimeForDeceleration(int i3) {
                return ScrollDurationManger.this.scrollDuration;
            }
        };
        linearSmoothScroller.setTargetPosition(i2);
        startSmoothScroll(linearSmoothScroller);
    }
}
