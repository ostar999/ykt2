package cn.hutool.crypto.digest.mac;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.security.MessageDigest;

/* loaded from: classes.dex */
public class Mac implements Serializable {
    private static final long serialVersionUID = 1;
    private final MacEngine engine;

    public Mac(MacEngine macEngine) {
        this.engine = macEngine;
    }

    public byte[] digest(String str, Charset charset) {
        return digest(CharSequenceUtil.bytes(str, charset));
    }

    public String digestBase64(String str, boolean z2) {
        return digestBase64(str, CharsetUtil.CHARSET_UTF_8, z2);
    }

    public String digestHex(String str, Charset charset) {
        return HexUtil.encodeHexStr(digest(str, charset));
    }

    public String getAlgorithm() {
        return this.engine.getAlgorithm();
    }

    public MacEngine getEngine() {
        return this.engine;
    }

    public int getMacLength() {
        return this.engine.getMacLength();
    }

    public boolean verify(byte[] bArr, byte[] bArr2) {
        return MessageDigest.isEqual(bArr, bArr2);
    }

    public byte[] digest(String str) {
        return digest(str, CharsetUtil.CHARSET_UTF_8);
    }

    public String digestBase64(String str, Charset charset, boolean z2) {
        byte[] bArrDigest = digest(str, charset);
        return z2 ? Base64.encodeUrlSafe(bArrDigest) : Base64.encode(bArrDigest);
    }

    public String digestHex(String str) {
        return digestHex(str, CharsetUtil.CHARSET_UTF_8);
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

    public String digestHex(byte[] bArr) {
        return HexUtil.encodeHexStr(digest(bArr));
    }

    public String digestHex(InputStream inputStream) {
        return HexUtil.encodeHexStr(digest(inputStream));
    }

    public String digestHex(InputStream inputStream, int i2) {
        return HexUtil.encodeHexStr(digest(inputStream, i2));
    }

    public byte[] digest(byte[] bArr) {
        return digest(new ByteArrayInputStream(bArr), -1);
    }

    public byte[] digest(InputStream inputStream) {
        return digest(inputStream, 8192);
    }

    public byte[] digest(InputStream inputStream, int i2) {
        return this.engine.digest(inputStream, i2);
    }
}
