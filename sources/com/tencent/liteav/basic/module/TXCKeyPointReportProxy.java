package com.tencent.liteav.basic.module;

import android.content.Context;
import com.tencent.liteav.basic.log.TXCLog;
import java.io.File;

/* loaded from: classes6.dex */
public class TXCKeyPointReportProxy {

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        public int f18473a;

        /* renamed from: b, reason: collision with root package name */
        public int f18474b;

        /* renamed from: c, reason: collision with root package name */
        public int f18475c;

        /* renamed from: d, reason: collision with root package name */
        public int f18476d;

        /* renamed from: e, reason: collision with root package name */
        public String f18477e;

        /* renamed from: f, reason: collision with root package name */
        public String f18478f;

        /* renamed from: g, reason: collision with root package name */
        public String f18479g;

        /* renamed from: h, reason: collision with root package name */
        public String f18480h;
    }

    public static void a(Context context) {
        File externalFilesDir;
        if (context == null || (externalFilesDir = context.getApplicationContext().getExternalFilesDir(null)) == null) {
            return;
        }
        String str = externalFilesDir.getAbsolutePath() + "/liteav/ssoreport.txt";
        File file = new File(str);
        if (!file.exists()) {
            try {
                if (!file.mkdirs()) {
                    TXCLog.e("TXCKeyPointReportProxy", "can not create sso file path");
                    return;
                }
            } catch (Exception e2) {
                TXCLog.e("TXCKeyPointReportProxy", "create sso file exception:" + e2.toString());
            }
        }
        nativeInit(str);
    }

    public static void b(int i2, int i3) {
        nativeTagKeyPointEnd(i2, i3);
    }

    public static void c(int i2, int i3) {
        nativeSetBasicInfo(i2, i3);
    }

    private static native void nativeInit(String str);

    private static native void nativeSendCacheReport();

    private static native void nativeSetBasicInfo(int i2, int i3);

    private static native void nativeSetCpu(int i2, int i3);

    private static native void nativeSetDeviceInfo(int i2, int i3, int i4, int i5, String str, String str2, String str3, String str4);

    private static native void nativeSetErrorCode(int i2);

    private static native void nativeSetLocalQuality(int i2, int i3, int i4);

    private static native void nativeTagKeyPointAudio(String str, int i2);

    private static native void nativeTagKeyPointEnd(int i2, int i3);

    private static native void nativeTagKeyPointStart(int i2, long j2);

    private static native void nativeTagKeyPointVideo(String str, int i2);

    private static native void nativesetRemoteQuality(String str, int i2, long j2, int i3);

    public static void b(String str, int i2) {
        nativeTagKeyPointAudio(str, i2);
    }

    public static void b(int i2) {
        nativeSetErrorCode(i2);
    }

    public static void a() {
        nativeSendCacheReport();
    }

    public static void a(a aVar) {
        nativeSetDeviceInfo(aVar.f18473a, aVar.f18474b, aVar.f18475c, aVar.f18476d, aVar.f18477e, aVar.f18478f, aVar.f18479g, aVar.f18480h);
    }

    public static void a(int i2, int i3) {
        nativeSetCpu(i2, i3);
    }

    public static void a(int i2) {
        nativeTagKeyPointStart(i2, -1L);
    }

    public static void a(int i2, long j2) {
        nativeTagKeyPointStart(i2, j2);
    }

    public static void a(String str, int i2) {
        nativeTagKeyPointVideo(str, i2);
    }

    public static void a(String str, int i2, long j2, int i3) {
        nativesetRemoteQuality(str, i2, j2, i3);
    }

    public static void a(int i2, int i3, int i4) {
        nativeSetLocalQuality(i2, i3, i4);
    }
}
