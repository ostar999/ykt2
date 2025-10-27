package com.psychiatrygarden.widget.imagezoom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.viewpager.widget.ViewPager;

/* loaded from: classes6.dex */
public class HackyViewPager extends ViewPager {
    private boolean isLocked;

    public HackyViewPager(Context context) {
        super(context);
        this.isLocked = false;
    }

    public boolean isLocked() {
        return this.isLocked;
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!this.isLocked) {
            try {
                return super.onInterceptTouchEvent(ev);
            } catch (IllegalArgumentException e2) {
                e2.printStackTrace();
            }
        }
        return false;
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        return !this.isLocked && super.onTouchEvent(event);
    }

    public void setLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }

    public void toggleLock() {
        this.isLocked = !this.isLocked;
    }

    public HackyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.isLocked = false;
    }
}
