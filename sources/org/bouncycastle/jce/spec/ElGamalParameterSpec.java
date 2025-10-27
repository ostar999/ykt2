package org.bouncycastle.jce.spec;

import java.math.BigInteger;
import java.security.spec.AlgorithmParameterSpec;

/* loaded from: classes9.dex */
public class ElGamalParameterSpec implements AlgorithmParameterSpec {

    /* renamed from: g, reason: collision with root package name */
    private BigInteger f27911g;

    /* renamed from: p, reason: collision with root package name */
    private BigInteger f27912p;

    public ElGamalParameterSpec(BigInteger bigInteger, BigInteger bigInteger2) {
        this.f27912p = bigInteger;
        this.f27911g = bigInteger2;
    }

    public BigInteger getG() {
        return this.f27911g;
    }

    public BigInteger getP() {
        return this.f27912p;
    }
}
