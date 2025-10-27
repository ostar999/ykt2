package cn.hutool.crypto;

import cn.hutool.core.io.IORuntimeException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.KeySpec;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.sec.ECPrivateKey;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.bouncycastle.jcajce.spec.OpenSSHPrivateKeySpec;
import org.bouncycastle.jcajce.spec.OpenSSHPublicKeySpec;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.math.ec.FixedPointCombMultiplier;
import org.bouncycastle.util.BigIntegers;

/* loaded from: classes.dex */
public class ECKeyUtil {
    public static KeySpec createOpenSSHPrivateKeySpec(byte[] bArr) {
        return new OpenSSHPrivateKeySpec(bArr);
    }

    public static KeySpec createOpenSSHPublicKeySpec(byte[] bArr) {
        return new OpenSSHPublicKeySpec(bArr);
    }

    public static ECPrivateKeyParameters decodePrivateKeyParams(byte[] bArr) {
        PrivateKey privateKeyGeneratePrivateKey;
        try {
            try {
                return toSm2PrivateParams(bArr);
            } catch (Exception unused) {
                privateKeyGeneratePrivateKey = KeyUtil.generatePrivateKey("sm2", bArr);
                return toPrivateParams(privateKeyGeneratePrivateKey);
            }
        } catch (Exception unused2) {
            privateKeyGeneratePrivateKey = KeyUtil.generatePrivateKey("sm2", createOpenSSHPrivateKeySpec(bArr));
            return toPrivateParams(privateKeyGeneratePrivateKey);
        }
    }

    public static ECPublicKeyParameters decodePublicKeyParams(byte[] bArr) {
        PublicKey publicKeyGeneratePublicKey;
        try {
            try {
                return toSm2PublicParams(bArr);
            } catch (Exception unused) {
                publicKeyGeneratePublicKey = KeyUtil.generatePublicKey("sm2", bArr);
                return toPublicParams(publicKeyGeneratePublicKey);
            }
        } catch (Exception unused2) {
            publicKeyGeneratePublicKey = KeyUtil.generatePublicKey("sm2", createOpenSSHPublicKeySpec(bArr));
            return toPublicParams(publicKeyGeneratePublicKey);
        }
    }

    public static ECPublicKeyParameters getPublicParams(ECPrivateKeyParameters eCPrivateKeyParameters) {
        ECDomainParameters parameters = eCPrivateKeyParameters.getParameters();
        return new ECPublicKeyParameters(new FixedPointCombMultiplier().multiply(parameters.getG(), eCPrivateKeyParameters.getD()), parameters);
    }

    public static AsymmetricKeyParameter toParams(Key key) {
        if (key instanceof PrivateKey) {
            return toPrivateParams((PrivateKey) key);
        }
        if (key instanceof PublicKey) {
            return toPublicParams((PublicKey) key);
        }
        return null;
    }

    public static ECPrivateKeyParameters toPrivateParams(String str, ECDomainParameters eCDomainParameters) {
        if (str == null) {
            return null;
        }
        return toPrivateParams(BigIntegers.fromUnsignedByteArray(SecureUtil.decode(str)), eCDomainParameters);
    }

    public static ECPublicKeyParameters toPublicParams(String str, String str2, ECDomainParameters eCDomainParameters) {
        return toPublicParams(SecureUtil.decode(str), SecureUtil.decode(str2), eCDomainParameters);
    }

