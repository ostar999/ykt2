package cn.hutool.crypto.digest;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.PrimitiveArrayUtil;
import cn.hutool.crypto.CryptoException;
import cn.hutool.crypto.SecureUtil;
import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;

/* loaded from: classes.dex */
public class Digester implements Serializable {
    private static final long serialVersionUID = 1;
    private MessageDigest digest;
    protected int digestCount;
    protected byte[] salt;
    protected int saltPosition;

    public Digester(DigestAlgorithm digestAlgorithm) {
        this(digestAlgorithm.getValue());
    }

    private byte[] digestWithSalt(InputStream inputStream, int i2) throws IOException {
        if (this.saltPosition <= 0) {
            this.digest.update(this.salt);
        }
        byte[] bArr = new byte[i2];
        int i3 = 0;
        while (true) {
            int i4 = inputStream.read(bArr, 0, i2);
            if (i4 <= -1) {
                break;
            }
            i3 += i4;
            int i5 = this.saltPosition;
            if (i5 <= 0 || i3 < i5) {
                this.digest.update(bArr, 0, i4);
            } else {
                if (i3 != i5) {
                    this.digest.update(bArr, 0, i3 - i5);
                }
                this.digest.update(this.salt);
                this.digest.update(bArr, i3 - this.saltPosition, i4);
            }
        }
        if (i3 < this.saltPosition) {
            this.digest.update(this.salt);
        }
        return this.digest.digest();
    }

    private byte[] digestWithoutSalt(InputStream inputStream, int i2) throws IOException {
        byte[] bArr = new byte[i2];
        while (true) {
            int i3 = inputStream.read(bArr, 0, i2);
            if (i3 <= -1) {
                return this.digest.digest();
            }
            this.digest.update(bArr, 0, i3);
        }
    }

    private byte[] doDigest(byte[]... bArr) {
        for (byte[] bArr2 : bArr) {
            if (bArr2 != null) {
                this.digest.update(bArr2);
            }
        }
        return this.digest.digest();
    }

    private byte[] resetAndRepeatDigest(byte[] bArr) {
        int iMax = Math.max(1, this.digestCount);
        reset();
        for (int i2 = 0; i2 < iMax - 1; i2++) {
            bArr = doDigest(bArr);
            reset();
        }
        return bArr;
    }

    public byte[] digest(String str, String str2) {
        return digest(str, CharsetUtil.charset(str2));
    }

    public String digestHex(String str, String str2) {
        return digestHex(str, CharsetUtil.charset(str2));
    }

    public MessageDigest getDigest() {
        return this.digest;
    }

    public int getDigestLength() {
        return this.digest.getDigestLength();
    }

    public Digester init(String str, Provider provider) {
        if (provider == null) {
            this.digest = SecureUtil.createMessageDigest(str);
        } else {
            try {
                this.digest = MessageDigest.getInstance(str, provider);
            } catch (NoSuchAlgorithmException e2) {
                throw new CryptoException(e2);
            }
        }
        return this;
    }

    public Digester reset() {
        this.digest.reset();
        return this;
    }

    public Digester setDigestCount(int i2) {
        this.digestCount = i2;
        return this;
    }

    public Digester setSalt(byte[] bArr) {
        this.salt = bArr;
        return this;
    }

    public Digester setSaltPosition(int i2) {
        this.saltPosition = i2;
        return this;
    }

    public Digester(String str) {
        this(str, (Provider) null);
    }

    public byte[] digest(String str, Charset charset) {
        return digest(CharSequenceUtil.bytes(str, charset));
    }

    public String digestHex(String str, Charset charset) {
        return HexUtil.encodeHexStr(digest(str, charset));
    }

    public Digester(DigestAlgorithm digestAlgorithm, Provider provider) {
        init(digestAlgorithm.getValue(), provider);
    }

    public byte[] digest(String str) {
        return digest(str, CharsetUtil.CHARSET_UTF_8);
    }

    public String digestHex(String str) {
        return digestHex(str, "UTF-8");
    }

    public byte[] digest(File file) throws Throwable {
        BufferedInputStream inputStream;
        try {
            inputStream = FileUtil.getInputStream(file);
        } catch (Throwable th) {
            th = th;
            inputStream = null;
        }
        try {
            byte[] bArrDigest = digest(inputStream);
            IoUtil.close((Closeable) inputStream);
            return bArrDigest;
        } catch (Throwable th2) {
            th = th2;
            IoUtil.close((Closeable) inputStream);
            throw th;
        }
    }

    public String digestHex(File file) {
        return HexUtil.encodeHexStr(digest(file));
    }

    public Digester(String str, Provider provider) {
        init(str, provider);
    }

    public String digestHex(byte[] bArr) {
        return HexUtil.encodeHexStr(digest(bArr));
    }

    public String digestHex(InputStream inputStream) {
        return HexUtil.encodeHexStr(digest(inputStream));
    }

    public Digester(MessageDigest messageDigest) {
        this.digest = messageDigest;
    }

    public String digestHex(InputStream inputStream, int i2) {
        return HexUtil.encodeHexStr(digest(inputStream, i2));
    }

    public byte[] digest(byte[] bArr) {
        byte[] bArrDoDigest;
        int i2 = this.saltPosition;
        if (i2 <= 0) {
            bArrDoDigest = doDigest(this.salt, bArr);
        } else if (i2 >= bArr.length) {
            bArrDoDigest = doDigest(bArr, this.salt);
        } else if (PrimitiveArrayUtil.isNotEmpty(this.salt)) {
            this.digest.update(bArr, 0, this.saltPosition);
            this.digest.update(this.salt);
            MessageDigest messageDigest = this.digest;
            int i3 = this.saltPosition;
            messageDigest.update(bArr, i3, bArr.length - i3);
            bArrDoDigest = this.digest.digest();
        } else {
            bArrDoDigest = doDigest(bArr);
        }
        return resetAndRepeatDigest(bArrDoDigest);
    }

    public byte[] digest(InputStream inputStream) {
        return digest(inputStream, 8192);
    }

    public byte[] digest(InputStream inputStream, int i2) throws IORuntimeException {
        byte[] bArrDigestWithSalt;
        if (i2 < 1) {
            i2 = 8192;
        }
        try {
            if (PrimitiveArrayUtil.isEmpty(this.salt)) {
                bArrDigestWithSalt = digestWithoutSalt(inputStream, i2);
            } else {
                bArrDigestWithSalt = digestWithSalt(inputStream, i2);
            }
            return resetAndRepeatDigest(bArrDigestWithSalt);
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }
}
