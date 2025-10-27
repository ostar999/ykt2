package com.google.common.hash;

import com.google.common.annotations.Beta;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

@CanIgnoreReturnValue
@Beta
/* loaded from: classes4.dex */
public interface PrimitiveSink {
    PrimitiveSink putBoolean(boolean z2);

    PrimitiveSink putByte(byte b3);

    PrimitiveSink putBytes(ByteBuffer byteBuffer);

    PrimitiveSink putBytes(byte[] bArr);

    PrimitiveSink putBytes(byte[] bArr, int i2, int i3);

    PrimitiveSink putChar(char c3);

    PrimitiveSink putDouble(double d3);

    PrimitiveSink putFloat(float f2);

    PrimitiveSink putInt(int i2);

    PrimitiveSink putLong(long j2);

    PrimitiveSink putShort(short s2);

    PrimitiveSink putString(CharSequence charSequence, Charset charset);

    PrimitiveSink putUnencodedChars(CharSequence charSequence);
}
