package org.bouncycastle.crypto.tls;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes9.dex */
public interface TlsKeyExchange {
    void generateClientKeyExchange(OutputStream outputStream) throws IOException;

    byte[] generatePremasterSecret() throws IOException;

    void processClientCredentials(TlsCredentials tlsCredentials) throws IOException;

    void processServerCertificate(Certificate certificate) throws IOException;

    void processServerKeyExchange(InputStream inputStream) throws IOException;

    void skipClientCredentials() throws IOException;

    void skipServerCertificate() throws IOException;

    void skipServerKeyExchange() throws IOException;

    void validateCertificateRequest(CertificateRequest certificateRequest) throws IOException;
}
