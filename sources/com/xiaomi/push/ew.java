package com.xiaomi.push;

import android.content.Context;

/* loaded from: classes6.dex */
final class ew implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ int f24832a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ Context f409a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ String f410a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ String f24833b;

    public ew(Context context, String str, int i2, String str2) {
        this.f409a = context;
        this.f410a = str;
        this.f24832a = i2;
        this.f24833b = str2;
    }

    @Override // java.lang.Runnable
    public void run() {
        ev.c(this.f409a, this.f410a, this.f24832a, this.f24833b);
    }
}
