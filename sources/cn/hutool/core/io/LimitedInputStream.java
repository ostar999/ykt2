package cn.hutool.core.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes.dex */
public class LimitedInputStream extends FilterInputStream {
    private long currentPos;
    private final long maxSize;

    public LimitedInputStream(InputStream inputStream, long j2) {
        super(inputStream);
        this.maxSize = j2;
    }

    private void checkPos() {
        if (this.currentPos > this.maxSize) {
            throw new IllegalStateException("Read limit exceeded");
        }
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read() throws IOException {
        int i2 = super.read();
        if (i2 != -1) {
            this.currentPos++;
            checkPos();
        }
        return i2;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public long skip(long j2) throws IOException {
        long jSkip = super.skip(j2);
        if (jSkip != 0) {
            this.currentPos += jSkip;
            checkPos();
        }
        return jSkip;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i2, int i3) throws IOException {
        int i4 = super.read(bArr, i2, i3);
        if (i4 > 0) {
            this.currentPos += i4;
            checkPos();
        }
        return i4;
    }
}
