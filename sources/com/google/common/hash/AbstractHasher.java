package com.google.common.hash;

import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

@CanIgnoreReturnValue
/* loaded from: classes4.dex */
abstract class AbstractHasher implements Hasher {
    @Override // com.google.common.hash.Hasher
    public <T> Hasher putObject(T t2, Funnel<? super T> funnel) {
        funnel.funnel(t2, this);
        return this;
    }

    @Override // com.google.common.hash.PrimitiveSink
    public final Hasher putBoolean(boolean z2) {
        return putByte(z2 ? (byte) 1 : (byte) 0);
    }

    @Override // com.google.common.hash.PrimitiveSink
    public Hasher putChar(char c3) {
        putByte((byte) c3);
        putByte((byte) (c3 >>> '\b'));
        return this;
    }

    @Override // com.google.common.hash.PrimitiveSink
    public final Hasher putDouble(double d3) {
        return putLong(Double.doubleToRawLongBits(d3));
    }

    @Override // com.google.common.hash.PrimitiveSink
    public final Hasher putFloat(float f2) {
        return putInt(Float.floatToRawIntBits(f2));
    }

    @Override // com.google.common.hash.PrimitiveSink
    public Hasher putInt(int i2) {
        putByte((byte) i2);
        putByte((byte) (i2 >>> 8));
        putByte((byte) (i2 >>> 16));
        putByte((byte) (i2 >>> 24));
        return this;
    }

    @Override // com.google.common.hash.PrimitiveSink
    public Hasher putLong(long j2) {
        for (int i2 = 0; i2 < 64; i2 += 8) {
            putByte((byte) (j2 >>> i2));
        }
        return this;
    }

    @Override // com.google.common.hash.PrimitiveSink
    public Hasher putShort(short s2) {
        putByte((byte) s2);
        putByte((byte) (s2 >>> 8));
        return this;
    }

    @Override // com.google.common.hash.PrimitiveSink
    public Hasher putString(CharSequence charSequence, Charset charset) {
        return putBytes(charSequence.toString().getBytes(charset));
    }

    @Override // com.google.common.hash.PrimitiveSink
    public Hasher putUnencodedChars(CharSequence charSequence) {
        int length = charSequence.length();
        for (int i2 = 0; i2 < length; i2++) {
            putChar(charSequence.charAt(i2));
        }
        return this;
    }

    @Override // com.google.common.hash.PrimitiveSink
    public Hasher putBytes(byte[] bArr) {
        return putBytes(bArr, 0, bArr.length);
    }

    @Override // com.google.common.hash.PrimitiveSink
    public Hasher putBytes(byte[] bArr, int i2, int i3) {
        Preconditions.checkPositionIndexes(i2, i2 + i3, bArr.length);
        for (int i4 = 0; i4 < i3; i4++) {
            putByte(bArr[i2 + i4]);
        }
        return this;
    }

    @Override // com.google.common.hash.PrimitiveSink
    public Hasher putBytes(ByteBuffer byteBuffer) {
        if (byteBuffer.hasArray()) {
            putBytes(byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), byteBuffer.remaining());
            byteBuffer.position(byteBuffer.limit());
        } else {
            for (int iRemaining = byteBuffer.remaining(); iRemaining > 0; iRemaining--) {
                putByte(byteBuffer.get());
            }
        }
        return this;
    }
}
