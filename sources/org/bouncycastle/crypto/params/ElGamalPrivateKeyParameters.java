package org.bouncycastle.crypto.params;

import java.math.BigInteger;

/* loaded from: classes9.dex */
public class ElGamalPrivateKeyParameters extends ElGamalKeyParameters {

    /* renamed from: x, reason: collision with root package name */
    private BigInteger f27869x;

    public ElGamalPrivateKeyParameters(BigInteger bigInteger, ElGamalParameters elGamalParameters) {
        super(true, elGamalParameters);
        this.f27869x = bigInteger;
    }

    @Override // org.bouncycastle.crypto.params.ElGamalKeyParameters
    public boolean equals(Object obj) {
        if ((obj instanceof ElGamalPrivateKeyParameters) && ((ElGamalPrivateKeyParameters) obj).getX().equals(this.f27869x)) {
            return super.equals(obj);
        }
        return false;
    }

    public BigInteger getX() {
        return this.f27869x;
    }

    @Override // org.bouncycastle.crypto.params.ElGamalKeyParameters
    public int hashCode() {
        return getX().hashCode();
    }
}
