package com.easefun.polyv.mediasdk.player.misc;

import java.io.IOException;

/* loaded from: classes3.dex */
public interface IAndroidIO {
    int close() throws IOException;

    int open(String str) throws IOException;

    int read(byte[] bArr, int i2) throws IOException;

    long seek(long j2, int i2) throws IOException;
}
