package org.bouncycastle.crypto.params;

import java.math.BigInteger;
import org.bouncycastle.crypto.CipherParameters;

/* loaded from: classes9.dex */
public class GOST3410Parameters implements CipherParameters {

    /* renamed from: a, reason: collision with root package name */
    private BigInteger f27871a;

    /* renamed from: p, reason: collision with root package name */
    private BigInteger f27872p;

    /* renamed from: q, reason: collision with root package name */
    private BigInteger f27873q;
    private GOST3410ValidationParameters validation;

    public GOST3410Parameters(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
        this.f27872p = bigInteger;
        this.f27873q = bigInteger2;
        this.f27871a = bigInteger3;
    }

    public GOST3410Parameters(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, GOST3410ValidationParameters gOST3410ValidationParameters) {
        this.f27871a = bigInteger3;
        this.f27872p = bigInteger;
        this.f27873q = bigInteger2;
        this.validation = gOST3410ValidationParameters;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof GOST3410Parameters)) {
            return false;
        }
        GOST3410Parameters gOST3410Parameters = (GOST3410Parameters) obj;
        return gOST3410Parameters.getP().equals(this.f27872p) && gOST3410Parameters.getQ().equals(this.f27873q) && gOST3410Parameters.getA().equals(this.f27871a);
    }

    public BigInteger getA() {
        return this.f27871a;
    }

    public BigInteger getP() {
        return this.f27872p;
    }

    public BigInteger getQ() {
        return this.f27873q;
    }

    public GOST3410ValidationParameters getValidationParameters() {
        return this.validation;
    }

    public int hashCode() {
        return (this.f27872p.hashCode() ^ this.f27873q.hashCode()) ^ this.f27871a.hashCode();
    }
}
