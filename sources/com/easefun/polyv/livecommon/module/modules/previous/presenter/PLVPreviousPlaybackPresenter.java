package com.easefun.polyv.livecommon.module.modules.previous.presenter;

import androidx.annotation.NonNull;
import com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager;
import com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.data.PLVPlayInfoVO;
import com.easefun.polyv.livecommon.module.modules.previous.contract.IPLVPreviousPlaybackContract;
import com.easefun.polyv.livecommon.module.modules.previous.presenter.data.PLVPreviousData;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.livescenes.model.PLVPlaybackListVO;
import com.plv.livescenes.previous.PLVPreviousManager;
import com.plv.livescenes.previous.model.PLVChapterDataVO;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVPreviousPlaybackPresenter implements IPLVPreviousPlaybackContract.IPreviousPlaybackPresenter {
    private static final int DEFAULT_PAGE_SIZE = 50;
    private Disposable chapterListDisposable;
    private final IPLVLiveRoomDataManager mLiveRoomDataManager;
    private Disposable mPreviousListDisposable;
    private List<IPLVPreviousPlaybackContract.IPreviousPlaybackView> mPreviousViews;
    private int mCurrentPage = 1;
    private final List<PLVPlaybackListVO.DataBean.ContentsBean> mPlaybackList = new ArrayList();
    private final PLVPreviousData mPlvPreviousData = new PLVPreviousData();

    public interface ViewRunnable {
        void run(@NonNull IPLVPreviousPlaybackContract.IPreviousPlaybackView view);
    }

    public PLVPreviousPlaybackPresenter(IPLVLiveRoomDataManager liveRoomDataManager) {
        this.mLiveRoomDataManager = liveRoomDataManager;
        init();
    }

    public static /* synthetic */ int access$210(PLVPreviousPlaybackPresenter pLVPreviousPlaybackPresenter) {
        int i2 = pLVPreviousPlaybackPresenter.mCurrentPage;
        pLVPreviousPlaybackPresenter.mCurrentPage = i2 - 1;
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void callbackToView(ViewRunnable runnable) {
        List<IPLVPreviousPlaybackContract.IPreviousPlaybackView> list = this.mPreviousViews;
        if (list != null) {
            for (IPLVPreviousPlaybackContract.IPreviousPlaybackView iPreviousPlaybackView : list) {
                if (iPreviousPlaybackView != null && runnable != null) {
                    runnable.run(iPreviousPlaybackView);
                }
            }
        }
    }

    private void commonRequestChaptersList(String channelId, String videoId, Consumer<List<PLVChapterDataVO>> successCallback, Consumer<Throwable> failCallback) {
        Disposable disposable = this.chapterListDisposable;
        if (disposable != null) {
            disposable.dispose();
        }
        this.chapterListDisposable = PLVPreviousManager.getInstance().getPLVChatApi().getChapterList(channelId, videoId).subscribe(successCallback, failCallback);
    }

    private void commonRequestPreviousList(String channelId, int mCurrentPage, Consumer<PLVPlaybackListVO> successCallback, Consumer<Throwable> failCallback) {
        Disposable disposable = this.mPreviousListDisposable;
        if (disposable != null) {
            disposable.dispose();
        }
        this.mPreviousListDisposable = PLVPreviousManager.getInstance().getPLVChatApi().getPlaybackList(channelId, mCurrentPage, 50, this.mLiveRoomDataManager.getConfig().getVideoListType()).subscribe(successCallback, failCallback);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.previous.contract.IPLVPreviousPlaybackContract.IPreviousPlaybackPresenter
    public void changePlaybackVideoSeek(int position) {
        this.mPlvPreviousData.getPlayBackVidoSeekData().postValue(Integer.valueOf(position));
    }

    @Override // com.easefun.polyv.livecommon.module.modules.previous.contract.IPLVPreviousPlaybackContract.IPreviousPlaybackPresenter
    public void changePlaybackVideoVid(String vid) {
        this.mPlvPreviousData.getPlaybackVideoVidData().postValue(vid);
        this.mPlvPreviousData.update(this.mPlaybackList, vid);
        requestChapterDetail();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.previous.contract.IPLVPreviousPlaybackContract.IPreviousPlaybackPresenter
    public PLVPreviousData getData() {
        return this.mPlvPreviousData;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.previous.contract.IPLVPreviousPlaybackContract.IPreviousPlaybackPresenter
    public void init() {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.previous.contract.IPLVPreviousPlaybackContract.IPreviousPlaybackPresenter
    public void onDestroy() {
        Disposable disposable = this.mPreviousListDisposable;
        if (disposable != null) {
            disposable.dispose();
        }
        Disposable disposable2 = this.mPreviousListDisposable;
        if (disposable2 != null) {
            disposable2.dispose();
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.previous.contract.IPLVPreviousPlaybackContract.IPreviousPlaybackPresenter
    public void onPlayComplete() {
        if (this.mPreviousViews != null) {
            callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.previous.presenter.PLVPreviousPlaybackPresenter.8
                @Override // com.easefun.polyv.livecommon.module.modules.previous.presenter.PLVPreviousPlaybackPresenter.ViewRunnable
                public void run(@NonNull IPLVPreviousPlaybackContract.IPreviousPlaybackView view) {
                    view.onPlayComplete();
                }
            });
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.previous.contract.IPLVPreviousPlaybackContract.IPreviousPlaybackPresenter
    public void onSeekChange(final int position) {
        callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.previous.presenter.PLVPreviousPlaybackPresenter.9
            @Override // com.easefun.polyv.livecommon.module.modules.previous.presenter.PLVPreviousPlaybackPresenter.ViewRunnable
            public void run(@NonNull IPLVPreviousPlaybackContract.IPreviousPlaybackView view) {
                view.onSeekChange(position);
            }
        });
    }

    @Override // com.easefun.polyv.livecommon.module.modules.previous.contract.IPLVPreviousPlaybackContract.IPreviousPlaybackPresenter
    public void registerView(IPLVPreviousPlaybackContract.IPreviousPlaybackView view) {
        if (this.mPreviousViews == null) {
            this.mPreviousViews = new ArrayList();
        }
        if (!this.mPreviousViews.contains(view)) {
            this.mPreviousViews.add(view);
        }
        view.setPresenter(this);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.previous.contract.IPLVPreviousPlaybackContract.IPreviousPlaybackPresenter
    public void requestChapterDetail() {
        String channelId = this.mLiveRoomDataManager.getConfig().getChannelId();
        PLVPlaybackListVO.DataBean.ContentsBean previousDetail = this.mPlvPreviousData.getPreviousDetail();
        Consumer<List<PLVChapterDataVO>> consumer = new Consumer<List<PLVChapterDataVO>>() { // from class: com.easefun.polyv.livecommon.module.modules.previous.presenter.PLVPreviousPlaybackPresenter.3
            @Override // io.reactivex.functions.Consumer
            public void accept(final List<PLVChapterDataVO> dataVOS) throws Exception {
                PLVPreviousPlaybackPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.previous.presenter.PLVPreviousPlaybackPresenter.3.1
                    @Override // com.easefun.polyv.livecommon.module.modules.previous.presenter.PLVPreviousPlaybackPresenter.ViewRunnable
                    public void run(@NonNull IPLVPreviousPlaybackContract.IPreviousPlaybackView view) {
                        view.updateChapterList(dataVOS);
                    }
                });
            }
        };
        Consumer<Throwable> consumer2 = new Consumer<Throwable>() { // from class: com.easefun.polyv.livecommon.module.modules.previous.presenter.PLVPreviousPlaybackPresenter.4
            @Override // io.reactivex.functions.Consumer
            public void accept(final Throwable throwable) throws Exception {
                PLVPreviousPlaybackPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.previous.presenter.PLVPreviousPlaybackPresenter.4.1
                    @Override // com.easefun.polyv.livecommon.module.modules.previous.presenter.PLVPreviousPlaybackPresenter.ViewRunnable
                    public void run(@NonNull IPLVPreviousPlaybackContract.IPreviousPlaybackView view) {
                        PLVCommonLog.exception(throwable);
                        view.requestChapterError();
                    }
                });
            }
        };
        if (previousDetail != null) {
            commonRequestChaptersList(channelId, previousDetail.getVideoId(), consumer, consumer2);
        } else {
            callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.previous.presenter.PLVPreviousPlaybackPresenter.5
                @Override // com.easefun.polyv.livecommon.module.modules.previous.presenter.PLVPreviousPlaybackPresenter.ViewRunnable
                public void run(@NonNull IPLVPreviousPlaybackContract.IPreviousPlaybackView view) {
                    view.requestChapterError();
                }
            });
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.previous.contract.IPLVPreviousPlaybackContract.IPreviousPlaybackPresenter
    public void requestLoadMorePreviousVideo() {
        this.mCurrentPage++;
        commonRequestPreviousList(this.mLiveRoomDataManager.getConfig().getChannelId(), this.mCurrentPage, new Consumer<PLVPlaybackListVO>() { // from class: com.easefun.polyv.livecommon.module.modules.previous.presenter.PLVPreviousPlaybackPresenter.6
            @Override // io.reactivex.functions.Consumer
            public void accept(final PLVPlaybackListVO listVO) throws Exception {
                if (listVO.getData() != null) {
                    final List<PLVPlaybackListVO.DataBean.ContentsBean> contents = listVO.getData().getContents();
                    PLVPreviousPlaybackPresenter.this.mPlaybackList.addAll(contents);
                    PLVPreviousPlaybackPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.previous.presenter.PLVPreviousPlaybackPresenter.6.1
                        @Override // com.easefun.polyv.livecommon.module.modules.previous.presenter.PLVPreviousPlaybackPresenter.ViewRunnable
                        public void run(@NonNull IPLVPreviousPlaybackContract.IPreviousPlaybackView view) {
                            if (contents.size() >= 50) {
                                view.previousLoadModreData(listVO);
                            } else if (contents.size() <= 0) {
                                view.previousNoMoreData();
                            } else {
                                view.previousLoadModreData(listVO);
                                view.previousNoMoreData();
                            }
                        }
                    });
                }
            }
        }, new Consumer<Throwable>() { // from class: com.easefun.polyv.livecommon.module.modules.previous.presenter.PLVPreviousPlaybackPresenter.7
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable throwable) throws Exception {
                PLVCommonLog.exception(throwable);
                PLVPreviousPlaybackPresenter.access$210(PLVPreviousPlaybackPresenter.this);
                PLVPreviousPlaybackPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.previous.presenter.PLVPreviousPlaybackPresenter.7.1
                    @Override // com.easefun.polyv.livecommon.module.modules.previous.presenter.PLVPreviousPlaybackPresenter.ViewRunnable
                    public void run(@NonNull IPLVPreviousPlaybackContract.IPreviousPlaybackView view) {
                        view.previousLoadMoreError();
                    }
                });
            }
        });
    }

    @Override // com.easefun.polyv.livecommon.module.modules.previous.contract.IPLVPreviousPlaybackContract.IPreviousPlaybackPresenter
    public void requestPreviousList() {
        Disposable disposable = this.mPreviousListDisposable;
        if (disposable != null) {
            disposable.dispose();
        }
        commonRequestPreviousList(this.mLiveRoomDataManager.getConfig().getChannelId(), this.mCurrentPage, new Consumer<PLVPlaybackListVO>() { // from class: com.easefun.polyv.livecommon.module.modules.previous.presenter.PLVPreviousPlaybackPresenter.1
            @Override // io.reactivex.functions.Consumer
            public void accept(final PLVPlaybackListVO listVO) throws Exception {
                PLVPreviousPlaybackPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.previous.presenter.PLVPreviousPlaybackPresenter.1.1
                    @Override // com.easefun.polyv.livecommon.module.modules.previous.presenter.PLVPreviousPlaybackPresenter.ViewRunnable
                    public void run(@NonNull IPLVPreviousPlaybackContract.IPreviousPlaybackView view) {
                        if (listVO.getData() == null) {
                            view.requestPreviousError();
                            return;
                        }
                        if (listVO.getData().getContents().size() <= 0) {
                            view.previousNoMoreData();
                            return;
                        }
                        PLVPreviousPlaybackPresenter.this.mPlaybackList.addAll(listVO.getData().getContents());
                        view.updatePreviousVideoList(listVO);
                        if (listVO.getData().getContents().size() < 50) {
                            view.previousNoMoreData();
                        }
                    }
                });
            }
        }, new Consumer<Throwable>() { // from class: com.easefun.polyv.livecommon.module.modules.previous.presenter.PLVPreviousPlaybackPresenter.2
            @Override // io.reactivex.functions.Consumer
            public void accept(final Throwable throwable) throws Exception {
                PLVPreviousPlaybackPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.previous.presenter.PLVPreviousPlaybackPresenter.2.1
                    @Override // com.easefun.polyv.livecommon.module.modules.previous.presenter.PLVPreviousPlaybackPresenter.ViewRunnable
                    public void run(@NonNull IPLVPreviousPlaybackContract.IPreviousPlaybackView view) {
                        PLVCommonLog.exception(throwable);
                        view.requestPreviousError();
                    }
                });
            }
        });
    }

    @Override // com.easefun.polyv.livecommon.module.modules.previous.contract.IPLVPreviousPlaybackContract.IPreviousPlaybackPresenter
    public void unregisterView(IPLVPreviousPlaybackContract.IPreviousPlaybackView view) {
        List<IPLVPreviousPlaybackContract.IPreviousPlaybackView> list = this.mPreviousViews;
        if (list != null) {
            list.remove(view);
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.previous.contract.IPLVPreviousPlaybackContract.IPreviousPlaybackPresenter
    public void updatePlaybackCurrentPosition(PLVPlayInfoVO playInfoVO) {
        onSeekChange(playInfoVO.getPosition() / 1000);
        if (playInfoVO.getTotalTime() <= 0 || playInfoVO.getPosition() < playInfoVO.getTotalTime()) {
            return;
        }
        onPlayComplete();
    }
}
