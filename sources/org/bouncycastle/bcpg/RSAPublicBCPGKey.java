package org.bouncycastle.bcpg;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;

/* loaded from: classes9.dex */
public class RSAPublicBCPGKey extends BCPGObject implements BCPGKey {

    /* renamed from: e, reason: collision with root package name */
    MPInteger f27801e;

    /* renamed from: n, reason: collision with root package name */
    MPInteger f27802n;

    public RSAPublicBCPGKey(BigInteger bigInteger, BigInteger bigInteger2) {
        this.f27802n = new MPInteger(bigInteger);
        this.f27801e = new MPInteger(bigInteger2);
    }

    public RSAPublicBCPGKey(BCPGInputStream bCPGInputStream) throws IOException {
        this.f27802n = new MPInteger(bCPGInputStream);
        this.f27801e = new MPInteger(bCPGInputStream);
    }

    @Override // org.bouncycastle.bcpg.BCPGObject
    public void encode(BCPGOutputStream bCPGOutputStream) throws IOException {
        bCPGOutputStream.writeObject(this.f27802n);
        bCPGOutputStream.writeObject(this.f27801e);
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
        return this.f27802n.getValue();
    }

    public BigInteger getPublicExponent() {
        return this.f27801e.getValue();
    }
}
