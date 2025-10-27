package org.bouncycastle.crypto.tls;

import java.io.IOException;
import java.io.InputStream;
import org.bouncycastle.crypto.Signer;
import org.bouncycastle.crypto.io.SignerInputStream;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;

/* loaded from: classes9.dex */
class TlsECDHEKeyExchange extends TlsECDHKeyExchange {
    public TlsECDHEKeyExchange(TlsClientContext tlsClientContext, int i2) {
        super(tlsClientContext, i2);
    }

    public Signer initSigner(TlsSigner tlsSigner, SecurityParameters securityParameters) {
        Signer signerCreateVerifyer = tlsSigner.createVerifyer(this.serverPublicKey);
        byte[] bArr = securityParameters.clientRandom;
        signerCreateVerifyer.update(bArr, 0, bArr.length);
        byte[] bArr2 = securityParameters.serverRandom;
        signerCreateVerifyer.update(bArr2, 0, bArr2.length);
        return signerCreateVerifyer;
    }

    @Override // org.bouncycastle.crypto.tls.TlsECDHKeyExchange, org.bouncycastle.crypto.tls.TlsKeyExchange
    public void processClientCredentials(TlsCredentials tlsCredentials) throws IOException {
        if (!(tlsCredentials instanceof TlsSignerCredentials)) {
            throw new TlsFatalAlert((short) 80);
        }
    }

    @Override // org.bouncycastle.crypto.tls.TlsECDHKeyExchange, org.bouncycastle.crypto.tls.TlsKeyExchange
    public void processServerKeyExchange(InputStream inputStream) throws IOException {
        Signer signerInitSigner = initSigner(this.tlsSigner, this.context.getSecurityParameters());
        SignerInputStream signerInputStream = new SignerInputStream(inputStream, signerInitSigner);
        if (TlsUtils.readUint8(signerInputStream) != 3) {
            throw new TlsFatalAlert((short) 40);
        }
        ECDomainParameters eCParameters = NamedCurve.getECParameters(TlsUtils.readUint16(signerInputStream));
        byte[] opaque8 = TlsUtils.readOpaque8(signerInputStream);
        if (!signerInitSigner.verifySignature(TlsUtils.readOpaque16(inputStream))) {
            throw new TlsFatalAlert((short) 42);
        }
        this.ecAgreeServerPublicKey = validateECPublicKey(new ECPublicKeyParameters(eCParameters.getCurve().decodePoint(opaque8), eCParameters));
    }

    @Override // org.bouncycastle.crypto.tls.TlsECDHKeyExchange, org.bouncycastle.crypto.tls.TlsKeyExchange
    public void skipServerKeyExchange() throws IOException {
        throw new TlsFatalAlert((short) 10);
    }

    @Override // org.bouncycastle.crypto.tls.TlsECDHKeyExchange, org.bouncycastle.crypto.tls.TlsKeyExchange
    public void validateCertificateRequest(CertificateRequest certificateRequest) throws IOException {
        for (short s2 : certificateRequest.getCertificateTypes()) {
            if (s2 != 1 && s2 != 2 && s2 != 64) {
                throw new TlsFatalAlert((short) 47);
            }
        }
    }
}
