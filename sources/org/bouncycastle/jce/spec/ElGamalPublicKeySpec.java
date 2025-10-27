package org.bouncycastle.jce.spec;

import java.math.BigInteger;

/* loaded from: classes9.dex */
public class ElGamalPublicKeySpec extends ElGamalKeySpec {

    /* renamed from: y, reason: collision with root package name */
    private BigInteger f27914y;

    public ElGamalPublicKeySpec(BigInteger bigInteger, ElGamalParameterSpec elGamalParameterSpec) {
        super(elGamalParameterSpec);
        this.f27914y = bigInteger;
    }

    public BigInteger getY() {
        return this.f27914y;
    }
}
