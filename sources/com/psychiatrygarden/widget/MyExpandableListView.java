package com.psychiatrygarden.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ExpandableListView;

/* loaded from: classes6.dex */
public class MyExpandableListView extends ExpandableListView {
    public MyExpandableListView(Context context) {
        super(context);
    }

    @Override // android.widget.ListView, android.widget.AbsListView, android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(536870911, Integer.MIN_VALUE));
    }

    public MyExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyExpandableListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
