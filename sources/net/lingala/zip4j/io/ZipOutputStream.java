package net.lingala.zip4j.io;

import java.io.IOException;
import java.io.OutputStream;
import net.lingala.zip4j.model.ZipModel;

/* loaded from: classes9.dex */
public class ZipOutputStream extends DeflaterOutputStream {
    public ZipOutputStream(OutputStream outputStream) {
        this(outputStream, null);
    }

    @Override // net.lingala.zip4j.io.DeflaterOutputStream, net.lingala.zip4j.io.CipherOutputStream, net.lingala.zip4j.io.BaseOutputStream, java.io.OutputStream
    public void write(int i2) throws IOException {
        write(new byte[]{(byte) i2}, 0, 1);
    }

    public ZipOutputStream(OutputStream outputStream, ZipModel zipModel) {
        super(outputStream, zipModel);
    }

    @Override // net.lingala.zip4j.io.DeflaterOutputStream, net.lingala.zip4j.io.CipherOutputStream, java.io.OutputStream
    public void write(byte[] bArr) throws IOException {
        write(bArr, 0, bArr.length);
    }

    @Override // net.lingala.zip4j.io.DeflaterOutputStream, net.lingala.zip4j.io.CipherOutputStream, java.io.OutputStream
    public void write(byte[] bArr, int i2, int i3) throws IOException {
        this.crc.update(bArr, i2, i3);
        updateTotalBytesRead(i3);
        super.write(bArr, i2, i3);
    }
}
