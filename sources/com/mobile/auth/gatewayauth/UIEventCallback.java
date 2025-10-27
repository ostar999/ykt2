package com.mobile.auth.gatewayauth;

import android.content.Context;
import com.mobile.auth.gatewayauth.annotations.AuthNumber;

@AuthNumber
/* loaded from: classes4.dex */
public interface UIEventCallback {
    void onEvent(String str, Context context, String str2);
}
