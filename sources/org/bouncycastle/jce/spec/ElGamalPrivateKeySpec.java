package org.bouncycastle.jce.spec;

import java.math.BigInteger;

/* loaded from: classes9.dex */
public class ElGamalPrivateKeySpec extends ElGamalKeySpec {

    /* renamed from: x, reason: collision with root package name */
    private BigInteger f27913x;

    public ElGamalPrivateKeySpec(BigInteger bigInteger, ElGamalParameterSpec elGamalParameterSpec) {
        super(elGamalParameterSpec);
        this.f27913x = bigInteger;
    }

    public BigInteger getX() {
        return this.f27913x;
    }
}
