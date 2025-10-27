package org.bouncycastle.bcpg;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/* loaded from: classes9.dex */
public class OnePassSignaturePacket extends ContainedPacket {
    private int hashAlgorithm;
    private int keyAlgorithm;
    private long keyID;
    private int nested;
    private int sigType;
    private int version;

    public OnePassSignaturePacket(int i2, int i3, int i4, long j2, boolean z2) {
        this.version = 3;
        this.sigType = i2;
        this.hashAlgorithm = i3;
        this.keyAlgorithm = i4;
        this.keyID = j2;
        this.nested = !z2 ? 1 : 0;
    }

    public OnePassSignaturePacket(BCPGInputStream bCPGInputStream) throws IOException {
        this.version = bCPGInputStream.read();
        this.sigType = bCPGInputStream.read();
        this.hashAlgorithm = bCPGInputStream.read();
        this.keyAlgorithm = bCPGInputStream.read();
        long j2 = this.keyID | (bCPGInputStream.read() << 56);
        this.keyID = j2;
        long j3 = j2 | (bCPGInputStream.read() << 48);
        this.keyID = j3;
        long j4 = j3 | (bCPGInputStream.read() << 40);
        this.keyID = j4;
        long j5 = j4 | (bCPGInputStream.read() << 32);
        this.keyID = j5;
        long j6 = j5 | (bCPGInputStream.read() << 24);
        this.keyID = j6;
        long j7 = j6 | (bCPGInputStream.read() << 16);
        this.keyID = j7;
        long j8 = j7 | (bCPGInputStream.read() << 8);
        this.keyID = j8;
        this.keyID = j8 | bCPGInputStream.read();
        this.nested = bCPGInputStream.read();
    }

    @Override // org.bouncycastle.bcpg.ContainedPacket
    public void encode(BCPGOutputStream bCPGOutputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BCPGOutputStream bCPGOutputStream2 = new BCPGOutputStream(byteArrayOutputStream);
        bCPGOutputStream2.write(this.version);
        bCPGOutputStream2.write(this.sigType);
        bCPGOutputStream2.write(this.hashAlgorithm);
        bCPGOutputStream2.write(this.keyAlgorithm);
        bCPGOutputStream2.write((byte) (this.keyID >> 56));
        bCPGOutputStream2.write((byte) (this.keyID >> 48));
        bCPGOutputStream2.write((byte) (this.keyID >> 40));
        bCPGOutputStream2.write((byte) (this.keyID >> 32));
        bCPGOutputStream2.write((byte) (this.keyID >> 24));
        bCPGOutputStream2.write((byte) (this.keyID >> 16));
        bCPGOutputStream2.write((byte) (this.keyID >> 8));
        bCPGOutputStream2.write((byte) this.keyID);
        bCPGOutputStream2.write(this.nested);
        bCPGOutputStream.writePacket(4, byteArrayOutputStream.toByteArray(), true);
    }

    public int getHashAlgorithm() {
        return this.hashAlgorithm;
    }

    public int getKeyAlgorithm() {
        return this.keyAlgorithm;
    }

    public long getKeyID() {
        return this.keyID;
    }

    public int getSignatureType() {
        return this.sigType;
    }
}
