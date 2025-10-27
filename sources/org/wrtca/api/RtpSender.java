package org.wrtca.api;

import org.wrtca.jni.CalledByNative;
import org.wrtca.jni.JNINamespace;
import org.wrtca.jni.JniCommon;

@JNINamespace("webrtc::jni")
/* loaded from: classes9.dex */
public class RtpSender {
    private MediaStreamTrack cachedTrack;
    private final DtmfSender dtmfSender;
    public final long nativeRtpSender;
    private boolean ownsTrack = true;

    @CalledByNative
    public RtpSender(long j2) {
        this.nativeRtpSender = j2;
        long jNativeGetTrack = nativeGetTrack(j2);
        this.cachedTrack = jNativeGetTrack != 0 ? new MediaStreamTrack(jNativeGetTrack) : null;
        long jNativeGetDtmfSender = nativeGetDtmfSender(j2);
        this.dtmfSender = jNativeGetDtmfSender != 0 ? new DtmfSender(jNativeGetDtmfSender) : null;
    }

    private static native long nativeGetDtmfSender(long j2);

    private static native String nativeGetId(long j2);

    private static native RtpParameters nativeGetParameters(long j2);

    private static native long nativeGetTrack(long j2);

    private static native boolean nativeSetParameters(long j2, RtpParameters rtpParameters);

    private static native boolean nativeSetTrack(long j2, long j3);

    public void dispose() {
        DtmfSender dtmfSender = this.dtmfSender;
        if (dtmfSender != null) {
            dtmfSender.dispose();
        }
        MediaStreamTrack mediaStreamTrack = this.cachedTrack;
        if (mediaStreamTrack != null && this.ownsTrack) {
            mediaStreamTrack.dispose();
        }
        JniCommon.nativeReleaseRef(this.nativeRtpSender);
    }

    public DtmfSender dtmf() {
        return this.dtmfSender;
    }

    public RtpParameters getParameters() {
        return nativeGetParameters(this.nativeRtpSender);
    }

    public String id() {
        return nativeGetId(this.nativeRtpSender);
    }

    public boolean setParameters(RtpParameters rtpParameters) {
        return nativeSetParameters(this.nativeRtpSender, rtpParameters);
    }

    public boolean setTrack(MediaStreamTrack mediaStreamTrack, boolean z2) {
        if (!nativeSetTrack(this.nativeRtpSender, mediaStreamTrack == null ? 0L : mediaStreamTrack.nativeTrack)) {
            return false;
        }
        MediaStreamTrack mediaStreamTrack2 = this.cachedTrack;
        if (mediaStreamTrack2 != null && this.ownsTrack) {
            mediaStreamTrack2.dispose();
        }
        this.cachedTrack = mediaStreamTrack;
        this.ownsTrack = z2;
        return true;
    }

    public MediaStreamTrack track() {
        return this.cachedTrack;
    }
}
