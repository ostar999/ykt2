package com.google.android.exoplayer2.audio;

import com.google.android.exoplayer2.audio.AudioProcessor;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.nio.ByteBuffer;

/* loaded from: classes3.dex */
public final class SilenceSkippingAudioProcessor extends BaseAudioProcessor {
    public static final long DEFAULT_MINIMUM_SILENCE_DURATION_US = 150000;
    public static final long DEFAULT_PADDING_SILENCE_US = 20000;
    public static final short DEFAULT_SILENCE_THRESHOLD_LEVEL = 1024;
    private static final int STATE_MAYBE_SILENT = 1;
    private static final int STATE_NOISY = 0;
    private static final int STATE_SILENT = 2;
    private int bytesPerFrame;
    private boolean enabled;
    private boolean hasOutputNoise;
    private byte[] maybeSilenceBuffer;
    private int maybeSilenceBufferSize;
    private final long minimumSilenceDurationUs;
    private byte[] paddingBuffer;
    private final long paddingSilenceUs;
    private int paddingSize;
    private final short silenceThresholdLevel;
    private long skippedFrames;
    private int state;

    public SilenceSkippingAudioProcessor() {
        this(DEFAULT_MINIMUM_SILENCE_DURATION_US, DEFAULT_PADDING_SILENCE_US, DEFAULT_SILENCE_THRESHOLD_LEVEL);
    }

    private int durationUsToFrames(long j2) {
        return (int) ((j2 * this.inputAudioFormat.sampleRate) / 1000000);
    }

    private int findNoiseLimit(ByteBuffer byteBuffer) {
        int iLimit = byteBuffer.limit();
        do {
            iLimit -= 2;
            if (iLimit < byteBuffer.position()) {
                return byteBuffer.position();
            }
        } while (Math.abs((int) byteBuffer.getShort(iLimit)) <= this.silenceThresholdLevel);
        int i2 = this.bytesPerFrame;
        return ((iLimit / i2) * i2) + i2;
    }

    private int findNoisePosition(ByteBuffer byteBuffer) {
        for (int iPosition = byteBuffer.position(); iPosition < byteBuffer.limit(); iPosition += 2) {
            if (Math.abs((int) byteBuffer.getShort(iPosition)) > this.silenceThresholdLevel) {
                int i2 = this.bytesPerFrame;
                return i2 * (iPosition / i2);
            }
        }
        return byteBuffer.limit();
    }

    private void output(byte[] bArr, int i2) {
        replaceOutputBuffer(i2).put(bArr, 0, i2).flip();
        if (i2 > 0) {
            this.hasOutputNoise = true;
        }
    }

    private void processMaybeSilence(ByteBuffer byteBuffer) {
        int iLimit = byteBuffer.limit();
        int iFindNoisePosition = findNoisePosition(byteBuffer);
        int iPosition = iFindNoisePosition - byteBuffer.position();
        byte[] bArr = this.maybeSilenceBuffer;
        int length = bArr.length;
        int i2 = this.maybeSilenceBufferSize;
        int i3 = length - i2;
        if (iFindNoisePosition < iLimit && iPosition < i3) {
            output(bArr, i2);
            this.maybeSilenceBufferSize = 0;
            this.state = 0;
            return;
        }
        int iMin = Math.min(iPosition, i3);
        byteBuffer.limit(byteBuffer.position() + iMin);
        byteBuffer.get(this.maybeSilenceBuffer, this.maybeSilenceBufferSize, iMin);
        int i4 = this.maybeSilenceBufferSize + iMin;
        this.maybeSilenceBufferSize = i4;
        byte[] bArr2 = this.maybeSilenceBuffer;
        if (i4 == bArr2.length) {
            if (this.hasOutputNoise) {
                output(bArr2, this.paddingSize);
                this.skippedFrames += (this.maybeSilenceBufferSize - (this.paddingSize * 2)) / this.bytesPerFrame;
            } else {
                this.skippedFrames += (i4 - this.paddingSize) / this.bytesPerFrame;
            }
            updatePaddingBuffer(byteBuffer, this.maybeSilenceBuffer, this.maybeSilenceBufferSize);
            this.maybeSilenceBufferSize = 0;
            this.state = 2;
        }
        byteBuffer.limit(iLimit);
    }

    private void processNoisy(ByteBuffer byteBuffer) {
        int iLimit = byteBuffer.limit();
        byteBuffer.limit(Math.min(iLimit, byteBuffer.position() + this.maybeSilenceBuffer.length));
        int iFindNoiseLimit = findNoiseLimit(byteBuffer);
        if (iFindNoiseLimit == byteBuffer.position()) {
            this.state = 1;
        } else {
            byteBuffer.limit(iFindNoiseLimit);
            output(byteBuffer);
        }
        byteBuffer.limit(iLimit);
    }

