package com.easefun.polyv.livecommon.module.modules.player.playback.model;

import androidx.annotation.NonNull;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.PLVPlaybackCacheDatabaseDataSource;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.PLVPlaybackCacheLocalStorageDataSource;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.PLVPlaybackCacheMemoryDataSource;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.PLVPlaybackCacheNetworkDataSource;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.entity.PLVPlaybackCacheVideoVO;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.enums.PLVPlaybackCacheDownloadStatusEnum;
import com.hjq.permissions.Permission;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.permission.PLVFastPermission;
import com.plv.foundationsdk.utils.PLVAppUtils;
import com.plv.livescenes.playback.video.PLVPlaybackListType;
import io.reactivex.Emitter;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVPlaybackCacheRepo {
    private static volatile PLVPlaybackCacheRepo INSTANCE = null;
    private static final String TAG = "PLVPlaybackCacheRepo";
    private Emitter<PLVPlaybackCacheVideoVO> cacheVideoUpdateEmitter;
    private final PLVPlaybackCacheDatabaseDataSource databaseDataSource;
    private Disposable localStorageCacheUpdateDisposable;
    private final PLVPlaybackCacheLocalStorageDataSource localStorageDataSource;
    private Disposable localStorageValidateDisposable;
    private PLVPlaybackCacheMemoryDataSource memoryDataSource;
    private PLVPlaybackCacheNetworkDataSource networkDataSource;
    public final Observable<PLVPlaybackCacheVideoVO> playbackCacheVideoUpdateObservable = Observable.create(new ObservableOnSubscribe<PLVPlaybackCacheVideoVO>() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.model.PLVPlaybackCacheRepo.6
        @Override // io.reactivex.ObservableOnSubscribe
        public void subscribe(@NonNull ObservableEmitter<PLVPlaybackCacheVideoVO> emitter) throws Exception {
            PLVPlaybackCacheRepo.this.cacheVideoUpdateEmitter = emitter;
        }
    }).publish().autoConnect();

    private PLVPlaybackCacheRepo(final PLVPlaybackCacheDatabaseDataSource databaseDataSource, final PLVPlaybackCacheLocalStorageDataSource localStorageDataSource) {
        this.databaseDataSource = databaseDataSource;
        this.localStorageDataSource = localStorageDataSource;
        init();
    }

    public static PLVPlaybackCacheRepo getInstance(final PLVPlaybackCacheDatabaseDataSource databaseDataSource, final PLVPlaybackCacheLocalStorageDataSource localStorageDataSource, final PLVPlaybackCacheMemoryDataSource memoryDataSource, final PLVPlaybackCacheNetworkDataSource networkDataSource) {
        if (INSTANCE == null) {
            synchronized (PLVPlaybackCacheRepo.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PLVPlaybackCacheRepo(databaseDataSource, localStorageDataSource);
                }
            }
        }
        INSTANCE.memoryDataSource = memoryDataSource;
        INSTANCE.networkDataSource = networkDataSource;
        return INSTANCE;
    }

    private boolean hasPermission() {
        return PLVFastPermission.hasPermission(PLVAppUtils.getApp(), Permission.WRITE_EXTERNAL_STORAGE);
    }

    private void init() {
        observeLocalStorageCacheUpdate();
        validateLocalStorageDownloadContent();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyCacheVideoUpdate(PLVPlaybackCacheVideoVO vo) {
        Emitter<PLVPlaybackCacheVideoVO> emitter = this.cacheVideoUpdateEmitter;
        if (emitter != null) {
            emitter.onNext(vo);
        }
    }

    private void observeLocalStorageCacheUpdate() {
        Disposable disposable = this.localStorageCacheUpdateDisposable;
        if (disposable != null) {
            disposable.dispose();
        }
        this.localStorageCacheUpdateDisposable = this.localStorageDataSource.playbackCacheUpdateObservable.subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).retry().subscribe(new Consumer<PLVPlaybackCacheVideoVO>() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.model.PLVPlaybackCacheRepo.1
            @Override // io.reactivex.functions.Consumer
            public void accept(PLVPlaybackCacheVideoVO videoVO) throws Exception {
                PLVPlaybackCacheRepo.this.databaseDataSource.updateCacheVideo(videoVO);
                PLVPlaybackCacheRepo.this.memoryDataSource.putCacheVideo(videoVO);
                PLVPlaybackCacheRepo.this.notifyCacheVideoUpdate(videoVO.copy());
            }
        }, new Consumer<Throwable>() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.model.PLVPlaybackCacheRepo.2
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable throwable) throws Exception {
                PLVCommonLog.exception(throwable);
            }
        });
    }

    private void validateLocalStorageDownloadContent() {
        Disposable disposable = this.localStorageValidateDisposable;
        if (disposable != null) {
            disposable.dispose();
        }
        if (hasPermission()) {
            this.localStorageValidateDisposable = listCacheVideos().observeOn(Schedulers.computation()).flatMap(new Function<List<PLVPlaybackCacheVideoVO>, ObservableSource<PLVPlaybackCacheVideoVO>>() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.model.PLVPlaybackCacheRepo.5
                @Override // io.reactivex.functions.Function
                public ObservableSource<PLVPlaybackCacheVideoVO> apply(@NonNull List<PLVPlaybackCacheVideoVO> vos) throws Exception {
                    return Observable.fromIterable(vos);
                }
            }).subscribe(new Consumer<PLVPlaybackCacheVideoVO>() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.model.PLVPlaybackCacheRepo.3
                @Override // io.reactivex.functions.Consumer
                public void accept(PLVPlaybackCacheVideoVO vo) throws Exception {
                    if (vo.getDownloadStatusEnum() == PLVPlaybackCacheDownloadStatusEnum.DOWNLOADED && !PLVPlaybackCacheRepo.this.localStorageDataSource.checkDownloadedVideoExist(vo)) {
                        PLVCommonLog.w(PLVPlaybackCacheRepo.TAG, "delete downloaded video because local content not exist, id: " + vo.getVideoPoolId());
                        PLVPlaybackCacheRepo.this.deleteDownloadVideo(vo);
                    }
                    if (vo.getDownloadStatusEnum() != PLVPlaybackCacheDownloadStatusEnum.DOWNLOADING || PLVPlaybackCacheRepo.this.localStorageDataSource.isDownloading(vo)) {
                        return;
                    }
                    PLVCommonLog.i(PLVPlaybackCacheRepo.TAG, "pause downloading video because status not consistent, id: " + vo.getVideoPoolId());
                    PLVPlaybackCacheRepo.this.pauseDownloadVideo(vo);
                }
            }, new Consumer<Throwable>() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.model.PLVPlaybackCacheRepo.4
                @Override // io.reactivex.functions.Consumer
                public void accept(Throwable throwable) throws Exception {
                    PLVCommonLog.exception(throwable);
                }
            });
        }
    }

    public void deleteDownloadVideo(PLVPlaybackCacheVideoVO vo) {
        if (hasPermission()) {
            PLVPlaybackCacheVideoVO pLVPlaybackCacheVideoVOCopy = vo.copy();
            pLVPlaybackCacheVideoVOCopy.clearDownloadStatus();
            this.localStorageDataSource.deleteDownloadVideo(pLVPlaybackCacheVideoVOCopy);
            this.databaseDataSource.deleteCacheVideo(pLVPlaybackCacheVideoVOCopy);
            this.memoryDataSource.removeCacheVideo(pLVPlaybackCacheVideoVOCopy);
            notifyCacheVideoUpdate(pLVPlaybackCacheVideoVOCopy);
        }
    }

    public Observable<PLVPlaybackCacheVideoVO> getCacheVideoById(final String channelId, final String videoPoolId, final PLVPlaybackListType playbackListType) {
        return Observable.just(1).observeOn(Schedulers.io()).flatMap(new Function<Integer, ObservableSource<? extends PLVPlaybackCacheVideoVO>>() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.model.PLVPlaybackCacheRepo.7
            @Override // io.reactivex.functions.Function
            public ObservableSource<? extends PLVPlaybackCacheVideoVO> apply(@NonNull Integer integer) throws Exception {
                PLVPlaybackCacheVideoVO cacheVideoById = PLVPlaybackCacheRepo.this.memoryDataSource.getCacheVideoById(videoPoolId);
                if (cacheVideoById != null) {
                    return Observable.just(cacheVideoById);
                }
                PLVPlaybackCacheVideoVO cacheVideoById2 = PLVPlaybackCacheRepo.this.databaseDataSource.getCacheVideoById(videoPoolId);
                if (cacheVideoById2 != null) {
                    PLVPlaybackCacheRepo.this.memoryDataSource.putCacheVideo(cacheVideoById2);
                    if (cacheVideoById2.getDownloadStatusEnum() != PLVPlaybackCacheDownloadStatusEnum.NOT_IN_DOWNLOAD_LIST) {
                        return Observable.just(cacheVideoById2);
                    }
                }
                return PLVPlaybackCacheRepo.this.networkDataSource.getPlaybackCacheVideoData(channelId, videoPoolId, playbackListType).subscribeOn(Schedulers.io()).doOnNext(new Consumer<PLVPlaybackCacheVideoVO>() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.model.PLVPlaybackCacheRepo.7.1
                    @Override // io.reactivex.functions.Consumer
                    public void accept(PLVPlaybackCacheVideoVO videoVO) throws Exception {
                        videoVO.setDownloadStatusEnum(PLVPlaybackCacheDownloadStatusEnum.NOT_IN_DOWNLOAD_LIST);
                        PLVPlaybackCacheRepo.this.databaseDataSource.insertCacheVideo(videoVO);
                        PLVPlaybackCacheRepo.this.memoryDataSource.putCacheVideo(videoVO);
                    }
                });
            }
        });
    }

    public Observable<List<PLVPlaybackCacheVideoVO>> listCacheVideos() {
        return this.databaseDataSource.listCacheVideos().subscribeOn(Schedulers.io()).doOnNext(new Consumer<List<PLVPlaybackCacheVideoVO>>() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.model.PLVPlaybackCacheRepo.8
            @Override // io.reactivex.functions.Consumer
            public void accept(List<PLVPlaybackCacheVideoVO> playbackCacheVideoVOS) throws Exception {
                if (playbackCacheVideoVOS != null) {
                    PLVPlaybackCacheRepo.this.memoryDataSource.putCacheVideos(playbackCacheVideoVOS);
                }
            }
        });
    }

    public void pauseDownloadVideo(PLVPlaybackCacheVideoVO vo) {
        if (hasPermission()) {
            PLVPlaybackCacheVideoVO pLVPlaybackCacheVideoVOCopy = vo.copy();
            pLVPlaybackCacheVideoVOCopy.setDownloadStatusEnum(PLVPlaybackCacheDownloadStatusEnum.PAUSING);
            this.localStorageDataSource.pauseDownloadVideo(pLVPlaybackCacheVideoVOCopy);
            this.databaseDataSource.updateCacheVideo(pLVPlaybackCacheVideoVOCopy);
            this.memoryDataSource.putCacheVideo(pLVPlaybackCacheVideoVOCopy);
            notifyCacheVideoUpdate(pLVPlaybackCacheVideoVOCopy);
        }
    }

    public void startDownloadVideo(PLVPlaybackCacheVideoVO vo) {
        if (hasPermission()) {
            PLVPlaybackCacheVideoVO pLVPlaybackCacheVideoVOCopy = vo.copy();
            pLVPlaybackCacheVideoVOCopy.setDownloadStatusEnum(PLVPlaybackCacheDownloadStatusEnum.WAITING);
            this.localStorageDataSource.startDownloadVideo(pLVPlaybackCacheVideoVOCopy);
            this.databaseDataSource.insertCacheVideo(pLVPlaybackCacheVideoVOCopy);
            this.memoryDataSource.putCacheVideo(pLVPlaybackCacheVideoVOCopy);
            notifyCacheVideoUpdate(pLVPlaybackCacheVideoVOCopy);
        }
    }
}
