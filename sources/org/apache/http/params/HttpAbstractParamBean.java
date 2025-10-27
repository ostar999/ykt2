package org.apache.http.params;

/* loaded from: classes9.dex */
public abstract class HttpAbstractParamBean {
    protected final HttpParams params;

    public HttpAbstractParamBean(HttpParams httpParams) {
        if (httpParams == null) {
            throw new IllegalArgumentException("HTTP parameters may not be null");
        }
        this.params = httpParams;
    }
}
