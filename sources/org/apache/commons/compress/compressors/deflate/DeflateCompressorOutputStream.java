package org.apache.commons.compress.compressors.deflate;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import org.apache.commons.compress.compressors.CompressorOutputStream;

/* loaded from: classes9.dex */
public class DeflateCompressorOutputStream extends CompressorOutputStream {
    private final Deflater deflater;
    private final DeflaterOutputStream out;

    public DeflateCompressorOutputStream(OutputStream outputStream) throws IOException {
        this(outputStream, new DeflateParameters());
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        try {
            this.out.close();
        } finally {
            this.deflater.end();
        }
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

    public DeflateCompressorOutputStream(OutputStream outputStream, DeflateParameters deflateParameters) throws IOException {
        Deflater deflater = new Deflater(deflateParameters.getCompressionLevel(), !deflateParameters.withZlibHeader());
        this.deflater = deflater;
        this.out = new DeflaterOutputStream(outputStream, deflater);
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i2, int i3) throws IOException {
        this.out.write(bArr, i2, i3);
    }
}
