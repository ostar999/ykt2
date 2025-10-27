package com.huawei.hms.hatool;

import android.content.Context;
import android.text.TextUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class j0 implements n0 {

    /* renamed from: a, reason: collision with root package name */
    public Context f7763a = b.i();

    /* renamed from: b, reason: collision with root package name */
    public String f7764b;

    /* renamed from: c, reason: collision with root package name */
    public JSONObject f7765c;

    /* renamed from: d, reason: collision with root package name */
    public String f7766d;

    /* renamed from: e, reason: collision with root package name */
    public String f7767e;

    /* renamed from: f, reason: collision with root package name */
    public String f7768f;

    /* renamed from: g, reason: collision with root package name */
    public String f7769g;

    /* renamed from: h, reason: collision with root package name */
    public Boolean f7770h;

    public j0(String str, JSONObject jSONObject, String str2, String str3, long j2) {
        this.f7764b = str;
        this.f7765c = jSONObject;
        this.f7766d = str2;
        this.f7767e = str3;
        this.f7768f = String.valueOf(j2);
        if (a.i(str2, "oper")) {
            f0 f0VarA = e0.a().a(str2, j2);
            this.f7769g = f0VarA.a();
            this.f7770h = Boolean.valueOf(f0VarA.b());
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        JSONArray jSONArray;
        y.c("hmsSdk", "Begin to run EventRecordTask...");
        int iH = b.h();
        int iK = c.k(this.f7766d, this.f7767e);
        if (q0.a(this.f7763a, "stat_v2_1", iH * 1048576)) {
            y.c("hmsSdk", "stat sp file reach max limited size, discard new event");
            h0.a().a("", "alltype");
            return;
        }
        q qVar = new q();
        qVar.b(this.f7764b);
        qVar.a(this.f7765c.toString());
        qVar.d(this.f7767e);
        qVar.c(this.f7768f);
        qVar.f(this.f7769g);
        Boolean bool = this.f7770h;
        qVar.e(bool == null ? null : String.valueOf(bool));
        try {
            JSONObject jSONObjectD = qVar.d();
            String strA = u0.a(this.f7766d, this.f7767e);
            String strA2 = g0.a(this.f7763a, "stat_v2_1", strA, "");
            try {
                jSONArray = !TextUtils.isEmpty(strA2) ? new JSONArray(strA2) : new JSONArray();
            } catch (JSONException unused) {
                y.d("hmsSdk", "Cached data corrupted: stat_v2_1");
                jSONArray = new JSONArray();
            }
            jSONArray.put(jSONObjectD);
            g0.b(this.f7763a, "stat_v2_1", strA, jSONArray.toString());
            if (jSONArray.toString().length() > iK * 1024) {
                h0.a().a(this.f7766d, this.f7767e);
            }
        } catch (JSONException unused2) {
            y.e("hmsSdk", "eventRecord toJson error! The record failed.");
        }
    }
}
