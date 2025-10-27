package com.easefun.polyv.livecloudclass.modules.media;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import com.catchpig.mvvm.utils.DateUtil;
import com.easefun.polyv.businesssdk.api.auxiliary.PolyvAuxiliaryVideoview;
import com.easefun.polyv.businesssdk.api.common.player.PolyvPlayError;
import com.easefun.polyv.businesssdk.api.common.ppt.IPolyvPPTView;
import com.easefun.polyv.livecloudclass.R;
import com.easefun.polyv.livecloudclass.modules.chatroom.chatlandscape.PLVLCChatLandscapeLayout;
import com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout;
import com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveLandscapePlayerController;
import com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveMediaController;
import com.easefun.polyv.livecloudclass.modules.media.danmu.IPLVLCDanmuController;
import com.easefun.polyv.livecloudclass.modules.media.danmu.IPLVLCLandscapeMessageSender;
import com.easefun.polyv.livecloudclass.modules.media.danmu.PLVLCDanmuFragment;
import com.easefun.polyv.livecloudclass.modules.media.danmu.PLVLCDanmuWrapper;
import com.easefun.polyv.livecloudclass.modules.media.danmu.PLVLCLandscapeMessageSendPanel;
import com.easefun.polyv.livecloudclass.modules.media.floating.PLVLCFloatingWindow;
import com.easefun.polyv.livecloudclass.modules.media.widget.PLVLCLightTipsView;
import com.easefun.polyv.livecloudclass.modules.media.widget.PLVLCLiveAudioModeView;
import com.easefun.polyv.livecloudclass.modules.media.widget.PLVLCNetworkTipsView;
import com.easefun.polyv.livecloudclass.modules.media.widget.PLVLCVideoLoadingLayout;
import com.easefun.polyv.livecloudclass.modules.media.widget.PLVLCVolumeTipsView;
import com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager;
import com.easefun.polyv.livecommon.module.data.PLVStatefulData;
import com.easefun.polyv.livecommon.module.modules.marquee.IPLVMarqueeView;
import com.easefun.polyv.livecommon.module.modules.player.PLVPlayErrorMessageUtils;
import com.easefun.polyv.livecommon.module.modules.player.PLVPlayerState;
import com.easefun.polyv.livecommon.module.modules.player.floating.PLVFloatingPlayerManager;
import com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract;
import com.easefun.polyv.livecommon.module.modules.player.live.presenter.PLVLivePlayerPresenter;
import com.easefun.polyv.livecommon.module.modules.player.live.view.PLVAbsLivePlayerView;
import com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.data.PLVPlayInfoVO;
import com.easefun.polyv.livecommon.module.modules.reward.view.effect.PLVRewardSVGAHelper;
import com.easefun.polyv.livecommon.module.modules.watermark.IPLVWatermarkView;
import com.easefun.polyv.livecommon.module.utils.imageloader.PLVImageLoader;
import com.easefun.polyv.livecommon.module.utils.listener.IPLVOnDataChangedListener;
import com.easefun.polyv.livecommon.module.utils.rotaion.PLVOrientationManager;
import com.easefun.polyv.livecommon.ui.widget.PLVPlaceHolderView;
import com.easefun.polyv.livecommon.ui.widget.PLVPlayerLogoView;
import com.easefun.polyv.livecommon.ui.widget.PLVSwitchViewAnchorLayout;
import com.easefun.polyv.livecommon.ui.widget.PLVTriangleIndicateTextView;
import com.easefun.polyv.livescenes.model.PolyvChatFunctionSwitchVO;
import com.easefun.polyv.livescenes.model.PolyvLiveClassDetailVO;
import com.easefun.polyv.livescenes.video.PolyvLiveVideoView;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.plv.business.api.common.player.PLVBaseVideoView;
import com.plv.foundationsdk.component.di.PLVDependManager;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.log.elog.PLVELogsService;
import com.plv.foundationsdk.utils.PLVScreenUtils;
import com.plv.livescenes.document.model.PLVPPTStatus;
import com.plv.livescenes.linkmic.manager.PLVLinkMicConfig;
import com.plv.livescenes.log.player.PLVPlayerElog;
import com.plv.livescenes.model.PLVChatFunctionSwitchVO;
import com.plv.livescenes.video.api.IPLVLiveListenerEvent;
import com.plv.socket.event.chat.PLVRewardEvent;
import com.plv.thirdpart.blankj.utilcode.util.ScreenUtils;
import com.plv.thirdpart.blankj.utilcode.util.StringUtils;
import com.plv.thirdpart.blankj.utilcode.util.TimeUtils;
import com.plv.thirdpart.blankj.utilcode.util.ToastUtils;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import org.eclipse.jetty.util.URIUtil;

/* loaded from: classes3.dex */
public class PLVLCLiveMediaLayout extends FrameLayout implements IPLVLCMediaLayout {
    private static final String DEFAULT_COVER_IMAGE = "https://s1.videocc.net/default-img/channel/default-splash.png";
    private static final float RATIO_WH = 1.7777778f;
    private static final boolean SYNC_LANDSCAPE_CHATROOM_LAYOUT_VISIBILITY_WITH_DANMU = true;
    private static final String TAG = "PLVLCLiveVideoLayout";
    private PLVLCLiveAudioModeView audioModeView;
    private PLVLCChatLandscapeLayout chatLandscapeLayout;
    private String coverImage;
    private ImageView coverImageView;
    private IPLVLCDanmuController danmuController;
    private PLVLCDanmuWrapper danmuWrapper;
    private FrameLayout flLivePlayerSwitchView;
    private PLVLCFloatingWindow floatingWindow;
    private boolean isClickShowSubTab;
    private boolean isJoinLinkMic;
    private boolean isJoinRTC;
    private boolean isLandscape;
    private boolean isLowLatency;
    private boolean isOnlyAudio;
    private boolean isRewardEffectShow;
    private boolean isShowLandscapeRTCLayout;
    private int landscapeMarginRightForLinkMicLayout;
    private IPLVLCLandscapeMessageSender landscapeMessageSender;
    private PLVLCLightTipsView lightTipsView;
    private TextView livePlayerFloatingPlayingPlaceholderTv;
    private IPLVLivePlayerContract.ILivePlayerPresenter livePlayerPresenter;
    private IPLVLivePlayerContract.ILivePlayerView livePlayerView;
    private IPLVLiveRoomDataManager liveRoomDataManager;
    private String liveStartTime;
    private LinearLayout llAuxiliaryCountDown;
    private PLVLCVideoLoadingLayout loadingView;
    private PLVPlayerLogoView logoView;
    private IPLVMarqueeView marqueeView;
    private IPLVLCLiveMediaController mediaController;
    private PLVLCNetworkTipsView networkTipsView;
    private PLVPlaceHolderView noStreamView;
    private IPLVLCMediaLayout.OnViewActionListener onViewActionListener;
    private PLVPlaceHolderView playErrorView;
    private PLVSwitchViewAnchorLayout playerSwitchAnchor;
    private View playerView;
    private SVGAImageView rewardSvgaView;
    private ImageView screenshotIV;
    private CountDownTimer startTimeCountDown;
    private View stopStreamView;
    private PolyvAuxiliaryVideoview subVideoView;
    private PLVRewardSVGAHelper svgaHelper;
    private SVGAParser svgaParser;
    private TextView timeCountDownTv;
    private TextView tvCountDown;
    private PolyvLiveVideoView videoView;
    private PLVLCVolumeTipsView volumeTipsView;
    private IPLVWatermarkView watermarkView;

