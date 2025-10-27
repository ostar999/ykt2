package org.apache.http.impl.io;

import java.io.IOException;
import java.io.OutputStream;
import org.apache.http.io.SessionOutputBuffer;

/* loaded from: classes9.dex */
public class ChunkedOutputStream extends OutputStream {
    private byte[] cache;
    private int cachePosition;
    private boolean closed;
    private final SessionOutputBuffer out;
    private boolean wroteLastChunk;

    public ChunkedOutputStream(SessionOutputBuffer sessionOutputBuffer, int i2) throws IOException {
        this.cachePosition = 0;
        this.wroteLastChunk = false;
        this.closed = false;
        this.cache = new byte[i2];
        this.out = sessionOutputBuffer;
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this.closed) {
            return;
        }
        this.closed = true;
        finish();
        this.out.flush();
    }

    public void finish() throws IOException {
        if (this.wroteLastChunk) {
            return;
        }
        flushCache();
        writeClosingChunk();
        this.wroteLastChunk = true;
    }

    @Override // java.io.OutputStream, java.io.Flushable
    public void flush() throws IOException {
        flushCache();
        this.out.flush();
    }

    public void flushCache() throws IOException {
        int i2 = this.cachePosition;
        if (i2 > 0) {
            this.out.writeLine(Integer.toHexString(i2));
            this.out.write(this.cache, 0, this.cachePosition);
            this.out.writeLine("");
            this.cachePosition = 0;
        }
    }

    public void flushCacheWithAppend(byte[] bArr, int i2, int i3) throws IOException {
        this.out.writeLine(Integer.toHexString(this.cachePosition + i3));
        this.out.write(this.cache, 0, this.cachePosition);
        this.out.write(bArr, i2, i3);
        this.out.writeLine("");
        this.cachePosition = 0;
    }

    @Override // java.io.OutputStream
    public void write(int i2) throws IOException {
        if (this.closed) {
            throw new IOException("Attempted write to closed stream.");
        }
        byte[] bArr = this.cache;
        int i3 = this.cachePosition;
        bArr[i3] = (byte) i2;
        int i4 = i3 + 1;
        this.cachePosition = i4;
        if (i4 == bArr.length) {
            flushCache();
        }
    }

    public void writeClosingChunk() throws IOException {
        this.out.writeLine("0");
        this.out.writeLine("");
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr) throws IOException {
        write(bArr, 0, bArr.length);
    }

    public ChunkedOutputStream(SessionOutputBuffer sessionOutputBuffer) throws IOException {
        this(sessionOutputBuffer, 2048);
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i2, int i3) throws IOException {
        if (!this.closed) {
            byte[] bArr2 = this.cache;
            int length = bArr2.length;
            int i4 = this.cachePosition;
            if (i3 >= length - i4) {
                flushCacheWithAppend(bArr, i2, i3);
                return;
            } else {
                System.arraycopy(bArr, i2, bArr2, i4, i3);
                this.cachePosition += i3;
                return;
            }
        }
        throw new IOException("Attempted write to closed stream.");
    }
}
