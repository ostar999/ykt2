package com.alipay.sdk.packet.impl;

import com.just.agentweb.BuildConfig;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class b extends com.alipay.sdk.packet.d {
    @Override // com.alipay.sdk.packet.d
    public final JSONObject a() throws JSONException {
        return com.alipay.sdk.packet.d.a("sdkConfig", "obtain");
    }

    @Override // com.alipay.sdk.packet.d
    public final String b() {
        return BuildConfig.VERSION_NAME;
    }
}
