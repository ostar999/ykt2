package org.bouncycastle.openpgp;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Date;
import org.bouncycastle.bcpg.MPInteger;
import org.bouncycastle.bcpg.OnePassSignaturePacket;
import org.bouncycastle.bcpg.SignaturePacket;

/* loaded from: classes9.dex */
public class PGPV3SignatureGenerator {
    private MessageDigest dig;
    private int hashAlgorithm;
    private int keyAlgorithm;
    private byte lastb;
    private PGPPrivateKey privKey;
    private Signature sig;
    private int signatureType;

    public PGPV3SignatureGenerator(int i2, int i3, String str) throws NoSuchAlgorithmException, NoSuchProviderException, PGPException {
        this(i2, i3, PGPUtil.getProvider(str));
    }

    public PGPV3SignatureGenerator(int i2, int i3, Provider provider) throws NoSuchAlgorithmException, PGPException {
        this.keyAlgorithm = i2;
        this.hashAlgorithm = i3;
        this.dig = PGPUtil.getDigestInstance(PGPUtil.getDigestName(i3), provider);
        this.sig = Signature.getInstance(PGPUtil.getSignatureName(i2, i3), provider);
    }

    public PGPSignature generate() throws SignatureException, PGPException {
        long time = new Date().getTime() / 1000;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(this.signatureType);
        byteArrayOutputStream.write((byte) (time >> 24));
        byteArrayOutputStream.write((byte) (time >> 16));
        byteArrayOutputStream.write((byte) (time >> 8));
        byteArrayOutputStream.write((byte) time);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        this.sig.update(byteArray);
        this.dig.update(byteArray);
        int i2 = this.keyAlgorithm;
        MPInteger[] mPIntegerArrDsaSigToMpi = (i2 == 3 || i2 == 1) ? new MPInteger[]{new MPInteger(new BigInteger(1, this.sig.sign()))} : PGPUtil.dsaSigToMpi(this.sig.sign());
        byte[] bArrDigest = this.dig.digest();
        return new PGPSignature(new SignaturePacket(3, this.signatureType, this.privKey.getKeyID(), this.keyAlgorithm, this.hashAlgorithm, time * 1000, new byte[]{bArrDigest[0], bArrDigest[1]}, mPIntegerArrDsaSigToMpi));
    }

    public PGPOnePassSignature generateOnePassVersion(boolean z2) throws PGPException {
        return new PGPOnePassSignature(new OnePassSignaturePacket(this.signatureType, this.hashAlgorithm, this.keyAlgorithm, this.privKey.getKeyID(), z2));
    }

    public void initSign(int i2, PGPPrivateKey pGPPrivateKey) throws InvalidKeyException, PGPException {
        initSign(i2, pGPPrivateKey, null);
    }

    public void initSign(int i2, PGPPrivateKey pGPPrivateKey, SecureRandom secureRandom) throws InvalidKeyException, PGPException {
        this.privKey = pGPPrivateKey;
        this.signatureType = i2;
        try {
            if (secureRandom == null) {
                this.sig.initSign(pGPPrivateKey.getKey());
            } else {
                this.sig.initSign(pGPPrivateKey.getKey(), secureRandom);
            }
            this.dig.reset();
            this.lastb = (byte) 0;
        } catch (InvalidKeyException e2) {
            throw new PGPException("invalid key.", e2);
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
            if (r0 != r1) goto L34
            r0 = 10
            r1 = 13
            if (r4 != r1) goto L20
        Lb:
            java.security.Signature r2 = r3.sig
            r2.update(r1)
            java.security.Signature r2 = r3.sig
            r2.update(r0)
            java.security.MessageDigest r2 = r3.dig
            r2.update(r1)
            java.security.MessageDigest r1 = r3.dig
            r1.update(r0)
            goto L31
        L20:
            if (r4 != r0) goto L27
            byte r2 = r3.lastb
            if (r2 == r1) goto L31
            goto Lb
        L27:
            java.security.Signature r0 = r3.sig
            r0.update(r4)
            java.security.MessageDigest r0 = r3.dig
            r0.update(r4)
        L31:
            r3.lastb = r4
            goto L3e
        L34:
            java.security.Signature r0 = r3.sig
            r0.update(r4)
            java.security.MessageDigest r0 = r3.dig
            r0.update(r4)
        L3e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.openpgp.PGPV3SignatureGenerator.update(byte):void");
    }

    public void update(byte[] bArr) throws SignatureException {
        update(bArr, 0, bArr.length);
    }

    public void update(byte[] bArr, int i2, int i3) throws SignatureException {
        if (this.signatureType != 1) {
            this.sig.update(bArr, i2, i3);
            this.dig.update(bArr, i2, i3);
        } else {
            int i4 = i3 + i2;
            while (i2 != i4) {
                update(bArr[i2]);
                i2++;
            }
        }
    }
}
