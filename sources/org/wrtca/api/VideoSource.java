package org.wrtca.api;

import org.wrtca.jni.JNINamespace;

@JNINamespace("webrtc::jni")
/* loaded from: classes9.dex */
public class VideoSource extends MediaSource {
    public VideoSource(long j2) {
        super(j2);
    }

    private static native void nativeAdaptOutputFormat(long j2, int i2, int i3, int i4);

    private static native void nativeSetCropSize(long j2, int i2, int i3);

    public void adaptOutputFormat(int i2, int i3, int i4) {
        nativeAdaptOutputFormat(this.nativeSource, i2, i3, i4);
    }

    public void setCropSize(int i2, int i3) {
        nativeSetCropSize(this.nativeSource, i2, i3);
    }
}
