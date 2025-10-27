package org.bouncycastle.crypto.generators;

import com.easefun.polyv.mediasdk.player.IjkMediaMeta;
import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.crypto.params.GOST3410Parameters;
import org.bouncycastle.crypto.params.GOST3410ValidationParameters;

/* loaded from: classes9.dex */
public class GOST3410ParametersGenerator {
    private static final BigInteger ONE = BigInteger.valueOf(1);
    private static final BigInteger TWO = BigInteger.valueOf(2);
    private SecureRandom init_random;
    private int size;
    private int typeproc;

    private int procedure_A(int i2, int i3, BigInteger[] bigIntegerArr, int i4) {
        BigInteger bigInteger;
        BigInteger[] bigIntegerArr2;
        BigInteger bigInteger2;
        BigInteger bigInteger3;
        int i5;
        int i6;
        int iNextInt = i2;
        while (true) {
            if (iNextInt >= 0 && iNextInt <= 65536) {
                break;
            }
            iNextInt = this.init_random.nextInt() / 32768;
        }
        int iNextInt2 = i3;
        while (true) {
            if (iNextInt2 >= 0 && iNextInt2 <= 65536 && iNextInt2 / 2 != 0) {
                break;
            }
            iNextInt2 = (this.init_random.nextInt() / 32768) + 1;
        }
        BigInteger bigInteger4 = new BigInteger(Integer.toString(iNextInt2));
        BigInteger bigInteger5 = new BigInteger("19381");
        BigInteger bigInteger6 = new BigInteger(Integer.toString(iNextInt));
        int i7 = 0;
        BigInteger[] bigIntegerArr3 = {bigInteger6};
        int[] iArr = {i4};
        int i8 = 0;
        int i9 = 0;
        while (iArr[i8] >= 17) {
            int length = iArr.length + 1;
            int[] iArr2 = new int[length];
            System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
            iArr = new int[length];
            System.arraycopy(iArr2, 0, iArr, 0, length);
            i9 = i8 + 1;
            iArr[i9] = iArr[i8] / 2;
            i8 = i9;
        }
        BigInteger[] bigIntegerArr4 = new BigInteger[i9 + 1];
        int i10 = 16;
        bigIntegerArr4[i9] = new BigInteger("8003", 16);
        int i11 = i9 - 1;
        int i12 = 0;
        while (true) {
            if (i12 >= i9) {
                bigInteger = bigIntegerArr3[i7];
                break;
            }
            int i13 = iArr[i11] / i10;
            while (true) {
                int length2 = bigIntegerArr3.length;
                BigInteger[] bigIntegerArr5 = new BigInteger[length2];
                System.arraycopy(bigIntegerArr3, i7, bigIntegerArr5, i7, bigIntegerArr3.length);
                bigIntegerArr2 = new BigInteger[i13 + 1];
                System.arraycopy(bigIntegerArr5, i7, bigIntegerArr2, i7, length2);
                int i14 = i7;
                while (i14 < i13) {
                    int i15 = i14 + 1;
                    bigIntegerArr2[i15] = bigIntegerArr2[i14].multiply(bigInteger5).add(bigInteger4).mod(TWO.pow(i10));
                    i14 = i15;
                }
                BigInteger bigInteger7 = new BigInteger("0");
                for (int i16 = i7; i16 < i13; i16++) {
                    bigInteger7 = bigInteger7.add(bigIntegerArr2[i16].multiply(TWO.pow(i16 * 16)));
                }
                bigIntegerArr2[i7] = bigIntegerArr2[i13];
                BigInteger bigInteger8 = TWO;
                int i17 = i11 + 1;
                BigInteger bigIntegerAdd = bigInteger8.pow(iArr[i11] - 1).divide(bigIntegerArr4[i17]).add(bigInteger8.pow(iArr[i11] - 1).multiply(bigInteger7).divide(bigIntegerArr4[i17].multiply(bigInteger8.pow(i13 * 16))));
                BigInteger bigIntegerMod = bigIntegerAdd.mod(bigInteger8);
                BigInteger bigInteger9 = ONE;
                if (bigIntegerMod.compareTo(bigInteger9) == 0) {
                    bigIntegerAdd = bigIntegerAdd.add(bigInteger9);
                }
                int i18 = 0;
                while (true) {
                    bigInteger2 = bigInteger4;
                    bigInteger3 = bigInteger5;
                    long j2 = i18;
                    i5 = i9;
                    BigInteger bigIntegerMultiply = bigIntegerArr4[i17].multiply(bigIntegerAdd.add(BigInteger.valueOf(j2)));
                    BigInteger bigInteger10 = ONE;
                    BigInteger bigIntegerAdd2 = bigIntegerMultiply.add(bigInteger10);
                    bigIntegerArr4[i11] = bigIntegerAdd2;
                    BigInteger bigInteger11 = TWO;
                    i6 = i13;
                    if (bigIntegerAdd2.compareTo(bigInteger11.pow(iArr[i11])) == 1) {
                        break;
                    }
                    if (bigInteger11.modPow(bigIntegerArr4[i17].multiply(bigIntegerAdd.add(BigInteger.valueOf(j2))), bigIntegerArr4[i11]).compareTo(bigInteger10) == 0 && bigInteger11.modPow(bigIntegerAdd.add(BigInteger.valueOf(j2)), bigIntegerArr4[i11]).compareTo(bigInteger10) != 0) {
                        break;
                    }
                    i18 += 2;
                    i9 = i5;
                    bigInteger5 = bigInteger3;
                    bigInteger4 = bigInteger2;
                    i13 = i6;
                }
                i9 = i5;
                bigInteger5 = bigInteger3;
                bigIntegerArr3 = bigIntegerArr2;
                bigInteger4 = bigInteger2;
                i13 = i6;
                i7 = 0;
                i10 = 16;
            }
            i11--;
            if (i11 < 0) {
                bigIntegerArr[0] = bigIntegerArr4[0];
                bigIntegerArr[1] = bigIntegerArr4[1];
                bigInteger = bigIntegerArr2[0];
                break;
            }
            i12++;
            i9 = i5;
            bigInteger5 = bigInteger3;
            bigIntegerArr3 = bigIntegerArr2;
            bigInteger4 = bigInteger2;
            i7 = 0;
            i10 = 16;
        }
        return bigInteger.intValue();
    }

