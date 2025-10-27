package com.tencent.liteav.videoengine.decoder;

/* loaded from: classes6.dex */
final /* synthetic */ class VideoDecodeController$$Lambda$10 implements Runnable {
    private final VideoDecodeController arg$1;

    private VideoDecodeController$$Lambda$10(VideoDecodeController videoDecodeController) {
        this.arg$1 = videoDecodeController;
    }

    public static Runnable lambdaFactory$(VideoDecodeController videoDecodeController) {
        return new VideoDecodeController$$Lambda$10(videoDecodeController);
    }

    @Override // java.lang.Runnable
    public void run() {
        VideoDecodeController.lambda$uninitialize$9(this.arg$1);
    }
}
