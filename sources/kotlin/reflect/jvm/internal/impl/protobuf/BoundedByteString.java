package kotlin.reflect.jvm.internal.impl.protobuf;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.reflect.jvm.internal.impl.protobuf.ByteString;
import org.slf4j.Marker;

/* loaded from: classes8.dex */
class BoundedByteString extends LiteralByteString {
    private final int bytesLength;
    private final int bytesOffset;

    public class BoundedByteIterator implements ByteString.ByteIterator {
        private final int limit;
        private int position;

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.position < this.limit;
        }

        @Override // kotlin.reflect.jvm.internal.impl.protobuf.ByteString.ByteIterator
        public byte nextByte() {
            int i2 = this.position;
            if (i2 >= this.limit) {
                throw new NoSuchElementException();
            }
            byte[] bArr = BoundedByteString.this.bytes;
            this.position = i2 + 1;
            return bArr[i2];
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException();
        }

        private BoundedByteIterator() {
            int offsetIntoBytes = BoundedByteString.this.getOffsetIntoBytes();
            this.position = offsetIntoBytes;
            this.limit = offsetIntoBytes + BoundedByteString.this.size();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.Iterator
        public Byte next() {
            return Byte.valueOf(nextByte());
        }
    }

    public BoundedByteString(byte[] bArr, int i2, int i3) {
        super(bArr);
        if (i2 < 0) {
            StringBuilder sb = new StringBuilder(29);
            sb.append("Offset too small: ");
            sb.append(i2);
            throw new IllegalArgumentException(sb.toString());
        }
        if (i3 < 0) {
            StringBuilder sb2 = new StringBuilder(29);
            sb2.append("Length too small: ");
            sb2.append(i2);
            throw new IllegalArgumentException(sb2.toString());
        }
        if (i2 + i3 <= bArr.length) {
            this.bytesOffset = i2;
            this.bytesLength = i3;
            return;
        }
        StringBuilder sb3 = new StringBuilder(48);
        sb3.append("Offset+Length too large: ");
        sb3.append(i2);
        sb3.append(Marker.ANY_NON_NULL_MARKER);
        sb3.append(i3);
        throw new IllegalArgumentException(sb3.toString());
    }

    @Override // kotlin.reflect.jvm.internal.impl.protobuf.LiteralByteString
    public byte byteAt(int i2) {
        if (i2 < 0) {
            StringBuilder sb = new StringBuilder(28);
            sb.append("Index too small: ");
            sb.append(i2);
            throw new ArrayIndexOutOfBoundsException(sb.toString());
        }
        if (i2 < size()) {
            return this.bytes[this.bytesOffset + i2];
        }
        int size = size();
        StringBuilder sb2 = new StringBuilder(41);
        sb2.append("Index too large: ");
        sb2.append(i2);
        sb2.append(", ");
        sb2.append(size);
        throw new ArrayIndexOutOfBoundsException(sb2.toString());
    }

    @Override // kotlin.reflect.jvm.internal.impl.protobuf.LiteralByteString, kotlin.reflect.jvm.internal.impl.protobuf.ByteString
    public void copyToInternal(byte[] bArr, int i2, int i3, int i4) {
        System.arraycopy(this.bytes, getOffsetIntoBytes() + i2, bArr, i3, i4);
    }

    @Override // kotlin.reflect.jvm.internal.impl.protobuf.LiteralByteString
    public int getOffsetIntoBytes() {
        return this.bytesOffset;
    }

    @Override // kotlin.reflect.jvm.internal.impl.protobuf.LiteralByteString, kotlin.reflect.jvm.internal.impl.protobuf.ByteString
    public int size() {
        return this.bytesLength;
    }

    @Override // kotlin.reflect.jvm.internal.impl.protobuf.LiteralByteString, kotlin.reflect.jvm.internal.impl.protobuf.ByteString, java.lang.Iterable
    public Iterator<Byte> iterator() {
        return new BoundedByteIterator();
    }
}
