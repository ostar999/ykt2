package com.huawei.hms.hatool;

import com.aliyun.vod.log.struct.AliyunLogKey;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class e1 extends n {

    /* renamed from: b, reason: collision with root package name */
    public String f7721b = "";

    /* renamed from: c, reason: collision with root package name */
    public String f7722c = "";

    /* renamed from: d, reason: collision with root package name */
    public String f7723d = "";

    /* renamed from: e, reason: collision with root package name */
    public String f7724e = "";

    /* renamed from: f, reason: collision with root package name */
    public String f7725f = "";

    /* renamed from: g, reason: collision with root package name */
    public String f7726g;

    @Override // com.huawei.hms.hatool.s
    public JSONObject a() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("androidid", this.f7827a);
        jSONObject.put("oaid", this.f7726g);
        jSONObject.put(AliyunLogKey.KEY_UUID, this.f7725f);
        jSONObject.put("upid", this.f7724e);
        jSONObject.put("imei", this.f7721b);
        jSONObject.put("sn", this.f7722c);
        jSONObject.put("udid", this.f7723d);
        return jSONObject;
    }

    public void b(String str) {
        this.f7721b = str;
    }

    public void c(String str) {
        this.f7726g = str;
    }

    public void d(String str) {
        this.f7722c = str;
    }

    public void e(String str) {
        this.f7723d = str;
    }

    public void f(String str) {
        this.f7724e = str;
    }

    public void g(String str) {
        this.f7725f = str;
    }
}
