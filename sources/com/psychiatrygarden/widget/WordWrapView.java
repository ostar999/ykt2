package com.psychiatrygarden.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/* loaded from: classes6.dex */
public class WordWrapView extends ViewGroup {
    private static final int PADDING_HOR = 10;
    private static final int PADDING_VERTICAL = 5;
    private static final int SIDE_MARGIN = 10;
    private static final int TEXT_MARGIN = 10;

    public WordWrapView(Context context) {
        super(context);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean changed, int l2, int t2, int r2, int b3) {
        int childCount = getChildCount();
        int i2 = r2 - l2;
        int i3 = 1;
        int i4 = 10;
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt = getChildAt(i5);
            int measuredWidth = childAt.getMeasuredWidth();
            int measuredHeight = childAt.getMeasuredHeight();
            int i6 = measuredWidth + 10;
            i4 += i6;
            if (i4 > i2) {
                i3++;
                i4 = i6;
            }
            int i7 = (measuredHeight + 10) * i3;
            if (i5 == 0) {
                childAt.layout((i4 - measuredWidth) - 10, i7 - measuredHeight, i4 - 10, i7);
            } else {
                childAt.layout(i4 - measuredWidth, i7 - measuredHeight, i4, i7);
            }
        }
    }

    @Override // android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int size = View.MeasureSpec.getSize(widthMeasureSpec) - 20;
        int childCount = getChildCount();
        int i2 = 1;
        int i3 = 0;
        int i4 = 0;
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt = getChildAt(i5);
            childAt.setPadding(10, 5, 10, 5);
            childAt.measure(0, 0);
            int measuredWidth = childAt.getMeasuredWidth();
            int measuredHeight = childAt.getMeasuredHeight();
            i4 += measuredWidth + 10;
            if (i4 > size) {
                i2++;
                i4 = measuredWidth;
            }
            i3 = (measuredHeight + 10) * i2;
        }
        setMeasuredDimension(size, i3);
    }

    public WordWrapView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public WordWrapView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
