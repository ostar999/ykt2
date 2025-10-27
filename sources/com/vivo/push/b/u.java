package com.vivo.push.b;

/* loaded from: classes6.dex */
public final class u extends v {

    /* renamed from: a, reason: collision with root package name */
    private long f24276a;

    /* renamed from: b, reason: collision with root package name */
    private int f24277b;

    public u() {
        super(20);
        this.f24276a = -1L;
    }

    @Override // com.vivo.push.b.v, com.vivo.push.b.s, com.vivo.push.o
    public final void c(com.vivo.push.a aVar) {
        super.c(aVar);
        aVar.a("undo_msg_v1", this.f24276a);
        aVar.a("undo_msg_type_v1", this.f24277b);
    }

    public final long d() {
        return this.f24276a;
    }

    public final String e() {
        long j2 = this.f24276a;
        if (j2 != -1) {
            return String.valueOf(j2);
        }
        return null;
    }

    @Override // com.vivo.push.b.s, com.vivo.push.o
    public final String toString() {
        return "OnUndoMsgCommand";
    }

    @Override // com.vivo.push.b.v, com.vivo.push.b.s, com.vivo.push.o
    public final void d(com.vivo.push.a aVar) {
        super.d(aVar);
        this.f24276a = aVar.b("undo_msg_v1", this.f24276a);
        this.f24277b = aVar.b("undo_msg_type_v1", 0);
    }
}
