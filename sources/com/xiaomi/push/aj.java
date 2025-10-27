package com.xiaomi.push;

import com.xiaomi.push.ai;

/* loaded from: classes6.dex */
class aj extends ai.b {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ ai f24601a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ String f185a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public aj(ai aiVar, ai.a aVar, String str) {
        super(aVar);
        this.f24601a = aiVar;
        this.f185a = str;
    }

    @Override // com.xiaomi.push.ai.b
    public void a() {
        super.a();
    }

    @Override // com.xiaomi.push.ai.b
    public void b() {
        this.f24601a.f181a.edit().putLong(this.f185a, System.currentTimeMillis()).commit();
    }
}
