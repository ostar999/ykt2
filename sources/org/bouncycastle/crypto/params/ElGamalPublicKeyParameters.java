package org.bouncycastle.crypto.params;

import java.math.BigInteger;

/* loaded from: classes9.dex */
public class ElGamalPublicKeyParameters extends ElGamalKeyParameters {

    /* renamed from: y, reason: collision with root package name */
    private BigInteger f27870y;

    public ElGamalPublicKeyParameters(BigInteger bigInteger, ElGamalParameters elGamalParameters) {
        super(false, elGamalParameters);
        this.f27870y = bigInteger;
    }

    @Override // org.bouncycastle.crypto.params.ElGamalKeyParameters
    public boolean equals(Object obj) {
        return (obj instanceof ElGamalPublicKeyParameters) && ((ElGamalPublicKeyParameters) obj).getY().equals(this.f27870y) && super.equals(obj);
    }

    public BigInteger getY() {
        return this.f27870y;
    }

    @Override // org.bouncycastle.crypto.params.ElGamalKeyParameters
    public int hashCode() {
        return this.f27870y.hashCode() ^ super.hashCode();
    }
}
