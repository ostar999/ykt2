package org.bouncycastle.openpgp;

/* loaded from: classes9.dex */
public class PGPOnePassSignatureList {
    PGPOnePassSignature[] sigs;

    public PGPOnePassSignatureList(PGPOnePassSignature pGPOnePassSignature) {
        this.sigs = new PGPOnePassSignature[]{pGPOnePassSignature};
    }

    public PGPOnePassSignatureList(PGPOnePassSignature[] pGPOnePassSignatureArr) {
        PGPOnePassSignature[] pGPOnePassSignatureArr2 = new PGPOnePassSignature[pGPOnePassSignatureArr.length];
        this.sigs = pGPOnePassSignatureArr2;
        System.arraycopy(pGPOnePassSignatureArr, 0, pGPOnePassSignatureArr2, 0, pGPOnePassSignatureArr.length);
    }

    public PGPOnePassSignature get(int i2) {
        return this.sigs[i2];
    }

    public boolean isEmpty() {
        return this.sigs.length == 0;
    }

    public int size() {
        return this.sigs.length;
    }
}
