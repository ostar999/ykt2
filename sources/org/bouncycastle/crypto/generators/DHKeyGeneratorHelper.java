package org.bouncycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.crypto.params.DHParameters;
import org.bouncycastle.util.BigIntegers;

/* loaded from: classes9.dex */
class DHKeyGeneratorHelper {
    static final DHKeyGeneratorHelper INSTANCE = new DHKeyGeneratorHelper();
    private static final BigInteger ONE = BigInteger.valueOf(1);
    private static final BigInteger TWO = BigInteger.valueOf(2);

    private DHKeyGeneratorHelper() {
    }

    public BigInteger calculatePrivate(DHParameters dHParameters, SecureRandom secureRandom) {
        BigInteger p2 = dHParameters.getP();
        int l2 = dHParameters.getL();
        if (l2 != 0) {
            return new BigInteger(l2, secureRandom).setBit(l2 - 1);
        }
        BigInteger bigInteger = TWO;
        int m2 = dHParameters.getM();
        BigInteger bigIntegerShiftLeft = m2 != 0 ? ONE.shiftLeft(m2 - 1) : bigInteger;
        BigInteger bigIntegerSubtract = p2.subtract(bigInteger);
        BigInteger q2 = dHParameters.getQ();
        if (q2 != null) {
            bigIntegerSubtract = q2.subtract(bigInteger);
        }
        return BigIntegers.createRandomInRange(bigIntegerShiftLeft, bigIntegerSubtract, secureRandom);
    }

    public BigInteger calculatePublic(DHParameters dHParameters, BigInteger bigInteger) {
        return dHParameters.getG().modPow(bigInteger, dHParameters.getP());
    }
}
