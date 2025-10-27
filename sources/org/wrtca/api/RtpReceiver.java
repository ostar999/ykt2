package org.wrtca.api;

import org.wrtca.api.MediaStreamTrack;
import org.wrtca.jni.CalledByNative;
import org.wrtca.jni.JNINamespace;
import org.wrtca.jni.JniCommon;

@JNINamespace("webrtc::jni")
/* loaded from: classes9.dex */
public class RtpReceiver {
    private MediaStreamTrack cachedTrack;
    private long nativeObserver;
    public final long nativeRtpReceiver;

    public interface Observer {
        @CalledByNative("Observer")
        void onFirstPacketReceived(MediaStreamTrack.MediaType mediaType);
    }

    @CalledByNative
    public RtpReceiver(long j2) {
        this.nativeRtpReceiver = j2;
        this.cachedTrack = new MediaStreamTrack(nativeGetTrack(j2));
    }

    private static native String nativeGetId(long j2);

    private static native RtpParameters nativeGetParameters(long j2);

    private static native long nativeGetTrack(long j2);

    private static native long nativeSetObserver(long j2, Observer observer);

    private static native boolean nativeSetParameters(long j2, RtpParameters rtpParameters);

    private static native void nativeUnsetObserver(long j2, long j3);

    public void SetObserver(Observer observer) {
        long j2 = this.nativeObserver;
        if (j2 != 0) {
            nativeUnsetObserver(this.nativeRtpReceiver, j2);
        }
        this.nativeObserver = nativeSetObserver(this.nativeRtpReceiver, observer);
    }

    @CalledByNative
    public void dispose() {
        this.cachedTrack.dispose();
        long j2 = this.nativeObserver;
        if (j2 != 0) {
            nativeUnsetObserver(this.nativeRtpReceiver, j2);
            this.nativeObserver = 0L;
        }
        JniCommon.nativeReleaseRef(this.nativeRtpReceiver);
    }

    public RtpParameters getParameters() {
        return nativeGetParameters(this.nativeRtpReceiver);
    }

    public String id() {
        return nativeGetId(this.nativeRtpReceiver);
    }

    public boolean setParameters(RtpParameters rtpParameters) {
        if (rtpParameters == null) {
            return false;
        }
        return nativeSetParameters(this.nativeRtpReceiver, rtpParameters);
    }

    public MediaStreamTrack track() {
        return this.cachedTrack;
    }
}
