package com.google.android.exoplayer2.transformer;

import android.media.MediaCodec;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.audio.AudioProcessor;
import com.google.android.exoplayer2.audio.SonicAudioProcessor;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.util.Assertions;
import java.io.IOException;
import java.nio.ByteBuffer;
import org.checkerframework.checker.nullness.qual.EnsuresNonNullIf;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

@RequiresApi(18)
/* loaded from: classes3.dex */
final class TransformerAudioRenderer extends TransformerBaseRenderer {
    private static final int DEFAULT_ENCODER_BITRATE = 131072;
    private static final float SPEED_UNSET = -1.0f;
    private static final String TAG = "TransformerAudioRenderer";
    private float currentSpeed;

    @Nullable
    private MediaCodecAdapterWrapper decoder;
    private final DecoderInputBuffer decoderInputBuffer;
    private Format decoderInputFormat;
    private boolean drainingSonicForSpeedChange;

    @Nullable
    private MediaCodecAdapterWrapper encoder;
    private AudioProcessor.AudioFormat encoderInputAudioFormat;
    private final DecoderInputBuffer encoderInputBuffer;
    private boolean hasEncoderOutputFormat;
    private boolean muxerWrapperTrackEnded;
    private long nextEncoderInputBufferTimeUs;
    private final SonicAudioProcessor sonicAudioProcessor;
    private ByteBuffer sonicOutputBuffer;

    @Nullable
    private SpeedProvider speedProvider;

    public TransformerAudioRenderer(MuxerWrapper muxerWrapper, TransformerMediaClock transformerMediaClock, Transformation transformation) {
        super(1, muxerWrapper, transformerMediaClock, transformation);
        this.decoderInputBuffer = new DecoderInputBuffer(0);
        this.encoderInputBuffer = new DecoderInputBuffer(0);
        this.sonicAudioProcessor = new SonicAudioProcessor();
        this.sonicOutputBuffer = AudioProcessor.EMPTY_BUFFER;
        this.nextEncoderInputBufferTimeUs = 0L;
        this.currentSpeed = -1.0f;
    }

    private ExoPlaybackException createRendererException(Throwable th, int i2) {
        return ExoPlaybackException.createForRenderer(th, TAG, getIndex(), this.decoderInputFormat, 4, false, i2);
    }

    @EnsuresNonNullIf(expression = {"decoderInputFormat", "decoder"}, result = true)
    private boolean ensureDecoderConfigured() throws ExoPlaybackException {
        if (this.decoder != null && this.decoderInputFormat != null) {
            return true;
        }
        FormatHolder formatHolder = getFormatHolder();
        if (readSource(formatHolder, this.decoderInputBuffer, 2) != -5) {
            return false;
        }
        Format format = (Format) Assertions.checkNotNull(formatHolder.format);
        this.decoderInputFormat = format;
        try {
            MediaCodecAdapterWrapper mediaCodecAdapterWrapperCreateForAudioDecoding = MediaCodecAdapterWrapper.createForAudioDecoding(format);
            SegmentSpeedProvider segmentSpeedProvider = new SegmentSpeedProvider(this.decoderInputFormat);
            this.speedProvider = segmentSpeedProvider;
            this.currentSpeed = segmentSpeedProvider.getSpeed(0L);
            this.decoder = mediaCodecAdapterWrapperCreateForAudioDecoding;
            return true;
        } catch (IOException e2) {
            throw createRendererException(e2, 1000);
        }
    }

