package org.bouncycastle.openpgp;

import java.security.PrivateKey;

/* loaded from: classes9.dex */
public class PGPPrivateKey {
    private long keyID;
    private PrivateKey privateKey;

    public PGPPrivateKey(PrivateKey privateKey, long j2) {
        this.privateKey = privateKey;
        this.keyID = j2;
    }

    public PrivateKey getKey() {
        return this.privateKey;
    }

    public long getKeyID() {
        return this.keyID;
    }
}
