package net.lingala.zip4j.io;

import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import net.lingala.zip4j.unzip.UnzipEngine;

/* loaded from: classes9.dex */
public class InflaterInputStream extends PartInputStream {
    private byte[] buff;
    private long bytesWritten;
    private Inflater inflater;
    private byte[] oneByteBuff;
    private long uncompressedSize;
    private UnzipEngine unzipEngine;

    public InflaterInputStream(RandomAccessFile randomAccessFile, long j2, long j3, UnzipEngine unzipEngine) {
        super(randomAccessFile, j2, j3, unzipEngine);
        this.oneByteBuff = new byte[1];
        this.inflater = new Inflater(true);
        this.buff = new byte[4096];
        this.unzipEngine = unzipEngine;
        this.bytesWritten = 0L;
        this.uncompressedSize = unzipEngine.getFileHeader().getUncompressedSize();
    }

    private void fill() throws IOException {
        byte[] bArr = this.buff;
        int i2 = super.read(bArr, 0, bArr.length);
        if (i2 == -1) {
            throw new EOFException("Unexpected end of ZLIB input stream");
        }
        this.inflater.setInput(this.buff, 0, i2);
    }

    private void finishInflating() throws IOException {
        while (super.read(new byte[1024], 0, 1024) != -1) {
        }
        checkAndReadAESMacBytes();
    }

    @Override // net.lingala.zip4j.io.PartInputStream, net.lingala.zip4j.io.BaseInputStream, java.io.InputStream
    public int available() {
        return !this.inflater.finished() ? 1 : 0;
    }

    @Override // net.lingala.zip4j.io.PartInputStream, java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.inflater.end();
        super.close();
    }

    @Override // net.lingala.zip4j.io.PartInputStream, net.lingala.zip4j.io.BaseInputStream
    public UnzipEngine getUnzipEngine() {
        return super.getUnzipEngine();
    }

    @Override // net.lingala.zip4j.io.PartInputStream, net.lingala.zip4j.io.BaseInputStream, java.io.InputStream
    public int read() throws IOException {
        if (read(this.oneByteBuff, 0, 1) == -1) {
            return -1;
        }
        return this.oneByteBuff[0] & 255;
    }

    @Override // net.lingala.zip4j.io.PartInputStream, net.lingala.zip4j.io.BaseInputStream
    public void seek(long j2) throws IOException {
        super.seek(j2);
    }

    @Override // net.lingala.zip4j.io.PartInputStream, java.io.InputStream
    public long skip(long j2) throws DataFormatException, IOException {
        if (j2 < 0) {
            throw new IllegalArgumentException("negative skip length");
        }
        int iMin = (int) Math.min(j2, 2147483647L);
        byte[] bArr = new byte[512];
        int i2 = 0;
        while (i2 < iMin) {
            int i3 = iMin - i2;
            if (i3 > 512) {
                i3 = 512;
            }
            int i4 = read(bArr, 0, i3);
            if (i4 == -1) {
                break;
            }
            i2 += i4;
        }
        return i2;
    }

    @Override // net.lingala.zip4j.io.PartInputStream, java.io.InputStream
    public int read(byte[] bArr) throws IOException {
        if (bArr != null) {
            return read(bArr, 0, bArr.length);
        }
        throw new NullPointerException("input buffer is null");
    }

    @Override // net.lingala.zip4j.io.PartInputStream, java.io.InputStream
    public int read(byte[] bArr, int i2, int i3) throws DataFormatException, IOException {
        if (bArr != null) {
            if (i2 < 0 || i3 < 0 || i3 > bArr.length - i2) {
                throw new IndexOutOfBoundsException();
            }
            if (i3 == 0) {
                return 0;
            }
            try {
                if (this.bytesWritten >= this.uncompressedSize) {
                    finishInflating();
                    return -1;
                }
                while (true) {
                    int iInflate = this.inflater.inflate(bArr, i2, i3);
                    if (iInflate == 0) {
                        if (this.inflater.finished() || this.inflater.needsDictionary()) {
                            break;
                        }
                        if (this.inflater.needsInput()) {
                            fill();
                        }
                    } else {
                        this.bytesWritten += iInflate;
                        return iInflate;
                    }
                }
                finishInflating();
                return -1;
            } catch (DataFormatException e2) {
                String message = e2.getMessage() != null ? e2.getMessage() : "Invalid ZLIB data format";
                UnzipEngine unzipEngine = this.unzipEngine;
                if (unzipEngine != null && unzipEngine.getLocalFileHeader().isEncrypted() && this.unzipEngine.getLocalFileHeader().getEncryptionMethod() == 0) {
                    message = message + " - Wrong Password?";
                }
                throw new IOException(message);
            }
        }
        throw new NullPointerException("input buffer is null");
    }
}
