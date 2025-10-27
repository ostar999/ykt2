package com.hyphenate.easeui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.hyphenate.easeui.adapter.EaseBaseRecyclerViewAdapter;
import com.hyphenate.easeui.adapter.EaseBaseRecyclerViewAdapter.ViewHolder;

/* loaded from: classes4.dex */
public abstract class EaseBaseDelegate<T, VH extends EaseBaseRecyclerViewAdapter.ViewHolder> extends EaseAdapterDelegate<T, VH> {
    public abstract VH createViewHolder(View view);

    public abstract int getLayoutId();

    @Override // com.hyphenate.easeui.adapter.EaseAdapterDelegate
    public VH onCreateViewHolder(ViewGroup viewGroup, String str) {
        return (VH) createViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(getLayoutId(), viewGroup, false));
    }
}
