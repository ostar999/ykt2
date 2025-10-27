package io.agora.rtc.internal;

/* loaded from: classes8.dex */
interface AudioRoutingListener {
    void onAudioRoutingChanged(int routing);

    void onAudioRoutingDestroyed();

    void onAudioRoutingError(int errCode);
}
