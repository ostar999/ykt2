package com.tencent.tbs.one.impl.a;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;

/* loaded from: classes6.dex */
public abstract class c<T> extends b<T> {

    /* renamed from: b, reason: collision with root package name */
    public final File f21733b;

    /* renamed from: c, reason: collision with root package name */
    public final long f21734c;

    /* renamed from: d, reason: collision with root package name */
    public k f21735d;

    public c(File file, long j2) {
        this.f21733b = file;
        this.f21734c = j2;
    }

    private void d() throws IOException {
        k kVar = this.f21735d;
        if (kVar != null) {
            kVar.a();
        }
    }

    @Override // com.tencent.tbs.one.impl.a.b
    public final void a() {
        o.d(new Runnable() { // from class: com.tencent.tbs.one.impl.a.c.1
            @Override // java.lang.Runnable
            public final void run() {
                try {
                    c cVar = c.this;
                    File file = cVar.f21733b;
                    Callable<Boolean> callable = new Callable<Boolean>() { // from class: com.tencent.tbs.one.impl.a.c.1.1
                        @Override // java.util.concurrent.Callable
                        public final /* synthetic */ Boolean call() {
                            return Boolean.valueOf(!c.this.f21723a);
                        }
                    };
                    cVar.f21735d = (k) k.a(new Callable<k>() { // from class: com.tencent.tbs.one.impl.a.k.1

                        /* renamed from: a */
                        public final /* synthetic */ Callable f21756a;

                        /* renamed from: b */
                        public final /* synthetic */ File f21757b;

                        public AnonymousClass1(Callable callable2, File file2) {
                            callable = callable2;
                            file = file2;
                        }

                        @Override // java.util.concurrent.Callable
                        public final /* synthetic */ k call() throws Exception {
                            if (((Boolean) callable.call()).booleanValue()) {
                                return k.a(file);
                            }
                            throw new Exception("Aborted");
                        }
                    }, file2, c.this.f21734c);
                    c.this.c();
                } catch (Exception e2) {
                    c.this.a(e2);
                }
            }
        });
    }

    @Override // com.tencent.tbs.one.impl.a.b
    public void a(int i2, String str, Throwable th) throws IOException {
        d();
        super.a(i2, str, th);
    }

    public abstract void a(Exception exc);

    @Override // com.tencent.tbs.one.impl.a.b
    public void a(T t2) throws IOException {
        d();
        super.a((c<T>) t2);
    }

    public abstract void c();
}
