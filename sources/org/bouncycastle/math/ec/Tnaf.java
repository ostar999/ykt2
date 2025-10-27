package org.bouncycastle.math.ec;

import java.math.BigInteger;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECPoint;

/* loaded from: classes9.dex */
class Tnaf {
    private static final BigInteger MINUS_ONE;
    private static final BigInteger MINUS_THREE;
    private static final BigInteger MINUS_TWO;
    public static final byte POW_2_WIDTH = 16;
    public static final byte WIDTH = 4;
    public static final ZTauElement[] alpha0;
    public static final byte[][] alpha0Tnaf;
    public static final ZTauElement[] alpha1;
    public static final byte[][] alpha1Tnaf;

    static {
        BigInteger bigInteger = ECConstants.ONE;
        BigInteger bigIntegerNegate = bigInteger.negate();
        MINUS_ONE = bigIntegerNegate;
        MINUS_TWO = ECConstants.TWO.negate();
        BigInteger bigIntegerNegate2 = ECConstants.THREE.negate();
        MINUS_THREE = bigIntegerNegate2;
        BigInteger bigInteger2 = ECConstants.ZERO;
        alpha0 = new ZTauElement[]{null, new ZTauElement(bigInteger, bigInteger2), null, new ZTauElement(bigIntegerNegate2, bigIntegerNegate), null, new ZTauElement(bigIntegerNegate, bigIntegerNegate), null, new ZTauElement(bigInteger, bigIntegerNegate), null};
        alpha0Tnaf = new byte[][]{null, new byte[]{1}, null, new byte[]{-1, 0, 1}, null, new byte[]{1, 0, 1}, null, new byte[]{-1, 0, 0, 1}};
        alpha1 = new ZTauElement[]{null, new ZTauElement(bigInteger, bigInteger2), null, new ZTauElement(bigIntegerNegate2, bigInteger), null, new ZTauElement(bigIntegerNegate, bigInteger), null, new ZTauElement(bigInteger, bigInteger), null};
        alpha1Tnaf = new byte[][]{null, new byte[]{1}, null, new byte[]{-1, 0, 1}, null, new byte[]{1, 0, 1}, null, new byte[]{-1, 0, 0, -1}};
    }

    public static SimpleBigDecimal approximateDivisionByN(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, byte b3, int i2, int i3) {
        BigInteger bigIntegerMultiply = bigInteger2.multiply(bigInteger.shiftRight(((i2 - r0) - 2) + b3));
        BigInteger bigIntegerAdd = bigIntegerMultiply.add(bigInteger3.multiply(bigIntegerMultiply.shiftRight(i2)));
        int i4 = (((i2 + 5) / 2) + i3) - i3;
        BigInteger bigIntegerShiftRight = bigIntegerAdd.shiftRight(i4);
        if (bigIntegerAdd.testBit(i4 - 1)) {
            bigIntegerShiftRight = bigIntegerShiftRight.add(ECConstants.ONE);
        }
        return new SimpleBigDecimal(bigIntegerShiftRight, i3);
    }

    public static BigInteger[] getLucas(byte b3, int i2, boolean z2) {
        BigInteger bigInteger;
        BigInteger bigIntegerSubtract;
        if (b3 != 1 && b3 != -1) {
            throw new IllegalArgumentException("mu must be 1 or -1");
        }
        if (z2) {
            bigInteger = ECConstants.TWO;
            bigIntegerSubtract = BigInteger.valueOf(b3);
        } else {
            bigInteger = ECConstants.ZERO;
            bigIntegerSubtract = ECConstants.ONE;
        }
        int i3 = 1;
        while (i3 < i2) {
            i3++;
            BigInteger bigInteger2 = bigIntegerSubtract;
            bigIntegerSubtract = (b3 == 1 ? bigIntegerSubtract : bigIntegerSubtract.negate()).subtract(bigInteger.shiftLeft(1));
            bigInteger = bigInteger2;
        }
        return new BigInteger[]{bigInteger, bigIntegerSubtract};
    }

    public static byte getMu(ECCurve.F2m f2m) {
        BigInteger bigInteger = f2m.getA().toBigInteger();
        if (bigInteger.equals(ECConstants.ZERO)) {
            return (byte) -1;
        }
        if (bigInteger.equals(ECConstants.ONE)) {
            return (byte) 1;
        }
        throw new IllegalArgumentException("No Koblitz curve (ABC), TNAF multiplication not possible");
    }

