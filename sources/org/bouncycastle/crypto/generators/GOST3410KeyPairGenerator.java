package org.bouncycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.bouncycastle.crypto.KeyGenerationParameters;
import org.bouncycastle.crypto.params.GOST3410KeyGenerationParameters;
import org.bouncycastle.crypto.params.GOST3410Parameters;
import org.bouncycastle.crypto.params.GOST3410PrivateKeyParameters;
import org.bouncycastle.crypto.params.GOST3410PublicKeyParameters;

/* loaded from: classes9.dex */
public class GOST3410KeyPairGenerator implements AsymmetricCipherKeyPairGenerator {
    private static final BigInteger ZERO = BigInteger.valueOf(0);
    private GOST3410KeyGenerationParameters param;

    @Override // org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator
    public AsymmetricCipherKeyPair generateKeyPair() {
        GOST3410Parameters parameters = this.param.getParameters();
        SecureRandom random = this.param.getRandom();
        BigInteger q2 = parameters.getQ();
        BigInteger p2 = parameters.getP();
        BigInteger a3 = parameters.getA();
        while (true) {
            BigInteger bigInteger = new BigInteger(256, random);
            if (!bigInteger.equals(ZERO) && bigInteger.compareTo(q2) < 0) {
                return new AsymmetricCipherKeyPair(new GOST3410PublicKeyParameters(a3.modPow(bigInteger, p2), parameters), new GOST3410PrivateKeyParameters(bigInteger, parameters));
            }
        }
    }

    @Override // org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator
    public void init(KeyGenerationParameters keyGenerationParameters) {
        this.param = (GOST3410KeyGenerationParameters) keyGenerationParameters;
    }
}
