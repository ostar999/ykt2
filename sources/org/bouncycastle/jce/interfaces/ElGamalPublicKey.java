package org.bouncycastle.jce.interfaces;

import java.math.BigInteger;
import java.security.PublicKey;

/* loaded from: classes9.dex */
public interface ElGamalPublicKey extends ElGamalKey, PublicKey {
    BigInteger getY();
}
