package com.easefun.polyv.livecommon.ui.widget.itemview.adapter;

import androidx.recyclerview.widget.RecyclerView;
import com.easefun.polyv.livecommon.ui.widget.itemview.PLVBaseViewData;
import com.easefun.polyv.livecommon.ui.widget.itemview.holder.PLVBaseViewHolder;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class PLVBaseAdapter<Data extends PLVBaseViewData, Holder extends PLVBaseViewHolder> extends RecyclerView.Adapter<Holder> {
    public abstract List<Data> getDataList();
}
