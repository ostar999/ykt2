package com.plv.livescenes.video;

import com.plv.business.api.common.player.PLVVideoViewListener;
import com.plv.livescenes.model.PLVLiveCountdownVO;
import com.plv.livescenes.video.api.IPLVLiveListenerEvent;
import com.plv.livescenes.video.api.IPLVLiveListenerNotifyer;
import com.plv.livescenes.video.api.IPLVLiveVideoViewListenerBinder;

/* loaded from: classes5.dex */
public class PLVLiveListener extends PLVVideoViewListener implements IPLVLiveListenerNotifyer, IPLVLiveVideoViewListenerBinder {
    private IPLVLiveListenerEvent.MicroPhoneListener microPhoneListener;
    private IPLVLiveListenerEvent.OnNoLiveAtPresentListener noLiveAtPresentListener;
    protected IPLVLiveListenerEvent.OnCameraShowListener onCameraShowListener;
    private IPLVLiveListenerEvent.OnLinesChangedListener onLinesChangedListener;
    private IPLVLiveListenerEvent.OnLowLatencyNetworkQualityListener onLowLatencyNetworkQualityListener = null;
    private IPLVLiveListenerEvent.OnOnlyAudioListener onOnlyAudioListener;
    private IPLVLiveListenerEvent.OnRTCPlayEventListener onRTCPlayEventListener;
    private IPLVLiveListenerEvent.OnSessionIdChangedListener onSessionIdChangedListener;
    private IPLVLiveListenerEvent.OnSupportRTCListener onSupportRTCListener;
    private IPLVLiveListenerEvent.OnWillPlayWaittingListener onWillPlayWaittingListener;

    @Override // com.plv.business.api.common.player.PLVVideoViewListener
    public void clearAllListener() {
        super.clearAllListener();
        this.onWillPlayWaittingListener = null;
        this.onGetMarqueeVoListener = null;
        this.onCameraShowListener = null;
        this.noLiveAtPresentListener = null;
        this.onSupportRTCListener = null;
        this.onRTCPlayEventListener = null;
        this.onOnlyAudioListener = null;
        this.onSessionIdChangedListener = null;
        this.onLowLatencyNetworkQualityListener = null;
    }

    @Override // com.plv.livescenes.video.api.IPLVLiveListenerNotifyer
    public void notifyLinesChanged(int i2) {
        IPLVLiveListenerEvent.OnLinesChangedListener onLinesChangedListener = this.onLinesChangedListener;
        if (onLinesChangedListener != null) {
            onLinesChangedListener.onLinesChanged(i2);
        }
    }

    @Override // com.plv.livescenes.video.api.IPLVLiveListenerNotifyer
    public void notifyLiveCountdownCallback(PLVLiveCountdownVO pLVLiveCountdownVO) {
        IPLVLiveListenerEvent.OnNoLiveAtPresentListener onNoLiveAtPresentListener = this.noLiveAtPresentListener;
        if (onNoLiveAtPresentListener instanceof IPLVLiveListenerEvent.OnNoLiveAtPresentListenerExt) {
            ((IPLVLiveListenerEvent.OnNoLiveAtPresentListenerExt) onNoLiveAtPresentListener).onLiveCountdownCallback(pLVLiveCountdownVO);
        }
    }

    @Override // com.plv.livescenes.video.api.IPLVLiveListenerNotifyer
    public void notifyLiveEnd() {
        IPLVLiveListenerEvent.OnNoLiveAtPresentListener onNoLiveAtPresentListener = this.noLiveAtPresentListener;
        if (onNoLiveAtPresentListener != null) {
            onNoLiveAtPresentListener.onLiveEnd();
        }
    }

    @Override // com.plv.livescenes.video.api.IPLVLiveListenerNotifyer
    public void notifyLiveStop() {
        IPLVLiveListenerEvent.OnNoLiveAtPresentListener onNoLiveAtPresentListener = this.noLiveAtPresentListener;
        if (onNoLiveAtPresentListener != null) {
            onNoLiveAtPresentListener.onLiveStop();
        }
    }

    @Override // com.plv.livescenes.video.api.IPLVLiveListenerNotifyer
    public void notifyMicroPhoneShow(int i2) {
        IPLVLiveListenerEvent.MicroPhoneListener microPhoneListener = this.microPhoneListener;
        if (microPhoneListener != null) {
            microPhoneListener.showMicPhoneLine(i2);
        }
    }

    @Override // com.plv.livescenes.video.api.IPLVLiveListenerNotifyer
    public void notifyNoLivePresenter() {
        IPLVLiveListenerEvent.OnNoLiveAtPresentListener onNoLiveAtPresentListener = this.noLiveAtPresentListener;
        if (onNoLiveAtPresentListener != null) {
            onNoLiveAtPresentListener.onNoLiveAtPresent();
        }
    }

    @Override // com.plv.livescenes.video.api.IPLVLiveListenerNotifyer
    public void notifyOnLowLatencyNetworkQuality(int i2) {
        IPLVLiveListenerEvent.OnLowLatencyNetworkQualityListener onLowLatencyNetworkQualityListener = this.onLowLatencyNetworkQualityListener;
        if (onLowLatencyNetworkQualityListener != null) {
            onLowLatencyNetworkQualityListener.onNetworkQuality(i2);
        }
    }

    @Override // com.plv.livescenes.video.api.IPLVLiveListenerNotifyer
    public void notifyOnWillPlayWaitting(boolean z2) {
        IPLVLiveListenerEvent.OnWillPlayWaittingListener onWillPlayWaittingListener = this.onWillPlayWaittingListener;
        if (onWillPlayWaittingListener != null) {
            onWillPlayWaittingListener.onWillPlayWaitting(z2);
        }
    }

