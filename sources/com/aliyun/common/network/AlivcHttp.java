package com.aliyun.common.network;

import androidx.annotation.Keep;
import com.just.agentweb.DefaultWebClient;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

@Keep
/* loaded from: classes2.dex */
public class AlivcHttp {

    public class a implements X509TrustManager {
        @Override // javax.net.ssl.X509TrustManager
        public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
        }

        @Override // javax.net.ssl.X509TrustManager
        public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
        }

        @Override // javax.net.ssl.X509TrustManager
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    public class b implements HostnameVerifier {
        @Override // javax.net.ssl.HostnameVerifier
        public boolean verify(String str, SSLSession sSLSession) {
            return true;
        }
    }

    public static boolean isHttps(AlivcHttpRequest alivcHttpRequest) {
        return alivcHttpRequest.getUrl().startsWith(DefaultWebClient.HTTPS_SCHEME);
    }

    public static boolean needOutput(AlivcHttpRequest alivcHttpRequest) {
        return ((alivcHttpRequest != null) && ("POST".equalsIgnoreCase(alivcHttpRequest.getMethod()) || "PUT".equalsIgnoreCase(alivcHttpRequest.getMethod()))) && alivcHttpRequest.getBody() != null && alivcHttpRequest.getBody().length > 0;
    }

    private static String readStream(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return "";
        }
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        byte[] bArr = new byte[4096];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while (true) {
            int i2 = bufferedInputStream.read(bArr);
            if (i2 == -1) {
                return byteArrayOutputStream.toString();
            }
            byteArrayOutputStream.write(bArr, 0, i2);
        }
    }

    /*  JADX ERROR: Type inference failed
        jadx.core.utils.exceptions.JadxOverflowException: Type inference error: updates count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:77)
        */
    /* JADX WARN: Not initialized variable reg: 12, insn: 0x0181: MOVE (r18 I:??[long, double]) = (r12 I:??[long, double]), block:B:82:0x017f */
    /* JADX WARN: Not initialized variable reg: 12, insn: 0x018c: MOVE (r18 I:??[long, double]) = (r12 I:??[long, double]), block:B:84:0x018a */
    /* JADX WARN: Not initialized variable reg: 12, insn: 0x0197: MOVE (r18 I:??[long, double]) = (r12 I:??[long, double]), block:B:86:0x0195 */
    /* JADX WARN: Not initialized variable reg: 12, insn: 0x01a2: MOVE (r18 I:??[long, double]) = (r12 I:??[long, double]), block:B:88:0x01a0 */
    /* JADX WARN: Not initialized variable reg: 12, insn: 0x01ad: MOVE (r18 I:??[long, double]) = (r12 I:??[long, double]), block:B:90:0x01ab */
    public static com.aliyun.common.network.AlivcHttpResponse request(com.aliyun.common.network.AlivcHttpRequest r22) {
        /*
            Method dump skipped, instructions count: 649
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aliyun.common.network.AlivcHttp.request(com.aliyun.common.network.AlivcHttpRequest):com.aliyun.common.network.AlivcHttpResponse");
    }

    private static void safeClose(Closeable closeable) throws IOException {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException unused) {
            }
        }
    }

    public static void skipSSLVerification(HttpsURLConnection httpsURLConnection) throws NoSuchAlgorithmException, KeyManagementException {
        TrustManager[] trustManagerArr = {new a()};
        SSLContext sSLContext = SSLContext.getInstance("TLS");
        sSLContext.init(null, trustManagerArr, new SecureRandom());
        httpsURLConnection.setSSLSocketFactory(sSLContext.getSocketFactory());
        httpsURLConnection.setHostnameVerifier(new b());
    }

    public static boolean trustAllSSLCert(AlivcHttpRequest alivcHttpRequest) {
        return alivcHttpRequest.isTrustAllSSLCert();
    }
}
