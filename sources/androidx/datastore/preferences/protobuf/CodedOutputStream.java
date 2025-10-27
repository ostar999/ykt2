package androidx.datastore.preferences.protobuf;

import androidx.datastore.preferences.protobuf.Utf8;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.logging.Level;
import java.util.logging.Logger;

/* loaded from: classes.dex */
public abstract class CodedOutputStream extends ByteOutput {
    public static final int DEFAULT_BUFFER_SIZE = 4096;

    @Deprecated
    public static final int LITTLE_ENDIAN_32_SIZE = 4;
    private boolean serializationDeterministic;
    CodedOutputStreamWriter wrapper;
    private static final Logger logger = Logger.getLogger(CodedOutputStream.class.getName());
    private static final boolean HAS_UNSAFE_ARRAY_OPERATIONS = UnsafeUtil.hasUnsafeArrayOperations();

    public static abstract class AbstractBufferedEncoder extends CodedOutputStream {
        final byte[] buffer;
        final int limit;
        int position;
        int totalBytesWritten;

        public AbstractBufferedEncoder(int i2) {
            super();
            if (i2 < 0) {
                throw new IllegalArgumentException("bufferSize must be >= 0");
            }
            byte[] bArr = new byte[Math.max(i2, 20)];
            this.buffer = bArr;
            this.limit = bArr.length;
        }

        public final void buffer(byte b3) {
            byte[] bArr = this.buffer;
            int i2 = this.position;
            this.position = i2 + 1;
            bArr[i2] = b3;
            this.totalBytesWritten++;
        }

        public final void bufferFixed32NoTag(int i2) {
            byte[] bArr = this.buffer;
            int i3 = this.position;
            int i4 = i3 + 1;
            bArr[i3] = (byte) (i2 & 255);
            int i5 = i4 + 1;
            bArr[i4] = (byte) ((i2 >> 8) & 255);
            int i6 = i5 + 1;
            bArr[i5] = (byte) ((i2 >> 16) & 255);
            this.position = i6 + 1;
            bArr[i6] = (byte) ((i2 >> 24) & 255);
            this.totalBytesWritten += 4;
        }

        public final void bufferFixed64NoTag(long j2) {
            byte[] bArr = this.buffer;
            int i2 = this.position;
            int i3 = i2 + 1;
            bArr[i2] = (byte) (j2 & 255);
            int i4 = i3 + 1;
            bArr[i3] = (byte) ((j2 >> 8) & 255);
            int i5 = i4 + 1;
            bArr[i4] = (byte) ((j2 >> 16) & 255);
            int i6 = i5 + 1;
            bArr[i5] = (byte) (255 & (j2 >> 24));
            int i7 = i6 + 1;
            bArr[i6] = (byte) (((int) (j2 >> 32)) & 255);
            int i8 = i7 + 1;
            bArr[i7] = (byte) (((int) (j2 >> 40)) & 255);
            int i9 = i8 + 1;
            bArr[i8] = (byte) (((int) (j2 >> 48)) & 255);
            this.position = i9 + 1;
            bArr[i9] = (byte) (((int) (j2 >> 56)) & 255);
            this.totalBytesWritten += 8;
        }

        public final void bufferInt32NoTag(int i2) {
            if (i2 >= 0) {
                bufferUInt32NoTag(i2);
            } else {
                bufferUInt64NoTag(i2);
            }
        }

        public final void bufferTag(int i2, int i3) {
            bufferUInt32NoTag(WireFormat.makeTag(i2, i3));
        }

        public final void bufferUInt32NoTag(int i2) {
            if (!CodedOutputStream.HAS_UNSAFE_ARRAY_OPERATIONS) {
                while ((i2 & (-128)) != 0) {
                    byte[] bArr = this.buffer;
                    int i3 = this.position;
                    this.position = i3 + 1;
                    bArr[i3] = (byte) ((i2 & 127) | 128);
                    this.totalBytesWritten++;
                    i2 >>>= 7;
                }
                byte[] bArr2 = this.buffer;
                int i4 = this.position;
                this.position = i4 + 1;
                bArr2[i4] = (byte) i2;
                this.totalBytesWritten++;
                return;
            }
            long j2 = this.position;
            while ((i2 & (-128)) != 0) {
                byte[] bArr3 = this.buffer;
                int i5 = this.position;
                this.position = i5 + 1;
                UnsafeUtil.putByte(bArr3, i5, (byte) ((i2 & 127) | 128));
                i2 >>>= 7;
            }
            byte[] bArr4 = this.buffer;
            int i6 = this.position;
            this.position = i6 + 1;
            UnsafeUtil.putByte(bArr4, i6, (byte) i2);
            this.totalBytesWritten += (int) (this.position - j2);
        }

