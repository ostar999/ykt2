package org.eclipse.jetty.http;

import java.io.IOException;

/* loaded from: classes9.dex */
public class HttpException extends IOException {
    String _reason;
    int _status;

    public HttpException(int i2) {
        this._status = i2;
        this._reason = null;
    }

    public String getReason() {
        return this._reason;
    }

    public int getStatus() {
        return this._status;
    }

    public void setReason(String str) {
        this._reason = str;
    }

    public void setStatus(int i2) {
        this._status = i2;
    }

    @Override // java.lang.Throwable
    public String toString() {
        return "HttpException(" + this._status + "," + this._reason + "," + super.getCause() + ")";
    }

    public HttpException(int i2, String str) {
        this._status = i2;
        this._reason = str;
    }

    public HttpException(int i2, String str, Throwable th) {
        this._status = i2;
        this._reason = str;
        initCause(th);
    }
}
