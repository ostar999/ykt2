package com.tencent.liteav.videoengine.decoder;

import com.tencent.liteav.videobase.frame.PixelFrame;

/* loaded from: classes6.dex */
final /* synthetic */ class VideoDecodeController$$Lambda$11 implements Runnable {
    private final VideoDecodeController arg$1;
    private final PixelFrame arg$2;
    private final long arg$3;

    private VideoDecodeController$$Lambda$11(VideoDecodeController videoDecodeController, PixelFrame pixelFrame, long j2) {
        this.arg$1 = videoDecodeController;
        this.arg$2 = pixelFrame;
        this.arg$3 = j2;
    }

    public static Runnable lambdaFactory$(VideoDecodeController videoDecodeController, PixelFrame pixelFrame, long j2) {
        return new VideoDecodeController$$Lambda$11(videoDecodeController, pixelFrame, j2);
    }

    @Override // java.lang.Runnable
    public void run() {
        VideoDecodeController.lambda$onDecodeFrame$10(this.arg$1, this.arg$2, this.arg$3);
    }
}
