package com.easefun.polyv.livecommon.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/* loaded from: classes3.dex */
public class PLVEmptyViewGroup extends ViewGroup {
    public PLVEmptyViewGroup(Context context) {
        super(context);
        setLayoutParams(new ViewGroup.LayoutParams(0, 0));
        setVisibility(8);
    }

    @Override // android.view.ViewGroup
    public boolean drawChild(Canvas canvas, View child, long drawingTime) {
        return true;
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean changed, int l2, int t2, int r2, int b3) {
    }

    @Override // android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(View.MeasureSpec.makeMeasureSpec(0, 1073741824), View.MeasureSpec.makeMeasureSpec(0, 1073741824));
    }

    public PLVEmptyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayoutParams(new ViewGroup.LayoutParams(0, 0));
        setVisibility(8);
    }

    public PLVEmptyViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayoutParams(new ViewGroup.LayoutParams(0, 0));
        setVisibility(8);
    }
}
