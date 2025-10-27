package com.alipay.sdk.util;

import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class b {
    public static JSONObject a(JSONObject jSONObject, JSONObject jSONObject2) throws JSONException {
        JSONObject jSONObject3 = new JSONObject();
        try {
            JSONObject[] jSONObjectArr = {jSONObject, jSONObject2};
            for (int i2 = 0; i2 < 2; i2++) {
                JSONObject jSONObject4 = jSONObjectArr[i2];
                if (jSONObject4 != null) {
                    Iterator<String> itKeys = jSONObject4.keys();
                    while (itKeys.hasNext()) {
                        String next = itKeys.next();
                        jSONObject3.put(next, jSONObject4.get(next));
                    }
                }
            }
        } catch (JSONException unused) {
        }
        return jSONObject3;
    }
}
