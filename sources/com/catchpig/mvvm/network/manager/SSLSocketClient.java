package com.catchpig.mvvm.network.manager;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f8F¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0010"}, d2 = {"Lcom/catchpig/mvvm/network/manager/SSLSocketClient;", "", "()V", "hostnameVerifier", "Ljavax/net/ssl/HostnameVerifier;", "getHostnameVerifier", "()Ljavax/net/ssl/HostnameVerifier;", "ssLSocketFactory", "Ljavax/net/ssl/SSLSocketFactory;", "getSsLSocketFactory", "()Ljavax/net/ssl/SSLSocketFactory;", "trustManager", "", "Ljavax/net/ssl/X509TrustManager;", "getTrustManager", "()[Ljavax/net/ssl/X509TrustManager;", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class SSLSocketClient {

    @NotNull
    public static final SSLSocketClient INSTANCE = new SSLSocketClient();

    private SSLSocketClient() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean _get_hostnameVerifier_$lambda$0(String str, SSLSession sSLSession) {
        return true;
    }

    @NotNull
    public final HostnameVerifier getHostnameVerifier() {
        return new HostnameVerifier() { // from class: com.catchpig.mvvm.network.manager.b
            @Override // javax.net.ssl.HostnameVerifier
            public final boolean verify(String str, SSLSession sSLSession) {
                return SSLSocketClient._get_hostnameVerifier_$lambda$0(str, sSLSession);
            }
        };
    }

    @NotNull
    public final SSLSocketFactory getSsLSocketFactory() throws NoSuchAlgorithmException, KeyManagementException {
        try {
            SSLContext sSLContext = SSLContext.getInstance("SSL");
            sSLContext.init(null, getTrustManager(), new SecureRandom());
            SSLSocketFactory socketFactory = sSLContext.getSocketFactory();
            Intrinsics.checkNotNullExpressionValue(socketFactory, "{\n            val sslCon…t.socketFactory\n        }");
            return socketFactory;
        } catch (Exception e2) {
            throw new RuntimeException(e2);
        }
    }

    @NotNull
    public final X509TrustManager[] getTrustManager() {
        return new X509TrustManager[]{new X509TrustManager() { // from class: com.catchpig.mvvm.network.manager.SSLSocketClient$trustManager$1
            @Override // javax.net.ssl.X509TrustManager
            public void checkClientTrusted(@Nullable X509Certificate[] chain, @Nullable String authType) {
            }

            @Override // javax.net.ssl.X509TrustManager
            public void checkServerTrusted(@Nullable X509Certificate[] chain, @Nullable String authType) {
            }

            @Override // javax.net.ssl.X509TrustManager
            @NotNull
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }};
    }
}
