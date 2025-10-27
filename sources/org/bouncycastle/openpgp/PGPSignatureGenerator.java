package org.bouncycastle.openpgp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
import org.bouncycastle.bcpg.SignatureSubpacket;
import org.bouncycastle.bcpg.UserAttributeSubpacket;
import org.bouncycastle.bcpg.sig.IssuerKeyID;
import org.bouncycastle.bcpg.sig.SignatureCreationTime;
import org.bouncycastle.util.Strings;

/* loaded from: classes9.dex */
public class PGPSignatureGenerator {
    private MessageDigest dig;
    private int hashAlgorithm;
    SignatureSubpacket[] hashed;
    private int keyAlgorithm;
    private byte lastb;
    private PGPPrivateKey privKey;
    private Signature sig;
    private int signatureType;
    SignatureSubpacket[] unhashed;

    public PGPSignatureGenerator(int i2, int i3, String str) throws NoSuchAlgorithmException, NoSuchProviderException, PGPException {
        this(i2, str, i3, str);
    }

    public PGPSignatureGenerator(int i2, int i3, Provider provider) throws NoSuchAlgorithmException, PGPException {
        this(i2, provider, i3, provider);
    }

    public PGPSignatureGenerator(int i2, String str, int i3, String str2) throws NoSuchAlgorithmException, NoSuchProviderException, PGPException {
        this(i2, PGPUtil.getProvider(str), i3, PGPUtil.getProvider(str2));
    }

    public PGPSignatureGenerator(int i2, Provider provider, int i3, Provider provider2) throws NoSuchAlgorithmException, PGPException {
        this.unhashed = new SignatureSubpacket[0];
        this.hashed = new SignatureSubpacket[0];
        this.keyAlgorithm = i2;
        this.hashAlgorithm = i3;
        this.dig = PGPUtil.getDigestInstance(PGPUtil.getDigestName(i3), provider2);
        this.sig = Signature.getInstance(PGPUtil.getSignatureName(i2, i3), provider);
    }

    private byte[] getEncodedPublicKey(PGPPublicKey pGPPublicKey) throws PGPException {
        try {
            return pGPPublicKey.publicPk.getEncodedContents();
        } catch (IOException e2) {
            throw new PGPException("exception preparing key.", e2);
        }
    }

    private SignatureSubpacket[] insertSubpacket(SignatureSubpacket[] signatureSubpacketArr, SignatureSubpacket signatureSubpacket) {
        SignatureSubpacket[] signatureSubpacketArr2 = new SignatureSubpacket[signatureSubpacketArr.length + 1];
        signatureSubpacketArr2[0] = signatureSubpacket;
        System.arraycopy(signatureSubpacketArr, 0, signatureSubpacketArr2, 1, signatureSubpacketArr.length);
        return signatureSubpacketArr2;
    }

    private boolean packetPresent(SignatureSubpacket[] signatureSubpacketArr, int i2) {
        for (int i3 = 0; i3 != signatureSubpacketArr.length; i3++) {
            if (signatureSubpacketArr[i3].getType() == i2) {
                return true;
            }
        }
        return false;
    }

    private void updateWithIdData(int i2, byte[] bArr) throws SignatureException {
        update((byte) i2);
        update((byte) (bArr.length >> 24));
        update((byte) (bArr.length >> 16));
        update((byte) (bArr.length >> 8));
        update((byte) bArr.length);
        update(bArr);
    }

    private void updateWithPublicKey(PGPPublicKey pGPPublicKey) throws SignatureException, PGPException {
        byte[] encodedPublicKey = getEncodedPublicKey(pGPPublicKey);
        update((byte) -103);
        update((byte) (encodedPublicKey.length >> 8));
        update((byte) encodedPublicKey.length);
        update(encodedPublicKey);
    }

