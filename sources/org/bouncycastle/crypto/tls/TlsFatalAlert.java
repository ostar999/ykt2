package org.bouncycastle.crypto.tls;

import java.io.IOException;

/* loaded from: classes9.dex */
public class TlsFatalAlert extends IOException {
    private static final long serialVersionUID = 3584313123679111168L;
    private short alertDescription;

    public TlsFatalAlert(short s2) {
        this.alertDescription = s2;
    }

    public short getAlertDescription() {
        return this.alertDescription;
    }
}
