package com.easefun.polyv.livecloudclass.modules.pagemenu.previous.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.easefun.polyv.livecloudclass.R;
import com.easefun.polyv.livecommon.module.modules.previous.customview.PLVPreviousAdapter;
import com.easefun.polyv.livecommon.ui.widget.itemview.PLVBaseViewData;

/* loaded from: classes3.dex */
public class PLVLCPreviousAdapter extends PLVPreviousAdapter<PLVBaseViewData, PLVLCPreviousViewHolder> {
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public PLVLCPreviousViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i2) {
        return new PLVLCPreviousViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.plvlc_previous_list_item, viewGroup, false), this);
    }
}
