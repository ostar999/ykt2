package com.hjq.http.exception;

import okhttp3.Response;

/* loaded from: classes4.dex */
public final class ResponseException extends HttpException {
    private final Response mResponse;

    public ResponseException(String str, Response response) {
        super(str);
        this.mResponse = response;
    }

    public Response getResponse() {
        return this.mResponse;
    }

    public ResponseException(String str, Throwable th, Response response) {
        super(str, th);
        this.mResponse = response;
    }
}
