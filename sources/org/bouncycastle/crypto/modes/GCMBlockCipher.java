package org.bouncycastle.crypto.modes;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.modes.gcm.GCMMultiplier;
import org.bouncycastle.crypto.modes.gcm.Tables8kGCMMultiplier;
import org.bouncycastle.crypto.params.AEADParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.crypto.util.Pack;
import org.bouncycastle.util.Arrays;

/* loaded from: classes9.dex */
public class GCMBlockCipher implements AEADBlockCipher {
    private static final int BLOCK_SIZE = 16;
    private static final byte[] ZEROES = new byte[16];
    private byte[] A;
    private byte[] H;
    private byte[] J0;
    private byte[] S;
    private byte[] bufBlock;
    private int bufOff;
    private BlockCipher cipher;
    private byte[] counter;
    private boolean forEncryption;
    private byte[] initS;
    private KeyParameter keyParam;
    private byte[] macBlock;
    private int macSize;
    private GCMMultiplier multiplier;
    private byte[] nonce;
    private long totalLength;

    public GCMBlockCipher(BlockCipher blockCipher) {
        this(blockCipher, null);
    }

    public GCMBlockCipher(BlockCipher blockCipher, GCMMultiplier gCMMultiplier) {
        if (blockCipher.getBlockSize() != 16) {
            throw new IllegalArgumentException("cipher required with a block size of 16.");
        }
        gCMMultiplier = gCMMultiplier == null ? new Tables8kGCMMultiplier() : gCMMultiplier;
        this.cipher = blockCipher;
        this.multiplier = gCMMultiplier;
    }

    private void gCTRBlock(byte[] bArr, int i2, byte[] bArr2, int i3) throws IllegalStateException, DataLengthException {
        byte[] bArr3;
        for (int i4 = 15; i4 >= 12; i4--) {
            byte[] bArr4 = this.counter;
            byte b3 = (byte) ((bArr4[i4] + 1) & 255);
            bArr4[i4] = b3;
            if (b3 != 0) {
                break;
            }
        }
        byte[] bArr5 = new byte[16];
        this.cipher.processBlock(this.counter, 0, bArr5, 0);
        if (this.forEncryption) {
            System.arraycopy(ZEROES, i2, bArr5, i2, 16 - i2);
            bArr3 = bArr5;
        } else {
            bArr3 = bArr;
        }
        for (int i5 = i2 - 1; i5 >= 0; i5--) {
            byte b4 = (byte) (bArr5[i5] ^ bArr[i5]);
            bArr5[i5] = b4;
            bArr2[i3 + i5] = b4;
        }
        xor(this.S, bArr3);
        this.multiplier.multiplyH(this.S);
        this.totalLength += i2;
    }

    private byte[] gHASH(byte[] bArr) {
        byte[] bArr2 = new byte[16];
        for (int i2 = 0; i2 < bArr.length; i2 += 16) {
            byte[] bArr3 = new byte[16];
            System.arraycopy(bArr, i2, bArr3, 0, Math.min(bArr.length - i2, 16));
            xor(bArr2, bArr3);
            this.multiplier.multiplyH(bArr2);
        }
        return bArr2;
    }

    private static void packLength(long j2, byte[] bArr, int i2) {
        Pack.intToBigEndian((int) (j2 >>> 32), bArr, i2);
        Pack.intToBigEndian((int) j2, bArr, i2 + 4);
    }

    private int process(byte b3, byte[] bArr, int i2) throws IllegalStateException, DataLengthException {
        byte[] bArr2 = this.bufBlock;
        int i3 = this.bufOff;
        int i4 = i3 + 1;
        this.bufOff = i4;
        bArr2[i3] = b3;
        if (i4 != bArr2.length) {
            return 0;
        }
        gCTRBlock(bArr2, 16, bArr, i2);
        if (!this.forEncryption) {
            byte[] bArr3 = this.bufBlock;
            System.arraycopy(bArr3, 16, bArr3, 0, this.macSize);
        }
        this.bufOff = this.bufBlock.length - 16;
        return 16;
    }

    private void reset(boolean z2) {
        this.S = Arrays.clone(this.initS);
        this.counter = Arrays.clone(this.J0);
        this.bufOff = 0;
        this.totalLength = 0L;
        byte[] bArr = this.bufBlock;
        if (bArr != null) {
            Arrays.fill(bArr, (byte) 0);
        }
        if (z2) {
            this.macBlock = null;
        }
        this.cipher.reset();
    }

    private static void xor(byte[] bArr, byte[] bArr2) {
        for (int i2 = 15; i2 >= 0; i2--) {
            bArr[i2] = (byte) (bArr[i2] ^ bArr2[i2]);
        }
    }

