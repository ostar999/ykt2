package org.bouncycastle.bcpg;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import org.bouncycastle.util.io.Streams;

/* loaded from: classes9.dex */
public class BCPGInputStream extends InputStream implements PacketTags {
    InputStream in;
    boolean next = false;
    int nextB;

    public static class PartialInputStream extends InputStream {
        private int dataLength;
        private BCPGInputStream in;
        private boolean partial;

        public PartialInputStream(BCPGInputStream bCPGInputStream, boolean z2, int i2) {
            this.in = bCPGInputStream;
            this.partial = z2;
            this.dataLength = i2;
        }

        private int loadDataLength() throws IOException {
            int i2 = this.in.read();
            if (i2 < 0) {
                return -1;
            }
            this.partial = false;
            if (i2 >= 192) {
                if (i2 <= 223) {
                    i2 = ((i2 - 192) << 8) + this.in.read() + 192;
                } else if (i2 == 255) {
                    i2 = (this.in.read() << 24) | (this.in.read() << 16) | (this.in.read() << 8) | this.in.read();
                } else {
                    this.partial = true;
                    i2 = 1 << (i2 & 31);
                }
            }
            this.dataLength = i2;
            return this.dataLength;
        }

        @Override // java.io.InputStream
        public int available() throws IOException {
            int iAvailable = this.in.available();
            int i2 = this.dataLength;
            if (iAvailable <= i2 || i2 < 0) {
                return iAvailable;
            }
            if (this.partial && i2 == 0) {
                return 1;
            }
            return i2;
        }

        @Override // java.io.InputStream
        public int read() throws IOException {
            while (this.dataLength == 0) {
                if (!this.partial || loadDataLength() < 0) {
                    return -1;
                }
            }
            int i2 = this.in.read();
            if (i2 < 0) {
                throw new EOFException("premature end of stream in PartialInputStream");
            }
            this.dataLength--;
            return i2;
        }

        @Override // java.io.InputStream
        public int read(byte[] bArr, int i2, int i3) throws IOException {
            do {
                int i4 = this.dataLength;
                if (i4 != 0) {
                    if (i4 <= i3 && i4 >= 0) {
                        i3 = i4;
                    }
                    int i5 = this.in.read(bArr, i2, i3);
                    if (i5 < 0) {
                        throw new EOFException("premature end of stream in PartialInputStream");
                    }
                    this.dataLength -= i5;
                    return i5;
                }
                if (!this.partial) {
                    return -1;
                }
            } while (loadDataLength() >= 0);
            return -1;
        }
    }

    public BCPGInputStream(InputStream inputStream) {
        this.in = inputStream;
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        return this.in.available();
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.in.close();
    }

    public int nextPacketTag() throws IOException {
        if (!this.next) {
            try {
                this.nextB = this.in.read();
            } catch (EOFException unused) {
                this.nextB = -1;
            }
        }
        this.next = true;
        int i2 = this.nextB;
        if (i2 < 0) {
            return i2;
        }
        int i3 = i2 & 64;
        int i4 = i2 & 63;
        return i3 != 0 ? i4 : i4 >> 2;
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        if (!this.next) {
            return this.in.read();
        }
        this.next = false;
        return this.nextB;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i2, int i3) throws IOException {
        if (i3 == 0) {
            return 0;
        }
        if (!this.next) {
            return this.in.read(bArr, i2, i3);
        }
        int i4 = this.nextB;
        if (i4 < 0) {
            return -1;
        }
        bArr[i2] = (byte) i4;
        this.next = false;
        return 1;
    }

    public void readFully(byte[] bArr) throws IOException {
        readFully(bArr, 0, bArr.length);
    }

    public void readFully(byte[] bArr, int i2, int i3) throws IOException {
        if (Streams.readFully(this, bArr, i2, i3) < i3) {
            throw new EOFException();
        }
    }

    public Packet readPacket() throws IOException {
        int i2;
        int i3;
        int i4;
        int i5;
        int i6 = read();
        if (i6 < 0) {
            return null;
        }
        if ((i6 & 128) == 0) {
            throw new IOException("invalid header encountered");
        }
        int i7 = 0;
        boolean z2 = true;
        if ((i6 & 64) != 0) {
            i2 = i6 & 63;
            i3 = read();
            if (i3 < 192) {
                z2 = false;
                i7 = i3;
            } else if (i3 <= 223) {
                i3 = ((i3 - 192) << 8) + this.in.read() + 192;
                z2 = false;
                i7 = i3;
            } else if (i3 == 255) {
                i4 = (this.in.read() << 24) | (this.in.read() << 16) | (this.in.read() << 8);
                i5 = this.in.read();
                i3 = i4 | i5;
                z2 = false;
                i7 = i3;
            } else {
                i3 = 1 << (i3 & 31);
                i7 = i3;
            }
        } else {
            int i8 = i6 & 3;
            i2 = (i6 & 63) >> 2;
            if (i8 != 0) {
                if (i8 == 1) {
                    i4 = read() << 8;
                } else if (i8 == 2) {
                    i4 = (read() << 24) | (read() << 16) | (read() << 8);
                } else if (i8 != 3) {
                    throw new IOException("unknown length type encountered");
                }
                i5 = read();
                i3 = i4 | i5;
                z2 = false;
                i7 = i3;
            } else {
                i3 = read();
                z2 = false;
                i7 = i3;
            }
        }
        BCPGInputStream bCPGInputStream = (i7 == 0 && z2) ? this : new BCPGInputStream(new PartialInputStream(this, z2, i7));
        switch (i2) {
            case 0:
                return new InputStreamPacket(bCPGInputStream);
            case 1:
                return new PublicKeyEncSessionPacket(bCPGInputStream);
            case 2:
                return new SignaturePacket(bCPGInputStream);
            case 3:
                return new SymmetricKeyEncSessionPacket(bCPGInputStream);
            case 4:
                return new OnePassSignaturePacket(bCPGInputStream);
            case 5:
                return new SecretKeyPacket(bCPGInputStream);
            case 6:
                return new PublicKeyPacket(bCPGInputStream);
            case 7:
                return new SecretSubkeyPacket(bCPGInputStream);
            case 8:
                return new CompressedDataPacket(bCPGInputStream);
            case 9:
                return new SymmetricEncDataPacket(bCPGInputStream);
            case 10:
                return new MarkerPacket(bCPGInputStream);
            case 11:
                return new LiteralDataPacket(bCPGInputStream);
            case 12:
                return new TrustPacket(bCPGInputStream);
            case 13:
                return new UserIDPacket(bCPGInputStream);
            case 14:
                return new PublicSubkeyPacket(bCPGInputStream);
            default:
                switch (i2) {
                    case 17:
                        return new UserAttributePacket(bCPGInputStream);
                    case 18:
                        return new SymmetricEncIntegrityPacket(bCPGInputStream);
                    case 19:
                        return new ModDetectionCodePacket(bCPGInputStream);
                    default:
                        switch (i2) {
                            case 60:
                            case 61:
                            case 62:
                            case 63:
                                return new ExperimentalPacket(i2, bCPGInputStream);
                            default:
                                throw new IOException("unknown packet type encountered: " + i2);
                        }
                }
        }
    }
}
