package org.bouncycastle.openssl;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.CRLException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.security.interfaces.DSAParams;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.util.ArrayList;
import javax.crypto.NoSuchPaddingException;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERInteger;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.cms.ContentInfo;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.pkcs.RSAPrivateKeyStructure;
import org.bouncycastle.asn1.x509.DSAParameter;
import org.bouncycastle.jce.PKCS10CertificationRequest;
import org.bouncycastle.util.Strings;
import org.bouncycastle.util.encoders.Hex;
import org.bouncycastle.util.io.pem.PemGenerationException;
import org.bouncycastle.util.io.pem.PemHeader;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemObjectGenerator;
import org.bouncycastle.x509.X509AttributeCertificate;
import org.bouncycastle.x509.X509V2AttributeCertificate;

/* loaded from: classes9.dex */
public class MiscPEMGenerator implements PemObjectGenerator {
    private String algorithm;
    private Object obj;
    private char[] password;
    private Provider provider;
    private SecureRandom random;

    public MiscPEMGenerator(Object obj) {
        this.obj = obj;
    }

    public MiscPEMGenerator(Object obj, String str, char[] cArr, SecureRandom secureRandom, String str2) throws NoSuchProviderException {
        this.obj = obj;
        this.algorithm = str;
        this.password = cArr;
        this.random = secureRandom;
        if (str2 != null) {
            Provider provider = Security.getProvider(str2);
            this.provider = provider;
            if (provider != null) {
                return;
            }
            throw new NoSuchProviderException("cannot find provider: " + str2);
        }
    }

    public MiscPEMGenerator(Object obj, String str, char[] cArr, SecureRandom secureRandom, Provider provider) {
        this.obj = obj;
        this.algorithm = str;
        this.password = cArr;
        this.random = secureRandom;
        this.provider = provider;
    }

    private PemObject createPemObject(Object obj) throws IOException, CRLException, CertificateEncodingException {
        byte[] encoded;
        String str;
        if (obj instanceof PemObject) {
            return (PemObject) obj;
        }
        if (obj instanceof PemObjectGenerator) {
            return ((PemObjectGenerator) obj).generate();
        }
        if (obj instanceof X509Certificate) {
            try {
                encoded = ((X509Certificate) obj).getEncoded();
                str = "CERTIFICATE";
            } catch (CertificateEncodingException e2) {
                throw new PemGenerationException("Cannot encode object: " + e2.toString());
            }
        } else if (obj instanceof X509CRL) {
            try {
                encoded = ((X509CRL) obj).getEncoded();
                str = "X509 CRL";
            } catch (CRLException e3) {
                throw new PemGenerationException("Cannot encode object: " + e3.toString());
            }
        } else {
            if (obj instanceof KeyPair) {
                return createPemObject(((KeyPair) obj).getPrivate());
            }
            if (obj instanceof PrivateKey) {
                PrivateKeyInfo privateKeyInfo = new PrivateKeyInfo((ASN1Sequence) ASN1Object.fromByteArray(((Key) obj).getEncoded()));
                if (obj instanceof RSAPrivateKey) {
                    encoded = privateKeyInfo.getPrivateKey().getEncoded();
                    str = "RSA PRIVATE KEY";
                } else if (obj instanceof DSAPrivateKey) {
                    DSAParameter dSAParameter = DSAParameter.getInstance(privateKeyInfo.getAlgorithmId().getParameters());
                    ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
                    aSN1EncodableVector.add(new DERInteger(0));
                    aSN1EncodableVector.add(new DERInteger(dSAParameter.getP()));
                    aSN1EncodableVector.add(new DERInteger(dSAParameter.getQ()));
                    aSN1EncodableVector.add(new DERInteger(dSAParameter.getG()));
                    BigInteger x2 = ((DSAPrivateKey) obj).getX();
                    aSN1EncodableVector.add(new DERInteger(dSAParameter.getG().modPow(x2, dSAParameter.getP())));
                    aSN1EncodableVector.add(new DERInteger(x2));
                    encoded = new DERSequence(aSN1EncodableVector).getEncoded();
                    str = "DSA PRIVATE KEY";
                } else {
                    if (!((PrivateKey) obj).getAlgorithm().equals("ECDSA")) {
                        throw new IOException("Cannot identify private key");
                    }
                    encoded = privateKeyInfo.getPrivateKey().getEncoded();
                    str = "EC PRIVATE KEY";
                }
            } else if (obj instanceof PublicKey) {
                encoded = ((PublicKey) obj).getEncoded();
                str = "PUBLIC KEY";
            } else if (obj instanceof X509AttributeCertificate) {
                encoded = ((X509V2AttributeCertificate) obj).getEncoded();
                str = "ATTRIBUTE CERTIFICATE";
            } else if (obj instanceof PKCS10CertificationRequest) {
                encoded = ((PKCS10CertificationRequest) obj).getEncoded();
                str = "CERTIFICATE REQUEST";
            } else {
                if (!(obj instanceof ContentInfo)) {
                    throw new PemGenerationException("unknown object passed - can't encode.");
                }
                encoded = ((ContentInfo) obj).getEncoded();
                str = "PKCS7";
            }
        }
        return new PemObject(str, encoded);
    }

