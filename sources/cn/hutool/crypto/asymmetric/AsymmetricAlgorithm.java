package cn.hutool.crypto.asymmetric;

/* loaded from: classes.dex */
public enum AsymmetricAlgorithm {
    RSA("RSA"),
    RSA_ECB_PKCS1("RSA/ECB/PKCS1Padding"),
    RSA_ECB("RSA/ECB/NoPadding"),
    RSA_None("RSA/None/NoPadding");

    private final String value;

    AsymmetricAlgorithm(String str) {
        this.value = str;
    }

    public String getValue() {
        return this.value;
    }
}