    public static ECPoint.F2m[] getPreComp(ECPoint.F2m f2m, byte b3) {
        ECPoint.F2m[] f2mArr = new ECPoint.F2m[16];
        f2mArr[1] = f2m;
        byte[][] bArr = b3 == 0 ? alpha0Tnaf : alpha1Tnaf;
        int length = bArr.length;
        for (int i2 = 3; i2 < length; i2 += 2) {
            f2mArr[i2] = multiplyFromTnaf(f2m, bArr[i2]);
        }
        return f2mArr;
    }

    public static BigInteger[] getSi(ECCurve.F2m f2m) {
        BigInteger bigIntegerAdd;
        BigInteger bigIntegerAdd2;
        if (!f2m.isKoblitz()) {
            throw new IllegalArgumentException("si is defined for Koblitz curves only");
        }
        int m2 = f2m.getM();
        int iIntValue = f2m.getA().toBigInteger().intValue();
        byte mu = f2m.getMu();
        int iIntValue2 = f2m.getH().intValue();
        BigInteger[] lucas = getLucas(mu, (m2 + 3) - iIntValue, false);
        if (mu == 1) {
            BigInteger bigInteger = ECConstants.ONE;
            bigIntegerAdd = bigInteger.subtract(lucas[1]);
            bigIntegerAdd2 = bigInteger.subtract(lucas[0]);
        } else {
            if (mu != -1) {
                throw new IllegalArgumentException("mu must be 1 or -1");
            }
            BigInteger bigInteger2 = ECConstants.ONE;
            bigIntegerAdd = bigInteger2.add(lucas[1]);
            bigIntegerAdd2 = bigInteger2.add(lucas[0]);
        }
        BigInteger[] bigIntegerArr = new BigInteger[2];
        if (iIntValue2 == 2) {
            bigIntegerArr[0] = bigIntegerAdd.shiftRight(1);
            bigIntegerArr[1] = bigIntegerAdd2.shiftRight(1).negate();
        } else {
            if (iIntValue2 != 4) {
                throw new IllegalArgumentException("h (Cofactor) must be 2 or 4");
            }
            bigIntegerArr[0] = bigIntegerAdd.shiftRight(2);
            bigIntegerArr[1] = bigIntegerAdd2.shiftRight(2).negate();
        }
        return bigIntegerArr;
    }

    public static BigInteger getTw(byte b3, int i2) {
        if (i2 == 4) {
            return b3 == 1 ? BigInteger.valueOf(6L) : BigInteger.valueOf(10L);
        }
        BigInteger[] lucas = getLucas(b3, i2, false);
        BigInteger bit = ECConstants.ZERO.setBit(i2);
        return ECConstants.TWO.multiply(lucas[0]).multiply(lucas[1].modInverse(bit)).mod(bit);
    }

    public static ECPoint.F2m multiplyFromTnaf(ECPoint.F2m f2m, byte[] bArr) {
        ECPoint.F2m f2mTau = (ECPoint.F2m) ((ECCurve.F2m) f2m.getCurve()).getInfinity();
        for (int length = bArr.length - 1; length >= 0; length--) {
            f2mTau = tau(f2mTau);
            byte b3 = bArr[length];
            if (b3 == 1) {
                f2mTau = f2mTau.addSimple(f2m);
            } else if (b3 == -1) {
                f2mTau = f2mTau.subtractSimple(f2m);
            }
        }
        return f2mTau;
    }

    public static ECPoint.F2m multiplyRTnaf(ECPoint.F2m f2m, BigInteger bigInteger) {
        ECCurve.F2m f2m2 = (ECCurve.F2m) f2m.getCurve();
        return multiplyTnaf(f2m, partModReduction(bigInteger, f2m2.getM(), (byte) f2m2.getA().toBigInteger().intValue(), f2m2.getSi(), f2m2.getMu(), (byte) 10));
    }

    public static ECPoint.F2m multiplyTnaf(ECPoint.F2m f2m, ZTauElement zTauElement) {
        return multiplyFromTnaf(f2m, tauAdicNaf(((ECCurve.F2m) f2m.getCurve()).getMu(), zTauElement));
    }

