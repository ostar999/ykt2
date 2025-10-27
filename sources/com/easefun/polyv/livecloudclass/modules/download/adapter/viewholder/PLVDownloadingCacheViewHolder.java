package com.easefun.polyv.livecloudclass.modules.download.adapter.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.easefun.polyv.livecloudclass.R;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.entity.PLVPlaybackCacheVideoVO;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.enums.PLVPlaybackCacheDownloadStatusEnum;
import com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.PLVPlaybackCacheListViewModel;
import com.easefun.polyv.livecommon.ui.widget.roundview.PLVRoundImageView;
import com.plv.foundationsdk.component.di.PLVDependManager;
import com.plv.foundationsdk.utils.PLVSugarUtil;

/* loaded from: classes3.dex */
public class PLVDownloadingCacheViewHolder extends PLVAbsPlaybackCacheViewHolder implements View.OnClickListener {
    private PLVRoundImageView playbackCacheDownloadingDeleteIv;
    private ProgressBar playbackCacheDownloadingProgressBar;
    private TextView playbackCacheDownloadingSizeTv;
    private PLVRoundImageView playbackCacheDownloadingStartOrPauseIv;
    private TextView playbackCacheDownloadingStatusTv;
    private TextView playbackCacheDownloadingVideoTitleTv;
    private final PLVPlaybackCacheListViewModel playbackCacheListViewModel;
    private PLVPlaybackCacheVideoVO videoVO;

    public PLVDownloadingCacheViewHolder(View view) {
        super(view);
        this.playbackCacheListViewModel = (PLVPlaybackCacheListViewModel) PLVDependManager.getInstance().get(PLVPlaybackCacheListViewModel.class);
        initView();
    }

    public static View createView(ViewGroup viewGroup) {
        return LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.plv_playback_cache_downloading_item_layout, viewGroup, false);
    }

    private void initView() {
        this.playbackCacheDownloadingVideoTitleTv = (TextView) this.itemView.findViewById(R.id.plv_playback_cache_downloading_video_title_tv);
        this.playbackCacheDownloadingProgressBar = (ProgressBar) this.itemView.findViewById(R.id.plv_playback_cache_downloading_progress_bar);
        this.playbackCacheDownloadingStatusTv = (TextView) this.itemView.findViewById(R.id.plv_playback_cache_downloading_status_tv);
        this.playbackCacheDownloadingSizeTv = (TextView) this.itemView.findViewById(R.id.plv_playback_cache_downloading_size_tv);
        this.playbackCacheDownloadingDeleteIv = (PLVRoundImageView) this.itemView.findViewById(R.id.plv_playback_cache_downloading_delete_iv);
        this.playbackCacheDownloadingStartOrPauseIv = (PLVRoundImageView) this.itemView.findViewById(R.id.plv_playback_cache_downloading_start_or_pause_iv);
    }

    private static boolean isDownloading(PLVPlaybackCacheVideoVO pLVPlaybackCacheVideoVO) {
        return pLVPlaybackCacheVideoVO.getDownloadStatusEnum() == PLVPlaybackCacheDownloadStatusEnum.DOWNLOADING;
    }

    @Override // com.easefun.polyv.livecloudclass.modules.download.adapter.viewholder.PLVAbsPlaybackCacheViewHolder
    public void bind(PLVPlaybackCacheVideoVO pLVPlaybackCacheVideoVO) {
        this.videoVO = pLVPlaybackCacheVideoVO;
        this.playbackCacheDownloadingVideoTitleTv.setText(pLVPlaybackCacheVideoVO.getTitle());
        this.playbackCacheDownloadingProgressBar.setProgress(((Integer) PLVSugarUtil.getOrDefault(pLVPlaybackCacheVideoVO.getProgress(), 0)).intValue());
        this.playbackCacheDownloadingStatusTv.setText(pLVPlaybackCacheVideoVO.getDownloadStatusEnum().getStatusName());
        this.playbackCacheDownloadingSizeTv.setText(PLVSugarUtil.format("{}/{}", PLVPlaybackCacheVideoVO.bytesToFitSizeString(pLVPlaybackCacheVideoVO.getDownloadedBytes()), PLVPlaybackCacheVideoVO.bytesToFitSizeString(pLVPlaybackCacheVideoVO.getTotalBytes())));
        this.playbackCacheDownloadingStartOrPauseIv.setImageResource(isDownloading(pLVPlaybackCacheVideoVO) ? R.drawable.plv_playback_cache_pause_icon : R.drawable.plv_playback_cache_start_icon);
        this.playbackCacheDownloadingDeleteIv.setOnClickListener(this);
        this.playbackCacheDownloadingStartOrPauseIv.setOnClickListener(this);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == this.playbackCacheDownloadingDeleteIv.getId()) {
            this.playbackCacheListViewModel.deleteDownload(this.videoVO);
        } else if (id == this.playbackCacheDownloadingStartOrPauseIv.getId()) {
            if (this.videoVO.getDownloadStatusEnum() == PLVPlaybackCacheDownloadStatusEnum.WAITING || this.videoVO.getDownloadStatusEnum() == PLVPlaybackCacheDownloadStatusEnum.DOWNLOADING) {
                this.playbackCacheListViewModel.pauseDownload(this.videoVO);
            } else {
                this.playbackCacheListViewModel.startDownload(this.videoVO);
            }
        }
    }
}
