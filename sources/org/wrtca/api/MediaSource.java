package org.wrtca.api;

import org.wrtca.jni.CalledByNative;
import org.wrtca.jni.JNINamespace;
import org.wrtca.jni.JniCommon;

@JNINamespace("webrtc::jni")
/* loaded from: classes9.dex */
public class MediaSource {
    public final long nativeSource;

    public enum State {
        INITIALIZING,
        LIVE,
        ENDED,
        MUTED;

        @CalledByNative("State")
        public static State fromNativeIndex(int i2) {
            return values()[i2];
        }
    }

    public MediaSource(long j2) {
        this.nativeSource = j2;
    }

    private static native State nativeGetState(long j2);

    public void dispose() {
        JniCommon.nativeReleaseRef(this.nativeSource);
    }

    public State state() {
        return nativeGetState(this.nativeSource);
    }
}
