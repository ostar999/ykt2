package com.aliyun.svideo.common.baseAdapter.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes2.dex */
public class GridItemDecoration extends RecyclerView.ItemDecoration {
    private Drawable dividerDrawable;
    private int orientation;

    public GridItemDecoration(Drawable drawable) {
        this.orientation = 1;
        this.dividerDrawable = drawable;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        if (this.dividerDrawable != null && recyclerView.getChildLayoutPosition(view) >= 1) {
            int i2 = this.orientation;
            if (i2 == 1) {
                rect.top = this.dividerDrawable.getIntrinsicHeight();
            } else if (i2 == 0) {
                rect.left = this.dividerDrawable.getIntrinsicWidth();
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void onDrawOver(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
        if (this.dividerDrawable == null) {
            return;
        }
        int childCount = recyclerView.getChildCount();
        int width = recyclerView.getWidth();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = recyclerView.getChildAt(i2);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) childAt.getLayoutParams();
            int paddingLeft = recyclerView.getPaddingLeft() + childAt.getPaddingLeft();
            int top2 = childAt.getTop() - ((ViewGroup.MarginLayoutParams) layoutParams).topMargin;
            int intrinsicHeight = top2 - this.dividerDrawable.getIntrinsicHeight();
            int top3 = childAt.getTop() + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin;
            int bottom = childAt.getBottom() + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
            int left = childAt.getLeft() - ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin;
            this.dividerDrawable.setBounds(left - this.dividerDrawable.getIntrinsicWidth(), top3, left, bottom);
            this.dividerDrawable.draw(canvas);
            this.dividerDrawable.setBounds(paddingLeft, intrinsicHeight, width, top2);
            this.dividerDrawable.draw(canvas);
        }
    }

    public GridItemDecoration(Context context, int i2) {
        this.orientation = 1;
        this.dividerDrawable = context.getResources().getDrawable(i2);
    }

    public GridItemDecoration(Context context, int i2, int i3) {
        this.orientation = 1;
        this.dividerDrawable = context.getResources().getDrawable(i2);
        this.orientation = i3;
    }
}
