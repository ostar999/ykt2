package com.tencent.liteav.videoengine.decoder;

/* loaded from: classes6.dex */
final /* synthetic */ class VideoDecodeController$$Lambda$4 implements Runnable {
    private final VideoDecodeController arg$1;
    private final com.tencent.liteav.videobase.e.b arg$2;

    private VideoDecodeController$$Lambda$4(VideoDecodeController videoDecodeController, com.tencent.liteav.videobase.e.b bVar) {
        this.arg$1 = videoDecodeController;
        this.arg$2 = bVar;
    }

    public static Runnable lambdaFactory$(VideoDecodeController videoDecodeController, com.tencent.liteav.videobase.e.b bVar) {
        return new VideoDecodeController$$Lambda$4(videoDecodeController, bVar);
    }

    @Override // java.lang.Runnable
    public void run() {
        this.arg$1.decodeInternal(this.arg$2);
    }
}
