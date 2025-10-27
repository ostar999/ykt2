package com.mobile.auth.i;

import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class d extends g {

    /* renamed from: a, reason: collision with root package name */
    private final String f10370a;

    /* renamed from: b, reason: collision with root package name */
    private final String f10371b;

    /* renamed from: c, reason: collision with root package name */
    private final String f10372c;

    /* renamed from: d, reason: collision with root package name */
    private String f10373d = "authz";

    /* renamed from: e, reason: collision with root package name */
    private String f10374e;

    public d(String str, String str2, String str3) {
        this.f10370a = str;
        this.f10371b = str2;
        this.f10372c = str3;
    }

    @Override // com.mobile.auth.i.g
    public String a() {
        return this.f10370a;
    }

    @Override // com.mobile.auth.i.g
    public String a(String str) {
        return null;
    }

    @Override // com.mobile.auth.i.g
    public JSONObject b() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("ver", this.f10371b);
            jSONObject.put("data", this.f10372c);
            jSONObject.put("userCapaid", this.f10374e);
            jSONObject.put("funcType", this.f10373d);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return jSONObject;
    }

    public void b(String str) {
        this.f10373d = str;
    }

    public void c(String str) {
        this.f10374e = str;
    }
}
