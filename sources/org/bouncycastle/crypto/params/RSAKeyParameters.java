package org.bouncycastle.crypto.params;

import java.math.BigInteger;

/* loaded from: classes9.dex */
public class RSAKeyParameters extends AsymmetricKeyParameter {
    private BigInteger exponent;
    private BigInteger modulus;

    public RSAKeyParameters(boolean z2, BigInteger bigInteger, BigInteger bigInteger2) {
        super(z2);
        this.modulus = bigInteger;
        this.exponent = bigInteger2;
    }

    public BigInteger getExponent() {
        return this.exponent;
    }

    public BigInteger getModulus() {
        return this.modulus;
    }
}
