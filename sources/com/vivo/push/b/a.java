package com.vivo.push.b;

import java.util.ArrayList;

/* loaded from: classes6.dex */
public final class a extends c {

    /* renamed from: a, reason: collision with root package name */
    private ArrayList<String> f24239a;

    public a(boolean z2, String str, ArrayList<String> arrayList) {
        super(z2 ? 2002 : 2003, str);
        this.f24239a = arrayList;
    }

    @Override // com.vivo.push.b.c, com.vivo.push.o
    public final void c(com.vivo.push.a aVar) {
        super.c(aVar);
        aVar.a("tags", this.f24239a);
    }

    @Override // com.vivo.push.b.c, com.vivo.push.o
    public final void d(com.vivo.push.a aVar) {
        super.d(aVar);
        this.f24239a = aVar.c("tags");
    }

    @Override // com.vivo.push.b.c, com.vivo.push.o
    public final String toString() {
        return "AliasCommand:" + b();
    }
}