    private long procedure_Aa(long j2, long j3, BigInteger[] bigIntegerArr, int i2) {
        BigInteger bigInteger;
        BigInteger[] bigIntegerArr2;
        BigInteger bigInteger2;
        BigInteger bigInteger3;
        int i3;
        long jNextInt = j2;
        while (true) {
            if (jNextInt >= 0 && jNextInt <= IjkMediaMeta.AV_CH_WIDE_RIGHT) {
                break;
            }
            jNextInt = this.init_random.nextInt() * 2;
        }
        long jNextInt2 = j3;
        while (true) {
            if (jNextInt2 >= 0 && jNextInt2 <= IjkMediaMeta.AV_CH_WIDE_RIGHT && jNextInt2 / 2 != 0) {
                break;
            }
            jNextInt2 = (this.init_random.nextInt() * 2) + 1;
        }
        BigInteger bigInteger4 = new BigInteger(Long.toString(jNextInt2));
        BigInteger bigInteger5 = new BigInteger("97781173");
        BigInteger bigInteger6 = new BigInteger(Long.toString(jNextInt));
        int i4 = 0;
        BigInteger[] bigIntegerArr3 = {bigInteger6};
        int[] iArr = {i2};
        int i5 = 0;
        int i6 = 0;
        while (iArr[i5] >= 33) {
            int length = iArr.length + 1;
            int[] iArr2 = new int[length];
            System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
            iArr = new int[length];
            System.arraycopy(iArr2, 0, iArr, 0, length);
            i6 = i5 + 1;
            iArr[i6] = iArr[i5] / 2;
            i5 = i6;
        }
        BigInteger[] bigIntegerArr4 = new BigInteger[i6 + 1];
        bigIntegerArr4[i6] = new BigInteger("8000000B", 16);
        int i7 = i6 - 1;
        int i8 = 0;
        while (true) {
            if (i8 >= i6) {
                bigInteger = bigIntegerArr3[i4];
                break;
            }
            int i9 = 32;
            int i10 = iArr[i7] / 32;
            while (true) {
                int length2 = bigIntegerArr3.length;
                BigInteger[] bigIntegerArr5 = new BigInteger[length2];
                System.arraycopy(bigIntegerArr3, i4, bigIntegerArr5, i4, bigIntegerArr3.length);
                bigIntegerArr2 = new BigInteger[i10 + 1];
                System.arraycopy(bigIntegerArr5, i4, bigIntegerArr2, i4, length2);
                int i11 = i4;
                while (i11 < i10) {
                    int i12 = i11 + 1;
                    bigIntegerArr2[i12] = bigIntegerArr2[i11].multiply(bigInteger5).add(bigInteger4).mod(TWO.pow(i9));
                    i11 = i12;
                }
                BigInteger bigInteger7 = new BigInteger("0");
                for (int i13 = i4; i13 < i10; i13++) {
                    bigInteger7 = bigInteger7.add(bigIntegerArr2[i13].multiply(TWO.pow(i13 * 32)));
                }
                bigIntegerArr2[i4] = bigIntegerArr2[i10];
                BigInteger bigInteger8 = TWO;
                int i14 = i7 + 1;
                BigInteger bigIntegerAdd = bigInteger8.pow(iArr[i7] - 1).divide(bigIntegerArr4[i14]).add(bigInteger8.pow(iArr[i7] - 1).multiply(bigInteger7).divide(bigIntegerArr4[i14].multiply(bigInteger8.pow(i10 * 32))));
                BigInteger bigIntegerMod = bigIntegerAdd.mod(bigInteger8);
                BigInteger bigInteger9 = ONE;
                if (bigIntegerMod.compareTo(bigInteger9) == 0) {
                    bigIntegerAdd = bigIntegerAdd.add(bigInteger9);
                }
                int i15 = 0;
                while (true) {
                    long j4 = i15;
                    bigInteger2 = bigInteger4;
                    BigInteger bigIntegerMultiply = bigIntegerArr4[i14].multiply(bigIntegerAdd.add(BigInteger.valueOf(j4)));
                    BigInteger bigInteger10 = ONE;
                    BigInteger bigIntegerAdd2 = bigIntegerMultiply.add(bigInteger10);
                    bigIntegerArr4[i7] = bigIntegerAdd2;
                    bigInteger3 = bigInteger5;
                    BigInteger bigInteger11 = TWO;
                    i3 = i6;
                    if (bigIntegerAdd2.compareTo(bigInteger11.pow(iArr[i7])) == 1) {
                        break;
                    }
                    if (bigInteger11.modPow(bigIntegerArr4[i14].multiply(bigIntegerAdd.add(BigInteger.valueOf(j4))), bigIntegerArr4[i7]).compareTo(bigInteger10) == 0 && bigInteger11.modPow(bigIntegerAdd.add(BigInteger.valueOf(j4)), bigIntegerArr4[i7]).compareTo(bigInteger10) != 0) {
                        break;
                    }
                    i15 += 2;
                    bigInteger4 = bigInteger2;
                    i6 = i3;
                    bigInteger5 = bigInteger3;
                }
                bigInteger4 = bigInteger2;
                bigIntegerArr3 = bigIntegerArr2;
                bigInteger5 = bigInteger3;
                i4 = 0;
                i9 = 32;
                i6 = i3;
            }
            i7--;
            if (i7 < 0) {
                bigIntegerArr[0] = bigIntegerArr4[0];
                bigIntegerArr[1] = bigIntegerArr4[1];
                bigInteger = bigIntegerArr2[0];
                break;
            }
            i8++;
            bigInteger4 = bigInteger2;
            i6 = i3;
            bigIntegerArr3 = bigIntegerArr2;
            bigInteger5 = bigInteger3;
            i4 = 0;
        }
        return bigInteger.longValue();
    }

