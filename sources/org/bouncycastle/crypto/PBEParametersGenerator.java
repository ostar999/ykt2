package org.bouncycastle.crypto;

import org.bouncycastle.util.Strings;

/* loaded from: classes9.dex */
public abstract class PBEParametersGenerator {
    protected int iterationCount;
    protected byte[] password;
    protected byte[] salt;

    public static byte[] PKCS12PasswordToBytes(char[] cArr) {
        if (cArr.length <= 0) {
            return new byte[0];
        }
        byte[] bArr = new byte[(cArr.length + 1) * 2];
        for (int i2 = 0; i2 != cArr.length; i2++) {
            int i3 = i2 * 2;
            char c3 = cArr[i2];
            bArr[i3] = (byte) (c3 >>> '\b');
            bArr[i3 + 1] = (byte) c3;
        }
        return bArr;
    }

    public static byte[] PKCS5PasswordToBytes(char[] cArr) {
        int length = cArr.length;
        byte[] bArr = new byte[length];
        for (int i2 = 0; i2 != length; i2++) {
            bArr[i2] = (byte) cArr[i2];
        }
        return bArr;
    }

    public static byte[] PKCS5PasswordToUTF8Bytes(char[] cArr) {
        return Strings.toUTF8ByteArray(cArr);
    }

    public abstract CipherParameters generateDerivedMacParameters(int i2);

    public abstract CipherParameters generateDerivedParameters(int i2);

    public abstract CipherParameters generateDerivedParameters(int i2, int i3);

    public int getIterationCount() {
        return this.iterationCount;
    }

    public byte[] getPassword() {
        return this.password;
    }

    public byte[] getSalt() {
        return this.salt;
    }

    public void init(byte[] bArr, byte[] bArr2, int i2) {
        this.password = bArr;
        this.salt = bArr2;
        this.iterationCount = i2;
    }
}
