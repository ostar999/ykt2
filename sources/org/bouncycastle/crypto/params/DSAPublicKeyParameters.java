package org.bouncycastle.crypto.params;

import java.math.BigInteger;

/* loaded from: classes9.dex */
public class DSAPublicKeyParameters extends DSAKeyParameters {

    /* renamed from: y, reason: collision with root package name */
    private BigInteger f27862y;

    public DSAPublicKeyParameters(BigInteger bigInteger, DSAParameters dSAParameters) {
        super(false, dSAParameters);
        this.f27862y = bigInteger;
    }

    public BigInteger getY() {
        return this.f27862y;
    }
}
