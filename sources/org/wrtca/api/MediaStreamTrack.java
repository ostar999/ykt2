package org.wrtca.api;

import org.wrtca.jni.CalledByNative;
import org.wrtca.jni.JNINamespace;
import org.wrtca.jni.JniCommon;

@JNINamespace("webrtc::jni")
/* loaded from: classes9.dex */
public class MediaStreamTrack {
    public final long nativeTrack;

    public enum MediaType {
        MEDIA_TYPE_AUDIO(0),
        MEDIA_TYPE_VIDEO(1);

        private final int nativeIndex;

        MediaType(int i2) {
            this.nativeIndex = i2;
        }

        @CalledByNative("MediaType")
        public static MediaType fromNativeIndex(int i2) {
            for (MediaType mediaType : values()) {
                if (mediaType.getNative() == i2) {
                    return mediaType;
                }
            }
            throw new IllegalArgumentException("Unknown native media type: " + i2);
        }

        @CalledByNative("MediaType")
        public int getNative() {
            return this.nativeIndex;
        }
    }

    public enum State {
        LIVE,
        ENDED;

        @CalledByNative("State")
        public static State fromNativeIndex(int i2) {
            return values()[i2];
        }
    }

    public MediaStreamTrack(long j2) {
        this.nativeTrack = j2;
    }

    private static native boolean nativeGetEnabled(long j2);

    private static native String nativeGetId(long j2);

    private static native String nativeGetKind(long j2);

    private static native State nativeGetState(long j2);

    private static native boolean nativeSetEnabled(long j2, boolean z2);

    public void dispose() {
        JniCommon.nativeReleaseRef(this.nativeTrack);
    }

    public boolean enabled() {
        return nativeGetEnabled(this.nativeTrack);
    }

    public String id() {
        return nativeGetId(this.nativeTrack);
    }

    public String kind() {
        return nativeGetKind(this.nativeTrack);
    }

    public boolean setEnabled(boolean z2) {
        return nativeSetEnabled(this.nativeTrack, z2);
    }

    public State state() {
        return nativeGetState(this.nativeTrack);
    }
}
