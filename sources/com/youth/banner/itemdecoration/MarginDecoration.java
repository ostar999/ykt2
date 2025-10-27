package com.youth.banner.itemdecoration;

import android.graphics.Rect;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Px;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes8.dex */
public class MarginDecoration extends RecyclerView.ItemDecoration {
    private int mMarginPx;

    public MarginDecoration(@Px int i2) {
        this.mMarginPx = i2;
    }

    private LinearLayoutManager requireLinearLayoutManager(@NonNull RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            return (LinearLayoutManager) layoutManager;
        }
        throw new IllegalStateException("The layoutManager must be LinearLayoutManager");
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(@NonNull Rect rect, @NonNull View view, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.State state) {
        if (requireLinearLayoutManager(recyclerView).getOrientation() == 1) {
            int i2 = this.mMarginPx;
            rect.top = i2;
            rect.bottom = i2;
        } else {
            int i3 = this.mMarginPx;
            rect.left = i3;
            rect.right = i3;
        }
    }
}
