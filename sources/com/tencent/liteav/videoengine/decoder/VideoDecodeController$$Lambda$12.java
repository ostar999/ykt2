package com.tencent.liteav.videoengine.decoder;

import java.nio.ByteBuffer;

/* loaded from: classes6.dex */
final /* synthetic */ class VideoDecodeController$$Lambda$12 implements Runnable {
    private final VideoDecodeController arg$1;
    private final ByteBuffer arg$2;

    private VideoDecodeController$$Lambda$12(VideoDecodeController videoDecodeController, ByteBuffer byteBuffer) {
        this.arg$1 = videoDecodeController;
        this.arg$2 = byteBuffer;
    }

    public static Runnable lambdaFactory$(VideoDecodeController videoDecodeController, ByteBuffer byteBuffer) {
        return new VideoDecodeController$$Lambda$12(videoDecodeController, byteBuffer);
    }

    @Override // java.lang.Runnable
    public void run() {
        VideoDecodeController.lambda$onDecodeSEI$11(this.arg$1, this.arg$2);
    }
}
