package com.psychiatrygarden.widget;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes6.dex */
public class RcycleViewPageViewOnListener extends RecyclerView.OnScrollListener {
    public int oldPosition = -1;
    public OnPageChangeListener onPageChangeListener;
    public SnapHelper snapHelper;

    public interface OnPageChangeListener {
        void onPageSelected(int position);

        void onScorllStateChange(RecyclerView recyclerView, int newState);

        void onScrolled(RecyclerView recyclerView, int dx, int dy);
    }

    public RcycleViewPageViewOnListener() {
    }

    @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
    public void onScrollStateChanged(@NonNull @NotNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        View viewFindSnapView = this.snapHelper.findSnapView(layoutManager);
        int position = viewFindSnapView != null ? layoutManager.getPosition(viewFindSnapView) : 0;
        OnPageChangeListener onPageChangeListener = this.onPageChangeListener;
        if (onPageChangeListener != null) {
            onPageChangeListener.onScorllStateChange(recyclerView, newState);
            if (newState != 0 || this.oldPosition == position) {
                return;
            }
            this.oldPosition = position;
            this.onPageChangeListener.onPageSelected(position);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
    public void onScrolled(@NonNull @NotNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        OnPageChangeListener onPageChangeListener = this.onPageChangeListener;
        if (onPageChangeListener != null) {
            onPageChangeListener.onScrolled(recyclerView, dx, dy);
        }
    }

    public RcycleViewPageViewOnListener(SnapHelper snapHelper, OnPageChangeListener onPageChangeListener) {
        this.snapHelper = snapHelper;
        this.onPageChangeListener = onPageChangeListener;
    }
}
