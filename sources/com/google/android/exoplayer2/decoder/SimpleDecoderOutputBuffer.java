package com.google.android.exoplayer2.decoder;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.decoder.DecoderOutputBuffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* loaded from: classes3.dex */
public class SimpleDecoderOutputBuffer extends DecoderOutputBuffer {

    @Nullable
    public ByteBuffer data;
    private final DecoderOutputBuffer.Owner<SimpleDecoderOutputBuffer> owner;

    public SimpleDecoderOutputBuffer(DecoderOutputBuffer.Owner<SimpleDecoderOutputBuffer> owner) {
        this.owner = owner;
    }

    @Override // com.google.android.exoplayer2.decoder.Buffer
    public void clear() {
        super.clear();
        ByteBuffer byteBuffer = this.data;
        if (byteBuffer != null) {
            byteBuffer.clear();
        }
    }

    public ByteBuffer init(long j2, int i2) {
        this.timeUs = j2;
        ByteBuffer byteBuffer = this.data;
        if (byteBuffer == null || byteBuffer.capacity() < i2) {
            this.data = ByteBuffer.allocateDirect(i2).order(ByteOrder.nativeOrder());
        }
        this.data.position(0);
        this.data.limit(i2);
        return this.data;
    }

    @Override // com.google.android.exoplayer2.decoder.DecoderOutputBuffer
    public void release() {
        this.owner.releaseOutputBuffer(this);
    }
}
