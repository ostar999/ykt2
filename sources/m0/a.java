package m0;

import cn.hutool.crypto.CryptoException;
import cn.hutool.crypto.digest.mac.MacEngine;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes.dex */
public final /* synthetic */ class a {
    public static byte[] a(MacEngine macEngine, InputStream inputStream, int i2) {
        if (i2 < 1) {
            i2 = 8192;
        }
        byte[] bArr = new byte[i2];
        try {
            try {
                int i3 = inputStream.read(bArr, 0, i2);
                while (i3 > -1) {
                    macEngine.update(bArr, 0, i3);
                    i3 = inputStream.read(bArr, 0, i2);
                }
                return macEngine.doFinal();
            } catch (IOException e2) {
                throw new CryptoException(e2);
            }
        } finally {
            macEngine.reset();
        }
    }

    public static void b(MacEngine macEngine, byte[] bArr) {
        macEngine.update(bArr, 0, bArr.length);
    }
}
