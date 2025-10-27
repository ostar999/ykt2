package org.bouncycastle.crypto.encodings;

import java.security.SecureRandom;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.params.ParametersWithRandom;

/* loaded from: classes9.dex */
public class OAEPEncoding implements AsymmetricBlockCipher {
    private byte[] defHash;
    private AsymmetricBlockCipher engine;
    private boolean forEncryption;
    private Digest hash;
    private Digest mgf1Hash;
    private SecureRandom random;

    public OAEPEncoding(AsymmetricBlockCipher asymmetricBlockCipher) {
        this(asymmetricBlockCipher, new SHA1Digest(), null);
    }

    public OAEPEncoding(AsymmetricBlockCipher asymmetricBlockCipher, Digest digest) {
        this(asymmetricBlockCipher, digest, null);
    }

    public OAEPEncoding(AsymmetricBlockCipher asymmetricBlockCipher, Digest digest, Digest digest2, byte[] bArr) {
        this.engine = asymmetricBlockCipher;
        this.hash = digest;
        this.mgf1Hash = digest2;
        this.defHash = new byte[digest.getDigestSize()];
        if (bArr != null) {
            digest.update(bArr, 0, bArr.length);
        }
        digest.doFinal(this.defHash, 0);
    }

    public OAEPEncoding(AsymmetricBlockCipher asymmetricBlockCipher, Digest digest, byte[] bArr) {
        this(asymmetricBlockCipher, digest, digest, bArr);
    }

    private void ItoOSP(int i2, byte[] bArr) {
        bArr[0] = (byte) (i2 >>> 24);
        bArr[1] = (byte) (i2 >>> 16);
        bArr[2] = (byte) (i2 >>> 8);
        bArr[3] = (byte) (i2 >>> 0);
    }

    private byte[] maskGeneratorFunction1(byte[] bArr, int i2, int i3, int i4) {
        byte[] bArr2 = new byte[i4];
        int digestSize = this.mgf1Hash.getDigestSize();
        byte[] bArr3 = new byte[digestSize];
        byte[] bArr4 = new byte[4];
        this.hash.reset();
        int i5 = 0;
        do {
            ItoOSP(i5, bArr4);
            this.mgf1Hash.update(bArr, i2, i3);
            this.mgf1Hash.update(bArr4, 0, 4);
            this.mgf1Hash.doFinal(bArr3, 0);
            System.arraycopy(bArr3, 0, bArr2, i5 * digestSize, digestSize);
            i5++;
        } while (i5 < i4 / digestSize);
        int i6 = digestSize * i5;
        if (i6 < i4) {
            ItoOSP(i5, bArr4);
            this.mgf1Hash.update(bArr, i2, i3);
            this.mgf1Hash.update(bArr4, 0, 4);
            this.mgf1Hash.doFinal(bArr3, 0);
            System.arraycopy(bArr3, 0, bArr2, i6, i4 - i6);
        }
        return bArr2;
    }

    public byte[] decodeBlock(byte[] bArr, int i2, int i3) throws InvalidCipherTextException {
        byte[] bArr2;
        byte[] bArrProcessBlock = this.engine.processBlock(bArr, i2, i3);
        if (bArrProcessBlock.length < this.engine.getOutputBlockSize()) {
            int outputBlockSize = this.engine.getOutputBlockSize();
            byte[] bArr3 = new byte[outputBlockSize];
            System.arraycopy(bArrProcessBlock, 0, bArr3, outputBlockSize - bArrProcessBlock.length, bArrProcessBlock.length);
            bArrProcessBlock = bArr3;
        }
        int length = bArrProcessBlock.length;
        byte[] bArr4 = this.defHash;
        if (length < (bArr4.length * 2) + 1) {
            throw new InvalidCipherTextException("data too short");
        }
        byte[] bArrMaskGeneratorFunction1 = maskGeneratorFunction1(bArrProcessBlock, bArr4.length, bArrProcessBlock.length - bArr4.length, bArr4.length);
        int i4 = 0;
        while (true) {
            bArr2 = this.defHash;
            if (i4 == bArr2.length) {
                break;
            }
            bArrProcessBlock[i4] = (byte) (bArrProcessBlock[i4] ^ bArrMaskGeneratorFunction1[i4]);
            i4++;
        }
        byte[] bArrMaskGeneratorFunction12 = maskGeneratorFunction1(bArrProcessBlock, 0, bArr2.length, bArrProcessBlock.length - bArr2.length);
        for (int length2 = this.defHash.length; length2 != bArrProcessBlock.length; length2++) {
            bArrProcessBlock[length2] = (byte) (bArrProcessBlock[length2] ^ bArrMaskGeneratorFunction12[length2 - this.defHash.length]);
        }
        int i5 = 0;
        while (true) {
            byte[] bArr5 = this.defHash;
            if (i5 == bArr5.length) {
                int length3 = bArr5.length * 2;
                while (length3 != bArrProcessBlock.length && bArrProcessBlock[length3] == 0) {
                    length3++;
                }
                if (length3 >= bArrProcessBlock.length - 1 || bArrProcessBlock[length3] != 1) {
                    throw new InvalidCipherTextException("data start wrong " + length3);
                }
                int i6 = length3 + 1;
                int length4 = bArrProcessBlock.length - i6;
                byte[] bArr6 = new byte[length4];
                System.arraycopy(bArrProcessBlock, i6, bArr6, 0, length4);
                return bArr6;
            }
            if (bArr5[i5] != bArrProcessBlock[bArr5.length + i5]) {
                throw new InvalidCipherTextException("data hash wrong");
            }
            i5++;
        }
    }

