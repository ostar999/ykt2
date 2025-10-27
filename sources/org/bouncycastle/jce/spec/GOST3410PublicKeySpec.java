package org.bouncycastle.jce.spec;

import java.math.BigInteger;
import java.security.spec.KeySpec;

/* loaded from: classes9.dex */
public class GOST3410PublicKeySpec implements KeySpec {

    /* renamed from: a, reason: collision with root package name */
    private BigInteger f27922a;

    /* renamed from: p, reason: collision with root package name */
    private BigInteger f27923p;

    /* renamed from: q, reason: collision with root package name */
    private BigInteger f27924q;

    /* renamed from: y, reason: collision with root package name */
    private BigInteger f27925y;

    public GOST3410PublicKeySpec(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4) {
        this.f27925y = bigInteger;
        this.f27923p = bigInteger2;
        this.f27924q = bigInteger3;
        this.f27922a = bigInteger4;
    }

    public BigInteger getA() {
        return this.f27922a;
    }

    public BigInteger getP() {
        return this.f27923p;
    }

    public BigInteger getQ() {
        return this.f27924q;
    }

    public BigInteger getY() {
        return this.f27925y;
    }
}
