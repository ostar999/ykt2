package com.vivo.push.b;

import java.util.ArrayList;

/* loaded from: classes6.dex */
public final class m extends s {

    /* renamed from: a, reason: collision with root package name */
    private ArrayList<String> f24260a;

    public m() {
        super(8);
    }

    @Override // com.vivo.push.b.s, com.vivo.push.o
    public final void c(com.vivo.push.a aVar) {
        super.c(aVar);
        aVar.a("tags_list", this.f24260a);
    }

    public final ArrayList<String> d() {
        return this.f24260a;
    }

    @Override // com.vivo.push.b.s, com.vivo.push.o
    public final String toString() {
        return "OnListTagCommand";
    }

    @Override // com.vivo.push.b.s, com.vivo.push.o
    public final void d(com.vivo.push.a aVar) {
        super.d(aVar);
        this.f24260a = aVar.c("tags_list");
    }
}
