package org.bouncycastle.crypto.signers;

import java.security.SecureRandom;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.SignerWithRecovery;
import org.bouncycastle.crypto.digests.RIPEMD128Digest;
import org.bouncycastle.crypto.digests.RIPEMD160Digest;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.crypto.params.ParametersWithSalt;
import org.bouncycastle.crypto.params.RSAKeyParameters;

/* loaded from: classes9.dex */
public class ISO9796d2PSSSigner implements SignerWithRecovery {
    public static final int TRAILER_IMPLICIT = 188;
    public static final int TRAILER_RIPEMD128 = 13004;
    public static final int TRAILER_RIPEMD160 = 12748;
    public static final int TRAILER_SHA1 = 13260;
    private byte[] block;
    private AsymmetricBlockCipher cipher;
    private Digest digest;
    private boolean fullMessage;
    private int hLen;
    private int keyBits;
    private byte[] mBuf;
    private int messageLength;
    private SecureRandom random;
    private byte[] recoveredMessage;
    private int saltLength;
    private byte[] standardSalt;
    private int trailer;

    public ISO9796d2PSSSigner(AsymmetricBlockCipher asymmetricBlockCipher, Digest digest, int i2) {
        this(asymmetricBlockCipher, digest, i2, false);
    }

    public ISO9796d2PSSSigner(AsymmetricBlockCipher asymmetricBlockCipher, Digest digest, int i2, boolean z2) {
        int i3;
        this.cipher = asymmetricBlockCipher;
        this.digest = digest;
        this.hLen = digest.getDigestSize();
        this.saltLength = i2;
        if (z2) {
            i3 = 188;
        } else if (digest instanceof SHA1Digest) {
            i3 = 13260;
        } else if (digest instanceof RIPEMD160Digest) {
            i3 = 12748;
        } else {
            if (!(digest instanceof RIPEMD128Digest)) {
                throw new IllegalArgumentException("no valid trailer for digest");
            }
            i3 = 13004;
        }
        this.trailer = i3;
    }

    private void ItoOSP(int i2, byte[] bArr) {
        bArr[0] = (byte) (i2 >>> 24);
        bArr[1] = (byte) (i2 >>> 16);
        bArr[2] = (byte) (i2 >>> 8);
        bArr[3] = (byte) (i2 >>> 0);
    }

    private void LtoOSP(long j2, byte[] bArr) {
        bArr[0] = (byte) (j2 >>> 56);
        bArr[1] = (byte) (j2 >>> 48);
        bArr[2] = (byte) (j2 >>> 40);
        bArr[3] = (byte) (j2 >>> 32);
        bArr[4] = (byte) (j2 >>> 24);
        bArr[5] = (byte) (j2 >>> 16);
        bArr[6] = (byte) (j2 >>> 8);
        bArr[7] = (byte) (j2 >>> 0);
    }

    private void clearBlock(byte[] bArr) {
        for (int i2 = 0; i2 != bArr.length; i2++) {
            bArr[i2] = 0;
        }
    }

    private boolean isSameAs(byte[] bArr, byte[] bArr2) {
        boolean z2 = this.messageLength == bArr2.length;
        for (int i2 = 0; i2 != bArr2.length; i2++) {
            if (bArr[i2] != bArr2[i2]) {
                z2 = false;
            }
        }
        return z2;
    }

    private byte[] maskGeneratorFunction1(byte[] bArr, int i2, int i3, int i4) {
        int i5;
        byte[] bArr2 = new byte[i4];
        byte[] bArr3 = new byte[this.hLen];
        byte[] bArr4 = new byte[4];
        this.digest.reset();
        int i6 = 0;
        while (true) {
            i5 = this.hLen;
            if (i6 >= i4 / i5) {
                break;
            }
            ItoOSP(i6, bArr4);
            this.digest.update(bArr, i2, i3);
            this.digest.update(bArr4, 0, 4);
            this.digest.doFinal(bArr3, 0);
            int i7 = this.hLen;
            System.arraycopy(bArr3, 0, bArr2, i6 * i7, i7);
            i6++;
        }
        if (i5 * i6 < i4) {
            ItoOSP(i6, bArr4);
            this.digest.update(bArr, i2, i3);
            this.digest.update(bArr4, 0, 4);
            this.digest.doFinal(bArr3, 0);
            int i8 = this.hLen;
            System.arraycopy(bArr3, 0, bArr2, i6 * i8, i4 - (i6 * i8));
        }
        return bArr2;
    }

