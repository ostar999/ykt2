package org.bouncycastle.crypto.params;

import org.bouncycastle.crypto.CipherParameters;

/* loaded from: classes9.dex */
public class AEADParameters implements CipherParameters {
    private byte[] associatedText;
    private KeyParameter key;
    private int macSize;
    private byte[] nonce;

    public AEADParameters(KeyParameter keyParameter, int i2, byte[] bArr, byte[] bArr2) {
        this.key = keyParameter;
        this.nonce = bArr;
        this.macSize = i2;
        this.associatedText = bArr2;
    }

    public byte[] getAssociatedText() {
        return this.associatedText;
    }

    public KeyParameter getKey() {
        return this.key;
    }

    public int getMacSize() {
        return this.macSize;
    }

    public byte[] getNonce() {
        return this.nonce;
    }
}
