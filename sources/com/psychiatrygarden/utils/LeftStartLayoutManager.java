package com.psychiatrygarden.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes6.dex */
public class LeftStartLayoutManager extends LinearLayoutManager {

    public static class LeftStartSmoothScroller extends LinearSmoothScroller {
        public LeftStartSmoothScroller(Context context) {
            super(context);
        }

        @Override // androidx.recyclerview.widget.LinearSmoothScroller
        public int calculateDtToFit(int viewStart, int viewEnd, int boxStart, int boxEnd, int snapPreference) {
            return boxStart - viewStart;
        }

        @Override // androidx.recyclerview.widget.LinearSmoothScroller
        public float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
            return 100.0f / displayMetrics.densityDpi;
        }
    }

    public LeftStartLayoutManager(Context context) {
        super(context);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        LeftStartSmoothScroller leftStartSmoothScroller = new LeftStartSmoothScroller(recyclerView.getContext());
        leftStartSmoothScroller.setTargetPosition(position);
        startSmoothScroll(leftStartSmoothScroller);
    }

    public LeftStartLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public LeftStartLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
