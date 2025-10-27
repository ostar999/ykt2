package com.github.mervick.aes_everywhere.legacy;

import com.github.mervick.aes_everywhere.AbstractAes256;
import java.nio.charset.StandardCharsets;

/* loaded from: classes3.dex */
public class Aes256 extends AbstractAes256 {
    public static String decrypt(String str, String str2) throws Exception {
        return new String(AbstractAes256._decrypt(Base64.decode(str, 2), str2.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
    }

    public static String encrypt(String str, String str2) throws Exception {
        return Base64.encodeToString(AbstractAes256._encrypt(str.getBytes(StandardCharsets.UTF_8), str2.getBytes(StandardCharsets.UTF_8)), 2);
    }

    public static byte[] decrypt(byte[] bArr, byte[] bArr2) throws Exception {
        return AbstractAes256._decrypt(Base64.decode(bArr, 2), bArr2);
    }

    public static byte[] encrypt(byte[] bArr, byte[] bArr2) throws Exception {
        return Base64.encode(AbstractAes256._encrypt(bArr, bArr2), 2);
    }
}
