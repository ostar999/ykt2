package com.alipay.sdk.packet.impl;

import android.content.Context;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class c extends com.alipay.sdk.packet.d {

    /* renamed from: t, reason: collision with root package name */
    public static final String f3307t = "log_v";

    @Override // com.alipay.sdk.packet.d
    public final String a(String str, JSONObject jSONObject) {
        return str;
    }

    @Override // com.alipay.sdk.packet.d
    public final List<Header> a(boolean z2, String str) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new BasicHeader(com.alipay.sdk.packet.d.f3285a, String.valueOf(z2)));
        arrayList.add(new BasicHeader(com.alipay.sdk.packet.d.f3288d, "application/octet-stream"));
        arrayList.add(new BasicHeader(com.alipay.sdk.packet.d.f3291g, "CBC"));
        return arrayList;
    }

    @Override // com.alipay.sdk.packet.d
    public final JSONObject a() throws JSONException {
        return null;
    }

    @Override // com.alipay.sdk.packet.d
    public final String c() throws JSONException {
        HashMap map = new HashMap();
        map.put("api_name", "/sdk/log");
        map.put(com.alipay.sdk.packet.d.f3294j, "1.0.0");
        HashMap map2 = new HashMap();
        map2.put(f3307t, "1.0");
        return com.alipay.sdk.packet.d.a((HashMap<String, String>) map, (HashMap<String, String>) map2);
    }

    @Override // com.alipay.sdk.packet.d
    public final com.alipay.sdk.packet.b a(Context context, String str) throws Throwable {
        return a(context, str, "https://mcgw.alipay.com/sdklog.do", true);
    }
}
