package com.google.android.exoplayer2.util;

import androidx.annotation.Nullable;
import com.google.common.base.Charsets;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;
import okio.Utf8;

/* loaded from: classes3.dex */
public final class ParsableByteArray {
    private byte[] data;
    private int limit;
    private int position;

    public ParsableByteArray() {
        this.data = Util.EMPTY_BYTE_ARRAY;
    }

    public int bytesLeft() {
        return this.limit - this.position;
    }

    public int capacity() {
        return this.data.length;
    }

    public void ensureCapacity(int i2) {
        if (i2 > capacity()) {
            this.data = Arrays.copyOf(this.data, i2);
        }
    }

    public byte[] getData() {
        return this.data;
    }

    public int getPosition() {
        return this.position;
    }

    public int limit() {
        return this.limit;
    }

    public char peekChar() {
        byte[] bArr = this.data;
        int i2 = this.position;
        return (char) ((bArr[i2 + 1] & 255) | ((bArr[i2] & 255) << 8));
    }

    public int peekUnsignedByte() {
        return this.data[this.position] & 255;
    }

    public void readBytes(ParsableBitArray parsableBitArray, int i2) {
        readBytes(parsableBitArray.data, 0, i2);
        parsableBitArray.setPosition(0);
    }

    @Nullable
    public String readDelimiterTerminatedString(char c3) {
        if (bytesLeft() == 0) {
            return null;
        }
        int i2 = this.position;
        while (i2 < this.limit && this.data[i2] != c3) {
            i2++;
        }
        byte[] bArr = this.data;
        int i3 = this.position;
        String strFromUtf8Bytes = Util.fromUtf8Bytes(bArr, i3, i2 - i3);
        this.position = i2;
        if (i2 < this.limit) {
            this.position = i2 + 1;
        }
        return strFromUtf8Bytes;
    }

    public double readDouble() {
        return Double.longBitsToDouble(readLong());
    }

    public float readFloat() {
        return Float.intBitsToFloat(readInt());
    }

    public int readInt() {
        byte[] bArr = this.data;
        int i2 = this.position;
        int i3 = i2 + 1;
        int i4 = i3 + 1;
        int i5 = ((bArr[i2] & 255) << 24) | ((bArr[i3] & 255) << 16);
        int i6 = i4 + 1;
        int i7 = i5 | ((bArr[i4] & 255) << 8);
        this.position = i6 + 1;
        return (bArr[i6] & 255) | i7;
    }

    public int readInt24() {
        byte[] bArr = this.data;
        int i2 = this.position;
        int i3 = i2 + 1;
        int i4 = i3 + 1;
        int i5 = (((bArr[i2] & 255) << 24) >> 8) | ((bArr[i3] & 255) << 8);
        this.position = i4 + 1;
        return (bArr[i4] & 255) | i5;
    }

    @Nullable
    public String readLine() {
        if (bytesLeft() == 0) {
            return null;
        }
        int i2 = this.position;
        while (i2 < this.limit && !Util.isLinebreak(this.data[i2])) {
            i2++;
        }
        int i3 = this.position;
        if (i2 - i3 >= 3) {
            byte[] bArr = this.data;
            if (bArr[i3] == -17 && bArr[i3 + 1] == -69 && bArr[i3 + 2] == -65) {
                this.position = i3 + 3;
            }
        }
        byte[] bArr2 = this.data;
        int i4 = this.position;
        String strFromUtf8Bytes = Util.fromUtf8Bytes(bArr2, i4, i2 - i4);
        this.position = i2;
        int i5 = this.limit;
        if (i2 == i5) {
            return strFromUtf8Bytes;
        }
        byte[] bArr3 = this.data;
        if (bArr3[i2] == 13) {
            int i6 = i2 + 1;
            this.position = i6;
            if (i6 == i5) {
                return strFromUtf8Bytes;
            }
        }
        int i7 = this.position;
        if (bArr3[i7] == 10) {
            this.position = i7 + 1;
        }
        return strFromUtf8Bytes;
    }

    public int readLittleEndianInt() {
        byte[] bArr = this.data;
        int i2 = this.position;
        int i3 = i2 + 1;
        int i4 = i3 + 1;
        int i5 = (bArr[i2] & 255) | ((bArr[i3] & 255) << 8);
        int i6 = i4 + 1;
        int i7 = i5 | ((bArr[i4] & 255) << 16);
        this.position = i6 + 1;
        return ((bArr[i6] & 255) << 24) | i7;
    }

