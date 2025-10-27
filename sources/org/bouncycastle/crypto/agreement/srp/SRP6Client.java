package org.bouncycastle.crypto.agreement.srp;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.Digest;

/* loaded from: classes9.dex */
public class SRP6Client {
    protected BigInteger A;
    protected BigInteger B;
    protected BigInteger N;
    protected BigInteger S;

    /* renamed from: a, reason: collision with root package name */
    protected BigInteger f27810a;
    protected Digest digest;

    /* renamed from: g, reason: collision with root package name */
    protected BigInteger f27811g;
    protected SecureRandom random;

    /* renamed from: u, reason: collision with root package name */
    protected BigInteger f27812u;

    /* renamed from: x, reason: collision with root package name */
    protected BigInteger f27813x;

    private BigInteger calculateS() {
        BigInteger bigIntegerCalculateK = SRP6Util.calculateK(this.digest, this.N, this.f27811g);
        return this.B.subtract(this.f27811g.modPow(this.f27813x, this.N).multiply(bigIntegerCalculateK).mod(this.N)).mod(this.N).modPow(this.f27812u.multiply(this.f27813x).add(this.f27810a), this.N);
    }

    public BigInteger calculateSecret(BigInteger bigInteger) throws CryptoException {
        BigInteger bigIntegerValidatePublicValue = SRP6Util.validatePublicValue(this.N, bigInteger);
        this.B = bigIntegerValidatePublicValue;
        this.f27812u = SRP6Util.calculateU(this.digest, this.N, this.A, bigIntegerValidatePublicValue);
        BigInteger bigIntegerCalculateS = calculateS();
        this.S = bigIntegerCalculateS;
        return bigIntegerCalculateS;
    }

    public BigInteger generateClientCredentials(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        this.f27813x = SRP6Util.calculateX(this.digest, this.N, bArr, bArr2, bArr3);
        BigInteger bigIntegerSelectPrivateValue = selectPrivateValue();
        this.f27810a = bigIntegerSelectPrivateValue;
        BigInteger bigIntegerModPow = this.f27811g.modPow(bigIntegerSelectPrivateValue, this.N);
        this.A = bigIntegerModPow;
        return bigIntegerModPow;
    }

    public void init(BigInteger bigInteger, BigInteger bigInteger2, Digest digest, SecureRandom secureRandom) {
        this.N = bigInteger;
        this.f27811g = bigInteger2;
        this.digest = digest;
        this.random = secureRandom;
    }

    public BigInteger selectPrivateValue() {
        return SRP6Util.generatePrivateValue(this.digest, this.N, this.f27811g, this.random);
    }
}
