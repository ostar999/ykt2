package com.tencent.liteav.videoengine.decoder;

/* loaded from: classes6.dex */
final /* synthetic */ class VideoDecodeController$$Lambda$13 implements Runnable {
    private final VideoDecodeController arg$1;

    private VideoDecodeController$$Lambda$13(VideoDecodeController videoDecodeController) {
        this.arg$1 = videoDecodeController;
    }

    public static Runnable lambdaFactory$(VideoDecodeController videoDecodeController) {
        return new VideoDecodeController$$Lambda$13(videoDecodeController);
    }

    @Override // java.lang.Runnable
    public void run() {
        VideoDecodeController.lambda$onDecodeFailed$12(this.arg$1);
    }
}
