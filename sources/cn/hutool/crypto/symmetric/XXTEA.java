package cn.hutool.crypto.symmetric;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.charset.Charset;

/* loaded from: classes.dex */
public class XXTEA implements SymmetricEncryptor, SymmetricDecryptor, Serializable {
    private static final int DELTA = -1640531527;
    private static final long serialVersionUID = 1;
    private final byte[] key;

    public XXTEA(byte[] bArr) {
        this.key = bArr;
    }

    private static byte[] fixKey(byte[] bArr) {
        if (bArr.length == 16) {
            return bArr;
        }
        byte[] bArr2 = new byte[16];
        System.arraycopy(bArr, 0, bArr2, 0, Math.min(bArr.length, 16));
        return bArr2;
    }

    private static int mx(int i2, int i3, int i4, int i5, int i6, int[] iArr) {
        return ((i2 ^ i3) + (iArr[(i5 & 3) ^ i6] ^ i4)) ^ (((i4 >>> 5) ^ (i3 << 2)) + ((i3 >>> 3) ^ (i4 << 4)));
    }

    private static byte[] toByteArray(int[] iArr, boolean z2) {
        int length = iArr.length << 2;
        if (z2) {
            int i2 = iArr[iArr.length - 1];
            int i3 = length - 4;
            if (i2 < i3 - 3 || i2 > i3) {
                return null;
            }
            length = i2;
        }
        byte[] bArr = new byte[length];
        for (int i4 = 0; i4 < length; i4++) {
            bArr[i4] = (byte) (iArr[i4 >>> 2] >>> ((i4 & 3) << 3));
        }
        return bArr;
    }

    private static int[] toIntArray(byte[] bArr, boolean z2) {
        int[] iArr;
        int length = (bArr.length & 3) == 0 ? bArr.length >>> 2 : (bArr.length >>> 2) + 1;
        if (z2) {
            iArr = new int[length + 1];
            iArr[length] = bArr.length;
        } else {
            iArr = new int[length];
        }
        int length2 = bArr.length;
        for (int i2 = 0; i2 < length2; i2++) {
            int i3 = i2 >>> 2;
            iArr[i3] = iArr[i3] | ((bArr[i2] & 255) << ((i2 & 3) << 3));
        }
        return iArr;
    }

    @Override // cn.hutool.crypto.symmetric.SymmetricDecryptor
    public /* synthetic */ byte[] decrypt(InputStream inputStream) {
        return c.a(this, inputStream);
    }

    @Override // cn.hutool.crypto.symmetric.SymmetricDecryptor
    public /* synthetic */ byte[] decrypt(String str) {
        return c.b(this, str);
    }

    @Override // cn.hutool.crypto.symmetric.SymmetricDecryptor
    public byte[] decrypt(byte[] bArr) {
        return bArr.length == 0 ? bArr : toByteArray(decrypt(toIntArray(bArr, false), toIntArray(fixKey(this.key), false)), true);
    }

    @Override // cn.hutool.crypto.symmetric.SymmetricDecryptor
    public /* synthetic */ String decryptStr(InputStream inputStream) {
        return c.c(this, inputStream);
    }

    @Override // cn.hutool.crypto.symmetric.SymmetricDecryptor
    public /* synthetic */ String decryptStr(InputStream inputStream, Charset charset) {
        return c.d(this, inputStream, charset);
    }

    @Override // cn.hutool.crypto.symmetric.SymmetricDecryptor
    public /* synthetic */ String decryptStr(String str) {
        return c.e(this, str);
    }

    @Override // cn.hutool.crypto.symmetric.SymmetricDecryptor
    public /* synthetic */ String decryptStr(String str, Charset charset) {
        return c.f(this, str, charset);
    }

    @Override // cn.hutool.crypto.symmetric.SymmetricDecryptor
    public /* synthetic */ String decryptStr(byte[] bArr) {
        return c.g(this, bArr);
    }

    @Override // cn.hutool.crypto.symmetric.SymmetricDecryptor
    public /* synthetic */ String decryptStr(byte[] bArr, Charset charset) {
        return c.h(this, bArr, charset);
    }

    @Override // cn.hutool.crypto.symmetric.SymmetricEncryptor
    public /* synthetic */ byte[] encrypt(InputStream inputStream) {
        return d.a(this, inputStream);
    }

    @Override // cn.hutool.crypto.symmetric.SymmetricEncryptor
    public /* synthetic */ byte[] encrypt(String str) {
        return d.b(this, str);
    }

    @Override // cn.hutool.crypto.symmetric.SymmetricEncryptor
    public /* synthetic */ byte[] encrypt(String str, String str2) {
        return d.c(this, str, str2);
    }

