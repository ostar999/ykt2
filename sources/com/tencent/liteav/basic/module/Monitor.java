package com.tencent.liteav.basic.module;

import com.tencent.liteav.basic.util.h;

/* loaded from: classes6.dex */
public class Monitor {
    static {
        h.d();
    }

    public static void a(String str, int i2, String str2) {
        nativeInit(str, i2, str2);
    }

    private static native void nativeInit(String str, int i2, String str2);

    private static native void nativeOnlineLog(int i2, String str, String str2, int i3);

    private static native void nativeOnlineLogWithLimit(int i2, int i3, String str, String str2, int i4, int i5);

    private static native void nativeOnlineLogWithTag(int i2, String str, String str2, int i3, String str3);

    private static native void nativeUnInit();

    public static void a(int i2, String str, String str2, int i3) {
        nativeOnlineLog(i2, str, str2, i3);
    }

    public static void a(int i2, int i3, String str, String str2, int i4, int i5) {
        nativeOnlineLogWithLimit(i2, i3, str, str2, i4, i5);
    }

    public static void a(int i2, String str, String str2, int i3, String str3) {
        nativeOnlineLogWithTag(i2, str, str2, i3, str3);
    }

    public static void a() {
        nativeUnInit();
    }
}
