package com.alibaba.sdk.android.httpdns.e;

import com.huawei.hms.push.constant.RemoteMessageConst;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class k {

    /* renamed from: d, reason: collision with root package name */
    private HashMap<String, a> f2763d;

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        private int f2764a;
        private String host;
        private String[] ips;
        private String[] ipv6s;

        public a(String str, String[] strArr, String[] strArr2, int i2) {
            this.host = str;
            this.ips = strArr;
            this.ipv6s = strArr2;
            if (i2 <= 0) {
                this.f2764a = 60;
            } else {
                this.f2764a = i2;
            }
        }

        public int a() {
            return this.f2764a;
        }

        public String[] getIps() {
            return this.ips;
        }

        public String[] getIpv6s() {
            return this.ipv6s;
        }
    }

    private k(HashMap<String, a> map) {
        new HashMap();
        this.f2763d = map;
    }

    public static k a(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject(str);
        if (!jSONObject.has("dns")) {
            return null;
        }
        JSONArray jSONArray = jSONObject.getJSONArray("dns");
        HashMap map = new HashMap();
        String[] strArr = null;
        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
            JSONObject jSONObject2 = jSONArray.getJSONObject(i2);
            String string = jSONObject2.getString(com.alipay.sdk.cons.c.f3231f);
            int i3 = jSONObject2.getInt("type");
            int i4 = jSONObject2.getInt(RemoteMessageConst.TTL);
            if (jSONObject2.has("ips")) {
                JSONArray jSONArray2 = jSONObject2.getJSONArray("ips");
                int length = jSONArray2.length();
                String[] strArr2 = new String[length];
                for (int i5 = 0; i5 < length; i5++) {
                    strArr2[i5] = jSONArray2.getString(i5);
                }
                strArr = strArr2;
            }
            a aVar = (a) map.get(string);
            if (aVar == null) {
                aVar = new a(string, null, null, i4);
                map.put(string, aVar);
            }
            if (i3 == 1) {
                aVar.ips = strArr;
            } else if (i3 == 28) {
                aVar.ipv6s = strArr;
            }
        }
        return new k(map);
    }

    /* renamed from: a, reason: collision with other method in class */
    public a m46a(String str) {
        return this.f2763d.get(str);
    }

    public List<String> b() {
        return new ArrayList(this.f2763d.keySet());
    }
}
