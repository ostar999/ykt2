package com.alipay.sdk.packet;

import android.text.TextUtils;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class b {

    /* renamed from: a, reason: collision with root package name */
    String f3281a;

    /* renamed from: b, reason: collision with root package name */
    public String f3282b;

    public b(String str, String str2) {
        this.f3281a = str;
        this.f3282b = str2;
    }

    private void a(String str) {
        this.f3281a = str;
    }

    private String b() {
        return this.f3281a;
    }

    private String c() {
        return this.f3282b;
    }

    public final String toString() {
        return "\nenvelop:" + this.f3281a + "\nbody:" + this.f3282b;
    }

    private void b(String str) {
        this.f3282b = str;
    }

    public final JSONObject a() {
        if (TextUtils.isEmpty(this.f3282b)) {
            return null;
        }
        try {
            return new JSONObject(this.f3282b);
        } catch (Exception unused) {
            return null;
        }
    }
}
