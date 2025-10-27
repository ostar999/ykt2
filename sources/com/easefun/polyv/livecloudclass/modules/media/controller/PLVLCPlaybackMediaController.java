package com.easefun.polyv.livecloudclass.modules.media.controller;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import com.easefun.polyv.livecloudclass.R;
import com.easefun.polyv.livecloudclass.modules.chatroom.widget.PLVLCLikeIconView;
import com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCPlaybackMediaController;
import com.easefun.polyv.livecloudclass.modules.media.widget.PLVLCPlaybackMoreLayout;
import com.easefun.polyv.livecommon.module.modules.commodity.viewmodel.PLVCommodityViewModel;
import com.easefun.polyv.livecommon.module.modules.commodity.viewmodel.vo.PLVCommodityUiState;
import com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract;
import com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.data.PLVPlayInfoVO;
import com.easefun.polyv.livecommon.module.utils.rotaion.PLVOrientationManager;
import com.easefun.polyv.livecommon.ui.widget.PLVTriangleIndicateTextView;
import com.easefun.polyv.livecommon.ui.widget.imageview.IPLVVisibilityChangedListener;
import com.easefun.polyv.livecommon.ui.widget.imageview.PLVSimpleImageView;
import com.easefun.polyv.livescenes.playback.video.PolyvPlaybackVideoView;
import com.google.android.exoplayer2.C;
import com.plv.foundationsdk.component.di.PLVDependManager;
import com.plv.foundationsdk.rx.PLVRxTimer;
import com.plv.foundationsdk.utils.PLVScreenUtils;
import com.plv.foundationsdk.utils.PLVTimeUtils;
import com.plv.thirdpart.blankj.utilcode.util.ConvertUtils;
import com.plv.thirdpart.blankj.utilcode.util.ScreenUtils;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/* loaded from: classes3.dex */
public class PLVLCPlaybackMediaController extends FrameLayout implements IPLVLCPlaybackMediaController, View.OnClickListener {
    private static final int SHOW_TIME = 5000;
    private static final String TAG = "PLVLCPlaybackMediaController";
    private ImageView btnMoreLand;
    private ImageView btnMorePort;
    private TextView cardEnterCdLandTv;
    private PLVSimpleImageView cardEnterLandView;
    private View cardEnterReferView;
    private PLVTriangleIndicateTextView cardEnterTipsLandView;
    private final PLVCommodityViewModel commodityViewModel;
    private PLVSimpleImageView controllerCommodityLandIv;
    private boolean hasShowReopenFloatingViewTip;
    private ImageView iVFullScreenPort;
    private boolean isPbDragging;
    private boolean isServerEnablePPT;
    private ImageView ivBackLand;
    private ImageView ivBackPort;
    private ImageView ivDanmuSwitchLand;
    private PLVLCLikeIconView ivLikesLand;
    private ImageView ivPlayPauseLand;
    private ImageView ivPlayPausePort;
    private ImageView ivSubviewShowLand;
    private ImageView ivSubviewShowPort;
    private ImageView ivTopGradientPort;
    private View likesReferView;
    private PLVLCPlaybackMoreLayout moreLayout;
    private IPLVLCPlaybackMediaController.OnViewActionListener onViewActionListener;
    private SeekBar.OnSeekBarChangeListener playProgressChangeListener;
    private IPLVPlaybackPlayerContract.IPlaybackPlayerPresenter playerPresenter;
    private Disposable reopenFloatingDelay;
    private RelativeLayout rlRootLand;
    private RelativeLayout rlRootPort;
    private SeekBar sbPlayProgressLand;
    private SeekBar sbPlayProgressPort;
    private TextView tvCurrentTimeLand;
    private TextView tvCurrentTimePort;
    private TextView tvReopenFloatingViewTip;
    private TextView tvStartSendMessageLand;
    private TextView tvTotalTimeLand;
    private TextView tvTotalTimePort;
    private TextView tvVideoNameLand;
    private TextView tvVideoNamePort;

    public PLVLCPlaybackMediaController(@NonNull Context context) {
        this(context, null);
    }

