package org.wrtca.video;

import org.wrtca.jni.JNINamespace;

@JNINamespace("webrtc::jni")
/* loaded from: classes9.dex */
public class Histogram {
    private final long handle;

    private Histogram(long j2) {
        this.handle = j2;
    }

    public static Histogram createCounts(String str, int i2, int i3, int i4) {
        return new Histogram(nativeCreateCounts(str, i2, i3, i4));
    }

    public static Histogram createEnumeration(String str, int i2) {
        return new Histogram(nativeCreateEnumeration(str, i2));
    }

    private static native void nativeAddSample(long j2, int i2);

    private static native long nativeCreateCounts(String str, int i2, int i3, int i4);

    private static native long nativeCreateEnumeration(String str, int i2);

    public void addSample(int i2) {
        nativeAddSample(this.handle, i2);
    }
}
