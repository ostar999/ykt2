package cn.hutool.crypto.digest;

/* loaded from: classes.dex */
public enum HmacAlgorithm {
    HmacMD5("HmacMD5"),
    HmacSHA1("HmacSHA1"),
    HmacSHA256("HmacSHA256"),
    HmacSHA384("HmacSHA384"),
    HmacSHA512("HmacSHA512"),
    HmacSM3("HmacSM3"),
    SM4CMAC("SM4CMAC");

    private final String value;

    HmacAlgorithm(String str) {
        this.value = str;
    }

    public String getValue() {
        return this.value;
    }
}
