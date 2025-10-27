package org.bouncycastle.openpgp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.PublicKey;
import java.security.interfaces.DSAParams;
import java.security.interfaces.DSAPublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.DSAPublicKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.bouncycastle.bcpg.BCPGKey;
import org.bouncycastle.bcpg.BCPGOutputStream;
import org.bouncycastle.bcpg.ContainedPacket;
import org.bouncycastle.bcpg.DSAPublicBCPGKey;
import org.bouncycastle.bcpg.ElGamalPublicBCPGKey;
import org.bouncycastle.bcpg.MPInteger;
import org.bouncycastle.bcpg.PublicKeyAlgorithmTags;
import org.bouncycastle.bcpg.PublicKeyPacket;
import org.bouncycastle.bcpg.RSAPublicBCPGKey;
import org.bouncycastle.bcpg.TrustPacket;
import org.bouncycastle.bcpg.UserAttributePacket;
import org.bouncycastle.bcpg.UserIDPacket;
import org.bouncycastle.jce.interfaces.ElGamalPublicKey;
import org.bouncycastle.jce.spec.ElGamalParameterSpec;
import org.bouncycastle.jce.spec.ElGamalPublicKeySpec;
import org.bouncycastle.util.Arrays;

/* loaded from: classes9.dex */
public class PGPPublicKey implements PublicKeyAlgorithmTags {
    private static final int[] MASTER_KEY_CERTIFICATION_TYPES = {19, 18, 17, 16};
    private byte[] fingerprint;
    List idSigs;
    List idTrusts;
    List ids;
    private long keyID;
    List keySigs;
    private int keyStrength;
    PublicKeyPacket publicPk;
    List subSigs;
    TrustPacket trustPk;

    public PGPPublicKey(int i2, PublicKey publicKey, Date date) throws NoSuchAlgorithmException, PGPException {
        BCPGKey elGamalPublicBCPGKey;
        BCPGKey rSAPublicBCPGKey;
        this.keySigs = new ArrayList();
        this.ids = new ArrayList();
        this.idTrusts = new ArrayList();
        this.idSigs = new ArrayList();
        this.subSigs = null;
        if (publicKey instanceof RSAPublicKey) {
            RSAPublicKey rSAPublicKey = (RSAPublicKey) publicKey;
            rSAPublicBCPGKey = new RSAPublicBCPGKey(rSAPublicKey.getModulus(), rSAPublicKey.getPublicExponent());
        } else {
            if (publicKey instanceof DSAPublicKey) {
                DSAPublicKey dSAPublicKey = (DSAPublicKey) publicKey;
                DSAParams params = dSAPublicKey.getParams();
                elGamalPublicBCPGKey = new DSAPublicBCPGKey(params.getP(), params.getQ(), params.getG(), dSAPublicKey.getY());
            } else {
                if (!(publicKey instanceof ElGamalPublicKey)) {
                    throw new PGPException("unknown key class");
                }
                ElGamalPublicKey elGamalPublicKey = (ElGamalPublicKey) publicKey;
                ElGamalParameterSpec parameters = elGamalPublicKey.getParameters();
                elGamalPublicBCPGKey = new ElGamalPublicBCPGKey(parameters.getP(), parameters.getG(), elGamalPublicKey.getY());
            }
            rSAPublicBCPGKey = elGamalPublicBCPGKey;
        }
        this.publicPk = new PublicKeyPacket(i2, date, rSAPublicBCPGKey);
        this.ids = new ArrayList();
        this.idSigs = new ArrayList();
        try {
            init();
        } catch (IOException e2) {
            throw new PGPException("exception calculating keyID", e2);
        }
    }

    public PGPPublicKey(int i2, PublicKey publicKey, Date date, String str) throws PGPException, NoSuchProviderException {
        this(i2, publicKey, date);
    }

    public PGPPublicKey(PublicKeyPacket publicKeyPacket, List list, List list2) throws NoSuchAlgorithmException, IOException {
        this.keySigs = new ArrayList();
        this.ids = new ArrayList();
        this.idTrusts = new ArrayList();
        new ArrayList();
        this.subSigs = null;
        this.publicPk = publicKeyPacket;
        this.ids = list;
        this.idSigs = list2;
        init();
    }

