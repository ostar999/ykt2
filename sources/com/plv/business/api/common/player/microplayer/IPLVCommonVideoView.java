package com.plv.business.api.common.player.microplayer;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import com.plv.business.api.auxiliary.PLVAuxiliaryVideoview;
import com.plv.business.api.common.ppt.IPLVPPTView;
import com.plv.business.model.video.PLVBaseVideoParams;

/* loaded from: classes4.dex */
public interface IPLVCommonVideoView<R> {
    void bindPPTView(IPLVPPTView iPLVPPTView);

    boolean canPlaySkipHeadAd();

    boolean changeBitRate(int i2);

    boolean changeLines(int i2);

    R getModleVO();

    int getStayTimeDuration();

    PLVAuxiliaryVideoview getSubVideoView();

    String getViewerId();

    int getWatchTimeDuration();

    boolean isOnlyAudio();

    boolean isPauseState();

    @MainThread
    void playByMode(PLVBaseVideoParams pLVBaseVideoParams, int i2);

    @MainThread
    void playFromHeadAd();

    @MainThread
    boolean playSkipHeadAd();

    @MainThread
    boolean playSkipHeadAd(boolean z2);

    @MainThread
    boolean playTailAd();

    @MainThread
    boolean playTeaser();

    void setIsLinkMic(boolean z2);

    void setSubVideoView(@NonNull PLVAuxiliaryVideoview pLVAuxiliaryVideoview);

    void setViewerId(String str);

    void startFromNew();
}
