package org.wrtca.api;

import org.wrtca.jni.CalledByNative;

/* loaded from: classes9.dex */
public class IceCandidate {
    public final String sdp;
    public final int sdpMLineIndex;
    public final String sdpMid;
    public final String serverUrl;

    public IceCandidate(String str, int i2, String str2) {
        this.sdpMid = str;
        this.sdpMLineIndex = i2;
        this.sdp = str2;
        this.serverUrl = "";
    }

    @CalledByNative
    public String getSdp() {
        return this.sdp;
    }

    @CalledByNative
    public String getSdpMid() {
        return this.sdpMid;
    }

    public String toString() {
        return this.sdpMid + ":" + this.sdpMLineIndex + ":" + this.sdp + ":" + this.serverUrl;
    }

    @CalledByNative
    public IceCandidate(String str, int i2, String str2, String str3) {
        this.sdpMid = str;
        this.sdpMLineIndex = i2;
        this.sdp = str2;
        this.serverUrl = str3;
    }
}
