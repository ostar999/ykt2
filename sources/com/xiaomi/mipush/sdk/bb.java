package com.xiaomi.mipush.sdk;

import android.database.ContentObserver;
import android.os.Handler;

/* loaded from: classes6.dex */
class bb extends ContentObserver {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ az f24543a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public bb(az azVar, Handler handler) {
        super(handler);
        this.f24543a = azVar;
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z2) {
        az azVar = this.f24543a;
        azVar.f139a = Integer.valueOf(com.xiaomi.push.service.ba.a(azVar.f135a).a());
        if (this.f24543a.f139a.intValue() != 0) {
            this.f24543a.f135a.getContentResolver().unregisterContentObserver(this);
            if (com.xiaomi.push.as.b(this.f24543a.f135a)) {
                this.f24543a.m151c();
            }
        }
    }
}
