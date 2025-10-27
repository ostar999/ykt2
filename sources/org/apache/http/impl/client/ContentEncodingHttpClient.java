package org.apache.http.impl.client;

import org.apache.http.annotation.ThreadSafe;
import org.apache.http.client.protocol.RequestAcceptEncoding;
import org.apache.http.client.protocol.ResponseContentEncoding;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpProcessor;

@ThreadSafe
/* loaded from: classes9.dex */
public class ContentEncodingHttpClient extends DefaultHttpClient {
    public ContentEncodingHttpClient(ClientConnectionManager clientConnectionManager, HttpParams httpParams) {
        super(clientConnectionManager, httpParams);
    }

    @Override // org.apache.http.impl.client.DefaultHttpClient, org.apache.http.impl.client.AbstractHttpClient
    public BasicHttpProcessor createHttpProcessor() {
        BasicHttpProcessor basicHttpProcessorCreateHttpProcessor = super.createHttpProcessor();
        basicHttpProcessorCreateHttpProcessor.addRequestInterceptor(new RequestAcceptEncoding());
        basicHttpProcessorCreateHttpProcessor.addResponseInterceptor(new ResponseContentEncoding());
        return basicHttpProcessorCreateHttpProcessor;
    }

    public ContentEncodingHttpClient(HttpParams httpParams) {
        this(null, httpParams);
    }

    public ContentEncodingHttpClient() {
        this(null);
    }
}
