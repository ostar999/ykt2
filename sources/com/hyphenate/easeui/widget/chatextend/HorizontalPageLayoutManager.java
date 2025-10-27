package com.hyphenate.easeui.widget.chatextend;

import android.graphics.Rect;
import android.util.SparseArray;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes4.dex */
public class HorizontalPageLayoutManager extends RecyclerView.LayoutManager implements PageDecorationLastJudge {
    private int columns;
    private int heightMode;
    private boolean isUseSetHeight;
    private int itemHeightUsed;
    private int itemSetHeight;
    private int itemWidthUsed;
    private int onePageSize;
    private int rows;
    private int totalHeight = 0;
    private int totalWidth = 0;
    private int offsetY = 0;
    private int offsetX = 0;
    private int pageSize = 0;
    private int itemWidth = 0;
    private int itemHeight = 0;
    private SparseArray<Rect> allItemFrames = new SparseArray<>();

    public HorizontalPageLayoutManager(int i2, int i3) {
        this.rows = 0;
        this.columns = 0;
        this.onePageSize = 0;
        this.rows = i2;
        this.columns = i3;
        this.onePageSize = i2 * i3;
    }

    private void computePageSize(RecyclerView.State state) {
        this.pageSize = (state.getItemCount() / this.onePageSize) + (state.getItemCount() % this.onePageSize == 0 ? 0 : 1);
    }

    private int getUsableHeight() {
        return (getHeight() - getPaddingTop()) - getPaddingBottom();
    }

    private int getUsableWidth() {
        return (getWidth() - getPaddingLeft()) - getPaddingRight();
    }

    private int getWrapItemHeight() {
        if (this.heightMode != Integer.MIN_VALUE) {
            return this.itemHeight;
        }
        if (this.isUseSetHeight) {
            int i2 = this.itemSetHeight;
            int i3 = this.rows;
            int i4 = i2 * i3;
            int i5 = this.totalHeight;
            if (i4 <= i5) {
                this.itemHeight = i2;
            } else {
                this.itemHeight = i5 / i3;
            }
        } else {
            this.itemHeight = this.totalHeight / this.rows;
        }
        return this.itemHeight;
    }

