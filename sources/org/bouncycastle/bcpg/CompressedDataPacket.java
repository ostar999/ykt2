package org.bouncycastle.bcpg;

import java.io.IOException;

/* loaded from: classes9.dex */
public class CompressedDataPacket extends InputStreamPacket {
    int algorithm;

    public CompressedDataPacket(BCPGInputStream bCPGInputStream) throws IOException {
        super(bCPGInputStream);
        this.algorithm = bCPGInputStream.read();
    }

    public int getAlgorithm() {
        return this.algorithm;
    }
}
