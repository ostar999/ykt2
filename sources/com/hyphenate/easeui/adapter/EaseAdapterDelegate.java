package com.hyphenate.easeui.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public abstract class EaseAdapterDelegate<T, VH extends RecyclerView.ViewHolder> implements Cloneable {
    public static final String DEFAULT_TAG = "";
    private String tag = "";
    public List<String> tags = new ArrayList();

    public EaseAdapterDelegate() {
        setTag(this.tag);
    }

    @NonNull
    public Object clone() throws CloneNotSupportedException {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public int getItemCount() {
        return 0;
    }

    public int getItemViewType() {
        return 0;
    }

    public String getTag() {
        return this.tag;
    }

    public List<String> getTags() {
        return this.tags;
    }

    public boolean isForViewType(T t2, int i2) {
        return true;
    }

    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
    }

    public void onBindViewHolder(VH vh, int i2, T t2) {
    }

    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i2, @NonNull List<Object> list, T t2) {
    }

    public abstract VH onCreateViewHolder(ViewGroup viewGroup, String str);

    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
    }

    public boolean onFailedToRecycleView(@NonNull RecyclerView.ViewHolder viewHolder) {
        return false;
    }

    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder viewHolder) {
    }

    public void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder viewHolder) {
    }

    public void onViewRecycled(@NonNull RecyclerView.ViewHolder viewHolder) {
    }

    public void setTag(String str) {
        this.tag = str;
        this.tags.add(str);
    }

    public EaseAdapterDelegate(String str) {
        setTag(str);
    }
}
