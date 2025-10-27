package org.bouncycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.params.DSAParameters;
import org.bouncycastle.crypto.params.DSAValidationParameters;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.BigIntegers;

/* loaded from: classes9.dex */
public class DSAParametersGenerator {
    private int L;
    private int N;
    private int certainty;
    private SecureRandom random;
    private static final BigInteger ZERO = BigInteger.valueOf(0);
    private static final BigInteger ONE = BigInteger.valueOf(1);
    private static final BigInteger TWO = BigInteger.valueOf(2);

    private static BigInteger calculateGenerator_FIPS186_2(BigInteger bigInteger, BigInteger bigInteger2, SecureRandom secureRandom) {
        BigInteger bigIntegerModPow;
        BigInteger bigIntegerDivide = bigInteger.subtract(ONE).divide(bigInteger2);
        BigInteger bigIntegerSubtract = bigInteger.subtract(TWO);
        do {
            bigIntegerModPow = BigIntegers.createRandomInRange(TWO, bigIntegerSubtract, secureRandom).modPow(bigIntegerDivide, bigInteger);
        } while (bigIntegerModPow.bitLength() <= 1);
        return bigIntegerModPow;
    }

    private static BigInteger calculateGenerator_FIPS186_3_Unverifiable(BigInteger bigInteger, BigInteger bigInteger2, SecureRandom secureRandom) {
        return calculateGenerator_FIPS186_2(bigInteger, bigInteger2, secureRandom);
    }

    private DSAParameters generateParameters_FIPS186_2() {
        int i2 = 20;
        byte[] bArr = new byte[20];
        byte[] bArr2 = new byte[20];
        byte[] bArr3 = new byte[20];
        byte[] bArr4 = new byte[20];
        SHA1Digest sHA1Digest = new SHA1Digest();
        int i3 = this.L;
        int i4 = (i3 - 1) / 160;
        int i5 = i3 / 8;
        byte[] bArr5 = new byte[i5];
        while (true) {
            this.random.nextBytes(bArr);
            hash(sHA1Digest, bArr, bArr2);
            int i6 = 0;
            System.arraycopy(bArr, 0, bArr3, 0, i2);
            inc(bArr3);
            hash(sHA1Digest, bArr3, bArr3);
            for (int i7 = 0; i7 != i2; i7++) {
                bArr4[i7] = (byte) (bArr2[i7] ^ bArr3[i7]);
            }
            bArr4[0] = (byte) (bArr4[0] | (-128));
            bArr4[19] = (byte) (bArr4[19] | 1);
            BigInteger bigInteger = new BigInteger(1, bArr4);
            if (bigInteger.isProbablePrime(this.certainty)) {
                byte[] bArrClone = Arrays.clone(bArr);
                inc(bArrClone);
                int i8 = 0;
                while (i8 < 4096) {
                    int i9 = i6;
                    while (true) {
                        inc(bArrClone);
                        hash(sHA1Digest, bArrClone, bArr2);
                        if (i9 >= i4) {
                            break;
                        }
                        i9++;
                        System.arraycopy(bArr2, i6, bArr5, i5 - (i9 * 20), i2);
                    }
                    int i10 = i5 - (i4 * 20);
                    System.arraycopy(bArr2, 20 - i10, bArr5, i6, i10);
                    bArr5[i6] = (byte) (bArr5[i6] | (-128));
                    BigInteger bigInteger2 = new BigInteger(1, bArr5);
                    BigInteger bigIntegerSubtract = bigInteger2.subtract(bigInteger2.mod(bigInteger.shiftLeft(1)).subtract(ONE));
                    if (bigIntegerSubtract.bitLength() == this.L && bigIntegerSubtract.isProbablePrime(this.certainty)) {
                        return new DSAParameters(bigIntegerSubtract, bigInteger, calculateGenerator_FIPS186_2(bigIntegerSubtract, bigInteger, this.random), new DSAValidationParameters(bArr, i8));
                    }
                    i8++;
                    i2 = 20;
                    i6 = 0;
                }
            }
        }
    }

