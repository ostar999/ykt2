package org.bouncycastle.bcpg;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;

/* loaded from: classes9.dex */
public class PublicKeyEncSessionPacket extends ContainedPacket implements PublicKeyAlgorithmTags {
    private int algorithm;
    private BigInteger[] data;
    private long keyID;
    private int version;

    public PublicKeyEncSessionPacket(long j2, int i2, BigInteger[] bigIntegerArr) {
        this.version = 3;
        this.keyID = j2;
        this.algorithm = i2;
        this.data = bigIntegerArr;
    }

    public PublicKeyEncSessionPacket(BCPGInputStream bCPGInputStream) throws IOException {
        this.version = bCPGInputStream.read();
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
        int i2 = bCPGInputStream.read();
        this.algorithm = i2;
        if (i2 == 1 || i2 == 2) {
            this.data = new BigInteger[]{new MPInteger(bCPGInputStream).getValue()};
            return;
        }
        if (i2 != 16 && i2 != 20) {
            throw new IOException("unknown PGP public key algorithm encountered");
        }
        BigInteger[] bigIntegerArr = new BigInteger[2];
        this.data = bigIntegerArr;
        bigIntegerArr[0] = new MPInteger(bCPGInputStream).getValue();
        this.data[1] = new MPInteger(bCPGInputStream).getValue();
    }

    @Override // org.bouncycastle.bcpg.ContainedPacket
    public void encode(BCPGOutputStream bCPGOutputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BCPGOutputStream bCPGOutputStream2 = new BCPGOutputStream(byteArrayOutputStream);
        bCPGOutputStream2.write(this.version);
        bCPGOutputStream2.write((byte) (this.keyID >> 56));
        bCPGOutputStream2.write((byte) (this.keyID >> 48));
        bCPGOutputStream2.write((byte) (this.keyID >> 40));
        bCPGOutputStream2.write((byte) (this.keyID >> 32));
        bCPGOutputStream2.write((byte) (this.keyID >> 24));
        bCPGOutputStream2.write((byte) (this.keyID >> 16));
        bCPGOutputStream2.write((byte) (this.keyID >> 8));
        bCPGOutputStream2.write((byte) this.keyID);
        bCPGOutputStream2.write(this.algorithm);
        int i2 = 0;
        while (true) {
            BigInteger[] bigIntegerArr = this.data;
            if (i2 == bigIntegerArr.length) {
                bCPGOutputStream.writePacket(1, byteArrayOutputStream.toByteArray(), true);
                return;
            } else {
                bCPGOutputStream2.writeObject(new MPInteger(bigIntegerArr[i2]));
                i2++;
            }
        }
    }

    public int getAlgorithm() {
        return this.algorithm;
    }

    public BigInteger[] getEncSessionKey() {
        return this.data;
    }

    public long getKeyID() {
        return this.keyID;
    }

    public int getVersion() {
        return this.version;
    }
}
