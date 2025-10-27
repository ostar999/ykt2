package org.bouncycastle.crypto.modes;

import androidx.core.view.MotionEventCompat;
import com.google.common.base.Ascii;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.params.ParametersWithIV;

/* loaded from: classes9.dex */
public class GOFBBlockCipher implements BlockCipher {
    static final int C1 = 16843012;
    static final int C2 = 16843009;
    private byte[] IV;
    int N3;
    int N4;
    private final int blockSize;
    private final BlockCipher cipher;
    boolean firstStep = true;
    private byte[] ofbOutV;
    private byte[] ofbV;

    public GOFBBlockCipher(BlockCipher blockCipher) {
        this.cipher = blockCipher;
        int blockSize = blockCipher.getBlockSize();
        this.blockSize = blockSize;
        if (blockSize != 8) {
            throw new IllegalArgumentException("GCTR only for 64 bit block ciphers");
        }
        this.IV = new byte[blockCipher.getBlockSize()];
        this.ofbV = new byte[blockCipher.getBlockSize()];
        this.ofbOutV = new byte[blockCipher.getBlockSize()];
    }

    private int bytesToint(byte[] bArr, int i2) {
        return ((bArr[i2 + 3] << Ascii.CAN) & (-16777216)) + ((bArr[i2 + 2] << 16) & 16711680) + ((bArr[i2 + 1] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) + (bArr[i2] & 255);
    }

    private void intTobytes(int i2, byte[] bArr, int i3) {
        bArr[i3 + 3] = (byte) (i2 >>> 24);
        bArr[i3 + 2] = (byte) (i2 >>> 16);
        bArr[i3 + 1] = (byte) (i2 >>> 8);
        bArr[i3] = (byte) i2;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public String getAlgorithmName() {
        return this.cipher.getAlgorithmName() + "/GCTR";
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int getBlockSize() {
        return this.blockSize;
    }

    public BlockCipher getUnderlyingCipher() {
        return this.cipher;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void init(boolean z2, CipherParameters cipherParameters) throws IllegalArgumentException {
        BlockCipher blockCipher;
        this.firstStep = true;
        this.N3 = 0;
        this.N4 = 0;
        if (cipherParameters instanceof ParametersWithIV) {
            ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
            byte[] iv = parametersWithIV.getIV();
            int length = iv.length;
            byte[] bArr = this.IV;
            if (length < bArr.length) {
                System.arraycopy(iv, 0, bArr, bArr.length - iv.length, iv.length);
                int i2 = 0;
                while (true) {
                    byte[] bArr2 = this.IV;
                    if (i2 >= bArr2.length - iv.length) {
                        break;
                    }
                    bArr2[i2] = 0;
                    i2++;
                }
            } else {
                System.arraycopy(iv, 0, bArr, 0, bArr.length);
            }
            reset();
            blockCipher = this.cipher;
            cipherParameters = parametersWithIV.getParameters();
        } else {
            reset();
            blockCipher = this.cipher;
        }
        blockCipher.init(true, cipherParameters);
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int processBlock(byte[] bArr, int i2, byte[] bArr2, int i3) throws IllegalStateException, DataLengthException {
        int i4 = this.blockSize;
        if (i2 + i4 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        }
        if (i4 + i3 > bArr2.length) {
            throw new DataLengthException("output buffer too short");
        }
        if (this.firstStep) {
            this.firstStep = false;
            this.cipher.processBlock(this.ofbV, 0, this.ofbOutV, 0);
            this.N3 = bytesToint(this.ofbOutV, 0);
            this.N4 = bytesToint(this.ofbOutV, 4);
        }
        int i5 = this.N3 + 16843009;
        this.N3 = i5;
        this.N4 += 16843012;
        intTobytes(i5, this.ofbV, 0);
        intTobytes(this.N4, this.ofbV, 4);
        this.cipher.processBlock(this.ofbV, 0, this.ofbOutV, 0);
        int i6 = 0;
        while (true) {
            int i7 = this.blockSize;
            if (i6 >= i7) {
                byte[] bArr3 = this.ofbV;
                System.arraycopy(bArr3, i7, bArr3, 0, bArr3.length - i7);
                byte[] bArr4 = this.ofbOutV;
                byte[] bArr5 = this.ofbV;
                int length = bArr5.length;
                int i8 = this.blockSize;
                System.arraycopy(bArr4, 0, bArr5, length - i8, i8);
                return this.blockSize;
            }
            bArr2[i3 + i6] = (byte) (this.ofbOutV[i6] ^ bArr[i2 + i6]);
            i6++;
        }
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void reset() {
        byte[] bArr = this.IV;
        System.arraycopy(bArr, 0, this.ofbV, 0, bArr.length);
        this.cipher.reset();
    }
}
