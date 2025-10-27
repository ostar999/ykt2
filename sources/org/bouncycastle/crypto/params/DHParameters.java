package org.bouncycastle.crypto.params;

import java.math.BigInteger;
import org.bouncycastle.crypto.CipherParameters;

/* loaded from: classes9.dex */
public class DHParameters implements CipherParameters {
    private static final int DEFAULT_MINIMUM_LENGTH = 160;

    /* renamed from: g, reason: collision with root package name */
    private BigInteger f27850g;

    /* renamed from: j, reason: collision with root package name */
    private BigInteger f27851j;

    /* renamed from: l, reason: collision with root package name */
    private int f27852l;

    /* renamed from: m, reason: collision with root package name */
    private int f27853m;

    /* renamed from: p, reason: collision with root package name */
    private BigInteger f27854p;

    /* renamed from: q, reason: collision with root package name */
    private BigInteger f27855q;
    private DHValidationParameters validation;

    public DHParameters(BigInteger bigInteger, BigInteger bigInteger2) {
        this(bigInteger, bigInteger2, null, 0);
    }

    public DHParameters(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
        this(bigInteger, bigInteger2, bigInteger3, 0);
    }

    public DHParameters(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, int i2) {
        this(bigInteger, bigInteger2, bigInteger3, getDefaultMParam(i2), i2, null, null);
    }

    public DHParameters(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, int i2, int i3) {
        this(bigInteger, bigInteger2, bigInteger3, i2, i3, null, null);
    }

    public DHParameters(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, int i2, int i3, BigInteger bigInteger4, DHValidationParameters dHValidationParameters) {
        if (i3 != 0) {
            if (i3 >= bigInteger.bitLength()) {
                throw new IllegalArgumentException("when l value specified, it must be less than bitlength(p)");
            }
            if (i3 < i2) {
                throw new IllegalArgumentException("when l value specified, it may not be less than m value");
            }
        }
        this.f27850g = bigInteger2;
        this.f27854p = bigInteger;
        this.f27855q = bigInteger3;
        this.f27853m = i2;
        this.f27852l = i3;
        this.f27851j = bigInteger4;
        this.validation = dHValidationParameters;
    }

    public DHParameters(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4, DHValidationParameters dHValidationParameters) {
        this(bigInteger, bigInteger2, bigInteger3, 160, 0, bigInteger4, dHValidationParameters);
    }

    private static int getDefaultMParam(int i2) {
        if (i2 != 0 && i2 < 160) {
            return i2;
        }
        return 160;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof DHParameters)) {
            return false;
        }
        DHParameters dHParameters = (DHParameters) obj;
        if (getQ() != null) {
            if (!getQ().equals(dHParameters.getQ())) {
                return false;
            }
        } else if (dHParameters.getQ() != null) {
            return false;
        }
        return dHParameters.getP().equals(this.f27854p) && dHParameters.getG().equals(this.f27850g);
    }

    public BigInteger getG() {
        return this.f27850g;
    }

    public BigInteger getJ() {
        return this.f27851j;
    }

    public int getL() {
        return this.f27852l;
    }

    public int getM() {
        return this.f27853m;
    }

    public BigInteger getP() {
        return this.f27854p;
    }

    public BigInteger getQ() {
        return this.f27855q;
    }

    public DHValidationParameters getValidationParameters() {
        return this.validation;
    }

    public int hashCode() {
        return (getP().hashCode() ^ getG().hashCode()) ^ (getQ() != null ? getQ().hashCode() : 0);
    }
}
