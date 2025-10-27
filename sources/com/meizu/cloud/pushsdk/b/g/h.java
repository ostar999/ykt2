package com.meizu.cloud.pushsdk.b.g;

import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes4.dex */
final class h implements c {

    /* renamed from: a, reason: collision with root package name */
    public final a f9195a;

    /* renamed from: b, reason: collision with root package name */
    public final l f9196b;

    /* renamed from: c, reason: collision with root package name */
    private boolean f9197c;

    public h(l lVar) {
        this(lVar, new a());
    }

    public h(l lVar, a aVar) {
        if (lVar == null) {
            throw new IllegalArgumentException("source == null");
        }
        this.f9195a = aVar;
        this.f9196b = lVar;
    }

    @Override // com.meizu.cloud.pushsdk.b.g.l
    public long b(a aVar, long j2) throws IOException {
        if (aVar == null) {
            throw new IllegalArgumentException("sink == null");
        }
        if (j2 < 0) {
            throw new IllegalArgumentException("byteCount < 0: " + j2);
        }
        if (this.f9197c) {
            throw new IllegalStateException("closed");
        }
        a aVar2 = this.f9195a;
        if (aVar2.f9179b == 0 && this.f9196b.b(aVar2, 2048L) == -1) {
            return -1L;
        }
        return this.f9195a.b(aVar, Math.min(j2, this.f9195a.f9179b));
    }

    @Override // com.meizu.cloud.pushsdk.b.g.l, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this.f9197c) {
            return;
        }
        this.f9197c = true;
        this.f9196b.close();
        this.f9195a.j();
    }

    @Override // com.meizu.cloud.pushsdk.b.g.c
    public InputStream d() {
        return new InputStream() { // from class: com.meizu.cloud.pushsdk.b.g.h.1
            @Override // java.io.InputStream
            public int available() throws IOException {
                if (h.this.f9197c) {
                    throw new IOException("closed");
                }
                return (int) Math.min(h.this.f9195a.f9179b, 2147483647L);
            }

            @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
            public void close() throws IOException {
                h.this.close();
            }

            @Override // java.io.InputStream
            public int read() throws IOException {
                if (h.this.f9197c) {
                    throw new IOException("closed");
                }
                h hVar = h.this;
                a aVar = hVar.f9195a;
                if (aVar.f9179b == 0 && hVar.f9196b.b(aVar, 2048L) == -1) {
                    return -1;
                }
                return h.this.f9195a.f() & 255;
            }

            @Override // java.io.InputStream
            public int read(byte[] bArr, int i2, int i3) throws IOException {
                if (h.this.f9197c) {
                    throw new IOException("closed");
                }
                n.a(bArr.length, i2, i3);
                h hVar = h.this;
                a aVar = hVar.f9195a;
                if (aVar.f9179b == 0 && hVar.f9196b.b(aVar, 2048L) == -1) {
                    return -1;
                }
                return h.this.f9195a.a(bArr, i2, i3);
            }

            public String toString() {
                return h.this + ".inputStream()";
            }
        };
    }

    @Override // com.meizu.cloud.pushsdk.b.g.c
    public String h() throws IOException {
        this.f9195a.a(this.f9196b);
        return this.f9195a.h();
    }

    @Override // com.meizu.cloud.pushsdk.b.g.c
    public byte[] i() throws IOException {
        this.f9195a.a(this.f9196b);
        return this.f9195a.i();
    }

    public String toString() {
        return "buffer(" + this.f9196b + ")";
    }
}
