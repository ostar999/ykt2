package org.bouncycastle.bcpg;

import java.io.IOException;
import java.io.OutputStream;
import org.bouncycastle.util.Arrays;

/* loaded from: classes9.dex */
public class UserAttributeSubpacket {
    protected byte[] data;
    int type;

    public UserAttributeSubpacket(int i2, byte[] bArr) {
        this.type = i2;
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
        outputStream.write(this.type);
        outputStream.write(this.data);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof UserAttributeSubpacket)) {
            return false;
        }
        UserAttributeSubpacket userAttributeSubpacket = (UserAttributeSubpacket) obj;
        return this.type == userAttributeSubpacket.type && Arrays.areEqual(this.data, userAttributeSubpacket.data);
    }

    public byte[] getData() {
        return this.data;
    }

    public int getType() {
        return this.type;
    }

    public int hashCode() {
        return this.type ^ Arrays.hashCode(this.data);
    }
}
