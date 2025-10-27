package io.agora.rtc.mediaio;

/* loaded from: classes8.dex */
public class AgoraDefaultSource implements IVideoSource {
    @Override // io.agora.rtc.mediaio.IVideoSource
    public int getBufferType() {
        return 0;
    }

    @Override // io.agora.rtc.mediaio.IVideoSource
    public void onDispose() {
    }

    @Override // io.agora.rtc.mediaio.IVideoSource
    public boolean onInitialize(IVideoFrameConsumer consumer) {
        return true;
    }

    @Override // io.agora.rtc.mediaio.IVideoSource
    public boolean onStart() {
        return true;
    }

    @Override // io.agora.rtc.mediaio.IVideoSource
    public void onStop() {
    }
}
