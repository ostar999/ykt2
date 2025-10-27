package org.bouncycastle.bcpg;

import java.io.IOException;
import java.util.Date;

/* loaded from: classes9.dex */
public class PublicSubkeyPacket extends PublicKeyPacket {
    public PublicSubkeyPacket(int i2, Date date, BCPGKey bCPGKey) {
        super(i2, date, bCPGKey);
    }

    public PublicSubkeyPacket(BCPGInputStream bCPGInputStream) throws IOException {
        super(bCPGInputStream);
    }

    @Override // org.bouncycastle.bcpg.PublicKeyPacket, org.bouncycastle.bcpg.ContainedPacket
    public void encode(BCPGOutputStream bCPGOutputStream) throws IOException {
        bCPGOutputStream.writePacket(14, getEncodedContents(), true);
    }
}
