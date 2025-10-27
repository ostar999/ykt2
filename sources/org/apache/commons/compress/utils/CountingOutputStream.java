package org.apache.commons.compress.utils;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes9.dex */
public class CountingOutputStream extends FilterOutputStream {
    private long bytesWritten;

    public CountingOutputStream(OutputStream outputStream) {
        super(outputStream);
        this.bytesWritten = 0L;
    }

    public void count(long j2) {
        if (j2 != -1) {
            this.bytesWritten += j2;
        }
    }

    public long getBytesWritten() {
        return this.bytesWritten;
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public void write(int i2) throws IOException {
        ((FilterOutputStream) this).out.write(i2);
        count(1L);
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public void write(byte[] bArr) throws IOException {
        write(bArr, 0, bArr.length);
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public void write(byte[] bArr, int i2, int i3) throws IOException {
        ((FilterOutputStream) this).out.write(bArr, i2, i3);
        count(i3);
    }
}
