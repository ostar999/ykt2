package com.vivo.push.b;

import java.io.Serializable;
import java.util.ArrayList;

/* loaded from: classes6.dex */
public final class z extends c {

    /* renamed from: a, reason: collision with root package name */
    private ArrayList<String> f24284a;

    public z(boolean z2, String str, ArrayList<String> arrayList) {
        super(z2 ? 2004 : 2005, str);
        this.f24284a = arrayList;
    }

    @Override // com.vivo.push.b.c, com.vivo.push.o
    public final void c(com.vivo.push.a aVar) {
        super.c(aVar);
        aVar.a("tags", (Serializable) this.f24284a);
    }

    @Override // com.vivo.push.b.c, com.vivo.push.o
    public final void d(com.vivo.push.a aVar) {
        super.d(aVar);
        this.f24284a = aVar.c("tags");
    }

    @Override // com.vivo.push.b.c, com.vivo.push.o
    public final String toString() {
        return "TagCommand";
    }
}
