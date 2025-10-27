package com.unity3d.player;

import android.graphics.BitmapFactory;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public final class j {

    /* renamed from: a, reason: collision with root package name */
    private String f24124a;

    /* renamed from: b, reason: collision with root package name */
    private String f24125b;

    /* renamed from: c, reason: collision with root package name */
    private String[] f24126c;

    /* renamed from: d, reason: collision with root package name */
    private String[] f24127d;

    /* renamed from: e, reason: collision with root package name */
    private int f24128e;

    /* renamed from: f, reason: collision with root package name */
    private long f24129f;

    /* renamed from: g, reason: collision with root package name */
    private String[] f24130g;

    /* renamed from: h, reason: collision with root package name */
    private String[] f24131h;

    /* renamed from: i, reason: collision with root package name */
    private String f24132i;

    /* renamed from: j, reason: collision with root package name */
    private String f24133j;

    /* renamed from: k, reason: collision with root package name */
    private String[] f24134k;

    public j(JSONObject jSONObject) {
        this.f24124a = jSONObject.optString("imageUrl");
        this.f24125b = jSONObject.optString("clickUrl", "");
        this.f24128e = jSONObject.optInt("duration", 5);
        this.f24129f = jSONObject.optLong("expiration", 0L);
        this.f24126c = a(jSONObject.optJSONArray("impression"));
        this.f24127d = a(jSONObject.optJSONArray("clickImpression"));
        this.f24130g = a(jSONObject.optJSONArray("primaryClickImpression"));
        this.f24131h = a(jSONObject.optJSONArray("fallbackClickImpression"));
        this.f24132i = jSONObject.optString("mediaType");
        this.f24133j = jSONObject.optString("videoUrl");
        this.f24134k = a(jSONObject.optJSONArray("completeClickImpression"));
    }

    private static String[] a(JSONArray jSONArray) {
        if (jSONArray == null) {
            return null;
        }
        int length = jSONArray.length();
        String[] strArr = new String[length];
        for (int i2 = 0; i2 < length; i2++) {
            strArr[i2] = jSONArray.optString(i2);
        }
        return strArr;
    }

    public final boolean a() {
        if ("VIDEO".equals(j())) {
            return (k() == null || k() == "") ? false : true;
        }
        String strB = b();
        if (strB == null || strB == "") {
            return false;
        }
        if (strB.startsWith("file://")) {
            strB = strB.substring(7);
        }
        return BitmapFactory.decodeFile(strB) != null;
    }

    public final String b() {
        return this.f24124a;
    }

    public final String c() {
        return this.f24125b;
    }

    public final String[] d() {
        return this.f24126c;
    }

    public final String[] e() {
        return this.f24127d;
    }

    public final int f() {
        return this.f24128e;
    }

    public final long g() {
        return this.f24129f;
    }

    public final String[] h() {
        return this.f24130g;
    }

    public final String[] i() {
        return this.f24131h;
    }

    public final String j() {
        return this.f24132i;
    }

    public final String k() {
        return this.f24133j;
    }

    public final String[] l() {
        return this.f24134k;
    }
}
