package cn.hutool.core.lang.hash;

import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.text.CharSequenceUtil;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import net.lingala.zip4j.util.InternalZipConstants;

/* loaded from: classes.dex */
public class KetamaHash implements Hash64<String>, Hash32<String> {
    private static byte[] md5(String str) throws NoSuchAlgorithmException {
        try {
            return MessageDigest.getInstance("MD5").digest(CharSequenceUtil.utf8Bytes(str));
        } catch (NoSuchAlgorithmException e2) {
            throw new UtilException("MD5 algorithm not suooport!", e2);
        }
    }

    @Override // cn.hutool.core.lang.hash.Hash64, cn.hutool.core.lang.hash.Hash
    public Number hash(String str) {
        return Long.valueOf(hash64(str));
    }

    @Override // cn.hutool.core.lang.hash.Hash32
    public int hash32(String str) {
        return (int) (hash64(str) & InternalZipConstants.ZIP_64_LIMIT);
    }

    @Override // cn.hutool.core.lang.hash.Hash64
    public long hash64(String str) throws NoSuchAlgorithmException {
        byte[] bArrMd5 = md5(str);
        return ((bArrMd5[3] & 255) << 24) | ((bArrMd5[2] & 255) << 16) | ((bArrMd5[1] & 255) << 8) | (bArrMd5[0] & 255);
    }
}
