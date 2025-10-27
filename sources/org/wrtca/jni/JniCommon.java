package org.wrtca.jni;

import java.nio.ByteBuffer;

@JNINamespace("webrtc::jni")
/* loaded from: classes9.dex */
public class JniCommon {
    public static native void nativeAddRef(long j2);

    public static native ByteBuffer nativeAllocateByteBuffer(int i2);

    public static native void nativeFreeByteBuffer(ByteBuffer byteBuffer);

    public static native void nativeReleaseRef(long j2);
}
