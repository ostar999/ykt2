package com.easefun.polyv.livecloudclass.modules.ppt;

import com.easefun.polyv.businesssdk.api.common.ppt.IPolyvPPTView;
import com.plv.livescenes.document.model.PLVPPTStatus;

/* loaded from: classes3.dex */
public interface IPLVLCPPTView {

    public interface OnPLVLCLivePPTViewListener {
        void onLiveBackTopActivity();

        void onLiveChangeToLandscape(boolean z2);

        void onLivePPTStatusChange(PLVPPTStatus pLVPPTStatus);

        void onLiveRestartVideoView();

        void onLiveStartOrPauseVideoView(boolean z2);

        void onLiveSwitchPPTViewLocation(boolean z2);
    }

    public interface OnPLVLCPlaybackPPTViewListener {
        void onPlaybackSwitchPPTViewLocation(boolean z2);
    }

    void destroy();

    IPolyvPPTView getPlaybackPPTViewToBindInPlayer();

    void initLivePPT(OnPLVLCLivePPTViewListener onPLVLCLivePPTViewListener);

    void initPlaybackPPT(OnPLVLCPlaybackPPTViewListener onPLVLCPlaybackPPTViewListener);

    void notifyJoinRtcChannel();

    void notifyLeaveRtcChannel();

    void reLoad();

    void sendSEIData(long j2);

    void sendWebMessage(String str, String str2);

    void setIsLowLatencyWatch(boolean z2);

    void setPlaybackCurrentPosition(int i2);

    void turnPagePPT(String str);
}
