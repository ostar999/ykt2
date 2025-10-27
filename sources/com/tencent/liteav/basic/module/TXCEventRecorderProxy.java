package com.tencent.liteav.basic.module;

/* loaded from: classes6.dex */
public class TXCEventRecorderProxy {

    /* renamed from: a, reason: collision with root package name */
    private long f18472a;

    public static void a(String str, int i2, long j2, long j3, String str2, int i3) {
        if (str == null || str2 == null) {
            return;
        }
        nativeAddEventMsg(str, i2, j2, j3, str2, i3);
    }

    private static native void nativeAddEventMsg(String str, int i2, long j2, long j3, String str2, int i3);

    private static native void nativeRelease(long j2);

    public void finalize() throws Throwable {
        nativeRelease(this.f18472a);
        this.f18472a = 0L;
        super.finalize();
    }
}
