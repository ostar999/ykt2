package com.tencent.bugly.proguard;

import android.content.Context;
import com.umeng.socialize.common.SocializeConstants;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes6.dex */
public final class af {

    /* renamed from: a, reason: collision with root package name */
    static af f17454a;

    /* renamed from: b, reason: collision with root package name */
    protected Context f17455b;

    /* renamed from: c, reason: collision with root package name */
    public Map<String, String> f17456c = null;

    public af(Context context) {
        this.f17455b = context;
    }

    private static byte[] b(HttpURLConnection httpURLConnection) {
        BufferedInputStream bufferedInputStream;
        if (httpURLConnection == null) {
            return null;
        }
        try {
            bufferedInputStream = new BufferedInputStream(httpURLConnection.getInputStream());
        } catch (Throwable th) {
            th = th;
            bufferedInputStream = null;
        }
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr = new byte[1024];
            while (true) {
                int i2 = bufferedInputStream.read(bArr);
                if (i2 <= 0) {
                    break;
                }
                byteArrayOutputStream.write(bArr, 0, i2);
            }
            byteArrayOutputStream.flush();
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            try {
                bufferedInputStream.close();
            } catch (Throwable th2) {
                th2.printStackTrace();
            }
            return byteArray;
        } catch (Throwable th3) {
            th = th3;
            try {
                if (!al.a(th)) {
                    th.printStackTrace();
                }
                return null;
            } finally {
                if (bufferedInputStream != null) {
                    try {
                        bufferedInputStream.close();
                    } catch (Throwable th4) {
                        th4.printStackTrace();
                    }
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:88:0x016c A[PHI: r4 r6 r7 r10 r13 r14 r18
      0x016c: PHI (r4v4 java.lang.Throwable) = (r4v3 java.lang.Throwable), (r4v14 java.lang.Throwable) binds: [B:103:0x018c, B:87:0x016a] A[DONT_GENERATE, DONT_INLINE]
      0x016c: PHI (r6v7 int) = (r6v6 int), (r6v11 int) binds: [B:103:0x018c, B:87:0x016a] A[DONT_GENERATE, DONT_INLINE]
      0x016c: PHI (r7v4 char) = (r7v3 char), (r7v11 char) binds: [B:103:0x018c, B:87:0x016a] A[DONT_GENERATE, DONT_INLINE]
      0x016c: PHI (r10v9 java.lang.String) = (r10v8 java.lang.String), (r10v13 java.lang.String) binds: [B:103:0x018c, B:87:0x016a] A[DONT_GENERATE, DONT_INLINE]
      0x016c: PHI (r13v4 int) = (r13v3 int), (r13v6 int) binds: [B:103:0x018c, B:87:0x016a] A[DONT_GENERATE, DONT_INLINE]
      0x016c: PHI (r14v5 int) = (r14v4 int), (r14v10 int) binds: [B:103:0x018c, B:87:0x016a] A[DONT_GENERATE, DONT_INLINE]
      0x016c: PHI (r18v3 int) = (r18v2 int), (r18v8 int) binds: [B:103:0x018c, B:87:0x016a] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:98:0x017f A[Catch: all -> 0x0172, TRY_LEAVE, TryCatch #2 {all -> 0x0172, blocks: (B:23:0x009e, B:25:0x00b6, B:29:0x00c7, B:28:0x00c5, B:49:0x00f0, B:51:0x00f8, B:64:0x0124, B:66:0x012f, B:79:0x0150, B:82:0x015d, B:96:0x0179, B:98:0x017f), top: B:120:0x009e }] */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final byte[] a(java.lang.String r21, byte[] r22, com.tencent.bugly.proguard.aj r23, java.util.Map<java.lang.String, java.lang.String> r24) {
        /*
            Method dump skipped, instructions count: 441
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.af.a(java.lang.String, byte[], com.tencent.bugly.proguard.aj, java.util.Map):byte[]");
    }

    private static Map<String, String> a(HttpURLConnection httpURLConnection) {
        HashMap map = new HashMap();
        Map<String, List<String>> headerFields = httpURLConnection.getHeaderFields();
        if (headerFields == null || headerFields.size() == 0) {
            return null;
        }
        for (String str : headerFields.keySet()) {
            List<String> list = headerFields.get(str);
            if (list.size() > 0) {
                map.put(str, list.get(0));
            }
        }
        return map;
    }

    private static HttpURLConnection a(String str, byte[] bArr, String str2, Map<String, String> map) {
        if (str == null) {
            al.e("destUrl is null.", new Object[0]);
            return null;
        }
        HttpURLConnection httpURLConnectionA = a(str2, str);
        if (httpURLConnectionA == null) {
            al.e("Failed to get HttpURLConnection object.", new Object[0]);
            return null;
        }
        try {
            httpURLConnectionA.setRequestProperty("wup_version", SocializeConstants.PROTOCOL_VERSON);
            if (map != null && map.size() > 0) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    httpURLConnectionA.setRequestProperty(entry.getKey(), URLEncoder.encode(entry.getValue(), "utf-8"));
                }
            }
            httpURLConnectionA.setRequestProperty("A37", URLEncoder.encode(str2, "utf-8"));
            httpURLConnectionA.setRequestProperty("A38", URLEncoder.encode(str2, "utf-8"));
            OutputStream outputStream = httpURLConnectionA.getOutputStream();
            if (bArr == null) {
                outputStream.write(0);
            } else {
                outputStream.write(bArr);
            }
            return httpURLConnectionA;
        } catch (Throwable th) {
            if (!al.a(th)) {
                th.printStackTrace();
            }
            al.e("Failed to upload, please check your network.", new Object[0]);
            return null;
        }
    }

    private static HttpURLConnection a(String str, String str2) {
        HttpURLConnection httpURLConnection;
        try {
            URL url = new URL(str2);
            Proxy proxy = an.f17517a;
            if (proxy != null) {
                httpURLConnection = (HttpURLConnection) url.openConnection(proxy);
            } else if (str != null && str.toLowerCase(Locale.US).contains("wap")) {
                httpURLConnection = (HttpURLConnection) url.openConnection(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(System.getProperty("http.proxyHost"), Integer.parseInt(System.getProperty("http.proxyPort")))));
            } else {
                httpURLConnection = (HttpURLConnection) url.openConnection();
            }
            httpURLConnection.setConnectTimeout(30000);
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setInstanceFollowRedirects(false);
            return httpURLConnection;
        } catch (Throwable th) {
            if (al.a(th)) {
                return null;
            }
            th.printStackTrace();
            return null;
        }
    }
}
