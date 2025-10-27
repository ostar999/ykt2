package org.apache.http.conn.params;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.annotation.ThreadSafe;
import org.apache.http.conn.routing.HttpRoute;

@ThreadSafe
/* loaded from: classes9.dex */
public final class ConnPerRouteBean implements ConnPerRoute {
    public static final int DEFAULT_MAX_CONNECTIONS_PER_ROUTE = 2;
    private volatile int defaultMax;
    private final ConcurrentHashMap<HttpRoute, Integer> maxPerHostMap;

    public ConnPerRouteBean(int i2) {
        this.maxPerHostMap = new ConcurrentHashMap<>();
        setDefaultMaxPerRoute(i2);
    }

    @Deprecated
    public int getDefaultMax() {
        return this.defaultMax;
    }

    public int getDefaultMaxPerRoute() {
        return this.defaultMax;
    }

    @Override // org.apache.http.conn.params.ConnPerRoute
    public int getMaxForRoute(HttpRoute httpRoute) {
        if (httpRoute == null) {
            throw new IllegalArgumentException("HTTP route may not be null.");
        }
        Integer num = this.maxPerHostMap.get(httpRoute);
        return num != null ? num.intValue() : this.defaultMax;
    }

    public void setDefaultMaxPerRoute(int i2) {
        if (i2 < 1) {
            throw new IllegalArgumentException("The maximum must be greater than 0.");
        }
        this.defaultMax = i2;
    }

    public void setMaxForRoute(HttpRoute httpRoute, int i2) {
        if (httpRoute == null) {
            throw new IllegalArgumentException("HTTP route may not be null.");
        }
        if (i2 < 1) {
            throw new IllegalArgumentException("The maximum must be greater than 0.");
        }
        this.maxPerHostMap.put(httpRoute, Integer.valueOf(i2));
    }

    public void setMaxForRoutes(Map<HttpRoute, Integer> map) {
        if (map == null) {
            return;
        }
        this.maxPerHostMap.clear();
        this.maxPerHostMap.putAll(map);
    }

    public String toString() {
        return this.maxPerHostMap.toString();
    }

    public ConnPerRouteBean() {
        this(2);
    }
}
