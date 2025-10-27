package com.huawei.hms.hatool;

import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class f1 extends o {

    /* renamed from: g, reason: collision with root package name */
    public String f7734g = "";

    @Override // com.huawei.hms.hatool.s
    public JSONObject a() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("protocol_version", "1");
        jSONObject.put("compress_mode", "1");
        jSONObject.put("serviceid", this.f7831d);
        jSONObject.put("appid", this.f7828a);
        jSONObject.put("hmac", this.f7734g);
        jSONObject.put("chifer", this.f7833f);
        jSONObject.put("timestamp", this.f7829b);
        jSONObject.put("servicetag", this.f7830c);
        jSONObject.put("requestid", this.f7832e);
        return jSONObject;
    }

    public void g(String str) {
        this.f7734g = str;
    }
}
