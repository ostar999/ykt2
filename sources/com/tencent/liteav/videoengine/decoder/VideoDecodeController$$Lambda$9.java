package com.tencent.liteav.videoengine.decoder;

/* loaded from: classes6.dex */
final /* synthetic */ class VideoDecodeController$$Lambda$9 implements Runnable {
    private final VideoDecodeController arg$1;
    private final int arg$2;
    private final int arg$3;

    private VideoDecodeController$$Lambda$9(VideoDecodeController videoDecodeController, int i2, int i3) {
        this.arg$1 = videoDecodeController;
        this.arg$2 = i2;
        this.arg$3 = i3;
    }

    public static Runnable lambdaFactory$(VideoDecodeController videoDecodeController, int i2, int i3) {
        return new VideoDecodeController$$Lambda$9(videoDecodeController, i2, i3);
    }

    @Override // java.lang.Runnable
    public void run() {
        VideoDecodeController.lambda$setHWDecoderMaxCache$8(this.arg$1, this.arg$2, this.arg$3);
    }
}
