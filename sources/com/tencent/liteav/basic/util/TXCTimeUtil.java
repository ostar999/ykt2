package com.tencent.liteav.basic.util;

/* loaded from: classes6.dex */
public class TXCTimeUtil {
    static {
        h.d();
    }

    public static long generatePtsMS() {
        return nativeGeneratePtsMS();
    }

    public static long getClockTickInHz() {
        return nativeGetClockTickInHz();
    }

    public static long getTimeTick() {
        return nativeGetTimeTick();
    }

    public static long getUtcTimeTick() {
        return nativeGetUtcTimeTick();
    }

    public static void initAppStartTime() {
        nativeInitAppStartTime();
    }

    private static native long nativeGeneratePtsMS();

    private static native long nativeGetClockTickInHz();

    private static native long nativeGetTimeTick();

    private static native long nativeGetUtcTimeTick();

    private static native void nativeInitAppStartTime();
}
