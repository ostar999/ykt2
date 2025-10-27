package org.wrtca.video;

import org.wrtca.jni.JNINamespace;

@JNINamespace("webrtc::jni")
/* loaded from: classes9.dex */
class VP9Decoder extends WrappedNativeVideoDecoder {
    public static native long nativeCreateDecoder();

    public static native boolean nativeIsSupported();

    @Override // org.wrtca.video.WrappedNativeVideoDecoder
    public long createNativeDecoder() {
        return nativeCreateDecoder();
    }
}
