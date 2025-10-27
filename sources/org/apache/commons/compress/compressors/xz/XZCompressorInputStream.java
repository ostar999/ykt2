package org.apache.commons.compress.compressors.xz;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.tukaani.xz.SingleXZInputStream;
import org.tukaani.xz.XZ;
import org.tukaani.xz.XZInputStream;

/* loaded from: classes9.dex */
public class XZCompressorInputStream extends CompressorInputStream {
    private final InputStream in;

    public XZCompressorInputStream(InputStream inputStream) throws IOException {
        this(inputStream, false);
    }

    public static boolean matches(byte[] bArr, int i2) {
        if (i2 < XZ.HEADER_MAGIC.length) {
            return false;
        }
        for (int i3 = 0; i3 < XZ.HEADER_MAGIC.length; i3++) {
            if (bArr[i3] != XZ.HEADER_MAGIC[i3]) {
                return false;
            }
        }
        return true;
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
        count(i2 != -1 ? 1 : -1);
        return i2;
    }

    @Override // java.io.InputStream
    public long skip(long j2) throws IOException {
        return this.in.skip(j2);
    }

    public XZCompressorInputStream(InputStream inputStream, boolean z2) throws IOException {
        if (z2) {
            this.in = new XZInputStream(inputStream);
        } else {
            this.in = new SingleXZInputStream(inputStream);
        }
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i2, int i3) throws IOException {
        int i4 = this.in.read(bArr, i2, i3);
        count(i4);
        return i4;
    }
}
