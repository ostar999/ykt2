package com.psychiatrygarden.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/* loaded from: classes6.dex */
public class NoScrollViewPager extends ViewPager {
    private boolean isSwipeEnabled;

    public NoScrollViewPager(@NonNull Context context) {
        super(context);
        this.isSwipeEnabled = true;
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return this.isSwipeEnabled && super.onInterceptTouchEvent(ev);
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.View
    public boolean onTouchEvent(MotionEvent ev) {
        return this.isSwipeEnabled && super.onTouchEvent(ev);
    }

    public void setSwipeEnabled(boolean enabled) {
        this.isSwipeEnabled = enabled;
    }

    public NoScrollViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.isSwipeEnabled = true;
    }
}
