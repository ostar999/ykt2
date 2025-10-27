package org.bouncycastle.bcpg;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;

/* loaded from: classes9.dex */
public class PublicKeyPacket extends ContainedPacket implements PublicKeyAlgorithmTags {
    private int algorithm;
    private BCPGKey key;
    private long time;
    private int validDays;
    private int version;

    public PublicKeyPacket(int i2, Date date, BCPGKey bCPGKey) {
        this.version = 4;
        this.time = date.getTime() / 1000;
        this.algorithm = i2;
        this.key = bCPGKey;
    }

    public PublicKeyPacket(BCPGInputStream bCPGInputStream) throws IOException {
        BCPGKey rSAPublicBCPGKey;
        this.version = bCPGInputStream.read();
        this.time = (bCPGInputStream.read() << 24) | (bCPGInputStream.read() << 16) | (bCPGInputStream.read() << 8) | bCPGInputStream.read();
        if (this.version <= 3) {
            this.validDays = (bCPGInputStream.read() << 8) | bCPGInputStream.read();
        }
        byte b3 = (byte) bCPGInputStream.read();
        this.algorithm = b3;
        if (b3 == 1 || b3 == 2 || b3 == 3) {
            rSAPublicBCPGKey = new RSAPublicBCPGKey(bCPGInputStream);
        } else if (b3 == 16) {
            rSAPublicBCPGKey = new ElGamalPublicBCPGKey(bCPGInputStream);
        } else if (b3 != 17) {
            if (b3 != 20) {
                throw new IOException("unknown PGP public key algorithm encountered");
            }
            rSAPublicBCPGKey = new ElGamalPublicBCPGKey(bCPGInputStream);
        } else {
            rSAPublicBCPGKey = new DSAPublicBCPGKey(bCPGInputStream);
        }
        this.key = rSAPublicBCPGKey;
    }

    @Override // org.bouncycastle.bcpg.ContainedPacket
    public void encode(BCPGOutputStream bCPGOutputStream) throws IOException {
        bCPGOutputStream.writePacket(6, getEncodedContents(), true);
    }

    public int getAlgorithm() {
        return this.algorithm;
    }

    public byte[] getEncodedContents() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BCPGOutputStream bCPGOutputStream = new BCPGOutputStream(byteArrayOutputStream);
        bCPGOutputStream.write(this.version);
        bCPGOutputStream.write((byte) (this.time >> 24));
        bCPGOutputStream.write((byte) (this.time >> 16));
        bCPGOutputStream.write((byte) (this.time >> 8));
        bCPGOutputStream.write((byte) this.time);
        if (this.version <= 3) {
            bCPGOutputStream.write((byte) (this.validDays >> 8));
            bCPGOutputStream.write((byte) this.validDays);
        }
        bCPGOutputStream.write(this.algorithm);
        bCPGOutputStream.writeObject((BCPGObject) this.key);
        return byteArrayOutputStream.toByteArray();
    }

    public BCPGKey getKey() {
        return this.key;
    }

    public Date getTime() {
        return new Date(this.time * 1000);
    }

    public int getValidDays() {
        return this.validDays;
    }

    public int getVersion() {
        return this.version;
    }
}
