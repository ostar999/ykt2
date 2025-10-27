package net.polyv.danmaku.danmaku.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes9.dex */
public class IOUtils {
    public static void closeQuietly(InputStream inputStream) throws IOException {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException unused) {
            }
        }
    }

    public static byte[] getBytes(InputStream inputStream) throws IOException {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr = new byte[8192];
            while (true) {
                int i2 = inputStream.read(bArr);
                if (i2 == -1) {
                    inputStream.close();
                    return byteArrayOutputStream.toByteArray();
                }
                byteArrayOutputStream.write(bArr, 0, i2);
            }
        } catch (IOException unused) {
            return null;
        }
    }

    public static String getString(InputStream inputStream) throws IOException {
        byte[] bytes = getBytes(inputStream);
        if (bytes == null) {
            return null;
        }
        return new String(bytes);
    }

    public static void closeQuietly(OutputStream outputStream) throws IOException {
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException unused) {
            }
        }
    }
}
