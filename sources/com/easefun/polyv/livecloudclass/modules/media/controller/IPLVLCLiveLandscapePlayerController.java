package com.easefun.polyv.livecloudclass.modules.media.controller;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.easefun.polyv.livecloudclass.modules.chatroom.widget.PLVLCLikeIconView;
import com.easefun.polyv.livecloudclass.modules.media.widget.PLVLCPPTTurnPageLayout;
import com.easefun.polyv.livecommon.ui.widget.PLVTriangleIndicateTextView;

/* loaded from: classes3.dex */
public interface IPLVLCLiveLandscapePlayerController {
    ImageView getBackView();

    ImageView getBulletinView();

    TextView getCardEnterCdView();

    PLVTriangleIndicateTextView getCardEnterTipsView();

    ImageView getCardEnterView();

    ImageView getCommodityView();

    ImageView getDanmuSwitchView();

    ImageView getFloatingControlView();

    View getGradientBar();

    ViewGroup getLandRoot();

    PLVLCLikeIconView getLikesView();

    TextView getMessageSender();

    ImageView getMoreView();

    TextView getNameView();

    PLVLCPPTTurnPageLayout getPPTTurnPageLayout();

    ImageView getPauseView();

    ImageView getRefreshView();

    ImageView getRewardView();

    ImageView getSwitchView();

    TextView getViewerCountView();

    void hide();

    void show();
}
