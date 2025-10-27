package com.ykb.ebook.network;

import android.annotation.SuppressLint;
import com.plv.foundationsdk.log.elog.logcode.socket.PLVErrorCodeSocketBase;
import java.net.Socket;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.X509ExtendedTrustManager;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J#\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\tH\u0016¢\u0006\u0002\u0010\nJ+\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\fH\u0016¢\u0006\u0002\u0010\rJ+\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016¢\u0006\u0002\u0010\u0010J#\u0010\u0011\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\tH\u0016¢\u0006\u0002\u0010\nJ+\u0010\u0011\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\fH\u0016¢\u0006\u0002\u0010\rJ+\u0010\u0011\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016¢\u0006\u0002\u0010\u0010J\u0013\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\u0013¨\u0006\u0014"}, d2 = {"Lcom/ykb/ebook/network/MineX509;", "Ljavax/net/ssl/X509ExtendedTrustManager;", "()V", "checkClientTrusted", "", "chain", "", "Ljava/security/cert/X509Certificate;", "authType", "", "([Ljava/security/cert/X509Certificate;Ljava/lang/String;)V", PLVErrorCodeSocketBase.MODULE_NAME, "Ljava/net/Socket;", "([Ljava/security/cert/X509Certificate;Ljava/lang/String;Ljava/net/Socket;)V", "engine", "Ljavax/net/ssl/SSLEngine;", "([Ljava/security/cert/X509Certificate;Ljava/lang/String;Ljavax/net/ssl/SSLEngine;)V", "checkServerTrusted", "getAcceptedIssuers", "()[Ljava/security/cert/X509Certificate;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SuppressLint({"TrustAllX509TrustManager", "CustomX509TrustManager"})
/* loaded from: classes7.dex */
public final class MineX509 extends X509ExtendedTrustManager {
    @Override // javax.net.ssl.X509TrustManager
    public void checkClientTrusted(@NotNull X509Certificate[] chain, @NotNull String authType) {
        Intrinsics.checkNotNullParameter(chain, "chain");
        Intrinsics.checkNotNullParameter(authType, "authType");
    }

    @Override // javax.net.ssl.X509ExtendedTrustManager
    public void checkClientTrusted(@NotNull X509Certificate[] chain, @NotNull String authType, @NotNull Socket socket) {
        Intrinsics.checkNotNullParameter(chain, "chain");
        Intrinsics.checkNotNullParameter(authType, "authType");
        Intrinsics.checkNotNullParameter(socket, "socket");
    }

    @Override // javax.net.ssl.X509ExtendedTrustManager
    public void checkClientTrusted(@NotNull X509Certificate[] chain, @NotNull String authType, @NotNull SSLEngine engine) {
        Intrinsics.checkNotNullParameter(chain, "chain");
        Intrinsics.checkNotNullParameter(authType, "authType");
        Intrinsics.checkNotNullParameter(engine, "engine");
    }

    @Override // javax.net.ssl.X509TrustManager
    public void checkServerTrusted(@NotNull X509Certificate[] chain, @NotNull String authType) {
        Intrinsics.checkNotNullParameter(chain, "chain");
        Intrinsics.checkNotNullParameter(authType, "authType");
    }

    @Override // javax.net.ssl.X509ExtendedTrustManager
    public void checkServerTrusted(@NotNull X509Certificate[] chain, @NotNull String authType, @NotNull Socket socket) {
        Intrinsics.checkNotNullParameter(chain, "chain");
        Intrinsics.checkNotNullParameter(authType, "authType");
        Intrinsics.checkNotNullParameter(socket, "socket");
    }

    @Override // javax.net.ssl.X509ExtendedTrustManager
    public void checkServerTrusted(@NotNull X509Certificate[] chain, @NotNull String authType, @NotNull SSLEngine engine) {
        Intrinsics.checkNotNullParameter(chain, "chain");
        Intrinsics.checkNotNullParameter(authType, "authType");
        Intrinsics.checkNotNullParameter(engine, "engine");
    }

    @Override // javax.net.ssl.X509TrustManager
    @NotNull
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }
}
