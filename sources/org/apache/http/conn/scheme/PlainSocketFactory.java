package org.apache.http.conn.scheme;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import org.apache.http.annotation.Immutable;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

@Immutable
/* loaded from: classes9.dex */
public class PlainSocketFactory implements SocketFactory, SchemeSocketFactory {
    private final HostNameResolver nameResolver;

    @Deprecated
    public PlainSocketFactory(HostNameResolver hostNameResolver) {
        this.nameResolver = hostNameResolver;
    }

    public static PlainSocketFactory getSocketFactory() {
        return new PlainSocketFactory();
    }

    @Override // org.apache.http.conn.scheme.SchemeSocketFactory
    public Socket connectSocket(Socket socket, InetSocketAddress inetSocketAddress, InetSocketAddress inetSocketAddress2, HttpParams httpParams) throws IOException {
        if (inetSocketAddress == null) {
            throw new IllegalArgumentException("Remote address may not be null");
        }
        if (httpParams == null) {
            throw new IllegalArgumentException("HTTP parameters may not be null");
        }
        if (socket == null) {
            socket = createSocket();
        }
        if (inetSocketAddress2 != null) {
            socket.setReuseAddress(HttpConnectionParams.getSoReuseaddr(httpParams));
            socket.bind(inetSocketAddress2);
        }
        int connectionTimeout = HttpConnectionParams.getConnectionTimeout(httpParams);
        try {
            socket.setSoTimeout(HttpConnectionParams.getSoTimeout(httpParams));
            socket.connect(inetSocketAddress, connectionTimeout);
            return socket;
        } catch (SocketTimeoutException unused) {
            throw new ConnectTimeoutException("Connect to " + inetSocketAddress + " timed out");
        }
    }

    @Override // org.apache.http.conn.scheme.SchemeSocketFactory
    public Socket createSocket(HttpParams httpParams) {
        return new Socket();
    }

    @Override // org.apache.http.conn.scheme.SocketFactory
    public final boolean isSecure(Socket socket) throws IllegalArgumentException {
        if (socket == null) {
            throw new IllegalArgumentException("Socket may not be null.");
        }
        if (socket.isClosed()) {
            throw new IllegalArgumentException("Socket is closed.");
        }
        return false;
    }

    @Override // org.apache.http.conn.scheme.SocketFactory
    public Socket createSocket() {
        return new Socket();
    }

    public PlainSocketFactory() {
        this.nameResolver = null;
    }

    @Override // org.apache.http.conn.scheme.SocketFactory
    @Deprecated
    public Socket connectSocket(Socket socket, String str, int i2, InetAddress inetAddress, int i3, HttpParams httpParams) throws IOException {
        InetSocketAddress inetSocketAddress;
        InetAddress byName;
        if (inetAddress != null || i3 > 0) {
            if (i3 < 0) {
                i3 = 0;
            }
            inetSocketAddress = new InetSocketAddress(inetAddress, i3);
        } else {
            inetSocketAddress = null;
        }
        HostNameResolver hostNameResolver = this.nameResolver;
        if (hostNameResolver != null) {
            byName = hostNameResolver.resolve(str);
        } else {
            byName = InetAddress.getByName(str);
        }
        return connectSocket(socket, new InetSocketAddress(byName, i2), inetSocketAddress, httpParams);
    }
}
