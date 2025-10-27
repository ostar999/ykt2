package com.hjq.http.listener;

import okhttp3.Call;

/* loaded from: classes4.dex */
public interface OnHttpListener<T> {
    void onEnd(Call call);

    void onFail(Exception exc);

    void onStart(Call call);

    void onSucceed(T t2);

    void onSucceed(T t2, boolean z2);
}
