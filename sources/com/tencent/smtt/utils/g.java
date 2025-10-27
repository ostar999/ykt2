package com.tencent.smtt.utils;

import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

/* loaded from: classes6.dex */
public class g {

    public interface a {
        void a(int i2);
    }

    public static String a(String str, Map<String, String> map, byte[] bArr, a aVar, boolean z2) throws Throwable {
        HttpURLConnection httpURLConnectionA;
        if (bArr == null || (httpURLConnectionA = a(str, map)) == null) {
            return null;
        }
        if (z2) {
            a(httpURLConnectionA, bArr);
        } else {
            b(httpURLConnectionA, bArr);
        }
        return a(httpURLConnectionA, aVar, false);
    }

    public static String a(String str, byte[] bArr, a aVar, boolean z2) throws IOException {
        String str2;
        String str3;
        try {
            str3 = str + (z2 ? i.a().c() : h.a().b());
            try {
                bArr = z2 ? i.a().a(bArr) : h.a().a(bArr);
            } catch (Exception e2) {
                e2.printStackTrace();
                TbsLog.e("HttpUtils", "rsaKey exception #2: " + e2);
            }
        } catch (Exception e3) {
            e3.printStackTrace();
            str2 = "rsaKey exception #1: " + e3;
        }
        if (bArr == null) {
            str2 = "postData is null";
            TbsLog.e("HttpUtils", str2);
            return null;
        }
        HashMap map = new HashMap();
        map.put("Content-Type", "application/x-www-form-urlencoded");
        map.put("Content-Length", String.valueOf(bArr.length));
        HttpURLConnection httpURLConnectionA = a(str3, map);
        if (httpURLConnectionA == null) {
            return null;
        }
        b(httpURLConnectionA, bArr);
        return a(httpURLConnectionA, aVar, z2);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(6:8|(6:(9:(1:17)|48|19|20|46|21|(2:22|(1:24)(1:50))|(1:26)(1:28)|27)(1:12)|46|21|(3:22|(0)(0)|24)|(0)(0)|27)|13|48|19|20) */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x007b, code lost:
    
        r4 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x007c, code lost:
    
        r0 = r5;
        r5 = null;
     */
    /* JADX WARN: Removed duplicated region for block: B:24:0x004d A[Catch: all -> 0x0076, LOOP:0: B:22:0x0046->B:24:0x004d, LOOP_END, TryCatch #1 {all -> 0x0076, blocks: (B:21:0x0044, B:22:0x0046, B:24:0x004d, B:26:0x0054, B:28:0x0064), top: B:46:0x0044 }] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0054 A[Catch: all -> 0x0076, TryCatch #1 {all -> 0x0076, blocks: (B:21:0x0044, B:22:0x0046, B:24:0x004d, B:26:0x0054, B:28:0x0064), top: B:46:0x0044 }] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0064 A[Catch: all -> 0x0076, TRY_LEAVE, TryCatch #1 {all -> 0x0076, blocks: (B:21:0x0044, B:22:0x0046, B:24:0x004d, B:26:0x0054, B:28:0x0064), top: B:46:0x0044 }] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0052 A[EDGE_INSN: B:50:0x0052->B:25:0x0052 BREAK  A[LOOP:0: B:22:0x0046->B:24:0x004d], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.lang.String a(java.net.HttpURLConnection r4, com.tencent.smtt.utils.g.a r5, boolean r6) throws java.io.IOException {
        /*
            r0 = 0
            int r1 = r4.getResponseCode()     // Catch: java.lang.Throwable -> L89
            if (r5 == 0) goto La
            r5.a(r1)     // Catch: java.lang.Throwable -> L89
        La:
            r5 = 200(0xc8, float:2.8E-43)
            if (r1 != r5) goto L80
            java.io.InputStream r5 = r4.getInputStream()     // Catch: java.lang.Throwable -> L89
            java.lang.String r4 = r4.getContentEncoding()     // Catch: java.lang.Throwable -> L89
            if (r4 == 0) goto L27
            java.lang.String r1 = "gzip"
            boolean r1 = r4.equalsIgnoreCase(r1)     // Catch: java.lang.Throwable -> L89
            if (r1 == 0) goto L27
            java.util.zip.GZIPInputStream r4 = new java.util.zip.GZIPInputStream     // Catch: java.lang.Throwable -> L89
            r4.<init>(r5)     // Catch: java.lang.Throwable -> L89
        L25:
            r5 = r4
            goto L3d
        L27:
            if (r4 == 0) goto L3d
            java.lang.String r1 = "deflate"
            boolean r4 = r4.equalsIgnoreCase(r1)     // Catch: java.lang.Throwable -> L89
            if (r4 == 0) goto L3d
            java.util.zip.InflaterInputStream r4 = new java.util.zip.InflaterInputStream     // Catch: java.lang.Throwable -> L89
            java.util.zip.Inflater r1 = new java.util.zip.Inflater     // Catch: java.lang.Throwable -> L89
            r2 = 1
            r1.<init>(r2)     // Catch: java.lang.Throwable -> L89
            r4.<init>(r5, r1)     // Catch: java.lang.Throwable -> L89
            goto L25
        L3d:
            java.io.ByteArrayOutputStream r4 = new java.io.ByteArrayOutputStream     // Catch: java.lang.Throwable -> L7b
            r4.<init>()     // Catch: java.lang.Throwable -> L7b
            r0 = 128(0x80, float:1.8E-43)
            byte[] r0 = new byte[r0]     // Catch: java.lang.Throwable -> L76
        L46:
            int r1 = r5.read(r0)     // Catch: java.lang.Throwable -> L76
            r2 = -1
            if (r1 == r2) goto L52
            r2 = 0
            r4.write(r0, r2, r1)     // Catch: java.lang.Throwable -> L76
            goto L46
        L52:
            if (r6 == 0) goto L64
            java.lang.String r0 = new java.lang.String     // Catch: java.lang.Throwable -> L76
            byte[] r6 = r4.toByteArray()     // Catch: java.lang.Throwable -> L76
            java.lang.String r1 = "utf-8"
            r0.<init>(r6, r1)     // Catch: java.lang.Throwable -> L76
        L60:
            r3 = r0
            r0 = r5
            r5 = r3
            goto L82
        L64:
            java.lang.String r0 = new java.lang.String     // Catch: java.lang.Throwable -> L76
            com.tencent.smtt.utils.h r6 = com.tencent.smtt.utils.h.a()     // Catch: java.lang.Throwable -> L76
            byte[] r1 = r4.toByteArray()     // Catch: java.lang.Throwable -> L76
            byte[] r6 = r6.c(r1)     // Catch: java.lang.Throwable -> L76
            r0.<init>(r6)     // Catch: java.lang.Throwable -> L76
            goto L60
        L76:
            r6 = move-exception
            r0 = r5
            r5 = r4
            r4 = r6
            goto L8b
        L7b:
            r4 = move-exception
            r3 = r0
            r0 = r5
            r5 = r3
            goto L8b
        L80:
            r4 = r0
            r5 = r4
        L82:
            a(r0)
            a(r4)
            goto Lc0
        L89:
            r4 = move-exception
            r5 = r0
        L8b:
            r4.printStackTrace()     // Catch: java.lang.Throwable -> Lc1
            java.lang.String r6 = "HttpUtil"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lc1
            r1.<init>()     // Catch: java.lang.Throwable -> Lc1
            java.lang.String r2 = "httpPost exception: "
            r1.append(r2)     // Catch: java.lang.Throwable -> Lc1
            r1.append(r4)     // Catch: java.lang.Throwable -> Lc1
            java.lang.String r1 = r1.toString()     // Catch: java.lang.Throwable -> Lc1
            com.tencent.smtt.utils.TbsLog.e(r6, r1)     // Catch: java.lang.Throwable -> Lc1
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lc1
            r6.<init>()     // Catch: java.lang.Throwable -> Lc1
            java.lang.String r1 = "[HttpError] "
            r6.append(r1)     // Catch: java.lang.Throwable -> Lc1
            java.lang.String r4 = r4.getMessage()     // Catch: java.lang.Throwable -> Lc1
            r6.append(r4)     // Catch: java.lang.Throwable -> Lc1
            java.lang.String r4 = r6.toString()     // Catch: java.lang.Throwable -> Lc1
            a(r0)
            a(r5)
            r5 = r4
        Lc0:
            return r5
        Lc1:
            r4 = move-exception
            a(r0)
            a(r5)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.utils.g.a(java.net.HttpURLConnection, com.tencent.smtt.utils.g$a, boolean):java.lang.String");
    }

    private static HttpURLConnection a(String str, Map<String, String> map) throws ProtocolException {
        HttpURLConnection httpURLConnection = null;
        try {
            HttpURLConnection httpURLConnection2 = (HttpURLConnection) new URL(str).openConnection();
            try {
                httpURLConnection2.setRequestMethod("POST");
                httpURLConnection2.setDoOutput(true);
                httpURLConnection2.setDoInput(true);
                httpURLConnection2.setUseCaches(false);
                httpURLConnection2.setConnectTimeout(20000);
                httpURLConnection2.setRequestProperty("Connection", "close");
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    httpURLConnection2.setRequestProperty(entry.getKey(), entry.getValue());
                }
                return httpURLConnection2;
            } catch (Exception e2) {
                e = e2;
                httpURLConnection = httpURLConnection2;
                TbsLog.e("HttpUtil", "initHttpPostURLConnection exception: " + e);
                return httpURLConnection;
            }
        } catch (Exception e3) {
            e = e3;
        }
    }

    private static void a(Closeable closeable) throws IOException {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception unused) {
            }
        }
    }

    private static void a(HttpURLConnection httpURLConnection, byte[] bArr) throws Throwable {
        GZIPOutputStream gZIPOutputStream;
        try {
            gZIPOutputStream = new GZIPOutputStream(new BufferedOutputStream(httpURLConnection.getOutputStream(), 204800));
        } catch (Exception e2) {
            e = e2;
            gZIPOutputStream = null;
        } catch (Throwable th) {
            th = th;
            gZIPOutputStream = null;
            a(null);
            a(gZIPOutputStream);
            throw th;
        }
        try {
            try {
                gZIPOutputStream.write(bArr);
                gZIPOutputStream.flush();
            } catch (Throwable th2) {
                th = th2;
                a(null);
                a(gZIPOutputStream);
                throw th;
            }
        } catch (Exception e3) {
            e = e3;
            e.printStackTrace();
            a(null);
            a(gZIPOutputStream);
        }
        a(null);
        a(gZIPOutputStream);
    }

    private static void b(HttpURLConnection httpURLConnection, byte[] bArr) throws IOException {
        OutputStream outputStream = null;
        try {
            try {
                outputStream = httpURLConnection.getOutputStream();
                outputStream.write(bArr);
                outputStream.flush();
            } catch (Exception e2) {
                TbsLog.e("HttpUtil", "writePostData exception: " + e2);
            }
        } finally {
            a(outputStream);
        }
    }
}