    public PGPSignature generate() throws SignatureException, IOException, PGPException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        SignatureSubpacket[] signatureSubpacketArrInsertSubpacket = !packetPresent(this.hashed, 2) ? insertSubpacket(this.hashed, new SignatureCreationTime(false, new Date())) : this.hashed;
        SignatureSubpacket[] signatureSubpacketArrInsertSubpacket2 = (packetPresent(this.hashed, 16) || packetPresent(this.unhashed, 16)) ? this.unhashed : insertSubpacket(this.unhashed, new IssuerKeyID(false, this.privKey.getKeyID()));
        byte b3 = (byte) 4;
        try {
            byteArrayOutputStream.write(b3);
            byteArrayOutputStream.write((byte) this.signatureType);
            byteArrayOutputStream.write((byte) this.keyAlgorithm);
            byteArrayOutputStream.write((byte) this.hashAlgorithm);
            ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
            for (int i2 = 0; i2 != signatureSubpacketArrInsertSubpacket.length; i2++) {
                signatureSubpacketArrInsertSubpacket[i2].encode(byteArrayOutputStream2);
            }
            byte[] byteArray = byteArrayOutputStream2.toByteArray();
            byteArrayOutputStream.write((byte) (byteArray.length >> 8));
            byteArrayOutputStream.write((byte) byteArray.length);
            byteArrayOutputStream.write(byteArray);
            byte[] byteArray2 = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.write(b3);
            byteArrayOutputStream.write(-1);
            byteArrayOutputStream.write((byte) (byteArray2.length >> 24));
            byteArrayOutputStream.write((byte) (byteArray2.length >> 16));
            byteArrayOutputStream.write((byte) (byteArray2.length >> 8));
            byteArrayOutputStream.write((byte) byteArray2.length);
            byte[] byteArray3 = byteArrayOutputStream.toByteArray();
            this.sig.update(byteArray3);
            this.dig.update(byteArray3);
            int i3 = this.keyAlgorithm;
            MPInteger[] mPIntegerArrDsaSigToMpi = (i3 == 3 || i3 == 1) ? new MPInteger[]{new MPInteger(new BigInteger(1, this.sig.sign()))} : PGPUtil.dsaSigToMpi(this.sig.sign());
            byte[] bArrDigest = this.dig.digest();
            return new PGPSignature(new SignaturePacket(this.signatureType, this.privKey.getKeyID(), this.keyAlgorithm, this.hashAlgorithm, signatureSubpacketArrInsertSubpacket, signatureSubpacketArrInsertSubpacket2, new byte[]{bArrDigest[0], bArrDigest[1]}, mPIntegerArrDsaSigToMpi));
        } catch (IOException e2) {
            throw new PGPException("exception encoding hashed data.", e2);
        }
    }

    public PGPSignature generateCertification(String str, PGPPublicKey pGPPublicKey) throws SignatureException, PGPException {
        updateWithPublicKey(pGPPublicKey);
        updateWithIdData(180, Strings.toByteArray(str));
        return generate();
    }

    public PGPSignature generateCertification(PGPPublicKey pGPPublicKey) throws SignatureException, PGPException {
        updateWithPublicKey(pGPPublicKey);
        return generate();
    }

    public PGPSignature generateCertification(PGPPublicKey pGPPublicKey, PGPPublicKey pGPPublicKey2) throws SignatureException, PGPException {
        updateWithPublicKey(pGPPublicKey);
        updateWithPublicKey(pGPPublicKey2);
        return generate();
    }

    public PGPSignature generateCertification(PGPUserAttributeSubpacketVector pGPUserAttributeSubpacketVector, PGPPublicKey pGPPublicKey) throws SignatureException, PGPException {
        updateWithPublicKey(pGPPublicKey);
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            UserAttributeSubpacket[] subpacketArray = pGPUserAttributeSubpacketVector.toSubpacketArray();
            for (int i2 = 0; i2 != subpacketArray.length; i2++) {
                subpacketArray[i2].encode(byteArrayOutputStream);
            }
            updateWithIdData(209, byteArrayOutputStream.toByteArray());
            return generate();
        } catch (IOException e2) {
            throw new PGPException("cannot encode subpacket array", e2);
        }
    }

    public PGPOnePassSignature generateOnePassVersion(boolean z2) throws PGPException {
        return new PGPOnePassSignature(new OnePassSignaturePacket(this.signatureType, this.hashAlgorithm, this.keyAlgorithm, this.privKey.getKeyID(), z2));
    }

    public void initSign(int i2, PGPPrivateKey pGPPrivateKey) throws PGPException {
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

    public void setHashedSubpackets(PGPSignatureSubpacketVector pGPSignatureSubpacketVector) {
        if (pGPSignatureSubpacketVector == null) {
            this.hashed = new SignatureSubpacket[0];
        } else {
            this.hashed = pGPSignatureSubpacketVector.toSubpacketArray();
        }
    }

    public void setUnhashedSubpackets(PGPSignatureSubpacketVector pGPSignatureSubpacketVector) {
        if (pGPSignatureSubpacketVector == null) {
            this.unhashed = new SignatureSubpacket[0];
        } else {
            this.unhashed = pGPSignatureSubpacketVector.toSubpacketArray();
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
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.openpgp.PGPSignatureGenerator.update(byte):void");
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
