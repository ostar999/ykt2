package org.bouncycastle.bcpg;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/* loaded from: classes9.dex */
public class TrustPacket extends ContainedPacket {
    byte[] levelAndTrustAmount;

    public TrustPacket(int i2) {
        this.levelAndTrustAmount = new byte[]{(byte) i2};
    }

    public TrustPacket(BCPGInputStream bCPGInputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while (true) {
            int i2 = bCPGInputStream.read();
            if (i2 < 0) {
                this.levelAndTrustAmount = byteArrayOutputStream.toByteArray();
                return;
            }
            byteArrayOutputStream.write(i2);
        }
    }

    @Override // org.bouncycastle.bcpg.ContainedPacket
    public void encode(BCPGOutputStream bCPGOutputStream) throws IOException {
        bCPGOutputStream.writePacket(12, this.levelAndTrustAmount, true);
    }

    public byte[] getLevelAndTrustAmount() {
        return this.levelAndTrustAmount;
    }
}
