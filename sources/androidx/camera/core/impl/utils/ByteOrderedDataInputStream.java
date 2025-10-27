package androidx.camera.core.impl.utils;

import androidx.annotation.RequiresApi;
import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteOrder;
import net.lingala.zip4j.util.InternalZipConstants;

@RequiresApi(21)
/* loaded from: classes.dex */
final class ByteOrderedDataInputStream extends InputStream implements DataInput {
    private ByteOrder mByteOrder;
    private final DataInputStream mDataInputStream;
    final int mLength;
    int mPosition;
    private static final ByteOrder LITTLE_ENDIAN = ByteOrder.LITTLE_ENDIAN;
    private static final ByteOrder BIG_ENDIAN = ByteOrder.BIG_ENDIAN;

    public ByteOrderedDataInputStream(InputStream inputStream) throws IOException {
        this(inputStream, ByteOrder.BIG_ENDIAN);
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        return this.mDataInputStream.available();
    }

    public int getLength() {
        return this.mLength;
    }

    @Override // java.io.InputStream
    public void mark(int i2) {
        synchronized (this.mDataInputStream) {
            this.mDataInputStream.mark(i2);
        }
    }

    public int peek() {
        return this.mPosition;
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        this.mPosition++;
        return this.mDataInputStream.read();
    }

    @Override // java.io.DataInput
    public boolean readBoolean() throws IOException {
        this.mPosition++;
        return this.mDataInputStream.readBoolean();
    }

    @Override // java.io.DataInput
    public byte readByte() throws IOException {
        int i2 = this.mPosition + 1;
        this.mPosition = i2;
        if (i2 > this.mLength) {
            throw new EOFException();
        }
        int i3 = this.mDataInputStream.read();
        if (i3 >= 0) {
            return (byte) i3;
        }
        throw new EOFException();
    }

    @Override // java.io.DataInput
    public char readChar() throws IOException {
        this.mPosition += 2;
        return this.mDataInputStream.readChar();
    }

    @Override // java.io.DataInput
    public double readDouble() throws IOException {
        return Double.longBitsToDouble(readLong());
    }

    @Override // java.io.DataInput
    public float readFloat() throws IOException {
        return Float.intBitsToFloat(readInt());
    }

    @Override // java.io.DataInput
    public void readFully(byte[] bArr, int i2, int i3) throws IOException {
        int i4 = this.mPosition + i3;
        this.mPosition = i4;
        if (i4 > this.mLength) {
            throw new EOFException();
        }
        if (this.mDataInputStream.read(bArr, i2, i3) != i3) {
            throw new IOException("Couldn't read up to the length of buffer");
        }
    }

    @Override // java.io.DataInput
    public int readInt() throws IOException {
        int i2 = this.mPosition + 4;
        this.mPosition = i2;
        if (i2 > this.mLength) {
            throw new EOFException();
        }
        int i3 = this.mDataInputStream.read();
        int i4 = this.mDataInputStream.read();
        int i5 = this.mDataInputStream.read();
        int i6 = this.mDataInputStream.read();
        if ((i3 | i4 | i5 | i6) < 0) {
            throw new EOFException();
        }
        ByteOrder byteOrder = this.mByteOrder;
        if (byteOrder == LITTLE_ENDIAN) {
            return (i6 << 24) + (i5 << 16) + (i4 << 8) + i3;
        }
        if (byteOrder == BIG_ENDIAN) {
            return (i3 << 24) + (i4 << 16) + (i5 << 8) + i6;
        }
        throw new IOException("Invalid byte order: " + this.mByteOrder);
    }

    @Override // java.io.DataInput
    public String readLine() {
        throw new UnsupportedOperationException("readLine() not implemented.");
    }

    @Override // java.io.DataInput
    public long readLong() throws IOException {
        int i2 = this.mPosition + 8;
        this.mPosition = i2;
        if (i2 > this.mLength) {
            throw new EOFException();
        }
        int i3 = this.mDataInputStream.read();
        int i4 = this.mDataInputStream.read();
        int i5 = this.mDataInputStream.read();
        int i6 = this.mDataInputStream.read();
        int i7 = this.mDataInputStream.read();
        int i8 = this.mDataInputStream.read();
        int i9 = this.mDataInputStream.read();
        int i10 = this.mDataInputStream.read();
        if ((i3 | i4 | i5 | i6 | i7 | i8 | i9 | i10) < 0) {
            throw new EOFException();
        }
        ByteOrder byteOrder = this.mByteOrder;
        if (byteOrder == LITTLE_ENDIAN) {
            return (i10 << 56) + (i9 << 48) + (i8 << 40) + (i7 << 32) + (i6 << 24) + (i5 << 16) + (i4 << 8) + i3;
        }
        if (byteOrder == BIG_ENDIAN) {
            return (i3 << 56) + (i4 << 48) + (i5 << 40) + (i6 << 32) + (i7 << 24) + (i8 << 16) + (i9 << 8) + i10;
        }
        throw new IOException("Invalid byte order: " + this.mByteOrder);
    }

