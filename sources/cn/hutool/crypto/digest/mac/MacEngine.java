package cn.hutool.crypto.digest.mac;

import java.io.InputStream;

/* loaded from: classes.dex */
public interface MacEngine {
    byte[] digest(InputStream inputStream, int i2);

    byte[] doFinal();

    String getAlgorithm();

    int getMacLength();

    void reset();

    void update(byte[] bArr);

    void update(byte[] bArr, int i2, int i3);
}
