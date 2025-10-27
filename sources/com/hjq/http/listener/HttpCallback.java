package com.hjq.http.listener;

import okhttp3.Call;

/* loaded from: classes4.dex */
public class HttpCallback<T> implements OnHttpListener<T> {
    private final OnHttpListener mListener;

    public HttpCallback(OnHttpListener onHttpListener) {
        this.mListener = onHttpListener;
    }

    @Override // com.hjq.http.listener.OnHttpListener
    public void onEnd(Call call) {
        OnHttpListener onHttpListener = this.mListener;
        if (onHttpListener == null) {
            return;
        }
        onHttpListener.onEnd(call);
    }

    @Override // com.hjq.http.listener.OnHttpListener
    public void onFail(Exception exc) {
        OnHttpListener onHttpListener = this.mListener;
        if (onHttpListener == null) {
            return;
        }
        onHttpListener.onFail(exc);
    }

    @Override // com.hjq.http.listener.OnHttpListener
    public void onStart(Call call) {
        OnHttpListener onHttpListener = this.mListener;
        if (onHttpListener == null) {
            return;
        }
        onHttpListener.onStart(call);
    }

    @Override // com.hjq.http.listener.OnHttpListener
    public void onSucceed(T t2, boolean z2) {
        onSucceed(t2);
    }

    @Override // com.hjq.http.listener.OnHttpListener
    public void onSucceed(T t2) {
        OnHttpListener onHttpListener = this.mListener;
        if (onHttpListener == null) {
            return;
        }
        onHttpListener.onSucceed(t2);
    }
}
