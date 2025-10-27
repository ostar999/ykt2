package cn.hutool.crypto;

import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import java.util.Map;

/* loaded from: classes.dex */
public class SignUtil {
    public static Sign sign(SignAlgorithm signAlgorithm) {
        return new Sign(signAlgorithm);
    }

    public static String signParams(SymmetricCrypto symmetricCrypto, Map<?, ?> map, String... strArr) {
        return signParams(symmetricCrypto, map, "", "", true, strArr);
    }

    public static String signParamsMd5(Map<?, ?> map, String... strArr) {
        return signParams(DigestAlgorithm.MD5, map, strArr);
    }

    public static String signParamsSha1(Map<?, ?> map, String... strArr) {
        return signParams(DigestAlgorithm.SHA1, map, strArr);
    }

    public static String signParamsSha256(Map<?, ?> map, String... strArr) {
        return signParams(DigestAlgorithm.SHA256, map, strArr);
    }

    public static Sign sign(SignAlgorithm signAlgorithm, String str, String str2) {
        return new Sign(signAlgorithm, str, str2);
    }

    public static String signParams(SymmetricCrypto symmetricCrypto, Map<?, ?> map, String str, String str2, boolean z2, String... strArr) {
        return symmetricCrypto.encryptHex(MapUtil.sortJoin(map, str, str2, z2, strArr));
    }

    public static Sign sign(SignAlgorithm signAlgorithm, byte[] bArr, byte[] bArr2) {
        return new Sign(signAlgorithm, bArr, bArr2);
    }

    public static String signParams(DigestAlgorithm digestAlgorithm, Map<?, ?> map, String... strArr) {
        return signParams(digestAlgorithm, map, "", "", true, strArr);
    }

    public static String signParams(DigestAlgorithm digestAlgorithm, Map<?, ?> map, String str, String str2, boolean z2, String... strArr) {
        return new Digester(digestAlgorithm).digestHex(MapUtil.sortJoin(map, str, str2, z2, strArr));
    }
}
