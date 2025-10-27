package net.lingala.zip4j.crypto.engine;

/* loaded from: classes9.dex */
public class ZipCryptoEngine {
    private static final int[] CRC_TABLE = new int[256];
    private final int[] keys = new int[3];

    static {
        for (int i2 = 0; i2 < 256; i2++) {
            int i3 = i2;
            for (int i4 = 0; i4 < 8; i4++) {
                i3 = (i3 & 1) == 1 ? (i3 >>> 1) ^ (-306674912) : i3 >>> 1;
            }
            CRC_TABLE[i2] = i3;
        }
    }

    private int crc32(int i2, byte b3) {
        return CRC_TABLE[(i2 ^ b3) & 255] ^ (i2 >>> 8);
    }

    public byte decryptByte() {
        int i2 = this.keys[2] | 2;
        return (byte) ((i2 * (i2 ^ 1)) >>> 8);
    }

    public void initKeys(char[] cArr) {
        int[] iArr = this.keys;
        iArr[0] = 305419896;
        iArr[1] = 591751049;
        iArr[2] = 878082192;
        for (char c3 : cArr) {
            updateKeys((byte) (c3 & 255));
        }
    }

    public void updateKeys(byte b3) {
        int[] iArr = this.keys;
        iArr[0] = crc32(iArr[0], b3);
        int[] iArr2 = this.keys;
        int i2 = iArr2[1] + (iArr2[0] & 255);
        iArr2[1] = i2;
        int i3 = (i2 * 134775813) + 1;
        iArr2[1] = i3;
        iArr2[2] = crc32(iArr2[2], (byte) (i3 >> 24));
    }
}
