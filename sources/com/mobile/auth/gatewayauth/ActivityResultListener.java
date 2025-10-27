package com.mobile.auth.gatewayauth;

import android.content.Intent;
import com.mobile.auth.gatewayauth.annotations.AuthNumber;

@AuthNumber
/* loaded from: classes4.dex */
public interface ActivityResultListener {
    void onActivityResult(int i2, int i3, Intent intent);
}
