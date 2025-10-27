package org.bouncycastle.crypto.params;

import java.math.BigInteger;
import org.bouncycastle.math.ec.ECConstants;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECPoint;

/* loaded from: classes9.dex */
public class ECDomainParameters implements ECConstants {
    ECPoint G;
    ECCurve curve;

    /* renamed from: h, reason: collision with root package name */
    BigInteger f27863h;

    /* renamed from: n, reason: collision with root package name */
    BigInteger f27864n;
    byte[] seed;

    public ECDomainParameters(ECCurve eCCurve, ECPoint eCPoint, BigInteger bigInteger) {
        this.curve = eCCurve;
        this.G = eCPoint;
        this.f27864n = bigInteger;
        this.f27863h = ECConstants.ONE;
        this.seed = null;
    }

    public ECDomainParameters(ECCurve eCCurve, ECPoint eCPoint, BigInteger bigInteger, BigInteger bigInteger2) {
        this.curve = eCCurve;
        this.G = eCPoint;
        this.f27864n = bigInteger;
        this.f27863h = bigInteger2;
        this.seed = null;
    }

    public ECDomainParameters(ECCurve eCCurve, ECPoint eCPoint, BigInteger bigInteger, BigInteger bigInteger2, byte[] bArr) {
        this.curve = eCCurve;
        this.G = eCPoint;
        this.f27864n = bigInteger;
        this.f27863h = bigInteger2;
        this.seed = bArr;
    }

    public ECCurve getCurve() {
        return this.curve;
    }

    public ECPoint getG() {
        return this.G;
    }

    public BigInteger getH() {
        return this.f27863h;
    }

    public BigInteger getN() {
        return this.f27864n;
    }

    public byte[] getSeed() {
        return this.seed;
    }
}
