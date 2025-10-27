package org.apache.commons.compress.utils;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes9.dex */
public class CountingInputStream extends FilterInputStream {
    private long bytesRead;

    public CountingInputStream(InputStream inputStream) {
        super(inputStream);
    }

    public final void count(long j2) {
        if (j2 != -1) {
            this.bytesRead += j2;
        }
    }

    public long getBytesRead() {
        return this.bytesRead;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read() throws IOException {
        int i2 = ((FilterInputStream) this).in.read();
        if (i2 >= 0) {
            count(1L);
        }
        return i2;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr) throws IOException {
        return read(bArr, 0, bArr.length);
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i2, int i3) throws IOException {
        int i4 = ((FilterInputStream) this).in.read(bArr, i2, i3);
        if (i4 >= 0) {
            count(i4);
        }
        return i4;
    }
}
