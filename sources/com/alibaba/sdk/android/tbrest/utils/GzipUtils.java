package com.alibaba.sdk.android.tbrest.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

/* loaded from: classes2.dex */
public class GzipUtils {
    /* JADX WARN: Removed duplicated region for block: B:56:0x0052 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x005c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:68:? A[SYNTHETIC] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:17:0x002a -> B:51:0x004d). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static byte[] gzip(byte[] r4) throws java.lang.Throwable {
        /*
            if (r4 == 0) goto L65
            int r0 = r4.length
            if (r0 != 0) goto L7
            goto L65
        L7:
            r0 = 0
            java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L38
            r1.<init>()     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L38
            java.util.zip.GZIPOutputStream r2 = new java.util.zip.GZIPOutputStream     // Catch: java.lang.Throwable -> L30 java.lang.Exception -> L32
            int r3 = r4.length     // Catch: java.lang.Throwable -> L30 java.lang.Exception -> L32
            r2.<init>(r1, r3)     // Catch: java.lang.Throwable -> L30 java.lang.Exception -> L32
            r2.write(r4)     // Catch: java.lang.Exception -> L2e java.lang.Throwable -> L4e
            r2.finish()     // Catch: java.lang.Exception -> L2e java.lang.Throwable -> L4e
            byte[] r0 = r1.toByteArray()     // Catch: java.lang.Exception -> L2e java.lang.Throwable -> L4e
            r2.close()     // Catch: java.io.IOException -> L21
            goto L25
        L21:
            r4 = move-exception
            r4.printStackTrace()
        L25:
            r1.close()     // Catch: java.io.IOException -> L29
            goto L4d
        L29:
            r4 = move-exception
            r4.printStackTrace()
            goto L4d
        L2e:
            r4 = move-exception
            goto L3b
        L30:
            r4 = move-exception
            goto L50
        L32:
            r4 = move-exception
            r2 = r0
            goto L3b
        L35:
            r4 = move-exception
            r1 = r0
            goto L50
        L38:
            r4 = move-exception
            r1 = r0
            r2 = r1
        L3b:
            r4.printStackTrace()     // Catch: java.lang.Throwable -> L4e
            if (r2 == 0) goto L48
            r2.close()     // Catch: java.io.IOException -> L44
            goto L48
        L44:
            r4 = move-exception
            r4.printStackTrace()
        L48:
            if (r1 == 0) goto L4d
            r1.close()     // Catch: java.io.IOException -> L29
        L4d:
            return r0
        L4e:
            r4 = move-exception
            r0 = r2
        L50:
            if (r0 == 0) goto L5a
            r0.close()     // Catch: java.io.IOException -> L56
            goto L5a
        L56:
            r0 = move-exception
            r0.printStackTrace()
        L5a:
            if (r1 == 0) goto L64
            r1.close()     // Catch: java.io.IOException -> L60
            goto L64
        L60:
            r0 = move-exception
            r0.printStackTrace()
        L64:
            throw r4
        L65:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.tbrest.utils.GzipUtils.gzip(byte[]):byte[]");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v7, types: [java.lang.String] */
    public static byte[] gzipAndRc4Bytes(String str) throws Throwable {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        GZIPOutputStream gZIPOutputStream = null;
        GZIPOutputStream gZIPOutputStream2 = null;
        try {
            try {
                GZIPOutputStream gZIPOutputStream3 = new GZIPOutputStream(byteArrayOutputStream);
                try {
                    ?? r12 = "UTF-8";
                    gZIPOutputStream3.write(str.getBytes("UTF-8"));
                    gZIPOutputStream3.flush();
                    gZIPOutputStream3.close();
                    gZIPOutputStream = r12;
                } catch (IOException e2) {
                    e = e2;
                    gZIPOutputStream2 = gZIPOutputStream3;
                    e.printStackTrace();
                    gZIPOutputStream = gZIPOutputStream2;
                    if (gZIPOutputStream2 != null) {
                        gZIPOutputStream2.close();
                        gZIPOutputStream = gZIPOutputStream2;
                    }
                    byte[] bArrRc4 = RC4.rc4(byteArrayOutputStream.toByteArray());
                    byteArrayOutputStream.close();
                    return bArrRc4;
                } catch (Throwable th) {
                    th = th;
                    gZIPOutputStream = gZIPOutputStream3;
                    if (gZIPOutputStream != null) {
                        try {
                            gZIPOutputStream.close();
                        } catch (Exception unused) {
                        }
                    }
                    throw th;
                }
            } catch (IOException e3) {
                e = e3;
            }
            byte[] bArrRc42 = RC4.rc4(byteArrayOutputStream.toByteArray());
            try {
                byteArrayOutputStream.close();
            } catch (Exception unused2) {
            }
            return bArrRc42;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:74:0x008e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:80:0x007a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:82:0x0084 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:96:? A[SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r4v0 */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v10, types: [java.io.ByteArrayOutputStream, java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r4v2 */
    /* JADX WARN: Type inference failed for: r4v3, types: [java.io.ByteArrayOutputStream] */
    /* JADX WARN: Type inference failed for: r4v4 */
    /* JADX WARN: Type inference failed for: r4v5, types: [java.io.ByteArrayOutputStream] */
    /* JADX WARN: Type inference failed for: r4v6 */
    /* JADX WARN: Type inference failed for: r4v7 */
    /* JADX WARN: Type inference failed for: r4v8 */
    /* JADX WARN: Type inference failed for: r4v9 */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:23:0x003c -> B:87:0x0076). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static byte[] unGzip(byte[] r8) throws java.lang.Throwable {
        /*
            r0 = 0
            java.io.ByteArrayInputStream r1 = new java.io.ByteArrayInputStream     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L56
            r1.<init>(r8)     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L56
            java.util.zip.GZIPInputStream r8 = new java.util.zip.GZIPInputStream     // Catch: java.lang.Throwable -> L49 java.lang.Exception -> L4c
            r8.<init>(r1)     // Catch: java.lang.Throwable -> L49 java.lang.Exception -> L4c
            r2 = 1024(0x400, float:1.435E-42)
            byte[] r3 = new byte[r2]     // Catch: java.lang.Throwable -> L42 java.lang.Exception -> L46
            java.io.ByteArrayOutputStream r4 = new java.io.ByteArrayOutputStream     // Catch: java.lang.Throwable -> L42 java.lang.Exception -> L46
            r4.<init>()     // Catch: java.lang.Throwable -> L42 java.lang.Exception -> L46
        L14:
            r5 = 0
            int r6 = r8.read(r3, r5, r2)     // Catch: java.lang.Exception -> L40 java.lang.Throwable -> L77
            r7 = -1
            if (r6 == r7) goto L20
            r4.write(r3, r5, r6)     // Catch: java.lang.Exception -> L40 java.lang.Throwable -> L77
            goto L14
        L20:
            r4.flush()     // Catch: java.lang.Exception -> L40 java.lang.Throwable -> L77
            byte[] r0 = r4.toByteArray()     // Catch: java.lang.Exception -> L40 java.lang.Throwable -> L77
            r4.close()     // Catch: java.lang.Exception -> L2b
            goto L2f
        L2b:
            r2 = move-exception
            r2.printStackTrace()
        L2f:
            r8.close()     // Catch: java.io.IOException -> L33
            goto L37
        L33:
            r8 = move-exception
            r8.printStackTrace()
        L37:
            r1.close()     // Catch: java.io.IOException -> L3b
            goto L76
        L3b:
            r8 = move-exception
            r8.printStackTrace()
            goto L76
        L40:
            r2 = move-exception
            goto L5a
        L42:
            r2 = move-exception
            r4 = r0
            r0 = r2
            goto L78
        L46:
            r2 = move-exception
            r4 = r0
            goto L5a
        L49:
            r8 = move-exception
            r4 = r0
            goto L53
        L4c:
            r2 = move-exception
            r8 = r0
            r4 = r8
            goto L5a
        L50:
            r8 = move-exception
            r1 = r0
            r4 = r1
        L53:
            r0 = r8
            r8 = r4
            goto L78
        L56:
            r2 = move-exception
            r8 = r0
            r1 = r8
            r4 = r1
        L5a:
            r2.printStackTrace()     // Catch: java.lang.Throwable -> L77
            if (r4 == 0) goto L67
            r4.close()     // Catch: java.lang.Exception -> L63
            goto L67
        L63:
            r2 = move-exception
            r2.printStackTrace()
        L67:
            if (r8 == 0) goto L71
            r8.close()     // Catch: java.io.IOException -> L6d
            goto L71
        L6d:
            r8 = move-exception
            r8.printStackTrace()
        L71:
            if (r1 == 0) goto L76
            r1.close()     // Catch: java.io.IOException -> L3b
        L76:
            return r0
        L77:
            r0 = move-exception
        L78:
            if (r4 == 0) goto L82
            r4.close()     // Catch: java.lang.Exception -> L7e
            goto L82
        L7e:
            r2 = move-exception
            r2.printStackTrace()
        L82:
            if (r8 == 0) goto L8c
            r8.close()     // Catch: java.io.IOException -> L88
            goto L8c
        L88:
            r8 = move-exception
            r8.printStackTrace()
        L8c:
            if (r1 == 0) goto L96
            r1.close()     // Catch: java.io.IOException -> L92
            goto L96
        L92:
            r8 = move-exception
            r8.printStackTrace()
        L96:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.tbrest.utils.GzipUtils.unGzip(byte[]):byte[]");
    }
}