    private DSAParameters generateParameters_FIPS186_3() {
        SHA256Digest sHA256Digest = new SHA256Digest();
        int digestSize = sHA256Digest.getDigestSize() * 8;
        byte[] bArr = new byte[this.N / 8];
        int i2 = this.L;
        int i3 = (i2 - 1) / digestSize;
        int i4 = 1;
        int i5 = (i2 - 1) % digestSize;
        byte[] bArr2 = new byte[sHA256Digest.getDigestSize()];
        while (true) {
            this.random.nextBytes(bArr);
            hash(sHA256Digest, bArr, bArr2);
            BigInteger bigInteger = new BigInteger(i4, bArr2);
            BigInteger bigInteger2 = ONE;
            BigInteger bigIntegerMod = bigInteger.mod(bigInteger2.shiftLeft(this.N - i4));
            BigInteger bigIntegerSubtract = bigInteger2.shiftLeft(this.N - i4).add(bigIntegerMod).add(bigInteger2).subtract(bigIntegerMod.mod(TWO));
            if (bigIntegerSubtract.isProbablePrime(this.certainty)) {
                byte[] bArrClone = Arrays.clone(bArr);
                int i6 = this.L * 4;
                int i7 = 0;
                while (i7 < i6) {
                    BigInteger bigIntegerAdd = ZERO;
                    int i8 = 0;
                    int i9 = 0;
                    while (i8 <= i3) {
                        inc(bArrClone);
                        hash(sHA256Digest, bArrClone, bArr2);
                        BigInteger bigInteger3 = new BigInteger(i4, bArr2);
                        if (i8 == i3) {
                            bigInteger3 = bigInteger3.mod(ONE.shiftLeft(i5));
                        }
                        bigIntegerAdd = bigIntegerAdd.add(bigInteger3.shiftLeft(i9));
                        i8++;
                        i9 += digestSize;
                        i4 = 1;
                    }
                    BigInteger bigInteger4 = ONE;
                    BigInteger bigIntegerAdd2 = bigIntegerAdd.add(bigInteger4.shiftLeft(this.L - 1));
                    BigInteger bigIntegerSubtract2 = bigIntegerAdd2.subtract(bigIntegerAdd2.mod(bigIntegerSubtract.shiftLeft(1)).subtract(bigInteger4));
                    if (bigIntegerSubtract2.bitLength() == this.L && bigIntegerSubtract2.isProbablePrime(this.certainty)) {
                        return new DSAParameters(bigIntegerSubtract2, bigIntegerSubtract, calculateGenerator_FIPS186_3_Unverifiable(bigIntegerSubtract2, bigIntegerSubtract, this.random), new DSAValidationParameters(bArr, i7));
                    }
                    i7++;
                    i4 = 1;
                }
            }
        }
    }

    private static int getDefaultN(int i2) {
        return i2 > 1024 ? 256 : 160;
    }

    private static void hash(Digest digest, byte[] bArr, byte[] bArr2) {
        digest.update(bArr, 0, bArr.length);
        digest.doFinal(bArr2, 0);
    }

    private static void inc(byte[] bArr) {
        for (int length = bArr.length - 1; length >= 0; length--) {
            byte b3 = (byte) ((bArr[length] + 1) & 255);
            bArr[length] = b3;
            if (b3 != 0) {
                return;
            }
        }
    }

    private void init(int i2, int i3, int i4, SecureRandom secureRandom) {
        this.L = i2;
        this.N = i3;
        this.certainty = i4;
        this.random = secureRandom;
    }

    public DSAParameters generateParameters() {
        return this.L > 1024 ? generateParameters_FIPS186_3() : generateParameters_FIPS186_2();
    }

    public void init(int i2, int i3, SecureRandom secureRandom) {
        init(i2, getDefaultN(i2), i3, secureRandom);
    }
}
