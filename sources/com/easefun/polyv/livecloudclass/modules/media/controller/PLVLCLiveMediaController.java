package com.easefun.polyv.livecloudclass.modules.media.controller;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import com.easefun.polyv.businesssdk.model.video.PolyvDefinitionVO;
import com.easefun.polyv.livecloudclass.R;
import com.easefun.polyv.livecloudclass.modules.chatroom.widget.PLVLCLikeIconView;
import com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveMediaController;
import com.easefun.polyv.livecloudclass.modules.media.widget.PLVLCLiveMoreLayout;
import com.easefun.polyv.livecloudclass.modules.media.widget.PLVLCPPTTurnPageLayout;
import com.easefun.polyv.livecommon.module.modules.commodity.viewmodel.PLVCommodityViewModel;
import com.easefun.polyv.livecommon.module.modules.commodity.viewmodel.vo.PLVCommodityUiState;
import com.easefun.polyv.livecommon.module.modules.player.floating.PLVFloatingPlayerManager;
import com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract;
import com.easefun.polyv.livecommon.module.modules.player.live.presenter.data.PLVPlayInfoVO;
import com.easefun.polyv.livecommon.module.utils.rotaion.PLVOrientationManager;
import com.easefun.polyv.livescenes.video.PolyvLiveVideoView;
import com.google.android.exoplayer2.C;
import com.plv.foundationsdk.component.di.PLVDependManager;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.rx.PLVRxTimer;
import com.plv.foundationsdk.utils.PLVScreenUtils;
import com.plv.livescenes.document.model.PLVPPTStatus;
import com.plv.thirdpart.blankj.utilcode.util.ConvertUtils;
import com.plv.thirdpart.blankj.utilcode.util.ScreenUtils;
import com.plv.thirdpart.blankj.utilcode.util.StringUtils;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVLCLiveMediaController extends FrameLayout implements IPLVLCLiveMediaController, View.OnClickListener {
    private static final int SHOW_TIME = 5000;
    private static final String TAG = "PLVLCLiveMediaController";
    private ImageView backLandIv;
    private ImageView backPortIv;
    private Disposable bitPopupWindowTimer;
    private PopupWindow bitRatePopupWindow;
    private ImageView bulletinLandIv;
    private ImageView commodityView;
    private final PLVCommodityViewModel commodityViewModel;
    private int currentBitratePos;
    private ImageView danmuSwitchLandIv;
    private List<PolyvDefinitionVO> definitionVOS;
    private ImageView gradientBarTopPortView;
    private boolean hasShowReopenFloatingViewTip;
    private boolean hideRefreshButtonInRtcChannel;
    private boolean isChannelOpenLinkMic;
    private boolean isLinkMic;
    private boolean isLowLatencyWatch;
    private boolean isMainVideoViewPlaying;
    private boolean isRequestingJoinLinkMic;
    private boolean isRtcChannelWatch;
    private boolean isServerEnablePPT;
    private IPLVLCLiveLandscapePlayerController landscapeController;
    private PLVLCLikeIconView likesLandIv;
    private ImageView liveControlFloatingIv;
    private ImageView liveControlFloatingLandIv;
    private LinearLayout liveMoreControlLl;
    private IPLVLivePlayerContract.ILivePlayerPresenter livePlayerPresenter;
    private ImageView moreLandIv;
    private PLVLCLiveMoreLayout moreLayout;
    private ImageView morePortIv;
    private IPLVLCLiveMediaController.OnViewActionListener onViewActionListener;
    private PLVLCPPTTurnPageLayout pptTurnPageLandLayout;
    private PLVLCPPTTurnPageLayout pptTurnPagePortLayout;
    private Disposable reopenFloatingDelay;
    private ImageView rewardView;
    private TextView startSendMessageLandIv;
    private TextView tvReopenFloatingViewTip;
    private ViewGroup videoControllerLandLy;
    private ViewGroup videoControllerPortLy;
    private TextView videoNameLandTv;
    private TextView videoNamePortTv;
    private ImageView videoPauseLandIv;
    private ImageView videoPausePortIv;
    private ImageView videoPptSwitchLandIv;
    private ImageView videoPptSwitchPortIv;
    private ImageView videoRefreshLandIv;
    private ImageView videoRefreshPortIv;
    private ImageView videoScreenSwitchPortIv;
    private TextView videoViewerCountLandTv;
    private TextView videoViewerCountPortTv;

    public class LinkMicMediaDispatcher extends RtcMediaDispatcher {
        private LinkMicMediaDispatcher() {
            super();
        }

        @Override // com.easefun.polyv.livecloudclass.modules.media.controller.PLVLCLiveMediaController.RtcMediaDispatcher, com.easefun.polyv.livecloudclass.modules.media.controller.PLVLCLiveMediaController.LiveMediaDispatcher
        public void updateViewProperties() {
            PLVLCLiveMediaController.this.moreLayout.hide();
            PLVLCLiveMediaController.this.videoPausePortIv.setVisibility(8);
            PLVLCLiveMediaController.this.videoPauseLandIv.setVisibility(8);
            if (PLVLCLiveMediaController.this.hideRefreshButtonInRtcChannel) {
                PLVLCLiveMediaController.this.videoRefreshPortIv.setVisibility(8);
                PLVLCLiveMediaController.this.videoRefreshLandIv.setVisibility(8);
            }
            PLVLCLiveMediaController.this.liveControlFloatingIv.setVisibility(8);
            PLVLCLiveMediaController.this.liveControlFloatingLandIv.setVisibility(8);
            PLVLCLiveMediaController.this.morePortIv.setVisibility(8);
            PLVLCLiveMediaController.this.moreLandIv.setVisibility(8);
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) PLVLCLiveMediaController.this.startSendMessageLandIv.getLayoutParams();
            marginLayoutParams.leftMargin = ConvertUtils.dp2px(79.0f);
            marginLayoutParams.rightMargin = ConvertUtils.dp2px(85.0f);
            PLVLCLiveMediaController.this.startSendMessageLandIv.setLayoutParams(marginLayoutParams);
        }
    }

    public interface LiveMediaDispatcher {
        void acceptPlayInfo(@Nullable PLVPlayInfoVO pLVPlayInfoVO);

        void changePlayPause();

        boolean isPlaying();

        void updateViewProperties();
    }

    public class LowLatencyMediaDispatcher extends VideoViewDispatcher {
        private LowLatencyMediaDispatcher() {
            super();
        }
    }

    public class RtcMediaDispatcher implements LiveMediaDispatcher {
        private RtcMediaDispatcher() {
        }

        @Override // com.easefun.polyv.livecloudclass.modules.media.controller.PLVLCLiveMediaController.LiveMediaDispatcher
        public void acceptPlayInfo(@Nullable PLVPlayInfoVO pLVPlayInfoVO) {
            boolean zIsPlaying = isPlaying();
            PLVLCLiveMediaController.this.videoPausePortIv.setSelected(zIsPlaying);
            PLVLCLiveMediaController.this.videoPauseLandIv.setSelected(zIsPlaying);
        }

        @Override // com.easefun.polyv.livecloudclass.modules.media.controller.PLVLCLiveMediaController.LiveMediaDispatcher
        public void changePlayPause() {
            if (PLVLCLiveMediaController.this.onViewActionListener == null) {
                return;
            }
            boolean zIsPlaying = isPlaying();
            if (zIsPlaying) {
                PLVLCLiveMediaController.this.livePlayerPresenter.pause();
            } else {
                PLVLCLiveMediaController.this.livePlayerPresenter.resume();
            }
            PLVLCLiveMediaController.this.onViewActionListener.onRtcPauseResume(zIsPlaying);
            acceptPlayInfo(null);
        }

        @Override // com.easefun.polyv.livecloudclass.modules.media.controller.PLVLCLiveMediaController.LiveMediaDispatcher
        public boolean isPlaying() {
            if (PLVLCLiveMediaController.this.onViewActionListener == null) {
                return false;
            }
            return !PLVLCLiveMediaController.this.onViewActionListener.isRtcPausing();
        }

        @Override // com.easefun.polyv.livecloudclass.modules.media.controller.PLVLCLiveMediaController.LiveMediaDispatcher
        public void updateViewProperties() {
            PLVLCLiveMediaController.this.videoPausePortIv.setVisibility(0);
            PLVLCLiveMediaController.this.videoPauseLandIv.setVisibility(0);
            if (PLVLCLiveMediaController.this.hideRefreshButtonInRtcChannel) {
                PLVLCLiveMediaController.this.videoRefreshPortIv.setVisibility(8);
                PLVLCLiveMediaController.this.videoRefreshLandIv.setVisibility(8);
            }
            PLVLCLiveMediaController.this.liveControlFloatingIv.setVisibility(PLVLCLiveMediaController.this.isRequestingJoinLinkMic ? 8 : 0);
            PLVLCLiveMediaController.this.liveControlFloatingLandIv.setVisibility(PLVLCLiveMediaController.this.isRequestingJoinLinkMic ? 8 : 0);
            boolean z2 = PLVLCLiveMediaController.this.isMainVideoViewPlaying && !PLVFloatingPlayerManager.getInstance().isFloatingWindowShowing();
            PLVLCLiveMediaController.this.morePortIv.setVisibility(z2 ? 0 : 8);
            PLVLCLiveMediaController.this.moreLandIv.setVisibility(z2 ? 0 : 8);
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) PLVLCLiveMediaController.this.startSendMessageLandIv.getLayoutParams();
            marginLayoutParams.leftMargin = ConvertUtils.dp2px(79.0f);
            marginLayoutParams.rightMargin = ConvertUtils.dp2px(85.0f);
            PLVLCLiveMediaController.this.startSendMessageLandIv.setLayoutParams(marginLayoutParams);
        }
    }

    public class VideoViewDispatcher implements LiveMediaDispatcher {
        private VideoViewDispatcher() {
        }

        @Override // com.easefun.polyv.livecloudclass.modules.media.controller.PLVLCLiveMediaController.LiveMediaDispatcher
        public void acceptPlayInfo(@Nullable PLVPlayInfoVO pLVPlayInfoVO) {
            if (pLVPlayInfoVO == null) {
                return;
            }
            PLVLCLiveMediaController.this.videoPausePortIv.setSelected(pLVPlayInfoVO.isPlaying());
            PLVLCLiveMediaController.this.videoPauseLandIv.setSelected(pLVPlayInfoVO.isPlaying());
        }

        @Override // com.easefun.polyv.livecloudclass.modules.media.controller.PLVLCLiveMediaController.LiveMediaDispatcher
        public void changePlayPause() {
            if (isPlaying()) {
                PLVLCLiveMediaController.this.livePlayerPresenter.pause();
            } else {
                PLVLCLiveMediaController.this.livePlayerPresenter.restartPlay();
            }
        }

        @Override // com.easefun.polyv.livecloudclass.modules.media.controller.PLVLCLiveMediaController.LiveMediaDispatcher
        public boolean isPlaying() {
            return PLVLCLiveMediaController.this.livePlayerPresenter.isPlaying();
        }

        @Override // com.easefun.polyv.livecloudclass.modules.media.controller.PLVLCLiveMediaController.LiveMediaDispatcher
        public void updateViewProperties() {
            PLVLCLiveMediaController.this.videoPausePortIv.setVisibility(0);
            PLVLCLiveMediaController.this.videoPauseLandIv.setVisibility(0);
            PLVLCLiveMediaController.this.videoRefreshPortIv.setVisibility(0);
            PLVLCLiveMediaController.this.videoRefreshLandIv.setVisibility(0);
            PLVLCLiveMediaController.this.liveControlFloatingIv.setVisibility(PLVLCLiveMediaController.this.isRequestingJoinLinkMic ? 8 : 0);
            PLVLCLiveMediaController.this.liveControlFloatingLandIv.setVisibility(PLVLCLiveMediaController.this.isRequestingJoinLinkMic ? 8 : 0);
            boolean z2 = PLVLCLiveMediaController.this.isMainVideoViewPlaying && !PLVFloatingPlayerManager.getInstance().isFloatingWindowShowing();
            PLVLCLiveMediaController.this.morePortIv.setVisibility(z2 ? 0 : 8);
            PLVLCLiveMediaController.this.moreLandIv.setVisibility(z2 ? 0 : 8);
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) PLVLCLiveMediaController.this.startSendMessageLandIv.getLayoutParams();
            marginLayoutParams.leftMargin = ConvertUtils.dp2px(32.0f);
            marginLayoutParams.rightMargin = ConvertUtils.dp2px(38.0f);
            PLVLCLiveMediaController.this.startSendMessageLandIv.setLayoutParams(marginLayoutParams);
        }
    }

    public PLVLCLiveMediaController(@NonNull Context context) {
        this(context, null);
    }

    private void createBitrateChangeWindow() {
        PopupWindow popupWindow = new PopupWindow(View.inflate(getContext(), R.layout.plvlc_live_controller_bitrate_popup_layout, null), -2, -2, true);
        this.bitRatePopupWindow = popupWindow;
        popupWindow.setFocusable(true);
        this.bitRatePopupWindow.setTouchable(true);
        this.bitRatePopupWindow.setBackgroundDrawable(new ColorDrawable(0));
        this.bitRatePopupWindow.setOutsideTouchable(true);
        this.bitRatePopupWindow.update();
        this.bitRatePopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.easefun.polyv.livecloudclass.modules.media.controller.PLVLCLiveMediaController.12
            @Override // android.widget.PopupWindow.OnDismissListener
            public void onDismiss() {
                PLVLCLiveMediaController pLVLCLiveMediaController = PLVLCLiveMediaController.this;
                pLVLCLiveMediaController.dispose(pLVLCLiveMediaController.bitPopupWindowTimer);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispose(Disposable disposable) {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public LiveMediaDispatcher getLiveMediaDispatcher() {
        return this.isLinkMic ? new LinkMicMediaDispatcher() : this.isRtcChannelWatch ? new RtcMediaDispatcher() : this.isLowLatencyWatch ? new LowLatencyMediaDispatcher() : new VideoViewDispatcher();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideBitPopup() {
        PopupWindow popupWindow = this.bitRatePopupWindow;
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }

    private void initMoreLayout() {
        PLVLCLiveMoreLayout pLVLCLiveMoreLayout = new PLVLCLiveMoreLayout(this);
        this.moreLayout = pLVLCLiveMoreLayout;
        pLVLCLiveMoreLayout.setOnBitrateSelectedListener(new PLVLCLiveMoreLayout.OnBitrateSelectedListener() { // from class: com.easefun.polyv.livecloudclass.modules.media.controller.PLVLCLiveMediaController.2
            @Override // com.easefun.polyv.livecloudclass.modules.media.widget.PLVLCLiveMoreLayout.OnBitrateSelectedListener
            public void onBitrateSelected(PolyvDefinitionVO polyvDefinitionVO, int i2) {
                PLVLCLiveMediaController.this.livePlayerPresenter.changeBitRate(i2);
            }
        });
        this.moreLayout.setOnLinesSelectedListener(new PLVLCLiveMoreLayout.OnLinesSelectedListener() { // from class: com.easefun.polyv.livecloudclass.modules.media.controller.PLVLCLiveMediaController.3
            @Override // com.easefun.polyv.livecloudclass.modules.media.widget.PLVLCLiveMoreLayout.OnLinesSelectedListener
            public void onLineSelected(int i2, int i3) {
                PLVLCLiveMediaController.this.livePlayerPresenter.changeLines(i3);
            }
        });
        this.moreLayout.setOnOnlyAudioSwitchListener(new PLVLCLiveMoreLayout.OnOnlyAudioSwitchListener() { // from class: com.easefun.polyv.livecloudclass.modules.media.controller.PLVLCLiveMediaController.4
            @Override // com.easefun.polyv.livecloudclass.modules.media.widget.PLVLCLiveMoreLayout.OnOnlyAudioSwitchListener
            public boolean onOnlyAudioSelect(boolean z2) {
                if (z2) {
                    PLVLCLiveMediaController.this.livePlayerPresenter.changeMediaPlayMode(1);
                } else {
                    PLVLCLiveMediaController.this.livePlayerPresenter.changeMediaPlayMode(0);
                }
                return true;
            }
        });
        this.moreLayout.setOnChangeLowLatencyListener(new PLVLCLiveMoreLayout.OnChangeLowLatencyListener() { // from class: com.easefun.polyv.livecloudclass.modules.media.controller.PLVLCLiveMediaController.5
            @Override // com.plv.foundationsdk.utils.PLVSugarUtil.Consumer
            public void accept(@NonNull Boolean bool) {
                PLVLCLiveMediaController.this.isLowLatencyWatch = bool.booleanValue();
                if (PLVLCLiveMediaController.this.onViewActionListener != null) {
                    PLVLCLiveMediaController.this.onViewActionListener.onChangeLowLatencyMode(bool.booleanValue());
                }
            }
        });
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.plvlc_live_controller_layout, this);
        this.videoControllerPortLy = (ViewGroup) findViewById(R.id.video_controller_port_ly);
        ImageView imageView = (ImageView) findViewById(R.id.back_port_iv);
        this.backPortIv = imageView;
        imageView.setOnClickListener(this);
        this.videoNamePortTv = (TextView) findViewById(R.id.video_name_port_tv);
        this.videoViewerCountPortTv = (TextView) findViewById(R.id.video_viewer_count_port_tv);
        ImageView imageView2 = (ImageView) findViewById(R.id.video_pause_port_iv);
        this.videoPausePortIv = imageView2;
        imageView2.setOnClickListener(this);
        ImageView imageView3 = (ImageView) findViewById(R.id.video_refresh_port_iv);
        this.videoRefreshPortIv = imageView3;
        imageView3.setOnClickListener(this);
        ImageView imageView4 = (ImageView) findViewById(R.id.video_ppt_switch_port_iv);
        this.videoPptSwitchPortIv = imageView4;
        imageView4.setOnClickListener(this);
        ImageView imageView5 = (ImageView) findViewById(R.id.video_screen_switch_port_iv);
        this.videoScreenSwitchPortIv = imageView5;
        imageView5.setOnClickListener(this);
        this.liveMoreControlLl = (LinearLayout) findViewById(R.id.plvlc_live_more_control_ll);
        ImageView imageView6 = (ImageView) findViewById(R.id.more_port_iv);
        this.morePortIv = imageView6;
        imageView6.setOnClickListener(this);
        ImageView imageView7 = (ImageView) findViewById(R.id.plvlc_live_control_floating_iv);
        this.liveControlFloatingIv = imageView7;
        imageView7.setOnClickListener(this);
        this.gradientBarTopPortView = (ImageView) findViewById(R.id.gradient_bar_top_port_view);
        this.tvReopenFloatingViewTip = (TextView) findViewById(R.id.plvlc_live_player_controller_tv_reopen_floating_view);
        PLVLCPPTTurnPageLayout pLVLCPPTTurnPageLayout = (PLVLCPPTTurnPageLayout) findViewById(R.id.video_ppt_turn_page_layout);
        this.pptTurnPagePortLayout = pLVLCPPTTurnPageLayout;
        pLVLCPPTTurnPageLayout.setOnPPTTurnPageListener(new PLVLCPPTTurnPageLayout.OnPPTTurnPageListener() { // from class: com.easefun.polyv.livecloudclass.modules.media.controller.PLVLCLiveMediaController.1
            @Override // com.easefun.polyv.livecloudclass.modules.media.widget.PLVLCPPTTurnPageLayout.OnPPTTurnPageListener
            public void onPPTTurnPage(String str) {
                PLVLCLiveMediaController.this.onViewActionListener.onPPTTurnPage(str);
            }
        });
        initMoreLayout();
        if (ScreenUtils.isPortrait()) {
            this.videoControllerPortLy.setVisibility(0);
        } else {
            this.videoControllerPortLy.setVisibility(4);
        }
        observeFloatingPlayerShowingState();
    }

    private void observeCommodityStatus() {
        this.commodityViewModel.getCommodityUiStateLiveData().observe((LifecycleOwner) getContext(), new Observer<PLVCommodityUiState>() { // from class: com.easefun.polyv.livecloudclass.modules.media.controller.PLVLCLiveMediaController.7
            boolean needShowControllerOnClosed = false;

            @Override // androidx.lifecycle.Observer
            public void onChanged(@Nullable PLVCommodityUiState pLVCommodityUiState) {
                if (pLVCommodityUiState == null) {
                    return;
                }
                PLVLCLiveMediaController.this.commodityView.setVisibility(pLVCommodityUiState.hasProductView ? 0 : 8);
                if (pLVCommodityUiState.showProductViewOnLandscape) {
                    if (this.needShowControllerOnClosed) {
                        return;
                    }
                    this.needShowControllerOnClosed = PLVLCLiveMediaController.this.isShowing();
                    PLVLCLiveMediaController.this.hide();
                    return;
                }
                if (this.needShowControllerOnClosed) {
                    PLVLCLiveMediaController.this.show();
                    this.needShowControllerOnClosed = false;
                }
            }
        });
    }

    private void observeFloatingPlayerShowingState() {
        PLVFloatingPlayerManager.getInstance().getFloatingViewShowState().observe((LifecycleOwner) getContext(), new Observer<Boolean>() { // from class: com.easefun.polyv.livecloudclass.modules.media.controller.PLVLCLiveMediaController.6
            @Override // androidx.lifecycle.Observer
            public void onChanged(@Nullable Boolean bool) {
                if (bool == null) {
                    return;
                }
                PLVLCLiveMediaController.this.getLiveMediaDispatcher().updateViewProperties();
            }
        });
    }

    private void observePlayInfoVO() {
        this.livePlayerPresenter.getData().getPlayInfoVO().observe((LifecycleOwner) getContext(), new Observer<PLVPlayInfoVO>() { // from class: com.easefun.polyv.livecloudclass.modules.media.controller.PLVLCLiveMediaController.15
            @Override // androidx.lifecycle.Observer
            public void onChanged(@Nullable PLVPlayInfoVO pLVPlayInfoVO) {
                PLVLCLiveMediaController.this.getLiveMediaDispatcher().acceptPlayInfo(pLVPlayInfoVO);
            }
        });
    }

    private void setPortraitController() {
        post(new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.media.controller.PLVLCLiveMediaController.14
            @Override // java.lang.Runnable
            public void run() {
                PLVLCLiveMediaController.this.videoControllerLandLy.setVisibility(8);
                PLVLCLiveMediaController.this.videoControllerPortLy.setVisibility(0);
            }
        });
    }

    private void showBitrateChangeView() {
        List<PolyvDefinitionVO> list = this.definitionVOS;
        if (list == null || this.currentBitratePos >= list.size() - 1 || this.livePlayerPresenter.getMediaPlayMode() == 1) {
            return;
        }
        int[] iArr = new int[2];
        ImageView imageView = this.videoControllerPortLy.getVisibility() == 0 ? this.videoRefreshPortIv : this.videoControllerLandLy.getVisibility() == 0 ? this.videoRefreshLandIv : null;
        if (imageView == null) {
            return;
        }
        imageView.getLocationOnScreen(iArr);
        int i2 = iArr[0];
        if (i2 == iArr[1] && i2 == 0) {
            iArr[0] = ConvertUtils.dp2px(16.0f);
            iArr[1] = ConvertUtils.dp2px(126.0f);
        }
        if (this.bitRatePopupWindow == null) {
            createBitrateChangeWindow();
        }
        View contentView = this.bitRatePopupWindow.getContentView();
        TextView textView = (TextView) contentView.findViewById(R.id.live_bitrate_popup_definition_tv);
        textView.setText(this.definitionVOS.get(Math.max(0, this.currentBitratePos + 1)).getDefinition());
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecloudclass.modules.media.controller.PLVLCLiveMediaController.10
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PLVLCLiveMediaController.this.hideBitPopup();
                PLVLCLiveMediaController.this.livePlayerPresenter.changeBitRate(PLVLCLiveMediaController.this.currentBitratePos + 1);
            }
        });
        contentView.measure(0, 0);
        this.bitRatePopupWindow.showAtLocation(imageView, 0, iArr[0] + 10, (iArr[1] - contentView.getMeasuredHeight()) - 10);
        dispose(this.bitPopupWindowTimer);
        this.bitPopupWindowTimer = PLVRxTimer.delay(5000L, new Consumer<Long>() { // from class: com.easefun.polyv.livecloudclass.modules.media.controller.PLVLCLiveMediaController.11
            @Override // io.reactivex.functions.Consumer
            public void accept(Long l2) throws Exception {
                PLVLCLiveMediaController.this.hideBitPopup();
            }
        });
    }

    private void updateBitrateVO() {
        this.definitionVOS = this.livePlayerPresenter.getBitrateVO();
        this.currentBitratePos = this.livePlayerPresenter.getBitratePos();
    }

    private void updateMoreLayout() {
        this.moreLayout.updateViewWithPlayInfo(this.livePlayerPresenter.getMediaPlayMode(), new Pair<>(this.livePlayerPresenter.getBitrateVO(), Integer.valueOf(this.livePlayerPresenter.getBitratePos())), new int[]{this.livePlayerPresenter.getLinesCount(), this.livePlayerPresenter.getLinesPos()});
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveMediaController
    public void clean() {
        PLVLCLiveMoreLayout pLVLCLiveMoreLayout = this.moreLayout;
        if (pLVLCLiveMoreLayout != null) {
            pLVLCLiveMoreLayout.hide();
        }
        dispose(this.bitPopupWindowTimer);
        dispose(this.reopenFloatingDelay);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveMediaController
    public void dispatchDanmuSwitchOnClicked(View view) {
        onClick(view);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveMediaController
    public IPLVLCLiveLandscapePlayerController getLandscapeController() {
        return this.landscapeController;
    }

    @Override // com.plv.business.api.common.mediacontrol.IPLVMediaController, com.plv.business.api.common.mediacontrol.IMediaController, com.easefun.polyv.mediasdk.example.widget.media.IMediaController
    public void hide() {
        setVisibility(8);
        IPLVLCLiveMediaController.OnViewActionListener onViewActionListener = this.onViewActionListener;
        if (onViewActionListener != null) {
            onViewActionListener.onShow(false);
        }
    }

    @Override // com.plv.business.api.common.mediacontrol.IPLVMediaController, com.plv.business.api.common.mediacontrol.IMediaController, com.easefun.polyv.mediasdk.example.widget.media.IMediaController
    public boolean isShowing() {
        return isShown();
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveMediaController
    public void notifyChatroomStatusChanged(boolean z2, boolean z3) {
        TextView textView = this.startSendMessageLandIv;
        if (textView != null) {
            textView.setText(z2 ? "聊天室已关闭" : z3 ? "当前为专注模式，无法发言" : "跟大家聊点什么吧~");
            this.startSendMessageLandIv.setOnClickListener((z2 || z3) ? null : this);
        }
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveMediaController
    public void notifyLowLatencyUpdate(boolean z2) {
        this.isLowLatencyWatch = z2;
        this.moreLayout.updateViewWithLatency(z2);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.video_pause_port_iv || id == R.id.video_pause_land_iv) {
            getLiveMediaDispatcher().changePlayPause();
            return;
        }
        if (id == R.id.video_screen_switch_port_iv) {
            PLVOrientationManager.getInstance().setLandscape((Activity) getContext());
            return;
        }
        if (id == R.id.video_refresh_port_iv || id == R.id.video_refresh_land_iv) {
            this.livePlayerPresenter.restartPlay();
            return;
        }
        if (id == R.id.back_port_iv) {
            ((Activity) getContext()).onBackPressed();
            return;
        }
        if (id == R.id.back_land_iv) {
            PLVOrientationManager.getInstance().setPortrait((Activity) getContext());
            return;
        }
        if (id == R.id.more_port_iv) {
            hide();
            this.moreLayout.showWhenPortrait(getHeight());
            return;
        }
        if (id == R.id.more_land_iv) {
            hide();
            this.moreLayout.showWhenLandscape();
            return;
        }
        if (id == R.id.start_send_message_land_tv) {
            hide();
            IPLVLCLiveMediaController.OnViewActionListener onViewActionListener = this.onViewActionListener;
            if (onViewActionListener != null) {
                onViewActionListener.onStartSendMessageAction();
                return;
            }
            return;
        }
        if (id == R.id.video_ppt_switch_port_iv || id == R.id.video_ppt_switch_land_iv) {
            boolean zIsSelected = this.videoPptSwitchPortIv.isSelected();
            boolean z2 = !zIsSelected;
            this.videoPptSwitchPortIv.setSelected(z2);
            this.videoPptSwitchLandIv.setSelected(z2);
            IPLVLCLiveMediaController.OnViewActionListener onViewActionListener2 = this.onViewActionListener;
            if (onViewActionListener2 != null) {
                onViewActionListener2.onClickShowOrHideSubTab(zIsSelected);
                return;
            }
            return;
        }
        if (id == R.id.bulletin_land_iv) {
            IPLVLCLiveMediaController.OnViewActionListener onViewActionListener3 = this.onViewActionListener;
            if (onViewActionListener3 != null) {
                onViewActionListener3.onShowBulletinAction();
                return;
            }
            return;
        }
        if (id == R.id.likes_land_iv) {
            show();
            postDelayed(new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.media.controller.PLVLCLiveMediaController.16
                @Override // java.lang.Runnable
                public void run() {
                    PLVLCLiveMediaController.this.likesLandIv.addLoveIcon(1);
                }
            }, 200L);
            IPLVLCLiveMediaController.OnViewActionListener onViewActionListener4 = this.onViewActionListener;
            if (onViewActionListener4 != null) {
                onViewActionListener4.onSendLikesAction();
                return;
            }
            return;
        }
        if (id == R.id.plvlc_iv_show_point_reward) {
            IPLVLCLiveMediaController.OnViewActionListener onViewActionListener5 = this.onViewActionListener;
            if (onViewActionListener5 != null) {
                onViewActionListener5.onShowRewardView();
                hide();
                return;
            }
            return;
        }
        if (id != this.liveControlFloatingIv.getId() && id != this.liveControlFloatingLandIv.getId()) {
            if (id == this.commodityView.getId()) {
                this.commodityViewModel.showProductLayoutOnLandscape();
            }
        } else {
            IPLVLCLiveMediaController.OnViewActionListener onViewActionListener6 = this.onViewActionListener;
            if (onViewActionListener6 != null) {
                onViewActionListener6.onClickFloating();
            }
        }
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        PLVCommonLog.d(TAG, "onConfigurationChanged");
        super.onConfigurationChanged(configuration);
        if (configuration.orientation == 2) {
            setLandscapeController();
        } else {
            setPortraitController();
        }
    }

    @Override // com.plv.business.api.common.mediacontrol.IPLVMediaController
    public void onLongBuffering(String str) {
        showBitrateChangeView();
    }

    @Override // com.plv.business.api.common.mediacontrol.IMediaController, com.easefun.polyv.mediasdk.example.widget.media.IMediaController
    public void setAnchorView(View view) {
    }

    @Override // android.view.View, com.plv.business.api.common.mediacontrol.IMediaController, com.easefun.polyv.mediasdk.example.widget.media.IMediaController
    public void setEnabled(boolean z2) {
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveMediaController
    public void setLandscapeController(@NonNull IPLVLCLiveLandscapePlayerController iPLVLCLiveLandscapePlayerController) {
        this.landscapeController = iPLVLCLiveLandscapePlayerController;
        this.videoControllerLandLy = iPLVLCLiveLandscapePlayerController.getLandRoot();
        ImageView backView = iPLVLCLiveLandscapePlayerController.getBackView();
        this.backLandIv = backView;
        backView.setOnClickListener(this);
        this.videoNameLandTv = iPLVLCLiveLandscapePlayerController.getNameView();
        this.videoViewerCountLandTv = iPLVLCLiveLandscapePlayerController.getViewerCountView();
        ImageView pauseView = iPLVLCLiveLandscapePlayerController.getPauseView();
        this.videoPauseLandIv = pauseView;
        pauseView.setOnClickListener(this);
        ImageView refreshView = iPLVLCLiveLandscapePlayerController.getRefreshView();
        this.videoRefreshLandIv = refreshView;
        refreshView.setOnClickListener(this);
        ImageView switchView = iPLVLCLiveLandscapePlayerController.getSwitchView();
        this.videoPptSwitchLandIv = switchView;
        switchView.setOnClickListener(this);
        ImageView floatingControlView = iPLVLCLiveLandscapePlayerController.getFloatingControlView();
        this.liveControlFloatingLandIv = floatingControlView;
        floatingControlView.setOnClickListener(this);
        TextView messageSender = iPLVLCLiveLandscapePlayerController.getMessageSender();
        this.startSendMessageLandIv = messageSender;
        messageSender.setOnClickListener(this);
        this.danmuSwitchLandIv = iPLVLCLiveLandscapePlayerController.getDanmuSwitchView();
        ImageView bulletinView = iPLVLCLiveLandscapePlayerController.getBulletinView();
        this.bulletinLandIv = bulletinView;
        bulletinView.setOnClickListener(this);
        PLVLCLikeIconView likesView = iPLVLCLiveLandscapePlayerController.getLikesView();
        this.likesLandIv = likesView;
        likesView.setOnButtonClickListener(this);
        ImageView moreView = iPLVLCLiveLandscapePlayerController.getMoreView();
        this.moreLandIv = moreView;
        moreView.setOnClickListener(this);
        ImageView rewardView = iPLVLCLiveLandscapePlayerController.getRewardView();
        this.rewardView = rewardView;
        rewardView.setOnClickListener(this);
        ImageView commodityView = iPLVLCLiveLandscapePlayerController.getCommodityView();
        this.commodityView = commodityView;
        commodityView.setOnClickListener(this);
        PLVLCPPTTurnPageLayout pPTTurnPageLayout = iPLVLCLiveLandscapePlayerController.getPPTTurnPageLayout();
        this.pptTurnPageLandLayout = pPTTurnPageLayout;
        pPTTurnPageLayout.setOnPPTTurnPageListener(new PLVLCPPTTurnPageLayout.OnPPTTurnPageListener() { // from class: com.easefun.polyv.livecloudclass.modules.media.controller.PLVLCLiveMediaController.8
            @Override // com.easefun.polyv.livecloudclass.modules.media.widget.PLVLCPPTTurnPageLayout.OnPPTTurnPageListener
            public void onPPTTurnPage(String str) {
                PLVLCLiveMediaController.this.onViewActionListener.onPPTTurnPage(str);
            }
        });
        if (ScreenUtils.isPortrait()) {
            this.videoControllerLandLy.setVisibility(8);
        } else {
            this.videoControllerLandLy.setVisibility(0);
        }
        observeCommodityStatus();
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveMediaController
    public void setLivePlayerPresenter(@NonNull IPLVLivePlayerContract.ILivePlayerPresenter iLivePlayerPresenter) {
        this.livePlayerPresenter = iLivePlayerPresenter;
        observePlayInfoVO();
    }

    @Override // com.plv.business.api.common.mediacontrol.IMediaController, com.easefun.polyv.mediasdk.example.widget.media.IMediaController
    public void setMediaPlayer(MediaController.MediaPlayerControl mediaPlayerControl) {
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveMediaController
    public void setOnLikesSwitchEnabled(boolean z2) {
        this.likesLandIv.setVisibility(z2 ? 0 : 4);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveMediaController
    public void setOnViewActionListener(IPLVLCLiveMediaController.OnViewActionListener onViewActionListener) {
        this.onViewActionListener = onViewActionListener;
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveMediaController
    public void setServerEnablePPT(boolean z2) {
        this.isServerEnablePPT = z2;
        this.videoPptSwitchPortIv.setVisibility(z2 ? 0 : 8);
        this.videoPptSwitchLandIv.setVisibility(z2 ? 0 : 8);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveMediaController
    public void setTurnPageLayoutStatus(boolean z2) {
        this.pptTurnPageLandLayout.setVisibility(z2 ? 0 : 8);
        this.pptTurnPagePortLayout.setVisibility(z2 ? 0 : 8);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveMediaController
    public void setVideoName(String str) {
        this.videoNamePortTv.setText(str);
        this.videoNameLandTv.setText(str);
    }

    @Override // android.view.View
    public void setVisibility(int i2) {
        super.setVisibility(i2);
        if (i2 == 0) {
            this.landscapeController.show();
        } else {
            this.landscapeController.hide();
        }
    }

    @Override // com.plv.business.api.common.mediacontrol.IPLVMediaController, com.plv.business.api.common.mediacontrol.IMediaController, com.easefun.polyv.mediasdk.example.widget.media.IMediaController
    public void show() {
        setVisibility(0);
        IPLVLCLiveMediaController.OnViewActionListener onViewActionListener = this.onViewActionListener;
        if (onViewActionListener != null) {
            onViewActionListener.onShow(true);
        }
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveMediaController
    public void showMoreLayout() {
        if (PLVScreenUtils.isPortrait(getContext())) {
            ImageView imageView = this.morePortIv;
            if (imageView != null) {
                imageView.performClick();
                return;
            }
            return;
        }
        ImageView imageView2 = this.moreLandIv;
        if (imageView2 != null) {
            imageView2.performClick();
        }
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveMediaController
    public void updateOnClickCloseFloatingView() {
        this.videoPptSwitchPortIv.performClick();
        if (this.hasShowReopenFloatingViewTip) {
            return;
        }
        this.hasShowReopenFloatingViewTip = true;
        this.tvReopenFloatingViewTip.setVisibility(0);
        dispose(this.reopenFloatingDelay);
        this.reopenFloatingDelay = PLVRxTimer.delay(C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS, new Consumer<Long>() { // from class: com.easefun.polyv.livecloudclass.modules.media.controller.PLVLCLiveMediaController.9
            @Override // io.reactivex.functions.Consumer
            public void accept(Long l2) throws Exception {
                PLVLCLiveMediaController.this.tvReopenFloatingViewTip.setVisibility(8);
            }
        });
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveMediaController
    public void updatePPTStatusChange(PLVPPTStatus pLVPPTStatus) {
        this.pptTurnPagePortLayout.updatePageData(pLVPPTStatus);
        this.pptTurnPageLandLayout.updatePageData(pLVPPTStatus);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveMediaController
    public void updateRewardView(boolean z2) {
        ImageView imageView = this.rewardView;
        if (imageView != null) {
            imageView.setVisibility(z2 ? 0 : 8);
        }
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveMediaController
    public void updateViewerCount(long j2) {
        this.videoViewerCountPortTv.setVisibility(0);
        this.videoViewerCountLandTv.setVisibility(0);
        String wString = StringUtils.toWString(j2);
        this.videoViewerCountPortTv.setText(wString + "次播放");
        this.videoViewerCountLandTv.setText(wString + "次播放");
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveMediaController
    public void updateWhenJoinLinkMic(boolean z2) {
        this.isLinkMic = true;
        this.isRequestingJoinLinkMic = false;
        this.hideRefreshButtonInRtcChannel = z2;
        getLiveMediaDispatcher().updateViewProperties();
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveMediaController
    public void updateWhenJoinRtc(boolean z2) {
        this.isRtcChannelWatch = true;
        this.hideRefreshButtonInRtcChannel = z2;
        getLiveMediaDispatcher().updateViewProperties();
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveMediaController
    public void updateWhenLeaveLinkMic() {
        this.isLinkMic = false;
        this.isRequestingJoinLinkMic = false;
        getLiveMediaDispatcher().updateViewProperties();
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveMediaController
    public void updateWhenLeaveRtc() {
        this.isRtcChannelWatch = false;
        getLiveMediaDispatcher().updateViewProperties();
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveMediaController
    public void updateWhenLinkMicOpenOrClose(boolean z2) {
        if (this.isChannelOpenLinkMic == z2) {
            return;
        }
        this.isChannelOpenLinkMic = z2;
        if (z2) {
            return;
        }
        this.isRequestingJoinLinkMic = false;
        getLiveMediaDispatcher().updateViewProperties();
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveMediaController
    public void updateWhenOnlyAudio(boolean z2) {
        this.moreLayout.updateWhenOnlyAudio(z2);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveMediaController
    public void updateWhenRequestJoinLinkMic(boolean z2) {
        this.isRequestingJoinLinkMic = z2;
        getLiveMediaDispatcher().updateViewProperties();
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveMediaController
    public void updateWhenSubVideoViewClick(boolean z2) {
        this.isMainVideoViewPlaying = z2;
        this.videoPausePortIv.setVisibility(8);
        this.videoPauseLandIv.setVisibility(8);
        this.videoRefreshPortIv.setVisibility(8);
        this.videoRefreshLandIv.setVisibility(8);
        this.videoPptSwitchPortIv.setVisibility(8);
        this.videoPptSwitchLandIv.setVisibility(8);
        this.liveMoreControlLl.setVisibility(8);
        this.liveControlFloatingLandIv.setVisibility(8);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.startSendMessageLandIv.getLayoutParams();
        marginLayoutParams.leftMargin = ConvertUtils.dp2px(106.0f);
        marginLayoutParams.rightMargin = ConvertUtils.dp2px(112.0f);
        this.startSendMessageLandIv.setLayoutParams(marginLayoutParams);
        getLiveMediaDispatcher().updateViewProperties();
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveMediaController
    public void updateWhenVideoViewPrepared() {
        this.isMainVideoViewPlaying = true;
        this.videoPausePortIv.setVisibility(0);
        this.videoPauseLandIv.setVisibility(0);
        this.videoRefreshPortIv.setVisibility(0);
        this.videoRefreshLandIv.setVisibility(0);
        if (this.isServerEnablePPT) {
            this.videoPptSwitchPortIv.setVisibility(0);
            this.videoPptSwitchLandIv.setVisibility(0);
        }
        this.liveMoreControlLl.setVisibility(0);
        this.liveControlFloatingLandIv.setVisibility(0);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.startSendMessageLandIv.getLayoutParams();
        marginLayoutParams.leftMargin = ConvertUtils.dp2px(32.0f);
        marginLayoutParams.rightMargin = ConvertUtils.dp2px(38.0f);
        this.startSendMessageLandIv.setLayoutParams(marginLayoutParams);
        getLiveMediaDispatcher().updateViewProperties();
    }

    public PLVLCLiveMediaController(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    @Override // com.plv.business.api.common.mediacontrol.IPLVMediaController
    public void onPrepared(PolyvLiveVideoView polyvLiveVideoView) {
        updateMoreLayout();
        updateBitrateVO();
    }

    public PLVLCLiveMediaController(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.commodityViewModel = (PLVCommodityViewModel) PLVDependManager.getInstance().get(PLVCommodityViewModel.class);
        this.hasShowReopenFloatingViewTip = false;
        initView();
    }

    private void setLandscapeController() {
        post(new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.media.controller.PLVLCLiveMediaController.13
            @Override // java.lang.Runnable
            public void run() {
                PLVLCLiveMediaController.this.videoControllerPortLy.setVisibility(8);
                PLVLCLiveMediaController.this.videoControllerLandLy.setVisibility(0);
            }
        });
    }
}
