package org.bouncycastle.openpgp;

import androidx.core.view.MotionEventCompat;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.DSAPrivateKeySpec;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import org.bouncycastle.bcpg.BCPGInputStream;
import org.bouncycastle.bcpg.BCPGObject;
import org.bouncycastle.bcpg.BCPGOutputStream;
import org.bouncycastle.bcpg.ContainedPacket;
import org.bouncycastle.bcpg.DSAPublicBCPGKey;
import org.bouncycastle.bcpg.DSASecretBCPGKey;
import org.bouncycastle.bcpg.ElGamalPublicBCPGKey;
import org.bouncycastle.bcpg.ElGamalSecretBCPGKey;
import org.bouncycastle.bcpg.PublicKeyPacket;
import org.bouncycastle.bcpg.RSAPublicBCPGKey;
import org.bouncycastle.bcpg.RSASecretBCPGKey;
import org.bouncycastle.bcpg.S2K;
import org.bouncycastle.bcpg.SecretKeyPacket;
import org.bouncycastle.bcpg.SecretSubkeyPacket;
import org.bouncycastle.bcpg.TrustPacket;
import org.bouncycastle.bcpg.UserAttributePacket;
import org.bouncycastle.bcpg.UserIDPacket;
import org.bouncycastle.jce.interfaces.ElGamalPrivateKey;
import org.bouncycastle.jce.spec.ElGamalParameterSpec;
import org.bouncycastle.jce.spec.ElGamalPrivateKeySpec;

/* loaded from: classes9.dex */
public class PGPSecretKey {

    /* renamed from: pub, reason: collision with root package name */
    final PGPPublicKey f27947pub;
    final SecretKeyPacket secret;

    public PGPSecretKey(int i2, int i3, PublicKey publicKey, PrivateKey privateKey, Date date, String str, int i4, char[] cArr, PGPSignatureSubpacketVector pGPSignatureSubpacketVector, PGPSignatureSubpacketVector pGPSignatureSubpacketVector2, SecureRandom secureRandom, String str2) throws PGPException, NoSuchProviderException {
        this(i2, new PGPKeyPair(i3, publicKey, privateKey, date), str, i4, cArr, pGPSignatureSubpacketVector, pGPSignatureSubpacketVector2, secureRandom, str2);
    }

    public PGPSecretKey(int i2, int i3, PublicKey publicKey, PrivateKey privateKey, Date date, String str, int i4, char[] cArr, boolean z2, PGPSignatureSubpacketVector pGPSignatureSubpacketVector, PGPSignatureSubpacketVector pGPSignatureSubpacketVector2, SecureRandom secureRandom, String str2) throws PGPException, NoSuchProviderException {
        this(i2, new PGPKeyPair(i3, publicKey, privateKey, date), str, i4, cArr, z2, pGPSignatureSubpacketVector, pGPSignatureSubpacketVector2, secureRandom, str2);
    }

    public PGPSecretKey(int i2, PGPKeyPair pGPKeyPair, String str, int i3, char[] cArr, PGPSignatureSubpacketVector pGPSignatureSubpacketVector, PGPSignatureSubpacketVector pGPSignatureSubpacketVector2, SecureRandom secureRandom, String str2) throws PGPException, NoSuchProviderException {
        this(i2, pGPKeyPair, str, i3, cArr, false, pGPSignatureSubpacketVector, pGPSignatureSubpacketVector2, secureRandom, str2);
    }

    public PGPSecretKey(int i2, PGPKeyPair pGPKeyPair, String str, int i3, char[] cArr, boolean z2, PGPSignatureSubpacketVector pGPSignatureSubpacketVector, PGPSignatureSubpacketVector pGPSignatureSubpacketVector2, SecureRandom secureRandom, String str2) throws PGPException, NoSuchProviderException {
        this(i2, pGPKeyPair, str, i3, cArr, z2, pGPSignatureSubpacketVector, pGPSignatureSubpacketVector2, secureRandom, PGPUtil.getProvider(str2));
    }

