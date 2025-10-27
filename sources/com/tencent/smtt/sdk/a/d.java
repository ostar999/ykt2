package com.tencent.smtt.sdk.a;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class d {

    /* renamed from: a, reason: collision with root package name */
    private int f21127a;

    /* renamed from: b, reason: collision with root package name */
    private long f21128b;

    /* renamed from: c, reason: collision with root package name */
    private List<b> f21129c;

    private d() {
    }

    public static d a(String str) {
        JSONException e2;
        d dVar;
        JSONObject jSONObject;
        if (str == null) {
            return null;
        }
        try {
            jSONObject = new JSONObject(str);
            dVar = new d();
        } catch (JSONException e3) {
            e2 = e3;
            dVar = null;
        }
        try {
            dVar.f21127a = jSONObject.optInt("ret_code", -1);
            dVar.f21128b = jSONObject.optLong("next_req_interval", 1000L);
            JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("cmds");
            if (jSONArrayOptJSONArray != null) {
                dVar.f21129c = new ArrayList();
                for (int i2 = 0; i2 < jSONArrayOptJSONArray.length(); i2++) {
                    b bVarA = b.a(jSONArrayOptJSONArray.optJSONObject(i2));
                    if (bVarA != null) {
                        dVar.f21129c.add(bVarA);
                    }
                }
            }
        } catch (JSONException e4) {
            e2 = e4;
            e2.printStackTrace();
            return dVar;
        }
        return dVar;
    }

    public int a() {
        return this.f21127a;
    }

    public long b() {
        return this.f21128b;
    }

    public List<b> c() {
        return this.f21129c;
    }
}
