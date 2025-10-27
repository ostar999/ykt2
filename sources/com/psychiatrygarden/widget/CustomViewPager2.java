package com.psychiatrygarden.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.viewpager.widget.ViewPager;

/* loaded from: classes6.dex */
public class CustomViewPager2 extends ViewPager {
    private float mInitialTouchX;
    private float mInitialTouchY;
    private LeftSwipListener mListener;
    private float mStartX;

    public interface LeftSwipListener {
        void onLeftSwipe();
    }

    public CustomViewPager2(Context context) {
        super(context);
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if (action == 0) {
            this.mInitialTouchX = ev.getX();
            this.mInitialTouchY = ev.getY();
            this.mStartX = ev.getRawX();
        } else if (action == 2) {
            if (Math.abs(ev.getY() - this.mInitialTouchY) > Math.abs(ev.getX() - this.mInitialTouchX)) {
                return false;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.View
    public boolean onTouchEvent(MotionEvent ev) {
        LeftSwipListener leftSwipListener;
        if (ev.getAction() == 1) {
            if (this.mStartX - ev.getRawX() > 200.0f && (leftSwipListener = this.mListener) != null) {
                leftSwipListener.onLeftSwipe();
            }
        }
        return super.onTouchEvent(ev);
    }

    public void setListener(LeftSwipListener listener) {
        this.mListener = listener;
    }

    public CustomViewPager2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