    public PLVLCLiveMediaLayout(@NonNull Context context) {
        this(context, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideScreenShotView() {
        this.screenshotIV.setVisibility(8);
    }

    private void initAudioModeView() {
        this.audioModeView.setOnChangeVideoModeListener(new PLVLCLiveAudioModeView.OnChangeVideoModeListener() { // from class: com.easefun.polyv.livecloudclass.modules.media.PLVLCLiveMediaLayout.5
            @Override // com.easefun.polyv.livecloudclass.modules.media.widget.PLVLCLiveAudioModeView.OnChangeVideoModeListener
            public void onClickPlayVideo() {
                PLVLCLiveMediaLayout.this.livePlayerPresenter.changeMediaPlayMode(0);
            }
        });
    }

    private void initChatLandscapeLayout() {
        this.chatLandscapeLayout.setOnRoomStatusListener(new PLVLCChatLandscapeLayout.OnRoomStatusListener() { // from class: com.easefun.polyv.livecloudclass.modules.media.PLVLCLiveMediaLayout.8
            @Override // com.easefun.polyv.livecloudclass.modules.chatroom.chatlandscape.PLVLCChatLandscapeLayout.OnRoomStatusListener
            public void onStatusChanged(boolean z2, boolean z3) {
                PLVLCLiveMediaLayout.this.mediaController.notifyChatroomStatusChanged(z2, z3);
                if (z2 || z3) {
                    PLVLCLiveMediaLayout.this.landscapeMessageSender.hideMessageSender();
                }
            }
        });
    }

    private void initDanmuView() {
        this.danmuController = new PLVLCDanmuFragment();
        ((AppCompatActivity) getContext()).getSupportFragmentManager().beginTransaction().add(R.id.danmu_ly, (Fragment) this.danmuController, "danmuFragment").commitAllowingStateLoss();
        PLVLCDanmuWrapper pLVLCDanmuWrapper = new PLVLCDanmuWrapper(this);
        this.danmuWrapper = pLVLCDanmuWrapper;
        pLVLCDanmuWrapper.setDanmuController(this.danmuController);
        PLVLCLandscapeMessageSendPanel pLVLCLandscapeMessageSendPanel = new PLVLCLandscapeMessageSendPanel((AppCompatActivity) getContext(), this);
        this.landscapeMessageSender = pLVLCLandscapeMessageSendPanel;
        pLVLCLandscapeMessageSendPanel.setOnSendMessageListener(new IPLVLCLandscapeMessageSender.OnSendMessageListener() { // from class: com.easefun.polyv.livecloudclass.modules.media.PLVLCLiveMediaLayout.3
            @Override // com.easefun.polyv.livecloudclass.modules.media.danmu.IPLVLCLandscapeMessageSender.OnSendMessageListener
            public void onSend(String str) {
                if (PLVLCLiveMediaLayout.this.onViewActionListener != null) {
                    Pair<Boolean, Integer> pairOnSendChatMessageAction = PLVLCLiveMediaLayout.this.onViewActionListener.onSendChatMessageAction(str);
                    if (((Boolean) pairOnSendChatMessageAction.first).booleanValue()) {
                        return;
                    }
                    ToastUtils.showShort(PLVLCLiveMediaLayout.this.getResources().getString(R.string.plv_chat_toast_send_msg_failed) + ": " + pairOnSendChatMessageAction.second);
                }
            }
        });
    }

    private void initFloatingPlayer() {
        PLVLCFloatingWindow pLVLCFloatingWindow = (PLVLCFloatingWindow) PLVDependManager.getInstance().get(PLVLCFloatingWindow.class);
        this.floatingWindow = pLVLCFloatingWindow;
        pLVLCFloatingWindow.bindContentView(this.playerSwitchAnchor);
        PLVFloatingPlayerManager.getInstance().getFloatingViewShowState().observe((LifecycleOwner) getContext(), new Observer<Boolean>() { // from class: com.easefun.polyv.livecloudclass.modules.media.PLVLCLiveMediaLayout.10
            @Override // androidx.lifecycle.Observer
            public void onChanged(@Nullable Boolean bool) {
                PLVLCLiveMediaLayout.this.livePlayerFloatingPlayingPlaceholderTv.setVisibility(bool != null && bool.booleanValue() ? 0 : 8);
            }
        });
    }

    private void initLayoutWH() {
        post(new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.media.PLVLCLiveMediaLayout.9
            @Override // java.lang.Runnable
            public void run() {
                ViewGroup.LayoutParams layoutParams = PLVLCLiveMediaLayout.this.getLayoutParams();
                layoutParams.width = -1;
                layoutParams.height = ScreenUtils.isPortrait() ? (int) (PLVLCLiveMediaLayout.this.getWidth() / PLVLCLiveMediaLayout.RATIO_WH) : -1;
                PLVLCLiveMediaLayout.this.setLayoutParams(layoutParams);
            }
        });
    }

    private void initLoadingView() {
        this.loadingView.bindVideoView(this.videoView);
    }

    private void initMediaController() {
        this.mediaController.setOnViewActionListener(new IPLVLCLiveMediaController.OnViewActionListener() { // from class: com.easefun.polyv.livecloudclass.modules.media.PLVLCLiveMediaLayout.4
            @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveMediaController.OnViewActionListener
            public boolean isRtcPausing() {
                if (PLVLCLiveMediaLayout.this.onViewActionListener != null) {
                    return PLVLCLiveMediaLayout.this.onViewActionListener.isRtcPausing();
                }
                return false;
            }

            @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveMediaController.OnViewActionListener
            public void onChangeLowLatencyMode(boolean z2) {
                PLVLCLiveMediaLayout.this.isLowLatency = z2;
                PLVLCLiveMediaLayout.this.livePlayerPresenter.startPlay(z2);
                if (z2) {
                    PLVELogsService.getInstance().addStaticsLog(PLVPlayerElog.class, PLVPlayerElog.Event.SWITCH_TO_NO_DELAY, " isLowLatency: " + z2, new String[0]);
                } else {
                    PLVELogsService.getInstance().addStaticsLog(PLVPlayerElog.class, PLVPlayerElog.Event.SWITCH_TO_DELAY, " isLowLatency: " + z2, new String[0]);
                }
                if (PLVLCLiveMediaLayout.this.onViewActionListener != null) {
                    PLVLCLiveMediaLayout.this.onViewActionListener.onWatchLowLatency(z2);
                }
                PLVLCLiveMediaLayout.this.networkTipsView.setIsLowLatency(z2);
            }

            @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveMediaController.OnViewActionListener
            public void onClickFloating() {
                if (PLVLCLiveMediaLayout.this.floatingWindow != null) {
                    PLVLCLiveMediaLayout.this.floatingWindow.showByUser(!PLVLCLiveMediaLayout.this.floatingWindow.isRequestingShowByUser());
                }
            }

            @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveMediaController.OnViewActionListener
            public void onClickShowOrHideSubTab(boolean z2) {
                PLVLCLiveMediaLayout.this.isClickShowSubTab = z2;
                if (PLVLCLiveMediaLayout.this.isJoinRTC && PLVScreenUtils.isLandscape(PLVLCLiveMediaLayout.this.getContext())) {
                    if (z2) {
                        PLVLCLiveMediaLayout.this.showLandscapeRTCLayout(true);
                    } else {
                        PLVLCLiveMediaLayout.this.showLandscapeRTCLayout(false);
                    }
                }
                if (PLVLCLiveMediaLayout.this.onViewActionListener != null) {
                    PLVLCLiveMediaLayout.this.onViewActionListener.onClickShowOrHideSubTab(z2);
                }
            }

            @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveMediaController.OnViewActionListener
            public void onPPTTurnPage(String str) {
                PLVLCLiveMediaLayout.this.onViewActionListener.onPPTTurnPage(str);
            }

            @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveMediaController.OnViewActionListener
            public void onRtcPauseResume(boolean z2) {
                if (PLVLCLiveMediaLayout.this.onViewActionListener != null) {
                    PLVLCLiveMediaLayout.this.onViewActionListener.onRtcPauseResume(z2);
                }
            }

            @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveMediaController.OnViewActionListener
            public void onSendLikesAction() {
                if (PLVLCLiveMediaLayout.this.onViewActionListener != null) {
                    PLVLCLiveMediaLayout.this.onViewActionListener.onSendLikesAction();
                }
            }

            @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveMediaController.OnViewActionListener
            public void onShow(boolean z2) {
                if (PLVLCLiveMediaLayout.this.onViewActionListener != null) {
                    PLVLCLiveMediaLayout.this.onViewActionListener.onShowMediaController(z2);
                }
            }

            @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveMediaController.OnViewActionListener
            public void onShowBulletinAction() {
                if (PLVLCLiveMediaLayout.this.onViewActionListener != null) {
                    PLVLCLiveMediaLayout.this.onViewActionListener.onShowBulletinAction();
                }
            }

            @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveMediaController.OnViewActionListener
            public void onShowRewardView() {
                if (PLVLCLiveMediaLayout.this.onViewActionListener != null) {
                    PLVLCLiveMediaLayout.this.onViewActionListener.onShowRewardAction();
                }
            }

            @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveMediaController.OnViewActionListener
            public void onStartSendMessageAction() {
                PLVLCLiveMediaLayout.this.landscapeMessageSender.openMessageSender();
            }
        });
    }

    private void initNetworkTipsLayout() {
        this.networkTipsView.setOnClickChangeNormalLatencyListener(new PLVLCNetworkTipsView.OnClickChangeNormalLatencyListener() { // from class: com.easefun.polyv.livecloudclass.modules.media.PLVLCLiveMediaLayout.7
            @Override // com.easefun.polyv.livecloudclass.modules.media.widget.PLVLCNetworkTipsView.OnClickChangeNormalLatencyListener
            public void onClickChangeNormalLatency() {
                PLVLCLiveMediaLayout.this.isLowLatency = false;
                PLVLCLiveMediaLayout.this.livePlayerPresenter.startPlay(false);
                if (PLVLCLiveMediaLayout.this.onViewActionListener != null) {
                    PLVLCLiveMediaLayout.this.onViewActionListener.onWatchLowLatency(false);
                }
                PLVLCLiveMediaLayout.this.mediaController.notifyLowLatencyUpdate(false);
            }
        });
    }

    private void initPlayErrorView() {
        this.playErrorView.setPlaceHolderImg(R.drawable.plv_bg_player_error_ic);
        this.playErrorView.setOnChangeLinesViewClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecloudclass.modules.media.PLVLCLiveMediaLayout.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PLVLCLiveMediaLayout.this.mediaController.showMoreLayout();
            }
        });
        this.playErrorView.setOnRefreshViewClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecloudclass.modules.media.PLVLCLiveMediaLayout.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PLVLCLiveMediaLayout.this.livePlayerPresenter.restartPlay();
            }
        });
    }

    private void initSwitchView() {
        this.playerSwitchAnchor.setOnSwitchListener(new PLVSwitchViewAnchorLayout.IPLVSwitchViewAnchorLayoutListener() { // from class: com.easefun.polyv.livecloudclass.modules.media.PLVLCLiveMediaLayout.6
            @Override // com.easefun.polyv.livecommon.ui.widget.PLVSwitchViewAnchorLayout.IPLVSwitchViewAnchorLayoutListener
            public void onSwitchBackAfter() {
                try {
                    View switchView = PLVLCLiveMediaLayout.this.playerSwitchAnchor.getSwitchView();
                    PLVCommonLog.d(PLVLCLiveMediaLayout.TAG, "onSwitchBackAfter-> childOfAnchor= " + switchView);
                    if (switchView == PLVLCLiveMediaLayout.this.flLivePlayerSwitchView) {
                        PLVLCLiveMediaLayout.this.flLivePlayerSwitchView.removeAllViews();
                        PLVLCLiveMediaLayout.this.videoView.addView(PLVLCLiveMediaLayout.this.playerView, 0);
                        PLVLCLiveMediaLayout.this.videoView.addView(PLVLCLiveMediaLayout.this.screenshotIV);
                        PLVLCLiveMediaLayout.this.videoView.addView(PLVLCLiveMediaLayout.this.audioModeView);
                        PLVLCLiveMediaLayout.this.videoView.addView(PLVLCLiveMediaLayout.this.coverImageView);
                        PLVLCLiveMediaLayout.this.videoView.addView(PLVLCLiveMediaLayout.this.logoView);
                        PLVLCLiveMediaLayout.this.videoView.addView(PLVLCLiveMediaLayout.this.loadingView);
                        PLVLCLiveMediaLayout.this.videoView.addView(PLVLCLiveMediaLayout.this.noStreamView);
                        PLVLCLiveMediaLayout.this.videoView.addView(PLVLCLiveMediaLayout.this.stopStreamView);
                    }
                } catch (IllegalAccessException e2) {
                    PLVCommonLog.exception(e2);
                }
            }

            @Override // com.easefun.polyv.livecommon.ui.widget.PLVSwitchViewAnchorLayout.IPLVSwitchViewAnchorLayoutListener
            public void onSwitchElsewhereBefore() {
                try {
                    View switchView = PLVLCLiveMediaLayout.this.playerSwitchAnchor.getSwitchView();
                    PLVCommonLog.d(PLVLCLiveMediaLayout.TAG, "onSwitchElsewhereBefore-> childOfAnchor= " + switchView);
                    if (switchView == PLVLCLiveMediaLayout.this.flLivePlayerSwitchView) {
                        PLVLCLiveMediaLayout.this.videoView.removeView(PLVLCLiveMediaLayout.this.playerView);
                        PLVLCLiveMediaLayout.this.videoView.removeView(PLVLCLiveMediaLayout.this.screenshotIV);
                        PLVLCLiveMediaLayout.this.videoView.removeView(PLVLCLiveMediaLayout.this.audioModeView);
                        PLVLCLiveMediaLayout.this.videoView.removeView(PLVLCLiveMediaLayout.this.coverImageView);
                        PLVLCLiveMediaLayout.this.videoView.removeView(PLVLCLiveMediaLayout.this.logoView);
                        PLVLCLiveMediaLayout.this.videoView.removeView(PLVLCLiveMediaLayout.this.loadingView);
                        PLVLCLiveMediaLayout.this.videoView.removeView(PLVLCLiveMediaLayout.this.noStreamView);
                        PLVLCLiveMediaLayout.this.videoView.removeView(PLVLCLiveMediaLayout.this.stopStreamView);
                        PLVLCLiveMediaLayout.this.flLivePlayerSwitchView.addView(PLVLCLiveMediaLayout.this.playerView);
                        PLVLCLiveMediaLayout.this.flLivePlayerSwitchView.addView(PLVLCLiveMediaLayout.this.screenshotIV);
                        PLVLCLiveMediaLayout.this.flLivePlayerSwitchView.addView(PLVLCLiveMediaLayout.this.audioModeView);
                        PLVLCLiveMediaLayout.this.flLivePlayerSwitchView.addView(PLVLCLiveMediaLayout.this.coverImageView);
                        PLVLCLiveMediaLayout.this.flLivePlayerSwitchView.addView(PLVLCLiveMediaLayout.this.logoView);
                        PLVLCLiveMediaLayout.this.flLivePlayerSwitchView.addView(PLVLCLiveMediaLayout.this.loadingView);
                        PLVLCLiveMediaLayout.this.flLivePlayerSwitchView.addView(PLVLCLiveMediaLayout.this.noStreamView);
                        PLVLCLiveMediaLayout.this.flLivePlayerSwitchView.addView(PLVLCLiveMediaLayout.this.stopStreamView);
                    }
                } catch (IllegalAccessException e2) {
                    PLVCommonLog.exception(e2);
                }
            }
        });
    }

    private void initVideoView() {
        this.noStreamView.setPlaceHolderImg(R.drawable.plvlc_bg_player_no_stream);
        this.noStreamView.setPlaceHolderText(getResources().getString(R.string.plv_player_video_live_no_stream));
        this.videoView.setSubVideoView(this.subVideoView);
        this.videoView.setAudioModeView(this.audioModeView);
        this.videoView.setPlayerBufferingIndicator(this.loadingView);
        this.videoView.setNoStreamIndicator(this.noStreamView);
        this.videoView.setStopStreamIndicator(this.stopStreamView);
        this.videoView.setMediaController(this.mediaController);
        this.marqueeView = (IPLVMarqueeView) ((Activity) getContext()).findViewById(R.id.polyv_marquee_view);
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.plvlc_live_player_layout, this);
        this.playerSwitchAnchor = (PLVSwitchViewAnchorLayout) findViewById(R.id.plvlc_playback_switch_anchor_player);
        this.flLivePlayerSwitchView = (FrameLayout) findViewById(R.id.plvlc_playback_fl_player_switch_view_parent);
        PolyvLiveVideoView polyvLiveVideoView = (PolyvLiveVideoView) findViewById(R.id.live_video_view);
        this.videoView = polyvLiveVideoView;
        this.playerView = polyvLiveVideoView.findViewById(PLVBaseVideoView.IJK_VIDEO_ID);
        this.subVideoView = (PolyvAuxiliaryVideoview) findViewById(R.id.sub_video_view);
        this.tvCountDown = (TextView) findViewById(R.id.auxiliary_tv_count_down);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.polyv_auxiliary_controller_ll_tips);
        this.llAuxiliaryCountDown = linearLayout;
        linearLayout.setVisibility(8);
        this.audioModeView = (PLVLCLiveAudioModeView) findViewById(R.id.audio_mode_ly);
        this.logoView = (PLVPlayerLogoView) findViewById(R.id.live_logo_view);
        this.loadingView = (PLVLCVideoLoadingLayout) findViewById(R.id.video_loading_view);
        this.noStreamView = (PLVPlaceHolderView) findViewById(R.id.no_stream_ly);
        this.playErrorView = (PLVPlaceHolderView) findViewById(R.id.play_error_ly);
        this.stopStreamView = findViewById(R.id.stop_stream_ly);
        this.lightTipsView = (PLVLCLightTipsView) findViewById(R.id.light_view);
        this.volumeTipsView = (PLVLCVolumeTipsView) findViewById(R.id.volume_view);
        this.screenshotIV = (ImageView) findViewById(R.id.screenshot_iv);
        this.timeCountDownTv = (TextView) findViewById(R.id.time_count_down_tv);
        this.mediaController = (IPLVLCLiveMediaController) findViewById(R.id.controller_view);
        this.chatLandscapeLayout = (PLVLCChatLandscapeLayout) findViewById(R.id.chat_landscape_ly);
        this.rewardSvgaView = (SVGAImageView) findViewById(R.id.plvlc_reward_svg);
        this.svgaParser = new SVGAParser(getContext());
        PLVRewardSVGAHelper pLVRewardSVGAHelper = new PLVRewardSVGAHelper();
        this.svgaHelper = pLVRewardSVGAHelper;
        pLVRewardSVGAHelper.init(this.rewardSvgaView, this.svgaParser);
        this.coverImageView = (ImageView) findViewById(R.id.plvlc_cover_image_view);
        this.watermarkView = (IPLVWatermarkView) findViewById(R.id.polyv_watermark_view);
        this.networkTipsView = (PLVLCNetworkTipsView) findViewById(R.id.network_tips_view);
        this.livePlayerFloatingPlayingPlaceholderTv = (TextView) findViewById(R.id.plvlc_live_player_floating_playing_placeholder_tv);
        PLVPlaceHolderView pLVPlaceHolderView = new PLVPlaceHolderView(getContext());
        pLVPlaceHolderView.setVisibility(0);
        pLVPlaceHolderView.setPlaceHolderText("");
        addView(pLVPlaceHolderView, 0);
        initVideoView();
        initPlayErrorView();
        initDanmuView();
        initMediaController();
        initAudioModeView();
        initLoadingView();
        initSwitchView();
        initNetworkTipsLayout();
        initChatLandscapeLayout();
        initLayoutWH();
        initFloatingPlayer();
    }

    private void observeLinkMicStatus(IPLVLivePlayerContract.ILivePlayerPresenter iLivePlayerPresenter) {
        iLivePlayerPresenter.getData().getLinkMicState().observe((LifecycleOwner) getContext(), new Observer<Pair<Boolean, Boolean>>() { // from class: com.easefun.polyv.livecloudclass.modules.media.PLVLCLiveMediaLayout.11
            @Override // androidx.lifecycle.Observer
            public void onChanged(@Nullable Pair<Boolean, Boolean> pair) {
                Object obj;
                if (pair == null || (obj = pair.first) == null) {
                    return;
                }
                PLVLCLiveMediaLayout.this.updateWhenLinkMicOpenStatusChanged(((Boolean) obj).booleanValue());
            }
        });
    }

    private void observeLiveRoomData() {
        this.liveRoomDataManager.getClassDetailVO().observe((LifecycleOwner) getContext(), new Observer<PLVStatefulData<PolyvLiveClassDetailVO>>() { // from class: com.easefun.polyv.livecloudclass.modules.media.PLVLCLiveMediaLayout.16
            @Override // androidx.lifecycle.Observer
            @SuppressLint({"SetTextI18n"})
            public void onChanged(@Nullable PLVStatefulData<PolyvLiveClassDetailVO> pLVStatefulData) {
                PolyvLiveClassDetailVO data;
                PLVLCLiveMediaLayout.this.liveRoomDataManager.getClassDetailVO().removeObserver(this);
                if (pLVStatefulData == null || !pLVStatefulData.isSuccess() || (data = pLVStatefulData.getData()) == null || data.getData() == null) {
                    return;
                }
                if (!data.getData().isLiveStatus()) {
                    String startTime = data.getData().getStartTime();
                    if (!StringUtils.isEmpty(startTime)) {
                        PLVLCLiveMediaLayout.this.startLiveTimeCountDown(startTime);
                    }
                }
                PLVLCLiveMediaLayout.this.mediaController.setVideoName(data.getData().getName());
                PLVLCLiveMediaLayout.this.coverImage = data.getData().getSplashImg();
                PLVLCLiveMediaLayout pLVLCLiveMediaLayout = PLVLCLiveMediaLayout.this;
                pLVLCLiveMediaLayout.updateCoverImage(pLVLCLiveMediaLayout.isOnlyAudio, PLVLCLiveMediaLayout.this.coverImage);
            }
        });
        this.liveRoomDataManager.getFunctionSwitchVO().observe((LifecycleOwner) getContext(), new Observer<PLVStatefulData<PolyvChatFunctionSwitchVO>>() { // from class: com.easefun.polyv.livecloudclass.modules.media.PLVLCLiveMediaLayout.17
            @Override // androidx.lifecycle.Observer
            public void onChanged(@Nullable PLVStatefulData<PolyvChatFunctionSwitchVO> pLVStatefulData) {
                PolyvChatFunctionSwitchVO data;
                List<PLVChatFunctionSwitchVO.DataBean> data2;
                PLVLCLiveMediaLayout.this.liveRoomDataManager.getFunctionSwitchVO().removeObserver(this);
                if (pLVStatefulData == null || !pLVStatefulData.isSuccess() || (data = pLVStatefulData.getData()) == null || data.getData() == null || (data2 = data.getData()) == null) {
                    return;
                }
                for (PLVChatFunctionSwitchVO.DataBean dataBean : data2) {
                    boolean zIsEnabled = dataBean.isEnabled();
                    String type = dataBean.getType();
                    type.hashCode();
                    if (type.equals(PLVChatFunctionSwitchVO.TYPE_SEND_FLOWERS_ENABLED)) {
                        PLVLCLiveMediaLayout.this.mediaController.setOnLikesSwitchEnabled(zIsEnabled);
                    }
                }
            }
        });
        this.liveRoomDataManager.getIsOnlyAudioEnabled().observe((LifecycleOwner) getContext(), new Observer<Boolean>() { // from class: com.easefun.polyv.livecloudclass.modules.media.PLVLCLiveMediaLayout.18
            @Override // androidx.lifecycle.Observer
            public void onChanged(@Nullable Boolean bool) {
                if (bool == null) {
                    bool = Boolean.FALSE;
                }
                PLVLCLiveMediaLayout.this.isOnlyAudio = bool.booleanValue();
                PLVLCLiveMediaLayout pLVLCLiveMediaLayout = PLVLCLiveMediaLayout.this;
                pLVLCLiveMediaLayout.updateCoverImage(pLVLCLiveMediaLayout.isOnlyAudio, PLVLCLiveMediaLayout.this.coverImage);
            }
        });
        this.liveRoomDataManager.getPointRewardEnableData().observe((LifecycleOwner) getContext(), new Observer<PLVStatefulData<Boolean>>() { // from class: com.easefun.polyv.livecloudclass.modules.media.PLVLCLiveMediaLayout.19
            @Override // androidx.lifecycle.Observer
            public void onChanged(@Nullable PLVStatefulData<Boolean> pLVStatefulData) {
                PLVLCLiveMediaLayout.this.liveRoomDataManager.getPointRewardEnableData().removeObserver(this);
                if (PLVLCLiveMediaLayout.this.mediaController != null) {
                    PLVLCLiveMediaLayout.this.mediaController.updateRewardView(pLVStatefulData.getData().booleanValue());
                }
            }
        });
        this.liveRoomDataManager.getRewardEventData().observe((LifecycleOwner) getContext(), new Observer<PLVRewardEvent>() { // from class: com.easefun.polyv.livecloudclass.modules.media.PLVLCLiveMediaLayout.20
            @Override // androidx.lifecycle.Observer
            public void onChanged(@Nullable PLVRewardEvent pLVRewardEvent) {
                if (pLVRewardEvent != null && PLVLCLiveMediaLayout.this.isLandscape && PLVLCLiveMediaLayout.this.isRewardEffectShow) {
                    PLVLCLiveMediaLayout.this.svgaHelper.addEvent(pLVRewardEvent);
                }
            }
        });
    }

    private void setLandscape() {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
        marginLayoutParams.width = -1;
        marginLayoutParams.height = -1;
        setLayoutParams(marginLayoutParams);
        if (this.isJoinRTC && this.isClickShowSubTab && this.isShowLandscapeRTCLayout) {
            showLandscapeRTCLayout(true);
        } else {
            showLandscapeRTCLayout(false);
        }
        this.rewardSvgaView.setVisibility(0);
    }

    private void setPortrait() {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
        marginLayoutParams.width = -1;
        int iMin = (int) (Math.min(ScreenUtils.getScreenHeight(), ScreenUtils.getScreenWidth()) / RATIO_WH);
        marginLayoutParams.height = iMin;
        setLayoutParams(marginLayoutParams);
        ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) this.playerSwitchAnchor.getLayoutParams();
        marginLayoutParams2.width = -1;
        marginLayoutParams2.height = iMin;
        marginLayoutParams2.rightMargin = 0;
        this.playerSwitchAnchor.setLayoutParams(marginLayoutParams2);
        ViewGroup.MarginLayoutParams marginLayoutParams3 = (ViewGroup.MarginLayoutParams) this.networkTipsView.getLayoutParams();
        marginLayoutParams3.rightMargin = 0;
        this.networkTipsView.setLayoutParams(marginLayoutParams3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showLandscapeRTCLayout(boolean z2) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.playerSwitchAnchor.getLayoutParams();
        marginLayoutParams.width = -1;
        marginLayoutParams.height = -1;
        marginLayoutParams.rightMargin = z2 ? this.landscapeMarginRightForLinkMicLayout : 0;
        this.playerSwitchAnchor.setLayoutParams(marginLayoutParams);
        ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) this.networkTipsView.getLayoutParams();
        marginLayoutParams2.rightMargin = z2 ? this.landscapeMarginRightForLinkMicLayout : 0;
        this.networkTipsView.setLayoutParams(marginLayoutParams2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showScreenShotView() {
        this.screenshotIV.setImageBitmap(this.livePlayerPresenter.screenshot());
        this.screenshotIV.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startLiveTimeCountDown(String str) {
        this.liveStartTime = str;
        if (TextUtils.isEmpty(str)) {
            this.timeCountDownTv.setVisibility(8);
            return;
        }
        CountDownTimer countDownTimer = new CountDownTimer(TimeUtils.string2Millis(str, new SimpleDateFormat(DateUtil.TIME_FORMAT_YMDHMS, Locale.getDefault())) - System.currentTimeMillis(), 1000L) { // from class: com.easefun.polyv.livecloudclass.modules.media.PLVLCLiveMediaLayout.15
            @Override // android.os.CountDownTimer
            public void onFinish() {
                PLVLCLiveMediaLayout.this.timeCountDownTv.setVisibility(8);
            }

            @Override // android.os.CountDownTimer
            public void onTick(long j2) {
                PLVLCLiveMediaLayout.this.timeCountDownTv.setText("倒计时：" + TimeUtils.toCountDownTime(j2));
                PLVLCLiveMediaLayout.this.timeCountDownTv.setVisibility(0);
            }
        };
        this.startTimeCountDown = countDownTimer;
        countDownTimer.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopLiveCountDown() {
        CountDownTimer countDownTimer = this.startTimeCountDown;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        TextView textView = this.timeCountDownTv;
        if (textView != null) {
            textView.setVisibility(8);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateCoverImage(boolean z2, String str) {
        if (!z2) {
            this.coverImageView.setVisibility(4);
            return;
        }
        if (TextUtils.isEmpty(str)) {
            str = DEFAULT_COVER_IMAGE;
        }
        if (str.startsWith("//")) {
            str = URIUtil.HTTPS_COLON + str;
        }
        this.coverImageView.setVisibility(0);
        PLVImageLoader.getInstance().loadImage(str, this.coverImageView);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void acceptNetworkQuality(int i2) {
        this.networkTipsView.acceptNetworkQuality(i2);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void addOnLinkMicStateListener(IPLVOnDataChangedListener<Pair<Boolean, Boolean>> iPLVOnDataChangedListener) {
        this.livePlayerPresenter.getData().getLinkMicState().observe((LifecycleOwner) getContext(), iPLVOnDataChangedListener);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void addOnPPTShowStateListener(IPLVOnDataChangedListener<Boolean> iPLVOnDataChangedListener) {
        this.livePlayerPresenter.getData().getPPTShowState().observe((LifecycleOwner) getContext(), iPLVOnDataChangedListener);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void addOnPlayInfoVOListener(IPLVOnDataChangedListener<PLVPlayInfoVO> iPLVOnDataChangedListener) {
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void addOnPlayerStateListener(IPLVOnDataChangedListener<PLVPlayerState> iPLVOnDataChangedListener) {
        this.livePlayerPresenter.getData().getPlayerState().observe((LifecycleOwner) getContext(), iPLVOnDataChangedListener);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void addOnSeekCompleteListener(IPLVOnDataChangedListener<Integer> iPLVOnDataChangedListener) {
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void addOnSeiDataListener(IPLVOnDataChangedListener<Long> iPLVOnDataChangedListener) {
        this.livePlayerPresenter.getData().getSeiData().observe((LifecycleOwner) getContext(), iPLVOnDataChangedListener);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void destroy() {
        IPLVLivePlayerContract.ILivePlayerPresenter iLivePlayerPresenter = this.livePlayerPresenter;
        if (iLivePlayerPresenter != null) {
            iLivePlayerPresenter.destroy();
        }
        IPLVLCLiveMediaController iPLVLCLiveMediaController = this.mediaController;
        if (iPLVLCLiveMediaController != null) {
            iPLVLCLiveMediaController.clean();
        }
        PLVLCDanmuWrapper pLVLCDanmuWrapper = this.danmuWrapper;
        if (pLVLCDanmuWrapper != null) {
            pLVLCDanmuWrapper.release();
        }
        IPLVLCDanmuController iPLVLCDanmuController = this.danmuController;
        if (iPLVLCDanmuController != null) {
            iPLVLCDanmuController.release();
        }
        IPLVLCLandscapeMessageSender iPLVLCLandscapeMessageSender = this.landscapeMessageSender;
        if (iPLVLCLandscapeMessageSender != null) {
            iPLVLCLandscapeMessageSender.dismiss();
        }
        stopLiveCountDown();
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public TextView getCardEnterCdView() {
        return this.mediaController.getLandscapeController().getCardEnterCdView();
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public PLVTriangleIndicateTextView getCardEnterTipsView() {
        return this.mediaController.getLandscapeController().getCardEnterTipsView();
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public ImageView getCardEnterView() {
        return this.mediaController.getLandscapeController().getCardEnterView();
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public PLVLCChatLandscapeLayout getChatLandscapeLayout() {
        return this.chatLandscapeLayout;
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public int getDuration() {
        return 0;
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public IPLVLCLiveLandscapePlayerController getLandscapeControllerView() {
        return this.mediaController.getLandscapeController();
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public PLVPlayerLogoView getLogoView() {
        return this.logoView;
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public PLVSwitchViewAnchorLayout getPlayerSwitchView() {
        return this.playerSwitchAnchor;
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public String getSessionId() {
        return null;
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public float getSpeed() {
        return 0.0f;
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public int getVideoCurrentPosition() {
        return 0;
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public int getVolume() {
        return this.livePlayerPresenter.getVolume();
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public boolean hideController() {
        boolean zIsShowing = this.mediaController.isShowing();
        this.mediaController.hide();
        return zIsShowing;
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void init(@NonNull IPLVLiveRoomDataManager iPLVLiveRoomDataManager) {
        this.liveRoomDataManager = iPLVLiveRoomDataManager;
        this.floatingWindow.setLiveRoomData(iPLVLiveRoomDataManager);
        observeLiveRoomData();
        PLVLivePlayerPresenter pLVLivePlayerPresenter = new PLVLivePlayerPresenter(iPLVLiveRoomDataManager);
        this.livePlayerPresenter = pLVLivePlayerPresenter;
        pLVLivePlayerPresenter.registerView(this.livePlayerView);
        this.livePlayerPresenter.init();
        observeLinkMicStatus(this.livePlayerPresenter);
        this.mediaController.setLivePlayerPresenter(this.livePlayerPresenter);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public boolean isPlaying() {
        return this.livePlayerPresenter.isPlaying();
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void notifyRTCPrepared() {
        this.videoView.rtcPrepared();
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public boolean onBackPressed() {
        if (!ScreenUtils.isLandscape()) {
            return false;
        }
        PLVOrientationManager.getInstance().setPortrait((Activity) getContext());
        return true;
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        if (configuration.orientation == 2) {
            setLandscape();
            this.isLandscape = true;
        } else {
            setPortrait();
            this.isLandscape = false;
        }
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void onTurnPageLayoutChange(boolean z2) {
        this.mediaController.setTurnPageLayoutStatus(z2);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void pause() {
        this.livePlayerPresenter.pause();
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void resume() {
        this.livePlayerPresenter.resume();
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void seekTo(int i2, int i3) {
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void sendDanmaku(CharSequence charSequence) {
        this.danmuController.sendDanmaku(charSequence);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void setChatPlaybackEnabled(boolean z2) {
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void setHideLandscapeRTCLayout() {
        this.isShowLandscapeRTCLayout = false;
        if (this.isLandscape) {
            showLandscapeRTCLayout(false);
        } else {
            PLVCommonLog.d(TAG, "PLVLCLiveMediaLayout.setHideLandscapeRTCLayout-->isLandscape=false. We do noting when it is portrait");
        }
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void setLandscapeControllerView(@NonNull IPLVLCLiveLandscapePlayerController iPLVLCLiveLandscapePlayerController) {
        this.mediaController.setLandscapeController(iPLVLCLiveLandscapePlayerController);
        final ImageView danmuSwitchView = iPLVLCLiveLandscapePlayerController.getDanmuSwitchView();
        danmuSwitchView.setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecloudclass.modules.media.PLVLCLiveMediaLayout.13
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PLVLCLiveMediaLayout.this.danmuWrapper.dispatchDanmuSwitchOnClicked(view);
                PLVLCLiveMediaLayout.this.mediaController.dispatchDanmuSwitchOnClicked(view);
                PLVLCLiveMediaLayout.this.chatLandscapeLayout.toggle(!danmuSwitchView.isSelected());
            }
        });
        this.danmuWrapper.setDanmuSwitchLandView(danmuSwitchView);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void setLandscapeRewardEffectVisibility(boolean z2) {
        this.isRewardEffectShow = z2;
        if (z2) {
            this.rewardSvgaView.setVisibility(0);
        } else {
            this.svgaHelper.clear();
            this.rewardSvgaView.setVisibility(4);
        }
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void setOnRTCPlayEventListener(IPLVLiveListenerEvent.OnRTCPlayEventListener onRTCPlayEventListener) {
        this.videoView.setOnRTCPlayEventListener(onRTCPlayEventListener);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void setOnViewActionListener(IPLVLCMediaLayout.OnViewActionListener onViewActionListener) {
        this.onViewActionListener = onViewActionListener;
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void setPPTView(IPolyvPPTView iPolyvPPTView) {
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void setShowLandscapeRTCLayout() {
        this.isShowLandscapeRTCLayout = true;
        if (this.isLandscape) {
            showLandscapeRTCLayout(true);
        } else {
            PLVCommonLog.d(TAG, "PLVLCLiveMediaLayout.setShowLandscapeRTCLayout-->isLandscape=false. We'll wait for portrait to show landscape rtc layout");
        }
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void setSpeed(float f2) {
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void setVolume(int i2) {
        this.livePlayerPresenter.setVolume(i2);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void showController() {
        this.mediaController.show();
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void startPlay() {
        post(new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.media.PLVLCLiveMediaLayout.12
            @Override // java.lang.Runnable
            public void run() {
                PLVLCLiveMediaLayout.this.livePlayerPresenter.startPlay(PLVLCLiveMediaLayout.this.isLowLatency);
                if (PLVLCLiveMediaLayout.this.onViewActionListener != null) {
                    PLVLCLiveMediaLayout.this.onViewActionListener.onWatchLowLatency(PLVLCLiveMediaLayout.this.isLowLatency);
                }
                PLVLCLiveMediaLayout.this.networkTipsView.setIsLowLatency(PLVLCLiveMediaLayout.this.isLowLatency);
            }
        });
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void stop() {
        this.livePlayerPresenter.stop();
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void updateOnClickCloseFloatingView() {
        this.mediaController.show();
        this.mediaController.updateOnClickCloseFloatingView();
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void updatePPTStatusChange(PLVPPTStatus pLVPPTStatus) {
        this.mediaController.updatePPTStatusChange(pLVPPTStatus);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void updatePlayBackVideVid(String str) {
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void updatePlayBackVideVidAndPlay(String str) {
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void updateViewerCount(long j2) {
        this.mediaController.updateViewerCount(j2);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void updateWhenJoinLinkMic() {
        this.isJoinLinkMic = true;
        this.videoView.setIsLinkMic(true);
        this.mediaController.updateWhenJoinLinkMic(this.liveRoomDataManager.isSupportRTC());
        this.networkTipsView.setIsLinkMic(true);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void updateWhenJoinRTC(int i2) {
        this.isJoinRTC = true;
        this.landscapeMarginRightForLinkMicLayout = i2;
        this.mediaController.updateWhenJoinRtc(this.liveRoomDataManager.isSupportRTC());
        if (this.liveRoomDataManager.isSupportRTC()) {
            stop();
        } else {
            this.livePlayerPresenter.setPlayerVolume(0);
        }
        this.livePlayerPresenter.setNeedGestureDetector(false);
        this.mediaController.show();
        if (PLVScreenUtils.isPortrait(getContext())) {
            setPortrait();
        } else {
            setLandscape();
        }
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void updateWhenLeaveLinkMic() {
        this.isJoinLinkMic = false;
        this.videoView.setIsLinkMic(false);
        this.mediaController.updateWhenLeaveLinkMic();
        this.networkTipsView.setIsLinkMic(true);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void updateWhenLeaveRTC() {
        this.isJoinRTC = false;
        this.landscapeMarginRightForLinkMicLayout = 0;
        this.mediaController.updateWhenLeaveRtc();
        if (this.liveRoomDataManager.isSupportRTC()) {
            startPlay();
        }
        this.livePlayerPresenter.setNeedGestureDetector(true);
        this.livePlayerPresenter.setPlayerVolume(100);
        if (PLVScreenUtils.isPortrait(getContext())) {
            setPortrait();
        } else {
            setLandscape();
        }
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void updateWhenLinkMicOpenStatusChanged(boolean z2) {
        this.mediaController.updateWhenLinkMicOpenOrClose(z2);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void updateWhenRequestJoinLinkMic(boolean z2) {
        this.mediaController.updateWhenRequestJoinLinkMic(z2);
    }

    public PLVLCLiveMediaLayout(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PLVLCLiveMediaLayout(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.marqueeView = null;
        this.watermarkView = null;
        this.isLowLatency = PLVLinkMicConfig.getInstance().isLowLatencyWatchEnabled();
        this.isClickShowSubTab = true;
        this.isShowLandscapeRTCLayout = false;
        this.isRewardEffectShow = true;
        this.isOnlyAudio = false;
        this.coverImage = DEFAULT_COVER_IMAGE;
        this.livePlayerView = new PLVAbsLivePlayerView() { // from class: com.easefun.polyv.livecloudclass.modules.media.PLVLCLiveMediaLayout.14
            @Override // com.easefun.polyv.livecommon.module.modules.player.live.view.PLVAbsLivePlayerView, com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerView
            public View getBufferingIndicator() {
                return PLVLCLiveMediaLayout.this.loadingView;
            }

            @Override // com.easefun.polyv.livecommon.module.modules.player.live.view.PLVAbsLivePlayerView, com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerView
            public PolyvLiveVideoView getLiveVideoView() {
                return PLVLCLiveMediaLayout.this.videoView;
            }

            @Override // com.easefun.polyv.livecommon.module.modules.player.live.view.PLVAbsLivePlayerView, com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerView
            public PLVPlayerLogoView getLogo() {
                return PLVLCLiveMediaLayout.this.logoView;
            }

            @Override // com.easefun.polyv.livecommon.module.modules.player.live.view.PLVAbsLivePlayerView, com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerView
            public IPLVMarqueeView getMarqueeView() {
                return PLVLCLiveMediaLayout.this.marqueeView;
            }

            @Override // com.easefun.polyv.livecommon.module.modules.player.live.view.PLVAbsLivePlayerView, com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerView
            public View getNoStreamIndicator() {
                return PLVLCLiveMediaLayout.this.noStreamView;
            }

            @Override // com.easefun.polyv.livecommon.module.modules.player.live.view.PLVAbsLivePlayerView, com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerView
            public View getPlayErrorIndicator() {
                return PLVLCLiveMediaLayout.this.playErrorView;
            }

            @Override // com.easefun.polyv.livecommon.module.modules.player.live.view.PLVAbsLivePlayerView, com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerView
            public PolyvAuxiliaryVideoview getSubVideoView() {
                return PLVLCLiveMediaLayout.this.subVideoView;
            }

            @Override // com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerView
            public IPLVWatermarkView getWatermarkView() {
                return PLVLCLiveMediaLayout.this.watermarkView;
            }

            @Override // com.easefun.polyv.livecommon.module.modules.player.live.view.PLVAbsLivePlayerView, com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerView
            public boolean onLightChanged(int i3, boolean z2) {
                PLVLCLiveMediaLayout.this.lightTipsView.setLightPercent(i3, z2);
                return true;
            }

            @Override // com.easefun.polyv.livecommon.module.modules.player.live.view.PLVAbsLivePlayerView, com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerView
            public void onLiveEnd() {
                super.onLiveEnd();
                Log.i(PLVLCLiveMediaLayout.TAG, "onLiveEnd: ");
                PLVLCLiveMediaLayout pLVLCLiveMediaLayout = PLVLCLiveMediaLayout.this;
                pLVLCLiveMediaLayout.startLiveTimeCountDown(pLVLCLiveMediaLayout.liveStartTime);
            }

            @Override // com.easefun.polyv.livecommon.module.modules.player.live.view.PLVAbsLivePlayerView, com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerView
            public void onLoadSlow(int i3, boolean z2) {
                super.onLoadSlow(i3, z2);
                PLVPlayErrorMessageUtils.showOnLoadSlow(PLVLCLiveMediaLayout.this.playErrorView, PLVLCLiveMediaLayout.this.liveRoomDataManager.getConfig().isLive());
            }

            @Override // com.easefun.polyv.livecommon.module.modules.player.live.view.PLVAbsLivePlayerView, com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerView
            public void onLowLatencyNetworkQuality(int i3) {
                PLVLCLiveMediaLayout.this.networkTipsView.acceptNetworkQuality(i3);
            }

            @Override // com.easefun.polyv.livecommon.module.modules.player.live.view.PLVAbsLivePlayerView, com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerView
            public boolean onNetworkRecover() {
                return PLVLCLiveMediaLayout.this.isJoinRTC;
            }

            @Override // com.easefun.polyv.livecommon.module.modules.player.live.view.PLVAbsLivePlayerView, com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerView
            public void onNoLiveAtPresent() {
                super.onNoLiveAtPresent();
                ToastUtils.showShort(R.string.plv_player_toast_no_live);
            }

            @Override // com.easefun.polyv.livecommon.module.modules.player.live.view.PLVAbsLivePlayerView, com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerView
            public void onOnlyAudio(boolean z2) {
                super.onOnlyAudio(z2);
                PLVLCLiveMediaLayout.this.mediaController.updateWhenOnlyAudio(z2);
            }

            @Override // com.easefun.polyv.livecommon.module.modules.player.live.view.PLVAbsLivePlayerView, com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerView
            public void onPlayError(PolyvPlayError polyvPlayError, String str) {
                super.onPlayError(polyvPlayError, str);
                PLVPlayErrorMessageUtils.showOnPlayError(PLVLCLiveMediaLayout.this.playErrorView, polyvPlayError, PLVLCLiveMediaLayout.this.liveRoomDataManager.getConfig().isLive());
                PLVCommonLog.e(PLVLCLiveMediaLayout.TAG, str);
            }

            @Override // com.easefun.polyv.livecommon.module.modules.player.live.view.PLVAbsLivePlayerView, com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerView
            public void onPrepared(int i3) {
                super.onPrepared(i3);
                PLVLCLiveMediaLayout.this.hideScreenShotView();
                PLVLCLiveMediaLayout.this.stopLiveCountDown();
                PLVLCLiveMediaLayout.this.mediaController.updateWhenVideoViewPrepared();
                PLVLCLiveMediaLayout.this.mediaController.show();
            }

            @Override // com.easefun.polyv.livecommon.module.modules.player.live.view.PLVAbsLivePlayerView, com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerView
            public void onRestartPlay() {
                super.onRestartPlay();
                PLVLCLiveMediaLayout.this.showScreenShotView();
            }

            @Override // com.easefun.polyv.livecommon.module.modules.player.live.view.PLVAbsLivePlayerView, com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerView
            public void onServerDanmuOpen(boolean z2) {
                super.onServerDanmuOpen(z2);
                PLVLCLiveMediaLayout.this.danmuWrapper.setOnServerDanmuOpen(z2);
            }

            @Override // com.easefun.polyv.livecommon.module.modules.player.live.view.PLVAbsLivePlayerView, com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerView
            public void onShowPPTView(int i3) {
                super.onShowPPTView(i3);
                PLVLCLiveMediaLayout.this.mediaController.setServerEnablePPT(i3 == 0);
            }

            @Override // com.easefun.polyv.livecommon.module.modules.player.live.view.PLVAbsLivePlayerView, com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerView
            public void onSubVideoViewClick(boolean z2) {
                super.onSubVideoViewClick(z2);
                if (z2) {
                    return;
                }
                PLVLCLiveMediaLayout.this.mediaController.updateWhenSubVideoViewClick(z2);
                if (PLVLCLiveMediaLayout.this.mediaController.isShowing()) {
                    PLVLCLiveMediaLayout.this.mediaController.hide();
                } else {
                    PLVLCLiveMediaLayout.this.mediaController.show();
                }
            }

            @Override // com.easefun.polyv.livecommon.module.modules.player.live.view.PLVAbsLivePlayerView, com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerView
            public void onSubVideoViewCountDown(boolean z2, int i3, int i4, int i5) {
                if (z2) {
                    PLVLCLiveMediaLayout.this.llAuxiliaryCountDown.setVisibility(0);
                    PLVLCLiveMediaLayout.this.tvCountDown.setText("广告：" + i4 + "s");
                }
            }

            @Override // com.easefun.polyv.livecommon.module.modules.player.live.view.PLVAbsLivePlayerView, com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerView
            public void onSubVideoViewLoadImage(String str, ImageView imageView) {
                PLVImageLoader.getInstance().loadImage(PLVLCLiveMediaLayout.this.subVideoView.getContext(), str, imageView);
                ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
                layoutParams.width = -1;
                layoutParams.height = -1;
                imageView.setLayoutParams(layoutParams);
            }

            @Override // com.easefun.polyv.livecommon.module.modules.player.live.view.PLVAbsLivePlayerView, com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerView
            public void onSubVideoViewVisiblityChanged(boolean z2, boolean z3) {
                if (!z2) {
                    PLVLCLiveMediaLayout.this.llAuxiliaryCountDown.setVisibility(8);
                } else {
                    if (z3) {
                        return;
                    }
                    PLVLCLiveMediaLayout.this.llAuxiliaryCountDown.setVisibility(8);
                }
            }

            @Override // com.easefun.polyv.livecommon.module.modules.player.live.view.PLVAbsLivePlayerView, com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract.ILivePlayerView
            public boolean onVolumeChanged(int i3, boolean z2) {
                PLVLCLiveMediaLayout.this.volumeTipsView.setVolumePercent(i3, z2);
                return true;
            }
        };
        initView();
    }
}
