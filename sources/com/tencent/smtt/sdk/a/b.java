package com.tencent.smtt.sdk.a;

import cn.hutool.core.text.CharPool;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    private int f21115a;

    /* renamed from: b, reason: collision with root package name */
    private int f21116b;

    /* renamed from: c, reason: collision with root package name */
    private String f21117c;

    /* renamed from: d, reason: collision with root package name */
    private long f21118d;

    private b() {
    }

    public static b a(JSONObject jSONObject) {
        if (jSONObject == null) {
            return null;
        }
        b bVar = new b();
        bVar.f21115a = jSONObject.optInt("id", -1);
        bVar.f21116b = jSONObject.optInt("cmd_id", -1);
        bVar.f21117c = jSONObject.optString("ext_params", "");
        bVar.f21118d = jSONObject.optLong("expiration", 0L) * 1000;
        return bVar;
    }

    public int a() {
        return this.f21115a;
    }

    public int b() {
        return this.f21116b;
    }

    public String c() {
        return this.f21117c;
    }

    public long d() {
        return this.f21118d;
    }

    public boolean e() {
        return System.currentTimeMillis() > this.f21118d;
    }

    public String toString() {
        return "[id=" + this.f21115a + ", cmd=" + this.f21116b + ", extra='" + this.f21117c + CharPool.SINGLE_QUOTE + ", expiration=" + a.a(this.f21118d) + ']';
    }
}
