package io.agora.rtc.mediaio;

import java.nio.ByteBuffer;

/* loaded from: classes8.dex */
public class AgoraDefaultRender implements IVideoSink {
    @Override // io.agora.rtc.mediaio.IVideoFrameConsumer
    public void consumeByteArrayFrame(byte[] data, int pixelFormat, int width, int height, int rotation, long ts) {
    }

    @Override // io.agora.rtc.mediaio.IVideoFrameConsumer
    public void consumeByteBufferFrame(ByteBuffer buffer, int pixelFormat, int width, int height, int rotation, long ts) {
    }

    @Override // io.agora.rtc.mediaio.IVideoFrameConsumer
    public void consumeTextureFrame(int texId, int pixelFormat, int width, int height, int rotation, long ts, float[] matrix) {
    }

    @Override // io.agora.rtc.mediaio.IVideoSink
    public int getBufferType() {
        return 0;
    }

    @Override // io.agora.rtc.mediaio.IVideoSink
    public long getEGLContextHandle() {
        return 0L;
    }

    @Override // io.agora.rtc.mediaio.IVideoSink
    public int getPixelFormat() {
        return 0;
    }

    @Override // io.agora.rtc.mediaio.IVideoSink
    public void onDispose() {
    }

    @Override // io.agora.rtc.mediaio.IVideoSink
    public boolean onInitialize() {
        return true;
    }

    @Override // io.agora.rtc.mediaio.IVideoSink
    public boolean onStart() {
        return true;
    }

    @Override // io.agora.rtc.mediaio.IVideoSink
    public void onStop() {
    }
}
