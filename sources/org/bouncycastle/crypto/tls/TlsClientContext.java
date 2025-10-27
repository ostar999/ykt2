package org.bouncycastle.crypto.tls;

import java.security.SecureRandom;

/* loaded from: classes9.dex */
public interface TlsClientContext {
    SecureRandom getSecureRandom();

    SecurityParameters getSecurityParameters();

    Object getUserObject();

    void setUserObject(Object obj);
}
