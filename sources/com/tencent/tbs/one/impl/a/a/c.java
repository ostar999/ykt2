package com.tencent.tbs.one.impl.a.a;

import com.tencent.tbs.one.impl.a.a.d;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes6.dex */
public abstract class c implements Runnable {

    /* renamed from: b, reason: collision with root package name */
    public d f21712b;

    /* renamed from: d, reason: collision with root package name */
    public boolean f21714d;

    /* renamed from: e, reason: collision with root package name */
    public AtomicBoolean f21715e = new AtomicBoolean();

    /* renamed from: a, reason: collision with root package name */
    public AtomicBoolean f21711a = new AtomicBoolean();

    /* renamed from: c, reason: collision with root package name */
    public List<c> f21713c = new ArrayList();

    /* renamed from: f, reason: collision with root package name */
    public List<Runnable> f21716f = new ArrayList();

    public final void a() {
        if (this.f21714d) {
            return;
        }
        this.f21714d = true;
        List<c> list = this.f21713c;
        if (list == null || list.size() <= 0) {
            b();
        } else {
            for (c cVar : this.f21713c) {
                cVar.f21712b = this.f21712b;
                cVar.f21716f.add(new Runnable() { // from class: com.tencent.tbs.one.impl.a.a.c.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        c cVar2 = c.this;
                        Iterator<c> it = cVar2.f21713c.iterator();
                        while (it.hasNext()) {
                            if (!it.next().f21711a.get()) {
                                return;
                            }
                        }
                        cVar2.b();
                    }
                });
                cVar.a();
            }
        }
        this.f21712b.a();
    }

    public final void a(int i2, String str, Throwable th) {
        d.a aVar = this.f21712b.f21718a;
        if (aVar != null) {
            aVar.a(i2, str, th);
        }
    }

    public final void b() {
        if (this.f21715e.compareAndSet(false, true)) {
            this.f21712b.b(this);
        }
    }

    public final void b(c cVar) {
        this.f21713c.add(cVar);
    }

    public final void c() {
        if (this.f21711a.compareAndSet(false, true)) {
            Iterator<Runnable> it = this.f21716f.iterator();
            while (it.hasNext()) {
                it.next().run();
            }
            this.f21712b.b();
        }
    }
}
