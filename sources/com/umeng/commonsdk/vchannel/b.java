package com.umeng.commonsdk.vchannel;

import android.content.Context;
import cn.hutool.core.text.StrPool;
import com.umeng.commonsdk.service.UMGlobalContext;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class b {

    /* renamed from: b, reason: collision with root package name */
    private String f23555b;

    /* renamed from: a, reason: collision with root package name */
    private String f23554a = "_$unknown";

    /* renamed from: c, reason: collision with root package name */
    private long f23556c = 0;

    /* renamed from: d, reason: collision with root package name */
    private long f23557d = 0;

    /* renamed from: e, reason: collision with root package name */
    private String f23558e = a.f23553j;

    /* renamed from: f, reason: collision with root package name */
    private Map<String, Object> f23559f = null;

    public b(Context context) {
        this.f23555b = UMGlobalContext.getInstance(context).getProcessName(context);
    }

    public String a() {
        return this.f23554a;
    }

    public long b() {
        return this.f23556c;
    }

    public Map<String, Object> c() {
        return this.f23559f;
    }

    public JSONObject d() {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("id", this.f23554a);
            jSONObject.put("pn", this.f23555b);
            jSONObject.put("ds", this.f23557d);
            jSONObject.put("ts", this.f23556c);
            Map<String, Object> map = this.f23559f;
            if (map != null && map.size() > 0) {
                for (String str : this.f23559f.keySet()) {
                    jSONObject.put(str, this.f23559f.get(str));
                }
            }
            JSONArray jSONArray = new JSONArray();
            jSONArray.put(jSONObject);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put(this.f23558e, jSONArray);
            JSONArray jSONArray2 = new JSONArray();
            jSONArray2.put(jSONObject2);
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put("ekv", jSONArray2);
            return jSONObject3;
        } catch (Throwable unused) {
            return null;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(StrPool.BRACKET_START);
        sb.append("id:" + this.f23554a + ",");
        sb.append("pn:" + this.f23555b + ",");
        sb.append("ts:" + this.f23556c + ",");
        Map<String, Object> map = this.f23559f;
        if (map != null && map.size() > 0) {
            for (String str : this.f23559f.keySet()) {
                Object obj = this.f23559f.get(str);
                sb.append(obj == null ? str + ": null," : str + ": " + obj.toString() + ",");
            }
        }
        sb.append("ds:" + this.f23557d + StrPool.BRACKET_END);
        return sb.toString();
    }

    public void a(String str) {
        this.f23554a = str;
    }

    public void a(long j2) {
        this.f23556c = j2;
    }

    public void a(Map<String, Object> map) {
        this.f23559f = map;
    }
}
