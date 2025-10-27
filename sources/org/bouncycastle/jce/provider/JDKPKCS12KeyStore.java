package org.bouncycastle.jce.provider;

import cn.hutool.core.text.StrPool;
import cn.hutool.crypto.KeyUtil;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.KeyStoreSpi;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.BERConstructedOctetString;
import org.bouncycastle.asn1.BEROutputStream;
import org.bouncycastle.asn1.DERBMPString;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DEROutputStream;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.pkcs.AuthenticatedSafe;
import org.bouncycastle.asn1.pkcs.CertBag;
import org.bouncycastle.asn1.pkcs.ContentInfo;
import org.bouncycastle.asn1.pkcs.EncryptedData;
import org.bouncycastle.asn1.pkcs.EncryptedPrivateKeyInfo;
import org.bouncycastle.asn1.pkcs.MacData;
import org.bouncycastle.asn1.pkcs.PKCS12PBEParams;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.Pfx;
import org.bouncycastle.asn1.pkcs.SafeBag;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.DigestInfo;
import org.bouncycastle.asn1.x509.SubjectKeyIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.asn1.x509.X509ObjectIdentifiers;
import org.bouncycastle.jce.interfaces.BCKeyStore;
import org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Strings;

/* loaded from: classes9.dex */
public class JDKPKCS12KeyStore extends KeyStoreSpi implements PKCSObjectIdentifiers, X509ObjectIdentifiers, BCKeyStore {
    static final int CERTIFICATE = 1;
    static final int KEY = 2;
    static final int KEY_PRIVATE = 0;
    static final int KEY_PUBLIC = 1;
    static final int KEY_SECRET = 2;
    private static final int MIN_ITERATIONS = 1024;
    static final int NULL = 0;
    private static final int SALT_SIZE = 20;
    static final int SEALED = 4;
    static final int SECRET = 3;
    private static final Provider bcProvider = new BouncyCastleProvider();
    private DERObjectIdentifier certAlgorithm;
    private CertificateFactory certFact;
    private IgnoresCaseHashtable certs;
    private DERObjectIdentifier keyAlgorithm;
    private IgnoresCaseHashtable keys;
    private Hashtable localIds = new Hashtable();
    private Hashtable chainCerts = new Hashtable();
    private Hashtable keyCerts = new Hashtable();
    protected SecureRandom random = new SecureRandom();

    public static class BCPKCS12KeyStore extends JDKPKCS12KeyStore {
        public BCPKCS12KeyStore() {
            super(JDKPKCS12KeyStore.bcProvider, PKCSObjectIdentifiers.pbeWithSHAAnd3_KeyTripleDES_CBC, PKCSObjectIdentifiers.pbewithSHAAnd40BitRC2_CBC);
        }
    }

    public static class BCPKCS12KeyStore3DES extends JDKPKCS12KeyStore {
        /* JADX WARN: Illegal instructions before constructor call */
        public BCPKCS12KeyStore3DES() {
            Provider provider = JDKPKCS12KeyStore.bcProvider;
            ASN1ObjectIdentifier aSN1ObjectIdentifier = PKCSObjectIdentifiers.pbeWithSHAAnd3_KeyTripleDES_CBC;
            super(provider, aSN1ObjectIdentifier, aSN1ObjectIdentifier);
        }
    }

    public class CertId {
        byte[] id;

        public CertId(PublicKey publicKey) {
            this.id = JDKPKCS12KeyStore.this.createSubjectKeyId(publicKey).getKeyIdentifier();
        }

        public CertId(byte[] bArr) {
            this.id = bArr;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj instanceof CertId) {
                return Arrays.areEqual(this.id, ((CertId) obj).id);
            }
            return false;
        }

