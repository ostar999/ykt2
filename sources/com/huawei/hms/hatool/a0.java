package com.huawei.hms.hatool;

import android.text.TextUtils;
import com.tencent.liteav.audio.TXEAudioDef;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Map;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;

/* loaded from: classes4.dex */
public abstract class a0 {

    public static class a extends Exception {
        public a(String str) {
            super(str);
        }
    }

    public static b0 a(String str, byte[] bArr, Map<String, String> map) {
        return a(str, bArr, map, "POST");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v0, types: [java.lang.CharSequence, java.lang.String] */
    /* JADX WARN: Type inference failed for: r6v1 */
    /* JADX WARN: Type inference failed for: r6v10, types: [java.net.HttpURLConnection] */
    /* JADX WARN: Type inference failed for: r6v11, types: [java.net.HttpURLConnection] */
    /* JADX WARN: Type inference failed for: r6v12, types: [java.net.HttpURLConnection] */
    /* JADX WARN: Type inference failed for: r6v13, types: [java.net.HttpURLConnection] */
    /* JADX WARN: Type inference failed for: r6v14, types: [java.net.HttpURLConnection] */
    /* JADX WARN: Type inference failed for: r6v15, types: [java.net.HttpURLConnection] */
    /* JADX WARN: Type inference failed for: r6v16, types: [java.net.HttpURLConnection] */
    /* JADX WARN: Type inference failed for: r6v17, types: [java.net.HttpURLConnection, java.net.URLConnection] */
    /* JADX WARN: Type inference failed for: r6v2 */
    /* JADX WARN: Type inference failed for: r6v3 */
    /* JADX WARN: Type inference failed for: r6v4 */
    /* JADX WARN: Type inference failed for: r6v5 */
    /* JADX WARN: Type inference failed for: r6v6 */
    /* JADX WARN: Type inference failed for: r6v7 */
    /* JADX WARN: Type inference failed for: r6v8 */
    /* JADX WARN: Type inference failed for: r6v9, types: [java.net.HttpURLConnection] */
    /* JADX WARN: Type inference failed for: r8v0, types: [java.util.Map, java.util.Map<java.lang.String, java.lang.String>] */
    /* JADX WARN: Type inference failed for: r8v1 */
    /* JADX WARN: Type inference failed for: r8v10, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r8v11, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r8v12, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r8v13, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r8v14, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r8v15, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r8v16, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r8v17 */
    /* JADX WARN: Type inference failed for: r8v18 */
    /* JADX WARN: Type inference failed for: r8v19 */
    /* JADX WARN: Type inference failed for: r8v2 */
    /* JADX WARN: Type inference failed for: r8v20 */
    /* JADX WARN: Type inference failed for: r8v21 */
    /* JADX WARN: Type inference failed for: r8v22 */
    /* JADX WARN: Type inference failed for: r8v23 */
    /* JADX WARN: Type inference failed for: r8v24 */
    /* JADX WARN: Type inference failed for: r8v25, types: [java.io.Closeable, java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r8v3 */
    /* JADX WARN: Type inference failed for: r8v4 */
    /* JADX WARN: Type inference failed for: r8v5 */
    /* JADX WARN: Type inference failed for: r8v6 */
    /* JADX WARN: Type inference failed for: r8v7 */
    /* JADX WARN: Type inference failed for: r8v8 */
    /* JADX WARN: Type inference failed for: r8v9, types: [java.io.Closeable] */
    public static b0 a(String str, byte[] bArr, Map<String, String> map, String str2) throws Throwable {
        BufferedOutputStream bufferedOutputStream;
        int responseCode;
        if (TextUtils.isEmpty(str)) {
            return new b0(-100, "");
        }
        BufferedOutputStream bufferedOutputStream2 = null;
        int i2 = TXEAudioDef.TXE_AUDIO_PLAY_ERR_AUDIO_TYPE_NOT_SUPPORT;
        try {
            try {
                str = a((String) str, bArr.length, (Map<String, String>) map, str2);
                try {
                    if (str == 0) {
                        b0 b0Var = new b0(TXEAudioDef.TXE_AUDIO_PLAY_ERR_INVALID_STATE, "");
                        t0.a((Closeable) null);
                        t0.a((Closeable) null);
                        if (str != 0) {
                            t0.a((HttpURLConnection) str);
                        }
                        return b0Var;
                    }
                    map = str.getOutputStream();
                    try {
                        bufferedOutputStream = new BufferedOutputStream(map);
                    } catch (a unused) {
                    } catch (SecurityException unused2) {
                    } catch (ConnectException unused3) {
                    } catch (UnknownHostException unused4) {
                    } catch (SSLHandshakeException unused5) {
                    } catch (SSLPeerUnverifiedException unused6) {
                    } catch (IOException unused7) {
                    }
                    try {
                        try {
                            bufferedOutputStream.write(bArr);
                            bufferedOutputStream.flush();
                            responseCode = str.getResponseCode();
                        } catch (UnknownHostException unused8) {
                        } catch (SSLHandshakeException unused9) {
                        } catch (IOException unused10) {
                        } catch (SecurityException unused11) {
                        } catch (ConnectException unused12) {
                        } catch (SSLPeerUnverifiedException unused13) {
                        }
                        try {
                            b0 b0Var2 = new b0(responseCode, b(str));
                            t0.a((Closeable) bufferedOutputStream);
                            t0.a((Closeable) map);
                            t0.a((HttpURLConnection) str);
                            return b0Var2;
                        } catch (SecurityException unused14) {
                            i2 = responseCode;
                            bufferedOutputStream2 = bufferedOutputStream;
                            y.f("hmsSdk", "SecurityException with HttpClient. Please check INTERNET permission.");
                            b0 b0Var3 = new b0(i2, "");
                            t0.a((Closeable) bufferedOutputStream2);
                            t0.a((Closeable) map);
                            if (str != 0) {
                                t0.a((HttpURLConnection) str);
                            }
                            return b0Var3;
                        } catch (ConnectException unused15) {
                            i2 = responseCode;
                            bufferedOutputStream2 = bufferedOutputStream;
                            y.f("hmsSdk", "Network is unreachable or Connection refused");
                            b0 b0Var4 = new b0(i2, "");
                            t0.a((Closeable) bufferedOutputStream2);
                            t0.a((Closeable) map);
                            if (str != 0) {
                                t0.a((HttpURLConnection) str);
                            }
                            return b0Var4;
                        } catch (UnknownHostException unused16) {
                            i2 = responseCode;
                            bufferedOutputStream2 = bufferedOutputStream;
                            y.f("hmsSdk", "No address associated with hostname or No network");
                            b0 b0Var5 = new b0(i2, "");
                            t0.a((Closeable) bufferedOutputStream2);
                            t0.a((Closeable) map);
                            if (str != 0) {
                                t0.a((HttpURLConnection) str);
                            }
                            return b0Var5;
                        } catch (SSLHandshakeException unused17) {
                            i2 = responseCode;
                            bufferedOutputStream2 = bufferedOutputStream;
                            y.f("hmsSdk", "Chain validation failed,Certificate expired");
                            b0 b0Var6 = new b0(i2, "");
                            t0.a((Closeable) bufferedOutputStream2);
                            t0.a((Closeable) map);
                            if (str != 0) {
                                t0.a((HttpURLConnection) str);
                            }
                            return b0Var6;
                        } catch (SSLPeerUnverifiedException unused18) {
                            i2 = responseCode;
                            bufferedOutputStream2 = bufferedOutputStream;
                            y.f("hmsSdk", "Certificate has not been verified,Request is restricted!");
                            b0 b0Var7 = new b0(i2, "");
                            t0.a((Closeable) bufferedOutputStream2);
                            t0.a((Closeable) map);
                            if (str != 0) {
                                t0.a((HttpURLConnection) str);
                            }
                            return b0Var7;
                        } catch (IOException unused19) {
                            i2 = responseCode;
                            bufferedOutputStream2 = bufferedOutputStream;
                            y.f("hmsSdk", "events PostRequest(byte[]): IOException occurred.");
                            b0 b0Var8 = new b0(i2, "");
                            t0.a((Closeable) bufferedOutputStream2);
                            t0.a((Closeable) map);
                            if (str != 0) {
                                t0.a((HttpURLConnection) str);
                            }
                            return b0Var8;
                        }
                    } catch (a unused20) {
                        bufferedOutputStream2 = bufferedOutputStream;
                        y.f("hmsSdk", "PostRequest(byte[]): No ssl socket factory set!");
                        b0 b0Var9 = new b0(TXEAudioDef.TXE_AUDIO_PLAY_ERR_INVALID_STATE, "");
                        t0.a((Closeable) bufferedOutputStream2);
                        t0.a((Closeable) map);
                        if (str != 0) {
                            t0.a((HttpURLConnection) str);
                        }
                        return b0Var9;
                    } catch (Throwable th) {
                        th = th;
                        bufferedOutputStream2 = bufferedOutputStream;
                        t0.a((Closeable) bufferedOutputStream2);
                        t0.a((Closeable) map);
                        if (str != 0) {
                            t0.a((HttpURLConnection) str);
                        }
                        throw th;
                    }
                } catch (a unused21) {
                    map = 0;
                } catch (SecurityException unused22) {
                    map = 0;
                } catch (ConnectException unused23) {
                    map = 0;
                } catch (UnknownHostException unused24) {
                    map = 0;
                } catch (SSLHandshakeException unused25) {
                    map = 0;
                } catch (SSLPeerUnverifiedException unused26) {
                    map = 0;
                } catch (IOException unused27) {
                    map = 0;
                } catch (Throwable th2) {
                    th = th2;
                    map = 0;
                }
            } catch (a unused28) {
                str = 0;
                map = 0;
            } catch (SecurityException unused29) {
                str = 0;
                map = 0;
            } catch (ConnectException unused30) {
                str = 0;
                map = 0;
            } catch (UnknownHostException unused31) {
                str = 0;
                map = 0;
            } catch (SSLHandshakeException unused32) {
                str = 0;
                map = 0;
            } catch (SSLPeerUnverifiedException unused33) {
                str = 0;
                map = 0;
            } catch (IOException unused34) {
                str = 0;
                map = 0;
            } catch (Throwable th3) {
                th = th3;
                str = 0;
                map = 0;
            }
        } catch (Throwable th4) {
            th = th4;
        }
    }

    public static HttpURLConnection a(String str, int i2, Map<String, String> map, String str2) throws ProtocolException, IllegalArgumentException, a {
        if (TextUtils.isEmpty(str)) {
            y.b("hmsSdk", "CreateConnection: invalid urlPath.");
            return null;
        }
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
        a(httpURLConnection);
        httpURLConnection.setRequestMethod(str2);
        httpURLConnection.setConnectTimeout(15000);
        httpURLConnection.setReadTimeout(15000);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        httpURLConnection.setRequestProperty("Content-Length", String.valueOf(i2));
        httpURLConnection.setRequestProperty("Connection", "close");
        if (map != null && map.size() >= 1) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                if (key != null && !TextUtils.isEmpty(key)) {
                    httpURLConnection.setRequestProperty(key, entry.getValue());
                }
            }
        }
        return httpURLConnection;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0025  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0031  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void a(java.net.HttpURLConnection r2) throws java.lang.IllegalArgumentException, com.huawei.hms.hatool.a0.a {
        /*
            java.lang.String r0 = "hmsSdk"
            boolean r1 = r2 instanceof javax.net.ssl.HttpsURLConnection
            if (r1 == 0) goto L39
            javax.net.ssl.HttpsURLConnection r2 = (javax.net.ssl.HttpsURLConnection) r2
            android.content.Context r1 = com.huawei.hms.hatool.b.i()     // Catch: java.lang.IllegalAccessException -> L11 java.io.IOException -> L14 java.security.GeneralSecurityException -> L17 java.security.KeyStoreException -> L1a java.security.NoSuchAlgorithmException -> L1d
            com.huawei.secure.android.common.ssl.SecureSSLSocketFactory r0 = com.huawei.secure.android.common.ssl.SecureSSLSocketFactory.getInstance(r1)     // Catch: java.lang.IllegalAccessException -> L11 java.io.IOException -> L14 java.security.GeneralSecurityException -> L17 java.security.KeyStoreException -> L1a java.security.NoSuchAlgorithmException -> L1d
            goto L23
        L11:
            java.lang.String r1 = "getSocketFactory(): Illegal Access Exception "
            goto L1f
        L14:
            java.lang.String r1 = "getSocketFactory(): IO Exception!"
            goto L1f
        L17:
            java.lang.String r1 = "getSocketFactory(): General Security Exception"
            goto L1f
        L1a:
            java.lang.String r1 = "getSocketFactory(): Key Store exception"
            goto L1f
        L1d:
            java.lang.String r1 = "getSocketFactory(): Algorithm Exception!"
        L1f:
            com.huawei.hms.hatool.y.f(r0, r1)
            r0 = 0
        L23:
            if (r0 == 0) goto L31
            r2.setSSLSocketFactory(r0)
            com.huawei.secure.android.common.ssl.hostname.StrictHostnameVerifier r0 = new com.huawei.secure.android.common.ssl.hostname.StrictHostnameVerifier
            r0.<init>()
            r2.setHostnameVerifier(r0)
            goto L39
        L31:
            com.huawei.hms.hatool.a0$a r2 = new com.huawei.hms.hatool.a0$a
            java.lang.String r0 = "No ssl socket factory set"
            r2.<init>(r0)
            throw r2
        L39:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.hatool.a0.a(java.net.HttpURLConnection):void");
    }

    public static String b(HttpURLConnection httpURLConnection) throws IOException {
        InputStream inputStream = null;
        try {
            try {
                inputStream = httpURLConnection.getInputStream();
                return t0.a(inputStream);
            } catch (IOException unused) {
                y.f("hmsSdk", "When Response Content From Connection inputStream operation exception! " + httpURLConnection.getResponseCode());
                t0.a((Closeable) inputStream);
                return "";
            }
        } finally {
            t0.a((Closeable) inputStream);
        }
    }
}
