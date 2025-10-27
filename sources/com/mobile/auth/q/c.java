package com.mobile.auth.q;

import com.mobile.auth.gatewayauth.ExceptionProcessor;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class c implements com.mobile.auth.e.b {

    /* renamed from: a, reason: collision with root package name */
    private Map<String, String> f10522a = new HashMap();

    /* renamed from: b, reason: collision with root package name */
    private com.mobile.auth.e.b f10523b;

    public c(com.mobile.auth.e.b bVar) {
        this.f10523b = bVar;
    }

    public Map<String, String> a() {
        try {
            return this.f10522a;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    @Override // com.mobile.auth.e.b
    public void a(int i2, JSONObject jSONObject) {
        try {
            Map<String, String> map = this.f10522a;
            if (map != null && !map.isEmpty()) {
                if (jSONObject == null) {
                    jSONObject = new JSONObject();
                }
                for (Map.Entry<String, String> entry : this.f10522a.entrySet()) {
                    try {
                        jSONObject.put(entry.getKey(), entry.getValue());
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                }
            }
            com.mobile.auth.e.b bVar = this.f10523b;
            if (bVar != null) {
                bVar.a(i2, jSONObject);
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }
}