        public int hashCode() {
            return Arrays.hashCode(this.id);
        }
    }

    public static class DefPKCS12KeyStore extends JDKPKCS12KeyStore {
        public DefPKCS12KeyStore() {
            super(null, PKCSObjectIdentifiers.pbeWithSHAAnd3_KeyTripleDES_CBC, PKCSObjectIdentifiers.pbewithSHAAnd40BitRC2_CBC);
        }
    }

    public static class DefPKCS12KeyStore3DES extends JDKPKCS12KeyStore {
        /* JADX WARN: Illegal instructions before constructor call */
        public DefPKCS12KeyStore3DES() {
            ASN1ObjectIdentifier aSN1ObjectIdentifier = PKCSObjectIdentifiers.pbeWithSHAAnd3_KeyTripleDES_CBC;
            super(null, aSN1ObjectIdentifier, aSN1ObjectIdentifier);
        }
    }

    public static class IgnoresCaseHashtable {
        private Hashtable keys;
        private Hashtable orig;

        private IgnoresCaseHashtable() {
            this.orig = new Hashtable();
            this.keys = new Hashtable();
        }

        public Enumeration elements() {
            return this.orig.elements();
        }

        public Object get(String str) {
            String str2 = (String) this.keys.get(Strings.toLowerCase(str));
            if (str2 == null) {
                return null;
            }
            return this.orig.get(str2);
        }

        public Enumeration keys() {
            return this.orig.keys();
        }

        public void put(String str, Object obj) {
            String lowerCase = Strings.toLowerCase(str);
            String str2 = (String) this.keys.get(lowerCase);
            if (str2 != null) {
                this.orig.remove(str2);
            }
            this.keys.put(lowerCase, str);
            this.orig.put(str, obj);
        }

        public Object remove(String str) {
            String str2 = (String) this.keys.remove(Strings.toLowerCase(str));
            if (str2 == null) {
                return null;
            }
            return this.orig.remove(str2);
        }
    }

    public JDKPKCS12KeyStore(Provider provider, DERObjectIdentifier dERObjectIdentifier, DERObjectIdentifier dERObjectIdentifier2) {
        this.keys = new IgnoresCaseHashtable();
        this.certs = new IgnoresCaseHashtable();
        this.keyAlgorithm = dERObjectIdentifier;
        this.certAlgorithm = dERObjectIdentifier2;
        try {
            this.certFact = provider != null ? CertificateFactory.getInstance(KeyUtil.CERT_TYPE_X509, provider) : CertificateFactory.getInstance(KeyUtil.CERT_TYPE_X509);
        } catch (Exception e2) {
            throw new IllegalArgumentException("can't create cert factory - " + e2.toString());
        }
    }

    private static byte[] calculatePbeMac(DERObjectIdentifier dERObjectIdentifier, byte[] bArr, int i2, char[] cArr, boolean z2, byte[] bArr2) throws Exception {
        String id = dERObjectIdentifier.getId();
        Provider provider = bcProvider;
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(id, provider);
        PBEParameterSpec pBEParameterSpec = new PBEParameterSpec(bArr, i2);
        JCEPBEKey jCEPBEKey = (JCEPBEKey) secretKeyFactory.generateSecret(new PBEKeySpec(cArr));
        jCEPBEKey.setTryWrongPKCS12Zero(z2);
        Mac mac = Mac.getInstance(dERObjectIdentifier.getId(), provider);
        mac.init(jCEPBEKey, pBEParameterSpec);
        mac.update(bArr2);
        return mac.doFinal();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public SubjectKeyIdentifier createSubjectKeyId(PublicKey publicKey) {
        try {
            return new SubjectKeyIdentifier(new SubjectPublicKeyInfo((ASN1Sequence) ASN1Object.fromByteArray(publicKey.getEncoded())));
        } catch (Exception unused) {
            throw new RuntimeException("error creating key");
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void doStore(OutputStream outputStream, char[] cArr, boolean z2) throws NoSuchPaddingException, NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidAlgorithmParameterException {
        Enumeration enumeration;
        boolean z3;
        Enumeration enumeration2;
        boolean z4;
        boolean z5;
        if (cArr == null) {
            throw new NullPointerException("No password supplied for PKCS#12 KeyStore.");
        }
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        Enumeration enumerationKeys = this.keys.keys();
        while (enumerationKeys.hasMoreElements()) {
            byte[] bArr = new byte[20];
            this.random.nextBytes(bArr);
            String str = (String) enumerationKeys.nextElement();
            PrivateKey privateKey = (PrivateKey) this.keys.get(str);
            PKCS12PBEParams pKCS12PBEParams = new PKCS12PBEParams(bArr, 1024);
            EncryptedPrivateKeyInfo encryptedPrivateKeyInfo = new EncryptedPrivateKeyInfo(new AlgorithmIdentifier(this.keyAlgorithm, pKCS12PBEParams.getDERObject()), wrapKey(this.keyAlgorithm.getId(), privateKey, pKCS12PBEParams, cArr));
            ASN1EncodableVector aSN1EncodableVector2 = new ASN1EncodableVector();
            if (privateKey instanceof PKCS12BagAttributeCarrier) {
                PKCS12BagAttributeCarrier pKCS12BagAttributeCarrier = (PKCS12BagAttributeCarrier) privateKey;
                ASN1ObjectIdentifier aSN1ObjectIdentifier = PKCSObjectIdentifiers.pkcs_9_at_friendlyName;
                DERBMPString dERBMPString = (DERBMPString) pKCS12BagAttributeCarrier.getBagAttribute(aSN1ObjectIdentifier);
                if (dERBMPString == null || !dERBMPString.getString().equals(str)) {
                    pKCS12BagAttributeCarrier.setBagAttribute(aSN1ObjectIdentifier, new DERBMPString(str));
                }
                ASN1ObjectIdentifier aSN1ObjectIdentifier2 = PKCSObjectIdentifiers.pkcs_9_at_localKeyId;
                if (pKCS12BagAttributeCarrier.getBagAttribute(aSN1ObjectIdentifier2) == null) {
                    pKCS12BagAttributeCarrier.setBagAttribute(aSN1ObjectIdentifier2, createSubjectKeyId(engineGetCertificate(str).getPublicKey()));
                }
                Enumeration bagAttributeKeys = pKCS12BagAttributeCarrier.getBagAttributeKeys();
                z5 = false;
                while (bagAttributeKeys.hasMoreElements()) {
                    DERObjectIdentifier dERObjectIdentifier = (DERObjectIdentifier) bagAttributeKeys.nextElement();
                    ASN1EncodableVector aSN1EncodableVector3 = new ASN1EncodableVector();
                    aSN1EncodableVector3.add(dERObjectIdentifier);
                    aSN1EncodableVector3.add(new DERSet(pKCS12BagAttributeCarrier.getBagAttribute(dERObjectIdentifier)));
                    aSN1EncodableVector2.add(new DERSequence(aSN1EncodableVector3));
                    z5 = true;
                }
            } else {
                z5 = false;
            }
            if (!z5) {
                ASN1EncodableVector aSN1EncodableVector4 = new ASN1EncodableVector();
                Certificate certificateEngineGetCertificate = engineGetCertificate(str);
                aSN1EncodableVector4.add(PKCSObjectIdentifiers.pkcs_9_at_localKeyId);
                aSN1EncodableVector4.add(new DERSet(createSubjectKeyId(certificateEngineGetCertificate.getPublicKey())));
                aSN1EncodableVector2.add(new DERSequence(aSN1EncodableVector4));
                ASN1EncodableVector aSN1EncodableVector5 = new ASN1EncodableVector();
                aSN1EncodableVector5.add(PKCSObjectIdentifiers.pkcs_9_at_friendlyName);
                aSN1EncodableVector5.add(new DERSet(new DERBMPString(str)));
                aSN1EncodableVector2.add(new DERSequence(aSN1EncodableVector5));
            }
            aSN1EncodableVector.add(new SafeBag(PKCSObjectIdentifiers.pkcs8ShroudedKeyBag, encryptedPrivateKeyInfo.getDERObject(), new DERSet(aSN1EncodableVector2)));
        }
        BERConstructedOctetString bERConstructedOctetString = new BERConstructedOctetString(new DERSequence(aSN1EncodableVector).getDEREncoded());
        byte[] bArr2 = new byte[20];
        this.random.nextBytes(bArr2);
        ASN1EncodableVector aSN1EncodableVector6 = new ASN1EncodableVector();
        AlgorithmIdentifier algorithmIdentifier = new AlgorithmIdentifier(this.certAlgorithm, new PKCS12PBEParams(bArr2, 1024).getDERObject());
        Hashtable hashtable = new Hashtable();
        Enumeration enumerationKeys2 = this.keys.keys();
        while (enumerationKeys2.hasMoreElements()) {
            try {
                String str2 = (String) enumerationKeys2.nextElement();
                Certificate certificateEngineGetCertificate2 = engineGetCertificate(str2);
                CertBag certBag = new CertBag(PKCSObjectIdentifiers.x509Certificate, new DEROctetString(certificateEngineGetCertificate2.getEncoded()));
                ASN1EncodableVector aSN1EncodableVector7 = new ASN1EncodableVector();
                if (certificateEngineGetCertificate2 instanceof PKCS12BagAttributeCarrier) {
                    PKCS12BagAttributeCarrier pKCS12BagAttributeCarrier2 = (PKCS12BagAttributeCarrier) certificateEngineGetCertificate2;
                    ASN1ObjectIdentifier aSN1ObjectIdentifier3 = PKCSObjectIdentifiers.pkcs_9_at_friendlyName;
                    DERBMPString dERBMPString2 = (DERBMPString) pKCS12BagAttributeCarrier2.getBagAttribute(aSN1ObjectIdentifier3);
                    if (dERBMPString2 == null || !dERBMPString2.getString().equals(str2)) {
                        pKCS12BagAttributeCarrier2.setBagAttribute(aSN1ObjectIdentifier3, new DERBMPString(str2));
                    }
                    ASN1ObjectIdentifier aSN1ObjectIdentifier4 = PKCSObjectIdentifiers.pkcs_9_at_localKeyId;
                    if (pKCS12BagAttributeCarrier2.getBagAttribute(aSN1ObjectIdentifier4) == null) {
                        pKCS12BagAttributeCarrier2.setBagAttribute(aSN1ObjectIdentifier4, createSubjectKeyId(certificateEngineGetCertificate2.getPublicKey()));
                    }
                    Enumeration bagAttributeKeys2 = pKCS12BagAttributeCarrier2.getBagAttributeKeys();
                    z4 = false;
                    while (bagAttributeKeys2.hasMoreElements()) {
                        DERObjectIdentifier dERObjectIdentifier2 = (DERObjectIdentifier) bagAttributeKeys2.nextElement();
                        Enumeration enumeration3 = enumerationKeys2;
                        ASN1EncodableVector aSN1EncodableVector8 = new ASN1EncodableVector();
                        aSN1EncodableVector8.add(dERObjectIdentifier2);
                        aSN1EncodableVector8.add(new DERSet(pKCS12BagAttributeCarrier2.getBagAttribute(dERObjectIdentifier2)));
                        aSN1EncodableVector7.add(new DERSequence(aSN1EncodableVector8));
                        enumerationKeys2 = enumeration3;
                        z4 = true;
                    }
                    enumeration2 = enumerationKeys2;
                } else {
                    enumeration2 = enumerationKeys2;
                    z4 = false;
                }
                if (!z4) {
                    ASN1EncodableVector aSN1EncodableVector9 = new ASN1EncodableVector();
                    aSN1EncodableVector9.add(PKCSObjectIdentifiers.pkcs_9_at_localKeyId);
                    aSN1EncodableVector9.add(new DERSet(createSubjectKeyId(certificateEngineGetCertificate2.getPublicKey())));
                    aSN1EncodableVector7.add(new DERSequence(aSN1EncodableVector9));
                    ASN1EncodableVector aSN1EncodableVector10 = new ASN1EncodableVector();
                    aSN1EncodableVector10.add(PKCSObjectIdentifiers.pkcs_9_at_friendlyName);
                    aSN1EncodableVector10.add(new DERSet(new DERBMPString(str2)));
                    aSN1EncodableVector7.add(new DERSequence(aSN1EncodableVector10));
                }
                aSN1EncodableVector6.add(new SafeBag(PKCSObjectIdentifiers.certBag, certBag.getDERObject(), new DERSet(aSN1EncodableVector7)));
                hashtable.put(certificateEngineGetCertificate2, certificateEngineGetCertificate2);
                enumerationKeys2 = enumeration2;
            } catch (CertificateEncodingException e2) {
                throw new IOException("Error encoding certificate: " + e2.toString());
            }
        }
        Enumeration enumerationKeys3 = this.certs.keys();
        while (enumerationKeys3.hasMoreElements()) {
            try {
                String str3 = (String) enumerationKeys3.nextElement();
                Certificate certificate = (Certificate) this.certs.get(str3);
                if (this.keys.get(str3) == null) {
                    CertBag certBag2 = new CertBag(PKCSObjectIdentifiers.x509Certificate, new DEROctetString(certificate.getEncoded()));
                    ASN1EncodableVector aSN1EncodableVector11 = new ASN1EncodableVector();
                    if (certificate instanceof PKCS12BagAttributeCarrier) {
                        PKCS12BagAttributeCarrier pKCS12BagAttributeCarrier3 = (PKCS12BagAttributeCarrier) certificate;
                        ASN1ObjectIdentifier aSN1ObjectIdentifier5 = PKCSObjectIdentifiers.pkcs_9_at_friendlyName;
                        DERBMPString dERBMPString3 = (DERBMPString) pKCS12BagAttributeCarrier3.getBagAttribute(aSN1ObjectIdentifier5);
                        if (dERBMPString3 == null || !dERBMPString3.getString().equals(str3)) {
                            pKCS12BagAttributeCarrier3.setBagAttribute(aSN1ObjectIdentifier5, new DERBMPString(str3));
                        }
                        Enumeration bagAttributeKeys3 = pKCS12BagAttributeCarrier3.getBagAttributeKeys();
                        z3 = false;
                        while (bagAttributeKeys3.hasMoreElements()) {
                            DERObjectIdentifier dERObjectIdentifier3 = (DERObjectIdentifier) bagAttributeKeys3.nextElement();
                            Enumeration enumeration4 = enumerationKeys3;
                            if (dERObjectIdentifier3.equals(PKCSObjectIdentifiers.pkcs_9_at_localKeyId)) {
                                enumerationKeys3 = enumeration4;
                            } else {
                                ASN1EncodableVector aSN1EncodableVector12 = new ASN1EncodableVector();
                                aSN1EncodableVector12.add(dERObjectIdentifier3);
                                aSN1EncodableVector12.add(new DERSet(pKCS12BagAttributeCarrier3.getBagAttribute(dERObjectIdentifier3)));
                                aSN1EncodableVector11.add(new DERSequence(aSN1EncodableVector12));
                                enumerationKeys3 = enumeration4;
                                z3 = true;
                            }
                        }
                        enumeration = enumerationKeys3;
                    } else {
                        enumeration = enumerationKeys3;
                        z3 = false;
                    }
                    if (!z3) {
                        ASN1EncodableVector aSN1EncodableVector13 = new ASN1EncodableVector();
                        aSN1EncodableVector13.add(PKCSObjectIdentifiers.pkcs_9_at_friendlyName);
                        aSN1EncodableVector13.add(new DERSet(new DERBMPString(str3)));
                        aSN1EncodableVector11.add(new DERSequence(aSN1EncodableVector13));
                    }
                    aSN1EncodableVector6.add(new SafeBag(PKCSObjectIdentifiers.certBag, certBag2.getDERObject(), new DERSet(aSN1EncodableVector11)));
                    hashtable.put(certificate, certificate);
                    enumerationKeys3 = enumeration;
                }
            } catch (CertificateEncodingException e3) {
                throw new IOException("Error encoding certificate: " + e3.toString());
            }
        }
        Enumeration enumerationKeys4 = this.chainCerts.keys();
        while (enumerationKeys4.hasMoreElements()) {
            try {
                Certificate certificate2 = (Certificate) this.chainCerts.get((CertId) enumerationKeys4.nextElement());
                if (hashtable.get(certificate2) == null) {
                    CertBag certBag3 = new CertBag(PKCSObjectIdentifiers.x509Certificate, new DEROctetString(certificate2.getEncoded()));
                    ASN1EncodableVector aSN1EncodableVector14 = new ASN1EncodableVector();
                    if (certificate2 instanceof PKCS12BagAttributeCarrier) {
                        PKCS12BagAttributeCarrier pKCS12BagAttributeCarrier4 = (PKCS12BagAttributeCarrier) certificate2;
                        Enumeration bagAttributeKeys4 = pKCS12BagAttributeCarrier4.getBagAttributeKeys();
                        while (bagAttributeKeys4.hasMoreElements()) {
                            DERObjectIdentifier dERObjectIdentifier4 = (DERObjectIdentifier) bagAttributeKeys4.nextElement();
                            if (!dERObjectIdentifier4.equals(PKCSObjectIdentifiers.pkcs_9_at_localKeyId)) {
                                ASN1EncodableVector aSN1EncodableVector15 = new ASN1EncodableVector();
                                aSN1EncodableVector15.add(dERObjectIdentifier4);
                                aSN1EncodableVector15.add(new DERSet(pKCS12BagAttributeCarrier4.getBagAttribute(dERObjectIdentifier4)));
                                aSN1EncodableVector14.add(new DERSequence(aSN1EncodableVector15));
                            }
                        }
                    }
                    aSN1EncodableVector6.add(new SafeBag(PKCSObjectIdentifiers.certBag, certBag3.getDERObject(), new DERSet(aSN1EncodableVector14)));
                }
            } catch (CertificateEncodingException e4) {
                throw new IOException("Error encoding certificate: " + e4.toString());
            }
        }
        byte[] bArrCryptData = cryptData(true, algorithmIdentifier, cArr, false, new DERSequence(aSN1EncodableVector6).getDEREncoded());
        ASN1ObjectIdentifier aSN1ObjectIdentifier6 = PKCSObjectIdentifiers.data;
        AuthenticatedSafe authenticatedSafe = new AuthenticatedSafe(new ContentInfo[]{new ContentInfo(aSN1ObjectIdentifier6, bERConstructedOctetString), new ContentInfo(PKCSObjectIdentifiers.encryptedData, new EncryptedData(aSN1ObjectIdentifier6, algorithmIdentifier, new BERConstructedOctetString(bArrCryptData)).getDERObject())});
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        (z2 ? new DEROutputStream(byteArrayOutputStream) : new BEROutputStream(byteArrayOutputStream)).writeObject(authenticatedSafe);
        ContentInfo contentInfo = new ContentInfo(aSN1ObjectIdentifier6, new BERConstructedOctetString(byteArrayOutputStream.toByteArray()));
        byte[] bArr3 = new byte[20];
        this.random.nextBytes(bArr3);
        byte[] octets = ((ASN1OctetString) contentInfo.getContent()).getOctets();
        try {
            ASN1ObjectIdentifier aSN1ObjectIdentifier7 = X509ObjectIdentifiers.id_SHA1;
            (z2 ? new DEROutputStream(outputStream) : new BEROutputStream(outputStream)).writeObject(new Pfx(contentInfo, new MacData(new DigestInfo(new AlgorithmIdentifier(aSN1ObjectIdentifier7, new DERNull()), calculatePbeMac(aSN1ObjectIdentifier7, bArr3, 1024, cArr, false, octets)), bArr3, 1024)));
        } catch (Exception e5) {
            throw new IOException("error constructing MAC: " + e5.toString());
        }
    }

    public byte[] cryptData(boolean z2, AlgorithmIdentifier algorithmIdentifier, char[] cArr, boolean z3, byte[] bArr) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException, InvalidAlgorithmParameterException {
        String id = algorithmIdentifier.getObjectId().getId();
        PKCS12PBEParams pKCS12PBEParams = new PKCS12PBEParams((ASN1Sequence) algorithmIdentifier.getParameters());
        PBEKeySpec pBEKeySpec = new PBEKeySpec(cArr);
        try {
            Provider provider = bcProvider;
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(id, provider);
            PBEParameterSpec pBEParameterSpec = new PBEParameterSpec(pKCS12PBEParams.getIV(), pKCS12PBEParams.getIterations().intValue());
            JCEPBEKey jCEPBEKey = (JCEPBEKey) secretKeyFactory.generateSecret(pBEKeySpec);
            jCEPBEKey.setTryWrongPKCS12Zero(z3);
            Cipher cipher = Cipher.getInstance(id, provider);
            cipher.init(z2 ? 1 : 2, jCEPBEKey, pBEParameterSpec);
            return cipher.doFinal(bArr);
        } catch (Exception e2) {
            throw new IOException("exception decrypting data - " + e2.toString());
        }
    }

    @Override // java.security.KeyStoreSpi
    public Enumeration engineAliases() {
        Hashtable hashtable = new Hashtable();
        Enumeration enumerationKeys = this.certs.keys();
        while (enumerationKeys.hasMoreElements()) {
            hashtable.put(enumerationKeys.nextElement(), "cert");
        }
        Enumeration enumerationKeys2 = this.keys.keys();
        while (enumerationKeys2.hasMoreElements()) {
            String str = (String) enumerationKeys2.nextElement();
            if (hashtable.get(str) == null) {
                hashtable.put(str, "key");
            }
        }
        return hashtable.keys();
    }

    @Override // java.security.KeyStoreSpi
    public boolean engineContainsAlias(String str) {
        return (this.certs.get(str) == null && this.keys.get(str) == null) ? false : true;
    }

    @Override // java.security.KeyStoreSpi
    public void engineDeleteEntry(String str) throws KeyStoreException {
        Key key = (Key) this.keys.remove(str);
        Certificate certificate = (Certificate) this.certs.remove(str);
        if (certificate != null) {
            this.chainCerts.remove(new CertId(certificate.getPublicKey()));
        }
        if (key != null) {
            String str2 = (String) this.localIds.remove(str);
            if (str2 != null) {
                certificate = (Certificate) this.keyCerts.remove(str2);
            }
            if (certificate != null) {
                this.chainCerts.remove(new CertId(certificate.getPublicKey()));
            }
        }
        if (certificate == null && key == null) {
            throw new KeyStoreException("no such entry as " + str);
        }
    }

    @Override // java.security.KeyStoreSpi
    public Certificate engineGetCertificate(String str) {
        if (str == null) {
            throw new IllegalArgumentException("null alias passed to getCertificate.");
        }
        Certificate certificate = (Certificate) this.certs.get(str);
        if (certificate != null) {
            return certificate;
        }
        String str2 = (String) this.localIds.get(str);
        return (Certificate) (str2 != null ? this.keyCerts.get(str2) : this.keyCerts.get(str));
    }

    @Override // java.security.KeyStoreSpi
    public String engineGetCertificateAlias(Certificate certificate) {
        Enumeration enumerationElements = this.certs.elements();
        Enumeration enumerationKeys = this.certs.keys();
        while (enumerationElements.hasMoreElements()) {
            Certificate certificate2 = (Certificate) enumerationElements.nextElement();
            String str = (String) enumerationKeys.nextElement();
            if (certificate2.equals(certificate)) {
                return str;
            }
        }
        Enumeration enumerationElements2 = this.keyCerts.elements();
        Enumeration enumerationKeys2 = this.keyCerts.keys();
        while (enumerationElements2.hasMoreElements()) {
            Certificate certificate3 = (Certificate) enumerationElements2.nextElement();
            String str2 = (String) enumerationKeys2.nextElement();
            if (certificate3.equals(certificate)) {
                return str2;
            }
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0068  */
    @Override // java.security.KeyStoreSpi
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.security.cert.Certificate[] engineGetCertificateChain(java.lang.String r9) throws java.security.NoSuchAlgorithmException, java.security.SignatureException, java.security.InvalidKeyException, java.security.cert.CertificateException, java.security.NoSuchProviderException {
        /*
            r8 = this;
            if (r9 == 0) goto Lc3
            boolean r0 = r8.engineIsKeyEntry(r9)
            r1 = 0
            if (r0 != 0) goto La
            return r1
        La:
            java.security.cert.Certificate r9 = r8.engineGetCertificate(r9)
            if (r9 == 0) goto Lc2
            java.util.Vector r0 = new java.util.Vector
            r0.<init>()
        L15:
            if (r9 == 0) goto Lae
            r2 = r9
            java.security.cert.X509Certificate r2 = (java.security.cert.X509Certificate) r2
            org.bouncycastle.asn1.ASN1ObjectIdentifier r3 = org.bouncycastle.asn1.x509.X509Extensions.AuthorityKeyIdentifier
            java.lang.String r3 = r3.getId()
            byte[] r3 = r2.getExtensionValue(r3)
            if (r3 == 0) goto L68
            org.bouncycastle.asn1.ASN1InputStream r4 = new org.bouncycastle.asn1.ASN1InputStream     // Catch: java.io.IOException -> L5d
            r4.<init>(r3)     // Catch: java.io.IOException -> L5d
            org.bouncycastle.asn1.DERObject r3 = r4.readObject()     // Catch: java.io.IOException -> L5d
            org.bouncycastle.asn1.ASN1OctetString r3 = (org.bouncycastle.asn1.ASN1OctetString) r3     // Catch: java.io.IOException -> L5d
            byte[] r3 = r3.getOctets()     // Catch: java.io.IOException -> L5d
            org.bouncycastle.asn1.ASN1InputStream r4 = new org.bouncycastle.asn1.ASN1InputStream     // Catch: java.io.IOException -> L5d
            r4.<init>(r3)     // Catch: java.io.IOException -> L5d
            org.bouncycastle.asn1.x509.AuthorityKeyIdentifier r3 = new org.bouncycastle.asn1.x509.AuthorityKeyIdentifier     // Catch: java.io.IOException -> L5d
            org.bouncycastle.asn1.DERObject r4 = r4.readObject()     // Catch: java.io.IOException -> L5d
            org.bouncycastle.asn1.ASN1Sequence r4 = (org.bouncycastle.asn1.ASN1Sequence) r4     // Catch: java.io.IOException -> L5d
            r3.<init>(r4)     // Catch: java.io.IOException -> L5d
            byte[] r4 = r3.getKeyIdentifier()     // Catch: java.io.IOException -> L5d
            if (r4 == 0) goto L68
            java.util.Hashtable r4 = r8.chainCerts     // Catch: java.io.IOException -> L5d
            org.bouncycastle.jce.provider.JDKPKCS12KeyStore$CertId r5 = new org.bouncycastle.jce.provider.JDKPKCS12KeyStore$CertId     // Catch: java.io.IOException -> L5d
            byte[] r3 = r3.getKeyIdentifier()     // Catch: java.io.IOException -> L5d
            r5.<init>(r3)     // Catch: java.io.IOException -> L5d
            java.lang.Object r3 = r4.get(r5)     // Catch: java.io.IOException -> L5d
            java.security.cert.Certificate r3 = (java.security.cert.Certificate) r3     // Catch: java.io.IOException -> L5d
            goto L69
        L5d:
            r9 = move-exception
            java.lang.RuntimeException r0 = new java.lang.RuntimeException
            java.lang.String r9 = r9.toString()
            r0.<init>(r9)
            throw r0
        L68:
            r3 = r1
        L69:
            if (r3 != 0) goto La3
            java.security.Principal r4 = r2.getIssuerDN()
            java.security.Principal r5 = r2.getSubjectDN()
            boolean r5 = r4.equals(r5)
            if (r5 != 0) goto La3
            java.util.Hashtable r5 = r8.chainCerts
            java.util.Enumeration r5 = r5.keys()
        L7f:
            boolean r6 = r5.hasMoreElements()
            if (r6 == 0) goto La3
            java.util.Hashtable r6 = r8.chainCerts
            java.lang.Object r7 = r5.nextElement()
            java.lang.Object r6 = r6.get(r7)
            java.security.cert.X509Certificate r6 = (java.security.cert.X509Certificate) r6
            java.security.Principal r7 = r6.getSubjectDN()
            boolean r7 = r7.equals(r4)
            if (r7 == 0) goto L7f
            java.security.PublicKey r7 = r6.getPublicKey()     // Catch: java.lang.Exception -> L7f
            r2.verify(r7)     // Catch: java.lang.Exception -> L7f
            r3 = r6
        La3:
            r0.addElement(r9)
            if (r3 == r9) goto Lab
            r9 = r3
            goto L15
        Lab:
            r9 = r1
            goto L15
        Lae:
            int r9 = r0.size()
            java.security.cert.Certificate[] r1 = new java.security.cert.Certificate[r9]
            r2 = 0
        Lb5:
            if (r2 == r9) goto Lc2
            java.lang.Object r3 = r0.elementAt(r2)
            java.security.cert.Certificate r3 = (java.security.cert.Certificate) r3
            r1[r2] = r3
            int r2 = r2 + 1
            goto Lb5
        Lc2:
            return r1
        Lc3:
            java.lang.IllegalArgumentException r9 = new java.lang.IllegalArgumentException
            java.lang.String r0 = "null alias passed to getCertificateChain."
            r9.<init>(r0)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jce.provider.JDKPKCS12KeyStore.engineGetCertificateChain(java.lang.String):java.security.cert.Certificate[]");
    }

    @Override // java.security.KeyStoreSpi
    public Date engineGetCreationDate(String str) {
        return new Date();
    }

    @Override // java.security.KeyStoreSpi
    public Key engineGetKey(String str, char[] cArr) throws NoSuchAlgorithmException, UnrecoverableKeyException {
        if (str != null) {
            return (Key) this.keys.get(str);
        }
        throw new IllegalArgumentException("null alias passed to getKey.");
    }

    @Override // java.security.KeyStoreSpi
    public boolean engineIsCertificateEntry(String str) {
        return this.certs.get(str) != null && this.keys.get(str) == null;
    }

    @Override // java.security.KeyStoreSpi
    public boolean engineIsKeyEntry(String str) {
        return this.keys.get(str) != null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00c4  */
    /* JADX WARN: Type inference failed for: r0v21, types: [org.bouncycastle.jce.provider.JDKPKCS12KeyStore$IgnoresCaseHashtable] */
    /* JADX WARN: Type inference failed for: r10v22, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r11v16, types: [org.bouncycastle.jce.provider.JDKPKCS12KeyStore$IgnoresCaseHashtable] */
    /* JADX WARN: Type inference failed for: r18v11, types: [org.bouncycastle.asn1.ASN1OctetString] */
    /* JADX WARN: Type inference failed for: r18v12 */
    /* JADX WARN: Type inference failed for: r18v13 */
    /* JADX WARN: Type inference failed for: r18v8 */
    /* JADX WARN: Type inference failed for: r18v9 */
    /* JADX WARN: Type inference failed for: r1v40, types: [org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier] */
    /* JADX WARN: Type inference failed for: r2v8, types: [java.lang.Object, java.security.cert.Certificate] */
    /* JADX WARN: Type inference failed for: r4v18, types: [org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier] */
    @Override // java.security.KeyStoreSpi
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void engineLoad(java.io.InputStream r21, char[] r22) throws java.security.spec.InvalidKeySpecException, javax.crypto.NoSuchPaddingException, java.security.NoSuchAlgorithmException, java.io.IOException, java.security.InvalidKeyException, java.security.cert.CertificateException, java.security.InvalidAlgorithmParameterException {
        /*
            Method dump skipped, instructions count: 1506
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jce.provider.JDKPKCS12KeyStore.engineLoad(java.io.InputStream, char[]):void");
    }

    @Override // java.security.KeyStoreSpi
    public void engineSetCertificateEntry(String str, Certificate certificate) throws KeyStoreException {
        if (this.keys.get(str) == null) {
            this.certs.put(str, certificate);
            this.chainCerts.put(new CertId(certificate.getPublicKey()), certificate);
        } else {
            throw new KeyStoreException("There is a key entry with the name " + str + StrPool.DOT);
        }
    }

    @Override // java.security.KeyStoreSpi
    public void engineSetKeyEntry(String str, Key key, char[] cArr, Certificate[] certificateArr) throws KeyStoreException {
        if ((key instanceof PrivateKey) && certificateArr == null) {
            throw new KeyStoreException("no certificate chain for private key");
        }
        if (this.keys.get(str) != null) {
            engineDeleteEntry(str);
        }
        this.keys.put(str, key);
        this.certs.put(str, certificateArr[0]);
        for (int i2 = 0; i2 != certificateArr.length; i2++) {
            this.chainCerts.put(new CertId(certificateArr[i2].getPublicKey()), certificateArr[i2]);
        }
    }

    @Override // java.security.KeyStoreSpi
    public void engineSetKeyEntry(String str, byte[] bArr, Certificate[] certificateArr) throws KeyStoreException {
        throw new RuntimeException("operation not supported");
    }

    @Override // java.security.KeyStoreSpi
    public int engineSize() {
        Hashtable hashtable = new Hashtable();
        Enumeration enumerationKeys = this.certs.keys();
        while (enumerationKeys.hasMoreElements()) {
            hashtable.put(enumerationKeys.nextElement(), "cert");
        }
        Enumeration enumerationKeys2 = this.keys.keys();
        while (enumerationKeys2.hasMoreElements()) {
            String str = (String) enumerationKeys2.nextElement();
            if (hashtable.get(str) == null) {
                hashtable.put(str, "key");
            }
        }
        return hashtable.size();
    }

    @Override // java.security.KeyStoreSpi
    public void engineStore(OutputStream outputStream, char[] cArr) throws NoSuchPaddingException, NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidAlgorithmParameterException {
        doStore(outputStream, cArr, false);
    }

    @Override // java.security.KeyStoreSpi
    public void engineStore(KeyStore.LoadStoreParameter loadStoreParameter) throws NoSuchPaddingException, NoSuchAlgorithmException, IOException, InvalidKeyException, CertificateException, InvalidAlgorithmParameterException {
        char[] password;
        if (loadStoreParameter == null) {
            throw new IllegalArgumentException("'param' arg cannot be null");
        }
        if (!(loadStoreParameter instanceof JDKPKCS12StoreParameter)) {
            throw new IllegalArgumentException("No support for 'param' of type " + loadStoreParameter.getClass().getName());
        }
        JDKPKCS12StoreParameter jDKPKCS12StoreParameter = (JDKPKCS12StoreParameter) loadStoreParameter;
        KeyStore.ProtectionParameter protectionParameter = loadStoreParameter.getProtectionParameter();
        if (protectionParameter == null) {
            password = null;
        } else {
            if (!(protectionParameter instanceof KeyStore.PasswordProtection)) {
                throw new IllegalArgumentException("No support for protection parameter of type " + protectionParameter.getClass().getName());
            }
            password = ((KeyStore.PasswordProtection) protectionParameter).getPassword();
        }
        doStore(jDKPKCS12StoreParameter.getOutputStream(), password, jDKPKCS12StoreParameter.isUseDEREncoding());
    }

    @Override // org.bouncycastle.jce.interfaces.BCKeyStore
    public void setRandom(SecureRandom secureRandom) {
        this.random = secureRandom;
    }

    public PrivateKey unwrapKey(AlgorithmIdentifier algorithmIdentifier, byte[] bArr, char[] cArr, boolean z2) throws InvalidKeySpecException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException, InvalidAlgorithmParameterException {
        String id = algorithmIdentifier.getObjectId().getId();
        PKCS12PBEParams pKCS12PBEParams = new PKCS12PBEParams((ASN1Sequence) algorithmIdentifier.getParameters());
        PBEKeySpec pBEKeySpec = new PBEKeySpec(cArr);
        try {
            Provider provider = bcProvider;
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(id, provider);
            PBEParameterSpec pBEParameterSpec = new PBEParameterSpec(pKCS12PBEParams.getIV(), pKCS12PBEParams.getIterations().intValue());
            SecretKey secretKeyGenerateSecret = secretKeyFactory.generateSecret(pBEKeySpec);
            ((JCEPBEKey) secretKeyGenerateSecret).setTryWrongPKCS12Zero(z2);
            Cipher cipher = Cipher.getInstance(id, provider);
            cipher.init(4, secretKeyGenerateSecret, pBEParameterSpec);
            return (PrivateKey) cipher.unwrap(bArr, "", 2);
        } catch (Exception e2) {
            throw new IOException("exception unwrapping private key - " + e2.toString());
        }
    }

    public byte[] wrapKey(String str, Key key, PKCS12PBEParams pKCS12PBEParams, char[] cArr) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException, InvalidAlgorithmParameterException {
        PBEKeySpec pBEKeySpec = new PBEKeySpec(cArr);
        try {
            Provider provider = bcProvider;
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(str, provider);
            PBEParameterSpec pBEParameterSpec = new PBEParameterSpec(pKCS12PBEParams.getIV(), pKCS12PBEParams.getIterations().intValue());
            Cipher cipher = Cipher.getInstance(str, provider);
            cipher.init(3, secretKeyFactory.generateSecret(pBEKeySpec), pBEParameterSpec);
            return cipher.wrap(key);
        } catch (Exception e2) {
            throw new IOException("exception encrypting data - " + e2.toString());
        }
    }
}
