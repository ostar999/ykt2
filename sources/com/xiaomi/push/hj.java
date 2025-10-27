package com.xiaomi.push;

import com.xiaomi.push.hm;
import com.xiaomi.push.service.XMPushService;
import com.xiaomi.push.service.at;

/* loaded from: classes6.dex */
class hj implements at.b.a {

    /* renamed from: a, reason: collision with root package name */
    private int f25061a;

    /* renamed from: a, reason: collision with other field name */
    private gc f529a;

    /* renamed from: a, reason: collision with other field name */
    private XMPushService f530a;

    /* renamed from: a, reason: collision with other field name */
    private at.b f531a;

    /* renamed from: a, reason: collision with other field name */
    private boolean f533a = false;

    /* renamed from: a, reason: collision with other field name */
    private at.c f532a = at.c.binding;

    public hj(XMPushService xMPushService, at.b bVar) {
        this.f530a = xMPushService;
        this.f531a = bVar;
    }

    private void b() {
        this.f531a.b(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        fr frVar;
        b();
        if (this.f533a && this.f25061a != 11) {
            fs fsVarM490a = ho.m488a().m490a();
            int i2 = hl.f25063a[this.f532a.ordinal()];
            if (i2 == 1) {
                int i3 = this.f25061a;
                if (i3 == 17) {
                    frVar = fr.BIND_TCP_READ_TIMEOUT;
                } else if (i3 == 21) {
                    frVar = fr.BIND_TIMEOUT;
                } else {
                    try {
                        hm.a aVarC = hm.c(ho.a().a());
                        fsVarM490a.f434a = aVarC.f25064a.a();
                        fsVarM490a.c(aVarC.f534a);
                    } catch (NullPointerException unused) {
                        fsVarM490a = null;
                    }
                }
                fsVarM490a.f434a = frVar.a();
            } else if (i2 == 3) {
                frVar = fr.BIND_SUCCESS;
                fsVarM490a.f434a = frVar.a();
            }
            if (fsVarM490a != null) {
                fsVarM490a.b(this.f529a.mo450a());
                fsVarM490a.d(this.f531a.f1015b);
                fsVarM490a.f437b = 1;
                try {
                    fsVarM490a.a((byte) Integer.parseInt(this.f531a.f25597g));
                } catch (NumberFormatException unused2) {
                }
                ho.m488a().a(fsVarM490a);
            }
        }
    }

    public void a() {
        this.f531a.a(this);
        this.f529a = this.f530a.m695a();
    }

    @Override // com.xiaomi.push.service.at.b.a
    public void a(at.c cVar, at.c cVar2, int i2) {
        if (!this.f533a && cVar == at.c.binding) {
            this.f532a = cVar2;
            this.f25061a = i2;
            this.f533a = true;
        }
        this.f530a.a(new hk(this, 4));
    }
}