    public int readLittleEndianInt24() {
        byte[] bArr = this.data;
        int i2 = this.position;
        int i3 = i2 + 1;
        int i4 = i3 + 1;
        int i5 = (bArr[i2] & 255) | ((bArr[i3] & 255) << 8);
        this.position = i4 + 1;
        return ((bArr[i4] & 255) << 16) | i5;
    }

    public long readLittleEndianLong() {
        byte[] bArr = this.data;
        long j2 = bArr[r1] & 255;
        int i2 = this.position + 1 + 1 + 1;
        long j3 = j2 | ((bArr[r2] & 255) << 8) | ((bArr[r1] & 255) << 16);
        long j4 = j3 | ((bArr[i2] & 255) << 24);
        long j5 = j4 | ((bArr[r3] & 255) << 32);
        long j6 = j5 | ((bArr[r4] & 255) << 40);
        long j7 = j6 | ((bArr[r3] & 255) << 48);
        this.position = i2 + 1 + 1 + 1 + 1 + 1;
        return j7 | ((bArr[r4] & 255) << 56);
    }

    public short readLittleEndianShort() {
        byte[] bArr = this.data;
        int i2 = this.position;
        int i3 = i2 + 1;
        int i4 = bArr[i2] & 255;
        this.position = i3 + 1;
        return (short) (((bArr[i3] & 255) << 8) | i4);
    }

    public long readLittleEndianUnsignedInt() {
        byte[] bArr = this.data;
        long j2 = bArr[r1] & 255;
        int i2 = this.position + 1 + 1 + 1;
        long j3 = j2 | ((bArr[r2] & 255) << 8) | ((bArr[r1] & 255) << 16);
        this.position = i2 + 1;
        return j3 | ((bArr[i2] & 255) << 24);
    }

    public int readLittleEndianUnsignedInt24() {
        byte[] bArr = this.data;
        int i2 = this.position;
        int i3 = i2 + 1;
        int i4 = i3 + 1;
        int i5 = (bArr[i2] & 255) | ((bArr[i3] & 255) << 8);
        this.position = i4 + 1;
        return ((bArr[i4] & 255) << 16) | i5;
    }

    public int readLittleEndianUnsignedIntToInt() {
        int littleEndianInt = readLittleEndianInt();
        if (littleEndianInt >= 0) {
            return littleEndianInt;
        }
        StringBuilder sb = new StringBuilder(29);
        sb.append("Top bit not zero: ");
        sb.append(littleEndianInt);
        throw new IllegalStateException(sb.toString());
    }

    public int readLittleEndianUnsignedShort() {
        byte[] bArr = this.data;
        int i2 = this.position;
        int i3 = i2 + 1;
        int i4 = bArr[i2] & 255;
        this.position = i3 + 1;
        return ((bArr[i3] & 255) << 8) | i4;
    }

    public long readLong() {
        byte[] bArr = this.data;
        long j2 = (bArr[r1] & 255) << 56;
        int i2 = this.position + 1 + 1 + 1;
        long j3 = j2 | ((bArr[r2] & 255) << 48) | ((bArr[r1] & 255) << 40);
        long j4 = j3 | ((bArr[i2] & 255) << 32);
        long j5 = j4 | ((bArr[r3] & 255) << 24);
        long j6 = j5 | ((bArr[r4] & 255) << 16);
        long j7 = j6 | ((bArr[r3] & 255) << 8);
        this.position = i2 + 1 + 1 + 1 + 1 + 1;
        return j7 | (bArr[r4] & 255);
    }

    public String readNullTerminatedString(int i2) {
        if (i2 == 0) {
            return "";
        }
        int i3 = this.position;
        int i4 = (i3 + i2) - 1;
        String strFromUtf8Bytes = Util.fromUtf8Bytes(this.data, i3, (i4 >= this.limit || this.data[i4] != 0) ? i2 : i2 - 1);
        this.position += i2;
        return strFromUtf8Bytes;
    }

    public short readShort() {
        byte[] bArr = this.data;
        int i2 = this.position;
        int i3 = i2 + 1;
        int i4 = (bArr[i2] & 255) << 8;
        this.position = i3 + 1;
        return (short) ((bArr[i3] & 255) | i4);
    }

    public String readString(int i2) {
        return readString(i2, Charsets.UTF_8);
    }

    public int readSynchSafeInt() {
        return (readUnsignedByte() << 21) | (readUnsignedByte() << 14) | (readUnsignedByte() << 7) | readUnsignedByte();
    }

    public int readUnsignedByte() {
        byte[] bArr = this.data;
        int i2 = this.position;
        this.position = i2 + 1;
        return bArr[i2] & 255;
    }

