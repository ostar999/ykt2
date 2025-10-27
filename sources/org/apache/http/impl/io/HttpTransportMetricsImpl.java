package org.apache.http.impl.io;

import org.apache.http.io.HttpTransportMetrics;

/* loaded from: classes9.dex */
public class HttpTransportMetricsImpl implements HttpTransportMetrics {
    private long bytesTransferred = 0;

    @Override // org.apache.http.io.HttpTransportMetrics
    public long getBytesTransferred() {
        return this.bytesTransferred;
    }

    public void incrementBytesTransferred(long j2) {
        this.bytesTransferred += j2;
    }

    @Override // org.apache.http.io.HttpTransportMetrics
    public void reset() {
        this.bytesTransferred = 0L;
    }

    public void setBytesTransferred(long j2) {
        this.bytesTransferred = j2;
    }
}
