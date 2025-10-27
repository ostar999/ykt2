package com.huawei.hms.opendevice;

import android.content.Context;
import android.text.TextUtils;
import com.huawei.hms.support.log.HMSLog;
import com.huawei.secure.android.common.util.IOUtil;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/* loaded from: classes4.dex */
public abstract class d {

    public enum a {
        GET("GET"),
        POST("POST");


        /* renamed from: d, reason: collision with root package name */
        public String f7906d;

        a(String str) {
            this.f7906d = str;
        }

        public String a() {
            return this.f7906d;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v1 */
    /* JADX WARN: Type inference failed for: r10v10 */
    /* JADX WARN: Type inference failed for: r10v13 */
    /* JADX WARN: Type inference failed for: r10v16 */
    /* JADX WARN: Type inference failed for: r10v21 */
    /* JADX WARN: Type inference failed for: r10v5, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r2v0 */
    /* JADX WARN: Type inference failed for: r2v10 */
    /* JADX WARN: Type inference failed for: r2v2 */
    /* JADX WARN: Type inference failed for: r2v3, types: [java.net.HttpURLConnection] */
    /* JADX WARN: Type inference failed for: r2v8 */
    /* JADX WARN: Type inference failed for: r2v9 */
    /* JADX WARN: Type inference failed for: r8v1 */
    /* JADX WARN: Type inference failed for: r8v10 */
    /* JADX WARN: Type inference failed for: r8v11, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r8v12 */
    /* JADX WARN: Type inference failed for: r8v13 */
    /* JADX WARN: Type inference failed for: r8v15 */
    /* JADX WARN: Type inference failed for: r8v17 */
    /* JADX WARN: Type inference failed for: r8v21, types: [java.io.BufferedOutputStream, java.io.FilterOutputStream, java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r8v25 */
    /* JADX WARN: Type inference failed for: r8v26 */
    /* JADX WARN: Type inference failed for: r8v27 */
    /* JADX WARN: Type inference failed for: r8v28 */
    /* JADX WARN: Type inference failed for: r8v29 */
    /* JADX WARN: Type inference failed for: r8v30 */
    /* JADX WARN: Type inference failed for: r8v31 */
    /* JADX WARN: Type inference failed for: r8v32 */
    /* JADX WARN: Type inference failed for: r8v33 */
    /* JADX WARN: Type inference failed for: r8v8 */
    /* JADX WARN: Type inference failed for: r8v9 */
    /* JADX WARN: Type inference failed for: r9v1 */
    /* JADX WARN: Type inference failed for: r9v13 */
    /* JADX WARN: Type inference failed for: r9v15 */
    /* JADX WARN: Type inference failed for: r9v31 */
    /* JADX WARN: Type inference failed for: r9v32 */
    /* JADX WARN: Type inference failed for: r9v8, types: [java.io.InputStream] */
    public static String a(Context context, String str, String str2, Map<String, String> map) throws Throwable {
        ?? r8;
        ?? r9;
        ?? r10;
        HttpURLConnection httpURLConnectionA;
        InputStream inputStream;
        InputStream inputStream2;
        InputStream inputStream3;
        InputStream errorStream;
        ?? bufferedOutputStream;
        InputStream bufferedInputStream;
        InputStream inputStream4;
        OutputStream outputStream;
        ?? r2 = 0;
        if (str2 == null || TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return null;
        }
        int responseCode = -1;
        try {
            try {
                httpURLConnectionA = a(context, str, map, a.POST.a());
                if (httpURLConnectionA == null) {
                    IOUtil.closeSecure((OutputStream) null);
                    IOUtil.closeSecure((InputStream) null);
                    IOUtil.closeSecure((InputStream) null);
                    s.a(httpURLConnectionA);
                    HMSLog.i("PushHttpClient", "close connection");
                    return null;
                }
                try {
                    bufferedOutputStream = new BufferedOutputStream(httpURLConnectionA.getOutputStream());
                    try {
                        bufferedOutputStream.write(str2.getBytes("UTF-8"));
                        bufferedOutputStream.flush();
                        responseCode = httpURLConnectionA.getResponseCode();
                        StringBuilder sb = new StringBuilder();
                        sb.append("http post response code: ");
                        sb.append(responseCode);
                        HMSLog.d("PushHttpClient", sb.toString());
                        errorStream = responseCode >= 400 ? httpURLConnectionA.getErrorStream() : httpURLConnectionA.getInputStream();
                    } catch (IOException unused) {
                        errorStream = null;
                        bufferedOutputStream = bufferedOutputStream;
                        bufferedInputStream = errorStream;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("http execute encounter IOException - http code:");
                        sb2.append(responseCode);
                        HMSLog.w("PushHttpClient", sb2.toString());
                        outputStream = bufferedOutputStream;
                        inputStream4 = errorStream;
                        IOUtil.closeSecure(outputStream);
                        IOUtil.closeSecure(inputStream4);
                        IOUtil.closeSecure(bufferedInputStream);
                        s.a(httpURLConnectionA);
                        HMSLog.i("PushHttpClient", "close connection");
                        return null;
                    } catch (RuntimeException unused2) {
                        errorStream = null;
                        bufferedOutputStream = bufferedOutputStream;
                        bufferedInputStream = errorStream;
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append("http execute encounter RuntimeException - http code:");
                        sb3.append(responseCode);
                        HMSLog.w("PushHttpClient", sb3.toString());
                        outputStream = bufferedOutputStream;
                        inputStream4 = errorStream;
                        IOUtil.closeSecure(outputStream);
                        IOUtil.closeSecure(inputStream4);
                        IOUtil.closeSecure(bufferedInputStream);
                        s.a(httpURLConnectionA);
                        HMSLog.i("PushHttpClient", "close connection");
                        return null;
                    } catch (Exception unused3) {
                        errorStream = null;
                        bufferedOutputStream = bufferedOutputStream;
                        bufferedInputStream = errorStream;
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append("http execute encounter unknown exception - http code:");
                        sb4.append(responseCode);
                        HMSLog.w("PushHttpClient", sb4.toString());
                        outputStream = bufferedOutputStream;
                        inputStream4 = errorStream;
                        IOUtil.closeSecure(outputStream);
                        IOUtil.closeSecure(inputStream4);
                        IOUtil.closeSecure(bufferedInputStream);
                        s.a(httpURLConnectionA);
                        HMSLog.i("PushHttpClient", "close connection");
                        return null;
                    } catch (Throwable th) {
                        r10 = 0;
                        r2 = httpURLConnectionA;
                        th = th;
                        r9 = 0;
                        r8 = bufferedOutputStream;
                    }
                    try {
                        bufferedInputStream = new BufferedInputStream(errorStream);
                    } catch (IOException unused4) {
                        bufferedInputStream = null;
                    } catch (RuntimeException unused5) {
                        bufferedInputStream = null;
                    } catch (Exception unused6) {
                        bufferedInputStream = null;
                    } catch (Throwable th2) {
                        r2 = httpURLConnectionA;
                        th = th2;
                        r10 = 0;
                        r8 = bufferedOutputStream;
                        r9 = errorStream;
                        IOUtil.closeSecure((OutputStream) r8);
                        IOUtil.closeSecure((InputStream) r9);
                        IOUtil.closeSecure((InputStream) r10);
                        s.a((HttpURLConnection) r2);
                        HMSLog.i("PushHttpClient", "close connection");
                        throw th;
                    }
                } catch (IOException unused7) {
                    inputStream3 = null;
                    errorStream = inputStream3;
                    bufferedOutputStream = inputStream3;
                    bufferedInputStream = errorStream;
                    StringBuilder sb22 = new StringBuilder();
                    sb22.append("http execute encounter IOException - http code:");
                    sb22.append(responseCode);
                    HMSLog.w("PushHttpClient", sb22.toString());
                    outputStream = bufferedOutputStream;
                    inputStream4 = errorStream;
                    IOUtil.closeSecure(outputStream);
                    IOUtil.closeSecure(inputStream4);
                    IOUtil.closeSecure(bufferedInputStream);
                    s.a(httpURLConnectionA);
                    HMSLog.i("PushHttpClient", "close connection");
                    return null;
                } catch (RuntimeException unused8) {
                    inputStream2 = null;
                    errorStream = inputStream2;
                    bufferedOutputStream = inputStream2;
                    bufferedInputStream = errorStream;
                    StringBuilder sb32 = new StringBuilder();
                    sb32.append("http execute encounter RuntimeException - http code:");
                    sb32.append(responseCode);
                    HMSLog.w("PushHttpClient", sb32.toString());
                    outputStream = bufferedOutputStream;
                    inputStream4 = errorStream;
                    IOUtil.closeSecure(outputStream);
                    IOUtil.closeSecure(inputStream4);
                    IOUtil.closeSecure(bufferedInputStream);
                    s.a(httpURLConnectionA);
                    HMSLog.i("PushHttpClient", "close connection");
                    return null;
                } catch (Exception unused9) {
                    inputStream = null;
                    errorStream = inputStream;
                    bufferedOutputStream = inputStream;
                    bufferedInputStream = errorStream;
                    StringBuilder sb42 = new StringBuilder();
                    sb42.append("http execute encounter unknown exception - http code:");
                    sb42.append(responseCode);
                    HMSLog.w("PushHttpClient", sb42.toString());
                    outputStream = bufferedOutputStream;
                    inputStream4 = errorStream;
                    IOUtil.closeSecure(outputStream);
                    IOUtil.closeSecure(inputStream4);
                    IOUtil.closeSecure(bufferedInputStream);
                    s.a(httpURLConnectionA);
                    HMSLog.i("PushHttpClient", "close connection");
                    return null;
                } catch (Throwable th3) {
                    r9 = 0;
                    r10 = 0;
                    r2 = httpURLConnectionA;
                    th = th3;
                    r8 = 0;
                }
                try {
                    String strA = s.a(bufferedInputStream);
                    IOUtil.closeSecure((OutputStream) bufferedOutputStream);
                    IOUtil.closeSecure(errorStream);
                    IOUtil.closeSecure(bufferedInputStream);
                    s.a(httpURLConnectionA);
                    HMSLog.i("PushHttpClient", "close connection");
                    return strA;
                } catch (IOException unused10) {
                    StringBuilder sb222 = new StringBuilder();
                    sb222.append("http execute encounter IOException - http code:");
                    sb222.append(responseCode);
                    HMSLog.w("PushHttpClient", sb222.toString());
                    outputStream = bufferedOutputStream;
                    inputStream4 = errorStream;
                    IOUtil.closeSecure(outputStream);
                    IOUtil.closeSecure(inputStream4);
                    IOUtil.closeSecure(bufferedInputStream);
                    s.a(httpURLConnectionA);
                    HMSLog.i("PushHttpClient", "close connection");
                    return null;
                } catch (RuntimeException unused11) {
                    StringBuilder sb322 = new StringBuilder();
                    sb322.append("http execute encounter RuntimeException - http code:");
                    sb322.append(responseCode);
                    HMSLog.w("PushHttpClient", sb322.toString());
                    outputStream = bufferedOutputStream;
                    inputStream4 = errorStream;
                    IOUtil.closeSecure(outputStream);
                    IOUtil.closeSecure(inputStream4);
                    IOUtil.closeSecure(bufferedInputStream);
                    s.a(httpURLConnectionA);
                    HMSLog.i("PushHttpClient", "close connection");
                    return null;
                } catch (Exception unused12) {
                    StringBuilder sb422 = new StringBuilder();
                    sb422.append("http execute encounter unknown exception - http code:");
                    sb422.append(responseCode);
                    HMSLog.w("PushHttpClient", sb422.toString());
                    outputStream = bufferedOutputStream;
                    inputStream4 = errorStream;
                    IOUtil.closeSecure(outputStream);
                    IOUtil.closeSecure(inputStream4);
                    IOUtil.closeSecure(bufferedInputStream);
                    s.a(httpURLConnectionA);
                    HMSLog.i("PushHttpClient", "close connection");
                    return null;
                }
            } catch (IOException unused13) {
                httpURLConnectionA = null;
                inputStream3 = null;
            } catch (RuntimeException unused14) {
                httpURLConnectionA = null;
                inputStream2 = null;
            } catch (Exception unused15) {
                httpURLConnectionA = null;
                inputStream = null;
            } catch (Throwable th4) {
                th = th4;
                r8 = 0;
                r9 = 0;
                r10 = 0;
            }
        } catch (Throwable th5) {
            r2 = context;
            th = th5;
            r8 = str;
            r9 = str2;
            r10 = map;
        }
    }

    public static HttpURLConnection a(Context context, String str, Map<String, String> map, String str2) throws Exception {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
        a(context, httpURLConnection);
        httpURLConnection.setRequestMethod(str2);
        httpURLConnection.setConnectTimeout(15000);
        httpURLConnection.setReadTimeout(15000);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setRequestProperty("Content-type", "application/json; charset=UTF-8");
        httpURLConnection.setRequestProperty("Connection", "close");
        if (map != null && map.size() >= 1) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                if (key != null && !TextUtils.isEmpty(key)) {
                    httpURLConnection.setRequestProperty(key, URLEncoder.encode(entry.getValue() == null ? "" : entry.getValue(), "UTF-8"));
                }
            }
        }
        return httpURLConnection;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x003c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void a(android.content.Context r2, java.net.HttpURLConnection r3) throws java.lang.Exception {
        /*
            java.lang.String r0 = "PushHttpClient"
            boolean r1 = r3 instanceof javax.net.ssl.HttpsURLConnection
            if (r1 == 0) goto L44
            javax.net.ssl.HttpsURLConnection r3 = (javax.net.ssl.HttpsURLConnection) r3
            com.huawei.secure.android.common.ssl.SecureSSLSocketFactory r2 = com.huawei.secure.android.common.ssl.SecureSSLSocketFactory.getInstance(r2)     // Catch: java.lang.IllegalArgumentException -> Ld java.lang.IllegalAccessException -> L13 java.io.IOException -> L19 java.security.GeneralSecurityException -> L1f java.security.KeyStoreException -> L25 java.security.NoSuchAlgorithmException -> L2b
            goto L31
        Ld:
            java.lang.String r2 = "Get SocketFactory Illegal Argument Exception."
            com.huawei.hms.support.log.HMSLog.w(r0, r2)
            goto L30
        L13:
            java.lang.String r2 = "Get SocketFactory Illegal Access Exception."
            com.huawei.hms.support.log.HMSLog.w(r0, r2)
            goto L30
        L19:
            java.lang.String r2 = "Get SocketFactory IO Exception."
            com.huawei.hms.support.log.HMSLog.w(r0, r2)
            goto L30
        L1f:
            java.lang.String r2 = "Get SocketFactory General Security Exception."
            com.huawei.hms.support.log.HMSLog.w(r0, r2)
            goto L30
        L25:
            java.lang.String r2 = "Get SocketFactory Key Store exception."
            com.huawei.hms.support.log.HMSLog.w(r0, r2)
            goto L30
        L2b:
            java.lang.String r2 = "Get SocketFactory Algorithm Exception."
            com.huawei.hms.support.log.HMSLog.w(r0, r2)
        L30:
            r2 = 0
        L31:
            if (r2 == 0) goto L3c
            r3.setSSLSocketFactory(r2)
            org.apache.http.conn.ssl.X509HostnameVerifier r2 = com.huawei.secure.android.common.ssl.SecureSSLSocketFactory.STRICT_HOSTNAME_VERIFIER
            r3.setHostnameVerifier(r2)
            goto L44
        L3c:
            java.lang.Exception r2 = new java.lang.Exception
            java.lang.String r3 = "No ssl socket factory set."
            r2.<init>(r3)
            throw r2
        L44:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.opendevice.d.a(android.content.Context, java.net.HttpURLConnection):void");
    }
}