    @Override // org.bouncycastle.crypto.Signer
    public byte[] generateSignature() throws CryptoException {
        int digestSize = this.digest.getDigestSize();
        byte[] bArr = new byte[digestSize];
        this.digest.doFinal(bArr, 0);
        byte[] bArr2 = new byte[8];
        LtoOSP(this.messageLength * 8, bArr2);
        this.digest.update(bArr2, 0, 8);
        this.digest.update(this.mBuf, 0, this.messageLength);
        this.digest.update(bArr, 0, digestSize);
        byte[] bArr3 = this.standardSalt;
        if (bArr3 == null) {
            bArr3 = new byte[this.saltLength];
            this.random.nextBytes(bArr3);
        }
        this.digest.update(bArr3, 0, bArr3.length);
        int digestSize2 = this.digest.getDigestSize();
        byte[] bArr4 = new byte[digestSize2];
        this.digest.doFinal(bArr4, 0);
        int i2 = this.trailer == 188 ? 1 : 2;
        byte[] bArr5 = this.block;
        int length = bArr5.length;
        int i3 = this.messageLength;
        int length2 = ((((length - i3) - bArr3.length) - this.hLen) - i2) - 1;
        bArr5[length2] = 1;
        int i4 = length2 + 1;
        System.arraycopy(this.mBuf, 0, bArr5, i4, i3);
        System.arraycopy(bArr3, 0, this.block, i4 + this.messageLength, bArr3.length);
        byte[] bArrMaskGeneratorFunction1 = maskGeneratorFunction1(bArr4, 0, digestSize2, (this.block.length - this.hLen) - i2);
        for (int i5 = 0; i5 != bArrMaskGeneratorFunction1.length; i5++) {
            byte[] bArr6 = this.block;
            bArr6[i5] = (byte) (bArr6[i5] ^ bArrMaskGeneratorFunction1[i5]);
        }
        byte[] bArr7 = this.block;
        int length3 = bArr7.length;
        int i6 = this.hLen;
        System.arraycopy(bArr4, 0, bArr7, (length3 - i6) - i2, i6);
        int i7 = this.trailer;
        if (i7 == 188) {
            byte[] bArr8 = this.block;
            bArr8[bArr8.length - 1] = PSSSigner.TRAILER_IMPLICIT;
        } else {
            byte[] bArr9 = this.block;
            bArr9[bArr9.length - 2] = (byte) (i7 >>> 8);
            bArr9[bArr9.length - 1] = (byte) i7;
        }
        byte[] bArr10 = this.block;
        bArr10[0] = (byte) (bArr10[0] & 127);
        byte[] bArrProcessBlock = this.cipher.processBlock(bArr10, 0, bArr10.length);
        clearBlock(this.mBuf);
        clearBlock(this.block);
        this.messageLength = 0;
        return bArrProcessBlock;
    }

    @Override // org.bouncycastle.crypto.SignerWithRecovery
    public byte[] getRecoveredMessage() {
        return this.recoveredMessage;
    }

    @Override // org.bouncycastle.crypto.SignerWithRecovery
    public boolean hasFullMessage() {
        return this.fullMessage;
    }

    @Override // org.bouncycastle.crypto.Signer
    public void init(boolean z2, CipherParameters cipherParameters) {
        RSAKeyParameters rSAKeyParameters;
        SecureRandom secureRandom;
        int length = this.saltLength;
        if (cipherParameters instanceof ParametersWithRandom) {
            ParametersWithRandom parametersWithRandom = (ParametersWithRandom) cipherParameters;
            rSAKeyParameters = (RSAKeyParameters) parametersWithRandom.getParameters();
            if (z2) {
                secureRandom = parametersWithRandom.getRandom();
                this.random = secureRandom;
            }
        } else if (cipherParameters instanceof ParametersWithSalt) {
            ParametersWithSalt parametersWithSalt = (ParametersWithSalt) cipherParameters;
            rSAKeyParameters = (RSAKeyParameters) parametersWithSalt.getParameters();
            byte[] salt = parametersWithSalt.getSalt();
            this.standardSalt = salt;
            length = salt.length;
            if (salt.length != this.saltLength) {
                throw new IllegalArgumentException("Fixed salt is of wrong length");
            }
        } else {
            rSAKeyParameters = (RSAKeyParameters) cipherParameters;
            if (z2) {
                secureRandom = new SecureRandom();
                this.random = secureRandom;
            }
        }
        this.cipher.init(z2, rSAKeyParameters);
        int iBitLength = rSAKeyParameters.getModulus().bitLength();
        this.keyBits = iBitLength;
        byte[] bArr = new byte[(iBitLength + 7) / 8];
        this.block = bArr;
        int i2 = this.trailer;
        int length2 = bArr.length;
        if (i2 == 188) {
            this.mBuf = new byte[(((length2 - this.digest.getDigestSize()) - length) - 1) - 1];
        } else {
            this.mBuf = new byte[(((length2 - this.digest.getDigestSize()) - length) - 1) - 2];
        }
        reset();
    }

    @Override // org.bouncycastle.crypto.Signer
    public void reset() {
        this.digest.reset();
        this.messageLength = 0;
        byte[] bArr = this.mBuf;
        if (bArr != null) {
            clearBlock(bArr);
        }
        byte[] bArr2 = this.recoveredMessage;
        if (bArr2 != null) {
            clearBlock(bArr2);
            this.recoveredMessage = null;
        }
        this.fullMessage = false;
    }

    @Override // org.bouncycastle.crypto.Signer
    public void update(byte b3) {
        int i2 = this.messageLength;
        byte[] bArr = this.mBuf;
        if (i2 >= bArr.length) {
            this.digest.update(b3);
        } else {
            this.messageLength = i2 + 1;
            bArr[i2] = b3;
        }
    }

