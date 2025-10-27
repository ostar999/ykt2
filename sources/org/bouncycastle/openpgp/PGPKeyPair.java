package org.bouncycastle.openpgp;

import java.security.KeyPair;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;

/* loaded from: classes9.dex */
public class PGPKeyPair {
    PGPPrivateKey priv;

    /* renamed from: pub, reason: collision with root package name */
    PGPPublicKey f27945pub;

    public PGPKeyPair(int i2, KeyPair keyPair, Date date) throws PGPException {
        this(i2, keyPair.getPublic(), keyPair.getPrivate(), date);
    }

    public PGPKeyPair(int i2, KeyPair keyPair, Date date, String str) throws PGPException, NoSuchProviderException {
        this(i2, keyPair.getPublic(), keyPair.getPrivate(), date, str);
    }

    public PGPKeyPair(int i2, PublicKey publicKey, PrivateKey privateKey, Date date) throws PGPException {
        PGPPublicKey pGPPublicKey = new PGPPublicKey(i2, publicKey, date);
        this.f27945pub = pGPPublicKey;
        this.priv = new PGPPrivateKey(privateKey, pGPPublicKey.getKeyID());
    }

    public PGPKeyPair(int i2, PublicKey publicKey, PrivateKey privateKey, Date date, String str) throws PGPException, NoSuchProviderException {
        this(i2, publicKey, privateKey, date);
    }

    public PGPKeyPair(PGPPublicKey pGPPublicKey, PGPPrivateKey pGPPrivateKey) {
        this.f27945pub = pGPPublicKey;
        this.priv = pGPPrivateKey;
    }

    public long getKeyID() {
        return this.f27945pub.getKeyID();
    }

    public PGPPrivateKey getPrivateKey() {
        return this.priv;
    }

    public PGPPublicKey getPublicKey() {
        return this.f27945pub;
    }
}
