package org.bouncycastle.bcpg;

import java.io.IOException;
import java.io.OutputStream;
import net.lingala.zip4j.util.InternalZipConstants;
import okhttp3.internal.ws.WebSocketProtocol;

/* loaded from: classes9.dex */
public class BCPGOutputStream extends OutputStream implements PacketTags, CompressionAlgorithmTags {
    private static final int BUF_SIZE_POWER = 16;
    OutputStream out;
    private byte[] partialBuffer;
    private int partialBufferLength;
    private int partialOffset;
    private int partialPower;

    public BCPGOutputStream(OutputStream outputStream) {
        this.out = outputStream;
    }

    public BCPGOutputStream(OutputStream outputStream, int i2) throws IOException {
        this.out = outputStream;
        writeHeader(i2, true, true, 0L);
    }

    public BCPGOutputStream(OutputStream outputStream, int i2, long j2) throws IOException {
        this.out = outputStream;
        writeHeader(i2, false, false, j2);
    }

    public BCPGOutputStream(OutputStream outputStream, int i2, long j2, boolean z2) throws IOException {
        this.out = outputStream;
        if (j2 <= InternalZipConstants.ZIP_64_LIMIT) {
            writeHeader(i2, z2, false, j2);
            return;
        }
        writeHeader(i2, false, true, 0L);
        this.partialBufferLength = 65536;
        this.partialBuffer = new byte[65536];
        this.partialPower = 16;
        this.partialOffset = 0;
    }

    public BCPGOutputStream(OutputStream outputStream, int i2, byte[] bArr) throws IOException {
        this.out = outputStream;
        writeHeader(i2, false, true, 0L);
        this.partialBuffer = bArr;
        int length = bArr.length;
        this.partialPower = 0;
        while (length != 1) {
            length >>>= 1;
            this.partialPower++;
        }
        int i3 = this.partialPower;
        if (i3 > 30) {
            throw new IOException("Buffer cannot be greater than 2^30 in length.");
        }
        this.partialBufferLength = 1 << i3;
        this.partialOffset = 0;
    }

    private void partialFlush(boolean z2) throws IOException {
        if (z2) {
            writeNewPacketLength(this.partialOffset);
            this.out.write(this.partialBuffer, 0, this.partialOffset);
        } else {
            this.out.write(this.partialPower | 224);
            this.out.write(this.partialBuffer, 0, this.partialBufferLength);
        }
        this.partialOffset = 0;
    }

    private void writeHeader(int i2, boolean z2, boolean z3, long j2) throws IOException {
        int i3;
        int i4;
        if (this.partialBuffer != null) {
            partialFlush(true);
            this.partialBuffer = null;
        }
        if (!z2) {
            write(i2 | 64 | 128);
            if (z3) {
                this.partialOffset = 0;
                return;
            } else {
                writeNewPacketLength(j2);
                return;
            }
        }
        int i5 = (i2 << 2) | 128;
        if (z3) {
            i4 = i5 | 3;
        } else {
            if (j2 > 255) {
                if (j2 <= WebSocketProtocol.PAYLOAD_SHORT_MAX) {
                    i3 = i5 | 1;
                } else {
                    write(i5 | 2);
                    write((byte) (j2 >> 24));
                    i3 = (byte) (j2 >> 16);
                }
                write(i3);
                i5 = (byte) (j2 >> 8);
            }
            write(i5);
            i4 = (byte) j2;
        }
        write(i4);
    }

    private void writeNewPacketLength(long j2) throws IOException {
        if (j2 >= 192) {
            if (j2 <= 8383) {
                j2 -= 192;
                this.out.write((byte) (((j2 >> 8) & 255) + 192));
            } else {
                this.out.write(255);
                this.out.write((byte) (j2 >> 24));
                this.out.write((byte) (j2 >> 16));
                this.out.write((byte) (j2 >> 8));
            }
        }
        this.out.write((byte) j2);
    }

    private void writePartial(byte b3) throws IOException {
        if (this.partialOffset == this.partialBufferLength) {
            partialFlush(false);
        }
        byte[] bArr = this.partialBuffer;
        int i2 = this.partialOffset;
        this.partialOffset = i2 + 1;
        bArr[i2] = b3;
    }

    private void writePartial(byte[] bArr, int i2, int i3) throws IOException {
        if (this.partialOffset == this.partialBufferLength) {
            partialFlush(false);
        }
        int i4 = this.partialBufferLength;
        int i5 = this.partialOffset;
        if (i3 <= i4 - i5) {
            System.arraycopy(bArr, i2, this.partialBuffer, i5, i3);
        } else {
            System.arraycopy(bArr, i2, this.partialBuffer, i5, i4 - i5);
            int i6 = this.partialBufferLength;
            int i7 = this.partialOffset;
            int i8 = i2 + (i6 - i7);
            int i9 = i6 - i7;
            while (true) {
                i3 -= i9;
                partialFlush(false);
                int i10 = this.partialBufferLength;
                if (i3 <= i10) {
                    break;
                }
                System.arraycopy(bArr, i8, this.partialBuffer, 0, i10);
                i9 = this.partialBufferLength;
                i8 += i9;
            }
            System.arraycopy(bArr, i8, this.partialBuffer, 0, i3);
        }
        this.partialOffset += i3;
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        finish();
        this.out.flush();
        this.out.close();
    }

    public void finish() throws IOException {
        if (this.partialBuffer != null) {
            partialFlush(true);
            this.partialBuffer = null;
        }
    }

    @Override // java.io.OutputStream, java.io.Flushable
    public void flush() throws IOException {
        this.out.flush();
    }

    @Override // java.io.OutputStream
    public void write(int i2) throws IOException {
        if (this.partialBuffer != null) {
            writePartial((byte) i2);
        } else {
            this.out.write(i2);
        }
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i2, int i3) throws IOException {
        if (this.partialBuffer != null) {
            writePartial(bArr, i2, i3);
        } else {
            this.out.write(bArr, i2, i3);
        }
    }

    public void writeObject(BCPGObject bCPGObject) throws IOException {
        bCPGObject.encode(this);
    }

    public void writePacket(int i2, byte[] bArr, boolean z2) throws IOException {
        writeHeader(i2, z2, false, bArr.length);
        write(bArr);
    }

    public void writePacket(ContainedPacket containedPacket) throws IOException {
        containedPacket.encode(this);
    }
}
