package org.bouncycastle.openpgp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Date;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.DERInteger;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.bcpg.BCPGInputStream;
import org.bouncycastle.bcpg.BCPGOutputStream;
import org.bouncycastle.bcpg.MPInteger;
import org.bouncycastle.bcpg.SignaturePacket;
import org.bouncycastle.bcpg.SignatureSubpacket;
import org.bouncycastle.bcpg.TrustPacket;
import org.bouncycastle.bcpg.UserAttributeSubpacket;
import org.bouncycastle.util.BigIntegers;
import org.bouncycastle.util.Strings;

/* loaded from: classes9.dex */
public class PGPSignature {
    public static final int BINARY_DOCUMENT = 0;
    public static final int CANONICAL_TEXT_DOCUMENT = 1;
    public static final int CASUAL_CERTIFICATION = 18;
    public static final int CERTIFICATION_REVOCATION = 48;
    public static final int DEFAULT_CERTIFICATION = 16;
    public static final int DIRECT_KEY = 31;
    public static final int KEY_REVOCATION = 32;
    public static final int NO_CERTIFICATION = 17;
    public static final int POSITIVE_CERTIFICATION = 19;
    public static final int PRIMARYKEY_BINDING = 25;
    public static final int STAND_ALONE = 2;
    public static final int SUBKEY_BINDING = 24;
    public static final int SUBKEY_REVOCATION = 40;
    public static final int TIMESTAMP = 64;
    private byte lastb;
    private Signature sig;
    private SignaturePacket sigPck;
    private int signatureType;
    private TrustPacket trustPck;

    public PGPSignature(BCPGInputStream bCPGInputStream) throws IOException, PGPException {
        this((SignaturePacket) bCPGInputStream.readPacket());
    }

    public PGPSignature(SignaturePacket signaturePacket) throws PGPException {
        this.sigPck = signaturePacket;
        this.signatureType = signaturePacket.getSignatureType();
        this.trustPck = null;
    }

    public PGPSignature(SignaturePacket signaturePacket, TrustPacket trustPacket) throws PGPException {
        this(signaturePacket);
        this.trustPck = trustPacket;
    }

    private PGPSignatureSubpacketVector createSubpacketVector(SignatureSubpacket[] signatureSubpacketArr) {
        if (signatureSubpacketArr != null) {
            return new PGPSignatureSubpacketVector(signatureSubpacketArr);
        }
        return null;
    }

    private byte[] getEncodedPublicKey(PGPPublicKey pGPPublicKey) throws PGPException {
        try {
            return pGPPublicKey.publicPk.getEncodedContents();
        } catch (IOException e2) {
            throw new PGPException("exception preparing key.", e2);
        }
    }

