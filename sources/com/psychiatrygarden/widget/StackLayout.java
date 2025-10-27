package com.psychiatrygarden.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public class StackLayout extends ViewGroup {
    private Context context;
    private int itemMargin;
    private List<View> items;
    private int newHeight;
    private int stackLabelMaxLine;

    public StackLayout(Context context) {
        super(context);
        this.itemMargin = 0;
        this.stackLabelMaxLine = 0;
        this.newHeight = 0;
        this.context = context;
    }

    private int dp2px(float dpValue) {
        return (int) ((dpValue * Resources.getSystem().getDisplayMetrics().density) + 0.5f);
    }

    private void loadAttrs(Context context, AttributeSet attrs) {
        try {
            this.itemMargin = dp2px(4.0f);
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.StackLabel);
            this.itemMargin = typedArrayObtainStyledAttributes.getDimensionPixelOffset(0, this.itemMargin);
            this.stackLabelMaxLine = typedArrayObtainStyledAttributes.getInt(1, this.stackLabelMaxLine);
            typedArrayObtainStyledAttributes.recycle();
        } catch (Exception unused) {
        }
    }

    private void refreshViews() {
        int measuredWidth = getMeasuredWidth();
        this.items = new ArrayList();
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            if (getChildAt(i2).getVisibility() == 0) {
                this.items.add(getChildAt(i2));
            }
        }
        this.newHeight = 0;
        List<View> list = this.items;
        if (list == null || list.isEmpty()) {
            return;
        }
        int i3 = 0;
        int i4 = 0;
        for (int i5 = 0; i5 < this.items.size(); i5++) {
            View view = this.items.get(i5);
            view.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
            int measuredWidth2 = view.getMeasuredWidth();
            int measuredHeight = view.getMeasuredHeight();
            if (i3 + measuredWidth2 > measuredWidth) {
                i4 = i4 + measuredHeight + this.itemMargin;
                i3 = 0;
            }
            int i6 = i3 + measuredWidth2;
            int i7 = measuredHeight + i4;
            view.layout(i3, i4, measuredWidth2 > measuredWidth ? measuredWidth : i6, i7);
            i3 = this.itemMargin + i6;
            this.newHeight = i7;
        }
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean changed, int l2, int t2, int r2, int b3) {
    }

    @Override // android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        refreshViews();
        if (this.items.size() > 0 && this.stackLabelMaxLine > 0) {
            int measuredHeight = this.items.get(0).getMeasuredHeight();
            int i2 = this.stackLabelMaxLine;
            if ((measuredHeight * i2) + ((i2 - 1) * this.itemMargin) <= this.newHeight) {
                int measuredHeight2 = this.items.get(0).getMeasuredHeight();
                int i3 = this.stackLabelMaxLine;
                this.newHeight = (measuredHeight2 * i3) + ((i3 - 1) * this.itemMargin);
            }
        }
        setMeasuredDimension(getMeasuredWidth(), this.newHeight);
    }

    public StackLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.itemMargin = 0;
        this.stackLabelMaxLine = 0;
        this.newHeight = 0;
        this.context = context;
        loadAttrs(context, attrs);
    }

    public StackLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.itemMargin = 0;
        this.stackLabelMaxLine = 0;
        this.newHeight = 0;
        this.context = context;
        loadAttrs(context, attrs);
    }
}
