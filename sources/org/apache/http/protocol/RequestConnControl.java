package org.apache.http.protocol;

import java.io.IOException;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.eclipse.jetty.http.HttpMethods;

/* loaded from: classes9.dex */
public class RequestConnControl implements HttpRequestInterceptor {
    @Override // org.apache.http.HttpRequestInterceptor
    public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
        if (httpRequest == null) {
            throw new IllegalArgumentException("HTTP request may not be null");
        }
        if (httpRequest.getRequestLine().getMethod().equalsIgnoreCase(HttpMethods.CONNECT) || httpRequest.containsHeader("Connection")) {
            return;
        }
        httpRequest.addHeader("Connection", "Keep-Alive");
    }
}
