package org.apache.http.protocol;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.HttpVersion;
import org.apache.http.ProtocolException;
import org.apache.http.ProtocolVersion;

/* loaded from: classes9.dex */
public class ResponseContent implements HttpResponseInterceptor {
    @Override // org.apache.http.HttpResponseInterceptor
    public void process(HttpResponse httpResponse, HttpContext httpContext) throws HttpException, IOException {
        if (httpResponse == null) {
            throw new IllegalArgumentException("HTTP response may not be null");
        }
        if (httpResponse.containsHeader("Transfer-Encoding")) {
            throw new ProtocolException("Transfer-encoding header already present");
        }
        if (httpResponse.containsHeader("Content-Length")) {
            throw new ProtocolException("Content-Length header already present");
        }
        ProtocolVersion protocolVersion = httpResponse.getStatusLine().getProtocolVersion();
        HttpEntity entity = httpResponse.getEntity();
        if (entity == null) {
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == 204 || statusCode == 304 || statusCode == 205) {
                return;
            }
            httpResponse.addHeader("Content-Length", "0");
            return;
        }
        long contentLength = entity.getContentLength();
        if (entity.isChunked() && !protocolVersion.lessEquals(HttpVersion.HTTP_1_0)) {
            httpResponse.addHeader("Transfer-Encoding", "chunked");
        } else if (contentLength >= 0) {
            httpResponse.addHeader("Content-Length", Long.toString(entity.getContentLength()));
        }
        if (entity.getContentType() != null && !httpResponse.containsHeader("Content-Type")) {
            httpResponse.addHeader(entity.getContentType());
        }
        if (entity.getContentEncoding() == null || httpResponse.containsHeader("Content-Encoding")) {
            return;
        }
        httpResponse.addHeader(entity.getContentEncoding());
    }
}