    public PGPPublicKey(PublicKeyPacket publicKeyPacket, TrustPacket trustPacket, List list) throws NoSuchAlgorithmException, IOException {
        this.keySigs = new ArrayList();
        this.ids = new ArrayList();
        this.idTrusts = new ArrayList();
        this.idSigs = new ArrayList();
        this.publicPk = publicKeyPacket;
        this.trustPk = trustPacket;
        this.subSigs = list;
        init();
    }

    public PGPPublicKey(PublicKeyPacket publicKeyPacket, TrustPacket trustPacket, List list, List list2, List list3, List list4) throws NoSuchAlgorithmException, IOException {
        this.keySigs = new ArrayList();
        this.ids = new ArrayList();
        this.idTrusts = new ArrayList();
        new ArrayList();
        this.subSigs = null;
        this.publicPk = publicKeyPacket;
        this.trustPk = trustPacket;
        this.keySigs = list;
        this.ids = list2;
        this.idTrusts = list3;
        this.idSigs = list4;
        init();
    }

    public PGPPublicKey(PGPPublicKey pGPPublicKey) {
        this.keySigs = new ArrayList();
        this.ids = new ArrayList();
        this.idTrusts = new ArrayList();
        this.idSigs = new ArrayList();
        this.subSigs = null;
        this.publicPk = pGPPublicKey.publicPk;
        this.keySigs = new ArrayList(pGPPublicKey.keySigs);
        this.ids = new ArrayList(pGPPublicKey.ids);
        this.idTrusts = new ArrayList(pGPPublicKey.idTrusts);
        this.idSigs = new ArrayList(pGPPublicKey.idSigs.size());
        for (int i2 = 0; i2 != pGPPublicKey.idSigs.size(); i2++) {
            this.idSigs.add(new ArrayList((ArrayList) pGPPublicKey.idSigs.get(i2)));
        }
        if (pGPPublicKey.subSigs != null) {
            this.subSigs = new ArrayList(pGPPublicKey.subSigs.size());
            for (int i3 = 0; i3 != pGPPublicKey.subSigs.size(); i3++) {
                this.subSigs.add(pGPPublicKey.subSigs.get(i3));
            }
        }
        this.fingerprint = pGPPublicKey.fingerprint;
        this.keyID = pGPPublicKey.keyID;
        this.keyStrength = pGPPublicKey.keyStrength;
    }

    public PGPPublicKey(PGPPublicKey pGPPublicKey, TrustPacket trustPacket, List list) {
        this.keySigs = new ArrayList();
        this.ids = new ArrayList();
        this.idTrusts = new ArrayList();
        this.idSigs = new ArrayList();
        this.subSigs = null;
        this.publicPk = pGPPublicKey.publicPk;
        this.trustPk = trustPacket;
        this.subSigs = list;
        this.fingerprint = pGPPublicKey.fingerprint;
        this.keyID = pGPPublicKey.keyID;
        this.keyStrength = pGPPublicKey.keyStrength;
    }

    private static PGPPublicKey addCert(PGPPublicKey pGPPublicKey, Object obj, PGPSignature pGPSignature) {
        PGPPublicKey pGPPublicKey2 = new PGPPublicKey(pGPPublicKey);
        List list = null;
        for (int i2 = 0; i2 != pGPPublicKey2.ids.size(); i2++) {
            if (obj.equals(pGPPublicKey2.ids.get(i2))) {
                list = (List) pGPPublicKey2.idSigs.get(i2);
            }
        }
        if (list != null) {
            list.add(pGPSignature);
        } else {
            ArrayList arrayList = new ArrayList();
            arrayList.add(pGPSignature);
            pGPPublicKey2.ids.add(obj);
            pGPPublicKey2.idTrusts.add(null);
            pGPPublicKey2.idSigs.add(arrayList);
        }
        return pGPPublicKey2;
    }

    public static PGPPublicKey addCertification(PGPPublicKey pGPPublicKey, String str, PGPSignature pGPSignature) {
        return addCert(pGPPublicKey, str, pGPSignature);
    }

