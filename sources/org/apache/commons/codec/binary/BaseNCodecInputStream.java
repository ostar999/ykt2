package org.apache.commons.codec.binary;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes9.dex */
public class BaseNCodecInputStream extends FilterInputStream {
    private final BaseNCodec baseNCodec;
    private final boolean doEncode;
    private final byte[] singleByte;

    public BaseNCodecInputStream(InputStream inputStream, BaseNCodec baseNCodec, boolean z2) {
        super(inputStream);
        this.singleByte = new byte[1];
        this.doEncode = z2;
        this.baseNCodec = baseNCodec;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public boolean markSupported() {
        return false;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read() throws IOException {
        int i2 = read(this.singleByte, 0, 1);
        while (i2 == 0) {
            i2 = read(this.singleByte, 0, 1);
        }
        if (i2 <= 0) {
            return -1;
        }
        byte b3 = this.singleByte[0];
        return b3 < 0 ? b3 + 256 : b3;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i2, int i3) throws IOException {
        bArr.getClass();
        if (i2 >= 0 && i3 >= 0) {
            if (i2 > bArr.length || i2 + i3 > bArr.length) {
                throw new IndexOutOfBoundsException();
            }
            if (i3 == 0) {
                return 0;
            }
            int results = 0;
            while (results == 0) {
                if (!this.baseNCodec.hasData()) {
                    byte[] bArr2 = new byte[this.doEncode ? 4096 : 8192];
                    int i4 = ((FilterInputStream) this).in.read(bArr2);
                    if (this.doEncode) {
                        this.baseNCodec.encode(bArr2, 0, i4);
                    } else {
                        this.baseNCodec.decode(bArr2, 0, i4);
                    }
                }
                results = this.baseNCodec.readResults(bArr, i2, i3);
            }
            return results;
        }
        throw new IndexOutOfBoundsException();
    }
}