    @EnsuresNonNullIf(expression = {"encoder", "encoderInputAudioFormat"}, result = true)
    @RequiresNonNull({"decoder", "decoderInputFormat"})
    private boolean ensureEncoderAndAudioProcessingConfigured() throws ExoPlaybackException {
        if (this.encoder != null && this.encoderInputAudioFormat != null) {
            return true;
        }
        Format outputFormat = this.decoder.getOutputFormat();
        if (outputFormat == null) {
            return false;
        }
        AudioProcessor.AudioFormat audioFormat = new AudioProcessor.AudioFormat(outputFormat.sampleRate, outputFormat.channelCount, outputFormat.pcmEncoding);
        if (this.transformation.flattenForSlowMotion) {
            try {
                audioFormat = this.sonicAudioProcessor.configure(audioFormat);
                flushSonicAndSetSpeed(this.currentSpeed);
            } catch (AudioProcessor.UnhandledAudioFormatException e2) {
                throw createRendererException(e2, 1000);
            }
        }
        String str = this.transformation.audioMimeType;
        if (str == null) {
            str = this.decoderInputFormat.sampleMimeType;
        }
        try {
            this.encoder = MediaCodecAdapterWrapper.createForAudioEncoding(new Format.Builder().setSampleMimeType(str).setSampleRate(audioFormat.sampleRate).setChannelCount(audioFormat.channelCount).setAverageBitrate(131072).build());
            this.encoderInputAudioFormat = audioFormat;
            return true;
        } catch (IOException e3) {
            throw createRendererException(e3, 1000);
        }
    }

    private boolean feedDecoderFromInput(MediaCodecAdapterWrapper mediaCodecAdapterWrapper) {
        if (!mediaCodecAdapterWrapper.maybeDequeueInputBuffer(this.decoderInputBuffer)) {
            return false;
        }
        this.decoderInputBuffer.clear();
        int source = readSource(getFormatHolder(), this.decoderInputBuffer, 0);
        if (source == -5) {
            throw new IllegalStateException("Format changes are not supported.");
        }
        if (source != -4) {
            return false;
        }
        this.mediaClock.updateTimeForTrackType(getTrackType(), this.decoderInputBuffer.timeUs);
        DecoderInputBuffer decoderInputBuffer = this.decoderInputBuffer;
        decoderInputBuffer.timeUs -= ((TransformerBaseRenderer) this).streamOffsetUs;
        decoderInputBuffer.flip();
        mediaCodecAdapterWrapper.queueInputBuffer(this.decoderInputBuffer);
        return !this.decoderInputBuffer.isEndOfStream();
    }

    @RequiresNonNull({"encoderInputAudioFormat"})
    private void feedEncoder(MediaCodecAdapterWrapper mediaCodecAdapterWrapper, ByteBuffer byteBuffer) {
        ByteBuffer byteBuffer2 = (ByteBuffer) Assertions.checkNotNull(this.encoderInputBuffer.data);
        int iLimit = byteBuffer.limit();
        byteBuffer.limit(Math.min(iLimit, byteBuffer.position() + byteBuffer2.capacity()));
        byteBuffer2.put(byteBuffer);
        DecoderInputBuffer decoderInputBuffer = this.encoderInputBuffer;
        long j2 = this.nextEncoderInputBufferTimeUs;
        decoderInputBuffer.timeUs = j2;
        long jPosition = byteBuffer2.position();
        AudioProcessor.AudioFormat audioFormat = this.encoderInputAudioFormat;
        this.nextEncoderInputBufferTimeUs = j2 + getBufferDurationUs(jPosition, audioFormat.bytesPerFrame, audioFormat.sampleRate);
        this.encoderInputBuffer.setFlags(0);
        this.encoderInputBuffer.flip();
        byteBuffer.limit(iLimit);
        mediaCodecAdapterWrapper.queueInputBuffer(this.encoderInputBuffer);
    }

    @RequiresNonNull({"encoderInputAudioFormat"})
    private boolean feedEncoderFromDecoder(MediaCodecAdapterWrapper mediaCodecAdapterWrapper, MediaCodecAdapterWrapper mediaCodecAdapterWrapper2) {
        if (!mediaCodecAdapterWrapper2.maybeDequeueInputBuffer(this.encoderInputBuffer)) {
            return false;
        }
        if (mediaCodecAdapterWrapper.isEnded()) {
            queueEndOfStreamToEncoder(mediaCodecAdapterWrapper2);
            return false;
        }
        ByteBuffer outputBuffer = mediaCodecAdapterWrapper.getOutputBuffer();
        if (outputBuffer == null) {
            return false;
        }
        if (isSpeedChanging((MediaCodec.BufferInfo) Assertions.checkNotNull(mediaCodecAdapterWrapper.getOutputBufferInfo()))) {
            flushSonicAndSetSpeed(this.currentSpeed);
            return false;
        }
        feedEncoder(mediaCodecAdapterWrapper2, outputBuffer);
        if (outputBuffer.hasRemaining()) {
            return true;
        }
        mediaCodecAdapterWrapper.releaseOutputBuffer();
        return true;
    }

