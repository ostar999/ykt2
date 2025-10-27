package org.bouncycastle.crypto.params;

import java.math.BigInteger;
import org.bouncycastle.crypto.CipherParameters;

/* loaded from: classes9.dex */
public class DSAParameters implements CipherParameters {

    /* renamed from: g, reason: collision with root package name */
    private BigInteger f27858g;

    /* renamed from: p, reason: collision with root package name */
    private BigInteger f27859p;

    /* renamed from: q, reason: collision with root package name */
    private BigInteger f27860q;
    private DSAValidationParameters validation;

    public DSAParameters(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
        this.f27858g = bigInteger3;
        this.f27859p = bigInteger;
        this.f27860q = bigInteger2;
    }

    public DSAParameters(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, DSAValidationParameters dSAValidationParameters) {
        this.f27858g = bigInteger3;
        this.f27859p = bigInteger;
        this.f27860q = bigInteger2;
        this.validation = dSAValidationParameters;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof DSAParameters)) {
            return false;
        }
        DSAParameters dSAParameters = (DSAParameters) obj;
        return dSAParameters.getP().equals(this.f27859p) && dSAParameters.getQ().equals(this.f27860q) && dSAParameters.getG().equals(this.f27858g);
    }

    public BigInteger getG() {
        return this.f27858g;
    }

    public BigInteger getP() {
        return this.f27859p;
    }

    public BigInteger getQ() {
        return this.f27860q;
    }

    public DSAValidationParameters getValidationParameters() {
        return this.validation;
    }

    public int hashCode() {
        return (getP().hashCode() ^ getQ().hashCode()) ^ getG().hashCode();
    }
}
