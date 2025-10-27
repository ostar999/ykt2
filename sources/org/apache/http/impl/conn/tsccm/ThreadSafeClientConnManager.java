package org.apache.http.impl.conn.tsccm;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.annotation.ThreadSafe;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ClientConnectionOperator;
import org.apache.http.conn.ClientConnectionRequest;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.ManagedClientConnection;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.conn.DefaultClientConnectionOperator;
import org.apache.http.impl.conn.SchemeRegistryFactory;
import org.apache.http.params.HttpParams;

@ThreadSafe
/* loaded from: classes9.dex */
public class ThreadSafeClientConnManager implements ClientConnectionManager {
    protected final ClientConnectionOperator connOperator;
    protected final ConnPerRouteBean connPerRoute;

    @Deprecated
    protected final AbstractConnPool connectionPool;
    private final Log log;
    protected final ConnPoolByRoute pool;
    protected final SchemeRegistry schemeRegistry;

    public ThreadSafeClientConnManager(SchemeRegistry schemeRegistry) {
        this(schemeRegistry, -1L, TimeUnit.MILLISECONDS);
    }

    @Override // org.apache.http.conn.ClientConnectionManager
    public void closeExpiredConnections() {
        this.log.debug("Closing expired connections");
        this.pool.closeExpiredConnections();
    }

    @Override // org.apache.http.conn.ClientConnectionManager
    public void closeIdleConnections(long j2, TimeUnit timeUnit) {
        if (this.log.isDebugEnabled()) {
            this.log.debug("Closing connections idle longer than " + j2 + " " + timeUnit);
        }
        this.pool.closeIdleConnections(j2, timeUnit);
    }

    public ClientConnectionOperator createConnectionOperator(SchemeRegistry schemeRegistry) {
        return new DefaultClientConnectionOperator(schemeRegistry);
    }

    @Deprecated
    public AbstractConnPool createConnectionPool(HttpParams httpParams) {
        return new ConnPoolByRoute(this.connOperator, httpParams);
    }

    public void finalize() throws Throwable {
        try {
            shutdown();
        } finally {
            super.finalize();
        }
    }

    public int getConnectionsInPool(HttpRoute httpRoute) {
        return this.pool.getConnectionsInPool(httpRoute);
    }

    public int getDefaultMaxPerRoute() {
        return this.connPerRoute.getDefaultMaxPerRoute();
    }

    public int getMaxForRoute(HttpRoute httpRoute) {
        return this.connPerRoute.getMaxForRoute(httpRoute);
    }

    public int getMaxTotal() {
        return this.pool.getMaxTotalConnections();
    }

    @Override // org.apache.http.conn.ClientConnectionManager
    public SchemeRegistry getSchemeRegistry() {
        return this.schemeRegistry;
    }

    @Override // org.apache.http.conn.ClientConnectionManager
    public void releaseConnection(ManagedClientConnection managedClientConnection, long j2, TimeUnit timeUnit) {
        boolean zIsMarkedReusable;
        ConnPoolByRoute connPoolByRoute;
        if (!(managedClientConnection instanceof BasicPooledConnAdapter)) {
            throw new IllegalArgumentException("Connection class mismatch, connection not obtained from this manager.");
        }
        BasicPooledConnAdapter basicPooledConnAdapter = (BasicPooledConnAdapter) managedClientConnection;
        if (basicPooledConnAdapter.getPoolEntry() != null && basicPooledConnAdapter.getManager() != this) {
            throw new IllegalArgumentException("Connection not obtained from this manager.");
        }
        synchronized (basicPooledConnAdapter) {
            BasicPoolEntry basicPoolEntry = (BasicPoolEntry) basicPooledConnAdapter.getPoolEntry();
            if (basicPoolEntry == null) {
                return;
            }
            try {
                try {
                    if (basicPooledConnAdapter.isOpen() && !basicPooledConnAdapter.isMarkedReusable()) {
                        basicPooledConnAdapter.shutdown();
                    }
                    zIsMarkedReusable = basicPooledConnAdapter.isMarkedReusable();
                    if (this.log.isDebugEnabled()) {
                        if (zIsMarkedReusable) {
                            this.log.debug("Released connection is reusable.");
                        } else {
                            this.log.debug("Released connection is not reusable.");
                        }
                    }
                    basicPooledConnAdapter.detach();
                    connPoolByRoute = this.pool;
                } catch (Throwable th) {
                    boolean zIsMarkedReusable2 = basicPooledConnAdapter.isMarkedReusable();
                    if (this.log.isDebugEnabled()) {
                        if (zIsMarkedReusable2) {
                            this.log.debug("Released connection is reusable.");
                        } else {
                            this.log.debug("Released connection is not reusable.");
                        }
                    }
                    basicPooledConnAdapter.detach();
                    this.pool.freeEntry(basicPoolEntry, zIsMarkedReusable2, j2, timeUnit);
                    throw th;
                }
            } catch (IOException e2) {
                if (this.log.isDebugEnabled()) {
                    this.log.debug("Exception shutting down released connection.", e2);
                }
                zIsMarkedReusable = basicPooledConnAdapter.isMarkedReusable();
                if (this.log.isDebugEnabled()) {
                    if (zIsMarkedReusable) {
                        this.log.debug("Released connection is reusable.");
                    } else {
                        this.log.debug("Released connection is not reusable.");
                    }
                }
                basicPooledConnAdapter.detach();
                connPoolByRoute = this.pool;
            }
            connPoolByRoute.freeEntry(basicPoolEntry, zIsMarkedReusable, j2, timeUnit);
        }
    }

