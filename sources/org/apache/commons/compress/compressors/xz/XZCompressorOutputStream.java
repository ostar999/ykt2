package org.apache.commons.compress.compressors.xz;

import java.io.IOException;
import java.io.OutputStream;
import org.apache.commons.compress.compressors.CompressorOutputStream;
import org.tukaani.xz.LZMA2Options;
import org.tukaani.xz.XZOutputStream;

/* loaded from: classes9.dex */
public class XZCompressorOutputStream extends CompressorOutputStream {
    private final XZOutputStream out;

    public XZCompressorOutputStream(OutputStream outputStream) throws IOException {
        this.out = new XZOutputStream(outputStream, new LZMA2Options());
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.out.close();
    }

    public void finish() throws IOException {
        this.out.finish();
    }

    @Override // java.io.OutputStream, java.io.Flushable
    public void flush() throws IOException {
        this.out.flush();
    }

    @Override // java.io.OutputStream
    public void write(int i2) throws IOException {
        this.out.write(i2);
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i2, int i3) throws IOException {
        this.out.write(bArr, i2, i3);
    }

    public XZCompressorOutputStream(OutputStream outputStream, int i2) throws IOException {
        this.out = new XZOutputStream(outputStream, new LZMA2Options(i2));
    }
}
