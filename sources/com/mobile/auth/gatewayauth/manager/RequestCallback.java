package com.mobile.auth.gatewayauth.manager;

import com.mobile.auth.gatewayauth.annotations.SafeProtector;

@SafeProtector
/* loaded from: classes4.dex */
public interface RequestCallback<T, K> {
    void onError(K k2);

    void onSuccess(T t2);
}
