package org.apache.http.client.methods;

import java.net.URI;
import org.apache.http.annotation.NotThreadSafe;

@NotThreadSafe
/* loaded from: classes9.dex */
public class HttpTrace extends HttpRequestBase {
    public static final String METHOD_NAME = "TRACE";

    public HttpTrace() {
    }

    @Override // org.apache.http.client.methods.HttpRequestBase, org.apache.http.client.methods.HttpUriRequest
    public String getMethod() {
        return "TRACE";
    }

    public HttpTrace(URI uri) {
        setURI(uri);
    }

    public HttpTrace(String str) {
        setURI(URI.create(str));
    }
}
