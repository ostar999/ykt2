package com.hyphenate.easeui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.interfaces.OnItemClickListener;
import com.hyphenate.easeui.interfaces.OnItemLongClickListener;
import com.hyphenate.util.EMLog;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public abstract class EaseBaseRecyclerViewAdapter<T> extends EaseBaseAdapter<ViewHolder> {
    public static final int VIEW_TYPE_EMPTY = -1;
    public static final int VIEW_TYPE_ITEM = 0;
    private View emptyView;
    private int emptyViewId;
    private boolean hideEmptyView;
    public Context mContext;
    public List<T> mData;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public static abstract class ViewHolder<T> extends RecyclerView.ViewHolder {
        private EaseBaseAdapter adapter;

        public ViewHolder(@NonNull View view) {
            super(view);
            initView(view);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setAdapter(EaseBaseRecyclerViewAdapter easeBaseRecyclerViewAdapter) {
            this.adapter = easeBaseRecyclerViewAdapter;
        }

        public <E extends View> E findViewById(@IdRes int i2) {
            return (E) this.itemView.findViewById(i2);
        }

        public EaseBaseAdapter getAdapter() {
            return this.adapter;
        }

        public abstract void initView(View view);

        public abstract void setData(T t2, int i2);

        public void setDataList(List<T> list, int i2) {
        }
    }

    private View getEmptyView(ViewGroup viewGroup) {
        return LayoutInflater.from(this.mContext).inflate(getEmptyLayoutId(), viewGroup, false);
    }

    private ViewHolder getEmptyViewHolder(ViewGroup viewGroup) {
        View emptyView = getEmptyView(viewGroup);
        View view = this.emptyView;
        if (view != null) {
            emptyView = view;
        }
        if (this.emptyViewId > 0) {
            emptyView = LayoutInflater.from(this.mContext).inflate(this.emptyViewId, viewGroup, false);
        }
        if (this.hideEmptyView) {
            emptyView = LayoutInflater.from(this.mContext).inflate(R.layout.ease_layout_no_data_show_nothing, viewGroup, false);
        }
        return new ViewHolder<T>(emptyView) { // from class: com.hyphenate.easeui.adapter.EaseBaseRecyclerViewAdapter.3
            @Override // com.hyphenate.easeui.adapter.EaseBaseRecyclerViewAdapter.ViewHolder
            public void initView(View view2) {
            }

            @Override // com.hyphenate.easeui.adapter.EaseBaseRecyclerViewAdapter.ViewHolder
            public void setData(T t2, int i2) {
            }
        };
    }

    public void addData(T t2) {
        synchronized (EaseBaseRecyclerViewAdapter.class) {
            if (this.mData == null) {
                this.mData = new ArrayList();
            }
            this.mData.add(t2);
        }
        notifyDataSetChanged();
    }

    public void clearData() {
        List<T> list = this.mData;
        if (list != null) {
            list.clear();
            notifyDataSetChanged();
        }
    }

    public List<T> getData() {
        return this.mData;
    }

    public int getEmptyLayoutId() {
        return R.layout.ease_layout_default_no_data;
    }

    @Override // com.hyphenate.easeui.adapter.EaseBaseAdapter
    public synchronized T getItem(int i2) {
        T t2;
        List<T> list = this.mData;
        t2 = null;
        if (list != null && list.size() > i2) {
            t2 = this.mData.get(i2);
        }
        return t2;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<T> list = this.mData;
        if (list == null || list.isEmpty()) {
            return 1;
        }
        return this.mData.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public long getItemId(int i2) {
        return i2;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i2) {
        List<T> list = this.mData;
        return (list == null || list.isEmpty()) ? -1 : 0;
    }

    public abstract ViewHolder getViewHolder(ViewGroup viewGroup, int i2);

    public void hideEmptyView(boolean z2) {
        this.hideEmptyView = z2;
        notifyDataSetChanged();
    }

    public boolean isEmptyViewType(int i2) {
        return getItemViewType(i2) == -1;
    }

    public boolean isItemClickEnable() {
        return true;
    }

    public boolean isItemLongClickEnable() {
        return true;
    }

    public void itemClickAction(View view, int i2) {
        OnItemClickListener onItemClickListener = this.mOnItemClickListener;
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(view, i2);
        }
    }

    public boolean itemLongClickAction(View view, int i2) {
        OnItemLongClickListener onItemLongClickListener = this.mOnItemLongClickListener;
        if (onItemLongClickListener != null) {
            return onItemLongClickListener.onItemLongClick(view, i2);
        }
        return false;
    }

    public synchronized void setData(List<T> list) {
        this.mData = list;
        notifyDataSetChanged();
    }

    public void setEmptyView(View view) {
        this.emptyView = view;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.mOnItemLongClickListener = onItemLongClickListener;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i2) {
        List<T> list;
        viewHolder.setAdapter(this);
        if (isEmptyViewType(i2) || (list = this.mData) == null || list.isEmpty()) {
            return;
        }
        viewHolder.setData(getItem(i2), i2);
        viewHolder.setDataList(this.mData, i2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i2) {
        EMLog.i("adapter", "onCreateViewHolder()");
        this.mContext = viewGroup.getContext();
        if (i2 == -1) {
            return getEmptyViewHolder(viewGroup);
        }
        final ViewHolder viewHolder = getViewHolder(viewGroup, i2);
        if (isItemClickEnable()) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.hyphenate.easeui.adapter.EaseBaseRecyclerViewAdapter.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    EaseBaseRecyclerViewAdapter.this.itemClickAction(view, viewHolder.getBindingAdapterPosition());
                }
            });
        }
        if (isItemLongClickEnable()) {
            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.hyphenate.easeui.adapter.EaseBaseRecyclerViewAdapter.2
                @Override // android.view.View.OnLongClickListener
                public boolean onLongClick(View view) {
                    return EaseBaseRecyclerViewAdapter.this.itemLongClickAction(view, viewHolder.getBindingAdapterPosition());
                }
            });
        }
        return viewHolder;
    }

    public void setEmptyView(@LayoutRes int i2) {
        this.emptyViewId = i2;
        notifyDataSetChanged();
    }

    public void addData(List<T> list) {
        synchronized (EaseBaseRecyclerViewAdapter.class) {
            if (list != null) {
                if (!list.isEmpty()) {
                    List<T> list2 = this.mData;
                    if (list2 == null) {
                        this.mData = list;
                    } else {
                        list2.addAll(list);
                    }
                    notifyDataSetChanged();
                }
            }
        }
    }

    public void addData(int i2, List<T> list) {
        synchronized (EaseBaseRecyclerViewAdapter.class) {
            if (list != null) {
                if (!list.isEmpty()) {
                    List<T> list2 = this.mData;
                    if (list2 == null) {
                        this.mData = list;
                    } else {
                        list2.addAll(i2, list);
                    }
                    notifyDataSetChanged();
                }
            }
        }
    }

    public void addData(int i2, List<T> list, boolean z2) {
        synchronized (EaseBaseRecyclerViewAdapter.class) {
            if (list != null) {
                if (!list.isEmpty()) {
                    List<T> list2 = this.mData;
                    if (list2 == null) {
                        this.mData = list;
                    } else {
                        list2.addAll(i2, list);
                    }
                    if (z2) {
                        notifyDataSetChanged();
                    }
                }
            }
        }
    }
}
