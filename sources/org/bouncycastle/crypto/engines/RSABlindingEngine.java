package org.bouncycastle.crypto.engines;

import java.math.BigInteger;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.crypto.params.RSABlindingParameters;
import org.bouncycastle.crypto.params.RSAKeyParameters;

/* loaded from: classes9.dex */
public class RSABlindingEngine implements AsymmetricBlockCipher {
    private BigInteger blindingFactor;

    /* renamed from: core, reason: collision with root package name */
    private RSACoreEngine f27838core = new RSACoreEngine();
    private boolean forEncryption;
    private RSAKeyParameters key;

    private BigInteger blindMessage(BigInteger bigInteger) {
        return bigInteger.multiply(this.blindingFactor.modPow(this.key.getExponent(), this.key.getModulus())).mod(this.key.getModulus());
    }

    private BigInteger unblindMessage(BigInteger bigInteger) {
        BigInteger modulus = this.key.getModulus();
        return bigInteger.multiply(this.blindingFactor.modInverse(modulus)).mod(modulus);
    }

    @Override // org.bouncycastle.crypto.AsymmetricBlockCipher
    public int getInputBlockSize() {
        return this.f27838core.getInputBlockSize();
    }

    @Override // org.bouncycastle.crypto.AsymmetricBlockCipher
    public int getOutputBlockSize() {
        return this.f27838core.getOutputBlockSize();
    }

    @Override // org.bouncycastle.crypto.AsymmetricBlockCipher
    public void init(boolean z2, CipherParameters cipherParameters) {
        if (cipherParameters instanceof ParametersWithRandom) {
            cipherParameters = ((ParametersWithRandom) cipherParameters).getParameters();
        }
        RSABlindingParameters rSABlindingParameters = (RSABlindingParameters) cipherParameters;
        this.f27838core.init(z2, rSABlindingParameters.getPublicKey());
        this.forEncryption = z2;
        this.key = rSABlindingParameters.getPublicKey();
        this.blindingFactor = rSABlindingParameters.getBlindingFactor();
    }

    @Override // org.bouncycastle.crypto.AsymmetricBlockCipher
    public byte[] processBlock(byte[] bArr, int i2, int i3) {
        BigInteger bigIntegerConvertInput = this.f27838core.convertInput(bArr, i2, i3);
        return this.f27838core.convertOutput(this.forEncryption ? blindMessage(bigIntegerConvertInput) : unblindMessage(bigIntegerConvertInput));
    }
}
