package org.bouncycastle.bcpg;

import java.io.IOException;
import org.bouncycastle.util.Strings;

/* loaded from: classes9.dex */
public class LiteralDataPacket extends InputStreamPacket {
    byte[] fileName;
    int format;
    long modDate;

    public LiteralDataPacket(BCPGInputStream bCPGInputStream) throws IOException {
        super(bCPGInputStream);
        this.format = bCPGInputStream.read();
        this.fileName = new byte[bCPGInputStream.read()];
        int i2 = 0;
        while (true) {
            byte[] bArr = this.fileName;
            if (i2 == bArr.length) {
                this.modDate = (bCPGInputStream.read() << 24) | (bCPGInputStream.read() << 16) | (bCPGInputStream.read() << 8) | bCPGInputStream.read();
                return;
            } else {
                bArr[i2] = (byte) bCPGInputStream.read();
                i2++;
            }
        }
    }

    public String getFileName() {
        return Strings.fromUTF8ByteArray(this.fileName);
    }

    public int getFormat() {
        return this.format;
    }

    public long getModificationTime() {
        return this.modDate * 1000;
    }

    public byte[] getRawFileName() {
        int length = this.fileName.length;
        byte[] bArr = new byte[length];
        for (int i2 = 0; i2 != length; i2++) {
            bArr[i2] = this.fileName[i2];
        }
        return bArr;
    }
}
