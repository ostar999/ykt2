package com.huawei.hms.hatool;

import android.content.Context;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public final class i1 {

    /* renamed from: c, reason: collision with root package name */
    public static i1 f7752c;

    /* renamed from: a, reason: collision with root package name */
    public Context f7753a;

    /* renamed from: b, reason: collision with root package name */
    public final Object f7754b = new Object();

    public static i1 a() {
        if (f7752c == null) {
            b();
        }
        return f7752c;
    }

    public static synchronized void b() {
        if (f7752c == null) {
            f7752c = new i1();
        }
    }

    public final JSONObject a(Map<String, String> map) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (map != null) {
            try {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    jSONObject.put(entry.getKey(), entry.getValue());
                }
            } catch (JSONException unused) {
                y.b("hmsSdk", "Exception occured when transferring bundle to json");
            }
        }
        return jSONObject;
    }

    public void a(Context context) {
        synchronized (this.f7754b) {
            if (this.f7753a != null) {
                return;
            }
            this.f7753a = context;
            h0.a().a(context);
        }
    }

    public void a(String str, int i2) {
        h0.a().a(str, i2);
    }

    public void a(String str, int i2, String str2, LinkedHashMap<String, String> linkedHashMap) throws JSONException {
        h0.a().a(str, i2, str2, a(linkedHashMap));
    }

    public void a(String str, Context context, String str2, String str3) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("_constants", str3);
            h0.a().a(str, 0, str2, jSONObject);
        } catch (JSONException unused) {
            y.f("hmsSdk", "onEvent():JSON structure Exception!");
        }
    }
}