    private void procedure_B(int i2, int i3, BigInteger[] bigIntegerArr) {
        int iNextInt = i2;
        while (true) {
            if (iNextInt >= 0 && iNextInt <= 65536) {
                break;
            } else {
                iNextInt = this.init_random.nextInt() / 32768;
            }
        }
        int iNextInt2 = i3;
        while (true) {
            if (iNextInt2 >= 0 && iNextInt2 <= 65536 && iNextInt2 / 2 != 0) {
                break;
            } else {
                iNextInt2 = (this.init_random.nextInt() / 32768) + 1;
            }
        }
        BigInteger[] bigIntegerArr2 = new BigInteger[2];
        BigInteger bigInteger = new BigInteger(Integer.toString(iNextInt2));
        BigInteger bigInteger2 = new BigInteger("19381");
        int iProcedure_A = procedure_A(iNextInt, iNextInt2, bigIntegerArr2, 256);
        int i4 = 0;
        BigInteger bigInteger3 = bigIntegerArr2[0];
        int iProcedure_A2 = procedure_A(iProcedure_A, iNextInt2, bigIntegerArr2, 512);
        BigInteger bigInteger4 = bigIntegerArr2[0];
        BigInteger[] bigIntegerArr3 = new BigInteger[65];
        bigIntegerArr3[0] = new BigInteger(Integer.toString(iProcedure_A2));
        while (true) {
            int i5 = i4;
            while (i5 < 64) {
                int i6 = i5 + 1;
                bigIntegerArr3[i6] = bigIntegerArr3[i5].multiply(bigInteger2).add(bigInteger).mod(TWO.pow(16));
                i5 = i6;
            }
            BigInteger bigInteger5 = new BigInteger("0");
            for (int i7 = i4; i7 < 64; i7++) {
                bigInteger5 = bigInteger5.add(bigIntegerArr3[i7].multiply(TWO.pow(i7 * 16)));
            }
            bigIntegerArr3[i4] = bigIntegerArr3[64];
            BigInteger bigInteger6 = TWO;
            int i8 = 1024;
            BigInteger bigIntegerAdd = bigInteger6.pow(1023).divide(bigInteger3.multiply(bigInteger4)).add(bigInteger6.pow(1023).multiply(bigInteger5).divide(bigInteger3.multiply(bigInteger4).multiply(bigInteger6.pow(1024))));
            BigInteger bigIntegerMod = bigIntegerAdd.mod(bigInteger6);
            BigInteger bigInteger7 = ONE;
            if (bigIntegerMod.compareTo(bigInteger7) == 0) {
                bigIntegerAdd = bigIntegerAdd.add(bigInteger7);
            }
            BigInteger bigInteger8 = bigIntegerAdd;
            int i9 = i4;
            while (true) {
                long j2 = i9;
                BigInteger bigIntegerMultiply = bigInteger3.multiply(bigInteger4).multiply(bigInteger8.add(BigInteger.valueOf(j2)));
                BigInteger bigInteger9 = ONE;
                BigInteger bigIntegerAdd2 = bigIntegerMultiply.add(bigInteger9);
                BigInteger bigInteger10 = TWO;
                if (bigIntegerAdd2.compareTo(bigInteger10.pow(i8)) == 1) {
                    break;
                }
                if (bigInteger10.modPow(bigInteger3.multiply(bigInteger4).multiply(bigInteger8.add(BigInteger.valueOf(j2))), bigIntegerAdd2).compareTo(bigInteger9) == 0 && bigInteger10.modPow(bigInteger3.multiply(bigInteger8.add(BigInteger.valueOf(j2))), bigIntegerAdd2).compareTo(bigInteger9) != 0) {
                    bigIntegerArr[0] = bigIntegerAdd2;
                    bigIntegerArr[1] = bigInteger3;
                    return;
                } else {
                    i9 += 2;
                    i8 = 1024;
                }
            }
            i4 = 0;
        }
    }

