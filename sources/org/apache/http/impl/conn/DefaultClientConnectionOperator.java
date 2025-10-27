package org.apache.http.impl.conn;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpHost;
import org.apache.http.annotation.ThreadSafe;
import org.apache.http.conn.ClientConnectionOperator;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.conn.OperatedClientConnection;
import org.apache.http.conn.scheme.LayeredSchemeSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

@ThreadSafe
/* loaded from: classes9.dex */
public class DefaultClientConnectionOperator implements ClientConnectionOperator {
    private final Log log = LogFactory.getLog(getClass());
    protected final SchemeRegistry schemeRegistry;

    public DefaultClientConnectionOperator(SchemeRegistry schemeRegistry) {
        if (schemeRegistry == null) {
            throw new IllegalArgumentException("Scheme registry amy not be null");
        }
        this.schemeRegistry = schemeRegistry;
    }

    @Override // org.apache.http.conn.ClientConnectionOperator
    public OperatedClientConnection createConnection() {
        return new DefaultClientConnection();
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x00a7  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00c7 A[SYNTHETIC] */
    @Override // org.apache.http.conn.ClientConnectionOperator
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void openConnection(org.apache.http.conn.OperatedClientConnection r17, org.apache.http.HttpHost r18, java.net.InetAddress r19, org.apache.http.protocol.HttpContext r20, org.apache.http.params.HttpParams r21) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 243
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.impl.conn.DefaultClientConnectionOperator.openConnection(org.apache.http.conn.OperatedClientConnection, org.apache.http.HttpHost, java.net.InetAddress, org.apache.http.protocol.HttpContext, org.apache.http.params.HttpParams):void");
    }

    public void prepareSocket(Socket socket, HttpContext httpContext, HttpParams httpParams) throws IOException {
        socket.setTcpNoDelay(HttpConnectionParams.getTcpNoDelay(httpParams));
        socket.setSoTimeout(HttpConnectionParams.getSoTimeout(httpParams));
        int linger = HttpConnectionParams.getLinger(httpParams);
        if (linger >= 0) {
            socket.setSoLinger(linger > 0, linger);
        }
    }

    public InetAddress[] resolveHostname(String str) throws UnknownHostException {
        return InetAddress.getAllByName(str);
    }

    @Override // org.apache.http.conn.ClientConnectionOperator
    public void updateSecureConnection(OperatedClientConnection operatedClientConnection, HttpHost httpHost, HttpContext httpContext, HttpParams httpParams) throws IOException {
        if (operatedClientConnection == null) {
            throw new IllegalArgumentException("Connection may not be null");
        }
        if (httpHost == null) {
            throw new IllegalArgumentException("Target host may not be null");
        }
        if (httpParams == null) {
            throw new IllegalArgumentException("Parameters may not be null");
        }
        if (!operatedClientConnection.isOpen()) {
            throw new IllegalStateException("Connection must be open");
        }
        Scheme scheme = this.schemeRegistry.getScheme(httpHost.getSchemeName());
        if (!(scheme.getSchemeSocketFactory() instanceof LayeredSchemeSocketFactory)) {
            throw new IllegalArgumentException("Target scheme (" + scheme.getName() + ") must have layered socket factory.");
        }
        LayeredSchemeSocketFactory layeredSchemeSocketFactory = (LayeredSchemeSocketFactory) scheme.getSchemeSocketFactory();
        try {
            Socket socketCreateLayeredSocket = layeredSchemeSocketFactory.createLayeredSocket(operatedClientConnection.getSocket(), httpHost.getHostName(), httpHost.getPort(), true);
            prepareSocket(socketCreateLayeredSocket, httpContext, httpParams);
            operatedClientConnection.update(socketCreateLayeredSocket, httpHost, layeredSchemeSocketFactory.isSecure(socketCreateLayeredSocket), httpParams);
        } catch (ConnectException e2) {
            throw new HttpHostConnectException(httpHost, e2);
        }
    }
}
