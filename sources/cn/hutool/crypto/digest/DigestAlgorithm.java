package cn.hutool.crypto.digest;

/* loaded from: classes.dex */
public enum DigestAlgorithm {
    MD2("MD2"),
    MD5("MD5"),
    SHA1("SHA-1"),
    SHA256("SHA-256"),
    SHA384("SHA-384"),
    SHA512("SHA-512");

    private final String value;

    DigestAlgorithm(String str) {
        this.value = str;
    }

    public String getValue() {
        return this.value;
    }
}
