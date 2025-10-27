package com.google.android.exoplayer2.transformer;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.util.Assertions;
import java.nio.ByteBuffer;

@RequiresApi(18)
/* loaded from: classes3.dex */
final class TransformerMuxingVideoRenderer extends TransformerBaseRenderer {
    private static final String TAG = "TransformerVideoRenderer";
    private final DecoderInputBuffer buffer;
    private boolean formatRead;
    private boolean isBufferPending;
    private boolean isInputStreamEnded;

    @Nullable
    private SampleTransformer sampleTransformer;

    public TransformerMuxingVideoRenderer(MuxerWrapper muxerWrapper, TransformerMediaClock transformerMediaClock, Transformation transformation) {
        super(2, muxerWrapper, transformerMediaClock, transformation);
        this.buffer = new DecoderInputBuffer(2);
    }

    private boolean readAndTransformBuffer() {
        this.buffer.clear();
        int source = readSource(getFormatHolder(), this.buffer, 0);
        if (source == -5) {
            throw new IllegalStateException("Format changes are not supported.");
        }
        if (source == -3) {
            return false;
        }
        if (this.buffer.isEndOfStream()) {
            this.isInputStreamEnded = true;
            this.muxerWrapper.endTrack(getTrackType());
            return false;
        }
        this.mediaClock.updateTimeForTrackType(getTrackType(), this.buffer.timeUs);
        DecoderInputBuffer decoderInputBuffer = this.buffer;
        decoderInputBuffer.timeUs -= ((TransformerBaseRenderer) this).streamOffsetUs;
        ((ByteBuffer) Assertions.checkNotNull(decoderInputBuffer.data)).flip();
        SampleTransformer sampleTransformer = this.sampleTransformer;
        if (sampleTransformer != null) {
            sampleTransformer.transformSample(this.buffer);
        }
        return true;
    }

    @Override // com.google.android.exoplayer2.Renderer, com.google.android.exoplayer2.RendererCapabilities
    public String getName() {
        return TAG;
    }

    @Override // com.google.android.exoplayer2.Renderer
    public boolean isEnded() {
        return this.isInputStreamEnded;
    }

    @Override // com.google.android.exoplayer2.Renderer
    public void render(long j2, long j3) {
        boolean z2;
        if (!this.isRendererStarted || isEnded()) {
            return;
        }
        if (!this.formatRead) {
            FormatHolder formatHolder = getFormatHolder();
            if (readSource(formatHolder, this.buffer, 2) != -5) {
                return;
            }
            Format format = (Format) Assertions.checkNotNull(formatHolder.format);
            this.formatRead = true;
            if (this.transformation.flattenForSlowMotion) {
                this.sampleTransformer = new SefSlowMotionVideoSampleTransformer(format);
            }
            this.muxerWrapper.addTrackFormat(format);
        }
        do {
            if (!this.isBufferPending && !readAndTransformBuffer()) {
                return;
            }
            MuxerWrapper muxerWrapper = this.muxerWrapper;
            int trackType = getTrackType();
            DecoderInputBuffer decoderInputBuffer = this.buffer;
            z2 = !muxerWrapper.writeSample(trackType, decoderInputBuffer.data, decoderInputBuffer.isKeyFrame(), this.buffer.timeUs);
            this.isBufferPending = z2;
        } while (!z2);
    }
}
