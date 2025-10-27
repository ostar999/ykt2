package org.bouncycastle.openssl;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.RC2ParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.crypto.PBEParametersGenerator;
import org.bouncycastle.crypto.generators.OpenSSLPBEParametersGenerator;
import org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.bouncycastle.crypto.params.KeyParameter;

/* loaded from: classes9.dex */
final class PEMUtilities {
    private static final Map KEYSIZES;
    private static final Set PKCS5_SCHEME_1;
    private static final Set PKCS5_SCHEME_2;

    static {
        HashMap map = new HashMap();
        KEYSIZES = map;
        HashSet hashSet = new HashSet();
        PKCS5_SCHEME_1 = hashSet;
        HashSet hashSet2 = new HashSet();
        PKCS5_SCHEME_2 = hashSet2;
        hashSet.add(PKCSObjectIdentifiers.pbeWithMD2AndDES_CBC);
        hashSet.add(PKCSObjectIdentifiers.pbeWithMD2AndRC2_CBC);
        hashSet.add(PKCSObjectIdentifiers.pbeWithMD5AndDES_CBC);
        hashSet.add(PKCSObjectIdentifiers.pbeWithMD5AndRC2_CBC);
        hashSet.add(PKCSObjectIdentifiers.pbeWithSHA1AndDES_CBC);
        hashSet.add(PKCSObjectIdentifiers.pbeWithSHA1AndRC2_CBC);
        hashSet2.add(PKCSObjectIdentifiers.id_PBES2);
        ASN1ObjectIdentifier aSN1ObjectIdentifier = PKCSObjectIdentifiers.des_EDE3_CBC;
        hashSet2.add(aSN1ObjectIdentifier);
        ASN1ObjectIdentifier aSN1ObjectIdentifier2 = NISTObjectIdentifiers.id_aes128_CBC;
        hashSet2.add(aSN1ObjectIdentifier2);
        ASN1ObjectIdentifier aSN1ObjectIdentifier3 = NISTObjectIdentifiers.id_aes192_CBC;
        hashSet2.add(aSN1ObjectIdentifier3);
        ASN1ObjectIdentifier aSN1ObjectIdentifier4 = NISTObjectIdentifiers.id_aes256_CBC;
        hashSet2.add(aSN1ObjectIdentifier4);
        map.put(aSN1ObjectIdentifier.getId(), new Integer(192));
        map.put(aSN1ObjectIdentifier2.getId(), new Integer(128));
        map.put(aSN1ObjectIdentifier3.getId(), new Integer(192));
        map.put(aSN1ObjectIdentifier4.getId(), new Integer(256));
    }

