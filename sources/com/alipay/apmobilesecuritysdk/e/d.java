package com.alipay.apmobilesecuritysdk.e;

import android.content.Context;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class d {
    private static c a(String str) {
        try {
            if (com.alipay.security.mobile.module.a.a.a(str)) {
                return null;
            }
            JSONObject jSONObject = new JSONObject(str);
            return new c(jSONObject.optString("apdid"), jSONObject.optString("deviceInfoHash"), jSONObject.optString("timestamp"), jSONObject.optString(com.alipay.sdk.cons.b.f3217c), jSONObject.optString("utdid"));
        } catch (Exception e2) {
            com.alipay.apmobilesecuritysdk.c.a.a(e2);
            return null;
        }
    }

    public static synchronized void a() {
    }

    public static synchronized void a(Context context) {
        com.alipay.apmobilesecuritysdk.f.a.a(context, "vkeyid_profiles_v4", "key_deviceid_v4", "");
        com.alipay.apmobilesecuritysdk.f.a.a("wxcasxx_v4", "key_wxcasxx_v4", "");
    }

    public static synchronized void a(Context context, c cVar) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("apdid", cVar.a());
            jSONObject.put("deviceInfoHash", cVar.b());
            jSONObject.put("timestamp", cVar.c());
            jSONObject.put(com.alipay.sdk.cons.b.f3217c, cVar.d());
            jSONObject.put("utdid", cVar.e());
            String string = jSONObject.toString();
            com.alipay.apmobilesecuritysdk.f.a.a(context, "vkeyid_profiles_v4", "key_deviceid_v4", string);
            com.alipay.apmobilesecuritysdk.f.a.a("wxcasxx_v4", "key_wxcasxx_v4", string);
        } catch (Exception e2) {
            com.alipay.apmobilesecuritysdk.c.a.a(e2);
        }
    }

    public static synchronized c b() {
        String strA = com.alipay.apmobilesecuritysdk.f.a.a("wxcasxx_v4", "key_wxcasxx_v4");
        if (com.alipay.security.mobile.module.a.a.a(strA)) {
            return null;
        }
        return a(strA);
    }

    public static synchronized c b(Context context) {
        String strA;
        strA = com.alipay.apmobilesecuritysdk.f.a.a(context, "vkeyid_profiles_v4", "key_deviceid_v4");
        if (com.alipay.security.mobile.module.a.a.a(strA)) {
            strA = com.alipay.apmobilesecuritysdk.f.a.a("wxcasxx_v4", "key_wxcasxx_v4");
        }
        return a(strA);
    }

    public static synchronized c c(Context context) {
        String strA = com.alipay.apmobilesecuritysdk.f.a.a(context, "vkeyid_profiles_v4", "key_deviceid_v4");
        if (com.alipay.security.mobile.module.a.a.a(strA)) {
            return null;
        }
        return a(strA);
    }
}
