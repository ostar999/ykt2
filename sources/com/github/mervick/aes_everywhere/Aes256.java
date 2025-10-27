package com.github.mervick.aes_everywhere;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/* loaded from: classes3.dex */
public class Aes256 extends AbstractAes256 {
    public static String decrypt(String str, String str2) throws Exception {
        return new String(AbstractAes256._decrypt(Base64.getDecoder().decode(str), str2.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
    }

    public static String encrypt(String str, String str2) throws Exception {
        return Base64.getEncoder().encodeToString(AbstractAes256._encrypt(str.getBytes(StandardCharsets.UTF_8), str2.getBytes(StandardCharsets.UTF_8)));
    }

    public static byte[] decrypt(byte[] bArr, byte[] bArr2) throws Exception {
        return AbstractAes256._decrypt(Base64.getDecoder().decode(bArr), bArr2);
    }

    public static byte[] encrypt(byte[] bArr, byte[] bArr2) throws Exception {
        return Base64.getEncoder().encode(AbstractAes256._encrypt(bArr, bArr2));
    }
}
