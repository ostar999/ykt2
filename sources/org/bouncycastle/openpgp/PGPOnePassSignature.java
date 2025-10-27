package org.bouncycastle.openpgp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.Signature;
import java.security.SignatureException;
import org.bouncycastle.bcpg.BCPGInputStream;
import org.bouncycastle.bcpg.BCPGOutputStream;
import org.bouncycastle.bcpg.OnePassSignaturePacket;

/* loaded from: classes9.dex */
public class PGPOnePassSignature {
    private byte lastb;
    private Signature sig;
    private OnePassSignaturePacket sigPack;
    private int signatureType;

    public PGPOnePassSignature(BCPGInputStream bCPGInputStream) throws IOException, PGPException {
        this((OnePassSignaturePacket) bCPGInputStream.readPacket());
    }

    public PGPOnePassSignature(OnePassSignaturePacket onePassSignaturePacket) throws PGPException {
        this.sigPack = onePassSignaturePacket;
        this.signatureType = onePassSignaturePacket.getSignatureType();
    }

    public void encode(OutputStream outputStream) throws IOException {
        (outputStream instanceof BCPGOutputStream ? (BCPGOutputStream) outputStream : new BCPGOutputStream(outputStream)).writePacket(this.sigPack);
    }

    public byte[] getEncoded() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        encode(byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public int getHashAlgorithm() {
        return this.sigPack.getHashAlgorithm();
    }

    public int getKeyAlgorithm() {
        return this.sigPack.getKeyAlgorithm();
    }

    public long getKeyID() {
        return this.sigPack.getKeyID();
    }

    public int getSignatureType() {
        return this.sigPack.getSignatureType();
    }

    public void initVerify(PGPPublicKey pGPPublicKey, String str) throws NoSuchAlgorithmException, InvalidKeyException, PGPException, NoSuchProviderException {
        initVerify(pGPPublicKey, PGPUtil.getProvider(str));
    }

    public void initVerify(PGPPublicKey pGPPublicKey, Provider provider) throws NoSuchAlgorithmException, InvalidKeyException, PGPException {
        this.lastb = (byte) 0;
        try {
            Signature signature = Signature.getInstance(PGPUtil.getSignatureName(this.sigPack.getKeyAlgorithm(), this.sigPack.getHashAlgorithm()), provider);
            this.sig = signature;
            try {
                signature.initVerify(pGPPublicKey.getKey(provider));
            } catch (InvalidKeyException e2) {
                throw new PGPException("invalid key.", e2);
            }
        } catch (Exception e3) {
            throw new PGPException("can't set up signature object.", e3);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:6:0x000b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void update(byte r4) throws java.security.SignatureException {
        /*
            r3 = this;
            int r0 = r3.signatureType
            r1 = 1
            if (r0 != r1) goto L25
            r0 = 10
            r1 = 13
            if (r4 != r1) goto L16
        Lb:
            java.security.Signature r2 = r3.sig
            r2.update(r1)
            java.security.Signature r1 = r3.sig
            r1.update(r0)
            goto L22
        L16:
            if (r4 != r0) goto L1d
            byte r2 = r3.lastb
            if (r2 == r1) goto L22
            goto Lb
        L1d:
            java.security.Signature r0 = r3.sig
            r0.update(r4)
        L22:
            r3.lastb = r4
            goto L2a
        L25:
            java.security.Signature r0 = r3.sig
            r0.update(r4)
        L2a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.openpgp.PGPOnePassSignature.update(byte):void");
    }

    public void update(byte[] bArr) throws SignatureException {
        if (this.signatureType != 1) {
            this.sig.update(bArr);
            return;
        }
        for (int i2 = 0; i2 != bArr.length; i2++) {
            update(bArr[i2]);
        }
    }

    public void update(byte[] bArr, int i2, int i3) throws SignatureException {
        if (this.signatureType != 1) {
            this.sig.update(bArr, i2, i3);
            return;
        }
        int i4 = i3 + i2;
        while (i2 != i4) {
            update(bArr[i2]);
            i2++;
        }
    }

    public boolean verify(PGPSignature pGPSignature) throws SignatureException, PGPException {
        this.sig.update(pGPSignature.getSignatureTrailer());
        return this.sig.verify(pGPSignature.getSignature());
    }
}
