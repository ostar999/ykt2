package org.bouncycastle.crypto.agreement.srp;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.Digest;

/* loaded from: classes9.dex */
public class SRP6Server {
    protected BigInteger A;
    protected BigInteger B;
    protected BigInteger N;
    protected BigInteger S;

    /* renamed from: b, reason: collision with root package name */
    protected BigInteger f27814b;
    protected Digest digest;

    /* renamed from: g, reason: collision with root package name */
    protected BigInteger f27815g;
    protected SecureRandom random;

    /* renamed from: u, reason: collision with root package name */
    protected BigInteger f27816u;

    /* renamed from: v, reason: collision with root package name */
    protected BigInteger f27817v;

    private BigInteger calculateS() {
        return this.f27817v.modPow(this.f27816u, this.N).multiply(this.A).mod(this.N).modPow(this.f27814b, this.N);
    }

    public BigInteger calculateSecret(BigInteger bigInteger) throws CryptoException {
        BigInteger bigIntegerValidatePublicValue = SRP6Util.validatePublicValue(this.N, bigInteger);
        this.A = bigIntegerValidatePublicValue;
        this.f27816u = SRP6Util.calculateU(this.digest, this.N, bigIntegerValidatePublicValue, this.B);
        BigInteger bigIntegerCalculateS = calculateS();
        this.S = bigIntegerCalculateS;
        return bigIntegerCalculateS;
    }

    public BigInteger generateServerCredentials() {
        BigInteger bigIntegerCalculateK = SRP6Util.calculateK(this.digest, this.N, this.f27815g);
        this.f27814b = selectPrivateValue();
        BigInteger bigIntegerMod = bigIntegerCalculateK.multiply(this.f27817v).mod(this.N).add(this.f27815g.modPow(this.f27814b, this.N)).mod(this.N);
        this.B = bigIntegerMod;
        return bigIntegerMod;
    }

    public void init(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, Digest digest, SecureRandom secureRandom) {
        this.N = bigInteger;
        this.f27815g = bigInteger2;
        this.f27817v = bigInteger3;
        this.random = secureRandom;
        this.digest = digest;
    }

    public BigInteger selectPrivateValue() {
        return SRP6Util.generatePrivateValue(this.digest, this.N, this.f27815g, this.random);
    }
}
