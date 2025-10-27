package cn.hutool.core.io.checksum;

import java.io.Serializable;
import java.util.zip.Checksum;

/* loaded from: classes.dex */
public class CRC8 implements Checksum, Serializable {
    private static final long serialVersionUID = 1;
    private final short[] crcTable = new short[256];
    private final short init;
    private short value;

    public CRC8(int i2, short s2) {
        this.init = s2;
        this.value = s2;
        for (int i3 = 0; i3 < 256; i3++) {
            int i4 = i3;
            for (int i5 = 0; i5 < 8; i5++) {
                i4 = (i4 & 1) != 0 ? (i4 >>> 1) ^ i2 : i4 >>> 1;
            }
            this.crcTable[i3] = (short) i4;
        }
    }

    @Override // java.util.zip.Checksum
    public long getValue() {
        return this.value & 255;
    }

    @Override // java.util.zip.Checksum
    public void reset() {
        this.value = this.init;
    }

    @Override // java.util.zip.Checksum
    public void update(byte[] bArr, int i2, int i3) {
        for (int i4 = 0; i4 < i3; i4++) {
            byte b3 = bArr[i2 + i4];
            short s2 = this.value;
            this.value = (short) (this.crcTable[(b3 ^ s2) & 255] ^ (s2 << 8));
        }
    }

    @Override // java.util.zip.Checksum
    public void update(byte[] bArr) {
        update(bArr, 0, bArr.length);
    }

    @Override // java.util.zip.Checksum
    public void update(int i2) {
        update(new byte[]{(byte) i2}, 0, 1);
    }
}