    @Override // com.plv.livescenes.video.api.IPLVLiveListenerNotifyer
    public void notifyOnlyAudio(boolean z2) {
        IPLVLiveListenerEvent.OnOnlyAudioListener onOnlyAudioListener = this.onOnlyAudioListener;
        if (onOnlyAudioListener != null) {
            onOnlyAudioListener.onOnlyAudio(z2);
        }
    }

    @Override // com.plv.livescenes.video.api.IPLVLiveListenerNotifyer
    public void notifyRTCLiveEnd() {
        IPLVLiveListenerEvent.OnRTCPlayEventListener onRTCPlayEventListener = this.onRTCPlayEventListener;
        if (onRTCPlayEventListener != null) {
            onRTCPlayEventListener.onRTCLiveEnd();
        }
    }

    @Override // com.plv.livescenes.video.api.IPLVLiveListenerNotifyer
    public void notifyRTCLiveStart() {
        IPLVLiveListenerEvent.OnRTCPlayEventListener onRTCPlayEventListener = this.onRTCPlayEventListener;
        if (onRTCPlayEventListener != null) {
            onRTCPlayEventListener.onRTCLiveStart();
        }
    }

    @Override // com.plv.livescenes.video.api.IPLVLiveListenerNotifyer
    public void notifySessionIdChanged(String str) {
        IPLVLiveListenerEvent.OnSessionIdChangedListener onSessionIdChangedListener = this.onSessionIdChangedListener;
        if (onSessionIdChangedListener != null) {
            onSessionIdChangedListener.onSessionChanged(str);
        }
    }

    @Override // com.plv.livescenes.video.api.IPLVLiveListenerNotifyer
    public void notifyShowCamera(boolean z2) {
        IPLVLiveListenerEvent.OnCameraShowListener onCameraShowListener = this.onCameraShowListener;
        if (onCameraShowListener != null) {
            onCameraShowListener.cameraOpen(z2);
        }
    }

    @Override // com.plv.livescenes.video.api.IPLVLiveListenerNotifyer
    public void notifySupportRTC(boolean z2) {
        IPLVLiveListenerEvent.OnSupportRTCListener onSupportRTCListener = this.onSupportRTCListener;
        if (onSupportRTCListener != null) {
            onSupportRTCListener.onSupportRTC(z2);
        }
    }

    @Override // com.plv.livescenes.video.api.IPLVLiveVideoViewListenerBinder
    public void setMicroPhoneListener(IPLVLiveListenerEvent.MicroPhoneListener microPhoneListener) {
        this.microPhoneListener = microPhoneListener;
    }

    @Override // com.plv.livescenes.video.api.IPLVLiveVideoViewListenerBinder
    public void setOnCameraShowListener(IPLVLiveListenerEvent.OnCameraShowListener onCameraShowListener) {
        this.onCameraShowListener = onCameraShowListener;
    }

    @Override // com.plv.livescenes.video.api.IPLVLiveVideoViewListenerBinder
    public void setOnLinesChangedListener(IPLVLiveListenerEvent.OnLinesChangedListener onLinesChangedListener) {
        this.onLinesChangedListener = onLinesChangedListener;
    }

    @Override // com.plv.livescenes.video.api.IPLVLiveVideoViewListenerBinder
    public void setOnLowLatencyNetworkQualityListener(IPLVLiveListenerEvent.OnLowLatencyNetworkQualityListener onLowLatencyNetworkQualityListener) {
        this.onLowLatencyNetworkQualityListener = onLowLatencyNetworkQualityListener;
    }

    @Override // com.plv.livescenes.video.api.IPLVLiveVideoViewListenerBinder
    public void setOnNoLiveAtPresentListener(IPLVLiveListenerEvent.OnNoLiveAtPresentListener onNoLiveAtPresentListener) {
        this.noLiveAtPresentListener = onNoLiveAtPresentListener;
    }

    @Override // com.plv.livescenes.video.api.IPLVLiveVideoViewListenerBinder
    public void setOnOnlyAudioListener(IPLVLiveListenerEvent.OnOnlyAudioListener onOnlyAudioListener) {
        this.onOnlyAudioListener = onOnlyAudioListener;
    }

    @Override // com.plv.livescenes.video.api.IPLVLiveVideoViewListenerBinder
    public void setOnRTCPlayEventListener(IPLVLiveListenerEvent.OnRTCPlayEventListener onRTCPlayEventListener) {
        this.onRTCPlayEventListener = onRTCPlayEventListener;
    }

    @Override // com.plv.livescenes.video.api.IPLVLiveVideoViewListenerBinder
    public void setOnSessionIdChangedListener(IPLVLiveListenerEvent.OnSessionIdChangedListener onSessionIdChangedListener) {
        this.onSessionIdChangedListener = onSessionIdChangedListener;
    }

    @Override // com.plv.livescenes.video.api.IPLVLiveVideoViewListenerBinder
    public void setOnSupportRTCListener(IPLVLiveListenerEvent.OnSupportRTCListener onSupportRTCListener) {
        this.onSupportRTCListener = onSupportRTCListener;
    }

    @Override // com.plv.livescenes.video.api.IPLVLiveVideoViewListenerBinder
    public void setOnWillPlayWaittingListener(IPLVLiveListenerEvent.OnWillPlayWaittingListener onWillPlayWaittingListener) {
        this.onWillPlayWaittingListener = onWillPlayWaittingListener;
    }
}
