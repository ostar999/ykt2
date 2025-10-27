package com.easefun.polyv.livecommon.ui.widget;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes3.dex */
public class PLVAutoLineLayoutManager extends RecyclerView.LayoutManager {
    public static int span = 4;

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(-2, -2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        detachAndScrapAttachedViews(recycler);
        int width = getWidth();
        int i2 = 0;
        int iMax = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < getItemCount(); i4++) {
            View viewForPosition = recycler.getViewForPosition(i4);
            addView(viewForPosition);
            measureChildWithMargins(viewForPosition, 0, 0);
            int decoratedMeasuredWidth = getDecoratedMeasuredWidth(viewForPosition);
            int decoratedMeasuredHeight = getDecoratedMeasuredHeight(viewForPosition);
            int i5 = i2 + decoratedMeasuredWidth;
            if (i5 <= width) {
                layoutDecorated(viewForPosition, i5 - decoratedMeasuredWidth, i3, i5, i3 + decoratedMeasuredHeight);
                iMax = Math.max(iMax, decoratedMeasuredHeight);
                i2 = i5;
            } else {
                if (iMax == 0) {
                    iMax = decoratedMeasuredHeight;
                }
                i3 += iMax;
                layoutDecorated(viewForPosition, 0, i3, decoratedMeasuredWidth, i3 + decoratedMeasuredHeight);
                i2 = decoratedMeasuredWidth;
                iMax = decoratedMeasuredHeight;
            }
        }
    }
}
