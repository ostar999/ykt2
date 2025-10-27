package com.tencent.tbs.one.impl.a;

import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public final class f {
    public static JSONObject a(Map map) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (map != null) {
            for (Object obj : map.keySet()) {
                a(jSONObject, obj.toString(), map.get(obj));
            }
        }
        return jSONObject;
    }

    public static <T> void a(JSONObject jSONObject, String str, T t2) throws JSONException {
        try {
            jSONObject.put(str, t2);
        } catch (JSONException unused) {
        }
    }
}
