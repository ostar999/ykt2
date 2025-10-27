package com.alibaba.sdk.android.httpdns.d;

import com.alibaba.sdk.android.httpdns.RequestIpType;
import java.util.HashSet;

/* loaded from: classes2.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    private HashSet<String> f2711a = new HashSet<>();

    public void a(String str, RequestIpType requestIpType) {
        a(str, requestIpType, (String) null);
    }

    public synchronized void a(String str, RequestIpType requestIpType, String str2) {
        this.f2711a.remove(com.alibaba.sdk.android.httpdns.j.a.b(str, requestIpType, str2));
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m33a(String str, RequestIpType requestIpType) {
        return m34a(str, requestIpType, (String) null);
    }

    /* renamed from: a, reason: collision with other method in class */
    public synchronized boolean m34a(String str, RequestIpType requestIpType, String str2) {
        String strB = com.alibaba.sdk.android.httpdns.j.a.b(str, requestIpType, str2);
        RequestIpType requestIpType2 = RequestIpType.both;
        boolean z2 = true;
        if (requestIpType != requestIpType2) {
            String strB2 = com.alibaba.sdk.android.httpdns.j.a.b(str, requestIpType2, str2);
            if (this.f2711a.contains(strB) || this.f2711a.contains(strB2)) {
                z2 = false;
            }
            if (z2) {
                this.f2711a.add(strB);
            }
            return z2;
        }
        String strB3 = com.alibaba.sdk.android.httpdns.j.a.b(str, RequestIpType.v4, str2);
        String strB4 = com.alibaba.sdk.android.httpdns.j.a.b(str, RequestIpType.v6, str2);
        if (this.f2711a.contains(strB) || (this.f2711a.contains(strB3) && this.f2711a.contains(strB4))) {
            z2 = false;
        }
        if (z2) {
            this.f2711a.add(strB);
        }
        return z2;
    }
}