    private PemObject createPemObject(Object obj, String str, char[] cArr, SecureRandom secureRandom) throws NoSuchPaddingException, NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidAlgorithmParameterException {
        String str2;
        byte[] bArr;
        byte[] encoded;
        String str3;
        if (obj instanceof KeyPair) {
            return createPemObject(((KeyPair) obj).getPrivate(), str, cArr, secureRandom);
        }
        if (obj instanceof RSAPrivateCrtKey) {
            RSAPrivateCrtKey rSAPrivateCrtKey = (RSAPrivateCrtKey) obj;
            encoded = new RSAPrivateKeyStructure(rSAPrivateCrtKey.getModulus(), rSAPrivateCrtKey.getPublicExponent(), rSAPrivateCrtKey.getPrivateExponent(), rSAPrivateCrtKey.getPrimeP(), rSAPrivateCrtKey.getPrimeQ(), rSAPrivateCrtKey.getPrimeExponentP(), rSAPrivateCrtKey.getPrimeExponentQ(), rSAPrivateCrtKey.getCrtCoefficient()).getEncoded();
            str3 = "RSA PRIVATE KEY";
        } else {
            if (!(obj instanceof DSAPrivateKey)) {
                if (obj instanceof PrivateKey) {
                    PrivateKey privateKey = (PrivateKey) obj;
                    if ("ECDSA".equals(privateKey.getAlgorithm())) {
                        encoded = PrivateKeyInfo.getInstance(ASN1Object.fromByteArray(privateKey.getEncoded())).getPrivateKey().getEncoded();
                        str3 = "EC PRIVATE KEY";
                    }
                }
                str2 = null;
                bArr = null;
                if (str2 != null || bArr == null) {
                    throw new IllegalArgumentException("Object type not supported: " + obj.getClass().getName());
                }
                String upperCase = Strings.toUpperCase(str);
                if (upperCase.equals("DESEDE")) {
                    upperCase = "DES-EDE3-CBC";
                }
                byte[] bArr2 = new byte[upperCase.startsWith("AES-") ? 16 : 8];
                secureRandom.nextBytes(bArr2);
                byte[] bArrCrypt = PEMUtilities.crypt(true, this.provider, bArr, cArr, upperCase, bArr2);
                ArrayList arrayList = new ArrayList(2);
                arrayList.add(new PemHeader("Proc-Type", "4,ENCRYPTED"));
                arrayList.add(new PemHeader("DEK-Info", upperCase + "," + getHexEncoded(bArr2)));
                return new PemObject(str2, arrayList, bArrCrypt);
            }
            DSAPrivateKey dSAPrivateKey = (DSAPrivateKey) obj;
            DSAParams params = dSAPrivateKey.getParams();
            ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
            aSN1EncodableVector.add(new DERInteger(0));
            aSN1EncodableVector.add(new DERInteger(params.getP()));
            aSN1EncodableVector.add(new DERInteger(params.getQ()));
            aSN1EncodableVector.add(new DERInteger(params.getG()));
            BigInteger x2 = dSAPrivateKey.getX();
            aSN1EncodableVector.add(new DERInteger(params.getG().modPow(x2, params.getP())));
            aSN1EncodableVector.add(new DERInteger(x2));
            encoded = new DERSequence(aSN1EncodableVector).getEncoded();
            str3 = "DSA PRIVATE KEY";
        }
        bArr = encoded;
        str2 = str3;
        if (str2 != null) {
        }
        throw new IllegalArgumentException("Object type not supported: " + obj.getClass().getName());
    }

    private String getHexEncoded(byte[] bArr) throws IOException {
        byte[] bArrEncode = Hex.encode(bArr);
        char[] cArr = new char[bArrEncode.length];
        for (int i2 = 0; i2 != bArrEncode.length; i2++) {
            cArr[i2] = (char) bArrEncode[i2];
        }
        return new String(cArr);
    }

    @Override // org.bouncycastle.util.io.pem.PemObjectGenerator
    public PemObject generate() throws PemGenerationException {
        try {
            String str = this.algorithm;
            return str != null ? createPemObject(this.obj, str, this.password, this.random) : createPemObject(this.obj);
        } catch (IOException e2) {
            throw new PemGenerationException("encoding exception: " + e2.getMessage(), e2);
        }
    }
}
