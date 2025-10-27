package org.bouncycastle.crypto.modes;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;

/* loaded from: classes9.dex */
public class CTSBlockCipher extends BufferedBlockCipher {
    private int blockSize;

    public CTSBlockCipher(BlockCipher blockCipher) {
        if ((blockCipher instanceof OFBBlockCipher) || (blockCipher instanceof CFBBlockCipher)) {
            throw new IllegalArgumentException("CTSBlockCipher can only accept ECB, or CBC ciphers");
        }
        this.cipher = blockCipher;
        int blockSize = blockCipher.getBlockSize();
        this.blockSize = blockSize;
        this.buf = new byte[blockSize * 2];
        this.bufOff = 0;
    }

    @Override // org.bouncycastle.crypto.BufferedBlockCipher
    public int doFinal(byte[] bArr, int i2) throws InvalidCipherTextException, IllegalStateException, DataLengthException {
        if (this.bufOff + i2 > bArr.length) {
            throw new DataLengthException("output buffer to small in doFinal");
        }
        int blockSize = this.cipher.getBlockSize();
        int i3 = this.bufOff - blockSize;
        byte[] bArr2 = new byte[blockSize];
        if (this.forEncryption) {
            this.cipher.processBlock(this.buf, 0, bArr2, 0);
            int i4 = this.bufOff;
            if (i4 < blockSize) {
                throw new DataLengthException("need at least one block of input for CTS");
            }
            while (true) {
                byte[] bArr3 = this.buf;
                if (i4 == bArr3.length) {
                    break;
                }
                bArr3[i4] = bArr2[i4 - blockSize];
                i4++;
            }
            for (int i5 = blockSize; i5 != this.bufOff; i5++) {
                byte[] bArr4 = this.buf;
                bArr4[i5] = (byte) (bArr4[i5] ^ bArr2[i5 - blockSize]);
            }
            BlockCipher blockCipher = this.cipher;
            if (blockCipher instanceof CBCBlockCipher) {
                ((CBCBlockCipher) blockCipher).getUnderlyingCipher().processBlock(this.buf, blockSize, bArr, i2);
            } else {
                blockCipher.processBlock(this.buf, blockSize, bArr, i2);
            }
            System.arraycopy(bArr2, 0, bArr, i2 + blockSize, i3);
        } else {
            byte[] bArr5 = new byte[blockSize];
            BlockCipher blockCipher2 = this.cipher;
            if (blockCipher2 instanceof CBCBlockCipher) {
                ((CBCBlockCipher) blockCipher2).getUnderlyingCipher().processBlock(this.buf, 0, bArr2, 0);
            } else {
                blockCipher2.processBlock(this.buf, 0, bArr2, 0);
            }
            for (int i6 = blockSize; i6 != this.bufOff; i6++) {
                int i7 = i6 - blockSize;
                bArr5[i7] = (byte) (bArr2[i7] ^ this.buf[i6]);
            }
            System.arraycopy(this.buf, blockSize, bArr2, 0, i3);
            this.cipher.processBlock(bArr2, 0, bArr, i2);
            System.arraycopy(bArr5, 0, bArr, i2 + blockSize, i3);
        }
        int i8 = this.bufOff;
        reset();
        return i8;
    }

    @Override // org.bouncycastle.crypto.BufferedBlockCipher
    public int getOutputSize(int i2) {
        return i2 + this.bufOff;
    }

    @Override // org.bouncycastle.crypto.BufferedBlockCipher
    public int getUpdateOutputSize(int i2) {
        int i3 = i2 + this.bufOff;
        byte[] bArr = this.buf;
        int length = i3 % bArr.length;
        return length == 0 ? i3 - bArr.length : i3 - length;
    }

    @Override // org.bouncycastle.crypto.BufferedBlockCipher
    public int processByte(byte b3, byte[] bArr, int i2) throws IllegalStateException, DataLengthException {
        int i3 = this.bufOff;
        byte[] bArr2 = this.buf;
        int i4 = 0;
        if (i3 == bArr2.length) {
            int iProcessBlock = this.cipher.processBlock(bArr2, 0, bArr, i2);
            byte[] bArr3 = this.buf;
            int i5 = this.blockSize;
            System.arraycopy(bArr3, i5, bArr3, 0, i5);
            this.bufOff = this.blockSize;
            i4 = iProcessBlock;
        }
        byte[] bArr4 = this.buf;
        int i6 = this.bufOff;
        this.bufOff = i6 + 1;
        bArr4[i6] = b3;
        return i4;
    }

    @Override // org.bouncycastle.crypto.BufferedBlockCipher
    public int processBytes(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) throws IllegalStateException, DataLengthException {
        if (i3 < 0) {
            throw new IllegalArgumentException("Can't have a negative input length!");
        }
        int blockSize = getBlockSize();
        int updateOutputSize = getUpdateOutputSize(i3);
        if (updateOutputSize > 0 && updateOutputSize + i4 > bArr2.length) {
            throw new DataLengthException("output buffer too short");
        }
        byte[] bArr3 = this.buf;
        int length = bArr3.length;
        int i5 = this.bufOff;
        int i6 = length - i5;
        int i7 = 0;
        if (i3 > i6) {
            System.arraycopy(bArr, i2, bArr3, i5, i6);
            int iProcessBlock = this.cipher.processBlock(this.buf, 0, bArr2, i4) + 0;
            byte[] bArr4 = this.buf;
            System.arraycopy(bArr4, blockSize, bArr4, 0, blockSize);
            this.bufOff = blockSize;
            i3 -= i6;
            i2 += i6;
            while (i3 > blockSize) {
                System.arraycopy(bArr, i2, this.buf, this.bufOff, blockSize);
                iProcessBlock += this.cipher.processBlock(this.buf, 0, bArr2, i4 + iProcessBlock);
                byte[] bArr5 = this.buf;
                System.arraycopy(bArr5, blockSize, bArr5, 0, blockSize);
                i3 -= blockSize;
                i2 += blockSize;
            }
            i7 = iProcessBlock;
        }
        System.arraycopy(bArr, i2, this.buf, this.bufOff, i3);
        this.bufOff += i3;
        return i7;
    }
}
