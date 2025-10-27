package org.bouncycastle.bcpg;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;

/* loaded from: classes9.dex */
public class DSAPublicBCPGKey extends BCPGObject implements BCPGKey {

    /* renamed from: g, reason: collision with root package name */
    MPInteger f27792g;

    /* renamed from: p, reason: collision with root package name */
    MPInteger f27793p;

    /* renamed from: q, reason: collision with root package name */
    MPInteger f27794q;

    /* renamed from: y, reason: collision with root package name */
    MPInteger f27795y;

    public DSAPublicBCPGKey(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4) {
        this.f27793p = new MPInteger(bigInteger);
        this.f27794q = new MPInteger(bigInteger2);
        this.f27792g = new MPInteger(bigInteger3);
        this.f27795y = new MPInteger(bigInteger4);
    }

    public DSAPublicBCPGKey(BCPGInputStream bCPGInputStream) throws IOException {
        this.f27793p = new MPInteger(bCPGInputStream);
        this.f27794q = new MPInteger(bCPGInputStream);
        this.f27792g = new MPInteger(bCPGInputStream);
        this.f27795y = new MPInteger(bCPGInputStream);
    }

    @Override // org.bouncycastle.bcpg.BCPGObject
    public void encode(BCPGOutputStream bCPGOutputStream) throws IOException {
        bCPGOutputStream.writeObject(this.f27793p);
        bCPGOutputStream.writeObject(this.f27794q);
        bCPGOutputStream.writeObject(this.f27792g);
        bCPGOutputStream.writeObject(this.f27795y);
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
        return this.f27792g.getValue();
    }

    public BigInteger getP() {
        return this.f27793p.getValue();
    }

    public BigInteger getQ() {
        return this.f27794q.getValue();
    }

    public BigInteger getY() {
        return this.f27795y.getValue();
    }
}
