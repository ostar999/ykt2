package org.wrtca.video;

import org.wrtca.jni.JNINamespace;

@JNINamespace("webrtc::jni")
/* loaded from: classes9.dex */
class VP9Encoder extends WrappedNativeVideoEncoder {
    public static native long nativeCreateEncoder();

    public static native boolean nativeIsSupported();

    @Override // org.wrtca.video.WrappedNativeVideoEncoder
    public long createNativeEncoder() {
        return nativeCreateEncoder();
    }

    @Override // org.wrtca.video.WrappedNativeVideoEncoder
    public boolean isSoftwareEncoder() {
        return true;
    }
}