    private void dispose(Disposable disposable) {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    private boolean hasRightBottomViewVisibleExcludeLikesView() {
        return this.cardEnterLandView.getVisibility() == 0 || this.controllerCommodityLandIv.getVisibility() == 0;
    }

    private void initMoreLayout() {
        PLVLCPlaybackMoreLayout pLVLCPlaybackMoreLayout = new PLVLCPlaybackMoreLayout(this);
        this.moreLayout = pLVLCPlaybackMoreLayout;
        pLVLCPlaybackMoreLayout.setOnSpeedSelectedListener(new PLVLCPlaybackMoreLayout.OnSpeedSelectedListener() { // from class: com.easefun.polyv.livecloudclass.modules.media.controller.PLVLCPlaybackMediaController.1
            @Override // com.easefun.polyv.livecloudclass.modules.media.widget.PLVLCPlaybackMoreLayout.OnSpeedSelectedListener
            public void onSpeedSelected(Float f2, int i2) {
                PLVLCPlaybackMediaController.this.playerPresenter.setSpeed(f2.floatValue());
            }
        });
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.plvlc_playback_controller_layout, (ViewGroup) this, true);
        this.ivPlayPauseLand = (ImageView) findViewById(R.id.plvlc_playback_controller_land_iv_playpause);
        this.tvCurrentTimeLand = (TextView) findViewById(R.id.plvlc_playback_controller_land_tv_currenttime);
        this.tvTotalTimeLand = (TextView) findViewById(R.id.plvlc_playback_controller_land_tv_totaltime);
        this.ivSubviewShowLand = (ImageView) findViewById(R.id.plvlc_playback_controller_land_iv_subview_show_land);
        this.sbPlayProgressLand = (SeekBar) findViewById(R.id.plvlc_playback_controller_land_sb_playprogress);
        this.btnMoreLand = (ImageView) findViewById(R.id.plvlc_playback_controller_land_bt_more);
        this.ivBackLand = (ImageView) findViewById(R.id.plvlc_playback_controller_land_iv_back);
        this.tvVideoNameLand = (TextView) findViewById(R.id.plvlc_playback_controller_land_tv_video_name);
        this.ivLikesLand = (PLVLCLikeIconView) findViewById(R.id.plvlc_playback_controller_land_iv_likes);
        this.tvStartSendMessageLand = (TextView) findViewById(R.id.plvlc_playback_controller_land_tv_start_send_message);
        this.ivDanmuSwitchLand = (ImageView) findViewById(R.id.plvlc_playback_controller_land_iv_danmu_switch);
        this.rlRootLand = (RelativeLayout) findViewById(R.id.plvlc_playback_controller_land_rl_root);
        this.controllerCommodityLandIv = (PLVSimpleImageView) findViewById(R.id.plvlc_controller_commodity_land_iv);
        this.cardEnterLandView = (PLVSimpleImageView) findViewById(R.id.plvlc_card_enter_land_view);
        this.cardEnterCdLandTv = (TextView) findViewById(R.id.plvlc_card_enter_cd_land_tv);
        this.cardEnterTipsLandView = (PLVTriangleIndicateTextView) findViewById(R.id.plvlc_card_enter_tips_land_view);
        this.likesReferView = findViewById(R.id.plvlc_refer_view_1);
        this.cardEnterReferView = findViewById(R.id.plvlc_refer_view_2);
        this.ivPlayPausePort = (ImageView) findViewById(R.id.plvlc_playback_controller_port_iv_play_pause);
        this.tvCurrentTimePort = (TextView) findViewById(R.id.plvlc_playback_controller_port_tv_currenttime);
        this.tvTotalTimePort = (TextView) findViewById(R.id.plvlc_playback_controller_port_tv_totaltime);
        this.sbPlayProgressPort = (SeekBar) findViewById(R.id.plvlc_playback_controller_port_sb_playprogress);
        this.iVFullScreenPort = (ImageView) findViewById(R.id.plvlc_playback_controller_port_iv_full_screen);
        this.ivSubviewShowPort = (ImageView) findViewById(R.id.plvlc_playback_controller_port_iv_subview_show);
        this.btnMorePort = (ImageView) findViewById(R.id.plvlc_playback_controller_port_btn_controller_more);
        this.ivBackPort = (ImageView) findViewById(R.id.plvlc_playback_controller_port_iv_back);
        this.ivTopGradientPort = (ImageView) findViewById(R.id.plvlc_playback_controller_port_iv_top_gradient);
        this.tvVideoNamePort = (TextView) findViewById(R.id.plvlc_playback_controller_port_tv_video_name);
        this.rlRootPort = (RelativeLayout) findViewById(R.id.plvlc_playback_controller_port_rl_root);
        this.tvReopenFloatingViewTip = (TextView) findViewById(R.id.plvlc_playback_player_controller_tv_reopen_floating_view);
        this.ivBackLand.setOnClickListener(this);
        this.ivPlayPauseLand.setOnClickListener(this);
        this.ivPlayPausePort.setOnClickListener(this);
        this.iVFullScreenPort.setOnClickListener(this);
        this.sbPlayProgressLand.setOnSeekBarChangeListener(this.playProgressChangeListener);
        this.sbPlayProgressPort.setOnSeekBarChangeListener(this.playProgressChangeListener);
        this.ivSubviewShowLand.setOnClickListener(this);
        this.ivSubviewShowPort.setOnClickListener(this);
        this.btnMoreLand.setOnClickListener(this);
        this.btnMorePort.setOnClickListener(this);
        this.ivBackPort.setOnClickListener(this);
        this.ivLikesLand.setOnButtonClickListener(this);
        this.tvStartSendMessageLand.setOnClickListener(this);
        this.controllerCommodityLandIv.setOnClickListener(this);
        initMoreLayout();
        if (PLVScreenUtils.isLandscape(getContext())) {
            setLandscapeController();
        } else {
            setPortraitController();
        }
        observeCommodityStatus();
        observeForFitRightBottomViewLocation();
    }

