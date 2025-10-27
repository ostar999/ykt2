package com.plv.linkmic;

import com.plv.linkmic.model.PLVNetworkStatusVO;

/* loaded from: classes4.dex */
public abstract class PLVLinkMicEventHandler {

    public static class PLVAudioVolumeInfo {
        private String uid;
        private int volume;

        public PLVAudioVolumeInfo(String str, int i2) {
            this.uid = str;
            this.volume = i2;
        }

        public String getUid() {
            return this.uid;
        }

        public int getVolume() {
            return this.volume;
        }
    }

    public static class PLVLinkMicStates {
        private double cpuAppUsage;
        private double cpuTotalUsage;
        private int lastmileDelay;
        private int rxAudioKBitRate;
        private int rxBytes;
        private int rxKBitRate;
        private int rxVideoKBitRate;
        private int totalDuration;
        private int txAudioKBitRate;
        private int txBytes;
        private int txKBitRate;
        private int txVideoKBitRate;
        private int users;
    }

    public void onAudioQuality(String str, int i2, short s2, short s3) {
    }

    public void onFirstRemoteVideoDecoded(String str, int i2, int i3) {
    }

    public void onJoinChannelSuccess(String str) {
    }

    public void onLeaveChannel() {
    }

    public void onLocalAudioVolumeIndication(PLVAudioVolumeInfo pLVAudioVolumeInfo) {
    }

    public void onNetworkQuality(int i2) {
    }

    public void onRejoinChannelSuccess(String str, String str2) {
    }

    public void onRemoteAudioVolumeIndication(PLVAudioVolumeInfo[] pLVAudioVolumeInfoArr) {
    }

    public void onRemoteNetworkStatus(PLVNetworkStatusVO pLVNetworkStatusVO) {
    }

    public void onRemoteStreamClose(String str, int i2) {
    }

    public void onRemoteStreamOpen(String str, int i2) {
    }

    public void onScreenShare(boolean z2, int i2) {
    }

    public void onStreamPublished(String str, int i2) {
    }

    public void onStreamUnpublished(String str) {
    }

    public void onTokenExpired() {
    }

    public void onUpstreamNetworkStatus(PLVNetworkStatusVO pLVNetworkStatusVO) {
    }

    public void onUserJoined(String str) {
    }

    public void onUserMuteAudio(String str, boolean z2) {
    }

    public void onUserMuteAudio(String str, boolean z2, int i2) {
    }

    public void onUserMuteVideo(String str, boolean z2) {
    }

    public void onUserMuteVideo(String str, boolean z2, int i2) {
    }

    public void onUserOffline(String str) {
    }
}
