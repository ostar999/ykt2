package com.easefun.polyv.livecloudclass.modules.media.controller;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.easefun.polyv.livecloudclass.R;
import com.easefun.polyv.livecloudclass.modules.chatroom.widget.PLVLCLikeIconView;
import com.easefun.polyv.livecloudclass.modules.media.widget.PLVLCPPTTurnPageLayout;
import com.easefun.polyv.livecommon.ui.widget.PLVTriangleIndicateTextView;
import com.easefun.polyv.livecommon.ui.widget.imageview.IPLVVisibilityChangedListener;
import com.easefun.polyv.livecommon.ui.widget.imageview.PLVSimpleImageView;
import com.plv.thirdpart.blankj.utilcode.util.ConvertUtils;

/* loaded from: classes3.dex */
public class PLVLCLiveLandscapeChannelController extends FrameLayout implements IPLVLCLiveLandscapePlayerController {
    private PLVSimpleImageView cardEnterIv;
    private View cardEnterReferView;
    private PLVSimpleImageView controllerCommodityIv;
    private View likesReferView;
    private PLVLCLikeIconView likesView;
    private PLVSimpleImageView rewardIv;

    public PLVLCLiveLandscapeChannelController(@NonNull Context context) {
        this(context, null);
    }

    private boolean hasRightBottomViewVisibleExcludeLikesView() {
        return this.cardEnterIv.getVisibility() == 0 || this.rewardIv.getVisibility() == 0 || this.controllerCommodityIv.getVisibility() == 0;
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.plvlc_live_land_channel_controller, (ViewGroup) this, true);
        this.likesView = (PLVLCLikeIconView) findViewById(R.id.likes_land_iv);
        this.likesReferView = findViewById(R.id.plvlc_refer_view_1);
        this.cardEnterReferView = findViewById(R.id.plvlc_refer_view_2);
        this.cardEnterIv = (PLVSimpleImageView) findViewById(R.id.plvlc_card_enter_view);
        this.rewardIv = (PLVSimpleImageView) findViewById(R.id.plvlc_iv_show_point_reward);
        this.controllerCommodityIv = (PLVSimpleImageView) findViewById(R.id.plvlc_controller_commodity_iv);
        observeForFitRightBottomViewLocation();
    }

    private boolean isRightBottomOnlyCardEnterViewVisible() {
        return (this.cardEnterIv.getVisibility() != 0 || this.rewardIv.getVisibility() == 0 || this.controllerCommodityIv.getVisibility() == 0 || this.likesView.getVisibility() == 0) ? false : true;
    }

    private void observeForFitRightBottomViewLocation() {
        this.likesView.setVisibilityChangedListener(new IPLVVisibilityChangedListener() { // from class: com.easefun.polyv.livecloudclass.modules.media.controller.PLVLCLiveLandscapeChannelController.1
            @Override // com.easefun.polyv.livecommon.ui.widget.imageview.IPLVVisibilityChangedListener
            public void onChanged(int i2) {
                PLVLCLiveLandscapeChannelController.this.processRightBottomViewVisibilityChanged(i2, true);
            }
        });
        this.cardEnterIv.setVisibilityChangedListener(new IPLVVisibilityChangedListener() { // from class: com.easefun.polyv.livecloudclass.modules.media.controller.PLVLCLiveLandscapeChannelController.2
            @Override // com.easefun.polyv.livecommon.ui.widget.imageview.IPLVVisibilityChangedListener
            public void onChanged(int i2) {
                PLVLCLiveLandscapeChannelController.this.processRightBottomViewVisibilityChanged(i2, false);
            }
        });
        this.rewardIv.setVisibilityChangedListener(new IPLVVisibilityChangedListener() { // from class: com.easefun.polyv.livecloudclass.modules.media.controller.PLVLCLiveLandscapeChannelController.3
            @Override // com.easefun.polyv.livecommon.ui.widget.imageview.IPLVVisibilityChangedListener
            public void onChanged(int i2) {
                PLVLCLiveLandscapeChannelController.this.processRightBottomViewVisibilityChanged(i2, false);
            }
        });
        this.controllerCommodityIv.setVisibilityChangedListener(new IPLVVisibilityChangedListener() { // from class: com.easefun.polyv.livecloudclass.modules.media.controller.PLVLCLiveLandscapeChannelController.4
            @Override // com.easefun.polyv.livecommon.ui.widget.imageview.IPLVVisibilityChangedListener
            public void onChanged(int i2) {
                PLVLCLiveLandscapeChannelController.this.processRightBottomViewVisibilityChanged(i2, false);
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
            if (this.likesView.getVisibility() != 0) {
                this.likesReferView.getLayoutParams().width = ConvertUtils.dp2px(4.0f);
            }
        } else if (!hasRightBottomViewVisibleExcludeLikesView()) {
            this.likesReferView.getLayoutParams().width = ConvertUtils.dp2px(60.0f);
        }
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.cardEnterIv.getLayoutParams();
        if (marginLayoutParams != null) {
            marginLayoutParams.rightMargin = ConvertUtils.dp2px(isRightBottomOnlyCardEnterViewVisible() ? 44.0f : 20.0f);
        }
        ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) this.cardEnterReferView.getLayoutParams();
        if (marginLayoutParams2 != null) {
            marginLayoutParams2.rightMargin = ConvertUtils.dp2px(isRightBottomOnlyCardEnterViewVisible() ? 34.0f : 10.0f);
        }
        this.likesReferView.requestLayout();
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveLandscapePlayerController
    public ImageView getBackView() {
        return (ImageView) findViewById(R.id.back_land_iv);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveLandscapePlayerController
    public ImageView getBulletinView() {
        return (ImageView) findViewById(R.id.bulletin_land_iv);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveLandscapePlayerController
    public TextView getCardEnterCdView() {
        return (TextView) findViewById(R.id.plvlc_card_enter_cd_tv);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveLandscapePlayerController
    public PLVTriangleIndicateTextView getCardEnterTipsView() {
        return (PLVTriangleIndicateTextView) findViewById(R.id.plvlc_card_enter_tips_view);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveLandscapePlayerController
    public ImageView getCommodityView() {
        return this.controllerCommodityIv;
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveLandscapePlayerController
    public ImageView getDanmuSwitchView() {
        return (ImageView) findViewById(R.id.danmu_switch_land_iv);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveLandscapePlayerController
    public ImageView getFloatingControlView() {
        return (ImageView) findViewById(R.id.plvlc_live_control_floating_land_iv);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveLandscapePlayerController
    public View getGradientBar() {
        return findViewById(R.id.gradient_bar_land_ly);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveLandscapePlayerController
    public ViewGroup getLandRoot() {
        return (ViewGroup) findViewById(R.id.video_controller_land_ly);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveLandscapePlayerController
    public PLVLCLikeIconView getLikesView() {
        return this.likesView;
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveLandscapePlayerController
    public TextView getMessageSender() {
        return (TextView) findViewById(R.id.start_send_message_land_tv);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveLandscapePlayerController
    public ImageView getMoreView() {
        return (ImageView) findViewById(R.id.more_land_iv);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveLandscapePlayerController
    public TextView getNameView() {
        return (TextView) findViewById(R.id.video_name_land_tv);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveLandscapePlayerController
    public PLVLCPPTTurnPageLayout getPPTTurnPageLayout() {
        return (PLVLCPPTTurnPageLayout) findViewById(R.id.video_ppt_turn_page_land_layout);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveLandscapePlayerController
    public ImageView getPauseView() {
        return (ImageView) findViewById(R.id.video_pause_land_iv);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveLandscapePlayerController
    public ImageView getRefreshView() {
        return (ImageView) findViewById(R.id.video_refresh_land_iv);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveLandscapePlayerController
    public ImageView getSwitchView() {
        return (ImageView) findViewById(R.id.video_ppt_switch_land_iv);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveLandscapePlayerController
    public TextView getViewerCountView() {
        return (TextView) findViewById(R.id.video_viewer_count_land_tv);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveLandscapePlayerController
    public void hide() {
        setVisibility(8);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveLandscapePlayerController
    public void show() {
        setVisibility(0);
    }

    public PLVLCLiveLandscapeChannelController(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveLandscapePlayerController
    public PLVSimpleImageView getCardEnterView() {
        return this.cardEnterIv;
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveLandscapePlayerController
    public PLVSimpleImageView getRewardView() {
        return this.rewardIv;
    }

    public PLVLCLiveLandscapeChannelController(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        initView();
    }
}
