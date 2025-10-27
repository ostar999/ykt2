package com.meizu.cloud.pushsdk.b.g;

import java.io.IOException;

/* loaded from: classes4.dex */
final class g implements b {

    /* renamed from: a, reason: collision with root package name */
    public final a f9192a;

    /* renamed from: b, reason: collision with root package name */
    public final k f9193b;

    /* renamed from: c, reason: collision with root package name */
    private boolean f9194c;

    public g(k kVar) {
        this(kVar, new a());
    }

    public g(k kVar, a aVar) {
        if (kVar == null) {
            throw new IllegalArgumentException("sink == null");
        }
        this.f9192a = aVar;
        this.f9193b = kVar;
    }

    @Override // com.meizu.cloud.pushsdk.b.g.b
    public long a(l lVar) throws IOException {
        if (lVar == null) {
            throw new IllegalArgumentException("source == null");
        }
        long j2 = 0;
        while (true) {
            long jB = lVar.b(this.f9192a, 2048L);
            if (jB == -1) {
                return j2;
            }
            j2 += jB;
            a();
        }
    }

    public b a() throws IOException {
        if (this.f9194c) {
            throw new IllegalStateException("closed");
        }
        long jE = this.f9192a.e();
        if (jE > 0) {
            this.f9193b.a(this.f9192a, jE);
        }
        return this;
    }

    @Override // com.meizu.cloud.pushsdk.b.g.k
    public void a(a aVar, long j2) throws IOException {
        if (this.f9194c) {
            throw new IllegalStateException("closed");
        }
        this.f9192a.a(aVar, j2);
        a();
    }

    @Override // com.meizu.cloud.pushsdk.b.g.b
    public a b() {
        return this.f9192a;
    }

    @Override // com.meizu.cloud.pushsdk.b.g.b
    public b b(d dVar) throws IOException {
        if (this.f9194c) {
            throw new IllegalStateException("closed");
        }
        this.f9192a.b(dVar);
        return a();
    }

    @Override // com.meizu.cloud.pushsdk.b.g.b
    public b b(String str) throws IOException {
        if (this.f9194c) {
            throw new IllegalStateException("closed");
        }
        this.f9192a.b(str);
        return a();
    }

    @Override // com.meizu.cloud.pushsdk.b.g.b
    public b c(byte[] bArr) throws IOException {
        if (this.f9194c) {
            throw new IllegalStateException("closed");
        }
        this.f9192a.c(bArr);
        return a();
    }

    @Override // com.meizu.cloud.pushsdk.b.g.b
    public b c(byte[] bArr, int i2, int i3) throws IOException {
        if (this.f9194c) {
            throw new IllegalStateException("closed");
        }
        this.f9192a.c(bArr, i2, i3);
        return a();
    }

    @Override // com.meizu.cloud.pushsdk.b.g.k, java.io.Closeable, java.lang.AutoCloseable, com.meizu.cloud.pushsdk.b.g.l
    public void close() throws Throwable {
        if (this.f9194c) {
            return;
        }
        try {
            a aVar = this.f9192a;
            long j2 = aVar.f9179b;
            if (j2 > 0) {
                this.f9193b.a(aVar, j2);
            }
            th = null;
        } catch (Throwable th) {
            th = th;
        }
        try {
            this.f9193b.close();
        } catch (Throwable th2) {
            if (th == null) {
                th = th2;
            }
        }
        this.f9194c = true;
        if (th != null) {
            n.a(th);
        }
    }

    @Override // com.meizu.cloud.pushsdk.b.g.b
    public b e(long j2) throws IOException {
        if (this.f9194c) {
            throw new IllegalStateException("closed");
        }
        this.f9192a.e(j2);
        return a();
    }

    @Override // com.meizu.cloud.pushsdk.b.g.k, java.io.Flushable
    public void flush() throws IOException {
        if (this.f9194c) {
            throw new IllegalStateException("closed");
        }
        a aVar = this.f9192a;
        long j2 = aVar.f9179b;
        if (j2 > 0) {
            this.f9193b.a(aVar, j2);
        }
        this.f9193b.flush();
    }

    public String toString() {
        return "buffer(" + this.f9193b + ")";
    }
}
