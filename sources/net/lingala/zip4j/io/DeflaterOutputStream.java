package net.lingala.zip4j.io;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.Deflater;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipModel;
import net.lingala.zip4j.model.ZipParameters;

/* loaded from: classes9.dex */
public class DeflaterOutputStream extends CipherOutputStream {
    private byte[] buff;
    protected Deflater deflater;
    private boolean firstBytesRead;

    public DeflaterOutputStream(OutputStream outputStream, ZipModel zipModel) {
        super(outputStream, zipModel);
        this.deflater = new Deflater();
        this.buff = new byte[4096];
        this.firstBytesRead = false;
    }

    private void deflate() throws IOException {
        Deflater deflater = this.deflater;
        byte[] bArr = this.buff;
        int iDeflate = deflater.deflate(bArr, 0, bArr.length);
        if (iDeflate > 0) {
            if (this.deflater.finished()) {
                if (iDeflate == 4) {
                    return;
                }
                if (iDeflate < 4) {
                    decrementCompressedFileSize(4 - iDeflate);
                    return;
                }
                iDeflate -= 4;
            }
            if (this.firstBytesRead) {
                super.write(this.buff, 0, iDeflate);
            } else {
                super.write(this.buff, 2, iDeflate - 2);
                this.firstBytesRead = true;
            }
        }
    }

    @Override // net.lingala.zip4j.io.CipherOutputStream
    public void closeEntry() throws ZipException, IOException {
        if (this.zipParameters.getCompressionMethod() == 8) {
            if (!this.deflater.finished()) {
                this.deflater.finish();
                while (!this.deflater.finished()) {
                    deflate();
                }
            }
            this.firstBytesRead = false;
        }
        super.closeEntry();
    }

    @Override // net.lingala.zip4j.io.CipherOutputStream
    public void finish() throws ZipException, IOException {
        super.finish();
    }

    @Override // net.lingala.zip4j.io.CipherOutputStream
    public void putNextEntry(File file, ZipParameters zipParameters) throws ZipException, IOException {
        super.putNextEntry(file, zipParameters);
        if (zipParameters.getCompressionMethod() == 8) {
            this.deflater.reset();
            if ((zipParameters.getCompressionLevel() < 0 || zipParameters.getCompressionLevel() > 9) && zipParameters.getCompressionLevel() != -1) {
                throw new ZipException("invalid compression level for deflater. compression level should be in the range of 0-9");
            }
            this.deflater.setLevel(zipParameters.getCompressionLevel());
        }
    }

    @Override // net.lingala.zip4j.io.CipherOutputStream, java.io.OutputStream
    public void write(byte[] bArr) throws IOException {
        write(bArr, 0, bArr.length);
    }

    @Override // net.lingala.zip4j.io.CipherOutputStream, net.lingala.zip4j.io.BaseOutputStream, java.io.OutputStream
    public void write(int i2) throws IOException {
        write(new byte[]{(byte) i2}, 0, 1);
    }

    @Override // net.lingala.zip4j.io.CipherOutputStream, java.io.OutputStream
    public void write(byte[] bArr, int i2, int i3) throws IOException {
        if (this.zipParameters.getCompressionMethod() != 8) {
            super.write(bArr, i2, i3);
            return;
        }
        this.deflater.setInput(bArr, i2, i3);
        while (!this.deflater.needsInput()) {
            deflate();
        }
    }
}
