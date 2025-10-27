package org.bouncycastle.crypto.engines;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;
import org.bouncycastle.util.BigIntegers;

/* loaded from: classes9.dex */
public class RSABlindedEngine implements AsymmetricBlockCipher {
    private static BigInteger ONE = BigInteger.valueOf(1);

    /* renamed from: core, reason: collision with root package name */
    private RSACoreEngine f27837core = new RSACoreEngine();
    private RSAKeyParameters key;
    private SecureRandom random;

    @Override // org.bouncycastle.crypto.AsymmetricBlockCipher
    public int getInputBlockSize() {
        return this.f27837core.getInputBlockSize();
    }

    @Override // org.bouncycastle.crypto.AsymmetricBlockCipher
    public int getOutputBlockSize() {
        return this.f27837core.getOutputBlockSize();
    }

    @Override // org.bouncycastle.crypto.AsymmetricBlockCipher
    public void init(boolean z2, CipherParameters cipherParameters) {
        SecureRandom secureRandom;
        this.f27837core.init(z2, cipherParameters);
        if (cipherParameters instanceof ParametersWithRandom) {
            ParametersWithRandom parametersWithRandom = (ParametersWithRandom) cipherParameters;
            this.key = (RSAKeyParameters) parametersWithRandom.getParameters();
            secureRandom = parametersWithRandom.getRandom();
        } else {
            this.key = (RSAKeyParameters) cipherParameters;
            secureRandom = new SecureRandom();
        }
        this.random = secureRandom;
    }

    @Override // org.bouncycastle.crypto.AsymmetricBlockCipher
    public byte[] processBlock(byte[] bArr, int i2, int i3) {
        BigInteger bigIntegerProcessBlock;
        RSAPrivateCrtKeyParameters rSAPrivateCrtKeyParameters;
        BigInteger publicExponent;
        if (this.key == null) {
            throw new IllegalStateException("RSA engine not initialised");
        }
        BigInteger bigIntegerConvertInput = this.f27837core.convertInput(bArr, i2, i3);
        RSAKeyParameters rSAKeyParameters = this.key;
        if (!(rSAKeyParameters instanceof RSAPrivateCrtKeyParameters) || (publicExponent = (rSAPrivateCrtKeyParameters = (RSAPrivateCrtKeyParameters) rSAKeyParameters).getPublicExponent()) == null) {
            bigIntegerProcessBlock = this.f27837core.processBlock(bigIntegerConvertInput);
        } else {
            BigInteger modulus = rSAPrivateCrtKeyParameters.getModulus();
            BigInteger bigInteger = ONE;
            BigInteger bigIntegerCreateRandomInRange = BigIntegers.createRandomInRange(bigInteger, modulus.subtract(bigInteger), this.random);
            bigIntegerProcessBlock = this.f27837core.processBlock(bigIntegerCreateRandomInRange.modPow(publicExponent, modulus).multiply(bigIntegerConvertInput).mod(modulus)).multiply(bigIntegerCreateRandomInRange.modInverse(modulus)).mod(modulus);
        }
        return this.f27837core.convertOutput(bigIntegerProcessBlock);
    }
}
