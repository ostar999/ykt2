package com.xiaomi.clientreport.data;

import com.vivo.push.PushClientConstants;
import com.xiaomi.push.be;
import com.xiaomi.push.n;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class a {
    public String clientInterfaceId;
    private String pkgName;
    public int production;
    public int reportType;
    private String os = be.a();
    private String miuiVersion = n.m676a();

    public String getPackageName() {
        return this.pkgName;
    }

    public void setAppPackageName(String str) {
        this.pkgName = str;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("production", this.production);
            jSONObject.put("reportType", this.reportType);
            jSONObject.put("clientInterfaceId", this.clientInterfaceId);
            jSONObject.put("os", this.os);
            jSONObject.put("miuiVersion", this.miuiVersion);
            jSONObject.put(PushClientConstants.TAG_PKG_NAME, this.pkgName);
            return jSONObject;
        } catch (JSONException e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
            return null;
        }
    }

    public String toJsonString() throws JSONException {
        JSONObject json = toJson();
        return json == null ? "" : json.toString();
    }
}
