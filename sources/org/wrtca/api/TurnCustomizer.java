package org.wrtca.api;

import org.wrtca.jni.CalledByNative;
import org.wrtca.jni.JNINamespace;

@JNINamespace("webrtc::jni")
/* loaded from: classes9.dex */
public class TurnCustomizer {
    public final long nativeTurnCustomizer;

    public TurnCustomizer(long j2) {
        this.nativeTurnCustomizer = j2;
    }

    private static native void nativeFreeTurnCustomizer(long j2);

    public void dispose() {
        nativeFreeTurnCustomizer(this.nativeTurnCustomizer);
    }

    @CalledByNative
    public long getNativeTurnCustomizer() {
        return this.nativeTurnCustomizer;
    }
}
