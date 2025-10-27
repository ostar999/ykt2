package org.apache.commons.codec.binary;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes9.dex */
public class BaseNCodecOutputStream extends FilterOutputStream {
    private final BaseNCodec baseNCodec;
    private final boolean doEncode;
    private final byte[] singleByte;

    public BaseNCodecOutputStream(OutputStream outputStream, BaseNCodec baseNCodec, boolean z2) {
        super(outputStream);
        this.singleByte = new byte[1];
        this.baseNCodec = baseNCodec;
        this.doEncode = z2;
    }

    private void flush(boolean z2) throws IOException {
        byte[] bArr;
        int results;
        int iAvailable = this.baseNCodec.available();
        if (iAvailable > 0 && (results = this.baseNCodec.readResults((bArr = new byte[iAvailable]), 0, iAvailable)) > 0) {
            ((FilterOutputStream) this).out.write(bArr, 0, results);
        }
        if (z2) {
            ((FilterOutputStream) this).out.flush();
        }
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this.doEncode) {
            this.baseNCodec.encode(this.singleByte, 0, -1);
        } else {
            this.baseNCodec.decode(this.singleByte, 0, -1);
        }
        flush();
        ((FilterOutputStream) this).out.close();
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public void write(int i2) throws IOException {
        byte[] bArr = this.singleByte;
        bArr[0] = (byte) i2;
        write(bArr, 0, 1);
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public void write(byte[] bArr, int i2, int i3) throws IOException {
        bArr.getClass();
        if (i2 >= 0 && i3 >= 0) {
            if (i2 > bArr.length || i2 + i3 > bArr.length) {
                throw new IndexOutOfBoundsException();
            }
            if (i3 > 0) {
                if (this.doEncode) {
                    this.baseNCodec.encode(bArr, i2, i3);
                } else {
                    this.baseNCodec.decode(bArr, i2, i3);
                }
                flush(false);
                return;
            }
            return;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream, java.io.Flushable
    public void flush() throws IOException {
        flush(true);
    }
}
