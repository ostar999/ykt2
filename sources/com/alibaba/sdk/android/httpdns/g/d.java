package com.alibaba.sdk.android.httpdns.g;

/* loaded from: classes2.dex */
public class d {

    /* renamed from: e, reason: collision with root package name */
    private int f2785e;

    /* renamed from: e, reason: collision with other field name */
    private String f54e;

    /* renamed from: n, reason: collision with root package name */
    private String f2786n;

    /* renamed from: o, reason: collision with root package name */
    private String f2787o;
    private int port;

    public d(String str, String str2, int i2, String str3, int i3) {
        this.f54e = str;
        this.f2786n = str2;
        this.port = i2;
        this.f2787o = str3;
        this.f2785e = i3;
    }

    public int getPort() {
        return this.port;
    }

    public int getTimeout() {
        return this.f2785e;
    }

    public void j(String str) {
        this.f2786n = str;
    }

    public String l() {
        return this.f2786n;
    }

    public String m() {
        return this.f54e + this.f2786n + ":" + this.port + this.f2787o;
    }

    public void setPort(int i2) {
        this.port = i2;
    }
}
