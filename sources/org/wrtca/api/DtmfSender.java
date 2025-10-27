package org.wrtca.api;

import org.wrtca.jni.JNINamespace;
import org.wrtca.jni.JniCommon;

@JNINamespace("webrtc::jni")
/* loaded from: classes9.dex */
public class DtmfSender {
    public final long nativeDtmfSender;

    public DtmfSender(long j2) {
        this.nativeDtmfSender = j2;
    }

    private static native boolean nativeCanInsertDtmf(long j2);

    private static native int nativeDuration(long j2);

    private static native boolean nativeInsertDtmf(long j2, String str, int i2, int i3);

    private static native int nativeInterToneGap(long j2);

    private static native String nativeTones(long j2);

    public boolean canInsertDtmf() {
        return nativeCanInsertDtmf(this.nativeDtmfSender);
    }

    public void dispose() {
        JniCommon.nativeReleaseRef(this.nativeDtmfSender);
    }

    public int duration() {
        return nativeDuration(this.nativeDtmfSender);
    }

    public boolean insertDtmf(String str, int i2, int i3) {
        return nativeInsertDtmf(this.nativeDtmfSender, str, i2, i3);
    }

    public int interToneGap() {
        return nativeInterToneGap(this.nativeDtmfSender);
    }

    public String tones() {
        return nativeTones(this.nativeDtmfSender);
    }
}
