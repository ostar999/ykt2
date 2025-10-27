package org.apache.commons.compress.compressors.lzma;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.tukaani.xz.LZMAInputStream;

/* loaded from: classes9.dex */
public class LZMACompressorInputStream extends CompressorInputStream {
    private final InputStream in;

    public LZMACompressorInputStream(InputStream inputStream) throws IOException {
        this.in = new LZMAInputStream(inputStream);
    }

    public static boolean matches(byte[] bArr, int i2) {
        return bArr != null && i2 >= 3 && bArr[0] == 93 && bArr[1] == 0 && bArr[2] == 0;
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        return this.in.available();
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.in.close();
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        int i2 = this.in.read();
        count(i2 == -1 ? 0 : 1);
        return i2;
    }

    @Override // java.io.InputStream
    public long skip(long j2) throws IOException {
        return this.in.skip(j2);
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i2, int i3) throws IOException {
        int i4 = this.in.read(bArr, i2, i3);
        count(i4);
        return i4;
    }
}