    private boolean isRightBottomOnlyCardEnterViewVisible() {
        return (this.cardEnterLandView.getVisibility() != 0 || this.controllerCommodityLandIv.getVisibility() == 0 || this.ivLikesLand.getVisibility() == 0) ? false : true;
    }

    private void observeCommodityStatus() {
        this.commodityViewModel.getCommodityUiStateLiveData().observe((LifecycleOwner) getContext(), new Observer<PLVCommodityUiState>() { // from class: com.easefun.polyv.livecloudclass.modules.media.controller.PLVLCPlaybackMediaController.2
            boolean needShowControllerOnClosed = false;

            @Override // androidx.lifecycle.Observer
            public void onChanged(@Nullable PLVCommodityUiState pLVCommodityUiState) {
                if (pLVCommodityUiState == null) {
                    return;
                }
                PLVLCPlaybackMediaController.this.controllerCommodityLandIv.setVisibility(pLVCommodityUiState.hasProductView ? 0 : 8);
                if (pLVCommodityUiState.showProductViewOnLandscape) {
                    if (this.needShowControllerOnClosed) {
                        return;
                    }
                    this.needShowControllerOnClosed = PLVLCPlaybackMediaController.this.isShowing();
                    PLVLCPlaybackMediaController.this.hide();
                    return;
                }
                if (this.needShowControllerOnClosed) {
                    PLVLCPlaybackMediaController.this.show();
                    this.needShowControllerOnClosed = false;
                }
            }
        });
    }

