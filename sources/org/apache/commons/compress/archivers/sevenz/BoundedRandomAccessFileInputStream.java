package org.apache.commons.compress.archivers.sevenz;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

/* loaded from: classes9.dex */
class BoundedRandomAccessFileInputStream extends InputStream {
    private long bytesRemaining;
    private final RandomAccessFile file;

    public BoundedRandomAccessFileInputStream(RandomAccessFile randomAccessFile, long j2) {
        this.file = randomAccessFile;
        this.bytesRemaining = j2;
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        long j2 = this.bytesRemaining;
        if (j2 <= 0) {
            return -1;
        }
        this.bytesRemaining = j2 - 1;
        return this.file.read();
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i2, int i3) throws IOException {
        long j2 = this.bytesRemaining;
        if (j2 == 0) {
            return -1;
        }
        if (i3 > j2) {
            i3 = (int) j2;
        }
        int i4 = this.file.read(bArr, i2, i3);
        if (i4 >= 0) {
            this.bytesRemaining -= i4;
        }
        return i4;
    }
}
