package org.bouncycastle.crypto;

import java.math.BigInteger;

/* loaded from: classes9.dex */
public interface BasicAgreement {
    BigInteger calculateAgreement(CipherParameters cipherParameters);

    void init(CipherParameters cipherParameters);
}
