package com.alibaba.sdk.android.httpdns.h;

import java.util.Arrays;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class e {

    /* renamed from: e, reason: collision with root package name */
    private int[] f2805e;

    /* renamed from: e, reason: collision with other field name */
    private String[] f58e;

    /* renamed from: f, reason: collision with root package name */
    private int[] f2806f;

    /* renamed from: g, reason: collision with root package name */
    private String[] f2807g;

    /* renamed from: k, reason: collision with root package name */
    private boolean f2808k;

    public e(boolean z2, String[] strArr, String[] strArr2, int[] iArr, int[] iArr2) {
        this.f2808k = z2;
        this.f58e = strArr;
        this.f2807g = strArr2;
        this.f2805e = iArr;
        this.f2806f = iArr2;
    }

    public static e a(String str) throws JSONException {
        String[] strArr;
        String[] strArr2;
        int[] iArr;
        int[] iArr2;
        JSONObject jSONObject = new JSONObject(str);
        boolean zEquals = jSONObject.has("service_status") ? true ^ jSONObject.optString("service_status").equals("disable") : true;
        if (jSONObject.has("service_ip")) {
            JSONArray jSONArray = jSONObject.getJSONArray("service_ip");
            int length = jSONArray.length();
            strArr = new String[length];
            for (int i2 = 0; i2 < length; i2++) {
                strArr[i2] = jSONArray.getString(i2);
            }
        } else {
            strArr = null;
        }
        if (jSONObject.has("service_ipv6")) {
            JSONArray jSONArray2 = jSONObject.getJSONArray("service_ipv6");
            int length2 = jSONArray2.length();
            strArr2 = new String[length2];
            for (int i3 = 0; i3 < length2; i3++) {
                strArr2[i3] = jSONArray2.getString(i3);
            }
        } else {
            strArr2 = null;
        }
        if (jSONObject.has("service_ip_port")) {
            JSONArray jSONArray3 = jSONObject.getJSONArray("service_ip_port");
            int length3 = jSONArray3.length();
            iArr = new int[length3];
            for (int i4 = 0; i4 < length3; i4++) {
                iArr[i4] = jSONArray3.optInt(i4);
            }
        } else {
            iArr = null;
        }
        if (jSONObject.has("service_ipv6_port")) {
            JSONArray jSONArray4 = jSONObject.getJSONArray("service_ipv6_port");
            int length4 = jSONArray4.length();
            int[] iArr3 = new int[length4];
            for (int i5 = 0; i5 < length4; i5++) {
                iArr3[i5] = jSONArray4.optInt(i5);
            }
            iArr2 = iArr3;
        } else {
            iArr2 = null;
        }
        return new e(zEquals, strArr, strArr2, iArr, iArr2);
    }

    public int[] a() {
        return this.f2805e;
    }

    /* renamed from: a, reason: collision with other method in class */
    public String[] m54a() {
        return this.f58e;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        e eVar = (e) obj;
        return this.f2808k == eVar.f2808k && Arrays.equals(this.f58e, eVar.f58e) && Arrays.equals(this.f2807g, eVar.f2807g) && Arrays.equals(this.f2805e, eVar.f2805e) && Arrays.equals(this.f2806f, eVar.f2806f);
    }

    public boolean f() {
        return this.f2808k;
    }

    public int hashCode() {
        return (((((((Arrays.hashCode(new Object[]{Boolean.valueOf(this.f2808k)}) * 31) + Arrays.hashCode(this.f58e)) * 31) + Arrays.hashCode(this.f2807g)) * 31) + Arrays.hashCode(this.f2805e)) * 31) + Arrays.hashCode(this.f2806f);
    }

    public String toString() {
        return "UpdateServerResponse{enable=" + this.f2808k + ", serverIps=" + Arrays.toString(this.f58e) + ", serverIpv6s=" + Arrays.toString(this.f2807g) + ", serverPorts=" + Arrays.toString(this.f2805e) + ", serverIpv6Ports=" + Arrays.toString(this.f2806f) + '}';
    }
}
