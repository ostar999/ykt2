package com.aliyun.player.alivcplayerexpand.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/* loaded from: classes2.dex */
public class WordWrapView extends ViewGroup {
    private static final int PADDING_HOR = 10;
    private static final int PADDING_VERTICAL = 5;
    private static final int SIDE_MARGIN = 10;
    private static final int TEXT_MARGIN = 10;

    public WordWrapView(Context context) {
        super(context);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        int childCount = getChildCount();
        int i6 = i4 - i2;
        int i7 = 1;
        int i8 = 10;
        for (int i9 = 0; i9 < childCount; i9++) {
            View childAt = getChildAt(i9);
            int measuredWidth = childAt.getMeasuredWidth();
            int measuredHeight = childAt.getMeasuredHeight();
            int i10 = measuredWidth + 10;
            i8 += i10;
            if (i8 > i6) {
                i7++;
                i8 = i10;
            }
            int i11 = (measuredHeight + 10) * i7;
            if (i9 == 0) {
                childAt.layout((i8 - measuredWidth) - 10, i11 - measuredHeight, i8 - 10, i11);
            } else {
                childAt.layout(i8 - measuredWidth, i11 - measuredHeight, i8, i11);
            }
        }
    }

    @Override // android.view.View
    public void onMeasure(int i2, int i3) {
        int size = View.MeasureSpec.getSize(i2) - 20;
        int childCount = getChildCount();
        int i4 = 1;
        int i5 = 0;
        int i6 = 0;
        for (int i7 = 0; i7 < childCount; i7++) {
            View childAt = getChildAt(i7);
            childAt.setPadding(10, 5, 10, 5);
            childAt.measure(0, 0);
            int measuredWidth = childAt.getMeasuredWidth();
            int measuredHeight = childAt.getMeasuredHeight();
            i6 += measuredWidth + 10;
            if (i6 > size) {
                i4++;
                i6 = measuredWidth;
            }
            i5 = (measuredHeight + 10) * i4;
        }
        setMeasuredDimension(size, i5);
    }

    public WordWrapView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
    }

    public WordWrapView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
