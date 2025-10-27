package com.google.common.hash;

import com.google.common.annotations.Beta;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

@CanIgnoreReturnValue
@Beta
/* loaded from: classes4.dex */
public interface Hasher extends PrimitiveSink {
    HashCode hash();

    @Deprecated
    int hashCode();

    @Override // com.google.common.hash.PrimitiveSink
    Hasher putBoolean(boolean z2);

    @Override // com.google.common.hash.PrimitiveSink
    Hasher putByte(byte b3);

    @Override // com.google.common.hash.PrimitiveSink
    Hasher putBytes(ByteBuffer byteBuffer);

    @Override // com.google.common.hash.PrimitiveSink
    Hasher putBytes(byte[] bArr);

    @Override // com.google.common.hash.PrimitiveSink
    Hasher putBytes(byte[] bArr, int i2, int i3);

    @Override // com.google.common.hash.PrimitiveSink
    Hasher putChar(char c3);

    @Override // com.google.common.hash.PrimitiveSink
    Hasher putDouble(double d3);

    @Override // com.google.common.hash.PrimitiveSink
    Hasher putFloat(float f2);

    @Override // com.google.common.hash.PrimitiveSink
    Hasher putInt(int i2);

    @Override // com.google.common.hash.PrimitiveSink
    Hasher putLong(long j2);

    <T> Hasher putObject(T t2, Funnel<? super T> funnel);

    @Override // com.google.common.hash.PrimitiveSink
    Hasher putShort(short s2);

    @Override // com.google.common.hash.PrimitiveSink
    Hasher putString(CharSequence charSequence, Charset charset);

    @Override // com.google.common.hash.PrimitiveSink
    Hasher putUnencodedChars(CharSequence charSequence);
}