    private void getSig(Provider provider) throws PGPException {
        try {
            this.sig = Signature.getInstance(PGPUtil.getSignatureName(this.sigPck.getKeyAlgorithm(), this.sigPck.getHashAlgorithm()), provider);
        } catch (Exception e2) {
            throw new PGPException("can't set up signature object.", e2);
        }
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

    public void encode(OutputStream outputStream) throws IOException {
        BCPGOutputStream bCPGOutputStream = outputStream instanceof BCPGOutputStream ? (BCPGOutputStream) outputStream : new BCPGOutputStream(outputStream);
        bCPGOutputStream.writePacket(this.sigPck);
        TrustPacket trustPacket = this.trustPck;
        if (trustPacket != null) {
            bCPGOutputStream.writePacket(trustPacket);
        }
    }

    public Date getCreationTime() {
        return new Date(this.sigPck.getCreationTime());
    }

    public byte[] getEncoded() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        encode(byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public int getHashAlgorithm() {
        return this.sigPck.getHashAlgorithm();
    }

    public PGPSignatureSubpacketVector getHashedSubPackets() {
        return createSubpacketVector(this.sigPck.getHashedSubPackets());
    }

    public int getKeyAlgorithm() {
        return this.sigPck.getKeyAlgorithm();
    }

    public long getKeyID() {
        return this.sigPck.getKeyID();
    }

    public byte[] getSignature() throws PGPException {
        MPInteger[] signature = this.sigPck.getSignature();
        if (signature == null) {
            return this.sigPck.getSignatureBytes();
        }
        if (signature.length == 1) {
            return BigIntegers.asUnsignedByteArray(signature[0].getValue());
        }
        try {
            ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
            aSN1EncodableVector.add(new DERInteger(signature[0].getValue()));
            aSN1EncodableVector.add(new DERInteger(signature[1].getValue()));
            return new DERSequence(aSN1EncodableVector).getEncoded();
        } catch (IOException e2) {
            throw new PGPException("exception encoding DSA sig.", e2);
        }
    }

    public byte[] getSignatureTrailer() {
        return this.sigPck.getSignatureTrailer();
    }

    public int getSignatureType() {
        return this.sigPck.getSignatureType();
    }

    public PGPSignatureSubpacketVector getUnhashedSubPackets() {
        return createSubpacketVector(this.sigPck.getUnhashedSubPackets());
    }

    public int getVersion() {
        return this.sigPck.getVersion();
    }

    public boolean hasSubpackets() {
        return (this.sigPck.getHashedSubPackets() == null && this.sigPck.getUnhashedSubPackets() == null) ? false : true;
    }

    public void initVerify(PGPPublicKey pGPPublicKey, String str) throws InvalidKeyException, PGPException, NoSuchProviderException {
        initVerify(pGPPublicKey, PGPUtil.getProvider(str));
    }

    public void initVerify(PGPPublicKey pGPPublicKey, Provider provider) throws InvalidKeyException, PGPException {
        if (this.sig == null) {
            getSig(provider);
        }
        try {
            this.sig.initVerify(pGPPublicKey.getKey(provider));
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
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.openpgp.PGPSignature.update(byte):void");
    }

    public void update(byte[] bArr) throws SignatureException {
        update(bArr, 0, bArr.length);
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

    public boolean verify() throws SignatureException, PGPException {
        this.sig.update(getSignatureTrailer());
        return this.sig.verify(getSignature());
    }

    public boolean verifyCertification(String str, PGPPublicKey pGPPublicKey) throws SignatureException, PGPException {
        updateWithPublicKey(pGPPublicKey);
        updateWithIdData(180, Strings.toByteArray(str));
        update(this.sigPck.getSignatureTrailer());
        return this.sig.verify(getSignature());
    }

    public boolean verifyCertification(PGPPublicKey pGPPublicKey) throws SignatureException, PGPException {
        if (getSignatureType() != 32 && getSignatureType() != 40) {
            throw new IllegalStateException("signature is not a key signature");
        }
        updateWithPublicKey(pGPPublicKey);
        update(this.sigPck.getSignatureTrailer());
        return this.sig.verify(getSignature());
    }

    public boolean verifyCertification(PGPPublicKey pGPPublicKey, PGPPublicKey pGPPublicKey2) throws SignatureException, PGPException {
        updateWithPublicKey(pGPPublicKey);
        updateWithPublicKey(pGPPublicKey2);
        update(this.sigPck.getSignatureTrailer());
        return this.sig.verify(getSignature());
    }

    public boolean verifyCertification(PGPUserAttributeSubpacketVector pGPUserAttributeSubpacketVector, PGPPublicKey pGPPublicKey) throws SignatureException, PGPException {
        updateWithPublicKey(pGPPublicKey);
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            UserAttributeSubpacket[] subpacketArray = pGPUserAttributeSubpacketVector.toSubpacketArray();
            for (int i2 = 0; i2 != subpacketArray.length; i2++) {
                subpacketArray[i2].encode(byteArrayOutputStream);
            }
            updateWithIdData(209, byteArrayOutputStream.toByteArray());
            update(this.sigPck.getSignatureTrailer());
            return this.sig.verify(getSignature());
        } catch (IOException e2) {
            throw new PGPException("cannot encode subpacket array", e2);
        }
    }
}
