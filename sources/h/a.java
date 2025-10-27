package h;

import android.net.Uri;
import android.util.Log;
import d.b;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/* loaded from: classes8.dex */
public class a {

    /* renamed from: c, reason: collision with root package name */
    public static final String f27008c = "HttpConnection";

    /* renamed from: d, reason: collision with root package name */
    public static final String f27009d = "@#&=*+-_.,:!?()/~'%";

    /* renamed from: e, reason: collision with root package name */
    public static final String f27010e = "UTF-8";

    /* renamed from: f, reason: collision with root package name */
    public static final int f27011f = 5000;

    /* renamed from: g, reason: collision with root package name */
    public static final int f27012g = 10000;

    /* renamed from: h, reason: collision with root package name */
    public static a f27013h;

    /* renamed from: a, reason: collision with root package name */
    public TrustManager[] f27014a = {new C0451a()};

    /* renamed from: b, reason: collision with root package name */
    public HostnameVerifier f27015b = new b();

    /* renamed from: h.a$a, reason: collision with other inner class name */
    public class C0451a implements X509TrustManager {
        public C0451a() {
        }

        @Override // javax.net.ssl.X509TrustManager
        public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) {
        }

        @Override // javax.net.ssl.X509TrustManager
        public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateNotYetValidException, CertificateExpiredException {
            c.h.a(a.f27008c, "authType tls HttpConnection: " + str + "verify: " + b.a.f26736v);
            if (!b.a.f26736v) {
                c.h.a(a.f27008c, "tls verify in HttpConnection closed: ");
                return;
            }
            if (x509CertificateArr == null) {
                throw new IllegalArgumentException("Check Server x509Certificates is null");
            }
            if (x509CertificateArr.length <= 0) {
                throw new IllegalArgumentException("Check Server x509Certificates is empty");
            }
            for (X509Certificate x509Certificate : x509CertificateArr) {
                x509Certificate.checkValidity();
            }
        }

