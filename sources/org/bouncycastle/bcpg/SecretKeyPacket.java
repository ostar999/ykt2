package org.bouncycastle.bcpg;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/* loaded from: classes9.dex */
public class SecretKeyPacket extends ContainedPacket implements PublicKeyAlgorithmTags {
    public static final int USAGE_CHECKSUM = 255;
    public static final int USAGE_NONE = 0;
    public static final int USAGE_SHA1 = 254;
    private int encAlgorithm;
    private byte[] iv;
    private PublicKeyPacket pubKeyPacket;
    private S2K s2k;
    private int s2kUsage;
    private byte[] secKeyData;

    public SecretKeyPacket(BCPGInputStream bCPGInputStream) throws IOException {
        this.pubKeyPacket = this instanceof SecretSubkeyPacket ? new PublicSubkeyPacket(bCPGInputStream) : new PublicKeyPacket(bCPGInputStream);
        int i2 = bCPGInputStream.read();
        this.s2kUsage = i2;
        if (i2 == 255 || i2 == 254) {
            this.encAlgorithm = bCPGInputStream.read();
            this.s2k = new S2K(bCPGInputStream);
        } else {
            this.encAlgorithm = i2;
        }
        S2K s2k = this.s2k;
        if ((s2k == null || s2k.getType() != 101 || this.s2k.getProtectionMode() != 1) && this.s2kUsage != 0) {
            if (this.encAlgorithm < 7) {
                this.iv = new byte[8];
            } else {
                this.iv = new byte[16];
            }
            byte[] bArr = this.iv;
            bCPGInputStream.readFully(bArr, 0, bArr.length);
        }
        if (bCPGInputStream.available() != 0) {
            byte[] bArr2 = new byte[bCPGInputStream.available()];
            this.secKeyData = bArr2;
            bCPGInputStream.readFully(bArr2);
        }
    }

    public SecretKeyPacket(PublicKeyPacket publicKeyPacket, int i2, int i3, S2K s2k, byte[] bArr, byte[] bArr2) {
        this.pubKeyPacket = publicKeyPacket;
        this.encAlgorithm = i2;
        this.s2kUsage = i3;
        this.s2k = s2k;
        this.iv = bArr;
        this.secKeyData = bArr2;
    }

    public SecretKeyPacket(PublicKeyPacket publicKeyPacket, int i2, S2K s2k, byte[] bArr, byte[] bArr2) {
        this.pubKeyPacket = publicKeyPacket;
        this.encAlgorithm = i2;
        this.s2kUsage = i2 != 0 ? 255 : 0;
        this.s2k = s2k;
        this.iv = bArr;
        this.secKeyData = bArr2;
    }

    @Override // org.bouncycastle.bcpg.ContainedPacket
    public void encode(BCPGOutputStream bCPGOutputStream) throws IOException {
        bCPGOutputStream.writePacket(5, getEncodedContents(), true);
    }

    public int getEncAlgorithm() {
        return this.encAlgorithm;
    }

    public byte[] getEncodedContents() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BCPGOutputStream bCPGOutputStream = new BCPGOutputStream(byteArrayOutputStream);
        bCPGOutputStream.write(this.pubKeyPacket.getEncodedContents());
        bCPGOutputStream.write(this.s2kUsage);
        int i2 = this.s2kUsage;
        if (i2 == 255 || i2 == 254) {
            bCPGOutputStream.write(this.encAlgorithm);
            bCPGOutputStream.writeObject(this.s2k);
        }
        byte[] bArr = this.iv;
        if (bArr != null) {
            bCPGOutputStream.write(bArr);
        }
        byte[] bArr2 = this.secKeyData;
        if (bArr2 != null && bArr2.length > 0) {
            bCPGOutputStream.write(bArr2);
        }
        return byteArrayOutputStream.toByteArray();
    }

    public byte[] getIV() {
        return this.iv;
    }

    public PublicKeyPacket getPublicKeyPacket() {
        return this.pubKeyPacket;
    }

    public S2K getS2K() {
        return this.s2k;
    }

    public int getS2KUsage() {
        return this.s2kUsage;
    }

    public byte[] getSecretKeyData() {
        return this.secKeyData;
    }
}
