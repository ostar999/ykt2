package com.psychiatrygarden.activity.purchase.zoom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.viewpager.widget.ViewPager;

/* loaded from: classes5.dex */
public class ViewPagerFixed extends ViewPager {
    public ViewPagerFixed(Context context) {
        super(context);
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.View
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            return super.onTouchEvent(ev);
        } catch (IllegalArgumentException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public ViewPagerFixed(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
