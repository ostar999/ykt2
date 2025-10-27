package com.bumptech.glide.integration.recyclerview;

import android.widget.AbsListView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes2.dex */
public final class RecyclerToListViewScrollListener extends RecyclerView.OnScrollListener {
    public static final int UNKNOWN_SCROLL_STATE = Integer.MIN_VALUE;
    private final AbsListView.OnScrollListener scrollListener;
    private int lastFirstVisible = -1;
    private int lastVisibleCount = -1;
    private int lastItemCount = -1;

    public RecyclerToListViewScrollListener(@NonNull AbsListView.OnScrollListener onScrollListener) {
        this.scrollListener = onScrollListener;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
    public void onScrollStateChanged(RecyclerView recyclerView, int i2) {
        int i3;
        if (i2 != 0) {
            i3 = 1;
            if (i2 != 1) {
                i3 = 2;
                if (i2 != 2) {
                    i3 = Integer.MIN_VALUE;
                }
            }
        } else {
            i3 = 0;
        }
        this.scrollListener.onScrollStateChanged(null, i3);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
    public void onScrolled(RecyclerView recyclerView, int i2, int i3) {
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int iFindFirstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
        int iAbs = Math.abs(iFindFirstVisibleItemPosition - linearLayoutManager.findLastVisibleItemPosition());
        int itemCount = recyclerView.getAdapter().getItemCount();
        if (iFindFirstVisibleItemPosition == this.lastFirstVisible && iAbs == this.lastVisibleCount && itemCount == this.lastItemCount) {
            return;
        }
        this.scrollListener.onScroll(null, iFindFirstVisibleItemPosition, iAbs, itemCount);
        this.lastFirstVisible = iFindFirstVisibleItemPosition;
        this.lastVisibleCount = iAbs;
        this.lastItemCount = itemCount;
    }
}
