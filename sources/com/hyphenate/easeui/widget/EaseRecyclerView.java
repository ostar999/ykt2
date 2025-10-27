package com.hyphenate.easeui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class EaseRecyclerView extends RecyclerView {
    private static final int BASE_FOOTER_VIEW_TYPE = -2048;
    private static final int BASE_HEADER_VIEW_TYPE = -1024;
    private boolean isShouldSpan;
    private boolean isStaggered;
    private RecyclerView.Adapter mAdapter;
    private RecyclerViewContextMenuInfo mContextMenuInfo;
    private List<FixedViewInfo> mFooterViewInfos;
    private List<FixedViewInfo> mHeaderViewInfos;
    private RecyclerView.AdapterDataObserver mObserver;

    public class FixedViewInfo {
        public View view;
        public int viewType;

        public FixedViewInfo() {
        }
    }

    public static class RecyclerViewContextMenuInfo implements ContextMenu.ContextMenuInfo {
        public long id;
        public int position;

        public RecyclerViewContextMenuInfo(int i2, long j2) {
            this.position = i2;
            this.id = j2;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View view) {
            super(view);
        }
    }

    public class WrapperRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private RecyclerView.Adapter mAdapter;

        public WrapperRecyclerViewAdapter(RecyclerView.Adapter adapter) {
            this.mAdapter = adapter;
        }

        public void adjustSpanSize(RecyclerView recyclerView) {
            if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                final GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: com.hyphenate.easeui.widget.EaseRecyclerView.WrapperRecyclerViewAdapter.1
                    @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
                    public int getSpanSize(int i2) {
                        int headersCount = EaseRecyclerView.this.getHeadersCount();
                        int i3 = i2 - headersCount;
                        if (i2 < headersCount || i3 >= WrapperRecyclerViewAdapter.this.mAdapter.getItemCount()) {
                            return gridLayoutManager.getSpanCount();
                        }
                        return 1;
                    }
                });
            }
            if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
                EaseRecyclerView.this.isStaggered = true;
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int findRelativeAdapterPositionIn(@NonNull RecyclerView.Adapter<? extends RecyclerView.ViewHolder> adapter, @NonNull RecyclerView.ViewHolder viewHolder, int i2) {
            if (adapter == this) {
                return i2;
            }
            RecyclerView.Adapter adapter2 = this.mAdapter;
            if (!(adapter2 instanceof ConcatAdapter)) {
                return super.findRelativeAdapterPositionIn(adapter, viewHolder, i2);
            }
            List<? extends RecyclerView.Adapter<? extends RecyclerView.ViewHolder>> adapters = ((ConcatAdapter) adapter2).getAdapters();
            int itemCount = 0;
            for (int i3 = 0; i3 < adapters.size(); i3++) {
                RecyclerView.Adapter<? extends RecyclerView.ViewHolder> adapter3 = adapters.get(i3);
                if (adapter3 == adapter) {
                    return i2 - itemCount;
                }
                itemCount += adapter3.getItemCount();
            }
            return -1;
        }

        public RecyclerView.Adapter getAdapter() {
            return this.mAdapter;
        }

        public int getContentCount() {
            RecyclerView.Adapter adapter = this.mAdapter;
            if (adapter == null) {
                return 0;
            }
            return adapter.getItemCount();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return EaseRecyclerView.this.getHeadersCount() + EaseRecyclerView.this.getFootersCount() + getContentCount();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public long getItemId(int i2) {
            if (isHeaderOrFooter(i2)) {
                return -1L;
            }
            return this.mAdapter.getItemId(i2 - EaseRecyclerView.this.getHeadersCount());
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemViewType(int i2) {
            return isHeader(i2) ? ((FixedViewInfo) EaseRecyclerView.this.mHeaderViewInfos.get(i2)).viewType : isFooter(i2) ? ((FixedViewInfo) EaseRecyclerView.this.mFooterViewInfos.get((i2 - EaseRecyclerView.this.getHeadersCount()) - getContentCount())).viewType : this.mAdapter.getItemViewType(i2 - EaseRecyclerView.this.getHeadersCount());
        }

        public boolean isFooter(int i2) {
            return i2 >= EaseRecyclerView.this.getHeadersCount() + getContentCount();
        }

        public boolean isHeader(int i2) {
            return i2 < EaseRecyclerView.this.getHeadersCount();
        }

        public boolean isHeaderOrFooter(RecyclerView.ViewHolder viewHolder) {
            return (viewHolder instanceof ViewHolder) || isHeaderOrFooter(viewHolder.getAdapterPosition());
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i2) {
            if (isHeaderOrFooter(viewHolder)) {
                return;
            }
            this.mAdapter.onBindViewHolder(viewHolder, i2 - EaseRecyclerView.this.getHeadersCount());
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        @NonNull
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i2) {
            if (i2 >= EaseRecyclerView.BASE_HEADER_VIEW_TYPE && i2 < EaseRecyclerView.this.getHeadersCount() + EaseRecyclerView.BASE_HEADER_VIEW_TYPE) {
                return EaseRecyclerView.this.viewHolder(((FixedViewInfo) EaseRecyclerView.this.mHeaderViewInfos.get(i2 - EaseRecyclerView.BASE_HEADER_VIEW_TYPE)).view);
            }
            if (i2 < EaseRecyclerView.BASE_FOOTER_VIEW_TYPE || i2 >= EaseRecyclerView.this.getFootersCount() + EaseRecyclerView.BASE_FOOTER_VIEW_TYPE) {
                return this.mAdapter.onCreateViewHolder(viewGroup, i2);
            }
            return EaseRecyclerView.this.viewHolder(((FixedViewInfo) EaseRecyclerView.this.mFooterViewInfos.get(i2 - EaseRecyclerView.BASE_FOOTER_VIEW_TYPE)).view);
        }

        public boolean isHeaderOrFooter(int i2) {
            return isHeader(i2) || isFooter(i2);
        }
    }

    public EaseRecyclerView(@NonNull Context context) {
        super(context);
        this.mHeaderViewInfos = new ArrayList();
        this.mFooterViewInfos = new ArrayList();
        this.mObserver = new RecyclerView.AdapterDataObserver() { // from class: com.hyphenate.easeui.widget.EaseRecyclerView.1
            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onChanged() {
                EaseRecyclerView.this.mAdapter.notifyDataSetChanged();
            }

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeChanged(int i2, int i3) {
                EaseRecyclerView.this.mAdapter.notifyItemRangeChanged(i2 + EaseRecyclerView.this.getHeadersCount(), i3);
            }

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeInserted(int i2, int i3) {
                EaseRecyclerView.this.mAdapter.notifyItemRangeInserted(i2 + EaseRecyclerView.this.getHeadersCount(), i3);
            }

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeMoved(int i2, int i3, int i4) {
                EaseRecyclerView.this.mAdapter.notifyItemMoved(i2 + EaseRecyclerView.this.getHeadersCount(), i3 + EaseRecyclerView.this.getHeadersCount());
            }

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeRemoved(int i2, int i3) {
                EaseRecyclerView.this.mAdapter.notifyItemRangeRemoved(i2 + EaseRecyclerView.this.getHeadersCount(), i3);
            }

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeChanged(int i2, int i3, @Nullable Object obj) {
                EaseRecyclerView.this.mAdapter.notifyItemRangeChanged(i2 + EaseRecyclerView.this.getHeadersCount(), i3, obj);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public RecyclerView.ViewHolder viewHolder(View view) {
        if (this.isStaggered) {
            StaggeredGridLayoutManager.LayoutParams layoutParams = new StaggeredGridLayoutManager.LayoutParams(-1, -2);
            layoutParams.setFullSpan(true);
            view.setLayoutParams(layoutParams);
        }
        return new ViewHolder(view);
    }

    public void addFooterView(View view) {
        FixedViewInfo fixedViewInfo = new FixedViewInfo();
        fixedViewInfo.view = view;
        fixedViewInfo.viewType = this.mFooterViewInfos.size() + BASE_FOOTER_VIEW_TYPE;
        this.mFooterViewInfos.add(fixedViewInfo);
        RecyclerView.Adapter adapter = this.mAdapter;
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    public void addHeaderView(View view) {
        FixedViewInfo fixedViewInfo = new FixedViewInfo();
        fixedViewInfo.view = view;
        fixedViewInfo.viewType = this.mHeaderViewInfos.size() + BASE_HEADER_VIEW_TYPE;
        this.mHeaderViewInfos.add(fixedViewInfo);
        RecyclerView.Adapter adapter = this.mAdapter;
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    public int getChildBindingAdapterPosition(@NonNull View view) {
        RecyclerView.ViewHolder childViewHolderInt = getChildViewHolderInt(view);
        if (childViewHolderInt != null) {
            return childViewHolderInt.getBindingAdapterPosition();
        }
        return -1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView
    public RecyclerView.ViewHolder getChildViewHolder(@NonNull View view) {
        return super.getChildViewHolder(view);
    }

    public RecyclerView.ViewHolder getChildViewHolderInt(View view) {
        if (view == null) {
            return null;
        }
        return getChildViewHolder(view);
    }

    @Override // android.view.View
    public ContextMenu.ContextMenuInfo getContextMenuInfo() {
        return this.mContextMenuInfo;
    }

    public int getFootersCount() {
        return this.mFooterViewInfos.size();
    }

    public int getHeadersCount() {
        return this.mHeaderViewInfos.size();
    }

    public void removeHeaderViews() {
        List<FixedViewInfo> list = this.mHeaderViewInfos;
        if (list != null) {
            list.clear();
        }
        RecyclerView.Adapter adapter = this.mAdapter;
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView
    public void setAdapter(@Nullable RecyclerView.Adapter adapter) {
        if (!(adapter instanceof WrapperRecyclerViewAdapter)) {
            if (adapter != null) {
                adapter.registerAdapterDataObserver(this.mObserver);
            }
            WrapperRecyclerViewAdapter wrapperRecyclerViewAdapter = new WrapperRecyclerViewAdapter(adapter);
            this.mAdapter = wrapperRecyclerViewAdapter;
            if (adapter != null) {
                wrapperRecyclerViewAdapter.setHasStableIds(adapter.hasStableIds());
            }
        }
        super.setAdapter(this.mAdapter);
        if (this.isShouldSpan) {
            ((WrapperRecyclerViewAdapter) this.mAdapter).adjustSpanSize(this);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView
    public void setLayoutManager(@Nullable RecyclerView.LayoutManager layoutManager) {
        if ((layoutManager instanceof GridLayoutManager) || (layoutManager instanceof StaggeredGridLayoutManager)) {
            this.isShouldSpan = true;
        }
        super.setLayoutManager(layoutManager);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean showContextMenuForChild(View view) {
        int childBindingAdapterPosition = getChildBindingAdapterPosition(view);
        if (childBindingAdapterPosition < 0) {
            return false;
        }
        this.mContextMenuInfo = new RecyclerViewContextMenuInfo(childBindingAdapterPosition, getAdapter().getItemId(childBindingAdapterPosition));
        return super.showContextMenuForChild(view);
    }

    public EaseRecyclerView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mHeaderViewInfos = new ArrayList();
        this.mFooterViewInfos = new ArrayList();
        this.mObserver = new RecyclerView.AdapterDataObserver() { // from class: com.hyphenate.easeui.widget.EaseRecyclerView.1
            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onChanged() {
                EaseRecyclerView.this.mAdapter.notifyDataSetChanged();
            }

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeChanged(int i2, int i3) {
                EaseRecyclerView.this.mAdapter.notifyItemRangeChanged(i2 + EaseRecyclerView.this.getHeadersCount(), i3);
            }

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeInserted(int i2, int i3) {
                EaseRecyclerView.this.mAdapter.notifyItemRangeInserted(i2 + EaseRecyclerView.this.getHeadersCount(), i3);
            }

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeMoved(int i2, int i3, int i4) {
                EaseRecyclerView.this.mAdapter.notifyItemMoved(i2 + EaseRecyclerView.this.getHeadersCount(), i3 + EaseRecyclerView.this.getHeadersCount());
            }

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeRemoved(int i2, int i3) {
                EaseRecyclerView.this.mAdapter.notifyItemRangeRemoved(i2 + EaseRecyclerView.this.getHeadersCount(), i3);
            }

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeChanged(int i2, int i3, @Nullable Object obj) {
                EaseRecyclerView.this.mAdapter.notifyItemRangeChanged(i2 + EaseRecyclerView.this.getHeadersCount(), i3, obj);
            }
        };
    }

    public EaseRecyclerView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mHeaderViewInfos = new ArrayList();
        this.mFooterViewInfos = new ArrayList();
        this.mObserver = new RecyclerView.AdapterDataObserver() { // from class: com.hyphenate.easeui.widget.EaseRecyclerView.1
            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onChanged() {
                EaseRecyclerView.this.mAdapter.notifyDataSetChanged();
            }

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeChanged(int i22, int i3) {
                EaseRecyclerView.this.mAdapter.notifyItemRangeChanged(i22 + EaseRecyclerView.this.getHeadersCount(), i3);
            }

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeInserted(int i22, int i3) {
                EaseRecyclerView.this.mAdapter.notifyItemRangeInserted(i22 + EaseRecyclerView.this.getHeadersCount(), i3);
            }

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeMoved(int i22, int i3, int i4) {
                EaseRecyclerView.this.mAdapter.notifyItemMoved(i22 + EaseRecyclerView.this.getHeadersCount(), i3 + EaseRecyclerView.this.getHeadersCount());
            }

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeRemoved(int i22, int i3) {
                EaseRecyclerView.this.mAdapter.notifyItemRangeRemoved(i22 + EaseRecyclerView.this.getHeadersCount(), i3);
            }

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeChanged(int i22, int i3, @Nullable Object obj) {
                EaseRecyclerView.this.mAdapter.notifyItemRangeChanged(i22 + EaseRecyclerView.this.getHeadersCount(), i3, obj);
            }
        };
    }
}
