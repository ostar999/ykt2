package com.mobile.auth.i;

import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.mobile.auth.BuildConfig;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class f extends g {

    /* renamed from: a, reason: collision with root package name */
    private b f10381a;

    /* renamed from: b, reason: collision with root package name */
    private a f10382b;

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        private JSONObject f10383a;

        public JSONObject a() {
            return this.f10383a;
        }

        public void a(JSONObject jSONObject) {
            this.f10383a = jSONObject;
        }
    }

    public static class b extends g {

        /* renamed from: a, reason: collision with root package name */
        private String f10384a;

        /* renamed from: b, reason: collision with root package name */
        private String f10385b;

        /* renamed from: c, reason: collision with root package name */
        private String f10386c;

        /* renamed from: d, reason: collision with root package name */
        private String f10387d;

        /* renamed from: e, reason: collision with root package name */
        private String f10388e;

        @Override // com.mobile.auth.i.g
        public String a() {
            return this.f10387d;
        }

        @Override // com.mobile.auth.i.g
        public String a(String str) {
            return this.f10388e + this.f10387d + this.f10386c + this.f10385b + "@Fdiwmxy7CBDDQNUI";
        }

        @Override // com.mobile.auth.i.g
        public JSONObject b() {
            return null;
        }

        public void b(String str) {
            this.f10388e = str;
        }

        public String c() {
            return this.f10388e;
        }

        public void c(String str) {
            this.f10387d = str;
        }

        public String d() {
            return this.f10384a;
        }

        public void d(String str) {
            this.f10384a = str;
        }

        public String e() {
            return this.f10385b;
        }

        public void e(String str) {
            this.f10385b = str;
        }

        public String f() {
            return this.f10386c;
        }

        public void f(String str) {
            this.f10386c = str;
        }
    }

    @Override // com.mobile.auth.i.g
    public String a() {
        return this.f10381a.f10387d;
    }

    @Override // com.mobile.auth.i.g
    public String a(String str) {
        return null;
    }

    public void a(a aVar) {
        this.f10382b = aVar;
    }

    public void a(b bVar) {
        this.f10381a = bVar;
    }

    @Override // com.mobile.auth.i.g
    public JSONObject b() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        JSONObject jSONObject3 = new JSONObject();
        try {
            jSONObject2.put("sign", this.f10381a.d());
            jSONObject2.put("msgid", this.f10381a.e());
            jSONObject2.put("systemtime", this.f10381a.f());
            jSONObject2.put("appid", this.f10381a.a());
            jSONObject2.put("version", this.f10381a.c());
            jSONObject.put("header", jSONObject2);
            jSONObject3.put(BuildConfig.FLAVOR_type, this.f10382b.a());
            jSONObject.put(TtmlNode.TAG_BODY, jSONObject3);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return jSONObject;
    }
}
