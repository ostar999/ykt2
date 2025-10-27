package com.easefun.polyv.livecloudclass.modules.media.controller;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.easefun.polyv.businesssdk.api.common.meidacontrol.IPolyvMediaController;
import com.easefun.polyv.livecommon.module.modules.player.playback.contract.IPLVPlaybackPlayerContract;
import com.easefun.polyv.livecommon.ui.widget.PLVTriangleIndicateTextView;
import com.easefun.polyv.livescenes.playback.video.PolyvPlaybackVideoView;

/* loaded from: classes3.dex */
public interface IPLVLCPlaybackMediaController extends IPolyvMediaController<PolyvPlaybackVideoView> {

    public interface OnViewActionListener {
        void onClickShowOrHideSubTab(boolean z2);

        void onSendLikesAction();

        void onStartSendMessageAction();
    }

    void clean();

    void dispatchDanmuSwitchOnClicked(View view);

    TextView getCardEnterCdView();

    PLVTriangleIndicateTextView getCardEnterTipsView();

    ImageView getCardEnterView();

    View getLandscapeDanmuSwitchView();

    void notifyChatroomStatusChanged(boolean z2, boolean z3);

    boolean onBackPressed();

    void playOrPause();

    void setChatPlaybackEnabled(boolean z2);

    void setOnLikesSwitchEnabled(boolean z2);

    void setOnViewActionListener(OnViewActionListener onViewActionListener);

    void setPlaybackPlayerPresenter(IPLVPlaybackPlayerContract.IPlaybackPlayerPresenter iPlaybackPlayerPresenter);

    void setServerEnablePPT(boolean z2);

    void updateOnClickCloseFloatingView();
}
