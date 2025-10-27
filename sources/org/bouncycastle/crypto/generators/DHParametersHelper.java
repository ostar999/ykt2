package org.bouncycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.util.BigIntegers;

/* loaded from: classes9.dex */
class DHParametersHelper {
    private static final BigInteger ONE = BigInteger.valueOf(1);
    private static final BigInteger TWO = BigInteger.valueOf(2);

    public static BigInteger[] generateSafePrimes(int i2, int i3, SecureRandom secureRandom) {
        BigInteger bigInteger;
        BigInteger bigIntegerAdd;
        int i4 = i2 - 1;
        while (true) {
            bigInteger = new BigInteger(i4, 2, secureRandom);
            bigIntegerAdd = bigInteger.shiftLeft(1).add(ONE);
            if (bigIntegerAdd.isProbablePrime(i3) && (i3 <= 2 || bigInteger.isProbablePrime(i3))) {
                break;
            }
        }
        return new BigInteger[]{bigIntegerAdd, bigInteger};
    }

    public static BigInteger selectGenerator(BigInteger bigInteger, BigInteger bigInteger2, SecureRandom secureRandom) {
        BigInteger bigIntegerModPow;
        BigInteger bigIntegerSubtract = bigInteger.subtract(TWO);
        do {
            BigInteger bigInteger3 = TWO;
            bigIntegerModPow = BigIntegers.createRandomInRange(bigInteger3, bigIntegerSubtract, secureRandom).modPow(bigInteger3, bigInteger);
        } while (bigIntegerModPow.equals(ONE));
        return bigIntegerModPow;
    }
}
