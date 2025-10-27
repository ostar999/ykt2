package org.bouncycastle.jce;

import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PSSParameterSpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.DEREncodable;
import org.bouncycastle.asn1.DERInteger;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.CertificationRequest;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.RSASSAPSSparams;
import org.bouncycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.asn1.x509.X509Name;
import org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/* loaded from: classes9.dex */
public class PKCS10CertificationRequest extends CertificationRequest {
    private static Hashtable algorithms = new Hashtable();
    private static Hashtable params = new Hashtable();
    private static Hashtable keyAlgorithms = new Hashtable();
    private static Hashtable oids = new Hashtable();
    private static Set noParams = new HashSet();

    static {
        algorithms.put("MD2WITHRSAENCRYPTION", new DERObjectIdentifier("1.2.840.113549.1.1.2"));
        algorithms.put("MD2WITHRSA", new DERObjectIdentifier("1.2.840.113549.1.1.2"));
        algorithms.put("MD5WITHRSAENCRYPTION", new DERObjectIdentifier("1.2.840.113549.1.1.4"));
        algorithms.put("MD5WITHRSA", new DERObjectIdentifier("1.2.840.113549.1.1.4"));
        algorithms.put("RSAWITHMD5", new DERObjectIdentifier("1.2.840.113549.1.1.4"));
        algorithms.put("SHA1WITHRSAENCRYPTION", new DERObjectIdentifier("1.2.840.113549.1.1.5"));
        algorithms.put("SHA1WITHRSA", new DERObjectIdentifier("1.2.840.113549.1.1.5"));
        Hashtable hashtable = algorithms;
        ASN1ObjectIdentifier aSN1ObjectIdentifier = PKCSObjectIdentifiers.sha224WithRSAEncryption;
        hashtable.put("SHA224WITHRSAENCRYPTION", aSN1ObjectIdentifier);
        algorithms.put("SHA224WITHRSA", aSN1ObjectIdentifier);
        Hashtable hashtable2 = algorithms;
        ASN1ObjectIdentifier aSN1ObjectIdentifier2 = PKCSObjectIdentifiers.sha256WithRSAEncryption;
        hashtable2.put("SHA256WITHRSAENCRYPTION", aSN1ObjectIdentifier2);
        algorithms.put("SHA256WITHRSA", aSN1ObjectIdentifier2);
        Hashtable hashtable3 = algorithms;
        ASN1ObjectIdentifier aSN1ObjectIdentifier3 = PKCSObjectIdentifiers.sha384WithRSAEncryption;
        hashtable3.put("SHA384WITHRSAENCRYPTION", aSN1ObjectIdentifier3);
        algorithms.put("SHA384WITHRSA", aSN1ObjectIdentifier3);
        Hashtable hashtable4 = algorithms;
        ASN1ObjectIdentifier aSN1ObjectIdentifier4 = PKCSObjectIdentifiers.sha512WithRSAEncryption;
        hashtable4.put("SHA512WITHRSAENCRYPTION", aSN1ObjectIdentifier4);
        algorithms.put("SHA512WITHRSA", aSN1ObjectIdentifier4);
        Hashtable hashtable5 = algorithms;
        ASN1ObjectIdentifier aSN1ObjectIdentifier5 = PKCSObjectIdentifiers.id_RSASSA_PSS;
        hashtable5.put("SHA1WITHRSAANDMGF1", aSN1ObjectIdentifier5);
        algorithms.put("SHA224WITHRSAANDMGF1", aSN1ObjectIdentifier5);
        algorithms.put("SHA256WITHRSAANDMGF1", aSN1ObjectIdentifier5);
        algorithms.put("SHA384WITHRSAANDMGF1", aSN1ObjectIdentifier5);
        algorithms.put("SHA512WITHRSAANDMGF1", aSN1ObjectIdentifier5);
        algorithms.put("RSAWITHSHA1", new DERObjectIdentifier("1.2.840.113549.1.1.5"));
        Hashtable hashtable6 = algorithms;
        ASN1ObjectIdentifier aSN1ObjectIdentifier6 = TeleTrusTObjectIdentifiers.rsaSignatureWithripemd128;
        hashtable6.put("RIPEMD128WITHRSAENCRYPTION", aSN1ObjectIdentifier6);
        algorithms.put("RIPEMD128WITHRSA", aSN1ObjectIdentifier6);
        Hashtable hashtable7 = algorithms;
        ASN1ObjectIdentifier aSN1ObjectIdentifier7 = TeleTrusTObjectIdentifiers.rsaSignatureWithripemd160;
        hashtable7.put("RIPEMD160WITHRSAENCRYPTION", aSN1ObjectIdentifier7);
        algorithms.put("RIPEMD160WITHRSA", aSN1ObjectIdentifier7);
        Hashtable hashtable8 = algorithms;
        ASN1ObjectIdentifier aSN1ObjectIdentifier8 = TeleTrusTObjectIdentifiers.rsaSignatureWithripemd256;
        hashtable8.put("RIPEMD256WITHRSAENCRYPTION", aSN1ObjectIdentifier8);
        algorithms.put("RIPEMD256WITHRSA", aSN1ObjectIdentifier8);
        algorithms.put("SHA1WITHDSA", new DERObjectIdentifier("1.2.840.10040.4.3"));
        algorithms.put("DSAWITHSHA1", new DERObjectIdentifier("1.2.840.10040.4.3"));
        Hashtable hashtable9 = algorithms;
        ASN1ObjectIdentifier aSN1ObjectIdentifier9 = NISTObjectIdentifiers.dsa_with_sha224;
        hashtable9.put("SHA224WITHDSA", aSN1ObjectIdentifier9);
        Hashtable hashtable10 = algorithms;
        ASN1ObjectIdentifier aSN1ObjectIdentifier10 = NISTObjectIdentifiers.dsa_with_sha256;
        hashtable10.put("SHA256WITHDSA", aSN1ObjectIdentifier10);
        algorithms.put("SHA384WITHDSA", NISTObjectIdentifiers.dsa_with_sha384);
        algorithms.put("SHA512WITHDSA", NISTObjectIdentifiers.dsa_with_sha512);
        Hashtable hashtable11 = algorithms;
        ASN1ObjectIdentifier aSN1ObjectIdentifier11 = X9ObjectIdentifiers.ecdsa_with_SHA1;
        hashtable11.put("SHA1WITHECDSA", aSN1ObjectIdentifier11);
        Hashtable hashtable12 = algorithms;
        ASN1ObjectIdentifier aSN1ObjectIdentifier12 = X9ObjectIdentifiers.ecdsa_with_SHA224;
        hashtable12.put("SHA224WITHECDSA", aSN1ObjectIdentifier12);
        Hashtable hashtable13 = algorithms;
        ASN1ObjectIdentifier aSN1ObjectIdentifier13 = X9ObjectIdentifiers.ecdsa_with_SHA256;
        hashtable13.put("SHA256WITHECDSA", aSN1ObjectIdentifier13);
        Hashtable hashtable14 = algorithms;
        ASN1ObjectIdentifier aSN1ObjectIdentifier14 = X9ObjectIdentifiers.ecdsa_with_SHA384;
        hashtable14.put("SHA384WITHECDSA", aSN1ObjectIdentifier14);
        Hashtable hashtable15 = algorithms;
        ASN1ObjectIdentifier aSN1ObjectIdentifier15 = X9ObjectIdentifiers.ecdsa_with_SHA512;
        hashtable15.put("SHA512WITHECDSA", aSN1ObjectIdentifier15);
        algorithms.put("ECDSAWITHSHA1", aSN1ObjectIdentifier11);
        Hashtable hashtable16 = algorithms;
        DERObjectIdentifier dERObjectIdentifier = CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_94;
        hashtable16.put("GOST3411WITHGOST3410", dERObjectIdentifier);
        algorithms.put("GOST3410WITHGOST3411", dERObjectIdentifier);
        Hashtable hashtable17 = algorithms;
        DERObjectIdentifier dERObjectIdentifier2 = CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_2001;
        hashtable17.put("GOST3411WITHECGOST3410", dERObjectIdentifier2);
        algorithms.put("GOST3411WITHECGOST3410-2001", dERObjectIdentifier2);
        algorithms.put("GOST3411WITHGOST3410-2001", dERObjectIdentifier2);
        oids.put(new DERObjectIdentifier("1.2.840.113549.1.1.5"), "SHA1WITHRSA");
        oids.put(aSN1ObjectIdentifier, "SHA224WITHRSA");
        oids.put(aSN1ObjectIdentifier2, "SHA256WITHRSA");
        oids.put(aSN1ObjectIdentifier3, "SHA384WITHRSA");
        oids.put(aSN1ObjectIdentifier4, "SHA512WITHRSA");
        oids.put(dERObjectIdentifier, "GOST3411WITHGOST3410");
        oids.put(dERObjectIdentifier2, "GOST3411WITHECGOST3410");
        oids.put(new DERObjectIdentifier("1.2.840.113549.1.1.4"), "MD5WITHRSA");
        oids.put(new DERObjectIdentifier("1.2.840.113549.1.1.2"), "MD2WITHRSA");
        oids.put(new DERObjectIdentifier("1.2.840.10040.4.3"), "SHA1WITHDSA");
        oids.put(aSN1ObjectIdentifier11, "SHA1WITHECDSA");
        oids.put(aSN1ObjectIdentifier12, "SHA224WITHECDSA");
        oids.put(aSN1ObjectIdentifier13, "SHA256WITHECDSA");
        oids.put(aSN1ObjectIdentifier14, "SHA384WITHECDSA");
        oids.put(aSN1ObjectIdentifier15, "SHA512WITHECDSA");
        oids.put(OIWObjectIdentifiers.sha1WithRSA, "SHA1WITHRSA");
        oids.put(OIWObjectIdentifiers.dsaWithSHA1, "SHA1WITHDSA");
        oids.put(aSN1ObjectIdentifier9, "SHA224WITHDSA");
        oids.put(aSN1ObjectIdentifier10, "SHA256WITHDSA");
        keyAlgorithms.put(PKCSObjectIdentifiers.rsaEncryption, "RSA");
        keyAlgorithms.put(X9ObjectIdentifiers.id_dsa, "DSA");
        noParams.add(aSN1ObjectIdentifier11);
        noParams.add(aSN1ObjectIdentifier12);
        noParams.add(aSN1ObjectIdentifier13);
        noParams.add(aSN1ObjectIdentifier14);
        noParams.add(aSN1ObjectIdentifier15);
        noParams.add(X9ObjectIdentifiers.id_dsa_with_sha1);
        noParams.add(aSN1ObjectIdentifier9);
        noParams.add(aSN1ObjectIdentifier10);
        noParams.add(dERObjectIdentifier);
        noParams.add(dERObjectIdentifier2);
        params.put("SHA1WITHRSAANDMGF1", creatPSSParams(new AlgorithmIdentifier(OIWObjectIdentifiers.idSHA1, new DERNull()), 20));
        params.put("SHA224WITHRSAANDMGF1", creatPSSParams(new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha224, new DERNull()), 28));
        params.put("SHA256WITHRSAANDMGF1", creatPSSParams(new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha256, new DERNull()), 32));
        params.put("SHA384WITHRSAANDMGF1", creatPSSParams(new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha384, new DERNull()), 48));
        params.put("SHA512WITHRSAANDMGF1", creatPSSParams(new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha512, new DERNull()), 64));
    }

    public PKCS10CertificationRequest(String str, X500Principal x500Principal, PublicKey publicKey, ASN1Set aSN1Set, PrivateKey privateKey) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, NoSuchProviderException {
        this(str, convertName(x500Principal), publicKey, aSN1Set, privateKey, BouncyCastleProvider.PROVIDER_NAME);
    }

    public PKCS10CertificationRequest(String str, X500Principal x500Principal, PublicKey publicKey, ASN1Set aSN1Set, PrivateKey privateKey, String str2) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, NoSuchProviderException {
        this(str, convertName(x500Principal), publicKey, aSN1Set, privateKey, str2);
    }

    public PKCS10CertificationRequest(String str, X509Name x509Name, PublicKey publicKey, ASN1Set aSN1Set, PrivateKey privateKey) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, NoSuchProviderException {
        this(str, x509Name, publicKey, aSN1Set, privateKey, BouncyCastleProvider.PROVIDER_NAME);
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0070  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public PKCS10CertificationRequest(java.lang.String r5, org.bouncycastle.asn1.x509.X509Name r6, java.security.PublicKey r7, org.bouncycastle.asn1.ASN1Set r8, java.security.PrivateKey r9, java.lang.String r10) throws java.security.SignatureException, java.security.NoSuchAlgorithmException, java.security.InvalidKeyException, java.security.NoSuchProviderException {
        /*
            r4 = this;
            r4.<init>()
            java.lang.String r0 = org.bouncycastle.util.Strings.toUpperCase(r5)
            java.util.Hashtable r1 = org.bouncycastle.jce.PKCS10CertificationRequest.algorithms
            java.lang.Object r1 = r1.get(r0)
            org.bouncycastle.asn1.DERObjectIdentifier r1 = (org.bouncycastle.asn1.DERObjectIdentifier) r1
            if (r1 != 0) goto L1f
            org.bouncycastle.asn1.DERObjectIdentifier r1 = new org.bouncycastle.asn1.DERObjectIdentifier     // Catch: java.lang.Exception -> L17
            r1.<init>(r0)     // Catch: java.lang.Exception -> L17
            goto L1f
        L17:
            java.lang.IllegalArgumentException r5 = new java.lang.IllegalArgumentException
            java.lang.String r6 = "Unknown signature type requested"
            r5.<init>(r6)
            throw r5
        L1f:
            if (r6 == 0) goto Lb6
            if (r7 == 0) goto Lae
            java.util.Set r2 = org.bouncycastle.jce.PKCS10CertificationRequest.noParams
            boolean r2 = r2.contains(r1)
            if (r2 == 0) goto L33
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r0 = new org.bouncycastle.asn1.x509.AlgorithmIdentifier
            r0.<init>(r1)
        L30:
            r4.sigAlgId = r0
            goto L53
        L33:
            java.util.Hashtable r2 = org.bouncycastle.jce.PKCS10CertificationRequest.params
            boolean r2 = r2.containsKey(r0)
            if (r2 == 0) goto L4b
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r2 = new org.bouncycastle.asn1.x509.AlgorithmIdentifier
            java.util.Hashtable r3 = org.bouncycastle.jce.PKCS10CertificationRequest.params
            java.lang.Object r0 = r3.get(r0)
            org.bouncycastle.asn1.DEREncodable r0 = (org.bouncycastle.asn1.DEREncodable) r0
            r2.<init>(r1, r0)
            r4.sigAlgId = r2
            goto L53
        L4b:
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r0 = new org.bouncycastle.asn1.x509.AlgorithmIdentifier
            org.bouncycastle.asn1.DERNull r2 = org.bouncycastle.asn1.DERNull.INSTANCE
            r0.<init>(r1, r2)
            goto L30
        L53:
            byte[] r7 = r7.getEncoded()     // Catch: java.io.IOException -> La6
            org.bouncycastle.asn1.ASN1Object r7 = org.bouncycastle.asn1.ASN1Object.fromByteArray(r7)     // Catch: java.io.IOException -> La6
            org.bouncycastle.asn1.ASN1Sequence r7 = (org.bouncycastle.asn1.ASN1Sequence) r7     // Catch: java.io.IOException -> La6
            org.bouncycastle.asn1.pkcs.CertificationRequestInfo r0 = new org.bouncycastle.asn1.pkcs.CertificationRequestInfo     // Catch: java.io.IOException -> La6
            org.bouncycastle.asn1.x509.SubjectPublicKeyInfo r1 = new org.bouncycastle.asn1.x509.SubjectPublicKeyInfo     // Catch: java.io.IOException -> La6
            r1.<init>(r7)     // Catch: java.io.IOException -> La6
            r0.<init>(r6, r1, r8)     // Catch: java.io.IOException -> La6
            r4.reqInfo = r0     // Catch: java.io.IOException -> La6
            if (r10 != 0) goto L70
            java.security.Signature r5 = java.security.Signature.getInstance(r5)
            goto L74
        L70:
            java.security.Signature r5 = java.security.Signature.getInstance(r5, r10)
        L74:
            r5.initSign(r9)
            org.bouncycastle.asn1.pkcs.CertificationRequestInfo r6 = r4.reqInfo     // Catch: java.lang.Exception -> L8e
            java.lang.String r7 = "DER"
            byte[] r6 = r6.getEncoded(r7)     // Catch: java.lang.Exception -> L8e
            r5.update(r6)     // Catch: java.lang.Exception -> L8e
            org.bouncycastle.asn1.DERBitString r6 = new org.bouncycastle.asn1.DERBitString
            byte[] r5 = r5.sign()
            r6.<init>(r5)
            r4.sigBits = r6
            return
        L8e:
            r5 = move-exception
            java.lang.IllegalArgumentException r6 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "exception encoding TBS cert request - "
            r7.append(r8)
            r7.append(r5)
            java.lang.String r5 = r7.toString()
            r6.<init>(r5)
            throw r6
        La6:
            java.lang.IllegalArgumentException r5 = new java.lang.IllegalArgumentException
            java.lang.String r6 = "can't encode public key"
            r5.<init>(r6)
            throw r5
        Lae:
            java.lang.IllegalArgumentException r5 = new java.lang.IllegalArgumentException
            java.lang.String r6 = "public key must not be null"
            r5.<init>(r6)
            throw r5
        Lb6:
            java.lang.IllegalArgumentException r5 = new java.lang.IllegalArgumentException
            java.lang.String r6 = "subject must not be null"
            r5.<init>(r6)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jce.PKCS10CertificationRequest.<init>(java.lang.String, org.bouncycastle.asn1.x509.X509Name, java.security.PublicKey, org.bouncycastle.asn1.ASN1Set, java.security.PrivateKey, java.lang.String):void");
    }

    public PKCS10CertificationRequest(ASN1Sequence aSN1Sequence) {
        super(aSN1Sequence);
    }

    public PKCS10CertificationRequest(byte[] bArr) {
        super(toDERSequence(bArr));
    }

    private static X509Name convertName(X500Principal x500Principal) {
        try {
            return new X509Principal(x500Principal.getEncoded());
        } catch (IOException unused) {
            throw new IllegalArgumentException("can't convert name");
        }
    }

    private static RSASSAPSSparams creatPSSParams(AlgorithmIdentifier algorithmIdentifier, int i2) {
        return new RSASSAPSSparams(algorithmIdentifier, new AlgorithmIdentifier(PKCSObjectIdentifiers.id_mgf1, algorithmIdentifier), new DERInteger(i2), new DERInteger(1));
    }

    private static String getDigestAlgName(DERObjectIdentifier dERObjectIdentifier) {
        return PKCSObjectIdentifiers.md5.equals(dERObjectIdentifier) ? "MD5" : OIWObjectIdentifiers.idSHA1.equals(dERObjectIdentifier) ? "SHA1" : NISTObjectIdentifiers.id_sha224.equals(dERObjectIdentifier) ? "SHA224" : NISTObjectIdentifiers.id_sha256.equals(dERObjectIdentifier) ? "SHA256" : NISTObjectIdentifiers.id_sha384.equals(dERObjectIdentifier) ? "SHA384" : NISTObjectIdentifiers.id_sha512.equals(dERObjectIdentifier) ? "SHA512" : TeleTrusTObjectIdentifiers.ripemd128.equals(dERObjectIdentifier) ? "RIPEMD128" : TeleTrusTObjectIdentifiers.ripemd160.equals(dERObjectIdentifier) ? "RIPEMD160" : TeleTrusTObjectIdentifiers.ripemd256.equals(dERObjectIdentifier) ? "RIPEMD256" : CryptoProObjectIdentifiers.gostR3411.equals(dERObjectIdentifier) ? "GOST3411" : dERObjectIdentifier.getId();
    }

    public static String getSignatureName(AlgorithmIdentifier algorithmIdentifier) {
        DEREncodable parameters = algorithmIdentifier.getParameters();
        if (parameters == null || DERNull.INSTANCE.equals(parameters) || !algorithmIdentifier.getObjectId().equals(PKCSObjectIdentifiers.id_RSASSA_PSS)) {
            return algorithmIdentifier.getObjectId().getId();
        }
        return getDigestAlgName(RSASSAPSSparams.getInstance(parameters).getHashAlgorithm().getObjectId()) + "withRSAandMGF1";
    }

    private void setSignatureParameters(Signature signature, DEREncodable dEREncodable) throws NoSuchAlgorithmException, SignatureException, IOException, InvalidKeyException, InvalidAlgorithmParameterException {
        if (dEREncodable == null || DERNull.INSTANCE.equals(dEREncodable)) {
            return;
        }
        AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance(signature.getAlgorithm(), signature.getProvider());
        try {
            algorithmParameters.init(dEREncodable.getDERObject().getDEREncoded());
            if (signature.getAlgorithm().endsWith("MGF1")) {
                try {
                    signature.setParameter(algorithmParameters.getParameterSpec(PSSParameterSpec.class));
                } catch (GeneralSecurityException e2) {
                    throw new SignatureException("Exception extracting parameters: " + e2.getMessage());
                }
            }
        } catch (IOException e3) {
            throw new SignatureException("IOException decoding parameters: " + e3.getMessage());
        }
    }

    private static ASN1Sequence toDERSequence(byte[] bArr) {
        try {
            return (ASN1Sequence) new ASN1InputStream(bArr).readObject();
        } catch (Exception unused) {
            throw new IllegalArgumentException("badly encoded request");
        }
    }

    @Override // org.bouncycastle.asn1.ASN1Encodable
    public byte[] getEncoded() {
        try {
            return getEncoded(ASN1Encodable.DER);
        } catch (IOException e2) {
            throw new RuntimeException(e2.toString());
        }
    }

    public PublicKey getPublicKey() throws NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException {
        return getPublicKey(BouncyCastleProvider.PROVIDER_NAME);
    }

    public PublicKey getPublicKey(String str) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException {
        SubjectPublicKeyInfo subjectPublicKeyInfo = this.reqInfo.getSubjectPublicKeyInfo();
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(new DERBitString(subjectPublicKeyInfo).getBytes());
        AlgorithmIdentifier algorithmId = subjectPublicKeyInfo.getAlgorithmId();
        try {
            try {
                return str == null ? KeyFactory.getInstance(algorithmId.getObjectId().getId()).generatePublic(x509EncodedKeySpec) : KeyFactory.getInstance(algorithmId.getObjectId().getId(), str).generatePublic(x509EncodedKeySpec);
            } catch (NoSuchAlgorithmException e2) {
                if (keyAlgorithms.get(algorithmId.getObjectId()) == null) {
                    throw e2;
                }
                String str2 = (String) keyAlgorithms.get(algorithmId.getObjectId());
                return str == null ? KeyFactory.getInstance(str2).generatePublic(x509EncodedKeySpec) : KeyFactory.getInstance(str2, str).generatePublic(x509EncodedKeySpec);
            }
        } catch (InvalidKeySpecException unused) {
            throw new InvalidKeyException("error decoding public key");
        }
    }

    public boolean verify() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, NoSuchProviderException {
        return verify(BouncyCastleProvider.PROVIDER_NAME);
    }

    public boolean verify(String str) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, NoSuchProviderException {
        return verify(getPublicKey(str), str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v10 */
    /* JADX WARN: Type inference failed for: r5v7 */
    /* JADX WARN: Type inference failed for: r5v9 */
    public boolean verify(PublicKey publicKey, String str) throws NoSuchAlgorithmException, SignatureException, IOException, InvalidKeyException, NoSuchProviderException, InvalidAlgorithmParameterException {
        try {
            str = str == 0 ? Signature.getInstance(getSignatureName(this.sigAlgId)) : Signature.getInstance(getSignatureName(this.sigAlgId), str);
        } catch (NoSuchAlgorithmException e2) {
            if (oids.get(this.sigAlgId.getObjectId()) == null) {
                throw e2;
            }
            String str2 = (String) oids.get(this.sigAlgId.getObjectId());
            str = str == 0 ? Signature.getInstance(str2) : Signature.getInstance(str2, str);
        }
        setSignatureParameters(str, this.sigAlgId.getParameters());
        str.initVerify(publicKey);
        try {
            str.update(this.reqInfo.getEncoded(ASN1Encodable.DER));
            return str.verify(this.sigBits.getBytes());
        } catch (Exception e3) {
            throw new SignatureException("exception encoding TBS cert request - " + e3);
        }
    }
}
