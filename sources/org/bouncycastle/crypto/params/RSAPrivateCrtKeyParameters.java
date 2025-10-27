package org.bouncycastle.crypto.params;

import java.math.BigInteger;

/* loaded from: classes9.dex */
public class RSAPrivateCrtKeyParameters extends RSAKeyParameters {
    private BigInteger dP;
    private BigInteger dQ;

    /* renamed from: e, reason: collision with root package name */
    private BigInteger f27880e;

    /* renamed from: p, reason: collision with root package name */
    private BigInteger f27881p;

    /* renamed from: q, reason: collision with root package name */
    private BigInteger f27882q;
    private BigInteger qInv;

    public RSAPrivateCrtKeyParameters(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4, BigInteger bigInteger5, BigInteger bigInteger6, BigInteger bigInteger7, BigInteger bigInteger8) {
        super(true, bigInteger, bigInteger3);
        this.f27880e = bigInteger2;
        this.f27881p = bigInteger4;
        this.f27882q = bigInteger5;
        this.dP = bigInteger6;
        this.dQ = bigInteger7;
        this.qInv = bigInteger8;
    }

    public BigInteger getDP() {
        return this.dP;
    }

    public BigInteger getDQ() {
        return this.dQ;
    }

    public BigInteger getP() {
        return this.f27881p;
    }

    public BigInteger getPublicExponent() {
        return this.f27880e;
    }

    public BigInteger getQ() {
        return this.f27882q;
    }

    public BigInteger getQInv() {
        return this.qInv;
    }
}
