package com.vivo.push.b;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public final class t extends s {

    /* renamed from: a, reason: collision with root package name */
    private ArrayList<String> f24274a;

    /* renamed from: b, reason: collision with root package name */
    private ArrayList<String> f24275b;

    public t(int i2) {
        super(i2);
        this.f24274a = null;
        this.f24275b = null;
    }

    @Override // com.vivo.push.b.s, com.vivo.push.o
    public final void c(com.vivo.push.a aVar) {
        super.c(aVar);
        aVar.a("content", this.f24274a);
        aVar.a("error_msg", this.f24275b);
    }

    public final ArrayList<String> d() {
        return this.f24274a;
    }

    public final List<String> e() {
        return this.f24275b;
    }

    @Override // com.vivo.push.b.s, com.vivo.push.o
    public final String toString() {
        return "OnSetTagsCommand";
    }

    @Override // com.vivo.push.b.s, com.vivo.push.o
    public final void d(com.vivo.push.a aVar) {
        super.d(aVar);
        this.f24274a = aVar.c("content");
        this.f24275b = aVar.c("error_msg");
    }
}
