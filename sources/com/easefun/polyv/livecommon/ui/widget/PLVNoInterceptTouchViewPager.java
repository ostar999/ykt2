package com.easefun.polyv.livecommon.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* loaded from: classes3.dex */
public class PLVNoInterceptTouchViewPager extends PLVSimpleViewPager {
    private boolean dispatchTouchEvent;

    public PLVNoInterceptTouchViewPager(@NonNull Context context) {
        super(context);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean zDispatchTouchEvent = super.dispatchTouchEvent(ev);
        this.dispatchTouchEvent = zDispatchTouchEvent;
        return zDispatchTouchEvent;
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.PLVSimpleViewPager, androidx.viewpager.widget.ViewPager, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return this.dispatchTouchEvent && super.onInterceptTouchEvent(ev);
    }

    public boolean onSuperTouchEvent(MotionEvent e2) {
        return super.onTouchEvent(e2);
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.PLVSimpleViewPager, androidx.viewpager.widget.ViewPager, android.view.View
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

    public PLVNoInterceptTouchViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
}
