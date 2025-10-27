package io.agora.live;

/* loaded from: classes8.dex */
public abstract class LivePublisherHandler {
    public void onPublishStreamUrlFailed(String url, int errorCode) {
    }

    public void onPublisherTranscodingUpdated(LivePublisher publisher) {
    }

    public void onStreamInjectedStatus(String url, int uid, int status) {
    }

    public void onStreamUrlPublished(String url) {
    }

    public void onStreamUrlUnpublished(String url) {
    }
}
