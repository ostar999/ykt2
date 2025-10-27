package org.bouncycastle.bcpg;

import java.io.IOException;

/* loaded from: classes9.dex */
public class SymmetricEncIntegrityPacket extends InputStreamPacket {
    int version;

    public SymmetricEncIntegrityPacket(BCPGInputStream bCPGInputStream) throws IOException {
        super(bCPGInputStream);
        this.version = bCPGInputStream.read();
    }
}
