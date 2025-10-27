package com.nostra13.universalimageloader.cache.disc.naming;

import com.nostra13.universalimageloader.utils.L;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes4.dex */
public class Md5FileNameGenerator implements FileNameGenerator {
    private static final String HASH_ALGORITHM = "MD5";
    private static final int RADIX = 36;

    private byte[] getMD5(byte[] bArr) throws NoSuchAlgorithmException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(HASH_ALGORITHM);
            messageDigest.update(bArr);
            return messageDigest.digest();
        } catch (NoSuchAlgorithmException e2) {
            L.e(e2);
            return null;
        }
    }

    @Override // com.nostra13.universalimageloader.cache.disc.naming.FileNameGenerator
    public String generate(String str) {
        return new BigInteger(getMD5(str.getBytes())).abs().toString(36);
    }
}
