package com.easefun.polyv.livecloudclass.modules.linkmic;

import com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager;
import com.easefun.polyv.livecommon.module.utils.PLVViewSwitcher;
import com.easefun.polyv.livecommon.ui.widget.PLVPlayerLogoView;
import com.easefun.polyv.livecommon.ui.widget.PLVSwitchViewAnchorLayout;

/* loaded from: classes3.dex */
public interface IPLVLCLinkMicLayout {

    public interface OnPLVLinkMicLayoutListener {
        void onCancelRequestJoinLinkMic();

        void onChangeTeacherLocation(PLVViewSwitcher pLVViewSwitcher, PLVSwitchViewAnchorLayout pLVSwitchViewAnchorLayout);

        void onChannelLinkMicOpenStatusChanged(boolean z2);

        void onClickSwitchWithMediaOnce(PLVSwitchViewAnchorLayout pLVSwitchViewAnchorLayout);

        void onClickSwitchWithMediaTwice(PLVSwitchViewAnchorLayout pLVSwitchViewAnchorLayout, PLVSwitchViewAnchorLayout pLVSwitchViewAnchorLayout2);

        void onJoinLinkMic();

        void onJoinRtcChannel();

        void onLeaveLinkMic();

        void onLeaveRtcChannel();

        void onNetworkQuality(int i2);

        void onRTCPrepared();

        void onRequestJoinLinkMic();

        void onShowLandscapeRTCLayout(boolean z2);
    }

    void destroy();

    int getLandscapeWidth();

    void hideAll();

    void hideControlBar();

    void hideLinkMicList();

    void init(IPLVLiveRoomDataManager iPLVLiveRoomDataManager, IPLVLCLinkMicControlBar iPLVLCLinkMicControlBar);

    boolean isJoinChannel();

    boolean isMediaShowInLinkMicList();

    boolean isPausing();

    void notifySwitchedPptToMainScreenOnJoinChannel();

    void pause();

    void resume();

    void setIsAudio(boolean z2);

    void setIsTeacherOpenLinkMic(boolean z2);

    void setLiveEnd();

    void setLiveStart();

    void setLogoView(PLVPlayerLogoView pLVPlayerLogoView);

    void setOnPLVLinkMicLayoutListener(OnPLVLinkMicLayoutListener onPLVLinkMicLayoutListener);

    void setWatchLowLatency(boolean z2);

    void showAll();

    void showControlBar();

    void showLinkMicList();
}
