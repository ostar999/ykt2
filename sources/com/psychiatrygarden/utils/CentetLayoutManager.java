package com.psychiatrygarden.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes6.dex */
public class CentetLayoutManager extends LinearLayoutManager {

    public static class CenterSmoothScroll extends LinearSmoothScroller {
        public CenterSmoothScroll(Context context) {
            super(context);
        }

        @Override // androidx.recyclerview.widget.LinearSmoothScroller
        public int calculateDtToFit(int viewStart, int viewEnd, int boxStart, int boxEnd, int snapPreference) {
            return (boxStart + ((boxEnd - boxStart) / 2)) - (viewStart + ((viewEnd - viewStart) / 2));
        }

        @Override // androidx.recyclerview.widget.LinearSmoothScroller
        public float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
            return 5.0f / displayMetrics.densityDpi;
        }
    }

    public CentetLayoutManager(Context context) {
        super(context);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        CenterSmoothScroll centerSmoothScroll = new CenterSmoothScroll(recyclerView.getContext());
        centerSmoothScroll.setTargetPosition(position);
        startSmoothScroll(centerSmoothScroll);
    }

    public CentetLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public CentetLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