    private void observeForFitRightBottomViewLocation() {
        this.ivLikesLand.setVisibilityChangedListener(new IPLVVisibilityChangedListener() { // from class: com.easefun.polyv.livecloudclass.modules.media.controller.PLVLCPlaybackMediaController.3
            @Override // com.easefun.polyv.livecommon.ui.widget.imageview.IPLVVisibilityChangedListener
            public void onChanged(int i2) {
                PLVLCPlaybackMediaController.this.processRightBottomViewVisibilityChanged(i2, true);
            }
        });
        this.cardEnterLandView.setVisibilityChangedListener(new IPLVVisibilityChangedListener() { // from class: com.easefun.polyv.livecloudclass.modules.media.controller.PLVLCPlaybackMediaController.4
            @Override // com.easefun.polyv.livecommon.ui.widget.imageview.IPLVVisibilityChangedListener
            public void onChanged(int i2) {
                PLVLCPlaybackMediaController.this.processRightBottomViewVisibilityChanged(i2, false);
            }
        });
        this.controllerCommodityLandIv.setVisibilityChangedListener(new IPLVVisibilityChangedListener() { // from class: com.easefun.polyv.livecloudclass.modules.media.controller.PLVLCPlaybackMediaController.5
            @Override // com.easefun.polyv.livecommon.ui.widget.imageview.IPLVVisibilityChangedListener
            public void onChanged(int i2) {
                PLVLCPlaybackMediaController.this.processRightBottomViewVisibilityChanged(i2, false);
            }
        });
    }

