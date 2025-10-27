package com.hjq.http.model;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import okio.Timeout;

/* loaded from: classes4.dex */
public final class CallProxy implements Call {
    private Call mCall;

    public CallProxy(Call call) {
        this.mCall = call;
    }

    @Override // okhttp3.Call
    public void cancel() {
        Call call = this.mCall;
        if (call == null) {
            return;
        }
        call.cancel();
    }

    @Override // okhttp3.Call
    public void enqueue(Callback callback) {
        Call call = this.mCall;
        if (call == null) {
            return;
        }
        call.enqueue(callback);
    }

    @Override // okhttp3.Call
    public Response execute() throws IOException {
        Call call = this.mCall;
        if (call == null) {
            return null;
        }
        return call.execute();
    }

    @Override // okhttp3.Call
    /* renamed from: isCanceled */
    public boolean getCanceled() {
        Call call = this.mCall;
        if (call == null) {
            return false;
        }
        return call.getCanceled();
    }

    @Override // okhttp3.Call
    public boolean isExecuted() {
        Call call = this.mCall;
        if (call == null) {
            return false;
        }
        return call.isExecuted();
    }

    @Override // okhttp3.Call
    public Request request() {
        Call call = this.mCall;
        if (call == null) {
            return null;
        }
        return call.request();
    }

    public void setCall(Call call) {
        this.mCall = call;
    }

    @Override // okhttp3.Call
    public Timeout timeout() {
        Call call = this.mCall;
        if (call == null) {
            return null;
        }
        return call.timeout();
    }

    @Override // okhttp3.Call
    public Call clone() {
        Call call = this.mCall;
        if (call == null) {
            return null;
        }
        return call.clone();
    }
}
