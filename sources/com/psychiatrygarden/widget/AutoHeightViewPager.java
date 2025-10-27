package com.psychiatrygarden.widget;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import androidx.viewpager.widget.ViewPager;

/* loaded from: classes6.dex */
public class AutoHeightViewPager extends ViewPager {
    public AutoHeightViewPager(Context context) {
        super(context);
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) throws Resources.NotFoundException {
        int i2 = 0;
        for (int i3 = 0; i3 < getChildCount(); i3++) {
            View childAt = getChildAt(i3);
            childAt.measure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(0, 0));
            int measuredHeight = childAt.getMeasuredHeight();
            if (measuredHeight > i2) {
                i2 = measuredHeight;
            }
        }
        super.onMeasure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(i2, 1073741824));
    }

    public AutoHeightViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