    @RequiresNonNull({"encoderInputAudioFormat"})
    private boolean feedEncoderFromSonic(MediaCodecAdapterWrapper mediaCodecAdapterWrapper, MediaCodecAdapterWrapper mediaCodecAdapterWrapper2) {
        if (!mediaCodecAdapterWrapper2.maybeDequeueInputBuffer(this.encoderInputBuffer)) {
            return false;
        }
        if (!this.sonicOutputBuffer.hasRemaining()) {
            ByteBuffer output = this.sonicAudioProcessor.getOutput();
            this.sonicOutputBuffer = output;
            if (!output.hasRemaining()) {
                if (mediaCodecAdapterWrapper.isEnded() && this.sonicAudioProcessor.isEnded()) {
                    queueEndOfStreamToEncoder(mediaCodecAdapterWrapper2);
                }
                return false;
            }
        }
        feedEncoder(mediaCodecAdapterWrapper2, this.sonicOutputBuffer);
        return true;
    }

    private boolean feedMuxerFromEncoder(MediaCodecAdapterWrapper mediaCodecAdapterWrapper) {
        if (!this.hasEncoderOutputFormat) {
            Format outputFormat = mediaCodecAdapterWrapper.getOutputFormat();
            if (outputFormat == null) {
                return false;
            }
            this.hasEncoderOutputFormat = true;
            this.muxerWrapper.addTrackFormat(outputFormat);
        }
        if (mediaCodecAdapterWrapper.isEnded()) {
            this.muxerWrapper.endTrack(getTrackType());
            this.muxerWrapperTrackEnded = true;
            return false;
        }
        ByteBuffer outputBuffer = mediaCodecAdapterWrapper.getOutputBuffer();
        if (outputBuffer == null) {
            return false;
        }
        if (!this.muxerWrapper.writeSample(getTrackType(), outputBuffer, true, ((MediaCodec.BufferInfo) Assertions.checkNotNull(mediaCodecAdapterWrapper.getOutputBufferInfo())).presentationTimeUs)) {
            return false;
        }
        mediaCodecAdapterWrapper.releaseOutputBuffer();
        return true;
    }

    private boolean feedSonicFromDecoder(MediaCodecAdapterWrapper mediaCodecAdapterWrapper) {
        if (this.drainingSonicForSpeedChange) {
            if (this.sonicAudioProcessor.isEnded() && !this.sonicOutputBuffer.hasRemaining()) {
                flushSonicAndSetSpeed(this.currentSpeed);
                this.drainingSonicForSpeedChange = false;
            }
            return false;
        }
        if (this.sonicOutputBuffer.hasRemaining()) {
            return false;
        }
        if (mediaCodecAdapterWrapper.isEnded()) {
            this.sonicAudioProcessor.queueEndOfStream();
            return false;
        }
        Assertions.checkState(!this.sonicAudioProcessor.isEnded());
        ByteBuffer outputBuffer = mediaCodecAdapterWrapper.getOutputBuffer();
        if (outputBuffer == null) {
            return false;
        }
        if (isSpeedChanging((MediaCodec.BufferInfo) Assertions.checkNotNull(mediaCodecAdapterWrapper.getOutputBufferInfo()))) {
            this.sonicAudioProcessor.queueEndOfStream();
            this.drainingSonicForSpeedChange = true;
            return false;
        }
        this.sonicAudioProcessor.queueInput(outputBuffer);
        if (!outputBuffer.hasRemaining()) {
            mediaCodecAdapterWrapper.releaseOutputBuffer();
        }
        return true;
    }

