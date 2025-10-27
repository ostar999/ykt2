package org.bouncycastle.crypto.agreement.srp;

import java.math.BigInteger;
import org.bouncycastle.crypto.Digest;

/* loaded from: classes9.dex */
public class SRP6VerifierGenerator {
    protected BigInteger N;
    protected Digest digest;

    /* renamed from: g, reason: collision with root package name */
    protected BigInteger f27818g;

    public BigInteger generateVerifier(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        return this.f27818g.modPow(SRP6Util.calculateX(this.digest, this.N, bArr, bArr2, bArr3), this.N);
    }

    public void init(BigInteger bigInteger, BigInteger bigInteger2, Digest digest) {
        this.N = bigInteger;
        this.f27818g = bigInteger2;
        this.digest = digest;
    }
}
