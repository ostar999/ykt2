package com.mobile.auth.y;

import com.mobile.auth.gatewayauth.ExceptionProcessor;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public final class l {

    /* renamed from: a, reason: collision with root package name */
    public d f10619a = null;

    public final void a(int i2, int i3, String str, String str2, String str3) {
        try {
            t.c("typeTokenUaid=".concat(String.valueOf(i2)));
            try {
                if (this.f10619a == null) {
                    return;
                }
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("resultCode", i3);
                jSONObject.put("resultMsg", str);
                jSONObject.put("resultData", str2);
                jSONObject.put("seq", str3);
                this.f10619a.onResult(jSONObject.toString());
                this.f10619a = null;
            } catch (Exception unused) {
                t.b();
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }
}
