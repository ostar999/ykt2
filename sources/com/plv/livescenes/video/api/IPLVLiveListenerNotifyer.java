package com.plv.livescenes.video.api;

import com.plv.livescenes.model.PLVLiveCountdownVO;

/* loaded from: classes5.dex */
public interface IPLVLiveListenerNotifyer {
    void notifyLinesChanged(int i2);

    void notifyLiveCountdownCallback(PLVLiveCountdownVO pLVLiveCountdownVO);

    void notifyLiveEnd();

    void notifyLiveStop();

    void notifyMicroPhoneShow(int i2);

    void notifyNoLivePresenter();

    void notifyOnLowLatencyNetworkQuality(int i2);

    void notifyOnWillPlayWaitting(boolean z2);

    void notifyOnlyAudio(boolean z2);

    void notifyRTCLiveEnd();

    void notifyRTCLiveStart();

    void notifySessionIdChanged(String str);

    void notifyShowCamera(boolean z2);

    void notifySupportRTC(boolean z2);
}
