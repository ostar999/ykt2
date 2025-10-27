package com.meizu.cloud.pushsdk.a.a;

import android.content.Context;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

/* loaded from: classes4.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    private static final String f8930a = "b";

    /* renamed from: b, reason: collision with root package name */
    private static final Object f8931b = new Object();

    /* renamed from: c, reason: collision with root package name */
    private static b f8932c;

    /* renamed from: d, reason: collision with root package name */
    private Context f8933d;

    private b(Context context) {
        this.f8933d = context;
        try {
            System.setProperty("sun.net.http.allowRestrictedHeaders", k.a.f27523u);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        a.a(context);
    }

    public static b a(Context context) {
        if (f8932c == null) {
            synchronized (f8931b) {
                if (f8932c == null) {
                    f8932c = new b(context);
                }
            }
        }
        return f8932c;
    }

    private Map<String, String> a(Map<String, String> map) {
        if (map == null) {
            map = new HashMap<>();
        }
        byte[] bArrC = a.a().c();
        if (bArrC == null || bArrC.length <= 0) {
            byte[] bArrB = a.a().b();
            if (bArrB != null && bArrB.length > 0) {
                String str = new String(a.a().b());
                DebugLogger.d(f8930a, "attach x_a_key: " + str);
                map.put("X-A-Key", str);
            }
        } else {
            String str2 = new String(bArrC);
            DebugLogger.d(f8930a, "attach x_s_key: " + str2);
            map.put("X-S-Key", str2);
        }
        return map;
    }

    private void a(HttpURLConnection httpURLConnection, byte[] bArr) throws Throwable {
        GZIPOutputStream gZIPOutputStream;
        GZIPOutputStream gZIPOutputStream2 = null;
        try {
            gZIPOutputStream = new GZIPOutputStream(httpURLConnection.getOutputStream());
        } catch (Throwable th) {
            th = th;
        }
        try {
            gZIPOutputStream.write(bArr);
            gZIPOutputStream.flush();
            try {
                gZIPOutputStream.close();
            } catch (Exception unused) {
            }
        } catch (Throwable th2) {
            th = th2;
            gZIPOutputStream2 = gZIPOutputStream;
            if (gZIPOutputStream2 != null) {
                try {
                    gZIPOutputStream2.close();
                } catch (Exception unused2) {
                }
            }
            throw th;
        }
    }

    private void a(URLConnection uRLConnection) {
        try {
            String headerField = uRLConnection.getHeaderField("X-S-Key");
            DebugLogger.d(f8930a, "get x_s_key = " + headerField);
            if (TextUtils.isEmpty(headerField)) {
                return;
            }
            a.a().a(headerField);
        } catch (NullPointerException unused) {
        }
    }

    private byte[] a(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while (true) {
            try {
                int i2 = inputStream.read();
                if (i2 == -1) {
                    break;
                }
                byteArrayOutputStream.write(i2);
            } finally {
                try {
                    byteArrayOutputStream.close();
                } catch (IOException unused) {
                }
            }
        }
        return byteArrayOutputStream.toByteArray();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00fc A[EXC_TOP_SPLITTER, PHI: r0 r1
      0x00fc: PHI (r0v4 com.meizu.cloud.pushsdk.a.a.c) = (r0v10 com.meizu.cloud.pushsdk.a.a.c), (r0v6 com.meizu.cloud.pushsdk.a.a.c) binds: [B:41:0x0121, B:33:0x00fa] A[DONT_GENERATE, DONT_INLINE]
      0x00fc: PHI (r1v5 java.io.InputStream) = (r1v4 java.io.InputStream), (r1v8 java.io.InputStream) binds: [B:41:0x0121, B:33:0x00fa] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0128 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r0v2, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r0v3 */
    /* JADX WARN: Type inference failed for: r0v8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private com.meizu.cloud.pushsdk.a.a.c b(java.lang.String r7, java.util.Map<java.lang.String, java.lang.String> r8, java.lang.String r9) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 308
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meizu.cloud.pushsdk.a.a.b.b(java.lang.String, java.util.Map, java.lang.String):com.meizu.cloud.pushsdk.a.a.c");
    }

    private void b(URLConnection uRLConnection) {
        try {
            String headerField = uRLConnection.getHeaderField("Key-Timeout");
            DebugLogger.d(f8930a, "get keyTimeout = " + headerField);
        } catch (NullPointerException unused) {
        }
    }

    public c a(String str, Map<String, String> map, String str2) {
        try {
            return b(str, a(map), str2);
        } catch (IOException e2) {
            e2.printStackTrace();
            return null;
        }
    }
}
