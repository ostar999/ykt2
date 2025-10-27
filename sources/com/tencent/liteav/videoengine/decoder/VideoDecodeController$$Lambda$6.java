package com.tencent.liteav.videoengine.decoder;

import com.tencent.liteav.videoengine.decoder.VideoDecodeController;

/* loaded from: classes6.dex */
final /* synthetic */ class VideoDecodeController$$Lambda$6 implements Runnable {
    private final VideoDecodeController arg$1;
    private final VideoDecodeController.DecodeStrategy arg$2;

    private VideoDecodeController$$Lambda$6(VideoDecodeController videoDecodeController, VideoDecodeController.DecodeStrategy decodeStrategy) {
        this.arg$1 = videoDecodeController;
        this.arg$2 = decodeStrategy;
    }

    public static Runnable lambdaFactory$(VideoDecodeController videoDecodeController, VideoDecodeController.DecodeStrategy decodeStrategy) {
        return new VideoDecodeController$$Lambda$6(videoDecodeController, decodeStrategy);
    }

    @Override // java.lang.Runnable
    public void run() {
        VideoDecodeController.lambda$setDecodeStrategy$5(this.arg$1, this.arg$2);
    }
}
