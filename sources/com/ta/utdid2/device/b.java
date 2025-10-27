package com.ta.utdid2.device;

import android.content.Context;
import com.ta.a.e.h;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
class b {

    /* renamed from: e, reason: collision with root package name */
    int f17267e = -1;

    public static b a(String str) throws JSONException {
        JSONObject jSONObject;
        b bVar = new b();
        try {
            JSONObject jSONObject2 = new JSONObject(str);
            if (jSONObject2.has("code")) {
                bVar.f17267e = jSONObject2.getInt("code");
            }
            if (jSONObject2.has("data") && (jSONObject = jSONObject2.getJSONObject("data")) != null && jSONObject.has("utdid")) {
                String string = jSONObject.getString("utdid");
                if (c.c(string)) {
                    Context context = com.ta.a.a.a().getContext();
                    com.ta.a.d.e.a(string);
                    com.ta.a.d.e.a(context, string);
                    com.ta.a.d.e.b(string);
                }
            }
            h.m109a("BizResponse", "content", str);
        } catch (JSONException e2) {
            h.m109a("", e2.toString());
        }
        return bVar;
    }

    public static boolean b(int i2) {
        return i2 >= 0 && i2 != 10012;
    }
}
