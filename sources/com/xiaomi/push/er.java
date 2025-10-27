package com.xiaomi.push;

/* loaded from: classes6.dex */
class er implements ca {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ eq f24770a;

    public er(eq eqVar) {
        this.f24770a = eqVar;
    }

    @Override // com.xiaomi.push.ca
    public void a(String str) {
        this.f24770a.f344a = eq.b(str);
        synchronized (this.f24770a.f343a) {
            try {
                this.f24770a.f343a.notify();
            } catch (Exception e2) {
                com.xiaomi.channel.commonutils.logger.b.a(e2);
            }
        }
    }
}
