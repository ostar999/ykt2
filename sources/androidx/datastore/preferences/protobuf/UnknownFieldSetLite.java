package androidx.datastore.preferences.protobuf;

import androidx.datastore.preferences.protobuf.Writer;
import com.yikaobang.yixue.R2;
import java.io.IOException;
import java.util.Arrays;

/* loaded from: classes.dex */
public final class UnknownFieldSetLite {
    private static final UnknownFieldSetLite DEFAULT_INSTANCE = new UnknownFieldSetLite(0, new int[0], new Object[0], false);
    private static final int MIN_CAPACITY = 8;
    private int count;
    private boolean isMutable;
    private int memoizedSerializedSize;
    private Object[] objects;
    private int[] tags;

    private UnknownFieldSetLite() {
        this(0, new int[8], new Object[8], true);
    }

    private void ensureCapacity() {
        int i2 = this.count;
        int[] iArr = this.tags;
        if (i2 == iArr.length) {
            int i3 = i2 + (i2 < 4 ? 8 : i2 >> 1);
            this.tags = Arrays.copyOf(iArr, i3);
            this.objects = Arrays.copyOf(this.objects, i3);
        }
    }

    private static boolean equals(int[] iArr, int[] iArr2, int i2) {
        for (int i3 = 0; i3 < i2; i3++) {
            if (iArr[i3] != iArr2[i3]) {
                return false;
            }
        }
        return true;
    }

    public static UnknownFieldSetLite getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    private static int hashCode(int[] iArr, int i2) {
        int i3 = 17;
        for (int i4 = 0; i4 < i2; i4++) {
            i3 = (i3 * 31) + iArr[i4];
        }
        return i3;
    }

    private UnknownFieldSetLite mergeFrom(CodedInputStream codedInputStream) throws IOException {
        int tag;
        do {
            tag = codedInputStream.readTag();
            if (tag == 0) {
                break;
            }
        } while (mergeFieldFrom(tag, codedInputStream));
        return this;
    }

    public static UnknownFieldSetLite mutableCopyOf(UnknownFieldSetLite unknownFieldSetLite, UnknownFieldSetLite unknownFieldSetLite2) {
        int i2 = unknownFieldSetLite.count + unknownFieldSetLite2.count;
        int[] iArrCopyOf = Arrays.copyOf(unknownFieldSetLite.tags, i2);
        System.arraycopy(unknownFieldSetLite2.tags, 0, iArrCopyOf, unknownFieldSetLite.count, unknownFieldSetLite2.count);
        Object[] objArrCopyOf = Arrays.copyOf(unknownFieldSetLite.objects, i2);
        System.arraycopy(unknownFieldSetLite2.objects, 0, objArrCopyOf, unknownFieldSetLite.count, unknownFieldSetLite2.count);
        return new UnknownFieldSetLite(i2, iArrCopyOf, objArrCopyOf, true);
    }

    public static UnknownFieldSetLite newInstance() {
        return new UnknownFieldSetLite();
    }

    private static void writeField(int i2, Object obj, Writer writer) throws IOException {
        int tagFieldNumber = WireFormat.getTagFieldNumber(i2);
        int tagWireType = WireFormat.getTagWireType(i2);
        if (tagWireType == 0) {
            writer.writeInt64(tagFieldNumber, ((Long) obj).longValue());
            return;
        }
        if (tagWireType == 1) {
            writer.writeFixed64(tagFieldNumber, ((Long) obj).longValue());
            return;
        }
        if (tagWireType == 2) {
            writer.writeBytes(tagFieldNumber, (ByteString) obj);
            return;
        }
        if (tagWireType != 3) {
            if (tagWireType != 5) {
                throw new RuntimeException(InvalidProtocolBufferException.invalidWireType());
            }
            writer.writeFixed32(tagFieldNumber, ((Integer) obj).intValue());
        } else if (writer.fieldOrder() == Writer.FieldOrder.ASCENDING) {
            writer.writeStartGroup(tagFieldNumber);
            ((UnknownFieldSetLite) obj).writeTo(writer);
            writer.writeEndGroup(tagFieldNumber);
        } else {
            writer.writeEndGroup(tagFieldNumber);
            ((UnknownFieldSetLite) obj).writeTo(writer);
            writer.writeStartGroup(tagFieldNumber);
        }
    }

    public void checkMutable() {
        if (!this.isMutable) {
            throw new UnsupportedOperationException();
        }
    }

