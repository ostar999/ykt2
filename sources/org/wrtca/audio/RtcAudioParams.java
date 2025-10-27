package org.wrtca.audio;

import c.h;
import org.wrtca.jni.JNINamespace;

@JNINamespace("webrtc::jni")
/* loaded from: classes9.dex */
public class RtcAudioParams {
    private static final String TAG = "RTCAudioParams";

    public static void adjustRecordVolume(int i2) {
        float f2 = i2 / 100.0f;
        h.a(TAG, "volume scale: " + f2);
        nativeAdjustRecordVolume(f2);
    }

    public static native void nativeAdjustRecordVolume(float f2);
}