    @Override // org.apache.http.conn.ClientConnectionManager
    public ClientConnectionRequest requestConnection(final HttpRoute httpRoute, Object obj) {
        final PoolEntryRequest poolEntryRequestRequestPoolEntry = this.pool.requestPoolEntry(httpRoute, obj);
        return new ClientConnectionRequest() { // from class: org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager.1
            @Override // org.apache.http.conn.ClientConnectionRequest
            public void abortRequest() {
                poolEntryRequestRequestPoolEntry.abortRequest();
            }

            @Override // org.apache.http.conn.ClientConnectionRequest
            public ManagedClientConnection getConnection(long j2, TimeUnit timeUnit) throws InterruptedException, ConnectionPoolTimeoutException {
                if (httpRoute == null) {
                    throw new IllegalArgumentException("Route may not be null.");
                }
                if (ThreadSafeClientConnManager.this.log.isDebugEnabled()) {
                    ThreadSafeClientConnManager.this.log.debug("Get connection: " + httpRoute + ", timeout = " + j2);
                }
                return new BasicPooledConnAdapter(ThreadSafeClientConnManager.this, poolEntryRequestRequestPoolEntry.getPoolEntry(j2, timeUnit));
            }
        };
    }

    public void setDefaultMaxPerRoute(int i2) {
        this.connPerRoute.setDefaultMaxPerRoute(i2);
    }

    public void setMaxForRoute(HttpRoute httpRoute, int i2) {
        this.connPerRoute.setMaxForRoute(httpRoute, i2);
    }

    public void setMaxTotal(int i2) {
        this.pool.setMaxTotalConnections(i2);
    }

    @Override // org.apache.http.conn.ClientConnectionManager
    public void shutdown() {
        this.log.debug("Shutting down");
        this.pool.shutdown();
    }

    public ThreadSafeClientConnManager() {
        this(SchemeRegistryFactory.createDefault());
    }

    public ConnPoolByRoute createConnectionPool(long j2, TimeUnit timeUnit) {
        return new ConnPoolByRoute(this.connOperator, this.connPerRoute, 20, j2, timeUnit);
    }

    public int getConnectionsInPool() {
        return this.pool.getConnectionsInPool();
    }

    public ThreadSafeClientConnManager(SchemeRegistry schemeRegistry, long j2, TimeUnit timeUnit) {
        if (schemeRegistry != null) {
            this.log = LogFactory.getLog(getClass());
            this.schemeRegistry = schemeRegistry;
            this.connPerRoute = new ConnPerRouteBean();
            this.connOperator = createConnectionOperator(schemeRegistry);
            ConnPoolByRoute connPoolByRouteCreateConnectionPool = createConnectionPool(j2, timeUnit);
            this.pool = connPoolByRouteCreateConnectionPool;
            this.connectionPool = connPoolByRouteCreateConnectionPool;
            return;
        }
        throw new IllegalArgumentException("Scheme registry may not be null");
    }

    @Deprecated
    public ThreadSafeClientConnManager(HttpParams httpParams, SchemeRegistry schemeRegistry) {
        if (schemeRegistry != null) {
            this.log = LogFactory.getLog(getClass());
            this.schemeRegistry = schemeRegistry;
            this.connPerRoute = new ConnPerRouteBean();
            this.connOperator = createConnectionOperator(schemeRegistry);
            ConnPoolByRoute connPoolByRoute = (ConnPoolByRoute) createConnectionPool(httpParams);
            this.pool = connPoolByRoute;
            this.connectionPool = connPoolByRoute;
            return;
        }
        throw new IllegalArgumentException("Scheme registry may not be null");
    }
}
