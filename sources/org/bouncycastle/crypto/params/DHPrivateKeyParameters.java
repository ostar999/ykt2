package org.bouncycastle.crypto.params;

import java.math.BigInteger;

/* loaded from: classes9.dex */
public class DHPrivateKeyParameters extends DHKeyParameters {

    /* renamed from: x, reason: collision with root package name */
    private BigInteger f27856x;

    public DHPrivateKeyParameters(BigInteger bigInteger, DHParameters dHParameters) {
        super(true, dHParameters);
        this.f27856x = bigInteger;
    }

    @Override // org.bouncycastle.crypto.params.DHKeyParameters
    public boolean equals(Object obj) {
        return (obj instanceof DHPrivateKeyParameters) && ((DHPrivateKeyParameters) obj).getX().equals(this.f27856x) && super.equals(obj);
    }

    public BigInteger getX() {
        return this.f27856x;
    }

    @Override // org.bouncycastle.crypto.params.DHKeyParameters
    public int hashCode() {
        return this.f27856x.hashCode() ^ super.hashCode();
    }
}
