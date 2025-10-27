package cn.hutool.crypto.symmetric;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.CryptoException;
import cn.hutool.crypto.SecureUtil;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* loaded from: classes.dex */
public class RC4 implements Serializable {
    private static final int KEY_MIN_LENGTH = 5;
    private static final int SBOX_LENGTH = 256;
    private static final long serialVersionUID = 1;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private int[] sbox;

    public RC4(String str) throws CryptoException {
        setKey(str);
    }

    private int[] initSBox(byte[] bArr) {
        int[] iArr = new int[256];
        for (int i2 = 0; i2 < 256; i2++) {
            iArr[i2] = i2;
        }
        int i3 = 0;
        for (int i4 = 0; i4 < 256; i4++) {
            i3 = (((i3 + iArr[i4]) + bArr[i4 % bArr.length]) & 255) % 256;
            swap(i4, i3, iArr);
        }
        return iArr;
    }

    private void swap(int i2, int i3, int[] iArr) {
        int i4 = iArr[i2];
        iArr[i2] = iArr[i3];
        iArr[i3] = i4;
    }

    public byte[] crypt(byte[] bArr) {
        ReentrantReadWriteLock.ReadLock lock = this.lock.readLock();
        lock.lock();
        try {
            int[] iArr = (int[]) this.sbox.clone();
            byte[] bArr2 = new byte[bArr.length];
            int i2 = 0;
            int i3 = 0;
            for (int i4 = 0; i4 < bArr.length; i4++) {
                i2 = (i2 + 1) % 256;
                i3 = (i3 + iArr[i2]) % 256;
                swap(i2, i3, iArr);
                bArr2[i4] = (byte) (iArr[(iArr[i2] + iArr[i3]) % 256] ^ bArr[i4]);
            }
            return bArr2;
        } finally {
            lock.unlock();
        }
    }

    public String decrypt(byte[] bArr, Charset charset) throws CryptoException {
        return StrUtil.str(crypt(bArr), charset);
    }

    public byte[] encrypt(String str, Charset charset) throws CryptoException {
        return crypt(CharSequenceUtil.bytes(str, charset));
    }

    public String encryptBase64(byte[] bArr) {
        return Base64.encode(crypt(bArr));
    }

    public String encryptHex(byte[] bArr) {
        return HexUtil.encodeHexStr(crypt(bArr));
    }

    public void setKey(String str) throws CryptoException {
        int length = str.length();
        if (length < 5 || length >= 256) {
            throw new CryptoException("Key length has to be between {} and {}", 5, 255);
        }
        ReentrantReadWriteLock.WriteLock writeLock = this.lock.writeLock();
        writeLock.lock();
        try {
            this.sbox = initSBox(CharSequenceUtil.utf8Bytes(str));
        } finally {
            writeLock.unlock();
        }
    }

    public String decrypt(byte[] bArr) throws CryptoException {
        return decrypt(bArr, CharsetUtil.CHARSET_UTF_8);
    }

    public byte[] encrypt(String str) throws CryptoException {
        return encrypt(str, CharsetUtil.CHARSET_UTF_8);
    }

    public String encryptBase64(String str, Charset charset) {
        return Base64.encode(encrypt(str, charset));
    }

    public String encryptHex(String str, Charset charset) {
        return HexUtil.encodeHexStr(encrypt(str, charset));
    }

    public String decrypt(String str) {
        return decrypt(SecureUtil.decode(str));
    }

    public String encryptBase64(String str) {
        return Base64.encode(encrypt(str));
    }

    public String encryptHex(String str) {
        return HexUtil.encodeHexStr(encrypt(str));
    }

    public String decrypt(String str, Charset charset) {
        return StrUtil.str(decrypt(str), charset);
    }
}
