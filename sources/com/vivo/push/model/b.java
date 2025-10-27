package com.vivo.push.model;

import android.text.TextUtils;

/* loaded from: classes6.dex */
public final class b {

    /* renamed from: a, reason: collision with root package name */
    private String f24396a;

    /* renamed from: d, reason: collision with root package name */
    private String f24399d;

    /* renamed from: b, reason: collision with root package name */
    private long f24397b = -1;

    /* renamed from: c, reason: collision with root package name */
    private int f24398c = -1;

    /* renamed from: e, reason: collision with root package name */
    private boolean f24400e = false;

    /* renamed from: f, reason: collision with root package name */
    private boolean f24401f = false;

    public b(String str) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalAccessError("PushPackageInfo need a non-null pkgName.");
        }
        this.f24396a = str;
    }

    public final String a() {
        return this.f24396a;
    }

    public final long b() {
        return this.f24397b;
    }

    public final boolean c() {
        return this.f24400e;
    }

    public final boolean d() {
        return this.f24401f;
    }

    public final String toString() {
        return "PushPackageInfo{mPackageName=" + this.f24396a + ", mPushVersion=" + this.f24397b + ", mPackageVersion=" + this.f24398c + ", mInBlackList=" + this.f24400e + ", mPushEnable=" + this.f24401f + "}";
    }

    public final void a(long j2) {
        this.f24397b = j2;
    }

    public final void b(boolean z2) {
        this.f24401f = z2;
    }

    public final void a(boolean z2) {
        this.f24400e = z2;
    }

    public final void a(int i2) {
        this.f24398c = i2;
    }

    public final void a(String str) {
        this.f24399d = str;
    }
}
