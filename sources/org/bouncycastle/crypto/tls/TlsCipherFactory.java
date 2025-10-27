package org.bouncycastle.crypto.tls;

import java.io.IOException;

/* loaded from: classes9.dex */
public interface TlsCipherFactory {
    TlsCipher createCipher(TlsClientContext tlsClientContext, int i2, int i3) throws IOException;
}
