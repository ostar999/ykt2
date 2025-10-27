package org.bouncycastle.jce.spec;

import java.math.BigInteger;

/* loaded from: classes9.dex */
public class ECPrivateKeySpec extends ECKeySpec {

    /* renamed from: d, reason: collision with root package name */
    private BigInteger f27909d;

    public ECPrivateKeySpec(BigInteger bigInteger, ECParameterSpec eCParameterSpec) {
        super(eCParameterSpec);
        this.f27909d = bigInteger;
    }

    public BigInteger getD() {
        return this.f27909d;
    }
}
