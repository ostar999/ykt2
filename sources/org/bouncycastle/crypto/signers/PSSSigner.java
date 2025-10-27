package org.bouncycastle.crypto.signers;

import java.security.SecureRandom;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.Signer;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.crypto.params.RSABlindingParameters;
import org.bouncycastle.crypto.params.RSAKeyParameters;

/* loaded from: classes9.dex */
public class PSSSigner implements Signer {
    public static final byte TRAILER_IMPLICIT = -68;
    private byte[] block;
    private AsymmetricBlockCipher cipher;
    private Digest contentDigest;
    private int emBits;
    private int hLen;
    private byte[] mDash;
    private Digest mgfDigest;
    private int mgfhLen;
    private SecureRandom random;
    private int sLen;
    private byte[] salt;
    private byte trailer;

    public PSSSigner(AsymmetricBlockCipher asymmetricBlockCipher, Digest digest, int i2) {
        this(asymmetricBlockCipher, digest, i2, TRAILER_IMPLICIT);
    }

    public PSSSigner(AsymmetricBlockCipher asymmetricBlockCipher, Digest digest, int i2, byte b3) {
        this(asymmetricBlockCipher, digest, digest, i2, b3);
    }

    public PSSSigner(AsymmetricBlockCipher asymmetricBlockCipher, Digest digest, Digest digest2, int i2) {
        this(asymmetricBlockCipher, digest, digest2, i2, TRAILER_IMPLICIT);
    }

    public PSSSigner(AsymmetricBlockCipher asymmetricBlockCipher, Digest digest, Digest digest2, int i2, byte b3) {
        this.cipher = asymmetricBlockCipher;
        this.contentDigest = digest;
        this.mgfDigest = digest2;
        this.hLen = digest.getDigestSize();
        this.mgfhLen = digest2.getDigestSize();
        this.sLen = i2;
        this.salt = new byte[i2];
        this.mDash = new byte[i2 + 8 + this.hLen];
        this.trailer = b3;
    }

    private void ItoOSP(int i2, byte[] bArr) {
        bArr[0] = (byte) (i2 >>> 24);
        bArr[1] = (byte) (i2 >>> 16);
        bArr[2] = (byte) (i2 >>> 8);
        bArr[3] = (byte) (i2 >>> 0);
    }

    private void clearBlock(byte[] bArr) {
        for (int i2 = 0; i2 != bArr.length; i2++) {
            bArr[i2] = 0;
        }
    }

    private byte[] maskGeneratorFunction1(byte[] bArr, int i2, int i3, int i4) {
        int i5;
        byte[] bArr2 = new byte[i4];
        byte[] bArr3 = new byte[this.mgfhLen];
        byte[] bArr4 = new byte[4];
        this.mgfDigest.reset();
        int i6 = 0;
        while (true) {
            i5 = this.mgfhLen;
            if (i6 >= i4 / i5) {
                break;
            }
            ItoOSP(i6, bArr4);
            this.mgfDigest.update(bArr, i2, i3);
            this.mgfDigest.update(bArr4, 0, 4);
            this.mgfDigest.doFinal(bArr3, 0);
            int i7 = this.mgfhLen;
            System.arraycopy(bArr3, 0, bArr2, i6 * i7, i7);
            i6++;
        }
        if (i5 * i6 < i4) {
            ItoOSP(i6, bArr4);
            this.mgfDigest.update(bArr, i2, i3);
            this.mgfDigest.update(bArr4, 0, 4);
            this.mgfDigest.doFinal(bArr3, 0);
            int i8 = this.mgfhLen;
            System.arraycopy(bArr3, 0, bArr2, i6 * i8, i4 - (i6 * i8));
        }
        return bArr2;
    }

    @Override // org.bouncycastle.crypto.Signer
    public byte[] generateSignature() throws DataLengthException, CryptoException {
        Digest digest = this.contentDigest;
        byte[] bArr = this.mDash;
        digest.doFinal(bArr, (bArr.length - this.hLen) - this.sLen);
        if (this.sLen != 0) {
            this.random.nextBytes(this.salt);
            byte[] bArr2 = this.salt;
            byte[] bArr3 = this.mDash;
            int length = bArr3.length;
            int i2 = this.sLen;
            System.arraycopy(bArr2, 0, bArr3, length - i2, i2);
        }
        int i3 = this.hLen;
        byte[] bArr4 = new byte[i3];
        Digest digest2 = this.contentDigest;
        byte[] bArr5 = this.mDash;
        digest2.update(bArr5, 0, bArr5.length);
        this.contentDigest.doFinal(bArr4, 0);
        byte[] bArr6 = this.block;
        int length2 = bArr6.length;
        int i4 = this.sLen;
        int i5 = this.hLen;
        bArr6[(((length2 - i4) - 1) - i5) - 1] = 1;
        System.arraycopy(this.salt, 0, bArr6, ((bArr6.length - i4) - i5) - 1, i4);
        byte[] bArrMaskGeneratorFunction1 = maskGeneratorFunction1(bArr4, 0, i3, (this.block.length - this.hLen) - 1);
        for (int i6 = 0; i6 != bArrMaskGeneratorFunction1.length; i6++) {
            byte[] bArr7 = this.block;
            bArr7[i6] = (byte) (bArr7[i6] ^ bArrMaskGeneratorFunction1[i6]);
        }
        byte[] bArr8 = this.block;
        bArr8[0] = (byte) (bArr8[0] & (255 >> ((bArr8.length * 8) - this.emBits)));
        int length3 = bArr8.length;
        int i7 = this.hLen;
        System.arraycopy(bArr4, 0, bArr8, (length3 - i7) - 1, i7);
        byte[] bArr9 = this.block;
        bArr9[bArr9.length - 1] = this.trailer;
        byte[] bArrProcessBlock = this.cipher.processBlock(bArr9, 0, bArr9.length);
        clearBlock(this.block);
        return bArrProcessBlock;
    }

