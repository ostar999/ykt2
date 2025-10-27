package cn.hutool.crypto.digest;

/* loaded from: classes.dex */
public class SM3 extends Digester {
    public static final String ALGORITHM_NAME = "SM3";
    private static final long serialVersionUID = 1;

    public SM3() {
        super(ALGORITHM_NAME);
    }

    public static SM3 create() {
        return new SM3();
    }

    public SM3(byte[] bArr) {
        this(bArr, 0, 1);
    }

    public SM3(byte[] bArr, int i2) {
        this(bArr, 0, i2);
    }

    public SM3(byte[] bArr, int i2, int i3) {
        this();
        this.salt = bArr;
        this.saltPosition = i2;
        this.digestCount = i3;
    }
}