    public int readUnsignedFixedPoint1616() {
        byte[] bArr = this.data;
        int i2 = this.position;
        int i3 = i2 + 1;
        int i4 = (bArr[i3] & 255) | ((bArr[i2] & 255) << 8);
        this.position = i3 + 1 + 2;
        return i4;
    }

    public long readUnsignedInt() {
        byte[] bArr = this.data;
        long j2 = (bArr[r1] & 255) << 24;
        int i2 = this.position + 1 + 1 + 1;
        long j3 = j2 | ((bArr[r2] & 255) << 16) | ((bArr[r1] & 255) << 8);
        this.position = i2 + 1;
        return j3 | (bArr[i2] & 255);
    }

    public int readUnsignedInt24() {
        byte[] bArr = this.data;
        int i2 = this.position;
        int i3 = i2 + 1;
        int i4 = i3 + 1;
        int i5 = ((bArr[i2] & 255) << 16) | ((bArr[i3] & 255) << 8);
        this.position = i4 + 1;
        return (bArr[i4] & 255) | i5;
    }

    public int readUnsignedIntToInt() {
        int i2 = readInt();
        if (i2 >= 0) {
            return i2;
        }
        StringBuilder sb = new StringBuilder(29);
        sb.append("Top bit not zero: ");
        sb.append(i2);
        throw new IllegalStateException(sb.toString());
    }

    public long readUnsignedLongToLong() {
        long j2 = readLong();
        if (j2 >= 0) {
            return j2;
        }
        StringBuilder sb = new StringBuilder(38);
        sb.append("Top bit not zero: ");
        sb.append(j2);
        throw new IllegalStateException(sb.toString());
    }

    public int readUnsignedShort() {
        byte[] bArr = this.data;
        int i2 = this.position;
        int i3 = i2 + 1;
        int i4 = (bArr[i2] & 255) << 8;
        this.position = i3 + 1;
        return (bArr[i3] & 255) | i4;
    }

    public long readUtf8EncodedLong() {
        int i2;
        int i3;
        long j2 = this.data[this.position];
        int i4 = 7;
        while (true) {
            if (i4 < 0) {
                break;
            }
            if (((1 << i4) & j2) != 0) {
                i4--;
            } else if (i4 < 6) {
                j2 &= r6 - 1;
                i3 = 7 - i4;
            } else if (i4 == 7) {
                i3 = 1;
            }
        }
        i3 = 0;
        if (i3 == 0) {
            StringBuilder sb = new StringBuilder(55);
            sb.append("Invalid UTF-8 sequence first byte: ");
            sb.append(j2);
            throw new NumberFormatException(sb.toString());
        }
        for (i2 = 1; i2 < i3; i2++) {
            if ((this.data[this.position + i2] & 192) != 128) {
                StringBuilder sb2 = new StringBuilder(62);
                sb2.append("Invalid UTF-8 sequence continuation byte: ");
                sb2.append(j2);
                throw new NumberFormatException(sb2.toString());
            }
            j2 = (j2 << 6) | (r3 & Utf8.REPLACEMENT_BYTE);
        }
        this.position += i3;
        return j2;
    }

    public void reset(int i2) {
        reset(capacity() < i2 ? new byte[i2] : this.data, i2);
    }

    public void setLimit(int i2) {
        Assertions.checkArgument(i2 >= 0 && i2 <= this.data.length);
        this.limit = i2;
    }

    public void setPosition(int i2) {
        Assertions.checkArgument(i2 >= 0 && i2 <= this.limit);
        this.position = i2;
    }

    public void skipBytes(int i2) {
        setPosition(this.position + i2);
    }

    public String readString(int i2, Charset charset) {
        String str = new String(this.data, this.position, i2, charset);
        this.position += i2;
        return str;
    }

    public void reset(byte[] bArr) {
        reset(bArr, bArr.length);
    }

    public ParsableByteArray(int i2) {
        this.data = new byte[i2];
        this.limit = i2;
    }

    public void readBytes(byte[] bArr, int i2, int i3) {
        System.arraycopy(this.data, this.position, bArr, i2, i3);
        this.position += i3;
    }

    public void reset(byte[] bArr, int i2) {
        this.data = bArr;
        this.limit = i2;
        this.position = 0;
    }

    public void readBytes(ByteBuffer byteBuffer, int i2) {
        byteBuffer.put(this.data, this.position, i2);
        this.position += i2;
    }

    @Nullable
    public String readNullTerminatedString() {
        return readDelimiterTerminatedString((char) 0);
    }

    public ParsableByteArray(byte[] bArr) {
        this.data = bArr;
        this.limit = bArr.length;
    }

    public ParsableByteArray(byte[] bArr, int i2) {
        this.data = bArr;
        this.limit = i2;
    }
}