        public final void bufferUInt64NoTag(long j2) {
            if (!CodedOutputStream.HAS_UNSAFE_ARRAY_OPERATIONS) {
                while ((j2 & (-128)) != 0) {
                    byte[] bArr = this.buffer;
                    int i2 = this.position;
                    this.position = i2 + 1;
                    bArr[i2] = (byte) ((((int) j2) & 127) | 128);
                    this.totalBytesWritten++;
                    j2 >>>= 7;
                }
                byte[] bArr2 = this.buffer;
                int i3 = this.position;
                this.position = i3 + 1;
                bArr2[i3] = (byte) j2;
                this.totalBytesWritten++;
                return;
            }
            long j3 = this.position;
            while ((j2 & (-128)) != 0) {
                byte[] bArr3 = this.buffer;
                int i4 = this.position;
                this.position = i4 + 1;
                UnsafeUtil.putByte(bArr3, i4, (byte) ((((int) j2) & 127) | 128));
                j2 >>>= 7;
            }
            byte[] bArr4 = this.buffer;
            int i5 = this.position;
            this.position = i5 + 1;
            UnsafeUtil.putByte(bArr4, i5, (byte) j2);
            this.totalBytesWritten += (int) (this.position - j3);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public final int getTotalBytesWritten() {
            return this.totalBytesWritten;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public final int spaceLeft() {
            throw new UnsupportedOperationException("spaceLeft() can only be called on CodedOutputStreams that are writing to a flat array or ByteBuffer.");
        }
    }

    public static class ArrayEncoder extends CodedOutputStream {
        private final byte[] buffer;
        private final int limit;
        private final int offset;
        private int position;

        public ArrayEncoder(byte[] bArr, int i2, int i3) {
            super();
            if (bArr == null) {
                throw new NullPointerException("buffer");
            }
            int i4 = i2 + i3;
            if ((i2 | i3 | (bArr.length - i4)) < 0) {
                throw new IllegalArgumentException(String.format("Array range is invalid. Buffer.length=%d, offset=%d, length=%d", Integer.valueOf(bArr.length), Integer.valueOf(i2), Integer.valueOf(i3)));
            }
            this.buffer = bArr;
            this.offset = i2;
            this.position = i2;
            this.limit = i4;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void flush() {
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public final int getTotalBytesWritten() {
            return this.position - this.offset;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public final int spaceLeft() {
            return this.limit - this.position;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream, androidx.datastore.preferences.protobuf.ByteOutput
        public final void write(byte b3) throws IOException {
            try {
                byte[] bArr = this.buffer;
                int i2 = this.position;
                this.position = i2 + 1;
                bArr[i2] = b3;
            } catch (IndexOutOfBoundsException e2) {
                throw new OutOfSpaceException(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.position), Integer.valueOf(this.limit), 1), e2);
            }
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public final void writeBool(int i2, boolean z2) throws IOException {
            writeTag(i2, 0);
            write(z2 ? (byte) 1 : (byte) 0);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public final void writeByteArray(int i2, byte[] bArr) throws IOException {
            writeByteArray(i2, bArr, 0, bArr.length);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public final void writeByteArrayNoTag(byte[] bArr, int i2, int i3) throws IOException {
            writeUInt32NoTag(i3);
            write(bArr, i2, i3);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public final void writeByteBuffer(int i2, ByteBuffer byteBuffer) throws IOException {
            writeTag(i2, 2);
            writeUInt32NoTag(byteBuffer.capacity());
            writeRawBytes(byteBuffer);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public final void writeBytes(int i2, ByteString byteString) throws IOException {
            writeTag(i2, 2);
            writeBytesNoTag(byteString);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public final void writeBytesNoTag(ByteString byteString) throws IOException {
            writeUInt32NoTag(byteString.size());
            byteString.writeTo(this);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public final void writeFixed32(int i2, int i3) throws IOException {
            writeTag(i2, 5);
            writeFixed32NoTag(i3);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public final void writeFixed32NoTag(int i2) throws IOException {
            try {
                byte[] bArr = this.buffer;
                int i3 = this.position;
                int i4 = i3 + 1;
                bArr[i3] = (byte) (i2 & 255);
                int i5 = i4 + 1;
                bArr[i4] = (byte) ((i2 >> 8) & 255);
                int i6 = i5 + 1;
                bArr[i5] = (byte) ((i2 >> 16) & 255);
                this.position = i6 + 1;
                bArr[i6] = (byte) ((i2 >> 24) & 255);
            } catch (IndexOutOfBoundsException e2) {
                throw new OutOfSpaceException(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.position), Integer.valueOf(this.limit), 1), e2);
            }
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public final void writeFixed64(int i2, long j2) throws IOException {
            writeTag(i2, 1);
            writeFixed64NoTag(j2);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public final void writeFixed64NoTag(long j2) throws IOException {
            try {
                byte[] bArr = this.buffer;
                int i2 = this.position;
                int i3 = i2 + 1;
                bArr[i2] = (byte) (((int) j2) & 255);
                int i4 = i3 + 1;
                bArr[i3] = (byte) (((int) (j2 >> 8)) & 255);
                int i5 = i4 + 1;
                bArr[i4] = (byte) (((int) (j2 >> 16)) & 255);
                int i6 = i5 + 1;
                bArr[i5] = (byte) (((int) (j2 >> 24)) & 255);
                int i7 = i6 + 1;
                bArr[i6] = (byte) (((int) (j2 >> 32)) & 255);
                int i8 = i7 + 1;
                bArr[i7] = (byte) (((int) (j2 >> 40)) & 255);
                int i9 = i8 + 1;
                bArr[i8] = (byte) (((int) (j2 >> 48)) & 255);
                this.position = i9 + 1;
                bArr[i9] = (byte) (((int) (j2 >> 56)) & 255);
            } catch (IndexOutOfBoundsException e2) {
                throw new OutOfSpaceException(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.position), Integer.valueOf(this.limit), 1), e2);
            }
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public final void writeInt32(int i2, int i3) throws IOException {
            writeTag(i2, 0);
            writeInt32NoTag(i3);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public final void writeInt32NoTag(int i2) throws IOException {
            if (i2 >= 0) {
                writeUInt32NoTag(i2);
            } else {
                writeUInt64NoTag(i2);
            }
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream, androidx.datastore.preferences.protobuf.ByteOutput
        public final void writeLazy(byte[] bArr, int i2, int i3) throws IOException {
            write(bArr, i2, i3);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public final void writeMessage(int i2, MessageLite messageLite) throws IOException {
            writeTag(i2, 2);
            writeMessageNoTag(messageLite);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public final void writeMessageNoTag(MessageLite messageLite) throws IOException {
            writeUInt32NoTag(messageLite.getSerializedSize());
            messageLite.writeTo(this);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public final void writeMessageSetExtension(int i2, MessageLite messageLite) throws IOException {
            writeTag(1, 3);
            writeUInt32(2, i2);
            writeMessage(3, messageLite);
            writeTag(1, 4);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public final void writeRawBytes(ByteBuffer byteBuffer) throws IOException {
            if (byteBuffer.hasArray()) {
                write(byteBuffer.array(), byteBuffer.arrayOffset(), byteBuffer.capacity());
                return;
            }
            ByteBuffer byteBufferDuplicate = byteBuffer.duplicate();
            byteBufferDuplicate.clear();
            write(byteBufferDuplicate);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public final void writeRawMessageSetExtension(int i2, ByteString byteString) throws IOException {
            writeTag(1, 3);
            writeUInt32(2, i2);
            writeBytes(3, byteString);
            writeTag(1, 4);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public final void writeString(int i2, String str) throws IOException {
            writeTag(i2, 2);
            writeStringNoTag(str);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public final void writeStringNoTag(String str) throws IOException {
            int i2 = this.position;
            try {
                int iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(str.length() * 3);
                int iComputeUInt32SizeNoTag2 = CodedOutputStream.computeUInt32SizeNoTag(str.length());
                if (iComputeUInt32SizeNoTag2 == iComputeUInt32SizeNoTag) {
                    int i3 = i2 + iComputeUInt32SizeNoTag2;
                    this.position = i3;
                    int iEncode = Utf8.encode(str, this.buffer, i3, spaceLeft());
                    this.position = i2;
                    writeUInt32NoTag((iEncode - i2) - iComputeUInt32SizeNoTag2);
                    this.position = iEncode;
                } else {
                    writeUInt32NoTag(Utf8.encodedLength(str));
                    this.position = Utf8.encode(str, this.buffer, this.position, spaceLeft());
                }
            } catch (Utf8.UnpairedSurrogateException e2) {
                this.position = i2;
                inefficientWriteStringNoTag(str, e2);
            } catch (IndexOutOfBoundsException e3) {
                throw new OutOfSpaceException(e3);
            }
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public final void writeTag(int i2, int i3) throws IOException {
            writeUInt32NoTag(WireFormat.makeTag(i2, i3));
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public final void writeUInt32(int i2, int i3) throws IOException {
            writeTag(i2, 0);
            writeUInt32NoTag(i3);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public final void writeUInt32NoTag(int i2) throws IOException {
            if (!CodedOutputStream.HAS_UNSAFE_ARRAY_OPERATIONS || Android.isOnAndroidDevice() || spaceLeft() < 5) {
                while ((i2 & (-128)) != 0) {
                    try {
                        byte[] bArr = this.buffer;
                        int i3 = this.position;
                        this.position = i3 + 1;
                        bArr[i3] = (byte) ((i2 & 127) | 128);
                        i2 >>>= 7;
                    } catch (IndexOutOfBoundsException e2) {
                        throw new OutOfSpaceException(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.position), Integer.valueOf(this.limit), 1), e2);
                    }
                }
                byte[] bArr2 = this.buffer;
                int i4 = this.position;
                this.position = i4 + 1;
                bArr2[i4] = (byte) i2;
                return;
            }
            if ((i2 & (-128)) == 0) {
                byte[] bArr3 = this.buffer;
                int i5 = this.position;
                this.position = i5 + 1;
                UnsafeUtil.putByte(bArr3, i5, (byte) i2);
                return;
            }
            byte[] bArr4 = this.buffer;
            int i6 = this.position;
            this.position = i6 + 1;
            UnsafeUtil.putByte(bArr4, i6, (byte) (i2 | 128));
            int i7 = i2 >>> 7;
            if ((i7 & (-128)) == 0) {
                byte[] bArr5 = this.buffer;
                int i8 = this.position;
                this.position = i8 + 1;
                UnsafeUtil.putByte(bArr5, i8, (byte) i7);
                return;
            }
            byte[] bArr6 = this.buffer;
            int i9 = this.position;
            this.position = i9 + 1;
            UnsafeUtil.putByte(bArr6, i9, (byte) (i7 | 128));
            int i10 = i7 >>> 7;
            if ((i10 & (-128)) == 0) {
                byte[] bArr7 = this.buffer;
                int i11 = this.position;
                this.position = i11 + 1;
                UnsafeUtil.putByte(bArr7, i11, (byte) i10);
                return;
            }
            byte[] bArr8 = this.buffer;
            int i12 = this.position;
            this.position = i12 + 1;
            UnsafeUtil.putByte(bArr8, i12, (byte) (i10 | 128));
            int i13 = i10 >>> 7;
            if ((i13 & (-128)) == 0) {
                byte[] bArr9 = this.buffer;
                int i14 = this.position;
                this.position = i14 + 1;
                UnsafeUtil.putByte(bArr9, i14, (byte) i13);
                return;
            }
            byte[] bArr10 = this.buffer;
            int i15 = this.position;
            this.position = i15 + 1;
            UnsafeUtil.putByte(bArr10, i15, (byte) (i13 | 128));
            byte[] bArr11 = this.buffer;
            int i16 = this.position;
            this.position = i16 + 1;
            UnsafeUtil.putByte(bArr11, i16, (byte) (i13 >>> 7));
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public final void writeUInt64(int i2, long j2) throws IOException {
            writeTag(i2, 0);
            writeUInt64NoTag(j2);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public final void writeUInt64NoTag(long j2) throws IOException {
            if (CodedOutputStream.HAS_UNSAFE_ARRAY_OPERATIONS && spaceLeft() >= 10) {
                while ((j2 & (-128)) != 0) {
                    byte[] bArr = this.buffer;
                    int i2 = this.position;
                    this.position = i2 + 1;
                    UnsafeUtil.putByte(bArr, i2, (byte) ((((int) j2) & 127) | 128));
                    j2 >>>= 7;
                }
                byte[] bArr2 = this.buffer;
                int i3 = this.position;
                this.position = i3 + 1;
                UnsafeUtil.putByte(bArr2, i3, (byte) j2);
                return;
            }
            while ((j2 & (-128)) != 0) {
                try {
                    byte[] bArr3 = this.buffer;
                    int i4 = this.position;
                    this.position = i4 + 1;
                    bArr3[i4] = (byte) ((((int) j2) & 127) | 128);
                    j2 >>>= 7;
                } catch (IndexOutOfBoundsException e2) {
                    throw new OutOfSpaceException(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.position), Integer.valueOf(this.limit), 1), e2);
                }
            }
            byte[] bArr4 = this.buffer;
            int i5 = this.position;
            this.position = i5 + 1;
            bArr4[i5] = (byte) j2;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public final void writeByteArray(int i2, byte[] bArr, int i3, int i4) throws IOException {
            writeTag(i2, 2);
            writeByteArrayNoTag(bArr, i3, i4);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream, androidx.datastore.preferences.protobuf.ByteOutput
        public final void writeLazy(ByteBuffer byteBuffer) throws IOException {
            write(byteBuffer);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public final void writeMessage(int i2, MessageLite messageLite, Schema schema) throws IOException {
            writeTag(i2, 2);
            writeUInt32NoTag(((AbstractMessageLite) messageLite).getSerializedSize(schema));
            schema.writeTo(messageLite, this.wrapper);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public final void writeMessageNoTag(MessageLite messageLite, Schema schema) throws IOException {
            writeUInt32NoTag(((AbstractMessageLite) messageLite).getSerializedSize(schema));
            schema.writeTo(messageLite, this.wrapper);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream, androidx.datastore.preferences.protobuf.ByteOutput
        public final void write(byte[] bArr, int i2, int i3) throws IOException {
            try {
                System.arraycopy(bArr, i2, this.buffer, this.position, i3);
                this.position += i3;
            } catch (IndexOutOfBoundsException e2) {
                throw new OutOfSpaceException(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.position), Integer.valueOf(this.limit), Integer.valueOf(i3)), e2);
            }
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream, androidx.datastore.preferences.protobuf.ByteOutput
        public final void write(ByteBuffer byteBuffer) throws IOException {
            int iRemaining = byteBuffer.remaining();
            try {
                byteBuffer.get(this.buffer, this.position, iRemaining);
                this.position += iRemaining;
            } catch (IndexOutOfBoundsException e2) {
                throw new OutOfSpaceException(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.position), Integer.valueOf(this.limit), Integer.valueOf(iRemaining)), e2);
            }
        }
    }

    public static final class ByteOutputEncoder extends AbstractBufferedEncoder {
        private final ByteOutput out;

        public ByteOutputEncoder(ByteOutput byteOutput, int i2) {
            super(i2);
            if (byteOutput == null) {
                throw new NullPointerException("out");
            }
            this.out = byteOutput;
        }

        private void doFlush() throws IOException {
            this.out.write(this.buffer, 0, this.position);
            this.position = 0;
        }

        private void flushIfNotAvailable(int i2) throws IOException {
            if (this.limit - this.position < i2) {
                doFlush();
            }
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void flush() throws IOException {
            if (this.position > 0) {
                doFlush();
            }
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream, androidx.datastore.preferences.protobuf.ByteOutput
        public void write(byte b3) throws IOException {
            if (this.position == this.limit) {
                doFlush();
            }
            buffer(b3);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeBool(int i2, boolean z2) throws IOException {
            flushIfNotAvailable(11);
            bufferTag(i2, 0);
            buffer(z2 ? (byte) 1 : (byte) 0);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeByteArray(int i2, byte[] bArr) throws IOException {
            writeByteArray(i2, bArr, 0, bArr.length);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeByteArrayNoTag(byte[] bArr, int i2, int i3) throws IOException {
            writeUInt32NoTag(i3);
            write(bArr, i2, i3);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeByteBuffer(int i2, ByteBuffer byteBuffer) throws IOException {
            writeTag(i2, 2);
            writeUInt32NoTag(byteBuffer.capacity());
            writeRawBytes(byteBuffer);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeBytes(int i2, ByteString byteString) throws IOException {
            writeTag(i2, 2);
            writeBytesNoTag(byteString);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeBytesNoTag(ByteString byteString) throws IOException {
            writeUInt32NoTag(byteString.size());
            byteString.writeTo(this);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeFixed32(int i2, int i3) throws IOException {
            flushIfNotAvailable(14);
            bufferTag(i2, 5);
            bufferFixed32NoTag(i3);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeFixed32NoTag(int i2) throws IOException {
            flushIfNotAvailable(4);
            bufferFixed32NoTag(i2);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeFixed64(int i2, long j2) throws IOException {
            flushIfNotAvailable(18);
            bufferTag(i2, 1);
            bufferFixed64NoTag(j2);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeFixed64NoTag(long j2) throws IOException {
            flushIfNotAvailable(8);
            bufferFixed64NoTag(j2);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeInt32(int i2, int i3) throws IOException {
            flushIfNotAvailable(20);
            bufferTag(i2, 0);
            bufferInt32NoTag(i3);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeInt32NoTag(int i2) throws IOException {
            if (i2 >= 0) {
                writeUInt32NoTag(i2);
            } else {
                writeUInt64NoTag(i2);
            }
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream, androidx.datastore.preferences.protobuf.ByteOutput
        public void writeLazy(byte[] bArr, int i2, int i3) throws IOException {
            flush();
            this.out.writeLazy(bArr, i2, i3);
            this.totalBytesWritten += i3;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeMessage(int i2, MessageLite messageLite) throws IOException {
            writeTag(i2, 2);
            writeMessageNoTag(messageLite);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeMessageNoTag(MessageLite messageLite) throws IOException {
            writeUInt32NoTag(messageLite.getSerializedSize());
            messageLite.writeTo(this);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeMessageSetExtension(int i2, MessageLite messageLite) throws IOException {
            writeTag(1, 3);
            writeUInt32(2, i2);
            writeMessage(3, messageLite);
            writeTag(1, 4);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeRawBytes(ByteBuffer byteBuffer) throws IOException {
            if (byteBuffer.hasArray()) {
                write(byteBuffer.array(), byteBuffer.arrayOffset(), byteBuffer.capacity());
                return;
            }
            ByteBuffer byteBufferDuplicate = byteBuffer.duplicate();
            byteBufferDuplicate.clear();
            write(byteBufferDuplicate);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeRawMessageSetExtension(int i2, ByteString byteString) throws IOException {
            writeTag(1, 3);
            writeUInt32(2, i2);
            writeBytes(3, byteString);
            writeTag(1, 4);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeString(int i2, String str) throws IOException {
            writeTag(i2, 2);
            writeStringNoTag(str);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeStringNoTag(String str) throws IOException {
            int length = str.length() * 3;
            int iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(length);
            int i2 = iComputeUInt32SizeNoTag + length;
            int i3 = this.limit;
            if (i2 > i3) {
                byte[] bArr = new byte[length];
                int iEncode = Utf8.encode(str, bArr, 0, length);
                writeUInt32NoTag(iEncode);
                writeLazy(bArr, 0, iEncode);
                return;
            }
            if (i2 > i3 - this.position) {
                doFlush();
            }
            int i4 = this.position;
            try {
                int iComputeUInt32SizeNoTag2 = CodedOutputStream.computeUInt32SizeNoTag(str.length());
                if (iComputeUInt32SizeNoTag2 == iComputeUInt32SizeNoTag) {
                    int i5 = i4 + iComputeUInt32SizeNoTag2;
                    this.position = i5;
                    int iEncode2 = Utf8.encode(str, this.buffer, i5, this.limit - i5);
                    this.position = i4;
                    int i6 = (iEncode2 - i4) - iComputeUInt32SizeNoTag2;
                    bufferUInt32NoTag(i6);
                    this.position = iEncode2;
                    this.totalBytesWritten += i6;
                } else {
                    int iEncodedLength = Utf8.encodedLength(str);
                    bufferUInt32NoTag(iEncodedLength);
                    this.position = Utf8.encode(str, this.buffer, this.position, iEncodedLength);
                    this.totalBytesWritten += iEncodedLength;
                }
            } catch (Utf8.UnpairedSurrogateException e2) {
                this.totalBytesWritten -= this.position - i4;
                this.position = i4;
                inefficientWriteStringNoTag(str, e2);
            } catch (IndexOutOfBoundsException e3) {
                throw new OutOfSpaceException(e3);
            }
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeTag(int i2, int i3) throws IOException {
            writeUInt32NoTag(WireFormat.makeTag(i2, i3));
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeUInt32(int i2, int i3) throws IOException {
            flushIfNotAvailable(20);
            bufferTag(i2, 0);
            bufferUInt32NoTag(i3);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeUInt32NoTag(int i2) throws IOException {
            flushIfNotAvailable(5);
            bufferUInt32NoTag(i2);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeUInt64(int i2, long j2) throws IOException {
            flushIfNotAvailable(20);
            bufferTag(i2, 0);
            bufferUInt64NoTag(j2);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeUInt64NoTag(long j2) throws IOException {
            flushIfNotAvailable(10);
            bufferUInt64NoTag(j2);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeByteArray(int i2, byte[] bArr, int i3, int i4) throws IOException {
            writeTag(i2, 2);
            writeByteArrayNoTag(bArr, i3, i4);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeMessage(int i2, MessageLite messageLite, Schema schema) throws IOException {
            writeTag(i2, 2);
            writeMessageNoTag(messageLite, schema);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeMessageNoTag(MessageLite messageLite, Schema schema) throws IOException {
            writeUInt32NoTag(((AbstractMessageLite) messageLite).getSerializedSize(schema));
            schema.writeTo(messageLite, this.wrapper);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream, androidx.datastore.preferences.protobuf.ByteOutput
        public void write(byte[] bArr, int i2, int i3) throws IOException {
            flush();
            this.out.write(bArr, i2, i3);
            this.totalBytesWritten += i3;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream, androidx.datastore.preferences.protobuf.ByteOutput
        public void writeLazy(ByteBuffer byteBuffer) throws IOException {
            flush();
            int iRemaining = byteBuffer.remaining();
            this.out.writeLazy(byteBuffer);
            this.totalBytesWritten += iRemaining;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream, androidx.datastore.preferences.protobuf.ByteOutput
        public void write(ByteBuffer byteBuffer) throws IOException {
            flush();
            int iRemaining = byteBuffer.remaining();
            this.out.write(byteBuffer);
            this.totalBytesWritten += iRemaining;
        }
    }

    public static final class HeapNioEncoder extends ArrayEncoder {
        private final ByteBuffer byteBuffer;
        private int initialPosition;

        public HeapNioEncoder(ByteBuffer byteBuffer) {
            super(byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), byteBuffer.remaining());
            this.byteBuffer = byteBuffer;
            this.initialPosition = byteBuffer.position();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream.ArrayEncoder, androidx.datastore.preferences.protobuf.CodedOutputStream
        public void flush() {
            this.byteBuffer.position(this.initialPosition + getTotalBytesWritten());
        }
    }

    public static class OutOfSpaceException extends IOException {
        private static final String MESSAGE = "CodedOutputStream was writing to a flat byte array and ran out of space.";
        private static final long serialVersionUID = -6947486886997889499L;

        public OutOfSpaceException() {
            super(MESSAGE);
        }

        public OutOfSpaceException(String str) {
            super("CodedOutputStream was writing to a flat byte array and ran out of space.: " + str);
        }

        public OutOfSpaceException(Throwable th) {
            super(MESSAGE, th);
        }

        public OutOfSpaceException(String str, Throwable th) {
            super("CodedOutputStream was writing to a flat byte array and ran out of space.: " + str, th);
        }
    }

    public static final class OutputStreamEncoder extends AbstractBufferedEncoder {
        private final OutputStream out;

        public OutputStreamEncoder(OutputStream outputStream, int i2) {
            super(i2);
            if (outputStream == null) {
                throw new NullPointerException("out");
            }
            this.out = outputStream;
        }

        private void doFlush() throws IOException {
            this.out.write(this.buffer, 0, this.position);
            this.position = 0;
        }

        private void flushIfNotAvailable(int i2) throws IOException {
            if (this.limit - this.position < i2) {
                doFlush();
            }
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void flush() throws IOException {
            if (this.position > 0) {
                doFlush();
            }
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream, androidx.datastore.preferences.protobuf.ByteOutput
        public void write(byte b3) throws IOException {
            if (this.position == this.limit) {
                doFlush();
            }
            buffer(b3);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeBool(int i2, boolean z2) throws IOException {
            flushIfNotAvailable(11);
            bufferTag(i2, 0);
            buffer(z2 ? (byte) 1 : (byte) 0);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeByteArray(int i2, byte[] bArr) throws IOException {
            writeByteArray(i2, bArr, 0, bArr.length);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeByteArrayNoTag(byte[] bArr, int i2, int i3) throws IOException {
            writeUInt32NoTag(i3);
            write(bArr, i2, i3);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeByteBuffer(int i2, ByteBuffer byteBuffer) throws IOException {
            writeTag(i2, 2);
            writeUInt32NoTag(byteBuffer.capacity());
            writeRawBytes(byteBuffer);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeBytes(int i2, ByteString byteString) throws IOException {
            writeTag(i2, 2);
            writeBytesNoTag(byteString);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeBytesNoTag(ByteString byteString) throws IOException {
            writeUInt32NoTag(byteString.size());
            byteString.writeTo(this);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeFixed32(int i2, int i3) throws IOException {
            flushIfNotAvailable(14);
            bufferTag(i2, 5);
            bufferFixed32NoTag(i3);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeFixed32NoTag(int i2) throws IOException {
            flushIfNotAvailable(4);
            bufferFixed32NoTag(i2);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeFixed64(int i2, long j2) throws IOException {
            flushIfNotAvailable(18);
            bufferTag(i2, 1);
            bufferFixed64NoTag(j2);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeFixed64NoTag(long j2) throws IOException {
            flushIfNotAvailable(8);
            bufferFixed64NoTag(j2);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeInt32(int i2, int i3) throws IOException {
            flushIfNotAvailable(20);
            bufferTag(i2, 0);
            bufferInt32NoTag(i3);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeInt32NoTag(int i2) throws IOException {
            if (i2 >= 0) {
                writeUInt32NoTag(i2);
            } else {
                writeUInt64NoTag(i2);
            }
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream, androidx.datastore.preferences.protobuf.ByteOutput
        public void writeLazy(byte[] bArr, int i2, int i3) throws IOException {
            write(bArr, i2, i3);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeMessage(int i2, MessageLite messageLite) throws IOException {
            writeTag(i2, 2);
            writeMessageNoTag(messageLite);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeMessageNoTag(MessageLite messageLite) throws IOException {
            writeUInt32NoTag(messageLite.getSerializedSize());
            messageLite.writeTo(this);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeMessageSetExtension(int i2, MessageLite messageLite) throws IOException {
            writeTag(1, 3);
            writeUInt32(2, i2);
            writeMessage(3, messageLite);
            writeTag(1, 4);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeRawBytes(ByteBuffer byteBuffer) throws IOException {
            if (byteBuffer.hasArray()) {
                write(byteBuffer.array(), byteBuffer.arrayOffset(), byteBuffer.capacity());
                return;
            }
            ByteBuffer byteBufferDuplicate = byteBuffer.duplicate();
            byteBufferDuplicate.clear();
            write(byteBufferDuplicate);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeRawMessageSetExtension(int i2, ByteString byteString) throws IOException {
            writeTag(1, 3);
            writeUInt32(2, i2);
            writeBytes(3, byteString);
            writeTag(1, 4);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeString(int i2, String str) throws IOException {
            writeTag(i2, 2);
            writeStringNoTag(str);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeStringNoTag(String str) throws IOException {
            int iEncodedLength;
            try {
                int length = str.length() * 3;
                int iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(length);
                int i2 = iComputeUInt32SizeNoTag + length;
                int i3 = this.limit;
                if (i2 > i3) {
                    byte[] bArr = new byte[length];
                    int iEncode = Utf8.encode(str, bArr, 0, length);
                    writeUInt32NoTag(iEncode);
                    writeLazy(bArr, 0, iEncode);
                    return;
                }
                if (i2 > i3 - this.position) {
                    doFlush();
                }
                int iComputeUInt32SizeNoTag2 = CodedOutputStream.computeUInt32SizeNoTag(str.length());
                int i4 = this.position;
                try {
                    if (iComputeUInt32SizeNoTag2 == iComputeUInt32SizeNoTag) {
                        int i5 = i4 + iComputeUInt32SizeNoTag2;
                        this.position = i5;
                        int iEncode2 = Utf8.encode(str, this.buffer, i5, this.limit - i5);
                        this.position = i4;
                        iEncodedLength = (iEncode2 - i4) - iComputeUInt32SizeNoTag2;
                        bufferUInt32NoTag(iEncodedLength);
                        this.position = iEncode2;
                    } else {
                        iEncodedLength = Utf8.encodedLength(str);
                        bufferUInt32NoTag(iEncodedLength);
                        this.position = Utf8.encode(str, this.buffer, this.position, iEncodedLength);
                    }
                    this.totalBytesWritten += iEncodedLength;
                } catch (Utf8.UnpairedSurrogateException e2) {
                    this.totalBytesWritten -= this.position - i4;
                    this.position = i4;
                    throw e2;
                } catch (ArrayIndexOutOfBoundsException e3) {
                    throw new OutOfSpaceException(e3);
                }
            } catch (Utf8.UnpairedSurrogateException e4) {
                inefficientWriteStringNoTag(str, e4);
            }
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeTag(int i2, int i3) throws IOException {
            writeUInt32NoTag(WireFormat.makeTag(i2, i3));
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeUInt32(int i2, int i3) throws IOException {
            flushIfNotAvailable(20);
            bufferTag(i2, 0);
            bufferUInt32NoTag(i3);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeUInt32NoTag(int i2) throws IOException {
            flushIfNotAvailable(5);
            bufferUInt32NoTag(i2);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeUInt64(int i2, long j2) throws IOException {
            flushIfNotAvailable(20);
            bufferTag(i2, 0);
            bufferUInt64NoTag(j2);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeUInt64NoTag(long j2) throws IOException {
            flushIfNotAvailable(10);
            bufferUInt64NoTag(j2);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeByteArray(int i2, byte[] bArr, int i3, int i4) throws IOException {
            writeTag(i2, 2);
            writeByteArrayNoTag(bArr, i3, i4);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream, androidx.datastore.preferences.protobuf.ByteOutput
        public void writeLazy(ByteBuffer byteBuffer) throws IOException {
            write(byteBuffer);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeMessage(int i2, MessageLite messageLite, Schema schema) throws IOException {
            writeTag(i2, 2);
            writeMessageNoTag(messageLite, schema);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeMessageNoTag(MessageLite messageLite, Schema schema) throws IOException {
            writeUInt32NoTag(((AbstractMessageLite) messageLite).getSerializedSize(schema));
            schema.writeTo(messageLite, this.wrapper);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream, androidx.datastore.preferences.protobuf.ByteOutput
        public void write(byte[] bArr, int i2, int i3) throws IOException {
            int i4 = this.limit;
            int i5 = this.position;
            if (i4 - i5 >= i3) {
                System.arraycopy(bArr, i2, this.buffer, i5, i3);
                this.position += i3;
                this.totalBytesWritten += i3;
                return;
            }
            int i6 = i4 - i5;
            System.arraycopy(bArr, i2, this.buffer, i5, i6);
            int i7 = i2 + i6;
            int i8 = i3 - i6;
            this.position = this.limit;
            this.totalBytesWritten += i6;
            doFlush();
            if (i8 <= this.limit) {
                System.arraycopy(bArr, i7, this.buffer, 0, i8);
                this.position = i8;
            } else {
                this.out.write(bArr, i7, i8);
            }
            this.totalBytesWritten += i8;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream, androidx.datastore.preferences.protobuf.ByteOutput
        public void write(ByteBuffer byteBuffer) throws IOException {
            int iRemaining = byteBuffer.remaining();
            int i2 = this.limit;
            int i3 = this.position;
            if (i2 - i3 >= iRemaining) {
                byteBuffer.get(this.buffer, i3, iRemaining);
                this.position += iRemaining;
                this.totalBytesWritten += iRemaining;
                return;
            }
            int i4 = i2 - i3;
            byteBuffer.get(this.buffer, i3, i4);
            int i5 = iRemaining - i4;
            this.position = this.limit;
            this.totalBytesWritten += i4;
            doFlush();
            while (true) {
                int i6 = this.limit;
                if (i5 > i6) {
                    byteBuffer.get(this.buffer, 0, i6);
                    this.out.write(this.buffer, 0, this.limit);
                    int i7 = this.limit;
                    i5 -= i7;
                    this.totalBytesWritten += i7;
                } else {
                    byteBuffer.get(this.buffer, 0, i5);
                    this.position = i5;
                    this.totalBytesWritten += i5;
                    return;
                }
            }
        }
    }

    public static final class SafeDirectNioEncoder extends CodedOutputStream {
        private final ByteBuffer buffer;
        private final int initialPosition;
        private final ByteBuffer originalBuffer;

        public SafeDirectNioEncoder(ByteBuffer byteBuffer) {
            super();
            this.originalBuffer = byteBuffer;
            this.buffer = byteBuffer.duplicate().order(ByteOrder.LITTLE_ENDIAN);
            this.initialPosition = byteBuffer.position();
        }

        private void encode(String str) throws IOException {
            try {
                Utf8.encodeUtf8(str, this.buffer);
            } catch (IndexOutOfBoundsException e2) {
                throw new OutOfSpaceException(e2);
            }
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void flush() {
            this.originalBuffer.position(this.buffer.position());
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public int getTotalBytesWritten() {
            return this.buffer.position() - this.initialPosition;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public int spaceLeft() {
            return this.buffer.remaining();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream, androidx.datastore.preferences.protobuf.ByteOutput
        public void write(byte b3) throws IOException {
            try {
                this.buffer.put(b3);
            } catch (BufferOverflowException e2) {
                throw new OutOfSpaceException(e2);
            }
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeBool(int i2, boolean z2) throws IOException {
            writeTag(i2, 0);
            write(z2 ? (byte) 1 : (byte) 0);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeByteArray(int i2, byte[] bArr) throws IOException {
            writeByteArray(i2, bArr, 0, bArr.length);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeByteArrayNoTag(byte[] bArr, int i2, int i3) throws IOException {
            writeUInt32NoTag(i3);
            write(bArr, i2, i3);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeByteBuffer(int i2, ByteBuffer byteBuffer) throws IOException {
            writeTag(i2, 2);
            writeUInt32NoTag(byteBuffer.capacity());
            writeRawBytes(byteBuffer);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeBytes(int i2, ByteString byteString) throws IOException {
            writeTag(i2, 2);
            writeBytesNoTag(byteString);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeBytesNoTag(ByteString byteString) throws IOException {
            writeUInt32NoTag(byteString.size());
            byteString.writeTo(this);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeFixed32(int i2, int i3) throws IOException {
            writeTag(i2, 5);
            writeFixed32NoTag(i3);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeFixed32NoTag(int i2) throws IOException {
            try {
                this.buffer.putInt(i2);
            } catch (BufferOverflowException e2) {
                throw new OutOfSpaceException(e2);
            }
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeFixed64(int i2, long j2) throws IOException {
            writeTag(i2, 1);
            writeFixed64NoTag(j2);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeFixed64NoTag(long j2) throws IOException {
            try {
                this.buffer.putLong(j2);
            } catch (BufferOverflowException e2) {
                throw new OutOfSpaceException(e2);
            }
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeInt32(int i2, int i3) throws IOException {
            writeTag(i2, 0);
            writeInt32NoTag(i3);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeInt32NoTag(int i2) throws IOException {
            if (i2 >= 0) {
                writeUInt32NoTag(i2);
            } else {
                writeUInt64NoTag(i2);
            }
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream, androidx.datastore.preferences.protobuf.ByteOutput
        public void writeLazy(byte[] bArr, int i2, int i3) throws IOException {
            write(bArr, i2, i3);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeMessage(int i2, MessageLite messageLite) throws IOException {
            writeTag(i2, 2);
            writeMessageNoTag(messageLite);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeMessageNoTag(MessageLite messageLite) throws IOException {
            writeUInt32NoTag(messageLite.getSerializedSize());
            messageLite.writeTo(this);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeMessageSetExtension(int i2, MessageLite messageLite) throws IOException {
            writeTag(1, 3);
            writeUInt32(2, i2);
            writeMessage(3, messageLite);
            writeTag(1, 4);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeRawBytes(ByteBuffer byteBuffer) throws IOException {
            if (byteBuffer.hasArray()) {
                write(byteBuffer.array(), byteBuffer.arrayOffset(), byteBuffer.capacity());
                return;
            }
            ByteBuffer byteBufferDuplicate = byteBuffer.duplicate();
            byteBufferDuplicate.clear();
            write(byteBufferDuplicate);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeRawMessageSetExtension(int i2, ByteString byteString) throws IOException {
            writeTag(1, 3);
            writeUInt32(2, i2);
            writeBytes(3, byteString);
            writeTag(1, 4);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeString(int i2, String str) throws IOException {
            writeTag(i2, 2);
            writeStringNoTag(str);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeStringNoTag(String str) throws IOException {
            int iPosition = this.buffer.position();
            try {
                int iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(str.length() * 3);
                int iComputeUInt32SizeNoTag2 = CodedOutputStream.computeUInt32SizeNoTag(str.length());
                if (iComputeUInt32SizeNoTag2 == iComputeUInt32SizeNoTag) {
                    int iPosition2 = this.buffer.position() + iComputeUInt32SizeNoTag2;
                    this.buffer.position(iPosition2);
                    encode(str);
                    int iPosition3 = this.buffer.position();
                    this.buffer.position(iPosition);
                    writeUInt32NoTag(iPosition3 - iPosition2);
                    this.buffer.position(iPosition3);
                } else {
                    writeUInt32NoTag(Utf8.encodedLength(str));
                    encode(str);
                }
            } catch (Utf8.UnpairedSurrogateException e2) {
                this.buffer.position(iPosition);
                inefficientWriteStringNoTag(str, e2);
            } catch (IllegalArgumentException e3) {
                throw new OutOfSpaceException(e3);
            }
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeTag(int i2, int i3) throws IOException {
            writeUInt32NoTag(WireFormat.makeTag(i2, i3));
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeUInt32(int i2, int i3) throws IOException {
            writeTag(i2, 0);
            writeUInt32NoTag(i3);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeUInt32NoTag(int i2) throws IOException {
            while ((i2 & (-128)) != 0) {
                try {
                    this.buffer.put((byte) ((i2 & 127) | 128));
                    i2 >>>= 7;
                } catch (BufferOverflowException e2) {
                    throw new OutOfSpaceException(e2);
                }
            }
            this.buffer.put((byte) i2);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeUInt64(int i2, long j2) throws IOException {
            writeTag(i2, 0);
            writeUInt64NoTag(j2);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeUInt64NoTag(long j2) throws IOException {
            while (((-128) & j2) != 0) {
                try {
                    this.buffer.put((byte) ((((int) j2) & 127) | 128));
                    j2 >>>= 7;
                } catch (BufferOverflowException e2) {
                    throw new OutOfSpaceException(e2);
                }
            }
            this.buffer.put((byte) j2);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeByteArray(int i2, byte[] bArr, int i3, int i4) throws IOException {
            writeTag(i2, 2);
            writeByteArrayNoTag(bArr, i3, i4);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream, androidx.datastore.preferences.protobuf.ByteOutput
        public void writeLazy(ByteBuffer byteBuffer) throws IOException {
            write(byteBuffer);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream, androidx.datastore.preferences.protobuf.ByteOutput
        public void write(byte[] bArr, int i2, int i3) throws IOException {
            try {
                this.buffer.put(bArr, i2, i3);
            } catch (IndexOutOfBoundsException e2) {
                throw new OutOfSpaceException(e2);
            } catch (BufferOverflowException e3) {
                throw new OutOfSpaceException(e3);
            }
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeMessage(int i2, MessageLite messageLite, Schema schema) throws IOException {
            writeTag(i2, 2);
            writeMessageNoTag(messageLite, schema);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeMessageNoTag(MessageLite messageLite, Schema schema) throws IOException {
            writeUInt32NoTag(((AbstractMessageLite) messageLite).getSerializedSize(schema));
            schema.writeTo(messageLite, this.wrapper);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream, androidx.datastore.preferences.protobuf.ByteOutput
        public void write(ByteBuffer byteBuffer) throws IOException {
            try {
                this.buffer.put(byteBuffer);
            } catch (BufferOverflowException e2) {
                throw new OutOfSpaceException(e2);
            }
        }
    }

    public static final class UnsafeDirectNioEncoder extends CodedOutputStream {
        private final long address;
        private final ByteBuffer buffer;
        private final long initialPosition;
        private final long limit;
        private final long oneVarintLimit;
        private final ByteBuffer originalBuffer;
        private long position;

        public UnsafeDirectNioEncoder(ByteBuffer byteBuffer) {
            super();
            this.originalBuffer = byteBuffer;
            this.buffer = byteBuffer.duplicate().order(ByteOrder.LITTLE_ENDIAN);
            long jAddressOffset = UnsafeUtil.addressOffset(byteBuffer);
            this.address = jAddressOffset;
            long jPosition = byteBuffer.position() + jAddressOffset;
            this.initialPosition = jPosition;
            long jLimit = jAddressOffset + byteBuffer.limit();
            this.limit = jLimit;
            this.oneVarintLimit = jLimit - 10;
            this.position = jPosition;
        }

        private int bufferPos(long j2) {
            return (int) (j2 - this.address);
        }

        public static boolean isSupported() {
            return UnsafeUtil.hasUnsafeByteBufferOperations();
        }

        private void repositionBuffer(long j2) {
            this.buffer.position(bufferPos(j2));
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void flush() {
            this.originalBuffer.position(bufferPos(this.position));
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public int getTotalBytesWritten() {
            return (int) (this.position - this.initialPosition);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public int spaceLeft() {
            return (int) (this.limit - this.position);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream, androidx.datastore.preferences.protobuf.ByteOutput
        public void write(byte b3) throws IOException {
            long j2 = this.position;
            if (j2 >= this.limit) {
                throw new OutOfSpaceException(String.format("Pos: %d, limit: %d, len: %d", Long.valueOf(this.position), Long.valueOf(this.limit), 1));
            }
            this.position = 1 + j2;
            UnsafeUtil.putByte(j2, b3);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeBool(int i2, boolean z2) throws IOException {
            writeTag(i2, 0);
            write(z2 ? (byte) 1 : (byte) 0);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeByteArray(int i2, byte[] bArr) throws IOException {
            writeByteArray(i2, bArr, 0, bArr.length);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeByteArrayNoTag(byte[] bArr, int i2, int i3) throws IOException {
            writeUInt32NoTag(i3);
            write(bArr, i2, i3);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeByteBuffer(int i2, ByteBuffer byteBuffer) throws IOException {
            writeTag(i2, 2);
            writeUInt32NoTag(byteBuffer.capacity());
            writeRawBytes(byteBuffer);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeBytes(int i2, ByteString byteString) throws IOException {
            writeTag(i2, 2);
            writeBytesNoTag(byteString);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeBytesNoTag(ByteString byteString) throws IOException {
            writeUInt32NoTag(byteString.size());
            byteString.writeTo(this);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeFixed32(int i2, int i3) throws IOException {
            writeTag(i2, 5);
            writeFixed32NoTag(i3);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeFixed32NoTag(int i2) throws IOException {
            this.buffer.putInt(bufferPos(this.position), i2);
            this.position += 4;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeFixed64(int i2, long j2) throws IOException {
            writeTag(i2, 1);
            writeFixed64NoTag(j2);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeFixed64NoTag(long j2) throws IOException {
            this.buffer.putLong(bufferPos(this.position), j2);
            this.position += 8;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeInt32(int i2, int i3) throws IOException {
            writeTag(i2, 0);
            writeInt32NoTag(i3);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeInt32NoTag(int i2) throws IOException {
            if (i2 >= 0) {
                writeUInt32NoTag(i2);
            } else {
                writeUInt64NoTag(i2);
            }
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream, androidx.datastore.preferences.protobuf.ByteOutput
        public void writeLazy(byte[] bArr, int i2, int i3) throws IOException {
            write(bArr, i2, i3);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeMessage(int i2, MessageLite messageLite) throws IOException {
            writeTag(i2, 2);
            writeMessageNoTag(messageLite);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeMessageNoTag(MessageLite messageLite) throws IOException {
            writeUInt32NoTag(messageLite.getSerializedSize());
            messageLite.writeTo(this);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeMessageSetExtension(int i2, MessageLite messageLite) throws IOException {
            writeTag(1, 3);
            writeUInt32(2, i2);
            writeMessage(3, messageLite);
            writeTag(1, 4);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeRawBytes(ByteBuffer byteBuffer) throws IOException {
            if (byteBuffer.hasArray()) {
                write(byteBuffer.array(), byteBuffer.arrayOffset(), byteBuffer.capacity());
                return;
            }
            ByteBuffer byteBufferDuplicate = byteBuffer.duplicate();
            byteBufferDuplicate.clear();
            write(byteBufferDuplicate);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeRawMessageSetExtension(int i2, ByteString byteString) throws IOException {
            writeTag(1, 3);
            writeUInt32(2, i2);
            writeBytes(3, byteString);
            writeTag(1, 4);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeString(int i2, String str) throws IOException {
            writeTag(i2, 2);
            writeStringNoTag(str);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeStringNoTag(String str) throws IOException {
            long j2 = this.position;
            try {
                int iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(str.length() * 3);
                int iComputeUInt32SizeNoTag2 = CodedOutputStream.computeUInt32SizeNoTag(str.length());
                if (iComputeUInt32SizeNoTag2 == iComputeUInt32SizeNoTag) {
                    int iBufferPos = bufferPos(this.position) + iComputeUInt32SizeNoTag2;
                    this.buffer.position(iBufferPos);
                    Utf8.encodeUtf8(str, this.buffer);
                    int iPosition = this.buffer.position() - iBufferPos;
                    writeUInt32NoTag(iPosition);
                    this.position += iPosition;
                } else {
                    int iEncodedLength = Utf8.encodedLength(str);
                    writeUInt32NoTag(iEncodedLength);
                    repositionBuffer(this.position);
                    Utf8.encodeUtf8(str, this.buffer);
                    this.position += iEncodedLength;
                }
            } catch (Utf8.UnpairedSurrogateException e2) {
                this.position = j2;
                repositionBuffer(j2);
                inefficientWriteStringNoTag(str, e2);
            } catch (IllegalArgumentException e3) {
                throw new OutOfSpaceException(e3);
            } catch (IndexOutOfBoundsException e4) {
                throw new OutOfSpaceException(e4);
            }
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeTag(int i2, int i3) throws IOException {
            writeUInt32NoTag(WireFormat.makeTag(i2, i3));
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeUInt32(int i2, int i3) throws IOException {
            writeTag(i2, 0);
            writeUInt32NoTag(i3);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeUInt32NoTag(int i2) throws IOException {
            if (this.position <= this.oneVarintLimit) {
                while ((i2 & (-128)) != 0) {
                    long j2 = this.position;
                    this.position = j2 + 1;
                    UnsafeUtil.putByte(j2, (byte) ((i2 & 127) | 128));
                    i2 >>>= 7;
                }
                long j3 = this.position;
                this.position = 1 + j3;
                UnsafeUtil.putByte(j3, (byte) i2);
                return;
            }
            while (true) {
                long j4 = this.position;
                if (j4 >= this.limit) {
                    throw new OutOfSpaceException(String.format("Pos: %d, limit: %d, len: %d", Long.valueOf(this.position), Long.valueOf(this.limit), 1));
                }
                if ((i2 & (-128)) == 0) {
                    this.position = 1 + j4;
                    UnsafeUtil.putByte(j4, (byte) i2);
                    return;
                } else {
                    this.position = j4 + 1;
                    UnsafeUtil.putByte(j4, (byte) ((i2 & 127) | 128));
                    i2 >>>= 7;
                }
            }
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeUInt64(int i2, long j2) throws IOException {
            writeTag(i2, 0);
            writeUInt64NoTag(j2);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeUInt64NoTag(long j2) throws IOException {
            if (this.position <= this.oneVarintLimit) {
                while ((j2 & (-128)) != 0) {
                    long j3 = this.position;
                    this.position = j3 + 1;
                    UnsafeUtil.putByte(j3, (byte) ((((int) j2) & 127) | 128));
                    j2 >>>= 7;
                }
                long j4 = this.position;
                this.position = 1 + j4;
                UnsafeUtil.putByte(j4, (byte) j2);
                return;
            }
            while (true) {
                long j5 = this.position;
                if (j5 >= this.limit) {
                    throw new OutOfSpaceException(String.format("Pos: %d, limit: %d, len: %d", Long.valueOf(this.position), Long.valueOf(this.limit), 1));
                }
                if ((j2 & (-128)) == 0) {
                    this.position = 1 + j5;
                    UnsafeUtil.putByte(j5, (byte) j2);
                    return;
                } else {
                    this.position = j5 + 1;
                    UnsafeUtil.putByte(j5, (byte) ((((int) j2) & 127) | 128));
                    j2 >>>= 7;
                }
            }
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeByteArray(int i2, byte[] bArr, int i3, int i4) throws IOException {
            writeTag(i2, 2);
            writeByteArrayNoTag(bArr, i3, i4);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream, androidx.datastore.preferences.protobuf.ByteOutput
        public void writeLazy(ByteBuffer byteBuffer) throws IOException {
            write(byteBuffer);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeMessage(int i2, MessageLite messageLite, Schema schema) throws IOException {
            writeTag(i2, 2);
            writeMessageNoTag(messageLite, schema);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream
        public void writeMessageNoTag(MessageLite messageLite, Schema schema) throws IOException {
            writeUInt32NoTag(((AbstractMessageLite) messageLite).getSerializedSize(schema));
            schema.writeTo(messageLite, this.wrapper);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream, androidx.datastore.preferences.protobuf.ByteOutput
        public void write(byte[] bArr, int i2, int i3) throws IOException {
            if (bArr != null && i2 >= 0 && i3 >= 0 && bArr.length - i3 >= i2) {
                long j2 = i3;
                long j3 = this.limit - j2;
                long j4 = this.position;
                if (j3 >= j4) {
                    UnsafeUtil.copyMemory(bArr, i2, j4, j2);
                    this.position += j2;
                    return;
                }
            }
            if (bArr == null) {
                throw new NullPointerException("value");
            }
            throw new OutOfSpaceException(String.format("Pos: %d, limit: %d, len: %d", Long.valueOf(this.position), Long.valueOf(this.limit), Integer.valueOf(i3)));
        }

        @Override // androidx.datastore.preferences.protobuf.CodedOutputStream, androidx.datastore.preferences.protobuf.ByteOutput
        public void write(ByteBuffer byteBuffer) throws IOException {
            try {
                int iRemaining = byteBuffer.remaining();
                repositionBuffer(this.position);
                this.buffer.put(byteBuffer);
                this.position += iRemaining;
            } catch (BufferOverflowException e2) {
                throw new OutOfSpaceException(e2);
            }
        }
    }

    public static int computeBoolSize(int i2, boolean z2) {
        return computeTagSize(i2) + computeBoolSizeNoTag(z2);
    }

    public static int computeBoolSizeNoTag(boolean z2) {
        return 1;
    }

    public static int computeByteArraySize(int i2, byte[] bArr) {
        return computeTagSize(i2) + computeByteArraySizeNoTag(bArr);
    }

    public static int computeByteArraySizeNoTag(byte[] bArr) {
        return computeLengthDelimitedFieldSize(bArr.length);
    }

    public static int computeByteBufferSize(int i2, ByteBuffer byteBuffer) {
        return computeTagSize(i2) + computeByteBufferSizeNoTag(byteBuffer);
    }

    public static int computeByteBufferSizeNoTag(ByteBuffer byteBuffer) {
        return computeLengthDelimitedFieldSize(byteBuffer.capacity());
    }

    public static int computeBytesSize(int i2, ByteString byteString) {
        return computeTagSize(i2) + computeBytesSizeNoTag(byteString);
    }

    public static int computeBytesSizeNoTag(ByteString byteString) {
        return computeLengthDelimitedFieldSize(byteString.size());
    }

    public static int computeDoubleSize(int i2, double d3) {
        return computeTagSize(i2) + computeDoubleSizeNoTag(d3);
    }

    public static int computeDoubleSizeNoTag(double d3) {
        return 8;
    }

    public static int computeEnumSize(int i2, int i3) {
        return computeTagSize(i2) + computeEnumSizeNoTag(i3);
    }

    public static int computeEnumSizeNoTag(int i2) {
        return computeInt32SizeNoTag(i2);
    }

    public static int computeFixed32Size(int i2, int i3) {
        return computeTagSize(i2) + computeFixed32SizeNoTag(i3);
    }

    public static int computeFixed32SizeNoTag(int i2) {
        return 4;
    }

    public static int computeFixed64Size(int i2, long j2) {
        return computeTagSize(i2) + computeFixed64SizeNoTag(j2);
    }

    public static int computeFixed64SizeNoTag(long j2) {
        return 8;
    }

    public static int computeFloatSize(int i2, float f2) {
        return computeTagSize(i2) + computeFloatSizeNoTag(f2);
    }

    public static int computeFloatSizeNoTag(float f2) {
        return 4;
    }

    @Deprecated
    public static int computeGroupSize(int i2, MessageLite messageLite) {
        return (computeTagSize(i2) * 2) + computeGroupSizeNoTag(messageLite);
    }

    @Deprecated
    public static int computeGroupSizeNoTag(MessageLite messageLite) {
        return messageLite.getSerializedSize();
    }

    public static int computeInt32Size(int i2, int i3) {
        return computeTagSize(i2) + computeInt32SizeNoTag(i3);
    }

    public static int computeInt32SizeNoTag(int i2) {
        if (i2 >= 0) {
            return computeUInt32SizeNoTag(i2);
        }
        return 10;
    }

    public static int computeInt64Size(int i2, long j2) {
        return computeTagSize(i2) + computeInt64SizeNoTag(j2);
    }

    public static int computeInt64SizeNoTag(long j2) {
        return computeUInt64SizeNoTag(j2);
    }

    public static int computeLazyFieldMessageSetExtensionSize(int i2, LazyFieldLite lazyFieldLite) {
        return (computeTagSize(1) * 2) + computeUInt32Size(2, i2) + computeLazyFieldSize(3, lazyFieldLite);
    }

    public static int computeLazyFieldSize(int i2, LazyFieldLite lazyFieldLite) {
        return computeTagSize(i2) + computeLazyFieldSizeNoTag(lazyFieldLite);
    }

    public static int computeLazyFieldSizeNoTag(LazyFieldLite lazyFieldLite) {
        return computeLengthDelimitedFieldSize(lazyFieldLite.getSerializedSize());
    }

    public static int computeLengthDelimitedFieldSize(int i2) {
        return computeUInt32SizeNoTag(i2) + i2;
    }

    public static int computeMessageSetExtensionSize(int i2, MessageLite messageLite) {
        return (computeTagSize(1) * 2) + computeUInt32Size(2, i2) + computeMessageSize(3, messageLite);
    }

    public static int computeMessageSize(int i2, MessageLite messageLite) {
        return computeTagSize(i2) + computeMessageSizeNoTag(messageLite);
    }

    public static int computeMessageSizeNoTag(MessageLite messageLite) {
        return computeLengthDelimitedFieldSize(messageLite.getSerializedSize());
    }

    public static int computePreferredBufferSize(int i2) {
        if (i2 > 4096) {
            return 4096;
        }
        return i2;
    }

    public static int computeRawMessageSetExtensionSize(int i2, ByteString byteString) {
        return (computeTagSize(1) * 2) + computeUInt32Size(2, i2) + computeBytesSize(3, byteString);
    }

    @Deprecated
    public static int computeRawVarint32Size(int i2) {
        return computeUInt32SizeNoTag(i2);
    }

    @Deprecated
    public static int computeRawVarint64Size(long j2) {
        return computeUInt64SizeNoTag(j2);
    }

    public static int computeSFixed32Size(int i2, int i3) {
        return computeTagSize(i2) + computeSFixed32SizeNoTag(i3);
    }

    public static int computeSFixed32SizeNoTag(int i2) {
        return 4;
    }

    public static int computeSFixed64Size(int i2, long j2) {
        return computeTagSize(i2) + computeSFixed64SizeNoTag(j2);
    }

    public static int computeSFixed64SizeNoTag(long j2) {
        return 8;
    }

    public static int computeSInt32Size(int i2, int i3) {
        return computeTagSize(i2) + computeSInt32SizeNoTag(i3);
    }

    public static int computeSInt32SizeNoTag(int i2) {
        return computeUInt32SizeNoTag(encodeZigZag32(i2));
    }

    public static int computeSInt64Size(int i2, long j2) {
        return computeTagSize(i2) + computeSInt64SizeNoTag(j2);
    }

    public static int computeSInt64SizeNoTag(long j2) {
        return computeUInt64SizeNoTag(encodeZigZag64(j2));
    }

    public static int computeStringSize(int i2, String str) {
        return computeTagSize(i2) + computeStringSizeNoTag(str);
    }

    public static int computeStringSizeNoTag(String str) {
        int length;
        try {
            length = Utf8.encodedLength(str);
        } catch (Utf8.UnpairedSurrogateException unused) {
            length = str.getBytes(Internal.UTF_8).length;
        }
        return computeLengthDelimitedFieldSize(length);
    }

    public static int computeTagSize(int i2) {
        return computeUInt32SizeNoTag(WireFormat.makeTag(i2, 0));
    }

    public static int computeUInt32Size(int i2, int i3) {
        return computeTagSize(i2) + computeUInt32SizeNoTag(i3);
    }

    public static int computeUInt32SizeNoTag(int i2) {
        if ((i2 & (-128)) == 0) {
            return 1;
        }
        if ((i2 & (-16384)) == 0) {
            return 2;
        }
        if (((-2097152) & i2) == 0) {
            return 3;
        }
        return (i2 & (-268435456)) == 0 ? 4 : 5;
    }

    public static int computeUInt64Size(int i2, long j2) {
        return computeTagSize(i2) + computeUInt64SizeNoTag(j2);
    }

    public static int computeUInt64SizeNoTag(long j2) {
        int i2;
        if (((-128) & j2) == 0) {
            return 1;
        }
        if (j2 < 0) {
            return 10;
        }
        if (((-34359738368L) & j2) != 0) {
            j2 >>>= 28;
            i2 = 6;
        } else {
            i2 = 2;
        }
        if (((-2097152) & j2) != 0) {
            i2 += 2;
            j2 >>>= 14;
        }
        return (j2 & (-16384)) != 0 ? i2 + 1 : i2;
    }

    public static int encodeZigZag32(int i2) {
        return (i2 >> 31) ^ (i2 << 1);
    }

    public static long encodeZigZag64(long j2) {
        return (j2 >> 63) ^ (j2 << 1);
    }

    public static CodedOutputStream newInstance(OutputStream outputStream) {
        return newInstance(outputStream, 4096);
    }

    public static CodedOutputStream newSafeInstance(ByteBuffer byteBuffer) {
        return new SafeDirectNioEncoder(byteBuffer);
    }

    public static CodedOutputStream newUnsafeInstance(ByteBuffer byteBuffer) {
        return new UnsafeDirectNioEncoder(byteBuffer);
    }

    public final void checkNoSpaceLeft() {
        if (spaceLeft() != 0) {
            throw new IllegalStateException("Did not write as much data as expected.");
        }
    }

    public abstract void flush() throws IOException;

    public abstract int getTotalBytesWritten();

    public final void inefficientWriteStringNoTag(String str, Utf8.UnpairedSurrogateException unpairedSurrogateException) throws IOException {
        logger.log(Level.WARNING, "Converting ill-formed UTF-16. Your Protocol Buffer will not round trip correctly!", (Throwable) unpairedSurrogateException);
        byte[] bytes = str.getBytes(Internal.UTF_8);
        try {
            writeUInt32NoTag(bytes.length);
            writeLazy(bytes, 0, bytes.length);
        } catch (OutOfSpaceException e2) {
            throw e2;
        } catch (IndexOutOfBoundsException e3) {
            throw new OutOfSpaceException(e3);
        }
    }

    public boolean isSerializationDeterministic() {
        return this.serializationDeterministic;
    }

    public abstract int spaceLeft();

    public void useDeterministicSerialization() {
        this.serializationDeterministic = true;
    }

    @Override // androidx.datastore.preferences.protobuf.ByteOutput
    public abstract void write(byte b3) throws IOException;

    @Override // androidx.datastore.preferences.protobuf.ByteOutput
    public abstract void write(ByteBuffer byteBuffer) throws IOException;

    @Override // androidx.datastore.preferences.protobuf.ByteOutput
    public abstract void write(byte[] bArr, int i2, int i3) throws IOException;

    public abstract void writeBool(int i2, boolean z2) throws IOException;

    public final void writeBoolNoTag(boolean z2) throws IOException {
        write(z2 ? (byte) 1 : (byte) 0);
    }

    public abstract void writeByteArray(int i2, byte[] bArr) throws IOException;

    public abstract void writeByteArray(int i2, byte[] bArr, int i3, int i4) throws IOException;

    public final void writeByteArrayNoTag(byte[] bArr) throws IOException {
        writeByteArrayNoTag(bArr, 0, bArr.length);
    }

    public abstract void writeByteArrayNoTag(byte[] bArr, int i2, int i3) throws IOException;

    public abstract void writeByteBuffer(int i2, ByteBuffer byteBuffer) throws IOException;

    public abstract void writeBytes(int i2, ByteString byteString) throws IOException;

    public abstract void writeBytesNoTag(ByteString byteString) throws IOException;

    public final void writeDouble(int i2, double d3) throws IOException {
        writeFixed64(i2, Double.doubleToRawLongBits(d3));
    }

    public final void writeDoubleNoTag(double d3) throws IOException {
        writeFixed64NoTag(Double.doubleToRawLongBits(d3));
    }

    public final void writeEnum(int i2, int i3) throws IOException {
        writeInt32(i2, i3);
    }

    public final void writeEnumNoTag(int i2) throws IOException {
        writeInt32NoTag(i2);
    }

    public abstract void writeFixed32(int i2, int i3) throws IOException;

    public abstract void writeFixed32NoTag(int i2) throws IOException;

    public abstract void writeFixed64(int i2, long j2) throws IOException;

    public abstract void writeFixed64NoTag(long j2) throws IOException;

    public final void writeFloat(int i2, float f2) throws IOException {
        writeFixed32(i2, Float.floatToRawIntBits(f2));
    }

    public final void writeFloatNoTag(float f2) throws IOException {
        writeFixed32NoTag(Float.floatToRawIntBits(f2));
    }

    @Deprecated
    public final void writeGroup(int i2, MessageLite messageLite) throws IOException {
        writeTag(i2, 3);
        writeGroupNoTag(messageLite);
        writeTag(i2, 4);
    }

    @Deprecated
    public final void writeGroupNoTag(MessageLite messageLite) throws IOException {
        messageLite.writeTo(this);
    }

    public abstract void writeInt32(int i2, int i3) throws IOException;

    public abstract void writeInt32NoTag(int i2) throws IOException;

    public final void writeInt64(int i2, long j2) throws IOException {
        writeUInt64(i2, j2);
    }

    public final void writeInt64NoTag(long j2) throws IOException {
        writeUInt64NoTag(j2);
    }

    @Override // androidx.datastore.preferences.protobuf.ByteOutput
    public abstract void writeLazy(ByteBuffer byteBuffer) throws IOException;

    @Override // androidx.datastore.preferences.protobuf.ByteOutput
    public abstract void writeLazy(byte[] bArr, int i2, int i3) throws IOException;

    public abstract void writeMessage(int i2, MessageLite messageLite) throws IOException;

    public abstract void writeMessage(int i2, MessageLite messageLite, Schema schema) throws IOException;

    public abstract void writeMessageNoTag(MessageLite messageLite) throws IOException;

    public abstract void writeMessageNoTag(MessageLite messageLite, Schema schema) throws IOException;

    public abstract void writeMessageSetExtension(int i2, MessageLite messageLite) throws IOException;

    public final void writeRawByte(byte b3) throws IOException {
        write(b3);
    }

    public abstract void writeRawBytes(ByteBuffer byteBuffer) throws IOException;

    public final void writeRawBytes(byte[] bArr) throws IOException {
        write(bArr, 0, bArr.length);
    }

    @Deprecated
    public final void writeRawLittleEndian32(int i2) throws IOException {
        writeFixed32NoTag(i2);
    }

    @Deprecated
    public final void writeRawLittleEndian64(long j2) throws IOException {
        writeFixed64NoTag(j2);
    }

    public abstract void writeRawMessageSetExtension(int i2, ByteString byteString) throws IOException;

    @Deprecated
    public final void writeRawVarint32(int i2) throws IOException {
        writeUInt32NoTag(i2);
    }

    @Deprecated
    public final void writeRawVarint64(long j2) throws IOException {
        writeUInt64NoTag(j2);
    }

    public final void writeSFixed32(int i2, int i3) throws IOException {
        writeFixed32(i2, i3);
    }

    public final void writeSFixed32NoTag(int i2) throws IOException {
        writeFixed32NoTag(i2);
    }

    public final void writeSFixed64(int i2, long j2) throws IOException {
        writeFixed64(i2, j2);
    }

    public final void writeSFixed64NoTag(long j2) throws IOException {
        writeFixed64NoTag(j2);
    }

    public final void writeSInt32(int i2, int i3) throws IOException {
        writeUInt32(i2, encodeZigZag32(i3));
    }

    public final void writeSInt32NoTag(int i2) throws IOException {
        writeUInt32NoTag(encodeZigZag32(i2));
    }

    public final void writeSInt64(int i2, long j2) throws IOException {
        writeUInt64(i2, encodeZigZag64(j2));
    }

    public final void writeSInt64NoTag(long j2) throws IOException {
        writeUInt64NoTag(encodeZigZag64(j2));
    }

    public abstract void writeString(int i2, String str) throws IOException;

    public abstract void writeStringNoTag(String str) throws IOException;

    public abstract void writeTag(int i2, int i3) throws IOException;

    public abstract void writeUInt32(int i2, int i3) throws IOException;

    public abstract void writeUInt32NoTag(int i2) throws IOException;

    public abstract void writeUInt64(int i2, long j2) throws IOException;

    public abstract void writeUInt64NoTag(long j2) throws IOException;

    private CodedOutputStream() {
    }

    @Deprecated
    public static int computeGroupSize(int i2, MessageLite messageLite, Schema schema) {
        return (computeTagSize(i2) * 2) + computeGroupSizeNoTag(messageLite, schema);
    }

    @Deprecated
    public static int computeGroupSizeNoTag(MessageLite messageLite, Schema schema) {
        return ((AbstractMessageLite) messageLite).getSerializedSize(schema);
    }

    public static int computeMessageSize(int i2, MessageLite messageLite, Schema schema) {
        return computeTagSize(i2) + computeMessageSizeNoTag(messageLite, schema);
    }

    public static int computeMessageSizeNoTag(MessageLite messageLite, Schema schema) {
        return computeLengthDelimitedFieldSize(((AbstractMessageLite) messageLite).getSerializedSize(schema));
    }

    public static CodedOutputStream newInstance(OutputStream outputStream, int i2) {
        return new OutputStreamEncoder(outputStream, i2);
    }

    @Deprecated
    public final void writeGroupNoTag(MessageLite messageLite, Schema schema) throws IOException {
        schema.writeTo(messageLite, this.wrapper);
    }

    public final void writeRawByte(int i2) throws IOException {
        write((byte) i2);
    }

    public final void writeRawBytes(byte[] bArr, int i2, int i3) throws IOException {
        write(bArr, i2, i3);
    }

    public static CodedOutputStream newInstance(byte[] bArr) {
        return newInstance(bArr, 0, bArr.length);
    }

    public final void writeRawBytes(ByteString byteString) throws IOException {
        byteString.writeTo(this);
    }

    public static CodedOutputStream newInstance(byte[] bArr, int i2, int i3) {
        return new ArrayEncoder(bArr, i2, i3);
    }

    @Deprecated
    public final void writeGroup(int i2, MessageLite messageLite, Schema schema) throws IOException {
        writeTag(i2, 3);
        writeGroupNoTag(messageLite, schema);
        writeTag(i2, 4);
    }

    public static CodedOutputStream newInstance(ByteBuffer byteBuffer) {
        if (byteBuffer.hasArray()) {
            return new HeapNioEncoder(byteBuffer);
        }
        if (byteBuffer.isDirect() && !byteBuffer.isReadOnly()) {
            if (UnsafeDirectNioEncoder.isSupported()) {
                return newUnsafeInstance(byteBuffer);
            }
            return newSafeInstance(byteBuffer);
        }
        throw new IllegalArgumentException("ByteBuffer is read-only");
    }

    @Deprecated
    public static CodedOutputStream newInstance(ByteBuffer byteBuffer, int i2) {
        return newInstance(byteBuffer);
    }

    public static CodedOutputStream newInstance(ByteOutput byteOutput, int i2) {
        if (i2 >= 0) {
            return new ByteOutputEncoder(byteOutput, i2);
        }
        throw new IllegalArgumentException("bufferSize must be positive");
    }
}
