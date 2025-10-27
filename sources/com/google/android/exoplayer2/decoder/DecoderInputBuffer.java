package com.google.android.exoplayer2.decoder;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.ExoPlayerLibraryInfo;
import com.google.android.exoplayer2.Format;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;

/* loaded from: classes3.dex */
public class DecoderInputBuffer extends Buffer {
    public static final int BUFFER_REPLACEMENT_MODE_DIRECT = 2;
    public static final int BUFFER_REPLACEMENT_MODE_DISABLED = 0;
    public static final int BUFFER_REPLACEMENT_MODE_NORMAL = 1;
    private final int bufferReplacementMode;
    public final CryptoInfo cryptoInfo;

    @Nullable
    public ByteBuffer data;

    @Nullable
    public Format format;
    private final int paddingSize;

    @Nullable
    public ByteBuffer supplementalData;
    public long timeUs;
    public boolean waitingForKeys;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface BufferReplacementMode {
    }

    public static final class InsufficientCapacityException extends IllegalStateException {
        public final int currentCapacity;
        public final int requiredCapacity;

        public InsufficientCapacityException(int i2, int i3) {
            StringBuilder sb = new StringBuilder(44);
            sb.append("Buffer too small (");
            sb.append(i2);
            sb.append(" < ");
            sb.append(i3);
            sb.append(")");
            super(sb.toString());
            this.currentCapacity = i2;
            this.requiredCapacity = i3;
        }
    }

    static {
        ExoPlayerLibraryInfo.registerModule("goog.exo.decoder");
    }

    public DecoderInputBuffer(int i2) {
        this(i2, 0);
    }

    private ByteBuffer createReplacementByteBuffer(int i2) {
        int i3 = this.bufferReplacementMode;
        if (i3 == 1) {
            return ByteBuffer.allocate(i2);
        }
        if (i3 == 2) {
            return ByteBuffer.allocateDirect(i2);
        }
        ByteBuffer byteBuffer = this.data;
        throw new InsufficientCapacityException(byteBuffer == null ? 0 : byteBuffer.capacity(), i2);
    }

    public static DecoderInputBuffer newNoDataInstance() {
        return new DecoderInputBuffer(0);
    }

    @Override // com.google.android.exoplayer2.decoder.Buffer
    public void clear() {
        super.clear();
        ByteBuffer byteBuffer = this.data;
        if (byteBuffer != null) {
            byteBuffer.clear();
        }
        ByteBuffer byteBuffer2 = this.supplementalData;
        if (byteBuffer2 != null) {
            byteBuffer2.clear();
        }
        this.waitingForKeys = false;
    }

    @EnsuresNonNull({"data"})
    public void ensureSpaceForWrite(int i2) {
        int i3 = i2 + this.paddingSize;
        ByteBuffer byteBuffer = this.data;
        if (byteBuffer == null) {
            this.data = createReplacementByteBuffer(i3);
            return;
        }
        int iCapacity = byteBuffer.capacity();
        int iPosition = byteBuffer.position();
        int i4 = i3 + iPosition;
        if (iCapacity >= i4) {
            this.data = byteBuffer;
            return;
        }
        ByteBuffer byteBufferCreateReplacementByteBuffer = createReplacementByteBuffer(i4);
        byteBufferCreateReplacementByteBuffer.order(byteBuffer.order());
        if (iPosition > 0) {
            byteBuffer.flip();
            byteBufferCreateReplacementByteBuffer.put(byteBuffer);
        }
        this.data = byteBufferCreateReplacementByteBuffer;
    }

    public final void flip() {
        ByteBuffer byteBuffer = this.data;
        if (byteBuffer != null) {
            byteBuffer.flip();
        }
        ByteBuffer byteBuffer2 = this.supplementalData;
        if (byteBuffer2 != null) {
            byteBuffer2.flip();
        }
    }

    public final boolean isEncrypted() {
        return getFlag(1073741824);
    }

    @EnsuresNonNull({"supplementalData"})
    public void resetSupplementalData(int i2) {
        ByteBuffer byteBuffer = this.supplementalData;
        if (byteBuffer == null || byteBuffer.capacity() < i2) {
            this.supplementalData = ByteBuffer.allocate(i2);
        } else {
            this.supplementalData.clear();
        }
    }

    public DecoderInputBuffer(int i2, int i3) {
        this.cryptoInfo = new CryptoInfo();
        this.bufferReplacementMode = i2;
        this.paddingSize = i3;
    }
}
