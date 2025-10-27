package org.bouncycastle.crypto.engines;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.tls.AlertDescription;

/* loaded from: classes9.dex */
public class SkipjackEngine implements BlockCipher {
    static final int BLOCK_SIZE = 8;
    static short[] ftable = {163, 215, 9, 131, 248, 72, 246, 244, 179, 33, 21, 120, 153, 177, 175, 249, 231, 45, 77, 138, 206, 76, 202, 46, 82, 149, 217, 30, 78, 56, 68, 40, 10, 223, 2, 160, 23, 241, 96, 104, 18, 183, 122, 195, 233, 250, 61, 83, 150, 132, 107, 186, 242, 99, 154, 25, 124, 174, 229, 245, 247, 22, 106, 162, 57, 182, 123, 15, 193, 147, 129, 27, 238, 180, 26, 234, 208, 145, 47, 184, 85, 185, 218, 133, 63, 65, 191, 224, 90, 88, 128, 95, 102, 11, 216, 144, 53, 213, 192, 167, 51, 6, 101, 105, 69, 0, 148, 86, 109, 152, 155, 118, 151, 252, 178, 194, 176, 254, 219, 32, 225, 235, 214, 228, 221, 71, 74, 29, 66, 237, 158, AlertDescription.unsupported_extension, 73, 60, 205, 67, 39, 210, 7, 212, 222, 199, 103, 24, 137, 203, 48, 31, 141, 198, 143, 170, 200, 116, 220, 201, 93, 92, 49, 164, AlertDescription.unrecognized_name, 136, 97, 44, 159, 13, 43, 135, 80, 130, 84, 100, 38, 125, 3, 64, 52, 75, 28, AlertDescription.unknown_psk_identity, 209, 196, 253, 59, 204, 251, 127, 171, 230, 62, 91, 165, 173, 4, 35, 156, 20, 81, 34, 240, 41, 121, AlertDescription.bad_certificate_status_response, 126, 255, 140, 14, 226, 12, 239, 188, AlertDescription.bad_certificate_hash_value, 117, AlertDescription.certificate_unobtainable, 55, 161, 236, 211, 142, 98, 139, 134, 16, 232, 8, 119, 17, 190, 146, 79, 36, 197, 50, 54, 157, 207, 243, 166, 187, 172, 94, 108, 169, 19, 87, 37, 181, 227, 189, 168, 58, 1, 5, 89, 42, 70};
    private boolean encrypting;
    private int[] key0;
    private int[] key1;
    private int[] key2;
    private int[] key3;

    private int g(int i2, int i3) {
        int i4 = (i3 >> 8) & 255;
        int i5 = i3 & 255;
        short[] sArr = ftable;
        int i6 = i4 ^ sArr[this.key0[i2] ^ i5];
        int i7 = i5 ^ sArr[this.key1[i2] ^ i6];
        int i8 = i6 ^ sArr[this.key2[i2] ^ i7];
        return (i8 << 8) + (sArr[this.key3[i2] ^ i8] ^ i7);
    }

    private int h(int i2, int i3) {
        int i4 = i3 & 255;
        int i5 = (i3 >> 8) & 255;
        short[] sArr = ftable;
        int i6 = i4 ^ sArr[this.key3[i2] ^ i5];
        int i7 = i5 ^ sArr[this.key2[i2] ^ i6];
        int i8 = i6 ^ sArr[this.key1[i2] ^ i7];
        return ((sArr[this.key0[i2] ^ i8] ^ i7) << 8) + i8;
    }

