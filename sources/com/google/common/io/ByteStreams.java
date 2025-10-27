package com.google.common.io;

import android.support.v4.media.session.PlaybackStateCompat;
import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.math.IntMath;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

@GwtIncompatible
/* loaded from: classes4.dex */
public final class ByteStreams {
    private static final int BUFFER_SIZE = 8192;
    private static final int MAX_ARRAY_LEN = 2147483639;
    private static final OutputStream NULL_OUTPUT_STREAM = new OutputStream() { // from class: com.google.common.io.ByteStreams.1
        public String toString() {
            return "ByteStreams.nullOutputStream()";
        }

        @Override // java.io.OutputStream
        public void write(int i2) {
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr) {
            Preconditions.checkNotNull(bArr);
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr, int i2, int i3) {
            Preconditions.checkNotNull(bArr);
        }
    };
    private static final int TO_BYTE_ARRAY_DEQUE_SIZE = 20;
    private static final int ZERO_COPY_CHUNK_SIZE = 524288;

    private ByteStreams() {
    }

    private static byte[] combineBuffers(Deque<byte[]> deque, int i2) {
        byte[] bArr = new byte[i2];
        int i3 = i2;
        while (i3 > 0) {
            byte[] bArrRemoveFirst = deque.removeFirst();
            int iMin = Math.min(i3, bArrRemoveFirst.length);
            System.arraycopy(bArrRemoveFirst, 0, bArr, i2 - i3, iMin);
            i3 -= iMin;
        }
        return bArr;
    }

