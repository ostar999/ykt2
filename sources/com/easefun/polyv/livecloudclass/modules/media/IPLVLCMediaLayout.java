package com.easefun.polyv.livecloudclass.modules.media;

import android.util.Pair;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.easefun.polyv.businesssdk.api.common.ppt.IPolyvPPTView;
import com.easefun.polyv.livecloudclass.modules.chatroom.chatlandscape.PLVLCChatLandscapeLayout;
import com.easefun.polyv.livecloudclass.modules.media.controller.IPLVLCLiveLandscapePlayerController;
import com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager;
import com.easefun.polyv.livecommon.module.modules.player.PLVPlayerState;
import com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.data.PLVPlayInfoVO;
import com.easefun.polyv.livecommon.module.utils.listener.IPLVOnDataChangedListener;
import com.easefun.polyv.livecommon.ui.widget.PLVPlayerLogoView;
import com.easefun.polyv.livecommon.ui.widget.PLVSwitchViewAnchorLayout;
import com.easefun.polyv.livecommon.ui.widget.PLVTriangleIndicateTextView;
import com.plv.livescenes.document.model.PLVPPTStatus;
import com.plv.livescenes.video.api.IPLVLiveListenerEvent;

/* loaded from: classes3.dex */
public interface IPLVLCMediaLayout {

    public interface OnViewActionListener {
        boolean isRtcPausing();

        void onClickShowOrHideSubTab(boolean z2);

        void onPPTTurnPage(String str);

        void onRtcPauseResume(boolean z2);

        Pair<Boolean, Integer> onSendChatMessageAction(String str);

        void onSendLikesAction();

        void onShowBulletinAction();

        void onShowMediaController(boolean z2);

        void onShowRewardAction();

        void onWatchLowLatency(boolean z2);
    }

    void acceptNetworkQuality(int i2);

    void addOnLinkMicStateListener(IPLVOnDataChangedListener<Pair<Boolean, Boolean>> iPLVOnDataChangedListener);

    void addOnPPTShowStateListener(IPLVOnDataChangedListener<Boolean> iPLVOnDataChangedListener);

    void addOnPlayInfoVOListener(IPLVOnDataChangedListener<PLVPlayInfoVO> iPLVOnDataChangedListener);

    void addOnPlayerStateListener(IPLVOnDataChangedListener<PLVPlayerState> iPLVOnDataChangedListener);

    void addOnSeekCompleteListener(IPLVOnDataChangedListener<Integer> iPLVOnDataChangedListener);

    void addOnSeiDataListener(IPLVOnDataChangedListener<Long> iPLVOnDataChangedListener);

    void destroy();

    TextView getCardEnterCdView();

    PLVTriangleIndicateTextView getCardEnterTipsView();

    ImageView getCardEnterView();

    PLVLCChatLandscapeLayout getChatLandscapeLayout();

    int getDuration();

    IPLVLCLiveLandscapePlayerController getLandscapeControllerView();

    PLVPlayerLogoView getLogoView();

    PLVSwitchViewAnchorLayout getPlayerSwitchView();

    String getSessionId();

    float getSpeed();

    int getVideoCurrentPosition();

    int getVolume();

    boolean hideController();

    void init(IPLVLiveRoomDataManager iPLVLiveRoomDataManager);

    boolean isPlaying();

    void notifyRTCPrepared();

    boolean onBackPressed();

    void onTurnPageLayoutChange(boolean z2);

    void pause();

    void resume();

    void seekTo(int i2, int i3);

    void sendDanmaku(CharSequence charSequence);

    void setChatPlaybackEnabled(boolean z2);

    void setHideLandscapeRTCLayout();

    void setLandscapeControllerView(@NonNull IPLVLCLiveLandscapePlayerController iPLVLCLiveLandscapePlayerController);

    void setLandscapeRewardEffectVisibility(boolean z2);

    void setOnRTCPlayEventListener(IPLVLiveListenerEvent.OnRTCPlayEventListener onRTCPlayEventListener);

    void setOnViewActionListener(OnViewActionListener onViewActionListener);

    void setPPTView(IPolyvPPTView iPolyvPPTView);

    void setShowLandscapeRTCLayout();

    void setSpeed(float f2);

    void setVolume(int i2);

    void showController();

    void startPlay();

    void stop();

    void updateOnClickCloseFloatingView();

    void updatePPTStatusChange(PLVPPTStatus pLVPPTStatus);

    void updatePlayBackVideVid(String str);

    void updatePlayBackVideVidAndPlay(String str);

    void updateViewerCount(long j2);

    void updateWhenJoinLinkMic();

    void updateWhenJoinRTC(int i2);

    void updateWhenLeaveLinkMic();

    void updateWhenLeaveRTC();

    void updateWhenLinkMicOpenStatusChanged(boolean z2);

    void updateWhenRequestJoinLinkMic(boolean z2);
}
