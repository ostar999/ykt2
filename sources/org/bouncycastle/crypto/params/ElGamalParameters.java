package org.bouncycastle.crypto.params;

import java.math.BigInteger;
import org.bouncycastle.crypto.CipherParameters;

/* loaded from: classes9.dex */
public class ElGamalParameters implements CipherParameters {

    /* renamed from: g, reason: collision with root package name */
    private BigInteger f27866g;

    /* renamed from: l, reason: collision with root package name */
    private int f27867l;

    /* renamed from: p, reason: collision with root package name */
    private BigInteger f27868p;

    public ElGamalParameters(BigInteger bigInteger, BigInteger bigInteger2) {
        this(bigInteger, bigInteger2, 0);
    }

    public ElGamalParameters(BigInteger bigInteger, BigInteger bigInteger2, int i2) {
        this.f27866g = bigInteger2;
        this.f27868p = bigInteger;
        this.f27867l = i2;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ElGamalParameters)) {
            return false;
        }
        ElGamalParameters elGamalParameters = (ElGamalParameters) obj;
        return elGamalParameters.getP().equals(this.f27868p) && elGamalParameters.getG().equals(this.f27866g) && elGamalParameters.getL() == this.f27867l;
    }

    public BigInteger getG() {
        return this.f27866g;
    }

    public int getL() {
        return this.f27867l;
    }

    public BigInteger getP() {
        return this.f27868p;
    }

    public int hashCode() {
        return (getP().hashCode() ^ getG().hashCode()) + this.f27867l;
    }
}
