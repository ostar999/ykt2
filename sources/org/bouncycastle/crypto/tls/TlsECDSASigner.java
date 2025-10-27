package org.bouncycastle.crypto.tls;

import org.bouncycastle.crypto.DSA;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.signers.ECDSASigner;

/* loaded from: classes9.dex */
class TlsECDSASigner extends TlsDSASigner {
    @Override // org.bouncycastle.crypto.tls.TlsDSASigner
    public DSA createDSAImpl() {
        return new ECDSASigner();
    }

    @Override // org.bouncycastle.crypto.tls.TlsSigner
    public boolean isValidPublicKey(AsymmetricKeyParameter asymmetricKeyParameter) {
        return asymmetricKeyParameter instanceof ECPublicKeyParameters;
    }
}
