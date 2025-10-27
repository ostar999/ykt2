package androidx.datastore.preferences.protobuf;

import androidx.datastore.preferences.protobuf.MessageLite;
import com.yikaobang.yixue.R2;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public abstract class CodedInputStream {
    private static final int DEFAULT_BUFFER_SIZE = 4096;
    private static final int DEFAULT_RECURSION_LIMIT = 100;
    private static final int DEFAULT_SIZE_LIMIT = Integer.MAX_VALUE;
    int recursionDepth;
    int recursionLimit;
    private boolean shouldDiscardUnknownFields;
    int sizeLimit;
    CodedInputStreamReader wrapper;

    public static final class ArrayDecoder extends CodedInputStream {
        private final byte[] buffer;
        private int bufferSizeAfterLimit;
        private int currentLimit;
        private boolean enableAliasing;
        private final boolean immutable;
        private int lastTag;
        private int limit;
        private int pos;
        private int startPos;

        private void recomputeBufferSizeAfterLimit() {
            int i2 = this.limit + this.bufferSizeAfterLimit;
            this.limit = i2;
            int i3 = i2 - this.startPos;
            int i4 = this.currentLimit;
            if (i3 <= i4) {
                this.bufferSizeAfterLimit = 0;
                return;
            }
            int i5 = i3 - i4;
            this.bufferSizeAfterLimit = i5;
            this.limit = i2 - i5;
        }

        private void skipRawVarint() throws IOException {
            if (this.limit - this.pos >= 10) {
                skipRawVarintFastPath();
            } else {
                skipRawVarintSlowPath();
            }
        }

        private void skipRawVarintFastPath() throws IOException {
            for (int i2 = 0; i2 < 10; i2++) {
                byte[] bArr = this.buffer;
                int i3 = this.pos;
                this.pos = i3 + 1;
                if (bArr[i3] >= 0) {
                    return;
                }
            }
            throw InvalidProtocolBufferException.malformedVarint();
        }

        private void skipRawVarintSlowPath() throws IOException {
            for (int i2 = 0; i2 < 10; i2++) {
                if (readRawByte() >= 0) {
                    return;
                }
            }
            throw InvalidProtocolBufferException.malformedVarint();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void checkLastTagWas(int i2) throws InvalidProtocolBufferException {
            if (this.lastTag != i2) {
                throw InvalidProtocolBufferException.invalidEndTag();
            }
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void enableAliasing(boolean z2) {
            this.enableAliasing = z2;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int getBytesUntilLimit() {
            int i2 = this.currentLimit;
            if (i2 == Integer.MAX_VALUE) {
                return -1;
            }
            return i2 - getTotalBytesRead();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int getLastTag() {
            return this.lastTag;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int getTotalBytesRead() {
            return this.pos - this.startPos;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public boolean isAtEnd() throws IOException {
            return this.pos == this.limit;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void popLimit(int i2) {
            this.currentLimit = i2;
            recomputeBufferSizeAfterLimit();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int pushLimit(int i2) throws InvalidProtocolBufferException {
            if (i2 < 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            int totalBytesRead = i2 + getTotalBytesRead();
            int i3 = this.currentLimit;
            if (totalBytesRead > i3) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            this.currentLimit = totalBytesRead;
            recomputeBufferSizeAfterLimit();
            return i3;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public boolean readBool() throws IOException {
            return readRawVarint64() != 0;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public byte[] readByteArray() throws IOException {
            return readRawBytes(readRawVarint32());
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public ByteBuffer readByteBuffer() throws IOException {
            int rawVarint32 = readRawVarint32();
            if (rawVarint32 > 0) {
                int i2 = this.limit;
                int i3 = this.pos;
                if (rawVarint32 <= i2 - i3) {
                    ByteBuffer byteBufferWrap = (this.immutable || !this.enableAliasing) ? ByteBuffer.wrap(Arrays.copyOfRange(this.buffer, i3, i3 + rawVarint32)) : ByteBuffer.wrap(this.buffer, i3, rawVarint32).slice();
                    this.pos += rawVarint32;
                    return byteBufferWrap;
                }
            }
            if (rawVarint32 == 0) {
                return Internal.EMPTY_BYTE_BUFFER;
            }
            if (rawVarint32 < 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            throw InvalidProtocolBufferException.truncatedMessage();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public ByteString readBytes() throws IOException {
            int rawVarint32 = readRawVarint32();
            if (rawVarint32 > 0) {
                int i2 = this.limit;
                int i3 = this.pos;
                if (rawVarint32 <= i2 - i3) {
                    ByteString byteStringWrap = (this.immutable && this.enableAliasing) ? ByteString.wrap(this.buffer, i3, rawVarint32) : ByteString.copyFrom(this.buffer, i3, rawVarint32);
                    this.pos += rawVarint32;
                    return byteStringWrap;
                }
            }
            return rawVarint32 == 0 ? ByteString.EMPTY : ByteString.wrap(readRawBytes(rawVarint32));
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public double readDouble() throws IOException {
            return Double.longBitsToDouble(readRawLittleEndian64());
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readEnum() throws IOException {
            return readRawVarint32();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readFixed32() throws IOException {
            return readRawLittleEndian32();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public long readFixed64() throws IOException {
            return readRawLittleEndian64();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public float readFloat() throws IOException {
            return Float.intBitsToFloat(readRawLittleEndian32());
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void readGroup(int i2, MessageLite.Builder builder, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            int i3 = this.recursionDepth;
            if (i3 >= this.recursionLimit) {
                throw InvalidProtocolBufferException.recursionLimitExceeded();
            }
            this.recursionDepth = i3 + 1;
            builder.mergeFrom(this, extensionRegistryLite);
            checkLastTagWas(WireFormat.makeTag(i2, 4));
            this.recursionDepth--;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readInt32() throws IOException {
            return readRawVarint32();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public long readInt64() throws IOException {
            return readRawVarint64();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void readMessage(MessageLite.Builder builder, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            int rawVarint32 = readRawVarint32();
            if (this.recursionDepth >= this.recursionLimit) {
                throw InvalidProtocolBufferException.recursionLimitExceeded();
            }
            int iPushLimit = pushLimit(rawVarint32);
            this.recursionDepth++;
            builder.mergeFrom(this, extensionRegistryLite);
            checkLastTagWas(0);
            this.recursionDepth--;
            popLimit(iPushLimit);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public byte readRawByte() throws IOException {
            int i2 = this.pos;
            if (i2 == this.limit) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            byte[] bArr = this.buffer;
            this.pos = i2 + 1;
            return bArr[i2];
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public byte[] readRawBytes(int i2) throws IOException {
            if (i2 > 0) {
                int i3 = this.limit;
                int i4 = this.pos;
                if (i2 <= i3 - i4) {
                    int i5 = i2 + i4;
                    this.pos = i5;
                    return Arrays.copyOfRange(this.buffer, i4, i5);
                }
            }
            if (i2 > 0) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            if (i2 == 0) {
                return Internal.EMPTY_BYTE_ARRAY;
            }
            throw InvalidProtocolBufferException.negativeSize();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readRawLittleEndian32() throws IOException {
            int i2 = this.pos;
            if (this.limit - i2 < 4) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            byte[] bArr = this.buffer;
            this.pos = i2 + 4;
            return ((bArr[i2 + 3] & 255) << 24) | (bArr[i2] & 255) | ((bArr[i2 + 1] & 255) << 8) | ((bArr[i2 + 2] & 255) << 16);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public long readRawLittleEndian64() throws IOException {
            int i2 = this.pos;
            if (this.limit - i2 < 8) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            byte[] bArr = this.buffer;
            this.pos = i2 + 8;
            return ((bArr[i2 + 7] & 255) << 56) | (bArr[i2] & 255) | ((bArr[i2 + 1] & 255) << 8) | ((bArr[i2 + 2] & 255) << 16) | ((bArr[i2 + 3] & 255) << 24) | ((bArr[i2 + 4] & 255) << 32) | ((bArr[i2 + 5] & 255) << 40) | ((bArr[i2 + 6] & 255) << 48);
        }

        /* JADX WARN: Code restructure failed: missing block: B:33:0x0068, code lost:
        
            if (r2[r3] < 0) goto L34;
         */
        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public int readRawVarint32() throws java.io.IOException {
            /*
                r5 = this;
                int r0 = r5.pos
                int r1 = r5.limit
                if (r1 != r0) goto L7
                goto L6a
            L7:
                byte[] r2 = r5.buffer
                int r3 = r0 + 1
                r0 = r2[r0]
                if (r0 < 0) goto L12
                r5.pos = r3
                return r0
            L12:
                int r1 = r1 - r3
                r4 = 9
                if (r1 >= r4) goto L18
                goto L6a
            L18:
                int r1 = r3 + 1
                r3 = r2[r3]
                int r3 = r3 << 7
                r0 = r0 ^ r3
                if (r0 >= 0) goto L24
                r0 = r0 ^ (-128(0xffffffffffffff80, float:NaN))
                goto L70
            L24:
                int r3 = r1 + 1
                r1 = r2[r1]
                int r1 = r1 << 14
                r0 = r0 ^ r1
                if (r0 < 0) goto L31
                r0 = r0 ^ 16256(0x3f80, float:2.278E-41)
            L2f:
                r1 = r3
                goto L70
            L31:
                int r1 = r3 + 1
                r3 = r2[r3]
                int r3 = r3 << 21
                r0 = r0 ^ r3
                if (r0 >= 0) goto L3f
                r2 = -2080896(0xffffffffffe03f80, float:NaN)
                r0 = r0 ^ r2
                goto L70
            L3f:
                int r3 = r1 + 1
                r1 = r2[r1]
                int r4 = r1 << 28
                r0 = r0 ^ r4
                r4 = 266354560(0xfe03f80, float:2.2112565E-29)
                r0 = r0 ^ r4
                if (r1 >= 0) goto L2f
                int r1 = r3 + 1
                r3 = r2[r3]
                if (r3 >= 0) goto L70
                int r3 = r1 + 1
                r1 = r2[r1]
                if (r1 >= 0) goto L2f
                int r1 = r3 + 1
                r3 = r2[r3]
                if (r3 >= 0) goto L70
                int r3 = r1 + 1
                r1 = r2[r1]
                if (r1 >= 0) goto L2f
                int r1 = r3 + 1
                r2 = r2[r3]
                if (r2 >= 0) goto L70
            L6a:
                long r0 = r5.readRawVarint64SlowPath()
                int r0 = (int) r0
                return r0
            L70:
                r5.pos = r1
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.preferences.protobuf.CodedInputStream.ArrayDecoder.readRawVarint32():int");
        }

        /* JADX WARN: Code restructure failed: missing block: B:39:0x00b4, code lost:
        
            if (r2[r0] < 0) goto L40;
         */
        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public long readRawVarint64() throws java.io.IOException {
            /*
                Method dump skipped, instructions count: 192
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.preferences.protobuf.CodedInputStream.ArrayDecoder.readRawVarint64():long");
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public long readRawVarint64SlowPath() throws IOException {
            long j2 = 0;
            for (int i2 = 0; i2 < 64; i2 += 7) {
                j2 |= (r3 & 127) << i2;
                if ((readRawByte() & 128) == 0) {
                    return j2;
                }
            }
            throw InvalidProtocolBufferException.malformedVarint();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readSFixed32() throws IOException {
            return readRawLittleEndian32();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public long readSFixed64() throws IOException {
            return readRawLittleEndian64();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readSInt32() throws IOException {
            return CodedInputStream.decodeZigZag32(readRawVarint32());
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public long readSInt64() throws IOException {
            return CodedInputStream.decodeZigZag64(readRawVarint64());
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public String readString() throws IOException {
            int rawVarint32 = readRawVarint32();
            if (rawVarint32 > 0) {
                int i2 = this.limit;
                int i3 = this.pos;
                if (rawVarint32 <= i2 - i3) {
                    String str = new String(this.buffer, i3, rawVarint32, Internal.UTF_8);
                    this.pos += rawVarint32;
                    return str;
                }
            }
            if (rawVarint32 == 0) {
                return "";
            }
            if (rawVarint32 < 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            throw InvalidProtocolBufferException.truncatedMessage();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public String readStringRequireUtf8() throws IOException {
            int rawVarint32 = readRawVarint32();
            if (rawVarint32 > 0) {
                int i2 = this.limit;
                int i3 = this.pos;
                if (rawVarint32 <= i2 - i3) {
                    String strDecodeUtf8 = Utf8.decodeUtf8(this.buffer, i3, rawVarint32);
                    this.pos += rawVarint32;
                    return strDecodeUtf8;
                }
            }
            if (rawVarint32 == 0) {
                return "";
            }
            if (rawVarint32 <= 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            throw InvalidProtocolBufferException.truncatedMessage();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readTag() throws IOException {
            if (isAtEnd()) {
                this.lastTag = 0;
                return 0;
            }
            int rawVarint32 = readRawVarint32();
            this.lastTag = rawVarint32;
            if (WireFormat.getTagFieldNumber(rawVarint32) != 0) {
                return this.lastTag;
            }
            throw InvalidProtocolBufferException.invalidTag();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readUInt32() throws IOException {
            return readRawVarint32();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public long readUInt64() throws IOException {
            return readRawVarint64();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        @Deprecated
        public void readUnknownGroup(int i2, MessageLite.Builder builder) throws IOException {
            readGroup(i2, builder, ExtensionRegistryLite.getEmptyRegistry());
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void resetSizeCounter() {
            this.startPos = this.pos;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public boolean skipField(int i2) throws IOException {
            int tagWireType = WireFormat.getTagWireType(i2);
            if (tagWireType == 0) {
                skipRawVarint();
                return true;
            }
            if (tagWireType == 1) {
                skipRawBytes(8);
                return true;
            }
            if (tagWireType == 2) {
                skipRawBytes(readRawVarint32());
                return true;
            }
            if (tagWireType == 3) {
                skipMessage();
                checkLastTagWas(WireFormat.makeTag(WireFormat.getTagFieldNumber(i2), 4));
                return true;
            }
            if (tagWireType == 4) {
                return false;
            }
            if (tagWireType != 5) {
                throw InvalidProtocolBufferException.invalidWireType();
            }
            skipRawBytes(4);
            return true;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void skipMessage() throws IOException {
            int tag;
            do {
                tag = readTag();
                if (tag == 0) {
                    return;
                }
            } while (skipField(tag));
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void skipRawBytes(int i2) throws IOException {
            if (i2 >= 0) {
                int i3 = this.limit;
                int i4 = this.pos;
                if (i2 <= i3 - i4) {
                    this.pos = i4 + i2;
                    return;
                }
            }
            if (i2 >= 0) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            throw InvalidProtocolBufferException.negativeSize();
        }

        private ArrayDecoder(byte[] bArr, int i2, int i3, boolean z2) {
            super();
            this.currentLimit = Integer.MAX_VALUE;
            this.buffer = bArr;
            this.limit = i3 + i2;
            this.pos = i2;
            this.startPos = i2;
            this.immutable = z2;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void skipMessage(CodedOutputStream codedOutputStream) throws IOException {
            int tag;
            do {
                tag = readTag();
                if (tag == 0) {
                    return;
                }
            } while (skipField(tag, codedOutputStream));
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public <T extends MessageLite> T readGroup(int i2, Parser<T> parser, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            int i3 = this.recursionDepth;
            if (i3 < this.recursionLimit) {
                this.recursionDepth = i3 + 1;
                T partialFrom = parser.parsePartialFrom(this, extensionRegistryLite);
                checkLastTagWas(WireFormat.makeTag(i2, 4));
                this.recursionDepth--;
                return partialFrom;
            }
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public <T extends MessageLite> T readMessage(Parser<T> parser, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            int rawVarint32 = readRawVarint32();
            if (this.recursionDepth < this.recursionLimit) {
                int iPushLimit = pushLimit(rawVarint32);
                this.recursionDepth++;
                T partialFrom = parser.parsePartialFrom(this, extensionRegistryLite);
                checkLastTagWas(0);
                this.recursionDepth--;
                popLimit(iPushLimit);
                return partialFrom;
            }
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public boolean skipField(int i2, CodedOutputStream codedOutputStream) throws IOException {
            int tagWireType = WireFormat.getTagWireType(i2);
            if (tagWireType == 0) {
                long int64 = readInt64();
                codedOutputStream.writeRawVarint32(i2);
                codedOutputStream.writeUInt64NoTag(int64);
                return true;
            }
            if (tagWireType == 1) {
                long rawLittleEndian64 = readRawLittleEndian64();
                codedOutputStream.writeRawVarint32(i2);
                codedOutputStream.writeFixed64NoTag(rawLittleEndian64);
                return true;
            }
            if (tagWireType == 2) {
                ByteString bytes = readBytes();
                codedOutputStream.writeRawVarint32(i2);
                codedOutputStream.writeBytesNoTag(bytes);
                return true;
            }
            if (tagWireType == 3) {
                codedOutputStream.writeRawVarint32(i2);
                skipMessage(codedOutputStream);
                int iMakeTag = WireFormat.makeTag(WireFormat.getTagFieldNumber(i2), 4);
                checkLastTagWas(iMakeTag);
                codedOutputStream.writeRawVarint32(iMakeTag);
                return true;
            }
            if (tagWireType == 4) {
                return false;
            }
            if (tagWireType == 5) {
                int rawLittleEndian32 = readRawLittleEndian32();
                codedOutputStream.writeRawVarint32(i2);
                codedOutputStream.writeFixed32NoTag(rawLittleEndian32);
                return true;
            }
            throw InvalidProtocolBufferException.invalidWireType();
        }
    }

    public static final class IterableDirectByteBufferDecoder extends CodedInputStream {
        private int bufferSizeAfterCurrentLimit;
        private long currentAddress;
        private ByteBuffer currentByteBuffer;
        private long currentByteBufferLimit;
        private long currentByteBufferPos;
        private long currentByteBufferStartPos;
        private int currentLimit;
        private boolean enableAliasing;
        private boolean immutable;
        private Iterable<ByteBuffer> input;
        private Iterator<ByteBuffer> iterator;
        private int lastTag;
        private int startOffset;
        private int totalBufferSize;
        private int totalBytesRead;

        private long currentRemaining() {
            return this.currentByteBufferLimit - this.currentByteBufferPos;
        }

        private void getNextByteBuffer() throws InvalidProtocolBufferException {
            if (!this.iterator.hasNext()) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            tryGetNextByteBuffer();
        }

        private void readRawBytesTo(byte[] bArr, int i2, int i3) throws IOException {
            if (i3 < 0 || i3 > remaining()) {
                if (i3 > 0) {
                    throw InvalidProtocolBufferException.truncatedMessage();
                }
                if (i3 != 0) {
                    throw InvalidProtocolBufferException.negativeSize();
                }
                return;
            }
            int i4 = i3;
            while (i4 > 0) {
                if (currentRemaining() == 0) {
                    getNextByteBuffer();
                }
                int iMin = Math.min(i4, (int) currentRemaining());
                long j2 = iMin;
                UnsafeUtil.copyMemory(this.currentByteBufferPos, bArr, (i3 - i4) + i2, j2);
                i4 -= iMin;
                this.currentByteBufferPos += j2;
            }
        }

        private void recomputeBufferSizeAfterLimit() {
            int i2 = this.totalBufferSize + this.bufferSizeAfterCurrentLimit;
            this.totalBufferSize = i2;
            int i3 = i2 - this.startOffset;
            int i4 = this.currentLimit;
            if (i3 <= i4) {
                this.bufferSizeAfterCurrentLimit = 0;
                return;
            }
            int i5 = i3 - i4;
            this.bufferSizeAfterCurrentLimit = i5;
            this.totalBufferSize = i2 - i5;
        }

        private int remaining() {
            return (int) (((this.totalBufferSize - this.totalBytesRead) - this.currentByteBufferPos) + this.currentByteBufferStartPos);
        }

        private void skipRawVarint() throws IOException {
            for (int i2 = 0; i2 < 10; i2++) {
                if (readRawByte() >= 0) {
                    return;
                }
            }
            throw InvalidProtocolBufferException.malformedVarint();
        }

        private ByteBuffer slice(int i2, int i3) throws IOException {
            int iPosition = this.currentByteBuffer.position();
            int iLimit = this.currentByteBuffer.limit();
            try {
                try {
                    this.currentByteBuffer.position(i2);
                    this.currentByteBuffer.limit(i3);
                    return this.currentByteBuffer.slice();
                } catch (IllegalArgumentException unused) {
                    throw InvalidProtocolBufferException.truncatedMessage();
                }
            } finally {
                this.currentByteBuffer.position(iPosition);
                this.currentByteBuffer.limit(iLimit);
            }
        }

        private void tryGetNextByteBuffer() {
            ByteBuffer next = this.iterator.next();
            this.currentByteBuffer = next;
            this.totalBytesRead += (int) (this.currentByteBufferPos - this.currentByteBufferStartPos);
            long jPosition = next.position();
            this.currentByteBufferPos = jPosition;
            this.currentByteBufferStartPos = jPosition;
            this.currentByteBufferLimit = this.currentByteBuffer.limit();
            long jAddressOffset = UnsafeUtil.addressOffset(this.currentByteBuffer);
            this.currentAddress = jAddressOffset;
            this.currentByteBufferPos += jAddressOffset;
            this.currentByteBufferStartPos += jAddressOffset;
            this.currentByteBufferLimit += jAddressOffset;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void checkLastTagWas(int i2) throws InvalidProtocolBufferException {
            if (this.lastTag != i2) {
                throw InvalidProtocolBufferException.invalidEndTag();
            }
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void enableAliasing(boolean z2) {
            this.enableAliasing = z2;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int getBytesUntilLimit() {
            int i2 = this.currentLimit;
            if (i2 == Integer.MAX_VALUE) {
                return -1;
            }
            return i2 - getTotalBytesRead();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int getLastTag() {
            return this.lastTag;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int getTotalBytesRead() {
            return (int) (((this.totalBytesRead - this.startOffset) + this.currentByteBufferPos) - this.currentByteBufferStartPos);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public boolean isAtEnd() throws IOException {
            return (((long) this.totalBytesRead) + this.currentByteBufferPos) - this.currentByteBufferStartPos == ((long) this.totalBufferSize);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void popLimit(int i2) {
            this.currentLimit = i2;
            recomputeBufferSizeAfterLimit();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int pushLimit(int i2) throws InvalidProtocolBufferException {
            if (i2 < 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            int totalBytesRead = i2 + getTotalBytesRead();
            int i3 = this.currentLimit;
            if (totalBytesRead > i3) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            this.currentLimit = totalBytesRead;
            recomputeBufferSizeAfterLimit();
            return i3;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public boolean readBool() throws IOException {
            return readRawVarint64() != 0;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public byte[] readByteArray() throws IOException {
            return readRawBytes(readRawVarint32());
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public ByteBuffer readByteBuffer() throws IOException {
            int rawVarint32 = readRawVarint32();
            if (rawVarint32 > 0) {
                long j2 = rawVarint32;
                if (j2 <= currentRemaining()) {
                    if (this.immutable || !this.enableAliasing) {
                        byte[] bArr = new byte[rawVarint32];
                        UnsafeUtil.copyMemory(this.currentByteBufferPos, bArr, 0L, j2);
                        this.currentByteBufferPos += j2;
                        return ByteBuffer.wrap(bArr);
                    }
                    long j3 = this.currentByteBufferPos + j2;
                    this.currentByteBufferPos = j3;
                    long j4 = this.currentAddress;
                    return slice((int) ((j3 - j4) - j2), (int) (j3 - j4));
                }
            }
            if (rawVarint32 > 0 && rawVarint32 <= remaining()) {
                byte[] bArr2 = new byte[rawVarint32];
                readRawBytesTo(bArr2, 0, rawVarint32);
                return ByteBuffer.wrap(bArr2);
            }
            if (rawVarint32 == 0) {
                return Internal.EMPTY_BYTE_BUFFER;
            }
            if (rawVarint32 < 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            throw InvalidProtocolBufferException.truncatedMessage();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public ByteString readBytes() throws IOException {
            int rawVarint32 = readRawVarint32();
            if (rawVarint32 > 0) {
                long j2 = rawVarint32;
                long j3 = this.currentByteBufferLimit;
                long j4 = this.currentByteBufferPos;
                if (j2 <= j3 - j4) {
                    if (this.immutable && this.enableAliasing) {
                        int i2 = (int) (j4 - this.currentAddress);
                        ByteString byteStringWrap = ByteString.wrap(slice(i2, rawVarint32 + i2));
                        this.currentByteBufferPos += j2;
                        return byteStringWrap;
                    }
                    byte[] bArr = new byte[rawVarint32];
                    UnsafeUtil.copyMemory(j4, bArr, 0L, j2);
                    this.currentByteBufferPos += j2;
                    return ByteString.wrap(bArr);
                }
            }
            if (rawVarint32 > 0 && rawVarint32 <= remaining()) {
                byte[] bArr2 = new byte[rawVarint32];
                readRawBytesTo(bArr2, 0, rawVarint32);
                return ByteString.wrap(bArr2);
            }
            if (rawVarint32 == 0) {
                return ByteString.EMPTY;
            }
            if (rawVarint32 < 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            throw InvalidProtocolBufferException.truncatedMessage();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public double readDouble() throws IOException {
            return Double.longBitsToDouble(readRawLittleEndian64());
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readEnum() throws IOException {
            return readRawVarint32();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readFixed32() throws IOException {
            return readRawLittleEndian32();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public long readFixed64() throws IOException {
            return readRawLittleEndian64();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public float readFloat() throws IOException {
            return Float.intBitsToFloat(readRawLittleEndian32());
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void readGroup(int i2, MessageLite.Builder builder, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            int i3 = this.recursionDepth;
            if (i3 >= this.recursionLimit) {
                throw InvalidProtocolBufferException.recursionLimitExceeded();
            }
            this.recursionDepth = i3 + 1;
            builder.mergeFrom(this, extensionRegistryLite);
            checkLastTagWas(WireFormat.makeTag(i2, 4));
            this.recursionDepth--;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readInt32() throws IOException {
            return readRawVarint32();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public long readInt64() throws IOException {
            return readRawVarint64();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void readMessage(MessageLite.Builder builder, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            int rawVarint32 = readRawVarint32();
            if (this.recursionDepth >= this.recursionLimit) {
                throw InvalidProtocolBufferException.recursionLimitExceeded();
            }
            int iPushLimit = pushLimit(rawVarint32);
            this.recursionDepth++;
            builder.mergeFrom(this, extensionRegistryLite);
            checkLastTagWas(0);
            this.recursionDepth--;
            popLimit(iPushLimit);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public byte readRawByte() throws IOException {
            if (currentRemaining() == 0) {
                getNextByteBuffer();
            }
            long j2 = this.currentByteBufferPos;
            this.currentByteBufferPos = 1 + j2;
            return UnsafeUtil.getByte(j2);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public byte[] readRawBytes(int i2) throws IOException {
            if (i2 >= 0) {
                long j2 = i2;
                if (j2 <= currentRemaining()) {
                    byte[] bArr = new byte[i2];
                    UnsafeUtil.copyMemory(this.currentByteBufferPos, bArr, 0L, j2);
                    this.currentByteBufferPos += j2;
                    return bArr;
                }
            }
            if (i2 >= 0 && i2 <= remaining()) {
                byte[] bArr2 = new byte[i2];
                readRawBytesTo(bArr2, 0, i2);
                return bArr2;
            }
            if (i2 > 0) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            if (i2 == 0) {
                return Internal.EMPTY_BYTE_ARRAY;
            }
            throw InvalidProtocolBufferException.negativeSize();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readRawLittleEndian32() throws IOException {
            if (currentRemaining() < 4) {
                return (readRawByte() & 255) | ((readRawByte() & 255) << 8) | ((readRawByte() & 255) << 16) | ((readRawByte() & 255) << 24);
            }
            long j2 = this.currentByteBufferPos;
            this.currentByteBufferPos = 4 + j2;
            return ((UnsafeUtil.getByte(j2 + 3) & 255) << 24) | (UnsafeUtil.getByte(j2) & 255) | ((UnsafeUtil.getByte(1 + j2) & 255) << 8) | ((UnsafeUtil.getByte(2 + j2) & 255) << 16);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public long readRawLittleEndian64() throws IOException {
            long rawByte;
            byte rawByte2;
            if (currentRemaining() >= 8) {
                long j2 = this.currentByteBufferPos;
                this.currentByteBufferPos = 8 + j2;
                rawByte = (UnsafeUtil.getByte(j2) & 255) | ((UnsafeUtil.getByte(1 + j2) & 255) << 8) | ((UnsafeUtil.getByte(2 + j2) & 255) << 16) | ((UnsafeUtil.getByte(3 + j2) & 255) << 24) | ((UnsafeUtil.getByte(4 + j2) & 255) << 32) | ((UnsafeUtil.getByte(5 + j2) & 255) << 40) | ((UnsafeUtil.getByte(6 + j2) & 255) << 48);
                rawByte2 = UnsafeUtil.getByte(j2 + 7);
            } else {
                rawByte = (readRawByte() & 255) | ((readRawByte() & 255) << 8) | ((readRawByte() & 255) << 16) | ((readRawByte() & 255) << 24) | ((readRawByte() & 255) << 32) | ((readRawByte() & 255) << 40) | ((readRawByte() & 255) << 48);
                rawByte2 = readRawByte();
            }
            return ((rawByte2 & 255) << 56) | rawByte;
        }

        /* JADX WARN: Code restructure failed: missing block: B:33:0x0088, code lost:
        
            if (androidx.datastore.preferences.protobuf.UnsafeUtil.getByte(r4) < 0) goto L34;
         */
        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public int readRawVarint32() throws java.io.IOException {
            /*
                r10 = this;
                long r0 = r10.currentByteBufferPos
                long r2 = r10.currentByteBufferLimit
                int r2 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
                if (r2 != 0) goto La
                goto L8a
            La:
                r2 = 1
                long r4 = r0 + r2
                byte r0 = androidx.datastore.preferences.protobuf.UnsafeUtil.getByte(r0)
                if (r0 < 0) goto L1a
                long r4 = r10.currentByteBufferPos
                long r4 = r4 + r2
                r10.currentByteBufferPos = r4
                return r0
            L1a:
                long r6 = r10.currentByteBufferLimit
                long r8 = r10.currentByteBufferPos
                long r6 = r6 - r8
                r8 = 10
                int r1 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
                if (r1 >= 0) goto L26
                goto L8a
            L26:
                long r6 = r4 + r2
                byte r1 = androidx.datastore.preferences.protobuf.UnsafeUtil.getByte(r4)
                int r1 = r1 << 7
                r0 = r0 ^ r1
                if (r0 >= 0) goto L34
                r0 = r0 ^ (-128(0xffffffffffffff80, float:NaN))
                goto L90
            L34:
                long r4 = r6 + r2
                byte r1 = androidx.datastore.preferences.protobuf.UnsafeUtil.getByte(r6)
                int r1 = r1 << 14
                r0 = r0 ^ r1
                if (r0 < 0) goto L43
                r0 = r0 ^ 16256(0x3f80, float:2.278E-41)
            L41:
                r6 = r4
                goto L90
            L43:
                long r6 = r4 + r2
                byte r1 = androidx.datastore.preferences.protobuf.UnsafeUtil.getByte(r4)
                int r1 = r1 << 21
                r0 = r0 ^ r1
                if (r0 >= 0) goto L53
                r1 = -2080896(0xffffffffffe03f80, float:NaN)
                r0 = r0 ^ r1
                goto L90
            L53:
                long r4 = r6 + r2
                byte r1 = androidx.datastore.preferences.protobuf.UnsafeUtil.getByte(r6)
                int r6 = r1 << 28
                r0 = r0 ^ r6
                r6 = 266354560(0xfe03f80, float:2.2112565E-29)
                r0 = r0 ^ r6
                if (r1 >= 0) goto L41
                long r6 = r4 + r2
                byte r1 = androidx.datastore.preferences.protobuf.UnsafeUtil.getByte(r4)
                if (r1 >= 0) goto L90
                long r4 = r6 + r2
                byte r1 = androidx.datastore.preferences.protobuf.UnsafeUtil.getByte(r6)
                if (r1 >= 0) goto L41
                long r6 = r4 + r2
                byte r1 = androidx.datastore.preferences.protobuf.UnsafeUtil.getByte(r4)
                if (r1 >= 0) goto L90
                long r4 = r6 + r2
                byte r1 = androidx.datastore.preferences.protobuf.UnsafeUtil.getByte(r6)
                if (r1 >= 0) goto L41
                long r6 = r4 + r2
                byte r1 = androidx.datastore.preferences.protobuf.UnsafeUtil.getByte(r4)
                if (r1 >= 0) goto L90
            L8a:
                long r0 = r10.readRawVarint64SlowPath()
                int r0 = (int) r0
                return r0
            L90:
                r10.currentByteBufferPos = r6
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.preferences.protobuf.CodedInputStream.IterableDirectByteBufferDecoder.readRawVarint32():int");
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public long readRawVarint64() throws IOException {
            long j2;
            long j3;
            long j4;
            int i2;
            long j5 = this.currentByteBufferPos;
            if (this.currentByteBufferLimit != j5) {
                long j6 = j5 + 1;
                byte b3 = UnsafeUtil.getByte(j5);
                if (b3 >= 0) {
                    this.currentByteBufferPos++;
                    return b3;
                }
                if (this.currentByteBufferLimit - this.currentByteBufferPos >= 10) {
                    long j7 = j6 + 1;
                    int i3 = b3 ^ (UnsafeUtil.getByte(j6) << 7);
                    if (i3 >= 0) {
                        long j8 = j7 + 1;
                        int i4 = i3 ^ (UnsafeUtil.getByte(j7) << 14);
                        if (i4 >= 0) {
                            j2 = i4 ^ R2.id.ll_answer_enter;
                        } else {
                            j7 = j8 + 1;
                            int i5 = i4 ^ (UnsafeUtil.getByte(j8) << 21);
                            if (i5 < 0) {
                                i2 = i5 ^ (-2080896);
                            } else {
                                j8 = j7 + 1;
                                long j9 = i5 ^ (UnsafeUtil.getByte(j7) << 28);
                                if (j9 < 0) {
                                    long j10 = j8 + 1;
                                    long j11 = j9 ^ (UnsafeUtil.getByte(j8) << 35);
                                    if (j11 < 0) {
                                        j3 = -34093383808L;
                                    } else {
                                        j8 = j10 + 1;
                                        j9 = j11 ^ (UnsafeUtil.getByte(j10) << 42);
                                        if (j9 >= 0) {
                                            j4 = 4363953127296L;
                                        } else {
                                            j10 = j8 + 1;
                                            j11 = j9 ^ (UnsafeUtil.getByte(j8) << 49);
                                            if (j11 >= 0) {
                                                j8 = j10 + 1;
                                                j2 = (j11 ^ (UnsafeUtil.getByte(j10) << 56)) ^ 71499008037633920L;
                                                if (j2 < 0) {
                                                    long j12 = 1 + j8;
                                                    if (UnsafeUtil.getByte(j8) >= 0) {
                                                        j7 = j12;
                                                    }
                                                }
                                                this.currentByteBufferPos = j7;
                                                return j2;
                                            }
                                            j3 = -558586000294016L;
                                        }
                                    }
                                    j2 = j11 ^ j3;
                                    j7 = j10;
                                    this.currentByteBufferPos = j7;
                                    return j2;
                                }
                                j4 = 266354560;
                                j2 = j9 ^ j4;
                            }
                        }
                        j7 = j8;
                        this.currentByteBufferPos = j7;
                        return j2;
                    }
                    i2 = i3 ^ (-128);
                    j2 = i2;
                    this.currentByteBufferPos = j7;
                    return j2;
                }
            }
            return readRawVarint64SlowPath();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public long readRawVarint64SlowPath() throws IOException {
            long j2 = 0;
            for (int i2 = 0; i2 < 64; i2 += 7) {
                j2 |= (r3 & 127) << i2;
                if ((readRawByte() & 128) == 0) {
                    return j2;
                }
            }
            throw InvalidProtocolBufferException.malformedVarint();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readSFixed32() throws IOException {
            return readRawLittleEndian32();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public long readSFixed64() throws IOException {
            return readRawLittleEndian64();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readSInt32() throws IOException {
            return CodedInputStream.decodeZigZag32(readRawVarint32());
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public long readSInt64() throws IOException {
            return CodedInputStream.decodeZigZag64(readRawVarint64());
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public String readString() throws IOException {
            int rawVarint32 = readRawVarint32();
            if (rawVarint32 > 0) {
                long j2 = rawVarint32;
                long j3 = this.currentByteBufferLimit;
                long j4 = this.currentByteBufferPos;
                if (j2 <= j3 - j4) {
                    byte[] bArr = new byte[rawVarint32];
                    UnsafeUtil.copyMemory(j4, bArr, 0L, j2);
                    String str = new String(bArr, Internal.UTF_8);
                    this.currentByteBufferPos += j2;
                    return str;
                }
            }
            if (rawVarint32 > 0 && rawVarint32 <= remaining()) {
                byte[] bArr2 = new byte[rawVarint32];
                readRawBytesTo(bArr2, 0, rawVarint32);
                return new String(bArr2, Internal.UTF_8);
            }
            if (rawVarint32 == 0) {
                return "";
            }
            if (rawVarint32 < 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            throw InvalidProtocolBufferException.truncatedMessage();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public String readStringRequireUtf8() throws IOException {
            int rawVarint32 = readRawVarint32();
            if (rawVarint32 > 0) {
                long j2 = rawVarint32;
                long j3 = this.currentByteBufferLimit;
                long j4 = this.currentByteBufferPos;
                if (j2 <= j3 - j4) {
                    String strDecodeUtf8 = Utf8.decodeUtf8(this.currentByteBuffer, (int) (j4 - this.currentByteBufferStartPos), rawVarint32);
                    this.currentByteBufferPos += j2;
                    return strDecodeUtf8;
                }
            }
            if (rawVarint32 >= 0 && rawVarint32 <= remaining()) {
                byte[] bArr = new byte[rawVarint32];
                readRawBytesTo(bArr, 0, rawVarint32);
                return Utf8.decodeUtf8(bArr, 0, rawVarint32);
            }
            if (rawVarint32 == 0) {
                return "";
            }
            if (rawVarint32 <= 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            throw InvalidProtocolBufferException.truncatedMessage();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readTag() throws IOException {
            if (isAtEnd()) {
                this.lastTag = 0;
                return 0;
            }
            int rawVarint32 = readRawVarint32();
            this.lastTag = rawVarint32;
            if (WireFormat.getTagFieldNumber(rawVarint32) != 0) {
                return this.lastTag;
            }
            throw InvalidProtocolBufferException.invalidTag();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readUInt32() throws IOException {
            return readRawVarint32();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public long readUInt64() throws IOException {
            return readRawVarint64();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        @Deprecated
        public void readUnknownGroup(int i2, MessageLite.Builder builder) throws IOException {
            readGroup(i2, builder, ExtensionRegistryLite.getEmptyRegistry());
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void resetSizeCounter() {
            this.startOffset = (int) ((this.totalBytesRead + this.currentByteBufferPos) - this.currentByteBufferStartPos);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public boolean skipField(int i2) throws IOException {
            int tagWireType = WireFormat.getTagWireType(i2);
            if (tagWireType == 0) {
                skipRawVarint();
                return true;
            }
            if (tagWireType == 1) {
                skipRawBytes(8);
                return true;
            }
            if (tagWireType == 2) {
                skipRawBytes(readRawVarint32());
                return true;
            }
            if (tagWireType == 3) {
                skipMessage();
                checkLastTagWas(WireFormat.makeTag(WireFormat.getTagFieldNumber(i2), 4));
                return true;
            }
            if (tagWireType == 4) {
                return false;
            }
            if (tagWireType != 5) {
                throw InvalidProtocolBufferException.invalidWireType();
            }
            skipRawBytes(4);
            return true;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void skipMessage() throws IOException {
            int tag;
            do {
                tag = readTag();
                if (tag == 0) {
                    return;
                }
            } while (skipField(tag));
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void skipRawBytes(int i2) throws IOException {
            if (i2 < 0 || i2 > ((this.totalBufferSize - this.totalBytesRead) - this.currentByteBufferPos) + this.currentByteBufferStartPos) {
                if (i2 >= 0) {
                    throw InvalidProtocolBufferException.truncatedMessage();
                }
                throw InvalidProtocolBufferException.negativeSize();
            }
            while (i2 > 0) {
                if (currentRemaining() == 0) {
                    getNextByteBuffer();
                }
                int iMin = Math.min(i2, (int) currentRemaining());
                i2 -= iMin;
                this.currentByteBufferPos += iMin;
            }
        }

        private IterableDirectByteBufferDecoder(Iterable<ByteBuffer> iterable, int i2, boolean z2) {
            super();
            this.currentLimit = Integer.MAX_VALUE;
            this.totalBufferSize = i2;
            this.input = iterable;
            this.iterator = iterable.iterator();
            this.immutable = z2;
            this.totalBytesRead = 0;
            this.startOffset = 0;
            if (i2 != 0) {
                tryGetNextByteBuffer();
                return;
            }
            this.currentByteBuffer = Internal.EMPTY_BYTE_BUFFER;
            this.currentByteBufferPos = 0L;
            this.currentByteBufferStartPos = 0L;
            this.currentByteBufferLimit = 0L;
            this.currentAddress = 0L;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void skipMessage(CodedOutputStream codedOutputStream) throws IOException {
            int tag;
            do {
                tag = readTag();
                if (tag == 0) {
                    return;
                }
            } while (skipField(tag, codedOutputStream));
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public <T extends MessageLite> T readGroup(int i2, Parser<T> parser, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            int i3 = this.recursionDepth;
            if (i3 < this.recursionLimit) {
                this.recursionDepth = i3 + 1;
                T partialFrom = parser.parsePartialFrom(this, extensionRegistryLite);
                checkLastTagWas(WireFormat.makeTag(i2, 4));
                this.recursionDepth--;
                return partialFrom;
            }
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public <T extends MessageLite> T readMessage(Parser<T> parser, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            int rawVarint32 = readRawVarint32();
            if (this.recursionDepth < this.recursionLimit) {
                int iPushLimit = pushLimit(rawVarint32);
                this.recursionDepth++;
                T partialFrom = parser.parsePartialFrom(this, extensionRegistryLite);
                checkLastTagWas(0);
                this.recursionDepth--;
                popLimit(iPushLimit);
                return partialFrom;
            }
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public boolean skipField(int i2, CodedOutputStream codedOutputStream) throws IOException {
            int tagWireType = WireFormat.getTagWireType(i2);
            if (tagWireType == 0) {
                long int64 = readInt64();
                codedOutputStream.writeRawVarint32(i2);
                codedOutputStream.writeUInt64NoTag(int64);
                return true;
            }
            if (tagWireType == 1) {
                long rawLittleEndian64 = readRawLittleEndian64();
                codedOutputStream.writeRawVarint32(i2);
                codedOutputStream.writeFixed64NoTag(rawLittleEndian64);
                return true;
            }
            if (tagWireType == 2) {
                ByteString bytes = readBytes();
                codedOutputStream.writeRawVarint32(i2);
                codedOutputStream.writeBytesNoTag(bytes);
                return true;
            }
            if (tagWireType == 3) {
                codedOutputStream.writeRawVarint32(i2);
                skipMessage(codedOutputStream);
                int iMakeTag = WireFormat.makeTag(WireFormat.getTagFieldNumber(i2), 4);
                checkLastTagWas(iMakeTag);
                codedOutputStream.writeRawVarint32(iMakeTag);
                return true;
            }
            if (tagWireType == 4) {
                return false;
            }
            if (tagWireType == 5) {
                int rawLittleEndian32 = readRawLittleEndian32();
                codedOutputStream.writeRawVarint32(i2);
                codedOutputStream.writeFixed32NoTag(rawLittleEndian32);
                return true;
            }
            throw InvalidProtocolBufferException.invalidWireType();
        }
    }

    public static final class StreamDecoder extends CodedInputStream {
        private final byte[] buffer;
        private int bufferSize;
        private int bufferSizeAfterLimit;
        private int currentLimit;
        private final InputStream input;
        private int lastTag;
        private int pos;
        private RefillCallback refillCallback;
        private int totalBytesRetired;

        public interface RefillCallback {
            void onRefill();
        }

        public class SkippedDataSink implements RefillCallback {
            private ByteArrayOutputStream byteArrayStream;
            private int lastPos;

            private SkippedDataSink() {
                this.lastPos = StreamDecoder.this.pos;
            }

            public ByteBuffer getSkippedData() {
                ByteArrayOutputStream byteArrayOutputStream = this.byteArrayStream;
                if (byteArrayOutputStream == null) {
                    return ByteBuffer.wrap(StreamDecoder.this.buffer, this.lastPos, StreamDecoder.this.pos - this.lastPos);
                }
                byteArrayOutputStream.write(StreamDecoder.this.buffer, this.lastPos, StreamDecoder.this.pos);
                return ByteBuffer.wrap(this.byteArrayStream.toByteArray());
            }

            @Override // androidx.datastore.preferences.protobuf.CodedInputStream.StreamDecoder.RefillCallback
            public void onRefill() {
                if (this.byteArrayStream == null) {
                    this.byteArrayStream = new ByteArrayOutputStream();
                }
                this.byteArrayStream.write(StreamDecoder.this.buffer, this.lastPos, StreamDecoder.this.pos - this.lastPos);
                this.lastPos = 0;
            }
        }

        private ByteString readBytesSlowPath(int i2) throws IOException {
            byte[] rawBytesSlowPathOneChunk = readRawBytesSlowPathOneChunk(i2);
            if (rawBytesSlowPathOneChunk != null) {
                return ByteString.copyFrom(rawBytesSlowPathOneChunk);
            }
            int i3 = this.pos;
            int i4 = this.bufferSize;
            int length = i4 - i3;
            this.totalBytesRetired += i4;
            this.pos = 0;
            this.bufferSize = 0;
            List<byte[]> rawBytesSlowPathRemainingChunks = readRawBytesSlowPathRemainingChunks(i2 - length);
            byte[] bArr = new byte[i2];
            System.arraycopy(this.buffer, i3, bArr, 0, length);
            for (byte[] bArr2 : rawBytesSlowPathRemainingChunks) {
                System.arraycopy(bArr2, 0, bArr, length, bArr2.length);
                length += bArr2.length;
            }
            return ByteString.wrap(bArr);
        }

        private byte[] readRawBytesSlowPath(int i2, boolean z2) throws IOException {
            byte[] rawBytesSlowPathOneChunk = readRawBytesSlowPathOneChunk(i2);
            if (rawBytesSlowPathOneChunk != null) {
                return z2 ? (byte[]) rawBytesSlowPathOneChunk.clone() : rawBytesSlowPathOneChunk;
            }
            int i3 = this.pos;
            int i4 = this.bufferSize;
            int length = i4 - i3;
            this.totalBytesRetired += i4;
            this.pos = 0;
            this.bufferSize = 0;
            List<byte[]> rawBytesSlowPathRemainingChunks = readRawBytesSlowPathRemainingChunks(i2 - length);
            byte[] bArr = new byte[i2];
            System.arraycopy(this.buffer, i3, bArr, 0, length);
            for (byte[] bArr2 : rawBytesSlowPathRemainingChunks) {
                System.arraycopy(bArr2, 0, bArr, length, bArr2.length);
                length += bArr2.length;
            }
            return bArr;
        }

        private byte[] readRawBytesSlowPathOneChunk(int i2) throws IOException {
            if (i2 == 0) {
                return Internal.EMPTY_BYTE_ARRAY;
            }
            if (i2 < 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            int i3 = this.totalBytesRetired;
            int i4 = this.pos;
            int i5 = i3 + i4 + i2;
            if (i5 - this.sizeLimit > 0) {
                throw InvalidProtocolBufferException.sizeLimitExceeded();
            }
            int i6 = this.currentLimit;
            if (i5 > i6) {
                skipRawBytes((i6 - i3) - i4);
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            int i7 = this.bufferSize - i4;
            int i8 = i2 - i7;
            if (i8 >= 4096 && i8 > this.input.available()) {
                return null;
            }
            byte[] bArr = new byte[i2];
            System.arraycopy(this.buffer, this.pos, bArr, 0, i7);
            this.totalBytesRetired += this.bufferSize;
            this.pos = 0;
            this.bufferSize = 0;
            while (i7 < i2) {
                int i9 = this.input.read(bArr, i7, i2 - i7);
                if (i9 == -1) {
                    throw InvalidProtocolBufferException.truncatedMessage();
                }
                this.totalBytesRetired += i9;
                i7 += i9;
            }
            return bArr;
        }

        private List<byte[]> readRawBytesSlowPathRemainingChunks(int i2) throws IOException {
            ArrayList arrayList = new ArrayList();
            while (i2 > 0) {
                int iMin = Math.min(i2, 4096);
                byte[] bArr = new byte[iMin];
                int i3 = 0;
                while (i3 < iMin) {
                    int i4 = this.input.read(bArr, i3, iMin - i3);
                    if (i4 == -1) {
                        throw InvalidProtocolBufferException.truncatedMessage();
                    }
                    this.totalBytesRetired += i4;
                    i3 += i4;
                }
                i2 -= iMin;
                arrayList.add(bArr);
            }
            return arrayList;
        }

        private void recomputeBufferSizeAfterLimit() {
            int i2 = this.bufferSize + this.bufferSizeAfterLimit;
            this.bufferSize = i2;
            int i3 = this.totalBytesRetired + i2;
            int i4 = this.currentLimit;
            if (i3 <= i4) {
                this.bufferSizeAfterLimit = 0;
                return;
            }
            int i5 = i3 - i4;
            this.bufferSizeAfterLimit = i5;
            this.bufferSize = i2 - i5;
        }

        private void refillBuffer(int i2) throws IOException {
            if (tryRefillBuffer(i2)) {
                return;
            }
            if (i2 <= (this.sizeLimit - this.totalBytesRetired) - this.pos) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            throw InvalidProtocolBufferException.sizeLimitExceeded();
        }

        private void skipRawBytesSlowPath(int i2) throws IOException {
            if (i2 < 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            int i3 = this.totalBytesRetired;
            int i4 = this.pos;
            int i5 = i3 + i4 + i2;
            int i6 = this.currentLimit;
            if (i5 > i6) {
                skipRawBytes((i6 - i3) - i4);
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            int i7 = 0;
            if (this.refillCallback == null) {
                this.totalBytesRetired = i3 + i4;
                int i8 = this.bufferSize - i4;
                this.bufferSize = 0;
                this.pos = 0;
                i7 = i8;
                while (i7 < i2) {
                    try {
                        long j2 = i2 - i7;
                        long jSkip = this.input.skip(j2);
                        if (jSkip < 0 || jSkip > j2) {
                            throw new IllegalStateException(this.input.getClass() + "#skip returned invalid result: " + jSkip + "\nThe InputStream implementation is buggy.");
                        }
                        if (jSkip == 0) {
                            break;
                        } else {
                            i7 += (int) jSkip;
                        }
                    } finally {
                        this.totalBytesRetired += i7;
                        recomputeBufferSizeAfterLimit();
                    }
                }
            }
            if (i7 >= i2) {
                return;
            }
            int i9 = this.bufferSize;
            int i10 = i9 - this.pos;
            this.pos = i9;
            refillBuffer(1);
            while (true) {
                int i11 = i2 - i10;
                int i12 = this.bufferSize;
                if (i11 <= i12) {
                    this.pos = i11;
                    return;
                } else {
                    i10 += i12;
                    this.pos = i12;
                    refillBuffer(1);
                }
            }
        }

        private void skipRawVarint() throws IOException {
            if (this.bufferSize - this.pos >= 10) {
                skipRawVarintFastPath();
            } else {
                skipRawVarintSlowPath();
            }
        }

        private void skipRawVarintFastPath() throws IOException {
            for (int i2 = 0; i2 < 10; i2++) {
                byte[] bArr = this.buffer;
                int i3 = this.pos;
                this.pos = i3 + 1;
                if (bArr[i3] >= 0) {
                    return;
                }
            }
            throw InvalidProtocolBufferException.malformedVarint();
        }

        private void skipRawVarintSlowPath() throws IOException {
            for (int i2 = 0; i2 < 10; i2++) {
                if (readRawByte() >= 0) {
                    return;
                }
            }
            throw InvalidProtocolBufferException.malformedVarint();
        }

        private boolean tryRefillBuffer(int i2) throws IOException {
            int i3 = this.pos;
            if (i3 + i2 <= this.bufferSize) {
                throw new IllegalStateException("refillBuffer() called when " + i2 + " bytes were already available in buffer");
            }
            int i4 = this.sizeLimit;
            int i5 = this.totalBytesRetired;
            if (i2 > (i4 - i5) - i3 || i5 + i3 + i2 > this.currentLimit) {
                return false;
            }
            RefillCallback refillCallback = this.refillCallback;
            if (refillCallback != null) {
                refillCallback.onRefill();
            }
            int i6 = this.pos;
            if (i6 > 0) {
                int i7 = this.bufferSize;
                if (i7 > i6) {
                    byte[] bArr = this.buffer;
                    System.arraycopy(bArr, i6, bArr, 0, i7 - i6);
                }
                this.totalBytesRetired += i6;
                this.bufferSize -= i6;
                this.pos = 0;
            }
            InputStream inputStream = this.input;
            byte[] bArr2 = this.buffer;
            int i8 = this.bufferSize;
            int i9 = inputStream.read(bArr2, i8, Math.min(bArr2.length - i8, (this.sizeLimit - this.totalBytesRetired) - i8));
            if (i9 == 0 || i9 < -1 || i9 > this.buffer.length) {
                throw new IllegalStateException(this.input.getClass() + "#read(byte[]) returned invalid result: " + i9 + "\nThe InputStream implementation is buggy.");
            }
            if (i9 <= 0) {
                return false;
            }
            this.bufferSize += i9;
            recomputeBufferSizeAfterLimit();
            if (this.bufferSize >= i2) {
                return true;
            }
            return tryRefillBuffer(i2);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void checkLastTagWas(int i2) throws InvalidProtocolBufferException {
            if (this.lastTag != i2) {
                throw InvalidProtocolBufferException.invalidEndTag();
            }
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void enableAliasing(boolean z2) {
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int getBytesUntilLimit() {
            int i2 = this.currentLimit;
            if (i2 == Integer.MAX_VALUE) {
                return -1;
            }
            return i2 - (this.totalBytesRetired + this.pos);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int getLastTag() {
            return this.lastTag;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int getTotalBytesRead() {
            return this.totalBytesRetired + this.pos;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public boolean isAtEnd() throws IOException {
            return this.pos == this.bufferSize && !tryRefillBuffer(1);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void popLimit(int i2) {
            this.currentLimit = i2;
            recomputeBufferSizeAfterLimit();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int pushLimit(int i2) throws InvalidProtocolBufferException {
            if (i2 < 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            int i3 = i2 + this.totalBytesRetired + this.pos;
            int i4 = this.currentLimit;
            if (i3 > i4) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            this.currentLimit = i3;
            recomputeBufferSizeAfterLimit();
            return i4;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public boolean readBool() throws IOException {
            return readRawVarint64() != 0;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public byte[] readByteArray() throws IOException {
            int rawVarint32 = readRawVarint32();
            int i2 = this.bufferSize;
            int i3 = this.pos;
            if (rawVarint32 > i2 - i3 || rawVarint32 <= 0) {
                return readRawBytesSlowPath(rawVarint32, false);
            }
            byte[] bArrCopyOfRange = Arrays.copyOfRange(this.buffer, i3, i3 + rawVarint32);
            this.pos += rawVarint32;
            return bArrCopyOfRange;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public ByteBuffer readByteBuffer() throws IOException {
            int rawVarint32 = readRawVarint32();
            int i2 = this.bufferSize;
            int i3 = this.pos;
            if (rawVarint32 > i2 - i3 || rawVarint32 <= 0) {
                return rawVarint32 == 0 ? Internal.EMPTY_BYTE_BUFFER : ByteBuffer.wrap(readRawBytesSlowPath(rawVarint32, true));
            }
            ByteBuffer byteBufferWrap = ByteBuffer.wrap(Arrays.copyOfRange(this.buffer, i3, i3 + rawVarint32));
            this.pos += rawVarint32;
            return byteBufferWrap;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public ByteString readBytes() throws IOException {
            int rawVarint32 = readRawVarint32();
            int i2 = this.bufferSize;
            int i3 = this.pos;
            if (rawVarint32 > i2 - i3 || rawVarint32 <= 0) {
                return rawVarint32 == 0 ? ByteString.EMPTY : readBytesSlowPath(rawVarint32);
            }
            ByteString byteStringCopyFrom = ByteString.copyFrom(this.buffer, i3, rawVarint32);
            this.pos += rawVarint32;
            return byteStringCopyFrom;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public double readDouble() throws IOException {
            return Double.longBitsToDouble(readRawLittleEndian64());
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readEnum() throws IOException {
            return readRawVarint32();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readFixed32() throws IOException {
            return readRawLittleEndian32();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public long readFixed64() throws IOException {
            return readRawLittleEndian64();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public float readFloat() throws IOException {
            return Float.intBitsToFloat(readRawLittleEndian32());
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void readGroup(int i2, MessageLite.Builder builder, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            int i3 = this.recursionDepth;
            if (i3 >= this.recursionLimit) {
                throw InvalidProtocolBufferException.recursionLimitExceeded();
            }
            this.recursionDepth = i3 + 1;
            builder.mergeFrom(this, extensionRegistryLite);
            checkLastTagWas(WireFormat.makeTag(i2, 4));
            this.recursionDepth--;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readInt32() throws IOException {
            return readRawVarint32();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public long readInt64() throws IOException {
            return readRawVarint64();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void readMessage(MessageLite.Builder builder, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            int rawVarint32 = readRawVarint32();
            if (this.recursionDepth >= this.recursionLimit) {
                throw InvalidProtocolBufferException.recursionLimitExceeded();
            }
            int iPushLimit = pushLimit(rawVarint32);
            this.recursionDepth++;
            builder.mergeFrom(this, extensionRegistryLite);
            checkLastTagWas(0);
            this.recursionDepth--;
            popLimit(iPushLimit);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public byte readRawByte() throws IOException {
            if (this.pos == this.bufferSize) {
                refillBuffer(1);
            }
            byte[] bArr = this.buffer;
            int i2 = this.pos;
            this.pos = i2 + 1;
            return bArr[i2];
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public byte[] readRawBytes(int i2) throws IOException {
            int i3 = this.pos;
            if (i2 > this.bufferSize - i3 || i2 <= 0) {
                return readRawBytesSlowPath(i2, false);
            }
            int i4 = i2 + i3;
            this.pos = i4;
            return Arrays.copyOfRange(this.buffer, i3, i4);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readRawLittleEndian32() throws IOException {
            int i2 = this.pos;
            if (this.bufferSize - i2 < 4) {
                refillBuffer(4);
                i2 = this.pos;
            }
            byte[] bArr = this.buffer;
            this.pos = i2 + 4;
            return ((bArr[i2 + 3] & 255) << 24) | (bArr[i2] & 255) | ((bArr[i2 + 1] & 255) << 8) | ((bArr[i2 + 2] & 255) << 16);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public long readRawLittleEndian64() throws IOException {
            int i2 = this.pos;
            if (this.bufferSize - i2 < 8) {
                refillBuffer(8);
                i2 = this.pos;
            }
            byte[] bArr = this.buffer;
            this.pos = i2 + 8;
            return ((bArr[i2 + 7] & 255) << 56) | (bArr[i2] & 255) | ((bArr[i2 + 1] & 255) << 8) | ((bArr[i2 + 2] & 255) << 16) | ((bArr[i2 + 3] & 255) << 24) | ((bArr[i2 + 4] & 255) << 32) | ((bArr[i2 + 5] & 255) << 40) | ((bArr[i2 + 6] & 255) << 48);
        }

        /* JADX WARN: Code restructure failed: missing block: B:33:0x0068, code lost:
        
            if (r2[r3] < 0) goto L34;
         */
        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public int readRawVarint32() throws java.io.IOException {
            /*
                r5 = this;
                int r0 = r5.pos
                int r1 = r5.bufferSize
                if (r1 != r0) goto L7
                goto L6a
            L7:
                byte[] r2 = r5.buffer
                int r3 = r0 + 1
                r0 = r2[r0]
                if (r0 < 0) goto L12
                r5.pos = r3
                return r0
            L12:
                int r1 = r1 - r3
                r4 = 9
                if (r1 >= r4) goto L18
                goto L6a
            L18:
                int r1 = r3 + 1
                r3 = r2[r3]
                int r3 = r3 << 7
                r0 = r0 ^ r3
                if (r0 >= 0) goto L24
                r0 = r0 ^ (-128(0xffffffffffffff80, float:NaN))
                goto L70
            L24:
                int r3 = r1 + 1
                r1 = r2[r1]
                int r1 = r1 << 14
                r0 = r0 ^ r1
                if (r0 < 0) goto L31
                r0 = r0 ^ 16256(0x3f80, float:2.278E-41)
            L2f:
                r1 = r3
                goto L70
            L31:
                int r1 = r3 + 1
                r3 = r2[r3]
                int r3 = r3 << 21
                r0 = r0 ^ r3
                if (r0 >= 0) goto L3f
                r2 = -2080896(0xffffffffffe03f80, float:NaN)
                r0 = r0 ^ r2
                goto L70
            L3f:
                int r3 = r1 + 1
                r1 = r2[r1]
                int r4 = r1 << 28
                r0 = r0 ^ r4
                r4 = 266354560(0xfe03f80, float:2.2112565E-29)
                r0 = r0 ^ r4
                if (r1 >= 0) goto L2f
                int r1 = r3 + 1
                r3 = r2[r3]
                if (r3 >= 0) goto L70
                int r3 = r1 + 1
                r1 = r2[r1]
                if (r1 >= 0) goto L2f
                int r1 = r3 + 1
                r3 = r2[r3]
                if (r3 >= 0) goto L70
                int r3 = r1 + 1
                r1 = r2[r1]
                if (r1 >= 0) goto L2f
                int r1 = r3 + 1
                r2 = r2[r3]
                if (r2 >= 0) goto L70
            L6a:
                long r0 = r5.readRawVarint64SlowPath()
                int r0 = (int) r0
                return r0
            L70:
                r5.pos = r1
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.preferences.protobuf.CodedInputStream.StreamDecoder.readRawVarint32():int");
        }

        /* JADX WARN: Code restructure failed: missing block: B:39:0x00b4, code lost:
        
            if (r2[r0] < 0) goto L40;
         */
        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public long readRawVarint64() throws java.io.IOException {
            /*
                Method dump skipped, instructions count: 192
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.preferences.protobuf.CodedInputStream.StreamDecoder.readRawVarint64():long");
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public long readRawVarint64SlowPath() throws IOException {
            long j2 = 0;
            for (int i2 = 0; i2 < 64; i2 += 7) {
                j2 |= (r3 & 127) << i2;
                if ((readRawByte() & 128) == 0) {
                    return j2;
                }
            }
            throw InvalidProtocolBufferException.malformedVarint();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readSFixed32() throws IOException {
            return readRawLittleEndian32();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public long readSFixed64() throws IOException {
            return readRawLittleEndian64();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readSInt32() throws IOException {
            return CodedInputStream.decodeZigZag32(readRawVarint32());
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public long readSInt64() throws IOException {
            return CodedInputStream.decodeZigZag64(readRawVarint64());
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public String readString() throws IOException {
            int rawVarint32 = readRawVarint32();
            if (rawVarint32 > 0) {
                int i2 = this.bufferSize;
                int i3 = this.pos;
                if (rawVarint32 <= i2 - i3) {
                    String str = new String(this.buffer, i3, rawVarint32, Internal.UTF_8);
                    this.pos += rawVarint32;
                    return str;
                }
            }
            if (rawVarint32 == 0) {
                return "";
            }
            if (rawVarint32 > this.bufferSize) {
                return new String(readRawBytesSlowPath(rawVarint32, false), Internal.UTF_8);
            }
            refillBuffer(rawVarint32);
            String str2 = new String(this.buffer, this.pos, rawVarint32, Internal.UTF_8);
            this.pos += rawVarint32;
            return str2;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public String readStringRequireUtf8() throws IOException {
            byte[] rawBytesSlowPath;
            int rawVarint32 = readRawVarint32();
            int i2 = this.pos;
            int i3 = this.bufferSize;
            if (rawVarint32 <= i3 - i2 && rawVarint32 > 0) {
                rawBytesSlowPath = this.buffer;
                this.pos = i2 + rawVarint32;
            } else {
                if (rawVarint32 == 0) {
                    return "";
                }
                i2 = 0;
                if (rawVarint32 <= i3) {
                    refillBuffer(rawVarint32);
                    rawBytesSlowPath = this.buffer;
                    this.pos = rawVarint32 + 0;
                } else {
                    rawBytesSlowPath = readRawBytesSlowPath(rawVarint32, false);
                }
            }
            return Utf8.decodeUtf8(rawBytesSlowPath, i2, rawVarint32);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readTag() throws IOException {
            if (isAtEnd()) {
                this.lastTag = 0;
                return 0;
            }
            int rawVarint32 = readRawVarint32();
            this.lastTag = rawVarint32;
            if (WireFormat.getTagFieldNumber(rawVarint32) != 0) {
                return this.lastTag;
            }
            throw InvalidProtocolBufferException.invalidTag();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readUInt32() throws IOException {
            return readRawVarint32();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public long readUInt64() throws IOException {
            return readRawVarint64();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        @Deprecated
        public void readUnknownGroup(int i2, MessageLite.Builder builder) throws IOException {
            readGroup(i2, builder, ExtensionRegistryLite.getEmptyRegistry());
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void resetSizeCounter() {
            this.totalBytesRetired = -this.pos;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public boolean skipField(int i2) throws IOException {
            int tagWireType = WireFormat.getTagWireType(i2);
            if (tagWireType == 0) {
                skipRawVarint();
                return true;
            }
            if (tagWireType == 1) {
                skipRawBytes(8);
                return true;
            }
            if (tagWireType == 2) {
                skipRawBytes(readRawVarint32());
                return true;
            }
            if (tagWireType == 3) {
                skipMessage();
                checkLastTagWas(WireFormat.makeTag(WireFormat.getTagFieldNumber(i2), 4));
                return true;
            }
            if (tagWireType == 4) {
                return false;
            }
            if (tagWireType != 5) {
                throw InvalidProtocolBufferException.invalidWireType();
            }
            skipRawBytes(4);
            return true;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void skipMessage() throws IOException {
            int tag;
            do {
                tag = readTag();
                if (tag == 0) {
                    return;
                }
            } while (skipField(tag));
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void skipRawBytes(int i2) throws IOException {
            int i3 = this.bufferSize;
            int i4 = this.pos;
            if (i2 > i3 - i4 || i2 < 0) {
                skipRawBytesSlowPath(i2);
            } else {
                this.pos = i4 + i2;
            }
        }

        private StreamDecoder(InputStream inputStream, int i2) {
            super();
            this.currentLimit = Integer.MAX_VALUE;
            this.refillCallback = null;
            Internal.checkNotNull(inputStream, "input");
            this.input = inputStream;
            this.buffer = new byte[i2];
            this.bufferSize = 0;
            this.pos = 0;
            this.totalBytesRetired = 0;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void skipMessage(CodedOutputStream codedOutputStream) throws IOException {
            int tag;
            do {
                tag = readTag();
                if (tag == 0) {
                    return;
                }
            } while (skipField(tag, codedOutputStream));
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public <T extends MessageLite> T readGroup(int i2, Parser<T> parser, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            int i3 = this.recursionDepth;
            if (i3 < this.recursionLimit) {
                this.recursionDepth = i3 + 1;
                T partialFrom = parser.parsePartialFrom(this, extensionRegistryLite);
                checkLastTagWas(WireFormat.makeTag(i2, 4));
                this.recursionDepth--;
                return partialFrom;
            }
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public <T extends MessageLite> T readMessage(Parser<T> parser, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            int rawVarint32 = readRawVarint32();
            if (this.recursionDepth < this.recursionLimit) {
                int iPushLimit = pushLimit(rawVarint32);
                this.recursionDepth++;
                T partialFrom = parser.parsePartialFrom(this, extensionRegistryLite);
                checkLastTagWas(0);
                this.recursionDepth--;
                popLimit(iPushLimit);
                return partialFrom;
            }
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public boolean skipField(int i2, CodedOutputStream codedOutputStream) throws IOException {
            int tagWireType = WireFormat.getTagWireType(i2);
            if (tagWireType == 0) {
                long int64 = readInt64();
                codedOutputStream.writeRawVarint32(i2);
                codedOutputStream.writeUInt64NoTag(int64);
                return true;
            }
            if (tagWireType == 1) {
                long rawLittleEndian64 = readRawLittleEndian64();
                codedOutputStream.writeRawVarint32(i2);
                codedOutputStream.writeFixed64NoTag(rawLittleEndian64);
                return true;
            }
            if (tagWireType == 2) {
                ByteString bytes = readBytes();
                codedOutputStream.writeRawVarint32(i2);
                codedOutputStream.writeBytesNoTag(bytes);
                return true;
            }
            if (tagWireType == 3) {
                codedOutputStream.writeRawVarint32(i2);
                skipMessage(codedOutputStream);
                int iMakeTag = WireFormat.makeTag(WireFormat.getTagFieldNumber(i2), 4);
                checkLastTagWas(iMakeTag);
                codedOutputStream.writeRawVarint32(iMakeTag);
                return true;
            }
            if (tagWireType == 4) {
                return false;
            }
            if (tagWireType == 5) {
                int rawLittleEndian32 = readRawLittleEndian32();
                codedOutputStream.writeRawVarint32(i2);
                codedOutputStream.writeFixed32NoTag(rawLittleEndian32);
                return true;
            }
            throw InvalidProtocolBufferException.invalidWireType();
        }
    }

    public static final class UnsafeDirectNioDecoder extends CodedInputStream {
        private final long address;
        private final ByteBuffer buffer;
        private int bufferSizeAfterLimit;
        private int currentLimit;
        private boolean enableAliasing;
        private final boolean immutable;
        private int lastTag;
        private long limit;
        private long pos;
        private long startPos;

        private int bufferPos(long j2) {
            return (int) (j2 - this.address);
        }

        public static boolean isSupported() {
            return UnsafeUtil.hasUnsafeByteBufferOperations();
        }

        private void recomputeBufferSizeAfterLimit() {
            long j2 = this.limit + this.bufferSizeAfterLimit;
            this.limit = j2;
            int i2 = (int) (j2 - this.startPos);
            int i3 = this.currentLimit;
            if (i2 <= i3) {
                this.bufferSizeAfterLimit = 0;
                return;
            }
            int i4 = i2 - i3;
            this.bufferSizeAfterLimit = i4;
            this.limit = j2 - i4;
        }

        private int remaining() {
            return (int) (this.limit - this.pos);
        }

        private void skipRawVarint() throws IOException {
            if (remaining() >= 10) {
                skipRawVarintFastPath();
            } else {
                skipRawVarintSlowPath();
            }
        }

        private void skipRawVarintFastPath() throws IOException {
            for (int i2 = 0; i2 < 10; i2++) {
                long j2 = this.pos;
                this.pos = 1 + j2;
                if (UnsafeUtil.getByte(j2) >= 0) {
                    return;
                }
            }
            throw InvalidProtocolBufferException.malformedVarint();
        }

        private void skipRawVarintSlowPath() throws IOException {
            for (int i2 = 0; i2 < 10; i2++) {
                if (readRawByte() >= 0) {
                    return;
                }
            }
            throw InvalidProtocolBufferException.malformedVarint();
        }

        private ByteBuffer slice(long j2, long j3) throws IOException {
            int iPosition = this.buffer.position();
            int iLimit = this.buffer.limit();
            try {
                try {
                    this.buffer.position(bufferPos(j2));
                    this.buffer.limit(bufferPos(j3));
                    return this.buffer.slice();
                } catch (IllegalArgumentException unused) {
                    throw InvalidProtocolBufferException.truncatedMessage();
                }
            } finally {
                this.buffer.position(iPosition);
                this.buffer.limit(iLimit);
            }
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void checkLastTagWas(int i2) throws InvalidProtocolBufferException {
            if (this.lastTag != i2) {
                throw InvalidProtocolBufferException.invalidEndTag();
            }
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void enableAliasing(boolean z2) {
            this.enableAliasing = z2;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int getBytesUntilLimit() {
            int i2 = this.currentLimit;
            if (i2 == Integer.MAX_VALUE) {
                return -1;
            }
            return i2 - getTotalBytesRead();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int getLastTag() {
            return this.lastTag;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int getTotalBytesRead() {
            return (int) (this.pos - this.startPos);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public boolean isAtEnd() throws IOException {
            return this.pos == this.limit;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void popLimit(int i2) {
            this.currentLimit = i2;
            recomputeBufferSizeAfterLimit();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int pushLimit(int i2) throws InvalidProtocolBufferException {
            if (i2 < 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            int totalBytesRead = i2 + getTotalBytesRead();
            int i3 = this.currentLimit;
            if (totalBytesRead > i3) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            this.currentLimit = totalBytesRead;
            recomputeBufferSizeAfterLimit();
            return i3;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public boolean readBool() throws IOException {
            return readRawVarint64() != 0;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public byte[] readByteArray() throws IOException {
            return readRawBytes(readRawVarint32());
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public ByteBuffer readByteBuffer() throws IOException {
            int rawVarint32 = readRawVarint32();
            if (rawVarint32 <= 0 || rawVarint32 > remaining()) {
                if (rawVarint32 == 0) {
                    return Internal.EMPTY_BYTE_BUFFER;
                }
                if (rawVarint32 < 0) {
                    throw InvalidProtocolBufferException.negativeSize();
                }
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            if (this.immutable || !this.enableAliasing) {
                byte[] bArr = new byte[rawVarint32];
                long j2 = rawVarint32;
                UnsafeUtil.copyMemory(this.pos, bArr, 0L, j2);
                this.pos += j2;
                return ByteBuffer.wrap(bArr);
            }
            long j3 = this.pos;
            long j4 = rawVarint32;
            ByteBuffer byteBufferSlice = slice(j3, j3 + j4);
            this.pos += j4;
            return byteBufferSlice;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public ByteString readBytes() throws IOException {
            int rawVarint32 = readRawVarint32();
            if (rawVarint32 <= 0 || rawVarint32 > remaining()) {
                if (rawVarint32 == 0) {
                    return ByteString.EMPTY;
                }
                if (rawVarint32 < 0) {
                    throw InvalidProtocolBufferException.negativeSize();
                }
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            if (this.immutable && this.enableAliasing) {
                long j2 = this.pos;
                long j3 = rawVarint32;
                ByteBuffer byteBufferSlice = slice(j2, j2 + j3);
                this.pos += j3;
                return ByteString.wrap(byteBufferSlice);
            }
            byte[] bArr = new byte[rawVarint32];
            long j4 = rawVarint32;
            UnsafeUtil.copyMemory(this.pos, bArr, 0L, j4);
            this.pos += j4;
            return ByteString.wrap(bArr);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public double readDouble() throws IOException {
            return Double.longBitsToDouble(readRawLittleEndian64());
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readEnum() throws IOException {
            return readRawVarint32();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readFixed32() throws IOException {
            return readRawLittleEndian32();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public long readFixed64() throws IOException {
            return readRawLittleEndian64();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public float readFloat() throws IOException {
            return Float.intBitsToFloat(readRawLittleEndian32());
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void readGroup(int i2, MessageLite.Builder builder, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            int i3 = this.recursionDepth;
            if (i3 >= this.recursionLimit) {
                throw InvalidProtocolBufferException.recursionLimitExceeded();
            }
            this.recursionDepth = i3 + 1;
            builder.mergeFrom(this, extensionRegistryLite);
            checkLastTagWas(WireFormat.makeTag(i2, 4));
            this.recursionDepth--;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readInt32() throws IOException {
            return readRawVarint32();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public long readInt64() throws IOException {
            return readRawVarint64();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void readMessage(MessageLite.Builder builder, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            int rawVarint32 = readRawVarint32();
            if (this.recursionDepth >= this.recursionLimit) {
                throw InvalidProtocolBufferException.recursionLimitExceeded();
            }
            int iPushLimit = pushLimit(rawVarint32);
            this.recursionDepth++;
            builder.mergeFrom(this, extensionRegistryLite);
            checkLastTagWas(0);
            this.recursionDepth--;
            popLimit(iPushLimit);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public byte readRawByte() throws IOException {
            long j2 = this.pos;
            if (j2 == this.limit) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            this.pos = 1 + j2;
            return UnsafeUtil.getByte(j2);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public byte[] readRawBytes(int i2) throws IOException {
            if (i2 < 0 || i2 > remaining()) {
                if (i2 > 0) {
                    throw InvalidProtocolBufferException.truncatedMessage();
                }
                if (i2 == 0) {
                    return Internal.EMPTY_BYTE_ARRAY;
                }
                throw InvalidProtocolBufferException.negativeSize();
            }
            byte[] bArr = new byte[i2];
            long j2 = this.pos;
            long j3 = i2;
            slice(j2, j2 + j3).get(bArr);
            this.pos += j3;
            return bArr;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readRawLittleEndian32() throws IOException {
            long j2 = this.pos;
            if (this.limit - j2 < 4) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            this.pos = 4 + j2;
            return ((UnsafeUtil.getByte(j2 + 3) & 255) << 24) | (UnsafeUtil.getByte(j2) & 255) | ((UnsafeUtil.getByte(1 + j2) & 255) << 8) | ((UnsafeUtil.getByte(2 + j2) & 255) << 16);
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public long readRawLittleEndian64() throws IOException {
            long j2 = this.pos;
            if (this.limit - j2 < 8) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            this.pos = 8 + j2;
            return ((UnsafeUtil.getByte(j2 + 7) & 255) << 56) | (UnsafeUtil.getByte(j2) & 255) | ((UnsafeUtil.getByte(1 + j2) & 255) << 8) | ((UnsafeUtil.getByte(2 + j2) & 255) << 16) | ((UnsafeUtil.getByte(3 + j2) & 255) << 24) | ((UnsafeUtil.getByte(4 + j2) & 255) << 32) | ((UnsafeUtil.getByte(5 + j2) & 255) << 40) | ((UnsafeUtil.getByte(6 + j2) & 255) << 48);
        }

        /* JADX WARN: Code restructure failed: missing block: B:33:0x0083, code lost:
        
            if (androidx.datastore.preferences.protobuf.UnsafeUtil.getByte(r4) < 0) goto L34;
         */
        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public int readRawVarint32() throws java.io.IOException {
            /*
                r10 = this;
                long r0 = r10.pos
                long r2 = r10.limit
                int r2 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
                if (r2 != 0) goto La
                goto L85
            La:
                r2 = 1
                long r4 = r0 + r2
                byte r0 = androidx.datastore.preferences.protobuf.UnsafeUtil.getByte(r0)
                if (r0 < 0) goto L17
                r10.pos = r4
                return r0
            L17:
                long r6 = r10.limit
                long r6 = r6 - r4
                r8 = 9
                int r1 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
                if (r1 >= 0) goto L21
                goto L85
            L21:
                long r6 = r4 + r2
                byte r1 = androidx.datastore.preferences.protobuf.UnsafeUtil.getByte(r4)
                int r1 = r1 << 7
                r0 = r0 ^ r1
                if (r0 >= 0) goto L2f
                r0 = r0 ^ (-128(0xffffffffffffff80, float:NaN))
                goto L8b
            L2f:
                long r4 = r6 + r2
                byte r1 = androidx.datastore.preferences.protobuf.UnsafeUtil.getByte(r6)
                int r1 = r1 << 14
                r0 = r0 ^ r1
                if (r0 < 0) goto L3e
                r0 = r0 ^ 16256(0x3f80, float:2.278E-41)
            L3c:
                r6 = r4
                goto L8b
            L3e:
                long r6 = r4 + r2
                byte r1 = androidx.datastore.preferences.protobuf.UnsafeUtil.getByte(r4)
                int r1 = r1 << 21
                r0 = r0 ^ r1
                if (r0 >= 0) goto L4e
                r1 = -2080896(0xffffffffffe03f80, float:NaN)
                r0 = r0 ^ r1
                goto L8b
            L4e:
                long r4 = r6 + r2
                byte r1 = androidx.datastore.preferences.protobuf.UnsafeUtil.getByte(r6)
                int r6 = r1 << 28
                r0 = r0 ^ r6
                r6 = 266354560(0xfe03f80, float:2.2112565E-29)
                r0 = r0 ^ r6
                if (r1 >= 0) goto L3c
                long r6 = r4 + r2
                byte r1 = androidx.datastore.preferences.protobuf.UnsafeUtil.getByte(r4)
                if (r1 >= 0) goto L8b
                long r4 = r6 + r2
                byte r1 = androidx.datastore.preferences.protobuf.UnsafeUtil.getByte(r6)
                if (r1 >= 0) goto L3c
                long r6 = r4 + r2
                byte r1 = androidx.datastore.preferences.protobuf.UnsafeUtil.getByte(r4)
                if (r1 >= 0) goto L8b
                long r4 = r6 + r2
                byte r1 = androidx.datastore.preferences.protobuf.UnsafeUtil.getByte(r6)
                if (r1 >= 0) goto L3c
                long r6 = r4 + r2
                byte r1 = androidx.datastore.preferences.protobuf.UnsafeUtil.getByte(r4)
                if (r1 >= 0) goto L8b
            L85:
                long r0 = r10.readRawVarint64SlowPath()
                int r0 = (int) r0
                return r0
            L8b:
                r10.pos = r6
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.preferences.protobuf.CodedInputStream.UnsafeDirectNioDecoder.readRawVarint32():int");
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public long readRawVarint64() throws IOException {
            long j2;
            long j3;
            long j4;
            int i2;
            long j5 = this.pos;
            if (this.limit != j5) {
                long j6 = j5 + 1;
                byte b3 = UnsafeUtil.getByte(j5);
                if (b3 >= 0) {
                    this.pos = j6;
                    return b3;
                }
                if (this.limit - j6 >= 9) {
                    long j7 = j6 + 1;
                    int i3 = b3 ^ (UnsafeUtil.getByte(j6) << 7);
                    if (i3 >= 0) {
                        long j8 = j7 + 1;
                        int i4 = i3 ^ (UnsafeUtil.getByte(j7) << 14);
                        if (i4 >= 0) {
                            j2 = i4 ^ R2.id.ll_answer_enter;
                        } else {
                            j7 = j8 + 1;
                            int i5 = i4 ^ (UnsafeUtil.getByte(j8) << 21);
                            if (i5 < 0) {
                                i2 = i5 ^ (-2080896);
                            } else {
                                j8 = j7 + 1;
                                long j9 = i5 ^ (UnsafeUtil.getByte(j7) << 28);
                                if (j9 < 0) {
                                    long j10 = j8 + 1;
                                    long j11 = j9 ^ (UnsafeUtil.getByte(j8) << 35);
                                    if (j11 < 0) {
                                        j3 = -34093383808L;
                                    } else {
                                        j8 = j10 + 1;
                                        j9 = j11 ^ (UnsafeUtil.getByte(j10) << 42);
                                        if (j9 >= 0) {
                                            j4 = 4363953127296L;
                                        } else {
                                            j10 = j8 + 1;
                                            j11 = j9 ^ (UnsafeUtil.getByte(j8) << 49);
                                            if (j11 >= 0) {
                                                j8 = j10 + 1;
                                                j2 = (j11 ^ (UnsafeUtil.getByte(j10) << 56)) ^ 71499008037633920L;
                                                if (j2 < 0) {
                                                    long j12 = 1 + j8;
                                                    if (UnsafeUtil.getByte(j8) >= 0) {
                                                        j7 = j12;
                                                    }
                                                }
                                                this.pos = j7;
                                                return j2;
                                            }
                                            j3 = -558586000294016L;
                                        }
                                    }
                                    j2 = j11 ^ j3;
                                    j7 = j10;
                                    this.pos = j7;
                                    return j2;
                                }
                                j4 = 266354560;
                                j2 = j9 ^ j4;
                            }
                        }
                        j7 = j8;
                        this.pos = j7;
                        return j2;
                    }
                    i2 = i3 ^ (-128);
                    j2 = i2;
                    this.pos = j7;
                    return j2;
                }
            }
            return readRawVarint64SlowPath();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public long readRawVarint64SlowPath() throws IOException {
            long j2 = 0;
            for (int i2 = 0; i2 < 64; i2 += 7) {
                j2 |= (r3 & 127) << i2;
                if ((readRawByte() & 128) == 0) {
                    return j2;
                }
            }
            throw InvalidProtocolBufferException.malformedVarint();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readSFixed32() throws IOException {
            return readRawLittleEndian32();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public long readSFixed64() throws IOException {
            return readRawLittleEndian64();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readSInt32() throws IOException {
            return CodedInputStream.decodeZigZag32(readRawVarint32());
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public long readSInt64() throws IOException {
            return CodedInputStream.decodeZigZag64(readRawVarint64());
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public String readString() throws IOException {
            int rawVarint32 = readRawVarint32();
            if (rawVarint32 <= 0 || rawVarint32 > remaining()) {
                if (rawVarint32 == 0) {
                    return "";
                }
                if (rawVarint32 < 0) {
                    throw InvalidProtocolBufferException.negativeSize();
                }
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            byte[] bArr = new byte[rawVarint32];
            long j2 = rawVarint32;
            UnsafeUtil.copyMemory(this.pos, bArr, 0L, j2);
            String str = new String(bArr, Internal.UTF_8);
            this.pos += j2;
            return str;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public String readStringRequireUtf8() throws IOException {
            int rawVarint32 = readRawVarint32();
            if (rawVarint32 > 0 && rawVarint32 <= remaining()) {
                String strDecodeUtf8 = Utf8.decodeUtf8(this.buffer, bufferPos(this.pos), rawVarint32);
                this.pos += rawVarint32;
                return strDecodeUtf8;
            }
            if (rawVarint32 == 0) {
                return "";
            }
            if (rawVarint32 <= 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            throw InvalidProtocolBufferException.truncatedMessage();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readTag() throws IOException {
            if (isAtEnd()) {
                this.lastTag = 0;
                return 0;
            }
            int rawVarint32 = readRawVarint32();
            this.lastTag = rawVarint32;
            if (WireFormat.getTagFieldNumber(rawVarint32) != 0) {
                return this.lastTag;
            }
            throw InvalidProtocolBufferException.invalidTag();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public int readUInt32() throws IOException {
            return readRawVarint32();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public long readUInt64() throws IOException {
            return readRawVarint64();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        @Deprecated
        public void readUnknownGroup(int i2, MessageLite.Builder builder) throws IOException {
            readGroup(i2, builder, ExtensionRegistryLite.getEmptyRegistry());
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void resetSizeCounter() {
            this.startPos = this.pos;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public boolean skipField(int i2) throws IOException {
            int tagWireType = WireFormat.getTagWireType(i2);
            if (tagWireType == 0) {
                skipRawVarint();
                return true;
            }
            if (tagWireType == 1) {
                skipRawBytes(8);
                return true;
            }
            if (tagWireType == 2) {
                skipRawBytes(readRawVarint32());
                return true;
            }
            if (tagWireType == 3) {
                skipMessage();
                checkLastTagWas(WireFormat.makeTag(WireFormat.getTagFieldNumber(i2), 4));
                return true;
            }
            if (tagWireType == 4) {
                return false;
            }
            if (tagWireType != 5) {
                throw InvalidProtocolBufferException.invalidWireType();
            }
            skipRawBytes(4);
            return true;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void skipMessage() throws IOException {
            int tag;
            do {
                tag = readTag();
                if (tag == 0) {
                    return;
                }
            } while (skipField(tag));
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void skipRawBytes(int i2) throws IOException {
            if (i2 >= 0 && i2 <= remaining()) {
                this.pos += i2;
            } else {
                if (i2 >= 0) {
                    throw InvalidProtocolBufferException.truncatedMessage();
                }
                throw InvalidProtocolBufferException.negativeSize();
            }
        }

        private UnsafeDirectNioDecoder(ByteBuffer byteBuffer, boolean z2) {
            super();
            this.currentLimit = Integer.MAX_VALUE;
            this.buffer = byteBuffer;
            long jAddressOffset = UnsafeUtil.addressOffset(byteBuffer);
            this.address = jAddressOffset;
            this.limit = byteBuffer.limit() + jAddressOffset;
            long jPosition = jAddressOffset + byteBuffer.position();
            this.pos = jPosition;
            this.startPos = jPosition;
            this.immutable = z2;
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public void skipMessage(CodedOutputStream codedOutputStream) throws IOException {
            int tag;
            do {
                tag = readTag();
                if (tag == 0) {
                    return;
                }
            } while (skipField(tag, codedOutputStream));
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public <T extends MessageLite> T readGroup(int i2, Parser<T> parser, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            int i3 = this.recursionDepth;
            if (i3 < this.recursionLimit) {
                this.recursionDepth = i3 + 1;
                T partialFrom = parser.parsePartialFrom(this, extensionRegistryLite);
                checkLastTagWas(WireFormat.makeTag(i2, 4));
                this.recursionDepth--;
                return partialFrom;
            }
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public <T extends MessageLite> T readMessage(Parser<T> parser, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            int rawVarint32 = readRawVarint32();
            if (this.recursionDepth < this.recursionLimit) {
                int iPushLimit = pushLimit(rawVarint32);
                this.recursionDepth++;
                T partialFrom = parser.parsePartialFrom(this, extensionRegistryLite);
                checkLastTagWas(0);
                this.recursionDepth--;
                popLimit(iPushLimit);
                return partialFrom;
            }
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }

        @Override // androidx.datastore.preferences.protobuf.CodedInputStream
        public boolean skipField(int i2, CodedOutputStream codedOutputStream) throws IOException {
            int tagWireType = WireFormat.getTagWireType(i2);
            if (tagWireType == 0) {
                long int64 = readInt64();
                codedOutputStream.writeRawVarint32(i2);
                codedOutputStream.writeUInt64NoTag(int64);
                return true;
            }
            if (tagWireType == 1) {
                long rawLittleEndian64 = readRawLittleEndian64();
                codedOutputStream.writeRawVarint32(i2);
                codedOutputStream.writeFixed64NoTag(rawLittleEndian64);
                return true;
            }
            if (tagWireType == 2) {
                ByteString bytes = readBytes();
                codedOutputStream.writeRawVarint32(i2);
                codedOutputStream.writeBytesNoTag(bytes);
                return true;
            }
            if (tagWireType == 3) {
                codedOutputStream.writeRawVarint32(i2);
                skipMessage(codedOutputStream);
                int iMakeTag = WireFormat.makeTag(WireFormat.getTagFieldNumber(i2), 4);
                checkLastTagWas(iMakeTag);
                codedOutputStream.writeRawVarint32(iMakeTag);
                return true;
            }
            if (tagWireType == 4) {
                return false;
            }
            if (tagWireType == 5) {
                int rawLittleEndian32 = readRawLittleEndian32();
                codedOutputStream.writeRawVarint32(i2);
                codedOutputStream.writeFixed32NoTag(rawLittleEndian32);
                return true;
            }
            throw InvalidProtocolBufferException.invalidWireType();
        }
    }

    public static int decodeZigZag32(int i2) {
        return (-(i2 & 1)) ^ (i2 >>> 1);
    }

    public static long decodeZigZag64(long j2) {
        return (-(j2 & 1)) ^ (j2 >>> 1);
    }

    public static CodedInputStream newInstance(InputStream inputStream) {
        return newInstance(inputStream, 4096);
    }

    public static int readRawVarint32(int i2, InputStream inputStream) throws IOException {
        if ((i2 & 128) == 0) {
            return i2;
        }
        int i3 = i2 & 127;
        int i4 = 7;
        while (i4 < 32) {
            int i5 = inputStream.read();
            if (i5 == -1) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            i3 |= (i5 & 127) << i4;
            if ((i5 & 128) == 0) {
                return i3;
            }
            i4 += 7;
        }
        while (i4 < 64) {
            int i6 = inputStream.read();
            if (i6 == -1) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            if ((i6 & 128) == 0) {
                return i3;
            }
            i4 += 7;
        }
        throw InvalidProtocolBufferException.malformedVarint();
    }

    public abstract void checkLastTagWas(int i2) throws InvalidProtocolBufferException;

    public final void discardUnknownFields() {
        this.shouldDiscardUnknownFields = true;
    }

    public abstract void enableAliasing(boolean z2);

    public abstract int getBytesUntilLimit();

    public abstract int getLastTag();

    public abstract int getTotalBytesRead();

    public abstract boolean isAtEnd() throws IOException;

    public abstract void popLimit(int i2);

    public abstract int pushLimit(int i2) throws InvalidProtocolBufferException;

    public abstract boolean readBool() throws IOException;

    public abstract byte[] readByteArray() throws IOException;

    public abstract ByteBuffer readByteBuffer() throws IOException;

    public abstract ByteString readBytes() throws IOException;

    public abstract double readDouble() throws IOException;

    public abstract int readEnum() throws IOException;

    public abstract int readFixed32() throws IOException;

    public abstract long readFixed64() throws IOException;

    public abstract float readFloat() throws IOException;

    public abstract <T extends MessageLite> T readGroup(int i2, Parser<T> parser, ExtensionRegistryLite extensionRegistryLite) throws IOException;

    public abstract void readGroup(int i2, MessageLite.Builder builder, ExtensionRegistryLite extensionRegistryLite) throws IOException;

    public abstract int readInt32() throws IOException;

    public abstract long readInt64() throws IOException;

    public abstract <T extends MessageLite> T readMessage(Parser<T> parser, ExtensionRegistryLite extensionRegistryLite) throws IOException;

    public abstract void readMessage(MessageLite.Builder builder, ExtensionRegistryLite extensionRegistryLite) throws IOException;

    public abstract byte readRawByte() throws IOException;

    public abstract byte[] readRawBytes(int i2) throws IOException;

    public abstract int readRawLittleEndian32() throws IOException;

    public abstract long readRawLittleEndian64() throws IOException;

    public abstract int readRawVarint32() throws IOException;

    public abstract long readRawVarint64() throws IOException;

    public abstract long readRawVarint64SlowPath() throws IOException;

    public abstract int readSFixed32() throws IOException;

    public abstract long readSFixed64() throws IOException;

    public abstract int readSInt32() throws IOException;

    public abstract long readSInt64() throws IOException;

    public abstract String readString() throws IOException;

    public abstract String readStringRequireUtf8() throws IOException;

    public abstract int readTag() throws IOException;

    public abstract int readUInt32() throws IOException;

    public abstract long readUInt64() throws IOException;

    @Deprecated
    public abstract void readUnknownGroup(int i2, MessageLite.Builder builder) throws IOException;

    public abstract void resetSizeCounter();

    public final int setRecursionLimit(int i2) {
        if (i2 >= 0) {
            int i3 = this.recursionLimit;
            this.recursionLimit = i2;
            return i3;
        }
        throw new IllegalArgumentException("Recursion limit cannot be negative: " + i2);
    }

    public final int setSizeLimit(int i2) {
        if (i2 >= 0) {
            int i3 = this.sizeLimit;
            this.sizeLimit = i2;
            return i3;
        }
        throw new IllegalArgumentException("Size limit cannot be negative: " + i2);
    }

    public final boolean shouldDiscardUnknownFields() {
        return this.shouldDiscardUnknownFields;
    }

    public abstract boolean skipField(int i2) throws IOException;

    @Deprecated
    public abstract boolean skipField(int i2, CodedOutputStream codedOutputStream) throws IOException;

    public abstract void skipMessage() throws IOException;

    public abstract void skipMessage(CodedOutputStream codedOutputStream) throws IOException;

    public abstract void skipRawBytes(int i2) throws IOException;

    public final void unsetDiscardUnknownFields() {
        this.shouldDiscardUnknownFields = false;
    }

    private CodedInputStream() {
        this.recursionLimit = 100;
        this.sizeLimit = Integer.MAX_VALUE;
        this.shouldDiscardUnknownFields = false;
    }

    public static CodedInputStream newInstance(InputStream inputStream, int i2) {
        if (i2 > 0) {
            return inputStream == null ? newInstance(Internal.EMPTY_BYTE_ARRAY) : new StreamDecoder(inputStream, i2);
        }
        throw new IllegalArgumentException("bufferSize must be > 0");
    }

    public static CodedInputStream newInstance(Iterable<ByteBuffer> iterable) {
        if (!UnsafeDirectNioDecoder.isSupported()) {
            return newInstance(new IterableByteBufferInputStream(iterable));
        }
        return newInstance(iterable, false);
    }

    public static int readRawVarint32(InputStream inputStream) throws IOException {
        int i2 = inputStream.read();
        if (i2 != -1) {
            return readRawVarint32(i2, inputStream);
        }
        throw InvalidProtocolBufferException.truncatedMessage();
    }

    public static CodedInputStream newInstance(Iterable<ByteBuffer> iterable, boolean z2) {
        int i2 = 0;
        int iRemaining = 0;
        for (ByteBuffer byteBuffer : iterable) {
            iRemaining += byteBuffer.remaining();
            if (byteBuffer.hasArray()) {
                i2 |= 1;
            } else {
                i2 = byteBuffer.isDirect() ? i2 | 2 : i2 | 4;
            }
        }
        if (i2 == 2) {
            return new IterableDirectByteBufferDecoder(iterable, iRemaining, z2);
        }
        return newInstance(new IterableByteBufferInputStream(iterable));
    }

    public static CodedInputStream newInstance(byte[] bArr) {
        return newInstance(bArr, 0, bArr.length);
    }

    public static CodedInputStream newInstance(byte[] bArr, int i2, int i3) {
        return newInstance(bArr, i2, i3, false);
    }

    public static CodedInputStream newInstance(byte[] bArr, int i2, int i3, boolean z2) {
        ArrayDecoder arrayDecoder = new ArrayDecoder(bArr, i2, i3, z2);
        try {
            arrayDecoder.pushLimit(i3);
            return arrayDecoder;
        } catch (InvalidProtocolBufferException e2) {
            throw new IllegalArgumentException(e2);
        }
    }

    public static CodedInputStream newInstance(ByteBuffer byteBuffer) {
        return newInstance(byteBuffer, false);
    }

    public static CodedInputStream newInstance(ByteBuffer byteBuffer, boolean z2) {
        if (byteBuffer.hasArray()) {
            return newInstance(byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), byteBuffer.remaining(), z2);
        }
        if (byteBuffer.isDirect() && UnsafeDirectNioDecoder.isSupported()) {
            return new UnsafeDirectNioDecoder(byteBuffer, z2);
        }
        int iRemaining = byteBuffer.remaining();
        byte[] bArr = new byte[iRemaining];
        byteBuffer.duplicate().get(bArr);
        return newInstance(bArr, 0, iRemaining, true);
    }
}
