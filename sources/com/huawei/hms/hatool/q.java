package com.huawei.hms.hatool;

import android.text.TextUtils;
import androidx.core.app.NotificationCompat;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class q implements s {

    /* renamed from: a, reason: collision with root package name */
    public String f7852a;

    /* renamed from: b, reason: collision with root package name */
    public String f7853b;

    /* renamed from: c, reason: collision with root package name */
    public String f7854c;

    /* renamed from: d, reason: collision with root package name */
    public String f7855d;

    /* renamed from: e, reason: collision with root package name */
    public String f7856e;

    /* renamed from: f, reason: collision with root package name */
    public String f7857f;

    @Override // com.huawei.hms.hatool.s
    public JSONObject a() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("type", this.f7852a);
        jSONObject.put("eventtime", this.f7855d);
        jSONObject.put(NotificationCompat.CATEGORY_EVENT, this.f7853b);
        jSONObject.put("event_session_name", this.f7856e);
        jSONObject.put("first_session_event", this.f7857f);
        if (TextUtils.isEmpty(this.f7854c)) {
            return null;
        }
        jSONObject.put("properties", new JSONObject(this.f7854c));
        return jSONObject;
    }

    public void a(String str) {
        this.f7854c = str;
    }

    public void a(JSONObject jSONObject) {
        if (jSONObject == null) {
            return;
        }
        this.f7853b = jSONObject.optString(NotificationCompat.CATEGORY_EVENT);
        this.f7854c = jSONObject.optString("properties");
        this.f7854c = d.a(this.f7854c, d0.f().a());
        this.f7852a = jSONObject.optString("type");
        this.f7855d = jSONObject.optString("eventtime");
        this.f7856e = jSONObject.optString("event_session_name");
        this.f7857f = jSONObject.optString("first_session_event");
    }

    public String b() {
        return this.f7855d;
    }

    public void b(String str) {
        this.f7853b = str;
    }

    public String c() {
        return this.f7852a;
    }

    public void c(String str) {
        this.f7855d = str;
    }

    public JSONObject d() throws JSONException {
        JSONObject jSONObjectA = a();
        jSONObjectA.put("properties", d.b(this.f7854c, d0.f().a()));
        return jSONObjectA;
    }

    public void d(String str) {
        this.f7852a = str;
    }

    public void e(String str) {
        this.f7857f = str;
    }

    public void f(String str) {
        this.f7856e = str;
    }
}
