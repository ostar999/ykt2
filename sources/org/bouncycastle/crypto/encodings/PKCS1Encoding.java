package org.bouncycastle.crypto.encodings;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.SecureRandom;
import k.a;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.ParametersWithRandom;

/* loaded from: classes9.dex */
public class PKCS1Encoding implements AsymmetricBlockCipher {
    private static final int HEADER_LENGTH = 10;
    public static final String STRICT_LENGTH_ENABLED_PROPERTY = "org.bouncycastle.pkcs1.strict";
    private AsymmetricBlockCipher engine;
    private boolean forEncryption;
    private boolean forPrivateKey;
    private SecureRandom random;
    private boolean useStrictLength = useStrict();

    public PKCS1Encoding(AsymmetricBlockCipher asymmetricBlockCipher) {
        this.engine = asymmetricBlockCipher;
    }

    private byte[] decodeBlock(byte[] bArr, int i2, int i3) throws InvalidCipherTextException {
        byte b3;
        byte[] bArrProcessBlock = this.engine.processBlock(bArr, i2, i3);
        if (bArrProcessBlock.length < getOutputBlockSize()) {
            throw new InvalidCipherTextException("block truncated");
        }
        byte b4 = bArrProcessBlock[0];
        if (b4 != 1 && b4 != 2) {
            throw new InvalidCipherTextException("unknown block type");
        }
        if (this.useStrictLength && bArrProcessBlock.length != this.engine.getOutputBlockSize()) {
            throw new InvalidCipherTextException("block incorrect size");
        }
        int i4 = 1;
        while (i4 != bArrProcessBlock.length && (b3 = bArrProcessBlock[i4]) != 0) {
            if (b4 == 1 && b3 != -1) {
                throw new InvalidCipherTextException("block padding incorrect");
            }
            i4++;
        }
        int i5 = i4 + 1;
        if (i5 > bArrProcessBlock.length || i5 < 10) {
            throw new InvalidCipherTextException("no data in block");
        }
        int length = bArrProcessBlock.length - i5;
        byte[] bArr2 = new byte[length];
        System.arraycopy(bArrProcessBlock, i5, bArr2, 0, length);
        return bArr2;
    }

    private byte[] encodeBlock(byte[] bArr, int i2, int i3) throws InvalidCipherTextException {
        if (i3 > getInputBlockSize()) {
            throw new IllegalArgumentException("input data too large");
        }
        int inputBlockSize = this.engine.getInputBlockSize();
        byte[] bArr2 = new byte[inputBlockSize];
        if (this.forPrivateKey) {
            bArr2[0] = 1;
            for (int i4 = 1; i4 != (inputBlockSize - i3) - 1; i4++) {
                bArr2[i4] = -1;
            }
        } else {
            this.random.nextBytes(bArr2);
            bArr2[0] = 2;
            for (int i5 = 1; i5 != (inputBlockSize - i3) - 1; i5++) {
                while (bArr2[i5] == 0) {
                    bArr2[i5] = (byte) this.random.nextInt();
                }
            }
        }
        int i6 = inputBlockSize - i3;
        bArr2[i6 - 1] = 0;
        System.arraycopy(bArr, i2, bArr2, i6, i3);
        return this.engine.processBlock(bArr2, 0, inputBlockSize);
    }

    private boolean useStrict() {
        String str = (String) AccessController.doPrivileged(new PrivilegedAction() { // from class: org.bouncycastle.crypto.encodings.PKCS1Encoding.1
            @Override // java.security.PrivilegedAction
            public Object run() {
                return System.getProperty(PKCS1Encoding.STRICT_LENGTH_ENABLED_PROPERTY);
            }
        });
        return str == null || str.equals(a.f27523u);
    }

    @Override // org.bouncycastle.crypto.AsymmetricBlockCipher
    public int getInputBlockSize() {
        int inputBlockSize = this.engine.getInputBlockSize();
        return this.forEncryption ? inputBlockSize - 10 : inputBlockSize;
    }

    @Override // org.bouncycastle.crypto.AsymmetricBlockCipher
    public int getOutputBlockSize() {
        int outputBlockSize = this.engine.getOutputBlockSize();
        return this.forEncryption ? outputBlockSize : outputBlockSize - 10;
    }

    public AsymmetricBlockCipher getUnderlyingCipher() {
        return this.engine;
    }

    @Override // org.bouncycastle.crypto.AsymmetricBlockCipher
    public void init(boolean z2, CipherParameters cipherParameters) {
        AsymmetricKeyParameter asymmetricKeyParameter;
        if (cipherParameters instanceof ParametersWithRandom) {
            ParametersWithRandom parametersWithRandom = (ParametersWithRandom) cipherParameters;
            this.random = parametersWithRandom.getRandom();
            asymmetricKeyParameter = (AsymmetricKeyParameter) parametersWithRandom.getParameters();
        } else {
            this.random = new SecureRandom();
            asymmetricKeyParameter = (AsymmetricKeyParameter) cipherParameters;
        }
        this.engine.init(z2, cipherParameters);
        this.forPrivateKey = asymmetricKeyParameter.isPrivate();
        this.forEncryption = z2;
    }

    @Override // org.bouncycastle.crypto.AsymmetricBlockCipher
    public byte[] processBlock(byte[] bArr, int i2, int i3) throws InvalidCipherTextException {
        return this.forEncryption ? encodeBlock(bArr, i2, i3) : decodeBlock(bArr, i2, i3);
    }
}
