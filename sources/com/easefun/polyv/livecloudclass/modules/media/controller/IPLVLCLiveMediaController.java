package com.easefun.polyv.livecloudclass.modules.media.controller;

import android.view.View;
import androidx.annotation.NonNull;
import com.easefun.polyv.businesssdk.api.common.meidacontrol.IPolyvMediaController;
import com.easefun.polyv.livecommon.module.modules.player.live.contract.IPLVLivePlayerContract;
import com.easefun.polyv.livescenes.video.PolyvLiveVideoView;
import com.plv.livescenes.document.model.PLVPPTStatus;

/* loaded from: classes3.dex */
public interface IPLVLCLiveMediaController extends IPolyvMediaController<PolyvLiveVideoView> {

    public interface OnViewActionListener {
        boolean isRtcPausing();

        void onChangeLowLatencyMode(boolean z2);

        void onClickFloating();

        void onClickShowOrHideSubTab(boolean z2);

        void onPPTTurnPage(String str);

        void onRtcPauseResume(boolean z2);

        void onSendLikesAction();

        void onShow(boolean z2);

        void onShowBulletinAction();

        void onShowRewardView();

        void onStartSendMessageAction();
    }

    void clean();

    void dispatchDanmuSwitchOnClicked(View view);

    IPLVLCLiveLandscapePlayerController getLandscapeController();

    void notifyChatroomStatusChanged(boolean z2, boolean z3);

    void notifyLowLatencyUpdate(boolean z2);

    void setLandscapeController(@NonNull IPLVLCLiveLandscapePlayerController iPLVLCLiveLandscapePlayerController);

    void setLivePlayerPresenter(@NonNull IPLVLivePlayerContract.ILivePlayerPresenter iLivePlayerPresenter);

    void setOnLikesSwitchEnabled(boolean z2);

    void setOnViewActionListener(OnViewActionListener onViewActionListener);

    void setServerEnablePPT(boolean z2);

    void setTurnPageLayoutStatus(boolean z2);

    void setVideoName(String str);

    void showMoreLayout();

    void updateOnClickCloseFloatingView();

    void updatePPTStatusChange(PLVPPTStatus pLVPPTStatus);

    void updateRewardView(boolean z2);

    void updateViewerCount(long j2);

    void updateWhenJoinLinkMic(boolean z2);

    void updateWhenJoinRtc(boolean z2);

    void updateWhenLeaveLinkMic();

    void updateWhenLeaveRtc();

    void updateWhenLinkMicOpenOrClose(boolean z2);

    void updateWhenOnlyAudio(boolean z2);

    void updateWhenRequestJoinLinkMic(boolean z2);

    void updateWhenSubVideoViewClick(boolean z2);

    void updateWhenVideoViewPrepared();
}
