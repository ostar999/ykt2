package cn.hutool.crypto.symmetric;

import cn.hutool.core.io.IORuntimeException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

/* loaded from: classes.dex */
public interface SymmetricDecryptor {
    void decrypt(InputStream inputStream, OutputStream outputStream, boolean z2);

    byte[] decrypt(InputStream inputStream) throws IORuntimeException;

    byte[] decrypt(String str);

    byte[] decrypt(byte[] bArr);

    String decryptStr(InputStream inputStream);

    String decryptStr(InputStream inputStream, Charset charset);

    String decryptStr(String str);

    String decryptStr(String str, Charset charset);

    String decryptStr(byte[] bArr);

    String decryptStr(byte[] bArr, Charset charset);
}
