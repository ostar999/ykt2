package org.bouncycastle.crypto.agreement.srp;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.util.BigIntegers;
import org.eclipse.jetty.http.HttpTokens;

/* loaded from: classes9.dex */
public class SRP6Util {
    private static BigInteger ZERO = BigInteger.valueOf(0);
    private static BigInteger ONE = BigInteger.valueOf(1);

    public static BigInteger calculateK(Digest digest, BigInteger bigInteger, BigInteger bigInteger2) {
        return hashPaddedPair(digest, bigInteger, bigInteger, bigInteger2);
    }

    public static BigInteger calculateU(Digest digest, BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
        return hashPaddedPair(digest, bigInteger, bigInteger2, bigInteger3);
    }

    public static BigInteger calculateX(Digest digest, BigInteger bigInteger, byte[] bArr, byte[] bArr2, byte[] bArr3) {
        int digestSize = digest.getDigestSize();
        byte[] bArr4 = new byte[digestSize];
        digest.update(bArr2, 0, bArr2.length);
        digest.update(HttpTokens.COLON);
        digest.update(bArr3, 0, bArr3.length);
        digest.doFinal(bArr4, 0);
        digest.update(bArr, 0, bArr.length);
        digest.update(bArr4, 0, digestSize);
        digest.doFinal(bArr4, 0);
        return new BigInteger(1, bArr4).mod(bigInteger);
    }

    public static BigInteger generatePrivateValue(Digest digest, BigInteger bigInteger, BigInteger bigInteger2, SecureRandom secureRandom) {
        return BigIntegers.createRandomInRange(ONE.shiftLeft(Math.min(256, bigInteger.bitLength() / 2) - 1), bigInteger.subtract(ONE), secureRandom);
    }

    private static byte[] getPadded(BigInteger bigInteger, int i2) {
        byte[] bArrAsUnsignedByteArray = BigIntegers.asUnsignedByteArray(bigInteger);
        if (bArrAsUnsignedByteArray.length >= i2) {
            return bArrAsUnsignedByteArray;
        }
        byte[] bArr = new byte[i2];
        System.arraycopy(bArrAsUnsignedByteArray, 0, bArr, i2 - bArrAsUnsignedByteArray.length, bArrAsUnsignedByteArray.length);
        return bArr;
    }

    private static BigInteger hashPaddedPair(Digest digest, BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
        int iBitLength = (bigInteger.bitLength() + 7) / 8;
        byte[] padded = getPadded(bigInteger2, iBitLength);
        byte[] padded2 = getPadded(bigInteger3, iBitLength);
        digest.update(padded, 0, padded.length);
        digest.update(padded2, 0, padded2.length);
        byte[] bArr = new byte[digest.getDigestSize()];
        digest.doFinal(bArr, 0);
        return new BigInteger(1, bArr).mod(bigInteger);
    }

    public static BigInteger validatePublicValue(BigInteger bigInteger, BigInteger bigInteger2) throws CryptoException {
        BigInteger bigIntegerMod = bigInteger2.mod(bigInteger);
        if (bigIntegerMod.equals(ZERO)) {
            throw new CryptoException("Invalid public value: 0");
        }
        return bigIntegerMod;
    }
}
