package com.mobile.auth.c;

import com.mobile.auth.gatewayauth.ExceptionProcessor;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class o {

    /* renamed from: a, reason: collision with root package name */
    private static final String f9669a = "o";

    public static String a(int i2, String str) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("result", i2);
            jSONObject.put("msg", str);
            return jSONObject.toString();
        } catch (Throwable th) {
            try {
                com.mobile.auth.a.a.a(f9669a, "Json parse error", th);
                return "";
            } catch (Throwable th2) {
                try {
                    ExceptionProcessor.processException(th2);
                    return null;
                } catch (Throwable th3) {
                    ExceptionProcessor.processException(th3);
                    return null;
                }
            }
        }
    }
}
