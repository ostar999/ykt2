package com.hjq.http.exception;

/* loaded from: classes4.dex */
public class HttpException extends Exception {
    private final String mMessage;

    public HttpException(String str) {
        super(str);
        this.mMessage = str;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        return this.mMessage;
    }

    public HttpException(String str, Throwable th) {
        super(str, th);
        this.mMessage = str;
    }
}
