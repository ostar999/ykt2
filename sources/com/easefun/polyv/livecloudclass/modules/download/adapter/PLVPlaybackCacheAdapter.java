package com.easefun.polyv.livecloudclass.modules.download.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.easefun.polyv.livecloudclass.modules.download.adapter.viewholder.PLVAbsPlaybackCacheViewHolder;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.entity.PLVPlaybackCacheVideoVO;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVPlaybackCacheAdapter extends RecyclerView.Adapter<PLVAbsPlaybackCacheViewHolder> {
    private final int cacheType;
    private final List<PLVPlaybackCacheVideoVO> playbackCacheList = new ArrayList();

    public PLVPlaybackCacheAdapter(int i2) {
        this.cacheType = i2;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.playbackCacheList.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i2) {
        return this.cacheType;
    }

    public void updateData(List<PLVPlaybackCacheVideoVO> list) {
        this.playbackCacheList.clear();
        this.playbackCacheList.addAll(list);
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull PLVAbsPlaybackCacheViewHolder pLVAbsPlaybackCacheViewHolder, int i2) {
        pLVAbsPlaybackCacheViewHolder.bind(this.playbackCacheList.get(i2));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public PLVAbsPlaybackCacheViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i2) {
        return PLVAbsPlaybackCacheViewHolder.Factory.create(viewGroup, i2);
    }
}
