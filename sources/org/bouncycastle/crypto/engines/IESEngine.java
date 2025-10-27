package org.bouncycastle.crypto.engines;

import java.math.BigInteger;
import org.bouncycastle.crypto.BasicAgreement;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.DerivationFunction;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.Mac;
import org.bouncycastle.crypto.params.IESParameters;
import org.bouncycastle.crypto.params.IESWithCipherParameters;
import org.bouncycastle.crypto.params.KDFParameters;
import org.bouncycastle.crypto.params.KeyParameter;

/* loaded from: classes9.dex */
public class IESEngine {
    BasicAgreement agree;
    BufferedBlockCipher cipher;
    boolean forEncryption;
    DerivationFunction kdf;
    Mac mac;
    byte[] macBuf;
    IESParameters param;
    CipherParameters privParam;
    CipherParameters pubParam;

    public IESEngine(BasicAgreement basicAgreement, DerivationFunction derivationFunction, Mac mac) {
        this.agree = basicAgreement;
        this.kdf = derivationFunction;
        this.mac = mac;
        this.macBuf = new byte[mac.getMacSize()];
        this.cipher = null;
    }

    public IESEngine(BasicAgreement basicAgreement, DerivationFunction derivationFunction, Mac mac, BufferedBlockCipher bufferedBlockCipher) {
        this.agree = basicAgreement;
        this.kdf = derivationFunction;
        this.mac = mac;
        this.macBuf = new byte[mac.getMacSize()];
        this.cipher = bufferedBlockCipher;
    }

    private byte[] decryptBlock(byte[] bArr, int i2, int i3, byte[] bArr2) throws InvalidCipherTextException, IllegalStateException, DataLengthException, IllegalArgumentException {
        KeyParameter keyParameter;
        byte[] bArr3;
        KDFParameters kDFParameters = new KDFParameters(bArr2, this.param.getDerivationV());
        int macKeySize = this.param.getMacKeySize();
        this.kdf.init(kDFParameters);
        int macSize = i3 - this.mac.getMacSize();
        int i4 = 0;
        if (this.cipher == null) {
            int i5 = macKeySize / 8;
            byte[] bArrGenerateKdfBytes = generateKdfBytes(kDFParameters, macSize + i5);
            bArr3 = new byte[macSize];
            for (int i6 = 0; i6 != macSize; i6++) {
                bArr3[i6] = (byte) (bArr[i2 + i6] ^ bArrGenerateKdfBytes[i6]);
            }
            keyParameter = new KeyParameter(bArrGenerateKdfBytes, macSize, i5);
        } else {
            int cipherKeySize = ((IESWithCipherParameters) this.param).getCipherKeySize() / 8;
            int i7 = macKeySize / 8;
            byte[] bArrGenerateKdfBytes2 = generateKdfBytes(kDFParameters, cipherKeySize + i7);
            this.cipher.init(false, new KeyParameter(bArrGenerateKdfBytes2, 0, cipherKeySize));
            byte[] bArr4 = new byte[this.cipher.getOutputSize(macSize)];
            int iProcessBytes = this.cipher.processBytes(bArr, i2, macSize, bArr4, 0);
            int iDoFinal = iProcessBytes + this.cipher.doFinal(bArr4, iProcessBytes);
            byte[] bArr5 = new byte[iDoFinal];
            System.arraycopy(bArr4, 0, bArr5, 0, iDoFinal);
            keyParameter = new KeyParameter(bArrGenerateKdfBytes2, cipherKeySize, i7);
            bArr3 = bArr5;
        }
        byte[] encodingV = this.param.getEncodingV();
        this.mac.init(keyParameter);
        this.mac.update(bArr, i2, macSize);
        this.mac.update(encodingV, 0, encodingV.length);
        this.mac.doFinal(this.macBuf, 0);
        int i8 = i2 + macSize;
        while (true) {
            byte[] bArr6 = this.macBuf;
            if (i4 >= bArr6.length) {
                return bArr3;
            }
            if (bArr6[i4] != bArr[i8 + i4]) {
                throw new InvalidCipherTextException("Mac codes failed to equal.");
            }
            i4++;
        }
    }

    private byte[] encryptBlock(byte[] bArr, int i2, int i3, byte[] bArr2) throws InvalidCipherTextException, IllegalStateException, DataLengthException, IllegalArgumentException {
        byte[] bArr3;
        KeyParameter keyParameter;
        KDFParameters kDFParameters = new KDFParameters(bArr2, this.param.getDerivationV());
        int macKeySize = this.param.getMacKeySize();
        if (this.cipher == null) {
            int i4 = macKeySize / 8;
            byte[] bArrGenerateKdfBytes = generateKdfBytes(kDFParameters, i3 + i4);
            bArr3 = new byte[this.mac.getMacSize() + i3];
            for (int i5 = 0; i5 != i3; i5++) {
                bArr3[i5] = (byte) (bArr[i2 + i5] ^ bArrGenerateKdfBytes[i5]);
            }
            keyParameter = new KeyParameter(bArrGenerateKdfBytes, i3, i4);
        } else {
            int cipherKeySize = ((IESWithCipherParameters) this.param).getCipherKeySize() / 8;
            int i6 = macKeySize / 8;
            byte[] bArrGenerateKdfBytes2 = generateKdfBytes(kDFParameters, cipherKeySize + i6);
            this.cipher.init(true, new KeyParameter(bArrGenerateKdfBytes2, 0, cipherKeySize));
            byte[] bArr4 = new byte[this.cipher.getOutputSize(i3)];
            int iProcessBytes = this.cipher.processBytes(bArr, i2, i3, bArr4, 0);
            i3 = iProcessBytes + this.cipher.doFinal(bArr4, iProcessBytes);
            byte[] bArr5 = new byte[this.mac.getMacSize() + i3];
            System.arraycopy(bArr4, 0, bArr5, 0, i3);
            KeyParameter keyParameter2 = new KeyParameter(bArrGenerateKdfBytes2, cipherKeySize, i6);
            bArr3 = bArr5;
            keyParameter = keyParameter2;
        }
        byte[] encodingV = this.param.getEncodingV();
        this.mac.init(keyParameter);
        this.mac.update(bArr3, 0, i3);
        this.mac.update(encodingV, 0, encodingV.length);
        this.mac.doFinal(bArr3, i3);
        return bArr3;
    }

    private byte[] generateKdfBytes(KDFParameters kDFParameters, int i2) throws DataLengthException, IllegalArgumentException {
        byte[] bArr = new byte[i2];
        this.kdf.init(kDFParameters);
        this.kdf.generateBytes(bArr, 0, i2);
        return bArr;
    }

    public void init(boolean z2, CipherParameters cipherParameters, CipherParameters cipherParameters2, CipherParameters cipherParameters3) {
        this.forEncryption = z2;
        this.privParam = cipherParameters;
        this.pubParam = cipherParameters2;
        this.param = (IESParameters) cipherParameters3;
    }

    public byte[] processBlock(byte[] bArr, int i2, int i3) throws InvalidCipherTextException {
        this.agree.init(this.privParam);
        BigInteger bigIntegerCalculateAgreement = this.agree.calculateAgreement(this.pubParam);
        boolean z2 = this.forEncryption;
        byte[] byteArray = bigIntegerCalculateAgreement.toByteArray();
        return z2 ? encryptBlock(bArr, i2, i3, byteArray) : decryptBlock(bArr, i2, i3, byteArray);
    }
}