    public int getSerializedSize() {
        int iComputeUInt64Size;
        int i2 = this.memoizedSerializedSize;
        if (i2 != -1) {
            return i2;
        }
        int i3 = 0;
        for (int i4 = 0; i4 < this.count; i4++) {
            int i5 = this.tags[i4];
            int tagFieldNumber = WireFormat.getTagFieldNumber(i5);
            int tagWireType = WireFormat.getTagWireType(i5);
            if (tagWireType == 0) {
                iComputeUInt64Size = CodedOutputStream.computeUInt64Size(tagFieldNumber, ((Long) this.objects[i4]).longValue());
            } else if (tagWireType == 1) {
                iComputeUInt64Size = CodedOutputStream.computeFixed64Size(tagFieldNumber, ((Long) this.objects[i4]).longValue());
            } else if (tagWireType == 2) {
                iComputeUInt64Size = CodedOutputStream.computeBytesSize(tagFieldNumber, (ByteString) this.objects[i4]);
            } else if (tagWireType == 3) {
                iComputeUInt64Size = (CodedOutputStream.computeTagSize(tagFieldNumber) * 2) + ((UnknownFieldSetLite) this.objects[i4]).getSerializedSize();
            } else {
                if (tagWireType != 5) {
                    throw new IllegalStateException(InvalidProtocolBufferException.invalidWireType());
                }
                iComputeUInt64Size = CodedOutputStream.computeFixed32Size(tagFieldNumber, ((Integer) this.objects[i4]).intValue());
            }
            i3 += iComputeUInt64Size;
        }
        this.memoizedSerializedSize = i3;
        return i3;
    }

    public int getSerializedSizeAsMessageSet() {
        int i2 = this.memoizedSerializedSize;
        if (i2 != -1) {
            return i2;
        }
        int iComputeRawMessageSetExtensionSize = 0;
        for (int i3 = 0; i3 < this.count; i3++) {
            iComputeRawMessageSetExtensionSize += CodedOutputStream.computeRawMessageSetExtensionSize(WireFormat.getTagFieldNumber(this.tags[i3]), (ByteString) this.objects[i3]);
        }
        this.memoizedSerializedSize = iComputeRawMessageSetExtensionSize;
        return iComputeRawMessageSetExtensionSize;
    }

    public void makeImmutable() {
        this.isMutable = false;
    }

    public boolean mergeFieldFrom(int i2, CodedInputStream codedInputStream) throws IOException {
        checkMutable();
        int tagFieldNumber = WireFormat.getTagFieldNumber(i2);
        int tagWireType = WireFormat.getTagWireType(i2);
        if (tagWireType == 0) {
            storeField(i2, Long.valueOf(codedInputStream.readInt64()));
            return true;
        }
        if (tagWireType == 1) {
            storeField(i2, Long.valueOf(codedInputStream.readFixed64()));
            return true;
        }
        if (tagWireType == 2) {
            storeField(i2, codedInputStream.readBytes());
            return true;
        }
        if (tagWireType == 3) {
            UnknownFieldSetLite unknownFieldSetLite = new UnknownFieldSetLite();
            unknownFieldSetLite.mergeFrom(codedInputStream);
            codedInputStream.checkLastTagWas(WireFormat.makeTag(tagFieldNumber, 4));
            storeField(i2, unknownFieldSetLite);
            return true;
        }
        if (tagWireType == 4) {
            return false;
        }
        if (tagWireType != 5) {
            throw InvalidProtocolBufferException.invalidWireType();
        }
        storeField(i2, Integer.valueOf(codedInputStream.readFixed32()));
        return true;
    }

    public UnknownFieldSetLite mergeLengthDelimitedField(int i2, ByteString byteString) {
        checkMutable();
        if (i2 == 0) {
            throw new IllegalArgumentException("Zero is not a valid field number.");
        }
        storeField(WireFormat.makeTag(i2, 2), byteString);
        return this;
    }

    public UnknownFieldSetLite mergeVarintField(int i2, int i3) {
        checkMutable();
        if (i2 == 0) {
            throw new IllegalArgumentException("Zero is not a valid field number.");
        }
        storeField(WireFormat.makeTag(i2, 0), Long.valueOf(i3));
        return this;
    }

