package org.eclipse.jetty.client;

import com.yikaobang.yixue.R2;
import java.io.IOException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.SelectConnector;
import org.eclipse.jetty.client.security.Authentication;
import org.eclipse.jetty.client.security.SecurityListener;
import org.eclipse.jetty.http.HttpCookie;
import org.eclipse.jetty.http.HttpHeaderValues;
import org.eclipse.jetty.http.HttpHeaders;
import org.eclipse.jetty.http.HttpMethods;
import org.eclipse.jetty.http.PathMap;
import org.eclipse.jetty.io.Buffer;
import org.eclipse.jetty.io.ByteArrayBuffer;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.util.component.AggregateLifeCycle;
import org.eclipse.jetty.util.component.Dumpable;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes9.dex */
public class HttpDestination implements Dumpable {
    private static final Logger LOG = Log.getLogger((Class<?>) HttpDestination.class);
    private final Address _address;
    private PathMap _authorizations;
    private final HttpClient _client;
    private List<HttpCookie> _cookies;
    private final ByteArrayBuffer _hostHeader;
    private volatile int _maxConnections;
    private volatile int _maxQueueSize;
    private volatile Address _proxy;
    private Authentication _proxyAuthentication;
    private final boolean _ssl;
    private final List<HttpExchange> _exchanges = new LinkedList();
    private final List<AbstractHttpConnection> _connections = new LinkedList();
    private final BlockingQueue<Object> _reservedConnections = new ArrayBlockingQueue(10, true);
    private final List<AbstractHttpConnection> _idleConnections = new ArrayList();
    private int _pendingConnections = 0;
    private int _pendingReservedConnections = 0;

    public class ConnectExchange extends ContentExchange {
        private final SelectConnector.UpgradableEndPoint proxyEndPoint;

        public ConnectExchange(Address address, SelectConnector.UpgradableEndPoint upgradableEndPoint) throws IllegalArgumentException {
            this.proxyEndPoint = upgradableEndPoint;
            setMethod(HttpMethods.CONNECT);
            String string = address.toString();
            setRequestURI(string);
            addRequestHeader("Host", string);
            addRequestHeader(HttpHeaders.PROXY_CONNECTION, HttpHeaderValues.KEEP_ALIVE);
            addRequestHeader("User-Agent", "Jetty-Client");
        }

        @Override // org.eclipse.jetty.client.HttpExchange
        public void onConnectionFailed(Throwable th) throws InterruptedException {
            HttpDestination.this.onConnectionFailed(th);
        }

        @Override // org.eclipse.jetty.client.HttpExchange
        public void onException(Throwable th) {
            HttpExchange httpExchange;
            synchronized (HttpDestination.this) {
                httpExchange = !HttpDestination.this._exchanges.isEmpty() ? (HttpExchange) HttpDestination.this._exchanges.remove(0) : null;
            }
            if (httpExchange == null || !httpExchange.setStatus(9)) {
                return;
            }
            httpExchange.getEventListener().onException(th);
        }

        @Override // org.eclipse.jetty.client.HttpExchange
        public void onExpire() {
            HttpExchange httpExchange;
            synchronized (HttpDestination.this) {
                httpExchange = !HttpDestination.this._exchanges.isEmpty() ? (HttpExchange) HttpDestination.this._exchanges.remove(0) : null;
            }
            if (httpExchange == null || !httpExchange.setStatus(8)) {
                return;
            }
            httpExchange.getEventListener().onExpire();
        }

        @Override // org.eclipse.jetty.client.HttpExchange
        public void onResponseComplete() throws IOException {
            int responseStatus = getResponseStatus();
            if (responseStatus == 200) {
                this.proxyEndPoint.upgrade();
                return;
            }
            if (responseStatus == 504) {
                onExpire();
                return;
            }
            onException(new ProtocolException("Proxy: " + this.proxyEndPoint.getRemoteAddr() + ":" + this.proxyEndPoint.getRemotePort() + " didn't return http return code 200, but " + responseStatus));
        }
    }

    public HttpDestination(HttpClient httpClient, Address address, boolean z2) {
        this._client = httpClient;
        this._address = address;
        this._ssl = z2;
        this._maxConnections = httpClient.getMaxConnectionsPerAddress();
        this._maxQueueSize = httpClient.getMaxQueueSizePerAddress();
        String host = address.getHost();
        if (address.getPort() != (z2 ? R2.attr.banner_indicator_selected_color : 80)) {
            host = host + ":" + address.getPort();
        }
        this._hostHeader = new ByteArrayBuffer(host);
    }

