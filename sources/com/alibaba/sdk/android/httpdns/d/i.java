package com.alibaba.sdk.android.httpdns.d;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

/* loaded from: classes2.dex */
public class i {

    /* renamed from: c, reason: collision with root package name */
    private long f2726c = 0;

    /* renamed from: g, reason: collision with root package name */
    private String f2727g;

    public i(String str) {
        this.f2727g = str;
    }

    public HashMap<String, String> a(String str) {
        if (this.f2727g == null) {
            return null;
        }
        String string = Long.toString((System.currentTimeMillis() / 1000) + 600 + this.f2726c);
        try {
            String strC = com.alibaba.sdk.android.httpdns.j.a.c(str + "-" + this.f2727g + "-" + string);
            HashMap<String, String> map = new HashMap<>();
            map.put("t", string);
            map.put("s", strC);
            return map;
        } catch (NoSuchAlgorithmException unused) {
            return null;
        }
    }

    public void c(long j2) {
        this.f2726c = j2 - (System.currentTimeMillis() / 1000);
    }

    public void c(String str) {
        this.f2727g = str;
    }
}
