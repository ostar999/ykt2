package com.easefun.polyv.livecloudclass.modules.pagemenu.previous.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.easefun.polyv.livecloudclass.R;
import com.easefun.polyv.livecommon.module.modules.previous.customview.PLVChapterAdapter;
import com.easefun.polyv.livecommon.ui.widget.itemview.PLVBaseViewData;

/* loaded from: classes3.dex */
public class PLVLCChapterAdapter extends PLVChapterAdapter<PLVBaseViewData, PLVLCChapterViewHolder> {
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public PLVLCChapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i2) {
        return new PLVLCChapterViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.plvlc_chapter_list_item, viewGroup, false), this);
    }
}
