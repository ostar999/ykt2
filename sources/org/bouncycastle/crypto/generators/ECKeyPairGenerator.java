package org.bouncycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.bouncycastle.crypto.KeyGenerationParameters;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECKeyGenerationParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.math.ec.ECConstants;

/* loaded from: classes9.dex */
public class ECKeyPairGenerator implements AsymmetricCipherKeyPairGenerator, ECConstants {
    ECDomainParameters params;
    SecureRandom random;

    @Override // org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator
    public AsymmetricCipherKeyPair generateKeyPair() {
        BigInteger n2 = this.params.getN();
        int iBitLength = n2.bitLength();
        while (true) {
            BigInteger bigInteger = new BigInteger(iBitLength, this.random);
            if (!bigInteger.equals(ECConstants.ZERO) && bigInteger.compareTo(n2) < 0) {
                return new AsymmetricCipherKeyPair(new ECPublicKeyParameters(this.params.getG().multiply(bigInteger), this.params), new ECPrivateKeyParameters(bigInteger, this.params));
            }
        }
    }

    @Override // org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator
    public void init(KeyGenerationParameters keyGenerationParameters) {
        ECKeyGenerationParameters eCKeyGenerationParameters = (ECKeyGenerationParameters) keyGenerationParameters;
        this.random = eCKeyGenerationParameters.getRandom();
        this.params = eCKeyGenerationParameters.getDomainParameters();
    }
}
