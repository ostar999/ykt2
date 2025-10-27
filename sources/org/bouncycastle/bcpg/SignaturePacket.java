package org.bouncycastle.bcpg;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Vector;
import org.bouncycastle.bcpg.sig.IssuerKeyID;
import org.bouncycastle.bcpg.sig.SignatureCreationTime;
import org.bouncycastle.util.Arrays;

/* loaded from: classes9.dex */
public class SignaturePacket extends ContainedPacket implements PublicKeyAlgorithmTags {
    private long creationTime;
    private byte[] fingerPrint;
    private int hashAlgorithm;
    private SignatureSubpacket[] hashedData;
    private int keyAlgorithm;
    private long keyID;
    private MPInteger[] signature;
    private byte[] signatureEncoding;
    private int signatureType;
    private SignatureSubpacket[] unhashedData;
    private int version;

    public SignaturePacket(int i2, int i3, long j2, int i4, int i5, long j3, byte[] bArr, MPInteger[] mPIntegerArr) {
        this(i2, i3, j2, i4, i5, null, null, bArr, mPIntegerArr);
        this.creationTime = j3;
    }

    public SignaturePacket(int i2, int i3, long j2, int i4, int i5, SignatureSubpacket[] signatureSubpacketArr, SignatureSubpacket[] signatureSubpacketArr2, byte[] bArr, MPInteger[] mPIntegerArr) {
        this.version = i2;
        this.signatureType = i3;
        this.keyID = j2;
        this.keyAlgorithm = i4;
        this.hashAlgorithm = i5;
        this.hashedData = signatureSubpacketArr;
        this.unhashedData = signatureSubpacketArr2;
        this.fingerPrint = bArr;
        this.signature = mPIntegerArr;
        if (signatureSubpacketArr != null) {
            setCreationTime();
        }
    }

    public SignaturePacket(int i2, long j2, int i3, int i4, SignatureSubpacket[] signatureSubpacketArr, SignatureSubpacket[] signatureSubpacketArr2, byte[] bArr, MPInteger[] mPIntegerArr) {
        this(4, i2, j2, i3, i4, signatureSubpacketArr, signatureSubpacketArr2, bArr, mPIntegerArr);
    }

    public SignaturePacket(BCPGInputStream bCPGInputStream) throws IOException {
        int i2 = bCPGInputStream.read();
        this.version = i2;
        if (i2 == 3 || i2 == 2) {
            bCPGInputStream.read();
            this.signatureType = bCPGInputStream.read();
            this.creationTime = ((bCPGInputStream.read() << 24) | (bCPGInputStream.read() << 16) | (bCPGInputStream.read() << 8) | bCPGInputStream.read()) * 1000;
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
            this.keyAlgorithm = bCPGInputStream.read();
            this.hashAlgorithm = bCPGInputStream.read();
        } else {
            if (i2 != 4) {
                throw new RuntimeException("unsupported version: " + this.version);
            }
            this.signatureType = bCPGInputStream.read();
            this.keyAlgorithm = bCPGInputStream.read();
            this.hashAlgorithm = bCPGInputStream.read();
            byte[] bArr = new byte[(bCPGInputStream.read() << 8) | bCPGInputStream.read()];
            bCPGInputStream.readFully(bArr);
            SignatureSubpacketInputStream signatureSubpacketInputStream = new SignatureSubpacketInputStream(new ByteArrayInputStream(bArr));
            Vector vector = new Vector();
            while (true) {
                SignatureSubpacket packet = signatureSubpacketInputStream.readPacket();
                if (packet == null) {
                    break;
                } else {
                    vector.addElement(packet);
                }
            }
            this.hashedData = new SignatureSubpacket[vector.size()];
            for (int i3 = 0; i3 != this.hashedData.length; i3++) {
                SignatureSubpacket signatureSubpacket = (SignatureSubpacket) vector.elementAt(i3);
                if (signatureSubpacket instanceof IssuerKeyID) {
                    this.keyID = ((IssuerKeyID) signatureSubpacket).getKeyID();
                } else if (signatureSubpacket instanceof SignatureCreationTime) {
                    this.creationTime = ((SignatureCreationTime) signatureSubpacket).getTime().getTime();
                }
                this.hashedData[i3] = signatureSubpacket;
            }
            byte[] bArr2 = new byte[(bCPGInputStream.read() << 8) | bCPGInputStream.read()];
            bCPGInputStream.readFully(bArr2);
            SignatureSubpacketInputStream signatureSubpacketInputStream2 = new SignatureSubpacketInputStream(new ByteArrayInputStream(bArr2));
            vector.removeAllElements();
            while (true) {
                SignatureSubpacket packet2 = signatureSubpacketInputStream2.readPacket();
                if (packet2 == null) {
                    break;
                } else {
                    vector.addElement(packet2);
                }
            }
            this.unhashedData = new SignatureSubpacket[vector.size()];
            for (int i4 = 0; i4 != this.unhashedData.length; i4++) {
                SignatureSubpacket signatureSubpacket2 = (SignatureSubpacket) vector.elementAt(i4);
                if (signatureSubpacket2 instanceof IssuerKeyID) {
                    this.keyID = ((IssuerKeyID) signatureSubpacket2).getKeyID();
                }
                this.unhashedData[i4] = signatureSubpacket2;
            }
        }
        byte[] bArr3 = new byte[2];
        this.fingerPrint = bArr3;
        bCPGInputStream.readFully(bArr3);
        int i5 = this.keyAlgorithm;
        if (i5 == 1 || i5 == 3) {
            this.signature = new MPInteger[]{new MPInteger(bCPGInputStream)};
            return;
        }
        if (i5 == 20 || i5 == 16) {
            this.signature = new MPInteger[]{new MPInteger(bCPGInputStream), new MPInteger(bCPGInputStream), new MPInteger(bCPGInputStream)};
            return;
        }
        if (i5 == 17) {
            this.signature = new MPInteger[]{new MPInteger(bCPGInputStream), new MPInteger(bCPGInputStream)};
            return;
        }
        if (i5 < 100 || i5 > 110) {
            throw new IOException("unknown signature key algorithm: " + this.keyAlgorithm);
        }
        this.signature = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while (true) {
            int i6 = bCPGInputStream.read();
            if (i6 < 0) {
                this.signatureEncoding = byteArrayOutputStream.toByteArray();
                return;
            }
            byteArrayOutputStream.write(i6);
        }
    }

