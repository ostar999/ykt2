package cn.hutool.crypto.asymmetric;

import cn.hutool.crypto.asymmetric.AbstractAsymmetricCrypto;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.PrivateKey;
import java.security.PublicKey;

/* loaded from: classes.dex */
public abstract class AbstractAsymmetricCrypto<T extends AbstractAsymmetricCrypto<T>> extends BaseAsymmetric<T> implements AsymmetricEncryptor, AsymmetricDecryptor {
    private static final long serialVersionUID = 1;

    public AbstractAsymmetricCrypto(String str, PrivateKey privateKey, PublicKey publicKey) {
        super(str, privateKey, publicKey);
    }

    @Override // cn.hutool.crypto.asymmetric.AsymmetricDecryptor
    public /* synthetic */ byte[] decrypt(InputStream inputStream, KeyType keyType) {
        return a.a(this, inputStream, keyType);
    }

    @Override // cn.hutool.crypto.asymmetric.AsymmetricDecryptor
    public /* synthetic */ byte[] decrypt(String str, KeyType keyType) {
        return a.b(this, str, keyType);
    }

    @Override // cn.hutool.crypto.asymmetric.AsymmetricDecryptor
    public /* synthetic */ byte[] decryptFromBcd(String str, KeyType keyType) {
        return a.c(this, str, keyType);
    }

    @Override // cn.hutool.crypto.asymmetric.AsymmetricDecryptor
    public /* synthetic */ byte[] decryptFromBcd(String str, KeyType keyType, Charset charset) {
        return a.d(this, str, keyType, charset);
    }

    @Override // cn.hutool.crypto.asymmetric.AsymmetricDecryptor
    public /* synthetic */ String decryptStr(String str, KeyType keyType) {
        return a.e(this, str, keyType);
    }

    @Override // cn.hutool.crypto.asymmetric.AsymmetricDecryptor
    public /* synthetic */ String decryptStr(String str, KeyType keyType, Charset charset) {
        return a.f(this, str, keyType, charset);
    }

    @Override // cn.hutool.crypto.asymmetric.AsymmetricDecryptor
    public /* synthetic */ String decryptStrFromBcd(String str, KeyType keyType) {
        return a.g(this, str, keyType);
    }

    @Override // cn.hutool.crypto.asymmetric.AsymmetricDecryptor
    public /* synthetic */ String decryptStrFromBcd(String str, KeyType keyType, Charset charset) {
        return a.h(this, str, keyType, charset);
    }

    @Override // cn.hutool.crypto.asymmetric.AsymmetricEncryptor
    public /* synthetic */ byte[] encrypt(InputStream inputStream, KeyType keyType) {
        return b.a(this, inputStream, keyType);
    }

    @Override // cn.hutool.crypto.asymmetric.AsymmetricEncryptor
    public /* synthetic */ byte[] encrypt(String str, KeyType keyType) {
        return b.b(this, str, keyType);
    }

    @Override // cn.hutool.crypto.asymmetric.AsymmetricEncryptor
    public /* synthetic */ byte[] encrypt(String str, String str2, KeyType keyType) {
        return b.c(this, str, str2, keyType);
    }

    @Override // cn.hutool.crypto.asymmetric.AsymmetricEncryptor
    public /* synthetic */ byte[] encrypt(String str, Charset charset, KeyType keyType) {
        return b.d(this, str, charset, keyType);
    }

    @Override // cn.hutool.crypto.asymmetric.AsymmetricEncryptor
    public /* synthetic */ String encryptBase64(InputStream inputStream, KeyType keyType) {
        return b.e(this, inputStream, keyType);
    }

    @Override // cn.hutool.crypto.asymmetric.AsymmetricEncryptor
    public /* synthetic */ String encryptBase64(String str, KeyType keyType) {
        return b.f(this, str, keyType);
    }

    @Override // cn.hutool.crypto.asymmetric.AsymmetricEncryptor
    public /* synthetic */ String encryptBase64(String str, Charset charset, KeyType keyType) {
        return b.g(this, str, charset, keyType);
    }

    @Override // cn.hutool.crypto.asymmetric.AsymmetricEncryptor
    public /* synthetic */ String encryptBase64(byte[] bArr, KeyType keyType) {
        return b.h(this, bArr, keyType);
    }

    @Override // cn.hutool.crypto.asymmetric.AsymmetricEncryptor
    public /* synthetic */ String encryptBcd(String str, KeyType keyType) {
        return b.i(this, str, keyType);
    }

    @Override // cn.hutool.crypto.asymmetric.AsymmetricEncryptor
    public /* synthetic */ String encryptBcd(String str, KeyType keyType, Charset charset) {
        return b.j(this, str, keyType, charset);
    }

    @Override // cn.hutool.crypto.asymmetric.AsymmetricEncryptor
    public /* synthetic */ String encryptHex(InputStream inputStream, KeyType keyType) {
        return b.k(this, inputStream, keyType);
    }

    @Override // cn.hutool.crypto.asymmetric.AsymmetricEncryptor
    public /* synthetic */ String encryptHex(String str, KeyType keyType) {
        return b.l(this, str, keyType);
    }

    @Override // cn.hutool.crypto.asymmetric.AsymmetricEncryptor
    public /* synthetic */ String encryptHex(String str, Charset charset, KeyType keyType) {
        return b.m(this, str, charset, keyType);
    }

    @Override // cn.hutool.crypto.asymmetric.AsymmetricEncryptor
    public /* synthetic */ String encryptHex(byte[] bArr, KeyType keyType) {
        return b.n(this, bArr, keyType);
    }
}