    private AbstractHttpConnection getConnection(long j2) throws InterruptedException, IOException {
        boolean z2;
        AbstractHttpConnection idleConnection = null;
        while (idleConnection == null) {
            idleConnection = getIdleConnection();
            if (idleConnection != null || j2 <= 0) {
                break;
            }
            synchronized (this) {
                if (this._connections.size() + this._pendingConnections < this._maxConnections) {
                    z2 = true;
                    this._pendingReservedConnections++;
                } else {
                    z2 = false;
                }
            }
            if (z2) {
                startNewConnection();
                try {
                    Object objTake = this._reservedConnections.take();
                    if (!(objTake instanceof AbstractHttpConnection)) {
                        throw ((IOException) objTake);
                    }
                    idleConnection = (AbstractHttpConnection) objTake;
                } catch (InterruptedException e2) {
                    LOG.ignore(e2);
                }
            } else {
                try {
                    Thread.currentThread();
                    Thread.sleep(200L);
                    j2 -= 200;
                } catch (InterruptedException e3) {
                    LOG.ignore(e3);
                }
            }
        }
        return idleConnection;
    }

    public void addAuthorization(String str, Authentication authentication) {
        synchronized (this) {
            if (this._authorizations == null) {
                this._authorizations = new PathMap();
            }
            this._authorizations.put(str, authentication);
        }
    }

    public void addCookie(HttpCookie httpCookie) {
        synchronized (this) {
            if (this._cookies == null) {
                this._cookies = new ArrayList();
            }
            this._cookies.add(httpCookie);
        }
    }

    public void close() throws IOException {
        synchronized (this) {
            Iterator<AbstractHttpConnection> it = this._connections.iterator();
            while (it.hasNext()) {
                it.next().close();
            }
        }
    }

    public void doSend(HttpExchange httpExchange) throws IOException, IllegalArgumentException {
        boolean z2;
        Authentication authentication;
        List<HttpCookie> list = this._cookies;
        if (list != null) {
            StringBuilder sb = null;
            for (HttpCookie httpCookie : list) {
                if (sb == null) {
                    sb = new StringBuilder();
                } else {
                    sb.append("; ");
                }
                sb.append(httpCookie.getName());
                sb.append("=");
                sb.append(httpCookie.getValue());
            }
            if (sb != null) {
                httpExchange.addRequestHeader("Cookie", sb.toString());
            }
        }
        PathMap pathMap = this._authorizations;
        if (pathMap != null && (authentication = (Authentication) pathMap.match(httpExchange.getRequestURI())) != null) {
            authentication.setCredentials(httpExchange);
        }
        httpExchange.scheduleTimeout(this);
        AbstractHttpConnection idleConnection = getIdleConnection();
        if (idleConnection != null) {
            send(idleConnection, httpExchange);
            return;
        }
        synchronized (this) {
            if (this._exchanges.size() == this._maxQueueSize) {
                throw new RejectedExecutionException("Queue full for address " + this._address);
            }
            this._exchanges.add(httpExchange);
            z2 = this._connections.size() + this._pendingConnections < this._maxConnections;
        }
        if (z2) {
            startNewConnection();
        }
    }

    @Override // org.eclipse.jetty.util.component.Dumpable
    public String dump() {
        return AggregateLifeCycle.dump(this);
    }

    public void exchangeExpired(HttpExchange httpExchange) {
        synchronized (this) {
            this._exchanges.remove(httpExchange);
        }
    }

    public Address getAddress() {
        return this._address;
    }

    public int getConnections() {
        int size;
        synchronized (this) {
            size = this._connections.size();
        }
        return size;
    }

    public Buffer getHostHeader() {
        return this._hostHeader;
    }

