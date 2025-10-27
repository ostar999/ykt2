package cn.hutool.crypto.symmetric;

import cn.hutool.core.io.IORuntimeException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

/* loaded from: classes.dex */
public interface SymmetricEncryptor {
    void encrypt(InputStream inputStream, OutputStream outputStream, boolean z2);

    byte[] encrypt(InputStream inputStream) throws IORuntimeException;

    byte[] encrypt(String str);

    byte[] encrypt(String str, String str2);

    byte[] encrypt(String str, Charset charset);

    byte[] encrypt(byte[] bArr);

    String encryptBase64(InputStream inputStream);

    String encryptBase64(String str);

    String encryptBase64(String str, String str2);

    String encryptBase64(String str, Charset charset);

    String encryptBase64(byte[] bArr);

    String encryptHex(InputStream inputStream);

    String encryptHex(String str);

    String encryptHex(String str, String str2);

    String encryptHex(String str, Charset charset);

    String encryptHex(byte[] bArr);
}
