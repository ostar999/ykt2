package org.bouncycastle.crypto.tls;

import java.io.IOException;
import java.util.Hashtable;

/* loaded from: classes9.dex */
public interface TlsClient {
    TlsAuthentication getAuthentication() throws IOException;

    TlsCipher getCipher() throws IOException;

    int[] getCipherSuites();

    Hashtable getClientExtensions() throws IOException;

    TlsCompression getCompression() throws IOException;

    short[] getCompressionMethods();

    TlsKeyExchange getKeyExchange() throws IOException;

    void init(TlsClientContext tlsClientContext);

    void notifySecureRenegotiation(boolean z2) throws IOException;

    void notifySelectedCipherSuite(int i2);

    void notifySelectedCompressionMethod(short s2);

    void notifySessionID(byte[] bArr);

    void processServerExtensions(Hashtable hashtable);
}
