package com.psychiatrygarden.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

/* loaded from: classes6.dex */
public class MyCoordinatorLayout extends CoordinatorLayout {
    private int HEADER_FIXED_HEIGTH;
    private int fixed_num;

    public MyCoordinatorLayout(Context context) {
        this(context, null);
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout, android.view.ViewGroup
    public boolean drawChild(Canvas canvas, View child, long drawingTime) {
        Log.i("MyCoordinatorLayout", "drawChild: " + child);
        if (indexOfChild(child) >= this.fixed_num) {
            canvas.clipRect(0, this.HEADER_FIXED_HEIGTH - child.getTop(), canvas.getWidth(), canvas.getHeight());
        }
        return super.drawChild(canvas, child, drawingTime);
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout, android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.HEADER_FIXED_HEIGTH = 0;
        for (int i2 = 0; i2 < this.fixed_num; i2++) {
            this.HEADER_FIXED_HEIGTH += getChildAt(i2).getMeasuredHeight();
        }
    }

    public MyCoordinatorLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyCoordinatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.fixed_num = 0;
        this.HEADER_FIXED_HEIGTH = 0;
    }
}
