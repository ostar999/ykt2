package org.bouncycastle.math.ec;

import java.math.BigInteger;

/* loaded from: classes9.dex */
class ReferenceMultiplier implements ECMultiplier {
    @Override // org.bouncycastle.math.ec.ECMultiplier
    public ECPoint multiply(ECPoint eCPoint, BigInteger bigInteger, PreCompInfo preCompInfo) {
        ECPoint infinity = eCPoint.getCurve().getInfinity();
        int iBitLength = bigInteger.bitLength();
        for (int i2 = 0; i2 < iBitLength; i2++) {
            if (bigInteger.testBit(i2)) {
                infinity = infinity.add(eCPoint);
            }
            eCPoint = eCPoint.twice();
        }
        return infinity;
    }
}
