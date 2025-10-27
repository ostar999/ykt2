package org.bouncycastle.math.ec;

import java.math.BigInteger;

/* loaded from: classes9.dex */
class WNafMultiplier implements ECMultiplier {
    @Override // org.bouncycastle.math.ec.ECMultiplier
    public ECPoint multiply(ECPoint eCPoint, BigInteger bigInteger, PreCompInfo preCompInfo) {
        byte b3;
        int i2;
        byte b4;
        int i3;
        int length;
        WNafPreCompInfo wNafPreCompInfo = (preCompInfo == null || !(preCompInfo instanceof WNafPreCompInfo)) ? new WNafPreCompInfo() : (WNafPreCompInfo) preCompInfo;
        int iBitLength = bigInteger.bitLength();
        if (iBitLength < 13) {
            b3 = 2;
            i2 = 1;
        } else if (iBitLength < 41) {
            b3 = 3;
            i2 = 2;
        } else if (iBitLength < 121) {
            i2 = 4;
            b3 = 4;
        } else {
            b3 = 8;
            if (iBitLength < 337) {
                b3 = 5;
                i2 = 8;
            } else {
                if (iBitLength < 897) {
                    b4 = 6;
                    i3 = 16;
                } else if (iBitLength < 2305) {
                    b4 = 7;
                    i3 = 32;
                } else {
                    i2 = 127;
                }
                b3 = b4;
                i2 = i3;
            }
        }
        ECPoint[] preComp = wNafPreCompInfo.getPreComp();
        ECPoint twiceP = wNafPreCompInfo.getTwiceP();
        if (preComp == null) {
            preComp = new ECPoint[]{eCPoint};
            length = 1;
        } else {
            length = preComp.length;
        }
        if (twiceP == null) {
            twiceP = eCPoint.twice();
        }
        if (length < i2) {
            ECPoint[] eCPointArr = new ECPoint[i2];
            System.arraycopy(preComp, 0, eCPointArr, 0, length);
            while (length < i2) {
                eCPointArr[length] = twiceP.add(eCPointArr[length - 1]);
                length++;
            }
            preComp = eCPointArr;
        }
        byte[] bArrWindowNaf = windowNaf(b3, bigInteger);
        int length2 = bArrWindowNaf.length;
        ECPoint infinity = eCPoint.getCurve().getInfinity();
        for (int i4 = length2 - 1; i4 >= 0; i4--) {
            infinity = infinity.twice();
            byte b5 = bArrWindowNaf[i4];
            if (b5 != 0) {
                infinity = b5 > 0 ? infinity.add(preComp[(b5 - 1) / 2]) : infinity.subtract(preComp[((-b5) - 1) / 2]);
            }
        }
        wNafPreCompInfo.setPreComp(preComp);
        wNafPreCompInfo.setTwiceP(twiceP);
        eCPoint.setPreCompInfo(wNafPreCompInfo);
        return infinity;
    }

    public byte[] windowNaf(byte b3, BigInteger bigInteger) {
        byte[] bArr = new byte[bigInteger.bitLength() + 1];
        short s2 = (short) (1 << b3);
        BigInteger bigIntegerValueOf = BigInteger.valueOf(s2);
        int i2 = 0;
        int i3 = 0;
        while (bigInteger.signum() > 0) {
            if (bigInteger.testBit(0)) {
                BigInteger bigIntegerMod = bigInteger.mod(bigIntegerValueOf);
                boolean zTestBit = bigIntegerMod.testBit(b3 - 1);
                int iIntValue = bigIntegerMod.intValue();
                if (zTestBit) {
                    bArr[i3] = (byte) (iIntValue - s2);
                } else {
                    bArr[i3] = (byte) iIntValue;
                }
                bigInteger = bigInteger.subtract(BigInteger.valueOf(bArr[i3]));
                i2 = i3;
            } else {
                bArr[i3] = 0;
            }
            bigInteger = bigInteger.shiftRight(1);
            i3++;
        }
        int i4 = i2 + 1;
        byte[] bArr2 = new byte[i4];
        System.arraycopy(bArr, 0, bArr2, 0, i4);
        return bArr2;
    }
}
