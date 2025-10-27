package org.bouncycastle.jce.spec;

import java.math.BigInteger;

/* loaded from: classes9.dex */
public class GOST3410PublicKeyParameterSetSpec {

    /* renamed from: a, reason: collision with root package name */
    private BigInteger f27919a;

    /* renamed from: p, reason: collision with root package name */
    private BigInteger f27920p;

    /* renamed from: q, reason: collision with root package name */
    private BigInteger f27921q;

    public GOST3410PublicKeyParameterSetSpec(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
        this.f27920p = bigInteger;
        this.f27921q = bigInteger2;
        this.f27919a = bigInteger3;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof GOST3410PublicKeyParameterSetSpec)) {
            return false;
        }
        GOST3410PublicKeyParameterSetSpec gOST3410PublicKeyParameterSetSpec = (GOST3410PublicKeyParameterSetSpec) obj;
        return this.f27919a.equals(gOST3410PublicKeyParameterSetSpec.f27919a) && this.f27920p.equals(gOST3410PublicKeyParameterSetSpec.f27920p) && this.f27921q.equals(gOST3410PublicKeyParameterSetSpec.f27921q);
    }

    public BigInteger getA() {
        return this.f27919a;
    }

    public BigInteger getP() {
        return this.f27920p;
    }

    public BigInteger getQ() {
        return this.f27921q;
    }

    public int hashCode() {
        return (this.f27919a.hashCode() ^ this.f27920p.hashCode()) ^ this.f27921q.hashCode();
    }
}
