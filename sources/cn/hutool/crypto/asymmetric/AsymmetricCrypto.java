package cn.hutool.crypto.asymmetric;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FastByteArrayOutputStream;
import cn.hutool.crypto.CipherWrapper;
import cn.hutool.crypto.CryptoException;
import cn.hutool.crypto.KeyUtil;
import cn.hutool.crypto.SecureUtil;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

/* loaded from: classes.dex */
public class AsymmetricCrypto extends AbstractAsymmetricCrypto<AsymmetricCrypto> {
    private static final long serialVersionUID = 1;
    protected CipherWrapper cipherWrapper;
    protected int decryptBlockSize;
    protected int encryptBlockSize;

    public AsymmetricCrypto(AsymmetricAlgorithm asymmetricAlgorithm) {
        this(asymmetricAlgorithm, (byte[]) null, (byte[]) null);
    }

    private byte[] doFinal(byte[] bArr, int i2) throws BadPaddingException, IllegalBlockSizeException, IOException {
        int length = bArr.length;
        return length <= i2 ? getCipher().doFinal(bArr, 0, length) : doFinalWithBlock(bArr, i2);
    }

    private byte[] doFinalWithBlock(byte[] bArr, int i2) throws BadPaddingException, IllegalBlockSizeException, IOException {
        int length = bArr.length;
        FastByteArrayOutputStream fastByteArrayOutputStream = new FastByteArrayOutputStream();
        int i3 = 0;
        int i4 = length;
        while (i4 > 0) {
            int iMin = Math.min(i4, i2);
            fastByteArrayOutputStream.write(getCipher().doFinal(bArr, i3, iMin));
            i3 += iMin;
            i4 = length - i3;
        }
        return fastByteArrayOutputStream.toByteArray();
    }

    private Cipher initMode(int i2, Key key) throws InvalidKeyException, InvalidAlgorithmParameterException {
        return this.cipherWrapper.initMode(i2, key).getCipher();
    }

    @Override // cn.hutool.crypto.asymmetric.AsymmetricDecryptor
    public byte[] decrypt(byte[] bArr, KeyType keyType) {
        int blockSize;
        Key keyByType = getKeyByType(keyType);
        this.lock.lock();
        try {
            try {
                Cipher cipherInitMode = initMode(2, keyByType);
                if (this.decryptBlockSize < 0 && (blockSize = cipherInitMode.getBlockSize()) > 0) {
                    this.decryptBlockSize = blockSize;
                }
                int length = this.decryptBlockSize;
                if (length < 0) {
                    length = bArr.length;
                }
                return doFinal(bArr, length);
            } catch (Exception e2) {
                throw new CryptoException(e2);
            }
        } finally {
            this.lock.unlock();
        }
    }

    @Override // cn.hutool.crypto.asymmetric.AsymmetricEncryptor
    public byte[] encrypt(byte[] bArr, KeyType keyType) {
        int blockSize;
        Key keyByType = getKeyByType(keyType);
        this.lock.lock();
        try {
            try {
                Cipher cipherInitMode = initMode(1, keyByType);
                if (this.encryptBlockSize < 0 && (blockSize = cipherInitMode.getBlockSize()) > 0) {
                    this.encryptBlockSize = blockSize;
                }
                int length = this.encryptBlockSize;
                if (length < 0) {
                    length = bArr.length;
                }
                return doFinal(bArr, length);
            } catch (Exception e2) {
                throw new CryptoException(e2);
            }
        } finally {
            this.lock.unlock();
        }
    }

    public AlgorithmParameterSpec getAlgorithmParameterSpec() {
        return this.cipherWrapper.getParams();
    }

    public Cipher getCipher() {
        return this.cipherWrapper.getCipher();
    }

    public int getDecryptBlockSize() {
        return this.decryptBlockSize;
    }

    public int getEncryptBlockSize() {
        return this.encryptBlockSize;
    }

    public void initCipher() {
        this.cipherWrapper = new CipherWrapper(this.algorithm);
    }

    public void setAlgorithmParameterSpec(AlgorithmParameterSpec algorithmParameterSpec) {
        this.cipherWrapper.setParams(algorithmParameterSpec);
    }

    public void setDecryptBlockSize(int i2) {
        this.decryptBlockSize = i2;
    }

    public void setEncryptBlockSize(int i2) {
        this.encryptBlockSize = i2;
    }

    public AsymmetricCrypto setRandom(SecureRandom secureRandom) {
        this.cipherWrapper.setRandom(secureRandom);
        return this;
    }

    public AsymmetricCrypto(String str) {
        this(str, (byte[]) null, (byte[]) null);
    }

    @Override // cn.hutool.crypto.asymmetric.BaseAsymmetric
    public AsymmetricCrypto init(String str, PrivateKey privateKey, PublicKey publicKey) {
        super.init(str, privateKey, publicKey);
        initCipher();
        return this;
    }

    public AsymmetricCrypto(AsymmetricAlgorithm asymmetricAlgorithm, String str, String str2) {
        this(asymmetricAlgorithm.getValue(), SecureUtil.decode(str), SecureUtil.decode(str2));
    }

    public AsymmetricCrypto(AsymmetricAlgorithm asymmetricAlgorithm, byte[] bArr, byte[] bArr2) {
        this(asymmetricAlgorithm.getValue(), bArr, bArr2);
    }

    public AsymmetricCrypto(AsymmetricAlgorithm asymmetricAlgorithm, PrivateKey privateKey, PublicKey publicKey) {
        this(asymmetricAlgorithm.getValue(), privateKey, publicKey);
    }

    public AsymmetricCrypto(String str, String str2, String str3) {
        this(str, Base64.decode(str2), Base64.decode(str3));
    }

    public AsymmetricCrypto(String str, byte[] bArr, byte[] bArr2) {
        this(str, KeyUtil.generatePrivateKey(str, bArr), KeyUtil.generatePublicKey(str, bArr2));
    }

    public AsymmetricCrypto(String str, PrivateKey privateKey, PublicKey publicKey) {
        super(str, privateKey, publicKey);
        this.encryptBlockSize = -1;
        this.decryptBlockSize = -1;
    }
}
