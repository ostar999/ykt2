package com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource;

import androidx.annotation.NonNull;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.entity.PLVPlaybackCacheVideoVO;
import com.plv.livescenes.download.PLVDownloader;
import com.plv.livescenes.download.api.PLVPlaybackDownloadApiManager;
import com.plv.livescenes.model.PLVPlaybackVideoVO;
import com.plv.livescenes.model.PLVTempStorePlaybackVideoVO;
import com.plv.livescenes.playback.video.PLVPlaybackListType;
import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/* loaded from: classes3.dex */
public class PLVPlaybackCacheNetworkDataSource {
    private final PLVPlaybackDownloadApiManager downloadApiManager;

    public PLVPlaybackCacheNetworkDataSource(final PLVPlaybackDownloadApiManager downloadApiManager) {
        this.downloadApiManager = downloadApiManager;
    }

    private Observable<PLVPlaybackCacheVideoVO> getTempStorePlaybackVideoData(final String channelId, final String fileId) {
        return this.downloadApiManager.requestTempStorePlaybackVideoDetail(channelId, fileId).subscribeOn(Schedulers.io()).map(new Function<PLVTempStorePlaybackVideoVO, PLVPlaybackCacheVideoVO>() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.PLVPlaybackCacheNetworkDataSource.1
            @Override // io.reactivex.functions.Function
            public PLVPlaybackCacheVideoVO apply(@NonNull PLVTempStorePlaybackVideoVO playbackVideoVO) throws Exception {
                PLVPlaybackCacheVideoVO pLVPlaybackCacheVideoVO = new PLVPlaybackCacheVideoVO();
                pLVPlaybackCacheVideoVO.setVideoPoolId(playbackVideoVO.getData().getFileId());
                pLVPlaybackCacheVideoVO.setVideoId(playbackVideoVO.getData().getFileId());
                pLVPlaybackCacheVideoVO.setTitle(playbackVideoVO.getData().getFilename());
                pLVPlaybackCacheVideoVO.setVideoDuration(playbackVideoVO.getData().getDuration());
                pLVPlaybackCacheVideoVO.setLiveType(playbackVideoVO.getData().getLiveType());
                pLVPlaybackCacheVideoVO.setChannelSessionId(playbackVideoVO.getData().getChannelSessionId());
                pLVPlaybackCacheVideoVO.setOriginSessionId(playbackVideoVO.getData().getOriginSessionId());
                pLVPlaybackCacheVideoVO.setTotalBytes(Long.valueOf(PLVDownloader.getVideoDownloadSize(playbackVideoVO)));
                pLVPlaybackCacheVideoVO.setEnableDownload(Boolean.valueOf(playbackVideoVO.getData().isPlaybackCacheEnable() && playbackVideoVO.getData().getVideoCache().getVideoUrl().toLowerCase().endsWith(".mp4")));
                return pLVPlaybackCacheVideoVO;
            }
        });
    }

    public Observable<PLVPlaybackCacheVideoVO> getPlaybackCacheVideoData(final String channelId, final String videoId, final PLVPlaybackListType playbackListType) {
        return playbackListType == PLVPlaybackListType.TEMP_STORE ? getTempStorePlaybackVideoData(channelId, videoId) : getPlaybackCacheVideoData(channelId, videoId);
    }

    private Observable<PLVPlaybackCacheVideoVO> getPlaybackCacheVideoData(final String channelId, final String videoId) {
        return this.downloadApiManager.requestPlaybackVideoDetail(channelId, videoId).subscribeOn(Schedulers.io()).map(new Function<PLVPlaybackVideoVO, PLVPlaybackCacheVideoVO>() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.PLVPlaybackCacheNetworkDataSource.2
            @Override // io.reactivex.functions.Function
            public PLVPlaybackCacheVideoVO apply(@NonNull PLVPlaybackVideoVO playbackVideoVO) throws Exception {
                PLVPlaybackCacheVideoVO pLVPlaybackCacheVideoVO = new PLVPlaybackCacheVideoVO();
                pLVPlaybackCacheVideoVO.setVideoPoolId(playbackVideoVO.getData().getVideoPoolId());
                pLVPlaybackCacheVideoVO.setVideoId(playbackVideoVO.getData().getVideoId());
                pLVPlaybackCacheVideoVO.setTitle(playbackVideoVO.getData().getTitle());
                pLVPlaybackCacheVideoVO.setFirstImageUrl(playbackVideoVO.getData().getFirstImage());
                pLVPlaybackCacheVideoVO.setVideoDuration(playbackVideoVO.getData().getDuration());
                pLVPlaybackCacheVideoVO.setLiveType(playbackVideoVO.getData().getLiveType());
                pLVPlaybackCacheVideoVO.setChannelSessionId(playbackVideoVO.getData().getChannelSessionId());
                pLVPlaybackCacheVideoVO.setOriginSessionId(playbackVideoVO.getData().getOriginSessionId());
                pLVPlaybackCacheVideoVO.setTotalBytes(Long.valueOf(PLVDownloader.getVideoDownloadSize(playbackVideoVO)));
                pLVPlaybackCacheVideoVO.setEnableDownload(Boolean.valueOf(playbackVideoVO.getData().isPlaybackCacheEnable() && playbackVideoVO.getData().getVideoCache().getVideoUrl().toLowerCase().endsWith(".mp4")));
                return pLVPlaybackCacheVideoVO;
            }
        });
    }
}
