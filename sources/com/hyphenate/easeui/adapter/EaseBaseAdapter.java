package com.hyphenate.easeui.adapter;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

/* loaded from: classes4.dex */
public abstract class EaseBaseAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    public abstract Object getItem(int i2);
}
