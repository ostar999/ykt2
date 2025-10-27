package com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.config.PLVPlaybackCacheConfig;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.entity.PLVPlaybackCacheVideoVO;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.enums.PLVPlaybackCacheDownloadStatusEnum;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.livescenes.download.IPLVDownloader;
import com.plv.livescenes.download.IPLVDownloaderListener;
import com.plv.livescenes.download.PLVDownloader;
import com.plv.livescenes.download.PLVDownloaderManager;
import com.plv.livescenes.download.PLVPlaybackCacheVO;
import com.plv.livescenes.download.listener.IPLVDownloaderBeforeStartListener;
import com.plv.livescenes.download.listener.IPLVDownloaderStopListener;
import com.plv.thirdpart.blankj.utilcode.util.FileUtils;
import io.reactivex.Emitter;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes3.dex */
public class PLVPlaybackCacheLocalStorageDataSource {
    private static volatile PLVPlaybackCacheLocalStorageDataSource INSTANCE = null;
    private static final String TAG = "PLVPlaybackCacheLocalStorageDataSource";
    private final PLVDownloaderManager downloaderManager;
    private PLVPlaybackCacheConfig playbackCacheConfig;
    private Emitter<PLVPlaybackCacheVideoVO> playbackCacheUpdateEmitter;
    private final Map<String, PLVPlaybackCacheVideoVO> downloaderKeyToVideoMap = new ConcurrentHashMap();
    public final Observable<PLVPlaybackCacheVideoVO> playbackCacheUpdateObservable = Observable.create(new ObservableOnSubscribe<PLVPlaybackCacheVideoVO>() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.PLVPlaybackCacheLocalStorageDataSource.1
        @Override // io.reactivex.ObservableOnSubscribe
        public void subscribe(@NonNull ObservableEmitter<PLVPlaybackCacheVideoVO> emitter) throws Exception {
            PLVPlaybackCacheLocalStorageDataSource.this.playbackCacheUpdateEmitter = emitter;
        }
    });

    private PLVPlaybackCacheLocalStorageDataSource(final PLVDownloaderManager downloaderManager) {
        this.downloaderManager = downloaderManager;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void fillDataToVideoVO(@Nullable final PLVPlaybackCacheVideoVO videoVO, @Nullable final PLVPlaybackCacheVO playbackCacheVO) {
        if (videoVO == null || playbackCacheVO == null) {
            return;
        }
        String videoPath = playbackCacheVO.getVideoPath();
        String pptDir = playbackCacheVO.getPptDir();
        String jsPath = playbackCacheVO.getJsPath();
        String title = playbackCacheVO.getTitle();
        String duration = playbackCacheVO.getDuration();
        Long videoSize = playbackCacheVO.getVideoSize();
        String firstImage = playbackCacheVO.getFirstImage();
        if (videoPath != null) {
            videoVO.setVideoPath(videoPath);
        }
        if (pptDir != null) {
            videoVO.setPptPath(pptDir);
        }
        if (jsPath != null) {
            videoVO.setJsPath(jsPath);
        }
        if (title != null) {
            videoVO.setTitle(title);
        }
        if (duration != null) {
            videoVO.setVideoDuration(duration);
        }
        if (videoSize != null) {
            videoVO.setTotalBytes(videoSize);
        }
        if (firstImage != null) {
            videoVO.setFirstImageUrl(firstImage);
        }
    }

    private PLVDownloader getDownloader(@NonNull final PLVPlaybackCacheVideoVO videoVO) {
        return this.downloaderManager.addDownloader(new IPLVDownloader.Builder(videoVO.getVideoPoolId(), videoVO.getViewerInfoVO().getChannelId()).setPlaybackListType(videoVO.getViewerInfoVO().getPlaybackListType()).downloadDir(this.playbackCacheConfig.getDownloadRootDirectory()));
    }

    public static PLVPlaybackCacheLocalStorageDataSource getInstance(final PLVDownloaderManager downloaderManager, final PLVPlaybackCacheConfig playbackCacheConfig) {
        if (INSTANCE == null) {
            synchronized (PLVPlaybackCacheLocalStorageDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PLVPlaybackCacheLocalStorageDataSource(downloaderManager);
                }
            }
        }
        INSTANCE.playbackCacheConfig = playbackCacheConfig;
        return INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyPlaybackCacheUpdate(PLVPlaybackCacheVideoVO videoVO) {
        Emitter<PLVPlaybackCacheVideoVO> emitter = this.playbackCacheUpdateEmitter;
        if (emitter != null) {
            emitter.onNext(videoVO);
        }
    }

    private void setupDownloaderListener(final PLVDownloader downloader) {
        downloader.setDownloadBeforeStartListener(new IPLVDownloaderBeforeStartListener<PLVPlaybackCacheVO>() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.PLVPlaybackCacheLocalStorageDataSource.2
            @Override // com.plv.livescenes.download.listener.IPLVDownloaderBeforeStartListener
            public void onBeforeStart(IPLVDownloader ignored, final PLVPlaybackCacheVO playbackCacheVO) {
                PLVPlaybackCacheVideoVO pLVPlaybackCacheVideoVO = (PLVPlaybackCacheVideoVO) PLVPlaybackCacheLocalStorageDataSource.this.downloaderKeyToVideoMap.get(downloader.getKey());
                PLVPlaybackCacheLocalStorageDataSource.fillDataToVideoVO(pLVPlaybackCacheVideoVO, playbackCacheVO);
                if (pLVPlaybackCacheVideoVO != null) {
                    pLVPlaybackCacheVideoVO.setDownloadStatusEnum(PLVPlaybackCacheDownloadStatusEnum.DOWNLOADING);
                    PLVPlaybackCacheLocalStorageDataSource.this.notifyPlaybackCacheUpdate(pLVPlaybackCacheVideoVO.copy());
                }
            }
        });
        downloader.setDownloadListener(new IPLVDownloaderListener() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.PLVPlaybackCacheLocalStorageDataSource.3
            @Override // com.plv.livescenes.download.IPLVDownloaderListener
            public void onFailure(int errorReason) {
                PLVCommonLog.e(PLVPlaybackCacheLocalStorageDataSource.TAG, "downloader onFailure errorReason = " + errorReason);
                PLVPlaybackCacheVideoVO pLVPlaybackCacheVideoVO = (PLVPlaybackCacheVideoVO) PLVPlaybackCacheLocalStorageDataSource.this.downloaderKeyToVideoMap.get(downloader.getKey());
                if (pLVPlaybackCacheVideoVO != null) {
                    pLVPlaybackCacheVideoVO.setDownloadStatusEnum(PLVPlaybackCacheDownloadStatusEnum.DOWNLOAD_FAIL);
                    PLVPlaybackCacheLocalStorageDataSource.this.notifyPlaybackCacheUpdate(pLVPlaybackCacheVideoVO.copy());
                }
            }

            @Override // com.plv.livescenes.download.IPLVDownloaderListener
            public void onProgress(long current, long total) {
                PLVPlaybackCacheVideoVO pLVPlaybackCacheVideoVO = (PLVPlaybackCacheVideoVO) PLVPlaybackCacheLocalStorageDataSource.this.downloaderKeyToVideoMap.get(downloader.getKey());
                if (pLVPlaybackCacheVideoVO != null) {
                    pLVPlaybackCacheVideoVO.setDownloadStatusEnum(PLVPlaybackCacheDownloadStatusEnum.DOWNLOADING);
                    float f2 = current / total;
                    pLVPlaybackCacheVideoVO.setProgress(Integer.valueOf((int) (100.0f * f2)));
                    if (pLVPlaybackCacheVideoVO.getTotalBytes() != null) {
                        pLVPlaybackCacheVideoVO.setDownloadedBytes(Long.valueOf((long) (pLVPlaybackCacheVideoVO.getTotalBytes().longValue() * f2)));
                    }
                    PLVPlaybackCacheLocalStorageDataSource.this.notifyPlaybackCacheUpdate(pLVPlaybackCacheVideoVO.copy());
                }
            }

            @Override // com.plv.livescenes.download.IPLVDownloaderListener
            public void onSuccess(PLVPlaybackCacheVO playbackCacheVO) {
                PLVPlaybackCacheVideoVO pLVPlaybackCacheVideoVO = (PLVPlaybackCacheVideoVO) PLVPlaybackCacheLocalStorageDataSource.this.downloaderKeyToVideoMap.get(downloader.getKey());
                if (pLVPlaybackCacheVideoVO != null) {
                    PLVPlaybackCacheLocalStorageDataSource.fillDataToVideoVO(pLVPlaybackCacheVideoVO, playbackCacheVO);
                    pLVPlaybackCacheVideoVO.setProgress(100);
                    pLVPlaybackCacheVideoVO.setDownloadStatusEnum(PLVPlaybackCacheDownloadStatusEnum.DOWNLOADED);
                    PLVPlaybackCacheLocalStorageDataSource.this.notifyPlaybackCacheUpdate(pLVPlaybackCacheVideoVO.copy());
                }
            }
        });
        downloader.setDownloadStopListener(new IPLVDownloaderStopListener() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.PLVPlaybackCacheLocalStorageDataSource.4
            @Override // com.plv.livescenes.download.listener.IPLVDownloaderStopListener
            public void onStop() {
                PLVPlaybackCacheVideoVO pLVPlaybackCacheVideoVO = (PLVPlaybackCacheVideoVO) PLVPlaybackCacheLocalStorageDataSource.this.downloaderKeyToVideoMap.get(downloader.getKey());
                if (pLVPlaybackCacheVideoVO != null) {
                    pLVPlaybackCacheVideoVO.setDownloadStatusEnum(PLVPlaybackCacheDownloadStatusEnum.PAUSING);
                    PLVPlaybackCacheLocalStorageDataSource.this.notifyPlaybackCacheUpdate(pLVPlaybackCacheVideoVO.copy());
                }
            }
        });
    }

    public boolean checkDownloadedVideoExist(@NonNull final PLVPlaybackCacheVideoVO vo) {
        String videoPath = vo.getVideoPath();
        if (videoPath != null && !FileUtils.isFileExists(videoPath)) {
            return false;
        }
        String pptPath = vo.getPptPath();
        if (pptPath != null && !FileUtils.isFileExists(pptPath)) {
            return false;
        }
        String jsPath = vo.getJsPath();
        return jsPath == null || FileUtils.isFileExists(jsPath);
    }

    public void deleteDownloadVideo(@NonNull final PLVPlaybackCacheVideoVO videoVO) {
        PLVDownloader downloader = getDownloader(videoVO);
        if (downloader == null) {
            return;
        }
        this.downloaderManager.removeDownloader(downloader.getKey());
        this.downloaderKeyToVideoMap.remove(downloader.getKey());
        downloader.deleteDownloadContent();
    }

    public boolean isDownloading(@NonNull final PLVPlaybackCacheVideoVO vo) {
        return getDownloader(vo).isDownloading();
    }

    public void pauseDownloadVideo(@NonNull final PLVPlaybackCacheVideoVO videoVO) {
        PLVDownloader downloader = getDownloader(videoVO);
        if (downloader == null) {
            return;
        }
        this.downloaderManager.removeDownloader(downloader.getKey());
        PLVPlaybackCacheVideoVO pLVPlaybackCacheVideoVO = this.downloaderKeyToVideoMap.get(downloader.getKey());
        if (pLVPlaybackCacheVideoVO != null) {
            pLVPlaybackCacheVideoVO.setDownloadStatusEnum(PLVPlaybackCacheDownloadStatusEnum.PAUSING);
            notifyPlaybackCacheUpdate(pLVPlaybackCacheVideoVO.copy());
        }
    }

    public void startDownloadVideo(@NonNull final PLVPlaybackCacheVideoVO videoVO) {
        PLVPlaybackCacheVideoVO pLVPlaybackCacheVideoVOCopy = videoVO.copy();
        PLVDownloader downloader = getDownloader(pLVPlaybackCacheVideoVOCopy);
        this.downloaderKeyToVideoMap.put(downloader.getKey(), pLVPlaybackCacheVideoVOCopy);
        setupDownloaderListener(downloader);
        pLVPlaybackCacheVideoVOCopy.setDownloadStatusEnum(PLVPlaybackCacheDownloadStatusEnum.WAITING);
        notifyPlaybackCacheUpdate(pLVPlaybackCacheVideoVOCopy.copy());
        this.downloaderManager.startDownload(downloader);
    }
}
