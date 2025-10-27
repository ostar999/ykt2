package org.apache.http.client.methods;

import java.net.URI;
import org.apache.http.annotation.NotThreadSafe;

@NotThreadSafe
/* loaded from: classes9.dex */
public class HttpHead extends HttpRequestBase {
    public static final String METHOD_NAME = "HEAD";

    public HttpHead() {
    }

    @Override // org.apache.http.client.methods.HttpRequestBase, org.apache.http.client.methods.HttpUriRequest
    public String getMethod() {
        return "HEAD";
    }

    public HttpHead(URI uri) {
        setURI(uri);
    }

    public HttpHead(String str) {
        setURI(URI.create(str));
    }
}
