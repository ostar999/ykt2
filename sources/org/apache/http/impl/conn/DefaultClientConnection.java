package org.apache.http.impl.conn;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseFactory;
import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.conn.OperatedClientConnection;
import org.apache.http.impl.SocketHttpClientConnection;
import org.apache.http.io.HttpMessageParser;
import org.apache.http.io.SessionInputBuffer;
import org.apache.http.io.SessionOutputBuffer;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HttpContext;

@NotThreadSafe
/* loaded from: classes9.dex */
public class DefaultClientConnection extends SocketHttpClientConnection implements OperatedClientConnection, HttpContext {
    private boolean connSecure;
    private volatile boolean shutdown;
    private volatile Socket socket;
    private HttpHost targetHost;
    private final Log log = LogFactory.getLog(getClass());
    private final Log headerLog = LogFactory.getLog("org.apache.http.headers");
    private final Log wireLog = LogFactory.getLog("org.apache.http.wire");
    private final Map<String, Object> attributes = new HashMap();

    @Override // org.apache.http.impl.SocketHttpClientConnection, org.apache.http.HttpConnection
    public void close() throws IOException {
        try {
            super.close();
            this.log.debug("Connection closed");
        } catch (IOException e2) {
            this.log.debug("I/O error closing connection", e2);
        }
    }

    @Override // org.apache.http.impl.AbstractHttpClientConnection
    public HttpMessageParser createResponseParser(SessionInputBuffer sessionInputBuffer, HttpResponseFactory httpResponseFactory, HttpParams httpParams) {
        return new DefaultResponseParser(sessionInputBuffer, null, httpResponseFactory, httpParams);
    }

    @Override // org.apache.http.impl.SocketHttpClientConnection
    public SessionInputBuffer createSessionInputBuffer(Socket socket, int i2, HttpParams httpParams) throws IOException {
        if (i2 == -1) {
            i2 = 8192;
        }
        SessionInputBuffer sessionInputBufferCreateSessionInputBuffer = super.createSessionInputBuffer(socket, i2, httpParams);
        return this.wireLog.isDebugEnabled() ? new LoggingSessionInputBuffer(sessionInputBufferCreateSessionInputBuffer, new Wire(this.wireLog), HttpProtocolParams.getHttpElementCharset(httpParams)) : sessionInputBufferCreateSessionInputBuffer;
    }

    @Override // org.apache.http.impl.SocketHttpClientConnection
    public SessionOutputBuffer createSessionOutputBuffer(Socket socket, int i2, HttpParams httpParams) throws IOException {
        if (i2 == -1) {
            i2 = 8192;
        }
        SessionOutputBuffer sessionOutputBufferCreateSessionOutputBuffer = super.createSessionOutputBuffer(socket, i2, httpParams);
        return this.wireLog.isDebugEnabled() ? new LoggingSessionOutputBuffer(sessionOutputBufferCreateSessionOutputBuffer, new Wire(this.wireLog), HttpProtocolParams.getHttpElementCharset(httpParams)) : sessionOutputBufferCreateSessionOutputBuffer;
    }

    @Override // org.apache.http.protocol.HttpContext
    public Object getAttribute(String str) {
        return this.attributes.get(str);
    }

    @Override // org.apache.http.impl.SocketHttpClientConnection, org.apache.http.conn.OperatedClientConnection
    public final Socket getSocket() {
        return this.socket;
    }

    @Override // org.apache.http.conn.OperatedClientConnection
    public final HttpHost getTargetHost() {
        return this.targetHost;
    }

    @Override // org.apache.http.conn.OperatedClientConnection
    public final boolean isSecure() {
        return this.connSecure;
    }

    @Override // org.apache.http.conn.OperatedClientConnection
    public void openCompleted(boolean z2, HttpParams httpParams) throws IOException {
        assertNotOpen();
        if (httpParams == null) {
            throw new IllegalArgumentException("Parameters must not be null.");
        }
        this.connSecure = z2;
        bind(this.socket, httpParams);
    }

    @Override // org.apache.http.conn.OperatedClientConnection
    public void opening(Socket socket, HttpHost httpHost) throws IOException {
        assertNotOpen();
        this.socket = socket;
        this.targetHost = httpHost;
        if (this.shutdown) {
            socket.close();
            throw new IOException("Connection already shutdown");
        }
    }

    @Override // org.apache.http.impl.AbstractHttpClientConnection, org.apache.http.HttpClientConnection
    public HttpResponse receiveResponseHeader() throws HttpException, IllegalStateException, IOException {
        HttpResponse httpResponseReceiveResponseHeader = super.receiveResponseHeader();
        if (this.log.isDebugEnabled()) {
            this.log.debug("Receiving response: " + httpResponseReceiveResponseHeader.getStatusLine());
        }
        if (this.headerLog.isDebugEnabled()) {
            this.headerLog.debug("<< " + httpResponseReceiveResponseHeader.getStatusLine().toString());
            for (Header header : httpResponseReceiveResponseHeader.getAllHeaders()) {
                this.headerLog.debug("<< " + header.toString());
            }
        }
        return httpResponseReceiveResponseHeader;
    }

    @Override // org.apache.http.protocol.HttpContext
    public Object removeAttribute(String str) {
        return this.attributes.remove(str);
    }

    @Override // org.apache.http.impl.AbstractHttpClientConnection, org.apache.http.HttpClientConnection
    public void sendRequestHeader(HttpRequest httpRequest) throws HttpException, IllegalStateException, IOException {
        if (this.log.isDebugEnabled()) {
            this.log.debug("Sending request: " + httpRequest.getRequestLine());
        }
        super.sendRequestHeader(httpRequest);
        if (this.headerLog.isDebugEnabled()) {
            this.headerLog.debug(">> " + httpRequest.getRequestLine().toString());
            for (Header header : httpRequest.getAllHeaders()) {
                this.headerLog.debug(">> " + header.toString());
            }
        }
    }

    @Override // org.apache.http.protocol.HttpContext
    public void setAttribute(String str, Object obj) {
        this.attributes.put(str, obj);
    }

    @Override // org.apache.http.impl.SocketHttpClientConnection, org.apache.http.HttpConnection
    public void shutdown() throws IOException {
        this.shutdown = true;
        try {
            super.shutdown();
            this.log.debug("Connection shut down");
            Socket socket = this.socket;
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e2) {
            this.log.debug("I/O error shutting down connection", e2);
        }
    }

    @Override // org.apache.http.conn.OperatedClientConnection
    public void update(Socket socket, HttpHost httpHost, boolean z2, HttpParams httpParams) throws IOException {
        assertOpen();
        if (httpHost == null) {
            throw new IllegalArgumentException("Target host must not be null.");
        }
        if (httpParams == null) {
            throw new IllegalArgumentException("Parameters must not be null.");
        }
        if (socket != null) {
            this.socket = socket;
            bind(socket, httpParams);
        }
        this.targetHost = httpHost;
        this.connSecure = z2;
    }
}