    @Override // org.bouncycastle.crypto.Signer
    public void update(byte[] bArr, int i2, int i3) {
        while (i3 > 0 && this.messageLength < this.mBuf.length) {
            update(bArr[i2]);
            i2++;
            i3--;
        }
        if (i3 > 0) {
            this.digest.update(bArr, i2, i3);
        }
    }

    @Override // org.bouncycastle.crypto.SignerWithRecovery
    public void updateWithRecoveredMessage(byte[] bArr) throws InvalidCipherTextException {
        throw new RuntimeException("not implemented");
    }

    @Override // org.bouncycastle.crypto.Signer
    public boolean verifySignature(byte[] bArr) {
        int i2;
        try {
            byte[] bArrProcessBlock = this.cipher.processBlock(bArr, 0, bArr.length);
            int length = bArrProcessBlock.length;
            int i3 = this.keyBits;
            if (length < (i3 + 7) / 8) {
                int i4 = (i3 + 7) / 8;
                byte[] bArr2 = new byte[i4];
                System.arraycopy(bArrProcessBlock, 0, bArr2, i4 - bArrProcessBlock.length, bArrProcessBlock.length);
                clearBlock(bArrProcessBlock);
                bArrProcessBlock = bArr2;
            }
            if (((bArrProcessBlock[bArrProcessBlock.length - 1] & 255) ^ 188) == 0) {
                i2 = 1;
            } else {
                i2 = 2;
                int i5 = ((bArrProcessBlock[bArrProcessBlock.length - 2] & 255) << 8) | (bArrProcessBlock[bArrProcessBlock.length - 1] & 255);
                if (i5 != 12748) {
                    if (i5 != 13004) {
                        if (i5 != 13260) {
                            throw new IllegalArgumentException("unrecognised hash in signature");
                        }
                        if (!(this.digest instanceof SHA1Digest)) {
                            throw new IllegalStateException("signer should be initialised with SHA1");
                        }
                    } else if (!(this.digest instanceof RIPEMD128Digest)) {
                        throw new IllegalStateException("signer should be initialised with RIPEMD128");
                    }
                } else if (!(this.digest instanceof RIPEMD160Digest)) {
                    throw new IllegalStateException("signer should be initialised with RIPEMD160");
                }
            }
            int i6 = this.hLen;
            byte[] bArr3 = new byte[i6];
            this.digest.doFinal(bArr3, 0);
            int length2 = bArrProcessBlock.length;
            int i7 = this.hLen;
            byte[] bArrMaskGeneratorFunction1 = maskGeneratorFunction1(bArrProcessBlock, (length2 - i7) - i2, i7, (bArrProcessBlock.length - i7) - i2);
            for (int i8 = 0; i8 != bArrMaskGeneratorFunction1.length; i8++) {
                bArrProcessBlock[i8] = (byte) (bArrProcessBlock[i8] ^ bArrMaskGeneratorFunction1[i8]);
            }
            bArrProcessBlock[0] = (byte) (bArrProcessBlock[0] & 127);
            int i9 = 0;
            while (i9 != bArrProcessBlock.length && bArrProcessBlock[i9] != 1) {
                i9++;
            }
            int i10 = i9 + 1;
            if (i10 >= bArrProcessBlock.length) {
                clearBlock(bArrProcessBlock);
                return false;
            }
            this.fullMessage = i10 > 1;
            byte[] bArr4 = new byte[(bArrMaskGeneratorFunction1.length - i10) - this.saltLength];
            this.recoveredMessage = bArr4;
            System.arraycopy(bArrProcessBlock, i10, bArr4, 0, bArr4.length);
            byte[] bArr5 = new byte[8];
            LtoOSP(this.recoveredMessage.length * 8, bArr5);
            this.digest.update(bArr5, 0, 8);
            byte[] bArr6 = this.recoveredMessage;
            if (bArr6.length != 0) {
                this.digest.update(bArr6, 0, bArr6.length);
            }
            this.digest.update(bArr3, 0, i6);
            this.digest.update(bArrProcessBlock, i10 + this.recoveredMessage.length, this.saltLength);
            int digestSize = this.digest.getDigestSize();
            byte[] bArr7 = new byte[digestSize];
            this.digest.doFinal(bArr7, 0);
            int length3 = (bArrProcessBlock.length - i2) - digestSize;
            boolean z2 = true;
            for (int i11 = 0; i11 != digestSize; i11++) {
                if (bArr7[i11] != bArrProcessBlock[length3 + i11]) {
                    z2 = false;
                }
            }
            clearBlock(bArrProcessBlock);
            clearBlock(bArr7);
            if (!z2) {
                this.fullMessage = false;
                clearBlock(this.recoveredMessage);
                return false;
            }
            if (this.messageLength != 0) {
                if (!isSameAs(this.mBuf, this.recoveredMessage)) {
                    clearBlock(this.mBuf);
                    return false;
                }
                this.messageLength = 0;
            }
            clearBlock(this.mBuf);
            return true;
        } catch (Exception unused) {
            return false;
        }
    }
}
