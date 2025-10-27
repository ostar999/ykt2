package com.easefun.polyv.livecommon.module.modules.player.live.presenter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.easefun.polyv.businesssdk.api.auxiliary.IPolyvAuxiliaryVideoViewListenerEvent;
import com.easefun.polyv.businesssdk.api.auxiliary.PolyvAuxiliaryVideoview;
import com.easefun.polyv.businesssdk.api.common.player.PolyvPlayError;
import com.easefun.polyv.businesssdk.api.common.player.listener.IPolyvVideoViewListenerEvent;
import com.easefun.polyv.businesssdk.model.video.PolyvDefinitionVO;
import com.easefun.polyv.businesssdk.model.video.PolyvLiveChannelVO;
import com.easefun.polyv.businesssdk.model.video.PolyvLiveLinesVO;
import com.easefun.polyv.businesssdk.model.video.PolyvLiveMarqueeVO;
import com.easefun.polyv.livecommon.module.config.PLVLiveChannelConfig;
import com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager;
import com.easefun.polyv.livecommon.module.modules.marquee.IPLVMarqueeView;
import com.easefun.polyv.livecommon.module.modules.marquee.PLVMarqueeCommonController;
import com.easefun.polyv.livecommon.module.modules.marquee.model.PLVMarqueeModel;
import com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract;
import com.easefun.polyv.livecommon.module.modules.player.live.presenter.data.PLVLivePlayerData;
import com.easefun.polyv.livecommon.module.modules.player.live.presenter.data.PLVPlayInfoVO;
import com.easefun.polyv.livecommon.module.modules.watermark.IPLVWatermarkView;
import com.easefun.polyv.livecommon.module.modules.watermark.PLVWatermarkCommonController;
import com.easefun.polyv.livecommon.module.modules.watermark.PLVWatermarkTextVO;
import com.easefun.polyv.livecommon.module.utils.PLVWebUtils;
import com.easefun.polyv.livecommon.ui.widget.PLVPlayerLogoView;
import com.easefun.polyv.livescenes.video.PolyvLiveVideoView;
import com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent;
import com.plv.business.model.video.PLVBaseVideoParams;
import com.plv.business.model.video.PLVLiveVideoParams;
import com.plv.business.model.video.PLVWatermarkVO;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.utils.PLVControlUtils;
import com.plv.foundationsdk.utils.PLVFormatUtils;
import com.plv.livescenes.linkmic.manager.PLVLinkMicConfig;
import com.plv.livescenes.video.api.IPLVLiveListenerEvent;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVLivePlayerPresenter implements IPLVLivePlayerContract.ILivePlayerPresenter {
    private static final String TAG = "PLVLivePlayerPresenter";
    private static final int WHAT_PLAY_PROGRESS = 1;
    private IPLVLiveRoomDataManager liveRoomDataManager;
    private PLVPlayerLogoView logoView;
    private IPLVVideoViewListenerEvent.OnGestureClickListener onSubGestureClickListener;
    private PolyvAuxiliaryVideoview subVideoView;
    private WeakReference<IPLVLivePlayerContract.ILivePlayerView> vWeakReference;
    private PolyvLiveVideoView videoView;
    private boolean isAllowOpenAdHead = false;
    private boolean isAllowMarqueeRunning = true;
    private boolean isAllowWatermarkShow = true;
    private String subVideoViewHerf = "";
    private boolean isLowLatency = PLVLinkMicConfig.getInstance().isLowLatencyWatchEnabled();
    private Handler selfHandler = new Handler(Looper.getMainLooper()) { // from class: com.easefun.polyv.livecommon.module.modules.player.live.presenter.PLVLivePlayerPresenter.29
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                PLVLivePlayerPresenter.this.startPlayProgressTimer();
            }
        }
    };
    private PLVLivePlayerData livePlayerData = new PLVLivePlayerData();

    public PLVLivePlayerPresenter(@NonNull IPLVLiveRoomDataManager liveRoomDataManager) {
        this.liveRoomDataManager = liveRoomDataManager;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public PLVLiveChannelConfig getConfig() {
        return this.liveRoomDataManager.getConfig();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public IPLVLivePlayerContract.ILivePlayerView getView() {
        WeakReference<IPLVLivePlayerContract.ILivePlayerView> weakReference = this.vWeakReference;
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }

    private void initSubVideoViewListener() {
        PolyvAuxiliaryVideoview polyvAuxiliaryVideoview = this.subVideoView;
        if (polyvAuxiliaryVideoview != null) {
            polyvAuxiliaryVideoview.setOnVideoPlayListener(new IPLVVideoViewListenerEvent.OnVideoPlayListener() { // from class: com.easefun.polyv.livecommon.module.modules.player.live.presenter.PLVLivePlayerPresenter.1
                @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent.OnVideoPlayListener
                public void onPlay(boolean isFirst) {
                    IPLVLivePlayerContract.ILivePlayerView view = PLVLivePlayerPresenter.this.getView();
                    if (view != null) {
                        view.onSubVideoViewPlay(isFirst);
                    }
                }
            });
            IPLVVideoViewListenerEvent.OnGestureClickListener onGestureClickListener = new IPLVVideoViewListenerEvent.OnGestureClickListener() { // from class: com.easefun.polyv.livecommon.module.modules.player.live.presenter.PLVLivePlayerPresenter.2
                @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent.OnGestureClickListener
                public void callback(boolean start, boolean end) {
                    boolean z2 = (PLVLivePlayerPresenter.this.subVideoView == null || !PLVLivePlayerPresenter.this.subVideoView.isPlaying()) ? PLVLivePlayerPresenter.this.videoView != null && PLVLivePlayerPresenter.this.videoView.isPlaying() : false;
                    IPLVLivePlayerContract.ILivePlayerView view = PLVLivePlayerPresenter.this.getView();
                    if (view != null) {
                        view.onSubVideoViewClick(z2);
                    }
                    if (TextUtils.isEmpty(PLVLivePlayerPresenter.this.subVideoViewHerf)) {
                        return;
                    }
                    PLVWebUtils.openWebLink(PLVLivePlayerPresenter.this.subVideoViewHerf, PLVLivePlayerPresenter.this.subVideoView.getContext());
                }
            };
            this.onSubGestureClickListener = onGestureClickListener;
            this.subVideoView.setOnGestureClickListener(onGestureClickListener);
            this.subVideoView.setOnSubVideoViewLoadImage(new IPolyvAuxiliaryVideoViewListenerEvent.IPolyvOnSubVideoViewLoadImage() { // from class: com.easefun.polyv.livecommon.module.modules.player.live.presenter.PLVLivePlayerPresenter.3
                @Override // com.plv.business.api.auxiliary.IPLVAuxiliaryVideoViewListenerEvent.IPLVOnSubVideoViewLoadImage
                public void onLoad(String imageUrl, final ImageView imageView, final String coverHref) {
                    IPLVLivePlayerContract.ILivePlayerView view = PLVLivePlayerPresenter.this.getView();
                    if (view != null) {
                        view.onSubVideoViewLoadImage(imageUrl, imageView);
                    }
                    PLVLivePlayerPresenter.this.subVideoViewHerf = coverHref;
                    if (TextUtils.isEmpty(coverHref)) {
                        return;
                    }
                    imageView.setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecommon.module.modules.player.live.presenter.PLVLivePlayerPresenter.3.1
                        @Override // android.view.View.OnClickListener
                        public void onClick(View v2) {
                            PLVWebUtils.openWebLink(coverHref, PLVLivePlayerPresenter.this.subVideoView.getContext());
                        }
                    });
                }
            });
            this.subVideoView.setOnSubVideoViewCountdownListener(new IPolyvAuxiliaryVideoViewListenerEvent.IPolyvOnSubVideoViewCountdownListener() { // from class: com.easefun.polyv.livecommon.module.modules.player.live.presenter.PLVLivePlayerPresenter.4
                @Override // com.plv.business.api.auxiliary.IPLVAuxiliaryVideoViewListenerEvent.IPLVOnSubVideoViewCountdownListener
                public void onCountdown(int totalTime, int remainTime, int adStage) {
                    boolean z2 = PLVLivePlayerPresenter.this.subVideoView != null && PLVLivePlayerPresenter.this.subVideoView.isOpenHeadAd();
                    IPLVLivePlayerContract.ILivePlayerView view = PLVLivePlayerPresenter.this.getView();
                    if (view != null) {
                        view.onSubVideoViewCountDown(z2, totalTime, remainTime, adStage);
                        if (z2) {
                            PLVLivePlayerPresenter.this.setLogoVisibility(0);
                        } else {
                            PLVLivePlayerPresenter.this.setLogoVisibility(8);
                        }
                    }
                }

                @Override // com.plv.business.api.auxiliary.IPLVAuxiliaryVideoViewListenerEvent.IPLVOnSubVideoViewCountdownListener
                public void onVisibilityChange(boolean isShow) {
                    boolean z2 = PLVLivePlayerPresenter.this.subVideoView != null && PLVLivePlayerPresenter.this.subVideoView.isOpenHeadAd();
                    IPLVLivePlayerContract.ILivePlayerView view = PLVLivePlayerPresenter.this.getView();
                    if (view != null) {
                        view.onSubVideoViewVisiblityChanged(z2, isShow);
                    }
                }
            });
        }
    }

    private void initVideoViewListener() {
        PolyvLiveVideoView polyvLiveVideoView = this.videoView;
        if (polyvLiveVideoView != null) {
            polyvLiveVideoView.setOnErrorListener(new IPolyvVideoViewListenerEvent.OnErrorListener() { // from class: com.easefun.polyv.livecommon.module.modules.player.live.presenter.PLVLivePlayerPresenter.5
                @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent.OnErrorListener
                public void onError(int what, int extra) {
                }

                @Override // com.easefun.polyv.businesssdk.api.common.player.listener.IPolyvVideoViewListenerEvent.OnErrorListener
                public void onError(PolyvPlayError error) {
                    PLVLivePlayerPresenter.this.setDefaultViewStatus();
                    int i2 = error.playStage;
                    String str = (i2 != 1 ? i2 != 2 ? i2 != 3 ? error.isMainStage() ? "主视频" : "" : "片尾广告" : "暖场视频" : "片头广告") + "播放异常\n" + error.errorDescribe + " (errorCode:" + error.errorCode + "-" + error.playStage + ")\n" + error.playPath;
                    IPLVLivePlayerContract.ILivePlayerView view = PLVLivePlayerPresenter.this.getView();
                    if (view != null) {
                        view.onPlayError(error, str);
                    }
                    PLVLivePlayerPresenter.this.setLogoVisibility(8);
                    PLVLivePlayerPresenter.this.stopMarqueeView();
                    PLVLivePlayerPresenter.this.removeWatermark();
                }
            });
            this.videoView.setOnVideoLoadSlowListener(new IPLVVideoViewListenerEvent.OnVideoLoadSlowListener() { // from class: com.easefun.polyv.livecommon.module.modules.player.live.presenter.PLVLivePlayerPresenter.6
                @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent.OnVideoLoadSlowListener
                public void onLoadSlow(int loadedTime, boolean isBufferEvent) {
                    IPLVLivePlayerContract.ILivePlayerView view = PLVLivePlayerPresenter.this.getView();
                    if (view != null) {
                        view.onLoadSlow(loadedTime, isBufferEvent);
                    }
                }
            });
            this.videoView.setOnInfoListener(new IPLVVideoViewListenerEvent.OnInfoListener() { // from class: com.easefun.polyv.livecommon.module.modules.player.live.presenter.PLVLivePlayerPresenter.7
                @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent.OnInfoListener
                public void onInfo(int what, int extra) {
                    if (what == 702) {
                        PLVLivePlayerPresenter.this.resetErrorViewStatus();
                    }
                }
            });
            this.videoView.setOnNoLiveAtPresentListener(new IPLVLiveListenerEvent.OnNoLiveAtPresentListener() { // from class: com.easefun.polyv.livecommon.module.modules.player.live.presenter.PLVLivePlayerPresenter.8
                @Override // com.plv.livescenes.video.api.IPLVLiveListenerEvent.OnNoLiveAtPresentListener
                public void onLiveEnd() {
                    PLVCommonLog.d(PLVLivePlayerPresenter.TAG, "onLiveEnd");
                    PLVLivePlayerPresenter.this.livePlayerData.postLiveEnd();
                    IPLVLivePlayerContract.ILivePlayerView view = PLVLivePlayerPresenter.this.getView();
                    if (view != null) {
                        view.onLiveEnd();
                    }
                    PLVLivePlayerPresenter.this.setLogoVisibility(8);
                    PLVLivePlayerPresenter.this.stopMarqueeView();
                    PLVLivePlayerPresenter.this.removeWatermark();
                }

                @Override // com.plv.livescenes.video.api.IPLVLiveListenerEvent.OnNoLiveAtPresentListener
                public void onLiveStop() {
                    PLVCommonLog.d(PLVLivePlayerPresenter.TAG, "onLiveStop");
                    PLVLivePlayerPresenter.this.livePlayerData.postLiveStop();
                    IPLVLivePlayerContract.ILivePlayerView view = PLVLivePlayerPresenter.this.getView();
                    if (view != null) {
                        view.onLiveStop();
                    }
                    PLVLivePlayerPresenter.this.setLogoVisibility(8);
                    PLVLivePlayerPresenter.this.stopMarqueeView();
                    PLVLivePlayerPresenter.this.removeWatermark();
                }

                @Override // com.plv.livescenes.video.api.IPLVLiveListenerEvent.OnNoLiveAtPresentListener
                public void onNoLiveAtPresent() {
                    PLVCommonLog.d(PLVLivePlayerPresenter.TAG, "onNoLiveAtPresent");
                    PLVLivePlayerPresenter.this.videoView.removeRenderView();
                    PLVLivePlayerPresenter.this.livePlayerData.postNoLive();
                    IPLVLivePlayerContract.ILivePlayerView view = PLVLivePlayerPresenter.this.getView();
                    if (view != null) {
                        view.onNoLiveAtPresent();
                    }
                    PLVLivePlayerPresenter.this.setLogoVisibility(8);
                    PLVLivePlayerPresenter.this.stopMarqueeView();
                    PLVLivePlayerPresenter.this.removeWatermark();
                }
            });
            this.videoView.setOnPreparedListener(new IPLVVideoViewListenerEvent.OnPreparedListener() { // from class: com.easefun.polyv.livecommon.module.modules.player.live.presenter.PLVLivePlayerPresenter.9
                @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent.OnPreparedListener
                public void onPrepared() {
                    PLVCommonLog.d(PLVLivePlayerPresenter.TAG, "onPrepared");
                    PLVLivePlayerPresenter.this.livePlayerData.postPrepared();
                    PLVLivePlayerPresenter.this.liveRoomDataManager.setSessionId(PLVLivePlayerPresenter.this.videoView.getModleVO() != null ? PLVLivePlayerPresenter.this.videoView.getModleVO().getChannelSessionId() : null);
                    if (PLVLivePlayerPresenter.this.videoView.getMediaPlayMode() == 1) {
                        PLVLivePlayerPresenter.this.videoView.removeRenderView();
                        PLVLivePlayerPresenter.this.setLogoVisibility(8);
                    } else if (PLVLivePlayerPresenter.this.videoView.getMediaPlayMode() == 0) {
                        PLVLivePlayerPresenter.this.setLogoVisibility(0);
                    }
                    IPLVLivePlayerContract.ILivePlayerView view = PLVLivePlayerPresenter.this.getView();
                    if (view != null) {
                        view.onPrepared(PLVLivePlayerPresenter.this.videoView.getMediaPlayMode());
                    }
                    PLVLivePlayerPresenter.this.setMarqueeViewRunning(true);
                    PLVLivePlayerPresenter.this.showWatermarkView(true);
                    PLVLivePlayerPresenter.this.resetErrorViewStatus();
                }

                @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent.OnPreparedListener
                public void onPreparing() {
                    PLVCommonLog.d(PLVLivePlayerPresenter.TAG, "onPreparing");
                }
            });
            this.videoView.setOnLinesChangedListener(new IPLVLiveListenerEvent.OnLinesChangedListener() { // from class: com.easefun.polyv.livecommon.module.modules.player.live.presenter.PLVLivePlayerPresenter.10
                @Override // com.plv.livescenes.video.api.IPLVLiveListenerEvent.OnLinesChangedListener
                public void onLinesChanged(final int linesPos) {
                    PLVLivePlayerPresenter.this.livePlayerData.postLinesChange(linesPos);
                    IPLVLivePlayerContract.ILivePlayerView view = PLVLivePlayerPresenter.this.getView();
                    if (view != null) {
                        view.onLinesChanged(linesPos);
                    }
                }
            });
            this.videoView.setOnGetWatermarkVOListener(new IPLVVideoViewListenerEvent.OnGetWatermarkVoListener() { // from class: com.easefun.polyv.livecommon.module.modules.player.live.presenter.PLVLivePlayerPresenter.11
                @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent.OnGetWatermarkVoListener
                public void onGetWatermarkVO(final PLVWatermarkVO waterMarkVO) {
                    PLVWatermarkCommonController.getInstance().updateWatermarkView(waterMarkVO, PLVLivePlayerPresenter.this.getConfig().getUser().getViewerName());
                    if (PLVLivePlayerPresenter.this.isWatermarkExisted()) {
                        if ("N".equals(waterMarkVO.watermarkRestrict)) {
                            PLVLivePlayerPresenter.this.removeWatermark();
                        } else {
                            PLVLivePlayerPresenter.this.setWatermarkTextVO(waterMarkVO);
                        }
                    }
                }
            });
            this.videoView.setOnGetMarqueeVoListener(new IPolyvVideoViewListenerEvent.OnGetMarqueeVoListener() { // from class: com.easefun.polyv.livecommon.module.modules.player.live.presenter.PLVLivePlayerPresenter.12
                @Override // com.easefun.polyv.businesssdk.api.common.player.listener.IPolyvVideoViewListenerEvent.OnGetMarqueeVoListener
                public void onGetMarqueeVo(PolyvLiveMarqueeVO marqueeVo) {
                    PLVMarqueeCommonController.getInstance().updateMarqueeView(marqueeVo, PLVLivePlayerPresenter.this.getConfig().getUser().getViewerName(), new PLVMarqueeCommonController.IPLVMarqueeControllerCallback() { // from class: com.easefun.polyv.livecommon.module.modules.player.live.presenter.PLVLivePlayerPresenter.12.1
                        @Override // com.easefun.polyv.livecommon.module.modules.marquee.PLVMarqueeCommonController.IPLVMarqueeControllerCallback
                        public void onMarqueeModel(int controllerTip, PLVMarqueeModel marqueeModel) {
                            if (PLVLivePlayerPresenter.this.isMarqueeExisted()) {
                                if (controllerTip == 0 || controllerTip == 1) {
                                    PLVLivePlayerPresenter.this.isAllowMarqueeRunning = false;
                                    final Activity activity = (Activity) PLVLivePlayerPresenter.this.videoView.getContext();
                                    activity.runOnUiThread(new Runnable() { // from class: com.easefun.polyv.livecommon.module.modules.player.live.presenter.PLVLivePlayerPresenter.12.1.1
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
                                    PLVLivePlayerPresenter.this.isAllowMarqueeRunning = false;
                                    PLVLivePlayerPresenter.this.stopMarqueeView();
                                } else {
                                    if (controllerTip != 3) {
                                        return;
                                    }
                                    Log.i(PLVLivePlayerPresenter.TAG, "onMarqueeModel: allow");
                                    PLVLivePlayerPresenter.this.isAllowMarqueeRunning = true;
                                    PLVLivePlayerPresenter.this.stopMarqueeView();
                                    PLVLivePlayerPresenter.this.setMarqueeViewModel(marqueeModel);
                                }
                            }
                        }
                    });
                }
            });
            this.videoView.setOnGestureClickListener(new IPLVVideoViewListenerEvent.OnGestureClickListener() { // from class: com.easefun.polyv.livecommon.module.modules.player.live.presenter.PLVLivePlayerPresenter.13
                @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent.OnGestureClickListener
                public void callback(boolean start, boolean end) {
                    if (PLVLivePlayerPresenter.this.videoView.isOnline() || PLVLivePlayerPresenter.this.onSubGestureClickListener == null) {
                        return;
                    }
                    PLVLivePlayerPresenter.this.onSubGestureClickListener.callback(start, end);
                }
            });
            this.videoView.setOnGestureLeftDownListener(new IPLVVideoViewListenerEvent.OnGestureLeftDownListener() { // from class: com.easefun.polyv.livecommon.module.modules.player.live.presenter.PLVLivePlayerPresenter.14
                @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent.OnGestureLeftDownListener
                public void callback(boolean start, boolean end) {
                    int iMax = Math.max(0, PLVLivePlayerPresenter.this.videoView.getBrightness((Activity) PLVLivePlayerPresenter.this.videoView.getContext()) - 8);
                    IPLVLivePlayerContract.ILivePlayerView view = PLVLivePlayerPresenter.this.getView();
                    if (view != null) {
                        boolean zOnLightChanged = view.onLightChanged(iMax, end);
                        if (start && zOnLightChanged) {
                            PLVLivePlayerPresenter.this.videoView.setBrightness((Activity) PLVLivePlayerPresenter.this.videoView.getContext(), iMax);
                        }
                    }
                }
            });
            this.videoView.setOnGestureLeftUpListener(new IPLVVideoViewListenerEvent.OnGestureLeftUpListener() { // from class: com.easefun.polyv.livecommon.module.modules.player.live.presenter.PLVLivePlayerPresenter.15
                @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent.OnGestureLeftUpListener
                public void callback(boolean start, boolean end) {
                    int iMin = Math.min(100, PLVLivePlayerPresenter.this.videoView.getBrightness((Activity) PLVLivePlayerPresenter.this.videoView.getContext()) + 8);
                    IPLVLivePlayerContract.ILivePlayerView view = PLVLivePlayerPresenter.this.getView();
                    if (view != null) {
                        boolean zOnLightChanged = view.onLightChanged(iMin, end);
                        if (start && zOnLightChanged) {
                            PLVLivePlayerPresenter.this.videoView.setBrightness((Activity) PLVLivePlayerPresenter.this.videoView.getContext(), iMin);
                        }
                    }
                }
            });
            this.videoView.setOnGestureRightDownListener(new IPLVVideoViewListenerEvent.OnGestureRightDownListener() { // from class: com.easefun.polyv.livecommon.module.modules.player.live.presenter.PLVLivePlayerPresenter.16
                @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent.OnGestureRightDownListener
                public void callback(boolean start, boolean end) {
                    int iMax = Math.max(0, PLVLivePlayerPresenter.this.videoView.getVolume() - PLVControlUtils.getVolumeValidProgress(PLVLivePlayerPresenter.this.videoView.getContext(), 8));
                    IPLVLivePlayerContract.ILivePlayerView view = PLVLivePlayerPresenter.this.getView();
                    if (view != null) {
                        boolean zOnVolumeChanged = view.onVolumeChanged(iMax, end);
                        if (start && zOnVolumeChanged) {
                            PLVLivePlayerPresenter.this.videoView.setVolume(iMax);
                        }
                    }
                }
            });
            this.videoView.setOnGestureRightUpListener(new IPLVVideoViewListenerEvent.OnGestureRightUpListener() { // from class: com.easefun.polyv.livecommon.module.modules.player.live.presenter.PLVLivePlayerPresenter.17
                @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent.OnGestureRightUpListener
                public void callback(boolean start, boolean end) {
                    int iMin = Math.min(100, PLVLivePlayerPresenter.this.videoView.getVolume() + PLVControlUtils.getVolumeValidProgress(PLVLivePlayerPresenter.this.videoView.getContext(), 8));
                    IPLVLivePlayerContract.ILivePlayerView view = PLVLivePlayerPresenter.this.getView();
                    if (view != null) {
                        boolean zOnVolumeChanged = view.onVolumeChanged(iMin, end);
                        if (start && zOnVolumeChanged) {
                            PLVLivePlayerPresenter.this.videoView.setVolume(iMin);
                        }
                    }
                }
            });
            this.videoView.setOnDanmuServerOpenListener(new IPLVVideoViewListenerEvent.OnDanmuServerOpenListener() { // from class: com.easefun.polyv.livecommon.module.modules.player.live.presenter.PLVLivePlayerPresenter.18
                @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent.OnDanmuServerOpenListener
                public void onDanmuServerOpenListener(boolean isServerDanmuOpen) {
                    IPLVLivePlayerContract.ILivePlayerView view = PLVLivePlayerPresenter.this.getView();
                    if (view != null) {
                        view.onServerDanmuOpen(isServerDanmuOpen);
                    }
                }
            });
            this.videoView.setMicroPhoneListener(new IPLVLiveListenerEvent.MicroPhoneListener() { // from class: com.easefun.polyv.livecommon.module.modules.player.live.presenter.PLVLivePlayerPresenter.19
                @Override // com.plv.livescenes.video.api.IPLVLiveListenerEvent.MicroPhoneListener
                public void showMicPhoneLine(int visible) {
                    PLVCommonLog.d(PLVLivePlayerPresenter.TAG, "showMicPhoneLine visible=" + visible);
                    PLVLivePlayerPresenter.this.livePlayerData.postLinkMicOpen(visible == 0, "audio".equals(PLVLivePlayerPresenter.this.videoView.getLinkMicType()));
                }
            });
            this.videoView.setOnPPTShowListener(new IPLVVideoViewListenerEvent.OnPPTShowListener() { // from class: com.easefun.polyv.livecommon.module.modules.player.live.presenter.PLVLivePlayerPresenter.20
                @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent.OnPPTShowListener
                public void showPPTView(int visible) {
                    PLVLivePlayerPresenter.this.livePlayerData.postPPTShowState(visible == 0);
                    IPLVLivePlayerContract.ILivePlayerView view = PLVLivePlayerPresenter.this.getView();
                    if (view != null) {
                        view.onShowPPTView(visible);
                    }
                }
            });
            this.videoView.setOnSupportRTCListener(new IPLVLiveListenerEvent.OnSupportRTCListener() { // from class: com.easefun.polyv.livecommon.module.modules.player.live.presenter.PLVLivePlayerPresenter.21
                @Override // com.plv.livescenes.video.api.IPLVLiveListenerEvent.OnSupportRTCListener
                public void onSupportRTC(boolean isSupportRTC) {
                    PLVLivePlayerPresenter.this.liveRoomDataManager.setSupportRTC(isSupportRTC);
                }
            });
            this.videoView.setOnSEIRefreshListener(new IPLVVideoViewListenerEvent.OnSEIRefreshListener() { // from class: com.easefun.polyv.livecommon.module.modules.player.live.presenter.PLVLivePlayerPresenter.22
                @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent.OnSEIRefreshListener
                public void onSEIRefresh(int seiType, byte[] seiData) {
                    long j2 = PLVFormatUtils.parseLong(new String(seiData));
                    PLVCommonLog.v(PLVLivePlayerPresenter.TAG, "sei ts = " + j2);
                    PLVLivePlayerPresenter.this.livePlayerData.postSeiData(j2);
                }
            });
            this.videoView.setOnNetworkStateListener(new IPLVVideoViewListenerEvent.OnNetworkStateListener() { // from class: com.easefun.polyv.livecommon.module.modules.player.live.presenter.PLVLivePlayerPresenter.23
                @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent.OnNetworkStateListener
                public boolean onNetworkError() {
                    return false;
                }

                @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent.OnNetworkStateListener
                public boolean onNetworkRecover() {
                    IPLVLivePlayerContract.ILivePlayerView view = PLVLivePlayerPresenter.this.getView();
                    if (view != null) {
                        return view.onNetworkRecover();
                    }
                    return false;
                }
            });
            this.videoView.setOnLowLatencyNetworkQualityListener(new IPLVLiveListenerEvent.OnLowLatencyNetworkQualityListener() { // from class: com.easefun.polyv.livecommon.module.modules.player.live.presenter.PLVLivePlayerPresenter.24
                @Override // com.plv.livescenes.video.api.IPLVLiveListenerEvent.OnLowLatencyNetworkQualityListener
                public void onNetworkQuality(int networkQuality) {
                    IPLVLivePlayerContract.ILivePlayerView view = PLVLivePlayerPresenter.this.getView();
                    if (view != null) {
                        view.onLowLatencyNetworkQuality(networkQuality);
                    }
                }
            });
            this.videoView.setOnGetLogoListener(new IPLVVideoViewListenerEvent.OnGetLogoListener() { // from class: com.easefun.polyv.livecommon.module.modules.player.live.presenter.PLVLivePlayerPresenter.25
                @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent.OnGetLogoListener
                public void onLogo(String logoImage, int logoAlpha, int logoPosition, String logoHref) {
                    if (TextUtils.isEmpty(logoImage)) {
                        return;
                    }
                    PLVPlayerLogoView.LogoParam logoHref2 = new PLVPlayerLogoView.LogoParam().setWidth(0.14f).setHeight(0.25f).setAlpha(logoAlpha).setOffsetX(0.03f).setOffsetY(0.06f).setPos(logoPosition).setResUrl(logoImage).setLogoHref(logoHref);
                    IPLVLivePlayerContract.ILivePlayerView view = PLVLivePlayerPresenter.this.getView();
                    if (view != null) {
                        PLVLivePlayerPresenter.this.logoView = view.getLogo();
                        if (PLVLivePlayerPresenter.this.logoView != null) {
                            PLVLivePlayerPresenter.this.logoView.removeAllLogo();
                            PLVLivePlayerPresenter.this.logoView.addLogo(logoHref2);
                            PLVLivePlayerPresenter.this.logoView.setVisibility(8);
                        }
                    }
                }
            });
            this.videoView.setOnVideoPauseListener(new IPLVVideoViewListenerEvent.OnVideoPauseListener() { // from class: com.easefun.polyv.livecommon.module.modules.player.live.presenter.PLVLivePlayerPresenter.26
                @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent.OnVideoPauseListener
                public void onPause() {
                    PLVLivePlayerPresenter.this.setMarqueeViewRunning(false);
                }
            });
            this.videoView.setOnOnlyAudioListener(new IPLVLiveListenerEvent.OnOnlyAudioListener() { // from class: com.easefun.polyv.livecommon.module.modules.player.live.presenter.PLVLivePlayerPresenter.27
                @Override // com.plv.livescenes.video.api.IPLVLiveListenerEvent.OnOnlyAudioListener
                public void onOnlyAudio(boolean isOnlyAudio) {
                    if (!PLVLivePlayerPresenter.this.getConfig().isPPTChannelType()) {
                        isOnlyAudio = false;
                    }
                    PLVLivePlayerPresenter.this.liveRoomDataManager.setOnlyAudio(isOnlyAudio);
                    IPLVLivePlayerContract.ILivePlayerView view = PLVLivePlayerPresenter.this.getView();
                    if (view != null) {
                        view.onOnlyAudio(isOnlyAudio);
                    }
                }
            });
            this.videoView.setOnSessionIdChangedListener(new IPLVLiveListenerEvent.OnSessionIdChangedListener() { // from class: com.easefun.polyv.livecommon.module.modules.player.live.presenter.PLVLivePlayerPresenter.28
                @Override // com.plv.livescenes.video.api.IPLVLiveListenerEvent.OnSessionIdChangedListener
                public void onSessionChanged(String sessionId) {
                    PLVLivePlayerPresenter.this.liveRoomDataManager.setSessionId(sessionId);
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
        IPLVLivePlayerContract.ILivePlayerView view = getView();
        if (view == null || (watermarkView = view.getWatermarkView()) == null) {
            return;
        }
        watermarkView.removeWatermark();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetErrorViewStatus() {
        IPLVLivePlayerContract.ILivePlayerView view = getView();
        if (view != null && view.getNoStreamIndicator() != null) {
            view.getNoStreamIndicator().setVisibility(8);
        }
        if (view == null || view.getPlayErrorIndicator() == null) {
            return;
        }
        view.getPlayErrorIndicator().setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDefaultViewStatus() {
        this.videoView.removeRenderView();
        IPLVLivePlayerContract.ILivePlayerView view = getView();
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
    public void setMarqueeViewRunning(boolean allow) {
        IPLVMarqueeView marqueeView;
        Log.i(TAG, "setMarqueeViewRunning: allow");
        if (this.isAllowMarqueeRunning && (marqueeView = getView().getMarqueeView()) != null) {
            if (allow) {
                marqueeView.start();
            } else {
                marqueeView.pause();
            }
        }
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
        updatePlayInfo();
        this.selfHandler.sendEmptyMessageDelayed(1, 1000L);
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

    private void updatePlayInfo() {
        if (this.videoView != null) {
            PLVPlayInfoVO.Builder builder = new PLVPlayInfoVO.Builder();
            builder.isPlaying(this.videoView.isPlaying());
            PolyvAuxiliaryVideoview polyvAuxiliaryVideoview = this.subVideoView;
            if (polyvAuxiliaryVideoview != null) {
                builder.isSubVideoViewPlaying(polyvAuxiliaryVideoview.isPlaying());
            }
            this.livePlayerData.postPlayInfoVO(builder.build());
            IPLVLivePlayerContract.ILivePlayerView view = getView();
            if (view != null) {
                view.updatePlayInfo(builder.build());
            }
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerPresenter
    public void changeBitRate(int bitRate) {
        PolyvLiveVideoView polyvLiveVideoView = this.videoView;
        if (polyvLiveVideoView != null) {
            polyvLiveVideoView.changeBitRate(bitRate);
        }
        stopMarqueeView();
        removeWatermark();
        resetErrorViewStatus();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerPresenter
    public void changeLines(int linesPos) {
        PolyvLiveVideoView polyvLiveVideoView = this.videoView;
        if (polyvLiveVideoView != null) {
            polyvLiveVideoView.changeLines(linesPos);
        }
        stopMarqueeView();
        removeWatermark();
        resetErrorViewStatus();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerPresenter
    public void changeMediaPlayMode(int mediaPlayMode) {
        PolyvLiveVideoView polyvLiveVideoView = this.videoView;
        if (polyvLiveVideoView != null) {
            polyvLiveVideoView.changeMediaPlayMode(mediaPlayMode);
        }
        stopMarqueeView();
        removeWatermark();
        resetErrorViewStatus();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerPresenter
    public void destroy() {
        stopPlayProgressTimer();
        stopMarqueeView();
        unregisterView();
        removeWatermark();
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
        PolyvLiveVideoView polyvLiveVideoView = this.videoView;
        if (polyvLiveVideoView != null) {
            polyvLiveVideoView.destroy();
            this.videoView = null;
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerPresenter
    public int getBitratePos() {
        PolyvLiveVideoView polyvLiveVideoView = this.videoView;
        if (polyvLiveVideoView == null) {
            return 0;
        }
        return polyvLiveVideoView.getBitratePos();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerPresenter
    @Nullable
    public List<PolyvDefinitionVO> getBitrateVO() {
        PolyvLiveChannelVO modleVO;
        List<PolyvLiveLinesVO> lines;
        PolyvLiveLinesVO polyvLiveLinesVO;
        PolyvLiveVideoView polyvLiveVideoView = this.videoView;
        if (polyvLiveVideoView == null || (modleVO = polyvLiveVideoView.getModleVO()) == null || (lines = modleVO.getLines()) == null || (polyvLiveLinesVO = lines.get(getLinesPos())) == null || polyvLiveLinesVO.getMultirateModel() == null) {
            return null;
        }
        if (modleVO.isMutilrateEnable()) {
            return polyvLiveLinesVO.getMultirateModel().getDefinitions();
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(new PolyvDefinitionVO(polyvLiveLinesVO.getMultirateModel().getDefaultDefinition(), polyvLiveLinesVO.getMultirateModel().getDefaultDefinitionUrl()));
        return arrayList;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerPresenter
    public PolyvLiveChannelVO getChannelVO() {
        PolyvLiveVideoView polyvLiveVideoView = this.videoView;
        if (polyvLiveVideoView == null) {
            return null;
        }
        return polyvLiveVideoView.getModleVO();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerPresenter
    @NonNull
    public PLVLivePlayerData getData() {
        return this.livePlayerData;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerPresenter
    public int getLinesCount() {
        PolyvLiveVideoView polyvLiveVideoView = this.videoView;
        if (polyvLiveVideoView == null || polyvLiveVideoView.getModleVO() == null || this.videoView.getModleVO().getLines() == null) {
            return 1;
        }
        return this.videoView.getModleVO().getLines().size();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerPresenter
    public int getLinesPos() {
        PolyvLiveVideoView polyvLiveVideoView = this.videoView;
        if (polyvLiveVideoView == null) {
            return 0;
        }
        return polyvLiveVideoView.getLinesPos();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerPresenter
    public int getMediaPlayMode() {
        PolyvLiveVideoView polyvLiveVideoView = this.videoView;
        if (polyvLiveVideoView != null) {
            return polyvLiveVideoView.getMediaPlayMode();
        }
        return 0;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerPresenter
    public String getSubVideoViewHerf() {
        return this.subVideoView.isShow() ? this.subVideoViewHerf : "";
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerPresenter
    public int getVolume() {
        PolyvLiveVideoView polyvLiveVideoView = this.videoView;
        if (polyvLiveVideoView == null) {
            return 0;
        }
        return polyvLiveVideoView.getVolume();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerPresenter
    public void init() {
        IPLVLivePlayerContract.ILivePlayerView view = getView();
        if (view == null) {
            return;
        }
        this.videoView = view.getLiveVideoView();
        this.subVideoView = view.getSubVideoView();
        initSubVideoViewListener();
        initVideoViewListener();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerPresenter
    public boolean isInPlaybackState() {
        PolyvLiveVideoView polyvLiveVideoView = this.videoView;
        if (polyvLiveVideoView != null) {
            return polyvLiveVideoView.isInPlaybackState();
        }
        return false;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerPresenter
    public boolean isPlaying() {
        PolyvLiveVideoView polyvLiveVideoView = this.videoView;
        if (polyvLiveVideoView != null) {
            return polyvLiveVideoView.isPlaying();
        }
        return false;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerPresenter
    public boolean isSubVideoViewShow() {
        PolyvAuxiliaryVideoview polyvAuxiliaryVideoview = this.subVideoView;
        if (polyvAuxiliaryVideoview != null) {
            return polyvAuxiliaryVideoview.isShow();
        }
        return false;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerPresenter
    public void pause() {
        PolyvLiveVideoView polyvLiveVideoView = this.videoView;
        if (polyvLiveVideoView != null) {
            polyvLiveVideoView.pause();
            updatePlayInfo();
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerPresenter
    public void registerView(@NonNull IPLVLivePlayerContract.ILivePlayerView v2) {
        this.vWeakReference = new WeakReference<>(v2);
        v2.setPresenter(this);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerPresenter
    public void restartPlay() {
        IPLVLivePlayerContract.ILivePlayerView view = getView();
        if (view != null) {
            view.onRestartPlay();
        }
        stopMarqueeView();
        removeWatermark();
        startPlay();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerPresenter
    public void resume() {
        PolyvLiveVideoView polyvLiveVideoView = this.videoView;
        if (polyvLiveVideoView != null) {
            polyvLiveVideoView.start();
            updatePlayInfo();
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerPresenter
    public Bitmap screenshot() {
        PolyvLiveVideoView polyvLiveVideoView = this.videoView;
        if (polyvLiveVideoView == null) {
            return null;
        }
        return polyvLiveVideoView.screenshot();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerPresenter
    public void setAllowOpenAdHead(boolean isAllowOpenAdHead) {
        this.isAllowOpenAdHead = isAllowOpenAdHead;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerPresenter
    public void setNeedGestureDetector(boolean need) {
        PolyvLiveVideoView polyvLiveVideoView = this.videoView;
        if (polyvLiveVideoView != null) {
            polyvLiveVideoView.setNeedGestureDetector(need);
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerPresenter
    public void setPlayerVolume(int volume) {
        PolyvLiveVideoView polyvLiveVideoView = this.videoView;
        if (polyvLiveVideoView != null) {
            polyvLiveVideoView.setPlayerVolume(volume);
        }
        PolyvAuxiliaryVideoview polyvAuxiliaryVideoview = this.subVideoView;
        if (polyvAuxiliaryVideoview != null) {
            polyvAuxiliaryVideoview.setPlayerVolume(volume);
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerPresenter
    public void setVolume(int volume) {
        PolyvLiveVideoView polyvLiveVideoView = this.videoView;
        if (polyvLiveVideoView != null) {
            polyvLiveVideoView.setVolume(volume);
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerPresenter
    public void startPlay() {
        startPlay(this.isLowLatency);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerPresenter
    public void stop() {
        PolyvLiveVideoView polyvLiveVideoView = this.videoView;
        if (polyvLiveVideoView != null) {
            polyvLiveVideoView.stopPlay();
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerPresenter
    public void unregisterView() {
        WeakReference<IPLVLivePlayerContract.ILivePlayerView> weakReference = this.vWeakReference;
        if (weakReference != null) {
            weakReference.clear();
            this.vWeakReference = null;
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerPresenter
    public void startPlay(boolean lowLatency) {
        removeWatermark();
        resetErrorViewStatus();
        PLVLiveVideoParams pLVLiveVideoParams = new PLVLiveVideoParams(getConfig().getChannelId(), getConfig().getAccount().getUserId(), getConfig().getUser().getViewerId());
        Boolean bool = Boolean.TRUE;
        PLVBaseVideoParams pLVBaseVideoParamsBuildOptions = pLVLiveVideoParams.buildOptions(PLVBaseVideoParams.WAIT_AD, bool).buildOptions(PLVBaseVideoParams.HEAD_AD, Boolean.valueOf(this.isAllowOpenAdHead)).buildOptions(PLVBaseVideoParams.MARQUEE, bool).buildOptions(PLVBaseVideoParams.PARAMS2, getConfig().getUser().getViewerName());
        this.isLowLatency = lowLatency;
        pLVBaseVideoParamsBuildOptions.buildOptions(PLVLiveVideoParams.LOW_LATENCY, Boolean.valueOf(lowLatency)).buildOptions(PLVBaseVideoParams.LOAD_SLOW_TIME, 15);
        PolyvLiveVideoView polyvLiveVideoView = this.videoView;
        if (polyvLiveVideoView != null) {
            polyvLiveVideoView.playByMode(pLVLiveVideoParams, 1002);
        }
        startPlayProgressTimer();
    }
}
