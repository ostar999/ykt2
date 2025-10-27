package io.agora.rtc;

import io.agora.rtc.IRtcEngineEventHandler;

/* loaded from: classes8.dex */
public abstract class IRtcChannelEventHandler {
    public void onActiveSpeaker(RtcChannel rtcChannel, int uid) {
    }

    public void onChannelError(RtcChannel rtcChannel, int err) {
    }

    public void onChannelMediaRelayEvent(RtcChannel rtcChannel, int code) {
    }

    public void onChannelMediaRelayStateChanged(RtcChannel rtcChannel, int state, int code) {
    }

    public void onChannelWarning(RtcChannel rtcChannel, int warn) {
    }

    public void onClientRoleChanged(RtcChannel rtcChannel, int oldRole, int newRole) {
    }

    public void onConnectionLost(RtcChannel rtcChannel) {
    }

    public void onConnectionStateChanged(RtcChannel rtcChannel, int state, int reason) {
    }

    public void onFirstRemoteAudioDecoded(RtcChannel rtcChannel, int uid, int elapsed) {
    }

    public void onFirstRemoteAudioFrame(RtcChannel rtcChannel, int uid, int elapsed) {
    }

    public void onFirstRemoteVideoFrame(RtcChannel rtcChannel, int uid, int width, int height, int elapsed) {
    }

    public void onJoinChannelSuccess(RtcChannel rtcChannel, int uid, int elapsed) {
    }

    public void onLeaveChannel(RtcChannel rtcChannel, IRtcEngineEventHandler.RtcStats stats) {
    }

    public void onNetworkQuality(RtcChannel rtcChannel, int uid, int txQuality, int rxQuality) {
    }

    public void onPublishAudioStateChanged(RtcChannel rtcChannel, int oldState, int newState, int elapseSinceLastState) {
    }

    public void onPublishVideoStateChanged(RtcChannel rtcChannel, int oldState, int newState, int elapseSinceLastState) {
    }

    public void onRejoinChannelSuccess(RtcChannel rtcChannel, int uid, int elapsed) {
    }

    public void onRemoteAudioStateChanged(RtcChannel rtcChannel, int uid, int state, int reason, int elapsed) {
    }

    public void onRemoteAudioStats(RtcChannel rtcChannel, IRtcEngineEventHandler.RemoteAudioStats stats) {
    }

    public void onRemoteSubscribeFallbackToAudioOnly(RtcChannel rtcChannel, int uid, boolean isFallbackOrRecover) {
    }

    public void onRemoteVideoStateChanged(RtcChannel rtcChannel, int uid, int state, int reason, int elapsed) {
    }

    public void onRemoteVideoStats(RtcChannel rtcChannel, IRtcEngineEventHandler.RemoteVideoStats stats) {
    }

    public void onRequestToken(RtcChannel rtcChannel) {
    }

    public void onRtcStats(RtcChannel rtcChannel, IRtcEngineEventHandler.RtcStats stats) {
    }

    public void onRtmpStreamingStateChanged(RtcChannel rtcChannel, String url, int state, int errCode) {
    }

    public void onStreamInjectedStatus(RtcChannel rtcChannel, String url, int uid, int status) {
    }

    public void onStreamMessage(RtcChannel rtcChannel, int uid, int streamId, byte[] data) {
    }

    public void onStreamMessageError(RtcChannel rtcChannel, int uid, int streamId, int error, int missed, int cached) {
    }

    public void onStreamPublished(RtcChannel rtcChannel, String url, int error) {
    }

    public void onStreamUnpublished(RtcChannel rtcChannel, String url) {
    }

    public void onSubscribeAudioStateChanged(RtcChannel rtcChannel, int uid, int oldState, int newState, int elapseSinceLastState) {
    }

    public void onSubscribeVideoStateChanged(RtcChannel rtcChannel, int uid, int oldState, int newState, int elapseSinceLastState) {
    }

    public void onTokenPrivilegeWillExpire(RtcChannel rtcChannel, String token) {
    }

    public void onTranscodingUpdated(RtcChannel rtcChannel) {
    }

    public void onUserJoined(RtcChannel rtcChannel, int uid, int elapsed) {
    }

    public void onUserMuteAudio(RtcChannel rtcChannel, int uid, boolean muted) {
    }

    public void onUserOffline(RtcChannel rtcChannel, int uid, int reason) {
    }

    public void onVideoSizeChanged(RtcChannel rtcChannel, int uid, int width, int height, int rotation) {
    }
}
