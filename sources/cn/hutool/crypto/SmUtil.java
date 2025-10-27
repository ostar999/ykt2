package cn.hutool.crypto;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.util.PrimitiveArrayUtil;
import cn.hutool.crypto.asymmetric.SM2;
import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;
import cn.hutool.crypto.digest.SM3;
import cn.hutool.crypto.digest.mac.BCHMacEngine;
import cn.hutool.crypto.digest.mac.MacEngine;
import cn.hutool.crypto.symmetric.SM4;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.PublicKey;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.gm.GMNamedCurves;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.signers.StandardDSAEncoding;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.encoders.Hex;

/* loaded from: classes.dex */
public class SmUtil {
    private static final int RS_LEN = 32;
    public static final String SM2_CURVE_NAME = "sm2p256v1";
    public static final ECDomainParameters SM2_DOMAIN_PARAMS = BCUtil.toDomainParams(GMNamedCurves.getByName("sm2p256v1"));
    public static final ASN1ObjectIdentifier ID_SM2_PUBLIC_KEY_PARAM = new ASN1ObjectIdentifier("1.2.156.10197.1.301");

    private static byte[] bigIntToFixedLengthBytes(BigInteger bigInteger) {
        byte[] byteArray = bigInteger.toByteArray();
        if (byteArray.length == 32) {
            return byteArray;
        }
        if (byteArray.length == 33 && byteArray[0] == 0) {
            return Arrays.copyOfRange(byteArray, 1, 33);
        }
        if (byteArray.length >= 32) {
            throw new CryptoException("Error rs: {}", Hex.toHexString(byteArray));
        }
        byte[] bArr = new byte[32];
        Arrays.fill(bArr, (byte) 0);
        System.arraycopy(byteArray, 0, bArr, 32 - byteArray.length, byteArray.length);
        return bArr;
    }

    public static byte[] changeC1C2C3ToC1C3C2(byte[] bArr, ECDomainParameters eCDomainParameters) {
        int fieldSize = (((eCDomainParameters.getCurve().getFieldSize() + 7) / 8) * 2) + 1;
        byte[] bArr2 = new byte[bArr.length];
        System.arraycopy(bArr, 0, bArr2, 0, fieldSize);
        System.arraycopy(bArr, bArr.length - 32, bArr2, fieldSize, 32);
        System.arraycopy(bArr, fieldSize, bArr2, fieldSize + 32, (bArr.length - fieldSize) - 32);
        return bArr2;
    }

    public static byte[] changeC1C3C2ToC1C2C3(byte[] bArr, ECDomainParameters eCDomainParameters) {
        int fieldSize = (((eCDomainParameters.getCurve().getFieldSize() + 7) / 8) * 2) + 1;
        byte[] bArr2 = new byte[bArr.length];
        System.arraycopy(bArr, 0, bArr2, 0, fieldSize);
        System.arraycopy(bArr, fieldSize + 32, bArr2, fieldSize, (bArr.length - fieldSize) - 32);
        System.arraycopy(bArr, fieldSize, bArr2, bArr.length - 32, 32);
        return bArr2;
    }

    public static MacEngine createHmacSm3Engine(byte[] bArr) {
        return new BCHMacEngine((Digest) new SM3Digest(), bArr);
    }

    public static HMac hmacSm3(byte[] bArr) {
        return new HMac(HmacAlgorithm.HmacSM3, bArr);
    }

    public static byte[] rsAsn1ToPlain(byte[] bArr) {
        try {
            BigInteger[] bigIntegerArrDecode = StandardDSAEncoding.INSTANCE.decode(SM2_DOMAIN_PARAMS.getN(), bArr);
            return PrimitiveArrayUtil.addAll(bigIntToFixedLengthBytes(bigIntegerArrDecode[0]), bigIntToFixedLengthBytes(bigIntegerArrDecode[1]));
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static byte[] rsPlainToAsn1(byte[] bArr) {
        if (bArr.length != 64) {
            throw new CryptoException("err rs. ");
        }
        try {
            return StandardDSAEncoding.INSTANCE.encode(SM2_DOMAIN_PARAMS.getN(), new BigInteger(1, Arrays.copyOfRange(bArr, 0, 32)), new BigInteger(1, Arrays.copyOfRange(bArr, 32, 64)));
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static SM2 sm2() {
        return new SM2();
    }

    public static SM3 sm3() {
        return new SM3();
    }

    public static SM3 sm3WithSalt(byte[] bArr) {
        return new SM3(bArr);
    }

    public static SM4 sm4() {
        return new SM4();
    }

    public static SM2 sm2(String str, String str2) {
        return new SM2(str, str2);
    }

    public static String sm3(String str) {
        return sm3().digestHex(str);
    }

    public static SM4 sm4(byte[] bArr) {
        return new SM4(bArr);
    }

    public static SM2 sm2(byte[] bArr, byte[] bArr2) {
        return new SM2(bArr, bArr2);
    }

    public static String sm3(InputStream inputStream) {
        return sm3().digestHex(inputStream);
    }

    public static SM2 sm2(PrivateKey privateKey, PublicKey publicKey) {
        return new SM2(privateKey, publicKey);
    }

    public static String sm3(File file) {
        return sm3().digestHex(file);
    }

    public static SM2 sm2(ECPrivateKeyParameters eCPrivateKeyParameters, ECPublicKeyParameters eCPublicKeyParameters) {
        return new SM2(eCPrivateKeyParameters, eCPublicKeyParameters);
    }
}
