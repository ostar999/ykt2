package com.hyphenate.easeui.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.hyphenate.easeui.adapter.EaseBaseRecyclerViewAdapter;
import java.util.List;

/* loaded from: classes4.dex */
public abstract class EaseBaseDelegateAdapter<T> extends EaseBaseRecyclerViewAdapter<T> {
    private static final String TAG = "adapter";
    private EaseAdapterDelegatesManager delegatesManager;

    public EaseBaseDelegateAdapter() {
        this.delegatesManager = new EaseAdapterDelegatesManager(false);
    }

    public EaseBaseDelegateAdapter addDelegate(EaseAdapterDelegate easeAdapterDelegate) {
        this.delegatesManager.addDelegate(easeAdapterDelegate, easeAdapterDelegate.getTag());
        notifyDataSetChanged();
        return this;
    }

    public EaseAdapterDelegate getAdapterDelegate(int i2) {
        return this.delegatesManager.getDelegate(i2);
    }

    public List<EaseAdapterDelegate<Object, EaseBaseRecyclerViewAdapter.ViewHolder>> getAllDelegate() {
        return this.delegatesManager.getAllDelegates();
    }

    public int getDelegateViewType(EaseAdapterDelegate easeAdapterDelegate) {
        return this.delegatesManager.getDelegateViewType(easeAdapterDelegate);
    }

    @Override // com.hyphenate.easeui.adapter.EaseBaseRecyclerViewAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i2) {
        try {
            return this.delegatesManager.getItemViewType(getItem(i2), i2);
        } catch (Exception unused) {
            return super.getItemViewType(i2);
        }
    }

    @Override // com.hyphenate.easeui.adapter.EaseBaseRecyclerViewAdapter
    public EaseBaseRecyclerViewAdapter.ViewHolder getViewHolder(ViewGroup viewGroup, int i2) {
        return this.delegatesManager.onCreateViewHolder(viewGroup, i2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        this.delegatesManager.onAttachedToRecyclerView(recyclerView);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        this.delegatesManager.onDetachedFromRecyclerView(recyclerView);
    }

    public EaseBaseDelegateAdapter setFallbackDelegate(EaseAdapterDelegate easeAdapterDelegate, String str) {
        easeAdapterDelegate.setTag(str);
        return setFallbackDelegate(easeAdapterDelegate);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public /* bridge */ /* synthetic */ void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i2, @NonNull List list) {
        onBindViewHolder((EaseBaseRecyclerViewAdapter.ViewHolder) viewHolder, i2, (List<Object>) list);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public boolean onFailedToRecycleView(@NonNull EaseBaseRecyclerViewAdapter.ViewHolder viewHolder) {
        return this.delegatesManager.onFailedToRecycleView(viewHolder);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewAttachedToWindow(@NonNull EaseBaseRecyclerViewAdapter.ViewHolder viewHolder) {
        this.delegatesManager.onViewAttachedToWindow(viewHolder);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewDetachedFromWindow(@NonNull EaseBaseRecyclerViewAdapter.ViewHolder viewHolder) {
        this.delegatesManager.onViewDetachedFromWindow(viewHolder);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewRecycled(@NonNull EaseBaseRecyclerViewAdapter.ViewHolder viewHolder) {
        this.delegatesManager.onViewRecycled(viewHolder);
    }

    public EaseBaseDelegateAdapter(EaseAdapterDelegatesManager easeAdapterDelegatesManager) {
        this.delegatesManager = easeAdapterDelegatesManager;
    }

    public EaseBaseDelegateAdapter addDelegate(EaseAdapterDelegate easeAdapterDelegate, String str) {
        easeAdapterDelegate.setTag(str);
        return addDelegate(easeAdapterDelegate);
    }

    @Override // com.hyphenate.easeui.adapter.EaseBaseRecyclerViewAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull EaseBaseRecyclerViewAdapter.ViewHolder viewHolder, int i2) {
        List<T> list;
        super.onBindViewHolder(viewHolder, i2);
        if (isEmptyViewType(i2) || (list = this.mData) == null || list.isEmpty() || this.delegatesManager.getAllDelegates().isEmpty()) {
            return;
        }
        this.delegatesManager.onBindViewHolder(viewHolder, i2, getItem(i2));
    }

    public EaseBaseDelegateAdapter setFallbackDelegate(EaseAdapterDelegate easeAdapterDelegate) {
        this.delegatesManager.fallbackDelegate = easeAdapterDelegate;
        return this;
    }

    public void onBindViewHolder(@NonNull EaseBaseRecyclerViewAdapter.ViewHolder viewHolder, int i2, @NonNull List<Object> list) {
        List<T> list2;
        super.onBindViewHolder((EaseBaseDelegateAdapter<T>) viewHolder, i2, list);
        if (isEmptyViewType(i2) || (list2 = this.mData) == null || list2.isEmpty() || this.delegatesManager.getAllDelegates().isEmpty()) {
            return;
        }
        this.delegatesManager.onBindViewHolder(viewHolder, i2, list, getItem(i2));
    }
}
