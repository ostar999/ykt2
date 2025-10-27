package org.bouncycastle.bcpg;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;

/* loaded from: classes9.dex */
public class DSASecretBCPGKey extends BCPGObject implements BCPGKey {

    /* renamed from: x, reason: collision with root package name */
    MPInteger f27796x;

    public DSASecretBCPGKey(BigInteger bigInteger) {
        this.f27796x = new MPInteger(bigInteger);
    }

    public DSASecretBCPGKey(BCPGInputStream bCPGInputStream) throws IOException {
        this.f27796x = new MPInteger(bCPGInputStream);
    }

    @Override // org.bouncycastle.bcpg.BCPGObject
    public void encode(BCPGOutputStream bCPGOutputStream) throws IOException {
        bCPGOutputStream.writeObject(this.f27796x);
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

    public BigInteger getX() {
        return this.f27796x.getValue();
    }
}