    private void flushSonicAndSetSpeed(float f2) {
        this.sonicAudioProcessor.setSpeed(f2);
        this.sonicAudioProcessor.setPitch(f2);
        this.sonicAudioProcessor.flush();
    }

    private static long getBufferDurationUs(long j2, int i2, int i3) {
        return ((j2 / i2) * 1000000) / i3;
    }

    private boolean isSpeedChanging(MediaCodec.BufferInfo bufferInfo) {
        if (!this.transformation.flattenForSlowMotion) {
            return false;
        }
        float speed = ((SpeedProvider) Assertions.checkNotNull(this.speedProvider)).getSpeed(bufferInfo.presentationTimeUs);
        boolean z2 = speed != this.currentSpeed;
        this.currentSpeed = speed;
        return z2;
    }

    private void queueEndOfStreamToEncoder(MediaCodecAdapterWrapper mediaCodecAdapterWrapper) {
        Assertions.checkState(((ByteBuffer) Assertions.checkNotNull(this.encoderInputBuffer.data)).position() == 0);
        DecoderInputBuffer decoderInputBuffer = this.encoderInputBuffer;
        decoderInputBuffer.timeUs = this.nextEncoderInputBufferTimeUs;
        decoderInputBuffer.addFlag(4);
        this.encoderInputBuffer.flip();
        mediaCodecAdapterWrapper.queueInputBuffer(this.encoderInputBuffer);
    }

    @Override // com.google.android.exoplayer2.Renderer, com.google.android.exoplayer2.RendererCapabilities
    public String getName() {
        return TAG;
    }

    @Override // com.google.android.exoplayer2.Renderer
    public boolean isEnded() {
        return this.muxerWrapperTrackEnded;
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    public void onReset() {
        this.decoderInputBuffer.clear();
        this.decoderInputBuffer.data = null;
        this.encoderInputBuffer.clear();
        this.encoderInputBuffer.data = null;
        this.sonicAudioProcessor.reset();
        MediaCodecAdapterWrapper mediaCodecAdapterWrapper = this.decoder;
        if (mediaCodecAdapterWrapper != null) {
            mediaCodecAdapterWrapper.release();
            this.decoder = null;
        }
        MediaCodecAdapterWrapper mediaCodecAdapterWrapper2 = this.encoder;
        if (mediaCodecAdapterWrapper2 != null) {
            mediaCodecAdapterWrapper2.release();
            this.encoder = null;
        }
        this.speedProvider = null;
        this.sonicOutputBuffer = AudioProcessor.EMPTY_BUFFER;
        this.nextEncoderInputBufferTimeUs = 0L;
        this.currentSpeed = -1.0f;
        this.muxerWrapperTrackEnded = false;
        this.hasEncoderOutputFormat = false;
        this.drainingSonicForSpeedChange = false;
    }

    @Override // com.google.android.exoplayer2.Renderer
    public void render(long j2, long j3) throws ExoPlaybackException {
        if (this.isRendererStarted && !isEnded() && ensureDecoderConfigured()) {
            MediaCodecAdapterWrapper mediaCodecAdapterWrapper = this.decoder;
            if (ensureEncoderAndAudioProcessingConfigured()) {
                MediaCodecAdapterWrapper mediaCodecAdapterWrapper2 = this.encoder;
                while (feedMuxerFromEncoder(mediaCodecAdapterWrapper2)) {
                }
                if (this.sonicAudioProcessor.isActive()) {
                    while (feedEncoderFromSonic(mediaCodecAdapterWrapper, mediaCodecAdapterWrapper2)) {
                    }
                    while (feedSonicFromDecoder(mediaCodecAdapterWrapper)) {
                    }
                } else {
                    while (feedEncoderFromDecoder(mediaCodecAdapterWrapper, mediaCodecAdapterWrapper2)) {
                    }
                }
            }
            while (feedDecoderFromInput(mediaCodecAdapterWrapper)) {
            }
        }
    }
}
