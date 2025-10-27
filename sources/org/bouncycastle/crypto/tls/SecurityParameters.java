package org.bouncycastle.crypto.tls;

/* loaded from: classes9.dex */
public class SecurityParameters {
    byte[] clientRandom = null;
    byte[] serverRandom = null;
    byte[] masterSecret = null;

    public byte[] getClientRandom() {
        return this.clientRandom;
    }

    public byte[] getMasterSecret() {
        return this.masterSecret;
    }

    public byte[] getServerRandom() {
        return this.serverRandom;
    }
}
