package org.bouncycastle.x509;

import cn.hutool.core.text.StrPool;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.DEREncodable;
import org.bouncycastle.asn1.DERInteger;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.RSASSAPSSparams;
import org.bouncycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import org.bouncycastle.jce.X509Principal;
import org.bouncycastle.util.Strings;

/* loaded from: classes9.dex */
class X509Util {
    private static Hashtable algorithms = new Hashtable();
    private static Hashtable params = new Hashtable();
    private static Set noParams = new HashSet();

    public static class Implementation {
        Object engine;
        Provider provider;

        public Implementation(Object obj, Provider provider) {
            this.engine = obj;
            this.provider = provider;
        }

        public Object getEngine() {
            return this.engine;
        }

        public Provider getProvider() {
            return this.provider;
        }
    }

    static {
        Hashtable hashtable = algorithms;
        ASN1ObjectIdentifier aSN1ObjectIdentifier = PKCSObjectIdentifiers.md2WithRSAEncryption;
        hashtable.put("MD2WITHRSAENCRYPTION", aSN1ObjectIdentifier);
        algorithms.put("MD2WITHRSA", aSN1ObjectIdentifier);
        Hashtable hashtable2 = algorithms;
        ASN1ObjectIdentifier aSN1ObjectIdentifier2 = PKCSObjectIdentifiers.md5WithRSAEncryption;
        hashtable2.put("MD5WITHRSAENCRYPTION", aSN1ObjectIdentifier2);
        algorithms.put("MD5WITHRSA", aSN1ObjectIdentifier2);
        Hashtable hashtable3 = algorithms;
        ASN1ObjectIdentifier aSN1ObjectIdentifier3 = PKCSObjectIdentifiers.sha1WithRSAEncryption;
        hashtable3.put("SHA1WITHRSAENCRYPTION", aSN1ObjectIdentifier3);
        algorithms.put("SHA1WITHRSA", aSN1ObjectIdentifier3);
        Hashtable hashtable4 = algorithms;
        ASN1ObjectIdentifier aSN1ObjectIdentifier4 = PKCSObjectIdentifiers.sha224WithRSAEncryption;
        hashtable4.put("SHA224WITHRSAENCRYPTION", aSN1ObjectIdentifier4);
        algorithms.put("SHA224WITHRSA", aSN1ObjectIdentifier4);
        Hashtable hashtable5 = algorithms;
        ASN1ObjectIdentifier aSN1ObjectIdentifier5 = PKCSObjectIdentifiers.sha256WithRSAEncryption;
        hashtable5.put("SHA256WITHRSAENCRYPTION", aSN1ObjectIdentifier5);
        algorithms.put("SHA256WITHRSA", aSN1ObjectIdentifier5);
        Hashtable hashtable6 = algorithms;
        ASN1ObjectIdentifier aSN1ObjectIdentifier6 = PKCSObjectIdentifiers.sha384WithRSAEncryption;
        hashtable6.put("SHA384WITHRSAENCRYPTION", aSN1ObjectIdentifier6);
        algorithms.put("SHA384WITHRSA", aSN1ObjectIdentifier6);
        Hashtable hashtable7 = algorithms;
        ASN1ObjectIdentifier aSN1ObjectIdentifier7 = PKCSObjectIdentifiers.sha512WithRSAEncryption;
        hashtable7.put("SHA512WITHRSAENCRYPTION", aSN1ObjectIdentifier7);
        algorithms.put("SHA512WITHRSA", aSN1ObjectIdentifier7);
        Hashtable hashtable8 = algorithms;
        ASN1ObjectIdentifier aSN1ObjectIdentifier8 = PKCSObjectIdentifiers.id_RSASSA_PSS;
        hashtable8.put("SHA1WITHRSAANDMGF1", aSN1ObjectIdentifier8);
        algorithms.put("SHA224WITHRSAANDMGF1", aSN1ObjectIdentifier8);
        algorithms.put("SHA256WITHRSAANDMGF1", aSN1ObjectIdentifier8);
        algorithms.put("SHA384WITHRSAANDMGF1", aSN1ObjectIdentifier8);
        algorithms.put("SHA512WITHRSAANDMGF1", aSN1ObjectIdentifier8);
        Hashtable hashtable9 = algorithms;
        ASN1ObjectIdentifier aSN1ObjectIdentifier9 = TeleTrusTObjectIdentifiers.rsaSignatureWithripemd160;
        hashtable9.put("RIPEMD160WITHRSAENCRYPTION", aSN1ObjectIdentifier9);
        algorithms.put("RIPEMD160WITHRSA", aSN1ObjectIdentifier9);
        Hashtable hashtable10 = algorithms;
        ASN1ObjectIdentifier aSN1ObjectIdentifier10 = TeleTrusTObjectIdentifiers.rsaSignatureWithripemd128;
        hashtable10.put("RIPEMD128WITHRSAENCRYPTION", aSN1ObjectIdentifier10);
        algorithms.put("RIPEMD128WITHRSA", aSN1ObjectIdentifier10);
        Hashtable hashtable11 = algorithms;
        ASN1ObjectIdentifier aSN1ObjectIdentifier11 = TeleTrusTObjectIdentifiers.rsaSignatureWithripemd256;
        hashtable11.put("RIPEMD256WITHRSAENCRYPTION", aSN1ObjectIdentifier11);
        algorithms.put("RIPEMD256WITHRSA", aSN1ObjectIdentifier11);
        Hashtable hashtable12 = algorithms;
        ASN1ObjectIdentifier aSN1ObjectIdentifier12 = X9ObjectIdentifiers.id_dsa_with_sha1;
        hashtable12.put("SHA1WITHDSA", aSN1ObjectIdentifier12);
        algorithms.put("DSAWITHSHA1", aSN1ObjectIdentifier12);
        Hashtable hashtable13 = algorithms;
        ASN1ObjectIdentifier aSN1ObjectIdentifier13 = NISTObjectIdentifiers.dsa_with_sha224;
        hashtable13.put("SHA224WITHDSA", aSN1ObjectIdentifier13);
        Hashtable hashtable14 = algorithms;
        ASN1ObjectIdentifier aSN1ObjectIdentifier14 = NISTObjectIdentifiers.dsa_with_sha256;
        hashtable14.put("SHA256WITHDSA", aSN1ObjectIdentifier14);
        Hashtable hashtable15 = algorithms;
        ASN1ObjectIdentifier aSN1ObjectIdentifier15 = NISTObjectIdentifiers.dsa_with_sha384;
        hashtable15.put("SHA384WITHDSA", aSN1ObjectIdentifier15);
        Hashtable hashtable16 = algorithms;
        ASN1ObjectIdentifier aSN1ObjectIdentifier16 = NISTObjectIdentifiers.dsa_with_sha512;
        hashtable16.put("SHA512WITHDSA", aSN1ObjectIdentifier16);
        Hashtable hashtable17 = algorithms;
        ASN1ObjectIdentifier aSN1ObjectIdentifier17 = X9ObjectIdentifiers.ecdsa_with_SHA1;
        hashtable17.put("SHA1WITHECDSA", aSN1ObjectIdentifier17);
        algorithms.put("ECDSAWITHSHA1", aSN1ObjectIdentifier17);
        Hashtable hashtable18 = algorithms;
        ASN1ObjectIdentifier aSN1ObjectIdentifier18 = X9ObjectIdentifiers.ecdsa_with_SHA224;
        hashtable18.put("SHA224WITHECDSA", aSN1ObjectIdentifier18);
        Hashtable hashtable19 = algorithms;
        ASN1ObjectIdentifier aSN1ObjectIdentifier19 = X9ObjectIdentifiers.ecdsa_with_SHA256;
        hashtable19.put("SHA256WITHECDSA", aSN1ObjectIdentifier19);
        Hashtable hashtable20 = algorithms;
        ASN1ObjectIdentifier aSN1ObjectIdentifier20 = X9ObjectIdentifiers.ecdsa_with_SHA384;
        hashtable20.put("SHA384WITHECDSA", aSN1ObjectIdentifier20);
        Hashtable hashtable21 = algorithms;
        ASN1ObjectIdentifier aSN1ObjectIdentifier21 = X9ObjectIdentifiers.ecdsa_with_SHA512;
        hashtable21.put("SHA512WITHECDSA", aSN1ObjectIdentifier21);
        Hashtable hashtable22 = algorithms;
        DERObjectIdentifier dERObjectIdentifier = CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_94;
        hashtable22.put("GOST3411WITHGOST3410", dERObjectIdentifier);
        algorithms.put("GOST3411WITHGOST3410-94", dERObjectIdentifier);
        Hashtable hashtable23 = algorithms;
        DERObjectIdentifier dERObjectIdentifier2 = CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_2001;
        hashtable23.put("GOST3411WITHECGOST3410", dERObjectIdentifier2);
        algorithms.put("GOST3411WITHECGOST3410-2001", dERObjectIdentifier2);
        algorithms.put("GOST3411WITHGOST3410-2001", dERObjectIdentifier2);
        noParams.add(aSN1ObjectIdentifier17);
        noParams.add(aSN1ObjectIdentifier18);
        noParams.add(aSN1ObjectIdentifier19);
        noParams.add(aSN1ObjectIdentifier20);
        noParams.add(aSN1ObjectIdentifier21);
        noParams.add(aSN1ObjectIdentifier12);
        noParams.add(aSN1ObjectIdentifier13);
        noParams.add(aSN1ObjectIdentifier14);
        noParams.add(aSN1ObjectIdentifier15);
        noParams.add(aSN1ObjectIdentifier16);
        noParams.add(dERObjectIdentifier);
        noParams.add(dERObjectIdentifier2);
        params.put("SHA1WITHRSAANDMGF1", creatPSSParams(new AlgorithmIdentifier(OIWObjectIdentifiers.idSHA1, new DERNull()), 20));
        params.put("SHA224WITHRSAANDMGF1", creatPSSParams(new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha224, new DERNull()), 28));
        params.put("SHA256WITHRSAANDMGF1", creatPSSParams(new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha256, new DERNull()), 32));
        params.put("SHA384WITHRSAANDMGF1", creatPSSParams(new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha384, new DERNull()), 48));
        params.put("SHA512WITHRSAANDMGF1", creatPSSParams(new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha512, new DERNull()), 64));
    }

    public static byte[] calculateSignature(DERObjectIdentifier dERObjectIdentifier, String str, String str2, PrivateKey privateKey, SecureRandom secureRandom, ASN1Encodable aSN1Encodable) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, IOException, NoSuchProviderException {
        if (dERObjectIdentifier == null) {
            throw new IllegalStateException("no signature algorithm specified");
        }
        Signature signatureInstance = getSignatureInstance(str, str2);
        if (secureRandom != null) {
            signatureInstance.initSign(privateKey, secureRandom);
        } else {
            signatureInstance.initSign(privateKey);
        }
        signatureInstance.update(aSN1Encodable.getEncoded(ASN1Encodable.DER));
        return signatureInstance.sign();
    }

    public static byte[] calculateSignature(DERObjectIdentifier dERObjectIdentifier, String str, PrivateKey privateKey, SecureRandom secureRandom, ASN1Encodable aSN1Encodable) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, IOException {
        if (dERObjectIdentifier == null) {
            throw new IllegalStateException("no signature algorithm specified");
        }
        Signature signatureInstance = getSignatureInstance(str);
        if (secureRandom != null) {
            signatureInstance.initSign(privateKey, secureRandom);
        } else {
            signatureInstance.initSign(privateKey);
        }
        signatureInstance.update(aSN1Encodable.getEncoded(ASN1Encodable.DER));
        return signatureInstance.sign();
    }

    public static X509Principal convertPrincipal(X500Principal x500Principal) {
        try {
            return new X509Principal(x500Principal.getEncoded());
        } catch (IOException unused) {
            throw new IllegalArgumentException("cannot convert principal");
        }
    }

    private static RSASSAPSSparams creatPSSParams(AlgorithmIdentifier algorithmIdentifier, int i2) {
        return new RSASSAPSSparams(algorithmIdentifier, new AlgorithmIdentifier(PKCSObjectIdentifiers.id_mgf1, algorithmIdentifier), new DERInteger(i2), new DERInteger(1));
    }

    public static Iterator getAlgNames() {
        Enumeration enumerationKeys = algorithms.keys();
        ArrayList arrayList = new ArrayList();
        while (enumerationKeys.hasMoreElements()) {
            arrayList.add(enumerationKeys.nextElement());
        }
        return arrayList.iterator();
    }

    public static DERObjectIdentifier getAlgorithmOID(String str) {
        String upperCase = Strings.toUpperCase(str);
        return algorithms.containsKey(upperCase) ? (DERObjectIdentifier) algorithms.get(upperCase) : new DERObjectIdentifier(upperCase);
    }

    public static Implementation getImplementation(String str, String str2) throws NoSuchAlgorithmException {
        Provider[] providers = Security.getProviders();
        for (int i2 = 0; i2 != providers.length; i2++) {
            Implementation implementation = getImplementation(str, Strings.toUpperCase(str2), providers[i2]);
            if (implementation != null) {
                return implementation;
            }
            try {
                getImplementation(str, str2, providers[i2]);
            } catch (NoSuchAlgorithmException unused) {
            }
        }
        throw new NoSuchAlgorithmException("cannot find implementation " + str2);
    }

    public static Implementation getImplementation(String str, String str2, Provider provider) throws NoSuchAlgorithmException {
        String upperCase = Strings.toUpperCase(str2);
        while (true) {
            String property = provider.getProperty("Alg.Alias." + str + StrPool.DOT + upperCase);
            if (property == null) {
                break;
            }
            upperCase = property;
        }
        String property2 = provider.getProperty(str + StrPool.DOT + upperCase);
        if (property2 == null) {
            throw new NoSuchAlgorithmException("cannot find implementation " + upperCase + " for provider " + provider.getName());
        }
        try {
            ClassLoader classLoader = provider.getClass().getClassLoader();
            return new Implementation((classLoader != null ? classLoader.loadClass(property2) : Class.forName(property2)).newInstance(), provider);
        } catch (ClassNotFoundException unused) {
            throw new IllegalStateException("algorithm " + upperCase + " in provider " + provider.getName() + " but no class \"" + property2 + "\" found!");
        } catch (Exception unused2) {
            throw new IllegalStateException("algorithm " + upperCase + " in provider " + provider.getName() + " but class \"" + property2 + "\" inaccessible!");
        }
    }

    public static Provider getProvider(String str) throws NoSuchProviderException {
        Provider provider = Security.getProvider(str);
        if (provider != null) {
            return provider;
        }
        throw new NoSuchProviderException("Provider " + str + " not found");
    }

    public static AlgorithmIdentifier getSigAlgID(DERObjectIdentifier dERObjectIdentifier, String str) {
        if (noParams.contains(dERObjectIdentifier)) {
            return new AlgorithmIdentifier(dERObjectIdentifier);
        }
        String upperCase = Strings.toUpperCase(str);
        return params.containsKey(upperCase) ? new AlgorithmIdentifier(dERObjectIdentifier, (DEREncodable) params.get(upperCase)) : new AlgorithmIdentifier(dERObjectIdentifier, new DERNull());
    }

    public static Signature getSignatureInstance(String str) throws NoSuchAlgorithmException {
        return Signature.getInstance(str);
    }

    public static Signature getSignatureInstance(String str, String str2) throws NoSuchAlgorithmException, NoSuchProviderException {
        return str2 != null ? Signature.getInstance(str, str2) : Signature.getInstance(str);
    }
}
