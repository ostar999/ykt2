package com.mobile.auth.gatewayauth;

import com.mobile.auth.gatewayauth.annotations.AuthNumber;
import com.mobile.auth.gatewayauth.annotations.SafeProtector;

@AuthNumber
@SafeProtector
/* loaded from: classes4.dex */
public interface PreLoginResultListener {
    void onTokenFailed(String str, String str2);

    void onTokenSuccess(String str);
}
