package io.agora.rtc.mediaio;

/* loaded from: classes8.dex */
public interface IVideoSource {
    int getBufferType();

    void onDispose();

    boolean onInitialize(IVideoFrameConsumer consumer);

    boolean onStart();

    void onStop();
}
