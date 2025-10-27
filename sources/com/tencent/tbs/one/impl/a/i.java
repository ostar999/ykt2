package com.tencent.tbs.one.impl.a;

import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes6.dex */
public final class i extends OutputStream {

    /* renamed from: a, reason: collision with root package name */
    public OutputStream[] f21751a;

    public i(OutputStream[] outputStreamArr) {
        this.f21751a = outputStreamArr;
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public final void close() throws IOException {
        for (OutputStream outputStream : this.f21751a) {
            outputStream.close();
        }
    }

    @Override // java.io.OutputStream, java.io.Flushable
    public final void flush() throws IOException {
        for (OutputStream outputStream : this.f21751a) {
            outputStream.flush();
        }
    }

    @Override // java.io.OutputStream
    public final void write(int i2) throws IOException {
        for (OutputStream outputStream : this.f21751a) {
            outputStream.write(i2);
        }
    }

    @Override // java.io.OutputStream
    public final void write(byte[] bArr) throws IOException {
        for (OutputStream outputStream : this.f21751a) {
            outputStream.write(bArr);
        }
    }

    @Override // java.io.OutputStream
    public final void write(byte[] bArr, int i2, int i3) throws IOException {
        for (OutputStream outputStream : this.f21751a) {
            outputStream.write(bArr, i2, i3);
        }
    }
}
