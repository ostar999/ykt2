package com.tencent.liteav.videoengine.decoder;

import com.tencent.liteav.basic.util.j;

/* loaded from: classes6.dex */
final /* synthetic */ class VideoDecodeController$$Lambda$15 implements j.a {
    private final VideoDecodeController arg$1;

    private VideoDecodeController$$Lambda$15(VideoDecodeController videoDecodeController) {
        this.arg$1 = videoDecodeController;
    }

    public static j.a lambdaFactory$(VideoDecodeController videoDecodeController) {
        return new VideoDecodeController$$Lambda$15(videoDecodeController);
    }

    @Override // com.tencent.liteav.basic.util.j.a
    public void onTimeout() {
        this.arg$1.onDrainDecodedFrameTimeOut();
    }
}