    private void setCreationTime() {
        int i2 = 0;
        while (true) {
            SignatureSubpacket[] signatureSubpacketArr = this.hashedData;
            if (i2 == signatureSubpacketArr.length) {
                return;
            }
            SignatureSubpacket signatureSubpacket = signatureSubpacketArr[i2];
            if (signatureSubpacket instanceof SignatureCreationTime) {
                this.creationTime = ((SignatureCreationTime) signatureSubpacket).getTime().getTime();
                return;
            }
            i2++;
        }
    }

    @Override // org.bouncycastle.bcpg.ContainedPacket
    public void encode(BCPGOutputStream bCPGOutputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BCPGOutputStream bCPGOutputStream2 = new BCPGOutputStream(byteArrayOutputStream);
        bCPGOutputStream2.write(this.version);
        int i2 = this.version;
        int i3 = 0;
        if (i2 == 3 || i2 == 2) {
            bCPGOutputStream2.write(5);
            long j2 = this.creationTime / 1000;
            bCPGOutputStream2.write(this.signatureType);
            bCPGOutputStream2.write((byte) (j2 >> 24));
            bCPGOutputStream2.write((byte) (j2 >> 16));
            bCPGOutputStream2.write((byte) (j2 >> 8));
            bCPGOutputStream2.write((byte) j2);
            bCPGOutputStream2.write((byte) (this.keyID >> 56));
            bCPGOutputStream2.write((byte) (this.keyID >> 48));
            bCPGOutputStream2.write((byte) (this.keyID >> 40));
            bCPGOutputStream2.write((byte) (this.keyID >> 32));
            bCPGOutputStream2.write((byte) (this.keyID >> 24));
            bCPGOutputStream2.write((byte) (this.keyID >> 16));
            bCPGOutputStream2.write((byte) (this.keyID >> 8));
            bCPGOutputStream2.write((byte) this.keyID);
            bCPGOutputStream2.write(this.keyAlgorithm);
            bCPGOutputStream2.write(this.hashAlgorithm);
        } else {
            if (i2 != 4) {
                throw new IOException("unknown version: " + this.version);
            }
            bCPGOutputStream2.write(this.signatureType);
            bCPGOutputStream2.write(this.keyAlgorithm);
            bCPGOutputStream2.write(this.hashAlgorithm);
            ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
            int i4 = 0;
            while (true) {
                SignatureSubpacket[] signatureSubpacketArr = this.hashedData;
                if (i4 == signatureSubpacketArr.length) {
                    break;
                }
                signatureSubpacketArr[i4].encode(byteArrayOutputStream2);
                i4++;
            }
            byte[] byteArray = byteArrayOutputStream2.toByteArray();
            bCPGOutputStream2.write(byteArray.length >> 8);
            bCPGOutputStream2.write(byteArray.length);
            bCPGOutputStream2.write(byteArray);
            byteArrayOutputStream2.reset();
            int i5 = 0;
            while (true) {
                SignatureSubpacket[] signatureSubpacketArr2 = this.unhashedData;
                if (i5 == signatureSubpacketArr2.length) {
                    break;
                }
                signatureSubpacketArr2[i5].encode(byteArrayOutputStream2);
                i5++;
            }
            byte[] byteArray2 = byteArrayOutputStream2.toByteArray();
            bCPGOutputStream2.write(byteArray2.length >> 8);
            bCPGOutputStream2.write(byteArray2.length);
            bCPGOutputStream2.write(byteArray2);
        }
        bCPGOutputStream2.write(this.fingerPrint);
        if (this.signature != null) {
            while (true) {
                MPInteger[] mPIntegerArr = this.signature;
                if (i3 == mPIntegerArr.length) {
                    break;
                }
                bCPGOutputStream2.writeObject(mPIntegerArr[i3]);
                i3++;
            }
        } else {
            bCPGOutputStream2.write(this.signatureEncoding);
        }
        bCPGOutputStream.writePacket(2, byteArrayOutputStream.toByteArray(), true);
    }

