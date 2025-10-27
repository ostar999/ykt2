package com.xiaomi.push;

import com.xiaomi.push.ai;

/* loaded from: classes6.dex */
class ak extends ai.b {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ ai f24602a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ak(ai aiVar, ai.a aVar) {
        super(aVar);
        this.f24602a = aiVar;
    }

    @Override // com.xiaomi.push.ai.b
    public void b() {
        synchronized (this.f24602a.f183a) {
            this.f24602a.f182a.remove(super.f24600a.mo185a());
        }
    }
}
