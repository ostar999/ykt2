package org.wrtca.api;

import org.wrtca.jni.JNINamespace;
import org.wrtca.video.WrappedNativeVideoDecoder;

@JNINamespace("webrtc::jni")
/* loaded from: classes9.dex */
public abstract class VideoDecoderFallback extends WrappedNativeVideoDecoder {
    private final VideoDecoder fallback;
    private final VideoDecoder primary;

    public VideoDecoderFallback(VideoDecoder videoDecoder, VideoDecoder videoDecoder2) {
        this.fallback = videoDecoder;
        this.primary = videoDecoder2;
    }

    private static native long nativeCreateDecoder(VideoDecoder videoDecoder, VideoDecoder videoDecoder2);

    public long createNativeDecoder() {
        return nativeCreateDecoder(this.fallback, this.primary);
    }
}
