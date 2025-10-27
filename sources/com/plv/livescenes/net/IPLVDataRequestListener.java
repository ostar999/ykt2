package com.plv.livescenes.net;

/* loaded from: classes5.dex */
public interface IPLVDataRequestListener<T> {
    void onFailed(String str, Throwable th);

    void onSuccess(T t2);
}
