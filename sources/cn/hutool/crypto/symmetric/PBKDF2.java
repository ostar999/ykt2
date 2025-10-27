package cn.hutool.crypto.symmetric;

import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.KeyUtil;
import javax.crypto.spec.PBEKeySpec;

/* loaded from: classes.dex */
public class PBKDF2 {
    private String algorithm;
    private int iterationCount;
    private int keyLength;

    public PBKDF2() {
        this.algorithm = "PBKDF2WithHmacSHA1";
        this.keyLength = 512;
        this.iterationCount = 1000;
    }

    public byte[] encrypt(char[] cArr, byte[] bArr) {
        return KeyUtil.generateKey(this.algorithm, new PBEKeySpec(cArr, bArr, this.iterationCount, this.keyLength)).getEncoded();
    }

    public String encryptHex(char[] cArr, byte[] bArr) {
        return HexUtil.encodeHexStr(encrypt(cArr, bArr));
    }

    public PBKDF2(String str, int i2, int i3) {
        this.algorithm = str;
        this.keyLength = i2;
        this.iterationCount = i3;
    }
}