    public static PGPPublicKey addCertification(PGPPublicKey pGPPublicKey, PGPSignature pGPSignature) {
        if (pGPPublicKey.isMasterKey()) {
            if (pGPSignature.getSignatureType() == 40) {
                throw new IllegalArgumentException("signature type incorrect for master key revocation.");
            }
        } else if (pGPSignature.getSignatureType() == 32) {
            throw new IllegalArgumentException("signature type incorrect for sub-key revocation.");
        }
        PGPPublicKey pGPPublicKey2 = new PGPPublicKey(pGPPublicKey);
        List list = pGPPublicKey2.subSigs;
        if (list == null) {
            list = pGPPublicKey2.keySigs;
        }
        list.add(pGPSignature);
        return pGPPublicKey2;
    }

    public static PGPPublicKey addCertification(PGPPublicKey pGPPublicKey, PGPUserAttributeSubpacketVector pGPUserAttributeSubpacketVector, PGPSignature pGPSignature) {
        return addCert(pGPPublicKey, pGPUserAttributeSubpacketVector, pGPSignature);
    }

    private long getExpirationTimeFromSig(boolean z2, int i2) {
        Iterator signaturesOfType = getSignaturesOfType(i2);
        if (!signaturesOfType.hasNext()) {
            return -1L;
        }
        PGPSignature pGPSignature = (PGPSignature) signaturesOfType.next();
        if (z2 && pGPSignature.getKeyID() != getKeyID()) {
            return -1L;
        }
        PGPSignatureSubpacketVector hashedSubPackets = pGPSignature.getHashedSubPackets();
        if (hashedSubPackets != null) {
            return hashedSubPackets.getKeyExpirationTime();
        }
        return 0L;
    }

