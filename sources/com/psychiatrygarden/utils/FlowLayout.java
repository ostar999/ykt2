package com.psychiatrygarden.utils;

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
    private boolean isLimitLines;
    private List<View> lineViews;
    protected List<List<View>> mAllViews;
    private int mGravity;
    protected List<Integer> mLineHeight;
    protected List<Integer> mLineWidth;
    private int maxLines;

    public FlowLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.maxLines = 1;
        this.isLimitLines = false;
        this.mAllViews = new ArrayList();
        this.mLineHeight = new ArrayList();
        this.mLineWidth = new ArrayList();
        this.lineViews = new ArrayList();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.TagFlowLayoutStyle);
        this.mGravity = typedArrayObtainStyledAttributes.getInt(3, -1);
        this.isLimitLines = typedArrayObtainStyledAttributes.getBoolean(1, false);
        this.maxLines = typedArrayObtainStyledAttributes.getInteger(0, 100);
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
        int i3 = 1;
        int i4 = 0;
        int iMax = 0;
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt = getChildAt(i5);
            if (childAt.getVisibility() != 8) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) childAt.getLayoutParams();
                int measuredWidth = childAt.getMeasuredWidth();
                int measuredHeight = childAt.getMeasuredHeight();
                if (measuredWidth + i4 + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin > (width - getPaddingLeft()) - getPaddingRight()) {
                    if (i3 == this.maxLines + 1 && this.isLimitLines) {
                        break;
                    }
                    i3++;
                    this.mLineHeight.add(Integer.valueOf(iMax));
                    this.mAllViews.add(this.lineViews);
                    this.mLineWidth.add(Integer.valueOf(i4));
                    iMax = marginLayoutParams.bottomMargin + marginLayoutParams.topMargin + measuredHeight;
                    this.lineViews = new ArrayList();
                    i4 = 0;
                }
                i4 += measuredWidth + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin;
                iMax = Math.max(iMax, measuredHeight + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin);
                this.lineViews.add(childAt);
            }
        }
        this.mLineHeight.add(Integer.valueOf(iMax));
        this.mLineWidth.add(Integer.valueOf(i4));
        this.mAllViews.add(this.lineViews);
        int paddingLeft2 = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int size = this.mAllViews.size();
        for (int i6 = 0; i6 < size; i6++) {
            this.lineViews = this.mAllViews.get(i6);
            int iIntValue = this.mLineHeight.get(i6).intValue();
            int iIntValue2 = this.mLineWidth.get(i6).intValue();
            int i7 = this.mGravity;
            if (i7 != -1) {
                if (i7 == 0) {
                    i2 = (width - iIntValue2) / 2;
                    paddingLeft = getPaddingLeft();
                } else if (i7 == 1) {
                    i2 = width - iIntValue2;
                    paddingLeft = getPaddingLeft();
                }
                paddingLeft2 = i2 + paddingLeft;
            } else {
                paddingLeft2 = getPaddingLeft();
            }
            for (int i8 = 0; i8 < this.lineViews.size(); i8++) {
                View view = this.lineViews.get(i8);
                if (view.getVisibility() != 8) {
                    ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                    int i9 = marginLayoutParams2.leftMargin + paddingLeft2;
                    int i10 = marginLayoutParams2.topMargin + paddingTop;
                    view.layout(i9, i10, view.getMeasuredWidth() + i9, i10 + view.getMeasuredHeight());
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
        int i4 = 0;
        int iMax = 0;
        int i5 = 0;
        int i6 = 1;
        int i7 = 0;
        while (true) {
            if (i3 >= childCount) {
                i2 = size2;
                break;
            }
            View childAt = getChildAt(i3);
            if (childAt.getVisibility() == 8) {
                if (i3 == childCount - 1) {
                    iMax = Math.max(i7, iMax);
                    i5 += i4;
                }
                i2 = size2;
            } else {
                measureChild(childAt, widthMeasureSpec, heightMeasureSpec);
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) childAt.getLayoutParams();
                i2 = size2;
                int measuredWidth = childAt.getMeasuredWidth() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin;
                int measuredHeight = childAt.getMeasuredHeight() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
                int i8 = i7 + measuredWidth;
                if (i8 <= (size - getPaddingLeft()) - getPaddingRight()) {
                    measuredHeight = Math.max(i4, measuredHeight);
                } else if (i6 == this.maxLines && this.isLimitLines) {
                    iMax = Math.max(iMax, i7);
                    i5 += i4;
                    break;
                } else {
                    i6++;
                    iMax = Math.max(iMax, i7);
                    i5 += i4;
                    i8 = measuredWidth;
                }
                if (i3 == childCount - 1) {
                    i5 += measuredHeight;
                    i4 = measuredHeight;
                    iMax = Math.max(i8, iMax);
                } else {
                    i4 = measuredHeight;
                }
                i7 = i8;
            }
            i3++;
            size2 = i2;
        }
        if (mode != 1073741824) {
            size = getPaddingRight() + iMax + getPaddingLeft();
        }
        setMeasuredDimension(size, mode2 == 1073741824 ? i2 : i5 + getPaddingTop() + getPaddingBottom());
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
