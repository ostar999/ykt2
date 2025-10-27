package com.google.android.exoplayer2.decoder;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.decoder.DecoderException;

/* loaded from: classes3.dex */
public interface Decoder<I, O, E extends DecoderException> {
    @Nullable
    I dequeueInputBuffer() throws DecoderException;

    @Nullable
    O dequeueOutputBuffer() throws DecoderException;

    void flush();

    String getName();

    void queueInputBuffer(I i2) throws DecoderException;

    void release();
}
