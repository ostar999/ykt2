package com.google.common.hash;

import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.Immutable;
import java.io.Serializable;
import java.nio.ByteBuffer;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Immutable
/* loaded from: classes4.dex */
final class SipHashFunction extends AbstractHashFunction implements Serializable {
    static final HashFunction SIP_HASH_24 = new SipHashFunction(2, 4, 506097522914230528L, 1084818905618843912L);
    private static final long serialVersionUID = 0;

    /* renamed from: c, reason: collision with root package name */
    private final int f7057c;

    /* renamed from: d, reason: collision with root package name */
    private final int f7058d;

    /* renamed from: k0, reason: collision with root package name */
    private final long f7059k0;

    /* renamed from: k1, reason: collision with root package name */
    private final long f7060k1;

    public static final class SipHasher extends AbstractStreamingHasher {
        private static final int CHUNK_SIZE = 8;

        /* renamed from: b, reason: collision with root package name */
        private long f7061b;

        /* renamed from: c, reason: collision with root package name */
        private final int f7062c;

        /* renamed from: d, reason: collision with root package name */
        private final int f7063d;
        private long finalM;

        /* renamed from: v0, reason: collision with root package name */
        private long f7064v0;

        /* renamed from: v1, reason: collision with root package name */
        private long f7065v1;
        private long v2;
        private long v3;

        public SipHasher(int i2, int i3, long j2, long j3) {
            super(8);
            this.f7061b = 0L;
            this.finalM = 0L;
            this.f7062c = i2;
            this.f7063d = i3;
            this.f7064v0 = 8317987319222330741L ^ j2;
            this.f7065v1 = 7237128888997146477L ^ j3;
            this.v2 = 7816392313619706465L ^ j2;
            this.v3 = 8387220255154660723L ^ j3;
        }

        private void processM(long j2) {
            this.v3 ^= j2;
            sipRound(this.f7062c);
            this.f7064v0 = j2 ^ this.f7064v0;
        }

        private void sipRound(int i2) {
            for (int i3 = 0; i3 < i2; i3++) {
                long j2 = this.f7064v0;
                long j3 = this.f7065v1;
                this.f7064v0 = j2 + j3;
                this.v2 += this.v3;
                this.f7065v1 = Long.rotateLeft(j3, 13);
                long jRotateLeft = Long.rotateLeft(this.v3, 16);
                long j4 = this.f7065v1;
                long j5 = this.f7064v0;
                this.f7065v1 = j4 ^ j5;
                this.v3 = jRotateLeft ^ this.v2;
                long jRotateLeft2 = Long.rotateLeft(j5, 32);
                long j6 = this.v2;
                long j7 = this.f7065v1;
                this.v2 = j6 + j7;
                this.f7064v0 = jRotateLeft2 + this.v3;
                this.f7065v1 = Long.rotateLeft(j7, 17);
                long jRotateLeft3 = Long.rotateLeft(this.v3, 21);
                long j8 = this.f7065v1;
                long j9 = this.v2;
                this.f7065v1 = j8 ^ j9;
                this.v3 = jRotateLeft3 ^ this.f7064v0;
                this.v2 = Long.rotateLeft(j9, 32);
            }
        }

        @Override // com.google.common.hash.AbstractStreamingHasher
        public HashCode makeHash() {
            long j2 = this.finalM ^ (this.f7061b << 56);
            this.finalM = j2;
            processM(j2);
            this.v2 ^= 255;
            sipRound(this.f7063d);
            return HashCode.fromLong(((this.f7064v0 ^ this.f7065v1) ^ this.v2) ^ this.v3);
        }

        @Override // com.google.common.hash.AbstractStreamingHasher
        public void process(ByteBuffer byteBuffer) {
            this.f7061b += 8;
            processM(byteBuffer.getLong());
        }

        @Override // com.google.common.hash.AbstractStreamingHasher
        public void processRemaining(ByteBuffer byteBuffer) {
            this.f7061b += byteBuffer.remaining();
            int i2 = 0;
            while (byteBuffer.hasRemaining()) {
                this.finalM ^= (byteBuffer.get() & 255) << i2;
                i2 += 8;
            }
        }
    }

    public SipHashFunction(int i2, int i3, long j2, long j3) {
        Preconditions.checkArgument(i2 > 0, "The number of SipRound iterations (c=%s) during Compression must be positive.", i2);
        Preconditions.checkArgument(i3 > 0, "The number of SipRound iterations (d=%s) during Finalization must be positive.", i3);
        this.f7057c = i2;
        this.f7058d = i3;
        this.f7059k0 = j2;
        this.f7060k1 = j3;
    }

    @Override // com.google.common.hash.HashFunction
    public int bits() {
        return 64;
    }

    public boolean equals(@NullableDecl Object obj) {
        if (!(obj instanceof SipHashFunction)) {
            return false;
        }
        SipHashFunction sipHashFunction = (SipHashFunction) obj;
        return this.f7057c == sipHashFunction.f7057c && this.f7058d == sipHashFunction.f7058d && this.f7059k0 == sipHashFunction.f7059k0 && this.f7060k1 == sipHashFunction.f7060k1;
    }

    public int hashCode() {
        return (int) ((((SipHashFunction.class.hashCode() ^ this.f7057c) ^ this.f7058d) ^ this.f7059k0) ^ this.f7060k1);
    }

    @Override // com.google.common.hash.HashFunction
    public Hasher newHasher() {
        return new SipHasher(this.f7057c, this.f7058d, this.f7059k0, this.f7060k1);
    }

    public String toString() {
        return "Hashing.sipHash" + this.f7057c + "" + this.f7058d + "(" + this.f7059k0 + ", " + this.f7060k1 + ")";
    }
}