    public byte[] encodeBlock(byte[] bArr, int i2, int i3) throws InvalidCipherTextException {
        int inputBlockSize = getInputBlockSize() + 1 + (this.defHash.length * 2);
        byte[] bArr2 = new byte[inputBlockSize];
        int i4 = inputBlockSize - i3;
        System.arraycopy(bArr, i2, bArr2, i4, i3);
        bArr2[i4 - 1] = 1;
        byte[] bArr3 = this.defHash;
        System.arraycopy(bArr3, 0, bArr2, bArr3.length, bArr3.length);
        int length = this.defHash.length;
        byte[] bArr4 = new byte[length];
        this.random.nextBytes(bArr4);
        byte[] bArrMaskGeneratorFunction1 = maskGeneratorFunction1(bArr4, 0, length, inputBlockSize - this.defHash.length);
        for (int length2 = this.defHash.length; length2 != inputBlockSize; length2++) {
            bArr2[length2] = (byte) (bArr2[length2] ^ bArrMaskGeneratorFunction1[length2 - this.defHash.length]);
        }
        System.arraycopy(bArr4, 0, bArr2, 0, this.defHash.length);
        byte[] bArr5 = this.defHash;
        byte[] bArrMaskGeneratorFunction12 = maskGeneratorFunction1(bArr2, bArr5.length, inputBlockSize - bArr5.length, bArr5.length);
        for (int i5 = 0; i5 != this.defHash.length; i5++) {
            bArr2[i5] = (byte) (bArr2[i5] ^ bArrMaskGeneratorFunction12[i5]);
        }
        return this.engine.processBlock(bArr2, 0, inputBlockSize);
    }

    @Override // org.bouncycastle.crypto.AsymmetricBlockCipher
    public int getInputBlockSize() {
        int inputBlockSize = this.engine.getInputBlockSize();
        return this.forEncryption ? (inputBlockSize - 1) - (this.defHash.length * 2) : inputBlockSize;
    }

    @Override // org.bouncycastle.crypto.AsymmetricBlockCipher
    public int getOutputBlockSize() {
        int outputBlockSize = this.engine.getOutputBlockSize();
        return this.forEncryption ? outputBlockSize : (outputBlockSize - 1) - (this.defHash.length * 2);
    }

    public AsymmetricBlockCipher getUnderlyingCipher() {
        return this.engine;
    }

    @Override // org.bouncycastle.crypto.AsymmetricBlockCipher
    public void init(boolean z2, CipherParameters cipherParameters) {
        this.random = cipherParameters instanceof ParametersWithRandom ? ((ParametersWithRandom) cipherParameters).getRandom() : new SecureRandom();
        this.engine.init(z2, cipherParameters);
        this.forEncryption = z2;
    }

    @Override // org.bouncycastle.crypto.AsymmetricBlockCipher
    public byte[] processBlock(byte[] bArr, int i2, int i3) throws InvalidCipherTextException {
        return this.forEncryption ? encodeBlock(bArr, i2, i3) : decodeBlock(bArr, i2, i3);
    }
}
