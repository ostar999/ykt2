package com.alibaba.sdk.android.emas;

import android.text.TextUtils;
import java.nio.charset.Charset;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class e {

    /* renamed from: d, reason: collision with root package name */
    String f2691d;

    /* renamed from: e, reason: collision with root package name */
    String f2692e;
    long timestamp;

    public e(String str, String str2, long j2) {
        this.f2692e = str;
        this.f2691d = str2;
        this.timestamp = j2;
    }

    public JSONObject a() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(com.heytap.mcssdk.constant.b.f7186k, this.f2692e);
            jSONObject.put("rawLog", this.f2691d);
            jSONObject.put("timestamp", this.timestamp);
            return jSONObject;
        } catch (JSONException unused) {
            return null;
        }
    }

    public int length() {
        return this.f2691d.getBytes(Charset.forName("UTF-8")).length;
    }

    public static e a(JSONObject jSONObject) {
        if (jSONObject == null) {
            return null;
        }
        String strOptString = jSONObject.optString(com.heytap.mcssdk.constant.b.f7186k);
        String strOptString2 = jSONObject.optString("rawLog");
        long jOptLong = jSONObject.optLong("timestamp");
        if (TextUtils.isEmpty(strOptString) || TextUtils.isEmpty(strOptString2) || jOptLong == 0) {
            return null;
        }
        return new e(strOptString, strOptString2, jOptLong);
    }
}