    public HttpClient getHttpClient() {
        return this._client;
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x0016 A[Catch: all -> 0x002f, TryCatch #0 {, blocks: (B:5:0x0005, B:6:0x000e, B:8:0x0016, B:9:0x0024), top: B:18:0x0005 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public org.eclipse.jetty.client.AbstractHttpConnection getIdleConnection() throws java.io.IOException {
        /*
            r3 = this;
            r0 = 0
            r1 = r0
        L2:
            monitor-enter(r3)
            if (r1 == 0) goto Le
            java.util.List<org.eclipse.jetty.client.AbstractHttpConnection> r2 = r3._connections     // Catch: java.lang.Throwable -> L2f
            r2.remove(r1)     // Catch: java.lang.Throwable -> L2f
            r1.close()     // Catch: java.lang.Throwable -> L2f
            r1 = r0
        Le:
            java.util.List<org.eclipse.jetty.client.AbstractHttpConnection> r2 = r3._idleConnections     // Catch: java.lang.Throwable -> L2f
            int r2 = r2.size()     // Catch: java.lang.Throwable -> L2f
            if (r2 <= 0) goto L24
            java.util.List<org.eclipse.jetty.client.AbstractHttpConnection> r1 = r3._idleConnections     // Catch: java.lang.Throwable -> L2f
            int r2 = r1.size()     // Catch: java.lang.Throwable -> L2f
            int r2 = r2 + (-1)
            java.lang.Object r1 = r1.remove(r2)     // Catch: java.lang.Throwable -> L2f
            org.eclipse.jetty.client.AbstractHttpConnection r1 = (org.eclipse.jetty.client.AbstractHttpConnection) r1     // Catch: java.lang.Throwable -> L2f
        L24:
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L2f
            if (r1 != 0) goto L28
            return r0
        L28:
            boolean r2 = r1.cancelIdleTimeout()
            if (r2 == 0) goto L2
            return r1
        L2f:
            r0 = move-exception
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L2f
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.client.HttpDestination.getIdleConnection():org.eclipse.jetty.client.AbstractHttpConnection");
    }

    public int getIdleConnections() {
        int size;
        synchronized (this) {
            size = this._idleConnections.size();
        }
        return size;
    }

    public int getMaxConnections() {
        return this._maxConnections;
    }

    public int getMaxQueueSize() {
        return this._maxQueueSize;
    }

    public Address getProxy() {
        return this._proxy;
    }

    public Authentication getProxyAuthentication() {
        return this._proxyAuthentication;
    }

    public boolean isProxied() {
        return this._proxy != null;
    }

    public boolean isSecure() {
        return this._ssl;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0042  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onConnectionFailed(java.lang.Throwable r6) throws java.lang.InterruptedException {
        /*
            r5 = this;
            monitor-enter(r5)
            int r0 = r5._pendingConnections     // Catch: java.lang.Throwable -> L59
            r1 = 1
            int r0 = r0 - r1
            r5._pendingConnections = r0     // Catch: java.lang.Throwable -> L59
            int r0 = r5._pendingReservedConnections     // Catch: java.lang.Throwable -> L59
            r2 = 0
            if (r0 <= 0) goto L11
            int r0 = r0 - r1
            r5._pendingReservedConnections = r0     // Catch: java.lang.Throwable -> L59
            r1 = r2
            goto L44
        L11:
            java.util.List<org.eclipse.jetty.client.HttpExchange> r0 = r5._exchanges     // Catch: java.lang.Throwable -> L59
            int r0 = r0.size()     // Catch: java.lang.Throwable -> L59
            r3 = 0
            if (r0 <= 0) goto L42
            java.util.List<org.eclipse.jetty.client.HttpExchange> r0 = r5._exchanges     // Catch: java.lang.Throwable -> L59
            java.lang.Object r0 = r0.remove(r2)     // Catch: java.lang.Throwable -> L59
            org.eclipse.jetty.client.HttpExchange r0 = (org.eclipse.jetty.client.HttpExchange) r0     // Catch: java.lang.Throwable -> L59
            r4 = 9
            boolean r4 = r0.setStatus(r4)     // Catch: java.lang.Throwable -> L59
            if (r4 == 0) goto L31
            org.eclipse.jetty.client.HttpEventListener r0 = r0.getEventListener()     // Catch: java.lang.Throwable -> L59
            r0.onConnectionFailed(r6)     // Catch: java.lang.Throwable -> L59
        L31:
            java.util.List<org.eclipse.jetty.client.HttpExchange> r6 = r5._exchanges     // Catch: java.lang.Throwable -> L59
            boolean r6 = r6.isEmpty()     // Catch: java.lang.Throwable -> L59
            if (r6 != 0) goto L42
            org.eclipse.jetty.client.HttpClient r6 = r5._client     // Catch: java.lang.Throwable -> L59
            boolean r6 = r6.isStarted()     // Catch: java.lang.Throwable -> L59
            if (r6 == 0) goto L42
            goto L43
        L42:
            r1 = r2
        L43:
            r6 = r3
        L44:
            monitor-exit(r5)     // Catch: java.lang.Throwable -> L59
            if (r1 == 0) goto L4a
            r5.startNewConnection()
        L4a:
            if (r6 == 0) goto L58
            java.util.concurrent.BlockingQueue<java.lang.Object> r0 = r5._reservedConnections     // Catch: java.lang.InterruptedException -> L52
            r0.put(r6)     // Catch: java.lang.InterruptedException -> L52
            goto L58
        L52:
            r6 = move-exception
            org.eclipse.jetty.util.log.Logger r0 = org.eclipse.jetty.client.HttpDestination.LOG
            r0.ignore(r6)
        L58:
            return
        L59:
            r6 = move-exception
            monitor-exit(r5)     // Catch: java.lang.Throwable -> L59
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.client.HttpDestination.onConnectionFailed(java.lang.Throwable):void");
    }

    public void onException(Throwable th) {
        synchronized (this) {
            this._pendingConnections--;
            if (this._exchanges.size() > 0) {
                HttpExchange httpExchangeRemove = this._exchanges.remove(0);
                if (httpExchangeRemove.setStatus(9)) {
                    httpExchangeRemove.getEventListener().onException(th);
                }
            }
        }
    }

    public void onNewConnection(AbstractHttpConnection abstractHttpConnection) throws InterruptedException, IOException {
        synchronized (this) {
            this._pendingConnections--;
            this._connections.add(abstractHttpConnection);
            int i2 = this._pendingReservedConnections;
            if (i2 > 0) {
                this._pendingReservedConnections = i2 - 1;
            } else {
                EndPoint endPoint = abstractHttpConnection.getEndPoint();
                if (isProxied() && (endPoint instanceof SelectConnector.UpgradableEndPoint)) {
                    ConnectExchange connectExchange = new ConnectExchange(getAddress(), (SelectConnector.UpgradableEndPoint) endPoint);
                    connectExchange.setAddress(getProxy());
                    LOG.debug("Establishing tunnel to {} via {}", getAddress(), getProxy());
                    send(abstractHttpConnection, connectExchange);
                } else if (this._exchanges.size() == 0) {
                    LOG.debug("No exchanges for new connection {}", abstractHttpConnection);
                    abstractHttpConnection.setIdleTimeout();
                    this._idleConnections.add(abstractHttpConnection);
                } else {
                    send(abstractHttpConnection, this._exchanges.remove(0));
                }
                abstractHttpConnection = null;
            }
        }
        if (abstractHttpConnection != null) {
            try {
                this._reservedConnections.put(abstractHttpConnection);
            } catch (InterruptedException e2) {
                LOG.ignore(e2);
            }
        }
    }

    public void resend(HttpExchange httpExchange) throws IOException, IllegalArgumentException {
        httpExchange.getEventListener().onRetry();
        httpExchange.reset();
        doSend(httpExchange);
    }

    public AbstractHttpConnection reserveConnection(long j2) throws InterruptedException, IOException {
        AbstractHttpConnection connection = getConnection(j2);
        if (connection != null) {
            connection.setReserved(true);
        }
        return connection;
    }

    public void returnConnection(AbstractHttpConnection abstractHttpConnection, boolean z2) throws IOException {
        boolean z3;
        if (abstractHttpConnection.isReserved()) {
            abstractHttpConnection.setReserved(false);
        }
        if (z2) {
            try {
                abstractHttpConnection.close();
            } catch (IOException e2) {
                LOG.ignore(e2);
            }
        }
        if (this._client.isStarted()) {
            if (z2 || !abstractHttpConnection.getEndPoint().isOpen()) {
                synchronized (this) {
                    this._connections.remove(abstractHttpConnection);
                    z3 = !this._exchanges.isEmpty();
                }
                if (z3) {
                    startNewConnection();
                    return;
                }
                return;
            }
            synchronized (this) {
                if (this._exchanges.size() == 0) {
                    abstractHttpConnection.setIdleTimeout();
                    this._idleConnections.add(abstractHttpConnection);
                } else {
                    send(abstractHttpConnection, this._exchanges.remove(0));
                }
                notifyAll();
            }
        }
    }

    public void returnIdleConnection(AbstractHttpConnection abstractHttpConnection) {
        boolean z2;
        abstractHttpConnection.onIdleExpired(abstractHttpConnection.getEndPoint() != null ? abstractHttpConnection.getEndPoint().getMaxIdleTime() : -1L);
        synchronized (this) {
            this._idleConnections.remove(abstractHttpConnection);
            this._connections.remove(abstractHttpConnection);
            z2 = !this._exchanges.isEmpty() && this._client.isStarted();
        }
        if (z2) {
            startNewConnection();
        }
    }

    public void send(HttpExchange httpExchange) throws IOException {
        LinkedList<String> registeredListeners = this._client.getRegisteredListeners();
        if (registeredListeners != null) {
            for (int size = registeredListeners.size(); size > 0; size--) {
                String str = registeredListeners.get(size - 1);
                try {
                    httpExchange.setEventListener((HttpEventListener) Class.forName(str).getDeclaredConstructor(HttpDestination.class, HttpExchange.class).newInstance(this, httpExchange));
                } catch (Exception e2) {
                    throw new IOException("Unable to instantiate registered listener for destination: " + str, e2) { // from class: org.eclipse.jetty.client.HttpDestination.1
                        final /* synthetic */ Exception val$e;

                        {
                            this.val$e = e2;
                            initCause(e2);
                        }
                    };
                }
            }
        }
        if (this._client.hasRealms()) {
            httpExchange.setEventListener(new SecurityListener(this, httpExchange));
        }
        doSend(httpExchange);
    }

    public void setMaxConnections(int i2) {
        this._maxConnections = i2;
    }

    public void setMaxQueueSize(int i2) {
        this._maxQueueSize = i2;
    }

    public void setProxy(Address address) {
        this._proxy = address;
    }

    public void setProxyAuthentication(Authentication authentication) {
        this._proxyAuthentication = authentication;
    }

    public void startNewConnection() {
        try {
            synchronized (this) {
                this._pendingConnections++;
            }
            HttpClient.Connector connector = this._client._connector;
            if (connector != null) {
                connector.startConnection(this);
            }
        } catch (Exception e2) {
            LOG.debug(e2);
            onConnectionFailed(e2);
        }
    }

    public synchronized String toDetailString() {
        StringBuilder sb;
        sb = new StringBuilder();
        sb.append(toString());
        sb.append('\n');
        synchronized (this) {
            for (AbstractHttpConnection abstractHttpConnection : this._connections) {
                sb.append(abstractHttpConnection.toDetailString());
                if (this._idleConnections.contains(abstractHttpConnection)) {
                    sb.append(" IDLE");
                }
                sb.append('\n');
            }
        }
        return sb.toString();
        sb.append("--");
        sb.append('\n');
        return sb.toString();
    }

    public synchronized String toString() {
        return String.format("HttpDestination@%x//%s:%d(%d/%d,%d,%d/%d)%n", Integer.valueOf(hashCode()), this._address.getHost(), Integer.valueOf(this._address.getPort()), Integer.valueOf(this._connections.size()), Integer.valueOf(this._maxConnections), Integer.valueOf(this._idleConnections.size()), Integer.valueOf(this._exchanges.size()), Integer.valueOf(this._maxQueueSize));
    }

    @Override // org.eclipse.jetty.util.component.Dumpable
    public void dump(Appendable appendable, String str) throws IOException {
        synchronized (this) {
            appendable.append(String.valueOf(this));
            appendable.append("idle=");
            appendable.append(String.valueOf(this._idleConnections.size()));
            appendable.append(" pending=");
            appendable.append(String.valueOf(this._pendingConnections));
            appendable.append("\n");
            AggregateLifeCycle.dump(appendable, str, this._connections);
        }
    }

    public void send(AbstractHttpConnection abstractHttpConnection, HttpExchange httpExchange) throws IOException {
        synchronized (this) {
            if (!abstractHttpConnection.send(httpExchange)) {
                if (httpExchange.getStatus() <= 1) {
                    this._exchanges.add(0, httpExchange);
                }
                returnIdleConnection(abstractHttpConnection);
            }
        }
    }
}
