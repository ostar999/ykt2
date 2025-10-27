package com.alibaba.sdk.android.crashdefend.a;

import com.alibaba.sdk.android.crashdefend.CrashDefendCallback;

/* loaded from: classes2.dex */
public class b implements Cloneable {

    /* renamed from: a, reason: collision with root package name */
    public String f2665a;

    /* renamed from: b, reason: collision with root package name */
    public String f2666b;

    /* renamed from: c, reason: collision with root package name */
    public int f2667c;

    /* renamed from: d, reason: collision with root package name */
    public int f2668d;

    /* renamed from: e, reason: collision with root package name */
    public int f2669e;

    /* renamed from: f, reason: collision with root package name */
    public long f2670f;

    /* renamed from: g, reason: collision with root package name */
    public long f2671g;

    /* renamed from: h, reason: collision with root package name */
    public int f2672h = 0;

    /* renamed from: i, reason: collision with root package name */
    public long f2673i = 0;

    /* renamed from: j, reason: collision with root package name */
    public volatile boolean f2674j = false;

    /* renamed from: k, reason: collision with root package name */
    public CrashDefendCallback f2675k = null;

    public Object clone() {
        try {
            return (b) super.clone();
        } catch (CloneNotSupportedException e2) {
            com.alibaba.sdk.android.crashdefend.c.b.a("CrashSDK", "clone fail: ", e2);
            return null;
        }
    }
}
