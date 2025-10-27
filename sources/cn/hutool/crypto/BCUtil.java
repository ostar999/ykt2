package cn.hutool.crypto;

import cn.hutool.core.io.IORuntimeException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.ECPublicKeySpec;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util;
import org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.bouncycastle.jce.spec.ECNamedCurveSpec;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.math.ec.ECCurve;

/* loaded from: classes.dex */
public class BCUtil {
    public static PublicKey decodeECPoint(String str, String str2) {
        return decodeECPoint(SecureUtil.decode(str), str2);
    }

    public static byte[] encodeECPrivateKey(PrivateKey privateKey) {
        return ((BCECPrivateKey) privateKey).getD().toByteArray();
    }

    public static byte[] encodeECPublicKey(PublicKey publicKey) {
        return encodeECPublicKey(publicKey, true);
    }

    public static PrivateKey readPemPrivateKey(InputStream inputStream) {
        return PemUtil.readPemPrivateKey(inputStream);
    }

    public static PublicKey readPemPublicKey(InputStream inputStream) {
        return PemUtil.readPemPublicKey(inputStream);
    }

    public static ECDomainParameters toDomainParams(ECParameterSpec eCParameterSpec) {
        return new ECDomainParameters(eCParameterSpec.getCurve(), eCParameterSpec.getG(), eCParameterSpec.getN(), eCParameterSpec.getH());
    }

    public static AsymmetricKeyParameter toParams(Key key) {
        return ECKeyUtil.toParams(key);
    }

    public static byte[] toPkcs1(PrivateKey privateKey) {
        try {
            return PrivateKeyInfo.getInstance(privateKey.getEncoded()).parsePrivateKey().toASN1Primitive().getEncoded();
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static ECPrivateKeyParameters toSm2Params(String str) {
        return ECKeyUtil.toSm2PrivateParams(str);
    }

    public static PublicKey decodeECPoint(byte[] bArr, String str) {
        X9ECParameters namedCurveByName = ECUtil.getNamedCurveByName(str);
        ECCurve curve = namedCurveByName.getCurve();
        return KeyUtil.generatePublicKey("EC", new ECPublicKeySpec(EC5Util.convertPoint(curve.decodePoint(bArr)), new ECNamedCurveSpec(str, curve, namedCurveByName.getG(), namedCurveByName.getN())));
    }

    public static byte[] encodeECPublicKey(PublicKey publicKey, boolean z2) {
        return ((BCECPublicKey) publicKey).getQ().getEncoded(z2);
    }

    public static ECPrivateKeyParameters toParams(String str, ECDomainParameters eCDomainParameters) {
        return ECKeyUtil.toPrivateParams(str, eCDomainParameters);
    }

    public static ECPrivateKeyParameters toSm2Params(byte[] bArr) {
        return ECKeyUtil.toSm2PrivateParams(bArr);
    }

    public static ECPrivateKeyParameters toParams(byte[] bArr, ECDomainParameters eCDomainParameters) {
        return ECKeyUtil.toPrivateParams(bArr, eCDomainParameters);
    }

    public static ECPrivateKeyParameters toSm2Params(BigInteger bigInteger) {
        return ECKeyUtil.toSm2PrivateParams(bigInteger);
    }

    public static ECPrivateKeyParameters toParams(BigInteger bigInteger, ECDomainParameters eCDomainParameters) {
        return ECKeyUtil.toPrivateParams(bigInteger, eCDomainParameters);
    }

    public static byte[] toPkcs1(PublicKey publicKey) {
        try {
            return SubjectPublicKeyInfo.getInstance(publicKey.getEncoded()).parsePublicKey().getEncoded();
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static ECPublicKeyParameters toSm2Params(String str, String str2) {
        return ECKeyUtil.toSm2PublicParams(str, str2);
    }

    public static ECPublicKeyParameters toParams(BigInteger bigInteger, BigInteger bigInteger2, ECDomainParameters eCDomainParameters) {
        return ECKeyUtil.toPublicParams(bigInteger, bigInteger2, eCDomainParameters);
    }

    public static ECPublicKeyParameters toSm2Params(byte[] bArr, byte[] bArr2) {
        return ECKeyUtil.toSm2PublicParams(bArr, bArr2);
    }

    public static ECDomainParameters toDomainParams(String str) {
        return toDomainParams(ECUtil.getNamedCurveByName(str));
    }

    public static ECPublicKeyParameters toParams(String str, String str2, ECDomainParameters eCDomainParameters) {
        return ECKeyUtil.toPublicParams(str, str2, eCDomainParameters);
    }

    public static ECDomainParameters toDomainParams(X9ECParameters x9ECParameters) {
        return new ECDomainParameters(x9ECParameters.getCurve(), x9ECParameters.getG(), x9ECParameters.getN(), x9ECParameters.getH());
    }

    public static ECPublicKeyParameters toParams(byte[] bArr, byte[] bArr2, ECDomainParameters eCDomainParameters) {
        return ECKeyUtil.toPublicParams(bArr, bArr2, eCDomainParameters);
    }

    public static ECPublicKeyParameters toParams(PublicKey publicKey) {
        return ECKeyUtil.toPublicParams(publicKey);
    }

    public static ECPrivateKeyParameters toParams(PrivateKey privateKey) {
        return ECKeyUtil.toPrivateParams(privateKey);
    }
}
