package com.android.volley.toolbox;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/* loaded from: classes2.dex */
public class PoolingByteArrayOutputStream extends ByteArrayOutputStream {
    private static final int DEFAULT_SIZE = 256;
    private final ByteArrayPool mPool;

    public PoolingByteArrayOutputStream(ByteArrayPool byteArrayPool) {
        this(byteArrayPool, 256);
    }

    private void expand(int i2) {
        int i3 = ((ByteArrayOutputStream) this).count;
        if (i3 + i2 <= ((ByteArrayOutputStream) this).buf.length) {
            return;
        }
        byte[] buf = this.mPool.getBuf((i3 + i2) * 2);
        System.arraycopy(((ByteArrayOutputStream) this).buf, 0, buf, 0, ((ByteArrayOutputStream) this).count);
        this.mPool.returnBuf(((ByteArrayOutputStream) this).buf);
        ((ByteArrayOutputStream) this).buf = buf;
    }

    @Override // java.io.ByteArrayOutputStream, java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.mPool.returnBuf(((ByteArrayOutputStream) this).buf);
        ((ByteArrayOutputStream) this).buf = null;
        super.close();
    }

    public void finalize() {
        this.mPool.returnBuf(((ByteArrayOutputStream) this).buf);
    }

    @Override // java.io.ByteArrayOutputStream, java.io.OutputStream
    public synchronized void write(byte[] bArr, int i2, int i3) {
        expand(i3);
        super.write(bArr, i2, i3);
    }

    public PoolingByteArrayOutputStream(ByteArrayPool byteArrayPool, int i2) {
        this.mPool = byteArrayPool;
        ((ByteArrayOutputStream) this).buf = byteArrayPool.getBuf(Math.max(i2, 256));
    }

    @Override // java.io.ByteArrayOutputStream, java.io.OutputStream
    public synchronized void write(int i2) {
        expand(1);
        super.write(i2);
    }
}
