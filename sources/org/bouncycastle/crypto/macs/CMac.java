package org.bouncycastle.crypto.macs;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.Mac;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.ISO7816d4Padding;

/* loaded from: classes9.dex */
public class CMac implements Mac {
    private static final byte CONSTANT_128 = -121;
    private static final byte CONSTANT_64 = 27;
    private byte[] L;
    private byte[] Lu;
    private byte[] Lu2;
    private byte[] ZEROES;
    private byte[] buf;
    private int bufOff;
    private BlockCipher cipher;
    private byte[] mac;
    private int macSize;

    public CMac(BlockCipher blockCipher) {
        this(blockCipher, blockCipher.getBlockSize() * 8);
    }

    public CMac(BlockCipher blockCipher, int i2) {
        if (i2 % 8 != 0) {
            throw new IllegalArgumentException("MAC size must be multiple of 8");
        }
        if (i2 > blockCipher.getBlockSize() * 8) {
            throw new IllegalArgumentException("MAC size must be less or equal to " + (blockCipher.getBlockSize() * 8));
        }
        if (blockCipher.getBlockSize() != 8 && blockCipher.getBlockSize() != 16) {
            throw new IllegalArgumentException("Block size must be either 64 or 128 bits");
        }
        this.cipher = new CBCBlockCipher(blockCipher);
        this.macSize = i2 / 8;
        this.mac = new byte[blockCipher.getBlockSize()];
        this.buf = new byte[blockCipher.getBlockSize()];
        this.ZEROES = new byte[blockCipher.getBlockSize()];
        this.bufOff = 0;
    }

    private byte[] doubleLu(byte[] bArr) {
        int i2 = 0;
        int i3 = (bArr[0] & 255) >> 7;
        byte[] bArr2 = new byte[bArr.length];
        while (i2 < bArr.length - 1) {
            int i4 = i2 + 1;
            bArr2[i2] = (byte) ((bArr[i2] << 1) + ((bArr[i4] & 255) >> 7));
            i2 = i4;
        }
        bArr2[bArr.length - 1] = (byte) (bArr[bArr.length - 1] << 1);
        if (i3 == 1) {
            int length = bArr.length - 1;
            bArr2[length] = (byte) ((bArr.length == 16 ? CONSTANT_128 : (byte) 27) ^ bArr2[length]);
        }
        return bArr2;
    }

    @Override // org.bouncycastle.crypto.Mac
    public int doFinal(byte[] bArr, int i2) throws IllegalStateException, DataLengthException {
        byte[] bArr2;
        if (this.bufOff == this.cipher.getBlockSize()) {
            bArr2 = this.Lu;
        } else {
            new ISO7816d4Padding().addPadding(this.buf, this.bufOff);
            bArr2 = this.Lu2;
        }
        int i3 = 0;
        while (true) {
            byte[] bArr3 = this.mac;
            if (i3 >= bArr3.length) {
                this.cipher.processBlock(this.buf, 0, bArr3, 0);
                System.arraycopy(this.mac, 0, bArr, i2, this.macSize);
                reset();
                return this.macSize;
            }
            byte[] bArr4 = this.buf;
            bArr4[i3] = (byte) (bArr4[i3] ^ bArr2[i3]);
            i3++;
        }
    }

    @Override // org.bouncycastle.crypto.Mac
    public String getAlgorithmName() {
        return this.cipher.getAlgorithmName();
    }

    @Override // org.bouncycastle.crypto.Mac
    public int getMacSize() {
        return this.macSize;
    }

    @Override // org.bouncycastle.crypto.Mac
    public void init(CipherParameters cipherParameters) throws IllegalStateException, DataLengthException, IllegalArgumentException {
        reset();
        this.cipher.init(true, cipherParameters);
        byte[] bArr = this.ZEROES;
        byte[] bArr2 = new byte[bArr.length];
        this.L = bArr2;
        this.cipher.processBlock(bArr, 0, bArr2, 0);
        byte[] bArrDoubleLu = doubleLu(this.L);
        this.Lu = bArrDoubleLu;
        this.Lu2 = doubleLu(bArrDoubleLu);
        this.cipher.init(true, cipherParameters);
    }

    @Override // org.bouncycastle.crypto.Mac
    public void reset() {
        int i2 = 0;
        while (true) {
            byte[] bArr = this.buf;
            if (i2 >= bArr.length) {
                this.bufOff = 0;
                this.cipher.reset();
                return;
            } else {
                bArr[i2] = 0;
                i2++;
            }
        }
    }

    @Override // org.bouncycastle.crypto.Mac
    public void update(byte b3) throws IllegalStateException, DataLengthException {
        int i2 = this.bufOff;
        byte[] bArr = this.buf;
        if (i2 == bArr.length) {
            this.cipher.processBlock(bArr, 0, this.mac, 0);
            this.bufOff = 0;
        }
        byte[] bArr2 = this.buf;
        int i3 = this.bufOff;
        this.bufOff = i3 + 1;
        bArr2[i3] = b3;
    }

    @Override // org.bouncycastle.crypto.Mac
    public void update(byte[] bArr, int i2, int i3) throws IllegalStateException, DataLengthException {
        if (i3 < 0) {
            throw new IllegalArgumentException("Can't have a negative input length!");
        }
        int blockSize = this.cipher.getBlockSize();
        int i4 = this.bufOff;
        int i5 = blockSize - i4;
        if (i3 > i5) {
            System.arraycopy(bArr, i2, this.buf, i4, i5);
            this.cipher.processBlock(this.buf, 0, this.mac, 0);
            this.bufOff = 0;
            i3 -= i5;
            i2 += i5;
            while (i3 > blockSize) {
                this.cipher.processBlock(bArr, i2, this.mac, 0);
                i3 -= blockSize;
                i2 += blockSize;
            }
        }
        System.arraycopy(bArr, i2, this.buf, this.bufOff, i3);
        this.bufOff += i3;
    }
}
