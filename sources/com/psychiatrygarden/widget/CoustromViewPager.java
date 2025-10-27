package com.psychiatrygarden.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.viewpager.widget.ViewPager;

/* loaded from: classes6.dex */
public class CoustromViewPager extends ViewPager {
    private boolean isSwipeEnabled;

    public CoustromViewPager(Context context) {
        super(context);
        this.isSwipeEnabled = true;
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return this.isSwipeEnabled && super.onInterceptTouchEvent(event);
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        return this.isSwipeEnabled && super.onTouchEvent(event);
    }

    public void setSwipeEnabled(boolean enabled) {
        this.isSwipeEnabled = enabled;
    }

    public CoustromViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.isSwipeEnabled = true;
    }
}