    public long getCreationTime() {
        return this.creationTime;
    }

    public int getHashAlgorithm() {
        return this.hashAlgorithm;
    }

    public SignatureSubpacket[] getHashedSubPackets() {
        return this.hashedData;
    }

    public int getKeyAlgorithm() {
        return this.keyAlgorithm;
    }

    public long getKeyID() {
        return this.keyID;
    }

    public MPInteger[] getSignature() {
        return this.signature;
    }

    public byte[] getSignatureBytes() {
        byte[] bArr = this.signatureEncoding;
        if (bArr != null) {
            return Arrays.clone(bArr);
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BCPGOutputStream bCPGOutputStream = new BCPGOutputStream(byteArrayOutputStream);
        int i2 = 0;
        while (true) {
            MPInteger[] mPIntegerArr = this.signature;
            if (i2 == mPIntegerArr.length) {
                return byteArrayOutputStream.toByteArray();
            }
            try {
                bCPGOutputStream.writeObject(mPIntegerArr[i2]);
                i2++;
            } catch (IOException e2) {
                throw new RuntimeException("internal error: " + e2);
            }
        }
    }

    public byte[] getSignatureTrailer() throws IOException {
        int i2 = this.version;
        if (i2 == 3 || i2 == 2) {
            return new byte[]{(byte) this.signatureType, (byte) (r7 >> 24), (byte) (r7 >> 16), (byte) (r7 >> 8), (byte) (this.creationTime / 1000)};
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            byteArrayOutputStream.write((byte) getVersion());
            byteArrayOutputStream.write((byte) getSignatureType());
            byteArrayOutputStream.write((byte) getKeyAlgorithm());
            byteArrayOutputStream.write((byte) getHashAlgorithm());
            ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
            SignatureSubpacket[] hashedSubPackets = getHashedSubPackets();
            for (int i3 = 0; i3 != hashedSubPackets.length; i3++) {
                hashedSubPackets[i3].encode(byteArrayOutputStream2);
            }
            byte[] byteArray = byteArrayOutputStream2.toByteArray();
            byteArrayOutputStream.write((byte) (byteArray.length >> 8));
            byteArrayOutputStream.write((byte) byteArray.length);
            byteArrayOutputStream.write(byteArray);
            byte[] byteArray2 = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.write((byte) getVersion());
            byteArrayOutputStream.write(-1);
            byteArrayOutputStream.write((byte) (byteArray2.length >> 24));
            byteArrayOutputStream.write((byte) (byteArray2.length >> 16));
            byteArrayOutputStream.write((byte) (byteArray2.length >> 8));
            byteArrayOutputStream.write((byte) byteArray2.length);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e2) {
            throw new RuntimeException("exception generating trailer: " + e2);
        }
    }

    public int getSignatureType() {
        return this.signatureType;
    }

    public SignatureSubpacket[] getUnhashedSubPackets() {
        return this.unhashedData;
    }

    public int getVersion() {
        return this.version;
    }
}
