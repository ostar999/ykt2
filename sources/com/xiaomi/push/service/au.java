package com.xiaomi.push.service;

import com.xiaomi.push.service.at;

/* loaded from: classes6.dex */
class au implements at.b.a {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ at.b f25608a;

    public au(at.b bVar) {
        this.f25608a = bVar;
    }

    @Override // com.xiaomi.push.service.at.b.a
    public void a(at.c cVar, at.c cVar2, int i2) {
        if (cVar2 == at.c.binding) {
            this.f25608a.f1008a.a(this.f25608a.f1007a, 60000L);
        } else {
            this.f25608a.f1008a.b(this.f25608a.f1007a);
        }
    }
}