        @Override // javax.net.ssl.X509TrustManager
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }

    public class b implements HostnameVerifier {
        public b() {
        }

        @Override // javax.net.ssl.HostnameVerifier
        public boolean verify(String str, SSLSession sSLSession) {
            c.h.a(a.f27008c, "HostnameVerifier certificate for " + str);
            if (!b.a.f26736v) {
                Log.d(a.f27008c, "verify host name closed");
                return true;
            }
            Boolean boolValueOf = Boolean.valueOf(HttpsURLConnection.getDefaultHostnameVerifier().verify("*.urtc.com.cn", sSLSession));
            c.h.a(a.f27008c, "host name  result :" + boolValueOf);
            return boolValueOf.booleanValue();
        }
    }

    public class c implements HostnameVerifier {
        public c() {
        }

        @Override // javax.net.ssl.HostnameVerifier
        public boolean verify(String str, SSLSession sSLSession) {
            return true;
        }
    }

    public a() throws NoSuchAlgorithmException, KeyManagementException {
        try {
            SSLContext sSLContext = SSLContext.getInstance("TLS");
            sSLContext.init(null, this.f27014a, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sSLContext.getSocketFactory());
        } catch (KeyManagementException e2) {
            e2.printStackTrace();
        } catch (NoSuchAlgorithmException e3) {
            e3.printStackTrace();
        }
    }

    public static a b() {
        if (f27013h == null) {
            synchronized (a.class) {
                if (f27013h == null) {
                    f27013h = new a();
                }
            }
        }
        return f27013h;
    }

    public HttpsURLConnection a(String str) throws IOException {
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) new URL(Uri.encode(str, f27009d)).openConnection();
        httpsURLConnection.setConnectTimeout(5000);
        httpsURLConnection.setReadTimeout(10000);
        httpsURLConnection.setHostnameVerifier(this.f27015b);
        return httpsURLConnection;
    }

    public void a() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0048 A[EXC_TOP_SPLITTER, PHI: r0 r6
      0x0048: PHI (r0v5 java.lang.String) = (r0v0 java.lang.String), (r0v9 java.lang.String) binds: [B:28:0x0046, B:11:0x0027] A[DONT_GENERATE, DONT_INLINE]
      0x0048: PHI (r6v6 ??) = (r6v5 ??), (r6v7 ??) binds: [B:28:0x0046, B:11:0x0027] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r6v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r6v2 */
    /* JADX WARN: Type inference failed for: r6v3 */
    /* JADX WARN: Type inference failed for: r6v4, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r6v5 */
    /* JADX WARN: Type inference failed for: r6v6, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r6v7, types: [java.io.InputStream] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String c(java.lang.String r6) throws java.lang.Throwable {
        /*
            r5 = this;
            r0 = 0
            java.io.InputStream r6 = r5.b(r6)     // Catch: java.lang.Throwable -> L36 java.io.IOException -> L3b
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L2e java.io.IOException -> L33
            java.io.InputStreamReader r2 = new java.io.InputStreamReader     // Catch: java.lang.Throwable -> L2e java.io.IOException -> L33
            java.lang.String r3 = "UTF-8"
            r2.<init>(r6, r3)     // Catch: java.lang.Throwable -> L2e java.io.IOException -> L33
            r1.<init>(r2)     // Catch: java.lang.Throwable -> L2e java.io.IOException -> L33
            java.lang.StringBuffer r2 = new java.lang.StringBuffer     // Catch: java.lang.Throwable -> L2a java.io.IOException -> L2c
            r2.<init>()     // Catch: java.lang.Throwable -> L2a java.io.IOException -> L2c
        L16:
            java.lang.String r3 = r1.readLine()     // Catch: java.lang.Throwable -> L2a java.io.IOException -> L2c
            if (r3 == 0) goto L20
            r2.append(r3)     // Catch: java.lang.Throwable -> L2a java.io.IOException -> L2c
            goto L16
        L20:
            java.lang.String r0 = r2.toString()     // Catch: java.lang.Throwable -> L2a java.io.IOException -> L2c
            r1.close()     // Catch: java.io.IOException -> L27
        L27:
            if (r6 == 0) goto L4b
            goto L48
        L2a:
            r0 = move-exception
            goto L4c
        L2c:
            r2 = move-exception
            goto L3e
        L2e:
            r1 = move-exception
            r4 = r1
            r1 = r0
            r0 = r4
            goto L4c
        L33:
            r2 = move-exception
            r1 = r0
            goto L3e
        L36:
            r6 = move-exception
            r1 = r0
            r0 = r6
            r6 = r1
            goto L4c
        L3b:
            r2 = move-exception
            r6 = r0
            r1 = r6
        L3e:
            r2.printStackTrace()     // Catch: java.lang.Throwable -> L2a
            if (r1 == 0) goto L46
            r1.close()     // Catch: java.io.IOException -> L46
        L46:
            if (r6 == 0) goto L4b
        L48:
            r6.close()     // Catch: java.io.IOException -> L4b
        L4b:
            return r0
        L4c:
            if (r1 == 0) goto L51
            r1.close()     // Catch: java.io.IOException -> L51
        L51:
            if (r6 == 0) goto L56
            r6.close()     // Catch: java.io.IOException -> L56
        L56:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: h.a.c(java.lang.String):java.lang.String");
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x0099  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0094 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x00b0 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:68:0x008f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00ab A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x00a6 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:91:? A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String a(java.lang.String r6, java.lang.String r7) throws java.lang.Throwable {
        /*
            r5 = this;
            r0 = 0
            javax.net.ssl.HttpsURLConnection r6 = r5.a(r6)     // Catch: java.lang.Throwable -> L82 java.lang.Exception -> L86
            java.lang.String r1 = "POST"
            r6.setRequestMethod(r1)     // Catch: java.lang.Throwable -> L82 java.lang.Exception -> L86
            r1 = 1
            r6.setDoOutput(r1)     // Catch: java.lang.Throwable -> L82 java.lang.Exception -> L86
            r6.setDoInput(r1)     // Catch: java.lang.Throwable -> L82 java.lang.Exception -> L86
            r1 = 0
            r6.setUseCaches(r1)     // Catch: java.lang.Throwable -> L82 java.lang.Exception -> L86
            java.lang.String r1 = "Content-Type"
            java.lang.String r2 = "application/json;charset=utf-8"
            r6.setRequestProperty(r1, r2)     // Catch: java.lang.Throwable -> L82 java.lang.Exception -> L86
            java.lang.String r1 = "UTF-8"
            if (r7 == 0) goto L37
            java.io.OutputStream r2 = r6.getOutputStream()     // Catch: java.lang.Throwable -> L82 java.lang.Exception -> L86
            java.io.DataOutputStream r3 = new java.io.DataOutputStream     // Catch: java.lang.Throwable -> L7a java.lang.Exception -> L7e
            r3.<init>(r2)     // Catch: java.lang.Throwable -> L7a java.lang.Exception -> L7e
            byte[] r7 = r7.getBytes(r1)     // Catch: java.lang.Throwable -> L7a java.lang.Exception -> L7e
            r3.write(r7)     // Catch: java.lang.Throwable -> L7a java.lang.Exception -> L7e
            r3.flush()     // Catch: java.lang.Throwable -> L7a java.lang.Exception -> L7e
            r3.close()     // Catch: java.lang.Throwable -> L7a java.lang.Exception -> L7e
            goto L38
        L37:
            r2 = r0
        L38:
            java.io.InputStream r6 = r6.getInputStream()     // Catch: java.lang.Throwable -> L7a java.lang.Exception -> L7e
            java.io.BufferedReader r7 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L6d java.lang.Exception -> L74
            java.io.InputStreamReader r3 = new java.io.InputStreamReader     // Catch: java.lang.Throwable -> L6d java.lang.Exception -> L74
            r3.<init>(r6, r1)     // Catch: java.lang.Throwable -> L6d java.lang.Exception -> L74
            r7.<init>(r3)     // Catch: java.lang.Throwable -> L6d java.lang.Exception -> L74
            java.lang.StringBuffer r1 = new java.lang.StringBuffer     // Catch: java.lang.Throwable -> L64 java.lang.Exception -> L67
            r1.<init>()     // Catch: java.lang.Throwable -> L64 java.lang.Exception -> L67
        L4b:
            java.lang.String r3 = r7.readLine()     // Catch: java.lang.Throwable -> L64 java.lang.Exception -> L67
            if (r3 == 0) goto L55
            r1.append(r3)     // Catch: java.lang.Throwable -> L64 java.lang.Exception -> L67
            goto L4b
        L55:
            java.lang.String r0 = r1.toString()     // Catch: java.lang.Throwable -> L64 java.lang.Exception -> L67
            if (r2 == 0) goto L5e
            r2.close()     // Catch: java.io.IOException -> L5e
        L5e:
            r7.close()     // Catch: java.io.IOException -> L61
        L61:
            if (r6 == 0) goto L9d
            goto L9a
        L64:
            r0 = move-exception
            goto La4
        L67:
            r1 = move-exception
            r4 = r7
            r7 = r6
            r6 = r1
            r1 = r4
            goto L8a
        L6d:
            r7 = move-exception
            r1 = r0
            r0 = r2
            r4 = r7
            r7 = r6
            r6 = r4
            goto La0
        L74:
            r7 = move-exception
            r1 = r0
            r4 = r7
            r7 = r6
            r6 = r4
            goto L8a
        L7a:
            r6 = move-exception
            r7 = r0
            r1 = r7
            goto L9f
        L7e:
            r6 = move-exception
            r7 = r0
            r1 = r7
            goto L8a
        L82:
            r6 = move-exception
            r7 = r0
            r1 = r7
            goto La0
        L86:
            r6 = move-exception
            r7 = r0
            r1 = r7
            r2 = r1
        L8a:
            r6.printStackTrace()     // Catch: java.lang.Throwable -> L9e
            if (r2 == 0) goto L92
            r2.close()     // Catch: java.io.IOException -> L92
        L92:
            if (r1 == 0) goto L97
            r1.close()     // Catch: java.io.IOException -> L97
        L97:
            if (r7 == 0) goto L9d
            r6 = r7
        L9a:
            r6.close()     // Catch: java.io.IOException -> L9d
        L9d:
            return r0
        L9e:
            r6 = move-exception
        L9f:
            r0 = r2
        La0:
            r2 = r0
            r0 = r6
            r6 = r7
            r7 = r1
        La4:
            if (r2 == 0) goto La9
            r2.close()     // Catch: java.io.IOException -> La9
        La9:
            if (r7 == 0) goto Lae
            r7.close()     // Catch: java.io.IOException -> Lae
        Lae:
            if (r6 == 0) goto Lb3
            r6.close()     // Catch: java.io.IOException -> Lb3
        Lb3:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: h.a.a(java.lang.String, java.lang.String):java.lang.String");
    }

    public InputStream b(String str) throws ProtocolException {
        try {
            HttpsURLConnection httpsURLConnectionA = a(str);
            httpsURLConnectionA.setRequestMethod("GET");
            return httpsURLConnectionA.getInputStream();
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public boolean a(HttpURLConnection httpURLConnection) throws IOException {
        return httpURLConnection.getResponseCode() == 200;
    }
}
