package com.tencent.smtt.sdk.a;

import android.text.TextUtils;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    private String f21119a;

    /* renamed from: b, reason: collision with root package name */
    private String f21120b;

    /* renamed from: c, reason: collision with root package name */
    private Integer f21121c;

    /* renamed from: d, reason: collision with root package name */
    private String f21122d;

    /* renamed from: e, reason: collision with root package name */
    private String f21123e;

    /* renamed from: f, reason: collision with root package name */
    private Integer f21124f;

    /* renamed from: g, reason: collision with root package name */
    private Integer f21125g;

    /* renamed from: h, reason: collision with root package name */
    private List<Integer> f21126h;

    public String a() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        try {
            if (!TextUtils.isEmpty(this.f21119a)) {
                jSONObject2.put("PP", this.f21119a);
            }
            if (!TextUtils.isEmpty(this.f21120b)) {
                jSONObject2.put("PPVN", this.f21120b);
            }
            Integer num = this.f21121c;
            if (num != null) {
                jSONObject2.put("ADRV", num);
            }
            if (!TextUtils.isEmpty(this.f21122d)) {
                jSONObject2.put("MODEL", this.f21122d);
            }
            if (!TextUtils.isEmpty(this.f21123e)) {
                jSONObject2.put("NAME", this.f21123e);
            }
            Integer num2 = this.f21124f;
            if (num2 != null) {
                jSONObject2.put("SDKVC", num2);
            }
            Integer num3 = this.f21125g;
            if (num3 != null) {
                jSONObject2.put("COMPVC", num3);
            }
            jSONObject.put("terminal_params", jSONObject2);
            if (this.f21126h != null) {
                JSONArray jSONArray = new JSONArray();
                for (int i2 = 0; i2 < this.f21126h.size(); i2++) {
                    jSONArray.put(this.f21126h.get(i2));
                }
                jSONObject.put("ids", jSONArray);
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return jSONObject.toString();
    }

    public void a(Integer num) {
        this.f21121c = num;
    }

    public void a(String str) {
        this.f21119a = str;
    }

    public void a(List<Integer> list) {
        this.f21126h = list;
    }

    public void b(Integer num) {
        this.f21124f = num;
    }

    public void b(String str) {
        this.f21120b = str;
    }

    public void c(Integer num) {
        this.f21125g = num;
    }

    public void c(String str) {
        this.f21122d = str;
    }

    public void d(String str) {
        this.f21123e = str;
    }
}
