package io.agora.live;

/* loaded from: classes8.dex */
public abstract class LiveSubscriberHandler {
    public void onFirstRemoteVideoDecoded(int uid, int width, int height, int elapsed) {
    }

    public void onStreamTypeChanged(int streamType, int uid) {
    }

    public void onVideoSizeChanged(int uid, int width, int height, int rotation) {
    }

    public void publishedByHost(int uid, int streamType) {
    }

    public void unpublishedByHost(int uid) {
    }
}
