package org.apache.commons.compress.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.Checksum;

/* loaded from: classes9.dex */
public class ChecksumVerifyingInputStream extends InputStream {
    private long bytesRemaining;
    private final Checksum checksum;
    private final long expectedChecksum;
    private final InputStream in;

    public ChecksumVerifyingInputStream(Checksum checksum, InputStream inputStream, long j2, long j3) {
        this.checksum = checksum;
        this.in = inputStream;
        this.expectedChecksum = j3;
        this.bytesRemaining = j2;
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.in.close();
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        if (this.bytesRemaining <= 0) {
            return -1;
        }
        int i2 = this.in.read();
        if (i2 >= 0) {
            this.checksum.update(i2);
            this.bytesRemaining--;
        }
        if (this.bytesRemaining != 0 || this.expectedChecksum == this.checksum.getValue()) {
            return i2;
        }
        throw new IOException("Checksum verification failed");
    }

    @Override // java.io.InputStream
    public long skip(long j2) throws IOException {
        return read() >= 0 ? 1L : 0L;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr) throws IOException {
        return read(bArr, 0, bArr.length);
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i2, int i3) throws IOException {
        int i4 = this.in.read(bArr, i2, i3);
        if (i4 >= 0) {
            this.checksum.update(bArr, i2, i4);
            this.bytesRemaining -= i4;
        }
        if (this.bytesRemaining > 0 || this.expectedChecksum == this.checksum.getValue()) {
            return i4;
        }
        throw new IOException("Checksum verification failed");
    }
}
