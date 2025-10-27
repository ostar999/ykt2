package com.easefun.polyv.livecloudclass.modules.download.adapter.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.easefun.polyv.livecloudclass.R;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.entity.PLVPlaybackCacheVideoVO;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.enums.PLVPlaybackCacheDownloadStatusEnum;
import com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.PLVPlaybackCacheListViewModel;
import com.easefun.polyv.livecommon.ui.widget.roundview.PLVRoundImageView;
import com.plv.foundationsdk.component.di.PLVDependManager;

/* loaded from: classes3.dex */
public class PLVDownloadedCacheViewHolder extends PLVAbsPlaybackCacheViewHolder implements View.OnClickListener {
    private PLVRoundImageView playbackCacheDownloadedDeleteIv;
    private TextView playbackCacheDownloadedSizeTv;
    private TextView playbackCacheDownloadedVideoTitleTv;
    private final PLVPlaybackCacheListViewModel playbackCacheListViewModel;
    private PLVPlaybackCacheVideoVO videoVO;

    public PLVDownloadedCacheViewHolder(View view) {
        super(view);
        this.playbackCacheListViewModel = (PLVPlaybackCacheListViewModel) PLVDependManager.getInstance().get(PLVPlaybackCacheListViewModel.class);
        initView();
    }

    public static View createView(ViewGroup viewGroup) {
        return LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.plv_playback_cache_downloaded_item_layout, viewGroup, false);
    }

    private void initView() {
        this.playbackCacheDownloadedVideoTitleTv = (TextView) this.itemView.findViewById(R.id.plv_playback_cache_downloaded_video_title_tv);
        this.playbackCacheDownloadedSizeTv = (TextView) this.itemView.findViewById(R.id.plv_playback_cache_downloaded_size_tv);
        this.playbackCacheDownloadedDeleteIv = (PLVRoundImageView) this.itemView.findViewById(R.id.plv_playback_cache_downloaded_delete_iv);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.download.adapter.viewholder.PLVAbsPlaybackCacheViewHolder
    public void bind(PLVPlaybackCacheVideoVO pLVPlaybackCacheVideoVO) {
        this.videoVO = pLVPlaybackCacheVideoVO;
        this.playbackCacheDownloadedVideoTitleTv.setText(pLVPlaybackCacheVideoVO.getTitle());
        if (pLVPlaybackCacheVideoVO.getDownloadStatusEnum() == PLVPlaybackCacheDownloadStatusEnum.DOWNLOADED) {
            this.playbackCacheDownloadedSizeTv.setText(PLVPlaybackCacheVideoVO.bytesToFitSizeString(pLVPlaybackCacheVideoVO.getTotalBytes()));
        } else {
            this.playbackCacheDownloadedSizeTv.setText(pLVPlaybackCacheVideoVO.getDownloadStatusEnum().getStatusName());
        }
        this.itemView.setOnClickListener(this);
        this.playbackCacheDownloadedDeleteIv.setOnClickListener(this);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == this.playbackCacheDownloadedDeleteIv.getId()) {
            this.playbackCacheListViewModel.deleteDownload(this.videoVO);
        } else if (id == this.itemView.getId()) {
            this.playbackCacheListViewModel.requestLaunchDownloadedPlayback(this.videoVO);
        }
    }
}
