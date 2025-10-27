package com.alipay.android.phone.mrpc.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.Header;

/* loaded from: classes2.dex */
public final class o extends t {

    /* renamed from: b, reason: collision with root package name */
    private String f2973b;

    /* renamed from: c, reason: collision with root package name */
    private byte[] f2974c;

    /* renamed from: g, reason: collision with root package name */
    private boolean f2978g;

    /* renamed from: e, reason: collision with root package name */
    private ArrayList<Header> f2976e = new ArrayList<>();

    /* renamed from: f, reason: collision with root package name */
    private Map<String, String> f2977f = new HashMap();

    /* renamed from: d, reason: collision with root package name */
    private String f2975d = "application/x-www-form-urlencoded";

    public o(String str) {
        this.f2973b = str;
    }

    public final String a() {
        return this.f2973b;
    }

    public final void a(String str) {
        this.f2975d = str;
    }

    public final void a(String str, String str2) {
        if (this.f2977f == null) {
            this.f2977f = new HashMap();
        }
        this.f2977f.put(str, str2);
    }

    public final void a(Header header) {
        this.f2976e.add(header);
    }

    public final void a(boolean z2) {
        this.f2978g = z2;
    }

    public final void a(byte[] bArr) {
        this.f2974c = bArr;
    }

    public final String b(String str) {
        Map<String, String> map = this.f2977f;
        if (map == null) {
            return null;
        }
        return map.get(str);
    }

    public final byte[] b() {
        return this.f2974c;
    }

    public final String c() {
        return this.f2975d;
    }

    public final ArrayList<Header> d() {
        return this.f2976e;
    }

    public final boolean e() {
        return this.f2978g;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || o.class != obj.getClass()) {
            return false;
        }
        o oVar = (o) obj;
        byte[] bArr = this.f2974c;
        if (bArr == null) {
            if (oVar.f2974c != null) {
                return false;
            }
        } else if (!bArr.equals(oVar.f2974c)) {
            return false;
        }
        String str = this.f2973b;
        String str2 = oVar.f2973b;
        if (str == null) {
            if (str2 != null) {
                return false;
            }
        } else if (!str.equals(str2)) {
            return false;
        }
        return true;
    }

    public final int hashCode() {
        Map<String, String> map = this.f2977f;
        int iHashCode = ((map == null || !map.containsKey("id")) ? 1 : this.f2977f.get("id").hashCode() + 31) * 31;
        String str = this.f2973b;
        return iHashCode + (str == null ? 0 : str.hashCode());
    }

    public final String toString() {
        return String.format("Url : %s,HttpHeader: %s", this.f2973b, this.f2976e);
    }
}