    public int decryptBlock(byte[] bArr, int i2, byte[] bArr2, int i3) {
        int i4 = (bArr[i2 + 0] << 8) + (bArr[i2 + 1] & 255);
        int i5 = (bArr[i2 + 2] << 8) + (bArr[i2 + 3] & 255);
        int i6 = (bArr[i2 + 4] << 8) + (bArr[i2 + 5] & 255);
        int i7 = (bArr[i2 + 6] << 8) + (bArr[i2 + 7] & 255);
        int i8 = 31;
        for (int i9 = 0; i9 < 2; i9++) {
            int i10 = 0;
            while (i10 < 8) {
                int iH = h(i8, i5);
                int i11 = (i6 ^ iH) ^ (i8 + 1);
                i8--;
                i10++;
                int i12 = i7;
                i7 = i4;
                i4 = iH;
                i5 = i11;
                i6 = i12;
            }
            int i13 = 0;
            while (i13 < 8) {
                int i14 = (i4 ^ i5) ^ (i8 + 1);
                int iH2 = h(i8, i5);
                i8--;
                i13++;
                int i15 = i7;
                i7 = i14;
                i4 = iH2;
                i5 = i6;
                i6 = i15;
            }
        }
        bArr2[i3 + 0] = (byte) (i4 >> 8);
        bArr2[i3 + 1] = (byte) i4;
        bArr2[i3 + 2] = (byte) (i5 >> 8);
        bArr2[i3 + 3] = (byte) i5;
        bArr2[i3 + 4] = (byte) (i6 >> 8);
        bArr2[i3 + 5] = (byte) i6;
        bArr2[i3 + 6] = (byte) (i7 >> 8);
        bArr2[i3 + 7] = (byte) i7;
        return 8;
    }

    public int encryptBlock(byte[] bArr, int i2, byte[] bArr2, int i3) {
        int i4 = (bArr[i2 + 0] << 8) + (bArr[i2 + 1] & 255);
        int i5 = (bArr[i2 + 2] << 8) + (bArr[i2 + 3] & 255);
        int i6 = (bArr[i2 + 4] << 8) + (bArr[i2 + 5] & 255);
        int i7 = (bArr[i2 + 6] << 8) + (bArr[i2 + 7] & 255);
        int i8 = 0;
        for (int i9 = 0; i9 < 2; i9++) {
            int i10 = 0;
            while (i10 < 8) {
                int iG = g(i8, i4);
                i8++;
                i10++;
                int i11 = i5;
                i5 = iG;
                i4 = (i7 ^ iG) ^ i8;
                i7 = i6;
                i6 = i11;
            }
            int i12 = 0;
            while (i12 < 8) {
                int i13 = i8 + 1;
                int i14 = (i5 ^ i4) ^ i13;
                int iG2 = g(i8, i4);
                i12++;
                i8 = i13;
                i5 = iG2;
                i4 = i7;
                i7 = i6;
                i6 = i14;
            }
        }
        bArr2[i3 + 0] = (byte) (i4 >> 8);
        bArr2[i3 + 1] = (byte) i4;
        bArr2[i3 + 2] = (byte) (i5 >> 8);
        bArr2[i3 + 3] = (byte) i5;
        bArr2[i3 + 4] = (byte) (i6 >> 8);
        bArr2[i3 + 5] = (byte) i6;
        bArr2[i3 + 6] = (byte) (i7 >> 8);
        bArr2[i3 + 7] = (byte) i7;
        return 8;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public String getAlgorithmName() {
        return "SKIPJACK";
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int getBlockSize() {
        return 8;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void init(boolean z2, CipherParameters cipherParameters) {
        if (!(cipherParameters instanceof KeyParameter)) {
            throw new IllegalArgumentException("invalid parameter passed to SKIPJACK init - " + cipherParameters.getClass().getName());
        }
        byte[] key = ((KeyParameter) cipherParameters).getKey();
        this.encrypting = z2;
        this.key0 = new int[32];
        this.key1 = new int[32];
        this.key2 = new int[32];
        this.key3 = new int[32];
        for (int i2 = 0; i2 < 32; i2++) {
            int i3 = i2 * 4;
            this.key0[i2] = key[i3 % 10] & 255;
            this.key1[i2] = key[(i3 + 1) % 10] & 255;
            this.key2[i2] = key[(i3 + 2) % 10] & 255;
            this.key3[i2] = key[(i3 + 3) % 10] & 255;
        }
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int processBlock(byte[] bArr, int i2, byte[] bArr2, int i3) {
        if (this.key1 == null) {
            throw new IllegalStateException("SKIPJACK engine not initialised");
        }
        if (i2 + 8 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        }
        if (i3 + 8 > bArr2.length) {
            throw new DataLengthException("output buffer too short");
        }
        if (this.encrypting) {
            encryptBlock(bArr, i2, bArr2, i3);
            return 8;
        }
        decryptBlock(bArr, i2, bArr2, i3);
        return 8;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void reset() {
    }
}
