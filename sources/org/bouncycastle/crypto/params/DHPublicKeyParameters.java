package org.bouncycastle.crypto.params;

import java.math.BigInteger;

/* loaded from: classes9.dex */
public class DHPublicKeyParameters extends DHKeyParameters {

    /* renamed from: y, reason: collision with root package name */
    private BigInteger f27857y;

    public DHPublicKeyParameters(BigInteger bigInteger, DHParameters dHParameters) {
        super(false, dHParameters);
        this.f27857y = bigInteger;
    }

    @Override // org.bouncycastle.crypto.params.DHKeyParameters
    public boolean equals(Object obj) {
        return (obj instanceof DHPublicKeyParameters) && ((DHPublicKeyParameters) obj).getY().equals(this.f27857y) && super.equals(obj);
    }

    public BigInteger getY() {
        return this.f27857y;
    }

    @Override // org.bouncycastle.crypto.params.DHKeyParameters
    public int hashCode() {
        return this.f27857y.hashCode() ^ super.hashCode();
    }
}
