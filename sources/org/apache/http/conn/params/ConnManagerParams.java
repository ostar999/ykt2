package org.apache.http.conn.params;

import org.apache.http.annotation.Immutable;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;

@Deprecated
@Immutable
/* loaded from: classes9.dex */
public final class ConnManagerParams implements ConnManagerPNames {
    private static final ConnPerRoute DEFAULT_CONN_PER_ROUTE = new ConnPerRoute() { // from class: org.apache.http.conn.params.ConnManagerParams.1
        @Override // org.apache.http.conn.params.ConnPerRoute
        public int getMaxForRoute(HttpRoute httpRoute) {
            return 2;
        }
    };
    public static final int DEFAULT_MAX_TOTAL_CONNECTIONS = 20;

    @Deprecated
    public static ConnPerRoute getMaxConnectionsPerRoute(HttpParams httpParams) {
        if (httpParams == null) {
            throw new IllegalArgumentException("HTTP parameters must not be null.");
        }
        ConnPerRoute connPerRoute = (ConnPerRoute) httpParams.getParameter(ConnManagerPNames.MAX_CONNECTIONS_PER_ROUTE);
        return connPerRoute == null ? DEFAULT_CONN_PER_ROUTE : connPerRoute;
    }

    @Deprecated
    public static int getMaxTotalConnections(HttpParams httpParams) {
        if (httpParams != null) {
            return httpParams.getIntParameter(ConnManagerPNames.MAX_TOTAL_CONNECTIONS, 20);
        }
        throw new IllegalArgumentException("HTTP parameters must not be null.");
    }

    @Deprecated
    public static long getTimeout(HttpParams httpParams) {
        if (httpParams == null) {
            throw new IllegalArgumentException("HTTP parameters may not be null");
        }
        Long l2 = (Long) httpParams.getParameter(ConnManagerPNames.TIMEOUT);
        return l2 != null ? l2.longValue() : httpParams.getIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 0);
    }

    @Deprecated
    public static void setMaxConnectionsPerRoute(HttpParams httpParams, ConnPerRoute connPerRoute) {
        if (httpParams == null) {
            throw new IllegalArgumentException("HTTP parameters must not be null.");
        }
        httpParams.setParameter(ConnManagerPNames.MAX_CONNECTIONS_PER_ROUTE, connPerRoute);
    }

    @Deprecated
    public static void setMaxTotalConnections(HttpParams httpParams, int i2) {
        if (httpParams == null) {
            throw new IllegalArgumentException("HTTP parameters must not be null.");
        }
        httpParams.setIntParameter(ConnManagerPNames.MAX_TOTAL_CONNECTIONS, i2);
    }

    @Deprecated
    public static void setTimeout(HttpParams httpParams, long j2) {
        if (httpParams == null) {
            throw new IllegalArgumentException("HTTP parameters may not be null");
        }
        httpParams.setLongParameter(ConnManagerPNames.TIMEOUT, j2);
    }
}
