package org.bouncycastle.bcpg;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;

/* loaded from: classes9.dex */
public class ElGamalPublicBCPGKey extends BCPGObject implements BCPGKey {

    /* renamed from: g, reason: collision with root package name */
    MPInteger f27797g;

    /* renamed from: p, reason: collision with root package name */
    MPInteger f27798p;

    /* renamed from: y, reason: collision with root package name */
    MPInteger f27799y;

    public ElGamalPublicBCPGKey(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
        this.f27798p = new MPInteger(bigInteger);
        this.f27797g = new MPInteger(bigInteger2);
        this.f27799y = new MPInteger(bigInteger3);
    }

    public ElGamalPublicBCPGKey(BCPGInputStream bCPGInputStream) throws IOException {
        this.f27798p = new MPInteger(bCPGInputStream);
        this.f27797g = new MPInteger(bCPGInputStream);
        this.f27799y = new MPInteger(bCPGInputStream);
    }

    @Override // org.bouncycastle.bcpg.BCPGObject
    public void encode(BCPGOutputStream bCPGOutputStream) throws IOException {
        bCPGOutputStream.writeObject(this.f27798p);
        bCPGOutputStream.writeObject(this.f27797g);
        bCPGOutputStream.writeObject(this.f27799y);
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

    public BigInteger getG() {
        return this.f27797g.getValue();
    }

    public BigInteger getP() {
        return this.f27798p.getValue();
    }

    public BigInteger getY() {
        return this.f27799y.getValue();
    }
}
