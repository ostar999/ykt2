package org.apache.commons.compress.compressors.deflate;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import org.apache.commons.compress.compressors.CompressorInputStream;

/* loaded from: classes9.dex */
public class DeflateCompressorInputStream extends CompressorInputStream {
    private static final int MAGIC_1 = 120;
    private static final int MAGIC_2a = 1;
    private static final int MAGIC_2b = 94;
    private static final int MAGIC_2c = 156;
    private static final int MAGIC_2d = 218;
    private final InputStream in;
    private final Inflater inflater;

    public DeflateCompressorInputStream(InputStream inputStream) {
        this(inputStream, new DeflateParameters());
    }

    public static boolean matches(byte[] bArr, int i2) {
        if (i2 <= 3 || bArr[0] != 120) {
            return false;
        }
        byte b3 = bArr[1];
        return b3 == 1 || b3 == 94 || b3 == -100 || b3 == -38;
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        return this.in.available();
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        try {
            this.in.close();
        } finally {
            this.inflater.end();
        }
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

    public DeflateCompressorInputStream(InputStream inputStream, DeflateParameters deflateParameters) {
        Inflater inflater = new Inflater(!deflateParameters.withZlibHeader());
        this.inflater = inflater;
        this.in = new InflaterInputStream(inputStream, inflater);
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i2, int i3) throws IOException {
        int i4 = this.in.read(bArr, i2, i3);
        count(i4);
        return i4;
    }
}