    @Override // cn.hutool.crypto.symmetric.SymmetricEncryptor
    public /* synthetic */ byte[] encrypt(String str, Charset charset) {
        return d.d(this, str, charset);
    }

    @Override // cn.hutool.crypto.symmetric.SymmetricEncryptor
    public byte[] encrypt(byte[] bArr) {
        return bArr.length == 0 ? bArr : toByteArray(encrypt(toIntArray(bArr, true), toIntArray(fixKey(this.key), false)), false);
    }

    @Override // cn.hutool.crypto.symmetric.SymmetricEncryptor
    public /* synthetic */ String encryptBase64(InputStream inputStream) {
        return d.e(this, inputStream);
    }

    @Override // cn.hutool.crypto.symmetric.SymmetricEncryptor
    public /* synthetic */ String encryptBase64(String str) {
        return d.f(this, str);
    }

    @Override // cn.hutool.crypto.symmetric.SymmetricEncryptor
    public /* synthetic */ String encryptBase64(String str, String str2) {
        return d.g(this, str, str2);
    }

    @Override // cn.hutool.crypto.symmetric.SymmetricEncryptor
    public /* synthetic */ String encryptBase64(String str, Charset charset) {
        return d.h(this, str, charset);
    }

    @Override // cn.hutool.crypto.symmetric.SymmetricEncryptor
    public /* synthetic */ String encryptBase64(byte[] bArr) {
        return d.i(this, bArr);
    }

    @Override // cn.hutool.crypto.symmetric.SymmetricEncryptor
    public /* synthetic */ String encryptHex(InputStream inputStream) {
        return d.j(this, inputStream);
    }

    @Override // cn.hutool.crypto.symmetric.SymmetricEncryptor
    public /* synthetic */ String encryptHex(String str) {
        return d.k(this, str);
    }

    @Override // cn.hutool.crypto.symmetric.SymmetricEncryptor
    public /* synthetic */ String encryptHex(String str, String str2) {
        return d.l(this, str, str2);
    }

    @Override // cn.hutool.crypto.symmetric.SymmetricEncryptor
    public /* synthetic */ String encryptHex(String str, Charset charset) {
        return d.m(this, str, charset);
    }

    @Override // cn.hutool.crypto.symmetric.SymmetricEncryptor
    public /* synthetic */ String encryptHex(byte[] bArr) {
        return d.n(this, bArr);
    }

    @Override // cn.hutool.crypto.symmetric.SymmetricDecryptor
    public void decrypt(InputStream inputStream, OutputStream outputStream, boolean z2) throws IOException, IORuntimeException {
        IoUtil.write(outputStream, z2, decrypt(IoUtil.readBytes(inputStream)));
    }

    @Override // cn.hutool.crypto.symmetric.SymmetricEncryptor
    public void encrypt(InputStream inputStream, OutputStream outputStream, boolean z2) throws IOException, IORuntimeException {
        IoUtil.write(outputStream, z2, encrypt(IoUtil.readBytes(inputStream)));
    }

    private static int[] decrypt(int[] iArr, int[] iArr2) {
        int length = iArr.length - 1;
        if (length < 1) {
            return iArr;
        }
        int iMx = iArr[0];
        for (int i2 = ((52 / (length + 1)) + 6) * DELTA; i2 != 0; i2 -= DELTA) {
            int i3 = (i2 >>> 2) & 3;
            int iMx2 = iMx;
            int i4 = length;
            while (i4 > 0) {
                iMx2 = iArr[i4] - mx(i2, iMx2, iArr[i4 - 1], i4, i3, iArr2);
                iArr[i4] = iMx2;
                i4--;
            }
            iMx = iArr[0] - mx(i2, iMx2, iArr[length], i4, i3, iArr2);
            iArr[0] = iMx;
        }
        return iArr;
    }

    private static int[] encrypt(int[] iArr, int[] iArr2) {
        int length = iArr.length - 1;
        if (length < 1) {
            return iArr;
        }
        int i2 = (52 / (length + 1)) + 6;
        int iMx = iArr[length];
        int i3 = 0;
        while (true) {
            int i4 = i2 - 1;
            if (i2 <= 0) {
                return iArr;
            }
            i3 += DELTA;
            int i5 = (i3 >>> 2) & 3;
            int iMx2 = iMx;
            int i6 = 0;
            while (i6 < length) {
                int i7 = i6 + 1;
                iMx2 = iArr[i6] + mx(i3, iArr[i7], iMx2, i6, i5, iArr2);
                iArr[i6] = iMx2;
                i6 = i7;
            }
            iMx = mx(i3, iArr[0], iMx2, i6, i5, iArr2) + iArr[length];
            iArr[length] = iMx;
            i2 = i4;
        }
    }
}
