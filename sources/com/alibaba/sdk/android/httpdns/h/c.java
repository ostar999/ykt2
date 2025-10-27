package com.alibaba.sdk.android.httpdns.h;

import java.util.HashMap;

/* loaded from: classes2.dex */
public class c {

    /* renamed from: j, reason: collision with root package name */
    private int f2803j = com.alipay.security.mobile.module.http.constant.a.f3441a;

    /* renamed from: f, reason: collision with root package name */
    private HashMap<String, b> f2802f = new HashMap<>();

    public int[] a(String str) {
        b bVar = this.f2802f.get(com.alibaba.sdk.android.httpdns.j.a.b(str));
        if (bVar == null || bVar.b() + this.f2803j < System.currentTimeMillis()) {
            return null;
        }
        return bVar.a();
    }

    /* renamed from: a, reason: collision with other method in class */
    public String[] m53a(String str) {
        b bVar = this.f2802f.get(com.alibaba.sdk.android.httpdns.j.a.b(str));
        if (bVar == null || bVar.b() + this.f2803j < System.currentTimeMillis()) {
            return null;
        }
        return bVar.m52a();
    }

    public void b(String str, String[] strArr, int[] iArr) {
        String strB = com.alibaba.sdk.android.httpdns.j.a.b(str);
        this.f2802f.put(strB, new b(strB, strArr, iArr));
    }
}
