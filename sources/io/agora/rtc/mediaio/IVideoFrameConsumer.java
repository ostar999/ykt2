package io.agora.rtc.mediaio;

import java.nio.ByteBuffer;

/* loaded from: classes8.dex */
public interface IVideoFrameConsumer {
    void consumeByteArrayFrame(byte[] data, int format, int width, int height, int rotation, long timestamp);

    void consumeByteBufferFrame(ByteBuffer buffer, int format, int width, int height, int rotation, long timestamp);

    void consumeTextureFrame(int textureId, int format, int width, int height, int rotation, long timestamp, float[] matrix);
}
