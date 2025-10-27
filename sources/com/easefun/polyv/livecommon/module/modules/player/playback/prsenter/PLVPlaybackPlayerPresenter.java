package com.easefun.polyv.livecommon.module.modules.player.playback.prsenter;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.easefun.polyv.businesssdk.api.auxiliary.IPolyvAuxiliaryVideoViewListenerEvent;
import com.easefun.polyv.businesssdk.api.auxiliary.PolyvAuxiliaryVideoview;
import com.easefun.polyv.businesssdk.api.common.player.PolyvPlayError;
import com.easefun.polyv.businesssdk.api.common.player.listener.IPolyvVideoViewListenerEvent;
import com.easefun.polyv.businesssdk.api.common.ppt.IPolyvPPTView;
import com.easefun.polyv.businesssdk.model.video.PolyvLiveMarqueeVO;
import com.easefun.polyv.livecommon.module.config.PLVLiveChannelConfig;
import com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager;
import com.easefun.polyv.livecommon.module.modules.marquee.IPLVMarqueeView;
import com.easefun.polyv.livecommon.module.modules.marquee.PLVMarqueeCommonController;
import com.easefun.polyv.livecommon.module.modules.marquee.model.PLVMarqueeModel;
import com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.PLVPlaybackPlayerRepo;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.entity.PLVPlaybackCacheVideoVO;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.enums.PLVPlaybackCacheDownloadStatusEnum;
import com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.data.PLVPlayInfoVO;
import com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.data.PLVPlaybackPlayerData;
import com.easefun.polyv.livecommon.module.modules.watermark.IPLVWatermarkView;
import com.easefun.polyv.livecommon.module.modules.watermark.PLVWatermarkCommonController;
import com.easefun.polyv.livecommon.module.modules.watermark.PLVWatermarkTextVO;
import com.easefun.polyv.livecommon.module.utils.PLVWebUtils;
import com.easefun.polyv.livecommon.module.utils.imageloader.PLVImageLoader;
import com.easefun.polyv.livecommon.ui.widget.PLVPlayerLogoView;
import com.easefun.polyv.livecommon.ui.widget.PLVPlayerRetryLayout;
import com.easefun.polyv.livescenes.playback.video.PolyvPlaybackVideoView;
import com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent;
import com.plv.business.model.video.PLVBaseVideoParams;
import com.plv.business.model.video.PLVPlaybackVideoParams;
import com.plv.business.model.video.PLVWatermarkVO;
import com.plv.foundationsdk.component.di.PLVDependManager;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.utils.PLVControlUtils;
import com.plv.foundationsdk.utils.PLVTimeUnit;
import com.plv.livescenes.model.PLVPlaybackVO;
import com.plv.livescenes.playback.video.api.IPLVPlaybackListenerEvent;
import com.plv.livescenes.playback.vo.PLVPlaybackDataVO;
import com.plv.livescenes.playback.vo.PLVPlaybackLocalCacheVO;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVPlaybackPlayerPresenter implements IPLVPlaybackPlayerContract.IPlaybackPlayerPresenter {
    private static final boolean AUTO_CONTINUE_PLAY = true;
    private static final String TAG = "PLVPlaybackPlayerPresen";
    private static final int WHAT_PLAY_PROGRESS = 1;
    private int fastForwardPos;
    private IPLVLiveRoomDataManager liveRoomDataManager;
    private PLVPlayerLogoView logoView;
    private IPLVVideoViewListenerEvent.OnGestureClickListener onSubGestureClickListener;

    @Nullable
    private PLVPlaybackDataVO playbackDataVO;
    private PolyvAuxiliaryVideoview subVideoView;
    private WeakReference<IPLVPlaybackPlayerContract.IPlaybackPlayerView> vWeakReference;
    private PolyvPlaybackVideoView videoView;
    private final PLVPlaybackPlayerRepo playbackPlayerRepo = new PLVPlaybackPlayerRepo();
    private final PLVPlaybackCacheVideoViewModel playbackCacheVideoViewModel = (PLVPlaybackCacheVideoViewModel) PLVDependManager.getInstance().get(PLVPlaybackCacheVideoViewModel.class);
    private final PLVPlaybackCacheListViewModel playbackCacheListViewModel = (PLVPlaybackCacheListViewModel) PLVDependManager.getInstance().get(PLVPlaybackCacheListViewModel.class);
    private String subVideoViewHerf = "";
    private boolean isAllowOpenAdHead = false;
    private boolean isAllowMarqueeRunning = true;
    private boolean isAllowWatermarkShow = true;
    private Handler selfHandler = new Handler(Looper.getMainLooper()) { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.PLVPlaybackPlayerPresenter.27
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                PLVPlaybackPlayerPresenter.this.startPlayProgressTimer();
            }
        }
    };
    private PLVPlaybackPlayerData playbackPlayerData = new PLVPlaybackPlayerData();

    public PLVPlaybackPlayerPresenter(@NonNull IPLVLiveRoomDataManager liveRoomDataManager) {
        this.liveRoomDataManager = liveRoomDataManager;
    }

    public static /* synthetic */ int access$1412(PLVPlaybackPlayerPresenter pLVPlaybackPlayerPresenter, int i2) {
        int i3 = pLVPlaybackPlayerPresenter.fastForwardPos + i2;
        pLVPlaybackPlayerPresenter.fastForwardPos = i3;
        return i3;
    }

    public static /* synthetic */ int access$1420(PLVPlaybackPlayerPresenter pLVPlaybackPlayerPresenter, int i2) {
        int i3 = pLVPlaybackPlayerPresenter.fastForwardPos - i2;
        pLVPlaybackPlayerPresenter.fastForwardPos = i3;
        return i3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkAutoContinuePlay() {
        PLVPlaybackDataVO pLVPlaybackDataVO;
        PLVPlayInfoVO playbackProgress;
        if (this.videoView == null || (pLVPlaybackDataVO = this.playbackDataVO) == null || (playbackProgress = this.playbackPlayerRepo.getPlaybackProgress(pLVPlaybackDataVO)) == null || playbackProgress.getTotalTime() <= 0 || playbackProgress.getPosition() <= 0 || playbackProgress.getPosition() < PLVTimeUnit.seconds(2L).toMillis() || playbackProgress.getPosition() > playbackProgress.getTotalTime() - PLVTimeUnit.seconds(2L).toMillis()) {
            return;
        }
        int position = playbackProgress.getPosition();
        PLVCommonLog.i(TAG, "Auto continue play, seek to: " + position);
        this.videoView.seekTo(position);
        if (getView() != null) {
            getView().onAutoContinuePlaySeeked(position);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public PLVLiveChannelConfig getConfig() {
        return this.liveRoomDataManager.getConfig();
    }

    private List<PLVPlaybackLocalCacheVO> getLocalCacheVideoList() {
        List<PLVPlaybackCacheVideoVO> value = this.playbackCacheListViewModel.getDownloadedListLiveData().getValue();
        if (value == null || value.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        for (PLVPlaybackCacheVideoVO pLVPlaybackCacheVideoVO : value) {
            if (pLVPlaybackCacheVideoVO.getDownloadStatusEnum() == PLVPlaybackCacheDownloadStatusEnum.DOWNLOADED) {
                arrayList.add(new PLVPlaybackLocalCacheVO().setVideoPoolId(pLVPlaybackCacheVideoVO.getVideoPoolId()).setVideoId(pLVPlaybackCacheVideoVO.getVideoId()).setChannelId(pLVPlaybackCacheVideoVO.getViewerInfoVO().getChannelId()).setPlaybackListType(pLVPlaybackCacheVideoVO.getViewerInfoVO().getPlaybackListType()).setLiveType(pLVPlaybackCacheVideoVO.getLiveType()).setChannelSessionId(pLVPlaybackCacheVideoVO.getChannelSessionId()).setOriginSessionId(pLVPlaybackCacheVideoVO.getOriginSessionId()).setVideoPath(pLVPlaybackCacheVideoVO.getVideoPath()).setJsPath(pLVPlaybackCacheVideoVO.getJsPath()).setPptPath(pLVPlaybackCacheVideoVO.getPptPath()));
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public IPLVPlaybackPlayerContract.IPlaybackPlayerView getView() {
        WeakReference<IPLVPlaybackPlayerContract.IPlaybackPlayerView> weakReference = this.vWeakReference;
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }

    private void initSubVideoViewListener() {
        PolyvAuxiliaryVideoview polyvAuxiliaryVideoview = this.subVideoView;
        if (polyvAuxiliaryVideoview != null) {
            polyvAuxiliaryVideoview.setOnVideoPlayListener(new IPLVVideoViewListenerEvent.OnVideoPlayListener() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.PLVPlaybackPlayerPresenter.1
                @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent.OnVideoPlayListener
                public void onPlay(boolean isFirst) {
                    IPLVPlaybackPlayerContract.IPlaybackPlayerView view = PLVPlaybackPlayerPresenter.this.getView();
                    if (view != null) {
                        view.onSubVideoViewPlay(isFirst);
                    }
                }
            });
            IPLVVideoViewListenerEvent.OnGestureClickListener onGestureClickListener = new IPLVVideoViewListenerEvent.OnGestureClickListener() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.PLVPlaybackPlayerPresenter.2
                @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent.OnGestureClickListener
                public void callback(boolean start, boolean end) {
                    if (TextUtils.isEmpty(PLVPlaybackPlayerPresenter.this.subVideoViewHerf)) {
                        return;
                    }
                    PLVWebUtils.openWebLink(PLVPlaybackPlayerPresenter.this.subVideoViewHerf, PLVPlaybackPlayerPresenter.this.subVideoView.getContext());
                }
            };
            this.onSubGestureClickListener = onGestureClickListener;
            this.subVideoView.setOnGestureClickListener(onGestureClickListener);
            this.subVideoView.setOnSubVideoViewLoadImage(new IPolyvAuxiliaryVideoViewListenerEvent.IPolyvOnSubVideoViewLoadImage() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.PLVPlaybackPlayerPresenter.3
                @Override // com.plv.business.api.auxiliary.IPLVAuxiliaryVideoViewListenerEvent.IPLVOnSubVideoViewLoadImage
                public void onLoad(String imageUrl, final ImageView imageView, final String coverHref) {
                    if (!TextUtils.isEmpty(coverHref)) {
                        PLVImageLoader.getInstance().loadImage(PLVPlaybackPlayerPresenter.this.subVideoView.getContext(), imageUrl, imageView);
                    }
                    PLVPlaybackPlayerPresenter.this.subVideoViewHerf = coverHref;
                    if (TextUtils.isEmpty(coverHref)) {
                        return;
                    }
                    imageView.setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.PLVPlaybackPlayerPresenter.3.1
                        @Override // android.view.View.OnClickListener
                        public void onClick(View v2) {
                            PLVWebUtils.openWebLink(coverHref, PLVPlaybackPlayerPresenter.this.subVideoView.getContext());
                        }
                    });
                }
            });
            this.subVideoView.setOnSubVideoViewCountdownListener(new IPolyvAuxiliaryVideoViewListenerEvent.IPolyvOnSubVideoViewCountdownListener() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.PLVPlaybackPlayerPresenter.4
                @Override // com.plv.business.api.auxiliary.IPLVAuxiliaryVideoViewListenerEvent.IPLVOnSubVideoViewCountdownListener
                public void onCountdown(int totalTime, int remainTime, int adStage) {
                    boolean z2 = PLVPlaybackPlayerPresenter.this.subVideoView != null && PLVPlaybackPlayerPresenter.this.subVideoView.isOpenHeadAd();
                    IPLVPlaybackPlayerContract.IPlaybackPlayerView view = PLVPlaybackPlayerPresenter.this.getView();
                    if (view != null) {
                        view.onSubVideoViewCountDown(z2, totalTime, remainTime, adStage);
                    }
                }

                @Override // com.plv.business.api.auxiliary.IPLVAuxiliaryVideoViewListenerEvent.IPLVOnSubVideoViewCountdownListener
                public void onVisibilityChange(boolean isShow) {
                    boolean z2 = PLVPlaybackPlayerPresenter.this.subVideoView != null && PLVPlaybackPlayerPresenter.this.subVideoView.isOpenHeadAd();
                    IPLVPlaybackPlayerContract.IPlaybackPlayerView view = PLVPlaybackPlayerPresenter.this.getView();
                    if (view != null) {
                        view.onSubVideoViewVisiblityChanged(z2, isShow);
                    }
                }
            });
        }
    }

    private void initVideoViewListener() {
        PolyvPlaybackVideoView polyvPlaybackVideoView = this.videoView;
        if (polyvPlaybackVideoView != null) {
            polyvPlaybackVideoView.setOnPreparedListener(new IPLVVideoViewListenerEvent.OnPreparedListener() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.PLVPlaybackPlayerPresenter.5
                @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent.OnPreparedListener
                public void onPrepared() {
                    PLVPlaybackPlayerPresenter.this.playbackPlayerData.postPrepared();
                    IPLVPlaybackPlayerContract.IPlaybackPlayerView view = PLVPlaybackPlayerPresenter.this.getView();
                    if (view != null) {
                        view.onPrepared();
                    }
                    PLVPlaybackPlayerPresenter.this.setLogoVisibility(0);
                    PLVPlaybackPlayerPresenter.this.setRetryLayoutVisibility(8);
                    PLVPlaybackPlayerPresenter.this.setAllowMarqueeViewRunning(true);
                    PLVPlaybackPlayerPresenter.this.checkAutoContinuePlay();
                    PLVPlaybackPlayerPresenter.this.resetErrorViewStatus();
                }

                @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent.OnPreparedListener
                public void onPreparing() {
                    PLVCommonLog.d(PLVPlaybackPlayerPresenter.TAG, "onPreparing");
                }
            });
            this.videoView.setOnErrorListener(new IPolyvVideoViewListenerEvent.OnErrorListener() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.PLVPlaybackPlayerPresenter.6
                @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent.OnErrorListener
                public void onError(int what, int extra) {
                    PLVCommonLog.d(PLVPlaybackPlayerPresenter.TAG, "onError:" + what);
                }

                @Override // com.easefun.polyv.businesssdk.api.common.player.listener.IPolyvVideoViewListenerEvent.OnErrorListener
                public void onError(PolyvPlayError error) {
                    PLVPlaybackPlayerPresenter.this.setDefaultViewStatus();
                    int i2 = error.playStage;
                    String str = (i2 != 1 ? i2 != 2 ? i2 != 3 ? error.isMainStage() ? "主视频" : "" : "片尾广告" : "暖场视频" : "片头广告") + "播放异常\n" + error.errorDescribe + "(" + error.errorCode + "-" + error.playStage + ")\n" + error.playPath;
                    IPLVPlaybackPlayerContract.IPlaybackPlayerView view = PLVPlaybackPlayerPresenter.this.getView();
                    if (view != null) {
                        view.onPlayError(error, str);
                    }
                    PLVPlaybackPlayerPresenter.this.setLogoVisibility(8);
                    PLVPlaybackPlayerPresenter.this.setRetryLayoutVisibility(0);
                    PLVPlaybackPlayerPresenter.this.stopMarqueeView();
                    PLVPlaybackPlayerPresenter.this.removeWatermark();
                }
            });
            this.videoView.setOnVideoLoadSlowListener(new IPLVVideoViewListenerEvent.OnVideoLoadSlowListener() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.PLVPlaybackPlayerPresenter.7
                @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent.OnVideoLoadSlowListener
                public void onLoadSlow(int loadedTime, boolean isBufferEvent) {
                    IPLVPlaybackPlayerContract.IPlaybackPlayerView view = PLVPlaybackPlayerPresenter.this.getView();
                    if (view != null) {
                        view.onLoadSlow(loadedTime, isBufferEvent);
                    }
                }
            });
            this.videoView.setOnCompletionListener(new IPLVVideoViewListenerEvent.OnCompletionListener() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.PLVPlaybackPlayerPresenter.8
                @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent.OnCompletionListener
                public void onCompletion() {
                    IPLVPlaybackPlayerContract.IPlaybackPlayerView view = PLVPlaybackPlayerPresenter.this.getView();
                    if (view != null) {
                        view.onCompletion();
                    }
                    PLVPlaybackPlayerPresenter.this.stopMarqueeView();
                    PLVPlaybackPlayerPresenter.this.removeWatermark();
                }
            });
            this.videoView.setOnSeekCompleteListener(new IPLVVideoViewListenerEvent.OnSeekCompleteListener() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.PLVPlaybackPlayerPresenter.9
                @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent.OnSeekCompleteListener
                public void onSeekComplete() {
                    PLVPlaybackPlayerPresenter.this.playbackPlayerData.postSeekComplete(PLVPlaybackPlayerPresenter.this.videoView.getCurrentPosition());
                }
            });
            this.videoView.setOnInfoListener(new IPLVVideoViewListenerEvent.OnInfoListener() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.PLVPlaybackPlayerPresenter.10
                @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent.OnInfoListener
                public void onInfo(int what, int extra) {
                    if (what == 701) {
                        IPLVPlaybackPlayerContract.IPlaybackPlayerView view = PLVPlaybackPlayerPresenter.this.getView();
                        if (view != null) {
                            view.onBufferStart();
                            return;
                        }
                        return;
                    }
                    if (what == 702) {
                        IPLVPlaybackPlayerContract.IPlaybackPlayerView view2 = PLVPlaybackPlayerPresenter.this.getView();
                        if (view2 != null) {
                            view2.onBufferEnd();
                        }
                        PLVPlaybackPlayerPresenter.this.resetErrorViewStatus();
                    }
                }
            });
            this.videoView.setOnVideoPlayListener(new IPLVVideoViewListenerEvent.OnVideoPlayListener() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.PLVPlaybackPlayerPresenter.11
                @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent.OnVideoPlayListener
                public void onPlay(boolean isFirst) {
                    IPLVPlaybackPlayerContract.IPlaybackPlayerView view = PLVPlaybackPlayerPresenter.this.getView();
                    if (view != null) {
                        view.onVideoPlay(isFirst);
                    }
                    PLVPlaybackPlayerPresenter.this.setAllowMarqueeViewRunning(true);
                    PLVPlaybackPlayerPresenter.this.removeWatermark();
                    PLVPlaybackPlayerPresenter.this.showWatermarkView(true);
                }
            });
            this.videoView.setOnVideoPauseListener(new IPLVVideoViewListenerEvent.OnVideoPauseListener() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.PLVPlaybackPlayerPresenter.12
                @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent.OnVideoPauseListener
                public void onPause() {
                    IPLVPlaybackPlayerContract.IPlaybackPlayerView view = PLVPlaybackPlayerPresenter.this.getView();
                    if (view != null) {
                        view.onVideoPause();
                    }
                    PLVPlaybackPlayerPresenter.this.setAllowMarqueeViewRunning(false);
                }
            });
            this.videoView.setOnGestureLeftDownListener(new IPLVVideoViewListenerEvent.OnGestureLeftDownListener() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.PLVPlaybackPlayerPresenter.13
                @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent.OnGestureLeftDownListener
                public void callback(boolean start, boolean end) {
                    int iMax = Math.max(0, PLVPlaybackPlayerPresenter.this.videoView.getBrightness((Activity) PLVPlaybackPlayerPresenter.this.videoView.getContext()) - 8);
                    IPLVPlaybackPlayerContract.IPlaybackPlayerView view = PLVPlaybackPlayerPresenter.this.getView();
                    if (view != null) {
                        boolean zOnLightChanged = view.onLightChanged(iMax, end);
                        if (start && zOnLightChanged) {
                            PLVPlaybackPlayerPresenter.this.videoView.setBrightness((Activity) PLVPlaybackPlayerPresenter.this.videoView.getContext(), iMax);
                        }
                    }
                }
            });
            this.videoView.setOnGestureLeftUpListener(new IPLVVideoViewListenerEvent.OnGestureLeftUpListener() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.PLVPlaybackPlayerPresenter.14
                @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent.OnGestureLeftUpListener
                public void callback(boolean start, boolean end) {
                    int iMin = Math.min(100, PLVPlaybackPlayerPresenter.this.videoView.getBrightness((Activity) PLVPlaybackPlayerPresenter.this.videoView.getContext()) + 8);
                    IPLVPlaybackPlayerContract.IPlaybackPlayerView view = PLVPlaybackPlayerPresenter.this.getView();
                    if (view != null) {
                        boolean zOnLightChanged = view.onLightChanged(iMin, end);
                        if (start && zOnLightChanged) {
                            PLVPlaybackPlayerPresenter.this.videoView.setBrightness((Activity) PLVPlaybackPlayerPresenter.this.videoView.getContext(), iMin);
                        }
                    }
                }
            });
            this.videoView.setOnGestureRightDownListener(new IPLVVideoViewListenerEvent.OnGestureRightDownListener() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.PLVPlaybackPlayerPresenter.15
                @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent.OnGestureRightDownListener
                public void callback(boolean start, boolean end) {
                    int iMax = Math.max(0, PLVPlaybackPlayerPresenter.this.videoView.getVolume() - PLVControlUtils.getVolumeValidProgress(PLVPlaybackPlayerPresenter.this.videoView.getContext(), 8));
                    IPLVPlaybackPlayerContract.IPlaybackPlayerView view = PLVPlaybackPlayerPresenter.this.getView();
                    if (view != null) {
                        boolean zOnVolumeChanged = view.onVolumeChanged(iMax, end);
                        if (start && zOnVolumeChanged) {
                            PLVPlaybackPlayerPresenter.this.videoView.setVolume(iMax);
                        }
                    }
                }
            });
            this.videoView.setOnGestureRightUpListener(new IPLVVideoViewListenerEvent.OnGestureRightUpListener() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.PLVPlaybackPlayerPresenter.16
                @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent.OnGestureRightUpListener
                public void callback(boolean start, boolean end) {
                    int iMin = Math.min(100, PLVPlaybackPlayerPresenter.this.videoView.getVolume() + PLVControlUtils.getVolumeValidProgress(PLVPlaybackPlayerPresenter.this.videoView.getContext(), 8));
                    IPLVPlaybackPlayerContract.IPlaybackPlayerView view = PLVPlaybackPlayerPresenter.this.getView();
                    if (view != null) {
                        boolean zOnVolumeChanged = view.onVolumeChanged(iMin, end);
                        if (start && zOnVolumeChanged) {
                            PLVPlaybackPlayerPresenter.this.videoView.setVolume(iMin);
                        }
                    }
                }
            });
            this.videoView.setOnGestureDoubleClickListener(new IPLVVideoViewListenerEvent.OnGestureDoubleClickListener() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.PLVPlaybackPlayerPresenter.17
                @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent.OnGestureDoubleClickListener
                public void callback() {
                    IPLVPlaybackPlayerContract.IPlaybackPlayerView view;
                    if (!PLVPlaybackPlayerPresenter.this.videoView.isInPlaybackStateEx() || (view = PLVPlaybackPlayerPresenter.this.getView()) == null) {
                        return;
                    }
                    view.onDoubleClick();
                }
            });
            this.videoView.setOnGestureSwipeLeftListener(new IPLVVideoViewListenerEvent.OnGestureSwipeLeftListener() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.PLVPlaybackPlayerPresenter.18
                @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent.OnGestureSwipeLeftListener
                public void callback(boolean start, boolean end, int times) {
                    IPLVPlaybackPlayerContract.IPlaybackPlayerView view = PLVPlaybackPlayerPresenter.this.getView();
                    if (!PLVPlaybackPlayerPresenter.this.videoView.isInPlaybackStateEx()) {
                        if (end) {
                            PLVPlaybackPlayerPresenter.this.fastForwardPos = 0;
                            if (view != null) {
                                view.onProgressChanged(PLVPlaybackPlayerPresenter.this.fastForwardPos, PLVPlaybackPlayerPresenter.this.videoView.getDuration(), end, false);
                                return;
                            }
                            return;
                        }
                        return;
                    }
                    if (PLVPlaybackPlayerPresenter.this.fastForwardPos == 0) {
                        PLVPlaybackPlayerPresenter pLVPlaybackPlayerPresenter = PLVPlaybackPlayerPresenter.this;
                        pLVPlaybackPlayerPresenter.fastForwardPos = pLVPlaybackPlayerPresenter.videoView.getCurrentPosition();
                    }
                    PLVPlaybackPlayerPresenter.access$1420(PLVPlaybackPlayerPresenter.this, times * 1000);
                    if (PLVPlaybackPlayerPresenter.this.fastForwardPos <= 0) {
                        PLVPlaybackPlayerPresenter.this.fastForwardPos = -1;
                    }
                    if (!end) {
                        if (view != null) {
                            view.onProgressChanged(PLVPlaybackPlayerPresenter.this.fastForwardPos, PLVPlaybackPlayerPresenter.this.videoView.getDuration(), end, false);
                            return;
                        }
                        return;
                    }
                    PLVPlaybackPlayerPresenter pLVPlaybackPlayerPresenter2 = PLVPlaybackPlayerPresenter.this;
                    pLVPlaybackPlayerPresenter2.fastForwardPos = Math.max(0, pLVPlaybackPlayerPresenter2.fastForwardPos);
                    if (view != null && view.onProgressChanged(PLVPlaybackPlayerPresenter.this.fastForwardPos, PLVPlaybackPlayerPresenter.this.videoView.getDuration(), end, false)) {
                        PLVPlaybackPlayerPresenter.this.videoView.seekTo(PLVPlaybackPlayerPresenter.this.fastForwardPos);
                        if (PLVPlaybackPlayerPresenter.this.videoView.isCompletedState()) {
                            PLVPlaybackPlayerPresenter.this.videoView.start();
                        }
                    }
                    PLVPlaybackPlayerPresenter.this.fastForwardPos = 0;
                }
            });
            this.videoView.setOnGestureSwipeRightListener(new IPLVVideoViewListenerEvent.OnGestureSwipeRightListener() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.PLVPlaybackPlayerPresenter.19
                @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent.OnGestureSwipeRightListener
                public void callback(boolean start, boolean end, int times) {
                    IPLVPlaybackPlayerContract.IPlaybackPlayerView view = PLVPlaybackPlayerPresenter.this.getView();
                    if (!PLVPlaybackPlayerPresenter.this.videoView.isInPlaybackStateEx()) {
                        if (end) {
                            PLVPlaybackPlayerPresenter.this.fastForwardPos = 0;
                            if (view != null) {
                                view.onProgressChanged(PLVPlaybackPlayerPresenter.this.fastForwardPos, PLVPlaybackPlayerPresenter.this.videoView.getDuration(), end, true);
                                return;
                            }
                            return;
                        }
                        return;
                    }
                    if (PLVPlaybackPlayerPresenter.this.fastForwardPos == 0) {
                        PLVPlaybackPlayerPresenter pLVPlaybackPlayerPresenter = PLVPlaybackPlayerPresenter.this;
                        pLVPlaybackPlayerPresenter.fastForwardPos = pLVPlaybackPlayerPresenter.videoView.getCurrentPosition();
                    }
                    PLVPlaybackPlayerPresenter.access$1412(PLVPlaybackPlayerPresenter.this, times * 1000);
                    if (PLVPlaybackPlayerPresenter.this.fastForwardPos > PLVPlaybackPlayerPresenter.this.videoView.getDuration()) {
                        PLVPlaybackPlayerPresenter pLVPlaybackPlayerPresenter2 = PLVPlaybackPlayerPresenter.this;
                        pLVPlaybackPlayerPresenter2.fastForwardPos = pLVPlaybackPlayerPresenter2.videoView.getDuration();
                    }
                    if (end) {
                        if (view != null && view.onProgressChanged(PLVPlaybackPlayerPresenter.this.fastForwardPos, PLVPlaybackPlayerPresenter.this.videoView.getDuration(), end, true)) {
                            if (!PLVPlaybackPlayerPresenter.this.videoView.isCompletedState()) {
                                PLVPlaybackPlayerPresenter.this.videoView.seekTo(PLVPlaybackPlayerPresenter.this.fastForwardPos);
                            } else if (PLVPlaybackPlayerPresenter.this.fastForwardPos < PLVPlaybackPlayerPresenter.this.videoView.getDuration()) {
                                PLVPlaybackPlayerPresenter.this.videoView.seekTo(PLVPlaybackPlayerPresenter.this.fastForwardPos);
                                PLVPlaybackPlayerPresenter.this.videoView.start();
                            }
                        }
                        PLVPlaybackPlayerPresenter.this.fastForwardPos = 0;
                    }
                    if (view != null) {
                        view.onProgressChanged(PLVPlaybackPlayerPresenter.this.fastForwardPos, PLVPlaybackPlayerPresenter.this.videoView.getDuration(), end, true);
                    }
                }
            });
            this.videoView.setOnGetMarqueeVoListener(new IPolyvVideoViewListenerEvent.OnGetMarqueeVoListener() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.PLVPlaybackPlayerPresenter.20
                @Override // com.easefun.polyv.businesssdk.api.common.player.listener.IPolyvVideoViewListenerEvent.OnGetMarqueeVoListener
                public void onGetMarqueeVo(PolyvLiveMarqueeVO marqueeVo) {
                    if (PLVPlaybackPlayerPresenter.this.isMarqueeExisted()) {
                        PLVMarqueeCommonController.getInstance().updateMarqueeView(marqueeVo, PLVPlaybackPlayerPresenter.this.getConfig().getUser().getViewerName(), new PLVMarqueeCommonController.IPLVMarqueeControllerCallback() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.PLVPlaybackPlayerPresenter.20.1
                            @Override // com.easefun.polyv.livecommon.module.modules.marquee.PLVMarqueeCommonController.IPLVMarqueeControllerCallback
                            public void onMarqueeModel(int controllerTip, PLVMarqueeModel marqueeModel) {
                                if (controllerTip == 0 || controllerTip == 1) {
                                    PLVPlaybackPlayerPresenter.this.isAllowMarqueeRunning = false;
                                    final Activity activity = (Activity) PLVPlaybackPlayerPresenter.this.videoView.getContext();
                                    activity.runOnUiThread(new Runnable() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.PLVPlaybackPlayerPresenter.20.1.1
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            String errorMessage = PLVMarqueeCommonController.getInstance().getErrorMessage();
                                            Activity activity2 = activity;
                                            if ("".equals(errorMessage)) {
                                                errorMessage = "跑马灯验证失败";
                                            }
                                            Toast.makeText(activity2, errorMessage, 0).show();
                                            activity.finish();
                                        }
                                    });
                                } else if (controllerTip == 2) {
                                    PLVPlaybackPlayerPresenter.this.isAllowMarqueeRunning = false;
                                    PLVPlaybackPlayerPresenter.this.stopMarqueeView();
                                } else {
                                    if (controllerTip != 3) {
                                        return;
                                    }
                                    PLVPlaybackPlayerPresenter.this.isAllowMarqueeRunning = true;
                                    PLVPlaybackPlayerPresenter.this.stopMarqueeView();
                                    PLVPlaybackPlayerPresenter.this.setMarqueeViewModel(marqueeModel);
                                }
                            }
                        });
                    }
                }
            });
            this.videoView.setOnGetWatermarkVOListener(new IPLVVideoViewListenerEvent.OnGetWatermarkVoListener() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.PLVPlaybackPlayerPresenter.21
                @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent.OnGetWatermarkVoListener
                public void onGetWatermarkVO(final PLVWatermarkVO waterMarkVO) {
                    PLVWatermarkCommonController.getInstance().updateWatermarkView(waterMarkVO, PLVPlaybackPlayerPresenter.this.getConfig().getUser().getViewerName());
                    if (PLVPlaybackPlayerPresenter.this.isWatermarkExisted()) {
                        if ("N".equals(waterMarkVO.watermarkRestrict)) {
                            PLVPlaybackPlayerPresenter.this.removeWatermark();
                        } else {
                            PLVPlaybackPlayerPresenter.this.setWatermarkTextVO(waterMarkVO);
                        }
                    }
                }
            });
            this.videoView.setOnDanmuServerOpenListener(new IPLVVideoViewListenerEvent.OnDanmuServerOpenListener() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.PLVPlaybackPlayerPresenter.22
                @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent.OnDanmuServerOpenListener
                public void onDanmuServerOpenListener(boolean isServerDanmuOpen) {
                    IPLVPlaybackPlayerContract.IPlaybackPlayerView view = PLVPlaybackPlayerPresenter.this.getView();
                    if (view != null) {
                        view.onServerDanmuOpen(isServerDanmuOpen);
                    }
                }
            });
            this.videoView.setOnPPTShowListener(new IPLVVideoViewListenerEvent.OnPPTShowListener() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.PLVPlaybackPlayerPresenter.23
                @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent.OnPPTShowListener
                public void showPPTView(int visible) {
                    PLVPlaybackPlayerPresenter.this.playbackPlayerData.postPPTShowState(visible == 0);
                    IPLVPlaybackPlayerContract.IPlaybackPlayerView view = PLVPlaybackPlayerPresenter.this.getView();
                    if (view != null) {
                        view.onShowPPTView(visible);
                    }
                }
            });
            this.videoView.setOnGetLogoListener(new IPLVVideoViewListenerEvent.OnGetLogoListener() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.PLVPlaybackPlayerPresenter.24
                @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent.OnGetLogoListener
                public void onLogo(String logoImage, int logoAlpha, int logoPosition, String logoHref) {
                    IPLVPlaybackPlayerContract.IPlaybackPlayerView view;
                    if (TextUtils.isEmpty(logoImage) || (view = PLVPlaybackPlayerPresenter.this.getView()) == null) {
                        return;
                    }
                    PLVPlaybackPlayerPresenter.this.logoView = view.getLogo();
                    if (PLVPlaybackPlayerPresenter.this.logoView != null) {
                        PLVPlaybackPlayerPresenter.this.logoView.removeAllLogo();
                        PLVPlaybackPlayerPresenter.this.logoView.addLogo(new PLVPlayerLogoView.LogoParam().setWidth(0.14f).setHeight(0.25f).setAlpha(logoAlpha).setOffsetX(0.03f).setOffsetY(0.06f).setPos(logoPosition).setResUrl(logoImage).setLogoHref(logoHref));
                    }
                }
            });
            this.videoView.setOnRetryListener(new IPLVPlaybackListenerEvent.OnRetryListener() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.PLVPlaybackPlayerPresenter.25
                @Override // com.plv.livescenes.playback.video.api.IPLVPlaybackListenerEvent.OnRetryListener
                public boolean onRetryFailed() {
                    if (PLVPlaybackPlayerPresenter.this.getView() == null || PLVPlaybackPlayerPresenter.this.getView().getRetryLayout() == null) {
                        return false;
                    }
                    ((PLVPlayerRetryLayout) PLVPlaybackPlayerPresenter.this.getView().getRetryLayout()).onRetryFailed("重试失败");
                    return false;
                }
            });
            this.videoView.setOnPlaybackDataReadyListener(new IPLVPlaybackListenerEvent.OnPlaybackDataReadyListener() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.PLVPlaybackPlayerPresenter.26
                @Override // com.plv.livescenes.playback.video.api.IPLVPlaybackListenerEvent.OnPlaybackDataReadyListener
                public void onPlaybackDataReady(PLVPlaybackDataVO playbackDataVO) {
                    if (playbackDataVO == null) {
                        return;
                    }
                    PLVPlaybackPlayerPresenter.this.playbackDataVO = playbackDataVO;
                    PLVPlaybackPlayerPresenter.this.playbackCacheVideoViewModel.updatePlaybackVideoInfo(playbackDataVO);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isMarqueeExisted() {
        return getView().getMarqueeView() != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isWatermarkExisted() {
        return getView().getWatermarkView() != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeWatermark() {
        IPLVWatermarkView watermarkView;
        IPLVPlaybackPlayerContract.IPlaybackPlayerView view = getView();
        if (view == null || (watermarkView = view.getWatermarkView()) == null) {
            return;
        }
        watermarkView.removeWatermark();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetErrorViewStatus() {
        IPLVPlaybackPlayerContract.IPlaybackPlayerView view = getView();
        if (view != null && view.getNoStreamIndicator() != null) {
            view.getNoStreamIndicator().setVisibility(8);
        }
        if (view == null || view.getPlayErrorIndicator() == null) {
            return;
        }
        view.getPlayErrorIndicator().setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setAllowMarqueeViewRunning(boolean allow) {
        IPLVMarqueeView marqueeView;
        if (this.isAllowMarqueeRunning && (marqueeView = getView().getMarqueeView()) != null) {
            if (allow) {
                marqueeView.start();
            } else {
                marqueeView.pause();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDefaultViewStatus() {
        this.videoView.removeRenderView();
        IPLVPlaybackPlayerContract.IPlaybackPlayerView view = getView();
        if (view == null || view.getBufferingIndicator() == null) {
            return;
        }
        view.getBufferingIndicator().setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setLogoVisibility(int visible) {
        PLVPlayerLogoView pLVPlayerLogoView = this.logoView;
        if (pLVPlayerLogoView != null) {
            pLVPlayerLogoView.setVisibility(visible);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setMarqueeViewModel(PLVMarqueeModel marqueeViewModel) {
        IPLVMarqueeView marqueeView = getView().getMarqueeView();
        if (marqueeView != null) {
            marqueeView.setPLVMarqueeModel(marqueeViewModel);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setRetryLayoutVisibility(int visible) {
        if (getView() == null || getView().getRetryLayout() == null) {
            return;
        }
        getView().getRetryLayout().setVisibility(visible);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setWatermarkTextVO(PLVWatermarkVO plvWatermarkVO) {
        IPLVWatermarkView watermarkView = getView().getWatermarkView();
        PLVWatermarkTextVO pLVWatermarkTextVO = new PLVWatermarkTextVO();
        String str = plvWatermarkVO.watermarkType;
        str.hashCode();
        if (str.equals("nickname")) {
            pLVWatermarkTextVO.setContent(getConfig().getUser().getViewerName()).setFontSize(plvWatermarkVO.watermarkFontSize).setFontAlpha(plvWatermarkVO.watermarkOpacity);
        } else if (str.equals("fixed")) {
            pLVWatermarkTextVO.setContent(plvWatermarkVO.watermarkContent).setFontSize(plvWatermarkVO.watermarkFontSize).setFontAlpha(plvWatermarkVO.watermarkOpacity);
        } else {
            PLVCommonLog.d(TAG, "设置水印失败，默认为空");
        }
        if (watermarkView != null) {
            watermarkView.setPLVWatermarkVO(pLVWatermarkTextVO);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showWatermarkView(boolean allow) {
        IPLVWatermarkView watermarkView;
        if (this.isAllowWatermarkShow && (watermarkView = getView().getWatermarkView()) != null) {
            if (allow) {
                watermarkView.showWatermark();
            } else {
                watermarkView.removeWatermark();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startPlayProgressTimer() {
        stopPlayProgressTimer();
        if (this.videoView == null) {
            this.selfHandler.sendEmptyMessageDelayed(1, 1000L);
            return;
        }
        PLVPlayInfoVO pLVPlayInfoVOUpdatePlayInfo = updatePlayInfo();
        if (this.playbackDataVO != null && this.videoView.isRealPlaying()) {
            this.playbackPlayerRepo.updatePlaybackProgress(this.playbackDataVO, pLVPlayInfoVOUpdatePlayInfo);
        }
        this.selfHandler.sendEmptyMessageDelayed(1, 1000 - (pLVPlayInfoVOUpdatePlayInfo.getPosition() % 1000));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopMarqueeView() {
        IPLVMarqueeView marqueeView = getView().getMarqueeView();
        if (marqueeView != null) {
            marqueeView.stop();
        }
    }

    private void stopPlayProgressTimer() {
        this.selfHandler.removeMessages(1);
    }

    private PLVPlayInfoVO updatePlayInfo() {
        int currentPosition = this.videoView.getCurrentPosition();
        int duration = (this.videoView.getDuration() / 1000) * 1000;
        if (this.videoView.isCompletedState() || currentPosition > duration) {
            currentPosition = duration;
        }
        PLVPlayInfoVO.Builder builderIsPlaying = new PLVPlayInfoVO.Builder().position(currentPosition).totalTime(duration).bufPercent(this.videoView.getBufferPercentage()).isPlaying(this.videoView.isPlaying());
        PolyvAuxiliaryVideoview polyvAuxiliaryVideoview = this.subVideoView;
        PLVPlayInfoVO pLVPlayInfoVOBuild = builderIsPlaying.isSubViewPlaying(polyvAuxiliaryVideoview != null && polyvAuxiliaryVideoview.isPlaying()).build();
        this.playbackPlayerData.postPlayInfoVO(pLVPlayInfoVOBuild);
        IPLVPlaybackPlayerContract.IPlaybackPlayerView view = getView();
        if (view != null) {
            view.updatePlayInfo(pLVPlayInfoVOBuild);
        }
        return pLVPlayInfoVOBuild;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerPresenter
    public void bindPPTView(IPolyvPPTView pptView) {
        PolyvPlaybackVideoView polyvPlaybackVideoView = this.videoView;
        if (polyvPlaybackVideoView != null) {
            polyvPlaybackVideoView.bindPPTView(pptView);
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerPresenter
    public void destroy() {
        stopMarqueeView();
        removeWatermark();
        unregisterView();
        PLVPlayerLogoView pLVPlayerLogoView = this.logoView;
        if (pLVPlayerLogoView != null) {
            pLVPlayerLogoView.removeAllViews();
            this.logoView = null;
        }
        PolyvAuxiliaryVideoview polyvAuxiliaryVideoview = this.subVideoView;
        if (polyvAuxiliaryVideoview != null) {
            polyvAuxiliaryVideoview.destroy();
            this.subVideoView = null;
        }
        PolyvPlaybackVideoView polyvPlaybackVideoView = this.videoView;
        if (polyvPlaybackVideoView != null) {
            polyvPlaybackVideoView.destroy();
            this.videoView = null;
        }
        stopPlayProgressTimer();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerPresenter
    @NonNull
    public PLVPlaybackPlayerData getData() {
        return this.playbackPlayerData;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerPresenter
    public int getDuration() {
        PolyvPlaybackVideoView polyvPlaybackVideoView = this.videoView;
        if (polyvPlaybackVideoView != null) {
            return polyvPlaybackVideoView.getDuration();
        }
        return 0;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerPresenter
    public String getSessionId() {
        PolyvPlaybackVideoView polyvPlaybackVideoView = this.videoView;
        if (polyvPlaybackVideoView == null || polyvPlaybackVideoView.getPlaybackData() == null) {
            return null;
        }
        return this.videoView.getPlaybackData().getChannelSessionId();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerPresenter
    public float getSpeed() {
        PolyvPlaybackVideoView polyvPlaybackVideoView = this.videoView;
        if (polyvPlaybackVideoView != null) {
            return polyvPlaybackVideoView.getSpeed();
        }
        return 0.0f;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerPresenter
    public String getSubVideoViewHerf() {
        return this.subVideoView.isShow() ? this.subVideoViewHerf : "";
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerPresenter
    public int getVideoCurrentPosition() {
        PolyvPlaybackVideoView polyvPlaybackVideoView = this.videoView;
        if (polyvPlaybackVideoView != null) {
            return polyvPlaybackVideoView.getCurrentPosition();
        }
        return 0;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerPresenter
    public String getVideoName() {
        PLVPlaybackVO.DataBean modleVO;
        PolyvPlaybackVideoView polyvPlaybackVideoView = this.videoView;
        if (polyvPlaybackVideoView == null || (modleVO = polyvPlaybackVideoView.getModleVO()) == null) {
            return null;
        }
        return modleVO.getTitle();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerPresenter
    public int getVolume() {
        PolyvPlaybackVideoView polyvPlaybackVideoView = this.videoView;
        if (polyvPlaybackVideoView == null) {
            return 0;
        }
        return polyvPlaybackVideoView.getVolume();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerPresenter
    public void init() {
        IPLVPlaybackPlayerContract.IPlaybackPlayerView view = getView();
        if (view == null) {
            return;
        }
        this.videoView = view.getPlaybackVideoView();
        this.subVideoView = view.getSubVideoView();
        initVideoViewListener();
        initSubVideoViewListener();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerPresenter
    public boolean isInPlaybackState() {
        PolyvPlaybackVideoView polyvPlaybackVideoView = this.videoView;
        if (polyvPlaybackVideoView != null) {
            return polyvPlaybackVideoView.isInPlaybackState();
        }
        return false;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerPresenter
    public boolean isPlaying() {
        PolyvPlaybackVideoView polyvPlaybackVideoView = this.videoView;
        if (polyvPlaybackVideoView != null) {
            return polyvPlaybackVideoView.isPlaying();
        }
        return false;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerPresenter
    public boolean isSubVideoViewShow() {
        PolyvAuxiliaryVideoview polyvAuxiliaryVideoview = this.subVideoView;
        if (polyvAuxiliaryVideoview != null) {
            return polyvAuxiliaryVideoview.isShow();
        }
        return false;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerPresenter
    public void pause() {
        PolyvPlaybackVideoView polyvPlaybackVideoView = this.videoView;
        if (polyvPlaybackVideoView != null) {
            polyvPlaybackVideoView.pause();
            updatePlayInfo();
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerPresenter
    public void registerView(@NonNull IPLVPlaybackPlayerContract.IPlaybackPlayerView v2) {
        this.vWeakReference = new WeakReference<>(v2);
        v2.setPresenter(this);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerPresenter
    public void resume() {
        PolyvPlaybackVideoView polyvPlaybackVideoView = this.videoView;
        if (polyvPlaybackVideoView != null) {
            polyvPlaybackVideoView.start();
            updatePlayInfo();
        }
        removeWatermark();
        showWatermarkView(true);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerPresenter
    public void seekTo(int duration) {
        PolyvPlaybackVideoView polyvPlaybackVideoView = this.videoView;
        if (polyvPlaybackVideoView != null) {
            polyvPlaybackVideoView.seekTo(duration);
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerPresenter
    public void setAllowOpenAdHead(boolean isAllowOpenAdHead) {
        this.isAllowOpenAdHead = isAllowOpenAdHead;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerPresenter
    public void setPlayerVid(String vid) {
        this.liveRoomDataManager.setConfigVid(vid);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerPresenter
    public void setPlayerVidAndPlay(String vid) {
        this.liveRoomDataManager.setConfigVid(vid);
        startPlay();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerPresenter
    public void setPlayerVolume(int volume) {
        PolyvPlaybackVideoView polyvPlaybackVideoView = this.videoView;
        if (polyvPlaybackVideoView != null) {
            polyvPlaybackVideoView.setPlayerVolume(volume);
        }
        PolyvAuxiliaryVideoview polyvAuxiliaryVideoview = this.subVideoView;
        if (polyvAuxiliaryVideoview != null) {
            polyvAuxiliaryVideoview.setPlayerVolume(volume);
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerPresenter
    public void setSpeed(float speed) {
        PolyvPlaybackVideoView polyvPlaybackVideoView = this.videoView;
        if (polyvPlaybackVideoView != null) {
            polyvPlaybackVideoView.setSpeed(speed);
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerPresenter
    public void setVolume(int volume) {
        PolyvPlaybackVideoView polyvPlaybackVideoView = this.videoView;
        if (polyvPlaybackVideoView != null) {
            polyvPlaybackVideoView.setVolume(volume);
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerPresenter
    public void startPlay() {
        resetErrorViewStatus();
        PLVPlaybackVideoParams pLVPlaybackVideoParams = new PLVPlaybackVideoParams(getConfig().getVid(), getConfig().getChannelId(), getConfig().getAccount().getUserId(), getConfig().getUser().getViewerId());
        Boolean bool = Boolean.TRUE;
        pLVPlaybackVideoParams.buildOptions(PLVBaseVideoParams.MARQUEE, bool).buildOptions(PLVBaseVideoParams.HEAD_AD, Boolean.valueOf(this.isAllowOpenAdHead)).buildOptions(PLVBaseVideoParams.PARAMS2, getConfig().getUser().getViewerName()).buildOptions(PLVPlaybackVideoParams.LOCAL_VIDEO_CACHE_LIST, getLocalCacheVideoList()).buildOptions(PLVPlaybackVideoParams.ENABLE_ACCURATE_SEEK, bool).buildOptions(PLVPlaybackVideoParams.ENABLE_AUTO_PLAY_TEMP_STORE_VIDEO, bool).buildOptions(PLVPlaybackVideoParams.VIDEO_LISTTYPE, this.liveRoomDataManager.getConfig().getVideoListType()).buildOptions(PLVBaseVideoParams.LOAD_SLOW_TIME, 15);
        PolyvPlaybackVideoView polyvPlaybackVideoView = this.videoView;
        if (polyvPlaybackVideoView != null) {
            polyvPlaybackVideoView.playByMode(pLVPlaybackVideoParams, 1001);
        }
        startPlayProgressTimer();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerPresenter
    public void stop() {
        PolyvPlaybackVideoView polyvPlaybackVideoView = this.videoView;
        if (polyvPlaybackVideoView != null) {
            polyvPlaybackVideoView.stopPlay();
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerPresenter
    public void unregisterView() {
        WeakReference<IPLVPlaybackPlayerContract.IPlaybackPlayerView> weakReference = this.vWeakReference;
        if (weakReference != null) {
            weakReference.clear();
            this.vWeakReference = null;
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerPresenter
    public void seekTo(int progress, int max) {
        PolyvPlaybackVideoView polyvPlaybackVideoView = this.videoView;
        if (polyvPlaybackVideoView == null || !polyvPlaybackVideoView.isInPlaybackStateEx()) {
            return;
        }
        int duration = (int) ((this.videoView.getDuration() * progress) / max);
        if (!this.videoView.isCompletedState()) {
            this.videoView.seekTo(duration);
        } else if (duration < this.videoView.getDuration()) {
            this.videoView.seekTo(duration);
            this.videoView.start();
        }
    }
}
