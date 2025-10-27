package com.hyphenate.easeui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import java.util.List;

/* loaded from: classes4.dex */
public abstract class EaseBaseChatExtendMenuAdapter<VH extends RecyclerView.ViewHolder, T> extends RecyclerView.Adapter<VH> {
    public List<T> mData;

    public abstract VH easeCreateViewHolder(View view);

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<T> list = this.mData;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public abstract int getItemLayoutId();

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i2) {
        return (VH) easeCreateViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(getItemLayoutId(), viewGroup, false));
    }

    public void setData(List<T> list) {
        this.mData = list;
        notifyDataSetChanged();
    }
}
