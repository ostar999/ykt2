package cn.hutool.crypto.digest;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;

/* loaded from: classes.dex */
public class MD5 extends Digester {
    private static final DigesterFactory FACTORY = DigesterFactory.ofJdk(DigestAlgorithm.MD5.getValue());
    private static final long serialVersionUID = 1;

    public MD5() {
        super(FACTORY.createMessageDigester());
    }

    public static MD5 create() {
        return new MD5();
    }

    public String digestHex16(String str, Charset charset) {
        return DigestUtil.md5HexTo16(digestHex(str, charset));
    }

    public MD5(byte[] bArr) {
        this(bArr, 0, 1);
    }

    public String digestHex16(String str) {
        return DigestUtil.md5HexTo16(digestHex(str));
    }

    public MD5(byte[] bArr, int i2) {
        this(bArr, 0, i2);
    }

    public String digestHex16(InputStream inputStream) {
        return DigestUtil.md5HexTo16(digestHex(inputStream));
    }

    public MD5(byte[] bArr, int i2, int i3) {
        this();
        this.salt = bArr;
        this.saltPosition = i2;
        this.digestCount = i3;
    }

    public String digestHex16(File file) {
        return DigestUtil.md5HexTo16(digestHex(file));
    }

    public String digestHex16(byte[] bArr) {
        return DigestUtil.md5HexTo16(digestHex(bArr));
    }
}
