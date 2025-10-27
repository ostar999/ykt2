package org.bouncycastle.crypto.params;

import java.math.BigInteger;

/* loaded from: classes9.dex */
public class DSAPrivateKeyParameters extends DSAKeyParameters {

    /* renamed from: x, reason: collision with root package name */
    private BigInteger f27861x;

    public DSAPrivateKeyParameters(BigInteger bigInteger, DSAParameters dSAParameters) {
        super(true, dSAParameters);
        this.f27861x = bigInteger;
    }

    public BigInteger getX() {
        return this.f27861x;
    }
}
