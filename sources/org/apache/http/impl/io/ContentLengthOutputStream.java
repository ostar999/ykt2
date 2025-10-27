package org.apache.http.impl.io;

import java.io.IOException;
import java.io.OutputStream;
import org.apache.http.io.SessionOutputBuffer;

/* loaded from: classes9.dex */
public class ContentLengthOutputStream extends OutputStream {
    private final long contentLength;
    private final SessionOutputBuffer out;
    private long total = 0;
    private boolean closed = false;

    public ContentLengthOutputStream(SessionOutputBuffer sessionOutputBuffer, long j2) {
        if (sessionOutputBuffer == null) {
            throw new IllegalArgumentException("Session output buffer may not be null");
        }
        if (j2 < 0) {
            throw new IllegalArgumentException("Content length may not be negative");
        }
        this.out = sessionOutputBuffer;
        this.contentLength = j2;
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
        long j2 = this.total;
        long j3 = this.contentLength;
        if (j2 < j3) {
            long j4 = j3 - j2;
            if (i3 > j4) {
                i3 = (int) j4;
            }
            this.out.write(bArr, i2, i3);
            this.total += i3;
        }
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr) throws IOException {
        write(bArr, 0, bArr.length);
    }

    @Override // java.io.OutputStream
    public void write(int i2) throws IOException {
        if (this.closed) {
            throw new IOException("Attempted write to closed stream.");
        }
        if (this.total < this.contentLength) {
            this.out.write(i2);
            this.total++;
        }
    }
}
