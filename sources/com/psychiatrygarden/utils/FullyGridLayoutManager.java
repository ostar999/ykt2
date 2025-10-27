package com.psychiatrygarden.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes6.dex */
public class FullyGridLayoutManager extends GridLayoutManager {
    private int[] mMeasuredDimension;
    final RecyclerView.State mState;

    public FullyGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
        this.mMeasuredDimension = new int[2];
        this.mState = new RecyclerView.State();
    }

    private void measureScrapChild(RecyclerView.Recycler recycler, int position, int widthSpec, int heightSpec, int[] measuredDimension) {
        if (position < this.mState.getItemCount()) {
            try {
                View viewForPosition = recycler.getViewForPosition(0);
                if (viewForPosition != null) {
                    RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) viewForPosition.getLayoutParams();
                    viewForPosition.measure(ViewGroup.getChildMeasureSpec(widthSpec, getPaddingLeft() + getPaddingRight(), ((ViewGroup.MarginLayoutParams) layoutParams).width), ViewGroup.getChildMeasureSpec(heightSpec, getPaddingTop() + getPaddingBottom(), ((ViewGroup.MarginLayoutParams) layoutParams).height));
                    measuredDimension[0] = viewForPosition.getMeasuredWidth() + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin;
                    measuredDimension[1] = viewForPosition.getMeasuredHeight() + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin;
                    recycler.recycleView(viewForPosition);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
        int mode = View.MeasureSpec.getMode(widthSpec);
        int mode2 = View.MeasureSpec.getMode(heightSpec);
        int size = View.MeasureSpec.getSize(widthSpec);
        int size2 = View.MeasureSpec.getSize(heightSpec);
        int itemCount = getItemCount();
        int spanCount = getSpanCount();
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < itemCount; i4++) {
            measureScrapChild(recycler, i4, View.MeasureSpec.makeMeasureSpec(i4, 0), View.MeasureSpec.makeMeasureSpec(i4, 0), this.mMeasuredDimension);
            if (getOrientation() == 0) {
                if (i4 % spanCount == 0) {
                    i2 += this.mMeasuredDimension[0];
                }
                if (i4 == 0) {
                    i3 = this.mMeasuredDimension[1];
                }
            } else {
                if (i4 % spanCount == 0) {
                    i3 += this.mMeasuredDimension[1];
                }
                if (i4 == 0) {
                    i2 = this.mMeasuredDimension[0];
                }
            }
        }
        if (mode != 1073741824) {
            size = i2;
        }
        if (mode2 != 1073741824) {
            size2 = i3;
        }
        setMeasuredDimension(size, size2);
    }

    public FullyGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
        this.mMeasuredDimension = new int[2];
        this.mState = new RecyclerView.State();
    }
}