    private void recycleAndFillItems(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (state.isPreLayout()) {
            return;
        }
        Rect rect = new Rect(getPaddingLeft() + this.offsetX, getPaddingTop(), ((getWidth() - getPaddingLeft()) - getPaddingRight()) + this.offsetX, (getHeight() - getPaddingTop()) - getPaddingBottom());
        Rect rect2 = new Rect();
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            View childAt = getChildAt(i2);
            rect2.left = getDecoratedLeft(childAt);
            rect2.top = getDecoratedTop(childAt);
            rect2.right = getDecoratedRight(childAt);
            rect2.bottom = getDecoratedBottom(childAt);
            if (!Rect.intersects(rect, rect2)) {
                removeAndRecycleView(childAt, recycler);
            }
        }
        for (int i3 = 0; i3 < getItemCount(); i3++) {
            if (Rect.intersects(rect, this.allItemFrames.get(i3))) {
                View viewForPosition = recycler.getViewForPosition(i3);
                addView(viewForPosition);
                measureChildWithMargins(viewForPosition, this.itemWidthUsed, this.itemHeightUsed);
                Rect rect3 = this.allItemFrames.get(i3);
                int i4 = rect3.left;
                int i5 = this.offsetX;
                layoutDecorated(viewForPosition, i4 - i5, rect3.top, rect3.right - i5, rect3.bottom);
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean canScrollHorizontally() {
        return true;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int computeHorizontalScrollExtent(RecyclerView.State state) {
        return getWidth();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int computeHorizontalScrollOffset(RecyclerView.State state) {
        return this.offsetX;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int computeHorizontalScrollRange(RecyclerView.State state) {
        computePageSize(state);
        return this.pageSize * getWidth();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(-1, -2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean isAutoMeasureEnabled() {
        return false;
    }

    @Override // com.hyphenate.easeui.widget.chatextend.PageDecorationLastJudge
    public boolean isLastColumn(int i2) {
        return i2 >= 0 && i2 < getItemCount() && (i2 + 1) % this.columns == 0;
    }

    @Override // com.hyphenate.easeui.widget.chatextend.PageDecorationLastJudge
    public boolean isLastRow(int i2) {
        if (i2 < 0 || i2 >= getItemCount()) {
            return false;
        }
        int i3 = this.onePageSize;
        int i4 = (i2 % i3) + 1;
        return i4 > (this.rows - 1) * this.columns && i4 <= i3;
    }

    @Override // com.hyphenate.easeui.widget.chatextend.PageDecorationLastJudge
    public boolean isPageLast(int i2) {
        return (i2 + 1) % this.onePageSize == 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onDetachedFromWindow(RecyclerView recyclerView, RecyclerView.Recycler recycler) {
        super.onDetachedFromWindow(recyclerView, recycler);
        this.offsetX = 0;
        this.offsetY = 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getItemCount() == 0) {
            removeAndRecycleAllViews(recycler);
            return;
        }
        if (state.isPreLayout()) {
            return;
        }
        this.itemWidth = getUsableWidth() / this.columns;
        int usableHeight = getUsableHeight() / this.rows;
        this.itemHeight = usableHeight;
        if (usableHeight == 0) {
            getWrapItemHeight();
        }
        this.itemWidthUsed = (this.columns - 1) * this.itemWidth;
        this.itemHeightUsed = (this.rows - 1) * this.itemHeight;
        computePageSize(state);
        this.totalWidth = (this.pageSize - 1) * getWidth();
        detachAndScrapAttachedViews(recycler);
        int itemCount = getItemCount();
        int i2 = 0;
        while (i2 < this.pageSize) {
            int i3 = 0;
            while (i3 < this.rows) {
                int i4 = 0;
                while (true) {
                    int i5 = this.columns;
                    if (i4 >= i5) {
                        break;
                    }
                    int i6 = (this.onePageSize * i2) + (i5 * i3) + i4;
                    if (i6 == itemCount) {
                        i3 = this.rows;
                        i2 = this.pageSize;
                        break;
                    }
                    View viewForPosition = recycler.getViewForPosition(i6);
                    addView(viewForPosition);
                    measureChildWithMargins(viewForPosition, this.itemWidthUsed, this.itemHeightUsed);
                    int decoratedMeasuredWidth = getDecoratedMeasuredWidth(viewForPosition);
                    int decoratedMeasuredHeight = getDecoratedMeasuredHeight(viewForPosition);
                    if (this.isUseSetHeight) {
                        decoratedMeasuredHeight = getWrapItemHeight();
                        this.itemHeight = decoratedMeasuredHeight;
                    } else if (i6 == 0 && decoratedMeasuredHeight != 0) {
                        this.itemHeight = decoratedMeasuredHeight;
                    }
                    Rect rect = this.allItemFrames.get(i6);
                    if (rect == null) {
                        rect = new Rect();
                    }
                    int usableWidth = (getUsableWidth() * i2) + (this.itemWidth * i4);
                    int i7 = this.itemHeight * i3;
                    rect.set(usableWidth, i7, decoratedMeasuredWidth + usableWidth, decoratedMeasuredHeight + i7);
                    this.allItemFrames.put(i6, rect);
                    i4++;
                }
                i3++;
            }
            removeAndRecycleAllViews(recycler);
            i2++;
        }
        recycleAndFillItems(recycler, state);
        requestLayout();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onMeasure(@NonNull RecyclerView.Recycler recycler, @NonNull RecyclerView.State state, int i2, int i3) {
        int mode = View.MeasureSpec.getMode(i3);
        this.heightMode = mode;
        if (mode == Integer.MIN_VALUE) {
            if (this.isUseSetHeight) {
                i3 = View.MeasureSpec.makeMeasureSpec(this.itemSetHeight * this.rows, 1073741824);
            }
            this.totalHeight = View.MeasureSpec.getSize(i3);
        }
        super.onMeasure(recycler, state, i2, i3);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int scrollHorizontallyBy(int i2, RecyclerView.Recycler recycler, RecyclerView.State state) {
        detachAndScrapAttachedViews(recycler);
        int i3 = this.offsetX;
        int i4 = i3 + i2;
        int i5 = this.totalWidth;
        if (i4 > i5) {
            i2 = i5 - i3;
        } else if (i4 < 0) {
            i2 = 0 - i3;
        }
        this.offsetX = i3 + i2;
        offsetChildrenHorizontal(-i2);
        recycleAndFillItems(recycler, state);
        return i2;
    }

    public void setItemHeight(int i2) {
        this.itemSetHeight = i2;
        this.isUseSetHeight = i2 > 0;
    }
}
