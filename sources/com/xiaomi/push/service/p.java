package com.xiaomi.push.service;

import android.accounts.Account;
import android.accounts.OnAccountsUpdateListener;

/* loaded from: classes6.dex */
class p implements OnAccountsUpdateListener {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ o f25705a;

    public p(o oVar) {
        this.f25705a = oVar;
    }

    @Override // android.accounts.OnAccountsUpdateListener
    public void onAccountsUpdated(Account[] accountArr) {
        com.xiaomi.push.ai.a(this.f25705a.f1080a).a(new q(this, accountArr));
    }
}