    @CanIgnoreReturnValue
    public static long copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        Preconditions.checkNotNull(inputStream);
        Preconditions.checkNotNull(outputStream);
        byte[] bArrCreateBuffer = createBuffer();
        long j2 = 0;
        while (true) {
            int i2 = inputStream.read(bArrCreateBuffer);
            if (i2 == -1) {
                return j2;
            }
            outputStream.write(bArrCreateBuffer, 0, i2);
            j2 += i2;
        }
    }

    public static byte[] createBuffer() {
        return new byte[8192];
    }

    @CanIgnoreReturnValue
    @Beta
    public static long exhaust(InputStream inputStream) throws IOException {
        byte[] bArrCreateBuffer = createBuffer();
        long j2 = 0;
        while (true) {
            long j3 = inputStream.read(bArrCreateBuffer);
            if (j3 == -1) {
                return j2;
            }
            j2 += j3;
        }
    }

    @Beta
    public static InputStream limit(InputStream inputStream, long j2) {
        return new LimitedInputStream(inputStream, j2);
    }

    @Beta
    public static ByteArrayDataInput newDataInput(byte[] bArr) {
        return newDataInput(new ByteArrayInputStream(bArr));
    }

    @Beta
    public static ByteArrayDataOutput newDataOutput() {
        return newDataOutput(new ByteArrayOutputStream());
    }

    @Beta
    public static OutputStream nullOutputStream() {
        return NULL_OUTPUT_STREAM;
    }

    @CanIgnoreReturnValue
    @Beta
    public static int read(InputStream inputStream, byte[] bArr, int i2, int i3) throws IOException {
        Preconditions.checkNotNull(inputStream);
        Preconditions.checkNotNull(bArr);
        if (i3 < 0) {
            throw new IndexOutOfBoundsException("len is negative");
        }
        int i4 = 0;
        while (i4 < i3) {
            int i5 = inputStream.read(bArr, i2 + i4, i3 - i4);
            if (i5 == -1) {
                break;
            }
            i4 += i5;
        }
        return i4;
    }

    @CanIgnoreReturnValue
    @Beta
    public static <T> T readBytes(InputStream inputStream, ByteProcessor<T> byteProcessor) throws IOException {
        int i2;
        Preconditions.checkNotNull(inputStream);
        Preconditions.checkNotNull(byteProcessor);
        byte[] bArrCreateBuffer = createBuffer();
        do {
            i2 = inputStream.read(bArrCreateBuffer);
            if (i2 == -1) {
                break;
            }
        } while (byteProcessor.processBytes(bArrCreateBuffer, 0, i2));
        return byteProcessor.getResult();
    }

    @Beta
    public static void readFully(InputStream inputStream, byte[] bArr) throws IOException {
        readFully(inputStream, bArr, 0, bArr.length);
    }

    @Beta
    public static void skipFully(InputStream inputStream, long j2) throws IOException {
        long jSkipUpTo = skipUpTo(inputStream, j2);
        if (jSkipUpTo >= j2) {
            return;
        }
        throw new EOFException("reached end of stream after skipping " + jSkipUpTo + " bytes; " + j2 + " bytes expected");
    }

    private static long skipSafely(InputStream inputStream, long j2) throws IOException {
        int iAvailable = inputStream.available();
        if (iAvailable == 0) {
            return 0L;
        }
        return inputStream.skip(Math.min(iAvailable, j2));
    }

    public static long skipUpTo(InputStream inputStream, long j2) throws IOException {
        byte[] bArrCreateBuffer = createBuffer();
        long j3 = 0;
        while (j3 < j2) {
            long j4 = j2 - j3;
            long jSkipSafely = skipSafely(inputStream, j4);
            if (jSkipSafely == 0) {
                jSkipSafely = inputStream.read(bArrCreateBuffer, 0, (int) Math.min(j4, bArrCreateBuffer.length));
                if (jSkipSafely == -1) {
                    break;
                }
            }
            j3 += jSkipSafely;
        }
        return j3;
    }

    public static byte[] toByteArray(InputStream inputStream) throws IOException {
        Preconditions.checkNotNull(inputStream);
        return toByteArrayInternal(inputStream, new ArrayDeque(20), 0);
    }

    private static byte[] toByteArrayInternal(InputStream inputStream, Deque<byte[]> deque, int i2) throws IOException {
        int iSaturatedMultiply = 8192;
        while (i2 < MAX_ARRAY_LEN) {
            int iMin = Math.min(iSaturatedMultiply, MAX_ARRAY_LEN - i2);
            byte[] bArr = new byte[iMin];
            deque.add(bArr);
            int i3 = 0;
            while (i3 < iMin) {
                int i4 = inputStream.read(bArr, i3, iMin - i3);
                if (i4 == -1) {
                    return combineBuffers(deque, i2);
                }
                i3 += i4;
                i2 += i4;
            }
            iSaturatedMultiply = IntMath.saturatedMultiply(iSaturatedMultiply, 2);
        }
        if (inputStream.read() == -1) {
            return combineBuffers(deque, MAX_ARRAY_LEN);
        }
        throw new OutOfMemoryError("input is too large to fit in a byte array");
    }

    public static class ByteArrayDataInputStream implements ByteArrayDataInput {
        final DataInput input;

        public ByteArrayDataInputStream(ByteArrayInputStream byteArrayInputStream) {
            this.input = new DataInputStream(byteArrayInputStream);
        }

        @Override // com.google.common.io.ByteArrayDataInput, java.io.DataInput
        public boolean readBoolean() {
            try {
                return this.input.readBoolean();
            } catch (IOException e2) {
                throw new IllegalStateException(e2);
            }
        }

        @Override // com.google.common.io.ByteArrayDataInput, java.io.DataInput
        public byte readByte() {
            try {
                return this.input.readByte();
            } catch (EOFException e2) {
                throw new IllegalStateException(e2);
            } catch (IOException e3) {
                throw new AssertionError(e3);
            }
        }

        @Override // com.google.common.io.ByteArrayDataInput, java.io.DataInput
        public char readChar() {
            try {
                return this.input.readChar();
            } catch (IOException e2) {
                throw new IllegalStateException(e2);
            }
        }

        @Override // com.google.common.io.ByteArrayDataInput, java.io.DataInput
        public double readDouble() {
            try {
                return this.input.readDouble();
            } catch (IOException e2) {
                throw new IllegalStateException(e2);
            }
        }

        @Override // com.google.common.io.ByteArrayDataInput, java.io.DataInput
        public float readFloat() {
            try {
                return this.input.readFloat();
            } catch (IOException e2) {
                throw new IllegalStateException(e2);
            }
        }

        @Override // com.google.common.io.ByteArrayDataInput, java.io.DataInput
        public void readFully(byte[] bArr) throws IOException {
            try {
                this.input.readFully(bArr);
            } catch (IOException e2) {
                throw new IllegalStateException(e2);
            }
        }

        @Override // com.google.common.io.ByteArrayDataInput, java.io.DataInput
        public int readInt() {
            try {
                return this.input.readInt();
            } catch (IOException e2) {
                throw new IllegalStateException(e2);
            }
        }

        @Override // com.google.common.io.ByteArrayDataInput, java.io.DataInput
        public String readLine() {
            try {
                return this.input.readLine();
            } catch (IOException e2) {
                throw new IllegalStateException(e2);
            }
        }

        @Override // com.google.common.io.ByteArrayDataInput, java.io.DataInput
        public long readLong() {
            try {
                return this.input.readLong();
            } catch (IOException e2) {
                throw new IllegalStateException(e2);
            }
        }

        @Override // com.google.common.io.ByteArrayDataInput, java.io.DataInput
        public short readShort() {
            try {
                return this.input.readShort();
            } catch (IOException e2) {
                throw new IllegalStateException(e2);
            }
        }

        @Override // com.google.common.io.ByteArrayDataInput, java.io.DataInput
        public String readUTF() {
            try {
                return this.input.readUTF();
            } catch (IOException e2) {
                throw new IllegalStateException(e2);
            }
        }

        @Override // com.google.common.io.ByteArrayDataInput, java.io.DataInput
        public int readUnsignedByte() {
            try {
                return this.input.readUnsignedByte();
            } catch (IOException e2) {
                throw new IllegalStateException(e2);
            }
        }

        @Override // com.google.common.io.ByteArrayDataInput, java.io.DataInput
        public int readUnsignedShort() {
            try {
                return this.input.readUnsignedShort();
            } catch (IOException e2) {
                throw new IllegalStateException(e2);
            }
        }

        @Override // com.google.common.io.ByteArrayDataInput, java.io.DataInput
        public int skipBytes(int i2) {
            try {
                return this.input.skipBytes(i2);
            } catch (IOException e2) {
                throw new IllegalStateException(e2);
            }
        }

        @Override // com.google.common.io.ByteArrayDataInput, java.io.DataInput
        public void readFully(byte[] bArr, int i2, int i3) throws IOException {
            try {
                this.input.readFully(bArr, i2, i3);
            } catch (IOException e2) {
                throw new IllegalStateException(e2);
            }
        }
    }

    public static class ByteArrayDataOutputStream implements ByteArrayDataOutput {
        final ByteArrayOutputStream byteArrayOutputSteam;
        final DataOutput output;

        public ByteArrayDataOutputStream(ByteArrayOutputStream byteArrayOutputStream) {
            this.byteArrayOutputSteam = byteArrayOutputStream;
            this.output = new DataOutputStream(byteArrayOutputStream);
        }

        @Override // com.google.common.io.ByteArrayDataOutput
        public byte[] toByteArray() {
            return this.byteArrayOutputSteam.toByteArray();
        }

        @Override // com.google.common.io.ByteArrayDataOutput, java.io.DataOutput
        public void write(int i2) throws IOException {
            try {
                this.output.write(i2);
            } catch (IOException e2) {
                throw new AssertionError(e2);
            }
        }

        @Override // com.google.common.io.ByteArrayDataOutput, java.io.DataOutput
        public void writeBoolean(boolean z2) throws IOException {
            try {
                this.output.writeBoolean(z2);
            } catch (IOException e2) {
                throw new AssertionError(e2);
            }
        }

        @Override // com.google.common.io.ByteArrayDataOutput, java.io.DataOutput
        public void writeByte(int i2) throws IOException {
            try {
                this.output.writeByte(i2);
            } catch (IOException e2) {
                throw new AssertionError(e2);
            }
        }

        @Override // com.google.common.io.ByteArrayDataOutput, java.io.DataOutput
        public void writeBytes(String str) throws IOException {
            try {
                this.output.writeBytes(str);
            } catch (IOException e2) {
                throw new AssertionError(e2);
            }
        }

        @Override // com.google.common.io.ByteArrayDataOutput, java.io.DataOutput
        public void writeChar(int i2) throws IOException {
            try {
                this.output.writeChar(i2);
            } catch (IOException e2) {
                throw new AssertionError(e2);
            }
        }

        @Override // com.google.common.io.ByteArrayDataOutput, java.io.DataOutput
        public void writeChars(String str) throws IOException {
            try {
                this.output.writeChars(str);
            } catch (IOException e2) {
                throw new AssertionError(e2);
            }
        }

        @Override // com.google.common.io.ByteArrayDataOutput, java.io.DataOutput
        public void writeDouble(double d3) throws IOException {
            try {
                this.output.writeDouble(d3);
            } catch (IOException e2) {
                throw new AssertionError(e2);
            }
        }

        @Override // com.google.common.io.ByteArrayDataOutput, java.io.DataOutput
        public void writeFloat(float f2) throws IOException {
            try {
                this.output.writeFloat(f2);
            } catch (IOException e2) {
                throw new AssertionError(e2);
            }
        }

        @Override // com.google.common.io.ByteArrayDataOutput, java.io.DataOutput
        public void writeInt(int i2) throws IOException {
            try {
                this.output.writeInt(i2);
            } catch (IOException e2) {
                throw new AssertionError(e2);
            }
        }

        @Override // com.google.common.io.ByteArrayDataOutput, java.io.DataOutput
        public void writeLong(long j2) throws IOException {
            try {
                this.output.writeLong(j2);
            } catch (IOException e2) {
                throw new AssertionError(e2);
            }
        }

        @Override // com.google.common.io.ByteArrayDataOutput, java.io.DataOutput
        public void writeShort(int i2) throws IOException {
            try {
                this.output.writeShort(i2);
            } catch (IOException e2) {
                throw new AssertionError(e2);
            }
        }

        @Override // com.google.common.io.ByteArrayDataOutput, java.io.DataOutput
        public void writeUTF(String str) throws IOException {
            try {
                this.output.writeUTF(str);
            } catch (IOException e2) {
                throw new AssertionError(e2);
            }
        }

        @Override // com.google.common.io.ByteArrayDataOutput, java.io.DataOutput
        public void write(byte[] bArr) throws IOException {
            try {
                this.output.write(bArr);
            } catch (IOException e2) {
                throw new AssertionError(e2);
            }
        }

        @Override // com.google.common.io.ByteArrayDataOutput, java.io.DataOutput
        public void write(byte[] bArr, int i2, int i3) throws IOException {
            try {
                this.output.write(bArr, i2, i3);
            } catch (IOException e2) {
                throw new AssertionError(e2);
            }
        }
    }

    @Beta
    public static ByteArrayDataInput newDataInput(byte[] bArr, int i2) {
        Preconditions.checkPositionIndex(i2, bArr.length);
        return newDataInput(new ByteArrayInputStream(bArr, i2, bArr.length - i2));
    }

    @Beta
    public static ByteArrayDataOutput newDataOutput(int i2) {
        if (i2 >= 0) {
            return newDataOutput(new ByteArrayOutputStream(i2));
        }
        throw new IllegalArgumentException(String.format("Invalid size: %s", Integer.valueOf(i2)));
    }

    @Beta
    public static void readFully(InputStream inputStream, byte[] bArr, int i2, int i3) throws IOException {
        int i4 = read(inputStream, bArr, i2, i3);
        if (i4 == i3) {
            return;
        }
        throw new EOFException("reached end of stream after reading " + i4 + " bytes; " + i3 + " bytes expected");
    }

    public static final class LimitedInputStream extends FilterInputStream {
        private long left;
        private long mark;

        public LimitedInputStream(InputStream inputStream, long j2) {
            super(inputStream);
            this.mark = -1L;
            Preconditions.checkNotNull(inputStream);
            Preconditions.checkArgument(j2 >= 0, "limit must be non-negative");
            this.left = j2;
        }

        @Override // java.io.FilterInputStream, java.io.InputStream
        public int available() throws IOException {
            return (int) Math.min(((FilterInputStream) this).in.available(), this.left);
        }

        @Override // java.io.FilterInputStream, java.io.InputStream
        public synchronized void mark(int i2) {
            ((FilterInputStream) this).in.mark(i2);
            this.mark = this.left;
        }

        @Override // java.io.FilterInputStream, java.io.InputStream
        public int read() throws IOException {
            if (this.left == 0) {
                return -1;
            }
            int i2 = ((FilterInputStream) this).in.read();
            if (i2 != -1) {
                this.left--;
            }
            return i2;
        }

        @Override // java.io.FilterInputStream, java.io.InputStream
        public synchronized void reset() throws IOException {
            if (!((FilterInputStream) this).in.markSupported()) {
                throw new IOException("Mark not supported");
            }
            if (this.mark == -1) {
                throw new IOException("Mark not set");
            }
            ((FilterInputStream) this).in.reset();
            this.left = this.mark;
        }

        @Override // java.io.FilterInputStream, java.io.InputStream
        public long skip(long j2) throws IOException {
            long jSkip = ((FilterInputStream) this).in.skip(Math.min(j2, this.left));
            this.left -= jSkip;
            return jSkip;
        }

        @Override // java.io.FilterInputStream, java.io.InputStream
        public int read(byte[] bArr, int i2, int i3) throws IOException {
            long j2 = this.left;
            if (j2 == 0) {
                return -1;
            }
            int i4 = ((FilterInputStream) this).in.read(bArr, i2, (int) Math.min(i3, j2));
            if (i4 != -1) {
                this.left -= i4;
            }
            return i4;
        }
    }

    public static byte[] toByteArray(InputStream inputStream, long j2) throws IOException {
        Preconditions.checkArgument(j2 >= 0, "expectedSize (%s) must be non-negative", j2);
        if (j2 <= 2147483639) {
            int i2 = (int) j2;
            byte[] bArr = new byte[i2];
            int i3 = i2;
            while (i3 > 0) {
                int i4 = i2 - i3;
                int i5 = inputStream.read(bArr, i4, i3);
                if (i5 == -1) {
                    return Arrays.copyOf(bArr, i4);
                }
                i3 -= i5;
            }
            int i6 = inputStream.read();
            if (i6 == -1) {
                return bArr;
            }
            ArrayDeque arrayDeque = new ArrayDeque(22);
            arrayDeque.add(bArr);
            arrayDeque.add(new byte[]{(byte) i6});
            return toByteArrayInternal(inputStream, arrayDeque, i2 + 1);
        }
        throw new OutOfMemoryError(j2 + " bytes is too large to fit in a byte array");
    }

    @Beta
    public static ByteArrayDataInput newDataInput(ByteArrayInputStream byteArrayInputStream) {
        return new ByteArrayDataInputStream((ByteArrayInputStream) Preconditions.checkNotNull(byteArrayInputStream));
    }

    @Beta
    public static ByteArrayDataOutput newDataOutput(ByteArrayOutputStream byteArrayOutputStream) {
        return new ByteArrayDataOutputStream((ByteArrayOutputStream) Preconditions.checkNotNull(byteArrayOutputStream));
    }

    @CanIgnoreReturnValue
    public static long copy(ReadableByteChannel readableByteChannel, WritableByteChannel writableByteChannel) throws IOException {
        Preconditions.checkNotNull(readableByteChannel);
        Preconditions.checkNotNull(writableByteChannel);
        long jWrite = 0;
        if (readableByteChannel instanceof FileChannel) {
            FileChannel fileChannel = (FileChannel) readableByteChannel;
            long jPosition = fileChannel.position();
            long j2 = jPosition;
            while (true) {
                long jTransferTo = fileChannel.transferTo(j2, PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED, writableByteChannel);
                j2 += jTransferTo;
                fileChannel.position(j2);
                if (jTransferTo <= 0 && j2 >= fileChannel.size()) {
                    return j2 - jPosition;
                }
            }
        } else {
            ByteBuffer byteBufferWrap = ByteBuffer.wrap(createBuffer());
            while (readableByteChannel.read(byteBufferWrap) != -1) {
                byteBufferWrap.flip();
                while (byteBufferWrap.hasRemaining()) {
                    jWrite += writableByteChannel.write(byteBufferWrap);
                }
                byteBufferWrap.clear();
            }
            return jWrite;
        }
    }
}