    public PGPSecretKey(int i2, PGPKeyPair pGPKeyPair, String str, int i3, char[] cArr, boolean z2, PGPSignatureSubpacketVector pGPSignatureSubpacketVector, PGPSignatureSubpacketVector pGPSignatureSubpacketVector2, SecureRandom secureRandom, Provider provider) throws PGPException {
        this(pGPKeyPair.getPrivateKey(), certifiedPublicKey(i2, pGPKeyPair, str, pGPSignatureSubpacketVector, pGPSignatureSubpacketVector2, provider), i3, cArr, z2, secureRandom, true, provider);
    }

    public PGPSecretKey(SecretKeyPacket secretKeyPacket, PGPPublicKey pGPPublicKey) {
        this.secret = secretKeyPacket;
        this.f27947pub = pGPPublicKey;
    }

    public PGPSecretKey(PGPPrivateKey pGPPrivateKey, PGPPublicKey pGPPublicKey, int i2, char[] cArr, boolean z2, SecureRandom secureRandom, Provider provider) throws PGPException {
        this(pGPPrivateKey, pGPPublicKey, i2, cArr, z2, secureRandom, false, provider);
    }

    public PGPSecretKey(PGPPrivateKey pGPPrivateKey, PGPPublicKey pGPPublicKey, int i2, char[] cArr, boolean z2, SecureRandom secureRandom, boolean z3, Provider provider) throws BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, IOException, InvalidKeyException, PGPException {
        BCPGObject rSASecretBCPGKey;
        Cipher cipher;
        SecretKeyPacket secretKeyPacket;
        this.f27947pub = pGPPublicKey;
        int algorithm = pGPPublicKey.getAlgorithm();
        if (algorithm == 1 || algorithm == 2 || algorithm == 3) {
            RSAPrivateCrtKey rSAPrivateCrtKey = (RSAPrivateCrtKey) pGPPrivateKey.getKey();
            rSASecretBCPGKey = new RSASecretBCPGKey(rSAPrivateCrtKey.getPrivateExponent(), rSAPrivateCrtKey.getPrimeP(), rSAPrivateCrtKey.getPrimeQ());
        } else if (algorithm == 16) {
            rSASecretBCPGKey = new ElGamalSecretBCPGKey(((ElGamalPrivateKey) pGPPrivateKey.getKey()).getX());
        } else if (algorithm != 17) {
            if (algorithm != 20) {
                throw new PGPException("unknown key class");
            }
            rSASecretBCPGKey = new ElGamalSecretBCPGKey(((ElGamalPrivateKey) pGPPrivateKey.getKey()).getX());
        } else {
            rSASecretBCPGKey = new DSASecretBCPGKey(((DSAPrivateKey) pGPPrivateKey.getKey()).getX());
        }
        String symmetricCipherName = PGPUtil.getSymmetricCipherName(i2);
        if (symmetricCipherName != null) {
            try {
                cipher = Cipher.getInstance(symmetricCipherName + "/CFB/NoPadding", provider);
            } catch (Exception e2) {
                throw new PGPException("Exception creating cipher", e2);
            }
        } else {
            cipher = null;
        }
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            BCPGOutputStream bCPGOutputStream = new BCPGOutputStream(byteArrayOutputStream);
            bCPGOutputStream.writeObject(rSASecretBCPGKey);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            bCPGOutputStream.write(checksum(z2, byteArray, byteArray.length));
            if (cipher != null) {
                byte[] bArr = new byte[8];
                secureRandom.nextBytes(bArr);
                S2K s2k = new S2K(2, bArr, 96);
                cipher.init(1, PGPUtil.makeKeyFromPassPhrase(i2, s2k, cArr, provider), secureRandom);
                byte[] iv = cipher.getIV();
                byte[] bArrDoFinal = cipher.doFinal(byteArrayOutputStream.toByteArray());
                int i3 = z2 ? 254 : 255;
                secretKeyPacket = z3 ? new SecretKeyPacket(pGPPublicKey.publicPk, i2, i3, s2k, iv, bArrDoFinal) : new SecretSubkeyPacket(pGPPublicKey.publicPk, i2, i3, s2k, iv, bArrDoFinal);
            } else {
                secretKeyPacket = z3 ? new SecretKeyPacket(pGPPublicKey.publicPk, i2, null, null, byteArrayOutputStream.toByteArray()) : new SecretSubkeyPacket(pGPPublicKey.publicPk, i2, null, null, byteArrayOutputStream.toByteArray());
            }
            this.secret = secretKeyPacket;
        } catch (PGPException e3) {
            throw e3;
        } catch (Exception e4) {
            throw new PGPException("Exception encrypting key", e4);
        }
    }

    private static PGPPublicKey certifiedPublicKey(int i2, PGPKeyPair pGPKeyPair, String str, PGPSignatureSubpacketVector pGPSignatureSubpacketVector, PGPSignatureSubpacketVector pGPSignatureSubpacketVector2, Provider provider) throws PGPException {
        try {
            PGPSignatureGenerator pGPSignatureGenerator = new PGPSignatureGenerator(pGPKeyPair.getPublicKey().getAlgorithm(), 2, provider);
            pGPSignatureGenerator.initSign(i2, pGPKeyPair.getPrivateKey());
            pGPSignatureGenerator.setHashedSubpackets(pGPSignatureSubpacketVector);
            pGPSignatureGenerator.setUnhashedSubpackets(pGPSignatureSubpacketVector2);
            try {
                return PGPPublicKey.addCertification(pGPKeyPair.getPublicKey(), str, pGPSignatureGenerator.generateCertification(str, pGPKeyPair.getPublicKey()));
            } catch (Exception e2) {
                throw new PGPException("exception doing certification: " + e2, e2);
            }
        } catch (Exception e3) {
            throw new PGPException("creating signature generator: " + e3, e3);
        }
    }

    private static byte[] checksum(boolean z2, byte[] bArr, int i2) throws NoSuchAlgorithmException, PGPException {
        if (z2) {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
                messageDigest.update(bArr, 0, i2);
                return messageDigest.digest();
            } catch (NoSuchAlgorithmException e2) {
                throw new PGPException("Can't find SHA-1", e2);
            }
        }
        int i3 = 0;
        for (int i4 = 0; i4 != i2; i4++) {
            i3 += bArr[i4] & 255;
        }
        return new byte[]{(byte) (i3 >> 8), (byte) i3};
    }

    public static PGPSecretKey copyWithNewPassword(PGPSecretKey pGPSecretKey, char[] cArr, char[] cArr2, int i2, SecureRandom secureRandom, String str) throws PGPException, NoSuchProviderException {
        return copyWithNewPassword(pGPSecretKey, cArr, cArr2, i2, secureRandom, PGPUtil.getProvider(str));
    }

    public static PGPSecretKey copyWithNewPassword(PGPSecretKey pGPSecretKey, char[] cArr, char[] cArr2, int i2, SecureRandom secureRandom, Provider provider) throws BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeyException, PGPException, InvalidAlgorithmParameterException, ShortBufferException {
        byte[] iv;
        byte[] bArrDoFinal;
        int i3;
        S2K s2k;
        byte[] bArrExtractKeyData = pGPSecretKey.extractKeyData(cArr, provider);
        int s2KUsage = pGPSecretKey.secret.getS2KUsage();
        if (i2 != 0) {
            try {
                Cipher cipher = Cipher.getInstance(PGPUtil.getSymmetricCipherName(i2) + "/CFB/NoPadding", provider);
                byte[] bArr = new byte[8];
                secureRandom.nextBytes(bArr);
                S2K s2k2 = new S2K(2, bArr, 96);
                try {
                    cipher.init(1, PGPUtil.makeKeyFromPassPhrase(i2, s2k2, cArr2, provider), secureRandom);
                    iv = cipher.getIV();
                    bArrDoFinal = cipher.doFinal(bArrExtractKeyData);
                    i3 = s2KUsage;
                    s2k = s2k2;
                } catch (PGPException e2) {
                    throw e2;
                } catch (Exception e3) {
                    throw new PGPException("Exception encrypting key", e3);
                }
            } catch (Exception e4) {
                throw new PGPException("Exception creating cipher", e4);
            }
        } else if (pGPSecretKey.secret.getS2KUsage() == 254) {
            int length = bArrExtractKeyData.length - 18;
            byte[] bArr2 = new byte[length];
            int i4 = length - 2;
            System.arraycopy(bArrExtractKeyData, 0, bArr2, 0, i4);
            byte[] bArrChecksum = checksum(false, bArr2, i4);
            bArr2[i4] = bArrChecksum[0];
            bArr2[length - 1] = bArrChecksum[1];
            iv = null;
            bArrDoFinal = bArr2;
            i3 = 0;
            s2k = null;
        } else {
            i3 = 0;
            s2k = null;
            iv = null;
            bArrDoFinal = bArrExtractKeyData;
        }
        SecretKeyPacket secretKeyPacket = pGPSecretKey.secret;
        return new PGPSecretKey(secretKeyPacket instanceof SecretSubkeyPacket ? new SecretSubkeyPacket(secretKeyPacket.getPublicKeyPacket(), i2, i3, s2k, iv, bArrDoFinal) : new SecretKeyPacket(secretKeyPacket.getPublicKeyPacket(), i2, i3, s2k, iv, bArrDoFinal), pGPSecretKey.f27947pub);
    }

    private byte[] extractKeyData(char[] cArr, Provider provider) throws BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeyException, PGPException, InvalidAlgorithmParameterException, ShortBufferException {
        Cipher cipher;
        int i2;
        String symmetricCipherName = PGPUtil.getSymmetricCipherName(this.secret.getEncAlgorithm());
        if (symmetricCipherName != null) {
            try {
                cipher = Cipher.getInstance(symmetricCipherName + "/CFB/NoPadding", provider);
            } catch (Exception e2) {
                throw new PGPException("Exception creating cipher", e2);
            }
        } else {
            cipher = null;
        }
        byte[] secretKeyData = this.secret.getSecretKeyData();
        try {
            if (cipher == null) {
                return secretKeyData;
            }
            try {
                int i3 = 4;
                int i4 = 2;
                int i5 = 0;
                if (this.secret.getPublicKeyPacket().getVersion() == 4) {
                    cipher.init(2, PGPUtil.makeKeyFromPassPhrase(this.secret.getEncAlgorithm(), this.secret.getS2K(), cArr, provider), new IvParameterSpec(this.secret.getIV()));
                    byte[] bArrDoFinal = cipher.doFinal(secretKeyData, 0, secretKeyData.length);
                    boolean z2 = this.secret.getS2KUsage() == 254;
                    byte[] bArrChecksum = checksum(z2, bArrDoFinal, z2 ? bArrDoFinal.length - 20 : bArrDoFinal.length - 2);
                    while (i5 != bArrChecksum.length) {
                        if (bArrChecksum[i5] != bArrDoFinal[(bArrDoFinal.length - bArrChecksum.length) + i5]) {
                            throw new PGPException("checksum mismatch at " + i5 + " of " + bArrChecksum.length);
                        }
                        i5++;
                    }
                    return bArrDoFinal;
                }
                SecretKey secretKeyMakeKeyFromPassPhrase = PGPUtil.makeKeyFromPassPhrase(this.secret.getEncAlgorithm(), this.secret.getS2K(), cArr, provider);
                int length = secretKeyData.length;
                byte[] bArr = new byte[length];
                int length2 = this.secret.getIV().length;
                byte[] bArr2 = new byte[length2];
                System.arraycopy(this.secret.getIV(), 0, bArr2, 0, length2);
                int i6 = 0;
                int i7 = 0;
                while (i6 != i3) {
                    cipher.init(i4, secretKeyMakeKeyFromPassPhrase, new IvParameterSpec(bArr2));
                    byte b3 = secretKeyData[i7];
                    int i8 = i7 + 1;
                    int i9 = (((b3 << 8) | (secretKeyData[i8] & 255)) + 7) / 8;
                    bArr[i7] = b3;
                    bArr[i8] = secretKeyData[i8];
                    int i10 = i7 + 2;
                    int i11 = i6;
                    byte[] bArr3 = bArr2;
                    int i12 = length2;
                    cipher.doFinal(secretKeyData, i10, i9, bArr, i10);
                    i7 += i9 + 2;
                    if (i11 != 3) {
                        i2 = 0;
                        System.arraycopy(secretKeyData, i7 - i12, bArr3, 0, i12);
                    } else {
                        i2 = 0;
                    }
                    i6 = i11 + 1;
                    bArr2 = bArr3;
                    length2 = i12;
                    i3 = 4;
                    i4 = 2;
                    i5 = i2;
                }
                int i13 = i5;
                int i14 = ((secretKeyData[i7] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (secretKeyData[i7 + 1] & 255);
                for (int i15 = i13; i15 < length - 2; i15++) {
                    i13 += bArr[i15] & 255;
                }
                int i16 = 65535 & i13;
                if (i16 == i14) {
                    return bArr;
                }
                throw new PGPException("checksum mismatch: passphrase wrong, expected " + Integer.toHexString(i14) + " found " + Integer.toHexString(i16));
            } catch (PGPException e3) {
                throw e3;
            } catch (Exception e4) {
                throw new PGPException("Exception decrypting key", e4);
            }
        } catch (PGPException e5) {
            throw e5;
        } catch (Exception e6) {
            throw new PGPException("Exception constructing key", e6);
        }
    }

    public static PGPSecretKey replacePublicKey(PGPSecretKey pGPSecretKey, PGPPublicKey pGPPublicKey) {
        if (pGPPublicKey.getKeyID() == pGPSecretKey.getKeyID()) {
            return new PGPSecretKey(pGPSecretKey.secret, pGPPublicKey);
        }
        throw new IllegalArgumentException("keyIDs do not match");
    }

    public void encode(OutputStream outputStream) throws IOException {
        BCPGOutputStream bCPGOutputStream = outputStream instanceof BCPGOutputStream ? (BCPGOutputStream) outputStream : new BCPGOutputStream(outputStream);
        bCPGOutputStream.writePacket(this.secret);
        TrustPacket trustPacket = this.f27947pub.trustPk;
        if (trustPacket != null) {
            bCPGOutputStream.writePacket(trustPacket);
        }
        if (this.f27947pub.subSigs != null) {
            for (int i2 = 0; i2 != this.f27947pub.subSigs.size(); i2++) {
                ((PGPSignature) this.f27947pub.subSigs.get(i2)).encode(bCPGOutputStream);
            }
            return;
        }
        for (int i3 = 0; i3 != this.f27947pub.keySigs.size(); i3++) {
            ((PGPSignature) this.f27947pub.keySigs.get(i3)).encode(bCPGOutputStream);
        }
        for (int i4 = 0; i4 != this.f27947pub.ids.size(); i4++) {
            bCPGOutputStream.writePacket(this.f27947pub.ids.get(i4) instanceof String ? new UserIDPacket((String) this.f27947pub.ids.get(i4)) : new UserAttributePacket(((PGPUserAttributeSubpacketVector) this.f27947pub.ids.get(i4)).toSubpacketArray()));
            if (this.f27947pub.idTrusts.get(i4) != null) {
                bCPGOutputStream.writePacket((ContainedPacket) this.f27947pub.idTrusts.get(i4));
            }
            ArrayList arrayList = (ArrayList) this.f27947pub.idSigs.get(i4);
            for (int i5 = 0; i5 != arrayList.size(); i5++) {
                ((PGPSignature) arrayList.get(i5)).encode(bCPGOutputStream);
            }
        }
    }

    public PGPPrivateKey extractPrivateKey(char[] cArr, String str) throws PGPException, NoSuchProviderException {
        return extractPrivateKey(cArr, PGPUtil.getProvider(str));
    }

    public PGPPrivateKey extractPrivateKey(char[] cArr, Provider provider) throws PGPException {
        byte[] secretKeyData = this.secret.getSecretKeyData();
        if (secretKeyData == null || secretKeyData.length < 1) {
            return null;
        }
        PublicKeyPacket publicKeyPacket = this.secret.getPublicKeyPacket();
        try {
            BCPGInputStream bCPGInputStream = new BCPGInputStream(new ByteArrayInputStream(extractKeyData(cArr, provider)));
            int algorithm = publicKeyPacket.getAlgorithm();
            if (algorithm == 1 || algorithm == 2 || algorithm == 3) {
                RSAPublicBCPGKey rSAPublicBCPGKey = (RSAPublicBCPGKey) publicKeyPacket.getKey();
                RSASecretBCPGKey rSASecretBCPGKey = new RSASecretBCPGKey(bCPGInputStream);
                return new PGPPrivateKey(KeyFactory.getInstance("RSA", provider).generatePrivate(new RSAPrivateCrtKeySpec(rSASecretBCPGKey.getModulus(), rSAPublicBCPGKey.getPublicExponent(), rSASecretBCPGKey.getPrivateExponent(), rSASecretBCPGKey.getPrimeP(), rSASecretBCPGKey.getPrimeQ(), rSASecretBCPGKey.getPrimeExponentP(), rSASecretBCPGKey.getPrimeExponentQ(), rSASecretBCPGKey.getCrtCoefficient())), getKeyID());
            }
            if (algorithm != 16) {
                if (algorithm == 17) {
                    DSAPublicBCPGKey dSAPublicBCPGKey = (DSAPublicBCPGKey) publicKeyPacket.getKey();
                    return new PGPPrivateKey(KeyFactory.getInstance("DSA", provider).generatePrivate(new DSAPrivateKeySpec(new DSASecretBCPGKey(bCPGInputStream).getX(), dSAPublicBCPGKey.getP(), dSAPublicBCPGKey.getQ(), dSAPublicBCPGKey.getG())), getKeyID());
                }
                if (algorithm != 20) {
                    throw new PGPException("unknown public key algorithm encountered");
                }
            }
            ElGamalPublicBCPGKey elGamalPublicBCPGKey = (ElGamalPublicBCPGKey) publicKeyPacket.getKey();
            return new PGPPrivateKey(KeyFactory.getInstance("ElGamal", provider).generatePrivate(new ElGamalPrivateKeySpec(new ElGamalSecretBCPGKey(bCPGInputStream).getX(), new ElGamalParameterSpec(elGamalPublicBCPGKey.getP(), elGamalPublicBCPGKey.getG()))), getKeyID());
        } catch (PGPException e2) {
            throw e2;
        } catch (Exception e3) {
            throw new PGPException("Exception constructing key", e3);
        }
    }

    public byte[] getEncoded() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        encode(byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public int getKeyEncryptionAlgorithm() {
        return this.secret.getEncAlgorithm();
    }

    public long getKeyID() {
        return this.f27947pub.getKeyID();
    }

    public PGPPublicKey getPublicKey() {
        return this.f27947pub;
    }

    public Iterator getUserAttributes() {
        return this.f27947pub.getUserAttributes();
    }

    public Iterator getUserIDs() {
        return this.f27947pub.getUserIDs();
    }

    public boolean isMasterKey() {
        return this.f27947pub.isMasterKey();
    }

    public boolean isSigningKey() {
        int algorithm = this.f27947pub.getAlgorithm();
        return algorithm == 1 || algorithm == 3 || algorithm == 17 || algorithm == 19 || algorithm == 20;
    }
}
