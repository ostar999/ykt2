package com.tencent.tbs.one.impl.a;

import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes6.dex */
public final class l extends InputStream {

    /* renamed from: b, reason: collision with root package name */
    public a f21759b;

    /* renamed from: c, reason: collision with root package name */
    public InputStream f21760c;

    /* renamed from: d, reason: collision with root package name */
    public long f21761d;

    /* renamed from: a, reason: collision with root package name */
    public long f21758a = 0;

    /* renamed from: e, reason: collision with root package name */
    public int f21762e = 0;

    public interface a {
        void a(int i2);

        boolean a();
    }

    public l(InputStream inputStream, long j2) {
        this.f21760c = inputStream;
        this.f21761d = j2;
    }

    private void a() throws IOException {
        a aVar = this.f21759b;
        if (aVar != null && !aVar.a()) {
            throw new IOException("Aborted");
        }
    }

    private void b() {
        a aVar;
        long j2 = this.f21761d;
        if (j2 <= 0 || (aVar = this.f21759b) == null) {
            return;
        }
        int i2 = (int) ((this.f21758a / j2) * 100.0d);
        if (i2 - this.f21762e >= 2) {
            this.f21762e = i2;
            aVar.a(i2);
        }
    }

    @Override // java.io.InputStream
    public final int available() {
        return this.f21760c.available();
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public final void close() throws IOException {
        this.f21760c.close();
    }

    @Override // java.io.InputStream
    public final synchronized void mark(int i2) {
        this.f21760c.mark(i2);
    }

    @Override // java.io.InputStream
    public final boolean markSupported() {
        return this.f21760c.markSupported();
    }

    @Override // java.io.InputStream
    public final int read() throws IOException {
        a();
        int i2 = this.f21760c.read();
        if (i2 != -1) {
            this.f21758a++;
            b();
        }
        return i2;
    }

    @Override // java.io.InputStream
    public final int read(byte[] bArr) throws IOException {
        a();
        int i2 = this.f21760c.read(bArr);
        if (i2 != -1) {
            this.f21758a += i2;
            b();
        }
        return i2;
    }

    @Override // java.io.InputStream
    public final int read(byte[] bArr, int i2, int i3) throws IOException {
        a();
        int i4 = this.f21760c.read(bArr, i2, i3);
        if (i4 != -1) {
            this.f21758a += i4;
            b();
        }
        return i4;
    }

    @Override // java.io.InputStream
    public final synchronized void reset() {
        this.f21760c.reset();
    }

    @Override // java.io.InputStream
    public final long skip(long j2) {
        return this.f21760c.skip(j2);
    }
}