    private void procedure_Bb(long j2, long j3, BigInteger[] bigIntegerArr) {
        long jNextInt = j2;
        while (true) {
            if (jNextInt >= 0 && jNextInt <= IjkMediaMeta.AV_CH_WIDE_RIGHT) {
                break;
            } else {
                jNextInt = this.init_random.nextInt() * 2;
            }
        }
        long jNextInt2 = j3;
        while (true) {
            if (jNextInt2 >= 0 && jNextInt2 <= IjkMediaMeta.AV_CH_WIDE_RIGHT && jNextInt2 / 2 != 0) {
                break;
            } else {
                jNextInt2 = (this.init_random.nextInt() * 2) + 1;
            }
        }
        BigInteger[] bigIntegerArr2 = new BigInteger[2];
        BigInteger bigInteger = new BigInteger(Long.toString(jNextInt2));
        BigInteger bigInteger2 = new BigInteger("97781173");
        long j4 = jNextInt2;
        long jProcedure_Aa = procedure_Aa(jNextInt, j4, bigIntegerArr2, 256);
        int i2 = 0;
        BigInteger bigInteger3 = bigIntegerArr2[0];
        long jProcedure_Aa2 = procedure_Aa(jProcedure_Aa, j4, bigIntegerArr2, 512);
        BigInteger bigInteger4 = bigIntegerArr2[0];
        BigInteger[] bigIntegerArr3 = new BigInteger[33];
        bigIntegerArr3[0] = new BigInteger(Long.toString(jProcedure_Aa2));
        while (true) {
            int i3 = i2;
            while (i3 < 32) {
                int i4 = i3 + 1;
                bigIntegerArr3[i4] = bigIntegerArr3[i3].multiply(bigInteger2).add(bigInteger).mod(TWO.pow(32));
                i3 = i4;
            }
            BigInteger bigInteger5 = new BigInteger("0");
            for (int i5 = i2; i5 < 32; i5++) {
                bigInteger5 = bigInteger5.add(bigIntegerArr3[i5].multiply(TWO.pow(i5 * 32)));
            }
            bigIntegerArr3[i2] = bigIntegerArr3[32];
            BigInteger bigInteger6 = TWO;
            int i6 = 1024;
            BigInteger bigIntegerAdd = bigInteger6.pow(1023).divide(bigInteger3.multiply(bigInteger4)).add(bigInteger6.pow(1023).multiply(bigInteger5).divide(bigInteger3.multiply(bigInteger4).multiply(bigInteger6.pow(1024))));
            BigInteger bigIntegerMod = bigIntegerAdd.mod(bigInteger6);
            BigInteger bigInteger7 = ONE;
            if (bigIntegerMod.compareTo(bigInteger7) == 0) {
                bigIntegerAdd = bigIntegerAdd.add(bigInteger7);
            }
            int i7 = i2;
            while (true) {
                long j5 = i7;
                BigInteger bigIntegerMultiply = bigInteger3.multiply(bigInteger4).multiply(bigIntegerAdd.add(BigInteger.valueOf(j5)));
                BigInteger bigInteger8 = ONE;
                BigInteger bigIntegerAdd2 = bigIntegerMultiply.add(bigInteger8);
                BigInteger bigInteger9 = TWO;
                if (bigIntegerAdd2.compareTo(bigInteger9.pow(i6)) == 1) {
                    break;
                }
                if (bigInteger9.modPow(bigInteger3.multiply(bigInteger4).multiply(bigIntegerAdd.add(BigInteger.valueOf(j5))), bigIntegerAdd2).compareTo(bigInteger8) == 0 && bigInteger9.modPow(bigInteger3.multiply(bigIntegerAdd.add(BigInteger.valueOf(j5))), bigIntegerAdd2).compareTo(bigInteger8) != 0) {
                    bigIntegerArr[0] = bigIntegerAdd2;
                    bigIntegerArr[1] = bigInteger3;
                    return;
                } else {
                    i7 += 2;
                    i6 = 1024;
                }
            }
            i2 = 0;
        }
    }

