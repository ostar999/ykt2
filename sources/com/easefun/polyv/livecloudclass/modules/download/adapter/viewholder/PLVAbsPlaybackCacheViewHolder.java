package com.easefun.polyv.livecloudclass.modules.download.adapter.viewholder;

import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.entity.PLVPlaybackCacheVideoVO;

/* loaded from: classes3.dex */
public abstract class PLVAbsPlaybackCacheViewHolder extends RecyclerView.ViewHolder {
    public static final int TYPE_DOWNLOADED_CACHE = 2;
    public static final int TYPE_DOWNLOADING_CACHE = 1;

    public static class Factory {
        public static PLVAbsPlaybackCacheViewHolder create(@NonNull ViewGroup viewGroup, int i2) {
            return i2 != 1 ? new PLVDownloadedCacheViewHolder(PLVDownloadedCacheViewHolder.createView(viewGroup)) : new PLVDownloadingCacheViewHolder(PLVDownloadingCacheViewHolder.createView(viewGroup));
        }
    }

    public PLVAbsPlaybackCacheViewHolder(View view) {
        super(view);
    }

    public abstract void bind(PLVPlaybackCacheVideoVO pLVPlaybackCacheVideoVO);
}
