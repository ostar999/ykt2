package com.tencent.liteav.videoengine.decoder;

import com.tencent.liteav.videoengine.decoder.VideoDecodeController;

/* loaded from: classes6.dex */
final /* synthetic */ class VideoDecodeController$$Lambda$3 implements Runnable {
    private final VideoDecodeController arg$1;
    private final VideoDecodeController.VideoDecodeControllerListener arg$2;

    private VideoDecodeController$$Lambda$3(VideoDecodeController videoDecodeController, VideoDecodeController.VideoDecodeControllerListener videoDecodeControllerListener) {
        this.arg$1 = videoDecodeController;
        this.arg$2 = videoDecodeControllerListener;
    }

    public static Runnable lambdaFactory$(VideoDecodeController videoDecodeController, VideoDecodeController.VideoDecodeControllerListener videoDecodeControllerListener) {
        return new VideoDecodeController$$Lambda$3(videoDecodeController, videoDecodeControllerListener);
    }

    @Override // java.lang.Runnable
    public void run() {
        VideoDecodeController.lambda$start$2(this.arg$1, this.arg$2);
    }
}
