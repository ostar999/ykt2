package com.psychiatrygarden.widget.glideUtil.progress;

import android.text.TextUtils;
import com.psychiatrygarden.widget.glideUtil.progress.ProgressResponseBody;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;

/* loaded from: classes6.dex */
public class ProgressManager {
    private static OkHttpClient okHttpClient;
    private static Map<String, OnProgressListener> listenersMap = Collections.synchronizedMap(new HashMap());
    private static final ProgressResponseBody.InternalProgressListener LISTENER = new ProgressResponseBody.InternalProgressListener() { // from class: com.psychiatrygarden.widget.glideUtil.progress.a
        @Override // com.psychiatrygarden.widget.glideUtil.progress.ProgressResponseBody.InternalProgressListener
        public final void onProgress(String str, long j2, long j3) {
            ProgressManager.lambda$static$1(str, j2, j3);
        }
    };

    private ProgressManager() {
    }

    public static void addListener(String url, OnProgressListener listener) {
        if (TextUtils.isEmpty(url) || listener == null) {
            return;
        }
        listenersMap.put(url, listener);
        listener.onProgress(false, 1, 0L, 0L);
    }

    public static OkHttpClient getOkHttpClient() throws NoSuchAlgorithmException, KeyManagementException {
        OkHttpClient okHttpClient2 = okHttpClient;
        if (okHttpClient2 != null) {
            return okHttpClient2;
        }
        try {
            TrustManager[] trustManagerArr = {new X509TrustManager() { // from class: com.psychiatrygarden.widget.glideUtil.progress.ProgressManager.1
                @Override // javax.net.ssl.X509TrustManager
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override // javax.net.ssl.X509TrustManager
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override // javax.net.ssl.X509TrustManager
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }};
            SSLContext sSLContext = SSLContext.getInstance("TLS");
            sSLContext.init(null, trustManagerArr, new SecureRandom());
            OkHttpClient.Builder builderHostnameVerifier = new OkHttpClient.Builder().protocols(Collections.singletonList(Protocol.HTTP_1_1)).addNetworkInterceptor(new Interceptor() { // from class: com.psychiatrygarden.widget.glideUtil.progress.b
                @Override // okhttp3.Interceptor
                public final Response intercept(Interceptor.Chain chain) {
                    return ProgressManager.lambda$getOkHttpClient$0(chain);
                }
            }).sslSocketFactory(sSLContext.getSocketFactory(), (X509TrustManager) trustManagerArr[0]).hostnameVerifier(new HostnameVerifier() { // from class: com.psychiatrygarden.widget.glideUtil.progress.ProgressManager.2
                @Override // javax.net.ssl.HostnameVerifier
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            TimeUnit timeUnit = TimeUnit.SECONDS;
            OkHttpClient okHttpClientBuild = builderHostnameVerifier.connectTimeout(60L, timeUnit).readTimeout(60L, timeUnit).writeTimeout(60L, timeUnit).retryOnConnectionFailure(false).build();
            okHttpClient = okHttpClientBuild;
            return okHttpClientBuild;
        } catch (Exception e2) {
            throw new RuntimeException(e2);
        }
    }

    public static OnProgressListener getProgressListener(String url) {
        Map<String, OnProgressListener> map;
        OnProgressListener onProgressListener;
        if (TextUtils.isEmpty(url) || (map = listenersMap) == null || map.size() == 0 || (onProgressListener = listenersMap.get(url)) == null) {
            return null;
        }
        return onProgressListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Response lambda$getOkHttpClient$0(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        Response responseProceed = chain.proceed(request);
        return responseProceed.newBuilder().addHeader("Connection", "close").body(new ProgressResponseBody(request.url().getUrl(), LISTENER, responseProceed.body())).build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$static$1(String str, long j2, long j3) {
        OnProgressListener progressListener = getProgressListener(str);
        if (progressListener != null) {
            int i2 = (int) (((j2 * 1.0f) / j3) * 100.0f);
            boolean z2 = i2 >= 100;
            progressListener.onProgress(z2, i2, j2, j3);
            if (z2) {
                removeListener(str);
            }
        }
    }

    public static void removeListener(String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        listenersMap.remove(url);
    }
}
