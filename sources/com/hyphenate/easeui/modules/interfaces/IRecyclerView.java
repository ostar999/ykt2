package com.hyphenate.easeui.modules.interfaces;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.hyphenate.easeui.interfaces.OnItemClickListener;
import com.hyphenate.easeui.interfaces.OnItemLongClickListener;

/* loaded from: classes4.dex */
public interface IRecyclerView {
    void addFooterAdapter(RecyclerView.Adapter adapter);

    void addHeaderAdapter(RecyclerView.Adapter adapter);

    void addRVItemDecoration(@NonNull RecyclerView.ItemDecoration itemDecoration);

    void removeAdapter(RecyclerView.Adapter adapter);

    void removeRVItemDecoration(@NonNull RecyclerView.ItemDecoration itemDecoration);

    void setOnItemClickListener(OnItemClickListener onItemClickListener);

    void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener);
}
