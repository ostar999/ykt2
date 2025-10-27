package com.tencent.liteav.videoengine.decoder;

import com.tencent.liteav.videobase.frame.PixelFrame;
import java.nio.ByteBuffer;

/* loaded from: classes6.dex */
public interface o {
    void onDecodeFailed(com.tencent.liteav.videobase.f.b bVar);

    void onDecodeFrame(PixelFrame pixelFrame, long j2);

    void onDecodeSEI(ByteBuffer byteBuffer);

    void onRequestKeyFrame();
}
