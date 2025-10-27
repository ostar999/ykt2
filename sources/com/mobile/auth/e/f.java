package com.mobile.auth.e;

import android.text.TextUtils;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class f {
    public static JSONObject a(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("resultCode", "103000");
            jSONObject.put("desc", k.a.f27523u);
            jSONObject.put("securityphone", str);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return jSONObject;
    }

    public static JSONObject a(String str, com.cmic.sso.sdk.a aVar, JSONObject jSONObject) throws JSONException {
        String[] strArr = {"未知", "移动", "联通", "电信"};
        try {
            String strB = aVar.b("operatortype", "0");
            jSONObject.put("operatorType", ("0".equals(strB) || TextUtils.isEmpty(strB)) ? "103000".equals(str) ? strArr[1] : strArr[0] : strArr[Integer.parseInt(strB)]);
        } catch (Exception e2) {
            Log.e("AuthnResult", "JSONException", e2);
        }
        return jSONObject;
    }

    public static JSONObject a(String str, String str2) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("resultCode", str);
            jSONObject.put("desc", str2);
        } catch (Exception e2) {
            Log.e("AuthnResult", "Exception", e2);
        }
        return jSONObject;
    }

    public static JSONObject a(String str, String str2, com.cmic.sso.sdk.a aVar, JSONObject jSONObject) throws JSONException, NumberFormatException {
        String str3;
        String str4;
        String str5;
        String str6 = "0";
        JSONObject jSONObject2 = new JSONObject();
        try {
            int i2 = Integer.parseInt(aVar.b("authType", "0"));
            int iC = aVar.c("networktype");
            if (i2 == 3) {
                if (iC == 3) {
                    str4 = "WIFI下网关鉴权";
                    str5 = "1";
                } else {
                    str4 = "网关鉴权";
                    str5 = "2";
                }
                String str7 = str5;
                str3 = str4;
                str6 = str7;
            } else {
                str3 = "其他";
            }
            jSONObject2.put("resultCode", str);
            jSONObject2.put("authType", str6);
            jSONObject2.put("authTypeDes", str3);
            if ("103000".equals(str)) {
                if (1 == aVar.c("logintype")) {
                    jSONObject2.put("openId", aVar.b("openId"));
                    jSONObject2.put("securityphone", aVar.b("securityphone"));
                }
                jSONObject2.put("token", jSONObject.optString("token"));
                jSONObject2.put("tokenExpiresIn", jSONObject.optString("tokenExpiresIn"));
            } else {
                jSONObject2.put("desc", str2);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        com.mobile.auth.l.c.b("AuthnResult", "返回参数:" + jSONObject2.toString());
        return jSONObject2;
    }

    public static JSONObject b(String str, String str2) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("resultCode", str);
            jSONObject.put("desc", str2);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return jSONObject;
    }
}
