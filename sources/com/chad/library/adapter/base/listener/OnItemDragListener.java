package com.chad.library.adapter.base.listener;

import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes2.dex */
public interface OnItemDragListener {
    void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int i2);

    void onItemDragMoving(RecyclerView.ViewHolder viewHolder, int i2, RecyclerView.ViewHolder viewHolder2, int i3);

    void onItemDragStart(RecyclerView.ViewHolder viewHolder, int i2);
}
