package org.apache.http.impl;

import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpConnection;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.ParseException;
import org.apache.http.ProtocolVersion;
import org.apache.http.TokenIterator;
import org.apache.http.message.BasicTokenIterator;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.eclipse.jetty.http.HttpHeaders;

/* loaded from: classes9.dex */
public class DefaultConnectionReuseStrategy implements ConnectionReuseStrategy {
    public TokenIterator createTokenIterator(HeaderIterator headerIterator) {
        return new BasicTokenIterator(headerIterator);
    }

    @Override // org.apache.http.ConnectionReuseStrategy
    public boolean keepAlive(HttpResponse httpResponse, HttpContext httpContext) {
        if (httpResponse == null) {
            throw new IllegalArgumentException("HTTP response may not be null.");
        }
        if (httpContext == null) {
            throw new IllegalArgumentException("HTTP context may not be null.");
        }
        HttpConnection httpConnection = (HttpConnection) httpContext.getAttribute(ExecutionContext.HTTP_CONNECTION);
        if (httpConnection != null && !httpConnection.isOpen()) {
            return false;
        }
        HttpEntity entity = httpResponse.getEntity();
        ProtocolVersion protocolVersion = httpResponse.getStatusLine().getProtocolVersion();
        if (entity != null && entity.getContentLength() < 0 && (!entity.isChunked() || protocolVersion.lessEquals(HttpVersion.HTTP_1_0))) {
            return false;
        }
        HeaderIterator headerIterator = httpResponse.headerIterator("Connection");
        if (!headerIterator.hasNext()) {
            headerIterator = httpResponse.headerIterator(HttpHeaders.PROXY_CONNECTION);
        }
        if (headerIterator.hasNext()) {
            try {
                TokenIterator tokenIteratorCreateTokenIterator = createTokenIterator(headerIterator);
                boolean z2 = false;
                while (tokenIteratorCreateTokenIterator.hasNext()) {
                    String strNextToken = tokenIteratorCreateTokenIterator.nextToken();
                    if (HTTP.CONN_CLOSE.equalsIgnoreCase(strNextToken)) {
                        return false;
                    }
                    if ("Keep-Alive".equalsIgnoreCase(strNextToken)) {
                        z2 = true;
                    }
                }
                if (z2) {
                    return true;
                }
            } catch (ParseException unused) {
                return false;
            }
        }
        return !protocolVersion.lessEquals(HttpVersion.HTTP_1_0);
    }
}
