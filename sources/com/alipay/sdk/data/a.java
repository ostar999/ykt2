package com.alipay.sdk.data;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.sdk.util.i;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class a {

    /* renamed from: a, reason: collision with root package name */
    public static final int f3240a = 3500;

    /* renamed from: b, reason: collision with root package name */
    public static final String f3241b = "http://h5.m.taobao.com/trade/paySuccess.html?bizOrderId=$OrderId$&";

    /* renamed from: c, reason: collision with root package name */
    public static final int f3242c = 1000;

    /* renamed from: d, reason: collision with root package name */
    public static final int f3243d = 20000;

    /* renamed from: e, reason: collision with root package name */
    public static final String f3244e = "alipay_cashier_dynamic_config";

    /* renamed from: f, reason: collision with root package name */
    public static final String f3245f = "timeout";

    /* renamed from: g, reason: collision with root package name */
    public static final String f3246g = "st_sdk_config";

    /* renamed from: h, reason: collision with root package name */
    public static final String f3247h = "tbreturl";

    /* renamed from: k, reason: collision with root package name */
    private static a f3248k;

    /* renamed from: i, reason: collision with root package name */
    int f3249i = 3500;

    /* renamed from: j, reason: collision with root package name */
    public String f3250j = f3241b;

    public static a b() {
        if (f3248k == null) {
            a aVar = new a();
            f3248k = aVar;
            String strB = i.b(com.alipay.sdk.sys.b.a().f3333a, f3244e, null);
            if (!TextUtils.isEmpty(strB)) {
                try {
                    JSONObject jSONObject = new JSONObject(strB);
                    aVar.f3249i = jSONObject.optInt("timeout", 3500);
                    aVar.f3250j = jSONObject.optString(f3247h, f3241b).trim();
                } catch (Throwable unused) {
                }
            }
        }
        return f3248k;
    }

    private String c() {
        return this.f3250j;
    }

    private void d() {
        String strB = i.b(com.alipay.sdk.sys.b.a().f3333a, f3244e, null);
        if (TextUtils.isEmpty(strB)) {
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject(strB);
            this.f3249i = jSONObject.optInt("timeout", 3500);
            this.f3250j = jSONObject.optString(f3247h, f3241b).trim();
        } catch (Throwable unused) {
        }
    }

    private void e() throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("timeout", a());
            jSONObject.put(f3247h, this.f3250j);
            i.a(com.alipay.sdk.sys.b.a().f3333a, f3244e, jSONObject.toString());
        } catch (Exception unused) {
        }
    }

    public final int a() {
        int i2 = this.f3249i;
        if (i2 < 1000 || i2 > 20000) {
            return 3500;
        }
        return this.f3249i;
    }

    private void a(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            this.f3249i = jSONObject.optInt("timeout", 3500);
            this.f3250j = jSONObject.optString(f3247h, f3241b).trim();
        } catch (Throwable unused) {
        }
    }

    public final void a(Context context) {
        new Thread(new b(this, context)).start();
    }

    private static /* synthetic */ void a(a aVar, String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        try {
            JSONObject jSONObjectOptJSONObject = new JSONObject(str).optJSONObject(f3246g);
            aVar.f3249i = jSONObjectOptJSONObject.optInt("timeout", 3500);
            aVar.f3250j = jSONObjectOptJSONObject.optString(f3247h, f3241b).trim();
        } catch (Throwable unused) {
        }
    }

    private void b(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        try {
            JSONObject jSONObjectOptJSONObject = new JSONObject(str).optJSONObject(f3246g);
            this.f3249i = jSONObjectOptJSONObject.optInt("timeout", 3500);
            this.f3250j = jSONObjectOptJSONObject.optString(f3247h, f3241b).trim();
        } catch (Throwable unused) {
        }
    }

    private static /* synthetic */ void a(a aVar) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("timeout", aVar.a());
            jSONObject.put(f3247h, aVar.f3250j);
            i.a(com.alipay.sdk.sys.b.a().f3333a, f3244e, jSONObject.toString());
        } catch (Exception unused) {
        }
    }
}