    @Override // java.io.DataInput
    public short readShort() throws IOException {
        int i2 = this.mPosition + 2;
        this.mPosition = i2;
        if (i2 > this.mLength) {
            throw new EOFException();
        }
        int i3 = this.mDataInputStream.read();
        int i4 = this.mDataInputStream.read();
        if ((i3 | i4) < 0) {
            throw new EOFException();
        }
        ByteOrder byteOrder = this.mByteOrder;
        if (byteOrder == LITTLE_ENDIAN) {
            return (short) ((i4 << 8) + i3);
        }
        if (byteOrder == BIG_ENDIAN) {
            return (short) ((i3 << 8) + i4);
        }
        throw new IOException("Invalid byte order: " + this.mByteOrder);
    }

    @Override // java.io.DataInput
    public String readUTF() throws IOException {
        this.mPosition += 2;
        return this.mDataInputStream.readUTF();
    }

    @Override // java.io.DataInput
    public int readUnsignedByte() throws IOException {
        this.mPosition++;
        return this.mDataInputStream.readUnsignedByte();
    }

    public long readUnsignedInt() throws IOException {
        return readInt() & InternalZipConstants.ZIP_64_LIMIT;
    }

    @Override // java.io.DataInput
    public int readUnsignedShort() throws IOException {
        int i2 = this.mPosition + 2;
        this.mPosition = i2;
        if (i2 > this.mLength) {
            throw new EOFException();
        }
        int i3 = this.mDataInputStream.read();
        int i4 = this.mDataInputStream.read();
        if ((i3 | i4) < 0) {
            throw new EOFException();
        }
        ByteOrder byteOrder = this.mByteOrder;
        if (byteOrder == LITTLE_ENDIAN) {
            return (i4 << 8) + i3;
        }
        if (byteOrder == BIG_ENDIAN) {
            return (i3 << 8) + i4;
        }
        throw new IOException("Invalid byte order: " + this.mByteOrder);
    }

    public void seek(long j2) throws IOException {
        int i2 = this.mPosition;
        if (i2 > j2) {
            this.mPosition = 0;
            this.mDataInputStream.reset();
            this.mDataInputStream.mark(this.mLength);
        } else {
            j2 -= i2;
        }
        int i3 = (int) j2;
        if (skipBytes(i3) != i3) {
            throw new IOException("Couldn't seek up to the byteCount");
        }
    }

    public void setByteOrder(ByteOrder byteOrder) {
        this.mByteOrder = byteOrder;
    }

    @Override // java.io.DataInput
    public int skipBytes(int i2) throws IOException {
        int iMin = Math.min(i2, this.mLength - this.mPosition);
        int iSkipBytes = 0;
        while (iSkipBytes < iMin) {
            iSkipBytes += this.mDataInputStream.skipBytes(iMin - iSkipBytes);
        }
        this.mPosition += iSkipBytes;
        return iSkipBytes;
    }

    public ByteOrderedDataInputStream(InputStream inputStream, ByteOrder byteOrder) throws IOException {
        this.mByteOrder = ByteOrder.BIG_ENDIAN;
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        this.mDataInputStream = dataInputStream;
        int iAvailable = dataInputStream.available();
        this.mLength = iAvailable;
        this.mPosition = 0;
        dataInputStream.mark(iAvailable);
        this.mByteOrder = byteOrder;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i2, int i3) throws IOException {
        int i4 = this.mDataInputStream.read(bArr, i2, i3);
        this.mPosition += i4;
        return i4;
    }

    @Override // java.io.DataInput
    public void readFully(byte[] bArr) throws IOException {
        int length = this.mPosition + bArr.length;
        this.mPosition = length;
        if (length <= this.mLength) {
            if (this.mDataInputStream.read(bArr, 0, bArr.length) != bArr.length) {
                throw new IOException("Couldn't read up to the length of buffer");
            }
            return;
        }
        throw new EOFException();
    }

    public ByteOrderedDataInputStream(byte[] bArr) throws IOException {
        this(new ByteArrayInputStream(bArr));
    }
}
