package org.bouncycastle.crypto.modes;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;

/* loaded from: classes9.dex */
public class OpenPGPCFBBlockCipher implements BlockCipher {
    private byte[] FR;
    private byte[] FRE;
    private byte[] IV;
    private int blockSize;
    private BlockCipher cipher;
    private int count;
    private boolean forEncryption;

    public OpenPGPCFBBlockCipher(BlockCipher blockCipher) {
        this.cipher = blockCipher;
        int blockSize = blockCipher.getBlockSize();
        this.blockSize = blockSize;
        this.IV = new byte[blockSize];
        this.FR = new byte[blockSize];
        this.FRE = new byte[blockSize];
    }

    private int decryptBlock(byte[] bArr, int i2, byte[] bArr2, int i3) throws IllegalStateException, DataLengthException {
        int i4;
        int i5;
        int i6;
        int i7 = this.blockSize;
        if (i2 + i7 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        }
        if (i3 + i7 > bArr2.length) {
            throw new DataLengthException("output buffer too short");
        }
        int i8 = this.count;
        int i9 = 2;
        int i10 = 0;
        if (i8 > i7) {
            byte b3 = bArr[i2];
            this.FR[i7 - 2] = b3;
            bArr2[i3] = encryptByte(b3, i7 - 2);
            byte b4 = bArr[i2 + 1];
            byte[] bArr3 = this.FR;
            int i11 = this.blockSize;
            bArr3[i11 - 1] = b4;
            bArr2[i3 + 1] = encryptByte(b4, i11 - 1);
            this.cipher.processBlock(this.FR, 0, this.FRE, 0);
            while (i9 < this.blockSize) {
                byte b5 = bArr[i2 + i9];
                int i12 = i9 - 2;
                this.FR[i12] = b5;
                bArr2[i3 + i9] = encryptByte(b5, i12);
                i9++;
            }
        } else {
            if (i8 == 0) {
                this.cipher.processBlock(this.FR, 0, this.FRE, 0);
                while (true) {
                    i6 = this.blockSize;
                    if (i10 >= i6) {
                        break;
                    }
                    int i13 = i2 + i10;
                    this.FR[i10] = bArr[i13];
                    bArr2[i10] = encryptByte(bArr[i13], i10);
                    i10++;
                }
                i5 = this.count + i6;
            } else if (i8 == i7) {
                this.cipher.processBlock(this.FR, 0, this.FRE, 0);
                byte b6 = bArr[i2];
                byte b7 = bArr[i2 + 1];
                bArr2[i3] = encryptByte(b6, 0);
                bArr2[i3 + 1] = encryptByte(b7, 1);
                byte[] bArr4 = this.FR;
                System.arraycopy(bArr4, 2, bArr4, 0, this.blockSize - 2);
                byte[] bArr5 = this.FR;
                int i14 = this.blockSize;
                bArr5[i14 - 2] = b6;
                bArr5[i14 - 1] = b7;
                this.cipher.processBlock(bArr5, 0, this.FRE, 0);
                while (true) {
                    i4 = this.blockSize;
                    if (i9 >= i4) {
                        break;
                    }
                    byte b8 = bArr[i2 + i9];
                    int i15 = i9 - 2;
                    this.FR[i15] = b8;
                    bArr2[i3 + i9] = encryptByte(b8, i15);
                    i9++;
                }
                i5 = this.count + i4;
            }
            this.count = i5;
        }
        return this.blockSize;
    }

    private int encryptBlock(byte[] bArr, int i2, byte[] bArr2, int i3) throws IllegalStateException, DataLengthException {
        int i4;
        int i5 = this.blockSize;
        if (i2 + i5 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        }
        if (i3 + i5 > bArr2.length) {
            throw new DataLengthException("output buffer too short");
        }
        int i6 = this.count;
        int i7 = 2;
        int i8 = 0;
        if (i6 > i5) {
            byte[] bArr3 = this.FR;
            int i9 = i5 - 2;
            byte bEncryptByte = encryptByte(bArr[i2], i5 - 2);
            bArr2[i3] = bEncryptByte;
            bArr3[i9] = bEncryptByte;
            byte[] bArr4 = this.FR;
            int i10 = this.blockSize;
            int i11 = i10 - 1;
            byte bEncryptByte2 = encryptByte(bArr[i2 + 1], i10 - 1);
            bArr2[i3 + 1] = bEncryptByte2;
            bArr4[i11] = bEncryptByte2;
            this.cipher.processBlock(this.FR, 0, this.FRE, 0);
            while (i7 < this.blockSize) {
                byte[] bArr5 = this.FR;
                int i12 = i7 - 2;
                byte bEncryptByte3 = encryptByte(bArr[i2 + i7], i12);
                bArr2[i3 + i7] = bEncryptByte3;
                bArr5[i12] = bEncryptByte3;
                i7++;
            }
        } else {
            if (i6 != 0) {
                if (i6 == i5) {
                    this.cipher.processBlock(this.FR, 0, this.FRE, 0);
                    bArr2[i3] = encryptByte(bArr[i2], 0);
                    bArr2[i3 + 1] = encryptByte(bArr[i2 + 1], 1);
                    byte[] bArr6 = this.FR;
                    System.arraycopy(bArr6, 2, bArr6, 0, this.blockSize - 2);
                    System.arraycopy(bArr2, i3, this.FR, this.blockSize - 2, 2);
                    this.cipher.processBlock(this.FR, 0, this.FRE, 0);
                    while (true) {
                        i4 = this.blockSize;
                        if (i7 >= i4) {
                            break;
                        }
                        byte[] bArr7 = this.FR;
                        int i13 = i7 - 2;
                        byte bEncryptByte4 = encryptByte(bArr[i2 + i7], i13);
                        bArr2[i3 + i7] = bEncryptByte4;
                        bArr7[i13] = bEncryptByte4;
                        i7++;
                    }
                }
            } else {
                this.cipher.processBlock(this.FR, 0, this.FRE, 0);
                while (true) {
                    i4 = this.blockSize;
                    if (i8 >= i4) {
                        break;
                    }
                    byte[] bArr8 = this.FR;
                    byte bEncryptByte5 = encryptByte(bArr[i2 + i8], i8);
                    bArr2[i3 + i8] = bEncryptByte5;
                    bArr8[i8] = bEncryptByte5;
                    i8++;
                }
            }
            this.count += i4;
        }
        return this.blockSize;
    }

    private byte encryptByte(byte b3, int i2) {
        return (byte) (b3 ^ this.FRE[i2]);
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public String getAlgorithmName() {
        return this.cipher.getAlgorithmName() + "/OpenPGPCFB";
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int getBlockSize() {
        return this.cipher.getBlockSize();
    }

    public BlockCipher getUnderlyingCipher() {
        return this.cipher;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void init(boolean z2, CipherParameters cipherParameters) throws IllegalArgumentException {
        this.forEncryption = z2;
        reset();
        this.cipher.init(true, cipherParameters);
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int processBlock(byte[] bArr, int i2, byte[] bArr2, int i3) throws IllegalStateException, DataLengthException {
        return this.forEncryption ? encryptBlock(bArr, i2, bArr2, i3) : decryptBlock(bArr, i2, bArr2, i3);
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void reset() {
        this.count = 0;
        byte[] bArr = this.IV;
        byte[] bArr2 = this.FR;
        System.arraycopy(bArr, 0, bArr2, 0, bArr2.length);
        this.cipher.reset();
    }
}
