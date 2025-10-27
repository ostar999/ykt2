package org.bouncycastle.math.ec;

import java.math.BigInteger;

/* loaded from: classes9.dex */
interface ECMultiplier {
    ECPoint multiply(ECPoint eCPoint, BigInteger bigInteger, PreCompInfo preCompInfo);
}
