package com.plv.livescenes.video.api;

import androidx.annotation.MainThread;
import com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent;
import com.plv.livescenes.model.PLVLiveCountdownVO;

/* loaded from: classes5.dex */
public interface IPLVLiveListenerEvent extends IPLVVideoViewListenerEvent {

    public interface MicroPhoneListener {
        void showMicPhoneLine(int i2);
    }

    public interface OnAdvertisementOutListener {
    }

    public interface OnCameraShowListener {
        void cameraOpen(boolean z2);
    }

    public interface OnLinesChangedListener {
        void onLinesChanged(int i2);
    }

    public interface OnLowLatencyNetworkQualityListener {
        void onNetworkQuality(int i2);
    }

    public static abstract class OnNoLiveAtPresentListener {
        public abstract void onLiveEnd();

        public void onLiveStop() {
        }

        public abstract void onNoLiveAtPresent();
    }

    public static abstract class OnNoLiveAtPresentListenerExt extends OnNoLiveAtPresentListener {
        public abstract void onLiveCountdownCallback(PLVLiveCountdownVO pLVLiveCountdownVO);
    }

    public interface OnNoLivePlaybackListener {
        void onNoLivePlayback(String str, String str2, String str3, boolean z2);
    }

    public interface OnOnlyAudioListener {
        void onOnlyAudio(boolean z2);
    }

    public interface OnRTCPlayEventListener {
        void onRTCLiveEnd();

        void onRTCLiveStart();
    }

    public interface OnSessionIdChangedListener {
        void onSessionChanged(String str);
    }

    public interface OnSupportRTCListener {
        void onSupportRTC(boolean z2);
    }

    public interface OnWillPlayWaittingListener {
        @MainThread
        void onWillPlayWaitting(boolean z2);
    }
}
