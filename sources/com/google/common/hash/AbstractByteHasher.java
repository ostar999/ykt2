package com.google.common.hash;

import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

@CanIgnoreReturnValue
/* loaded from: classes4.dex */
abstract class AbstractByteHasher extends AbstractHasher {
    private final ByteBuffer scratch = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN);

    public abstract void update(byte b3);

    public void update(byte[] bArr) {
        update(bArr, 0, bArr.length);
    }

    @Override // com.google.common.hash.PrimitiveSink
    public Hasher putByte(byte b3) {
        update(b3);
        return this;
    }

    @Override // com.google.common.hash.AbstractHasher, com.google.common.hash.PrimitiveSink
    public Hasher putChar(char c3) {
        this.scratch.putChar(c3);
        return update(2);
    }

    @Override // com.google.common.hash.AbstractHasher, com.google.common.hash.PrimitiveSink
    public Hasher putInt(int i2) {
        this.scratch.putInt(i2);
        return update(4);
    }

    @Override // com.google.common.hash.AbstractHasher, com.google.common.hash.PrimitiveSink
    public Hasher putLong(long j2) {
        this.scratch.putLong(j2);
        return update(8);
    }

    @Override // com.google.common.hash.AbstractHasher, com.google.common.hash.PrimitiveSink
    public Hasher putShort(short s2) {
        this.scratch.putShort(s2);
        return update(2);
    }

    public void update(byte[] bArr, int i2, int i3) {
        for (int i4 = i2; i4 < i2 + i3; i4++) {
            update(bArr[i4]);
        }
    }

    public void update(ByteBuffer byteBuffer) {
        if (byteBuffer.hasArray()) {
            update(byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), byteBuffer.remaining());
            byteBuffer.position(byteBuffer.limit());
        } else {
            for (int iRemaining = byteBuffer.remaining(); iRemaining > 0; iRemaining--) {
                update(byteBuffer.get());
            }
        }
    }

    @Override // com.google.common.hash.AbstractHasher, com.google.common.hash.PrimitiveSink
    public Hasher putBytes(byte[] bArr) {
        Preconditions.checkNotNull(bArr);
        update(bArr);
        return this;
    }

    @Override // com.google.common.hash.AbstractHasher, com.google.common.hash.PrimitiveSink
    public Hasher putBytes(byte[] bArr, int i2, int i3) {
        Preconditions.checkPositionIndexes(i2, i2 + i3, bArr.length);
        update(bArr, i2, i3);
        return this;
    }

    private Hasher update(int i2) {
        try {
            update(this.scratch.array(), 0, i2);
            return this;
        } finally {
            this.scratch.clear();
        }
    }

    @Override // com.google.common.hash.AbstractHasher, com.google.common.hash.PrimitiveSink
    public Hasher putBytes(ByteBuffer byteBuffer) {
        update(byteBuffer);
        return this;
    }
}