    private void observePlayInfoVO() {
        this.playerPresenter.getData().getPlayInfoVO().observe((LifecycleOwner) getContext(), new Observer<PLVPlayInfoVO>() { // from class: com.easefun.polyv.livecloudclass.modules.media.controller.PLVLCPlaybackMediaController.10
            @Override // androidx.lifecycle.Observer
            public void onChanged(@Nullable PLVPlayInfoVO pLVPlayInfoVO) {
                if (pLVPlayInfoVO == null) {
                    return;
                }
                int position = pLVPlayInfoVO.getPosition();
                int totalTime = pLVPlayInfoVO.getTotalTime();
                int bufPercent = pLVPlayInfoVO.getBufPercent();
                boolean zIsPlaying = pLVPlayInfoVO.isPlaying();
                if (!PLVLCPlaybackMediaController.this.isPbDragging) {
                    long j2 = position;
                    PLVLCPlaybackMediaController.this.tvCurrentTimePort.setText(PLVTimeUtils.generateTime(j2, true));
                    PLVLCPlaybackMediaController.this.tvCurrentTimeLand.setText(PLVTimeUtils.generateTime(j2, true));
                    if (totalTime > 0) {
                        long j3 = totalTime;
                        PLVLCPlaybackMediaController.this.sbPlayProgressPort.setProgress((int) ((PLVLCPlaybackMediaController.this.sbPlayProgressPort.getMax() * j2) / j3));
                        PLVLCPlaybackMediaController.this.sbPlayProgressLand.setProgress((int) ((PLVLCPlaybackMediaController.this.sbPlayProgressLand.getMax() * j2) / j3));
                    } else {
                        PLVLCPlaybackMediaController.this.sbPlayProgressPort.setProgress(0);
                        PLVLCPlaybackMediaController.this.sbPlayProgressLand.setProgress(0);
                    }
                }
                PLVLCPlaybackMediaController.this.sbPlayProgressPort.setSecondaryProgress((PLVLCPlaybackMediaController.this.sbPlayProgressPort.getMax() * bufPercent) / 100);
                PLVLCPlaybackMediaController.this.sbPlayProgressLand.setSecondaryProgress((PLVLCPlaybackMediaController.this.sbPlayProgressPort.getMax() * bufPercent) / 100);
                if (zIsPlaying) {
                    PLVLCPlaybackMediaController.this.ivPlayPausePort.setSelected(true);
                    PLVLCPlaybackMediaController.this.ivPlayPauseLand.setSelected(true);
                } else {
                    PLVLCPlaybackMediaController.this.ivPlayPausePort.setSelected(false);
                    PLVLCPlaybackMediaController.this.ivPlayPauseLand.setSelected(false);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processRightBottomViewVisibilityChanged(int i2, boolean z2) {
        if (this.likesReferView.getLayoutParams() == null) {
            return;
        }
        boolean z3 = i2 == 0;
        if (z2) {
            if (z3 || !hasRightBottomViewVisibleExcludeLikesView()) {
                this.likesReferView.getLayoutParams().width = ConvertUtils.dp2px(60.0f);
            } else {
                this.likesReferView.getLayoutParams().width = ConvertUtils.dp2px(4.0f);
            }
        } else if (z3) {
            if (this.ivLikesLand.getVisibility() != 0) {
                this.likesReferView.getLayoutParams().width = ConvertUtils.dp2px(4.0f);
            }
        } else if (!hasRightBottomViewVisibleExcludeLikesView()) {
            this.likesReferView.getLayoutParams().width = ConvertUtils.dp2px(60.0f);
        }
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.cardEnterLandView.getLayoutParams();
        if (marginLayoutParams != null) {
            marginLayoutParams.rightMargin = ConvertUtils.dp2px(isRightBottomOnlyCardEnterViewVisible() ? 44.0f : 20.0f);
        }
        ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) this.cardEnterReferView.getLayoutParams();
        if (marginLayoutParams2 != null) {
            marginLayoutParams2.rightMargin = ConvertUtils.dp2px(isRightBottomOnlyCardEnterViewVisible() ? 34.0f : 10.0f);
        }
        this.likesReferView.requestLayout();
    }

    private void setLandscapeController() {
        post(new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.media.controller.PLVLCPlaybackMediaController.8
            @Override // java.lang.Runnable
            public void run() {
                PLVLCPlaybackMediaController.this.rlRootPort.setVisibility(8);
                PLVLCPlaybackMediaController.this.rlRootLand.setVisibility(0);
            }
        });
    }

    private void setPortraitController() {
        post(new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.media.controller.PLVLCPlaybackMediaController.9
            @Override // java.lang.Runnable
            public void run() {
                PLVLCPlaybackMediaController.this.rlRootPort.setVisibility(0);
                PLVLCPlaybackMediaController.this.rlRootLand.setVisibility(8);
            }
        });
    }

    private void updateMoreLayout() {
        this.moreLayout.updateViewWithPlayInfo(this.playerPresenter.getSpeed());
    }

    private void updateTotalTimeView() {
        String str = "/" + PLVTimeUtils.generateTime(this.playerPresenter.getDuration(), true);
        this.tvTotalTimePort.setText(str);
        this.tvTotalTimeLand.setText(str);
    }

    private void updateVideoName() {
        this.tvVideoNamePort.setText(this.playerPresenter.getVideoName());
        this.tvVideoNameLand.setText(this.playerPresenter.getVideoName());
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCPlaybackMediaController
    public void clean() {
        PLVLCPlaybackMoreLayout pLVLCPlaybackMoreLayout = this.moreLayout;
        if (pLVLCPlaybackMoreLayout != null) {
            pLVLCPlaybackMoreLayout.hide();
        }
        dispose(this.reopenFloatingDelay);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCPlaybackMediaController
    public void dispatchDanmuSwitchOnClicked(View view) {
        onClick(view);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCPlaybackMediaController
    public TextView getCardEnterCdView() {
        return this.cardEnterCdLandTv;
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCPlaybackMediaController
    public PLVTriangleIndicateTextView getCardEnterTipsView() {
        return this.cardEnterTipsLandView;
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCPlaybackMediaController
    public ImageView getCardEnterView() {
        return this.cardEnterLandView;
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCPlaybackMediaController
    public View getLandscapeDanmuSwitchView() {
        return this.ivDanmuSwitchLand;
    }

    @Override // com.plv.business.api.common.mediacontrol.IPLVMediaController, com.plv.business.api.common.mediacontrol.IMediaController, com.easefun.polyv.mediasdk.example.widget.media.IMediaController
    public void hide() {
        setVisibility(8);
    }

    @Override // com.plv.business.api.common.mediacontrol.IPLVMediaController, com.plv.business.api.common.mediacontrol.IMediaController, com.easefun.polyv.mediasdk.example.widget.media.IMediaController
    public boolean isShowing() {
        return isShown();
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCPlaybackMediaController
    public void notifyChatroomStatusChanged(boolean z2, boolean z3) {
        TextView textView = this.tvStartSendMessageLand;
        if (textView != null) {
            textView.setText(z2 ? "聊天室已关闭" : z3 ? "当前为专注模式，无法发言" : "跟大家聊点什么吧~");
            this.tvStartSendMessageLand.setOnClickListener((z2 || z3) ? null : this);
        }
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCPlaybackMediaController
    public boolean onBackPressed() {
        return false;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.plvlc_playback_controller_port_iv_back) {
            ((Activity) getContext()).onBackPressed();
            return;
        }
        if (id == R.id.plvlc_playback_controller_land_iv_back) {
            PLVOrientationManager.getInstance().setPortrait((Activity) getContext());
            return;
        }
        if (id == R.id.plvlc_playback_controller_land_iv_playpause || id == R.id.plvlc_playback_controller_port_iv_play_pause) {
            playOrPause();
            return;
        }
        if (id == R.id.plvlc_playback_controller_port_iv_full_screen) {
            PLVOrientationManager.getInstance().setLandscape((Activity) getContext());
            return;
        }
        if (id == R.id.plvlc_playback_controller_land_iv_subview_show_land || id == R.id.plvlc_playback_controller_port_iv_subview_show) {
            boolean zIsSelected = this.ivSubviewShowPort.isSelected();
            boolean z2 = !zIsSelected;
            this.ivSubviewShowPort.setSelected(z2);
            this.ivSubviewShowLand.setSelected(z2);
            IPLVLCPlaybackMediaController.OnViewActionListener onViewActionListener = this.onViewActionListener;
            if (onViewActionListener != null) {
                onViewActionListener.onClickShowOrHideSubTab(zIsSelected);
                return;
            }
            return;
        }
        if (id == R.id.plvlc_playback_controller_land_bt_more || id == R.id.plvlc_playback_controller_port_btn_controller_more) {
            hide();
            if (ScreenUtils.isPortrait()) {
                this.moreLayout.showWhenPortrait(getHeight());
                return;
            } else {
                this.moreLayout.showWhenLandscape();
                return;
            }
        }
        if (id == R.id.plvlc_playback_controller_land_iv_likes) {
            show();
            postDelayed(new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.media.controller.PLVLCPlaybackMediaController.11
                @Override // java.lang.Runnable
                public void run() {
                    PLVLCPlaybackMediaController.this.ivLikesLand.addLoveIcon(1);
                }
            }, 200L);
            IPLVLCPlaybackMediaController.OnViewActionListener onViewActionListener2 = this.onViewActionListener;
            if (onViewActionListener2 != null) {
                onViewActionListener2.onSendLikesAction();
                return;
            }
            return;
        }
        if (id != R.id.plvlc_playback_controller_land_tv_start_send_message) {
            if (id == this.controllerCommodityLandIv.getId()) {
                this.commodityViewModel.showProductLayoutOnLandscape();
            }
        } else {
            hide();
            IPLVLCPlaybackMediaController.OnViewActionListener onViewActionListener3 = this.onViewActionListener;
            if (onViewActionListener3 != null) {
                onViewActionListener3.onStartSendMessageAction();
            }
        }
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        if (configuration.orientation == 2) {
            setLandscapeController();
        } else {
            setPortraitController();
        }
    }

    @Override // com.plv.business.api.common.mediacontrol.IPLVMediaController
    public void onLongBuffering(String str) {
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCPlaybackMediaController
    public void playOrPause() {
        if (this.playerPresenter.isPlaying()) {
            this.playerPresenter.pause();
            this.ivPlayPausePort.setSelected(false);
            this.ivPlayPauseLand.setSelected(false);
        } else {
            this.playerPresenter.resume();
            this.ivPlayPausePort.setSelected(true);
            this.ivPlayPauseLand.setSelected(true);
        }
    }

    @Override // com.plv.business.api.common.mediacontrol.IMediaController, com.easefun.polyv.mediasdk.example.widget.media.IMediaController
    public void setAnchorView(View view) {
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCPlaybackMediaController
    public void setChatPlaybackEnabled(boolean z2) {
        TextView textView = this.tvStartSendMessageLand;
        if (textView != null) {
            if (z2) {
                textView.setText("聊天室暂时关闭");
                this.tvStartSendMessageLand.setEnabled(false);
            } else {
                textView.setText("跟大家聊点什么吧~");
                this.tvStartSendMessageLand.setEnabled(true);
            }
        }
    }

    @Override // android.view.View, com.plv.business.api.common.mediacontrol.IMediaController, com.easefun.polyv.mediasdk.example.widget.media.IMediaController
    public void setEnabled(boolean z2) {
    }

    @Override // com.plv.business.api.common.mediacontrol.IMediaController, com.easefun.polyv.mediasdk.example.widget.media.IMediaController
    public void setMediaPlayer(MediaController.MediaPlayerControl mediaPlayerControl) {
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCPlaybackMediaController
    public void setOnLikesSwitchEnabled(boolean z2) {
        this.ivLikesLand.setVisibility(z2 ? 0 : 4);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCPlaybackMediaController
    public void setOnViewActionListener(IPLVLCPlaybackMediaController.OnViewActionListener onViewActionListener) {
        this.onViewActionListener = onViewActionListener;
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCPlaybackMediaController
    public void setPlaybackPlayerPresenter(IPLVPlaybackPlayerContract.IPlaybackPlayerPresenter iPlaybackPlayerPresenter) {
        this.playerPresenter = iPlaybackPlayerPresenter;
        observePlayInfoVO();
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCPlaybackMediaController
    public void setServerEnablePPT(boolean z2) {
        this.isServerEnablePPT = z2;
        this.ivSubviewShowPort.setVisibility(z2 ? 0 : 8);
        this.ivSubviewShowLand.setVisibility(z2 ? 0 : 8);
    }

    @Override // com.plv.business.api.common.mediacontrol.IPLVMediaController, com.plv.business.api.common.mediacontrol.IMediaController, com.easefun.polyv.mediasdk.example.widget.media.IMediaController
    public void show() {
        setVisibility(0);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCPlaybackMediaController
    public void updateOnClickCloseFloatingView() {
        this.ivSubviewShowPort.performClick();
        if (this.hasShowReopenFloatingViewTip) {
            return;
        }
        this.hasShowReopenFloatingViewTip = true;
        this.tvReopenFloatingViewTip.setVisibility(0);
        dispose(this.reopenFloatingDelay);
        this.reopenFloatingDelay = PLVRxTimer.delay(C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS, new Consumer<Long>() { // from class: com.easefun.polyv.livecloudclass.modules.media.controller.PLVLCPlaybackMediaController.6
            @Override // io.reactivex.functions.Consumer
            public void accept(Long l2) throws Exception {
                PLVLCPlaybackMediaController.this.tvReopenFloatingViewTip.setVisibility(8);
            }
        });
    }

    public PLVLCPlaybackMediaController(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    @Override // com.plv.business.api.common.mediacontrol.IPLVMediaController
    public void onPrepared(PolyvPlaybackVideoView polyvPlaybackVideoView) {
        updateTotalTimeView();
        updateMoreLayout();
        updateVideoName();
    }

    public PLVLCPlaybackMediaController(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.commodityViewModel = (PLVCommodityViewModel) PLVDependManager.getInstance().get(PLVCommodityViewModel.class);
        this.hasShowReopenFloatingViewTip = false;
        this.playProgressChangeListener = new SeekBar.OnSeekBarChangeListener() { // from class: com.easefun.polyv.livecloudclass.modules.media.controller.PLVLCPlaybackMediaController.7
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i3, boolean z2) {
                if (z2) {
                    PLVLCPlaybackMediaController.this.show();
                    PLVLCPlaybackMediaController.this.isPbDragging = true;
                    long duration = (int) ((PLVLCPlaybackMediaController.this.playerPresenter.getDuration() * i3) / seekBar.getMax());
                    PLVLCPlaybackMediaController.this.tvCurrentTimePort.setText(PLVTimeUtils.generateTime(duration, true));
                    PLVLCPlaybackMediaController.this.tvCurrentTimeLand.setText(PLVTimeUtils.generateTime(duration, true));
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
                seekBar.setSelected(true);
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBar.setSelected(false);
                PLVLCPlaybackMediaController.this.isPbDragging = false;
                PLVLCPlaybackMediaController.this.playerPresenter.seekTo(seekBar.getProgress(), seekBar.getMax());
            }
        };
        initView();
    }
}
