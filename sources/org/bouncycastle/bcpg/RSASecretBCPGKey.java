package org.bouncycastle.bcpg;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;

/* loaded from: classes9.dex */
public class RSASecretBCPGKey extends BCPGObject implements BCPGKey {
    BigInteger crt;

    /* renamed from: d, reason: collision with root package name */
    MPInteger f27803d;
    BigInteger expP;
    BigInteger expQ;

    /* renamed from: p, reason: collision with root package name */
    MPInteger f27804p;

    /* renamed from: q, reason: collision with root package name */
    MPInteger f27805q;

    /* renamed from: u, reason: collision with root package name */
    MPInteger f27806u;

    public RSASecretBCPGKey(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
        int iCompareTo = bigInteger2.compareTo(bigInteger3);
        if (iCompareTo >= 0) {
            if (iCompareTo == 0) {
                throw new IllegalArgumentException("p and q cannot be equal");
            }
            bigInteger3 = bigInteger2;
            bigInteger2 = bigInteger3;
        }
        this.f27803d = new MPInteger(bigInteger);
        this.f27804p = new MPInteger(bigInteger2);
        this.f27805q = new MPInteger(bigInteger3);
        this.f27806u = new MPInteger(bigInteger2.modInverse(bigInteger3));
        this.expP = bigInteger.remainder(bigInteger2.subtract(BigInteger.valueOf(1L)));
        this.expQ = bigInteger.remainder(bigInteger3.subtract(BigInteger.valueOf(1L)));
        this.crt = bigInteger3.modInverse(bigInteger2);
    }

    public RSASecretBCPGKey(BCPGInputStream bCPGInputStream) throws IOException {
        this.f27803d = new MPInteger(bCPGInputStream);
        this.f27804p = new MPInteger(bCPGInputStream);
        this.f27805q = new MPInteger(bCPGInputStream);
        this.f27806u = new MPInteger(bCPGInputStream);
        this.expP = this.f27803d.getValue().remainder(this.f27804p.getValue().subtract(BigInteger.valueOf(1L)));
        this.expQ = this.f27803d.getValue().remainder(this.f27805q.getValue().subtract(BigInteger.valueOf(1L)));
        this.crt = this.f27805q.getValue().modInverse(this.f27804p.getValue());
    }

    @Override // org.bouncycastle.bcpg.BCPGObject
    public void encode(BCPGOutputStream bCPGOutputStream) throws IOException {
        bCPGOutputStream.writeObject(this.f27803d);
        bCPGOutputStream.writeObject(this.f27804p);
        bCPGOutputStream.writeObject(this.f27805q);
        bCPGOutputStream.writeObject(this.f27806u);
    }

    public BigInteger getCrtCoefficient() {
        return this.crt;
    }

    @Override // org.bouncycastle.bcpg.BCPGObject, org.bouncycastle.bcpg.BCPGKey
    public byte[] getEncoded() {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            new BCPGOutputStream(byteArrayOutputStream).writeObject(this);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException unused) {
            return null;
        }
    }

    @Override // org.bouncycastle.bcpg.BCPGKey
    public String getFormat() {
        return "PGP";
    }

    public BigInteger getModulus() {
        return this.f27804p.getValue().multiply(this.f27805q.getValue());
    }

    public BigInteger getPrimeExponentP() {
        return this.expP;
    }

    public BigInteger getPrimeExponentQ() {
        return this.expQ;
    }

    public BigInteger getPrimeP() {
        return this.f27804p.getValue();
    }

    public BigInteger getPrimeQ() {
        return this.f27805q.getValue();
    }

    public BigInteger getPrivateExponent() {
        return this.f27803d.getValue();
    }
}
