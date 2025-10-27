package org.bouncycastle.crypto.tls;

import java.io.IOException;

/* loaded from: classes9.dex */
public interface TlsCipher {
    byte[] decodeCiphertext(short s2, byte[] bArr, int i2, int i3) throws IOException;

    byte[] encodePlaintext(short s2, byte[] bArr, int i2, int i3) throws IOException;
}
