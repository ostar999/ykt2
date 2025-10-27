package org.apache.http.impl.io;

import java.io.IOException;
import java.io.OutputStream;
import org.apache.http.io.SessionOutputBuffer;

/* loaded from: classes9.dex */
public class IdentityOutputStream extends OutputStream {
    private boolean closed = false;
    private final SessionOutputBuffer out;

    public IdentityOutputStream(SessionOutputBuffer sessionOutputBuffer) {
        if (sessionOutputBuffer == null) {
            throw new IllegalArgumentException("Session output buffer may not be null");
        }
        this.out = sessionOutputBuffer;
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this.closed) {
            return;
        }
        this.closed = true;
        this.out.flush();
    }

    @Override // java.io.OutputStream, java.io.Flushable
    public void flush() throws IOException {
        this.out.flush();
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i2, int i3) throws IOException {
        if (this.closed) {
            throw new IOException("Attempted write to closed stream.");
        }
        this.out.write(bArr, i2, i3);
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr) throws IOException {
        write(bArr, 0, bArr.length);
    }

    @Override // java.io.OutputStream
    public void write(int i2) throws IOException {
        if (!this.closed) {
            this.out.write(i2);
            return;
        }
        throw new IOException("Attempted write to closed stream.");
    }
}
