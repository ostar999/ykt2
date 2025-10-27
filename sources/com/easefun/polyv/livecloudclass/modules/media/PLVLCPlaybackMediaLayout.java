package com.easefun.polyv.livecloudclass.modules.media;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
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
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.OnLifecycleEvent;
import com.easefun.polyv.businesssdk.api.auxiliary.PolyvAuxiliaryVideoview;
import com.easefun.polyv.businesssdk.api.common.player.PolyvPlayError;
import com.easefun.polyv.businesssdk.api.common.ppt.IPolyvPPTView;
import com.easefun.polyv.livecloudclass.R;
import com.easefun.polyv.livecloudclass.modules.chatroom.chatlandscape.PLVLCChatLandscapeLayout;
import com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout;
import com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveLandscapePlayerController;
import com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCPlaybackMediaController;
import com.easefun.polyv.livecloudclass.modules.media.danmu.IPLVLCDanmuController;
import com.easefun.polyv.livecloudclass.modules.media.danmu.IPLVLCLandscapeMessageSender;
import com.easefun.polyv.livecloudclass.modules.media.danmu.PLVLCDanmuFragment;
import com.easefun.polyv.livecloudclass.modules.media.danmu.PLVLCDanmuWrapper;
import com.easefun.polyv.livecloudclass.modules.media.danmu.PLVLCLandscapeMessageSendPanel;
import com.easefun.polyv.livecloudclass.modules.media.widget.PLVLCLightTipsView;
import com.easefun.polyv.livecloudclass.modules.media.widget.PLVLCProgressTipsView;
import com.easefun.polyv.livecloudclass.modules.media.widget.PLVLCVideoLoadingLayout;
import com.easefun.polyv.livecloudclass.modules.media.widget.PLVLCVolumeTipsView;
import com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager;
import com.easefun.polyv.livecommon.module.data.PLVStatefulData;
import com.easefun.polyv.livecommon.module.modules.marquee.IPLVMarqueeView;
import com.easefun.polyv.livecommon.module.modules.marquee.PLVMarqueeView;
import com.easefun.polyv.livecommon.module.modules.player.PLVPlayErrorMessageUtils;
import com.easefun.polyv.livecommon.module.modules.player.PLVPlayerState;
import com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract;
import com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.PLVPlaybackPlayerPresenter;
import com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.data.PLVPlayInfoVO;
import com.easefun.polyv.livecommon.module.modules.player.playback.view.PLVAbsPlaybackPlayerView;
import com.easefun.polyv.livecommon.module.modules.watermark.IPLVWatermarkView;
import com.easefun.polyv.livecommon.module.modules.watermark.PLVWatermarkView;
import com.easefun.polyv.livecommon.module.utils.listener.IPLVOnDataChangedListener;
import com.easefun.polyv.livecommon.module.utils.rotaion.PLVOrientationManager;
import com.easefun.polyv.livecommon.ui.util.PLVViewUtil;
import com.easefun.polyv.livecommon.ui.widget.PLVPlaceHolderView;
import com.easefun.polyv.livecommon.ui.widget.PLVPlayerLogoView;
import com.easefun.polyv.livecommon.ui.widget.PLVPlayerRetryLayout;
import com.easefun.polyv.livecommon.ui.widget.PLVSwitchViewAnchorLayout;
import com.easefun.polyv.livecommon.ui.widget.PLVTriangleIndicateTextView;
import com.easefun.polyv.livecommon.ui.widget.roundview.PLVRoundRectLayout;
import com.easefun.polyv.livescenes.model.PolyvChatFunctionSwitchVO;
import com.easefun.polyv.livescenes.playback.video.PolyvPlaybackVideoView;
import com.plv.business.api.common.player.PLVBaseVideoView;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.utils.PLVTimeUnit;
import com.plv.foundationsdk.utils.PLVTimeUtils;
import com.plv.livescenes.document.model.PLVPPTStatus;
import com.plv.livescenes.model.PLVChatFunctionSwitchVO;
import com.plv.livescenes.video.api.IPLVLiveListenerEvent;
import com.plv.thirdpart.blankj.utilcode.util.ConvertUtils;
import com.plv.thirdpart.blankj.utilcode.util.ScreenUtils;
import com.plv.thirdpart.blankj.utilcode.util.ToastUtils;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVLCPlaybackMediaLayout extends FrameLayout implements IPLVLCMediaLayout, LifecycleObserver {
    private static final boolean AUTO_PAUSE_WHEN_ENTER_BACKGROUND = false;
    private static final int MAX_RETRY_COUNT = 3;
    private static final float RATIO_WH = 1.7777778f;
    private static final boolean SYNC_LANDSCAPE_CHATROOM_LAYOUT_VISIBILITY_WITH_DANMU = true;
    private static final String TAG = "PLVLCPlaybackMediaLayout";
    private PLVLCChatLandscapeLayout chatLandscapeLayout;
    private IPLVLCDanmuController danmuController;
    private PLVLCDanmuWrapper danmuWrapper;
    private FrameLayout flPlayerSwitchViewParent;
    private IPLVLCLandscapeMessageSender landscapeMessageSender;
    private PLVLCLightTipsView lightTipsView;
    private IPLVLiveRoomDataManager liveRoomDataManager;
    private LinearLayout llAuxiliaryCountDown;
    private PLVLCVideoLoadingLayout loadingLayout;
    private PLVPlayerLogoView logoView;
    private PLVMarqueeView marqueeView;
    private IPLVLCPlaybackMediaController mediaController;
    private PLVPlaceHolderView noStreamView;
    private IPLVLCMediaLayout.OnViewActionListener onViewActionListener;
    private boolean pausingOnEnterBackground;
    private PLVPlaceHolderView playErrorView;
    private PLVRoundRectLayout playbackAutoContinueSeekTimeHintLayout;
    private TextView playbackAutoContinueSeekTimeTv;
    private IPLVPlaybackPlayerContract.IPlaybackPlayerPresenter playbackPlayerPresenter;
    private IPLVPlaybackPlayerContract.IPlaybackPlayerView playbackPlayerView;
    private PLVPlayerRetryLayout playerRetryLayout;
    private View playerView;
    private PLVLCProgressTipsView progressTipsView;
    private PolyvAuxiliaryVideoview subVideoView;
    private PLVSwitchViewAnchorLayout switchAnchorPlayer;
    private TextView tvCountDown;
    private PolyvPlaybackVideoView videoView;
    private PLVLCVolumeTipsView volumeTipsView;
    private PLVWatermarkView watermarkView;

    public PLVLCPlaybackMediaLayout(@NonNull Context context) {
        this(context, null);
    }

    private void initChatLandscapeLayout() {
        this.chatLandscapeLayout.setOnRoomStatusListener(new PLVLCChatLandscapeLayout.OnRoomStatusListener() { // from class: com.easefun.polyv.livecloudclass.modules.media.PLVLCPlaybackMediaLayout.7
            @Override // com.easefun.polyv.livecloudclass.modules.chatroom.chatlandscape.PLVLCChatLandscapeLayout.OnRoomStatusListener
            public void onStatusChanged(boolean z2, boolean z3) {
                PLVLCPlaybackMediaLayout.this.mediaController.notifyChatroomStatusChanged(z2, z3);
                if (z2 || z3) {
                    PLVLCPlaybackMediaLayout.this.landscapeMessageSender.hideMessageSender();
                }
            }
        });
    }

    private void initDanmuView() {
        this.danmuController = new PLVLCDanmuFragment();
        ((AppCompatActivity) getContext()).getSupportFragmentManager().beginTransaction().add(R.id.plvlc_danmu_ly, (Fragment) this.danmuController, "danmuFragment").commitAllowingStateLoss();
        PLVLCDanmuWrapper pLVLCDanmuWrapper = new PLVLCDanmuWrapper(this);
        this.danmuWrapper = pLVLCDanmuWrapper;
        pLVLCDanmuWrapper.setDanmuController(this.danmuController);
        final View landscapeDanmuSwitchView = this.mediaController.getLandscapeDanmuSwitchView();
        landscapeDanmuSwitchView.setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecloudclass.modules.media.PLVLCPlaybackMediaLayout.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PLVLCPlaybackMediaLayout.this.danmuWrapper.dispatchDanmuSwitchOnClicked(view);
                PLVLCPlaybackMediaLayout.this.mediaController.dispatchDanmuSwitchOnClicked(view);
                PLVLCPlaybackMediaLayout.this.chatLandscapeLayout.toggle(!landscapeDanmuSwitchView.isSelected());
            }
        });
        this.danmuWrapper.setDanmuSwitchLandView(landscapeDanmuSwitchView);
        PLVLCLandscapeMessageSendPanel pLVLCLandscapeMessageSendPanel = new PLVLCLandscapeMessageSendPanel((AppCompatActivity) getContext(), this);
        this.landscapeMessageSender = pLVLCLandscapeMessageSendPanel;
        pLVLCLandscapeMessageSendPanel.setOnSendMessageListener(new IPLVLCLandscapeMessageSender.OnSendMessageListener() { // from class: com.easefun.polyv.livecloudclass.modules.media.PLVLCPlaybackMediaLayout.3
            @Override // com.easefun.polyv.livecloudclass.modules.media.danmu.IPLVLCLandscapeMessageSender.OnSendMessageListener
            public void onSend(String str) {
                if (PLVLCPlaybackMediaLayout.this.onViewActionListener != null) {
                    Pair<Boolean, Integer> pairOnSendChatMessageAction = PLVLCPlaybackMediaLayout.this.onViewActionListener.onSendChatMessageAction(str);
                    if (((Boolean) pairOnSendChatMessageAction.first).booleanValue()) {
                        return;
                    }
                    ToastUtils.showShort(PLVLCPlaybackMediaLayout.this.getResources().getString(R.string.plv_chat_toast_send_msg_failed) + ": " + pairOnSendChatMessageAction.second);
                }
            }
        });
    }

    private void initLayoutWH() {
        post(new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.media.PLVLCPlaybackMediaLayout.8
            @Override // java.lang.Runnable
            public void run() {
                ViewGroup.LayoutParams layoutParams = PLVLCPlaybackMediaLayout.this.getLayoutParams();
                layoutParams.width = -1;
                layoutParams.height = ScreenUtils.isPortrait() ? (int) (PLVLCPlaybackMediaLayout.this.getWidth() / PLVLCPlaybackMediaLayout.RATIO_WH) : -1;
                PLVLCPlaybackMediaLayout.this.setLayoutParams(layoutParams);
            }
        });
    }

    private void initLoadingView() {
        this.loadingLayout.bindVideoView(this.videoView);
    }

    private void initMediaController() {
        this.mediaController.setOnViewActionListener(new IPLVLCPlaybackMediaController.OnViewActionListener() { // from class: com.easefun.polyv.livecloudclass.modules.media.PLVLCPlaybackMediaLayout.4
            @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCPlaybackMediaController.OnViewActionListener
            public void onClickShowOrHideSubTab(boolean z2) {
                if (PLVLCPlaybackMediaLayout.this.onViewActionListener != null) {
                    PLVLCPlaybackMediaLayout.this.onViewActionListener.onClickShowOrHideSubTab(z2);
                }
            }

            @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCPlaybackMediaController.OnViewActionListener
            public void onSendLikesAction() {
                if (PLVLCPlaybackMediaLayout.this.onViewActionListener != null) {
                    PLVLCPlaybackMediaLayout.this.onViewActionListener.onSendLikesAction();
                }
            }

            @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCPlaybackMediaController.OnViewActionListener
            public void onStartSendMessageAction() {
                PLVLCPlaybackMediaLayout.this.landscapeMessageSender.openMessageSender();
            }
        });
    }

    private void initPlayErrorView() {
        this.playErrorView.setPlaceHolderImg(R.drawable.plv_bg_player_error_ic);
        this.playErrorView.setOnRefreshViewClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecloudclass.modules.media.PLVLCPlaybackMediaLayout.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PLVLCPlaybackMediaLayout.this.playbackPlayerPresenter.startPlay();
            }
        });
    }

    private void initRetryView() {
        this.playerRetryLayout.setOnClickPlayerRetryListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecloudclass.modules.media.PLVLCPlaybackMediaLayout.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (PLVLCPlaybackMediaLayout.this.playbackPlayerPresenter != null) {
                    PLVLCPlaybackMediaLayout.this.playbackPlayerPresenter.startPlay();
                }
            }
        });
    }

    private void initSwitchView() {
        this.switchAnchorPlayer.setOnSwitchListener(new PLVSwitchViewAnchorLayout.IPLVSwitchViewAnchorLayoutListener() { // from class: com.easefun.polyv.livecloudclass.modules.media.PLVLCPlaybackMediaLayout.6
            @Override // com.easefun.polyv.livecommon.ui.widget.PLVSwitchViewAnchorLayout.IPLVSwitchViewAnchorLayoutListener
            public void onSwitchBackAfter() {
                super.onSwitchBackAfter();
                if (PLVLCPlaybackMediaLayout.this.switchAnchorPlayer.getChildAt(0) == PLVLCPlaybackMediaLayout.this.flPlayerSwitchViewParent) {
                    PLVLCPlaybackMediaLayout.this.flPlayerSwitchViewParent.removeAllViews();
                    PLVLCPlaybackMediaLayout.this.videoView.addView(PLVLCPlaybackMediaLayout.this.playerView, 0);
                    PLVLCPlaybackMediaLayout.this.videoView.addView(PLVLCPlaybackMediaLayout.this.logoView);
                }
            }

            @Override // com.easefun.polyv.livecommon.ui.widget.PLVSwitchViewAnchorLayout.IPLVSwitchViewAnchorLayoutListener
            public void onSwitchElsewhereBefore() {
                super.onSwitchElsewhereBefore();
                if (PLVLCPlaybackMediaLayout.this.switchAnchorPlayer.getChildAt(0) == PLVLCPlaybackMediaLayout.this.flPlayerSwitchViewParent) {
                    PLVLCPlaybackMediaLayout.this.videoView.removeView(PLVLCPlaybackMediaLayout.this.playerView);
                    PLVLCPlaybackMediaLayout.this.videoView.removeView(PLVLCPlaybackMediaLayout.this.logoView);
                    PLVLCPlaybackMediaLayout.this.flPlayerSwitchViewParent.addView(PLVLCPlaybackMediaLayout.this.playerView);
                    PLVLCPlaybackMediaLayout.this.flPlayerSwitchViewParent.addView(PLVLCPlaybackMediaLayout.this.logoView);
                }
            }
        });
    }

    private void initVideoView() {
        this.videoView.enableRetry(true);
        this.videoView.setMaxRetryCount(3);
        this.noStreamView.setPlaceHolderImg(R.drawable.plv_bg_player_error_ic);
        this.noStreamView.setPlaceHolderText(getResources().getString(R.string.plv_player_video_playback_no_stream));
        this.videoView.setSubVideoView(this.subVideoView);
        this.videoView.setMediaController(this.mediaController);
        this.videoView.setNoStreamIndicator(this.noStreamView);
        this.videoView.setPlayerBufferingIndicator(this.loadingLayout);
        this.marqueeView = (PLVMarqueeView) ((Activity) getContext()).findViewById(R.id.polyv_marquee_view);
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.plvlc_playback_player_layout, (ViewGroup) this, true);
        this.videoView = (PolyvPlaybackVideoView) findViewById(R.id.plvlc_playback_video_view);
        this.subVideoView = (PolyvAuxiliaryVideoview) findViewById(R.id.sub_video_view);
        this.playerView = this.videoView.findViewById(PLVBaseVideoView.IJK_VIDEO_ID);
        this.mediaController = (IPLVLCPlaybackMediaController) findViewById(R.id.plvlc_playback_media_controller);
        this.noStreamView = (PLVPlaceHolderView) findViewById(R.id.no_stream_ly);
        this.playErrorView = (PLVPlaceHolderView) findViewById(R.id.play_error_ly);
        this.logoView = (PLVPlayerLogoView) findViewById(R.id.playback_logo_view);
        this.loadingLayout = (PLVLCVideoLoadingLayout) findViewById(R.id.plvlc_playback_loading_layout);
        this.playerRetryLayout = (PLVPlayerRetryLayout) findViewById(R.id.plvlc_playback_player_retry_layout);
        this.lightTipsView = (PLVLCLightTipsView) findViewById(R.id.plvlc_playback_tipsview_light);
        this.volumeTipsView = (PLVLCVolumeTipsView) findViewById(R.id.plvlc_playback_tipsview_volume);
        this.progressTipsView = (PLVLCProgressTipsView) findViewById(R.id.plvlc_playback_tipsview_progress);
        this.chatLandscapeLayout = (PLVLCChatLandscapeLayout) findViewById(R.id.plvlc_chat_landscape_ly);
        this.flPlayerSwitchViewParent = (FrameLayout) findViewById(R.id.plvlc_playback_fl_player_switch_view_parent);
        this.switchAnchorPlayer = (PLVSwitchViewAnchorLayout) findViewById(R.id.plvlc_playback_switch_anchor_player);
        this.tvCountDown = (TextView) findViewById(R.id.auxiliary_tv_count_down);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.polyv_auxiliary_controller_ll_tips);
        this.llAuxiliaryCountDown = linearLayout;
        linearLayout.setVisibility(8);
        this.watermarkView = (PLVWatermarkView) findViewById(R.id.polyv_watermark_view);
        this.playbackAutoContinueSeekTimeHintLayout = (PLVRoundRectLayout) findViewById(R.id.plvlc_playback_auto_continue_seek_time_hint_layout);
        this.playbackAutoContinueSeekTimeTv = (TextView) findViewById(R.id.plvlc_playback_auto_continue_seek_time_tv);
        initVideoView();
        initPlayErrorView();
        initDanmuView();
        initMediaController();
        initLoadingView();
        initRetryView();
        initSwitchView();
        initChatLandscapeLayout();
        initLayoutWH();
    }

    private void observeLiveRoomData() {
        this.liveRoomDataManager.getFunctionSwitchVO().observe((LifecycleOwner) getContext(), new Observer<PLVStatefulData<PolyvChatFunctionSwitchVO>>() { // from class: com.easefun.polyv.livecloudclass.modules.media.PLVLCPlaybackMediaLayout.10
            @Override // androidx.lifecycle.Observer
            public void onChanged(@Nullable PLVStatefulData<PolyvChatFunctionSwitchVO> pLVStatefulData) {
                PolyvChatFunctionSwitchVO data;
                List<PLVChatFunctionSwitchVO.DataBean> data2;
                PLVLCPlaybackMediaLayout.this.liveRoomDataManager.getFunctionSwitchVO().removeObserver(this);
                if (pLVStatefulData == null || !pLVStatefulData.isSuccess() || (data = pLVStatefulData.getData()) == null || data.getData() == null || (data2 = data.getData()) == null) {
                    return;
                }
                for (PLVChatFunctionSwitchVO.DataBean dataBean : data2) {
                    boolean zIsEnabled = dataBean.isEnabled();
                    String type = dataBean.getType();
                    type.hashCode();
                    if (type.equals(PLVChatFunctionSwitchVO.TYPE_SEND_FLOWERS_ENABLED)) {
                        PLVLCPlaybackMediaLayout.this.mediaController.setOnLikesSwitchEnabled(zIsEnabled);
                    }
                }
            }
        });
    }

    private void setLandscape() {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
        marginLayoutParams.width = -1;
        marginLayoutParams.height = -1;
        setLayoutParams(marginLayoutParams);
        ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) this.playbackAutoContinueSeekTimeHintLayout.getLayoutParams();
        marginLayoutParams2.bottomMargin = ConvertUtils.dp2px(92.0f);
        this.playbackAutoContinueSeekTimeHintLayout.setLayoutParams(marginLayoutParams2);
    }

    private void setPortrait() {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
        marginLayoutParams.width = -1;
        marginLayoutParams.height = (int) (Math.min(ScreenUtils.getScreenHeight(), ScreenUtils.getScreenWidth()) / RATIO_WH);
        setLayoutParams(marginLayoutParams);
        ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) this.playbackAutoContinueSeekTimeHintLayout.getLayoutParams();
        marginLayoutParams2.bottomMargin = ConvertUtils.dp2px(44.0f);
        this.playbackAutoContinueSeekTimeHintLayout.setLayoutParams(marginLayoutParams2);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void acceptNetworkQuality(int i2) {
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void addOnLinkMicStateListener(IPLVOnDataChangedListener<Pair<Boolean, Boolean>> iPLVOnDataChangedListener) {
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void addOnPPTShowStateListener(IPLVOnDataChangedListener<Boolean> iPLVOnDataChangedListener) {
        this.playbackPlayerPresenter.getData().getPPTShowState().observe((LifecycleOwner) getContext(), iPLVOnDataChangedListener);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void addOnPlayInfoVOListener(IPLVOnDataChangedListener<PLVPlayInfoVO> iPLVOnDataChangedListener) {
        this.playbackPlayerPresenter.getData().getPlayInfoVO().observe((LifecycleOwner) getContext(), iPLVOnDataChangedListener);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void addOnPlayerStateListener(IPLVOnDataChangedListener<PLVPlayerState> iPLVOnDataChangedListener) {
        this.playbackPlayerPresenter.getData().getPlayerState().observe((LifecycleOwner) getContext(), iPLVOnDataChangedListener);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void addOnSeekCompleteListener(IPLVOnDataChangedListener<Integer> iPLVOnDataChangedListener) {
        this.playbackPlayerPresenter.getData().getSeekCompleteVO().observe((LifecycleOwner) getContext(), iPLVOnDataChangedListener);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void addOnSeiDataListener(IPLVOnDataChangedListener<Long> iPLVOnDataChangedListener) {
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void destroy() {
        IPLVPlaybackPlayerContract.IPlaybackPlayerPresenter iPlaybackPlayerPresenter = this.playbackPlayerPresenter;
        if (iPlaybackPlayerPresenter != null) {
            iPlaybackPlayerPresenter.destroy();
        }
        IPLVLCPlaybackMediaController iPLVLCPlaybackMediaController = this.mediaController;
        if (iPLVLCPlaybackMediaController != null) {
            iPLVLCPlaybackMediaController.clean();
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
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public TextView getCardEnterCdView() {
        return this.mediaController.getCardEnterCdView();
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public PLVTriangleIndicateTextView getCardEnterTipsView() {
        return this.mediaController.getCardEnterTipsView();
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public ImageView getCardEnterView() {
        return this.mediaController.getCardEnterView();
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public PLVLCChatLandscapeLayout getChatLandscapeLayout() {
        return this.chatLandscapeLayout;
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public int getDuration() {
        return this.playbackPlayerPresenter.getDuration();
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public IPLVLCLiveLandscapePlayerController getLandscapeControllerView() {
        return null;
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public PLVPlayerLogoView getLogoView() {
        return this.logoView;
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public PLVSwitchViewAnchorLayout getPlayerSwitchView() {
        return this.switchAnchorPlayer;
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public String getSessionId() {
        return this.playbackPlayerPresenter.getSessionId();
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public float getSpeed() {
        return this.playbackPlayerPresenter.getSpeed();
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public int getVideoCurrentPosition() {
        return this.playbackPlayerPresenter.getVideoCurrentPosition();
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public int getVolume() {
        return this.playbackPlayerPresenter.getVolume();
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public boolean hideController() {
        boolean zIsShowing = this.mediaController.isShowing();
        this.mediaController.hide();
        return zIsShowing;
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void init(IPLVLiveRoomDataManager iPLVLiveRoomDataManager) {
        this.liveRoomDataManager = iPLVLiveRoomDataManager;
        observeLiveRoomData();
        PLVPlaybackPlayerPresenter pLVPlaybackPlayerPresenter = new PLVPlaybackPlayerPresenter(iPLVLiveRoomDataManager);
        this.playbackPlayerPresenter = pLVPlaybackPlayerPresenter;
        pLVPlaybackPlayerPresenter.registerView(this.playbackPlayerView);
        this.playbackPlayerPresenter.init();
        this.mediaController.setPlaybackPlayerPresenter(this.playbackPlayerPresenter);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public boolean isPlaying() {
        return this.playbackPlayerPresenter.isPlaying();
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void notifyRTCPrepared() {
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public boolean onBackPressed() {
        if (this.mediaController.onBackPressed()) {
            return true;
        }
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
        } else {
            setPortrait();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        if (this.pausingOnEnterBackground) {
            resume();
        }
        this.pausingOnEnterBackground = false;
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void onTurnPageLayoutChange(boolean z2) {
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void pause() {
        this.playbackPlayerPresenter.pause();
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void resume() {
        this.playbackPlayerPresenter.resume();
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void seekTo(int i2, int i3) {
        this.playbackPlayerPresenter.seekTo(i2, i3);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void sendDanmaku(CharSequence charSequence) {
        this.danmuController.sendDanmaku(charSequence);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void setChatPlaybackEnabled(boolean z2) {
        this.mediaController.setChatPlaybackEnabled(z2);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void setHideLandscapeRTCLayout() {
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void setLandscapeControllerView(@NonNull IPLVLCLiveLandscapePlayerController iPLVLCLiveLandscapePlayerController) {
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void setLandscapeRewardEffectVisibility(boolean z2) {
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void setOnRTCPlayEventListener(IPLVLiveListenerEvent.OnRTCPlayEventListener onRTCPlayEventListener) {
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void setOnViewActionListener(IPLVLCMediaLayout.OnViewActionListener onViewActionListener) {
        this.onViewActionListener = onViewActionListener;
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void setPPTView(IPolyvPPTView iPolyvPPTView) {
        this.playbackPlayerPresenter.bindPPTView(iPolyvPPTView);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void setShowLandscapeRTCLayout() {
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void setSpeed(float f2) {
        this.playbackPlayerPresenter.setSpeed(f2);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void setVolume(int i2) {
        this.playbackPlayerPresenter.setVolume(i2);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void showController() {
        this.mediaController.show();
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void startPlay() {
        this.playbackPlayerPresenter.startPlay();
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void stop() {
        this.playbackPlayerPresenter.stop();
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void updateOnClickCloseFloatingView() {
        this.mediaController.show();
        this.mediaController.updateOnClickCloseFloatingView();
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void updatePPTStatusChange(PLVPPTStatus pLVPPTStatus) {
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void updatePlayBackVideVid(String str) {
        this.playbackPlayerPresenter.setPlayerVid(str);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void updatePlayBackVideVidAndPlay(String str) {
        this.playbackPlayerPresenter.setPlayerVidAndPlay(str);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void updateViewerCount(long j2) {
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void updateWhenJoinLinkMic() {
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void updateWhenJoinRTC(int i2) {
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void updateWhenLeaveLinkMic() {
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void updateWhenLeaveRTC() {
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void updateWhenLinkMicOpenStatusChanged(boolean z2) {
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout
    public void updateWhenRequestJoinLinkMic(boolean z2) {
    }

    public PLVLCPlaybackMediaLayout(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public PLVLCPlaybackMediaLayout(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.marqueeView = null;
        this.watermarkView = null;
        this.pausingOnEnterBackground = false;
        this.playbackPlayerView = new PLVAbsPlaybackPlayerView() { // from class: com.easefun.polyv.livecloudclass.modules.media.PLVLCPlaybackMediaLayout.9
            @Override // com.easefun.polyv.livecommon.module.modules.player.playback.view.PLVAbsPlaybackPlayerView, com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerView
            public View getBufferingIndicator() {
                return super.getBufferingIndicator();
            }

            @Override // com.easefun.polyv.livecommon.module.modules.player.playback.view.PLVAbsPlaybackPlayerView, com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerView
            public PLVPlayerLogoView getLogo() {
                return PLVLCPlaybackMediaLayout.this.logoView;
            }

            @Override // com.easefun.polyv.livecommon.module.modules.player.playback.view.PLVAbsPlaybackPlayerView, com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerView
            public IPLVMarqueeView getMarqueeView() {
                return PLVLCPlaybackMediaLayout.this.marqueeView;
            }

            @Override // com.easefun.polyv.livecommon.module.modules.player.playback.view.PLVAbsPlaybackPlayerView, com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerView
            public View getNoStreamIndicator() {
                return PLVLCPlaybackMediaLayout.this.noStreamView;
            }

            @Override // com.easefun.polyv.livecommon.module.modules.player.playback.view.PLVAbsPlaybackPlayerView, com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerView
            public View getPlayErrorIndicator() {
                return PLVLCPlaybackMediaLayout.this.playErrorView;
            }

            @Override // com.easefun.polyv.livecommon.module.modules.player.playback.view.PLVAbsPlaybackPlayerView, com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerView
            public PolyvPlaybackVideoView getPlaybackVideoView() {
                return PLVLCPlaybackMediaLayout.this.videoView;
            }

            @Override // com.easefun.polyv.livecommon.module.modules.player.playback.view.PLVAbsPlaybackPlayerView, com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerView
            public View getRetryLayout() {
                return null;
            }

            @Override // com.easefun.polyv.livecommon.module.modules.player.playback.view.PLVAbsPlaybackPlayerView, com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerView
            public PolyvAuxiliaryVideoview getSubVideoView() {
                return PLVLCPlaybackMediaLayout.this.subVideoView;
            }

            @Override // com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerView
            public IPLVWatermarkView getWatermarkView() {
                return PLVLCPlaybackMediaLayout.this.watermarkView;
            }

            @Override // com.easefun.polyv.livecommon.module.modules.player.playback.view.PLVAbsPlaybackPlayerView, com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerView
            public void onAutoContinuePlaySeeked(int i3) {
                PLVLCPlaybackMediaLayout.this.playbackAutoContinueSeekTimeTv.setText(PLVTimeUtils.generateTime(i3));
                PLVViewUtil.showViewForDuration(PLVLCPlaybackMediaLayout.this.playbackAutoContinueSeekTimeHintLayout, PLVTimeUnit.seconds(3L).toMillis());
            }

            @Override // com.easefun.polyv.livecommon.module.modules.player.playback.view.PLVAbsPlaybackPlayerView, com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerView
            public void onBufferEnd() {
                super.onBufferEnd();
                PLVCommonLog.i(PLVLCPlaybackMediaLayout.TAG, "缓冲结束");
            }

            @Override // com.easefun.polyv.livecommon.module.modules.player.playback.view.PLVAbsPlaybackPlayerView, com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerView
            public void onBufferStart() {
                super.onBufferStart();
                PLVCommonLog.i(PLVLCPlaybackMediaLayout.TAG, "开始缓冲");
            }

            @Override // com.easefun.polyv.livecommon.module.modules.player.playback.view.PLVAbsPlaybackPlayerView, com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerView
            public void onDoubleClick() {
                super.onDoubleClick();
                PLVLCPlaybackMediaLayout.this.mediaController.playOrPause();
            }

            @Override // com.easefun.polyv.livecommon.module.modules.player.playback.view.PLVAbsPlaybackPlayerView, com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerView
            public boolean onLightChanged(int i3, boolean z2) {
                PLVLCPlaybackMediaLayout.this.lightTipsView.setLightPercent(i3, z2);
                return true;
            }

            @Override // com.easefun.polyv.livecommon.module.modules.player.playback.view.PLVAbsPlaybackPlayerView, com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerView
            public void onLoadSlow(int i3, boolean z2) {
                super.onLoadSlow(i3, z2);
                PLVPlayErrorMessageUtils.showOnLoadSlow(PLVLCPlaybackMediaLayout.this.playErrorView, PLVLCPlaybackMediaLayout.this.liveRoomDataManager.getConfig().isLive());
            }

            @Override // com.easefun.polyv.livecommon.module.modules.player.playback.view.PLVAbsPlaybackPlayerView, com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerView
            public void onPlayError(PolyvPlayError polyvPlayError, String str) {
                super.onPlayError(polyvPlayError, str);
                PLVPlayErrorMessageUtils.showOnPlayError(PLVLCPlaybackMediaLayout.this.playErrorView, polyvPlayError, PLVLCPlaybackMediaLayout.this.liveRoomDataManager.getConfig().isLive());
                PLVCommonLog.e(PLVLCPlaybackMediaLayout.TAG, str);
            }

            @Override // com.easefun.polyv.livecommon.module.modules.player.playback.view.PLVAbsPlaybackPlayerView, com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerView
            public void onPrepared() {
                super.onPrepared();
                PLVCommonLog.d(PLVLCPlaybackMediaLayout.TAG, "PLVLCPlaybackMediaLayout.onPreparing");
                PLVLCPlaybackMediaLayout.this.mediaController.show();
            }

            @Override // com.easefun.polyv.livecommon.module.modules.player.playback.view.PLVAbsPlaybackPlayerView, com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerView
            public boolean onProgressChanged(int i3, int i4, boolean z2, boolean z3) {
                PLVLCPlaybackMediaLayout.this.progressTipsView.setProgressPercent(i3, i4, z2, z3);
                return true;
            }

            @Override // com.easefun.polyv.livecommon.module.modules.player.playback.view.PLVAbsPlaybackPlayerView, com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerView
            public void onServerDanmuOpen(boolean z2) {
                super.onServerDanmuOpen(z2);
                PLVLCPlaybackMediaLayout.this.danmuWrapper.setOnServerDanmuOpen(z2);
            }

            @Override // com.easefun.polyv.livecommon.module.modules.player.playback.view.PLVAbsPlaybackPlayerView, com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerView
            public void onShowPPTView(int i3) {
                super.onShowPPTView(i3);
                PLVLCPlaybackMediaLayout.this.mediaController.setServerEnablePPT(i3 == 0);
            }

            @Override // com.easefun.polyv.livecommon.module.modules.player.playback.view.PLVAbsPlaybackPlayerView, com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerView
            public void onSubVideoViewCountDown(boolean z2, int i3, int i4, int i5) {
                if (z2) {
                    PLVLCPlaybackMediaLayout.this.llAuxiliaryCountDown.setVisibility(0);
                    PLVLCPlaybackMediaLayout.this.tvCountDown.setText("广告：" + i4 + "s");
                }
            }

            @Override // com.easefun.polyv.livecommon.module.modules.player.playback.view.PLVAbsPlaybackPlayerView, com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerView
            public void onSubVideoViewVisiblityChanged(boolean z2, boolean z3) {
                if (!z2) {
                    PLVLCPlaybackMediaLayout.this.llAuxiliaryCountDown.setVisibility(8);
                } else {
                    if (z3) {
                        return;
                    }
                    PLVLCPlaybackMediaLayout.this.llAuxiliaryCountDown.setVisibility(8);
                }
            }

            @Override // com.easefun.polyv.livecommon.module.modules.player.playback.view.PLVAbsPlaybackPlayerView, com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerView
            public boolean onVolumeChanged(int i3, boolean z2) {
                PLVLCPlaybackMediaLayout.this.volumeTipsView.setVolumePercent(i3, z2);
                return true;
            }

            @Override // com.easefun.polyv.livecommon.module.modules.player.playback.view.PLVAbsPlaybackPlayerView, com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract.IPlaybackPlayerView
            public void setPresenter(@NonNull IPLVPlaybackPlayerContract.IPlaybackPlayerPresenter iPlaybackPlayerPresenter) {
                super.setPresenter(iPlaybackPlayerPresenter);
                PLVLCPlaybackMediaLayout.this.playbackPlayerPresenter = iPlaybackPlayerPresenter;
            }
        };
        if (context instanceof LifecycleOwner) {
            ((LifecycleOwner) context).getLifecycle().addObserver(this);
        }
        initView();
    }
}