    private void processSilence(ByteBuffer byteBuffer) {
        int iLimit = byteBuffer.limit();
        int iFindNoisePosition = findNoisePosition(byteBuffer);
        byteBuffer.limit(iFindNoisePosition);
        this.skippedFrames += byteBuffer.remaining() / this.bytesPerFrame;
        updatePaddingBuffer(byteBuffer, this.paddingBuffer, this.paddingSize);
        if (iFindNoisePosition < iLimit) {
            output(this.paddingBuffer, this.paddingSize);
            this.state = 0;
            byteBuffer.limit(iLimit);
        }
    }

    private void updatePaddingBuffer(ByteBuffer byteBuffer, byte[] bArr, int i2) {
        int iMin = Math.min(byteBuffer.remaining(), this.paddingSize);
        int i3 = this.paddingSize - iMin;
        System.arraycopy(bArr, i2 - i3, this.paddingBuffer, 0, i3);
        byteBuffer.position(byteBuffer.limit() - iMin);
        byteBuffer.get(this.paddingBuffer, i3, iMin);
    }

    public long getSkippedFrames() {
        return this.skippedFrames;
    }

    @Override // com.google.android.exoplayer2.audio.BaseAudioProcessor, com.google.android.exoplayer2.audio.AudioProcessor
    public boolean isActive() {
        return this.enabled;
    }

    @Override // com.google.android.exoplayer2.audio.BaseAudioProcessor
    public AudioProcessor.AudioFormat onConfigure(AudioProcessor.AudioFormat audioFormat) throws AudioProcessor.UnhandledAudioFormatException {
        if (audioFormat.encoding == 2) {
            return this.enabled ? audioFormat : AudioProcessor.AudioFormat.NOT_SET;
        }
        throw new AudioProcessor.UnhandledAudioFormatException(audioFormat);
    }

    @Override // com.google.android.exoplayer2.audio.BaseAudioProcessor
    public void onFlush() {
        if (this.enabled) {
            this.bytesPerFrame = this.inputAudioFormat.bytesPerFrame;
            int iDurationUsToFrames = durationUsToFrames(this.minimumSilenceDurationUs) * this.bytesPerFrame;
            if (this.maybeSilenceBuffer.length != iDurationUsToFrames) {
                this.maybeSilenceBuffer = new byte[iDurationUsToFrames];
            }
            int iDurationUsToFrames2 = durationUsToFrames(this.paddingSilenceUs) * this.bytesPerFrame;
            this.paddingSize = iDurationUsToFrames2;
            if (this.paddingBuffer.length != iDurationUsToFrames2) {
                this.paddingBuffer = new byte[iDurationUsToFrames2];
            }
        }
        this.state = 0;
        this.skippedFrames = 0L;
        this.maybeSilenceBufferSize = 0;
        this.hasOutputNoise = false;
    }

    @Override // com.google.android.exoplayer2.audio.BaseAudioProcessor
    public void onQueueEndOfStream() {
        int i2 = this.maybeSilenceBufferSize;
        if (i2 > 0) {
            output(this.maybeSilenceBuffer, i2);
        }
        if (this.hasOutputNoise) {
            return;
        }
        this.skippedFrames += this.paddingSize / this.bytesPerFrame;
    }

    @Override // com.google.android.exoplayer2.audio.BaseAudioProcessor
    public void onReset() {
        this.enabled = false;
        this.paddingSize = 0;
        byte[] bArr = Util.EMPTY_BYTE_ARRAY;
        this.maybeSilenceBuffer = bArr;
        this.paddingBuffer = bArr;
    }

    @Override // com.google.android.exoplayer2.audio.AudioProcessor
    public void queueInput(ByteBuffer byteBuffer) {
        while (byteBuffer.hasRemaining() && !hasPendingOutput()) {
            int i2 = this.state;
            if (i2 == 0) {
                processNoisy(byteBuffer);
            } else if (i2 == 1) {
                processMaybeSilence(byteBuffer);
            } else {
                if (i2 != 2) {
                    throw new IllegalStateException();
                }
                processSilence(byteBuffer);
            }
        }
    }

    public void setEnabled(boolean z2) {
        this.enabled = z2;
    }

    public SilenceSkippingAudioProcessor(long j2, long j3, short s2) {
        Assertions.checkArgument(j3 <= j2);
        this.minimumSilenceDurationUs = j2;
        this.paddingSilenceUs = j3;
        this.silenceThresholdLevel = s2;
        byte[] bArr = Util.EMPTY_BYTE_ARRAY;
        this.maybeSilenceBuffer = bArr;
        this.paddingBuffer = bArr;
    }

    private void output(ByteBuffer byteBuffer) {
        int iRemaining = byteBuffer.remaining();
        replaceOutputBuffer(iRemaining).put(byteBuffer).flip();
        if (iRemaining > 0) {
            this.hasOutputNoise = true;
        }
    }
}
