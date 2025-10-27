package org.bouncycastle.crypto.tls;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.bouncycastle.asn1.x509.X509CertificateStructure;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.encodings.PKCS1Encoding;
import org.bouncycastle.crypto.engines.RSABlindedEngine;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.crypto.util.PublicKeyFactory;

/* loaded from: classes9.dex */
class TlsRSAKeyExchange implements TlsKeyExchange {
    protected TlsClientContext context;
    protected byte[] premasterSecret;
    protected AsymmetricKeyParameter serverPublicKey = null;
    protected RSAKeyParameters rsaServerPublicKey = null;

    public TlsRSAKeyExchange(TlsClientContext tlsClientContext) {
        this.context = tlsClientContext;
    }

    @Override // org.bouncycastle.crypto.tls.TlsKeyExchange
    public void generateClientKeyExchange(OutputStream outputStream) throws IOException {
        this.premasterSecret = new byte[48];
        this.context.getSecureRandom().nextBytes(this.premasterSecret);
        TlsUtils.writeVersion(this.premasterSecret, 0);
        PKCS1Encoding pKCS1Encoding = new PKCS1Encoding(new RSABlindedEngine());
        pKCS1Encoding.init(true, new ParametersWithRandom(this.rsaServerPublicKey, this.context.getSecureRandom()));
        try {
            byte[] bArr = this.premasterSecret;
            byte[] bArrProcessBlock = pKCS1Encoding.processBlock(bArr, 0, bArr.length);
            TlsUtils.writeUint24(bArrProcessBlock.length + 2, outputStream);
            TlsUtils.writeOpaque16(bArrProcessBlock, outputStream);
        } catch (InvalidCipherTextException unused) {
            throw new TlsFatalAlert((short) 80);
        }
    }

    @Override // org.bouncycastle.crypto.tls.TlsKeyExchange
    public byte[] generatePremasterSecret() throws IOException {
        byte[] bArr = this.premasterSecret;
        this.premasterSecret = null;
        return bArr;
    }

    @Override // org.bouncycastle.crypto.tls.TlsKeyExchange
    public void processClientCredentials(TlsCredentials tlsCredentials) throws IOException {
        if (!(tlsCredentials instanceof TlsSignerCredentials)) {
            throw new TlsFatalAlert((short) 80);
        }
    }

    @Override // org.bouncycastle.crypto.tls.TlsKeyExchange
    public void processServerCertificate(Certificate certificate) throws IOException {
        X509CertificateStructure x509CertificateStructure = certificate.certs[0];
        try {
            AsymmetricKeyParameter asymmetricKeyParameterCreateKey = PublicKeyFactory.createKey(x509CertificateStructure.getSubjectPublicKeyInfo());
            this.serverPublicKey = asymmetricKeyParameterCreateKey;
            if (asymmetricKeyParameterCreateKey.isPrivate()) {
                throw new TlsFatalAlert((short) 80);
            }
            this.rsaServerPublicKey = validateRSAPublicKey((RSAKeyParameters) this.serverPublicKey);
            TlsUtils.validateKeyUsage(x509CertificateStructure, 32);
        } catch (RuntimeException unused) {
            throw new TlsFatalAlert((short) 43);
        }
    }

    @Override // org.bouncycastle.crypto.tls.TlsKeyExchange
    public void processServerKeyExchange(InputStream inputStream) throws IOException {
        throw new TlsFatalAlert((short) 10);
    }

    @Override // org.bouncycastle.crypto.tls.TlsKeyExchange
    public void skipClientCredentials() throws IOException {
    }

    @Override // org.bouncycastle.crypto.tls.TlsKeyExchange
    public void skipServerCertificate() throws IOException {
        throw new TlsFatalAlert((short) 10);
    }

    @Override // org.bouncycastle.crypto.tls.TlsKeyExchange
    public void skipServerKeyExchange() throws IOException {
    }

    @Override // org.bouncycastle.crypto.tls.TlsKeyExchange
    public void validateCertificateRequest(CertificateRequest certificateRequest) throws IOException {
        for (short s2 : certificateRequest.getCertificateTypes()) {
            if (s2 != 1 && s2 != 2 && s2 != 64) {
                throw new TlsFatalAlert((short) 47);
            }
        }
    }

    public RSAKeyParameters validateRSAPublicKey(RSAKeyParameters rSAKeyParameters) throws IOException {
        if (rSAKeyParameters.getExponent().isProbablePrime(2)) {
            return rSAKeyParameters;
        }
        throw new TlsFatalAlert((short) 47);
    }
}
