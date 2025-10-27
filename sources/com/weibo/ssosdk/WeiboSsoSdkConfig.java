package com.weibo.ssosdk;

import android.content.Context;
import android.text.TextUtils;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class WeiboSsoSdkConfig implements Cloneable {
    private Context appContext;
    private String appKey = "";
    private String smid = "";
    private String from = "";
    private String wm = "";
    private String oldwm = "";
    private String sub = "";
    private String smApiKey = "";
    private HashMap<String, String> extra = new HashMap<>();

    private String urlEncode(String str) {
        try {
            return URLEncoder.encode(str, "utf-8");
        } catch (UnsupportedEncodingException unused) {
            return "";
        }
    }

    public void addExtra(String str, String str2) {
        this.extra.put(str, str2);
    }

    public Object clone() {
        try {
            WeiboSsoSdkConfig weiboSsoSdkConfig = (WeiboSsoSdkConfig) super.clone();
            HashMap<String, String> map = new HashMap<>();
            for (Map.Entry<String, String> entry : weiboSsoSdkConfig.extra.entrySet()) {
                map.put(entry.getKey(), entry.getValue());
            }
            weiboSsoSdkConfig.extra = map;
            return weiboSsoSdkConfig;
        } catch (CloneNotSupportedException unused) {
            return null;
        }
    }

    public String getAppKey(boolean z2) {
        return z2 ? urlEncode(this.appKey) : this.appKey;
    }

    public Context getApplicationContext() {
        return this.appContext;
    }

    public String getExtraString(boolean z2) throws JSONException {
        if (this.extra.isEmpty()) {
            return "";
        }
        JSONObject jSONObject = new JSONObject();
        for (Map.Entry<String, String> entry : this.extra.entrySet()) {
            try {
                jSONObject.put(entry.getKey(), entry.getValue());
            } catch (JSONException unused) {
                return "";
            }
        }
        return z2 ? urlEncode(jSONObject.toString()) : jSONObject.toString();
    }

    public String getFrom(boolean z2) {
        return z2 ? urlEncode(this.from) : this.from;
    }

    public String getOldWm(boolean z2) {
        return z2 ? urlEncode(this.oldwm) : this.oldwm;
    }

    public String getSmApiKey() {
        return this.smApiKey;
    }

    public String getSmid(boolean z2) {
        return z2 ? urlEncode(this.smid) : this.smid;
    }

    public String getSub(boolean z2) {
        return z2 ? urlEncode(this.sub) : this.sub;
    }

    public String getWm(boolean z2) {
        return z2 ? urlEncode(this.wm) : this.wm;
    }

    public void setAppKey(String str) {
        this.appKey = str;
    }

    public void setContext(Context context) {
        this.appContext = context.getApplicationContext();
    }

    public void setFrom(String str) {
        this.from = str;
    }

    public void setOldWm(String str) {
        this.oldwm = str;
    }

    public void setSmApiKey(String str) {
        this.smApiKey = str;
    }

    public void setSmid(String str) {
        this.smid = str;
    }

    public void setSub(String str) {
        this.sub = str;
    }

    public void setWm(String str) {
        this.wm = str;
    }

    public boolean verify() {
        return (this.appContext == null || TextUtils.isEmpty(this.appKey) || TextUtils.isEmpty(this.from) || TextUtils.isEmpty(this.wm)) ? false : true;
    }
}
