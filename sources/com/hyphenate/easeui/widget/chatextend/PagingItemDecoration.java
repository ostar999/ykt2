package com.hyphenate.easeui.widget.chatextend;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes4.dex */
public class PagingItemDecoration extends RecyclerView.ItemDecoration {
    private static final int[] ATTRS = {R.attr.listDivider};
    private Drawable mDivider;
    PageDecorationLastJudge mPageDecorationLastJudge;

    public PagingItemDecoration(Context context, PageDecorationLastJudge pageDecorationLastJudge) {
        if (pageDecorationLastJudge == null) {
            throw new IllegalArgumentException("pageDecorationLastJudge must be no null");
        }
        this.mPageDecorationLastJudge = pageDecorationLastJudge;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(ATTRS);
        this.mDivider = typedArrayObtainStyledAttributes.getDrawable(0);
        typedArrayObtainStyledAttributes.recycle();
    }

    public void drawHorizontal(Canvas canvas, RecyclerView recyclerView) {
        int childCount = recyclerView.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = recyclerView.getChildAt(i2);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) childAt.getLayoutParams();
            int left = childAt.getLeft() - ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin;
            int right = childAt.getRight() + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin + this.mDivider.getIntrinsicWidth();
            int bottom = childAt.getBottom() + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
            this.mDivider.setBounds(left, bottom, right, this.mDivider.getIntrinsicHeight() + bottom);
            this.mDivider.draw(canvas);
        }
    }

    public void drawVertical(Canvas canvas, RecyclerView recyclerView) {
        int childCount = recyclerView.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = recyclerView.getChildAt(i2);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) childAt.getLayoutParams();
            int top2 = childAt.getTop() - ((ViewGroup.MarginLayoutParams) layoutParams).topMargin;
            int bottom = childAt.getBottom() + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
            int right = childAt.getRight() + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin;
            this.mDivider.setBounds(right, top2, this.mDivider.getIntrinsicWidth() + right, bottom);
            this.mDivider.draw(canvas);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        int childAdapterPosition = recyclerView.getChildAdapterPosition(view);
        if (this.mPageDecorationLastJudge.isPageLast(childAdapterPosition)) {
            rect.set(0, 0, 0, 0);
            return;
        }
        if (this.mPageDecorationLastJudge.isLastRow(childAdapterPosition)) {
            rect.set(0, 0, this.mDivider.getIntrinsicWidth(), 0);
        } else if (this.mPageDecorationLastJudge.isLastColumn(childAdapterPosition)) {
            rect.set(0, 0, 0, this.mDivider.getIntrinsicHeight());
        } else {
            rect.set(0, 0, this.mDivider.getIntrinsicWidth(), this.mDivider.getIntrinsicHeight());
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void onDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
        drawHorizontal(canvas, recyclerView);
        drawVertical(canvas, recyclerView);
    }

    public void setDrawable(@NonNull Drawable drawable) {
        if (drawable == null) {
            throw new IllegalArgumentException("Drawable cannot be null.");
        }
        this.mDivider = drawable;
    }
}
