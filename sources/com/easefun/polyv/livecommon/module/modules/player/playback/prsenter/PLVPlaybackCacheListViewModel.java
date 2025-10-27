package com.easefun.polyv.livecommon.module.modules.player.playback.prsenter;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.PLVPlaybackCacheRepo;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.entity.PLVPlaybackCacheVideoVO;
import com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.usecase.PLVPlaybackCacheListMergeUseCase;
import com.plv.foundationsdk.component.livedata.Event;
import com.plv.foundationsdk.log.PLVCommonLog;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVPlaybackCacheListViewModel {
    private static volatile PLVPlaybackCacheListViewModel INSTANCE;
    private final PLVPlaybackCacheListMergeUseCase listMergeUseCase;
    private Disposable loadCacheListDisposable;
    private final PLVPlaybackCacheRepo playbackCacheRepo;
    private Disposable playbackCacheVideoUpdateDisposable;
    private final MutableLiveData<List<PLVPlaybackCacheVideoVO>> downloadingListLiveData = new MutableLiveData<>();
    private final List<PLVPlaybackCacheVideoVO> downloadingList = new ArrayList();
    private final MutableLiveData<List<PLVPlaybackCacheVideoVO>> downloadedListLiveData = new MutableLiveData<>();
    private final List<PLVPlaybackCacheVideoVO> downloadedList = new ArrayList();
    private final MutableLiveData<Event<PLVPlaybackCacheVideoVO>> onRequestLaunchDownloadedPlaybackLiveData = new MutableLiveData<>();

    private PLVPlaybackCacheListViewModel(final PLVPlaybackCacheRepo playbackCacheRepo, final PLVPlaybackCacheListMergeUseCase listMergeUseCase) {
        this.playbackCacheRepo = playbackCacheRepo;
        this.listMergeUseCase = listMergeUseCase;
        init();
    }

    public static PLVPlaybackCacheListViewModel getInstance(final PLVPlaybackCacheRepo playbackCacheRepo, final PLVPlaybackCacheListMergeUseCase listMergeUseCase) {
        if (INSTANCE == null) {
            synchronized (PLVPlaybackCacheListViewModel.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PLVPlaybackCacheListViewModel(playbackCacheRepo, listMergeUseCase);
                }
            }
        }
        return INSTANCE;
    }

    private void init() {
        observePlaybackCacheUpdate();
        initLoadPlaybackCacheList();
    }

    private void initLoadPlaybackCacheList() {
        Disposable disposable = this.loadCacheListDisposable;
        if (disposable != null) {
            disposable.dispose();
        }
        this.loadCacheListDisposable = this.playbackCacheRepo.listCacheVideos().subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).subscribe(new Consumer<List<PLVPlaybackCacheVideoVO>>() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.PLVPlaybackCacheListViewModel.3
            @Override // io.reactivex.functions.Consumer
            public void accept(List<PLVPlaybackCacheVideoVO> vos) throws Exception {
                for (PLVPlaybackCacheVideoVO pLVPlaybackCacheVideoVO : vos) {
                    PLVPlaybackCacheListViewModel.this.listMergeUseCase.reduceDownloadingList(PLVPlaybackCacheListViewModel.this.downloadingList, pLVPlaybackCacheVideoVO);
                    PLVPlaybackCacheListViewModel.this.listMergeUseCase.reduceDownloadedList(PLVPlaybackCacheListViewModel.this.downloadedList, pLVPlaybackCacheVideoVO);
                }
                PLVPlaybackCacheListViewModel.this.downloadingListLiveData.postValue(new ArrayList(PLVPlaybackCacheListViewModel.this.downloadingList));
                PLVPlaybackCacheListViewModel.this.downloadedListLiveData.postValue(new ArrayList(PLVPlaybackCacheListViewModel.this.downloadedList));
            }
        }, new Consumer<Throwable>() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.PLVPlaybackCacheListViewModel.4
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable throwable) throws Exception {
                PLVCommonLog.exception(throwable);
            }
        });
    }

    private void observePlaybackCacheUpdate() {
        Disposable disposable = this.playbackCacheVideoUpdateDisposable;
        if (disposable != null) {
            disposable.dispose();
        }
        this.playbackCacheVideoUpdateDisposable = this.playbackCacheRepo.playbackCacheVideoUpdateObservable.subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).retry().subscribe(new Consumer<PLVPlaybackCacheVideoVO>() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.PLVPlaybackCacheListViewModel.1
            @Override // io.reactivex.functions.Consumer
            public void accept(PLVPlaybackCacheVideoVO videoVO) throws Exception {
                boolean zReduceDownloadingList = PLVPlaybackCacheListViewModel.this.listMergeUseCase.reduceDownloadingList(PLVPlaybackCacheListViewModel.this.downloadingList, videoVO);
                boolean zReduceDownloadedList = PLVPlaybackCacheListViewModel.this.listMergeUseCase.reduceDownloadedList(PLVPlaybackCacheListViewModel.this.downloadedList, videoVO);
                if (zReduceDownloadingList) {
                    PLVPlaybackCacheListViewModel.this.downloadingListLiveData.postValue(new ArrayList(PLVPlaybackCacheListViewModel.this.downloadingList));
                }
                if (zReduceDownloadedList) {
                    PLVPlaybackCacheListViewModel.this.downloadedListLiveData.postValue(new ArrayList(PLVPlaybackCacheListViewModel.this.downloadedList));
                }
            }
        }, new Consumer<Throwable>() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.PLVPlaybackCacheListViewModel.2
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable throwable) throws Exception {
                PLVCommonLog.exception(throwable);
            }
        });
    }

    public void deleteDownload(PLVPlaybackCacheVideoVO vo) {
        this.playbackCacheRepo.deleteDownloadVideo(vo);
    }

    public LiveData<List<PLVPlaybackCacheVideoVO>> getDownloadedListLiveData() {
        return this.downloadedListLiveData;
    }

    public LiveData<List<PLVPlaybackCacheVideoVO>> getDownloadingListLiveData() {
        return this.downloadingListLiveData;
    }

    public LiveData<Event<PLVPlaybackCacheVideoVO>> getOnRequestLaunchDownloadedPlaybackLiveData() {
        return this.onRequestLaunchDownloadedPlaybackLiveData;
    }

    public void pauseDownload(PLVPlaybackCacheVideoVO vo) {
        this.playbackCacheRepo.pauseDownloadVideo(vo);
    }

    public void requestLaunchDownloadedPlayback(PLVPlaybackCacheVideoVO vo) {
        this.onRequestLaunchDownloadedPlaybackLiveData.postValue(new Event<>(vo));
    }

    public void startDownload(PLVPlaybackCacheVideoVO vo) {
        this.playbackCacheRepo.startDownloadVideo(vo);
    }
}
