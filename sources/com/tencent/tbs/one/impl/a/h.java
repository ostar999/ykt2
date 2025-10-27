package com.tencent.tbs.one.impl.a;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes6.dex */
public final class h<T> extends b<T> {

    /* renamed from: b, reason: collision with root package name */
    public List<b<T>> f21747b;

    /* renamed from: c, reason: collision with root package name */
    public b<T> f21748c;

    public h(b<T>[] bVarArr) {
        this.f21747b = new ArrayList(Arrays.asList(bVarArr));
    }

    @Override // com.tencent.tbs.one.impl.a.b
    public final void a() {
        c();
    }

    @Override // com.tencent.tbs.one.impl.a.b
    public final void b() {
        super.b();
        b<T> bVar = this.f21748c;
        if (bVar != null) {
            bVar.b();
        }
    }

    public final void c() {
        this.f21748c = this.f21747b.remove(0);
        final boolean z2 = this.f21747b.size() == 0;
        this.f21748c.a((m) new m<T>() { // from class: com.tencent.tbs.one.impl.a.h.1
            @Override // com.tencent.tbs.one.impl.a.m
            public final void a(int i2, int i3) {
                h.this.a(i3);
            }

            @Override // com.tencent.tbs.one.impl.a.m
            public final void a(int i2, String str, Throwable th) {
                g.a(str, th);
                if (!z2) {
                    h hVar = h.this;
                    if (!hVar.f21723a) {
                        hVar.c();
                        return;
                    }
                }
                h.this.a(i2, str, th);
            }

            @Override // com.tencent.tbs.one.impl.a.m
            public final void a(T t2) {
                h.this.a((h) t2);
            }
        });
    }
}
