package com.ykb.ebook.network;

import com.alipay.sdk.cons.c;
import java.net.InetAddress;
import java.net.Socket;
import javax.net.ssl.SSLSocketFactory;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J*\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\t\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\bH\u0016J*\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\f\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\r2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u001a\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\r2\u0006\u0010\u0007\u001a\u00020\bH\u0016J*\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\r2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\bH\u0016J\u0013\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\r0\u0012H\u0016¢\u0006\u0002\u0010\u0013J\u0013\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\r0\u0012H\u0016¢\u0006\u0002\u0010\u0013¨\u0006\u0015"}, d2 = {"Lcom/ykb/ebook/network/MineFactory;", "Ljavax/net/ssl/SSLSocketFactory;", "()V", "createSocket", "Ljava/net/Socket;", c.f3231f, "Ljava/net/InetAddress;", "port", "", "address", "localAddress", "localPort", "s", "", "autoClose", "", "localHost", "getDefaultCipherSuites", "", "()[Ljava/lang/String;", "getSupportedCipherSuites", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class MineFactory extends SSLSocketFactory {
    @Override // javax.net.SocketFactory
    @Nullable
    public Socket createSocket(@NotNull String host, int port) {
        Intrinsics.checkNotNullParameter(host, "host");
        return null;
    }

    @Override // javax.net.SocketFactory
    @Nullable
    public Socket createSocket(@NotNull String host, int port, @NotNull InetAddress localHost, int localPort) {
        Intrinsics.checkNotNullParameter(host, "host");
        Intrinsics.checkNotNullParameter(localHost, "localHost");
        return null;
    }

    @Override // javax.net.SocketFactory
    @Nullable
    public Socket createSocket(@NotNull InetAddress host, int port) {
        Intrinsics.checkNotNullParameter(host, "host");
        return null;
    }

    @Override // javax.net.SocketFactory
    @Nullable
    public Socket createSocket(@NotNull InetAddress address, int port, @NotNull InetAddress localAddress, int localPort) {
        Intrinsics.checkNotNullParameter(address, "address");
        Intrinsics.checkNotNullParameter(localAddress, "localAddress");
        return null;
    }

    @Override // javax.net.ssl.SSLSocketFactory
    @Nullable
    public Socket createSocket(@NotNull Socket s2, @NotNull String host, int port, boolean autoClose) {
        Intrinsics.checkNotNullParameter(s2, "s");
        Intrinsics.checkNotNullParameter(host, "host");
        return null;
    }

    @Override // javax.net.ssl.SSLSocketFactory
    @NotNull
    public String[] getDefaultCipherSuites() {
        return new String[0];
    }

    @Override // javax.net.ssl.SSLSocketFactory
    @NotNull
    public String[] getSupportedCipherSuites() {
        return new String[0];
    }
}
