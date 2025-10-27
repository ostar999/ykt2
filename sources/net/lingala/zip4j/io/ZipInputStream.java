package net.lingala.zip4j.io;

import java.io.IOException;
import java.io.InputStream;
import net.lingala.zip4j.exception.ZipException;

/* loaded from: classes9.dex */
public class ZipInputStream extends InputStream {
    private BaseInputStream is;

    public ZipInputStream(BaseInputStream baseInputStream) {
        this.is = baseInputStream;
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        return this.is.available();
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        close(false);
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        int i2 = this.is.read();
        if (i2 != -1) {
            this.is.getUnzipEngine().updateCRC(i2);
        }
        return i2;
    }

    @Override // java.io.InputStream
    public long skip(long j2) throws IOException {
        return this.is.skip(j2);
    }

    public void close(boolean z2) throws IOException {
        try {
            this.is.close();
            if (z2 || this.is.getUnzipEngine() == null) {
                return;
            }
            this.is.getUnzipEngine().checkCRC();
        } catch (ZipException e2) {
            throw new IOException(e2.getMessage());
        }
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr) throws IOException {
        return read(bArr, 0, bArr.length);
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i2, int i3) throws IOException {
        int i4 = this.is.read(bArr, i2, i3);
        if (i4 > 0 && this.is.getUnzipEngine() != null) {
            this.is.getUnzipEngine().updateCRC(bArr, i2, i4);
        }
        return i4;
    }
}
