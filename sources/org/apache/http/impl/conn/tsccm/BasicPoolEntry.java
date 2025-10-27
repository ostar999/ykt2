package org.apache.http.impl.conn.tsccm;

import java.lang.ref.ReferenceQueue;
import java.util.concurrent.TimeUnit;
import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.conn.ClientConnectionOperator;
import org.apache.http.conn.OperatedClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.conn.AbstractPoolEntry;

@NotThreadSafe
/* loaded from: classes9.dex */
public class BasicPoolEntry extends AbstractPoolEntry {
    private final long created;
    private long expiry;
    private long updated;
    private long validUntil;

    @Deprecated
    public BasicPoolEntry(ClientConnectionOperator clientConnectionOperator, HttpRoute httpRoute, ReferenceQueue<Object> referenceQueue) {
        super(clientConnectionOperator, httpRoute);
        if (httpRoute == null) {
            throw new IllegalArgumentException("HTTP route may not be null");
        }
        this.created = System.currentTimeMillis();
        this.validUntil = Long.MAX_VALUE;
        this.expiry = Long.MAX_VALUE;
    }

    public final OperatedClientConnection getConnection() {
        return this.connection;
    }

    public long getCreated() {
        return this.created;
    }

    public long getExpiry() {
        return this.expiry;
    }

    public final HttpRoute getPlannedRoute() {
        return this.route;
    }

    public long getUpdated() {
        return this.updated;
    }

    public long getValidUntil() {
        return this.validUntil;
    }

    @Deprecated
    public final BasicPoolEntryRef getWeakRef() {
        return null;
    }

    public boolean isExpired(long j2) {
        return j2 >= this.expiry;
    }

    @Override // org.apache.http.impl.conn.AbstractPoolEntry
    public void shutdownEntry() {
        super.shutdownEntry();
    }

    public void updateExpiry(long j2, TimeUnit timeUnit) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        this.updated = jCurrentTimeMillis;
        this.expiry = Math.min(this.validUntil, j2 > 0 ? jCurrentTimeMillis + timeUnit.toMillis(j2) : Long.MAX_VALUE);
    }

    public BasicPoolEntry(ClientConnectionOperator clientConnectionOperator, HttpRoute httpRoute) {
        this(clientConnectionOperator, httpRoute, -1L, TimeUnit.MILLISECONDS);
    }

    public BasicPoolEntry(ClientConnectionOperator clientConnectionOperator, HttpRoute httpRoute, long j2, TimeUnit timeUnit) {
        super(clientConnectionOperator, httpRoute);
        if (httpRoute != null) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            this.created = jCurrentTimeMillis;
            if (j2 > 0) {
                this.validUntil = jCurrentTimeMillis + timeUnit.toMillis(j2);
            } else {
                this.validUntil = Long.MAX_VALUE;
            }
            this.expiry = this.validUntil;
            return;
        }
        throw new IllegalArgumentException("HTTP route may not be null");
    }
}
