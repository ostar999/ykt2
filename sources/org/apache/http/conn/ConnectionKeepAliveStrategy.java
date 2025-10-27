package org.apache.http.conn;

import org.apache.http.HttpResponse;
import org.apache.http.protocol.HttpContext;

/* loaded from: classes9.dex */
public interface ConnectionKeepAliveStrategy {
    long getKeepAliveDuration(HttpResponse httpResponse, HttpContext httpContext);
}
