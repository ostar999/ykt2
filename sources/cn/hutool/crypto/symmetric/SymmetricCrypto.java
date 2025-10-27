package cn.hutool.crypto.symmetric;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Opt;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.PrimitiveArrayUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.CipherMode;
import cn.hutool.crypto.CipherWrapper;
import cn.hutool.crypto.CryptoException;
import cn.hutool.crypto.KeyUtil;
import cn.hutool.crypto.Padding;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEParameterSpec;

/* loaded from: classes.dex */
public class SymmetricCrypto implements SymmetricEncryptor, SymmetricDecryptor, Serializable {
    private static final long serialVersionUID = 1;
    private CipherWrapper cipherWrapper;
    private boolean isZeroPadding;
    private final Lock lock;
    private SecretKey secretKey;

    public SymmetricCrypto(SymmetricAlgorithm symmetricAlgorithm) {
        this(symmetricAlgorithm, (byte[]) null);
    }

    private static void copyForZeroPadding(CipherInputStream cipherInputStream, OutputStream outputStream, int i2) throws IOException {
        int iMax = i2 * (8192 > i2 ? Math.max(1, 8192 / i2) : 1);
        byte[] bArr = new byte[iMax];
        byte[] bArr2 = new byte[iMax];
        boolean z2 = true;
        int i3 = 0;
        while (true) {
            int i4 = cipherInputStream.read(bArr2);
            if (i4 == -1) {
                break;
            }
            if (z2) {
                z2 = false;
            } else {
                outputStream.write(bArr, 0, i3);
            }
            ArrayUtil.copy(bArr2, bArr, i4);
            i3 = i4;
        }
        int i5 = i3 - 1;
        while (i5 >= 0 && bArr[i5] == 0) {
            i5--;
        }
        outputStream.write(bArr, 0, i5 + 1);
        outputStream.flush();
    }

    private Cipher initMode(int i2) throws InvalidKeyException, InvalidAlgorithmParameterException {
        return this.cipherWrapper.initMode(i2, this.secretKey).getCipher();
    }

