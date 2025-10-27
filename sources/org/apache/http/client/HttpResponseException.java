package org.apache.http.client;

import org.apache.http.annotation.Immutable;

@Immutable
/* loaded from: classes9.dex */
public class HttpResponseException extends ClientProtocolException {
    private static final long serialVersionUID = -7186627969477257933L;
    private final int statusCode;

    public HttpResponseException(int i2, String str) {
        super(str);
        this.statusCode = i2;
    }

    public int getStatusCode() {
        return this.statusCode;
    }
}
