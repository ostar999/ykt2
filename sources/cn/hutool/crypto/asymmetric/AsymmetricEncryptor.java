package cn.hutool.crypto.asymmetric;

import cn.hutool.core.io.IORuntimeException;
import java.io.InputStream;
import java.nio.charset.Charset;

/* loaded from: classes.dex */
public interface AsymmetricEncryptor {
    byte[] encrypt(InputStream inputStream, KeyType keyType) throws IORuntimeException;

    byte[] encrypt(String str, KeyType keyType);

    byte[] encrypt(String str, String str2, KeyType keyType);

    byte[] encrypt(String str, Charset charset, KeyType keyType);

    byte[] encrypt(byte[] bArr, KeyType keyType);

    String encryptBase64(InputStream inputStream, KeyType keyType);

    String encryptBase64(String str, KeyType keyType);

    String encryptBase64(String str, Charset charset, KeyType keyType);

    String encryptBase64(byte[] bArr, KeyType keyType);

    @Deprecated
    String encryptBcd(String str, KeyType keyType);

    @Deprecated
    String encryptBcd(String str, KeyType keyType, Charset charset);

    String encryptHex(InputStream inputStream, KeyType keyType);

    String encryptHex(String str, KeyType keyType);

    String encryptHex(String str, Charset charset, KeyType keyType);

    String encryptHex(byte[] bArr, KeyType keyType);
}