    public static byte[] crypt(boolean z2, String str, byte[] bArr, char[] cArr, String str2, byte[] bArr2) throws IOException {
        Provider provider;
        if (str != null) {
            provider = Security.getProvider(str);
            if (provider == null) {
                throw new EncryptionException("cannot find provider: " + str);
            }
        } else {
            provider = null;
        }
        return crypt(z2, provider, bArr, cArr, str2, bArr2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r12v13, types: [javax.crypto.spec.RC2ParameterSpec] */
    /* JADX WARN: Type inference failed for: r12v14, types: [javax.crypto.spec.RC2ParameterSpec] */
    /* JADX WARN: Type inference failed for: r12v4, types: [javax.crypto.spec.RC2ParameterSpec] */
    public static byte[] crypt(boolean z2, Provider provider, byte[] bArr, char[] cArr, String str, byte[] bArr2) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException, InvalidAlgorithmParameterException {
        String str2;
        String str3;
        String str4;
        SecretKey key;
        IvParameterSpec ivParameterSpec = new IvParameterSpec(bArr2);
        String str5 = "NoPadding";
        if (str.endsWith("-CFB")) {
            str2 = "CFB";
            str3 = "NoPadding";
        } else {
            str2 = "CBC";
            str3 = "PKCS5Padding";
        }
        if (str.endsWith("-ECB") || "DES-EDE".equals(str) || "DES-EDE3".equals(str)) {
            str2 = "ECB";
            ivParameterSpec = null;
        }
        if (str.endsWith("-OFB")) {
            str2 = "OFB";
        } else {
            str5 = str3;
        }
        int i2 = 1;
        if (str.startsWith("DES-EDE")) {
            str4 = "DESede";
            key = getKey(cArr, "DESede", 24, bArr2, !str.startsWith("DES-EDE3"));
        } else if (str.startsWith("DES-")) {
            key = getKey(cArr, "DES", 8, bArr2);
            str4 = "DES";
        } else if (str.startsWith("BF-")) {
            str4 = "Blowfish";
            key = getKey(cArr, "Blowfish", 16, bArr2);
        } else {
            int i3 = 128;
            if (str.startsWith("RC2-")) {
                if (str.startsWith("RC2-40-")) {
                    i3 = 40;
                } else if (str.startsWith("RC2-64-")) {
                    i3 = 64;
                }
                str4 = "RC2";
                key = getKey(cArr, "RC2", i3 / 8, bArr2);
                ivParameterSpec = ivParameterSpec == null ? new RC2ParameterSpec(i3) : new RC2ParameterSpec(i3, bArr2);
            } else {
                if (!str.startsWith("AES-")) {
                    throw new EncryptionException("unknown encryption with private key");
                }
                if (bArr2.length > 8) {
                    byte[] bArr3 = new byte[8];
                    System.arraycopy(bArr2, 0, bArr3, 0, 8);
                    bArr2 = bArr3;
                }
                if (!str.startsWith("AES-128-")) {
                    if (str.startsWith("AES-192-")) {
                        i3 = 192;
                    } else {
                        if (!str.startsWith("AES-256-")) {
                            throw new EncryptionException("unknown AES encryption with private key");
                        }
                        i3 = 256;
                    }
                }
                str4 = "AES";
                key = getKey(cArr, "AES", i3 / 8, bArr2);
            }
        }
        try {
            Cipher cipher = Cipher.getInstance(str4 + "/" + str2 + "/" + str5, provider);
            if (!z2) {
                i2 = 2;
            }
            if (ivParameterSpec == null) {
                cipher.init(i2, key);
            } else {
                cipher.init(i2, key, ivParameterSpec);
            }
            return cipher.doFinal(bArr);
        } catch (Exception e2) {
            throw new EncryptionException("exception using cipher - please check password and data.", e2);
        }
    }

    public static SecretKey generateSecretKeyForPKCS5Scheme2(String str, char[] cArr, byte[] bArr, int i2) {
        PKCS5S2ParametersGenerator pKCS5S2ParametersGenerator = new PKCS5S2ParametersGenerator();
        pKCS5S2ParametersGenerator.init(PBEParametersGenerator.PKCS5PasswordToBytes(cArr), bArr, i2);
        return new SecretKeySpec(((KeyParameter) pKCS5S2ParametersGenerator.generateDerivedParameters(getKeySize(str))).getKey(), str);
    }

    private static SecretKey getKey(char[] cArr, String str, int i2, byte[] bArr) {
        return getKey(cArr, str, i2, bArr, false);
    }

    private static SecretKey getKey(char[] cArr, String str, int i2, byte[] bArr, boolean z2) {
        OpenSSLPBEParametersGenerator openSSLPBEParametersGenerator = new OpenSSLPBEParametersGenerator();
        openSSLPBEParametersGenerator.init(PBEParametersGenerator.PKCS5PasswordToBytes(cArr), bArr);
        byte[] key = ((KeyParameter) openSSLPBEParametersGenerator.generateDerivedParameters(i2 * 8)).getKey();
        if (z2 && key.length >= 24) {
            System.arraycopy(key, 0, key, 16, 8);
        }
        return new SecretKeySpec(key, str);
    }

    public static int getKeySize(String str) {
        Map map = KEYSIZES;
        if (map.containsKey(str)) {
            return ((Integer) map.get(str)).intValue();
        }
        throw new IllegalStateException("no key size for algorithm: " + str);
    }

    public static boolean isPKCS12(DERObjectIdentifier dERObjectIdentifier) {
        return dERObjectIdentifier.getId().startsWith(PKCSObjectIdentifiers.pkcs_12PbeIds.getId());
    }

    public static boolean isPKCS5Scheme1(DERObjectIdentifier dERObjectIdentifier) {
        return PKCS5_SCHEME_1.contains(dERObjectIdentifier);
    }

    public static boolean isPKCS5Scheme2(DERObjectIdentifier dERObjectIdentifier) {
        return PKCS5_SCHEME_2.contains(dERObjectIdentifier);
    }
}
