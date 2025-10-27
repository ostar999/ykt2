package com.psychiatrygarden.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;

/* loaded from: classes6.dex */
public class GrapeGridView extends GridView {
    public GrapeGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == 2) {
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override // android.widget.GridView, android.widget.AbsListView, android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(536870911, Integer.MIN_VALUE));
    }

    public GrapeGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GrapeGridView(Context context) {
        super(context);
    }
}
