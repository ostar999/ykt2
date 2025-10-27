package org.bouncycastle.util.encoders;

import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes9.dex */
public interface Encoder {
    int decode(String str, OutputStream outputStream) throws IOException;

    int decode(byte[] bArr, int i2, int i3, OutputStream outputStream) throws IOException;

    int encode(byte[] bArr, int i2, int i3, OutputStream outputStream) throws IOException;
}
