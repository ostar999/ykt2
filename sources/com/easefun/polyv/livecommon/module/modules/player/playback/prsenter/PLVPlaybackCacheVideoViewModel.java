package com.easefun.polyv.livecommon.module.modules.player.playback.prsenter;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.PLVPlaybackCacheRepo;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.entity.PLVPlaybackCacheVideoVO;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.entity.PLVPlaybackCacheViewerInfoVO;
import com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.config.PLVPlaybackCacheVideoConfig;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.livescenes.playback.video.PLVPlaybackListType;
import com.plv.livescenes.playback.vo.PLVPlaybackDataVO;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/* loaded from: classes3.dex */
public class PLVPlaybackCacheVideoViewModel {
    private Disposable playbackCacheInitDisposable;
    private final PLVPlaybackCacheRepo playbackCacheRepo;
    private Disposable playbackCacheUpdateDisposable;
    private final MutableLiveData<PLVPlaybackCacheVideoVO> playbackCacheUpdateLiveData = new MutableLiveData<>();
    private final PLVPlaybackCacheVideoConfig playbackCacheVideoConfig;

    public PLVPlaybackCacheVideoViewModel(final PLVPlaybackCacheRepo playbackCacheRepo, final PLVPlaybackCacheVideoConfig playbackCacheVideoConfig) {
        this.playbackCacheRepo = playbackCacheRepo;
        this.playbackCacheVideoConfig = playbackCacheVideoConfig;
        init();
    }

    private PLVPlaybackCacheVideoVO appendViewerInfo(PLVPlaybackCacheVideoVO vo) {
        PLVPlaybackCacheViewerInfoVO viewerInfoVO = vo.getViewerInfoVO();
        viewerInfoVO.setChannelId(this.playbackCacheVideoConfig.getChannelId());
        viewerInfoVO.setVid(this.playbackCacheVideoConfig.getVid());
        viewerInfoVO.setViewerId(this.playbackCacheVideoConfig.getViewerId());
        viewerInfoVO.setViewerName(this.playbackCacheVideoConfig.getViewerName());
        viewerInfoVO.setViewerAvatar(this.playbackCacheVideoConfig.getViewerAvatar());
        viewerInfoVO.setChannelType(this.playbackCacheVideoConfig.getChannelType());
        viewerInfoVO.setPlaybackListType(this.playbackCacheVideoConfig.getPlaybackListType());
        return vo;
    }

    private void init() {
        observePlaybackCacheUpdate();
        initPlaybackCache();
    }

    private void initPlaybackCache() {
        Disposable disposable = this.playbackCacheInitDisposable;
        if (disposable != null) {
            disposable.dispose();
        }
        this.playbackCacheInitDisposable = this.playbackCacheRepo.getCacheVideoById(this.playbackCacheVideoConfig.getChannelId(), this.playbackCacheVideoConfig.getVideoPoolId(), this.playbackCacheVideoConfig.getPlaybackListType()).subscribeOn(Schedulers.io()).subscribe(new Consumer<PLVPlaybackCacheVideoVO>() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.PLVPlaybackCacheVideoViewModel.3
            @Override // io.reactivex.functions.Consumer
            public void accept(PLVPlaybackCacheVideoVO videoVO) throws Exception {
                if (videoVO == null || videoVO.getVideoPoolId() == null || !videoVO.getVideoPoolId().equals(PLVPlaybackCacheVideoViewModel.this.playbackCacheVideoConfig.getVideoPoolId())) {
                    return;
                }
                PLVPlaybackCacheVideoViewModel.this.playbackCacheUpdateLiveData.postValue(videoVO);
            }
        }, new Consumer<Throwable>() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.PLVPlaybackCacheVideoViewModel.4
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable throwable) throws Exception {
                PLVCommonLog.exception(throwable);
            }
        });
    }

    private void observePlaybackCacheUpdate() {
        Disposable disposable = this.playbackCacheUpdateDisposable;
        if (disposable != null) {
            disposable.dispose();
        }
        this.playbackCacheUpdateDisposable = this.playbackCacheRepo.playbackCacheVideoUpdateObservable.subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).retry().subscribe(new Consumer<PLVPlaybackCacheVideoVO>() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.PLVPlaybackCacheVideoViewModel.1
            @Override // io.reactivex.functions.Consumer
            public void accept(PLVPlaybackCacheVideoVO videoVO) throws Exception {
                if (videoVO == null || videoVO.getVideoPoolId() == null || !videoVO.getVideoPoolId().equals(PLVPlaybackCacheVideoViewModel.this.playbackCacheVideoConfig.getVideoPoolId())) {
                    return;
                }
                PLVPlaybackCacheVideoViewModel.this.playbackCacheUpdateLiveData.postValue(videoVO);
            }
        }, new Consumer<Throwable>() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.PLVPlaybackCacheVideoViewModel.2
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable throwable) throws Exception {
                PLVCommonLog.exception(throwable);
            }
        });
    }

    public void deleteDownload(PLVPlaybackCacheVideoVO vo) {
        this.playbackCacheRepo.deleteDownloadVideo(appendViewerInfo(vo));
    }

    public LiveData<PLVPlaybackCacheVideoVO> getPlaybackCacheUpdateLiveData() {
        return this.playbackCacheUpdateLiveData;
    }

    public void pauseDownload(PLVPlaybackCacheVideoVO vo) {
        this.playbackCacheRepo.pauseDownloadVideo(appendViewerInfo(vo));
    }

    public void startDownload(PLVPlaybackCacheVideoVO vo) {
        this.playbackCacheRepo.startDownloadVideo(appendViewerInfo(vo));
    }

    public void updatePlaybackVideoInfo(PLVPlaybackDataVO playbackDataVO) {
        this.playbackCacheVideoConfig.setPlaybackListType(playbackDataVO.getPlaybackListType());
        if (playbackDataVO.getPlaybackListType() == PLVPlaybackListType.TEMP_STORE) {
            this.playbackCacheVideoConfig.setVideoPoolId(playbackDataVO.getFileId());
        } else {
            this.playbackCacheVideoConfig.setVideoPoolIdByVid(playbackDataVO.getVideoPoolId());
        }
        initPlaybackCache();
    }
}
