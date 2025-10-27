package cn.hutool.crypto.asymmetric;

import cn.hutool.core.io.IORuntimeException;
import java.io.InputStream;
import java.nio.charset.Charset;

/* loaded from: classes.dex */
public interface AsymmetricDecryptor {
    byte[] decrypt(InputStream inputStream, KeyType keyType) throws IORuntimeException;

    byte[] decrypt(String str, KeyType keyType);

    byte[] decrypt(byte[] bArr, KeyType keyType);

    byte[] decryptFromBcd(String str, KeyType keyType);

    @Deprecated
    byte[] decryptFromBcd(String str, KeyType keyType, Charset charset);

    String decryptStr(String str, KeyType keyType);

    String decryptStr(String str, KeyType keyType, Charset charset);

    @Deprecated
    String decryptStrFromBcd(String str, KeyType keyType);

    @Deprecated
    String decryptStrFromBcd(String str, KeyType keyType, Charset charset);
}
