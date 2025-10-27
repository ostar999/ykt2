package com.alibaba.sdk.android.httpdns.h;

/* loaded from: classes2.dex */
public class b {

    /* renamed from: e, reason: collision with root package name */
    private int[] f2800e;

    /* renamed from: e, reason: collision with other field name */
    private String[] f56e;

    /* renamed from: f, reason: collision with root package name */
    private long f2801f;

    /* renamed from: f, reason: collision with other field name */
    private String f57f;

    public b(String str, long j2, String[] strArr, int[] iArr) {
        this.f57f = str;
        this.f2801f = j2;
        this.f56e = strArr;
        this.f2800e = iArr;
    }

    public b(String str, String[] strArr, int[] iArr) {
        this(str, System.currentTimeMillis(), strArr, iArr);
    }

    public int[] a() {
        return this.f2800e;
    }

    /* renamed from: a, reason: collision with other method in class */
    public String[] m52a() {
        return this.f56e;
    }

    public long b() {
        return this.f2801f;
    }
}
