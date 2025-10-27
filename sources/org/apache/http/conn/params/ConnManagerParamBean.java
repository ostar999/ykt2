package org.apache.http.conn.params;

import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.params.HttpAbstractParamBean;
import org.apache.http.params.HttpParams;

@Deprecated
@NotThreadSafe
/* loaded from: classes9.dex */
public class ConnManagerParamBean extends HttpAbstractParamBean {
    public ConnManagerParamBean(HttpParams httpParams) {
        super(httpParams);
    }

    @Deprecated
    public void setConnectionsPerRoute(ConnPerRouteBean connPerRouteBean) {
        this.params.setParameter(ConnManagerPNames.MAX_CONNECTIONS_PER_ROUTE, connPerRouteBean);
    }

    @Deprecated
    public void setMaxTotalConnections(int i2) {
        this.params.setIntParameter(ConnManagerPNames.MAX_TOTAL_CONNECTIONS, i2);
    }

    public void setTimeout(long j2) {
        this.params.setLongParameter(ConnManagerPNames.TIMEOUT, j2);
    }
}
