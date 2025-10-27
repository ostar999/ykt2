package com.meizu.cloud.pushsdk.b.g;

import java.io.IOException;

/* loaded from: classes4.dex */
public abstract class e implements k {

    /* renamed from: a, reason: collision with root package name */
    private final k f9186a;

    public e(k kVar) {
        if (kVar == null) {
            throw new IllegalArgumentException("delegate == null");
        }
        this.f9186a = kVar;
    }

    @Override // com.meizu.cloud.pushsdk.b.g.k
    public void a(a aVar, long j2) throws IOException {
        this.f9186a.a(aVar, j2);
    }

    @Override // com.meizu.cloud.pushsdk.b.g.k, java.io.Closeable, java.lang.AutoCloseable, com.meizu.cloud.pushsdk.b.g.l
    public void close() throws IOException {
        this.f9186a.close();
    }

    @Override // com.meizu.cloud.pushsdk.b.g.k, java.io.Flushable
    public void flush() throws IOException {
        this.f9186a.flush();
    }

    public String toString() {
        return getClass().getSimpleName() + "(" + this.f9186a.toString() + ")";
    }
}