    @Override // org.bouncycastle.crypto.modes.AEADBlockCipher
    public int doFinal(byte[] bArr, int i2) throws InvalidCipherTextException, IllegalStateException, DataLengthException {
        int i3 = this.bufOff;
        if (!this.forEncryption) {
            int i4 = this.macSize;
            if (i3 < i4) {
                throw new InvalidCipherTextException("data too short");
            }
            i3 -= i4;
        }
        if (i3 > 0) {
            byte[] bArr2 = new byte[16];
            System.arraycopy(this.bufBlock, 0, bArr2, 0, i3);
            gCTRBlock(bArr2, i3, bArr, i2);
        }
        byte[] bArr3 = new byte[16];
        packLength(this.A.length * 8, bArr3, 0);
        packLength(this.totalLength * 8, bArr3, 8);
        xor(this.S, bArr3);
        this.multiplier.multiplyH(this.S);
        byte[] bArr4 = new byte[16];
        this.cipher.processBlock(this.J0, 0, bArr4, 0);
        xor(bArr4, this.S);
        int i5 = this.macSize;
        byte[] bArr5 = new byte[i5];
        this.macBlock = bArr5;
        System.arraycopy(bArr4, 0, bArr5, 0, i5);
        if (this.forEncryption) {
            System.arraycopy(this.macBlock, 0, bArr, i2 + this.bufOff, this.macSize);
            i3 += this.macSize;
        } else {
            int i6 = this.macSize;
            byte[] bArr6 = new byte[i6];
            System.arraycopy(this.bufBlock, i3, bArr6, 0, i6);
            if (!Arrays.constantTimeAreEqual(this.macBlock, bArr6)) {
                throw new InvalidCipherTextException("mac check in GCM failed");
            }
        }
        reset(false);
        return i3;
    }

    @Override // org.bouncycastle.crypto.modes.AEADBlockCipher
    public String getAlgorithmName() {
        return this.cipher.getAlgorithmName() + "/GCM";
    }

    @Override // org.bouncycastle.crypto.modes.AEADBlockCipher
    public byte[] getMac() {
        return Arrays.clone(this.macBlock);
    }

    @Override // org.bouncycastle.crypto.modes.AEADBlockCipher
    public int getOutputSize(int i2) {
        return this.forEncryption ? i2 + this.bufOff + this.macSize : (i2 + this.bufOff) - this.macSize;
    }

    @Override // org.bouncycastle.crypto.modes.AEADBlockCipher
    public BlockCipher getUnderlyingCipher() {
        return this.cipher;
    }

    @Override // org.bouncycastle.crypto.modes.AEADBlockCipher
    public int getUpdateOutputSize(int i2) {
        return ((i2 + this.bufOff) / 16) * 16;
    }

    @Override // org.bouncycastle.crypto.modes.AEADBlockCipher
    public void init(boolean z2, CipherParameters cipherParameters) throws IllegalStateException, DataLengthException, IllegalArgumentException {
        KeyParameter key;
        this.forEncryption = z2;
        this.macBlock = null;
        if (cipherParameters instanceof AEADParameters) {
            AEADParameters aEADParameters = (AEADParameters) cipherParameters;
            this.nonce = aEADParameters.getNonce();
            this.A = aEADParameters.getAssociatedText();
            int macSize = aEADParameters.getMacSize();
            if (macSize < 96 || macSize > 128 || macSize % 8 != 0) {
                throw new IllegalArgumentException("Invalid value for MAC size: " + macSize);
            }
            this.macSize = macSize / 8;
            key = aEADParameters.getKey();
        } else {
            if (!(cipherParameters instanceof ParametersWithIV)) {
                throw new IllegalArgumentException("invalid parameters passed to GCM");
            }
            ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
            this.nonce = parametersWithIV.getIV();
            this.A = null;
            this.macSize = 16;
            key = (KeyParameter) parametersWithIV.getParameters();
        }
        this.keyParam = key;
        this.bufBlock = new byte[z2 ? 16 : this.macSize + 16];
        byte[] bArr = this.nonce;
        if (bArr == null || bArr.length < 1) {
            throw new IllegalArgumentException("IV must be at least 1 byte");
        }
        if (this.A == null) {
            this.A = new byte[0];
        }
        this.cipher.init(true, this.keyParam);
        byte[] bArr2 = new byte[16];
        this.H = bArr2;
        this.cipher.processBlock(ZEROES, 0, bArr2, 0);
        this.multiplier.init(this.H);
        this.initS = gHASH(this.A);
        byte[] bArr3 = this.nonce;
        if (bArr3.length == 12) {
            byte[] bArr4 = new byte[16];
            this.J0 = bArr4;
            System.arraycopy(bArr3, 0, bArr4, 0, bArr3.length);
            this.J0[15] = 1;
        } else {
            this.J0 = gHASH(bArr3);
            byte[] bArr5 = new byte[16];
            packLength(this.nonce.length * 8, bArr5, 8);
            xor(this.J0, bArr5);
            this.multiplier.multiplyH(this.J0);
        }
        this.S = Arrays.clone(this.initS);
        this.counter = Arrays.clone(this.J0);
        this.bufOff = 0;
        this.totalLength = 0L;
    }

    @Override // org.bouncycastle.crypto.modes.AEADBlockCipher
    public int processByte(byte b3, byte[] bArr, int i2) throws DataLengthException {
        return process(b3, bArr, i2);
    }

    @Override // org.bouncycastle.crypto.modes.AEADBlockCipher
    public int processBytes(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) throws IllegalStateException, DataLengthException {
        int i5 = 0;
        for (int i6 = 0; i6 != i3; i6++) {
            byte[] bArr3 = this.bufBlock;
            int i7 = this.bufOff;
            int i8 = i7 + 1;
            this.bufOff = i8;
            bArr3[i7] = bArr[i2 + i6];
            if (i8 == bArr3.length) {
                gCTRBlock(bArr3, 16, bArr2, i4 + i5);
                if (!this.forEncryption) {
                    byte[] bArr4 = this.bufBlock;
                    System.arraycopy(bArr4, 16, bArr4, 0, this.macSize);
                }
                this.bufOff = this.bufBlock.length - 16;
                i5 += 16;
            }
        }
        return i5;
    }

    @Override // org.bouncycastle.crypto.modes.AEADBlockCipher
    public void reset() {
        reset(true);
    }
}
