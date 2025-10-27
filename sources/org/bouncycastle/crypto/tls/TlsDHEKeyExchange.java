package org.bouncycastle.crypto.tls;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import org.bouncycastle.crypto.Signer;
import org.bouncycastle.crypto.io.SignerInputStream;
import org.bouncycastle.crypto.params.DHParameters;
import org.bouncycastle.crypto.params.DHPublicKeyParameters;

/* loaded from: classes9.dex */
class TlsDHEKeyExchange extends TlsDHKeyExchange {
    public TlsDHEKeyExchange(TlsClientContext tlsClientContext, int i2) {
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

    @Override // org.bouncycastle.crypto.tls.TlsDHKeyExchange, org.bouncycastle.crypto.tls.TlsKeyExchange
    public void processServerKeyExchange(InputStream inputStream) throws IOException {
        Signer signerInitSigner = initSigner(this.tlsSigner, this.context.getSecurityParameters());
        SignerInputStream signerInputStream = new SignerInputStream(inputStream, signerInitSigner);
        byte[] opaque16 = TlsUtils.readOpaque16(signerInputStream);
        byte[] opaque162 = TlsUtils.readOpaque16(signerInputStream);
        byte[] opaque163 = TlsUtils.readOpaque16(signerInputStream);
        if (!signerInitSigner.verifySignature(TlsUtils.readOpaque16(inputStream))) {
            throw new TlsFatalAlert((short) 42);
        }
        this.dhAgreeServerPublicKey = validateDHPublicKey(new DHPublicKeyParameters(new BigInteger(1, opaque163), new DHParameters(new BigInteger(1, opaque16), new BigInteger(1, opaque162))));
    }

    @Override // org.bouncycastle.crypto.tls.TlsDHKeyExchange, org.bouncycastle.crypto.tls.TlsKeyExchange
    public void skipServerKeyExchange() throws IOException {
        throw new TlsFatalAlert((short) 10);
    }
}