    public final void printWithIndent(StringBuilder sb, int i2) {
        for (int i3 = 0; i3 < this.count; i3++) {
            MessageLiteToString.printField(sb, i2, String.valueOf(WireFormat.getTagFieldNumber(this.tags[i3])), this.objects[i3]);
        }
    }

    public void storeField(int i2, Object obj) {
        checkMutable();
        ensureCapacity();
        int[] iArr = this.tags;
        int i3 = this.count;
        iArr[i3] = i2;
        this.objects[i3] = obj;
        this.count = i3 + 1;
    }

    public void writeAsMessageSetTo(CodedOutputStream codedOutputStream) throws IOException {
        for (int i2 = 0; i2 < this.count; i2++) {
            codedOutputStream.writeRawMessageSetExtension(WireFormat.getTagFieldNumber(this.tags[i2]), (ByteString) this.objects[i2]);
        }
    }

    public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
        for (int i2 = 0; i2 < this.count; i2++) {
            int i3 = this.tags[i2];
            int tagFieldNumber = WireFormat.getTagFieldNumber(i3);
            int tagWireType = WireFormat.getTagWireType(i3);
            if (tagWireType == 0) {
                codedOutputStream.writeUInt64(tagFieldNumber, ((Long) this.objects[i2]).longValue());
            } else if (tagWireType == 1) {
                codedOutputStream.writeFixed64(tagFieldNumber, ((Long) this.objects[i2]).longValue());
            } else if (tagWireType == 2) {
                codedOutputStream.writeBytes(tagFieldNumber, (ByteString) this.objects[i2]);
            } else if (tagWireType == 3) {
                codedOutputStream.writeTag(tagFieldNumber, 3);
                ((UnknownFieldSetLite) this.objects[i2]).writeTo(codedOutputStream);
                codedOutputStream.writeTag(tagFieldNumber, 4);
            } else {
                if (tagWireType != 5) {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
                codedOutputStream.writeFixed32(tagFieldNumber, ((Integer) this.objects[i2]).intValue());
            }
        }
    }

    private UnknownFieldSetLite(int i2, int[] iArr, Object[] objArr, boolean z2) {
        this.memoizedSerializedSize = -1;
        this.count = i2;
        this.tags = iArr;
        this.objects = objArr;
        this.isMutable = z2;
    }

    private static boolean equals(Object[] objArr, Object[] objArr2, int i2) {
        for (int i3 = 0; i3 < i2; i3++) {
            if (!objArr[i3].equals(objArr2[i3])) {
                return false;
            }
        }
        return true;
    }

    private static int hashCode(Object[] objArr, int i2) {
        int iHashCode = 17;
        for (int i3 = 0; i3 < i2; i3++) {
            iHashCode = (iHashCode * 31) + objArr[i3].hashCode();
        }
        return iHashCode;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof UnknownFieldSetLite)) {
            return false;
        }
        UnknownFieldSetLite unknownFieldSetLite = (UnknownFieldSetLite) obj;
        int i2 = this.count;
        return i2 == unknownFieldSetLite.count && equals(this.tags, unknownFieldSetLite.tags, i2) && equals(this.objects, unknownFieldSetLite.objects, this.count);
    }

    public int hashCode() {
        int i2 = this.count;
        return ((((R2.attr.bl_checkable_gradient_endColor + i2) * 31) + hashCode(this.tags, i2)) * 31) + hashCode(this.objects, this.count);
    }

    public void writeAsMessageSetTo(Writer writer) throws IOException {
        if (writer.fieldOrder() == Writer.FieldOrder.DESCENDING) {
            for (int i2 = this.count - 1; i2 >= 0; i2--) {
                writer.writeMessageSetItem(WireFormat.getTagFieldNumber(this.tags[i2]), this.objects[i2]);
            }
            return;
        }
        for (int i3 = 0; i3 < this.count; i3++) {
            writer.writeMessageSetItem(WireFormat.getTagFieldNumber(this.tags[i3]), this.objects[i3]);
        }
    }

    public void writeTo(Writer writer) throws IOException {
        if (this.count == 0) {
            return;
        }
        if (writer.fieldOrder() == Writer.FieldOrder.ASCENDING) {
            for (int i2 = 0; i2 < this.count; i2++) {
                writeField(this.tags[i2], this.objects[i2], writer);
            }
            return;
        }
        for (int i3 = this.count - 1; i3 >= 0; i3--) {
            writeField(this.tags[i3], this.objects[i3], writer);
        }
    }
}
