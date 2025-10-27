package com.tencent.liteav.videoengine.decoder;

import org.json.JSONArray;

/* loaded from: classes6.dex */
final /* synthetic */ class VideoDecodeController$$Lambda$8 implements Runnable {
    private final VideoDecodeController arg$1;
    private final JSONArray arg$2;

    private VideoDecodeController$$Lambda$8(VideoDecodeController videoDecodeController, JSONArray jSONArray) {
        this.arg$1 = videoDecodeController;
        this.arg$2 = jSONArray;
    }

    public static Runnable lambdaFactory$(VideoDecodeController videoDecodeController, JSONArray jSONArray) {
        return new VideoDecodeController$$Lambda$8(videoDecodeController, jSONArray);
    }

    @Override // java.lang.Runnable
    public void run() {
        VideoDecodeController.lambda$setMediaCodecDeviceRelatedParams$7(this.arg$1, this.arg$2);
    }
}
