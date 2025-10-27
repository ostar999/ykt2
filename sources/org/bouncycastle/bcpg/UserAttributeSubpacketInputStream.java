package org.bouncycastle.bcpg;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import org.bouncycastle.bcpg.attr.ImageAttribute;

/* loaded from: classes9.dex */
public class UserAttributeSubpacketInputStream extends InputStream implements UserAttributeSubpacketTags {
    InputStream in;

    public UserAttributeSubpacketInputStream(InputStream inputStream) {
        this.in = inputStream;
    }

    private void readFully(byte[] bArr, int i2, int i3) throws IOException {
        if (i3 > 0) {
            int i4 = read();
            if (i4 < 0) {
                throw new EOFException();
            }
            bArr[i2] = (byte) i4;
            i2++;
            i3--;
        }
        while (i3 > 0) {
            int i5 = this.in.read(bArr, i2, i3);
            if (i5 < 0) {
                throw new EOFException();
            }
            i2 += i5;
            i3 -= i5;
        }
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        return this.in.available();
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        return this.in.read();
    }

    public UserAttributeSubpacket readPacket() throws IOException {
        int i2 = read();
        if (i2 < 0) {
            return null;
        }
        if (i2 >= 192) {
            i2 = i2 <= 223 ? ((i2 - 192) << 8) + this.in.read() + 192 : i2 == 255 ? (this.in.read() << 24) | (this.in.read() << 16) | (this.in.read() << 8) | this.in.read() : 0;
        }
        int i3 = this.in.read();
        if (i3 < 0) {
            throw new EOFException("unexpected EOF reading user attribute sub packet");
        }
        int i4 = i2 - 1;
        byte[] bArr = new byte[i4];
        readFully(bArr, 0, i4);
        return i3 != 1 ? new UserAttributeSubpacket(i3, bArr) : new ImageAttribute(bArr);
    }
}
