package com.meizu.cloud.pushsdk.a.a;

import com.google.android.exoplayer2.text.ttml.TtmlNode;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    private int f8934a;

    /* renamed from: b, reason: collision with root package name */
    private String f8935b;

    public c(int i2, String str) {
        this.f8934a = i2;
        this.f8935b = str;
    }

    public String toString() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("code", this.f8934a);
            jSONObject.put(TtmlNode.TAG_BODY, this.f8935b);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return "[NetResponse] " + jSONObject.toString();
    }
}
