package org.bouncycastle.bcpg;

import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes9.dex */
public class SignatureSubpacket {
    boolean critical;
    protected byte[] data;
    int type;

    public SignatureSubpacket(int i2, boolean z2, byte[] bArr) {
        this.type = i2;
        this.critical = z2;
        this.data = bArr;
    }

    public void encode(OutputStream outputStream) throws IOException {
        byte b3;
        int length = this.data.length + 1;
        if (length >= 192) {
            if (length <= 8383) {
                length -= 192;
                b3 = (byte) (((length >> 8) & 255) + 192);
            } else {
                outputStream.write(255);
                outputStream.write((byte) (length >> 24));
                outputStream.write((byte) (length >> 16));
                b3 = (byte) (length >> 8);
            }
            outputStream.write(b3);
        }
        outputStream.write((byte) length);
        outputStream.write(this.critical ? this.type | 128 : this.type);
        outputStream.write(this.data);
    }

    public byte[] getData() {
        return this.data;
    }

    public int getType() {
        return this.type;
    }

    public boolean isCritical() {
        return this.critical;
    }
}