    @Override // org.bouncycastle.crypto.Signer
    public void init(boolean z2, CipherParameters cipherParameters) {
        if (cipherParameters instanceof ParametersWithRandom) {
            ParametersWithRandom parametersWithRandom = (ParametersWithRandom) cipherParameters;
            CipherParameters parameters = parametersWithRandom.getParameters();
            this.random = parametersWithRandom.getRandom();
            cipherParameters = parameters;
        } else if (z2) {
            this.random = new SecureRandom();
        }
        this.cipher.init(z2, cipherParameters);
        int iBitLength = (cipherParameters instanceof RSABlindingParameters ? ((RSABlindingParameters) cipherParameters).getPublicKey() : (RSAKeyParameters) cipherParameters).getModulus().bitLength() - 1;
        this.emBits = iBitLength;
        if (iBitLength < (this.hLen * 8) + (this.sLen * 8) + 9) {
            throw new IllegalArgumentException("key too small for specified hash and salt lengths");
        }
        this.block = new byte[(iBitLength + 7) / 8];
        reset();
    }

    @Override // org.bouncycastle.crypto.Signer
    public void reset() {
        this.contentDigest.reset();
    }

    @Override // org.bouncycastle.crypto.Signer
    public void update(byte b3) {
        this.contentDigest.update(b3);
    }

    @Override // org.bouncycastle.crypto.Signer
    public void update(byte[] bArr, int i2, int i3) {
        this.contentDigest.update(bArr, i2, i3);
    }

    @Override // org.bouncycastle.crypto.Signer
    public boolean verifySignature(byte[] bArr) {
        Digest digest = this.contentDigest;
        byte[] bArr2 = this.mDash;
        digest.doFinal(bArr2, (bArr2.length - this.hLen) - this.sLen);
        try {
            byte[] bArrProcessBlock = this.cipher.processBlock(bArr, 0, bArr.length);
            byte[] bArr3 = this.block;
            System.arraycopy(bArrProcessBlock, 0, bArr3, bArr3.length - bArrProcessBlock.length, bArrProcessBlock.length);
            byte[] bArr4 = this.block;
            if (bArr4[bArr4.length - 1] != this.trailer) {
                clearBlock(bArr4);
                return false;
            }
            int length = bArr4.length;
            int i2 = this.hLen;
            byte[] bArrMaskGeneratorFunction1 = maskGeneratorFunction1(bArr4, (length - i2) - 1, i2, (bArr4.length - i2) - 1);
            for (int i3 = 0; i3 != bArrMaskGeneratorFunction1.length; i3++) {
                byte[] bArr5 = this.block;
                bArr5[i3] = (byte) (bArr5[i3] ^ bArrMaskGeneratorFunction1[i3]);
            }
            byte[] bArr6 = this.block;
            bArr6[0] = (byte) (bArr6[0] & (255 >> ((bArr6.length * 8) - this.emBits)));
            int i4 = 0;
            while (true) {
                byte[] bArr7 = this.block;
                int length2 = bArr7.length;
                int i5 = this.hLen;
                int i6 = this.sLen;
                if (i4 != ((length2 - i5) - i6) - 2) {
                    if (bArr7[i4] != 0) {
                        clearBlock(bArr7);
                        return false;
                    }
                    i4++;
                } else {
                    if (bArr7[((bArr7.length - i5) - i6) - 2] != 1) {
                        clearBlock(bArr7);
                        return false;
                    }
                    int length3 = ((bArr7.length - i6) - i5) - 1;
                    byte[] bArr8 = this.mDash;
                    System.arraycopy(bArr7, length3, bArr8, bArr8.length - i6, i6);
                    Digest digest2 = this.contentDigest;
                    byte[] bArr9 = this.mDash;
                    digest2.update(bArr9, 0, bArr9.length);
                    Digest digest3 = this.contentDigest;
                    byte[] bArr10 = this.mDash;
                    digest3.doFinal(bArr10, bArr10.length - this.hLen);
                    int length4 = this.block.length;
                    int i7 = this.hLen;
                    int i8 = (length4 - i7) - 1;
                    int length5 = this.mDash.length - i7;
                    while (true) {
                        byte[] bArr11 = this.mDash;
                        if (length5 == bArr11.length) {
                            clearBlock(bArr11);
                            clearBlock(this.block);
                            return true;
                        }
                        if ((this.block[i8] ^ bArr11[length5]) != 0) {
                            clearBlock(bArr11);
                            clearBlock(this.block);
                            return false;
                        }
                        i8++;
                        length5++;
                    }
                }
            }
        } catch (Exception unused) {
            return false;
        }
    }
}
