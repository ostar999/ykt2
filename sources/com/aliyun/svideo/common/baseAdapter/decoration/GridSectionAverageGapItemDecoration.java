package com.aliyun.svideo.common.baseAdapter.decoration;

import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.aliyun.svideo.common.baseAdapter.BaseSectionQuickAdapter;
import com.aliyun.svideo.common.baseAdapter.BaseViewHolder;
import com.aliyun.svideo.common.baseAdapter.entity.SectionEntity;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class GridSectionAverageGapItemDecoration extends RecyclerView.ItemDecoration {
    private int eachItemHPaddingPx;
    private float gapHorizontalDp;
    private float gapVerticalDp;
    private BaseSectionQuickAdapter mAdapter;
    private float sectionEdgeHPaddingDp;
    private int sectionEdgeHPaddingPx;
    private float sectionEdgeVPaddingDp;
    private int sectionEdgeVPaddingPx;
    private int gapHSizePx = -1;
    private int gapVSizePx = -1;
    private List<Section> mSectionList = new ArrayList();
    private RecyclerView.AdapterDataObserver mDataObserver = new RecyclerView.AdapterDataObserver() { // from class: com.aliyun.svideo.common.baseAdapter.decoration.GridSectionAverageGapItemDecoration.1
        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onChanged() {
            GridSectionAverageGapItemDecoration.this.markSections();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onItemRangeChanged(int i2, int i3) {
            GridSectionAverageGapItemDecoration.this.markSections();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onItemRangeInserted(int i2, int i3) {
            GridSectionAverageGapItemDecoration.this.markSections();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onItemRangeMoved(int i2, int i3, int i4) {
            GridSectionAverageGapItemDecoration.this.markSections();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onItemRangeRemoved(int i2, int i3) {
            GridSectionAverageGapItemDecoration.this.markSections();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onItemRangeChanged(int i2, int i3, @Nullable Object obj) {
            GridSectionAverageGapItemDecoration.this.markSections();
        }
    };

    public class Section {
        public int endPos;
        public int startPos;

        private Section() {
            this.startPos = 0;
            this.endPos = 0;
        }

        public boolean contains(int i2) {
            return i2 >= this.startPos && i2 <= this.endPos;
        }

        public int getCount() {
            return (this.endPos - this.startPos) + 1;
        }

        public String toString() {
            return "Section{startPos=" + this.startPos + ", endPos=" + this.endPos + '}';
        }
    }

    public GridSectionAverageGapItemDecoration(float f2, float f3, float f4, float f5) {
        this.gapHorizontalDp = f2;
        this.gapVerticalDp = f3;
        this.sectionEdgeHPaddingDp = f4;
        this.sectionEdgeVPaddingDp = f5;
    }

    private Section findSectionLastItemPos(int i2) {
        for (Section section : this.mSectionList) {
            if (section.contains(i2)) {
                return section;
            }
        }
        return null;
    }

    private boolean isLastRow(int i2, int i3, int i4) {
        int i5 = i4 % i3;
        if (i5 != 0) {
            i3 = i5;
        }
        return i2 > i4 - i3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public void markSections() {
        BaseSectionQuickAdapter baseSectionQuickAdapter = this.mAdapter;
        if (baseSectionQuickAdapter != null) {
            this.mSectionList.clear();
            Section section = new Section();
            int itemCount = baseSectionQuickAdapter.getItemCount();
            for (int i2 = 0; i2 < itemCount; i2++) {
                SectionEntity sectionEntity = (SectionEntity) baseSectionQuickAdapter.getItem(i2);
                if (sectionEntity == null || !sectionEntity.isHeader) {
                    section.endPos = i2;
                } else {
                    if (i2 != 0) {
                        section.endPos = i2 - 1;
                        this.mSectionList.add(section);
                    }
                    section = new Section();
                    section.startPos = i2 + 1;
                }
            }
            if (this.mSectionList.contains(section)) {
                return;
            }
            this.mSectionList.add(section);
        }
    }

    private void setUpWithAdapter(BaseSectionQuickAdapter<SectionEntity, BaseViewHolder> baseSectionQuickAdapter) {
        BaseSectionQuickAdapter baseSectionQuickAdapter2 = this.mAdapter;
        if (baseSectionQuickAdapter2 != null) {
            baseSectionQuickAdapter2.unregisterAdapterDataObserver(this.mDataObserver);
        }
        this.mAdapter = baseSectionQuickAdapter;
        baseSectionQuickAdapter.registerAdapterDataObserver(this.mDataObserver);
        markSections();
    }

    private void transformGapDefinition(RecyclerView recyclerView, int i2) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        recyclerView.getDisplay().getMetrics(displayMetrics);
        this.gapHSizePx = (int) TypedValue.applyDimension(1, this.gapHorizontalDp, displayMetrics);
        this.gapVSizePx = (int) TypedValue.applyDimension(1, this.gapVerticalDp, displayMetrics);
        this.sectionEdgeHPaddingPx = (int) TypedValue.applyDimension(1, this.sectionEdgeHPaddingDp, displayMetrics);
        this.sectionEdgeVPaddingPx = (int) TypedValue.applyDimension(1, this.sectionEdgeVPaddingDp, displayMetrics);
        this.eachItemHPaddingPx = ((this.sectionEdgeHPaddingPx * 2) + (this.gapHSizePx * (i2 - 1))) / i2;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        if (!(recyclerView.getLayoutManager() instanceof GridLayoutManager) || !(recyclerView.getAdapter() instanceof BaseSectionQuickAdapter)) {
            super.getItemOffsets(rect, view, recyclerView, state);
            return;
        }
        GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
        BaseSectionQuickAdapter<SectionEntity, BaseViewHolder> baseSectionQuickAdapter = (BaseSectionQuickAdapter) recyclerView.getAdapter();
        if (this.mAdapter != baseSectionQuickAdapter) {
            setUpWithAdapter(baseSectionQuickAdapter);
        }
        int spanCount = gridLayoutManager.getSpanCount();
        int childAdapterPosition = recyclerView.getChildAdapterPosition(view);
        SectionEntity item = baseSectionQuickAdapter.getItem(childAdapterPosition);
        if (item != null && item.isHeader) {
            rect.set(0, 0, 0, 0);
            return;
        }
        Section sectionFindSectionLastItemPos = findSectionLastItemPos(childAdapterPosition);
        if (this.gapHSizePx < 0 || this.gapVSizePx < 0) {
            transformGapDefinition(recyclerView, spanCount);
        }
        rect.top = this.gapVSizePx;
        rect.bottom = 0;
        int i2 = (childAdapterPosition + 1) - sectionFindSectionLastItemPos.startPos;
        int i3 = i2 % spanCount;
        if (i3 == 1) {
            int i4 = this.sectionEdgeHPaddingPx;
            rect.left = i4;
            rect.right = this.eachItemHPaddingPx - i4;
        } else if (i3 == 0) {
            int i5 = this.eachItemHPaddingPx;
            int i6 = this.sectionEdgeHPaddingPx;
            rect.left = i5 - i6;
            rect.right = i6;
        } else {
            int i7 = this.gapHSizePx;
            int i8 = this.eachItemHPaddingPx;
            int i9 = i7 - (i8 - this.sectionEdgeHPaddingPx);
            rect.left = i9;
            rect.right = i8 - i9;
        }
        if (i2 - spanCount <= 0) {
            rect.top = this.sectionEdgeVPaddingPx;
        }
        if (isLastRow(i2, spanCount, sectionFindSectionLastItemPos.getCount())) {
            rect.bottom = this.sectionEdgeVPaddingPx;
        }
    }
}
