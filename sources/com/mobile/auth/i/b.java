package com.mobile.auth.i;

import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class b extends g {

    /* renamed from: a, reason: collision with root package name */
    private String f10362a;

    /* renamed from: b, reason: collision with root package name */
    private String f10363b;

    /* renamed from: c, reason: collision with root package name */
    private String f10364c;

    /* renamed from: d, reason: collision with root package name */
    private String f10365d;

    /* renamed from: e, reason: collision with root package name */
    private String f10366e;

    /* renamed from: f, reason: collision with root package name */
    private String f10367f;

    /* renamed from: g, reason: collision with root package name */
    private String f10368g;

    @Override // com.mobile.auth.i.g
    public String a() {
        return this.f10367f;
    }

    @Override // com.mobile.auth.i.g
    public String a(String str) {
        return this.f10362a + this.f10366e + this.f10367f + "iYm0HAnkxQtpvN44";
    }

    @Override // com.mobile.auth.i.g
    public JSONObject b() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("version", this.f10362a);
            jSONObject.put("apptype", this.f10363b);
            jSONObject.put("phone_ID", this.f10364c);
            jSONObject.put("certflag", this.f10365d);
            jSONObject.put("sdkversion", this.f10366e);
            jSONObject.put("appid", this.f10367f);
            jSONObject.put("expandparams", "");
            jSONObject.put("sign", this.f10368g);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return jSONObject;
    }

    public void b(String str) {
        this.f10362a = str;
    }

    public void c(String str) {
        this.f10363b = str;
    }

    public void d(String str) {
        this.f10364c = str;
    }

    public void e(String str) {
        this.f10365d = str;
    }

    public void f(String str) {
        this.f10366e = str;
    }

    public void g(String str) {
        this.f10367f = str;
    }

    public void h(String str) {
        this.f10368g = str;
    }
}
