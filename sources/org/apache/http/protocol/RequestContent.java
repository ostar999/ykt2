package org.apache.http.protocol;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpVersion;
import org.apache.http.ProtocolException;
import org.apache.http.ProtocolVersion;

/* loaded from: classes9.dex */
public class RequestContent implements HttpRequestInterceptor {
    @Override // org.apache.http.HttpRequestInterceptor
    public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
        if (httpRequest == null) {
            throw new IllegalArgumentException("HTTP request may not be null");
        }
        if (httpRequest instanceof HttpEntityEnclosingRequest) {
            if (httpRequest.containsHeader("Transfer-Encoding")) {
                throw new ProtocolException("Transfer-encoding header already present");
            }
            if (httpRequest.containsHeader("Content-Length")) {
                throw new ProtocolException("Content-Length header already present");
            }
            ProtocolVersion protocolVersion = httpRequest.getRequestLine().getProtocolVersion();
            HttpEntity entity = ((HttpEntityEnclosingRequest) httpRequest).getEntity();
            if (entity == null) {
                httpRequest.addHeader("Content-Length", "0");
                return;
            }
            if (!entity.isChunked() && entity.getContentLength() >= 0) {
                httpRequest.addHeader("Content-Length", Long.toString(entity.getContentLength()));
            } else {
                if (protocolVersion.lessEquals(HttpVersion.HTTP_1_0)) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("Chunked transfer encoding not allowed for ");
                    stringBuffer.append(protocolVersion);
                    throw new ProtocolException(stringBuffer.toString());
                }
                httpRequest.addHeader("Transfer-Encoding", "chunked");
            }
            if (entity.getContentType() != null && !httpRequest.containsHeader("Content-Type")) {
                httpRequest.addHeader(entity.getContentType());
            }
            if (entity.getContentEncoding() == null || httpRequest.containsHeader("Content-Encoding")) {
                return;
            }
            httpRequest.addHeader(entity.getContentEncoding());
        }
    }
}
