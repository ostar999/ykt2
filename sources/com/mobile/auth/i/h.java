package com.mobile.auth.i;

import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class h extends a {

    /* renamed from: x, reason: collision with root package name */
    protected String f10389x = "";

    /* renamed from: y, reason: collision with root package name */
    protected String f10390y = "";

    @Override // com.mobile.auth.i.g
    public String a(String str) {
        return this.f10340b + this.f10341c + this.f10342d + this.f10343e + this.f10344f + this.f10345g + this.f10346h + this.f10347i + this.f10348j + this.f10351m + this.f10352n + str + this.f10353o + this.f10355q + this.f10356r + this.f10357s + this.f10358t + this.f10359u + this.f10360v + this.f10389x + this.f10390y + this.f10361w;
    }

    @Override // com.mobile.auth.i.a
    public void a_(String str) {
        this.f10360v = t(str);
    }

    @Override // com.mobile.auth.i.g
    public JSONObject b() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("ver", this.f10339a);
            jSONObject.put("sdkver", this.f10340b);
            jSONObject.put("appid", this.f10341c);
            jSONObject.put("imsi", this.f10342d);
            jSONObject.put("operatortype", this.f10343e);
            jSONObject.put("networktype", this.f10344f);
            jSONObject.put("mobilebrand", this.f10345g);
            jSONObject.put("mobilemodel", this.f10346h);
            jSONObject.put("mobilesystem", this.f10347i);
            jSONObject.put("clienttype", this.f10348j);
            jSONObject.put("interfacever", this.f10349k);
            jSONObject.put("expandparams", this.f10350l);
            jSONObject.put("msgid", this.f10351m);
            jSONObject.put("timestamp", this.f10352n);
            jSONObject.put("subimsi", this.f10353o);
            jSONObject.put("sign", this.f10354p);
            jSONObject.put("apppackage", this.f10355q);
            jSONObject.put("appsign", this.f10356r);
            jSONObject.put("ipv4_list", this.f10357s);
            jSONObject.put("ipv6_list", this.f10358t);
            jSONObject.put("sdkType", this.f10359u);
            jSONObject.put("tempPDR", this.f10360v);
            jSONObject.put("scrip", this.f10389x);
            jSONObject.put("userCapaid", this.f10390y);
            jSONObject.put("funcType", this.f10361w);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return jSONObject;
    }

    public String toString() {
        return this.f10339a + "&" + this.f10340b + "&" + this.f10341c + "&" + this.f10342d + "&" + this.f10343e + "&" + this.f10344f + "&" + this.f10345g + "&" + this.f10346h + "&" + this.f10347i + "&" + this.f10348j + "&" + this.f10349k + "&" + this.f10350l + "&" + this.f10351m + "&" + this.f10352n + "&" + this.f10353o + "&" + this.f10354p + "&" + this.f10355q + "&" + this.f10356r + "&&" + this.f10357s + "&" + this.f10358t + "&" + this.f10359u + "&" + this.f10360v + "&" + this.f10389x + "&" + this.f10390y + "&" + this.f10361w;
    }

    public void v(String str) {
        this.f10389x = t(str);
    }

    public void w(String str) {
        this.f10390y = t(str);
    }
}
