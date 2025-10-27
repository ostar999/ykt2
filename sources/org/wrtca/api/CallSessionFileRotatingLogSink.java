package org.wrtca.api;

import org.wrtca.jni.JNINamespace;
import org.wrtca.log.Logging;

@JNINamespace("webrtc::jni")
/* loaded from: classes9.dex */
public class CallSessionFileRotatingLogSink {
    private long nativeSink;

    public CallSessionFileRotatingLogSink(String str, int i2, Logging.Severity severity) {
        this.nativeSink = nativeAddSink(str, i2, severity.ordinal());
    }

    public static byte[] getLogData(String str) {
        return nativeGetLogData(str);
    }

    private static native long nativeAddSink(String str, int i2, int i3);

    private static native void nativeDeleteSink(long j2);

    private static native byte[] nativeGetLogData(String str);

    public void dispose() {
        long j2 = this.nativeSink;
        if (j2 != 0) {
            nativeDeleteSink(j2);
            this.nativeSink = 0L;
        }
    }
}
