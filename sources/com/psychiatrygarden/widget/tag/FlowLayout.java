package com.psychiatrygarden.widget.tag;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public class FlowLayout extends ViewGroup {
    private static final int CENTER = 0;
    private static final int LEFT = -1;
    private static final int RIGHT = 1;
    private static final String TAG = "FlowLayout";
    private List<View> lineViews;
    protected List<List<View>> mAllViews;
    private int mGravity;
    protected List<Integer> mLineHeight;
    protected List<Integer> mLineWidth;

    public FlowLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mAllViews = new ArrayList();
        this.mLineHeight = new ArrayList();
        this.mLineWidth = new ArrayList();
        this.lineViews = new ArrayList();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.TagFlowLayout);
        this.mGravity = typedArrayObtainStyledAttributes.getInt(1, -1);
        typedArrayObtainStyledAttributes.recycle();
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new ViewGroup.MarginLayoutParams(-2, -2);
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new ViewGroup.MarginLayoutParams(getContext(), attrs);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean changed, int l2, int t2, int r2, int b3) {
        int i2;
        int paddingLeft;
        this.mAllViews.clear();
        this.mLineHeight.clear();
        this.mLineWidth.clear();
        this.lineViews.clear();
        int width = getWidth();
        int childCount = getChildCount();
        int iMax = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < childCount; i4++) {
            View childAt = getChildAt(i4);
            if (childAt.getVisibility() != 8) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) childAt.getLayoutParams();
                int measuredWidth = childAt.getMeasuredWidth();
                int measuredHeight = childAt.getMeasuredHeight();
                if (measuredWidth + i3 + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin > (width - getPaddingLeft()) - getPaddingRight()) {
                    this.mLineHeight.add(Integer.valueOf(iMax));
                    this.mAllViews.add(this.lineViews);
                    this.mLineWidth.add(Integer.valueOf(i3));
                    iMax = marginLayoutParams.topMargin + measuredHeight + marginLayoutParams.bottomMargin;
                    this.lineViews = new ArrayList();
                    i3 = 0;
                }
                i3 += measuredWidth + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin;
                iMax = Math.max(iMax, measuredHeight + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin);
                this.lineViews.add(childAt);
            }
        }
        this.mLineHeight.add(Integer.valueOf(iMax));
        this.mLineWidth.add(Integer.valueOf(i3));
        this.mAllViews.add(this.lineViews);
        int paddingLeft2 = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int size = this.mAllViews.size();
        for (int i5 = 0; i5 < size; i5++) {
            this.lineViews = this.mAllViews.get(i5);
            int iIntValue = this.mLineHeight.get(i5).intValue();
            int iIntValue2 = this.mLineWidth.get(i5).intValue();
            int i6 = this.mGravity;
            if (i6 != -1) {
                if (i6 == 0) {
                    i2 = (width - iIntValue2) / 2;
                    paddingLeft = getPaddingLeft();
                } else if (i6 == 1) {
                    i2 = width - iIntValue2;
                    paddingLeft = getPaddingLeft();
                }
                paddingLeft2 = i2 + paddingLeft;
            } else {
                paddingLeft2 = getPaddingLeft();
            }
            for (int i7 = 0; i7 < this.lineViews.size(); i7++) {
                View view = this.lineViews.get(i7);
                if (view.getVisibility() != 8) {
                    ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                    int i8 = marginLayoutParams2.leftMargin + paddingLeft2;
                    int i9 = marginLayoutParams2.topMargin + paddingTop;
                    view.layout(i8, i9, view.getMeasuredWidth() + i8, view.getMeasuredHeight() + i9);
                    paddingLeft2 += view.getMeasuredWidth() + marginLayoutParams2.leftMargin + marginLayoutParams2.rightMargin;
                }
            }
            paddingTop += iIntValue;
        }
    }

    @Override // android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int i2;
        int size = View.MeasureSpec.getSize(widthMeasureSpec);
        int mode = View.MeasureSpec.getMode(widthMeasureSpec);
        int size2 = View.MeasureSpec.getSize(heightMeasureSpec);
        int mode2 = View.MeasureSpec.getMode(heightMeasureSpec);
        int childCount = getChildCount();
        int i3 = 0;
        int iMax = 0;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        while (i3 < childCount) {
            View childAt = getChildAt(i3);
            if (childAt.getVisibility() == 8) {
                if (i3 == childCount - 1) {
                    iMax = Math.max(i4, iMax);
                    i6 += i5;
                }
                i2 = size2;
            } else {
                measureChild(childAt, widthMeasureSpec, heightMeasureSpec);
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) childAt.getLayoutParams();
                i2 = size2;
                int measuredWidth = childAt.getMeasuredWidth() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin;
                int measuredHeight = childAt.getMeasuredHeight() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
                int i7 = i4 + measuredWidth;
                if (i7 > (size - getPaddingLeft()) - getPaddingRight()) {
                    iMax = Math.max(iMax, i4);
                    i6 += i5;
                } else {
                    measuredHeight = Math.max(i5, measuredHeight);
                    measuredWidth = i7;
                }
                if (i3 == childCount - 1) {
                    iMax = Math.max(measuredWidth, iMax);
                    i6 += measuredHeight;
                }
                i5 = measuredHeight;
                i4 = measuredWidth;
            }
            i3++;
            size2 = i2;
        }
        int i8 = size2;
        if (mode != 1073741824) {
            size = getPaddingRight() + iMax + getPaddingLeft();
        }
        setMeasuredDimension(size, mode2 == 1073741824 ? i8 : i6 + getPaddingTop() + getPaddingBottom());
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p2) {
        return new ViewGroup.MarginLayoutParams(p2);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context) {
        this(context, null);
    }
}
