package com.plv.livescenes.video.api;

import com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder;
import com.plv.livescenes.video.api.IPLVLiveListenerEvent;

/* loaded from: classes5.dex */
public interface IPLVLiveVideoViewListenerBinder extends IPLVVideoViewListenerBinder {
    void setMicroPhoneListener(IPLVLiveListenerEvent.MicroPhoneListener microPhoneListener);

    void setOnCameraShowListener(IPLVLiveListenerEvent.OnCameraShowListener onCameraShowListener);

    void setOnLinesChangedListener(IPLVLiveListenerEvent.OnLinesChangedListener onLinesChangedListener);

    void setOnLowLatencyNetworkQualityListener(IPLVLiveListenerEvent.OnLowLatencyNetworkQualityListener onLowLatencyNetworkQualityListener);

    void setOnNoLiveAtPresentListener(IPLVLiveListenerEvent.OnNoLiveAtPresentListener onNoLiveAtPresentListener);

    void setOnOnlyAudioListener(IPLVLiveListenerEvent.OnOnlyAudioListener onOnlyAudioListener);

    void setOnRTCPlayEventListener(IPLVLiveListenerEvent.OnRTCPlayEventListener onRTCPlayEventListener);

    void setOnSessionIdChangedListener(IPLVLiveListenerEvent.OnSessionIdChangedListener onSessionIdChangedListener);

    void setOnSupportRTCListener(IPLVLiveListenerEvent.OnSupportRTCListener onSupportRTCListener);

    void setOnWillPlayWaittingListener(IPLVLiveListenerEvent.OnWillPlayWaittingListener onWillPlayWaittingListener);
}