    private SymmetricCrypto initParams(String str, AlgorithmParameterSpec algorithmParameterSpec) {
        if (algorithmParameterSpec == null) {
            byte[] bArrRandomBytes = (byte[]) Opt.ofNullable(this.cipherWrapper).map(new Function() { // from class: cn.hutool.crypto.symmetric.a
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((CipherWrapper) obj).getCipher();
                }
            }).map(new Function() { // from class: cn.hutool.crypto.symmetric.b
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((Cipher) obj).getIV();
                }
            }).get();
            if (CharSequenceUtil.startWithIgnoreCase(str, "PBE")) {
                if (bArrRandomBytes == null) {
                    bArrRandomBytes = RandomUtil.randomBytes(8);
                }
                algorithmParameterSpec = new PBEParameterSpec(bArrRandomBytes, 100);
            } else if (CharSequenceUtil.startWithIgnoreCase(str, "AES") && bArrRandomBytes != null) {
                algorithmParameterSpec = new IvParameterSpec(bArrRandomBytes);
            }
        }
        return setParams(algorithmParameterSpec);
    }

    private byte[] paddingDataWithZero(byte[] bArr, int i2) {
        int length;
        int length2;
        return (!this.isZeroPadding || (length2 = (length = bArr.length) % i2) <= 0) ? bArr : PrimitiveArrayUtil.resize(bArr, (length + i2) - length2);
    }

    private byte[] removePadding(byte[] bArr, int i2) {
        if (!this.isZeroPadding || i2 <= 0) {
            return bArr;
        }
        int length = bArr.length;
        if (length % i2 != 0) {
            return bArr;
        }
        int i3 = length - 1;
        while (i3 >= 0 && bArr[i3] == 0) {
            i3--;
        }
        return PrimitiveArrayUtil.resize(bArr, i3 + 1);
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
        this.lock.lock();
        try {
            try {
                Cipher cipherInitMode = initMode(2);
                int blockSize = cipherInitMode.getBlockSize();
                byte[] bArrDoFinal = cipherInitMode.doFinal(bArr);
                this.lock.unlock();
                return removePadding(bArrDoFinal, blockSize);
            } catch (Exception e2) {
                throw new CryptoException(e2);
            }
        } catch (Throwable th) {
            this.lock.unlock();
            throw th;
        }
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
        this.lock.lock();
        try {
            try {
                Cipher cipherInitMode = initMode(1);
                return cipherInitMode.doFinal(paddingDataWithZero(bArr, cipherInitMode.getBlockSize()));
            } catch (Exception e2) {
                throw new CryptoException(e2);
            }
        } finally {
            this.lock.unlock();
        }
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

    public Cipher getCipher() {
        return this.cipherWrapper.getCipher();
    }

    public SecretKey getSecretKey() {
        return this.secretKey;
    }

    public SymmetricCrypto init(String str, SecretKey secretKey) throws IllegalArgumentException {
        Assert.notBlank(str, "'algorithm' must be not blank !", new Object[0]);
        this.secretKey = secretKey;
        Padding padding = Padding.ZeroPadding;
        if (str.contains(padding.name())) {
            str = CharSequenceUtil.replace(str, padding.name(), Padding.NoPadding.name());
            this.isZeroPadding = true;
        }
        this.cipherWrapper = new CipherWrapper(str);
        return this;
    }

    public SymmetricCrypto setIv(IvParameterSpec ivParameterSpec) {
        return setParams(ivParameterSpec);
    }

    public SymmetricCrypto setMode(CipherMode cipherMode) {
        this.lock.lock();
        try {
            try {
                initMode(cipherMode.getValue());
                return this;
            } catch (Exception e2) {
                throw new CryptoException(e2);
            }
        } finally {
            this.lock.unlock();
        }
    }

    public SymmetricCrypto setParams(AlgorithmParameterSpec algorithmParameterSpec) {
        this.cipherWrapper.setParams(algorithmParameterSpec);
        return this;
    }

    public SymmetricCrypto setRandom(SecureRandom secureRandom) {
        this.cipherWrapper.setRandom(secureRandom);
        return this;
    }

    public byte[] update(byte[] bArr) {
        Cipher cipher = this.cipherWrapper.getCipher();
        this.lock.lock();
        try {
            try {
                return cipher.update(paddingDataWithZero(bArr, cipher.getBlockSize()));
            } catch (Exception e2) {
                throw new CryptoException(e2);
            }
        } finally {
            this.lock.unlock();
        }
    }

    public String updateHex(byte[] bArr) {
        return HexUtil.encodeHexStr(update(bArr));
    }

    public SymmetricCrypto(String str) {
        this(str, (byte[]) null);
    }

    public SymmetricCrypto setIv(byte[] bArr) {
        return setIv(new IvParameterSpec(bArr));
    }

    public SymmetricCrypto(SymmetricAlgorithm symmetricAlgorithm, byte[] bArr) {
        this(symmetricAlgorithm.getValue(), bArr);
    }

    public SymmetricCrypto(SymmetricAlgorithm symmetricAlgorithm, SecretKey secretKey) {
        this(symmetricAlgorithm.getValue(), secretKey);
    }

    public SymmetricCrypto(String str, byte[] bArr) {
        this(str, KeyUtil.generateKey(str, bArr));
    }

    public SymmetricCrypto(String str, SecretKey secretKey) {
        this(str, secretKey, null);
    }

    public SymmetricCrypto(String str, SecretKey secretKey, AlgorithmParameterSpec algorithmParameterSpec) throws IllegalArgumentException {
        this.lock = new ReentrantLock();
        init(str, secretKey);
        initParams(str, algorithmParameterSpec);
    }

    @Override // cn.hutool.crypto.symmetric.SymmetricEncryptor
    public void encrypt(InputStream inputStream, OutputStream outputStream, boolean z2) throws Throwable {
        Cipher cipherInitMode;
        CipherOutputStream cipherOutputStream;
        int blockSize;
        int i2;
        this.lock.lock();
        CipherOutputStream cipherOutputStream2 = null;
        try {
            try {
                cipherInitMode = initMode(1);
                cipherOutputStream = new CipherOutputStream(outputStream, cipherInitMode);
            } catch (IORuntimeException e2) {
                throw e2;
            } catch (Exception e3) {
                e = e3;
            }
            try {
                long jCopy = IoUtil.copy(inputStream, cipherOutputStream);
                if (this.isZeroPadding && (blockSize = cipherInitMode.getBlockSize()) > 0 && (i2 = (int) (jCopy % blockSize)) > 0) {
                    cipherOutputStream.write(new byte[blockSize - i2]);
                    cipherOutputStream.flush();
                }
                this.lock.unlock();
                IoUtil.close((Closeable) cipherOutputStream);
                if (z2) {
                    IoUtil.close((Closeable) inputStream);
                }
            } catch (IORuntimeException e4) {
            } catch (Exception e5) {
                e = e5;
                throw new CryptoException(e);
            } catch (Throwable th) {
                th = th;
                cipherOutputStream2 = cipherOutputStream;
                this.lock.unlock();
                IoUtil.close((Closeable) cipherOutputStream2);
                if (z2) {
                    IoUtil.close((Closeable) inputStream);
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    @Override // cn.hutool.crypto.symmetric.SymmetricDecryptor
    public void decrypt(InputStream inputStream, OutputStream outputStream, boolean z2) throws Throwable {
        int blockSize;
        this.lock.lock();
        CipherInputStream cipherInputStream = null;
        try {
            try {
                Cipher cipherInitMode = initMode(2);
                CipherInputStream cipherInputStream2 = new CipherInputStream(inputStream, cipherInitMode);
                try {
                    if (this.isZeroPadding && (blockSize = cipherInitMode.getBlockSize()) > 0) {
                        copyForZeroPadding(cipherInputStream2, outputStream, blockSize);
                        this.lock.unlock();
                        IoUtil.close((Closeable) cipherInputStream2);
                        if (z2) {
                            IoUtil.close((Closeable) inputStream);
                            return;
                        }
                        return;
                    }
                    IoUtil.copy(cipherInputStream2, outputStream);
                    this.lock.unlock();
                    IoUtil.close((Closeable) cipherInputStream2);
                    if (z2) {
                        IoUtil.close((Closeable) inputStream);
                    }
                } catch (IORuntimeException e2) {
                } catch (IOException e3) {
                    e = e3;
                    throw new IORuntimeException(e);
                } catch (Exception e4) {
                    e = e4;
                    throw new CryptoException(e);
                } catch (Throwable th) {
                    th = th;
                    cipherInputStream = cipherInputStream2;
                    this.lock.unlock();
                    IoUtil.close((Closeable) cipherInputStream);
                    if (z2) {
                        IoUtil.close((Closeable) inputStream);
                    }
                    throw th;
                }
            } catch (IORuntimeException e5) {
                throw e5;
            } catch (IOException e6) {
                e = e6;
            } catch (Exception e7) {
                e = e7;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }
}
