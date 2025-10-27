package cn.hutool.crypto.asymmetric;

/* loaded from: classes.dex */
public enum KeyType {
    PublicKey(1),
    PrivateKey(2),
    SecretKey(3);

    private final int value;

    KeyType(int i2) {
        this.value = i2;
    }

    public int getValue() {
        return this.value;
    }
}
