package org.bouncycastle.crypto.tls;

import java.security.SecureRandom;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.Signer;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;

/* loaded from: classes9.dex */
interface TlsSigner {
    byte[] calculateRawSignature(SecureRandom secureRandom, AsymmetricKeyParameter asymmetricKeyParameter, byte[] bArr) throws CryptoException;

    Signer createVerifyer(AsymmetricKeyParameter asymmetricKeyParameter);

    boolean isValidPublicKey(AsymmetricKeyParameter asymmetricKeyParameter);
}
