package org.bouncycastle.openpgp;

/* loaded from: classes9.dex */
public class PGPSignatureList {
    PGPSignature[] sigs;

    public PGPSignatureList(PGPSignature pGPSignature) {
        this.sigs = new PGPSignature[]{pGPSignature};
    }

    public PGPSignatureList(PGPSignature[] pGPSignatureArr) {
        PGPSignature[] pGPSignatureArr2 = new PGPSignature[pGPSignatureArr.length];
        this.sigs = pGPSignatureArr2;
        System.arraycopy(pGPSignatureArr, 0, pGPSignatureArr2, 0, pGPSignatureArr.length);
    }

    public PGPSignature get(int i2) {
        return this.sigs[i2];
    }

    public boolean isEmpty() {
        return this.sigs.length == 0;
    }

    public int size() {
        return this.sigs.length;
    }
}
