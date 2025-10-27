package com.mobile.auth.gatewayauth;

import com.mobile.auth.gatewayauth.annotations.AuthNumber;
import com.mobile.auth.gatewayauth.model.LoginPhoneInfo;

@AuthNumber
/* loaded from: classes4.dex */
public interface OnLoginPhoneListener {
    void onGetFailed(String str);

    void onGetLoginPhone(LoginPhoneInfo loginPhoneInfo);
}
