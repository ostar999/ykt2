package com.alibaba.sdk.android.httpdns.e;

import com.huawei.hms.push.constant.RemoteMessageConst;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.util.Arrays;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class f {

    /* renamed from: a, reason: collision with root package name */
    private int f2739a;

    /* renamed from: b, reason: collision with root package name */
    private String f2740b;

    /* renamed from: f, reason: collision with root package name */
    private String[] f2741f;
    private String hostName;
    private String[] ips;

    public f(String str, String[] strArr, String[] strArr2, int i2, String str2) {
        this.hostName = str;
        if (strArr != null) {
            this.ips = strArr;
        } else {
            this.ips = new String[0];
        }
        if (strArr2 != null) {
            this.f2741f = strArr2;
        } else {
            this.f2741f = new String[0];
        }
        if (i2 > 0) {
            this.f2739a = i2;
        } else {
            this.f2739a = 60;
        }
        this.f2740b = str2;
    }

    public static f a(String str) throws JSONException {
        String[] strArr;
        String[] strArr2;
        JSONArray jSONArray;
        JSONObject jSONObject = new JSONObject(str);
        String string = jSONObject.getString(com.alipay.sdk.cons.c.f3231f);
        String string2 = null;
        try {
            if (jSONObject.has("ips")) {
                JSONArray jSONArray2 = jSONObject.getJSONArray("ips");
                int length = jSONArray2.length();
                strArr2 = new String[length];
                for (int i2 = 0; i2 < length; i2++) {
                    try {
                        strArr2[i2] = jSONArray2.getString(i2);
                    } catch (Exception e2) {
                        e = e2;
                        strArr = null;
                        e.printStackTrace();
                        return new f(string, strArr2, strArr, jSONObject.getInt(RemoteMessageConst.TTL), string2);
                    }
                }
            } else {
                strArr2 = null;
            }
            if (!jSONObject.has("ipsv6") || (jSONArray = jSONObject.getJSONArray("ipsv6")) == null || jSONArray.length() == 0) {
                strArr = null;
            } else {
                strArr = new String[jSONArray.length()];
                for (int i3 = 0; i3 < jSONArray.length(); i3++) {
                    try {
                        strArr[i3] = jSONArray.getString(i3);
                    } catch (Exception e3) {
                        e = e3;
                        e.printStackTrace();
                        return new f(string, strArr2, strArr, jSONObject.getInt(RemoteMessageConst.TTL), string2);
                    }
                }
            }
            if (jSONObject.has(PushConstants.EXTRA)) {
                string2 = jSONObject.getString(PushConstants.EXTRA);
            }
        } catch (Exception e4) {
            e = e4;
            strArr = null;
            strArr2 = null;
        }
        return new f(string, strArr2, strArr, jSONObject.getInt(RemoteMessageConst.TTL), string2);
    }

    public int a() {
        return this.f2739a;
    }

    public String[] b() {
        return this.f2741f;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        f fVar = (f) obj;
        return this.f2739a == fVar.f2739a && this.hostName.equals(fVar.hostName) && Arrays.equals(this.ips, fVar.ips) && Arrays.equals(this.f2741f, fVar.f2741f) && com.alibaba.sdk.android.httpdns.j.a.equals(this.f2740b, fVar.f2740b);
    }

    public String[] getIps() {
        return this.ips;
    }

    public String h() {
        return this.f2740b;
    }

    public int hashCode() {
        return (((Arrays.hashCode(new Object[]{this.hostName, Integer.valueOf(this.f2739a), this.f2740b}) * 31) + Arrays.hashCode(this.ips)) * 31) + Arrays.hashCode(this.f2741f);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("host: ");
        sb.append(this.hostName);
        sb.append(" ip cnt: ");
        String[] strArr = this.ips;
        sb.append(strArr != null ? strArr.length : 0);
        sb.append(" ttl: ");
        sb.append(this.f2739a);
        String string = sb.toString();
        if (this.ips != null) {
            for (int i2 = 0; i2 < this.ips.length; i2++) {
                string = string + "\n ip: " + this.ips[i2];
            }
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(string);
        sb2.append("\n ipv6 cnt: ");
        String[] strArr2 = this.f2741f;
        sb2.append(strArr2 != null ? strArr2.length : 0);
        String string2 = sb2.toString();
        if (this.f2741f != null) {
            for (int i3 = 0; i3 < this.f2741f.length; i3++) {
                string2 = string2 + "\n ipv6: " + this.f2741f[i3];
            }
        }
        return string2 + "\n extra: " + this.f2740b;
    }
}