    public static BigInteger norm(byte b3, ZTauElement zTauElement) {
        BigInteger bigIntegerSubtract;
        BigInteger bigInteger = zTauElement.f27941u;
        BigInteger bigIntegerMultiply = bigInteger.multiply(bigInteger);
        BigInteger bigIntegerMultiply2 = zTauElement.f27941u.multiply(zTauElement.f27942v);
        BigInteger bigInteger2 = zTauElement.f27942v;
        BigInteger bigIntegerShiftLeft = bigInteger2.multiply(bigInteger2).shiftLeft(1);
        if (b3 == 1) {
            bigIntegerSubtract = bigIntegerMultiply.add(bigIntegerMultiply2);
        } else {
            if (b3 != -1) {
                throw new IllegalArgumentException("mu must be 1 or -1");
            }
            bigIntegerSubtract = bigIntegerMultiply.subtract(bigIntegerMultiply2);
        }
        return bigIntegerSubtract.add(bigIntegerShiftLeft);
    }

    public static SimpleBigDecimal norm(byte b3, SimpleBigDecimal simpleBigDecimal, SimpleBigDecimal simpleBigDecimal2) {
        SimpleBigDecimal simpleBigDecimalSubtract;
        SimpleBigDecimal simpleBigDecimalMultiply = simpleBigDecimal.multiply(simpleBigDecimal);
        SimpleBigDecimal simpleBigDecimalMultiply2 = simpleBigDecimal.multiply(simpleBigDecimal2);
        SimpleBigDecimal simpleBigDecimalShiftLeft = simpleBigDecimal2.multiply(simpleBigDecimal2).shiftLeft(1);
        if (b3 == 1) {
            simpleBigDecimalSubtract = simpleBigDecimalMultiply.add(simpleBigDecimalMultiply2);
        } else {
            if (b3 != -1) {
                throw new IllegalArgumentException("mu must be 1 or -1");
            }
            simpleBigDecimalSubtract = simpleBigDecimalMultiply.subtract(simpleBigDecimalMultiply2);
        }
        return simpleBigDecimalSubtract.add(simpleBigDecimalShiftLeft);
    }

