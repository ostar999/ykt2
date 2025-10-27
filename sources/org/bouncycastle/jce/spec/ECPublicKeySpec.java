package org.bouncycastle.jce.spec;

import org.bouncycastle.math.ec.ECPoint;

/* loaded from: classes9.dex */
public class ECPublicKeySpec extends ECKeySpec {

    /* renamed from: q, reason: collision with root package name */
    private ECPoint f27910q;

    public ECPublicKeySpec(ECPoint eCPoint, ECParameterSpec eCParameterSpec) {
        super(eCParameterSpec);
        this.f27910q = eCPoint;
    }

    public ECPoint getQ() {
        return this.f27910q;
    }
}
