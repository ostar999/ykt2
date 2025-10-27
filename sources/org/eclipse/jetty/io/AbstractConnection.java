package org.eclipse.jetty.io;

import java.io.IOException;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes9.dex */
public abstract class AbstractConnection implements Connection {
    private static final Logger LOG = Log.getLogger((Class<?>) AbstractConnection.class);
    protected final EndPoint _endp;
    private final long _timeStamp;

    public AbstractConnection(EndPoint endPoint) {
        this._endp = endPoint;
        this._timeStamp = System.currentTimeMillis();
    }

    public EndPoint getEndPoint() {
        return this._endp;
    }

    @Override // org.eclipse.jetty.io.Connection
    public long getTimeStamp() {
        return this._timeStamp;
    }

    @Override // org.eclipse.jetty.io.Connection
    public void onIdleExpired(long j2) {
        try {
            LOG.debug("onIdleExpired {}ms {} {}", Long.valueOf(j2), this, this._endp);
            if (this._endp.isInputShutdown() || this._endp.isOutputShutdown()) {
                this._endp.close();
            } else {
                this._endp.shutdownOutput();
            }
        } catch (IOException e2) {
            LOG.ignore(e2);
            try {
                this._endp.close();
            } catch (IOException e3) {
                LOG.ignore(e3);
            }
        }
    }

    public String toString() {
        return String.format("%s@%x", getClass().getSimpleName(), Integer.valueOf(hashCode()));
    }

    public AbstractConnection(EndPoint endPoint, long j2) {
        this._endp = endPoint;
        this._timeStamp = j2;
    }
}
