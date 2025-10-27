package com.aliyun.utils;

import com.just.agentweb.DefaultWebClient;
import java.io.InputStream;

/* loaded from: classes2.dex */
public abstract class a {
    private static final int CONNECTION_TIMEOUT = 10000;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0060 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:46:? A[SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r4v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v3, types: [java.net.HttpURLConnection] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void doHttpGet(java.lang.String r4) throws java.lang.Throwable {
        /*
            r3 = this;
            r0 = 0
            java.net.URL r1 = new java.net.URL     // Catch: java.lang.Throwable -> L44 java.lang.Exception -> L47
            r1.<init>(r4)     // Catch: java.lang.Throwable -> L44 java.lang.Exception -> L47
            java.net.URLConnection r4 = r1.openConnection()     // Catch: java.lang.Throwable -> L44 java.lang.Exception -> L47
            boolean r4 = r4 instanceof java.net.HttpURLConnection     // Catch: java.lang.Throwable -> L44 java.lang.Exception -> L47
            if (r4 != 0) goto Lf
            return
        Lf:
            java.net.URLConnection r4 = r1.openConnection()     // Catch: java.lang.Throwable -> L44 java.lang.Exception -> L47
            java.net.HttpURLConnection r4 = (java.net.HttpURLConnection) r4     // Catch: java.lang.Throwable -> L44 java.lang.Exception -> L47
            java.lang.String r1 = "GET"
            r4.setRequestMethod(r1)     // Catch: java.lang.Exception -> L42 java.lang.Throwable -> L5d
            r1 = 10000(0x2710, float:1.4013E-41)
            r4.setConnectTimeout(r1)     // Catch: java.lang.Exception -> L42 java.lang.Throwable -> L5d
            r4.setReadTimeout(r1)     // Catch: java.lang.Exception -> L42 java.lang.Throwable -> L5d
            r4.connect()     // Catch: java.lang.Exception -> L42 java.lang.Throwable -> L5d
            int r1 = r4.getResponseCode()     // Catch: java.lang.Exception -> L42 java.lang.Throwable -> L5d
            r2 = 200(0xc8, float:2.8E-43)
            if (r1 != r2) goto L35
            java.io.InputStream r0 = r4.getInputStream()     // Catch: java.lang.Exception -> L42 java.lang.Throwable -> L5d
            r3.handleOKInputStream(r0)     // Catch: java.lang.Exception -> L42 java.lang.Throwable -> L5d
            goto L3c
        L35:
            java.io.InputStream r0 = r4.getErrorStream()     // Catch: java.lang.Exception -> L42 java.lang.Throwable -> L5d
            r3.handleErrorInputStream(r0)     // Catch: java.lang.Exception -> L42 java.lang.Throwable -> L5d
        L3c:
            if (r0 == 0) goto L59
            r0.close()     // Catch: java.io.IOException -> L59
            goto L59
        L42:
            r1 = move-exception
            goto L49
        L44:
            r1 = move-exception
            r4 = r0
            goto L5e
        L47:
            r1 = move-exception
            r4 = r0
        L49:
            java.lang.String r2 = "HttpClientUtil"
            java.lang.String r1 = r1.getMessage()     // Catch: java.lang.Throwable -> L5d
            com.cicada.player.utils.Logger.w(r2, r1)     // Catch: java.lang.Throwable -> L5d
            if (r0 == 0) goto L57
            r0.close()     // Catch: java.io.IOException -> L57
        L57:
            if (r4 == 0) goto L5c
        L59:
            r4.disconnect()
        L5c:
            return
        L5d:
            r1 = move-exception
        L5e:
            if (r0 == 0) goto L63
            r0.close()     // Catch: java.io.IOException -> L63
        L63:
            if (r4 == 0) goto L68
            r4.disconnect()
        L68:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aliyun.utils.a.doHttpGet(java.lang.String):void");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0060 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:46:? A[SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r4v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v3, types: [java.net.HttpURLConnection] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void doHttpsGet(java.lang.String r4) throws java.lang.Throwable {
        /*
            r3 = this;
            r0 = 0
            java.net.URL r1 = new java.net.URL     // Catch: java.lang.Throwable -> L44 java.lang.Exception -> L47
            r1.<init>(r4)     // Catch: java.lang.Throwable -> L44 java.lang.Exception -> L47
            java.net.URLConnection r4 = r1.openConnection()     // Catch: java.lang.Throwable -> L44 java.lang.Exception -> L47
            boolean r4 = r4 instanceof javax.net.ssl.HttpsURLConnection     // Catch: java.lang.Throwable -> L44 java.lang.Exception -> L47
            if (r4 != 0) goto Lf
            return
        Lf:
            java.net.URLConnection r4 = r1.openConnection()     // Catch: java.lang.Throwable -> L44 java.lang.Exception -> L47
            javax.net.ssl.HttpsURLConnection r4 = (javax.net.ssl.HttpsURLConnection) r4     // Catch: java.lang.Throwable -> L44 java.lang.Exception -> L47
            java.lang.String r1 = "GET"
            r4.setRequestMethod(r1)     // Catch: java.lang.Exception -> L42 java.lang.Throwable -> L5d
            r1 = 10000(0x2710, float:1.4013E-41)
            r4.setConnectTimeout(r1)     // Catch: java.lang.Exception -> L42 java.lang.Throwable -> L5d
            r4.setReadTimeout(r1)     // Catch: java.lang.Exception -> L42 java.lang.Throwable -> L5d
            r4.connect()     // Catch: java.lang.Exception -> L42 java.lang.Throwable -> L5d
            int r1 = r4.getResponseCode()     // Catch: java.lang.Exception -> L42 java.lang.Throwable -> L5d
            r2 = 200(0xc8, float:2.8E-43)
            if (r1 != r2) goto L35
            java.io.InputStream r0 = r4.getInputStream()     // Catch: java.lang.Exception -> L42 java.lang.Throwable -> L5d
            r3.handleOKInputStream(r0)     // Catch: java.lang.Exception -> L42 java.lang.Throwable -> L5d
            goto L3c
        L35:
            java.io.InputStream r0 = r4.getErrorStream()     // Catch: java.lang.Exception -> L42 java.lang.Throwable -> L5d
            r3.handleErrorInputStream(r0)     // Catch: java.lang.Exception -> L42 java.lang.Throwable -> L5d
        L3c:
            if (r0 == 0) goto L59
            r0.close()     // Catch: java.io.IOException -> L59
            goto L59
        L42:
            r1 = move-exception
            goto L49
        L44:
            r1 = move-exception
            r4 = r0
            goto L5e
        L47:
            r1 = move-exception
            r4 = r0
        L49:
            java.lang.String r2 = "HttpClientUtil"
            java.lang.String r1 = r1.getMessage()     // Catch: java.lang.Throwable -> L5d
            com.cicada.player.utils.Logger.d(r2, r1)     // Catch: java.lang.Throwable -> L5d
            if (r0 == 0) goto L57
            r0.close()     // Catch: java.io.IOException -> L57
        L57:
            if (r4 == 0) goto L5c
        L59:
            r4.disconnect()
        L5c:
            return
        L5d:
            r1 = move-exception
        L5e:
            if (r0 == 0) goto L63
            r0.close()     // Catch: java.io.IOException -> L63
        L63:
            if (r4 == 0) goto L68
            r4.disconnect()
        L68:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aliyun.utils.a.doHttpsGet(java.lang.String):void");
    }

    public void doGet(String str) throws Throwable {
        if (str.startsWith(DefaultWebClient.HTTPS_SCHEME)) {
            doHttpsGet(str);
        } else if (str.startsWith(DefaultWebClient.HTTP_SCHEME)) {
            doHttpGet(str);
        }
    }

    public abstract void handleErrorInputStream(InputStream inputStream);

    public abstract void handleOKInputStream(InputStream inputStream);
}
