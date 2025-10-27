package com.huawei.hms.hatool;

import android.os.Build;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class g1 extends p {

    /* renamed from: f, reason: collision with root package name */
    public String f7735f;

    /* renamed from: g, reason: collision with root package name */
    public String f7736g;

    /* renamed from: h, reason: collision with root package name */
    public String f7737h;

    @Override // com.huawei.hms.hatool.s
    public JSONObject a() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("_rom_ver", this.f7737h);
        jSONObject.put("_emui_ver", this.f7845a);
        jSONObject.put("_model", Build.MODEL);
        jSONObject.put("_mcc", this.f7735f);
        jSONObject.put("_mnc", this.f7736g);
        jSONObject.put("_package_name", this.f7846b);
        jSONObject.put("_app_ver", this.f7847c);
        jSONObject.put("_lib_ver", "2.2.0.313");
        jSONObject.put("_channel", this.f7848d);
        jSONObject.put("_lib_name", "hianalytics");
        jSONObject.put("_oaid_tracking_flag", this.f7849e);
        return jSONObject;
    }

    public void f(String str) {
        this.f7735f = str;
    }

    public void g(String str) {
        this.f7736g = str;
    }

    public void h(String str) {
        this.f7737h = str;
    }
}
