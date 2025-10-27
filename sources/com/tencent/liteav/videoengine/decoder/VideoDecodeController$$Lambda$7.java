package com.tencent.liteav.videoengine.decoder;

/* loaded from: classes6.dex */
final /* synthetic */ class VideoDecodeController$$Lambda$7 implements Runnable {
    private final VideoDecodeController arg$1;

    private VideoDecodeController$$Lambda$7(VideoDecodeController videoDecodeController) {
        this.arg$1 = videoDecodeController;
    }

    public static Runnable lambdaFactory$(VideoDecodeController videoDecodeController) {
        return new VideoDecodeController$$Lambda$7(videoDecodeController);
    }

    @Override // java.lang.Runnable
    public void run() {
        VideoDecodeController.lambda$restartDecoder$6(this.arg$1);
    }
}
