package net.lingala.zip4j.crypto.PBKDF2;

/* loaded from: classes9.dex */
public class PBKDF2Parameters {
    protected byte[] derivedKey;
    protected String hashAlgorithm;
    protected String hashCharset;
    protected int iterationCount;
    protected byte[] salt;

    public PBKDF2Parameters() {
        this.hashAlgorithm = null;
        this.hashCharset = "UTF-8";
        this.salt = null;
        this.iterationCount = 1000;
        this.derivedKey = null;
    }

    public byte[] getDerivedKey() {
        return this.derivedKey;
    }

    public String getHashAlgorithm() {
        return this.hashAlgorithm;
    }

    public String getHashCharset() {
        return this.hashCharset;
    }

    public int getIterationCount() {
        return this.iterationCount;
    }

    public byte[] getSalt() {
        return this.salt;
    }

    public void setDerivedKey(byte[] bArr) {
        this.derivedKey = bArr;
    }

    public void setHashAlgorithm(String str) {
        this.hashAlgorithm = str;
    }

    public void setHashCharset(String str) {
        this.hashCharset = str;
    }

    public void setIterationCount(int i2) {
        this.iterationCount = i2;
    }

    public void setSalt(byte[] bArr) {
        this.salt = bArr;
    }

    public PBKDF2Parameters(String str, String str2, byte[] bArr, int i2) {
        this.hashAlgorithm = str;
        this.hashCharset = str2;
        this.salt = bArr;
        this.iterationCount = i2;
        this.derivedKey = null;
    }

    public PBKDF2Parameters(String str, String str2, byte[] bArr, int i2, byte[] bArr2) {
        this.hashAlgorithm = str;
        this.hashCharset = str2;
        this.salt = bArr;
        this.iterationCount = i2;
        this.derivedKey = bArr2;
    }
}
