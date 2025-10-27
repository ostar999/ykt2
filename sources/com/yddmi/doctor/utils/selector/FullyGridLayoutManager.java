package com.yddmi.doctor.utils.selector;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes6.dex */
public class FullyGridLayoutManager extends GridLayoutManager {
    private final int[] mMeasuredDimension;
    final RecyclerView.State mState;

    public FullyGridLayoutManager(Context context, int i2) {
        super(context, i2);
        this.mMeasuredDimension = new int[2];
        this.mState = new RecyclerView.State();
    }

    private void measureScrapChild(RecyclerView.Recycler recycler, int i2, int i3, int i4, int[] iArr) {
        if (i2 < this.mState.getItemCount()) {
            try {
                View viewForPosition = recycler.getViewForPosition(0);
                if (viewForPosition != null) {
                    RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) viewForPosition.getLayoutParams();
                    viewForPosition.measure(ViewGroup.getChildMeasureSpec(i3, getPaddingLeft() + getPaddingRight(), ((ViewGroup.MarginLayoutParams) layoutParams).width), ViewGroup.getChildMeasureSpec(i4, getPaddingTop() + getPaddingBottom(), ((ViewGroup.MarginLayoutParams) layoutParams).height));
                    iArr[0] = viewForPosition.getMeasuredWidth() + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin;
                    iArr[1] = viewForPosition.getMeasuredHeight() + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin;
                    recycler.recycleView(viewForPosition);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onMeasure(@NonNull RecyclerView.Recycler recycler, @NonNull RecyclerView.State state, int i2, int i3) {
        int mode = View.MeasureSpec.getMode(i2);
        int mode2 = View.MeasureSpec.getMode(i3);
        int size = View.MeasureSpec.getSize(i2);
        int size2 = View.MeasureSpec.getSize(i3);
        int itemCount = getItemCount();
        int spanCount = getSpanCount();
        int i4 = 0;
        int i5 = 0;
        for (int i6 = 0; i6 < itemCount; i6++) {
            measureScrapChild(recycler, i6, View.MeasureSpec.makeMeasureSpec(i6, 0), View.MeasureSpec.makeMeasureSpec(i6, 0), this.mMeasuredDimension);
            if (getOrientation() == 0) {
                if (i6 % spanCount == 0) {
                    i4 += this.mMeasuredDimension[0];
                }
                if (i6 == 0) {
                    i5 = this.mMeasuredDimension[1];
                }
            } else {
                if (i6 % spanCount == 0) {
                    i5 += this.mMeasuredDimension[1];
                }
                if (i6 == 0) {
                    i4 = this.mMeasuredDimension[0];
                }
            }
        }
        if (mode != 1073741824) {
            size = i4;
        }
        if (mode2 != 1073741824) {
            size2 = i5;
        }
        setMeasuredDimension(size, size2);
    }

    public FullyGridLayoutManager(Context context, int i2, int i3, boolean z2) {
        super(context, i2, i3, z2);
        this.mMeasuredDimension = new int[2];
        this.mState = new RecyclerView.State();
    }
}
