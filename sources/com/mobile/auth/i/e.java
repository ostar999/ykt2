package com.mobile.auth.i;

import android.util.Base64;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class e extends g {

    /* renamed from: a, reason: collision with root package name */
    private a f10375a;

    /* renamed from: b, reason: collision with root package name */
    private byte[] f10376b;

    /* renamed from: c, reason: collision with root package name */
    private String f10377c;

    /* renamed from: d, reason: collision with root package name */
    private byte[] f10378d;

    /* renamed from: e, reason: collision with root package name */
    private String f10379e;

    /* renamed from: f, reason: collision with root package name */
    private boolean f10380f = false;

    @Override // com.mobile.auth.i.g
    public String a() {
        return this.f10375a.a();
    }

    @Override // com.mobile.auth.i.g
    public String a(String str) {
        return null;
    }

    public void a(a aVar) {
        this.f10375a = aVar;
    }

    public void a(boolean z2) {
        this.f10380f = z2;
    }

    public void a(byte[] bArr) {
        this.f10376b = bArr;
    }

    @Override // com.mobile.auth.i.g
    public JSONObject b() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (this.f10380f) {
            try {
                jSONObject.put("encrypted", this.f10377c);
                jSONObject.put("encryptedIV", Base64.encodeToString(this.f10378d, 0));
                jSONObject.put("reqdata", com.mobile.auth.l.a.a(this.f10376b, this.f10375a.toString(), this.f10378d));
                jSONObject.put("securityreinforce", this.f10379e);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        return jSONObject;
    }

    public void b(String str) {
        this.f10379e = str;
    }

    public void b(byte[] bArr) {
        this.f10378d = bArr;
    }

    public a c() {
        return this.f10375a;
    }

    public void c(String str) {
        this.f10377c = str;
    }
}
