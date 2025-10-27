package com.vivo.push.b;

/* loaded from: classes6.dex */
public final class w extends com.vivo.push.o {

    /* renamed from: a, reason: collision with root package name */
    private int f24280a;

    public w() {
        super(2011);
        this.f24280a = 0;
    }

    @Override // com.vivo.push.o
    public final void c(com.vivo.push.a aVar) {
        aVar.a("com.bbk.push.ikey.MODE_TYPE", this.f24280a);
    }

    @Override // com.vivo.push.o
    public final boolean c() {
        return true;
    }

    public final int d() {
        return this.f24280a;
    }

    @Override // com.vivo.push.o
    public final String toString() {
        return "PushModeCommand";
    }

    @Override // com.vivo.push.o
    public final void d(com.vivo.push.a aVar) {
        this.f24280a = aVar.b("com.bbk.push.ikey.MODE_TYPE", 0);
    }
}
