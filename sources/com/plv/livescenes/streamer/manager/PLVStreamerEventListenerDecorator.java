package com.plv.livescenes.streamer.manager;

import com.plv.linkmic.PLVLinkMicEventHandler;
import com.plv.linkmic.model.PLVNetworkStatusVO;
import com.plv.livescenes.streamer.listener.PLVStreamerEventListener;

/* loaded from: classes5.dex */
public class PLVStreamerEventListenerDecorator extends PLVStreamerEventListener {
    private PLVStreamerEventListener target;

    public PLVStreamerEventListenerDecorator(PLVStreamerEventListener pLVStreamerEventListener) {
        this.target = pLVStreamerEventListener;
    }

    @Override // com.plv.linkmic.PLVLinkMicEventHandler
    public void onAudioQuality(String str, int i2, short s2, short s3) {
        this.target.onAudioQuality(str, i2, s2, s3);
    }

    @Override // com.plv.linkmic.PLVLinkMicEventHandler
    public void onFirstRemoteVideoDecoded(String str, int i2, int i3) {
        this.target.onFirstRemoteVideoDecoded(str, i2, i3);
    }

    @Override // com.plv.linkmic.PLVLinkMicEventHandler
    public void onJoinChannelSuccess(String str) {
        this.target.onJoinChannelSuccess(str);
    }

    @Override // com.plv.linkmic.PLVLinkMicEventHandler
    public void onLeaveChannel() {
        this.target.onLeaveChannel();
    }

    @Override // com.plv.linkmic.PLVLinkMicEventHandler
    public void onLocalAudioVolumeIndication(PLVLinkMicEventHandler.PLVAudioVolumeInfo pLVAudioVolumeInfo) {
        this.target.onLocalAudioVolumeIndication(pLVAudioVolumeInfo);
    }

    @Override // com.plv.linkmic.PLVLinkMicEventHandler
    public void onNetworkQuality(int i2) {
        this.target.onNetworkQuality(i2);
    }

    @Override // com.plv.linkmic.PLVLinkMicEventHandler
    public void onRejoinChannelSuccess(String str, String str2) {
        this.target.onRejoinChannelSuccess(str, str2);
    }

    @Override // com.plv.linkmic.PLVLinkMicEventHandler
    public void onRemoteAudioVolumeIndication(PLVLinkMicEventHandler.PLVAudioVolumeInfo[] pLVAudioVolumeInfoArr) {
        this.target.onRemoteAudioVolumeIndication(pLVAudioVolumeInfoArr);
    }

    @Override // com.plv.linkmic.PLVLinkMicEventHandler
    public void onRemoteNetworkStatus(PLVNetworkStatusVO pLVNetworkStatusVO) {
        this.target.onRemoteNetworkStatus(pLVNetworkStatusVO);
    }

    @Override // com.plv.linkmic.PLVLinkMicEventHandler
    public void onRemoteStreamClose(String str, int i2) {
        this.target.onRemoteStreamClose(str, i2);
    }

    @Override // com.plv.linkmic.PLVLinkMicEventHandler
    public void onRemoteStreamOpen(String str, int i2) {
        this.target.onRemoteStreamOpen(str, i2);
    }

    @Override // com.plv.linkmic.PLVLinkMicEventHandler
    public void onScreenShare(boolean z2, int i2) {
        this.target.onScreenShare(z2, i2);
    }

    @Override // com.plv.linkmic.PLVLinkMicEventHandler
    public void onStreamPublished(String str, int i2) {
        this.target.onStreamPublished(str, i2);
    }

    @Override // com.plv.linkmic.PLVLinkMicEventHandler
    public void onStreamUnpublished(String str) {
        this.target.onStreamUnpublished(str);
    }

    @Override // com.plv.linkmic.PLVLinkMicEventHandler
    public void onTokenExpired() {
        this.target.onTokenExpired();
    }

    @Override // com.plv.linkmic.PLVLinkMicEventHandler
    public void onUpstreamNetworkStatus(PLVNetworkStatusVO pLVNetworkStatusVO) {
        this.target.onUpstreamNetworkStatus(pLVNetworkStatusVO);
    }

    @Override // com.plv.linkmic.PLVLinkMicEventHandler
    public void onUserJoined(String str) {
        this.target.onUserJoined(str);
    }

    @Override // com.plv.linkmic.PLVLinkMicEventHandler
    public void onUserMuteAudio(String str, boolean z2) {
        this.target.onUserMuteAudio(str, z2);
    }

    @Override // com.plv.linkmic.PLVLinkMicEventHandler
    public void onUserMuteVideo(String str, boolean z2) {
        this.target.onUserMuteVideo(str, z2);
    }

    @Override // com.plv.linkmic.PLVLinkMicEventHandler
    public void onUserOffline(String str) {
        this.target.onUserOffline(str);
    }

    @Override // com.plv.linkmic.PLVLinkMicEventHandler
    public void onUserMuteAudio(String str, boolean z2, int i2) {
        this.target.onUserMuteAudio(str, z2, i2);
    }

    @Override // com.plv.linkmic.PLVLinkMicEventHandler
    public void onUserMuteVideo(String str, boolean z2, int i2) {
        this.target.onUserMuteVideo(str, z2, i2);
    }
}
