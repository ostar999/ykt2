package org.bouncycastle.jce.spec;

import java.math.BigInteger;
import java.security.spec.KeySpec;

/* loaded from: classes9.dex */
public class GOST3410PrivateKeySpec implements KeySpec {

    /* renamed from: a, reason: collision with root package name */
    private BigInteger f27915a;

    /* renamed from: p, reason: collision with root package name */
    private BigInteger f27916p;

    /* renamed from: q, reason: collision with root package name */
    private BigInteger f27917q;

    /* renamed from: x, reason: collision with root package name */
    private BigInteger f27918x;

    public GOST3410PrivateKeySpec(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4) {
        this.f27918x = bigInteger;
        this.f27916p = bigInteger2;
        this.f27917q = bigInteger3;
        this.f27915a = bigInteger4;
    }

    public BigInteger getA() {
        return this.f27915a;
    }

    public BigInteger getP() {
        return this.f27916p;
    }

    public BigInteger getQ() {
        return this.f27917q;
    }

    public BigInteger getX() {
        return this.f27918x;
    }
}
