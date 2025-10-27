package com.xiaomi.push;

import com.xiaomi.push.al;
import com.xiaomi.push.dl;

/* loaded from: classes6.dex */
class dn extends al.b {

    /* renamed from: a, reason: collision with root package name */
    al.b f24738a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ dl f328a;

    public dn(dl dlVar) {
        this.f328a = dlVar;
    }

    @Override // com.xiaomi.push.al.b
    public void b() {
        dl.b bVar = (dl.b) this.f328a.f316a.peek();
        if (bVar == null || !bVar.a()) {
            return;
        }
        if (this.f328a.f316a.remove(bVar)) {
            this.f24738a = bVar;
        }
        al.b bVar2 = this.f24738a;
        if (bVar2 != null) {
            bVar2.b();
        }
    }

    @Override // com.xiaomi.push.al.b
    /* renamed from: c */
    public void mo328c() {
        al.b bVar = this.f24738a;
        if (bVar != null) {
            bVar.mo328c();
        }
    }
}
