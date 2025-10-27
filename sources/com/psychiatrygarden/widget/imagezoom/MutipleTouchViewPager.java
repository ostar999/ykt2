package com.psychiatrygarden.widget.imagezoom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.viewpager.widget.ViewPager;

/* loaded from: classes6.dex */
public class MutipleTouchViewPager extends ViewPager {
    private boolean mIsDisallowIntercept;

    public MutipleTouchViewPager(Context context) {
        super(context);
        this.mIsDisallowIntercept = false;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getPointerCount() <= 1 || !this.mIsDisallowIntercept) {
            return super.dispatchTouchEvent(ev);
        }
        requestDisallowInterceptTouchEvent(false);
        boolean zDispatchTouchEvent = super.dispatchTouchEvent(ev);
        requestDisallowInterceptTouchEvent(true);
        return zDispatchTouchEvent;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        this.mIsDisallowIntercept = disallowIntercept;
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
    }

    public MutipleTouchViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mIsDisallowIntercept = false;
    }
}
