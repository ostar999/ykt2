package org.bouncycastle.crypto.params;

import java.math.BigInteger;

/* loaded from: classes9.dex */
public class NaccacheSternKeyParameters extends AsymmetricKeyParameter {

    /* renamed from: g, reason: collision with root package name */
    private BigInteger f27878g;
    int lowerSigmaBound;

    /* renamed from: n, reason: collision with root package name */
    private BigInteger f27879n;

    public NaccacheSternKeyParameters(boolean z2, BigInteger bigInteger, BigInteger bigInteger2, int i2) {
        super(z2);
        this.f27878g = bigInteger;
        this.f27879n = bigInteger2;
        this.lowerSigmaBound = i2;
    }

    public BigInteger getG() {
        return this.f27878g;
    }

    public int getLowerSigmaBound() {
        return this.lowerSigmaBound;
    }

    public BigInteger getModulus() {
        return this.f27879n;
    }
}
