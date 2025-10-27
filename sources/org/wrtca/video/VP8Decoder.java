package org.wrtca.video;

import org.wrtca.jni.JNINamespace;

@JNINamespace("webrtc::jni")
/* loaded from: classes9.dex */
class VP8Decoder extends WrappedNativeVideoDecoder {
    public static native long nativeCreateDecoder();

    @Override // org.wrtca.video.WrappedNativeVideoDecoder
    public long createNativeDecoder() {
        return nativeCreateDecoder();
    }
}
