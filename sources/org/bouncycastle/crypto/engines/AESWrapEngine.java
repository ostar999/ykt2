package org.bouncycastle.crypto.engines;

/* loaded from: classes9.dex */
public class AESWrapEngine extends RFC3394WrapEngine {
    public AESWrapEngine() {
        super(new AESEngine());
    }
}
