package io.agora.live;

/* loaded from: classes8.dex */
public abstract class LiveEngineHandler {
    public void onConnectionInterrupted() {
    }

    public void onConnectionLost() {
    }

    public void onError(int errorCode) {
    }

    public void onJoinChannel(String channel, int uid, int elapsed) {
    }

    public void onLeaveChannel() {
    }

    public void onNetworkQuality(int uid, int txQuality, int rxQuality) {
    }

    public void onRejoinChannel(String channel, int uid, int elapsed) {
    }

    public void onReportLiveStats(LiveStats stats) {
    }

    public void onRequestToken() {
    }

    public void onTokenPrivilegeWillExpire(String token) {
    }

    public void onWarning(int warningCode) {
    }
}
