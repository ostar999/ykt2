package org.bouncycastle.bcpg;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/* loaded from: classes9.dex */
public class ExperimentalPacket extends ContainedPacket implements PublicKeyAlgorithmTags {
    private byte[] contents;
    private int tag;

    public ExperimentalPacket(int i2, BCPGInputStream bCPGInputStream) throws IOException {
        this.tag = i2;
        if (bCPGInputStream.available() == 0) {
            this.contents = new byte[0];
            return;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(bCPGInputStream.available());
        while (true) {
            int i3 = bCPGInputStream.read();
            if (i3 < 0) {
                this.contents = byteArrayOutputStream.toByteArray();
                return;
            }
            byteArrayOutputStream.write(i3);
        }
    }

    @Override // org.bouncycastle.bcpg.ContainedPacket
    public void encode(BCPGOutputStream bCPGOutputStream) throws IOException {
        bCPGOutputStream.writePacket(this.tag, this.contents, true);
    }

    public byte[] getContents() {
        byte[] bArr = this.contents;
        int length = bArr.length;
        byte[] bArr2 = new byte[length];
        System.arraycopy(bArr, 0, bArr2, 0, length);
        return bArr2;
    }

    public int getTag() {
        return this.tag;
    }
}
