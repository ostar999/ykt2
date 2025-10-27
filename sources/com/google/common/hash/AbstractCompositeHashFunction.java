package com.google.common.hash;

import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.Immutable;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

@Immutable
/* loaded from: classes4.dex */
abstract class AbstractCompositeHashFunction extends AbstractHashFunction {
    private static final long serialVersionUID = 0;
    final HashFunction[] functions;

    public AbstractCompositeHashFunction(HashFunction... hashFunctionArr) {
        for (HashFunction hashFunction : hashFunctionArr) {
            Preconditions.checkNotNull(hashFunction);
        }
        this.functions = hashFunctionArr;
    }

    private Hasher fromHashers(final Hasher[] hasherArr) {
        return new Hasher() { // from class: com.google.common.hash.AbstractCompositeHashFunction.1
            @Override // com.google.common.hash.Hasher
            public HashCode hash() {
                return AbstractCompositeHashFunction.this.makeHash(hasherArr);
            }

            @Override // com.google.common.hash.Hasher
            public <T> Hasher putObject(T t2, Funnel<? super T> funnel) {
                for (Hasher hasher : hasherArr) {
                    hasher.putObject(t2, funnel);
                }
                return this;
            }

            @Override // com.google.common.hash.PrimitiveSink
            public Hasher putBoolean(boolean z2) {
                for (Hasher hasher : hasherArr) {
                    hasher.putBoolean(z2);
                }
                return this;
            }

            @Override // com.google.common.hash.PrimitiveSink
            public Hasher putByte(byte b3) {
                for (Hasher hasher : hasherArr) {
                    hasher.putByte(b3);
                }
                return this;
            }

            @Override // com.google.common.hash.PrimitiveSink
            public Hasher putChar(char c3) {
                for (Hasher hasher : hasherArr) {
                    hasher.putChar(c3);
                }
                return this;
            }

            @Override // com.google.common.hash.PrimitiveSink
            public Hasher putDouble(double d3) {
                for (Hasher hasher : hasherArr) {
                    hasher.putDouble(d3);
                }
                return this;
            }

            @Override // com.google.common.hash.PrimitiveSink
            public Hasher putFloat(float f2) {
                for (Hasher hasher : hasherArr) {
                    hasher.putFloat(f2);
                }
                return this;
            }

            @Override // com.google.common.hash.PrimitiveSink
            public Hasher putInt(int i2) {
                for (Hasher hasher : hasherArr) {
                    hasher.putInt(i2);
                }
                return this;
            }

            @Override // com.google.common.hash.PrimitiveSink
            public Hasher putLong(long j2) {
                for (Hasher hasher : hasherArr) {
                    hasher.putLong(j2);
                }
                return this;
            }

            @Override // com.google.common.hash.PrimitiveSink
            public Hasher putShort(short s2) {
                for (Hasher hasher : hasherArr) {
                    hasher.putShort(s2);
                }
                return this;
            }

            @Override // com.google.common.hash.PrimitiveSink
            public Hasher putString(CharSequence charSequence, Charset charset) {
                for (Hasher hasher : hasherArr) {
                    hasher.putString(charSequence, charset);
                }
                return this;
            }

            @Override // com.google.common.hash.PrimitiveSink
            public Hasher putUnencodedChars(CharSequence charSequence) {
                for (Hasher hasher : hasherArr) {
                    hasher.putUnencodedChars(charSequence);
                }
                return this;
            }

            @Override // com.google.common.hash.PrimitiveSink
            public Hasher putBytes(byte[] bArr) {
                for (Hasher hasher : hasherArr) {
                    hasher.putBytes(bArr);
                }
                return this;
            }

            @Override // com.google.common.hash.PrimitiveSink
            public Hasher putBytes(byte[] bArr, int i2, int i3) {
                for (Hasher hasher : hasherArr) {
                    hasher.putBytes(bArr, i2, i3);
                }
                return this;
            }

            @Override // com.google.common.hash.PrimitiveSink
            public Hasher putBytes(ByteBuffer byteBuffer) {
                int iPosition = byteBuffer.position();
                for (Hasher hasher : hasherArr) {
                    byteBuffer.position(iPosition);
                    hasher.putBytes(byteBuffer);
                }
                return this;
            }
        };
    }

    public abstract HashCode makeHash(Hasher[] hasherArr);

    @Override // com.google.common.hash.HashFunction
    public Hasher newHasher() {
        int length = this.functions.length;
        Hasher[] hasherArr = new Hasher[length];
        for (int i2 = 0; i2 < length; i2++) {
            hasherArr[i2] = this.functions[i2].newHasher();
        }
        return fromHashers(hasherArr);
    }

    @Override // com.google.common.hash.AbstractHashFunction, com.google.common.hash.HashFunction
    public Hasher newHasher(int i2) {
        Preconditions.checkArgument(i2 >= 0);
        int length = this.functions.length;
        Hasher[] hasherArr = new Hasher[length];
        for (int i3 = 0; i3 < length; i3++) {
            hasherArr[i3] = this.functions[i3].newHasher(i2);
        }
        return fromHashers(hasherArr);
    }
}