    public static ZTauElement partModReduction(BigInteger bigInteger, int i2, byte b3, BigInteger[] bigIntegerArr, byte b4, byte b5) {
        BigInteger bigIntegerAdd = b4 == 1 ? bigIntegerArr[0].add(bigIntegerArr[1]) : bigIntegerArr[0].subtract(bigIntegerArr[1]);
        BigInteger bigInteger2 = getLucas(b4, i2, true)[1];
        ZTauElement zTauElementRound = round(approximateDivisionByN(bigInteger, bigIntegerArr[0], bigInteger2, b3, i2, b5), approximateDivisionByN(bigInteger, bigIntegerArr[1], bigInteger2, b3, i2, b5), b4);
        return new ZTauElement(bigInteger.subtract(bigIntegerAdd.multiply(zTauElementRound.f27941u)).subtract(BigInteger.valueOf(2L).multiply(bigIntegerArr[1]).multiply(zTauElementRound.f27942v)), bigIntegerArr[1].multiply(zTauElementRound.f27941u).subtract(bigIntegerArr[0].multiply(zTauElementRound.f27942v)));
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x0081, code lost:
    
        if (r5.compareTo(r9) >= 0) goto L34;
     */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0071  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static org.bouncycastle.math.ec.ZTauElement round(org.bouncycastle.math.ec.SimpleBigDecimal r8, org.bouncycastle.math.ec.SimpleBigDecimal r9, byte r10) {
        /*
            int r0 = r8.getScale()
            int r1 = r9.getScale()
            if (r1 != r0) goto La7
            r0 = -1
            r1 = 1
            if (r10 == r1) goto L19
            if (r10 != r0) goto L11
            goto L19
        L11:
            java.lang.IllegalArgumentException r8 = new java.lang.IllegalArgumentException
            java.lang.String r9 = "mu must be 1 or -1"
            r8.<init>(r9)
            throw r8
        L19:
            java.math.BigInteger r2 = r8.round()
            java.math.BigInteger r3 = r9.round()
            org.bouncycastle.math.ec.SimpleBigDecimal r8 = r8.subtract(r2)
            org.bouncycastle.math.ec.SimpleBigDecimal r9 = r9.subtract(r3)
            org.bouncycastle.math.ec.SimpleBigDecimal r4 = r8.add(r8)
            if (r10 != r1) goto L34
            org.bouncycastle.math.ec.SimpleBigDecimal r4 = r4.add(r9)
            goto L38
        L34:
            org.bouncycastle.math.ec.SimpleBigDecimal r4 = r4.subtract(r9)
        L38:
            org.bouncycastle.math.ec.SimpleBigDecimal r5 = r9.add(r9)
            org.bouncycastle.math.ec.SimpleBigDecimal r5 = r5.add(r9)
            org.bouncycastle.math.ec.SimpleBigDecimal r9 = r5.add(r9)
            if (r10 != r1) goto L4f
            org.bouncycastle.math.ec.SimpleBigDecimal r5 = r8.subtract(r5)
            org.bouncycastle.math.ec.SimpleBigDecimal r8 = r8.add(r9)
            goto L57
        L4f:
            org.bouncycastle.math.ec.SimpleBigDecimal r5 = r8.add(r5)
            org.bouncycastle.math.ec.SimpleBigDecimal r8 = r8.subtract(r9)
        L57:
            java.math.BigInteger r9 = org.bouncycastle.math.ec.ECConstants.ONE
            int r6 = r4.compareTo(r9)
            r7 = 0
            if (r6 < 0) goto L69
            java.math.BigInteger r6 = org.bouncycastle.math.ec.Tnaf.MINUS_ONE
            int r6 = r5.compareTo(r6)
            if (r6 >= 0) goto L75
            goto L71
        L69:
            java.math.BigInteger r1 = org.bouncycastle.math.ec.ECConstants.TWO
            int r1 = r8.compareTo(r1)
            if (r1 < 0) goto L74
        L71:
            r1 = r7
            r7 = r10
            goto L75
        L74:
            r1 = r7
        L75:
            java.math.BigInteger r6 = org.bouncycastle.math.ec.Tnaf.MINUS_ONE
            int r4 = r4.compareTo(r6)
            if (r4 >= 0) goto L84
            int r8 = r5.compareTo(r9)
            if (r8 < 0) goto L8f
            goto L8c
        L84:
            java.math.BigInteger r9 = org.bouncycastle.math.ec.Tnaf.MINUS_TWO
            int r8 = r8.compareTo(r9)
            if (r8 >= 0) goto L8e
        L8c:
            int r8 = -r10
            byte r7 = (byte) r8
        L8e:
            r0 = r1
        L8f:
            long r8 = (long) r0
            java.math.BigInteger r8 = java.math.BigInteger.valueOf(r8)
            java.math.BigInteger r8 = r2.add(r8)
            long r9 = (long) r7
            java.math.BigInteger r9 = java.math.BigInteger.valueOf(r9)
            java.math.BigInteger r9 = r3.add(r9)
            org.bouncycastle.math.ec.ZTauElement r10 = new org.bouncycastle.math.ec.ZTauElement
            r10.<init>(r8, r9)
            return r10
        La7:
            java.lang.IllegalArgumentException r8 = new java.lang.IllegalArgumentException
            java.lang.String r9 = "lambda0 and lambda1 do not have same scale"
            r8.<init>(r9)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.math.ec.Tnaf.round(org.bouncycastle.math.ec.SimpleBigDecimal, org.bouncycastle.math.ec.SimpleBigDecimal, byte):org.bouncycastle.math.ec.ZTauElement");
    }

    public static ECPoint.F2m tau(ECPoint.F2m f2m) {
        if (f2m.isInfinity()) {
            return f2m;
        }
        return new ECPoint.F2m(f2m.getCurve(), f2m.getX().square(), f2m.getY().square(), f2m.isCompressed());
    }

    public static byte[] tauAdicNaf(byte b3, ZTauElement zTauElement) {
        if (b3 != 1 && b3 != -1) {
            throw new IllegalArgumentException("mu must be 1 or -1");
        }
        int iBitLength = norm(b3, zTauElement).bitLength();
        byte[] bArr = new byte[iBitLength > 30 ? iBitLength + 4 : 34];
        BigInteger bigIntegerClearBit = zTauElement.f27941u;
        BigInteger bigInteger = zTauElement.f27942v;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            BigInteger bigInteger2 = ECConstants.ZERO;
            if (bigIntegerClearBit.equals(bigInteger2) && bigInteger.equals(bigInteger2)) {
                int i4 = i2 + 1;
                byte[] bArr2 = new byte[i4];
                System.arraycopy(bArr, 0, bArr2, 0, i4);
                return bArr2;
            }
            if (bigIntegerClearBit.testBit(0)) {
                byte bIntValue = (byte) ECConstants.TWO.subtract(bigIntegerClearBit.subtract(bigInteger.shiftLeft(1)).mod(ECConstants.FOUR)).intValue();
                bArr[i3] = bIntValue;
                bigIntegerClearBit = bIntValue == 1 ? bigIntegerClearBit.clearBit(0) : bigIntegerClearBit.add(ECConstants.ONE);
                i2 = i3;
            } else {
                bArr[i3] = 0;
            }
            BigInteger bigIntegerShiftRight = bigIntegerClearBit.shiftRight(1);
            BigInteger bigIntegerAdd = b3 == 1 ? bigInteger.add(bigIntegerShiftRight) : bigInteger.subtract(bigIntegerShiftRight);
            BigInteger bigIntegerNegate = bigIntegerClearBit.shiftRight(1).negate();
            i3++;
            bigIntegerClearBit = bigIntegerAdd;
            bigInteger = bigIntegerNegate;
        }
    }

    public static byte[] tauAdicWNaf(byte b3, ZTauElement zTauElement, byte b4, BigInteger bigInteger, BigInteger bigInteger2, ZTauElement[] zTauElementArr) {
        boolean z2;
        if (b3 != 1 && b3 != -1) {
            throw new IllegalArgumentException("mu must be 1 or -1");
        }
        int iBitLength = norm(b3, zTauElement).bitLength();
        byte[] bArr = new byte[iBitLength > 30 ? iBitLength + 4 + b4 : b4 + 34];
        BigInteger bigIntegerShiftRight = bigInteger.shiftRight(1);
        BigInteger bigIntegerAdd = zTauElement.f27941u;
        BigInteger bigIntegerAdd2 = zTauElement.f27942v;
        int i2 = 0;
        while (true) {
            BigInteger bigInteger3 = ECConstants.ZERO;
            if (bigIntegerAdd.equals(bigInteger3) && bigIntegerAdd2.equals(bigInteger3)) {
                return bArr;
            }
            if (bigIntegerAdd.testBit(0)) {
                BigInteger bigIntegerMod = bigIntegerAdd.add(bigIntegerAdd2.multiply(bigInteger2)).mod(bigInteger);
                if (bigIntegerMod.compareTo(bigIntegerShiftRight) >= 0) {
                    bigIntegerMod = bigIntegerMod.subtract(bigInteger);
                }
                byte bIntValue = (byte) bigIntegerMod.intValue();
                bArr[i2] = bIntValue;
                if (bIntValue < 0) {
                    bIntValue = (byte) (-bIntValue);
                    z2 = false;
                } else {
                    z2 = true;
                }
                if (z2) {
                    bigIntegerAdd = bigIntegerAdd.subtract(zTauElementArr[bIntValue].f27941u);
                    bigIntegerAdd2 = bigIntegerAdd2.subtract(zTauElementArr[bIntValue].f27942v);
                } else {
                    bigIntegerAdd = bigIntegerAdd.add(zTauElementArr[bIntValue].f27941u);
                    bigIntegerAdd2 = bigIntegerAdd2.add(zTauElementArr[bIntValue].f27942v);
                }
            } else {
                bArr[i2] = 0;
            }
            BigInteger bigIntegerShiftRight2 = bigIntegerAdd.shiftRight(1);
            BigInteger bigIntegerAdd3 = b3 == 1 ? bigIntegerAdd2.add(bigIntegerShiftRight2) : bigIntegerAdd2.subtract(bigIntegerShiftRight2);
            BigInteger bigIntegerNegate = bigIntegerAdd.shiftRight(1).negate();
            i2++;
            bigIntegerAdd = bigIntegerAdd3;
            bigIntegerAdd2 = bigIntegerNegate;
        }
    }
}
