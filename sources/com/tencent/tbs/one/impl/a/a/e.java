package com.tencent.tbs.one.impl.a.a;

import com.tencent.tbs.one.impl.a.a.d;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes6.dex */
public final class e extends d {

    /* renamed from: c, reason: collision with root package name */
    public Executor f21720c;

    /* renamed from: d, reason: collision with root package name */
    public boolean f21721d;

    /* renamed from: b, reason: collision with root package name */
    public AtomicInteger f21719b = new AtomicInteger();

    /* renamed from: e, reason: collision with root package name */
    public List<c> f21722e = new ArrayList();

    public e(Executor executor) {
        this.f21720c = executor;
    }

    @Override // com.tencent.tbs.one.impl.a.a.d
    public final void a() {
        this.f21719b.incrementAndGet();
    }

    @Override // com.tencent.tbs.one.impl.a.a.d
    public final void a(c cVar) {
        cVar.f21712b = this;
        this.f21721d = true;
        cVar.a();
        this.f21721d = false;
        c[] cVarArr = (c[]) this.f21722e.toArray(new c[0]);
        this.f21722e.clear();
        for (c cVar2 : cVarArr) {
            this.f21720c.execute(cVar2);
        }
    }

    @Override // com.tencent.tbs.one.impl.a.a.d
    public final void b() {
        d.a aVar;
        if (this.f21719b.decrementAndGet() > 0 || (aVar = this.f21718a) == null) {
            return;
        }
        aVar.a();
    }

    @Override // com.tencent.tbs.one.impl.a.a.d
    public final void b(c cVar) {
        if (this.f21721d) {
            this.f21722e.add(cVar);
        } else {
            this.f21720c.execute(cVar);
        }
    }
}
