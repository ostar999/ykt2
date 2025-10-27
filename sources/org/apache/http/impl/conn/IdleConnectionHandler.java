package org.apache.http.impl.conn;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpConnection;

@Deprecated
/* loaded from: classes9.dex */
public class IdleConnectionHandler {
    private final Log log = LogFactory.getLog(getClass());
    private final Map<HttpConnection, TimeValues> connectionToTimes = new HashMap();

    public static class TimeValues {
        private final long timeAdded;
        private final long timeExpires;

        public TimeValues(long j2, long j3, TimeUnit timeUnit) {
            this.timeAdded = j2;
            if (j3 > 0) {
                this.timeExpires = j2 + timeUnit.toMillis(j3);
            } else {
                this.timeExpires = Long.MAX_VALUE;
            }
        }
    }

    public void add(HttpConnection httpConnection, long j2, TimeUnit timeUnit) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (this.log.isDebugEnabled()) {
            this.log.debug("Adding connection at: " + jCurrentTimeMillis);
        }
        this.connectionToTimes.put(httpConnection, new TimeValues(jCurrentTimeMillis, j2, timeUnit));
    }

    public void closeExpiredConnections() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (this.log.isDebugEnabled()) {
            this.log.debug("Checking for expired connections, now: " + jCurrentTimeMillis);
        }
        for (Map.Entry<HttpConnection, TimeValues> entry : this.connectionToTimes.entrySet()) {
            HttpConnection key = entry.getKey();
            TimeValues value = entry.getValue();
            if (value.timeExpires <= jCurrentTimeMillis) {
                if (this.log.isDebugEnabled()) {
                    this.log.debug("Closing connection, expired @: " + value.timeExpires);
                }
                try {
                    key.close();
                } catch (IOException e2) {
                    this.log.debug("I/O error closing connection", e2);
                }
            }
        }
    }

    public void closeIdleConnections(long j2) {
        long jCurrentTimeMillis = System.currentTimeMillis() - j2;
        if (this.log.isDebugEnabled()) {
            this.log.debug("Checking for connections, idle timeout: " + jCurrentTimeMillis);
        }
        for (Map.Entry<HttpConnection, TimeValues> entry : this.connectionToTimes.entrySet()) {
            HttpConnection key = entry.getKey();
            long j3 = entry.getValue().timeAdded;
            if (j3 <= jCurrentTimeMillis) {
                if (this.log.isDebugEnabled()) {
                    this.log.debug("Closing idle connection, connection time: " + j3);
                }
                try {
                    key.close();
                } catch (IOException e2) {
                    this.log.debug("I/O error closing connection", e2);
                }
            }
        }
    }

    public boolean remove(HttpConnection httpConnection) {
        TimeValues timeValuesRemove = this.connectionToTimes.remove(httpConnection);
        if (timeValuesRemove != null) {
            return System.currentTimeMillis() <= timeValuesRemove.timeExpires;
        }
        this.log.warn("Removing a connection that never existed!");
        return true;
    }

    public void removeAll() {
        this.connectionToTimes.clear();
    }
}