    private void init() throws NoSuchAlgorithmException, IOException {
        BigInteger p2;
        RSAPublicBCPGKey rSAPublicBCPGKey;
        BCPGKey key = this.publicPk.getKey();
        if (this.publicPk.getVersion() <= 3) {
            rSAPublicBCPGKey = (RSAPublicBCPGKey) key;
            this.keyID = rSAPublicBCPGKey.getModulus().longValue();
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                byte[] encoded = new MPInteger(rSAPublicBCPGKey.getModulus()).getEncoded();
                messageDigest.update(encoded, 2, encoded.length - 2);
                byte[] encoded2 = new MPInteger(rSAPublicBCPGKey.getPublicExponent()).getEncoded();
                messageDigest.update(encoded2, 2, encoded2.length - 2);
                this.fingerprint = messageDigest.digest();
            } catch (NoSuchAlgorithmException unused) {
                throw new IOException("can't find MD5");
            }
        } else {
            byte[] encodedContents = this.publicPk.getEncodedContents();
            try {
                MessageDigest messageDigest2 = MessageDigest.getInstance("SHA1");
                messageDigest2.update((byte) -103);
                messageDigest2.update((byte) (encodedContents.length >> 8));
                messageDigest2.update((byte) encodedContents.length);
                messageDigest2.update(encodedContents);
                this.fingerprint = messageDigest2.digest();
                this.keyID = ((r1[r1.length - 8] & 255) << 56) | ((r1[r1.length - 7] & 255) << 48) | ((r1[r1.length - 6] & 255) << 40) | ((r1[r1.length - 5] & 255) << 32) | ((r1[r1.length - 4] & 255) << 24) | ((r1[r1.length - 3] & 255) << 16) | ((r1[r1.length - 2] & 255) << 8) | (r1[r1.length - 1] & 255);
                if (!(key instanceof RSAPublicBCPGKey)) {
                    if (key instanceof DSAPublicBCPGKey) {
                        p2 = ((DSAPublicBCPGKey) key).getP();
                    } else if (!(key instanceof ElGamalPublicBCPGKey)) {
                        return;
                    } else {
                        p2 = ((ElGamalPublicBCPGKey) key).getP();
                    }
                    this.keyStrength = p2.bitLength();
                }
                rSAPublicBCPGKey = (RSAPublicBCPGKey) key;
            } catch (NoSuchAlgorithmException unused2) {
                throw new IOException("can't find SHA1");
            }
        }
        p2 = rSAPublicBCPGKey.getModulus();
        this.keyStrength = p2.bitLength();
    }

    private static PGPPublicKey removeCert(PGPPublicKey pGPPublicKey, Object obj) {
        PGPPublicKey pGPPublicKey2 = new PGPPublicKey(pGPPublicKey);
        boolean z2 = false;
        for (int i2 = 0; i2 < pGPPublicKey2.ids.size(); i2++) {
            if (obj.equals(pGPPublicKey2.ids.get(i2))) {
                pGPPublicKey2.ids.remove(i2);
                pGPPublicKey2.idTrusts.remove(i2);
                pGPPublicKey2.idSigs.remove(i2);
                z2 = true;
            }
        }
        if (z2) {
            return pGPPublicKey2;
        }
        return null;
    }

    private static PGPPublicKey removeCert(PGPPublicKey pGPPublicKey, Object obj, PGPSignature pGPSignature) {
        PGPPublicKey pGPPublicKey2 = new PGPPublicKey(pGPPublicKey);
        boolean zRemove = false;
        for (int i2 = 0; i2 < pGPPublicKey2.ids.size(); i2++) {
            if (obj.equals(pGPPublicKey2.ids.get(i2))) {
                zRemove = ((List) pGPPublicKey2.idSigs.get(i2)).remove(pGPSignature);
            }
        }
        if (zRemove) {
            return pGPPublicKey2;
        }
        return null;
    }

    public static PGPPublicKey removeCertification(PGPPublicKey pGPPublicKey, String str) {
        return removeCert(pGPPublicKey, str);
    }

    public static PGPPublicKey removeCertification(PGPPublicKey pGPPublicKey, String str, PGPSignature pGPSignature) {
        return removeCert(pGPPublicKey, str, pGPSignature);
    }

    public static PGPPublicKey removeCertification(PGPPublicKey pGPPublicKey, PGPSignature pGPSignature) {
        PGPPublicKey pGPPublicKey2 = new PGPPublicKey(pGPPublicKey);
        List list = pGPPublicKey2.subSigs;
        if (list == null) {
            list = pGPPublicKey2.keySigs;
        }
        boolean zRemove = list.remove(pGPSignature);
        if (!zRemove) {
            Iterator userIDs = pGPPublicKey.getUserIDs();
            while (userIDs.hasNext()) {
                String str = (String) userIDs.next();
                Iterator signaturesForID = pGPPublicKey.getSignaturesForID(str);
                while (signaturesForID.hasNext()) {
                    if (pGPSignature == signaturesForID.next()) {
                        pGPPublicKey2 = removeCertification(pGPPublicKey2, str, pGPSignature);
                        zRemove = true;
                    }
                }
            }
            if (!zRemove) {
                Iterator userAttributes = pGPPublicKey.getUserAttributes();
                while (userAttributes.hasNext()) {
                    PGPUserAttributeSubpacketVector pGPUserAttributeSubpacketVector = (PGPUserAttributeSubpacketVector) userAttributes.next();
                    Iterator signaturesForUserAttribute = pGPPublicKey.getSignaturesForUserAttribute(pGPUserAttributeSubpacketVector);
                    while (signaturesForUserAttribute.hasNext()) {
                        if (pGPSignature == signaturesForUserAttribute.next()) {
                            pGPPublicKey2 = removeCertification(pGPPublicKey2, pGPUserAttributeSubpacketVector, pGPSignature);
                        }
                    }
                }
            }
        }
        return pGPPublicKey2;
    }

    public static PGPPublicKey removeCertification(PGPPublicKey pGPPublicKey, PGPUserAttributeSubpacketVector pGPUserAttributeSubpacketVector) {
        return removeCert(pGPPublicKey, pGPUserAttributeSubpacketVector);
    }

    public static PGPPublicKey removeCertification(PGPPublicKey pGPPublicKey, PGPUserAttributeSubpacketVector pGPUserAttributeSubpacketVector, PGPSignature pGPSignature) {
        return removeCert(pGPPublicKey, pGPUserAttributeSubpacketVector, pGPSignature);
    }

    public void encode(OutputStream outputStream) throws IOException {
        BCPGOutputStream bCPGOutputStream = outputStream instanceof BCPGOutputStream ? (BCPGOutputStream) outputStream : new BCPGOutputStream(outputStream);
        bCPGOutputStream.writePacket(this.publicPk);
        TrustPacket trustPacket = this.trustPk;
        if (trustPacket != null) {
            bCPGOutputStream.writePacket(trustPacket);
        }
        if (this.subSigs != null) {
            for (int i2 = 0; i2 != this.subSigs.size(); i2++) {
                ((PGPSignature) this.subSigs.get(i2)).encode(bCPGOutputStream);
            }
            return;
        }
        for (int i3 = 0; i3 != this.keySigs.size(); i3++) {
            ((PGPSignature) this.keySigs.get(i3)).encode(bCPGOutputStream);
        }
        for (int i4 = 0; i4 != this.ids.size(); i4++) {
            bCPGOutputStream.writePacket(this.ids.get(i4) instanceof String ? new UserIDPacket((String) this.ids.get(i4)) : new UserAttributePacket(((PGPUserAttributeSubpacketVector) this.ids.get(i4)).toSubpacketArray()));
            if (this.idTrusts.get(i4) != null) {
                bCPGOutputStream.writePacket((ContainedPacket) this.idTrusts.get(i4));
            }
            List list = (List) this.idSigs.get(i4);
            for (int i5 = 0; i5 != list.size(); i5++) {
                ((PGPSignature) list.get(i5)).encode(bCPGOutputStream);
            }
        }
    }

    public int getAlgorithm() {
        return this.publicPk.getAlgorithm();
    }

    public int getBitStrength() {
        return this.keyStrength;
    }

    public Date getCreationTime() {
        return this.publicPk.getTime();
    }

    public byte[] getEncoded() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        encode(byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public byte[] getFingerprint() {
        byte[] bArr = this.fingerprint;
        int length = bArr.length;
        byte[] bArr2 = new byte[length];
        System.arraycopy(bArr, 0, bArr2, 0, length);
        return bArr2;
    }

    public PublicKey getKey(String str) throws PGPException, NoSuchProviderException {
        return getKey(PGPUtil.getProvider(str));
    }

    public PublicKey getKey(Provider provider) throws PGPException {
        try {
            int algorithm = this.publicPk.getAlgorithm();
            if (algorithm == 1 || algorithm == 2 || algorithm == 3) {
                RSAPublicBCPGKey rSAPublicBCPGKey = (RSAPublicBCPGKey) this.publicPk.getKey();
                return KeyFactory.getInstance("RSA", provider).generatePublic(new RSAPublicKeySpec(rSAPublicBCPGKey.getModulus(), rSAPublicBCPGKey.getPublicExponent()));
            }
            if (algorithm != 16) {
                if (algorithm == 17) {
                    DSAPublicBCPGKey dSAPublicBCPGKey = (DSAPublicBCPGKey) this.publicPk.getKey();
                    return KeyFactory.getInstance("DSA", provider).generatePublic(new DSAPublicKeySpec(dSAPublicBCPGKey.getY(), dSAPublicBCPGKey.getP(), dSAPublicBCPGKey.getQ(), dSAPublicBCPGKey.getG()));
                }
                if (algorithm != 20) {
                    throw new PGPException("unknown public key algorithm encountered");
                }
            }
            ElGamalPublicBCPGKey elGamalPublicBCPGKey = (ElGamalPublicBCPGKey) this.publicPk.getKey();
            return KeyFactory.getInstance("ElGamal", provider).generatePublic(new ElGamalPublicKeySpec(elGamalPublicBCPGKey.getY(), new ElGamalParameterSpec(elGamalPublicBCPGKey.getP(), elGamalPublicBCPGKey.getG())));
        } catch (PGPException e2) {
            throw e2;
        } catch (Exception e3) {
            throw new PGPException("exception constructing public key", e3);
        }
    }

    public long getKeyID() {
        return this.keyID;
    }

    public Iterator getSignatures() {
        List list = this.subSigs;
        if (list != null) {
            return list.iterator();
        }
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.keySigs);
        for (int i2 = 0; i2 != this.idSigs.size(); i2++) {
            arrayList.addAll((Collection) this.idSigs.get(i2));
        }
        return arrayList.iterator();
    }

    public Iterator getSignaturesForID(String str) {
        for (int i2 = 0; i2 != this.ids.size(); i2++) {
            if (str.equals(this.ids.get(i2))) {
                return ((ArrayList) this.idSigs.get(i2)).iterator();
            }
        }
        return null;
    }

    public Iterator getSignaturesForUserAttribute(PGPUserAttributeSubpacketVector pGPUserAttributeSubpacketVector) {
        for (int i2 = 0; i2 != this.ids.size(); i2++) {
            if (pGPUserAttributeSubpacketVector.equals(this.ids.get(i2))) {
                return ((ArrayList) this.idSigs.get(i2)).iterator();
            }
        }
        return null;
    }

    public Iterator getSignaturesOfType(int i2) {
        ArrayList arrayList = new ArrayList();
        Iterator signatures = getSignatures();
        while (signatures.hasNext()) {
            PGPSignature pGPSignature = (PGPSignature) signatures.next();
            if (pGPSignature.getSignatureType() == i2) {
                arrayList.add(pGPSignature);
            }
        }
        return arrayList.iterator();
    }

    public byte[] getTrustData() {
        TrustPacket trustPacket = this.trustPk;
        if (trustPacket == null) {
            return null;
        }
        return Arrays.clone(trustPacket.getLevelAndTrustAmount());
    }

    public Iterator getUserAttributes() {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 != this.ids.size(); i2++) {
            if (this.ids.get(i2) instanceof PGPUserAttributeSubpacketVector) {
                arrayList.add(this.ids.get(i2));
            }
        }
        return arrayList.iterator();
    }

    public Iterator getUserIDs() {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 != this.ids.size(); i2++) {
            if (this.ids.get(i2) instanceof String) {
                arrayList.add(this.ids.get(i2));
            }
        }
        return arrayList.iterator();
    }

    public int getValidDays() {
        return this.publicPk.getVersion() > 3 ? (int) (getValidSeconds() / 86400) : this.publicPk.getValidDays();
    }

    public long getValidSeconds() {
        if (this.publicPk.getVersion() <= 3) {
            return this.publicPk.getValidDays() * 24 * 60 * 60;
        }
        int i2 = 0;
        if (isMasterKey()) {
            while (true) {
                int[] iArr = MASTER_KEY_CERTIFICATION_TYPES;
                if (i2 == iArr.length) {
                    break;
                }
                long expirationTimeFromSig = getExpirationTimeFromSig(true, iArr[i2]);
                if (expirationTimeFromSig >= 0) {
                    return expirationTimeFromSig;
                }
                i2++;
            }
        } else {
            long expirationTimeFromSig2 = getExpirationTimeFromSig(false, 24);
            if (expirationTimeFromSig2 >= 0) {
                return expirationTimeFromSig2;
            }
        }
        return 0L;
    }

    public int getVersion() {
        return this.publicPk.getVersion();
    }

    public boolean isEncryptionKey() {
        int algorithm = this.publicPk.getAlgorithm();
        return algorithm == 1 || algorithm == 2 || algorithm == 16 || algorithm == 20;
    }

    public boolean isMasterKey() {
        return this.subSigs == null;
    }

    public boolean isRevoked() {
        boolean z2 = false;
        if (isMasterKey()) {
            int i2 = 0;
            while (!z2 && i2 < this.keySigs.size()) {
                int i3 = i2 + 1;
                if (((PGPSignature) this.keySigs.get(i2)).getSignatureType() == 32) {
                    z2 = true;
                }
                i2 = i3;
            }
        } else {
            int i4 = 0;
            while (!z2 && i4 < this.subSigs.size()) {
                int i5 = i4 + 1;
                if (((PGPSignature) this.subSigs.get(i4)).getSignatureType() == 40) {
                    z2 = true;
                }
                i4 = i5;
            }
        }
        return z2;
    }
}
