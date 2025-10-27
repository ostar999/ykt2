package org.bouncycastle.crypto.params;

import java.math.BigInteger;

/* loaded from: classes9.dex */
public class GOST3410PublicKeyParameters extends GOST3410KeyParameters {

    /* renamed from: y, reason: collision with root package name */
    private BigInteger f27875y;

    public GOST3410PublicKeyParameters(BigInteger bigInteger, GOST3410Parameters gOST3410Parameters) {
        super(false, gOST3410Parameters);
        this.f27875y = bigInteger;
    }

    public BigInteger getY() {
        return this.f27875y;
    }
}
