package com.pizidea.imagepicker.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/* loaded from: classes4.dex */
public class SquareLayout extends RelativeLayout {
    public SquareLayout(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
    }

    @Override // android.widget.RelativeLayout, android.view.View
    public void onMeasure(int i2, int i3) {
        setMeasuredDimension(View.getDefaultSize(0, i2), View.getDefaultSize(0, i3));
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824);
        super.onMeasure(iMakeMeasureSpec, iMakeMeasureSpec);
    }

    public SquareLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public SquareLayout(Context context) {
        super(context);
    }
}
