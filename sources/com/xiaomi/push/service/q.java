package com.xiaomi.push.service;

import android.accounts.Account;

/* loaded from: classes6.dex */
class q implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ p f25706a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ Account[] f1083a;

    public q(p pVar, Account[] accountArr) {
        this.f25706a = pVar;
        this.f1083a = accountArr;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f25706a.f25705a.a(this.f1083a);
    }
}
