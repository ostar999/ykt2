package com.easefun.polyv.livecommon.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes3.dex */
public class PLVNoInterceptTouchRecyclerView extends RecyclerView {
    public PLVNoInterceptTouchRecyclerView(Context context) {
        super(context);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent ev) {
        super.dispatchTouchEvent(ev);
        return false;
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent e2) {
        return false;
    }

    public boolean onSuperTouchEvent(MotionEvent e2) {
        return super.onTouchEvent(e2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.View
    public boolean onTouchEvent(MotionEvent e2) {
        return false;
    }

    public PLVNoInterceptTouchRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
}