    public static PrivateKey toSm2PrivateKey(ECPrivateKey eCPrivateKey) {
        try {
            return KeyUtil.generatePrivateKey("SM2", new PrivateKeyInfo(new AlgorithmIdentifier(X9ObjectIdentifiers.id_ecPublicKey, (ASN1Encodable) SmUtil.ID_SM2_PUBLIC_KEY_PARAM), (ASN1Encodable) eCPrivateKey).getEncoded());
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static ECPrivateKeyParameters toSm2PrivateParams(String str) {
        return toPrivateParams(str, SmUtil.SM2_DOMAIN_PARAMS);
    }

    public static ECPublicKeyParameters toSm2PublicParams(byte[] bArr) {
        return toPublicParams(bArr, SmUtil.SM2_DOMAIN_PARAMS);
    }

    public static ECPrivateKeyParameters toPrivateParams(byte[] bArr, ECDomainParameters eCDomainParameters) {
        return toPrivateParams(BigIntegers.fromUnsignedByteArray(bArr), eCDomainParameters);
    }

    public static ECPublicKeyParameters toPublicParams(byte[] bArr, byte[] bArr2, ECDomainParameters eCDomainParameters) {
        if (bArr == null || bArr2 == null) {
            return null;
        }
        return toPublicParams(BigIntegers.fromUnsignedByteArray(bArr), BigIntegers.fromUnsignedByteArray(bArr2), eCDomainParameters);
    }

    public static ECPrivateKeyParameters toSm2PrivateParams(byte[] bArr) {
        return toPrivateParams(bArr, SmUtil.SM2_DOMAIN_PARAMS);
    }

    public static ECPublicKeyParameters toSm2PublicParams(String str) {
        return toPublicParams(str, SmUtil.SM2_DOMAIN_PARAMS);
    }

    public static ECPrivateKeyParameters toPrivateParams(BigInteger bigInteger, ECDomainParameters eCDomainParameters) {
        if (bigInteger == null) {
            return null;
        }
        return new ECPrivateKeyParameters(bigInteger, eCDomainParameters);
    }

    public static ECPublicKeyParameters toPublicParams(BigInteger bigInteger, BigInteger bigInteger2, ECDomainParameters eCDomainParameters) {
        if (bigInteger == null || bigInteger2 == null) {
            return null;
        }
        return toPublicParams(eCDomainParameters.getCurve().createPoint(bigInteger, bigInteger2), eCDomainParameters);
    }

    public static ECPrivateKeyParameters toSm2PrivateParams(BigInteger bigInteger) {
        return toPrivateParams(bigInteger, SmUtil.SM2_DOMAIN_PARAMS);
    }

    public static ECPublicKeyParameters toSm2PublicParams(String str, String str2) {
        return toPublicParams(str, str2, SmUtil.SM2_DOMAIN_PARAMS);
    }

    public static ECPrivateKeyParameters toPrivateParams(PrivateKey privateKey) {
        if (privateKey == null) {
            return null;
        }
        try {
            return (ECPrivateKeyParameters) ECUtil.generatePrivateKeyParameter(privateKey);
        } catch (InvalidKeyException e2) {
            throw new CryptoException(e2);
        }
    }

    public static ECPublicKeyParameters toSm2PublicParams(byte[] bArr, byte[] bArr2) {
        return toPublicParams(bArr, bArr2, SmUtil.SM2_DOMAIN_PARAMS);
    }

    public static ECPublicKeyParameters toPublicParams(String str, ECDomainParameters eCDomainParameters) {
        return toPublicParams(eCDomainParameters.getCurve().decodePoint(SecureUtil.decode(str)), eCDomainParameters);
    }

    public static ECPublicKeyParameters toPublicParams(byte[] bArr, ECDomainParameters eCDomainParameters) {
        return toPublicParams(eCDomainParameters.getCurve().decodePoint(bArr), eCDomainParameters);
    }

    public static ECPublicKeyParameters toPublicParams(ECPoint eCPoint, ECDomainParameters eCDomainParameters) {
        return new ECPublicKeyParameters(eCPoint, eCDomainParameters);
    }

    public static ECPublicKeyParameters toPublicParams(PublicKey publicKey) {
        if (publicKey == null) {
            return null;
        }
        try {
            return (ECPublicKeyParameters) ECUtil.generatePublicKeyParameter(publicKey);
        } catch (InvalidKeyException e2) {
            throw new CryptoException(e2);
        }
    }
}
