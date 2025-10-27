package org.bouncycastle.crypto.params;

import java.math.BigInteger;

/* loaded from: classes9.dex */
public class ECPrivateKeyParameters extends ECKeyParameters {

    /* renamed from: d, reason: collision with root package name */
    BigInteger f27865d;

    public ECPrivateKeyParameters(BigInteger bigInteger, ECDomainParameters eCDomainParameters) {
        super(true, eCDomainParameters);
        this.f27865d = bigInteger;
    }

    public BigInteger getD() {
        return this.f27865d;
    }
}