    private BigInteger procedure_C(BigInteger bigInteger, BigInteger bigInteger2) {
        BigInteger bigIntegerSubtract = bigInteger.subtract(ONE);
        BigInteger bigIntegerDivide = bigIntegerSubtract.divide(bigInteger2);
        int iBitLength = bigInteger.bitLength();
        while (true) {
            BigInteger bigInteger3 = new BigInteger(iBitLength, this.init_random);
            BigInteger bigInteger4 = ONE;
            if (bigInteger3.compareTo(bigInteger4) > 0 && bigInteger3.compareTo(bigIntegerSubtract) < 0) {
                BigInteger bigIntegerModPow = bigInteger3.modPow(bigIntegerDivide, bigInteger);
                if (bigIntegerModPow.compareTo(bigInteger4) != 0) {
                    return bigIntegerModPow;
                }
            }
        }
    }

    public GOST3410Parameters generateParameters() {
        BigInteger[] bigIntegerArr = new BigInteger[2];
        if (this.typeproc == 1) {
            int iNextInt = this.init_random.nextInt();
            int iNextInt2 = this.init_random.nextInt();
            int i2 = this.size;
            if (i2 == 512) {
                procedure_A(iNextInt, iNextInt2, bigIntegerArr, 512);
            } else {
                if (i2 != 1024) {
                    throw new IllegalArgumentException("Ooops! key size 512 or 1024 bit.");
                }
                procedure_B(iNextInt, iNextInt2, bigIntegerArr);
            }
            BigInteger bigInteger = bigIntegerArr[0];
            BigInteger bigInteger2 = bigIntegerArr[1];
            return new GOST3410Parameters(bigInteger, bigInteger2, procedure_C(bigInteger, bigInteger2), new GOST3410ValidationParameters(iNextInt, iNextInt2));
        }
        long jNextLong = this.init_random.nextLong();
        long jNextLong2 = this.init_random.nextLong();
        int i3 = this.size;
        if (i3 == 512) {
            procedure_Aa(jNextLong, jNextLong2, bigIntegerArr, 512);
        } else {
            if (i3 != 1024) {
                throw new IllegalStateException("Ooops! key size 512 or 1024 bit.");
            }
            procedure_Bb(jNextLong, jNextLong2, bigIntegerArr);
        }
        BigInteger bigInteger3 = bigIntegerArr[0];
        BigInteger bigInteger4 = bigIntegerArr[1];
        return new GOST3410Parameters(bigInteger3, bigInteger4, procedure_C(bigInteger3, bigInteger4), new GOST3410ValidationParameters(jNextLong, jNextLong2));
    }

    public void init(int i2, int i3, SecureRandom secureRandom) {
        this.size = i2;
        this.typeproc = i3;
        this.init_random = secureRandom;
    }
}
